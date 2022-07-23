import extenstions.*
import org.gradle.kotlin.dsl.implementation

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = ConfigData.compileSdk
    buildToolsVersion = ConfigData.buildToolsVersion

    defaultConfig {
        applicationId = "com.youssef.task"
        minSdk = ConfigData.minSdk
        targetSdk = ConfigData.targetSdk
        versionCode = Release.versionCode
        versionName = Release.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }

    flavorDimensions.add(ConfigData.dimension)
    productFlavors {
        create("staging") {
            buildConfigField(
                type = "String",
                name = "HOST",
                value = "\"https://api.rawg.io/api/\""
            )

            applicationIdSuffix = ".staging"
            dimension = ConfigData.dimension
            manifestPlaceholders["appIcon"] = "@drawable/logo"
            manifestPlaceholders["appIconRound"] = "@drawable/logo"
        }

        create("production") {
            buildConfigField(
                type = "String",
                name = "HOST",
                value = "\"https://api.rawg.io/api/\""
            )

            dimension = ConfigData.dimension
            manifestPlaceholders["appIcon"] = "@drawable/logo"
            manifestPlaceholders["appIconRound"] = "@drawable/logo"
        }
    }
}


dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.core)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.materialDesign)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.splash)
    implementation(Dependencies.timber)
    implementation(Dependencies.coil)
    implementation(Dependencies.easyPref)
    implementation(Dependencies.lifecycle)
    implementation(Dependencies.paging)

    addRecyclerView()
    addNavigation()
    addHilt()
    addNetwork()
    addRoom()
    addTestsDependencies()
}