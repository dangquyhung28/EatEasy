plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)



}

android {
    namespace = "com.example.eateasy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eateasy"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
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
    buildFeatures{
        viewBinding = true
    }



}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(fileTree(mapOf(
        "dir" to "D:\\App",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")

    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.gms:google-services:4.4.2")
    implementation (platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.firebase:firebase-storage:20.2.1")


    // Retrofit dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // GSON for JSON parsing
    implementation("com.google.code.gson:gson:2.8.8")



    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    //dang ki fb
    implementation("com.facebook.android:facebook-android-sdk:latest.release")


    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")










}