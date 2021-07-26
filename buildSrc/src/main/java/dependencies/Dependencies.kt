package dependencies


/**
 * Created by Anggit Prayogo on 24,July,2021
 * GitHub : https://github.com/anggit97
 */
object Versions {
    const val appId = "com.anggit97.albumin"
    const val minSdk = 21
    const val targetSdk = 30
    const val compileSdk = 30
    const val buildTools = "30.0.2"
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.2.1"

    // UI
    const val insetter = "dev.chrisbanes.insetter:insetter:0.6.0"
    const val lottie = "com.airbnb.android:lottie:3.5.0"
    const val recyclerViewAnimators = "jp.wasabeef:recyclerview-animators:4.0.1"

    // Utils
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val threetenAbp = "com.jakewharton.threetenabp:threetenabp:1.3.0"

    object Kotlin {
        private const val version = "1.5.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
    }

    object Coroutines {
        private const val version = "1.5.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Dagger {
        private const val version = "2.35"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$version"
        const val hiltTesting = "com.google.dagger:hilt-android-testing:$version"
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    }

    object Google {
        const val material = "com.google.android.material:material:1.4.0"
    }

    object AndroidX {

        const val activity = "androidx.activity:activity-ktx:1.2.3"
        const val annotation = "androidx.annotation:annotation:1.2.0"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val core = "androidx.core:core-ktx:1.5.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.4"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Hilt {
            private const val version = "1.0.0"
            const val work = "androidx.hilt:hilt-work:$version"
            const val viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:$version"
            const val compiler = "androidx.hilt:hilt-compiler:$version"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val compiler = "androidx.lifecycle:lifecycle-common-java8:$version"
        }

        object Navigation {
            private const val version = "2.4.0-alpha05"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val dynamicFeaturesFragment =
                "androidx.navigation:navigation-dynamic-features-fragment:$version"
            const val safeArgsPlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }
    }

    object Test {
        const val junit = "junit:junit:4.13"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val runner = "androidx.test:runner:1.1.0"
        const val mockk = "io.mockk:mockk:1.12.0"
        const val mockkAndroid = "io.mockk:mockk-android:1.12.0"
        const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0"
        const val core = "androidx.arch.core:core-testing:2.1.0"
        const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:5.7.1"
        const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:5.7.1"
    }

    object Tmkurami {
        const val dexopener = "com.github.tmurakami:dexopener:2.0.5"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val runtime = "com.squareup.retrofit2:retrofit:$version"
        const val serialization =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object BaseFlow {
        const val photoview = "com.github.chrisbanes:PhotoView:2.3.0"
    }

    object OkHttp {
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    }

    object InflationX {
        const val calligraphy3 = "io.github.inflationx:calligraphy3:3.1.1"
        const val viewPump = "io.github.inflationx:viewpump:2.0.3"
    }

    object Glide {
        private const val version = "4.12.0"
        const val runtime = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
        const val transformation = "jp.wasabeef:glide-transformations:4.3.0"
    }

    object Shimmer {
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
    }
}