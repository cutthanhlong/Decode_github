package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.tiktok.TikTokBusinessSdk;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.pubnative.lite.sdk.analytics.Reporting;
import notes.easy.android.mynotes.constant.Constants;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.firebase.FirebaseReportUtils;
import notes.easy.android.mynotes.firebase.RemoteConfig;
import notes.easy.android.mynotes.ui.activities.ActivityStackManager;
import notes.easy.android.mynotes.utils.ConstantsBase;
import notes.easy.android.mynotes.utils.DeviceUtils;
import notes.easy.android.mynotes.utils.LogRecord;
import notes.easy.android.mynotes.utils.NetworkUtils;
import notes.easy.android.mynotes.utils.ScreenUtils;
import notes.easy.android.mynotes.utils.WebViewUtils;
import src.ad.AdConfig;
import src.ad.SDKConfiguration;
import src.ad.adapters.AdLoader;
import src.ad.adapters.IAdAdapter;
import src.ad.adapters.IAdLoadListener;

/* loaded from: classes4.dex */
public class App extends MultiDexApplication implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    public static Locale default_laguage;
    public static SharedPreferences prefs;
    public static Context sContext;
    public static UserConfig userConfig;
    private AppOpenAdManager appOpenAdManager;
    private Activity currentActivity;
    public static ExecutorService sGlobalExecutor = Executors.newCachedThreadPool();
    public static ConcurrentHashMap<String, Object> sIconMap = new ConcurrentHashMap<>();
    private static final Handler sGlobalHandler = new Handler(Looper.getMainLooper());
    public static boolean hasInitFirebase = false;
    public static long lastOpenAdShowed = 0;
    public static String signStr = null;
    private static int count = 0;
    private static boolean isRunInBackground = true;
    private static boolean isLockOn = true;
    public static boolean isAppInit = false;

    private class AppOpenAdManager {
        public AppOpenAdManager() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showAdIfAvailable(Activity activity) {
            showAdIfAvailable(activity, new OnShowAdCompleteListener() { // from class: notes.easy.android.mynotes.a
            });
        }

        private void showAdIfAvailable(Activity activity, OnShowAdCompleteListener onShowAdCompleteListener) {
            boolean z6 = true;
            if (!activity.toString().contains("SplashActivity") && !activity.toString().contains("Welcom") && !activity.toString().contains("EditActivity") && !activity.toString().contains("applovin") && !activity.toString().contains("google") && !activity.toString().contains(Reporting.Key.CLICK_SOURCE_TYPE_AD) && !activity.toString().contains("Vip")) {
                z6 = false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The app open ad activity is not good: ");
            sb.append(z6);
            if (z6) {
                boolean hasValidCache = AdLoader.get(Constants.OPEN_ADS, activity).hasValidCache();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("The app open ad activity is not good need Load: ");
                sb2.append(hasValidCache);
                if (hasValidCache) {
                    return;
                }
                AdLoader.get(Constants.OPEN_ADS, activity).preLoadAd(activity);
                return;
            }
            FirebaseReportUtils.getInstance().meetRuleReport("open_ad");
            if (NetworkUtils.isNetworkConnected(App.getAppContext())) {
                FirebaseReportUtils.getInstance().withNetworkReport("open_ad");
            } else {
                FirebaseReportUtils.getInstance().withOutNetworkReport("open_ad");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add("ab_openad_h");
            arrayList.add("ab_openad");
            IAdAdapter allTopAdByScenes = AdLoader.getAllTopAdByScenes(activity, arrayList, Constants.OPEN_ADS);
            if (allTopAdByScenes == null) {
                AdLoader.get(Constants.OPEN_ADS, activity).preLoadAd(activity);
                return;
            }
            allTopAdByScenes.setAdListener(new IAdLoadListener() { // from class: notes.easy.android.mynotes.App.AppOpenAdManager.1
                @Override // src.ad.adapters.IAdLoadListener
                public void onAdLoaded(IAdAdapter iAdAdapter) {
                    LogRecord.e("opend", "onOpenAd Load Success");
                }

                @Override // src.ad.adapters.IAdLoadListener
                public void onAdClicked(IAdAdapter iAdAdapter) {
                }

                @Override // src.ad.adapters.IAdLoadListener
                public void onAdClosed(IAdAdapter iAdAdapter) {
                }

                @Override // src.ad.adapters.IAdLoadListener
                public void onError(String str) {
                }

                @Override // src.ad.adapters.IAdLoadListener
                public void onOpenAdDismiss(IAdAdapter iAdAdapter) {
                }
            });
            allTopAdByScenes.show("openad", activity);
            AdLoader.get(Constants.OPEN_ADS, activity).preLoadAd(activity);
            App.lastOpenAdShowed = System.currentTimeMillis();
            FirebaseReportUtils.getInstance().showAdReport("open_ad");
        }
    }

    public interface OnShowAdCompleteListener {
    }

    public static void executeOnGlobalExecutor(Runnable runnable) {
        sGlobalExecutor.execute(runnable);
    }

    public static Context getAppContext() {
        if (sContext == null) {
            Activity topActivity = ActivityStackManager.getInstance().getTopActivity();
            if (topActivity != null) {
                return topActivity.getApplicationContext();
            }
            if (EasyNoteManager.getAppContext() != null) {
                return EasyNoteManager.getAppContext();
            }
        }
        return sContext;
    }

    public static Handler getsGlobalHandler() {
        return sGlobalHandler;
    }

    public static void initAd(Context context) {
        SDKConfiguration.Builder builder = new SDKConfiguration.Builder();
        builder.admobAppId("ca-app-pub-3874218421060401~1423330522").dtAppId("208650").prophetId(ConstantsBase.SAVE_DIR_NAME);
        AdLoader.setBanInvalidAd(true);
        AdLoader.init(new AdLoader.ConfigFetcher() { // from class: notes.easy.android.mynotes.App.1
            @Override // src.ad.adapters.AdLoader.ConfigFetcher
            public List<AdConfig> getAdConfigList(String str) {
                return RemoteConfig.getAdConfigList(str);
            }

            @Override // src.ad.adapters.AdLoader.ConfigFetcher
            public boolean isAdFree(String str) {
                return App.isAdOpen();
            }
        }, context, builder.build());
    }

    public static void initNewUser(UserConfig userConfig2) {
        if (userConfig2.getNewUser() && System.currentTimeMillis() - userConfig2.getFirstTime() >= 86400000) {
            userConfig2.setNewUser(false);
        }
    }

    public static void initTikTokSDK(Context context) {
        try {
            TikTokBusinessSdk.initializeSdk(new TikTokBusinessSdk.TTConfig(context).setAppId(getAppContext().getPackageName()).setTTAppId("7331652166161039361"));
            TikTokBusinessSdk.startTrack();
        } catch (Exception e7) {
            e7.getMessage();
        }
    }

    public static boolean is6hFreeTry() {
        return false;
    }

    public static boolean isAdOpen() {
        UserConfig userConfig2 = userConfig;
        if (userConfig2 == null) {
            return false;
        }
        return userConfig2.getHasBuyed() || userConfig.getHasSubscribe() || RemoteConfig.getLong("adshow_switch") != 0;
    }

    public static boolean isLockOn() {
        return isLockOn;
    }

    public static boolean isReleaseEnv() {
        return "easynotes.notes.notepad.notebook.privatenotes.note".equals(getAppContext().getPackageName());
    }

    public static boolean isVip() {
        try {
            if (userConfig.getBillingMonthlyTestOpen()) {
                return false;
            }
            if (userConfig.getHasBuyed() || userConfig.getHasSubscribe()) {
                return true;
            }
            userConfig.getHasVipDebug();
            return false;
        } catch (Exception e7) {
            FirebaseCrashlytics.getInstance().recordException(new IllegalArgumentException("isVip " + e7.getMessage(), e7));
            return false;
        }
    }

    public static void setIsLockOn(boolean z6) {
        isLockOn = z6;
    }

    @Override // androidx.multidex.MultiDexApplication, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        try {
            default_laguage = ScreenUtils.getSystemLocale();
            super.attachBaseContext(ScreenUtils.setLocale(context, ScreenUtils.getInstance(context).getSelectLanguage() == 0 ? default_laguage : Constants.LANGUAGE.get(ScreenUtils.getInstance(context).getSelectLanguage())));
        } catch (Exception unused) {
            super.attachBaseContext(context);
        }
        userConfig = UserConfig.Companion.newInstance(context);
        prefs = getSharedPreferences(ConstantsBase.PREFS_NAME, 4);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        this.currentActivity = activity;
        count++;
        if (isRunInBackground) {
            isRunInBackground = false;
            LogRecord.e("App", "Move To Foreground ");
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i6 = count - 1;
        count = i6;
        if (i6 == 0) {
            isRunInBackground = true;
            isLockOn = true;
            LogRecord.e("App", "Move To Backoreground ");
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Locale systemLocale = ScreenUtils.getInstance(this).getSelectLanguage() == 0 ? ScreenUtils.getSystemLocale() : Constants.LANGUAGE.get(ScreenUtils.getInstance(this).getSelectLanguage());
        Context locale = systemLocale != null ? ScreenUtils.setLocale(this, systemLocale) : this;
        if (userConfig.getThemeState() == 2) {
            DeviceUtils.INSTANCE.setSDefaultUiMode(configuration.uiMode);
            DeviceUtils.setNightMode(locale);
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        EasyNoteManager.initAppContext(this);
        WebViewUtils.fixWebViewCrash(this);
        EasyNoteManager.initApp("App");
        FirebaseReportUtils.getInstance().reportNew("app_active");
        FirebaseReportUtils.getInstance().reportNew("v1_app_active");
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        this.appOpenAdManager = new AppOpenAdManager();
        long firstTime = userConfig.getFirstTime();
        Date date = new Date("2023/07/27 00:00:01");
        long currentTimeMillis = System.currentTimeMillis();
        long j6 = ((((currentTimeMillis - firstTime) / 1000) / 60) / 60) / 24;
        if (j6 >= 0) {
            FirebaseReportUtils.getInstance().setUserPropertyKV("cp_install_days", String.valueOf(j6 + 1));
        }
        if (firstTime < date.getTime()) {
            if (!userConfig.getFirstNewOpen()) {
                userConfig.setFirstNewTime(System.currentTimeMillis());
                userConfig.setFirstNewOpen(true);
            }
            if ((currentTimeMillis - userConfig.getFirstNewTime()) / 86400000 >= 14) {
                FirebaseReportUtils.getInstance().setUserPropertyKV("cp_active_rate", Integer.toString((int) ((((userConfig.getActiveNewDay() * 1.0f) / (r3 + 1)) * 100.0f) + 0.5f)));
            }
        } else if (j6 >= 15) {
            FirebaseReportUtils.getInstance().setUserPropertyKV("cp_active_rate", Integer.toString((int) ((userConfig.getActiveDay() * 100) / (j6 + 1))));
        }
        if (!TextUtils.isEmpty(userConfig.getCpPurchaseStatus())) {
            FirebaseReportUtils.getInstance().setUserPropertyKV("cp_purchase_status", userConfig.getCpPurchaseStatus());
        }
        if (!TextUtils.isEmpty(userConfig.getCpPermissionStatus())) {
            FirebaseReportUtils.getInstance().setUserPropertyKV("cp_permission_status", userConfig.getCpPermissionStatus());
        }
        if (!TextUtils.isEmpty(userConfig.getAppDeepLevel())) {
            FirebaseReportUtils.getInstance().setUserPropertyKV("cp_main_depth", userConfig.getAppDeepLevel());
        }
        if (TextUtils.isEmpty(userConfig.getAppFuncusage())) {
            return;
        }
        FirebaseReportUtils.getInstance().setUserPropertyKV("cp_functions_usage", userConfig.getAppFuncusage() + "_w" + userConfig.getWidgetUsage());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onMoveToForeground() {
        FirebaseReportUtils.getInstance().comeAdReport("open_ad");
        try {
            long j6 = RemoteConfig.getLong("ad_openad_on");
            LogRecord.e("opend", "Move To onMoveToForeground ");
            if (isAdOpen() || j6 != 0 || System.currentTimeMillis() - lastOpenAdShowed <= 60000) {
                return;
            }
            FirebaseReportUtils.getInstance().openAdReport("open_ad");
            this.appOpenAdManager.showAdIfAvailable(this.currentActivity);
        } catch (Exception unused) {
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }
}