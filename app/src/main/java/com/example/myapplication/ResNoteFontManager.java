package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.EasyNoteManager;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.db.DbHelper;
import notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener;
import notes.easy.android.mynotes.downloader.downloader.DownloaderConfig;
import notes.easy.android.mynotes.firebase.FirebaseReportUtils;
import notes.easy.android.mynotes.firebase.RemoteConfig;
import notes.easy.android.mynotes.ui.model.NoteFontBean;
import notes.easy.android.mynotes.ui.model.NoteFontConfig;
import notes.easy.android.mynotes.ui.model.NoteFontLocale;
import notes.easy.android.mynotes.ui.model.ResData;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class ResNoteFontManager {
    public static final String COVER = "cover/";
    public static final String DATA_END = ".json";
    public static final String DEFAULT_FONT_PATH = "notefont/font/moarope.ttf";
    public static final String DIRECTORY = "notefont/";
    public static final int DOWNLOAD_TYPE_COVER = 1;
    public static final int DOWNLOAD_TYPE_COVER_DARK = 2;
    public static final int DOWNLOAD_TYPE_FONT = 0;
    public static final String FONT = "font/";
    public static final String LOCAL_NOTE_FONT_CONFIG = "fonts_config.json";
    public static final String ROOT_URL_NOTE_FONT = "https://easynotes-bucket.s3.eu-west-1.amazonaws.com/material/font/files/";
    public static final String TAG = "NoteFontRes";
    public static final String DEFAULT_FONT = "moarope.ttf";
    public static final String[] FONT_TYPES = {DEFAULT_FONT, "lobstertwo.ttf", "ubuntu.ttf", "adistro.otf", "magiera.ttf", "great_vibes.otf", "floane.otf", "marker_felt.ttf", "hachi_maru_pop.ttf", "poping.ttf", "futura.ttf", "roboto.ttf"};
    private static ResNoteFontManager sInstance = null;
    private NoteFontConfig mCurrentConfig = null;
    private final List<String> mDownloadingUrl = Collections.synchronizedList(new ArrayList());
    public final HashMap<String, Integer> mDownloadingProgress = new HashMap<>();

    public interface FontDownloadListener {
        void onDownloadFailed(NoteFontBean noteFontBean);

        void onDownloadStart(NoteFontBean noteFontBean);

        void onDownloadSuccess(NoteFontBean noteFontBean, String str);

        void updateDownloadProgress(NoteFontBean noteFontBean, long j6, float f6, float f7);
    }

    private ResNoteFontManager() {
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkOldFontRes(Context context, String str, String str2) {
        try {
            File file = new File(FileUtils.getAppInternalDir().getAbsolutePath() + File.separator + str);
            if (file.exists()) {
                File file2 = new File(context.getFilesDir(), DIRECTORY + str2);
                if (file2.exists()) {
                    file.delete();
                    return;
                }
                if (file2.getParentFile() != null && !file2.getParentFile().exists()) {
                    file2.getParentFile().mkdirs();
                }
                file.renameTo(file2);
            }
        } catch (Exception unused) {
        }
    }

    public static ResNoteFontManager getInstance() {
        if (sInstance == null) {
            synchronized (ResNoteFontManager.class) {
                if (sInstance == null) {
                    sInstance = new ResNoteFontManager();
                }
            }
        }
        return sInstance;
    }

    private void init() {
        initDbNoteFontConfig();
        UserConfig userConfig = App.userConfig;
        int currentFontAppVersion = userConfig != null ? userConfig.getCurrentFontAppVersion() : 0;
        if (this.mCurrentConfig == null || currentFontAppVersion == 0 || 10489 != currentFontAppVersion) {
            NoteFontConfig assetNoteFontConfig = getAssetNoteFontConfig();
            if (this.mCurrentConfig == null || assetNoteFontConfig.getVersion() > this.mCurrentConfig.getVersion()) {
                this.mCurrentConfig = assetNoteFontConfig;
                updateNoteFontConfig(assetNoteFontConfig);
                copyFontAssetToFile();
            }
            UserConfig userConfig2 = App.userConfig;
            if (userConfig2 != null) {
                userConfig2.setCurrentFontAppVersion(10489);
            }
        }
    }

    public void checkRemote(final Context context) {
        NoteFontConfig noteFontConfig;
        String string = RemoteConfig.getString("note_font");
        if (!TextUtils.isEmpty(string) && (noteFontConfig = (NoteFontConfig) new Gson().fromJson(string, NoteFontConfig.class)) != null && noteFontConfig.getVersion() > this.mCurrentConfig.getVersion()) {
            List<Integer> appCode = noteFontConfig.getAppCode();
            for (int i6 = 0; i6 < appCode.size(); i6++) {
                if (10489 <= appCode.get(i6).intValue()) {
                    return;
                }
            }
            mergeRemoteFontConfig(noteFontConfig);
        }
        ExecutorUtils.CACHED_THREAD_POOL.execute(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteFontManager.1
            /* JADX WARN: Removed duplicated region for block: B:32:0x00da  */
            /* JADX WARN: Removed duplicated region for block: B:35:0x00de A[SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r11 = this;
                    notes.easy.android.mynotes.utils.ResNoteFontManager r0 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    java.util.List r0 = r0.getNoteFontList()
                    r1 = 0
                    r2 = 0
                    r3 = 0
                L9:
                    int r4 = r0.size()
                    if (r2 >= r4) goto Le2
                    java.lang.Object r4 = r0.get(r2)
                    notes.easy.android.mynotes.ui.model.NoteFontBean r4 = (notes.easy.android.mynotes.ui.model.NoteFontBean) r4
                    boolean r5 = r4.isCoverReady()
                    java.lang.String r6 = "cover/"
                    r7 = 1
                    if (r5 != 0) goto L58
                    java.lang.String r5 = r4.getFontIcon()
                    boolean r5 = android.text.TextUtils.isEmpty(r5)
                    if (r5 != 0) goto L54
                    java.lang.String r5 = r4.getFontIcon()
                    notes.easy.android.mynotes.utils.ResNoteFontManager r8 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    android.content.Context r9 = r2
                    java.lang.StringBuilder r10 = new java.lang.StringBuilder
                    r10.<init>()
                    r10.append(r6)
                    r10.append(r5)
                    java.lang.String r10 = r10.toString()
                    notes.easy.android.mynotes.utils.ResNoteFontManager.access$000(r8, r9, r5, r10)
                    notes.easy.android.mynotes.utils.ResNoteFontManager r8 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    android.graphics.drawable.Drawable r5 = r8.getDrawableFromFile(r5)
                    if (r5 == 0) goto L4e
                    r4.setCoverReady(r7)
                    goto L57
                L4e:
                    notes.easy.android.mynotes.utils.ResNoteFontManager r5 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    r5.startDownloadCover(r4, r7)
                    goto L58
                L54:
                    r4.setCoverReady(r7)
                L57:
                    r3 = 1
                L58:
                    boolean r5 = r4.isCoverDarkReady()
                    if (r5 != 0) goto L99
                    java.lang.String r5 = r4.getFontIconDark()
                    boolean r5 = android.text.TextUtils.isEmpty(r5)
                    if (r5 != 0) goto L95
                    java.lang.String r5 = r4.getFontIconDark()
                    notes.easy.android.mynotes.utils.ResNoteFontManager r8 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    android.content.Context r9 = r2
                    java.lang.StringBuilder r10 = new java.lang.StringBuilder
                    r10.<init>()
                    r10.append(r6)
                    r10.append(r5)
                    java.lang.String r6 = r10.toString()
                    notes.easy.android.mynotes.utils.ResNoteFontManager.access$000(r8, r9, r5, r6)
                    notes.easy.android.mynotes.utils.ResNoteFontManager r6 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    android.graphics.drawable.Drawable r5 = r6.getDrawableFromFile(r5)
                    if (r5 == 0) goto L8e
                    r4.setCoverDarkReady(r7)
                    goto L98
                L8e:
                    notes.easy.android.mynotes.utils.ResNoteFontManager r5 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    r6 = 2
                    r5.startDownloadCover(r4, r6)
                    goto L99
                L95:
                    r4.setCoverDarkReady(r7)
                L98:
                    r3 = 1
                L99:
                    boolean r5 = r4.isFontReady()
                    if (r5 != 0) goto Lde
                    java.lang.String r5 = r4.getFontName()
                    boolean r5 = android.text.TextUtils.isEmpty(r5)
                    if (r5 != 0) goto Ld7
                    notes.easy.android.mynotes.utils.ResNoteFontManager r5 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    android.content.Context r6 = r2
                    java.lang.String r8 = r4.getFontName()
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    java.lang.String r10 = "font/"
                    r9.append(r10)
                    java.lang.String r10 = r4.getFontName()
                    r9.append(r10)
                    java.lang.String r9 = r9.toString()
                    notes.easy.android.mynotes.utils.ResNoteFontManager.access$000(r5, r6, r8, r9)
                    notes.easy.android.mynotes.utils.ResNoteFontManager r5 = notes.easy.android.mynotes.utils.ResNoteFontManager.this
                    java.lang.String r6 = r4.getFontName()
                    android.graphics.Typeface r5 = r5.getFontFromFile(r6)
                    if (r5 != 0) goto Ld7
                    r5 = 0
                    goto Ld8
                Ld7:
                    r5 = 1
                Ld8:
                    if (r5 == 0) goto Lde
                    r4.setFontReady(r7)
                    r3 = 1
                Lde:
                    int r2 = r2 + 1
                    goto L9
                Le2:
                    if (r3 == 0) goto Lf0
                    android.os.Handler r0 = notes.easy.android.mynotes.App.getsGlobalHandler()
                    notes.easy.android.mynotes.utils.ResNoteFontManager$1$1 r1 = new notes.easy.android.mynotes.utils.ResNoteFontManager$1$1
                    r1.<init>()
                    r0.post(r1)
                Lf0:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.utils.ResNoteFontManager.AnonymousClass1.run():void");
            }
        });
    }

    public void copyFontAssetToFile() {
        App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteFontManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (ResNoteFontManager.this.mCurrentConfig != null) {
                        for (int i6 = 0; i6 < ResNoteFontManager.this.mCurrentConfig.getLocales().size(); i6++) {
                            NoteFontLocale noteFontLocale = ResNoteFontManager.this.mCurrentConfig.getLocales().get(i6);
                            for (int i7 = 0; i7 < noteFontLocale.getFonts().size(); i7++) {
                                NoteFontBean noteFontBean = noteFontLocale.getFonts().get(i7);
                                if (noteFontBean.isCoverReady() && noteFontBean.getFontIcon() != null && !TextUtils.isEmpty(noteFontBean.getFontIcon())) {
                                    ResNoteFontManager.this.copyFontAssetToFile(App.getAppContext(), ResNoteFontManager.COVER + noteFontBean.getFontIcon());
                                    ResNoteFontManager.this.checkOldFontRes(App.getAppContext(), noteFontBean.getFontIcon(), ResNoteFontManager.COVER + noteFontBean.getFontIcon());
                                }
                                if (noteFontBean.isCoverDarkReady() && noteFontBean.getFontIconDark() != null && !TextUtils.isEmpty(noteFontBean.getFontIconDark())) {
                                    ResNoteFontManager.this.copyFontAssetToFile(App.getAppContext(), ResNoteFontManager.COVER + noteFontBean.getFontIconDark());
                                    ResNoteFontManager.this.checkOldFontRes(App.getAppContext(), noteFontBean.getFontIconDark(), ResNoteFontManager.COVER + noteFontBean.getFontIconDark());
                                }
                                if (noteFontBean.isFontReady() && noteFontBean.getFontName() != null && !TextUtils.isEmpty(noteFontBean.getFontName())) {
                                    ResNoteFontManager.this.copyFontAssetToFile(App.getAppContext(), ResNoteFontManager.FONT + noteFontBean.getFontName());
                                    ResNoteFontManager.this.checkOldFontRes(App.getAppContext(), noteFontBean.getFontName(), ResNoteFontManager.FONT + noteFontBean.getFontName());
                                }
                            }
                        }
                    }
                } catch (Exception e7) {
                    FirebaseCrashlytics.getInstance().recordException(new IOException("copyBgToFile ", e7));
                }
            }
        });
    }

    public NoteFontConfig getAssetNoteFontConfig() {
        try {
            return (NoteFontConfig) new Gson().fromJson(FileUtils.readAssetFile("notefont/fonts_config.json", false), NoteFontConfig.class);
        } catch (Exception unused) {
            return null;
        }
    }

    public String getDownloadUrl(String str) {
        return "https://easynotes-bucket.s3.eu-west-1.amazonaws.com/material/font/files/" + str;
    }

    public Drawable getDrawableFromFile(String str) {
        Bitmap imageFromFile;
        if (TextUtils.isEmpty(str) || (imageFromFile = getImageFromFile(str)) == null) {
            return null;
        }
        return new BitmapDrawable(imageFromFile);
    }

    public Typeface getFontFromFile(String str) {
        Typeface typeface = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            typeface = Typeface.createFromAsset(App.getAppContext().getAssets(), "notefont/font/" + str);
        } catch (Exception unused) {
        }
        if (typeface != null) {
            return typeface;
        }
        try {
            File file = new File(App.getAppContext().getFilesDir() + File.separator + DIRECTORY + FONT + str);
            return file.exists() ? Typeface.createFromFile(file) : typeface;
        } catch (Exception unused2) {
            return typeface;
        }
    }

    public Bitmap getImageFromFile(String str) {
        Bitmap bitmap = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            InputStream open = App.getAppContext().getAssets().open("notefont/cover/" + str);
            bitmap = BitmapFactory.decodeStream(open);
            open.close();
        } catch (Exception unused) {
        }
        if (bitmap == null) {
            try {
                File file = new File(App.getAppContext().getFilesDir() + File.separator + DIRECTORY + COVER + str);
                if (file.exists()) {
                    bitmap = BitmapFactory.decodeFile(file.getPath());
                }
            } catch (Exception unused2) {
            }
        }
        if (bitmap != null) {
            return bitmap;
        }
        try {
            return BitmapFactory.decodeStream(App.getAppContext().getContentResolver().openInputStream(Uri.parse(str)));
        } catch (Exception unused3) {
            return bitmap;
        }
    }

    public List<NoteFontBean> getNoteFontList() {
        Locale currentLocale = EasyNoteManager.getInstance().getCurrentLocale(App.getAppContext());
        String substring = currentLocale == null ? "en" : currentLocale.toString().contains("zh_") ? "zh_TW" : currentLocale.toString().length() >= 2 ? currentLocale.toString().substring(0, 2) : currentLocale.toString();
        NoteFontConfig noteFontConfig = this.mCurrentConfig;
        if (noteFontConfig == null || noteFontConfig.getLocales() == null) {
            return new ArrayList();
        }
        List<NoteFontBean> list = null;
        for (int i6 = 0; i6 < this.mCurrentConfig.getLocales().size(); i6++) {
            NoteFontLocale noteFontLocale = this.mCurrentConfig.getLocales().get(i6);
            String locale = noteFontLocale.getLocale();
            if (TextUtils.equals("en", locale)) {
                list = noteFontLocale.getFonts();
            }
            if (TextUtils.equals(substring, locale)) {
                return noteFontLocale.getFonts();
            }
        }
        return list;
    }

    public void initDbNoteFontConfig() {
        ResData resConfigById = DbHelper.getInstance().getResConfigById(101);
        if (resConfigById != null) {
            try {
                this.mCurrentConfig = (NoteFontConfig) new Gson().fromJson(resConfigById.getData(), NoteFontConfig.class);
            } catch (Exception unused) {
            }
        }
    }

    public void mergeRemoteFontConfig(NoteFontConfig noteFontConfig) {
        this.mCurrentConfig.setVersion(noteFontConfig.getVersion());
        this.mCurrentConfig.setAppCode(noteFontConfig.getAppCode());
        this.mCurrentConfig.setLocales(noteFontConfig.getLocales());
        updateNoteFontConfig(this.mCurrentConfig);
    }

    public void retryDownloadCover(final NoteFontBean noteFontBean, final int i6) {
        if (NetworkUtils.isNetworkConnected(App.getAppContext())) {
            final String fontIcon = i6 == 1 ? noteFontBean.getFontIcon() : noteFontBean.getFontIconDark();
            final String downloadUrl = getDownloadUrl(fontIcon);
            if (this.mDownloadingUrl.contains(downloadUrl)) {
                return;
            }
            this.mDownloadingUrl.add(downloadUrl);
            new DownloaderConfig().setThreadNum(1).setDownloadUrl(downloadUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), "notefont/cover/")).setDownloadListener(new DownloadProgressListener() { // from class: notes.easy.android.mynotes.utils.ResNoteFontManager.5
                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadFailed(Exception exc) {
                    ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                    FirebaseReportUtils.getInstance().report("remote_font_download_error", "fail_reason", "failed#" + fontIcon);
                    FirebaseReportUtils.getInstance().report("remote_font_download_error_reason", "fail_reason", exc.getMessage());
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadSuccess(String str) {
                    ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                    try {
                        File file = new File(str);
                        if (file.exists() && file.length() == 0) {
                            FirebaseReportUtils.getInstance().report("remote_font_download_error", "fail_reason", "0length#" + fontIcon);
                            return;
                        }
                    } catch (Exception e7) {
                        FirebaseCrashlytics.getInstance().recordException(new IllegalArgumentException("checkFile ", e7));
                    }
                    FirebaseReportUtils.getInstance().reportNew("remote_font_download_ok");
                    FirebaseReportUtils.getInstance().reportNew("remote_font_app_download_ok");
                    if (i6 == 1) {
                        noteFontBean.setCoverReady(true);
                    } else {
                        noteFontBean.setCoverDarkReady(true);
                    }
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onStopDownload() {
                    ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onPauseDownload() {
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadTotalSize(long j6) {
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void updateDownloadProgress(long j6, float f6, float f7) {
                }
            }).buildWolf(App.getAppContext()).startDownload();
        }
    }

    public void retryDownloadFont(final NoteFontBean noteFontBean, final FontDownloadListener fontDownloadListener) {
        final String downloadUrl = getDownloadUrl(noteFontBean.getFontName());
        if (this.mDownloadingUrl.contains(downloadUrl)) {
            return;
        }
        this.mDownloadingUrl.add(downloadUrl);
        this.mDownloadingProgress.put(noteFontBean.getFontName(), 0);
        if (fontDownloadListener != null) {
            fontDownloadListener.onDownloadStart(noteFontBean);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("startDownloadFont ");
        sb.append(noteFontBean.getFontName());
        new DownloaderConfig().setThreadNum(1).setDownloadUrl(downloadUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), "notefont/font/")).setDownloadListener(new DownloadProgressListener() { // from class: notes.easy.android.mynotes.utils.ResNoteFontManager.7
            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onDownloadFailed(Exception exc) {
                FirebaseReportUtils.getInstance().report("remote_font_download_error", "fail_reason", "failed#" + noteFontBean.getFontName());
                FirebaseReportUtils.getInstance().report("remote_font_download_error_reason", "fail_reason", exc.getMessage());
                ResNoteFontManager.this.mDownloadingProgress.put(noteFontBean.getFontName(), -1);
                FontDownloadListener fontDownloadListener2 = fontDownloadListener;
                if (fontDownloadListener2 != null) {
                    fontDownloadListener2.onDownloadFailed(noteFontBean);
                }
                ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onDownloadSuccess(String str) {
                ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                ResNoteFontManager.this.mDownloadingProgress.put(noteFontBean.getFontName(), -1);
                try {
                    File file = new File(str);
                    if (file.exists() && file.length() == 0) {
                        FontDownloadListener fontDownloadListener2 = fontDownloadListener;
                        if (fontDownloadListener2 != null) {
                            fontDownloadListener2.onDownloadFailed(noteFontBean);
                        }
                        FirebaseReportUtils.getInstance().report("remote_font_download_error", "fail_reason", "0length#" + noteFontBean.getFontName());
                        return;
                    }
                    if (ResNoteFontManager.this.getFontFromFile(noteFontBean.getFontName()) != null) {
                        noteFontBean.setFontReady(true);
                        ResNoteFontManager resNoteFontManager = ResNoteFontManager.this;
                        resNoteFontManager.updateNoteFontConfig(resNoteFontManager.mCurrentConfig);
                        FirebaseReportUtils.getInstance().reportNew("remote_font_download_ok");
                        FirebaseReportUtils.getInstance().reportNew("remote_font_user_download_ok");
                        if (fontDownloadListener != null) {
                            fontDownloadListener.onDownloadSuccess(noteFontBean, str);
                            return;
                        }
                        return;
                    }
                    FontDownloadListener fontDownloadListener3 = fontDownloadListener;
                    if (fontDownloadListener3 != null) {
                        fontDownloadListener3.onDownloadFailed(noteFontBean);
                    }
                    FirebaseReportUtils.getInstance().report("remote_font_download_error", "fail_reason", "null#" + noteFontBean.getFontName());
                } catch (Exception e7) {
                    FirebaseCrashlytics.getInstance().recordException(new IllegalArgumentException("checkFile ", e7));
                    FontDownloadListener fontDownloadListener4 = fontDownloadListener;
                    if (fontDownloadListener4 != null) {
                        fontDownloadListener4.onDownloadFailed(noteFontBean);
                    }
                    FirebaseReportUtils.getInstance().report("remote_font_download_error", "fail_reason", "exception#" + noteFontBean.getFontName());
                }
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void updateDownloadProgress(long j6, float f6, float f7) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("updateDownloadProgress ");
                sb2.append(j6);
                sb2.append(StringUtils.SPACE);
                sb2.append(f6);
                sb2.append(StringUtils.SPACE);
                sb2.append(f7);
                ResNoteFontManager.this.mDownloadingProgress.put(noteFontBean.getFontName(), Integer.valueOf(Math.round(f6)));
                FontDownloadListener fontDownloadListener2 = fontDownloadListener;
                if (fontDownloadListener2 != null) {
                    fontDownloadListener2.updateDownloadProgress(noteFontBean, j6, f6, f7);
                }
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onPauseDownload() {
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onStopDownload() {
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onDownloadTotalSize(long j6) {
            }
        }).buildWolf(App.getAppContext()).startDownload();
    }

    public void startDownloadCover(final NoteFontBean noteFontBean, final int i6) {
        if (NetworkUtils.isNetworkConnected(App.getAppContext())) {
            final String downloadUrl = getDownloadUrl(i6 == 1 ? noteFontBean.getFontIcon() : noteFontBean.getFontIconDark());
            if (this.mDownloadingUrl.contains(downloadUrl)) {
                return;
            }
            FirebaseReportUtils.getInstance().reportNew("remote_font_download_start");
            FirebaseReportUtils.getInstance().reportNew("remote_font_app_download_start");
            this.mDownloadingUrl.add(downloadUrl);
            new DownloaderConfig().setThreadNum(1).setDownloadUrl(downloadUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), "notefont/cover/")).setDownloadListener(new DownloadProgressListener() { // from class: notes.easy.android.mynotes.utils.ResNoteFontManager.4
                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadFailed(Exception exc) {
                    ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                    ResNoteFontManager.this.retryDownloadCover(noteFontBean, i6);
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadSuccess(String str) {
                    ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                    try {
                        File file = new File(str);
                        if (file.exists() && file.length() == 0) {
                            ResNoteFontManager.this.retryDownloadCover(noteFontBean, i6);
                            return;
                        }
                    } catch (Exception e7) {
                        FirebaseCrashlytics.getInstance().recordException(new IllegalArgumentException("checkFile ", e7));
                    }
                    FirebaseReportUtils.getInstance().reportNew("remote_font_download_ok");
                    FirebaseReportUtils.getInstance().reportNew("remote_font_app_download_ok");
                    if (i6 == 1) {
                        noteFontBean.setCoverReady(true);
                    } else {
                        noteFontBean.setCoverDarkReady(true);
                    }
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onStopDownload() {
                    ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onPauseDownload() {
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadTotalSize(long j6) {
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void updateDownloadProgress(long j6, float f6, float f7) {
                }
            }).buildWolf(App.getAppContext()).startDownload();
        }
    }

    public void startDownloadFont(final NoteFontBean noteFontBean, final FontDownloadListener fontDownloadListener) {
        final String downloadUrl = getDownloadUrl(noteFontBean.getFontName());
        if (this.mDownloadingUrl.contains(downloadUrl)) {
            return;
        }
        this.mDownloadingUrl.add(downloadUrl);
        this.mDownloadingProgress.put(noteFontBean.getFontName(), 0);
        if (fontDownloadListener != null) {
            fontDownloadListener.onDownloadStart(noteFontBean);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("startDownloadZip ");
        sb.append(noteFontBean.getFontName());
        FirebaseReportUtils.getInstance().reportNew("remote_font_download_start");
        FirebaseReportUtils.getInstance().reportNew("remote_font_user_download_start");
        new DownloaderConfig().setThreadNum(1).setDownloadUrl(downloadUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), "notefont/font/")).setDownloadListener(new DownloadProgressListener() { // from class: notes.easy.android.mynotes.utils.ResNoteFontManager.6
            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onDownloadFailed(Exception exc) {
                ResNoteFontManager.this.mDownloadingProgress.put(noteFontBean.getFontName(), -1);
                ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                ResNoteFontManager.this.retryDownloadFont(noteFontBean, fontDownloadListener);
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onDownloadSuccess(String str) {
                ResNoteFontManager.this.mDownloadingUrl.remove(downloadUrl);
                ResNoteFontManager.this.mDownloadingProgress.put(noteFontBean.getFontName(), -1);
                try {
                    File file = new File(str);
                    if (file.exists() && file.length() == 0) {
                        ResNoteFontManager.this.retryDownloadFont(noteFontBean, fontDownloadListener);
                        return;
                    }
                    if (ResNoteFontManager.this.getFontFromFile(noteFontBean.getFontName()) == null) {
                        ResNoteFontManager.this.retryDownloadFont(noteFontBean, fontDownloadListener);
                        return;
                    }
                    noteFontBean.setFontReady(true);
                    ResNoteFontManager resNoteFontManager = ResNoteFontManager.this;
                    resNoteFontManager.updateNoteFontConfig(resNoteFontManager.mCurrentConfig);
                    FirebaseReportUtils.getInstance().reportNew("remote_font_download_ok");
                    FirebaseReportUtils.getInstance().reportNew("remote_font_user_download_ok");
                    if (fontDownloadListener != null) {
                        fontDownloadListener.onDownloadSuccess(noteFontBean, str);
                    }
                } catch (Exception e7) {
                    FirebaseCrashlytics.getInstance().recordException(new IllegalArgumentException("checkFile ", e7));
                    ResNoteFontManager.this.retryDownloadFont(noteFontBean, fontDownloadListener);
                }
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void updateDownloadProgress(long j6, float f6, float f7) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("updateDownloadProgress ");
                sb2.append(j6);
                sb2.append(StringUtils.SPACE);
                sb2.append(f6);
                sb2.append(StringUtils.SPACE);
                sb2.append(f7);
                ResNoteFontManager.this.mDownloadingProgress.put(noteFontBean.getFontName(), Integer.valueOf(Math.round(f6)));
                FontDownloadListener fontDownloadListener2 = fontDownloadListener;
                if (fontDownloadListener2 != null) {
                    fontDownloadListener2.updateDownloadProgress(noteFontBean, j6, f6, f7);
                }
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onPauseDownload() {
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onStopDownload() {
            }

            @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
            public void onDownloadTotalSize(long j6) {
            }
        }).buildWolf(App.getAppContext()).startDownload();
    }

    public void updateNoteFontConfig(final NoteFontConfig noteFontConfig) {
        if (noteFontConfig != null) {
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteFontManager.2
                @Override // java.lang.Runnable
                public void run() {
                    ResData resData = new ResData();
                    resData.setId(101);
                    resData.setVersion(noteFontConfig.getVersion());
                    resData.setType(1);
                    resData.setData(new Gson().toJson(noteFontConfig));
                    DbHelper.getInstance().updateResConfig(resData);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyFontAssetToFile(Context context, String str) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        InputStream inputStream = null;
        try {
            try {
                InputStream open = context.getAssets().open(DIRECTORY + str);
                try {
                    File file = new File(context.getFilesDir(), DIRECTORY + str);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    fileOutputStream2 = new FileOutputStream(file);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = open.read(bArr);
                            if (read == -1) {
                                break;
                            } else {
                                fileOutputStream2.write(bArr, 0, read);
                            }
                        }
                        open.close();
                    } catch (Exception unused) {
                        inputStream = open;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fileOutputStream2 == null) {
                            return;
                        }
                        fileOutputStream2.close();
                    } catch (Throwable th) {
                        inputStream = open;
                        fileOutputStream = fileOutputStream2;
                        th = th;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException unused2) {
                                throw th;
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception unused3) {
                    fileOutputStream2 = null;
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = null;
                    inputStream = open;
                }
            } catch (Exception unused4) {
                fileOutputStream2 = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
            fileOutputStream2.close();
        } catch (IOException unused5) {
        }
    }
}
