
# Gson
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
# -keep class mypersonalclass.data.model.** { *; }

# ButterKnife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}
