/*
 * MIT License
 * Copyright 2024 Nils Jäkel
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

plugins {
    org.jetbrains.kotlin.multiplatform
    com.android.library
}

kotlin {
    androidTarget()
}

val targetAndroidVersion = 34
android {
    compileSdk = targetAndroidVersion
    buildToolsVersion = "$targetAndroidVersion.0.0"

    defaultConfig {
        minSdk = 21
    }

    lint {
        targetSdk = targetAndroidVersion
        abortOnError = true
        checkDependencies = true
    }

    compileSdkVersion = "android-$targetAndroidVersion"
}
