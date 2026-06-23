plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)

  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
  id("org.jetbrains.kotlin.plugin.serialization")
  id("com.google.gms.google-services")
}

android {
  namespace = "com.ajzia.simplist"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.ajzia.simplist"
    minSdk = 30
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "com.ajzia.simplist.HiltTestRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

kotlin {
  compilerOptions {
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
  }
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.core.ktx)

  testImplementation(libs.junit)
  testImplementation(libs.truth)
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.kotlinx.coroutines.test)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)

  // For instrumented tests.
  androidTestImplementation(libs.truth)
  androidTestImplementation("com.google.dagger:hilt-android-testing:2.57.1")
  kspAndroidTest("com.google.dagger:hilt-android-compiler:2.57.1")

  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  implementation(libs.androidx.room.runtime)
  ksp(libs.androidx.room.compiler)
  implementation(libs.androidx.room.ktx)

  implementation("com.google.dagger:hilt-android:2.57.1")
  ksp("com.google.dagger:hilt-android-compiler:2.57.1")
  implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

  val nav_version = "2.9.7"
  implementation("androidx.navigation:navigation-compose:${nav_version}")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

  implementation(platform("com.google.firebase:firebase-bom:34.9.0"))
  implementation("com.google.firebase:firebase-firestore")

  implementation("androidx.datastore:datastore-preferences:1.2.1")
}