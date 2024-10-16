plugins {
    alias(libs.plugins.androidGradlePlugin)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "br.pedroso.upcomingmovies"

    compileSdk = 34

    defaultConfig {
        applicationId = "br.pedroso.movies"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildTypes.onEach {
        it.buildConfigField("String", "MOVIES_DB_API_KEY", properties["MovieDbApiKey"].toString())
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

composeCompiler {
    enableStrongSkippingMode = true

    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.palette)
    implementation(libs.androidx.activity)

    implementation(libs.androidx.lifecycle.runtime)

    implementation(libs.androidx.lifecycle.savedstate)

    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)

    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)

    androidTestImplementation(libs.compose.uiTestJUnit4)
    debugImplementation(libs.compose.uiTestManifest)

    implementation(libs.compose.activity)
    implementation(libs.compose.viewModel)

    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil.core)
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    // Design library
    implementation(libs.material)
    implementation(libs.androidx.cardView)
    implementation(libs.androidx.recyclerView)

    // Picasso
    implementation(libs.picasso)

    // Hilt
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigationCompose)

    // Kotlin Serialization
    implementation(libs.kotlin.serializationJson)

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converterKotlinxSerialization)

    coreLibraryDesugaring(libs.desugarJdkLibs)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.fixture)

    androidTestImplementation(libs.androidx.espresso.core) {
        exclude(group = "com.android.support", module = "support-annotations")
    }
}
