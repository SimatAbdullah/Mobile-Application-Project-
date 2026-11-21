
package com.example.loopit.ui.screens

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import java.io.Closeable
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Runs the local Teachable Machine image classifier.
 * Input: [1, 224, 224, 3] FLOAT32 RGB in [-1, 1].
 * Output: five scores.
 * Other Devices remains a manual category.
 */
internal class TeachableMachineClassifier(
    context: Context
) : Closeable {

    data class Prediction(
        val label: String,
        val score: Float
    )

    private val labels: List<String> =
        context.assets.open("labels.txt")
            .bufferedReader()
            .useLines { lines ->
                lines.map { line ->
                    line.trim()
                        .substringAfter(' ', line.trim())
                        .trim()
                }.filter { it.isNotEmpty() }.toList()
            }

    private val interpreter: Interpreter = run {
        val modelBytes = context.assets
            .open("model_unquant.tflite")
            .use { it.readBytes() }

        val modelBuffer =
            ByteBuffer.allocateDirect(modelBytes.size)
                .order(ByteOrder.nativeOrder())

        modelBuffer.put(modelBytes)
        modelBuffer.rewind()

        Interpreter(
            modelBuffer,
            Interpreter.Options().setNumThreads(2)
        )
    }

    fun classify(bitmap: Bitmap): List<Prediction> {
        val shape = interpreter.getInputTensor(0).shape()

        require(
            shape.contentEquals(
                intArrayOf(1, IMAGE_SIZE, IMAGE_SIZE, 3)
            )
        ) {
            "Unexpected input size: ${shape.contentToString()}"
        }

        require(
            interpreter.getInputTensor(0).dataType() ==
                    DataType.FLOAT32
        ) {
            "The supplied model must accept FLOAT32 input"
        }

        val outputShape =
            interpreter.getOutputTensor(0).shape()

        require(
            outputShape.contentEquals(
                intArrayOf(1, labels.size)
            )
        ) {
            "Model output ${outputShape.contentToString()} " +
                    "does not match ${labels.size} labels"
        }

        require(
            interpreter.getOutputTensor(0).dataType() ==
                    DataType.FLOAT32
        ) {
            "The supplied model must return FLOAT32 output"
        }

        val squareSide =
            minOf(bitmap.width, bitmap.height)

        val square = Bitmap.createBitmap(
            bitmap,
            (bitmap.width - squareSide) / 2,
            (bitmap.height - squareSide) / 2,
            squareSide,
            squareSide
        )

        val scaled = Bitmap.createScaledBitmap(
            square,
            IMAGE_SIZE,
            IMAGE_SIZE,
            true
        )

        val pixels =
            IntArray(IMAGE_SIZE * IMAGE_SIZE)

        scaled.getPixels(
            pixels,
            0,
            IMAGE_SIZE,
            0,
            0,
            IMAGE_SIZE,
            IMAGE_SIZE
        )

        val input = ByteBuffer.allocateDirect(
            4 * IMAGE_SIZE * IMAGE_SIZE * 3
        ).order(ByteOrder.nativeOrder())

        for (pixel in pixels) {
            input.putFloat(
                (((pixel shr 16) and 0xff) / 127.5f) - 1f
            )
            input.putFloat(
                (((pixel shr 8) and 0xff) / 127.5f) - 1f
            )
            input.putFloat(
                ((pixel and 0xff) / 127.5f) - 1f
            )
        }

        input.rewind()

        val output =
            Array(1) { FloatArray(labels.size) }

        interpreter.run(input, output)

        return labels.mapIndexed { index, label ->
            Prediction(
                label,
                output[0][index].coerceIn(0f, 1f)
            )
        }.sortedByDescending { it.score }
    }

    override fun close() {
        interpreter.close()
    }

    private companion object {
        const val IMAGE_SIZE = 224
    }
}
