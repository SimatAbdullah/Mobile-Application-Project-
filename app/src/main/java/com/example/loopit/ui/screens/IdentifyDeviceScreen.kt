
package com.example.loopit.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val identifyPurple = Color(0xFF504DCC)
private val identifyBackground = Color(0xFFF8FAFD)
private val identifySoftPurple = Color(0xFFF0EFFF)
private val identifyText = Color(0xFF20202A)
private val identifyMuted = Color(0xFF747584)
private val identifyBorder = Color(0xFFE8E8F0)

private val deviceCategories = listOf(
    "Smartphone", "Charger", "Tablet", "Laptop",
    "Accessories", "Other Devices"
)

@Composable
fun IdentifyDeviceScreen(
    onBackClick: () -> Unit = {},
    onContinueToDisposalGuidance: (String) -> Unit = {},
    onTakePhotoClick: (() -> Unit)? = null
) {
    var selectedDevice by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var otherDeviceName by rememberSaveable {
        mutableStateOf("")
    }

    var capturedPhoto by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var suggestedDevice by remember {
        mutableStateOf<String?>(null)
    }

    var suggestionConfidence by remember {
        mutableFloatStateOf(0f)
    }

    var photoMessage by remember {
        mutableStateOf<String?>(null)
    }

    var imageLabels by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    var showLabelDetails by remember {
        mutableStateOf(false)
    }

    var isAnalyzing by remember {
        mutableStateOf(false)
    }

    var analysisRequestId by remember {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current
    val analysisScope = rememberCoroutineScope()

    fun clearPhotoState() {
        analysisRequestId += 1
        capturedPhoto = null
        suggestedDevice = null
        suggestionConfidence = 0f
        photoMessage = null
        imageLabels = emptyList()
        showLabelDetails = false
        isAnalyzing = false
    }

    fun analyzeImage(bitmap: Bitmap) {
        analysisRequestId += 1
        val requestId = analysisRequestId

        isAnalyzing = true
        suggestedDevice = null
        suggestionConfidence = 0f
        imageLabels = emptyList()
        showLabelDetails = false
        photoMessage = "Analyzing your photo..."

        analysisScope.launch {
            try {
                val predictions = withContext(Dispatchers.Default) {
                    TeachableMachineClassifier(
                        context.applicationContext
                    ).use { classifier ->
                        classifier.classify(bitmap)
                    }
                }

                if (requestId == analysisRequestId) {
                    imageLabels = predictions.map { prediction ->
                        "${prediction.label}: ${(prediction.score * 100).toInt()}%"
                    }

                    val candidate = predictions.firstOrNull()

                    if (
                        candidate != null &&
                        candidate.score >= 0.70f
                    ) {
                        suggestedDevice = candidate.label
                        suggestionConfidence = candidate.score

                        photoMessage =
                            "AI suggests a device type, but it may be wrong. " +
                                    "Please confirm it below."
                    } else {
                        photoMessage =
                            "The model couldn't identify this photo with sufficient confidence. " +
                                    "Please select the correct device manually, including " +
                                    "Other Devices when applicable."
                    }
                }
            } catch (error: Exception) {
                if (requestId == analysisRequestId) {
                    photoMessage =
                        "Could not analyze the image. " +
                                "Please choose your device manually."
                }

                android.util.Log.e(
                    "LoopitIdentify",
                    "Teachable Machine error",
                    error
                )
            } finally {
                if (requestId == analysisRequestId) {
                    isAnalyzing = false
                }
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { photo ->
        if (photo == null) {
            photoMessage =
                "No photo was captured. Try again or choose manually."
        } else {
            clearPhotoState()
            capturedPhoto = photo
            analyzeImage(photo)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            clearPhotoState()

            try {
                val photo = decodeGalleryPreview(context, uri)
                    ?: throw IllegalArgumentException(
                        "Could not decode image"
                    )

                capturedPhoto = photo
                analyzeImage(photo)
            } catch (_: Exception) {
                photoMessage =
                    "Couldn't open this image. " +
                            "Try another photo or choose manually."
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(identifyBackground)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (selectedDevice != null) {
                        selectedDevice = null
                        clearPhotoState()
                    } else {
                        clearPhotoState()
                        onBackClick()
                    }
                },
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Text(
                    text = "←",
                    fontSize = 26.sp,
                    color = identifyText
                )
            }

            Text(
                text = if (selectedDevice == null) {
                    "Identify a Device"
                } else {
                    "Device Result"
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = identifyText
            )
        }

        Spacer(Modifier.height(20.dp))

        if (selectedDevice == null) {
            Text(
                text = "Identify your device",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = identifyText
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Take a photo or choose your device " +
                        "from the list below to get started.",
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = identifyMuted
            )

            Spacer(Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = !isAnalyzing,
                        role = Role.Button,
                        onClick = {
                            if (onTakePhotoClick != null) {
                                onTakePhotoClick()
                            } else {
                                cameraLauncher.launch(null)
                            }
                        }
                    ),
                shape = RoundedCornerShape(18.dp),
                color = identifySoftPurple,
                border = BorderStroke(
                    1.dp,
                    Color(0xFFDCD9FC)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 26.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                Color.White,
                                RoundedCornerShape(18.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        DeviceSymbol(
                            device = "Camera",
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = "Take a Photo",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = identifyPurple
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Capture an image of your device",
                        fontSize = 12.sp,
                        color = identifyMuted
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    galleryLauncher.launch("image/*")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !isAnalyzing,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(
                    1.dp,
                    identifyPurple
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = identifyPurple
                )
            ) {
                Text(
                    text = "Choose from Gallery",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            capturedPhoto?.let { photo ->
                Spacer(Modifier.height(12.dp))

                Image(
                    bitmap = photo.asImageBitmap(),
                    contentDescription = "Selected device photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )
            }

            if (isAnalyzing) {
                Spacer(Modifier.height(12.dp))

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            photoMessage?.let { message ->
                Spacer(Modifier.height(10.dp))

                Text(
                    text = message,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = identifyMuted
                )
            }

            suggestedDevice?.let { suggestion ->
                Spacer(Modifier.height(12.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = identifySoftPurple,
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFDCD9FC)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "AI SUGGESTION — NOT CONFIRMED",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = identifyPurple
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = suggestion,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = identifyText
                        )

                        Text(
                            text = "Model score: " +
                                    "${(suggestionConfidence * 100).toInt()}%. " +
                                    "This is not a guarantee.",
                            fontSize = 12.sp,
                            color = identifyMuted
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                selectedDevice = suggestion
                                suggestedDevice = null
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = identifyPurple
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Yes, this is $suggestion"
                            )
                        }

                        Text(
                            text = "Wrong suggestion? " +
                                    "Select the correct device below.",
                            fontSize = 12.sp,
                            color = identifyMuted
                        )
                    }
                }
            }

            if (imageLabels.isNotEmpty()) {
                TextButton(
                    onClick = {
                        showLabelDetails = !showLabelDetails
                    }
                ) {
                    Text(
                        text = if (showLabelDetails) {
                            "Hide AI analysis details"
                        } else {
                            "View AI analysis details"
                        },
                        color = identifyPurple,
                        fontSize = 12.sp
                    )
                }

                if (showLabelDetails) {
                    Text(
                        text = "Model labels (testing only):\n" +
                                imageLabels.joinToString("\n"),
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = identifyMuted
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = identifyBorder
                )

                Text(
                    text = "or",
                    modifier = Modifier.padding(
                        horizontal = 12.dp
                    ),
                    fontSize = 14.sp,
                    color = identifyMuted
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = identifyBorder
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "SELECT MANUALLY",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.6.sp,
                color = identifyMuted
            )

            Spacer(Modifier.height(12.dp))

            deviceCategories.forEach { device ->
                IdentifyDeviceOption(
                    device = device,
                    enabled = !isAnalyzing,
                    onClick = {
                        selectedDevice = device
                        otherDeviceName = ""
                        suggestedDevice = null
                        photoMessage = null
                        imageLabels = emptyList()
                    }
                )

                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(20.dp))
        } else {
            val device = selectedDevice!!

            Spacer(Modifier.height(18.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 30.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                identifySoftPurple,
                                RoundedCornerShape(26.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        DeviceSymbol(
                            device = device,
                            modifier = Modifier.size(58.dp)
                        )
                    }

                    capturedPhoto?.let { photo ->
                        Spacer(Modifier.height(14.dp))

                        Image(
                            bitmap = photo.asImageBitmap(),
                            contentDescription =
                                "Photo used for identification",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(Modifier.height(22.dp))

                    Text(
                        text = "DEVICE SELECTED",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = identifyPurple
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = device,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = identifyText
                    )

                    if (device == "Other Devices") {
                        Spacer(Modifier.height(18.dp))

                        OutlinedTextField(
                            value = otherDeviceName,
                            onValueChange = {
                                otherDeviceName = it.take(80)
                            },
                            label = {
                                Text("Device name")
                            },
                            placeholder = {
                                Text("e.g. Printer, Monitor, Router")
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "Enter your device type to continue. " +
                                    "Check with the collection point before recycling.",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = identifyMuted,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = if (device == "Other Devices") {
                            "We'll provide general guidance. Confirm that " +
                                    "your selected collection point accepts this device."
                        } else {
                            "Your device is ready for " +
                                    "the next step. We'll guide you " +
                                    "through safe disposal."
                        },
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = identifyMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(26.dp))

            Button(
                onClick = {
                    onContinueToDisposalGuidance(device)
                },
                enabled = device != "Other Devices" ||
                        otherDeviceName.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = identifyPurple,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Continue to Disposal Guidance",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = {
                    selectedDevice = null
                    clearPhotoState()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Not your device? Select manually",
                    fontSize = 13.sp,
                    color = identifyPurple
                )
            }
        }
    }
}

private fun decodeGalleryPreview(
    context: Context,
    uri: Uri
): Bitmap? {
    val bounds = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    context.contentResolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(
            it,
            null,
            bounds
        )
    }

    if (
        bounds.outWidth <= 0 ||
        bounds.outHeight <= 0
    ) {
        return null
    }

    var sampleSize = 1

    while (
        bounds.outWidth / sampleSize > 1024 ||
        bounds.outHeight / sampleSize > 1024
    ) {
        sampleSize *= 2
    }

    val options = BitmapFactory.Options().apply {
        inSampleSize = sampleSize
    }

    return context.contentResolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(
            it,
            null,
            options
        )
    }
}

@Composable
private fun IdentifyDeviceOption(
    device: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(
            1.dp,
            identifyBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .heightIn(min = 42.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        identifySoftPurple,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                DeviceSymbol(
                    device = device,
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Text(
                text = device,
                modifier = Modifier.weight(1f),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = identifyText
            )

            Text(
                text = "›",
                fontSize = 25.sp,
                color = identifyPurple
            )
        }
    }
}

@Composable
private fun DeviceSymbol(
    device: String,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2.dp.toPx()
        val corner = CornerRadius(4.dp.toPx())

        when (device) {
            "Smartphone", "Tablet" -> {
                val left = if (device == "Tablet") {
                    0.20f
                } else {
                    0.30f
                }

                val width = if (device == "Tablet") {
                    0.60f
                } else {
                    0.40f
                }

                drawRoundRect(
                    color = identifyPurple,
                    topLeft = Offset(
                        w * left,
                        h * 0.10f
                    ),
                    size = Size(
                        w * width,
                        h * 0.80f
                    ),
                    cornerRadius = corner,
                    style = Stroke(width = stroke)
                )

                drawCircle(
                    color = identifyPurple,
                    radius = w * 0.025f,
                    center = Offset(
                        w * 0.50f,
                        h * 0.81f
                    )
                )
            }

            "Laptop" -> {
                drawRoundRect(
                    color = identifyPurple,
                    topLeft = Offset(
                        w * 0.16f,
                        h * 0.18f
                    ),
                    size = Size(
                        w * 0.68f,
                        h * 0.52f
                    ),
                    cornerRadius = corner,
                    style = Stroke(width = stroke)
                )

                drawLine(
                    color = identifyPurple,
                    start = Offset(
                        w * 0.08f,
                        h * 0.79f
                    ),
                    end = Offset(
                        w * 0.92f,
                        h * 0.79f
                    ),
                    strokeWidth = stroke
                )
            }

            "Charger" -> {
                drawRoundRect(
                    color = identifyPurple,
                    topLeft = Offset(
                        w * 0.31f,
                        h * 0.28f
                    ),
                    size = Size(
                        w * 0.38f,
                        h * 0.36f
                    ),
                    cornerRadius = corner,
                    style = Stroke(width = stroke)
                )

                drawLine(
                    color = identifyPurple,
                    start = Offset(
                        w * 0.41f,
                        h * 0.15f
                    ),
                    end = Offset(
                        w * 0.41f,
                        h * 0.28f
                    ),
                    strokeWidth = stroke
                )

                drawLine(
                    color = identifyPurple,
                    start = Offset(
                        w * 0.59f,
                        h * 0.15f
                    ),
                    end = Offset(
                        w * 0.59f,
                        h * 0.28f
                    ),
                    strokeWidth = stroke
                )

                drawLine(
                    color = identifyPurple,
                    start = Offset(
                        w * 0.50f,
                        h * 0.64f
                    ),
                    end = Offset(
                        w * 0.50f,
                        h * 0.88f
                    ),
                    strokeWidth = stroke
                )
            }

            "Accessories" -> {
                drawArc(
                    color = identifyPurple,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(
                        w * 0.22f,
                        h * 0.16f
                    ),
                    size = Size(
                        w * 0.56f,
                        h * 0.60f
                    ),
                    style = Stroke(width = stroke)
                )

                drawRoundRect(
                    color = identifyPurple,
                    topLeft = Offset(
                        w * 0.18f,
                        h * 0.55f
                    ),
                    size = Size(
                        w * 0.16f,
                        h * 0.28f
                    ),
                    cornerRadius = corner
                )

                drawRoundRect(
                    color = identifyPurple,
                    topLeft = Offset(
                        w * 0.66f,
                        h * 0.55f
                    ),
                    size = Size(
                        w * 0.16f,
                        h * 0.28f
                    ),
                    cornerRadius = corner
                )
            }

            "Other Devices" -> {
                drawRoundRect(
                    color = identifyPurple,
                    topLeft = Offset(
                        w * 0.13f,
                        h * 0.17f
                    ),
                    size = Size(
                        w * 0.74f,
                        h * 0.55f
                    ),
                    cornerRadius = corner,
                    style = Stroke(width = stroke)
                )

                drawLine(
                    color = identifyPurple,
                    start = Offset(
                        w * 0.50f,
                        h * 0.72f
                    ),
                    end = Offset(
                        w * 0.50f,
                        h * 0.86f
                    ),
                    strokeWidth = stroke
                )

                drawLine(
                    color = identifyPurple,
                    start = Offset(
                        w * 0.31f,
                        h * 0.86f
                    ),
                    end = Offset(
                        w * 0.69f,
                        h * 0.86f
                    ),
                    strokeWidth = stroke
                )
            }

            "Camera" -> {
                drawRoundRect(
                    color = identifyPurple,
                    topLeft = Offset(
                        w * 0.12f,
                        h * 0.25f
                    ),
                    size = Size(
                        w * 0.76f,
                        h * 0.58f
                    ),
                    cornerRadius = corner,
                    style = Stroke(width = stroke)
                )

                drawCircle(
                    color = identifyPurple,
                    radius = w * 0.15f,
                    center = Offset(
                        w * 0.50f,
                        h * 0.54f
                    ),
                    style = Stroke(width = stroke)
                )

                drawLine(
                    color = identifyPurple,
                    start = Offset(
                        w * 0.31f,
                        h * 0.25f
                    ),
                    end = Offset(
                        w * 0.39f,
                        h * 0.15f
                    ),
                    strokeWidth = stroke
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Identify Device"
)
@Composable
private fun IdentifyDevicePreview() {
    MaterialTheme {
        IdentifyDeviceScreen()
    }
}
