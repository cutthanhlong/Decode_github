package com.example.myapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.facebook.internal.security.CertificateUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.flexbox.FlexItem;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.constant.Constants;
import notes.easy.android.mynotes.db.DbHelper;
import notes.easy.android.mynotes.models.Note;
import notes.easy.android.mynotes.subs.SubsCancelConfirmActivity;
import notes.easy.android.mynotes.ui.fragments.SketchFragmentNew;
import notes.easy.android.mynotes.utils.DeviceUtils;
import notes.easy.android.mynotes.utils.FileProviderHelper;
import notes.easy.android.mynotes.view.welcome.BatteryIgnoreState;
import org.apache.commons.io.FileUtils;

/* loaded from: classes5.dex */
public class Util {

    public interface ViewLayoutListener {
        void onLayout(int i6, int i7);
    }

    public static String FormatFileSize(long j6) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (j6 == 0) {
            return "0B";
        }
        if (j6 < 1024) {
            return decimalFormat.format(j6) + "B";
        }
        if (j6 < 1048576) {
            return decimalFormat.format(j6 / 1024.0d) + "KB";
        }
        if (j6 < FileUtils.ONE_GB) {
            return decimalFormat.format(j6 / 1048576.0d) + "MB";
        }
        return decimalFormat.format(j6 / 1.073741824E9d) + "GB";
    }

    private static Bitmap addLogo(Bitmap bitmap, Bitmap bitmap2, float f6) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap2 == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int width2 = bitmap2.getWidth();
        int height2 = bitmap2.getHeight();
        if (width == 0 || height == 0) {
            return null;
        }
        if (width2 == 0 || height2 == 0) {
            return bitmap;
        }
        float f7 = (width * f6) / width2;
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            canvas.scale(f7, f7, width / 2, height / 2);
            canvas.drawBitmap(bitmap2, (width - width2) / 2, (height - height2) / 2, (Paint) null);
            canvas.save();
            canvas.restore();
            return createBitmap;
        } catch (Exception unused) {
            return null;
        }
    }

    public static int checkColorSelectedIndex(int i6) {
        if (i6 == -1) {
            return -1;
        }
        int i7 = 0;
        while (true) {
            int[] iArr = SketchFragmentNew.colorList;
            if (i7 >= iArr.length) {
                return -1;
            }
            if (i6 == iArr[i7]) {
                return i7;
            }
            i7++;
        }
    }

    public static Bitmap createQRCode(String str, int i6, Bitmap bitmap) {
        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        if (bitmap != null) {
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        } else {
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        }
        hashMap.put(EncodeHintType.MARGIN, 1);
        try {
            BitMatrix encode = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, i6, i6, hashMap);
            int[] iArr = new int[i6 * i6];
            for (int i7 = 0; i7 < i6; i7++) {
                for (int i8 = 0; i8 < i6; i8++) {
                    if (encode.get(i8, i7)) {
                        iArr[(i7 * i6) + i8] = -16777216;
                    } else {
                        iArr[(i7 * i6) + i8] = 0;
                    }
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(i6, i6, Bitmap.Config.ARGB_8888);
            createBitmap.setPixels(iArr, 0, i6, 0, 0, i6, i6);
            return bitmap != null ? addLogo(createBitmap, bitmap, 0.2f) : createBitmap;
        } catch (WriterException unused) {
            return null;
        }
    }

    public static int dpToPx(Context context, float f6) {
        return (int) ((f6 * context.getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void executeAfterViewLayout(View view, ViewLayoutListener viewLayoutListener) {
        executeAfterViewLayout(view, false, viewLayoutListener);
    }

    public static int getColorWithAlpha(float f6, int i6) {
        return (Math.min(NalUnitUtil.EXTENDED_SAR, Math.max(0, (int) (f6 * 255.0f))) << 24) + (i6 & FlexItem.MAX_SIZE);
    }

    public static int getCustomEmojiNum(String str, String str2) {
        int i6 = 0;
        try {
            int indexOf = str.indexOf(str2);
            while (indexOf != -1) {
                indexOf = str.indexOf(str2, indexOf + 1);
                i6++;
            }
        } catch (Exception unused) {
        }
        return i6;
    }

    public static long getFileSize(File file) {
        long j6 = 0;
        try {
            if (file.exists()) {
                j6 = new FileInputStream(file).available();
            } else {
                file.createNewFile();
            }
        } catch (Exception unused) {
        }
        return j6;
    }

    public static void getGAID(final Context context) {
        new Thread(new Runnable() { // from class: notes.easy.android.mynotes.view.bubble.b
            @Override // java.lang.Runnable
            public final void run() {
                Util.lambda$getGAID$0(context);
            }
        }).start();
    }

    public static LinearGradient getLinearGradidenColor(int i6, int i7, int i8) {
        return new LinearGradient(0.0f, 0.0f, i6, i7, i8, Color.parseColor("#000000"), Shader.TileMode.REPEAT);
    }

    public static int[] getScreenWH(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    public static int getStatusHeight(Context context) {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getApplicationContext().getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e7) {
            e7.printStackTrace();
            return -1;
        }
    }

    public static void gotoEmail(Activity activity, String str, boolean z6) {
        String string = activity.getString(R.string.report_issue_easynote_feedback);
        String str2 = "Phone Model: " + Build.MODEL + "\nCountryCode:" + ((TelephonyManager) App.getAppContext().getSystemService("phone")).getNetworkCountryIso() + "\nAndroid Version: " + Build.VERSION.SDK + "\nApp Version: 1.2.97.0225\nIssue Source: " + str + "\n\n";
        if (z6) {
            str2 = str2 + "(" + activity.getString(R.string.bug_feedback_hint) + ")";
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("plain/text");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{"easynotes@guloolootech.com"});
        intent.putExtra("android.intent.extra.SUBJECT", string);
        intent.putExtra("android.intent.extra.TEXT", str2);
        try {
            intent.setPackage("com.google.android.gm");
            activity.startActivity(intent);
        } catch (Exception unused) {
            intent.setPackage(null);
            activity.startActivity(Intent.createChooser(intent, ""));
        }
    }

    public static void hide(Dialog dialog) {
        View currentFocus = dialog.getCurrentFocus();
        if (currentFocus instanceof TextView) {
            ((InputMethodManager) dialog.getContext().getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public static boolean is22Nation() {
        return Constants.WELCOM_LIST.contains(DeviceUtils.getCCODE(App.getAppContext()).toUpperCase());
    }

    public static BatteryIgnoreState isIgnoringBatteryOptimizations() {
        boolean isIgnoringBatteryOptimizations;
        if (Build.VERSION.SDK_INT < 23) {
            return BatteryIgnoreState.NOT_FOUND;
        }
        isIgnoringBatteryOptimizations = ((PowerManager) App.getAppContext().getSystemService("power")).isIgnoringBatteryOptimizations(App.getAppContext().getPackageName());
        return isIgnoringBatteryOptimizations ? BatteryIgnoreState.GRANTED : BatteryIgnoreState.DENIED;
    }

    public static void jumpToFaceBook(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("fb://page/103799631632789"));
            intent.setClassName("com.facebook.katana", "com.facebook.katana.IntentUriHandler");
            intent.addFlags(1);
            context.startActivity(intent);
        } catch (Exception unused) {
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.VIEW");
            intent2.setData(Uri.parse("https://www.facebook.com/Easy-Notes-103799631632789"));
            context.startActivity(intent2);
        }
    }

    public static void jumpToGoogleDriveSpace(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://drive.google.com/settings/storage"));
            intent.setPackage("com.google.android.apps.docs");
            context.startActivity(intent);
        } catch (Exception unused) {
            try {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://drive.google.com/settings/storage")));
            } catch (Exception unused2) {
            }
        }
    }

    public static void jumpToGooglePlay(Context context, String str) {
        try {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + str));
                intent.setPackage("com.android.vending");
                context.startActivity(intent);
            } catch (Exception unused) {
            }
        } catch (ActivityNotFoundException unused2) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + str)));
        }
    }

    public static void jumpToGooglePlayOnly(Context context) {
        try {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setPackage("com.android.vending");
                context.startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps")));
            }
        } catch (Exception unused2) {
        }
    }

    public static void jumpToGooglePlaySubs(Context context) {
        try {
            try {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(SubsCancelConfirmActivity.SUBS_APP)));
            } catch (Exception unused) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(SubsCancelConfirmActivity.SUBS_HTTP)));
            }
        } catch (Exception unused2) {
        }
    }

    public static void jumpToInstagram(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://instagram.com/_u/easynotes.official/"));
        intent.setPackage("com.instagram.android");
        try {
            context.startActivity(intent);
        } catch (Exception unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.instagram.com/easynotes.official/")));
        }
    }

    public static void jumpToRequestFeature(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://easynotes.featurebase.app/"));
        intent.addFlags(268435456);
        try {
            context.startActivity(intent);
        } catch (Exception unused) {
        }
    }

    public static void jumpToSetting(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            context.startActivity(intent);
        } catch (Exception unused) {
        }
    }

    public static void jumpToSettingNotification(Context context) {
        Intent intent = new Intent();
        try {
            intent.setFlags(268435456);
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
            context.startActivity(intent);
        } catch (Exception unused) {
            jumpToSetting(context);
        }
    }

    public static void jumpToStorageManager(Context context) {
        try {
            Intent intent = new Intent("android.settings.INTERNAL_STORAGE_SETTINGS");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getGAID$0(Context context) {
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            advertisingIdInfo.getId();
            advertisingIdInfo.isLimitAdTrackingEnabled();
        } catch (Exception e7) {
            e7.printStackTrace();
        }
    }

    public static boolean meetNoteRateUsRule(String str, long j6, Note note) {
        if (j6 < 5) {
            j6 = 8;
        }
        return (!TextUtils.isEmpty(str) && ((long) str.length()) >= j6) || (note.getAttachmentsList() != null && note.getAttachmentsList().size() > 0);
    }

    public static boolean meetRate(SharedPreferences sharedPreferences, String str, long j6, Note note) {
        if (!sharedPreferences.getBoolean(Constants.RATE_SHOW, false) && DbHelper.getInstance().getNotesActive().size() == 0 && meetNoteRateUsRule(str, j6, note)) {
            return true;
        }
        return !sharedPreferences.getBoolean(Constants.RATE_SHOW, false) && DbHelper.getInstance().getNotesActive().size() >= 1;
    }

    public static String ms2HMS(long j6) {
        long j7 = j6 / 1000;
        long j8 = j7 / 3600;
        int i6 = ((int) (j7 % 3600)) / 60;
        int i7 = ((int) j7) % 60;
        String valueOf = String.valueOf(j8);
        if (j8 < 10) {
            valueOf = "0" + valueOf;
        }
        String valueOf2 = String.valueOf(i6);
        if (i6 < 10) {
            valueOf2 = "0" + valueOf2;
        }
        String valueOf3 = String.valueOf(i7);
        if (i7 < 10) {
            valueOf3 = "0" + valueOf3;
        }
        if ("00".equals(valueOf)) {
            return "00:" + valueOf2 + CertificateUtil.DELIMITER + valueOf3;
        }
        return valueOf + CertificateUtil.DELIMITER + valueOf2 + CertificateUtil.DELIMITER + valueOf3;
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return -1;
        }
    }

    public static void requestIgnoringBattery(Context context) {
        try {
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            intent.setData(Uri.parse("package:" + App.getAppContext().getPackageName()));
            context.startActivity(intent);
        } catch (Exception unused) {
        }
    }

    public static void showFeedbackDialog(Activity activity) {
        String string = activity.getString(R.string.report_issue_easynote_feedback);
        String str = "Phone Model: " + Build.MODEL + "\nCountryCode:" + ((TelephonyManager) App.getAppContext().getSystemService("phone")).getNetworkCountryIso() + "\nAndroid Version: " + Build.VERSION.SDK + "\nApp Version: 1.2.97.0225\nIssue Source: Rate\n\n";
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("plain/text");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{"easynotes@guloolootech.com"});
        intent.putExtra("android.intent.extra.SUBJECT", string);
        intent.putExtra("android.intent.extra.TEXT", str);
        try {
            intent.setPackage("com.google.android.gm");
            activity.startActivity(intent);
        } catch (Exception unused) {
            intent.setPackage(null);
            activity.startActivity(Intent.createChooser(intent, ""));
        }
    }

    public static void toSelfSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

    public static void executeAfterViewLayout(final View view, boolean z6, final ViewLayoutListener viewLayoutListener) {
        if (view == null) {
            return;
        }
        int width = view.getWidth();
        int height = view.getHeight();
        if (width == 0 || height == 0 || z6) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: notes.easy.android.mynotes.view.bubble.Util.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    View view2 = view;
                    if (view2 == null || viewLayoutListener == null) {
                        return;
                    }
                    if (view2.getWidth() != 0 && view.getHeight() != 0) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    viewLayoutListener.onLayout(view.getWidth(), view.getHeight());
                }
            });
        } else {
            viewLayoutListener.onLayout(width, height);
        }
    }

    public static void gotoEmail(Context context, String str, String str2, List<Uri> list, File file, File file2) {
        gotoEmail(context, str, null, str2, list, file, file2);
    }

    public static void gotoEmail(Context context, String str, String str2, String str3, List<Uri> list, File file, File file2) {
        String string;
        String str4;
        if (!TextUtils.equals(str, Constants.INTENT_VALUE_RATE) && !TextUtils.equals(str, Constants.INTENT_VALUE_RESTORE_PURCHASE)) {
            string = context.getString(R.string.report_issue_to_easynote);
        } else {
            string = context.getString(R.string.report_issue_easynote_feedback);
        }
        String str5 = "Phone Model: " + Build.MODEL + "\nCountryCode:" + ((TelephonyManager) App.getAppContext().getSystemService("phone")).getNetworkCountryIso() + "\nAndroid Version: " + Build.VERSION.SDK + "\nApp Version: 1.2.97.0225\nIssue Source: " + str + "\n";
        if (!TextUtils.isEmpty(str2)) {
            str5 = str5 + "FAQ Category: " + str2 + "\n";
        }
        if (TextUtils.isEmpty(str3)) {
            str4 = "";
        } else {
            str4 = str3 + "\n\n";
        }
        String str6 = str4 + str5;
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            arrayList.addAll(list);
        }
        if (file != null) {
            arrayList.add(FileProviderHelper.getFileProvider(file));
        }
        if (file2 != null) {
            arrayList.add(FileProviderHelper.getFileProvider(file2));
        }
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.addFlags(268435456);
        intent.setType("message/rfc822");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{"easynotes@guloolootech.com"});
        intent.putExtra("android.intent.extra.SUBJECT", string);
        intent.putExtra("android.intent.extra.TEXT", str6);
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        try {
            intent.setPackage("com.google.android.gm");
            context.startActivity(intent);
        } catch (Exception unused) {
            intent.setPackage(null);
            context.startActivity(Intent.createChooser(intent, ""));
        }
    }
}
