package com.example.myapplication.custom.edittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.constant.Constants;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.constant.stickyBg.ConstantsColorBg;
import notes.easy.android.mynotes.db.DbHelper;
import notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener;
import notes.easy.android.mynotes.downloader.downloader.DownloaderConfig;
import notes.easy.android.mynotes.ui.model.EditBgModel;
import notes.easy.android.mynotes.ui.model.NoteBgBean;
import notes.easy.android.mynotes.ui.model.NoteBgCategory;
import notes.easy.android.mynotes.ui.model.NoteBgConfig;
import notes.easy.android.mynotes.ui.model.ResData;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class ResNoteBgManager {
    public static final int BLACK_WHITE_BG_ID = 40001;
    public static final String DATA_END = ".json";
    public static final int DEFAULT_BG_ID = 40002;
    public static final String DEFAULT_BLACK_BG = "#191818";
    public static final String DEFAULT_BLACK_HOME_BG = "#292828";
    public static final String DEFAULT_LIGHT_BG = "#FCE49E";
    public static final int DEFAULT_LINE_DASH_GAP_LENGTH = 2;
    public static final int DEFAULT_LINE_DASH_LENGTH = 4;
    public static final int DEFAULT_LINE_EDIT_DASH_GAP_LENGTH = 5;
    public static final int DEFAULT_LINE_EDIT_SPACING = 16;
    public static final int DEFAULT_LINE_SPACING = 15;
    public static final int DEFAULT_LINE_WIDTH = 1;
    public static final String DEFAULT_WHITE_BG = "#FFFFFF";
    public static final String DIRECTORY = "notebg/";
    public static final String GRADIENT_BL_TR = "bottomLeft_topRight";
    public static final String GRADIENT_BOTTOM_TOP = "bottom_top";
    public static final String GRADIENT_BR_TL = "bottomRight_topLeft";
    public static final String GRADIENT_LEFT_RIGHT = "left_right";
    public static final String GRADIENT_RIGHT_LEFT = "right_left";
    public static final String GRADIENT_TL_BR = "topLeft_bottomRight";
    public static final String GRADIENT_TOP_BOTTOM = "top_bottom";
    public static final String GRADIENT_TR_BL = "topRight_bottomLeft";
    public static final int ID_CUSTOM = 10;
    public static final String IMAGES_TYPE_CLAMP = "clamp";
    public static final String IMAGES_TYPE_REPEAT = "repeat";
    public static final String IMAGE_POSITION_BOTTOM_CENTER = "bottomCenter";
    public static final String IMAGE_POSITION_BOTTOM_LEFT = "bottomLeft";
    public static final String IMAGE_POSITION_BOTTOM_RIGHT = "bottomRight";
    public static final String IMAGE_POSITION_TOP_CENTER = "topCenter";
    public static final String IMAGE_POSITION_TOP_LEFT = "topLeft";
    public static final String IMAGE_POSITION_TOP_RIGHT = "topRight";
    public static final int IMAGE_POS_BOTTOM_CENTER = 4;
    public static final int IMAGE_POS_BOTTOM_LEFT = 5;
    public static final int IMAGE_POS_BOTTOM_RIGHT = 6;
    public static final int IMAGE_POS_TOP_CENTER = 1;
    public static final int IMAGE_POS_TOP_LEFT = 2;
    public static final int IMAGE_POS_TOP_RIGHT = 3;
    public static final String IMG = "img/";
    public static final int LINE_DASH = 4;
    public static final int LINE_GRID = 3;
    public static final int LINE_LINE = 1;
    public static final int LINE_POINT = 2;
    public static final String LINE_STYLE_DASH = "dash";
    public static final String LINE_STYLE_NORMAL = "normal";
    public static final String LINE_TYPE_GRID = "grid";
    public static final String LINE_TYPE_LINE = "line";
    public static final String LINE_TYPE_POINT = "point";
    public static final String LOCAL_NOTEBG_CONFIG = "notebg/background_config";
    public static final String ROOT_URL_NOTEBG = "https://easynotes-bucket.s3.eu-west-1.amazonaws.com/material/notebg/";
    public static final String TAB_CUSTOM = "customize";
    public static final String TAB_HOT = "hot";
    public static final String TAG = "NoteBgRes";
    public static final String TYPE_BORDER = "border";
    public static final String TYPE_CUSTOM = "custom";
    public static final int TYPE_CUSTOM_DARK_INT = 11;
    public static final int TYPE_CUSTOM_INT = 10;
    public static final String TYPE_GRADIENT = "gradient";
    public static final String TYPE_GRID = "grid";
    public static final String TYPE_LINE = "line";
    public static final String TYPE_PURECOLOR = "pureColor";
    public static final String TYPE_TILE_REPEAT = "tileRepeat";
    public static NoteBgBean sDefaultBg;
    private static ResNoteBgManager sInstance;
    private NoteBgConfig mCurrentConfig = null;
    private final List<String> mDownloadingUrl = Collections.synchronizedList(new ArrayList());
    public final HashMap<Integer, Integer> mDownloadingProgress = new HashMap<>();

    public interface BgDownloadListener {
        void onDownloadFailed(NoteBgBean noteBgBean);

        void onDownloadStart(NoteBgBean noteBgBean);

        void onDownloadSuccess(NoteBgBean noteBgBean, String str);

        void updateDownloadProgress(NoteBgBean noteBgBean, long j6, float f6, float f7);
    }

    private ResNoteBgManager() {
        init();
    }

    public static NoteBgBean getBlackWhiteBg() {
        NoteBgBean noteBgBean = new NoteBgBean();
        NoteBgBean.Background background = new NoteBgBean.Background();
        if (App.userConfig.getThemeState() == 1 || (App.userConfig.getThemeState() == 2 && DeviceUtils.getNightMode(App.getAppContext()) == 33)) {
            background.setGradientColors(new String[]{DEFAULT_BLACK_BG});
            noteBgBean.setDarkBg(true);
            noteBgBean.setThumbnailReady(true);
            noteBgBean.setImagesReady(true);
        } else {
            background.setGradientColors(new String[]{DEFAULT_WHITE_BG});
            noteBgBean.setDarkBg(false);
            noteBgBean.setThumbnailReady(true);
            noteBgBean.setImagesReady(true);
        }
        noteBgBean.setBg(background);
        noteBgBean.setId(BLACK_WHITE_BG_ID);
        return noteBgBean;
    }

    public static NoteBgBean getDefaultBg() {
        if (sDefaultBg == null) {
            sDefaultBg = new NoteBgBean();
            NoteBgBean.Background background = new NoteBgBean.Background();
            background.setGradientColors(new String[]{DEFAULT_LIGHT_BG});
            sDefaultBg.setBg(background);
            sDefaultBg.setThumbnailReady(true);
            sDefaultBg.setImagesReady(true);
            sDefaultBg.setId(DEFAULT_BG_ID);
        }
        return sDefaultBg;
    }

    public static Drawable getDrawableFromFile(String str) {
        Bitmap imageFromFile;
        if (TextUtils.isEmpty(str) || (imageFromFile = getImageFromFile(str)) == null) {
            return null;
        }
        return new BitmapDrawable(imageFromFile);
    }

    public static GradientDrawable getGradientDrawable(String[] strArr, String str) {
        if (strArr == null) {
            return null;
        }
        try {
            if (strArr.length <= 0) {
                return null;
            }
            GradientDrawable gradientDrawable = new GradientDrawable();
            char c7 = 0;
            gradientDrawable.setShape(0);
            if (strArr.length == 1) {
                strArr = new String[]{strArr[0], strArr[0]};
            }
            int length = strArr.length;
            int[] iArr = new int[length];
            for (int i6 = 0; i6 < length; i6++) {
                iArr[i6] = Color.parseColor(strArr[i6]);
            }
            gradientDrawable.setColors(iArr);
            gradientDrawable.setGradientType(0);
            GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
            if (!TextUtils.isEmpty(str)) {
                switch (str.hashCode()) {
                    case -2104165740:
                        if (str.equals(GRADIENT_BL_TR)) {
                            c7 = 6;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case -1835375644:
                        if (str.equals(GRADIENT_LEFT_RIGHT)) {
                            c7 = 2;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case -1682211519:
                        if (str.equals(GRADIENT_BOTTOM_TOP)) {
                            c7 = 1;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case -1387886518:
                        if (str.equals(GRADIENT_RIGHT_LEFT)) {
                            c7 = 3;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case -1133208491:
                        if (str.equals(GRADIENT_TOP_BOTTOM)) {
                            break;
                        }
                        c7 = 65535;
                        break;
                    case -381238802:
                        if (str.equals(GRADIENT_TL_BR)) {
                            c7 = 4;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case -251345334:
                        if (str.equals(GRADIENT_TR_BL)) {
                            c7 = 5;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case 107570030:
                        if (str.equals(GRADIENT_BR_TL)) {
                            c7 = 7;
                            break;
                        }
                        c7 = 65535;
                        break;
                    default:
                        c7 = 65535;
                        break;
                }
                switch (c7) {
                    case 0:
                        orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                        break;
                    case 1:
                        orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                        break;
                    case 2:
                        orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                        break;
                    case 3:
                        orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                        break;
                    case 4:
                        orientation = GradientDrawable.Orientation.TL_BR;
                        break;
                    case 5:
                        orientation = GradientDrawable.Orientation.TR_BL;
                        break;
                    case 6:
                        orientation = GradientDrawable.Orientation.BL_TR;
                        break;
                    case 7:
                        orientation = GradientDrawable.Orientation.BR_TL;
                        break;
                }
            }
            gradientDrawable.setOrientation(orientation);
            return gradientDrawable;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bitmap getImageFromFile(String str) {
        Bitmap bitmap = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            InputStream open = App.getAppContext().getAssets().open("notebg/img/" + str);
            bitmap = BitmapFactory.decodeStream(open);
            open.close();
        } catch (Exception unused) {
        }
        if (bitmap == null) {
            try {
                File file = new File(App.getAppContext().getFilesDir() + File.separator + DIRECTORY + IMG + str);
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

    public static ResNoteBgManager getInstance() {
        if (sInstance == null) {
            synchronized (ResNoteBgManager.class) {
                if (sInstance == null) {
                    sInstance = new ResNoteBgManager();
                }
            }
        }
        return sInstance;
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x000e, code lost:

        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static notes.easy.android.mynotes.ui.model.EditBgModel getStringColorModel(java.lang.String r4, int r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            java.util.List<notes.easy.android.mynotes.ui.model.EditBgModel> r0 = notes.easy.android.mynotes.constant.stickyBg.ConstantsColorBg.BG_All_LIST
            java.util.Iterator r0 = r0.iterator()
        Le:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto La6
            java.lang.Object r2 = r0.next()
            notes.easy.android.mynotes.ui.model.EditBgModel r2 = (notes.easy.android.mynotes.ui.model.EditBgModel) r2
            int r3 = r2.getType()
            if (r3 != r5) goto Le
            r3 = 9
            if (r5 == r3) goto L9b
            r3 = 44
            if (r5 == r3) goto L90
            r3 = 55
            if (r5 == r3) goto L90
            r3 = 66
            if (r5 == r3) goto L90
            r3 = 77
            if (r5 == r3) goto L90
            switch(r5) {
                case 0: goto L85;
                case 1: goto L7a;
                case 2: goto L6f;
                case 3: goto L64;
                case 4: goto L59;
                case 5: goto L4e;
                case 6: goto L43;
                case 7: goto L38;
                default: goto L37;
            }
        L37:
            goto Le
        L38:
            java.lang.String r3 = r2.getColorImgBg()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L43:
            java.lang.String r3 = r2.getColorSpecialImg()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L4e:
            java.lang.String r3 = r2.getColorImgBg()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L59:
            java.lang.String r3 = r2.getColorImgBg()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L64:
            java.lang.String r3 = r2.getColorImgBg()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L6f:
            java.lang.String r3 = r2.getGridImg()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L7a:
            java.lang.String r3 = r2.getColorGradient()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L85:
            java.lang.String r3 = r2.getPureString()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L90:
            java.lang.String r3 = r2.getPureString()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        L9b:
            java.lang.String r3 = r2.getColorImgBg()
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto Le
            return r2
        La6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.utils.ResNoteBgManager.getStringColorModel(java.lang.String, int):notes.easy.android.mynotes.ui.model.EditBgModel");
    }

    private void init() {
        initDbNoteBgConfig();
        UserConfig userConfig = App.userConfig;
        int bgResConfigVersion = userConfig != null ? userConfig.getBgResConfigVersion() : 0;
        if (this.mCurrentConfig == null || bgResConfigVersion == 0 || 10489 != bgResConfigVersion) {
            NoteBgConfig assetNoteBgConfig = getAssetNoteBgConfig();
            if (this.mCurrentConfig == null) {
                this.mCurrentConfig = assetNoteBgConfig;
                updateNoteBgConfig(assetNoteBgConfig);
                copyBgToFile();
                markNewRelease();
            } else {
                mergeAssetsBgConfig(assetNoteBgConfig);
            }
            UserConfig userConfig2 = App.userConfig;
            if (userConfig2 != null) {
                userConfig2.setBgResConfigVersion(10489);
            }
        }
    }

    private void markNewRelease() {
        String str = "";
        for (int i6 = 0; i6 < getNewReleaseList().size(); i6++) {
            str = i6 == getNewReleaseList().size() - 1 ? str + getNewReleaseList().get(i6) + "" : str + getNewReleaseList().get(i6) + Constants.SPECIAL_CHARACTOR;
        }
        App.userConfig.setBgResNewNeedShowId(str);
        App.userConfig.setBgResNewDialogHasShowedId(str);
        App.userConfig.setBgResNewNotiHasShowedId(str);
    }

    public static void unzipFolderWithFilter(File file, File file2, String str) {
        ZipInputStream zipInputStream;
        String absolutePath = file2.getAbsolutePath();
        FileOutputStream fileOutputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(file));
            FileOutputStream fileOutputStream2 = null;
            while (true) {
                try {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        FileUtils.closeStream(fileOutputStream2);
                        FileUtils.closeStream(zipInputStream);
                        return;
                    }
                    String name = nextEntry.getName();
                    File file3 = new File(absolutePath, name);
                    String canonicalPath = file3.getCanonicalPath();
                    if (!TextUtils.equals(name, str) && (canonicalPath.startsWith(App.getAppContext().getExternalFilesDir(null).getPath()) || canonicalPath.startsWith(Constants.UPZIP_FOLD_NAME_LOCAL))) {
                        if (!file3.exists()) {
                            file3.getParentFile().mkdirs();
                            file3.createNewFile();
                        }
                        FileOutputStream fileOutputStream3 = new FileOutputStream(file3);
                        try {
                            byte[] bArr = new byte[512];
                            while (true) {
                                int read = zipInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream3.write(bArr, 0, read);
                                fileOutputStream3.flush();
                            }
                            fileOutputStream2 = fileOutputStream3;
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream = fileOutputStream3;
                            FileUtils.closeStream(fileOutputStream);
                            FileUtils.closeStream(zipInputStream);
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = fileOutputStream2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            zipInputStream = null;
        }
    }

    public void checkRemote(Context context) {
        NoteBgConfig noteBgConfig;
        String string = RemoteConfig.getString("note_backgrounds");
        if (!TextUtils.isEmpty(string) && (noteBgConfig = (NoteBgConfig) new Gson().fromJson(string, NoteBgConfig.class)) != null && noteBgConfig.getVersion() > this.mCurrentConfig.getVersion()) {
            List<Integer> appCode = noteBgConfig.getAppCode();
            for (int i6 = 0; i6 < appCode.size(); i6++) {
                if (10489 <= appCode.get(i6).intValue()) {
                    return;
                }
            }
            mergeRemoteBgConfig(noteBgConfig);
        }
        ExecutorUtils.CACHED_THREAD_POOL.execute(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.1
            /* JADX WARN: Removed duplicated region for block: B:38:0x00c8  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x00cc A[SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r10 = this;
                    notes.easy.android.mynotes.utils.ResNoteBgManager r0 = notes.easy.android.mynotes.utils.ResNoteBgManager.this
                    java.util.List r0 = r0.getAllNoteBg()
                    android.content.Context r1 = notes.easy.android.mynotes.App.getAppContext()
                    boolean r1 = notes.easy.android.mynotes.utils.ScreenUtils.isPad(r1)
                    r2 = 0
                    r3 = 0
                    r4 = 0
                L11:
                    int r5 = r0.size()
                    if (r3 >= r5) goto Ld0
                    java.lang.Object r5 = r0.get(r3)
                    notes.easy.android.mynotes.ui.model.NoteBgBean r5 = (notes.easy.android.mynotes.ui.model.NoteBgBean) r5
                    boolean r6 = r5.isThumbnailReady()
                    r7 = 1
                    if (r6 != 0) goto L46
                    java.lang.String r6 = r5.getThumbnail()
                    boolean r6 = android.text.TextUtils.isEmpty(r6)
                    if (r6 != 0) goto L42
                    java.lang.String r6 = r5.getThumbnail()
                    android.graphics.drawable.Drawable r8 = notes.easy.android.mynotes.utils.ResNoteBgManager.getDrawableFromFile(r6)
                    if (r8 == 0) goto L3c
                    r5.setThumbnailReady(r7)
                    goto L45
                L3c:
                    notes.easy.android.mynotes.utils.ResNoteBgManager r8 = notes.easy.android.mynotes.utils.ResNoteBgManager.this
                    r8.startDownloadCover(r5, r6)
                    goto L46
                L42:
                    r5.setThumbnailReady(r7)
                L45:
                    r4 = 1
                L46:
                    boolean r6 = r5.isImagesReady()
                    if (r6 != 0) goto Lcc
                    notes.easy.android.mynotes.ui.model.NoteBgBean$Background r6 = r5.getBg()
                    if (r6 == 0) goto L70
                    notes.easy.android.mynotes.ui.model.NoteBgBean$Background r6 = r5.getBg()
                    java.lang.String r6 = r6.getBgName()
                    boolean r6 = android.text.TextUtils.isEmpty(r6)
                    if (r6 != 0) goto L70
                    notes.easy.android.mynotes.ui.model.NoteBgBean$Background r6 = r5.getBg()
                    java.lang.String r6 = r6.getBgName()
                    android.graphics.drawable.Drawable r6 = notes.easy.android.mynotes.utils.ResNoteBgManager.getDrawableFromFile(r6)
                    if (r6 != 0) goto L70
                    r6 = 0
                    goto L71
                L70:
                    r6 = 1
                L71:
                    if (r1 == 0) goto L9c
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PadImages[] r8 = r5.getPadImages()
                    if (r8 == 0) goto Lc6
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PadImages[] r8 = r5.getPadImages()
                    int r8 = r8.length
                    if (r8 <= 0) goto Lc6
                    r8 = 0
                L81:
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PadImages[] r9 = r5.getPadImages()
                    int r9 = r9.length
                    if (r8 >= r9) goto Lc6
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PadImages[] r9 = r5.getPadImages()
                    r9 = r9[r8]
                    java.lang.String r9 = r9.getImageName()
                    android.graphics.drawable.Drawable r9 = notes.easy.android.mynotes.utils.ResNoteBgManager.getDrawableFromFile(r9)
                    if (r9 != 0) goto L99
                    goto Lc1
                L99:
                    int r8 = r8 + 1
                    goto L81
                L9c:
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PhoneImages[] r8 = r5.getPhoneImages()
                    if (r8 == 0) goto Lc6
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PhoneImages[] r8 = r5.getPhoneImages()
                    int r8 = r8.length
                    if (r8 <= 0) goto Lc6
                    r8 = 0
                Laa:
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PhoneImages[] r9 = r5.getPhoneImages()
                    int r9 = r9.length
                    if (r8 >= r9) goto Lc6
                    notes.easy.android.mynotes.ui.model.NoteBgBean$PhoneImages[] r9 = r5.getPhoneImages()
                    r9 = r9[r8]
                    java.lang.String r9 = r9.getImageName()
                    android.graphics.drawable.Drawable r9 = notes.easy.android.mynotes.utils.ResNoteBgManager.getDrawableFromFile(r9)
                    if (r9 != 0) goto Lc3
                Lc1:
                    r6 = 0
                    goto Lc6
                Lc3:
                    int r8 = r8 + 1
                    goto Laa
                Lc6:
                    if (r6 == 0) goto Lcc
                    r5.setImagesReady(r7)
                    r4 = 1
                Lcc:
                    int r3 = r3 + 1
                    goto L11
                Ld0:
                    if (r4 == 0) goto Lde
                    android.os.Handler r0 = notes.easy.android.mynotes.App.getsGlobalHandler()
                    notes.easy.android.mynotes.utils.ResNoteBgManager$1$1 r1 = new notes.easy.android.mynotes.utils.ResNoteBgManager$1$1
                    r1.<init>()
                    r0.post(r1)
                Lde:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.utils.ResNoteBgManager.AnonymousClass1.run():void");
            }
        });
    }

    public void copyBgToFile() {
        App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (App.userConfig.getNoteBgPreCopyFinished()) {
                        return;
                    }
                    System.currentTimeMillis();
                    boolean isPad = ScreenUtils.isPad(App.getAppContext());
                    List<NoteBgBean> allNoteBg = ResNoteBgManager.getInstance().getAllNoteBg();
                    for (int i6 = 0; i6 < allNoteBg.size(); i6++) {
                        NoteBgBean noteBgBean = allNoteBg.get(i6);
                        if (noteBgBean.getThumbnail() != null && !TextUtils.isEmpty(noteBgBean.getThumbnail())) {
                            ResNoteBgManager.this.copyBgToFile(App.getAppContext(), noteBgBean.getThumbnail());
                        }
                        if (noteBgBean.getBg() != null && !TextUtils.isEmpty(noteBgBean.getBg().getBgName())) {
                            ResNoteBgManager.this.copyBgToFile(App.getAppContext(), noteBgBean.getBg().getBgName());
                        }
                        if (isPad) {
                            if (noteBgBean.getPadImages() != null && noteBgBean.getPadImages().length > 0) {
                                for (int i7 = 0; i7 < noteBgBean.getPadImages().length; i7++) {
                                    ResNoteBgManager.this.copyBgToFile(App.getAppContext(), noteBgBean.getPadImages()[i7].getImageName());
                                }
                            }
                        } else if (noteBgBean.getPhoneImages() != null && noteBgBean.getPhoneImages().length > 0) {
                            for (int i8 = 0; i8 < noteBgBean.getPhoneImages().length; i8++) {
                                ResNoteBgManager.this.copyBgToFile(App.getAppContext(), noteBgBean.getPhoneImages()[i8].getImageName());
                            }
                        }
                        System.currentTimeMillis();
                    }
                    App.userConfig.setNoteBgPreCopyFinished(true);
                    System.currentTimeMillis();
                } catch (Exception e7) {
                }
            }
        });
    }

    public List<NoteBgBean> getAllNoteBg() {
        return this.mCurrentConfig.getBgList() == null ? new ArrayList() : this.mCurrentConfig.getBgList();
    }

    public NoteBgConfig getAssetNoteBgConfig() {
        try {
            return (NoteBgConfig) new Gson().fromJson(FileUtils.readAssetFile("notebg/background_config.json", false), NoteBgConfig.class);
        } catch (Exception unused) {
            return null;
        }
    }

    public String getCoverUrl(NoteBgBean noteBgBean, String str) {
        return ROOT_URL_NOTEBG + noteBgBean.getId() + "/" + str;
    }

    public String getLocalBgFileParentPath() {
        return "notebg/img/";
    }

    public String getLocalBgFilePath(String str) {
        return getLocalBgFileParentPath() + "/" + str;
    }

    public List<Integer> getNewReleaseList() {
        return this.mCurrentConfig.getNewReleaseList() == null ? new ArrayList() : this.mCurrentConfig.getNewReleaseList();
    }

    public NoteBgBean getNoteBg(int i6) {
        return getNoteBg("", 0, i6);
    }

    public List<NoteBgCategory> getNoteBgCategoryList() {
        ArrayList arrayList = new ArrayList();
        if (this.mCurrentConfig.getShowList() == null) {
            return arrayList;
        }
        for (int i6 = 0; i6 < this.mCurrentConfig.getShowList().size(); i6++) {
            arrayList.add(this.mCurrentConfig.getShowList().get(i6));
        }
        return arrayList;
    }

    public int getNoteBgConfigVersion() {
        return this.mCurrentConfig.getVersion();
    }

    public String getPadZipUrl(NoteBgBean noteBgBean) {
        if (TextUtils.isEmpty(noteBgBean.getPackZip())) {
            return ROOT_URL_NOTEBG + noteBgBean.getId() + "/" + noteBgBean.getId() + "_pad.zip";
        }
        return ROOT_URL_NOTEBG + noteBgBean.getId() + "/" + noteBgBean.getPackZip() + "_pad.zip";
    }

    public String getStickyColor(int i6) {
        for (EditBgModel editBgModel : ConstantsColorBg.BG_All_LIST) {
            if (editBgModel.getBgId() == i6) {
                int type = editBgModel.getType();
                if (type != 9) {
                    if (type == 10) {
                        return editBgModel.getPureString();
                    }
                    if (type != 44 && type != 55 && type != 66 && type != 77) {
                        switch (type) {
                            case 0:
                                break;
                            case 1:
                                return editBgModel.getColorGradient();
                            case 2:
                                return editBgModel.getGridImg();
                            case 3:
                            case 4:
                            case 5:
                            case 7:
                                break;
                            case 6:
                                return editBgModel.getColorSpecialImg();
                            default:
                                return "";
                        }
                    }
                    return editBgModel.getPureString();
                }
                return editBgModel.getColorImgBg();
            }
        }
        return "";
    }

    public int getStickyType(int i6) {
        for (EditBgModel editBgModel : ConstantsColorBg.BG_All_LIST) {
            if (editBgModel.getBgId() == i6) {
                return editBgModel.getType();
            }
        }
        return 0;
    }

    public String getZipUrl(NoteBgBean noteBgBean) {
        String str = ScreenUtils.isPad(App.getAppContext()) ? "_pad.zip" : ".zip";
        if (TextUtils.isEmpty(noteBgBean.getPackZip())) {
            return ROOT_URL_NOTEBG + noteBgBean.getId() + "/" + noteBgBean.getId() + str;
        }
        return ROOT_URL_NOTEBG + noteBgBean.getId() + "/" + noteBgBean.getPackZip() + str;
    }

    public boolean hasPadRes(NoteBgBean noteBgBean) {
        if (noteBgBean.getPadImages() != null && noteBgBean.getPadImages().length > 0) {
            for (int i6 = 0; i6 < noteBgBean.getPadImages().length; i6++) {
                if (getDrawableFromFile(noteBgBean.getPadImages()[i6].getImageName()) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void initDbNoteBgConfig() {
        ResData resConfigById = DbHelper.getInstance().getResConfigById(100);
        if (resConfigById != null) {
            try {
                this.mCurrentConfig = (NoteBgConfig) new Gson().fromJson(resConfigById.getData(), NoteBgConfig.class);
            } catch (Exception unused) {
            }
        }
    }

    public void mergeAssetsBgConfig(NoteBgConfig noteBgConfig) {
        boolean z6;
        List<NoteBgBean> allNoteBg = getAllNoteBg();
        List<NoteBgBean> bgList = noteBgConfig.getBgList();
        for (int i6 = 0; i6 < bgList.size(); i6++) {
            NoteBgBean noteBgBean = bgList.get(i6);
            int i7 = 0;
            while (true) {
                if (i7 >= allNoteBg.size()) {
                    z6 = false;
                    break;
                }
                NoteBgBean noteBgBean2 = allNoteBg.get(i7);
                if (noteBgBean.getId() == noteBgBean2.getId()) {
                    if (noteBgBean.getVersion() > noteBgBean2.getVersion()) {
                        noteBgBean2.copy(noteBgBean);
                    }
                    z6 = true;
                } else {
                    i7++;
                }
            }
            if (!z6) {
                allNoteBg.add(noteBgBean);
            }
        }
        updateNoteBgConfig(this.mCurrentConfig);
    }

    public void mergeRemoteBgConfig(NoteBgConfig noteBgConfig) {
        boolean z6;
        this.mCurrentConfig.setVersion(noteBgConfig.getVersion());
        this.mCurrentConfig.setAppCode(noteBgConfig.getAppCode());
        this.mCurrentConfig.setShowList(noteBgConfig.getShowList());
        this.mCurrentConfig.setNewReleaseList(noteBgConfig.getNewReleaseList());
        String str = "";
        for (int i6 = 0; i6 < getNewReleaseList().size(); i6++) {
            str = i6 == getNewReleaseList().size() - 1 ? str + getNewReleaseList().get(i6) + "" : str + getNewReleaseList().get(i6) + Constants.SPECIAL_CHARACTOR;
        }
        App.userConfig.setBgResNewNeedShowId(str);
        List<NoteBgBean> allNoteBg = getAllNoteBg();
        List<NoteBgBean> bgList = noteBgConfig.getBgList();
        for (int i7 = 0; i7 < bgList.size(); i7++) {
            NoteBgBean noteBgBean = bgList.get(i7);
            int i8 = 0;
            while (true) {
                if (i8 >= allNoteBg.size()) {
                    z6 = false;
                    break;
                }
                NoteBgBean noteBgBean2 = allNoteBg.get(i8);
                if (noteBgBean.getId() == noteBgBean2.getId()) {
                    if (noteBgBean.getVersion() > noteBgBean2.getVersion()) {
                        noteBgBean2.copy(noteBgBean);
                    }
                    z6 = true;
                } else {
                    i8++;
                }
            }
            if (!z6) {
                allNoteBg.add(noteBgBean);
            }
        }
        updateNoteBgConfig(this.mCurrentConfig);
    }

    public NoteBgBean newCustomBg(String str, boolean z6) {
        NoteBgBean noteBgBean = new NoteBgBean();
        NoteBgBean.Background background = new NoteBgBean.Background();
        if (z6) {
            background.setGradientColors(new String[]{DEFAULT_BLACK_HOME_BG});
        } else {
            background.setGradientColors(new String[]{DEFAULT_LIGHT_BG});
        }
        noteBgBean.setBg(background);
        NoteBgBean.Custom custom = new NoteBgBean.Custom();
        custom.setCustomBg(str);
        noteBgBean.setCustom(custom);
        noteBgBean.setVip(true);
        noteBgBean.setId(10);
        noteBgBean.setDarkBg(z6);
        return noteBgBean;
    }

    public void retryDownloadCover(final NoteBgBean noteBgBean, String str) {
        if (NetworkUtils.isNetworkConnected(App.getAppContext())) {
            final String coverUrl = getCoverUrl(noteBgBean, str);
            if (this.mDownloadingUrl.contains(coverUrl)) {
                return;
            }
            this.mDownloadingUrl.add(coverUrl);
            new DownloaderConfig().setThreadNum(1).setDownloadUrl(coverUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), getLocalBgFileParentPath())).setDownloadListener(new DownloadProgressListener() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.5
                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadFailed(Exception exc) {
                    ResNoteBgManager.this.mDownloadingUrl.remove(coverUrl);
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadSuccess(String str2) {
                    ResNoteBgManager.this.mDownloadingUrl.remove(coverUrl);
                    try {
                        File file = new File(str2);
                        if (file.exists() && file.length() == 0) {
                            return;
                        }
                    } catch (Exception e7) {
                    }
                    noteBgBean.setThumbnailReady(true);
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onStopDownload() {
                    ResNoteBgManager.this.mDownloadingUrl.remove(coverUrl);
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

    public void retryDownloadPadZip(NoteBgBean noteBgBean, BgDownloadListener bgDownloadListener) {
        String padZipUrl = getPadZipUrl(noteBgBean);
        if (this.mDownloadingUrl.contains(padZipUrl)) {
            return;
        }
        this.mDownloadingUrl.add(padZipUrl);
        if (bgDownloadListener != null) {
            bgDownloadListener.onDownloadStart(noteBgBean);
        }
        new DownloaderConfig().setThreadNum(1).setDownloadUrl(padZipUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), getLocalBgFileParentPath())).setDownloadListener(new AnonymousClass9(bgDownloadListener, noteBgBean, padZipUrl)).buildWolf(App.getAppContext()).startDownload();
    }

    public void retryDownloadZip(NoteBgBean noteBgBean, BgDownloadListener bgDownloadListener) {
        String zipUrl = getZipUrl(noteBgBean);
        if (this.mDownloadingUrl.contains(zipUrl)) {
            return;
        }
        this.mDownloadingUrl.add(zipUrl);
        this.mDownloadingProgress.put(Integer.valueOf(noteBgBean.getId()), 0);
        StringBuilder sb = new StringBuilder();
        sb.append("startDownloadZip ");
        sb.append(noteBgBean.getId());
        new DownloaderConfig().setThreadNum(1).setDownloadUrl(zipUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), getLocalBgFileParentPath())).setDownloadListener(new AnonymousClass7(noteBgBean, bgDownloadListener, zipUrl)).buildWolf(App.getAppContext()).startDownload();
    }

    public boolean showNewReleaseDialog() {
        String bgResNewNeedShowId = App.userConfig.getBgResNewNeedShowId();
        String bgResNewDialogHasShowedId = App.userConfig.getBgResNewDialogHasShowedId();
        if (TextUtils.isEmpty(bgResNewNeedShowId) || TextUtils.equals(bgResNewNeedShowId, bgResNewDialogHasShowedId)) {
            return false;
        }
        try {
            for (String str : bgResNewNeedShowId.split(Constants.SPECIAL_CHARACTOR)) {
                int parseInt = Integer.parseInt(str);
                int i6 = 0;
                while (true) {
                    if (i6 < getAllNoteBg().size()) {
                        NoteBgBean noteBgBean = getAllNoteBg().get(i6);
                        if (noteBgBean.getId() != parseInt) {
                            i6++;
                        } else if (!noteBgBean.isThumbnailReady()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e7) {
            return false;
        }
    }

    public boolean showNewReleaseNoti() {
        String bgResNewNeedShowId = App.userConfig.getBgResNewNeedShowId();
        String bgResNewNotiHasShowedId = App.userConfig.getBgResNewNotiHasShowedId();
        if (TextUtils.isEmpty(bgResNewNeedShowId) || TextUtils.equals(bgResNewNeedShowId, bgResNewNotiHasShowedId)) {
            return false;
        }
        try {
            for (String str : bgResNewNeedShowId.split(Constants.SPECIAL_CHARACTOR)) {
                int parseInt = Integer.parseInt(str);
                int i6 = 0;
                while (true) {
                    if (i6 < getAllNoteBg().size()) {
                        NoteBgBean noteBgBean = getAllNoteBg().get(i6);
                        if (noteBgBean.getId() != parseInt) {
                            i6++;
                        } else if (!noteBgBean.isThumbnailReady()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e7) {
            return false;
        }
    }

    public void startDownloadCover(final NoteBgBean noteBgBean, final String str) {
        if (NetworkUtils.isNetworkConnected(App.getAppContext())) {
            final String coverUrl = getCoverUrl(noteBgBean, str);
            if (this.mDownloadingUrl.contains(coverUrl)) {
                return;
            }
            this.mDownloadingUrl.add(coverUrl);
            new DownloaderConfig().setThreadNum(1).setDownloadUrl(coverUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), getLocalBgFileParentPath())).setDownloadListener(new DownloadProgressListener() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.4
                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadFailed(Exception exc) {
                    ResNoteBgManager.this.mDownloadingUrl.remove(coverUrl);
                    ResNoteBgManager.this.retryDownloadCover(noteBgBean, str);
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onDownloadSuccess(String str2) {
                    ResNoteBgManager.this.mDownloadingUrl.remove(coverUrl);
                    try {
                        File file = new File(str2);
                        if (file.exists() && file.length() == 0) {
                            ResNoteBgManager.this.retryDownloadCover(noteBgBean, str);
                            return;
                        }
                    } catch (Exception e7) {
                    }
                    noteBgBean.setThumbnailReady(true);
                }

                @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
                public void onStopDownload() {
                    ResNoteBgManager.this.mDownloadingUrl.remove(coverUrl);
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

    public void startDownloadPadZip(NoteBgBean noteBgBean, BgDownloadListener bgDownloadListener) {
        if (NetworkUtils.isNetworkConnected(App.getAppContext())) {
            String padZipUrl = getPadZipUrl(noteBgBean);
            if (this.mDownloadingUrl.contains(padZipUrl)) {
                return;
            }
            this.mDownloadingUrl.add(padZipUrl);
            if (bgDownloadListener != null) {
                bgDownloadListener.onDownloadStart(noteBgBean);
            }
            new DownloaderConfig().setThreadNum(1).setDownloadUrl(padZipUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), getLocalBgFileParentPath())).setDownloadListener(new AnonymousClass8(bgDownloadListener, noteBgBean, padZipUrl)).buildWolf(App.getAppContext()).startDownload();
        }
    }

    public void startDownloadZip(NoteBgBean noteBgBean, BgDownloadListener bgDownloadListener) {
        String zipUrl = getZipUrl(noteBgBean);
        if (this.mDownloadingUrl.contains(zipUrl)) {
            return;
        }
        this.mDownloadingUrl.add(zipUrl);
        this.mDownloadingProgress.put(Integer.valueOf(noteBgBean.getId()), 0);
        if (bgDownloadListener != null) {
            bgDownloadListener.onDownloadStart(noteBgBean);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("startDownloadZip ");
        sb.append(noteBgBean.getId());
        new DownloaderConfig().setThreadNum(1).setDownloadUrl(zipUrl).setSaveDir(new File(App.getAppContext().getFilesDir(), getLocalBgFileParentPath())).setDownloadListener(new AnonymousClass6(noteBgBean, bgDownloadListener, zipUrl)).buildWolf(App.getAppContext()).startDownload();
    }

    public void updateNoteBgConfig(final NoteBgConfig noteBgConfig) {
        if (noteBgConfig != null) {
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.2
                @Override // java.lang.Runnable
                public void run() {
                    ResData resData = new ResData();
                    resData.setId(100);
                    resData.setVersion(noteBgConfig.getVersion());
                    resData.setType(1);
                    resData.setData(new Gson().toJson(noteBgConfig));
                    DbHelper.getInstance().updateResConfig(resData);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyBgToFile(Context context, String str) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        InputStream inputStream = null;
        try {
            try {
                InputStream open = context.getAssets().open("notebg/img/" + str);
                try {
                    File file = new File(context.getFilesDir(), "notebg/img/" + str);
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

    public NoteBgBean getNoteBg(String str, int i6, int i7) {
        EditBgModel stringColorModel;
        if (i6 == 10 || i6 == 11) {
            return newCustomBg(str, i6 == 11);
        }
        if (i7 == 0 && (stringColorModel = getStringColorModel(str, i6)) != null) {
            i7 = stringColorModel.getBgId();
        }
        if (i7 == 40001) {
            return getBlackWhiteBg();
        }
        for (int i8 = 0; i8 < getAllNoteBg().size(); i8++) {
            NoteBgBean noteBgBean = getAllNoteBg().get(i8);
            if (noteBgBean.getId() == i7) {
                return noteBgBean;
            }
        }
        return getDefaultBg();
    }

    /* renamed from: notes.easy.android.mynotes.utils.ResNoteBgManager$6, reason: invalid class name */
    class AnonymousClass6 implements DownloadProgressListener {
        final /* synthetic */ BgDownloadListener val$listener;
        final /* synthetic */ NoteBgBean val$noteBgBean;
        final /* synthetic */ String val$url;

        AnonymousClass6(NoteBgBean noteBgBean, BgDownloadListener bgDownloadListener, String str) {
            this.val$noteBgBean = noteBgBean;
            this.val$listener = bgDownloadListener;
            this.val$url = str;
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadFailed(Exception exc) {
            ResNoteBgManager.this.mDownloadingProgress.put(Integer.valueOf(this.val$noteBgBean.getId()), -1);
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
            ResNoteBgManager.this.retryDownloadZip(this.val$noteBgBean, this.val$listener);
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadSuccess(final String str) {
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
            ResNoteBgManager.this.mDownloadingProgress.put(Integer.valueOf(this.val$noteBgBean.getId()), -1);
            try {
                File file = new File(str);
                if (file.exists() && file.length() == 0) {
                    ResNoteBgManager.this.retryDownloadZip(this.val$noteBgBean, this.val$listener);
                    return;
                }
            } catch (Exception e7) {
            }
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.6.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ZipUtils.unzipFolder(new File(str), new File(App.getAppContext().getFilesDir(), ResNoteBgManager.this.getLocalBgFileParentPath()));
                        FileUtils.deleteFile(new File(str));
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.6.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                for (int i6 = 0; i6 < ResNoteBgManager.this.getAllNoteBg().size(); i6++) {
                                    NoteBgBean noteBgBean = ResNoteBgManager.this.getAllNoteBg().get(i6);
                                    if (noteBgBean.getId() == AnonymousClass6.this.val$noteBgBean.getId()) {
                                        noteBgBean.setImagesReady(true);
                                    }
                                }
                                ResNoteBgManager resNoteBgManager = ResNoteBgManager.this;
                                resNoteBgManager.updateNoteBgConfig(resNoteBgManager.mCurrentConfig);
                                if (AnonymousClass6.this.val$listener != null) {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                    anonymousClass6.val$listener.onDownloadSuccess(anonymousClass6.val$noteBgBean, str);
                                }
                            }
                        });
                    } catch (Exception unused) {
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.6.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                ResNoteBgManager.this.retryDownloadZip(anonymousClass6.val$noteBgBean, anonymousClass6.val$listener);
                            }
                        });
                    }
                }
            });
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void updateDownloadProgress(long j6, float f6, float f7) {
            StringBuilder sb = new StringBuilder();
            sb.append("updateDownloadProgress ");
            sb.append(j6);
            sb.append(StringUtils.SPACE);
            sb.append(f6);
            sb.append(StringUtils.SPACE);
            sb.append(f7);
            ResNoteBgManager.this.mDownloadingProgress.put(Integer.valueOf(this.val$noteBgBean.getId()), Integer.valueOf(Math.round(f6)));
            BgDownloadListener bgDownloadListener = this.val$listener;
            if (bgDownloadListener != null) {
                bgDownloadListener.updateDownloadProgress(this.val$noteBgBean, j6, f6, f7);
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
    }

    /* renamed from: notes.easy.android.mynotes.utils.ResNoteBgManager$7, reason: invalid class name */
    class AnonymousClass7 implements DownloadProgressListener {
        final /* synthetic */ BgDownloadListener val$listener;
        final /* synthetic */ NoteBgBean val$noteBgBean;
        final /* synthetic */ String val$url;

        AnonymousClass7(NoteBgBean noteBgBean, BgDownloadListener bgDownloadListener, String str) {
            this.val$noteBgBean = noteBgBean;
            this.val$listener = bgDownloadListener;
            this.val$url = str;
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadFailed(Exception exc) {
            ResNoteBgManager.this.mDownloadingProgress.put(Integer.valueOf(this.val$noteBgBean.getId()), -1);
            BgDownloadListener bgDownloadListener = this.val$listener;
            if (bgDownloadListener != null) {
                bgDownloadListener.onDownloadFailed(this.val$noteBgBean);
            }
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadSuccess(final String str) {
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
            ResNoteBgManager.this.mDownloadingProgress.put(Integer.valueOf(this.val$noteBgBean.getId()), -1);
            try {
                File file = new File(str);
                if (file.exists() && file.length() == 0) {
                    BgDownloadListener bgDownloadListener = this.val$listener;
                    if (bgDownloadListener != null) {
                        bgDownloadListener.onDownloadFailed(this.val$noteBgBean);
                    }
                    return;
                }
            } catch (Exception e7) {
            }
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.7.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ZipUtils.unzipFolder(new File(str), new File(App.getAppContext().getFilesDir(), ResNoteBgManager.this.getLocalBgFileParentPath()));
                        FileUtils.deleteFile(new File(str));
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.7.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                for (int i6 = 0; i6 < ResNoteBgManager.this.getAllNoteBg().size(); i6++) {
                                    NoteBgBean noteBgBean = ResNoteBgManager.this.getAllNoteBg().get(i6);
                                    if (noteBgBean.getId() == AnonymousClass7.this.val$noteBgBean.getId()) {
                                        noteBgBean.setImagesReady(true);
                                    }
                                }
                                ResNoteBgManager resNoteBgManager = ResNoteBgManager.this;
                                resNoteBgManager.updateNoteBgConfig(resNoteBgManager.mCurrentConfig);
                                if (AnonymousClass7.this.val$listener != null) {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                                    anonymousClass7.val$listener.onDownloadSuccess(anonymousClass7.val$noteBgBean, str);
                                }
                            }
                        });
                    } catch (Exception e8) {
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.7.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                                BgDownloadListener bgDownloadListener2 = anonymousClass7.val$listener;
                                if (bgDownloadListener2 != null) {
                                    bgDownloadListener2.onDownloadFailed(anonymousClass7.val$noteBgBean);
                                }
                            }
                        });
                    }
                }
            });
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void updateDownloadProgress(long j6, float f6, float f7) {
            StringBuilder sb = new StringBuilder();
            sb.append("updateDownloadProgress ");
            sb.append(j6);
            sb.append(StringUtils.SPACE);
            sb.append(f6);
            sb.append(StringUtils.SPACE);
            sb.append(f7);
            ResNoteBgManager.this.mDownloadingProgress.put(Integer.valueOf(this.val$noteBgBean.getId()), Integer.valueOf(Math.round(f6)));
            BgDownloadListener bgDownloadListener = this.val$listener;
            if (bgDownloadListener != null) {
                bgDownloadListener.updateDownloadProgress(this.val$noteBgBean, j6, f6, f7);
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
    }

    /* renamed from: notes.easy.android.mynotes.utils.ResNoteBgManager$8, reason: invalid class name */
    class AnonymousClass8 implements DownloadProgressListener {
        final /* synthetic */ BgDownloadListener val$listener;
        final /* synthetic */ NoteBgBean val$noteBgBean;
        final /* synthetic */ String val$url;

        AnonymousClass8(BgDownloadListener bgDownloadListener, NoteBgBean noteBgBean, String str) {
            this.val$listener = bgDownloadListener;
            this.val$noteBgBean = noteBgBean;
            this.val$url = str;
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadFailed(Exception exc) {
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
            ResNoteBgManager.this.retryDownloadPadZip(this.val$noteBgBean, this.val$listener);
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadSuccess(final String str) {
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
            try {
                File file = new File(str);
                if (file.exists() && file.length() == 0) {
                    ResNoteBgManager.this.retryDownloadPadZip(this.val$noteBgBean, this.val$listener);
                    return;
                }
            } catch (Exception unused) {
            }
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.8.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String str2 = "";
                        if (AnonymousClass8.this.val$noteBgBean.getBg() != null && !TextUtils.isEmpty(AnonymousClass8.this.val$noteBgBean.getBg().getBgName())) {
                            str2 = AnonymousClass8.this.val$noteBgBean.getBg().getBgName();
                        }
                        ResNoteBgManager.unzipFolderWithFilter(new File(str), new File(App.getAppContext().getFilesDir(), ResNoteBgManager.this.getLocalBgFileParentPath()), str2);
                        FileUtils.deleteFile(new File(str));
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.8.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                AnonymousClass8 anonymousClass8 = AnonymousClass8.this;
                                BgDownloadListener bgDownloadListener = anonymousClass8.val$listener;
                                if (bgDownloadListener != null) {
                                    bgDownloadListener.onDownloadSuccess(anonymousClass8.val$noteBgBean, str);
                                }
                            }
                        });
                    } catch (Exception unused2) {
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.8.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass8 anonymousClass8 = AnonymousClass8.this;
                                ResNoteBgManager.this.retryDownloadPadZip(anonymousClass8.val$noteBgBean, anonymousClass8.val$listener);
                            }
                        });
                    }
                }
            });
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void updateDownloadProgress(long j6, float f6, float f7) {
            BgDownloadListener bgDownloadListener = this.val$listener;
            if (bgDownloadListener != null) {
                bgDownloadListener.updateDownloadProgress(this.val$noteBgBean, j6, f6, f7);
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
    }

    /* renamed from: notes.easy.android.mynotes.utils.ResNoteBgManager$9, reason: invalid class name */
    class AnonymousClass9 implements DownloadProgressListener {
        final /* synthetic */ BgDownloadListener val$listener;
        final /* synthetic */ NoteBgBean val$noteBgBean;
        final /* synthetic */ String val$url;

        AnonymousClass9(BgDownloadListener bgDownloadListener, NoteBgBean noteBgBean, String str) {
            this.val$listener = bgDownloadListener;
            this.val$noteBgBean = noteBgBean;
            this.val$url = str;
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadFailed(Exception exc) {
            BgDownloadListener bgDownloadListener = this.val$listener;
            if (bgDownloadListener != null) {
                bgDownloadListener.onDownloadFailed(this.val$noteBgBean);
            }
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void onDownloadSuccess(final String str) {
            ResNoteBgManager.this.mDownloadingUrl.remove(this.val$url);
            try {
                File file = new File(str);
                if (file.exists() && file.length() == 0) {
                    BgDownloadListener bgDownloadListener = this.val$listener;
                    if (bgDownloadListener != null) {
                        bgDownloadListener.onDownloadFailed(this.val$noteBgBean);
                    }
                    return;
                }
            } catch (Exception e7) {
            }
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.9.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String str2 = "";
                        if (AnonymousClass9.this.val$noteBgBean.getBg() != null && !TextUtils.isEmpty(AnonymousClass9.this.val$noteBgBean.getBg().getBgName())) {
                            str2 = AnonymousClass9.this.val$noteBgBean.getBg().getBgName();
                        }
                        ResNoteBgManager.unzipFolderWithFilter(new File(str), new File(App.getAppContext().getFilesDir(), ResNoteBgManager.this.getLocalBgFileParentPath()), str2);
                        FileUtils.deleteFile(new File(str));
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.9.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                                BgDownloadListener bgDownloadListener2 = anonymousClass9.val$listener;
                                if (bgDownloadListener2 != null) {
                                    bgDownloadListener2.onDownloadSuccess(anonymousClass9.val$noteBgBean, str);
                                }
                            }
                        });
                    } catch (Exception e8) {
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.utils.ResNoteBgManager.9.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                                BgDownloadListener bgDownloadListener2 = anonymousClass9.val$listener;
                                if (bgDownloadListener2 != null) {
                                    bgDownloadListener2.onDownloadFailed(anonymousClass9.val$noteBgBean);
                                }
                            }
                        });
                    }
                }
            });
        }

        @Override // notes.easy.android.mynotes.downloader.downloader.DownloadProgressListener
        public void updateDownloadProgress(long j6, float f6, float f7) {
            BgDownloadListener bgDownloadListener = this.val$listener;
            if (bgDownloadListener != null) {
                bgDownloadListener.updateDownloadProgress(this.val$noteBgBean, j6, f6, f7);
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
    }
}
