package com.example.myapplication.utils;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.LocaleList;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import java.util.Locale;

/* loaded from: classes5.dex */
public class ScreenUtils {
    private static int SCREEN_HEIGHT = 0;
    private static int SCREEN_HEIGHT_REAL = 0;
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_WIDTH_REAL = 0;
    public static final String TAG = "ScreenUtils";
    private static volatile ScreenUtils instance;
    private final SharedPreferences mSharedPreferences;
    private final String TAG_LANGUAGE = "language_select";
    private final String SP_NAME = "language_setting";

    public interface ViewLayoutListener {
        void onLayout(int i6, int i7);
    }

    private ScreenUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int dpToPx(int i6) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * i6);
    }

    public static float dpToPxFloat(float f6) {
        return Resources.getSystem().getDisplayMetrics().density * f6;
    }

    public static void executeAfterViewLayout(View view, ViewLayoutListener viewLayoutListener) {
        executeAfterViewLayout(view, false, viewLayoutListener);
    }

    public static int getAdBannerHeight(Context context) {
        int adWidthDp = getAdWidthDp(context);
        int max = Math.max(Math.min(adWidthDp > 655 ? Math.round((adWidthDp / 728.0f) * 90.0f) : adWidthDp > 632 ? 81 : adWidthDp > 526 ? Math.round((adWidthDp / 468.0f) * 60.0f) : adWidthDp > 432 ? 68 : Math.round((adWidthDp / 320.0f) * 50.0f), Math.min(90, Math.round(getAdHeightDp(context, 0) * 0.15f))), 50);
        if (max >= 64) {
            return 64;
        }
        return max;
    }

    public static int getAdHeightDp(Context context, int i6) {
        DisplayMetrics displayMetrics;
        Configuration configuration;
        if (context == null) {
            return -1;
        }
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        Resources resources = context.getResources();
        if (resources == null || (displayMetrics = resources.getDisplayMetrics()) == null || (configuration = resources.getConfiguration()) == null) {
            return -1;
        }
        int i7 = configuration.orientation;
        if (i6 == 0) {
            i6 = i7;
        }
        return i6 == i7 ? Math.round(displayMetrics.heightPixels / displayMetrics.density) : Math.round(displayMetrics.widthPixels / displayMetrics.density);
    }

    public static int getAdWidthDp(Context context) {
        android.view.Display defaultDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    public static long getAppVersionCode(Context context) {
        try {
            return Build.VERSION.SDK_INT >= 28 ? context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode() : r2.versionCode;
        } catch (Exception unused) {
            return 0L;
        }
    }

    public static DisplayMetrics getCurrentScreenDisplay(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getDaoHangHeight(Context context) {
        try {
            if (context.getResources().getIdentifier("config_showNavigationBar", "bool", "android") != 0) {
                return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("navigation_bar_height", "dimen", "android"));
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:?, code lost:

        return r4 + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002c, code lost:

        if (r4 > 0.0f) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003c, code lost:

        if (r4 > 0.0f) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0018, code lost:

        if (r4 > 0.0f) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:?, code lost:

        return r4 - r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static float getDiffAngle(float r4, float r5) {
        /*
            float r0 = java.lang.Math.abs(r4)
            int r1 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r1 >= 0) goto L9
            goto L41
        L9:
            r1 = 1119092736(0x42b40000, float:90.0)
            float r2 = r0 - r1
            float r2 = java.lang.Math.abs(r2)
            r3 = 0
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 >= 0) goto L1e
            int r5 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r5 <= 0) goto L1c
        L1a:
            float r4 = r4 - r1
            goto L41
        L1c:
            float r4 = r4 + r1
            goto L41
        L1e:
            r1 = 1127481344(0x43340000, float:180.0)
            float r2 = r0 - r1
            float r2 = java.lang.Math.abs(r2)
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 >= 0) goto L2f
            int r5 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r5 <= 0) goto L1c
            goto L1a
        L2f:
            r1 = 1132920832(0x43870000, float:270.0)
            float r0 = r0 - r1
            float r0 = java.lang.Math.abs(r0)
            int r5 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r5 >= 0) goto L3f
            int r5 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r5 <= 0) goto L1c
            goto L1a
        L3f:
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
        L41:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.utils.ScreenUtils.getDiffAngle(float, float):float");
    }

    public static ScreenUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (ScreenUtils.class) {
                if (instance == null) {
                    instance = new ScreenUtils(context);
                }
            }
        }
        return instance;
    }

    public static int getRealScreenHeight(Activity activity) {
        android.view.Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.y;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenHeightReal() {
        if (SCREEN_HEIGHT_REAL == 0) {
            readScreenSizeReal();
        }
        return SCREEN_HEIGHT_REAL;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenWidthReal() {
        if (SCREEN_WIDTH_REAL == 0) {
            readScreenSizeReal();
        }
        return SCREEN_WIDTH_REAL;
    }

    public static int getStatusHeight(Context context) {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e7) {
            e7.printStackTrace();
            return -1;
        }
    }

    public static Locale getSystemLocale() {
        LocaleList localeList;
        Locale locale;
        if (Build.VERSION.SDK_INT < 24) {
            return Locale.getDefault();
        }
        localeList = LocaleList.getDefault();
        locale = localeList.get(0);
        return locale;
    }

    public static int getVirtualBarHeigh(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        android.view.Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class).invoke(defaultDisplay, displayMetrics);
            return displayMetrics.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e7) {
            e7.printStackTrace();
            return 0;
        }
    }

    public static boolean isLargeThan720(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.min(displayMetrics.heightPixels, displayMetrics.widthPixels) >= 720;
    }

    public static boolean isLessThan1920() {
        return (((float) getScreenHeight()) * 1.0f) / ((float) getScreenWidth()) <= 1.7777778f;
    }

    public static boolean isNavigationBarExist(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        if (viewGroup != null) {
            for (int i6 = 0; i6 < viewGroup.getChildCount(); i6++) {
                viewGroup.getChildAt(i6).getContext().getPackageName();
                if (viewGroup.getChildAt(i6).getId() != -1 && "navigationBarBackground" == activity.getResources().getResourceEntryName(viewGroup.getChildAt(i6).getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        for (int i6 = 0; i6 < str.length(); i6++) {
            System.out.println(str.charAt(i6));
            if (!Character.isDigit(str.charAt(i6))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isScreenOriatationLandscap(Context context) {
        try {
            return context.getResources().getConfiguration().orientation != 1;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static int pxToDp(float f6) {
        return Math.round(f6 / Resources.getSystem().getDisplayMetrics().density);
    }

    public static float pxToDpFloat(float f6) {
        return (f6 * 1.0f) / Resources.getSystem().getDisplayMetrics().density;
    }

    public static int readPictureAngle(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (Exception unused) {
            return 0;
        }
    }

    private static void readScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) App.getAppContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_WIDTH = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
        SCREEN_HEIGHT = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        boolean z6 = (App.getAppContext().getResources().getConfiguration().screenLayout & 15) >= 3;
        StringBuilder sb = new StringBuilder();
        sb.append("screen width ");
        sb.append(SCREEN_WIDTH);
        sb.append(" height ");
        sb.append(SCREEN_HEIGHT);
        sb.append(StringUtils.SPACE);
        sb.append(z6);
        int i6 = SCREEN_HEIGHT;
        int i7 = SCREEN_WIDTH;
        if (i6 >= i7 || z6) {
            return;
        }
        int i8 = i6 ^ i7;
        int i9 = i8 ^ i7;
        SCREEN_WIDTH = i9;
        SCREEN_HEIGHT = i8 ^ i9;
    }

    private static void readScreenSizeReal() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) App.getAppContext().getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        SCREEN_WIDTH_REAL = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
        SCREEN_HEIGHT_REAL = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        boolean z6 = (App.getAppContext().getResources().getConfiguration().screenLayout & 15) >= 3;
        StringBuilder sb = new StringBuilder();
        sb.append("screen width ");
        sb.append(SCREEN_WIDTH_REAL);
        sb.append(" height ");
        sb.append(SCREEN_HEIGHT_REAL);
        sb.append(StringUtils.SPACE);
        sb.append(z6);
        int i6 = SCREEN_HEIGHT_REAL;
        int i7 = SCREEN_WIDTH_REAL;
        if (i6 >= i7 || z6) {
            return;
        }
        int i8 = i6 ^ i7;
        int i9 = i8 ^ i7;
        SCREEN_WIDTH_REAL = i9;
        SCREEN_HEIGHT_REAL = i8 ^ i9;
    }

    public static void setApplicationLanguage(Context context, Locale locale) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= 24) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context.createConfigurationContext(configuration);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static Context setLocale(Context context, Locale locale) {
        return updateResources(context, locale);
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    public static void vibrate(Context context, long j6) {
        VibrationEffect createOneShot;
        if (context != null) {
            try {
                Object systemService = context.getSystemService("vibrator");
                if (!(systemService instanceof Vibrator) || Build.VERSION.SDK_INT < 26) {
                    return;
                }
                createOneShot = VibrationEffect.createOneShot(j6, -1);
                ((Vibrator) systemService).vibrate(createOneShot);
            } catch (Exception unused) {
            }
        }
    }

    public int getSelectLanguage() {
        return this.mSharedPreferences.getInt("language_select", 0);
    }

    public void saveLanguage(int i6) {
        SharedPreferences.Editor edit = this.mSharedPreferences.edit();
        edit.putInt("language_select", i6);
        edit.apply();
    }

    public static void executeAfterViewLayout(final View view, boolean z6, final ViewLayoutListener viewLayoutListener) {
        if (view == null) {
            return;
        }
        int width = view.getWidth();
        int height = view.getHeight();
        if (width == 0 || height == 0 || z6) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: notes.easy.android.mynotes.utils.ScreenUtils.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    View view2 = view;
                    if (view2 == null || viewLayoutListener == null) {
                        return;
                    }
                    view2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    viewLayoutListener.onLayout(view.getMeasuredWidth(), view.getMeasuredHeight());
                }
            });
        } else {
            viewLayoutListener.onLayout(width, height);
        }
    }

    public static int dpToPx(float f6) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * f6);
    }

    public static int readPictureAngle(Uri uri) {
        ExifInterface exifInterface;
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                exifInterface = new ExifInterface(App.getAppContext().getContentResolver().openFileDescriptor(uri, Constants.SPAN_R).getFileDescriptor());
            } else {
                String path = PathUtils.getPath(App.getAppContext(), uri);
                if (path == null) {
                    return 0;
                }
                exifInterface = new ExifInterface(path);
            }
            int attributeInt = exifInterface.getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (Exception unused) {
            return 0;
        }
    }

    public ScreenUtils(Context context) {
        this.mSharedPreferences = context.getSharedPreferences("language_setting", 0);
    }

    public static int getScreenHeight() {
        if (SCREEN_HEIGHT == 0) {
            readScreenSize();
        }
        return SCREEN_HEIGHT;
    }

    public static int getScreenWidth() {
        if (SCREEN_WIDTH == 0) {
            readScreenSize();
        }
        return SCREEN_WIDTH;
    }

    public static int getVirtualBarHeigh(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return activity.getWindow().findViewById(R.id.content).getTop() - rect.top;
    }
}
