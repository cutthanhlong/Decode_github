package com.example.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.UserDataStore;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.pubmatic.sdk.video.POBVideoConstant;
import com.zhihu.matisse.internal.entity.Item;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import notes.easy.android.mynotes.EasyNoteManager;
import notes.easy.android.mynotes.async.notes.SaveNoteTask;
import notes.easy.android.mynotes.backup.DriveServiceHelper;
import notes.easy.android.mynotes.backup.LoginHelper;
import notes.easy.android.mynotes.backup.drivesync.BackupHelper;
import notes.easy.android.mynotes.backup.drivesync.BackupRestoreListener;
import notes.easy.android.mynotes.constant.Constants;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.db.DbHelper;
import notes.easy.android.mynotes.firebase.FirebaseReportUtils;
import notes.easy.android.mynotes.firebase.RemoteConfig;
import notes.easy.android.mynotes.helpers.notifications.NotificationsHelper;
import notes.easy.android.mynotes.it.feio.android.omninotes.commons.models.BaseAttachment;
import notes.easy.android.mynotes.models.Attachment;
import notes.easy.android.mynotes.models.Category;
import notes.easy.android.mynotes.models.Note;
import notes.easy.android.mynotes.models.Tag;
import notes.easy.android.mynotes.models.listeners.OnNoteSaved;
import notes.easy.android.mynotes.receiver.DailyReminderReceiver;
import notes.easy.android.mynotes.ui.activities.BackupAndRestoreActivity;
import notes.easy.android.mynotes.ui.activities.BackupAndRestoreActivityPad;
import notes.easy.android.mynotes.ui.activities.EditActivity;
import notes.easy.android.mynotes.ui.activities.EditActivityPad;
import notes.easy.android.mynotes.ui.activities.FaqActivity;
import notes.easy.android.mynotes.ui.activities.FaqActivityPad;
import notes.easy.android.mynotes.ui.activities.MainActivity;
import notes.easy.android.mynotes.ui.activities.ReportIssueActivity;
import notes.easy.android.mynotes.ui.activities.ReportIssueActivityPad;
import notes.easy.android.mynotes.ui.activities.billing.VipDiscountUtil;
import notes.easy.android.mynotes.ui.activities.widget.WidgetCustomizeActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetSelectActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetSelectActivityPad;
import notes.easy.android.mynotes.ui.fragments.DialogLockFragment;
import notes.easy.android.mynotes.ui.fragments.PurchaseRestoreDialogFragment;
import notes.easy.android.mynotes.ui.model.EditContentBean;
import notes.easy.android.mynotes.ui.model.WidgetFirebaseReport;
import notes.easy.android.mynotes.utils.AndroidUpgradeUtils;
import notes.easy.android.mynotes.utils.AppInfoUtils;
import notes.easy.android.mynotes.utils.CategoryComparator;
import notes.easy.android.mynotes.utils.ConstantsBase;
import notes.easy.android.mynotes.utils.DeviceUtils;
import notes.easy.android.mynotes.utils.EventBus.EventUtils;
import notes.easy.android.mynotes.utils.ExtensionsKt;
import notes.easy.android.mynotes.utils.FileUtils;
import notes.easy.android.mynotes.utils.LogRecord;
import notes.easy.android.mynotes.utils.MapUtils;
import notes.easy.android.mynotes.utils.NetworkUtils;
import notes.easy.android.mynotes.utils.ReminderHelper;
import notes.easy.android.mynotes.utils.ResNoteBgManager;
import notes.easy.android.mynotes.utils.ResNoteFontManager;
import notes.easy.android.mynotes.utils.ScreenUtils;
import notes.easy.android.mynotes.utils.WidgetUtils;
import notes.easy.android.mynotes.utils.permission.PermissionUtils;
import notes.easy.android.mynotes.utils.share.ShareUtil;
import notes.easy.android.mynotes.view.DialogAddCategory;
import notes.easy.android.mynotes.view.DialogCancelInterface;
import notes.easy.android.mynotes.view.bubble.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import src.ad.adapters.AdLoader;
import src.ad.adapters.IAdAdapter;

/* loaded from: classes4.dex */
public class EasyNoteManager {
    private static Context sAppContext;
    private static EasyNoteManager sInstance;
    private static SharedPreferences sPrefs;
    private static UserConfig sUserConfig;
    private List<Category> mCategoryList = new ArrayList();

    /* renamed from: notes.easy.android.mynotes.EasyNoteManager$23, reason: invalid class name */
    class AnonymousClass23 implements DialogAddCategory.Positive1Listener<Integer> {
        final /* synthetic */ List val$categoryList;
        final /* synthetic */ Context val$context;
        final /* synthetic */ List val$noteList;

        AnonymousClass23(List list, List list2, Context context) {
            this.val$noteList = list;
            this.val$categoryList = list2;
            this.val$context = context;
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
        public void positiveClick(Integer num) {
            if (num == null || num.intValue() == -1) {
                return;
            }
            final ArrayList arrayList = new ArrayList();
            for (int i6 = 0; i6 < this.val$noteList.size(); i6++) {
                Note note = new Note((Note) this.val$noteList.get(i6));
                note.setCategory((Category) this.val$categoryList.get(num.intValue()));
                arrayList.add(note);
            }
            App.sGlobalExecutor.execute(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.23.1
                @Override // java.lang.Runnable
                public void run() {
                    for (int i7 = 0; i7 < arrayList.size(); i7++) {
                        DbHelper.getInstance().updateNote((Note) arrayList.get(i7), false);
                    }
                    App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.23.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EventUtils.post(102);
                            Toast.makeText(AnonymousClass23.this.val$context, R.string.menu_cate2, 0).show();
                        }
                    });
                }
            });
        }
    }

    private EasyNoteManager() {
        initMainThread();
        initSubThread();
    }

    private void doSyncNote(final Activity activity, final int i6) {
        DriveServiceHelper.reset(LoginHelper.getGoogleSignInAccount(activity));
        BackupHelper.getInstance().sync(activity, new BackupRestoreListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.22
            @Override // notes.easy.android.mynotes.backup.drivesync.BackupRestoreListener
            public void onSyncFinish(final BackupHelper.BackupRestoreResponse backupRestoreResponse) {
                if (i6 != 1) {
                    boolean z6 = false;
                    if (backupRestoreResponse.isAllSuccess()) {
                        Toast.makeText(EasyNoteManager.this.getContext(), R.string.sync_sync_successfully, 0).show();
                        return;
                    }
                    if (backupRestoreResponse.isRecoverAuth()) {
                        if (i6 == 2) {
                            if (EasyNoteManager.this.getUserConfig().getAutoSyncDialogNeedShow()) {
                                EasyNoteManager.this.getUserConfig().setAutoSyncDialogNeedShow(false);
                            }
                        }
                        z6 = true;
                    }
                    if (!z6) {
                        Toast.makeText(EasyNoteManager.this.getContext(), R.string.sync_failed_toast_to_setting, 1).show();
                    } else {
                        FirebaseReportUtils.getInstance().report("sync_fail_show");
                        DialogAddCategory.INSTANCE.showDriveGranted(activity, R.drawable.dialog_sync_in_failed1, R.string.sync_failed_login_invaild_title, R.string.sync_failed_login_invaild_des, R.string.log_in, new DialogCancelInterface() { // from class: notes.easy.android.mynotes.EasyNoteManager.22.1
                            @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                            public void confirmDelete() {
                                FirebaseReportUtils.getInstance().report("sync_fail_retry_click");
                                FirebaseReportUtils.getInstance().report("sync_fail_retry_click_" + BackupHelper.getErrorEventNameByResponse(backupRestoreResponse));
                                if (backupRestoreResponse.getResultIntent() != null) {
                                    LoginHelper.startUserRecoverableAuth(activity, backupRestoreResponse.getResultIntent());
                                } else {
                                    LoginHelper.requestSignIn(activity);
                                }
                                EasyNoteManager.this.getUserConfig().setSyncLastTimeErrorClosed(true);
                            }

                            @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                            public void doNothing() {
                            }
                        });
                    }
                }
            }

            @Override // notes.easy.android.mynotes.backup.drivesync.BackupRestoreListener
            public void onBackupFinish(BackupHelper.BackupRestoreResponse backupRestoreResponse) {
            }

            @Override // notes.easy.android.mynotes.backup.drivesync.BackupRestoreListener
            public void onProgressUpdate(int i7) {
            }

            @Override // notes.easy.android.mynotes.backup.drivesync.BackupRestoreListener
            public void onRestoreFinish(BackupHelper.BackupRestoreResponse backupRestoreResponse) {
            }
        }, i6);
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static EasyNoteManager getInstance() {
        if (sInstance == null) {
            synchronized (EasyNoteManager.class) {
                if (sInstance == null) {
                    sInstance = new EasyNoteManager();
                }
            }
        }
        return sInstance;
    }

    private void gotoNotificationPermissionSetting(final Activity activity) {
        FirebaseReportUtils.getInstance().reportNew("noti_permit_reminder3rd_popup");
        DialogAddCategory.INSTANCE.showNotificationPermissionRequestDialog(activity, R.string.ask_notify_title_3, R.string.promote_notification_des_2, R.string.promote_notification_open_setting, new DialogAddCategory.PositiveListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.27
            @Override // notes.easy.android.mynotes.view.DialogAddCategory.PositiveListener
            public void positiveClick() {
                FirebaseReportUtils.getInstance().reportNew("noti_permit_reminder3rd_click");
                Util.jumpToSettingNotification(activity);
            }
        });
    }

    public static void initApp(String str) {
        if (App.isAppInit) {
            return;
        }
        App.isAppInit = true;
        FirebaseApp.initializeApp(sAppContext);
        App.hasInitFirebase = true;
        App.signStr = AppInfoUtils.INSTANCE.getSignCompat(sAppContext);
        FacebookSdk.sdkInitialize(sAppContext);
        if (!App.userConfig.getFirstOpen()) {
            App.userConfig.setFirstTime(System.currentTimeMillis());
            App.userConfig.setInstallVersion(10489);
            App.userConfig.setFirstOpen(true);
            App.userConfig.setNewRealOpen(true);
        }
        App.initNewUser(App.userConfig);
        RemoteConfig.init();
        App.initAd(sAppContext);
        App.initTikTokSDK(sAppContext);
        new NotificationsHelper(sAppContext).initNotificationChannels();
        ResNoteBgManager.getInstance();
        ResNoteFontManager.getInstance();
        if (TextUtils.equals(str, "App")) {
            return;
        }
        FirebaseReportUtils.getInstance().report("app_init_from", "pr_status", str);
    }

    public static void initAppContext(Context context) {
        if (sAppContext == null) {
            sAppContext = context.getApplicationContext();
            sUserConfig = UserConfig.Companion.newInstance(sAppContext);
            sPrefs = sAppContext.getSharedPreferences(ConstantsBase.PREFS_NAME, 4);
            App.userConfig = sUserConfig;
            App.prefs = sPrefs;
            App.sContext = sAppContext;
        }
    }

    private void initCategory() {
        if (!getUserConfig().getHasInitCate()) {
            String lowerCase = DeviceUtils.getCCODE(getContext()).toLowerCase();
            ArrayList arrayList = new ArrayList();
            if (TextUtils.equals("us", lowerCase)) {
                arrayList.add(getContext().getResources().getString(R.string.tag_personal));
                arrayList.add(getContext().getResources().getString(R.string.tag_shopping));
            } else if (TextUtils.equals("de", lowerCase)) {
                arrayList.add(getContext().getResources().getString(R.string.tag_School));
                arrayList.add(getContext().getResources().getString(R.string.tag_shopping));
            } else if (TextUtils.equals("tw", lowerCase)) {
                arrayList.add(getContext().getResources().getString(R.string.tag_personal));
                arrayList.add(getContext().getResources().getString(R.string.tag_learn));
            } else if (TextUtils.equals(POBVideoConstant.ERROR_TRACKER_KEY_BITRATE, lowerCase)) {
                arrayList.add(getContext().getResources().getString(R.string.tag_School));
                arrayList.add(getContext().getResources().getString(R.string.tag_shopping));
            } else if (TextUtils.equals("jp", lowerCase)) {
                arrayList.add(getContext().getResources().getString(R.string.tag_personal));
                arrayList.add(getContext().getResources().getString(R.string.tag_shopping));
            } else if (TextUtils.equals("cl", lowerCase)) {
                arrayList.add(getContext().getResources().getString(R.string.tag_learn));
                arrayList.add(getContext().getResources().getString(R.string.tag_work));
            } else {
                arrayList.add(getContext().getResources().getString(R.string.cate_home));
                arrayList.add(getContext().getResources().getString(R.string.cate_work));
            }
            for (int i6 = 0; i6 < arrayList.size(); i6++) {
                Category category = new Category();
                category.setName((String) arrayList.get(i6));
                category.setDescription(null);
                category.setId(Calendar.getInstance().getTimeInMillis() + (i6 * 10));
                category.setColor(String.valueOf(-8268550));
                DbHelper.getInstance().updateCategory(category);
            }
            getUserConfig().setHasInitCate(true);
        }
        ArrayList<Category> categories = DbHelper.getInstance().getCategories();
        int allCateIndex = getUserConfig().getAllCateIndex();
        this.mCategoryList.add(allCateIndex == -1 ? new Category(Long.valueOf(Constants.DEFAULT_CATE_ID), getContext().getResources().getString(R.string.cate_all), null) : new Category(Long.valueOf(Constants.DEFAULT_CATE_ID), getContext().getResources().getString(R.string.cate_all), String.valueOf(allCateIndex)));
        this.mCategoryList.addAll(categories);
        if (getUserConfig().getHasMoved()) {
            Collections.sort(this.mCategoryList, new CategoryComparator());
        }
        if (getUserConfig().getCategoryBugfix()) {
            return;
        }
        getUserConfig().setCategoryBugfix(true);
        App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.4
            @Override // java.lang.Runnable
            public void run() {
                for (int i7 = 0; i7 < EasyNoteManager.this.mCategoryList.size(); i7++) {
                    Category category2 = (Category) EasyNoteManager.this.mCategoryList.get(i7);
                    if (Constants.CATEGORY_HOME.equalsIgnoreCase(category2.getName())) {
                        category2.setName(App.getAppContext().getResources().getString(R.string.cate_home));
                        DbHelper.getInstance().updateCategory(category2);
                    } else if (Constants.CATEGORY_WORK.equalsIgnoreCase(category2.getName())) {
                        category2.setName(App.getAppContext().getResources().getString(R.string.cate_work));
                        DbHelper.getInstance().updateCategory(category2);
                    }
                }
            }
        });
    }

    private void initSortFix() {
        if (getUserConfig().getSortGridBugfix()) {
            return;
        }
        getUserConfig().setSortGridBugfix(true);
        boolean z6 = getPrefs().getBoolean(ConstantsBase.PREF_EXPANDED_VIEW, true);
        getPrefs().edit().putBoolean(ConstantsBase.PREF_EXPANDED_VIEW_ARCHIVE, z6).commit();
        getPrefs().edit().putBoolean(ConstantsBase.PREF_EXPANDED_VIEW_REMINDER, z6).commit();
        getPrefs().edit().putBoolean(ConstantsBase.PREF_EXPANDED_VIEW_FAV, z6).commit();
        getPrefs().edit().putBoolean(ConstantsBase.PREF_EXPANDED_VIEW_TRASH, z6).commit();
        String string = getPrefs().getString(ConstantsBase.PREF_SORTING_COLUMN, DbHelper.KEY_USER_LAST_MODIFICATION);
        getPrefs().edit().putString(ConstantsBase.PREF_SORTING_COLUMN_ARCHIVE, string).commit();
        getPrefs().edit().putString(ConstantsBase.PREF_SORTING_COLUMN_REMINDER, string).commit();
        getPrefs().edit().putString(ConstantsBase.PREF_SORTING_COLUMN_FAV, string).commit();
        getPrefs().edit().putString(ConstantsBase.PREF_SORTING_COLUMN_TRASH, string).commit();
        int i6 = getPrefs().getInt(ConstantsBase.SORT_ACCEND, 0);
        getPrefs().edit().putInt(ConstantsBase.SORT_ACCEND_ARCHIVE, i6).commit();
        getPrefs().edit().putInt(ConstantsBase.SORT_ACCEND_REMINDER, i6).commit();
        getPrefs().edit().putInt(ConstantsBase.SORT_ACCEND_FAV, i6).commit();
        getPrefs().edit().putInt(ConstantsBase.SORT_ACCEND_TRASH, i6).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$duplicateNote$2(OnNoteSaved onNoteSaved, Note note) {
        onNoteSaved.onNoteSaved(note);
        EventUtils.post(103);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$lockNote$0() {
        EventUtils.post(102);
        Toast.makeText(getContext(), R.string.add_success, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$lockNote$1(Note note, Context context) {
        Note note2 = new Note(note);
        note2.setLocked(Boolean.TRUE);
        if (StringUtils.isNotEmpty(getUserConfig().getPatternPassword())) {
            note2.setPattern(getUserConfig().getPatternPassword());
        } else if (StringUtils.isNotEmpty(getUserConfig().getPwdCode())) {
            note2.setLatitude(getUserConfig().getPwdCode());
        }
        DbHelper.getInstance().updateNote(note2, false);
        WidgetUtils.updateWidget(note2, context);
        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.g
            @Override // java.lang.Runnable
            public final void run() {
                EasyNoteManager.this.lambda$lockNote$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Unit lambda$showVipMorePopupMenu$3(View view) {
        view.findViewById(R.id.pop_restore_purchase);
        view.findViewById(R.id.pop_faq);
        view.findViewById(R.id.pop_report_issue);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Unit lambda$showVipMorePopupMenu$4(Context context, View view) {
        if (context == null) {
            return null;
        }
        if (view.getId() == R.id.pop_restore_purchase) {
            FirebaseReportUtils.getInstance().reportNew("iap_more_restore");
            new PurchaseRestoreDialogFragment(context).show();
        } else if (view.getId() == R.id.pop_faq) {
            FirebaseReportUtils.getInstance().reportNew("iap_more_faq");
            getInstance().gotoFaqDetail(context, Constants.INTENT_VALUE_VIP);
        } else if (view.getId() == R.id.pop_report_issue) {
            FirebaseReportUtils.getInstance().reportNew("iap_more_feedback");
            getInstance().gotoReportIssue(context, Constants.INTENT_VALUE_VIP);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startInAppRate$5(ReviewManager reviewManager, Activity activity, Task task) {
        if (task.isSuccessful()) {
            Task<Void> launchReviewFlow = reviewManager.launchReviewFlow(activity, (ReviewInfo) task.getResult());
            launchReviewFlow.addOnCanceledListener(new OnCanceledListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.33
                @Override // com.google.android.gms.tasks.OnCanceledListener
                public void onCanceled() {
                    FirebaseReportUtils.getInstance().report("time_rate_inapp_cancel");
                }
            });
            launchReviewFlow.addOnFailureListener(new OnFailureListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.34
                @Override // com.google.android.gms.tasks.OnFailureListener
                public void onFailure(Exception exc) {
                    FirebaseReportUtils.getInstance().report("time_rate_inapp_fail");
                }
            });
            launchReviewFlow.addOnSuccessListener(new OnSuccessListener<Void>() { // from class: notes.easy.android.mynotes.EasyNoteManager.35
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public void onSuccess(Void r22) {
                    FirebaseReportUtils.getInstance().report("time_rate_inapp_success");
                }
            });
            launchReviewFlow.addOnCompleteListener(new OnCompleteListener<Void>() { // from class: notes.easy.android.mynotes.EasyNoteManager.36
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public void onComplete(Task<Void> task2) {
                    FirebaseReportUtils.getInstance().report("time_rate_inapp_complete");
                }
            });
            return;
        }
        int errorCode = ((ReviewException) task.getException()).getErrorCode();
        FirebaseReportUtils.getInstance().report("time_rate_inapp_error", "pr_reason", errorCode + "");
        Util.jumpToGooglePlay(activity, App.getAppContext().getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPwdDialogShow(final Context context, FragmentManager fragmentManager, final Note note) {
        new DialogLockFragment(context, 1, new DialogLockFragment.OnUnlockStateInterface() { // from class: notes.easy.android.mynotes.EasyNoteManager.19
            @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
            public void setPwdSucceed() {
                EasyNoteManager.this.lockNote(context, note, false);
            }

            @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
            public void unlockSucceed(boolean z6) {
                Toast.makeText(EasyNoteManager.this.getContext(), R.string.unlock_success, 0).show();
            }
        }).show(fragmentManager, "322");
    }

    private void showNotificationPermissionDialog(final Activity activity) {
        FirebaseReportUtils.getInstance().reportNew("noti_permit_reminder1st_popup");
        DialogAddCategory.INSTANCE.showNotificationPermissionRequestDialog(activity, R.string.ask_notify_title_1, R.string.ask_notify_content_1, R.string.allow, new DialogAddCategory.PositiveListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.28
            @Override // notes.easy.android.mynotes.view.DialogAddCategory.PositiveListener
            public void positiveClick() {
                FirebaseReportUtils.getInstance().reportNew("noti_permit_reminder1st_click");
                EasyNoteManager.this.showNotificationPermission(activity);
            }
        });
    }

    public void archiveNote(Note note, boolean z6) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(note);
        archiveNote(arrayList, z6);
    }

    public void arraryPinState(List<Note> list) {
        int i6 = 0;
        for (int i7 = 0; i7 < list.size(); i7++) {
            Note note = list.get(i7);
            if (note.getPinState() == 1) {
                list.remove(note);
                list.add(i6, note);
                i6++;
            }
        }
    }

    public void autoSyncNote(Activity activity, int i6) {
        if (activity == null || BackupHelper.isSyncing) {
            return;
        }
        GoogleSignInAccount googleSignInAccount = LoginHelper.getGoogleSignInAccount(activity);
        if (LoginHelper.isAccountValid(googleSignInAccount)) {
            int autoSyncSwitchState = getUserConfig().getAutoSyncSwitchState();
            boolean isNetworkConnected = NetworkUtils.isNetworkConnected(activity);
            boolean isWiFiConnected = NetworkUtils.isWiFiConnected(activity);
            if ((autoSyncSwitchState != 1 || !isWiFiConnected) && (autoSyncSwitchState != 0 || !isNetworkConnected)) {
                if (autoSyncSwitchState != 2) {
                    FirebaseReportUtils.getInstance().reportNew("autosync_fail_nonetwork");
                }
            } else {
                if (LoginHelper.isDriveAppDataGranted(googleSignInAccount)) {
                    doSyncNote(activity, i6);
                    return;
                }
                FirebaseReportUtils.getInstance().reportNew("sync_click_noDrive_req_show");
                getUserConfig().setSyncLastTimeErrorCode(BackupHelper.RESULT_CODE_PERMISSION_NEED);
                getUserConfig().setSyncLastTimeErrorClosed(false);
                Toast.makeText(getContext(), R.string.sync_failed_toast_to_setting, 1).show();
            }
        }
    }

    public int checkRateUs(Context context, int i6, int i7) {
        long currentTimeMillis = System.currentTimeMillis();
        if (!context.getSharedPreferences(ConstantsBase.PREFS_NAME, 4).getBoolean(Constants.RATE_SHOW, false) && ((i6 == 1 && i7 == 1) || i6 >= 2)) {
            return 1;
        }
        if (i6 >= 3 && NetworkUtils.isNetworkConnected(context) && !App.userConfig.getTimeClickRateNow() && App.userConfig.getTimeRateUsDialogShowNum() == 0 && currentTimeMillis - App.userConfig.getFirstTime() >= Constants.SIX_DAYS && ExtensionsKt.getDialogShowTimeExceeds24Hours(App.userConfig)) {
            return 2;
        }
        if (i6 >= 4 && NetworkUtils.isNetworkConnected(context) && !App.userConfig.getTimeClickRateNow() && App.userConfig.getTimeRateUsDialogShowNum() == 2 && currentTimeMillis - App.userConfig.getTimeRateUsDialogShowTime() >= Constants.ONE_MONTH && ExtensionsKt.getDialogShowTimeExceeds24Hours(App.userConfig)) {
            return 3;
        }
        if (!NetworkUtils.isNetworkConnected(context) || App.userConfig.getTimeClickRateNow() || App.userConfig.getTimeRateUsDialogShowNum() != 3 || currentTimeMillis - App.userConfig.getTimeRateUsDialogShowTime() < 2073600000 || currentTimeMillis - App.userConfig.getFirstTime() < 15552000000L || !ExtensionsKt.getDialogShowTimeExceeds24Hours(App.userConfig)) {
            return (!NetworkUtils.isNetworkConnected(context) || App.userConfig.getTimeClickRateNow() || App.userConfig.getTimeRateUsDialogShowNum() != 4 || currentTimeMillis - App.userConfig.getTimeRateUsDialogShowTime() < 2073600000 || currentTimeMillis - App.userConfig.getFirstTime() < 31536000000L || !ExtensionsKt.getDialogShowTimeExceeds24Hours(App.userConfig)) ? 0 : 5;
        }
        return 4;
    }

    public void deleteCategory(final Category category) {
        int i6 = 0;
        while (true) {
            if (i6 >= this.mCategoryList.size()) {
                break;
            }
            if (this.mCategoryList.get(i6).getId() == category.getId()) {
                this.mCategoryList.remove(i6);
                break;
            }
            i6++;
        }
        final ArrayList arrayList = new ArrayList();
        if (getUserConfig().getHasMoved()) {
            Collections.sort(this.mCategoryList, new CategoryComparator());
            System.currentTimeMillis();
            for (int i7 = 0; i7 < this.mCategoryList.size(); i7++) {
                Category category2 = this.mCategoryList.get(i7);
                category2.setDescription(String.valueOf(i7));
                if (category2.getId() == Constants.DEFAULT_CATE_ID) {
                    getUserConfig().setAllCateIndex(i7);
                }
            }
            arrayList.addAll(getCategoryNoAllList());
        }
        EventUtils.post(105);
        App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.6
            @Override // java.lang.Runnable
            public void run() {
                System.currentTimeMillis();
                DbHelper.getInstance().deleteCategory(category);
                if (!EasyNoteManager.this.getUserConfig().getHasMoved() || arrayList.size() <= 0) {
                    return;
                }
                for (int i8 = 0; i8 < arrayList.size(); i8++) {
                    DbHelper.getInstance().updateCategory((Category) arrayList.get(i8));
                }
            }
        });
    }

    public void deleteNote(Context context, Note note) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(note);
        deleteNote(context, arrayList);
    }

    public void deleteNoteDialog(final Context context, final Note note) {
        DialogAddCategory.INSTANCE.showOneTitleDialog(context, R.string.notes_delete_single_forever, R.string.yes, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.EasyNoteManager.12
            @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
            public void positiveClick(String str) {
                EasyNoteManager.this.deleteNote(context, note);
                Toast.makeText(context, R.string.note_deleted, 0).show();
            }
        }, null);
    }

    public void duplicateNote(Context context, Note note, final OnNoteSaved onNoteSaved) {
        long currentTimeMillis = System.currentTimeMillis();
        Note note2 = new Note(note);
        note2.setTitle("[" + context.getResources().getString(R.string.copy) + "]" + note2.getTitle());
        note2.set_id(Long.valueOf(currentTimeMillis));
        note2.setBgId(note.getBgId());
        note2.setStickyColor(note.getStickyColor());
        note2.setStickyType(note.getStickyType());
        note2.setArchived(Boolean.FALSE);
        note2.setCalendarCreate(0);
        note2.setIsPined(0);
        ArrayList arrayList = new ArrayList();
        for (int i6 = 0; i6 < note.getAttachmentsList().size(); i6++) {
            Attachment attachment = new Attachment(note.getAttachmentsList().get(i6));
            File file = new File(attachment.getUri().getPath());
            String name = file.getName();
            File file2 = new File(file.getParent(), currentTimeMillis + "_" + i6 + "_copy" + name.substring(name.lastIndexOf(".")));
            FileUtils.copyFile(file, file2);
            attachment.setUri(Uri.fromFile(file2));
            attachment.setId(Long.valueOf(((long) i6) + currentTimeMillis));
            arrayList.add(attachment);
        }
        note2.setAttachmentsList((List<? extends BaseAttachment>) arrayList);
        note2.setAttachmentsListOld(new ArrayList());
        new SaveNoteTask(new OnNoteSaved() { // from class: notes.easy.android.mynotes.f
            @Override // notes.easy.android.mynotes.models.listeners.OnNoteSaved
            public final void onNoteSaved(Note note3) {
                EasyNoteManager.lambda$duplicateNote$2(OnNoteSaved.this, note3);
            }
        }, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, note2);
    }

    public void export(final Activity activity, final Note note) {
        if (AndroidUpgradeUtils.isNeedCheckStoragePerminssion()) {
            PermissionUtils.requestPermission(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 100, new PermissionUtils.PermissionListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.20
                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                public void onGranted(boolean z6) {
                    BackupHelper.getInstance().singleExport(activity, note, null);
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                public void onDenied() {
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                public void onRequest() {
                }
            });
        } else {
            BackupHelper.getInstance().singleExport(activity, note, null);
        }
    }

    public IAdAdapter getAdmobAd(Activity activity, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ab_banner_h");
        arrayList.add("ab_banner_m");
        arrayList.add("ab_banner");
        return AdLoader.getAllTopAdByScenes(activity, arrayList, str);
    }

    public List<Category> getCategoryList() {
        ArrayList arrayList = new ArrayList();
        for (int i6 = 0; i6 < this.mCategoryList.size(); i6++) {
            arrayList.add(this.mCategoryList.get(i6).copy());
        }
        return arrayList;
    }

    public List<Category> getCategoryNoAllList() {
        ArrayList arrayList = new ArrayList();
        for (int i6 = 0; i6 < this.mCategoryList.size(); i6++) {
            if (this.mCategoryList.get(i6).getId() != Constants.DEFAULT_CATE_ID) {
                arrayList.add(this.mCategoryList.get(i6).copy());
            }
        }
        return arrayList;
    }

    public Context getContext() {
        return App.sContext == null ? sAppContext : App.getAppContext();
    }

    public Locale getCurrentLocale(Context context) {
        return ScreenUtils.getInstance(context).getSelectLanguage() == 0 ? ScreenUtils.getSystemLocale() : Constants.LANGUAGE.get(ScreenUtils.getInstance(context).getSelectLanguage());
    }

    public IAdAdapter getDTAd(Activity activity, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("dt_media_banner");
        return AdLoader.getAllTopAdByScenes(activity, arrayList, str);
    }

    public int getDefaultDateIndex() {
        if (getUserConfig().getDefaultDateIndex() == -1) {
            updateDefaultDate(getContext());
        }
        return getUserConfig().getDefaultDateIndex();
    }

    public List<Integer> getEditingToolList() {
        String editingToolSorting = App.userConfig.getEditingToolSorting();
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(editingToolSorting)) {
            try {
                List list = (List) new Gson().fromJson(editingToolSorting, new TypeToken<List<Integer>>() { // from class: notes.easy.android.mynotes.EasyNoteManager.32
                }.getType());
                if (list != null && !list.isEmpty()) {
                    arrayList.addAll(list);
                }
            } catch (Exception unused) {
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.addAll(Constants.getEDITING_TOOL_SORTING());
        }
        return arrayList;
    }

    public float getFontScale(Context context) {
        try {
            return Settings.System.getFloat(context.getContentResolver(), "font_scale");
        } catch (Settings.SettingNotFoundException unused) {
            return -1.0f;
        }
    }

    public DefaultItemAnimator getItemAnimator(final RecyclerView recyclerView) {
        return new DefaultItemAnimator() { // from class: notes.easy.android.mynotes.EasyNoteManager.7
            @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public void runPendingAnimations() {
                RecyclerView recyclerView2 = recyclerView;
                if (recyclerView2 != null) {
                    RecyclerView.LayoutManager layoutManager = recyclerView2.getLayoutManager();
                    if (layoutManager instanceof StaggeredGridLayoutManager) {
                        ((StaggeredGridLayoutManager) layoutManager).invalidateSpanAssignments();
                    }
                }
                super.runPendingAnimations();
            }
        };
    }

    public String getItemType(Context context, Item item) {
        if (context == null) {
            return "";
        }
        Uri contentUri = item.getContentUri();
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        if (contentUri == null) {
            return "";
        }
        ContentResolver contentResolver = context.getContentResolver();
        String extensionFromMimeType = singleton.getExtensionFromMimeType(contentResolver.getType(contentUri));
        return FileUtils.getExtension(getPath(contentResolver, contentUri)) + "&" + extensionFromMimeType;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0015, code lost:

        r0 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0021, code lost:

        if (notes.easy.android.mynotes.utils.ScreenUtils.isLargeThan720(r6) != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0011, code lost:

        if (notes.easy.android.mynotes.utils.ScreenUtils.isScreenOriatationLandscap(r6) != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:

        r0 = 4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.recyclerview.widget.RecyclerView.LayoutManager getNoteLayoutManager(android.content.Context r6, boolean r7) {
        /*
            r5 = this;
            r0 = 2
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L2d
            boolean r7 = notes.easy.android.mynotes.utils.ScreenUtils.isPad(r6)
            r3 = 4
            r4 = 3
            if (r7 == 0) goto L17
            boolean r6 = notes.easy.android.mynotes.utils.ScreenUtils.isScreenOriatationLandscap(r6)
            if (r6 == 0) goto L15
        L13:
            r0 = 4
            goto L24
        L15:
            r0 = 3
            goto L24
        L17:
            boolean r7 = notes.easy.android.mynotes.utils.ScreenUtils.isScreenOriatationLandscap(r6)
            if (r7 == 0) goto L24
            boolean r6 = notes.easy.android.mynotes.utils.ScreenUtils.isLargeThan720(r6)
            if (r6 == 0) goto L15
            goto L13
        L24:
            androidx.recyclerview.widget.StaggeredGridLayoutManager r6 = new androidx.recyclerview.widget.StaggeredGridLayoutManager
            r6.<init>(r0, r2)
            r6.setGapStrategy(r1)
            goto L49
        L2d:
            boolean r7 = notes.easy.android.mynotes.utils.ScreenUtils.isPad(r6)
            if (r7 != 0) goto L41
            boolean r7 = notes.easy.android.mynotes.utils.ScreenUtils.isScreenOriatationLandscap(r6)
            if (r7 == 0) goto L3a
            goto L41
        L3a:
            androidx.recyclerview.widget.LinearLayoutManager r7 = new androidx.recyclerview.widget.LinearLayoutManager
            r7.<init>(r6, r2, r1)
            r6 = r7
            goto L49
        L41:
            androidx.recyclerview.widget.StaggeredGridLayoutManager r6 = new androidx.recyclerview.widget.StaggeredGridLayoutManager
            r6.<init>(r0, r2)
            r6.setGapStrategy(r1)
        L49:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.EasyNoteManager.getNoteLayoutManager(android.content.Context, boolean):androidx.recyclerview.widget.RecyclerView$LayoutManager");
    }

    public String getPath(ContentResolver contentResolver, Uri uri) {
        Cursor cursor = null;
        if (uri == null) {
            return null;
        }
        if (!"content".equals(uri.getScheme())) {
            return uri.getPath();
        }
        try {
            Cursor query = contentResolver.query(uri, new String[]{"_data"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndex("_data"));
                        query.close();
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public SharedPreferences getPrefs() {
        SharedPreferences sharedPreferences = App.prefs;
        return sharedPreferences == null ? sPrefs : sharedPreferences;
    }

    public float getScreenScale(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public Class<?> getStartActivityClass(Class<?> cls, Class<?> cls2) {
        return ScreenUtils.isPad(getContext()) ? cls2 : cls;
    }

    public String getSurveyUrlByCountry(String str) {
        return TextUtils.equals(str, "us") ? "https://forms.gle/jvByQeMh2vdFB3Uf8" : TextUtils.equals(str, "kr") ? "https://forms.gle/9Ey4DQtoXuU8nypL8" : TextUtils.equals(str, POBVideoConstant.ERROR_TRACKER_KEY_BITRATE) ? "https://forms.gle/wXvdN8iX4v4EwASa8" : TextUtils.equals(str, "de") ? "https://forms.gle/iqUcrmytWNT16v8BA" : TextUtils.equals(str, "jp") ? "https://forms.gle/aUV3yQurhduAHi9e9" : TextUtils.equals(str, "gb") ? "https://forms.gle/Jp5jKQczqZKxsqYR7" : TextUtils.equals(str, "in") ? "https://forms.gle/eqFz8VnJbHsH64to7" : TextUtils.equals(str, "id") ? "https://forms.gle/jE5pqYcDQmbZzh7EA" : TextUtils.equals(str, "mx") ? "https://forms.gle/RWuvixGbGodMfhLy6" : TextUtils.equals(str, "tw") ? "https://forms.gle/mDh2YYhtmXB5sHQz7" : TextUtils.equals(str, "th") ? "https://forms.gle/7HYhq2VQCgK3yqAm8" : TextUtils.equals(str, UserDataStore.PHONE) ? "https://forms.gle/H9FBVM6yS4qFLkhZA" : TextUtils.equals(str, "ca") ? "https://forms.gle/PvpE3GqHfnd5GwUs6" : TextUtils.equals(str, "fr") ? "https://forms.gle/25whqWD7STqXCQQP9" : TextUtils.equals(str, "pl") ? "https://forms.gle/YMu5bNqjQTE4mtBG7" : TextUtils.equals(str, "vn") ? "https://forms.gle/Mrgm1PZKJcnUfReU6" : "";
    }

    public List<String> getTagList(String str) {
        String[] split;
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str) && (split = str.split(",")) != null && split.length > 0) {
            for (String str2 : split) {
                if (!TextUtils.isEmpty(str2)) {
                    arrayList.add(str2);
                }
            }
        }
        return arrayList;
    }

    public String getTagListString(List<Tag> list) {
        StringBuilder sb = new StringBuilder();
        for (int i6 = 0; i6 < list.size(); i6++) {
            sb.append(list.get(i6).getName());
            sb.append(",");
        }
        return sb.toString();
    }

    public String getTagString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i6 = 0; i6 < list.size(); i6++) {
            sb.append(list.get(i6));
            sb.append(",");
        }
        return sb.toString();
    }

    public UserConfig getUserConfig() {
        UserConfig userConfig = App.userConfig;
        return userConfig == null ? sUserConfig : userConfig;
    }

    public void gotoFaqDetail(Context context, String str) {
        Intent intent = new Intent(context, getInstance().getStartActivityClass(FaqActivity.class, FaqActivityPad.class));
        intent.putExtra("from", str);
        context.startActivity(intent);
    }

    public void gotoReportIssue(Context context, String str) {
        Intent intent = new Intent(context, getInstance().getStartActivityClass(ReportIssueActivity.class, ReportIssueActivityPad.class));
        intent.putExtra("from", str);
        context.startActivity(intent);
    }

    public void initMainThread() {
        initCategory();
        initSortFix();
        updateDefaultDate(getContext());
    }

    public void initSubThread() {
        if (!getUserConfig().getUserLastModifiedBugfix()) {
            getUserConfig().setUserLastModifiedBugfix(true);
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.1
                @Override // java.lang.Runnable
                public void run() {
                    List<Note> notesNoStatus = DbHelper.getInstance().getNotesNoStatus();
                    boolean z6 = false;
                    for (int i6 = 0; i6 < notesNoStatus.size(); i6++) {
                        Note note = notesNoStatus.get(i6);
                        if (note.getUserLastModification() == null || note.getUserLastModification().longValue() == 0) {
                            note.setUserLastModification(note.getLastModification());
                            note.setLastModification(Long.valueOf(note.getLastModification().longValue() + 1));
                            DbHelper.getInstance().updateNote(note, false, false);
                            z6 = true;
                        }
                    }
                    if (z6) {
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EventUtils.post(102);
                            }
                        });
                    }
                }
            });
        }
        if (!getUserConfig().getArchiveBugfix()) {
            getUserConfig().setArchiveBugfix(true);
            App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.2
                @Override // java.lang.Runnable
                public void run() {
                    DbHelper.getInstance().getNotesNoStatus();
                    List<Note> notesArchived = DbHelper.getInstance().getNotesArchived();
                    int i6 = 0;
                    boolean z6 = false;
                    while (i6 < notesArchived.size()) {
                        notesArchived.get(i6).setLastModification(Long.valueOf(notesArchived.get(i6).getLastModification().longValue() + 1));
                        DbHelper.getInstance().updateNote(notesArchived.get(i6), false, false);
                        i6++;
                        z6 = true;
                    }
                    if (z6) {
                        App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EventUtils.post(102);
                            }
                        });
                    }
                }
            });
        }
        App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FileUtils.deleteFile(new File(EasyNoteManager.this.getContext().getFilesDir().getAbsolutePath() + Constants.DIR_REPORT_NOTE));
                } catch (Exception e7) {
                    LogRecord.w("EasyNoteManager", "delete report issue ", e7);
                }
            }
        });
    }

    public boolean isNoteLocked(Note note) {
        boolean booleanValue = note.isLocked().booleanValue();
        if (!booleanValue || !TextUtils.isEmpty(getUserConfig().getPatternPassword()) || !TextUtils.isEmpty(getUserConfig().getPwdCode())) {
            return booleanValue;
        }
        if (note.getLatitude() == null || StringUtils.isEmpty(note.getLatitude().toString())) {
            return false;
        }
        return booleanValue;
    }

    public void isShowSetPwdDialog(final Context context, final FragmentManager fragmentManager, final Note note, final int i6, boolean z6) {
        if (!note.isLocked().booleanValue() || z6) {
            DialogAddCategory.INSTANCE.showLockingNoteDialog(context, DbHelper.getInstance().getNotesWithLock(true).size() <= 0 && i6 != 100, false, new DialogAddCategory.OnLockingInterface() { // from class: notes.easy.android.mynotes.EasyNoteManager.18
                @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                public void clickTryItOnce() {
                    FirebaseReportUtils.getInstance().reportNew("iap_lock_try");
                    int i7 = i6;
                    if (i7 == 1) {
                        EasyNoteManager.this.setPwdDialogShow(context, fragmentManager, note);
                    } else {
                        if (i7 != 2) {
                            return;
                        }
                        EasyNoteManager.this.lockNote(context, note);
                    }
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                public void clickUpgradeVip() {
                    VipDiscountUtil.jumpToVipPage(context, "lock_note");
                }
            });
        }
    }

    public boolean isShowSidebarCalendarRed() {
        return getUserConfig().getSaveNoteFirst() && System.currentTimeMillis() - getUserConfig().getFirstTime() > 864000000 && getUserConfig().isShowSidebarCalendarRed();
    }

    public boolean isShowSidebarHelpCenterRed() {
        return getUserConfig().getCreateNotesNumber() >= 2 && getUserConfig().isShowSidebarHelpCenterRed();
    }

    public boolean isShowSidebarMedalRed() {
        return getUserConfig().getSaveNoteFirst() && !getUserConfig().getSiderMedalRed();
    }

    public void lockNote(Context context, Note note) {
        lockNote(context, note, true);
    }

    public void logAreNotificationsEnabled() {
        boolean areNotificationsEnabled = NotificationManagerCompat.from(getContext()).areNotificationsEnabled();
        if (Build.VERSION.SDK_INT >= 33) {
            FirebaseReportUtils.getInstance().reportNew("notify_android13_status");
            if (areNotificationsEnabled) {
                FirebaseReportUtils.getInstance().reportNew("notify_android13_status_on");
                return;
            } else {
                FirebaseReportUtils.getInstance().reportNew("notify_android13_status_off");
                return;
            }
        }
        FirebaseReportUtils.getInstance().reportNew("notify_android12_status");
        if (areNotificationsEnabled) {
            FirebaseReportUtils.getInstance().reportNew("notify_android12_status_on");
        } else {
            FirebaseReportUtils.getInstance().reportNew("notify_android12_status_off");
        }
    }

    public IAdAdapter parseNativeAd(Activity activity, IAdAdapter iAdAdapter, ViewGroup viewGroup, String str, String str2) {
        View adView;
        if (activity == null || activity.isDestroyed() || (adView = iAdAdapter.getAdView(activity, null)) == null || viewGroup == null) {
            return null;
        }
        viewGroup.removeAllViews();
        viewGroup.addView(adView);
        viewGroup.setVisibility(0);
        getInstance().updateAdViewHeight(activity, viewGroup);
        FirebaseReportUtils.getInstance().showAdReport(str2);
        FirebaseReportUtils.getInstance().showAdAllReport(str2);
        FirebaseReportUtils.getInstance().showAdReportType(str2, String.valueOf(iAdAdapter.getAdSource()));
        AdLoader.get(str, activity).preLoadAd(activity);
        return iAdAdapter;
    }

    public void pinNote(final Note note, final boolean z6) {
        App.sGlobalExecutor.execute(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.13
            @Override // java.lang.Runnable
            public void run() {
                final Note note2 = new Note(note);
                if (z6) {
                    note2.setIsPined(1);
                } else {
                    note2.setIsPined(0);
                }
                DbHelper.getInstance().updateNote(note2, false);
                App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.13.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EventUtils.post(102, note2.get_id());
                    }
                });
            }
        });
    }

    public void popLock(final Context context, FragmentManager fragmentManager, final Note note) {
        String pwdCode = getUserConfig().getPwdCode();
        Objects.requireNonNull(pwdCode);
        if (pwdCode.isEmpty()) {
            String patternPassword = getUserConfig().getPatternPassword();
            Objects.requireNonNull(patternPassword);
            if (patternPassword.isEmpty()) {
                if (App.isVip()) {
                    setPwdDialogShow(context, fragmentManager, note);
                    return;
                } else {
                    isShowSetPwdDialog(context, fragmentManager, note, 1, false);
                    return;
                }
            }
        }
        if (getInstance().isNoteLocked(note)) {
            getInstance().unlockDialog(context, fragmentManager, new DialogLockFragment.OnUnlockInterface() { // from class: notes.easy.android.mynotes.EasyNoteManager.17
                @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockInterface
                public void unlockSucceed(boolean z6) {
                    EasyNoteManager.getInstance().unlockNote(context, note);
                    Toast.makeText(EasyNoteManager.this.getContext(), R.string.unlock_success, 0).show();
                }
            });
        } else if (App.isVip()) {
            lockNote(context, note);
        } else {
            isShowSetPwdDialog(context, fragmentManager, note, 2, false);
        }
    }

    public void requestMediaPermission(final Activity activity, int i6, PermissionUtils.PermissionDirctListener permissionDirctListener) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (permissionDirctListener != null) {
                permissionDirctListener.onPermissionAlreadyGranted();
            }
        } else if (ContextCompat.checkSelfPermission(activity, AndroidUpgradeUtils.getPermissionNameV34("android.permission.READ_MEDIA_VISUAL_USER_SELECTED", "android.permission.READ_MEDIA_IMAGES", "android.permission.WRITE_EXTERNAL_STORAGE")) == 0) {
            if (permissionDirctListener != null) {
                permissionDirctListener.onPermissionAlreadyGranted();
            }
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, AndroidUpgradeUtils.getPermissionNameV34("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_IMAGES", "android.permission.WRITE_EXTERNAL_STORAGE"))) {
            showMediaPermission(activity, i6, permissionDirctListener);
        } else if (App.userConfig.getMediaPermissionRationaleRequested()) {
            DialogAddCategory.INSTANCE.showOneTitleOneContentDialog(activity, R.string.permission_read_media_images_title, R.string.permission_read_media_images_des, R.string.promote_notification_open_setting, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.EasyNoteManager.30
                @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
                public void positiveClick(String str) {
                    Util.jumpToSetting(activity);
                }
            }, null);
        } else {
            showMediaPermission(activity, i6, permissionDirctListener);
        }
    }

    public void requestNotificationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < 33) {
            Util.jumpToSettingNotification(activity);
            return;
        }
        if (ContextCompat.checkSelfPermission(activity, "android.permission.POST_NOTIFICATIONS") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.POST_NOTIFICATIONS")) {
                showNotificationPermission(activity);
                return;
            }
            if (App.userConfig.getInstallVersion() <= 10448) {
                Util.jumpToSettingNotification(activity);
            } else if (App.userConfig.getNotificationPermissionRationaleRequested()) {
                Util.jumpToSettingNotification(activity);
            } else {
                showNotificationPermission(activity);
            }
        }
    }

    public void requestNotificationPermissionPromote(final Activity activity) {
        if (Build.VERSION.SDK_INT < 33) {
            FirebaseReportUtils.getInstance().reportNew("time_android12_notify_start");
            FirebaseReportUtils.getInstance().reportNew("time_android12_notify_show");
            DialogAddCategory.INSTANCE.showNotificationPermissionRequestDialog(activity, R.string.promote_notification_title, R.string.promote_notification_des, R.string.allow, new DialogAddCategory.PositiveListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.26
                @Override // notes.easy.android.mynotes.view.DialogAddCategory.PositiveListener
                public void positiveClick() {
                    FirebaseReportUtils.getInstance().reportNew("time_android12_notify_click");
                    Util.jumpToSettingNotification(activity);
                }
            });
        } else {
            FirebaseReportUtils.getInstance().reportNew("time_android13_notify_start");
            if (ContextCompat.checkSelfPermission(activity, "android.permission.POST_NOTIFICATIONS") != 0) {
                PermissionUtils.requestPermission(activity, new String[]{"android.permission.POST_NOTIFICATIONS"}, 101, new PermissionUtils.PermissionListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.25
                    @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                    public void onDenied() {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.POST_NOTIFICATIONS")) {
                            App.userConfig.setNotificationPermissionRationaleRequested(true);
                        }
                        FirebaseReportUtils.getInstance().reportNew("time_android13_notify_show");
                        DialogAddCategory.INSTANCE.showNotificationPermissionRequestDialog(activity, R.string.promote_notification_title, R.string.promote_notification_des, R.string.allow, new DialogAddCategory.PositiveListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.25.1
                            @Override // notes.easy.android.mynotes.view.DialogAddCategory.PositiveListener
                            public void positiveClick() {
                                FirebaseReportUtils.getInstance().reportNew("time_android13_notify_click");
                                AnonymousClass25 anonymousClass25 = AnonymousClass25.this;
                                EasyNoteManager.this.requestNotificationPermission(activity);
                            }
                        });
                    }

                    @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                    public void onGranted(boolean z6) {
                        EasyNoteManager.getInstance().logAreNotificationsEnabled();
                    }

                    @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                    public void onRequest() {
                    }
                });
            }
        }
    }

    public void requestNotificationPermissionWithDialog(Activity activity) {
        if (Build.VERSION.SDK_INT < 33) {
            gotoNotificationPermissionSetting(activity);
            return;
        }
        if (ContextCompat.checkSelfPermission(activity, "android.permission.POST_NOTIFICATIONS") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.POST_NOTIFICATIONS")) {
                showNotificationPermissionDialog(activity);
                return;
            }
            if (App.userConfig.getInstallVersion() <= 10448) {
                gotoNotificationPermissionSetting(activity);
            } else if (App.userConfig.getNotificationPermissionRationaleRequested()) {
                gotoNotificationPermissionSetting(activity);
            } else {
                FirebaseReportUtils.getInstance().reportNew("noti_permit_reminder1st_popup");
                showNotificationPermissionDialog(activity);
            }
        }
    }

    public void restoreListScrollPosition(RecyclerView recyclerView, int i6) {
        if (recyclerView == null || recyclerView.getLayoutManager() == null || recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() <= i6) {
            return;
        }
        recyclerView.getLayoutManager().scrollToPosition(i6);
    }

    public void shareNoteDialog(Context context, Note note) {
        shareNoteDialog(context, note, null);
    }

    public void showCategoryDialog(Context context, Note note) {
        if (note == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(note);
        showCategoryDialog(context, arrayList);
    }

    public void showMediaPermission(final Activity activity, int i6, final PermissionUtils.PermissionDirctListener permissionDirctListener) {
        if (activity != null) {
            PermissionUtils.requestPermission(activity, AndroidUpgradeUtils.getPermissionArrayV34(new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"}, new String[]{"android.permission.READ_MEDIA_IMAGES"}, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}), i6, new PermissionUtils.PermissionDirctListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.31
                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
                public void onPermissionAlreadyGranted() {
                    PermissionUtils.PermissionDirctListener permissionDirctListener2 = permissionDirctListener;
                    if (permissionDirctListener2 != null) {
                        permissionDirctListener2.onPermissionAlreadyGranted();
                    }
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
                public void onRequestPermissionsResult(String[] strArr, int[] iArr) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, AndroidUpgradeUtils.getPermissionNameV34("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_IMAGES", "android.permission.WRITE_EXTERNAL_STORAGE"))) {
                        App.userConfig.setMediaPermissionRationaleRequested(true);
                    }
                    if (ContextCompat.checkSelfPermission(activity, AndroidUpgradeUtils.getPermissionNameV34("android.permission.READ_MEDIA_VISUAL_USER_SELECTED", "android.permission.READ_MEDIA_IMAGES", "android.permission.WRITE_EXTERNAL_STORAGE")) == 0) {
                        App.userConfig.setMediaPermissionRationaleRequested(false);
                    }
                    PermissionUtils.PermissionDirctListener permissionDirctListener2 = permissionDirctListener;
                    if (permissionDirctListener2 != null) {
                        permissionDirctListener2.onRequestPermissionsResult(strArr, iArr);
                    }
                }
            });
        }
    }

    public void showNotificationPermission(final Activity activity) {
        if (activity != null) {
            PermissionUtils.requestPermission(activity, new String[]{"android.permission.POST_NOTIFICATIONS"}, 102, new PermissionUtils.PermissionListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.29
                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                public void onDenied() {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.POST_NOTIFICATIONS")) {
                        App.userConfig.setNotificationPermissionRationaleRequested(true);
                    }
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                public void onGranted(boolean z6) {
                    EasyNoteManager.getInstance().logAreNotificationsEnabled();
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionListener
                public void onRequest() {
                }
            });
        }
    }

    public void showVipMorePopupMenu(final Context context, View view) {
        if (context == null) {
            return;
        }
        FirebaseReportUtils.getInstance().reportNew("iap_more_click");
        DialogAddCategory.INSTANCE.showVipMorePopupMenu(context, view, new Function1() { // from class: notes.easy.android.mynotes.c
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit lambda$showVipMorePopupMenu$3;
                lambda$showVipMorePopupMenu$3 = EasyNoteManager.lambda$showVipMorePopupMenu$3((View) obj);
                return lambda$showVipMorePopupMenu$3;
            }
        }, new Function1() { // from class: notes.easy.android.mynotes.d
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit lambda$showVipMorePopupMenu$4;
                lambda$showVipMorePopupMenu$4 = EasyNoteManager.lambda$showVipMorePopupMenu$4(context, (View) obj);
                return lambda$showVipMorePopupMenu$4;
            }
        });
    }

    public void startInAppRate(final Activity activity) {
        FirebaseReportUtils.getInstance().report("time_rate_inapp_start");
        final ReviewManager create = ReviewManagerFactory.create(activity);
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener() { // from class: notes.easy.android.mynotes.b
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                EasyNoteManager.this.lambda$startInAppRate$5(create, activity, task);
            }
        });
    }

    public void startWidgetSelectActivity(Context context, Note note, String str) {
        WidgetCustomizeActivity.Companion.setNote(note);
        WidgetFirebaseReport widgetFirebaseReport = new WidgetFirebaseReport();
        widgetFirebaseReport.setHomepage(str);
        widgetFirebaseReport.setHomepageAdd(true);
        widgetFirebaseReport.setHomepageAddOk(true);
        Intent intent = new Intent(context, getStartActivityClass(WidgetSelectActivity.class, WidgetSelectActivityPad.class));
        intent.putExtra(Constants.EXTRA_IS_SIDEBAR, false);
        intent.putExtra(Constants.EXTRA_WIDGET_FIREBASE_REPORT, widgetFirebaseReport);
        context.startActivity(intent);
    }

    public void syncNote(Activity activity, int i6) {
        if (activity == null) {
            return;
        }
        if (BackupHelper.isSyncing) {
            Toast.makeText(getContext(), R.string.syncing_toast, 0).show();
            return;
        }
        if (!NetworkUtils.isNetworkConnected(activity)) {
            Toast.makeText(getContext(), R.string.no_network_error, 0).show();
            FirebaseReportUtils.getInstance().reportNew("sync_fail_nonetwork");
            return;
        }
        GoogleSignInAccount googleSignInAccount = LoginHelper.getGoogleSignInAccount(activity);
        if (!LoginHelper.isAccountValid(googleSignInAccount)) {
            activity.startActivity(new Intent(activity, getInstance().getStartActivityClass(BackupAndRestoreActivity.class, BackupAndRestoreActivityPad.class)).putExtra(Constants.INTENT_KEY_SYNC_LOGIN, true));
            return;
        }
        if (LoginHelper.isDriveAppDataGranted(googleSignInAccount)) {
            doSyncNote(activity, i6);
            return;
        }
        FirebaseReportUtils.getInstance().reportNew("sync_click_noDrive_req_show");
        getUserConfig().setSyncLastTimeErrorCode(BackupHelper.RESULT_CODE_PERMISSION_NEED);
        getUserConfig().setSyncLastTimeErrorClosed(false);
        Toast.makeText(getContext(), R.string.sync_failed_toast_to_setting, 1).show();
    }

    public void syncNotePullDown(final Activity activity) {
        if (activity == null || BackupHelper.isSyncing) {
            return;
        }
        if (!NetworkUtils.isNetworkConnected(activity)) {
            EventUtils.post(112);
            Toast.makeText(getContext(), R.string.no_network_error, 0).show();
            FirebaseReportUtils.getInstance().reportNew("sync_fail_nonetwork");
            return;
        }
        GoogleSignInAccount googleSignInAccount = LoginHelper.getGoogleSignInAccount(activity);
        if (!LoginHelper.isAccountValid(googleSignInAccount)) {
            DialogAddCategory.INSTANCE.showDriveGranted(activity, R.drawable.dialog_sync_to_google, R.string.sync_sign_in_to_sync, R.string.sync_sync_to_google_des, R.string.sync_sign_in, new DialogCancelInterface() { // from class: notes.easy.android.mynotes.EasyNoteManager.21
                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                public void confirmDelete() {
                    activity.startActivity(new Intent(activity, EasyNoteManager.getInstance().getStartActivityClass(BackupAndRestoreActivity.class, BackupAndRestoreActivityPad.class)).putExtra(Constants.INTENT_KEY_SYNC_LOGIN_NO_DIALOG, true));
                }

                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                public void doNothing() {
                }
            });
            EventUtils.post(112);
        } else {
            if (LoginHelper.isDriveAppDataGranted(googleSignInAccount)) {
                doSyncNote(activity, 0);
                return;
            }
            FirebaseReportUtils.getInstance().reportNew("sync_click_noDrive_req_show");
            EventUtils.post(112);
            getUserConfig().setSyncLastTimeErrorCode(BackupHelper.RESULT_CODE_PERMISSION_NEED);
            getUserConfig().setSyncLastTimeErrorClosed(false);
            Toast.makeText(getContext(), R.string.sync_failed_toast_to_setting, 1).show();
        }
    }

    public void trashNote(Note note, boolean z6) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(note);
        trashNote(arrayList, z6);
    }

    public void trashNoteDialog(Context context, final Note note) {
        DialogAddCategory.INSTANCE.showOneTitleDialog(context, R.string.notes_delete_single, R.string.delete, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.EasyNoteManager.10
            @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
            public void positiveClick(String str) {
                EasyNoteManager.this.trashNote(note, true);
            }
        }, null);
    }

    public void unlockDialog(Context context, FragmentManager fragmentManager, final DialogLockFragment.OnUnlockInterface onUnlockInterface) {
        new DialogLockFragment(context, 4, new DialogLockFragment.OnUnlockStateInterface() { // from class: notes.easy.android.mynotes.EasyNoteManager.14
            @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
            public void unlockSucceed(boolean z6) {
                DialogLockFragment.OnUnlockInterface onUnlockInterface2 = onUnlockInterface;
                if (onUnlockInterface2 != null) {
                    onUnlockInterface2.unlockSucceed(z6);
                }
            }

            @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
            public void setPwdSucceed() {
            }
        }).show(fragmentManager, "322");
    }

    public void unlockNote(final Context context, final Note note) {
        App.sGlobalExecutor.execute(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.15
            @Override // java.lang.Runnable
            public void run() {
                Note note2 = new Note(note);
                note2.setLocked(Boolean.FALSE);
                DbHelper.getInstance().updateNote(note2, false);
                WidgetUtils.updateWidget(note2, context);
                App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.15.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EventUtils.post(102);
                    }
                });
            }
        });
    }

    public void updateAdViewHeight(Context context, View view) {
        ViewGroup.LayoutParams layoutParams;
        if (context == null || view == null || (layoutParams = view.getLayoutParams()) == null) {
            return;
        }
        layoutParams.height = ScreenUtils.dpToPx(ScreenUtils.getAdBannerHeight(context));
        view.setLayoutParams(layoutParams);
    }

    public void updateCategory(Category category) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(category);
        updateCategory(arrayList);
    }

    public void updateDefaultDate(Context context) {
        Locale currentLocale = getCurrentLocale(context);
        if (getUserConfig().getDefaultDateIndex() == -1) {
            getUserConfig().setDefaultDateIndex(0);
            if (currentLocale != null) {
                if (currentLocale.toString().contains("zh") || currentLocale.toString().contains("ko") || currentLocale.toString().contains("ja")) {
                    getUserConfig().setDefaultDateIndex(1);
                    return;
                }
                if (currentLocale.toString().contains("de")) {
                    getUserConfig().setDefaultDateIndex(5);
                } else if (currentLocale.toString().contains("es")) {
                    getUserConfig().setDefaultDateIndex(3);
                } else if (currentLocale.toString().contains("ms")) {
                    getUserConfig().setDefaultDateIndex(7);
                }
            }
        }
    }

    public void lockNote(final Context context, final Note note, boolean z6) {
        if (z6) {
            new DialogLockFragment(context, 25, new AnonymousClass16(note, context)).show();
        } else {
            App.sGlobalExecutor.execute(new Runnable() { // from class: notes.easy.android.mynotes.e
                @Override // java.lang.Runnable
                public final void run() {
                    EasyNoteManager.this.lambda$lockNote$1(note, context);
                }
            });
        }
    }

    public void shareNoteDialog(final Context context, Note note, final DialogAddCategory.ShareListener shareListener) {
        DialogAddCategory.INSTANCE.showShareDialog(true, true, context, note, new DialogAddCategory.ShareListener() { // from class: notes.easy.android.mynotes.EasyNoteManager.24
            @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
            public void shareAsLongPic(Note note2) {
                MainActivity.deliverNote = note2;
                context.startActivity(new Intent(context, EasyNoteManager.getInstance().getStartActivityClass(EditActivity.class, EditActivityPad.class)).putExtra("from_daily", DailyReminderReceiver.SHARE_LONGPIC));
                DialogAddCategory.ShareListener shareListener2 = shareListener;
                if (shareListener2 != null) {
                    shareListener2.shareAsLongPic(note2);
                }
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
            public void shareAsPdf(Note note2) {
                if (App.isVip()) {
                    MainActivity.deliverNote = note2;
                    context.startActivity(new Intent(context, EasyNoteManager.getInstance().getStartActivityClass(EditActivity.class, EditActivityPad.class)).putExtra("from_daily", DailyReminderReceiver.SHARE_PDF));
                } else {
                    VipDiscountUtil.jumpToVipPage(context, "pdf");
                }
                DialogAddCategory.ShareListener shareListener2 = shareListener;
                if (shareListener2 != null) {
                    shareListener2.shareAsPdf(note2);
                }
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
            public void shareOnlyImg(Note note2) {
                ShareUtil.shareOnlyPic(note2, context);
                DialogAddCategory.ShareListener shareListener2 = shareListener;
                if (shareListener2 != null) {
                    shareListener2.shareOnlyImg(note2);
                }
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
            public void shareOnlyText(Note note2) {
                if (note2.getNewData() == null || note2.getNewData().isEmpty()) {
                    String title = note2.getTitle();
                    StringBuilder sb = new StringBuilder();
                    if (!TextUtils.isEmpty(note2.getContent())) {
                        if (note2.getContent().contains(Constants.GAP_BASE)) {
                            for (String str : note2.getContent().split(Constants.GAP_BASE)) {
                                sb.append(str);
                                sb.append("\n");
                            }
                        } else {
                            sb.append(note2.getContent());
                        }
                    }
                    String sb2 = sb.toString();
                    if (sb2.contains("[x]")) {
                        sb2 = sb2.replace("[x]", "");
                    }
                    if (sb2.contains("[ ]")) {
                        sb2 = sb2.replace("[ ]", "");
                    }
                    String str2 = title + System.getProperty("line.separator") + sb2;
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.SEND");
                    intent.setType(HTTP.PLAIN_TEXT_TYPE);
                    intent.putExtra("android.intent.extra.SUBJECT", title);
                    intent.putExtra("android.intent.extra.TEXT", str2);
                    Context context2 = context;
                    context2.startActivity(Intent.createChooser(intent, context2.getResources().getString(R.string.share)));
                } else {
                    String title2 = note2.getTitle();
                    Gson gson = new Gson();
                    StringBuffer stringBuffer = new StringBuffer();
                    Iterator<JsonElement> it2 = new JsonParser().parse(note2.getNewData()).getAsJsonArray().iterator();
                    while (it2.hasNext()) {
                        EditContentBean editContentBean = (EditContentBean) gson.fromJson(it2.next(), EditContentBean.class);
                        if (editContentBean.getViewType() < 4) {
                            stringBuffer.append(editContentBean.getContent());
                            stringBuffer.append("\n");
                        }
                    }
                    String str3 = title2 + System.getProperty("line.separator") + stringBuffer.toString();
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.SEND");
                    intent2.setType(HTTP.PLAIN_TEXT_TYPE);
                    intent2.putExtra("android.intent.extra.SUBJECT", title2);
                    intent2.putExtra("android.intent.extra.TEXT", str3);
                    Context context3 = context;
                    context3.startActivity(Intent.createChooser(intent2, context3.getResources().getString(R.string.share)));
                }
                DialogAddCategory.ShareListener shareListener2 = shareListener;
                if (shareListener2 != null) {
                    shareListener2.shareOnlyText(note2);
                }
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
            public void shareRecorings(Note note2) {
                ShareUtil.shareRecordings(note2, context);
                DialogAddCategory.ShareListener shareListener2 = shareListener;
                if (shareListener2 != null) {
                    shareListener2.shareRecorings(note2);
                }
            }
        });
    }

    public void archiveNote(final List<Note> list, final boolean z6) {
        App.sGlobalExecutor.execute(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.8
            @Override // java.lang.Runnable
            public void run() {
                for (int i6 = 0; i6 < list.size(); i6++) {
                    Note note = new Note((Note) list.get(i6));
                    if (z6) {
                        note.setCategory(new Category().makeDefaultCategory());
                    }
                    DbHelper.getInstance().archiveNote(note, z6);
                }
                App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EventUtils.post(102);
                    }
                });
            }
        });
    }

    public void deleteNote(final Context context, final List<Note> list) {
        App.sGlobalExecutor.execute(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.11
            @Override // java.lang.Runnable
            public void run() {
                for (int i6 = 0; i6 < list.size(); i6++) {
                    Note note = new Note((Note) list.get(i6));
                    ReminderHelper.removeReminder(App.getAppContext(), note);
                    DbHelper.getInstance().deleteNote(note);
                    MapUtils.deleteMap(context, note);
                }
                App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EventUtils.post(102);
                    }
                });
            }
        });
    }

    public void showCategoryDialog(Context context, List<Note> list) {
        Category category;
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        List<Category> categoryList = getCategoryList();
        int i6 = 0;
        while (true) {
            if (i6 >= categoryList.size()) {
                category = null;
                break;
            }
            category = categoryList.get(i6);
            if (category.getId() == Constants.DEFAULT_CATE_ID) {
                categoryList.remove(category);
                break;
            }
            i6++;
        }
        if (category != null) {
            categoryList.add(0, category);
        }
        for (int i7 = 0; i7 < categoryList.size(); i7++) {
            arrayList.add(categoryList.get(i7).getName());
        }
        DialogAddCategory.INSTANCE.showListItemSingleChoiceDialog(context, context.getResources().getString(R.string.moveto), context.getResources().getString(R.string.ok), context.getResources().getString(R.string.cancel), arrayList, -1, new AnonymousClass23(list, categoryList, context), (DialogAddCategory.Negative1Listener<Integer>) null);
    }

    public void trashNote(final List<Note> list, final boolean z6) {
        App.sGlobalExecutor.execute(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.9
            @Override // java.lang.Runnable
            public void run() {
                for (int i6 = 0; i6 < list.size(); i6++) {
                    Note note = new Note((Note) list.get(i6));
                    if (z6) {
                        ReminderHelper.removeReminder(App.getAppContext(), note);
                        note.setCategory(new Category().makeDefaultCategory());
                    } else {
                        ReminderHelper.addReminder(App.getAppContext(), note);
                    }
                    DbHelper.getInstance().trashNote(note, z6);
                }
                App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EventUtils.post(102);
                    }
                });
            }
        });
    }

    public void updateCategory(List<Category> list) {
        boolean z6;
        if (list == null || list.size() == 0) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        for (int i6 = 0; i6 < list.size(); i6++) {
            Category category = list.get(i6);
            int i7 = 0;
            while (true) {
                if (i7 >= this.mCategoryList.size()) {
                    z6 = true;
                    break;
                }
                Category category2 = this.mCategoryList.get(i7);
                if (category2.getId() == category.getId()) {
                    category2.copy(category);
                    if (category.getId() != Constants.DEFAULT_CATE_ID) {
                        arrayList.add(category);
                    }
                    z6 = false;
                } else {
                    i7++;
                }
            }
            if (z6) {
                arrayList.add(category);
                this.mCategoryList.add(category);
            }
        }
        if (getUserConfig().getHasMoved()) {
            Collections.sort(this.mCategoryList, new CategoryComparator());
        }
        EventUtils.post(105);
        App.executeOnGlobalExecutor(new Runnable() { // from class: notes.easy.android.mynotes.EasyNoteManager.5
            @Override // java.lang.Runnable
            public void run() {
                for (int i8 = 0; i8 < arrayList.size(); i8++) {
                    DbHelper.getInstance().updateCategory((Category) arrayList.get(i8));
                }
            }
        });
    }

    /* renamed from: notes.easy.android.mynotes.EasyNoteManager$16, reason: invalid class name */
    class AnonymousClass16 implements DialogLockFragment.OnUnlockStateInterface {
        final /* synthetic */ Context val$context;
        final /* synthetic */ Note val$note;

        AnonymousClass16(Note note, Context context) {
            this.val$note = note;
            this.val$context = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unlockSucceed$0() {
            EventUtils.post(102);
            Toast.makeText(EasyNoteManager.this.getContext(), R.string.add_success, 0).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unlockSucceed$1(Note note, Context context) {
            Note note2 = new Note(note);
            note2.setLocked(Boolean.TRUE);
            if (StringUtils.isNotEmpty(EasyNoteManager.this.getUserConfig().getPatternPassword())) {
                note2.setPattern(EasyNoteManager.this.getUserConfig().getPatternPassword());
            } else if (StringUtils.isNotEmpty(EasyNoteManager.this.getUserConfig().getPwdCode())) {
                note2.setLatitude(EasyNoteManager.this.getUserConfig().getPwdCode());
            }
            DbHelper.getInstance().updateNote(note2, false);
            WidgetUtils.updateWidget(note2, context);
            App.getsGlobalHandler().post(new Runnable() { // from class: notes.easy.android.mynotes.i
                @Override // java.lang.Runnable
                public final void run() {
                    EasyNoteManager.AnonymousClass16.this.lambda$unlockSucceed$0();
                }
            });
        }

        @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
        public void unlockSucceed(boolean z6) {
            ExecutorService executorService = App.sGlobalExecutor;
            final Note note = this.val$note;
            final Context context = this.val$context;
            executorService.execute(new Runnable() { // from class: notes.easy.android.mynotes.h
                @Override // java.lang.Runnable
                public final void run() {
                    EasyNoteManager.AnonymousClass16.this.lambda$unlockSucceed$1(note, context);
                }
            });
        }

        @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
        public void setPwdSucceed() {
        }
    }

    public void logWelcomeAbTest(String str) {
    }
}