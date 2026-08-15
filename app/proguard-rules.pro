# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep J2ME engine classes
-keep class com.j2merunner.engine.** { *; }
-keep class javax.microedition.** { *; }
