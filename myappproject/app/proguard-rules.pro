# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/ZhiShui/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


#/**
# * 该方法不能被混淆
# * @param scale
# */
# MetroCursorView.setScaleUp
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# ----- 混淆包路径 -------
-repackageclasses ''
-flattenpackagehierarchy ''
-target 1.6

-keepattributes EnclosingMethod

#//不进行混淆保持原样
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService



-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment




-keepattributes *Annotation*
-keep class org.apache.cordova.** { *; }
-dontwarn org.apache.cordova.**

-keep class in.srain.cube.views.ptr.**{*;}
-dontwarn in.srain.cube.views.ptr.**

-keep class com.readystatesoftware.systembartint.**{ *; }
-dontwarn com.readystatesoftware.systembartint.**

-keep public class com.tendcloud.tenddata.** { public protected *;}

#-keep public class in.srain.cube.views.ptr.** {*;}
#-dontwarn in.srain.cube.views.ptr.**
-keep public class butterknife.*{*;}
-keep public class com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}
#==================picasso======================
-dontwarn com.squareup.**
-dontwarn okio.**
-dontwarn org.codehaus
-dontwarn java.nio
-keep public class org.codehaus.**
-keep public class java.nio.**

#==================glide======================
-dontwarn com.bumptech.**
-keep class com.bumptech.glide.** {*;}

#==================android-gif-drawable======================
-dontwarn pl.droidsonroids.gif.**
-keep class pl.droidsonroids.gif.** {*;}

#==================hamcrest-core======================
-dontwarn org.hamcrest.**
-keep class org.hamcrest.** {*;}

#==================hamcrest-core======================
-dontwarn org.hamcrest.**
-keep class org.hamcrest.** {*;}



#==================model==========================
-keep public class com.aiju.yhk.bean.** { *; }


-keepattributes *Annotation*

#//保护指定的类和类的成员的名称，如果所有指定的类成员ss
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * { #保护类中的所有方法名
    public <methods>;
}

#//保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}


-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

##//保护指定类的成员，如果此类受到保护他们会保护的更好
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#//保护指定的类文件和类成员
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keep public class [your_pkg].R$*{
    public static final int *;
}



-keep public class AnnotationDatabaseImpl

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}








-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class org.json.** {*;}
-keep class com.ali.auth.**  {*;}

