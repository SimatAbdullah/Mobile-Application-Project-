
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.loopit"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.loopit"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)

    // Material Icons - Miad
    implementation("androidx.compose.material:material-icons-extended")

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Google Maps and Location - Salam
    implementation(libs.google.maps.compose)
    implementation(libs.google.play.services.location)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // ViewModel and Lifecycle - Salam
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Coroutines - Salam
    implementation(libs.kotlinx.coroutines.android)

    // ML Kit General Image Labeling - Qunoot
    implementation("com.google.mlkit:image-labeling:17.0.9")

    // ML Kit Custom Image Labeling - Qunoot
    implementation("com.google.mlkit:image-labeling-custom:17.0.3")

    // LiteRT / TensorFlow Lite - Qunoot
    implementation("com.google.ai.edge.litert:litert:1.4.2")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
