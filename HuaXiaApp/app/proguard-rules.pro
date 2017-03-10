# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
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


-dontwarn com.ut.mini.**
-dontwarn okio.**
-dontwarn com.xiaomi.**
-dontwarn com.squareup.wire.**
-dontwarn android.support.v4.**
-dontwarn u.aly.**

-keepattributes *Annotation*

-dontwarn android.support.v7.**
-keep class android.support.v4.** { *; }

-keep class okio.** {*;}
-keep class com.squareup.wire.** {*;}

-keep class com.umeng.message.protobuffer.* {
        public <fields>;
        public <methods>;
}

-keep class com.umeng.message.* {
    public <fields>;
    public <methods>;
}


-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}


-keep class org.android.agoo.impl.*{
	public <fields>;
    public <methods>;
}

-keep class org.android.agoo.service.* {*;}

-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.ut.**{*;}

-keep class com.ut.mini.**{*;}

-keep public class com.huaxia.finance.R$*{
    public static final int *;
}


-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-dontobfuscate
-dontoptimize
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep class sun.misc.Unsafe {*;}
#下面三个gson相关
-keep class com.google.gson.examples.android.model.**{*;}
-keep class org.json.** {*;}

-dontwarn org.apache.http.**
-dontwarn android.webkit.**
-keep class org.apache.http.** { *; }
-keep class org.apache.commons.codec.** { *; }
-keep class org.apache.commons.logging.** { *; }
-keep class android.net.compatibility.** { *; }
-keep class android.net.http.** { *; }
#
#-keep class android.webkit.WebViewClient
#
#-dontwarn android.net.http.SslError

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v7.app.AppCompatActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.app.NotificationCompat
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService
-keep public abstract interface com.asqw.android.Listener{
public protected <methods>;  #【所有方法不进行混淆】
}

#    ####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####
#
    -keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
    }
#
#    #保持 native 方法不被混淆
   -keepclasseswithmembernames class * {
             native <methods>;
         }
#
#    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
#
#    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }
#
#    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }
#
#    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable
#
#    #保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }
#
#    #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
    -keepclassmembers enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
    }

#    -keepclassmembers class * {
#        public void *ButtonClicked(android.view.View);
#    }

#    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }
