// https://developer.android.com/build
// Module-level gradle file for configure build settings for the specific module.
plugins {
    // initial plugins
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // ksp for room support
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.skele.bookshelf"
    // Determines which Android and Java APIs are available when compiling source code.
    compileSdk = 34

    defaultConfig {
        applicationId = "com.skele.bookshelf"
        // Lowest version of Android to support
        // This restricts which devices can install this app
        minSdk = 24
        // Sets the runtime behaviour of this app
        // Must be less than or equal to compileSdk which provides access to new APIs
        // If app runs on higher API level than targetSdk level, it runs on compatibility mode
        // On compatibility mode, the app behaves similarly to the lower version indicated in targeSdk
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

// Specifies dependencies required to build this module.
dependencies {

    // initial dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // room dependencies
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin Symbol Processing (KSP)
    // ksp should be configured in both project and module level gradle
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")
    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")

    // fragment dependencies
    val fragment_version = "1.6.2"
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:$fragment_version")

}