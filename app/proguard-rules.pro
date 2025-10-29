# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Kakao SDK
-keep class com.kakao.** { *; }
-keep class com.kakao.sdk.** { *; }
-keep interface com.kakao.sdk.** { *; }
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault

# Retrofit 및 OkHttp 관련 난독화 방지
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keepattributes Signature

# Retrofit
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# BaseResponse 유지
-keep class com.legacy.legacy_android.feature.data.core.BaseResponse { *; }
-keep class com.legacy.legacy_android.feature.network.login.TokenData { *; }
-keep class com.legacy.legacy_android.feature.network.login.KakaoLoginRequest { *; }
-keep class com.legacy.legacy_android.feature.network.login.GoogleLoginRequest { *; }

# 모든 API Response 모델 유지
-keep class com.legacy.legacy_android.feature.data.** { *; }
-keep class com.legacy.legacy_android.feature.network.** { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Kotlin
-keep class kotlin.Metadata { *; }