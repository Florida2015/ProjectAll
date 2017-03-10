# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\develop\AS\sdk/tools/proguard/proguard-android.txt
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
#所有的bean类
-keep class com.hxxc.user.app.bean.** { *; }
-keep class com.hxxc.user.app.Event.** {*;}
-keep class com.hxxc.user.app.data.bean.** {*;}
-keep class com.hxxc.user.app.data.entity.** {*;}

-keep class com.hxxc.user.app.ui.vh.** {*;}
-keep public class * extends com.hxxc.user.app.widget.trecyclerview.BaseViewHolder
-keep class com.hxxc.user.app.widget.trecyclerview.CommFooterVH {*;}
#-keep class com.hxxc.user.app.ui.pager.mine.vh.UserHeaderDynamicVH { *; }
#-keep class com.hxxc.user.app.ui.financial.vh.BindFinancialVH {*;}
#-keep class com.hxxc.user.app.ui.mine.equitynote.EquityNoteVH {*;}
#-keep class com.hxxc.user.app.ui.mine.integral.vh.IntegralVH{*;}
#-keep class com.hxxc.user.app.ui.mine.invitation.InvitaitonRecordVH {*;}
#-keep class com.hxxc.user.app.ui.mine.invitation.InvitationListVH {*;}
#-keep class com.hxxc.user.app.ui.mine.noticelist.vh.NoticeVH {*;}
#-keep class com.hxxc.user.app.ui.order.vh.OrderVH {*;}
#-keep class com.hxxc.user.app.ui.mine.redpackage.vh.RedPackageVH {*;}
#-keep class com.hxxc.user.app.ui.mine.tradelist.vh.TradeVH {*;}
#-keep class com.hxxc.user.app.ui.pager.mine.vh.UserDynamicVH {*;}


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**


-keep public class * extends android.view.view{
	public <init>(android.content.Context);
	public <init>(android.content.Context,android.util.AttributeSet);
	public <init>(android.content.Context,android.util.AttributeSet,int);
	public void set*(***);
	*** get* ();
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable{
	static final long serialVersionUID;
	private static final java.io.ObjectStreamField[] srrialPersistentFields;
	!static !transient <fields>;
	private void writeObject(java.io.ObjectOutputStream);
	private void readObject(java.io.ObjectInputStream);
	java.lang.Object writeReplace();
	java.lang.Object readResolve();
}
-keepclasseswithmembernames class * {
    native <methods>;
}

# webview + js
# keep 使用 webview 的类
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keep class android.webkit.JavascriptInterface {*;}
-keep class android.webkit.WebView{*;}
-keepclassmembers class com.hxxc.user.app.activity.index.AdsActivity {
   public *;
}
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
 }
-keepclassmembers class * extends android.webkit.WebChromeClient{ *; }
-keepclassmembers class * extends android.webkit.WebViewClient{ *; }



# ========== imageloader ==========
-keep class com.nostra13.universalimageloader.** { *; }
-keepclassmembers class com.nostra13.universalimageloader.** {*;}

# ========== EventBus ============
-keepclassmembers class ** {
    public void onEvent*(**);
}

#okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }


#rxjava和rxandroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# 使用注解
-keepattributes *Annotation*,Signature
#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


#友盟统计
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.hxxc.user.app.R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#百度定位
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

#分享
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**

-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep public class **.R$*{
    *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.handler.** {*;}
-keep class  com.alipay.share.sdk.** {
   *;
}
-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}
-keepattributes Signature


#友盟推送
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}

#IM聊天
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
 public *;
}
-keepattributes Exceptions,InnerClasses
-keep class io.rong.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent{*;}
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
 public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.eclipse.jdt.annotation.**
-keep class com.ultrapower.** {*;}

-keep class com.hxxc.user.app.listener.MyNotificationReceiver {*;}
-dontwarn io.rong.push.** 
-dontnote com.xiaomi.** 
-dontnote io.rong.**
-dontnote com.google.android.gms.gcm.**
-dontnote com.huawei.android.pushagent.**

#picasso
-dontwarn com.squareup.okhttp.**

#retrolambda
-dontwarn java.lang.invoke.*

#picasso 混淆
-dontwarn com.squareup.okhttp.**

#新浪微博：很草蛋，没有官方给的混淆。自己上
 -keep class com.sina.weibo.sdk.* { *; }
-keep class android.support.v4.* { *; }
-keep class lombok.ast.ecj.* { *; }
-dontwarn android.support.v4.**

# 微信 分享
# -keep class com.tencent.mm.sdk.** {
#    *;
# }
# -keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
#
# -keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
#
# -keep class com.tencent.open.TDialog$*
# -keep class com.tencent.open.TDialog$* {*;}
# -keep class com.tencent.open.PKDialog
# -keep class com.tencent.open.PKDialog {*;}
# -keep class com.tencent.open.PKDialog$*
# -keep class com.tencent.open.PKDialog$* {*;}

 #vh
 -keep class com.hxxc.user.app.ui.vh.* { *; }
 -keep class com.hxxc.user.app.ui.vh.** { *; }
# trecyclerview
  -keep class com.hxxc.user.app.widget.trecyclerview.* { *; }
  -keep class com.hxxc.user.app.widget.trecyclerview.** { *; }

#百度统计
-keep class com.baidu.bottom.** { *; }
-keep class com.baidu.kirin.** { *; }
-keep class com.baidu.mobstat.** { *; }