plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)

    kotlin("kapt")
}

android {
    namespace = "com.david.megaloginapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.david.megaloginapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.david.megaloginapp.CustomTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
        testOptions {
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
    packaging {
        resources.excludes.apply {
            add("META-INF/LICENSE-notice.md")
            add("META-INF/LICENSE.md")
            add("META-INF/LICENSE")
            add("META-INF/*.properties")
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}

dependencies {
    val media3Version = "1.1.0"
    val mockkVersion = "1.13.5"

    // Hilt
    implementation(libs.dagger.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    // Hilt Tests
    kaptTest(libs.hilt.android.compiler)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)

    // Room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Navigation
    implementation(libs.navigation.compose)

    // Lottie Animation
    implementation(libs.lottie.compose)

    // Media3
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    // Mockk
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)

    // Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    implementation("com.google.accompanist:accompanist-insets:0.23.1")
    implementation(libs.androidx.ktx)
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestUtil("androidx.test:orchestrator:1.4.2")
}
