plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.kukuadmin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kukuadmin"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.car.ui.lib)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

//    implementation (libs.viewpager2)
//    implementation (platform(libs.firebase.bom.v3210))
//    implementation (libs.firebase.analytics.ktx)
    implementation (libs.firebase.database)
//    implementation (libs.firebase.core)
    implementation (libs.firebase.database.v2000)
    implementation (libs.firebase.storage)
//    implementation (libs.picasso)
//    implementation (platform(libs.firebase.bom))
//    implementation (libs.google.firebase.analytics)
    implementation (libs.firebase.database.v2021)
//    implementation (libs.firebase.core.v2101)
//    implementation (libs.firebase.auth)
//    implementation (libs.play.services.auth)

    implementation (libs.picasso)
    implementation (libs.recyclerview.v121)

}