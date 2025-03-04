package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.utils.Constants;

import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.EasyNoteManager;
import notes.easy.android.mynotes.async.bus.GlobalFinishEvent;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.ui.fragments.BaseNewFragment;
import notes.easy.android.mynotes.utils.BarUtils;
import notes.easy.android.mynotes.utils.ConstantsBase;
import notes.easy.android.mynotes.utils.DeviceUtils;
import notes.easy.android.mynotes.utils.EventBus.EventInfo;
import notes.easy.android.mynotes.utils.permission.PermissionsManager;

/* loaded from: classes4.dex */
public abstract class BaseActivity extends AppCompatActivity {
    public static final int TRANSITION_HORIZONTAL = 1;
    public SharedPreferences prefs;
    public UserConfig userConfig;

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(ScreenUtils.setLocale(context, ScreenUtils.getInstance(context).getSelectLanguage() == 0 ? ScreenUtils.getSystemLocale() : Constants.LANGUAGE.get(ScreenUtils.getInstance(context).getSelectLanguage())));
    }

    protected int getBackGroundColor() {
        return ContextCompat.getColor(this, R.color.global_background);
    }

    public abstract int getResID();

    protected int getStatusColor() {
        return ContextCompat.getColor(this, R.color.status_color);
    }

    public void gotoLock() {
        if (showLock()) {
            startActivity(new Intent(this, (Class<?>) LockAppActivity.class));
            overridePendingTransition(0, 0);
        }
        App.setIsLockOn(false);
    }

    protected void initStatusBarMarginTop_() {
        View findViewById = findViewById(R.id.top_layout);
        if (findViewById != null) {
            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.height = BarUtils.getStatusBarHeight(this);
            findViewById.setLayoutParams(layoutParams);
        }
    }

    public abstract void initView(View view);

    protected boolean isDarkMode() {
        if (this.userConfig.getThemeState() != 1) {
            return this.userConfig.getThemeState() == 2 && DeviceUtils.getNightMode(App.getAppContext()) == 33;
        }
        return true;
    }

    protected boolean isStyleBg() {
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        boolean z6 = false;
        int i6 = 0;
        while (true) {
            if (i6 >= supportFragmentManager.getFragments().size()) {
                z6 = true;
                break;
            }
            Fragment fragment = supportFragmentManager.getFragments().get(i6);
            if (fragment.isVisible() && (fragment instanceof BaseNewFragment) && ((BaseNewFragment) fragment).onBackPressed()) {
                break;
            } else {
                i6++;
            }
        }
        if (z6) {
            super.onBackPressed();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(null);
        ActivityStackManager.getInstance().addActivity(this);
        EasyNoteManager.initAppContext(this);
        EasyNoteManager.initApp("Activity");
        this.prefs = getSharedPreferences(ConstantsBase.PREFS_NAME, 4);
        this.userConfig = UserConfig.Companion.newInstance(this);
        EventBus.getDefault().register(this);
        onPreOnCreate(bundle);
        setContentView(getResID());
        if (!isStyleBg()) {
            getWindow().getDecorView().getRootView().setBackgroundColor(getBackGroundColor());
        }
        initView(getWindow().getDecorView().getRootView());
        try {
            if (transparent()) {
                BarUtils.setStatusBarTransparent(this);
                BarUtils.setStatusBarTextColor(this, getStatusColor());
            } else {
                BarUtils.setStatusBarColor(this, getStatusColor());
            }
            if (this.userConfig.getThemeState() != 1 && (this.userConfig.getThemeState() != 2 || DeviceUtils.getNightMode(App.getAppContext()) != 33)) {
                BarUtils.setLightNavigationBar(this);
                return;
            }
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black_87alpha_df000));
            BarUtils.setDarkNavigationBar(this);
        } catch (Exception unused) {
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        PermissionsManager.getInstance().clear();
        ActivityStackManager.getInstance().removeActivity(this);
    }

    public void onEvent(EventInfo eventInfo) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i6, String[] strArr, int[] iArr) {
        PermissionsManager.getInstance().notifyPermissionsChange(strArr, iArr, i6);
        super.onRequestPermissionsResult(i6, strArr, iArr);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        try {
            if (showLock()) {
                gotoLock();
            }
        } catch (Exception unused) {
        }
    }

    public void setActionBarTitle(String str) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(str);
        }
    }

    public boolean showLock() {
        return App.isVip() && App.isLockOn() && this.userConfig.isEnableAppSwitch();
    }

    public void showToast(CharSequence charSequence, int i6) {
        if (this.prefs.getBoolean("settings_enable_info", true)) {
            Toast.makeText(getApplicationContext(), charSequence, i6).show();
        }
    }

    public void slideView(View view, float f6) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", f6);
        ofFloat.setDuration(400L);
        ofFloat.start();
    }

    protected boolean transparent() {
        return false;
    }

    public void onEvent(GlobalFinishEvent globalFinishEvent) {
        if (isFinishing() || isDestroyed() || (this instanceof LockAppActivity)) {
            return;
        }
        finish();
    }

    public static void notifyAppWidgets(Context context) {
    }

    public void onEventMainThread(EventInfo eventInfo) {
    }

    public void onPreOnCreate(Bundle bundle) {
    }

    @SuppressLint({"InlinedApi"})
    public void animateTransition(FragmentTransaction fragmentTransaction, int i6) {
    }
}
