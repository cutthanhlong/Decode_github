package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.exoplayer2.ExoPlayer;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import java.util.List;
import java.util.Objects;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.constant.Constants;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.db.DbHelper;
import notes.easy.android.mynotes.firebase.FirebaseReportUtils;
import notes.easy.android.mynotes.firebase.RemoteConfig;
import notes.easy.android.mynotes.models.Category;
import notes.easy.android.mynotes.models.Note;
import notes.easy.android.mynotes.ui.activities.EditActivity;
import notes.easy.android.mynotes.ui.activities.widget.DesktopSelectNotesListActivity;
import notes.easy.android.mynotes.ui.activities.widget.SidebarSelectNotesListActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetCustomizeActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetSelectActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetSelectActivityPad;
import notes.easy.android.mynotes.ui.fragments.DetailFragment;
import notes.easy.android.mynotes.ui.fragments.DetailFragmentNew;
import notes.easy.android.mynotes.ui.model.NoteBgBean;
import notes.easy.android.mynotes.ui.model.SidebarSelectWidgetEmptyNoteBean;
import notes.easy.android.mynotes.ui.model.WidgetFirebaseReport;
import notes.easy.android.mynotes.utils.BarUtils;
import notes.easy.android.mynotes.utils.ConstantsBase;
import notes.easy.android.mynotes.utils.NetworkUtils;
import notes.easy.android.mynotes.utils.ResNoteBgManager;
import notes.easy.android.mynotes.utils.ScreenUtils;
import notes.easy.android.mynotes.utils.date.DataAnalysisUtilsV2ToV1;
import notes.easy.android.mynotes.view.DialogAddCategory;
import src.ad.adapters.AdLoader;

/* loaded from: classes4.dex */
public class EditActivity extends BaseActivity {
    public static long AD_GAP_TIMES = 0;
    public static long AD_SHOW_DRAW = 0;
    private static boolean isRestore = false;
    public SidebarSelectWidgetEmptyNoteBean bean;
    public long contentSize;
    public Uri editUri;
    private View loading_ad;
    public DetailFragment mDetailFragment;
    public DetailFragmentNew mDetailFragmentNew;
    private FragmentManager mFragmentManager;
    public boolean isFromOUtside = false;
    public boolean createNewNote = false;
    public String editFrom = "";
    public int noteTYpe = -1;
    public int notifyType = 0;
    private boolean isCreate = false;
    private Context mBaseContext = null;
    private long createTime = 0;
    public boolean isShowWidgetDialog = false;
    public String SearchText = "";
    public boolean goAffix = false;
    public boolean isNewEdit = false;

    private Fragment checkFragmentInstance(int i6, Object obj) {
        Fragment findFragmentById = getFragmentManagerInstance().findFragmentById(i6);
        if (findFragmentById == null || !obj.equals(findFragmentById.getClass())) {
            return null;
        }
        return findFragmentById;
    }

    private FragmentManager getFragmentManagerInstance() {
        if (this.mFragmentManager == null) {
            this.mFragmentManager = getSupportFragmentManager();
        }
        return this.mFragmentManager;
    }

    private void initUI() {
        Bundle bundle = new Bundle();
        bundle.putInt("note_type", this.noteTYpe);
        bundle.putString("searchText", this.SearchText);
        try {
            if (MainActivity.deliverNote == null) {
                Note note = new Note();
                Category category = MainActivity.deliverCategory;
                if (category != null) {
                    note.setCategory(category);
                }
                MainActivity.deliverNote = note;
                UserConfig userConfig = this.userConfig;
                if (userConfig != null && userConfig.getNewRealOpen() && !this.userConfig.getDeepClick()) {
                    this.userConfig.setAppDeepLevel("create_click");
                    FirebaseReportUtils.getInstance().setUserPropertyKV("cp_main_depth", this.userConfig.getAppDeepLevel());
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("pr_status", "create_click");
                    FirebaseReportUtils.getInstance().reportNew("user_main_depth", bundle2);
                    this.userConfig.setDeepClick(true);
                }
            }
        } catch (Exception unused) {
        }
        if (MainActivity.deliverNote.getContent().length() == 0) {
            if (this.userConfig.getFirstTime() < 1683216000) {
                this.isNewEdit = false;
            } else {
                List<Note> notes2 = DbHelper.getInstance().getNotes("", true);
                int i6 = 0;
                while (true) {
                    if (i6 >= notes2.size()) {
                        break;
                    }
                    if (notes2.get(i6).getCreation().longValue() == Constants.DEMO_NOTE_ID) {
                        notes2.remove(i6);
                        break;
                    }
                    i6++;
                }
                boolean z6 = notes2.size() > 0;
                for (int i7 = 0; i7 < notes2.size(); i7++) {
                    if (notes2.get(i7).getNewData() == null || notes2.get(i7).getNewData().length() == 0) {
                        z6 = false;
                    }
                }
                this.isNewEdit = z6;
            }
        }
        if (MainActivity.deliverNote.getNewData() != null && MainActivity.deliverNote.getNewData().length() > 1) {
            this.isNewEdit = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("initUI: ");
        sb.append(this.isNewEdit);
        if (!this.isNewEdit) {
            if (this.mDetailFragment == null) {
                this.mDetailFragment = new DetailFragment();
            }
            this.mDetailFragment.setArguments(bundle);
            this.mDetailFragment.setBaseContext(this.mBaseContext);
            getFragmentManagerInstance().beginTransaction().replace(R.id.fragment_container, this.mDetailFragment).commit();
            return;
        }
        if (!MainActivity.openV1) {
            if (this.mDetailFragmentNew == null) {
                this.mDetailFragmentNew = new DetailFragmentNew();
            }
            this.mDetailFragmentNew.setArguments(bundle);
            this.mDetailFragmentNew.setBaseContext(this.mBaseContext);
            getFragmentManagerInstance().beginTransaction().replace(R.id.fragment_container, this.mDetailFragmentNew).commit();
            FirebaseReportUtils.getInstance().reportNew("edit_show_v2");
            return;
        }
        MainActivity.openV1 = false;
        this.isNewEdit = false;
        DataAnalysisUtilsV2ToV1.noteV2ToV1(MainActivity.deliverNote);
        if (this.mDetailFragment == null) {
            this.mDetailFragment = new DetailFragment();
        }
        this.mDetailFragment.setArguments(bundle);
        this.mDetailFragment.setBaseContext(this.mBaseContext);
        getFragmentManagerInstance().beginTransaction().replace(R.id.fragment_container, this.mDetailFragment).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        this.notifyType = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showMessage$1(String str) {
        Toast.makeText(this, str, 0).show();
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mBaseContext = context;
    }

    @Override // android.app.Activity
    public void finish() {
        if (this.isShowWidgetDialog) {
            return;
        }
        super.finish();
        if (MainActivity.mainHasExist || DesktopSelectNotesListActivity.isFinish) {
            return;
        }
        try {
            startActivity(new Intent(this, (Class<?>) MainActivity.class));
        } catch (Exception unused) {
        }
    }

    public boolean getCreateState() {
        return this.isCreate;
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity
    public int getResID() {
        return R.layout.activity_edit;
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity
    protected int getStatusColor() {
        return super.getStatusColor();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity
    public void initView(View view) {
        char c7;
        boolean z6 = false;
        isRestore = false;
        if (!App.isAdOpen() && NetworkUtils.isNetworkConnected(this) && this.userConfig.getShowInterAd() && RemoteConfig.getLong("edit_back_inter_switch") == 0 && !AdLoader.get(Constants.EDIT_SAVE_INTERS, this).hasValidCache()) {
            AdLoader.get(Constants.EDIT_SAVE_INTERS, this).preLoadAd(this);
        }
        this.contentSize = RemoteConfig.getLong("content_size");
        this.createTime = System.currentTimeMillis();
        if (getIntent() != null) {
            this.isFromOUtside = getIntent().getBooleanExtra("outside", false);
            this.editFrom = getIntent().getStringExtra("edit_from");
            this.SearchText = getIntent().getStringExtra("searchText");
            this.noteTYpe = getIntent().getIntExtra("note_type", -1);
            this.notifyType = getIntent().getIntExtra("from_daily", 0);
            this.createNewNote = getIntent().getBooleanExtra("add_note", false);
            this.goAffix = getIntent().getBooleanExtra("goAffix", false);
            if (!this.userConfig.getHasClickNewNote()) {
                this.userConfig.setHasClickNewNote(true);
            }
            if (getIntent().getStringExtra("week") != null) {
                String stringExtra = getIntent().getStringExtra("week");
                switch (stringExtra.hashCode()) {
                    case 1611847625:
                        if (stringExtra.equals(Constants.NOTIFICATION_FRI)) {
                            c7 = 2;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case 1611859602:
                        if (stringExtra.equals(Constants.NOTIFICATION_SAT)) {
                            c7 = 3;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case 1611861168:
                        if (stringExtra.equals(Constants.NOTIFICATION_TUE)) {
                            c7 = 0;
                            break;
                        }
                        c7 = 65535;
                        break;
                    case 1611863554:
                        if (stringExtra.equals(Constants.NOTIFICATION_WED)) {
                            c7 = 1;
                            break;
                        }
                        c7 = 65535;
                        break;
                    default:
                        c7 = 65535;
                        break;
                }
                if (c7 == 1) {
                    int intExtra = getIntent().getIntExtra("daily_report", 0);
                    if (intExtra == 0) {
                        FirebaseReportUtils.getInstance().reportNew("daily_notfi_wed1_click");
                    } else if (intExtra == 1) {
                        FirebaseReportUtils.getInstance().reportNew("daily_notfi_wed2_click");
                    } else if (intExtra == 2) {
                        FirebaseReportUtils.getInstance().reportNew("daily_notfi_wed3_click");
                    } else if (intExtra == 3) {
                        FirebaseReportUtils.getInstance().reportNew("daily_notfi_wed4_click");
                    }
                } else if (c7 == 2) {
                    int intExtra2 = getIntent().getIntExtra("daily_report", 0);
                    if (intExtra2 == 1) {
                        FirebaseReportUtils.getInstance().reportNew("daily_notfi_fri2_click");
                    } else if (intExtra2 == 2) {
                        FirebaseReportUtils.getInstance().reportNew("daily_notfi_fri3_click");
                    }
                }
            }
            if (getIntent().getIntExtra("click_create_note_notification", 0) == 1) {
                FirebaseReportUtils.getInstance().reportNew("first_reminder_click");
            } else if (getIntent().getIntExtra("click_create_note_notification", 0) == 2) {
                FirebaseReportUtils.getInstance().reportNew("second_reminder_click");
            } else if (getIntent().getIntExtra("click_create_note_notification", 0) == -1) {
                FirebaseReportUtils.getInstance().reportNew("clip_noti_click");
            }
            this.bean = (SidebarSelectWidgetEmptyNoteBean) getIntent().getSerializableExtra(SidebarSelectNotesListActivity.EXTRA_SIDEBAR_SELECT_WIDGET_EMPTY_NOTE);
        }
        initUI();
        Bundle bundle = new Bundle();
        String str = this.editFrom;
        if (str != null) {
            bundle.putString("pr_status", str.toLowerCase());
            FirebaseReportUtils.getInstance().logMainFlowNew(this, "v1_edit_page_show", "pr_status", this.editFrom.toLowerCase());
        } else {
            FirebaseReportUtils.getInstance().logMainFlowNew(this, "v1_edit_page_show");
        }
        FirebaseReportUtils.getInstance().reportNew("edit_show", bundle);
        if (ScreenUtils.isScreenOriatationLandscap(this)) {
            FirebaseReportUtils.getInstance().reportNew(" M_create_show_o");
        }
        View findViewById = findViewById(R.id.load_ad);
        this.loading_ad = findViewById;
        if (this.notifyType != 0 && findViewById != null) {
            findViewById.postDelayed(new Runnable() { // from class: w5.o0
                @Override // java.lang.Runnable
                public final void run() {
                    EditActivity.this.lambda$initView$0();
                }
            }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
        AD_SHOW_DRAW = RemoteConfig.getLong("draw_banner_show");
        if (MainActivity.deliverNote.getTitle().contains("[" + getResources().getString(R.string.copy) + "]")) {
            FirebaseReportUtils.getInstance().reportNew("copied_notes_show");
        }
        Note note = MainActivity.deliverNote;
        if (note != null && note.getCreation() != null && MainActivity.deliverNote.getCreation().longValue() == Constants.DEMO_NOTE_ID) {
            z6 = true;
        }
        if (!z6 && !this.userConfig.getFirstEditShow()) {
            this.userConfig.setFirstEditShow(true);
            FirebaseReportUtils.getInstance().logMainFlow(this, "v1_f_main_edit_show");
        }
        FirebaseReportUtils.getInstance().reportNew("M_edit_show");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i6, int i7, Intent intent) {
        super.onActivityResult(i6, i7, intent);
        if (i6 == 333 && intent != null && Objects.equals(intent.getStringExtra(ConstantsBase.GALLERY_TO_DRAW), "GalleryActivity")) {
            this.editUri = (Uri) intent.getParcelableExtra(ConstantsBase.GALLERY_SELECT_DRAW);
        }
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        try {
            if (this.isNewEdit) {
                Fragment checkFragmentInstance = checkFragmentInstance(R.id.fragment_container, DetailFragmentNew.class);
                if (checkFragmentInstance != null) {
                    DetailFragmentNew detailFragmentNew = (DetailFragmentNew) checkFragmentInstance;
                    if (detailFragmentNew.isSearchState()) {
                        detailFragmentNew.resetSearchedText();
                        return;
                    }
                    detailFragmentNew.goBack = true;
                    detailFragmentNew.saveAndExit(detailFragmentNew);
                    if (((DetailFragmentNew) checkFragmentInstance).newCreate) {
                        if (((DetailFragmentNew) checkFragmentInstance).isBackOrSave) {
                            ((DetailFragmentNew) checkFragmentInstance).reportSaveNoteEvent("M_create_success");
                            FirebaseReportUtils.getInstance().reportNew("M_create_success_back");
                            FirebaseReportUtils.getInstance().reportNew("M_create_OK");
                            if (!App.userConfig.getFirstEditOk()) {
                                App.userConfig.setFirstEditOk(true);
                                FirebaseReportUtils.getInstance().logMainFlow(this, "v1_f_main_create_ok");
                            }
                        } else {
                            String stringBuffer = ((DetailFragmentNew) checkFragmentInstance).editAction.toString();
                            Bundle bundle = new Bundle();
                            bundle.putString("key_back", stringBuffer);
                            FirebaseReportUtils.getInstance().reportOnlyNew("M_create_empty_back", bundle);
                            FirebaseReportUtils.getInstance().reportAll("M_create_empty_back", bundle);
                            FirebaseReportUtils.getInstance().reportNew("M_create_empty_back_android");
                        }
                    }
                    ((DetailFragmentNew) checkFragmentInstance).reportSaveNoteEvent("edit_save_all");
                    ((DetailFragmentNew) checkFragmentInstance).reportSaveNoteEvent("edit_save_auto_back");
                    NoteBgBean noteBg = ResNoteBgManager.getInstance().getNoteBg(this.mDetailFragmentNew.noteTmp.getStickyColor(), this.mDetailFragmentNew.noteTmp.getStickyType(), this.mDetailFragmentNew.noteTmp.getBgId());
                    FirebaseReportUtils.getInstance().reportNew("edit_default_color", Constants.THEME_EVENT_KEY, "" + noteBg.getId());
                    return;
                }
                return;
            }
            Fragment checkFragmentInstance2 = checkFragmentInstance(R.id.fragment_container, DetailFragment.class);
            if (checkFragmentInstance2 != null) {
                if (checkFragmentInstance2.isVisible() && ((DetailFragment) checkFragmentInstance2).onBackPressed()) {
                    return;
                }
                DetailFragment detailFragment = (DetailFragment) checkFragmentInstance2;
                detailFragment.goBack = true;
                detailFragment.saveAndExit(detailFragment);
                if (((DetailFragment) checkFragmentInstance2).isNewCreate) {
                    if (((DetailFragment) checkFragmentInstance2).isBackOrSave) {
                        ((DetailFragment) checkFragmentInstance2).reportSaveNoteEvent("M_create_success");
                        FirebaseReportUtils.getInstance().reportNew("M_create_OK");
                        FirebaseReportUtils.getInstance().reportNew("M_create_success_back");
                        if (!App.userConfig.getFirstEditOk()) {
                            App.userConfig.setFirstEditOk(true);
                            FirebaseReportUtils.getInstance().logMainFlow(this, "v1_f_main_create_ok");
                        }
                    } else {
                        String stringBuffer2 = ((DetailFragment) checkFragmentInstance2).editAction.toString();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("key_back", stringBuffer2);
                        FirebaseReportUtils.getInstance().reportNew("M_create_empty_back", bundle2);
                        FirebaseReportUtils.getInstance().reportNew("M_create_empty_back_android");
                    }
                }
                ((DetailFragment) checkFragmentInstance2).reportSaveNoteEvent("edit_save_all");
                ((DetailFragment) checkFragmentInstance2).reportSaveNoteEvent("edit_save_auto_back");
                ((DetailFragment) checkFragmentInstance2).reportNewSaveNoteEvent();
                NoteBgBean noteBg2 = ResNoteBgManager.getInstance().getNoteBg(this.mDetailFragment.noteTmp.getStickyColor(), this.mDetailFragment.noteTmp.getStickyType(), this.mDetailFragment.noteTmp.getBgId());
                FirebaseReportUtils.getInstance().reportNew("edit_default_color", Constants.THEME_EVENT_KEY, "" + noteBg2.getId());
            }
        } catch (Exception unused) {
        }
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isRestore) {
            return;
        }
        MainActivity.deliverNote = null;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity
    public void onPreOnCreate(Bundle bundle) {
        super.onPreOnCreate(bundle);
        if (isDarkMode()) {
            setTheme(R.style.MyMaterialTheme_Base_Dark);
        } else {
            setTheme(R.style.MyMaterialTheme_Base);
        }
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        boolean hasValidCache = AdLoader.get(Constants.EDIT_SAVE_INTERS, this).hasValidCache();
        if (RemoteConfig.getLong("edit_back_inter_switch") != 0 || System.currentTimeMillis() - this.createTime <= 4000 || hasValidCache) {
            return;
        }
        AdLoader.get(Constants.EDIT_SAVE_INTERS, this).preLoadAd(this);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        isRestore = true;
        if (this.isNewEdit) {
            DetailFragmentNew detailFragmentNew = this.mDetailFragmentNew;
            if (detailFragmentNew != null) {
                MainActivity.deliverNote = detailFragmentNew.noteTmp;
                return;
            }
            return;
        }
        DetailFragment detailFragment = this.mDetailFragment;
        if (detailFragment != null) {
            MainActivity.deliverNote = detailFragment.noteTmp;
        }
    }

    public void setCreateState(boolean z6) {
        this.isCreate = z6;
    }

    public void setEditUri(Uri uri) {
        this.editUri = uri;
    }

    public void setStatusBarColor(boolean z6) {
        if (z6) {
            BarUtils.setStatusBarTextColor(this, ContextCompat.getColor(App.getAppContext(), R.color.black_87alpha_df000));
        } else {
            BarUtils.setStatusBarTextColor(this, ContextCompat.getColor(App.getAppContext(), R.color.white_94alpha_f0fff));
        }
    }

    public void showLoadingAds(boolean z6) {
        this.loading_ad.setVisibility(z6 ? 0 : 8);
    }

    public void showMessage(final String str) {
        runOnUiThread(new Runnable() { // from class: w5.p0
            @Override // java.lang.Runnable
            public final void run() {
                EditActivity.this.lambda$showMessage$1(str);
            }
        });
    }

    public void showWidgetDialog(final Note note) {
        this.userConfig.setWidgetDialogShow(true);
        DialogAddCategory.INSTANCE.showAddWidgetDialog(this, new DialogAddCategory.ShowAddWidgetDialogInterface() { // from class: notes.easy.android.mynotes.ui.activities.EditActivity.1
            @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShowAddWidgetDialogInterface
            public void onDismiss() {
                EditActivity editActivity = EditActivity.this;
                editActivity.isShowWidgetDialog = false;
                editActivity.finish();
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShowAddWidgetDialogInterface
            public void onWidgetImgClick() {
                WidgetCustomizeActivity.Companion.setNote(note);
                WidgetFirebaseReport widgetFirebaseReport = new WidgetFirebaseReport();
                widgetFirebaseReport.setPromote("promote");
                widgetFirebaseReport.setPromoteAdd(true);
                widgetFirebaseReport.setPromoteAddOk(true);
                if (ScreenUtils.isPad(EditActivity.this)) {
                    Intent intent = new Intent(EditActivity.this, (Class<?>) WidgetSelectActivityPad.class);
                    intent.putExtra(Constants.EXTRA_IS_SIDEBAR, false);
                    intent.putExtra(Constants.EXTRA_WIDGET_FIREBASE_REPORT, widgetFirebaseReport);
                    EditActivity.this.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(EditActivity.this, (Class<?>) WidgetSelectActivity.class);
                intent2.putExtra(Constants.EXTRA_IS_SIDEBAR, false);
                intent2.putExtra(Constants.EXTRA_WIDGET_FIREBASE_REPORT, widgetFirebaseReport);
                EditActivity.this.startActivity(intent2);
            }
        });
    }

    @Override // notes.easy.android.mynotes.ui.activities.BaseActivity
    protected boolean transparent() {
        return true;
    }

    public void showMessage(int i6) {
        showMessage(getString(i6));
    }
}
