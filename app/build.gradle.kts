plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.example.kebunrayabanua"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String","BASE_URL","\"http://103.81.100.228/kebunraya/\"")
        buildConfigField("String","API_KEY","\"aad8d43db4ace8de270bd4a525df63df\"")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    androidExtensions {
        isExperimental = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.31")
    implementation("org.jetbrains.anko:anko-commons:0.10.8")
    implementation("org.jetbrains.anko:anko:0.10.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0-M2")

    //Firebase
    implementation("com.firebaseui:firebase-ui-auth:4.1.0")
    implementation("com.twitter.sdk.android:twitter-core:3.3.0")
    implementation("com.google.android.gms:play-services-auth:17.0.0")
    implementation("com.google.firebase:firebase-auth:18.0.0")
    implementation("com.google.android.gms:play-services-location:17.0.0")
    implementation("com.google.firebase:firebase-messaging:19.0.1")

    //network
    implementation("com.google.code.gson:gson:2.8.5")

    //Extras
    implementation("org.osmdroid:osmdroid-android:6.1.0")
    implementation("de.hdodenhof:circleimageview:3.0.0")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")
    implementation("com.romandanylyk:pageindicatorview:1.0.2")
    implementation("jp.wasabeef:glide-transformations:4.1.0")
    implementation("me.dm7.barcodescanner:zxing:1.9.13") {
        exclude(module = "com.android.support")
    }

    //noinspection GradleCompatible
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.legacy:legacy-support-core-ui:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    //testunit
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
