plugins {
    alias(libs.plugins.androidGradlePlugin)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
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

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converterGson)

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
