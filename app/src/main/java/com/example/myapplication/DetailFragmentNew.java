package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import b6.a2;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.example.myapplication.custom.edittext.EditText;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.myview.android.checklistview.interfaces.CheckListChangedListener;
import com.myview.android.checklistview.models.CheckListView;
import com.myview.android.checklistview.models.CheckListViewItem;
import com.myview.android.checklistview.models.ChecklistManager;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.edittext.EditTextMenuListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.Album;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.listener.OnSelectedListener;
import de.greenrobot.event.EventBus;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import it.feio.android.pixlui.links.TextLinkClickListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.fortuna.ical4j.util.Strings;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.EasyNoteManager;
import notes.easy.android.mynotes.async.AttachmentTask;
import notes.easy.android.mynotes.async.bus.PushbulletReplyEvent;
import notes.easy.android.mynotes.async.bus.WidgetEvent;
import notes.easy.android.mynotes.async.notes.SaveNoteTask;
import notes.easy.android.mynotes.constant.Constants;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.constant.stickyBg.ConstantsColorBg;
import notes.easy.android.mynotes.db.DbHelper;
import notes.easy.android.mynotes.edit.bullet.BulletContorller;
import notes.easy.android.mynotes.edit.bullet.BulletSpanInfo;
import notes.easy.android.mynotes.edit.bullet.MyBulletSpan;
import notes.easy.android.mynotes.edit.bullet.MyBulletSpanHelper;
import notes.easy.android.mynotes.edit.bullet.NumListEntry;
import notes.easy.android.mynotes.edit.view.BaseEditView;
import notes.easy.android.mynotes.firebase.FirebaseReportUtils;
import notes.easy.android.mynotes.firebase.RemoteConfig;
import notes.easy.android.mynotes.helpers.MyLog;
import notes.easy.android.mynotes.helpers.date.RecurrenceHelper;
import notes.easy.android.mynotes.models.Attachment;
import notes.easy.android.mynotes.models.Category;
import notes.easy.android.mynotes.models.Note;
import notes.easy.android.mynotes.models.adapters.FontColorAdadpter;
import notes.easy.android.mynotes.models.adapters.HighLightAdapter;
import notes.easy.android.mynotes.models.adapters.PicGridAdapter;
import notes.easy.android.mynotes.models.adapters.RecordGridAdapter;
import notes.easy.android.mynotes.models.listeners.OnAttachingFileListener;
import notes.easy.android.mynotes.models.listeners.OnNoteSaved;
import notes.easy.android.mynotes.models.listeners.OnReminderPickedListener;
import notes.easy.android.mynotes.models.views.ExpandableHeightGridView;
import notes.easy.android.mynotes.receiver.DailyReminderReceiver;
import notes.easy.android.mynotes.ui.activities.ActivityStackManager;
import notes.easy.android.mynotes.ui.activities.DrawActivity;
import notes.easy.android.mynotes.ui.activities.EditActivity;
import notes.easy.android.mynotes.ui.activities.EditCustomBgActivity;
import notes.easy.android.mynotes.ui.activities.GalleryActivity;
import notes.easy.android.mynotes.ui.activities.MainActivity;
import notes.easy.android.mynotes.ui.activities.billing.VipDiscountUtil;
import notes.easy.android.mynotes.ui.activities.widget.SidebarSelectNotesListActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetCustomizeActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetCustomizeActivityPad;
import notes.easy.android.mynotes.ui.activities.widget.WidgetSelectActivity;
import notes.easy.android.mynotes.ui.activities.widget.WidgetSelectActivityPad;
import notes.easy.android.mynotes.ui.activities.widget.WidgetTutorial;
import notes.easy.android.mynotes.ui.activities.widget.WidgetTutorialPad;
import notes.easy.android.mynotes.ui.adapters.EditContentAdapter;
import notes.easy.android.mynotes.ui.adapters.NoteFontAdapter;
import notes.easy.android.mynotes.ui.adapters.TagsAdapter;
import notes.easy.android.mynotes.ui.fragments.CustomDialog;
import notes.easy.android.mynotes.ui.fragments.DetailFragmentNew;
import notes.easy.android.mynotes.ui.fragments.DialogLockFragment;
import notes.easy.android.mynotes.ui.model.CustomBgBean;
import notes.easy.android.mynotes.ui.model.EditContentBean;
import notes.easy.android.mynotes.ui.model.EditContentUndoRedoBean;
import notes.easy.android.mynotes.ui.model.EmojiBean;
import notes.easy.android.mynotes.ui.model.NoteBgBean;
import notes.easy.android.mynotes.ui.model.NoteFontBean;
import notes.easy.android.mynotes.ui.model.WidgetFirebaseReport;
import notes.easy.android.mynotes.utils.AlphaManager;
import notes.easy.android.mynotes.utils.AndroidUpgradeUtils;
import notes.easy.android.mynotes.utils.ConstantsBase;
import notes.easy.android.mynotes.utils.DeviceUtils;
import notes.easy.android.mynotes.utils.DeviceUtilsKt;
import notes.easy.android.mynotes.utils.EmojiManager;
import notes.easy.android.mynotes.utils.EventBus.EventInfo;
import notes.easy.android.mynotes.utils.EventBus.EventUtils;
import notes.easy.android.mynotes.utils.FileHelper;
import notes.easy.android.mynotes.utils.FileProviderHelper;
import notes.easy.android.mynotes.utils.IntentChecker;
import notes.easy.android.mynotes.utils.KeyboardUtils;
import notes.easy.android.mynotes.utils.NetworkUtils;
import notes.easy.android.mynotes.utils.ReminderHelper;
import notes.easy.android.mynotes.utils.ResNoteBgManager;
import notes.easy.android.mynotes.utils.ResNoteFontManager;
import notes.easy.android.mynotes.utils.ScreenUtils;
import notes.easy.android.mynotes.utils.SortComparator;
import notes.easy.android.mynotes.utils.StorageHelper;
import notes.easy.android.mynotes.utils.SystemHelper;
import notes.easy.android.mynotes.utils.TextHelper;
import notes.easy.android.mynotes.utils.UriInOut;
import notes.easy.android.mynotes.utils.WidgetUtils;
import notes.easy.android.mynotes.utils.permission.PermissionUtils;
import notes.easy.android.mynotes.utils.print.NotePrintAdapterNew;
import notes.easy.android.mynotes.utils.print.PDFUtils;
import notes.easy.android.mynotes.utils.share.ShareUtil;
import notes.easy.android.mynotes.view.AddCategoryInterface;
import notes.easy.android.mynotes.view.AddEmojiInterface;
import notes.easy.android.mynotes.view.DetailPopupWindow;
import notes.easy.android.mynotes.view.DialogAddCategory;
import notes.easy.android.mynotes.view.DialogCancelInterface;
import notes.easy.android.mynotes.view.InputHelperView;
import notes.easy.android.mynotes.view.ItemDragCallback;
import notes.easy.android.mynotes.view.MyNestedScrollView;
import notes.easy.android.mynotes.view.NoteBgEditView;
import notes.easy.android.mynotes.view.ScrollGridLayoutManager;
import notes.easy.android.mynotes.view.SoftKeyBroadManager;
import notes.easy.android.mynotes.view.bubble.BubbleDialog;
import notes.easy.android.mynotes.view.bubble.Util;
import notes.easy.android.mynotes.view.record.DialogRecord;
import notes.easy.android.mynotes.view.setpw.DialogSetPwd;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.protocol.HTTP;
import org.slf4j.Marker;
import src.ad.adapters.AdLoader;
import src.ad.adapters.IAdAdapter;
import src.ad.adapters.IAdLoadListener;

/* loaded from: classes4.dex */
public class DetailFragmentNew extends BaseNewFragment implements OnReminderPickedListener, OnAttachingFileListener, TextWatcher, CheckListChangedListener, OnNoteSaved, AddCategoryInterface, InputHelperView.ChooseInterface, View.OnClickListener, EditTextMenuListener, DialogAddCategory.PhotoActionListener, DialogAddCategory.vipConfiremListener, DialogAddCategory.CateFinishListener, BaseEditView.PicClicklistener, DialogAddCategory.vipQuitListener, DialogAddCategory.chooseDarkListener, FontColorAdadpter.FontColorListener, DialogAddCategory.AudioListener, HighLightAdapter.OnListCallback {
    public static final int NOTIFY_DATA_ALL = 0;
    public static final int NOTIFY_DATA_CHANGED = 2;
    public static final int NOTIFY_DATA_INSERTED = 1;
    public static final int NOTIFY_DATA_REMOVED = 3;
    static Lock lock = new ReentrantLock();
    public static boolean needScrollToTop = false;
    public static Uri nowUri = null;
    private static boolean saveSkech = false;
    private final long AMPLITUDE_UPDATE_MS;
    private final List<String> ColorPoint;
    private boolean WidgetSave;
    private boolean activityPausing;
    boolean adShowed;
    LinearLayout add_Layout;
    ImageView addchecklistImg;
    TextView addchecklistTv;
    private boolean alarmChanged;
    private Timer amplitudeTimer;
    private Uri attachmentUri;
    ViewStub attachmentsBelow;
    private final List<Attachment> audioList;
    private long audioRecordingTime;
    private long audioRecordingTimeStart;
    private final Runnable autoSaveRunnable;
    private long autoSaveType;
    private final List<String> background;
    private final List<BaseEditView> baseEntrys;
    private boolean beginEdit;
    private boolean bgChanged;
    private BulletContorller bulletContorller;
    private final List<String> bulletLine;
    private int categoryArrowDownResource;
    private int categoryArrowUpResource;
    ImageView categoryImg;
    TextView categoryName;
    private final double changedFont;
    private final int checkImgResource;
    private int checkSize;
    EditText clickedView;
    private int clipboardDown;
    private boolean colorDialogConfirm;
    EditText content;
    EditContentAdapter contentAdapter;
    public List<EditContentBean> contentBeanList;
    LinearLayout contentLayout;
    public List<EditContentBean> contentOriginal;
    private RecyclerView contentRecycler;
    public List<EditContentUndoRedoBean> contentRedo;
    public String contentSearched;
    public List<EditContentUndoRedoBean> contentUndo;
    ImageView content_redo;
    ImageView content_undo;
    private EditText currentFocus;
    View currentFocusEdit;
    private int currentFontAbsoluteSize;
    private NoteFontBean currentFontBean;
    private String currentFontName;
    ArrayList<String> currentTagList;
    private String defaultContentStyle;
    private EditText detailFragmentEditText;
    RelativeLayout detailTopView;
    View detail_content_card;
    private DialogEmojiFragment dialogEmojiFragment;
    private int duration;
    private Timer durationTimer;
    ImageView editDateView;
    ImageView exitView;
    private String finalUrlClickable;
    private final List<String> fontColorLine;
    View fontColorUnder;
    private CustomDialog fontDialog;
    private final List<String> fontSizeLine;
    private boolean fontStyleChange;
    View fragment_layout;
    private boolean freeTryDialogShow;
    private boolean hasSaveSuccess;
    private boolean hasSavedFromCreate;
    private boolean hasSedLocked;
    private String highLightColor;
    private List<Attachment> imageList;
    private boolean isBold;
    private boolean isCalendarActivityComeIn;
    private final boolean isDark;
    private boolean isDrawOrCheck;
    private boolean isEdit;
    private boolean isItalic;
    private boolean isLineTheme_;
    private boolean isNewCreate;
    private boolean isSaveBg;
    boolean isSaved;
    private boolean isShowClipboardView;
    private boolean isStrikethrough;
    private boolean isUnderline;
    private boolean isWidgetHideBoard;
    private long lastSaveTime;
    private long lastTime;
    private int lastX;
    private int lastY;
    private NoteBgBean.Line line_;
    public PicGridAdapter mAttachmentAdapter;
    public Context mBaseContext;
    InputHelperView mBottomBar;
    private ChecklistManager mChecklistManager;
    private ChecklistManager mChecklistManager2;
    private String mCurrentFontColor;
    private int mCurrentFontIndex;
    private DetailFragmentNew mFragment;
    RecyclerView mGridView;
    private final Handler mHander;
    private NoteBgBean mLastNoteBg;
    private int mLastNoteBgTabSelectIndex;
    private NoteBgDialogFragment mNoteBgDialogFragment;
    private NoteBgBean mOriginalNoteBg;
    private long mPressedTime;
    private NoteBgBean mRecentNoteBg;
    public Handler mRefreshHandler;
    public EditActivity mainActivity;
    ImageView moreView;
    private boolean needSearch;
    private MyNestedScrollView nestedScrollView;
    private Note note;
    NoteBgEditView noteBgView;
    private Note noteOriginal;
    public Note noteTmp;
    private long onCreateTime;
    private final View.OnFocusChangeListener onFocusChangeListener;
    private String originFontStyle;
    ImageView pinView;
    private final int pinViewResource;
    private final List<String> point;
    private SharedPreferences prefs;
    ExpandableHeightGridView recordGrid;
    private RecordGridAdapter recordGridAdapter;
    private String recordName;
    ImageView reminderView;
    ViewGroup root;
    ScrollView scrollView;
    TextView searchButton;
    ImageView searchDelete;
    android.widget.EditText searchEdit;
    private final List<String> searchEditIndex;
    ConstraintLayout searchLayout;
    private final List<EditText> searchedEditList;
    private boolean showLockRed;
    private boolean showPdfRed;
    private boolean showReadingRed;
    private boolean showRemindRed;
    private boolean showShareRed;
    private int startIndex;
    private final List<String> strikethrough;
    private StringBuilder stringBuilder;
    RecyclerView tagRecycleView;
    private TagsAdapter tagsAdapter;
    private final TextLinkClickListener textLinkClickListener;
    private boolean timeModified;
    private long timesSwitch;
    EditText title;
    private View toggleChecklistView;
    private View toggleChecklistView2;
    ImageView topCategoryArrow;
    View topCategoryLayout;
    TextView topTimeText;
    RelativeLayout topToolBar;
    private final List<String> underLine;
    private boolean userFoucChanged;
    public boolean goBack = false;
    public boolean newCreate = true;
    private boolean isRecording = false;
    private boolean isRecordingPause = false;
    private boolean isShareAction = false;
    public boolean isBackOrSave = false;
    public StringBuffer editAction = new StringBuffer();
    private final UserConfig userPreferences = UserConfig.Companion.newInstance(App.getAppContext());
    private int dialogType = -1;
    private boolean anyDialogShow = false;
    public String SearchText = "";
    Gson gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriInOut()).create();
    private MediaRecorder mRecorder = null;
    private int contentLineCounter = 1;

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$1 */
    class AnonymousClass1 extends Handler {
        AnonymousClass1() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (DetailFragmentNew.this.contentRecycler != null) {
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                if (detailFragmentNew.contentAdapter != null && !detailFragmentNew.contentRecycler.isComputingLayout()) {
                    int i6 = message.what;
                    if (i6 == 0) {
                        DetailFragmentNew.this.contentAdapter.notifyDataSetChanged();
                        return;
                    }
                    if (i6 == 1) {
                        DetailFragmentNew.this.contentAdapter.notifyItemInserted(message.arg1);
                        return;
                    }
                    if (i6 != 2) {
                        if (i6 != 3) {
                            return;
                        }
                        DetailFragmentNew.this.contentAdapter.notifyItemRemoved(message.arg1);
                        return;
                    } else {
                        Object obj = message.obj;
                        if ((obj instanceof Boolean) && ((Boolean) obj).booleanValue()) {
                            DetailFragmentNew.this.contentAdapter.setCursorEditingStop(true);
                        }
                        DetailFragmentNew.this.contentAdapter.notifyItemChanged(message.arg1);
                        return;
                    }
                }
            }
            DetailFragmentNew.this.mRefreshHandler.sendMessageDelayed(message, 50L);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$11 */
    class AnonymousClass11 extends TimerTask {
        AnonymousClass11() {
        }

        public /* synthetic */ void lambda$run$0() {
            DetailFragmentNew.this.searchLayout.setVisibility(View.VISIBLE);
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            detailFragmentNew.searchEdit.setText(detailFragmentNew.SearchText);
            DetailFragmentNew.this.searchButton.performClick();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            DetailFragmentNew.this.mainActivity.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.a
                @Override // java.lang.Runnable
                public void run() {
                    DetailFragmentNew.AnonymousClass11.this.lambda$run$0();
                }
            });
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$12 */
    class AnonymousClass12 implements Runnable {
        final /* synthetic */ Editable val$editable;
        final /* synthetic */ String val$spans;
        final /* synthetic */ EditText val$textView;
        final /* synthetic */ boolean val$updateBullet;

        AnonymousClass12(String str, Editable editable, EditText editText, boolean z6) {
            this.val$spans = str;
            this.val$editable = editable;
            this.val$textView = editText;
            this.val$updateBullet = z6;
        }

        public /* synthetic */ void lambda$run$0(AbsoluteSizeSpan[] absoluteSizeSpanArr, EditText editText, int i6, int i7, int i8) {
            if (absoluteSizeSpanArr == null) {
                DetailFragmentNew.this.setAbsoluteFontSize(editText, i6, i7, i8, false);
            } else if (absoluteSizeSpanArr.length == 0) {
                DetailFragmentNew.this.setAbsoluteFontSize(editText, i6, i7, i8, false);
            }
        }

        public /* synthetic */ void lambda$run$1(EditText editText, int i6, int i7, int i8) {
            DetailFragmentNew.this.setTextFontColor(editText, i6, i7, i8, false);
        }

        public /* synthetic */ void lambda$run$2(EditText editText, int i6, int i7, int i8) {
            DetailFragmentNew.this.setTextFontColor(editText, i6, i7, i8, false);
        }

        public /* synthetic */ void lambda$run$3(EditText editText, Editable editable, int i6, int i7, int i8) {
            DetailFragmentNew.this.setTextBackground(editText, editable, i6, i7, i8, false);
        }

        public /* synthetic */ void lambda$run$4(Editable editable, int i6, int i7) {
            DetailFragmentNew.this.setTextStrikethrough(editable, true, i6, i7);
        }

        public /* synthetic */ void lambda$run$5(Editable editable, int i6, int i7) {
            DetailFragmentNew.this.setTextUnderLine(editable, true, i6, i7);
        }

        public /* synthetic */ void lambda$run$6(int i6, EditText editText, Editable editable, int i7, int i8) {
            if (i6 == 1) {
                DetailFragmentNew.this.setTextBold(editText, editable, true, i7, i8, false);
            } else if (i6 == 2) {
                DetailFragmentNew.this.setTextItalic(editText, editable, true, i7, i8, false);
            }
        }

        public /* synthetic */ void lambda$run$7(RelativeSizeSpan[] relativeSizeSpanArr, Editable editable, float f6, int i6, int i7) {
            if (relativeSizeSpanArr == null) {
                DetailFragmentNew.this.setTextRelativeSize(editable, f6, i6, i7);
            } else if (relativeSizeSpanArr.length == 0) {
                DetailFragmentNew.this.setTextRelativeSize(editable, f6, i6, i7);
            }
        }

        public static /* synthetic */ void lambda$run$8(String str, Editable editable, EditText editText, int i6) {
            if (Constants.SPAN_BULLET_D.equalsIgnoreCase(str)) {
                TextHelper.loadInitBulletSpan(editable, editText, "Dots", i6);
            } else if (Constants.SPAN_BULLET_Z.equalsIgnoreCase(str)) {
                TextHelper.loadInitBulletSpan(editable, editText, "digital", i6);
            } else {
                TextHelper.loadInitBulletSpan(editable, editText, "checkList_selected", i6);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            String[] split;
            String[] split2;
            String[] split3;
            String[] split4;
            String[] split5;
            String[] split6;
            String[] split7;
            List asList = Arrays.asList(this.val$spans.split(","));
            ArrayList arrayList = new ArrayList();
            for (int i6 = 0; i6 < asList.size(); i6++) {
                String str = (String) asList.get(i6);
                if (!TextUtils.isEmpty(str)) {
                    if (str.startsWith("f")) {
                        String substring = str.substring(1);
                        if (!TextUtils.isEmpty(substring) && (split7 = substring.split("/")) != null && split7.length == 3) {
                            final int parseInt = Util.parseInt(split7[0]);
                            final int parseInt2 = Util.parseInt(split7[1]);
                            if (parseInt == -1 || parseInt2 == -1) {
                                break;
                            }
                            final int parseInt3 = Integer.parseInt(split7[2]);
                            String str2 = "" + parseInt + parseInt2;
                            if (!DetailFragmentNew.this.fontSizeLine.contains(str2)) {
                                final AbsoluteSizeSpan[] absoluteSizeSpanArr = this.val$editable.getSpans(parseInt, parseInt2, AbsoluteSizeSpan.class);
                                EditActivity editActivity = DetailFragmentNew.this.mainActivity;
                                final EditText editText = this.val$textView;
                                editActivity.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.b
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DetailFragmentNew.AnonymousClass12.this.lambda$run$0(absoluteSizeSpanArr, editText, parseInt, parseInt2, parseInt3);
                                    }
                                });
                                DetailFragmentNew.this.fontSizeLine.add(str2);
                            }
                        }
                    } else if (str.startsWith(Constants.SPAN_FONT_COLOR)) {
                        String substring2 = str.substring(1);
                        if (!TextUtils.isEmpty(substring2) && (split6 = substring2.split("/")) != null && split6.length == 3) {
                            final int parseInt4 = Util.parseInt(split6[0]);
                            final int parseInt5 = Util.parseInt(split6[1]);
                            final int parseInt6 = Util.parseInt(split6[2]);
                            if (TextUtils.isEmpty(split6[2]) || !split6[2].startsWith(Constants.SPECIAL_CHARACTOR)) {
                                final int parseInt7 = Integer.parseInt(split6[2]);
                                if (parseInt4 != -1 && parseInt5 != -1 && parseInt6 != -1) {
                                    EditActivity editActivity2 = DetailFragmentNew.this.mainActivity;
                                    final EditText editText2 = this.val$textView;
                                    editActivity2.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.d
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            DetailFragmentNew.AnonymousClass12.this.lambda$run$2(editText2, parseInt4, parseInt5, parseInt7);
                                        }
                                    });
                                }
                            } else if (parseInt4 != -1 && parseInt5 != -1 && parseInt6 != -1) {
                                EditActivity editActivity3 = DetailFragmentNew.this.mainActivity;
                                final EditText editText3 = this.val$textView;
                                editActivity3.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.c
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DetailFragmentNew.AnonymousClass12.this.lambda$run$1(editText3, parseInt4, parseInt5, parseInt6);
                                    }
                                });
                            }
                        }
                    } else if (str.startsWith("h")) {
                        String substring3 = str.substring(1);
                        if (!TextUtils.isEmpty(substring3) && (split5 = substring3.split("/")) != null && split5.length == 3) {
                            final int parseInt8 = Util.parseInt(split5[0]);
                            final int parseInt9 = Util.parseInt(split5[1]);
                            final int parseInt10 = Util.parseInt(split5[2]);
                            if (parseInt8 != -1 && parseInt9 != -1 && parseInt10 != -1) {
                                EditActivity editActivity4 = DetailFragmentNew.this.mainActivity;
                                final EditText editText4 = this.val$textView;
                                final Editable editable = this.val$editable;
                                editActivity4.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.e
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DetailFragmentNew.AnonymousClass12.this.lambda$run$3(editText4, editable, parseInt8, parseInt9, parseInt10);
                                    }
                                });
                            }
                        }
                    } else if (str.startsWith(Constants.SPAN_STRIKETHROUGH)) {
                        String substring4 = str.substring(1);
                        if (!TextUtils.isEmpty(substring4) && (split4 = substring4.split("/")) != null && split4.length == 2) {
                            final int parseInt11 = Util.parseInt(split4[0]);
                            final int parseInt12 = Util.parseInt(split4[1]);
                            if (parseInt11 != -1 && parseInt12 != -1) {
                                EditActivity editActivity5 = DetailFragmentNew.this.mainActivity;
                                final Editable editable2 = this.val$editable;
                                editActivity5.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.f
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DetailFragmentNew.AnonymousClass12.this.lambda$run$4(editable2, parseInt11, parseInt12);
                                    }
                                });
                            }
                        }
                    } else if (str.startsWith(Constants.SPAN_U)) {
                        String substring5 = str.substring(1);
                        if (!TextUtils.isEmpty(substring5) && (split3 = substring5.split("/")) != null && split3.length == 2) {
                            final int parseInt13 = Util.parseInt(split3[0]);
                            final int parseInt14 = Util.parseInt(split3[1]);
                            if (parseInt13 != -1 && parseInt14 != -1) {
                                EditActivity editActivity6 = DetailFragmentNew.this.mainActivity;
                                final Editable editable3 = this.val$editable;
                                editActivity6.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.g
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DetailFragmentNew.AnonymousClass12.this.lambda$run$5(editable3, parseInt13, parseInt14);
                                    }
                                });
                            }
                        }
                    } else if (str.startsWith(Constants.SPAN_S)) {
                        String substring6 = str.substring(1);
                        if (!TextUtils.isEmpty(substring6) && (split2 = substring6.split("/")) != null && split2.length == 3) {
                            final int parseInt15 = Util.parseInt(split2[0]);
                            final int parseInt16 = Util.parseInt(split2[1]);
                            final int parseInt17 = Util.parseInt(split2[2]);
                            if (parseInt15 != -1 && parseInt16 != -1 && parseInt17 != -1) {
                                EditActivity editActivity7 = DetailFragmentNew.this.mainActivity;
                                final EditText editText5 = this.val$textView;
                                final Editable editable4 = this.val$editable;
                                editActivity7.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.h
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DetailFragmentNew.AnonymousClass12.this.lambda$run$6(parseInt17, editText5, editable4, parseInt15, parseInt16);
                                    }
                                });
                            }
                        }
                    } else if (str.startsWith(Constants.SPAN_R)) {
                        String substring7 = str.substring(1);
                        if (!TextUtils.isEmpty(substring7) && (split = substring7.split("/")) != null && split.length == 3) {
                            final int parseInt18 = Util.parseInt(split[0]);
                            final int parseInt19 = Util.parseInt(split[1]);
                            if (parseInt18 == -1 || parseInt19 == -1) {
                                break;
                            }
                            final float parseFloat = Float.parseFloat(split[2]);
                            String str3 = "" + parseInt18 + parseInt19;
                            if (!DetailFragmentNew.this.point.contains(str3)) {
                                final RelativeSizeSpan[] relativeSizeSpanArr = this.val$editable.getSpans(parseInt18, parseInt19, RelativeSizeSpan.class);
                                EditActivity editActivity8 = DetailFragmentNew.this.mainActivity;
                                final Editable editable5 = this.val$editable;
                                editActivity8.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.i
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DetailFragmentNew.AnonymousClass12.this.lambda$run$7(relativeSizeSpanArr, editable5, parseFloat, parseInt18, parseInt19);
                                    }
                                });
                                DetailFragmentNew.this.point.add(str3);
                            }
                        }
                    } else if (str.startsWith(Constants.SPAN_BULLET_D) || str.startsWith(Constants.SPAN_BULLET_Z) || str.startsWith(Constants.SPAN_BULLET_CHECK_BOX) || str.startsWith(Constants.SPAN_BULLET_CHECK_BOX_N)) {
                        arrayList.add(str);
                    }
                }
            }
            if (this.val$updateBullet && arrayList.size() > 0) {
                for (int i7 = 0; i7 < arrayList.size(); i7++) {
                    String str4 = (String) arrayList.get(i7);
                    String substring8 = str4.substring(1);
                    final String substring9 = str4.substring(0, 1);
                    if (!TextUtils.isEmpty(substring8)) {
                        final int parseInt20 = Util.parseInt(substring8);
                        EditActivity editActivity9 = DetailFragmentNew.this.mainActivity;
                        final Editable editable6 = this.val$editable;
                        final EditText editText6 = this.val$textView;
                        editActivity9.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.j
                            @Override // java.lang.Runnable
                            public void run() {
                                DetailFragmentNew.AnonymousClass12.lambda$run$8(substring9, editable6, editText6, parseInt20);
                            }
                        });
                    }
                }
            }
            DetailFragmentNew.this.point.clear();
            DetailFragmentNew.this.fontSizeLine.clear();
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$13 */
    class AnonymousClass13 implements SoftKeyBroadManager.SoftKeyboardStateListener {
        AnonymousClass13() {
        }

        @Override // notes.easy.android.mynotes.view.SoftKeyBroadManager.SoftKeyboardStateListener
        public void onSoftKeyboardClosed() {
            DetailFragmentNew.this.mBottomBar.requestLayout();
        }

        @Override // notes.easy.android.mynotes.view.SoftKeyBroadManager.SoftKeyboardStateListener
        public void onSoftKeyboardOpened(int i6) {
            DetailFragmentNew.this.mBottomBar.requestLayout();
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$14 */
    class AnonymousClass14 implements DetailPopupWindow.DetailPopupWindowLinstener {
        final /* synthetic */ List val$categoryList;

        AnonymousClass14(List list) {
            r2 = list;
        }

        @Override // notes.easy.android.mynotes.view.DetailPopupWindow.DetailPopupWindowLinstener
        public void dismiss() {
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            detailFragmentNew.topCategoryArrow.setImageResource(detailFragmentNew.categoryArrowDownResource);
        }

        @Override // notes.easy.android.mynotes.view.DetailPopupWindow.DetailPopupWindowLinstener
        public void selectTag(int i6) {
            if (i6 == 0) {
                FirebaseReportUtils.getInstance().reportNew("edit_add_category");
                DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                dialogAddCategory.showAddCategoryDialog(detailFragmentNew.mainActivity, detailFragmentNew, true, detailFragmentNew.categoryName, false, detailFragmentNew);
                return;
            }
            if (i6 == 1) {
                DetailFragmentNew.this.noteTmp.setCategory(new Category().makeDefaultCategory());
                DetailFragmentNew.this.categoryName.setText(R.string.uncategorized);
                FirebaseReportUtils.getInstance().reportNew("edit_select_category_click", "key_select", Album.ALBUM_NAME_ALL);
            } else {
                DetailFragmentNew.this.noteTmp.setCategory((Category) r2.get(i6));
                DetailFragmentNew.this.categoryName.setText(((Category) r2.get(i6)).getName());
                FirebaseReportUtils.getInstance().reportNew("edit_select_category_click", "key_select", ((Category) r2.get(i6)).getName());
            }
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$18 */
    class AnonymousClass18 implements TextWatcher {
        int startPos = 0;
        int endPos = 0;

        AnonymousClass18() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            DetailFragmentNew.this.setSaveIconColor();
            if (!TextUtils.isEmpty(editable.toString())) {
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.isBackOrSave = true;
                if (!detailFragmentNew.beginEdit && DetailFragmentNew.this.mHander != null && DetailFragmentNew.this.autoSaveRunnable != null) {
                    DetailFragmentNew.this.beginEdit = true;
                    DetailFragmentNew.this.mHander.postDelayed(DetailFragmentNew.this.autoSaveRunnable, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US);
                }
            }
            if (this.endPos <= this.startPos) {
                if (DetailFragmentNew.this.bulletContorller != null) {
                    DetailFragmentNew.this.bulletContorller.executeDeleteAction(DetailFragmentNew.this.content, editable, this.startPos, this.endPos);
                }
            } else if (DetailFragmentNew.this.bulletContorller != null) {
                DetailFragmentNew.this.bulletContorller.executeInputAction(DetailFragmentNew.this.content, editable, this.startPos, this.endPos);
            }
            boolean z6 = DetailFragmentNew.this.mCurrentFontIndex != Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(DetailFragmentNew.this.userPreferences.getDefaultFloatSize()));
            if (DetailFragmentNew.this.fontStyleIsChange() || DetailFragmentNew.this.currentFontAbsoluteSize != ScreenUtils.dpToPx(16)) {
                if (DetailFragmentNew.this.content.getSelectionEnd() < DetailFragmentNew.this.content.getEditableText().length()) {
                    DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                    EditText editText = detailFragmentNew2.content;
                    detailFragmentNew2.setTextShape(editText, editText.getEditableText(), DetailFragmentNew.this.isBold, DetailFragmentNew.this.isItalic, DetailFragmentNew.this.isUnderline, DetailFragmentNew.this.isStrikethrough, z6, DetailFragmentNew.this.highLightColor, DetailFragmentNew.this.startIndex, DetailFragmentNew.this.content.getSelectionEnd());
                } else {
                    DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
                    EditText editText2 = detailFragmentNew3.content;
                    detailFragmentNew3.setTextShape(editText2, editText2.getEditableText(), DetailFragmentNew.this.isBold, DetailFragmentNew.this.isItalic, DetailFragmentNew.this.isUnderline, DetailFragmentNew.this.isStrikethrough, z6, DetailFragmentNew.this.highLightColor, DetailFragmentNew.this.startIndex, DetailFragmentNew.this.content.getEditableText().length());
                }
            }
            DetailFragmentNew.this.showSavingView();
            DetailFragmentNew.this.userFoucChanged = false;
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            DetailFragmentNew.this.scrollContent();
            if (DetailFragmentNew.this.fontStyleIsChange() || DetailFragmentNew.this.currentFontAbsoluteSize != ScreenUtils.dpToPx(16)) {
                if (DetailFragmentNew.this.content.getSelectionStart() != DetailFragmentNew.this.content.getSelectionEnd() || DetailFragmentNew.this.content.getSelectionEnd() >= DetailFragmentNew.this.content.getEditableText().length()) {
                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                    detailFragmentNew.startIndex = detailFragmentNew.content.getEditableText().length();
                } else {
                    DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                    detailFragmentNew2.startIndex = detailFragmentNew2.content.getSelectionStart();
                }
            }
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            this.startPos = i6;
            this.endPos = i6 + i8;
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$2 */
    class AnonymousClass2 implements TextLinkClickListener {

        /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$2$1 */
        class AnonymousClass1 implements DialogCancelInterface {
            AnonymousClass1() {
            }

            @Override // notes.easy.android.mynotes.view.DialogCancelInterface
            public void confirmDelete() {
                FirebaseReportUtils.getInstance().reportNew("edit_url_open");
                try {
                    if (TextUtils.isEmpty(DetailFragmentNew.this.finalUrlClickable)) {
                        return;
                    }
                    if (!DetailFragmentNew.this.finalUrlClickable.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                        DetailFragmentNew.this.finalUrlClickable = "http://" + DetailFragmentNew.this.finalUrlClickable;
                    }
                    DetailFragmentNew.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(DetailFragmentNew.this.finalUrlClickable)));
                } catch (Exception unused) {
                }
            }

            @Override // notes.easy.android.mynotes.view.DialogCancelInterface
            public void doNothing() {
                FirebaseReportUtils.getInstance().reportNew("edit_url_edit");
                try {
                    KeyboardUtils.showKeyboardDontChange(DetailFragmentNew.this.clickedView);
                } catch (Exception unused) {
                }
            }
        }

        AnonymousClass2() {
        }

        @Override // it.feio.android.pixlui.links.TextLinkClickListener
        public void onTextLinkClick(View view, String str, String str2) {
            if (TextUtils.isEmpty(str) || DetailFragmentNew.this.noteTmp.isChecklist().booleanValue()) {
                return;
            }
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            EditText editText = detailFragmentNew.content;
            detailFragmentNew.clickedView = editText;
            if (!editText.hasFocus()) {
                int i6 = 0;
                while (true) {
                    if (i6 >= DetailFragmentNew.this.baseEntrys.size()) {
                        break;
                    }
                    EditText editText2 = ((BaseEditView) DetailFragmentNew.this.baseEntrys.get(i6)).getmEditText();
                    if (editText2.hasFocus()) {
                        DetailFragmentNew.this.clickedView = editText2;
                        break;
                    }
                    i6++;
                }
            }
            DetailFragmentNew.this.finalUrlClickable = str;
            if (!str.startsWith(HttpHost.DEFAULT_SCHEME_NAME) && !str.startsWith("www")) {
                if (str.contains("www")) {
                    int indexOf = DetailFragmentNew.this.finalUrlClickable.indexOf("www");
                    DetailFragmentNew.this.finalUrlClickable = str.substring(indexOf);
                } else if (str.contains(HttpHost.DEFAULT_SCHEME_NAME)) {
                    int indexOf2 = DetailFragmentNew.this.finalUrlClickable.indexOf(HttpHost.DEFAULT_SCHEME_NAME, 0);
                    DetailFragmentNew.this.finalUrlClickable = str.substring(indexOf2);
                }
            }
            KeyboardUtils.hideKeyboard(DetailFragmentNew.this.clickedView);
            DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
            DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
            dialogAddCategory.showHylinkClickDialog(detailFragmentNew2.mainActivity, detailFragmentNew2.finalUrlClickable, new DialogCancelInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.2.1
                AnonymousClass1() {
                }

                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                public void confirmDelete() {
                    FirebaseReportUtils.getInstance().reportNew("edit_url_open");
                    try {
                        if (TextUtils.isEmpty(DetailFragmentNew.this.finalUrlClickable)) {
                            return;
                        }
                        if (!DetailFragmentNew.this.finalUrlClickable.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                            DetailFragmentNew.this.finalUrlClickable = "http://" + DetailFragmentNew.this.finalUrlClickable;
                        }
                        DetailFragmentNew.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(DetailFragmentNew.this.finalUrlClickable)));
                    } catch (Exception unused) {
                    }
                }

                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                public void doNothing() {
                    FirebaseReportUtils.getInstance().reportNew("edit_url_edit");
                    try {
                        KeyboardUtils.showKeyboardDontChange(DetailFragmentNew.this.clickedView);
                    } catch (Exception unused) {
                    }
                }
            });
            FirebaseReportUtils.getInstance().reportNew("edit_url_click");
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$20 */
    class AnonymousClass20 implements DialogAddCategory.Positive1Listener<String> {
        AnonymousClass20() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
        public void positiveClick(String str) {
            DetailFragmentNew.this.archiveNote(true);
            Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.note_archived, 0).show();
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$21 */
    class AnonymousClass21 implements DialogAddCategory.Positive1Listener<String> {
        AnonymousClass21() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
        public void positiveClick(String str) {
            DetailFragmentNew.this.trashNote(true);
            Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.delete_success, 0).show();
            FirebaseReportUtils.getInstance().reportNew("edit_more_delete_OK");
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$22 */
    class AnonymousClass22 implements DialogAddCategory.TimerChangedListener {
        AnonymousClass22() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
        public void doRingTonChoose() {
            Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
            intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
            intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
            DetailFragmentNew.this.startActivityForResult(intent, 7);
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
        public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
            if (DetailFragmentNew.nowUri != null) {
                DetailFragmentNew.this.userPreferences.setRingTonNow(DetailFragmentNew.nowUri.toString());
            }
            if (DetailFragmentNew.this.noteTmp.isTrashed().booleanValue() || DetailFragmentNew.this.noteTmp.isArchived().booleanValue()) {
                Toast.makeText(App.getAppContext(), R.string.edit_alarm_success, 0).show();
                return;
            }
            Note note = DetailFragmentNew.this.noteTmp;
            note.setRecurrenceRule(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, note.getRecurrenceRule()));
            DetailFragmentNew.this.noteTmp.setAlarm(j6);
            DetailFragmentNew.this.alarmChanged = true;
            Toast.makeText(App.getAppContext(), R.string.set_alarm_success, 0).show();
            FirebaseReportUtils.getInstance().reportNew("edit_reminder_OK");
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$23 */
    class AnonymousClass23 implements DialogAddCategory.Positive1Listener<String> {
        AnonymousClass23() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
        public void positiveClick(String str) {
            FirebaseReportUtils.getInstance().reportNew("edit_more_tags_added");
            List<String> tagList = EasyNoteManager.getInstance().getTagList(App.userConfig.getTags());
            if (!tagList.contains(str)) {
                tagList.add(str);
                App.userConfig.setTags(EasyNoteManager.getInstance().getTagString(tagList));
                EventUtils.post(106);
            }
            String tags = DetailFragmentNew.this.noteTmp.getTags();
            if (!DetailFragmentNew.this.currentTagList.contains(str)) {
                if (TextUtils.isEmpty(tags)) {
                    DetailFragmentNew.this.noteTmp.setTags(str);
                } else {
                    DetailFragmentNew.this.noteTmp.setTags(tags + "," + str);
                }
                DetailFragmentNew.this.currentTagList.add(str);
                if (DetailFragmentNew.this.tagsAdapter != null) {
                    DetailFragmentNew.this.tagsAdapter.notifyDataSetChanged();
                }
                if (DetailFragmentNew.this.tagRecycleView.getVisibility() != 0) {
                    DetailFragmentNew.this.tagRecycleView.setVisibility(View.VISIBLE);
                }
                DetailFragmentNew.this.timeModified = true;
            }
            KeyboardUtils.hideKeyboard(DetailFragmentNew.this.mainActivity);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$25 */
    class AnonymousClass25 extends TimerTask {
        AnonymousClass25() {
        }

        public /* synthetic */ void lambda$run$0() {
            try {
                DetailFragmentNew.this.scrollView.fullScroll(130);
                if (DetailFragmentNew.this.nestedScrollView != null) {
                    DetailFragmentNew.this.nestedScrollView.fullScroll(33);
                }
            } catch (Exception unused) {
            }
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            DetailFragmentNew.this.mainActivity.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.k
                @Override // java.lang.Runnable
                public void run() {
                    DetailFragmentNew.AnonymousClass25.this.lambda$run$0();
                }
            });
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$28 */
    class AnonymousClass28 implements PermissionUtils.PermissionDirctListener {
        AnonymousClass28() {
        }

        @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
        public void onPermissionAlreadyGranted() {
            DetailFragmentNew.this.startGetContentAction();
        }

        @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
        public void onRequestPermissionsResult(String[] strArr, int[] iArr) {
            if (AndroidUpgradeUtils.isHaveVisualUserSelectedPerminssion(DetailFragmentNew.this.getActivity()) || (iArr.length > 0 && iArr[0] == 0)) {
                DetailFragmentNew.this.startGetContentAction();
            }
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$29 */
    class AnonymousClass29 implements PermissionUtils.PermissionDirctListener {
        AnonymousClass29() {
        }

        @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
        public void onPermissionAlreadyGranted() {
            DetailFragmentNew.this.startGetSingleContentAction();
        }

        @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
        public void onRequestPermissionsResult(String[] strArr, int[] iArr) {
            if (AndroidUpgradeUtils.isHaveVisualUserSelectedPerminssion(DetailFragmentNew.this.getActivity()) || (iArr.length > 0 && iArr[0] == 0)) {
                DetailFragmentNew.this.startGetSingleContentAction();
            }
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$3 */
    class AnonymousClass3 extends TimerTask {
        AnonymousClass3() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            EditContentAdapter editContentAdapter = DetailFragmentNew.this.contentAdapter;
            if (editContentAdapter != null) {
                HashMap<Integer, EditText> editTextHashMap = editContentAdapter.getEditTextHashMap();
                ArrayList arrayList = new ArrayList();
                Iterator<Integer> it2 = editTextHashMap.keySet().iterator();
                int i6 = 0;
                while (it2.hasNext()) {
                    int intValue = it2.next().intValue();
                    if (intValue == i6) {
                        i6++;
                        arrayList.add(editTextHashMap.get(Integer.valueOf(intValue)));
                    }
                }
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                ShareUtil.shareLongPictures(detailFragmentNew.noteTmp, detailFragmentNew.title, detailFragmentNew.contentBeanList, arrayList, detailFragmentNew.mainActivity, detailFragmentNew.toggleChecklistView, DetailFragmentNew.this.currentFontBean, DetailFragmentNew.this.topTimeText);
            }
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$31 */
    class AnonymousClass31 extends TimerTask {
        AnonymousClass31() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (DetailFragmentNew.this.mRecorder != null) {
                try {
                    DialogRecord.INSTANCE.updateRecordingWave(DetailFragmentNew.this.mRecorder.getMaxAmplitude());
                } catch (Exception unused) {
                }
            }
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$32 */
    class AnonymousClass32 extends TimerTask {
        AnonymousClass32() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            DetailFragmentNew.access$4308(DetailFragmentNew.this);
            DetailFragmentNew.this.broadcastDuration();
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$33 */
    class AnonymousClass33 implements DialogCancelInterface {

        /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$33$1 */
        class AnonymousClass1 implements DialogAddCategory.TimerChangedListener {
            AnonymousClass1() {
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
            public void doRingTonChoose() {
                Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
                intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
                DetailFragmentNew.this.startActivityForResult(intent, 7);
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
            public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
                if (DetailFragmentNew.nowUri != null) {
                    DetailFragmentNew.this.userPreferences.setRingTonNow(DetailFragmentNew.nowUri.toString());
                }
                Note note = DetailFragmentNew.this.noteTmp;
                note.setRecurrenceRule(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, note.getRecurrenceRule()));
                DetailFragmentNew.this.noteTmp.setAlarm(j6);
                DetailFragmentNew.this.alarmChanged = true;
                FirebaseReportUtils.getInstance().reportNew("edit_reminder_OK");
                if (DetailFragmentNew.this.noteTmp.getAlarm() != null) {
                    Toast.makeText(App.getAppContext(), R.string.set_alarm_success, 0).show();
                    DetailFragmentNew.this.doExit();
                }
            }
        }

        AnonymousClass33() {
        }

        @Override // notes.easy.android.mynotes.view.DialogCancelInterface
        public void confirmDelete() {
            FirebaseReportUtils.getInstance().reportNew("time_promote_edit_reminder_click");
            DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            Note note = detailFragmentNew.noteTmp;
            dialogAddCategory.showEditTimeDialog(true, note, detailFragmentNew.mainActivity, note.getRecurrenceRule(), R.string.add_reminder, new DialogAddCategory.TimerChangedListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.33.1
                AnonymousClass1() {
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                public void doRingTonChoose() {
                    Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                    intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
                    intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
                    DetailFragmentNew.this.startActivityForResult(intent, 7);
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
                    if (DetailFragmentNew.nowUri != null) {
                        DetailFragmentNew.this.userPreferences.setRingTonNow(DetailFragmentNew.nowUri.toString());
                    }
                    Note note2 = DetailFragmentNew.this.noteTmp;
                    note2.setRecurrenceRule(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, note2.getRecurrenceRule()));
                    DetailFragmentNew.this.noteTmp.setAlarm(j6);
                    DetailFragmentNew.this.alarmChanged = true;
                    FirebaseReportUtils.getInstance().reportNew("edit_reminder_OK");
                    if (DetailFragmentNew.this.noteTmp.getAlarm() != null) {
                        Toast.makeText(App.getAppContext(), R.string.set_alarm_success, 0).show();
                        DetailFragmentNew.this.doExit();
                    }
                }
            });
            DetailFragmentNew.this.newCreate = false;
        }

        @Override // notes.easy.android.mynotes.view.DialogCancelInterface
        public void doNothing() {
            DetailFragmentNew.this.doExit();
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$35 */
    class AnonymousClass35 implements DialogCancelInterface {
        AnonymousClass35() {
        }

        @Override // notes.easy.android.mynotes.view.DialogCancelInterface
        public void confirmDelete() {
            FirebaseReportUtils.getInstance().reportNew("time_promote_edit_cate_click");
            DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            dialogAddCategory.showAddCategoryDialog(detailFragmentNew.mainActivity, detailFragmentNew, true, detailFragmentNew.categoryName, true, detailFragmentNew);
        }

        @Override // notes.easy.android.mynotes.view.DialogCancelInterface
        public void doNothing() {
            DetailFragmentNew.this.doExit();
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$37 */
    class AnonymousClass37 implements DialogAddCategory.ShareListener {

        /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$37$1 */
        class AnonymousClass1 extends TimerTask {
            AnonymousClass1() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                EditContentAdapter editContentAdapter = DetailFragmentNew.this.contentAdapter;
                if (editContentAdapter != null) {
                    HashMap<Integer, EditText> editTextHashMap = editContentAdapter.getEditTextHashMap();
                    ArrayList arrayList = new ArrayList();
                    Iterator<Integer> it2 = editTextHashMap.keySet().iterator();
                    int i6 = 0;
                    while (it2.hasNext()) {
                        int intValue = it2.next().intValue();
                        if (intValue == i6) {
                            i6++;
                            arrayList.add(editTextHashMap.get(Integer.valueOf(intValue)));
                        }
                    }
                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                    ShareUtil.shareLongPictures(detailFragmentNew.noteTmp, detailFragmentNew.title, detailFragmentNew.contentBeanList, arrayList, detailFragmentNew.mainActivity, detailFragmentNew.toggleChecklistView, DetailFragmentNew.this.currentFontBean, DetailFragmentNew.this.topTimeText);
                }
            }
        }

        AnonymousClass37() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
        public void shareAsLongPic(Note note) {
            new Timer().schedule(new TimerTask() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.37.1
                AnonymousClass1() {
                }

                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    EditContentAdapter editContentAdapter = DetailFragmentNew.this.contentAdapter;
                    if (editContentAdapter != null) {
                        HashMap<Integer, EditText> editTextHashMap = editContentAdapter.getEditTextHashMap();
                        ArrayList arrayList = new ArrayList();
                        Iterator<Integer> it2 = editTextHashMap.keySet().iterator();
                        int i6 = 0;
                        while (it2.hasNext()) {
                            int intValue = it2.next().intValue();
                            if (intValue == i6) {
                                i6++;
                                arrayList.add(editTextHashMap.get(Integer.valueOf(intValue)));
                            }
                        }
                        DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                        ShareUtil.shareLongPictures(detailFragmentNew.noteTmp, detailFragmentNew.title, detailFragmentNew.contentBeanList, arrayList, detailFragmentNew.mainActivity, detailFragmentNew.toggleChecklistView, DetailFragmentNew.this.currentFontBean, DetailFragmentNew.this.topTimeText);
                    }
                }
            }, 100L);
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
        public void shareAsPdf(Note note) {
            if (App.isVip() || App.is6hFreeTry()) {
                DetailFragmentNew.this.exportPDF();
            } else {
                VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "pdf");
            }
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
        public void shareOnlyImg(Note note) {
            ShareUtil.shareOnlyPic(new Note(DetailFragmentNew.this.noteTmp), DetailFragmentNew.this.mainActivity);
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
        public void shareOnlyText(Note note) {
            Note note2 = new Note(DetailFragmentNew.this.noteTmp);
            note2.setTitle(DetailFragmentNew.this.getNoteTitle());
            String title = note2.getTitle();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i6 = 0; i6 < DetailFragmentNew.this.contentBeanList.size(); i6++) {
                stringBuffer.append(DetailFragmentNew.this.contentBeanList.get(i6).getContent());
                stringBuffer.append("\n");
            }
            String str = title + System.getProperty("line.separator") + stringBuffer;
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType(HTTP.PLAIN_TEXT_TYPE);
            intent.putExtra("android.intent.extra.SUBJECT", title);
            intent.putExtra("android.intent.extra.TEXT", str);
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            detailFragmentNew.startActivity(Intent.createChooser(intent, detailFragmentNew.getResources().getString(R.string.share)));
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
        public void shareRecorings(Note note) {
            ShareUtil.shareRecordings(new Note(DetailFragmentNew.this.noteTmp), DetailFragmentNew.this.mainActivity);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$38 */
    class AnonymousClass38 implements DialogAddCategory.OnLockingInterface {
        final /* synthetic */ ImageView val$lockImg;
        final /* synthetic */ TextView val$lockTv;
        final /* synthetic */ Note val$note;

        AnonymousClass38(Note note, ImageView imageView, TextView textView) {
            r2 = note;
            r3 = imageView;
            r4 = textView;
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
        public void clickTryItOnce() {
            DetailFragmentNew.this.clickAddLock(r2, r3, r4);
            FirebaseReportUtils.getInstance().reportNew("iap_lock_try");
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
        public void clickUpgradeVip() {
            VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "lock_note");
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$39 */
    class AnonymousClass39 implements DialogLockFragment.OnUnlockStateInterface {
        final /* synthetic */ ImageView val$lockImg;
        final /* synthetic */ TextView val$lockTv;
        final /* synthetic */ Note val$note;

        AnonymousClass39(Note note, ImageView imageView, TextView textView) {
            r2 = note;
            r3 = imageView;
            r4 = textView;
        }

        @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
        public void setPwdSucceed() {
            DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
        }

        @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
        public void unlockSucceed(boolean z6) {
            DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$4 */
    class AnonymousClass4 implements DialogAddCategory.TimerChangedListener {
        AnonymousClass4() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
        public void doRingTonChoose() {
            Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
            intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
            intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
            DetailFragmentNew.this.startActivityForResult(intent, 7);
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
        public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
            DetailFragmentNew.this.noteTmp.setShowDate(Long.valueOf(j6));
            DetailFragmentNew.this.noteTmp.setCalendarCreate(1);
            DetailFragmentNew.this.noteTmp.setLastModification(Long.valueOf(System.currentTimeMillis()));
            DetailFragmentNew.this.isCalendarActivityComeIn = true;
            try {
                String str = Constants.dateFormatList[EasyNoteManager.getInstance().getDefaultDateIndex()];
                if (System.currentTimeMillis() - j6 < 31536000000L) {
                    str = Constants.shortDateFormatList[EasyNoteManager.getInstance().getDefaultDateIndex()];
                }
                DetailFragmentNew.this.topTimeText.setText(new SimpleDateFormat(str).format(Long.valueOf(j6)) + ", " + DateUtils.formatDateTime(DetailFragmentNew.this.mainActivity, j6, 1));
                DetailFragmentNew.this.lastTime = j6;
                FirebaseReportUtils.getInstance().reportNew("edit_date_change_success");
            } catch (Exception unused) {
            }
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            detailFragmentNew.isBackOrSave = true;
            detailFragmentNew.timeModified = true;
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$40 */
    class AnonymousClass40 implements DialogSetPwd.OnUnlockStateInterface {
        final /* synthetic */ ImageView val$lockImg;
        final /* synthetic */ TextView val$lockTv;
        final /* synthetic */ Note val$note;

        AnonymousClass40(Note note, ImageView imageView, TextView textView) {
            r2 = note;
            r3 = imageView;
            r4 = textView;
        }

        @Override // notes.easy.android.mynotes.view.setpw.DialogSetPwd.OnUnlockStateInterface
        public void setPwdSucceed() {
            DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
        }

        @Override // notes.easy.android.mynotes.view.setpw.DialogSetPwd.OnUnlockStateInterface
        public void unlockSucceed(boolean z6) {
            DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$41 */
    class AnonymousClass41 implements HighLightAdapter.OnListCallback {
        final /* synthetic */ View val$highlightBg;

        AnonymousClass41(View view) {
            r2 = view;
        }

        @Override // notes.easy.android.mynotes.models.adapters.HighLightAdapter.OnListCallback
        public void onHighLightColorClick(View view, String str) {
            DetailFragmentNew.this.highLightColor = str;
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            detailFragmentNew.isBackOrSave = true;
            detailFragmentNew.setSaveIconColor();
            if (TextUtils.isEmpty(DetailFragmentNew.this.highLightColor)) {
                r2.setBackgroundColor(Color.parseColor(HighLightAdapter.HIGHLIGHT_COLOR_SHOW[0]));
            } else {
                r2.setBackgroundColor(Color.parseColor(DetailFragmentNew.this.highLightColor));
            }
            FirebaseReportUtils.getInstance().reportNew("edit_tool_font_highlight");
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$44 */
    class AnonymousClass44 implements Runnable {
        AnonymousClass44() {
        }

        @Override // java.lang.Runnable
        public void run() {
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            if (detailFragmentNew.goBack || !detailFragmentNew.hasSaveSuccess || System.currentTimeMillis() - DetailFragmentNew.this.lastSaveTime < 40000) {
                return;
            }
            if (TextUtils.isEmpty(DetailFragmentNew.this.noteTmp.getTitle()) && TextUtils.isEmpty(DetailFragmentNew.this.getNoteContent()) && DetailFragmentNew.this.noteTmp.getAttachmentsList().isEmpty()) {
                return;
            }
            DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
            detailFragmentNew2.saveNote(detailFragmentNew2, false);
            DetailFragmentNew.this.lastSaveTime = System.currentTimeMillis();
            if (DetailFragmentNew.this.autoSaveType <= 20) {
                DetailFragmentNew.this.autoSaveType = 40L;
            }
            DetailFragmentNew.this.hasSaveSuccess = true;
            DetailFragmentNew.this.mHander.postDelayed(this, DetailFragmentNew.this.autoSaveType * 1000);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$5 */
    class AnonymousClass5 extends TimerTask {
        AnonymousClass5() {
        }

        public /* synthetic */ void lambda$run$0() {
            DetailFragmentNew.this.contentAdapter.cursorOne();
            DetailFragmentNew.this.clipboard(true);
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (DetailFragmentNew.this.anyDialogShow) {
                return;
            }
            DetailFragmentNew.this.mainActivity.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.l
                @Override // java.lang.Runnable
                public void run() {
                    DetailFragmentNew.AnonymousClass5.this.lambda$run$0();
                }
            });
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$6 */
    class AnonymousClass6 implements EditContentAdapter.RecordGridActionInterface {
        AnonymousClass6() {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$7 */
    class AnonymousClass7 implements EditTextMenuListener {
        AnonymousClass7() {
        }

        @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
        public boolean onMenuDismiss() {
            DetailFragmentNew.this.mBottomBar.hideSelectedState();
            DetailFragmentNew.this.setSaveIconColor();
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            detailFragmentNew.isBackOrSave = true;
            detailFragmentNew.showSavingView();
            return false;
        }

        @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
        public boolean onMenuShow() {
            DetailFragmentNew.this.onMenuShow();
            return false;
        }

        @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
        public boolean onSelectedAreChanged(int i6, int i7) {
            DetailFragmentNew.this.onSelectedAreChanged(i6, i7);
            return false;
        }

        @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
        public void updateURState(boolean z6, boolean z7) {
            DetailFragmentNew.this.onCanDo(z6, z7);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$8 */
    class AnonymousClass8 extends LinearLayoutManager {
        AnonymousClass8(Context context, int i6, boolean z6) {
            super(context, i6, z6);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public boolean canScrollVertically() {
            return false;
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$9 */
    class AnonymousClass9 implements DialogAddCategory.Positive1Listener<String> {
        final /* synthetic */ String val$str;

        AnonymousClass9(String str) {
            r2 = str;
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
        public void positiveClick(String str) {
            DetailFragmentNew.this.currentTagList.remove(r2);
            if (DetailFragmentNew.this.tagsAdapter != null) {
                DetailFragmentNew.this.tagsAdapter.notifyDataSetChanged();
            }
            if (DetailFragmentNew.this.currentTagList.size() == 0) {
                DetailFragmentNew.this.tagRecycleView.setVisibility(View.GONE);
            }
            StringBuilder sb = new StringBuilder();
            for (int i6 = 0; i6 < DetailFragmentNew.this.currentTagList.size(); i6++) {
                sb.append(DetailFragmentNew.this.currentTagList.get(i6));
                sb.append(",");
            }
            DetailFragmentNew.this.noteTmp.setTags(sb.toString());
            DetailFragmentNew.this.timeModified = true;
        }
    }

    public DetailFragmentNew() {
        boolean z6 = App.userConfig.getThemeState() == 1 || (App.userConfig.getThemeState() == 2 && DeviceUtils.getNightMode(App.getAppContext()) == 33);
        this.isDark = z6;
        this.isEdit = true;
        this.checkImgResource = R.drawable.ic_edit_save;
        this.pinViewResource = z6 ? R.drawable.ic_flag_blue2_white : R.drawable.ic_flag_blue2;
        this.categoryArrowDownResource = R.drawable.ic_arrow_drop_down_blue;
        this.categoryArrowUpResource = R.drawable.ic_arrow_drop_up_blue;
        this.audioList = new ArrayList();
        this.imageList = new ArrayList();
        this.currentFontName = "";
        this.currentFontBean = null;
        this.originFontStyle = "";
        this.isWidgetHideBoard = false;
        this.isNewCreate = true;
        this.hasSavedFromCreate = false;
        this.mBaseContext = null;
        this.checkSize = 0;
        this.startIndex = 0;
        this.fontStyleChange = false;
        this.bgChanged = false;
        this.timeModified = false;
        this.alarmChanged = false;
        this.defaultContentStyle = "";
        this.point = new ArrayList();
        this.strikethrough = new ArrayList();
        this.underLine = new ArrayList();
        this.fontSizeLine = new ArrayList();
        this.ColorPoint = new ArrayList();
        this.background = new ArrayList();
        this.fontColorLine = new ArrayList();
        this.bulletLine = new ArrayList();
        this.stringBuilder = new StringBuilder();
        this.baseEntrys = new ArrayList();
        this.mHander = new Handler();
        this.freeTryDialogShow = false;
        this.timesSwitch = 2L;
        this.lastSaveTime = 0L;
        this.clipboardDown = 0;
        this.lastTime = System.currentTimeMillis();
        this.isCalendarActivityComeIn = false;
        this.contentBeanList = new ArrayList();
        this.contentOriginal = new ArrayList();
        this.contentSearched = "";
        this.contentUndo = new ArrayList();
        this.contentRedo = new ArrayList();
        this.beginEdit = false;
        this.mCurrentFontIndex = Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(this.userPreferences.getDefaultFloatSize()));
        this.mCurrentFontColor = Constants.DEFAULT_TEXT_COLOR;
        this.userFoucChanged = false;
        this.hasSedLocked = false;
        this.finalUrlClickable = "";
        this.clickedView = this.content;
        this.adShowed = false;
        this.isDrawOrCheck = false;
        this.mLastNoteBgTabSelectIndex = 1;
        this.isLineTheme_ = false;
        this.line_ = null;
        this.isSaveBg = true;
        this.mLastNoteBg = null;
        this.mOriginalNoteBg = null;
        this.mRecentNoteBg = null;
        this.mRefreshHandler = new Handler() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.1
            AnonymousClass1() {
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (DetailFragmentNew.this.contentRecycler != null) {
                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                    if (detailFragmentNew.contentAdapter != null && !detailFragmentNew.contentRecycler.isComputingLayout()) {
                        int i6 = message.what;
                        if (i6 == 0) {
                            DetailFragmentNew.this.contentAdapter.notifyDataSetChanged();
                            return;
                        }
                        if (i6 == 1) {
                            DetailFragmentNew.this.contentAdapter.notifyItemInserted(message.arg1);
                            return;
                        }
                        if (i6 != 2) {
                            if (i6 != 3) {
                                return;
                            }
                            DetailFragmentNew.this.contentAdapter.notifyItemRemoved(message.arg1);
                            return;
                        } else {
                            Object obj = message.obj;
                            if ((obj instanceof Boolean) && ((Boolean) obj).booleanValue()) {
                                DetailFragmentNew.this.contentAdapter.setCursorEditingStop(true);
                            }
                            DetailFragmentNew.this.contentAdapter.notifyItemChanged(message.arg1);
                            return;
                        }
                    }
                }
                DetailFragmentNew.this.mRefreshHandler.sendMessageDelayed(message, 50L);
            }
        };
        this.textLinkClickListener = new TextLinkClickListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.2

            /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$2$1 */
            class AnonymousClass1 implements DialogCancelInterface {
                AnonymousClass1() {
                }

                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                public void confirmDelete() {
                    FirebaseReportUtils.getInstance().reportNew("edit_url_open");
                    try {
                        if (TextUtils.isEmpty(DetailFragmentNew.this.finalUrlClickable)) {
                            return;
                        }
                        if (!DetailFragmentNew.this.finalUrlClickable.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                            DetailFragmentNew.this.finalUrlClickable = "http://" + DetailFragmentNew.this.finalUrlClickable;
                        }
                        DetailFragmentNew.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(DetailFragmentNew.this.finalUrlClickable)));
                    } catch (Exception unused) {
                    }
                }

                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                public void doNothing() {
                    FirebaseReportUtils.getInstance().reportNew("edit_url_edit");
                    try {
                        KeyboardUtils.showKeyboardDontChange(DetailFragmentNew.this.clickedView);
                    } catch (Exception unused) {
                    }
                }
            }

            AnonymousClass2() {
            }

            @Override // it.feio.android.pixlui.links.TextLinkClickListener
            public void onTextLinkClick(View view, String str, String str2) {
                if (TextUtils.isEmpty(str) || DetailFragmentNew.this.noteTmp.isChecklist().booleanValue()) {
                    return;
                }
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                EditText editText = detailFragmentNew.content;
                detailFragmentNew.clickedView = editText;
                if (!editText.hasFocus()) {
                    int i6 = 0;
                    while (true) {
                        if (i6 >= DetailFragmentNew.this.baseEntrys.size()) {
                            break;
                        }
                        EditText editText2 = ((BaseEditView) DetailFragmentNew.this.baseEntrys.get(i6)).getmEditText();
                        if (editText2.hasFocus()) {
                            DetailFragmentNew.this.clickedView = editText2;
                            break;
                        }
                        i6++;
                    }
                }
                DetailFragmentNew.this.finalUrlClickable = str;
                if (!str.startsWith(HttpHost.DEFAULT_SCHEME_NAME) && !str.startsWith("www")) {
                    if (str.contains("www")) {
                        int indexOf = DetailFragmentNew.this.finalUrlClickable.indexOf("www");
                        DetailFragmentNew.this.finalUrlClickable = str.substring(indexOf);
                    } else if (str.contains(HttpHost.DEFAULT_SCHEME_NAME)) {
                        int indexOf2 = DetailFragmentNew.this.finalUrlClickable.indexOf(HttpHost.DEFAULT_SCHEME_NAME, 0);
                        DetailFragmentNew.this.finalUrlClickable = str.substring(indexOf2);
                    }
                }
                KeyboardUtils.hideKeyboard(DetailFragmentNew.this.clickedView);
                DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
                DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                dialogAddCategory.showHylinkClickDialog(detailFragmentNew2.mainActivity, detailFragmentNew2.finalUrlClickable, new DialogCancelInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.2.1
                    AnonymousClass1() {
                    }

                    @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                    public void confirmDelete() {
                        FirebaseReportUtils.getInstance().reportNew("edit_url_open");
                        try {
                            if (TextUtils.isEmpty(DetailFragmentNew.this.finalUrlClickable)) {
                                return;
                            }
                            if (!DetailFragmentNew.this.finalUrlClickable.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                                DetailFragmentNew.this.finalUrlClickable = "http://" + DetailFragmentNew.this.finalUrlClickable;
                            }
                            DetailFragmentNew.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(DetailFragmentNew.this.finalUrlClickable)));
                        } catch (Exception unused) {
                        }
                    }

                    @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                    public void doNothing() {
                        FirebaseReportUtils.getInstance().reportNew("edit_url_edit");
                        try {
                            KeyboardUtils.showKeyboardDontChange(DetailFragmentNew.this.clickedView);
                        } catch (Exception unused) {
                        }
                    }
                });
                FirebaseReportUtils.getInstance().reportNew("edit_url_click");
            }
        };
        this.currentTagList = new ArrayList<>();
        this.searchedEditList = new ArrayList();
        this.searchEditIndex = new ArrayList();
        this.needSearch = false;
        this.hasSaveSuccess = true;
        this.WidgetSave = true;
        this.currentFontAbsoluteSize = ScreenUtils.dpToPx(16);
        this.onFocusChangeListener = new View.OnFocusChangeListener() { // from class: b6.d2
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z7) {
                DetailFragmentNew.this.lambda$new$43(view, z7);
            }
        };
        this.colorDialogConfirm = false;
        this.amplitudeTimer = new Timer();
        this.durationTimer = new Timer();
        this.AMPLITUDE_UPDATE_MS = 75L;
        this.duration = 0;
        this.changedFont = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        this.highLightColor = "";
        this.isBold = false;
        this.isItalic = false;
        this.isUnderline = false;
        this.isStrikethrough = false;
        this.isSaved = false;
        this.showLockRed = false;
        this.showRemindRed = false;
        this.showShareRed = false;
        this.showPdfRed = false;
        this.showReadingRed = false;
        this.autoSaveRunnable = new Runnable() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.44
            AnonymousClass44() {
            }

            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                if (detailFragmentNew.goBack || !detailFragmentNew.hasSaveSuccess || System.currentTimeMillis() - DetailFragmentNew.this.lastSaveTime < 40000) {
                    return;
                }
                if (TextUtils.isEmpty(DetailFragmentNew.this.noteTmp.getTitle()) && TextUtils.isEmpty(DetailFragmentNew.this.getNoteContent()) && DetailFragmentNew.this.noteTmp.getAttachmentsList().isEmpty()) {
                    return;
                }
                DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                detailFragmentNew2.saveNote(detailFragmentNew2, false);
                DetailFragmentNew.this.lastSaveTime = System.currentTimeMillis();
                if (DetailFragmentNew.this.autoSaveType <= 20) {
                    DetailFragmentNew.this.autoSaveType = 40L;
                }
                DetailFragmentNew.this.hasSaveSuccess = true;
                DetailFragmentNew.this.mHander.postDelayed(this, DetailFragmentNew.this.autoSaveType * 1000);
            }
        };
        this.mPressedTime = 0L;
    }

    static /* synthetic */ int access$4308(DetailFragmentNew detailFragmentNew) {
        int i6 = detailFragmentNew.duration;
        detailFragmentNew.duration = i6 + 1;
        return i6;
    }

    private void addAttachment(Attachment attachment) {
        this.noteTmp.addAttachment(attachment);
        if (attachment.getMime_type() != null) {
            if (attachment.getMime_type().equals("audio/amr")) {
                this.audioList.add(attachment);
                return;
            }
            this.imageList.add(attachment);
            PicGridAdapter picGridAdapter = this.mAttachmentAdapter;
            if (picGridAdapter != null) {
                picGridAdapter.notifyDataSetChanged();
            }
        }
    }

    private void addCheckItem() {
        showSavingView();
        try {
            if (this.noteTmp.isChecklist().booleanValue()) {
                ((CheckListView) this.toggleChecklistView).addHintItem();
            }
        } catch (Exception unused) {
        }
    }

    private void addNewChecklistView(boolean z6) {
        boolean checkSort = App.userConfig.getCheckSort();
        LinearLayout linearLayout = this.contentLayout;
        linearLayout.addView(this.toggleChecklistView2, linearLayout.getChildCount() - 1);
        ChecklistManager checklistManager = this.mChecklistManager2;
        if (checklistManager == null) {
            checklistManager = new ChecklistManager(this.mainActivity);
        }
        this.mChecklistManager2 = checklistManager;
        checklistManager.showCheckMarks(true).newEntryHint(StringUtils.SPACE).keepChecked(true).moveCheckedOnBottom(checkSort ? 1 : 0);
        this.mChecklistManager2.addTextChangedListener(this.mFragment);
        this.mChecklistManager2.setCheckListChangedListener(this.mFragment);
        View view = null;
        try {
            view = this.mChecklistManager2.convert(this.toggleChecklistView2);
            if (ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg()) {
                this.exitView.postDelayed(new Runnable() { // from class: b6.y2
                    @Override // java.lang.Runnable
                    public void run() {
                        DetailFragmentNew.this.lambda$addNewChecklistView$37();
                    }
                }, 150L);
            } else if (this.noteTmp.isChecklist().booleanValue()) {
                this.exitView.postDelayed(new Runnable() { // from class: b6.z2
                    @Override // java.lang.Runnable
                    public void run() {
                        DetailFragmentNew.this.lambda$addNewChecklistView$38();
                    }
                }, 150L);
            }
        } catch (Exception e7) {
            MyLog.e("Error switching checklist view", e7);
        }
        if (view != null) {
            this.mChecklistManager2.replaceViews(this.toggleChecklistView2, view);
            this.toggleChecklistView2 = view;
            this.checkSize = 2;
            ViewCompat.animate(view).alpha(1.0f).scaleXBy(0.0f).scaleX(1.0f).scaleYBy(0.0f).scaleY(1.0f);
        }
        initCheckListCustomEmoji(this.mChecklistManager2);
    }

    private void applayFontStyle(final String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        App.executeOnGlobalExecutor(new Runnable() { // from class: b6.u3
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$applayFontStyle$15(str);
            }
        });
    }

    private void applyCharacterStyle(EditText editText, Editable editable, String str, boolean z6) {
        if (editable == null || TextUtils.isEmpty(str)) {
            return;
        }
        App.executeOnGlobalExecutor(new AnonymousClass12(str, editable, editText, z6));
    }

    @SuppressLint({"NewApi"})
    public void archiveNote(boolean z6) {
        if (this.noteTmp.get_id() == null) {
            goHome();
            return;
        }
        this.noteTmp.setArchived(Boolean.valueOf(z6));
        if (z6) {
            this.noteTmp.setCategory(new Category().makeDefaultCategory());
        }
        this.goBack = true;
        saveNote(this, true);
        Toast.makeText(this.mainActivity, R.string.note_archived, 0).show();
    }

    private boolean attachmentChanged() {
        if (this.noteTmp.getAttachmentsList() == null && this.noteOriginal.getAttachmentsList() != null) {
            return true;
        }
        if (this.noteTmp.getAttachmentsList() == null || this.noteOriginal.getAttachmentsList() != null) {
            return (this.noteTmp.getAttachmentsList() != null || this.noteOriginal.getAttachmentsList() != null) && this.noteTmp.getAttachmentsList() != null && this.noteOriginal.getAttachmentsList() != null && this.noteTmp.getAttachmentsList().size() != this.noteOriginal.getAttachmentsList().size();
        }
        return true;
    }

    private boolean baseSttHasFocus() {
        boolean z6 = false;
        for (int i6 = 0; i6 < this.baseEntrys.size() && !(z6 = this.baseEntrys.get(i6).isFocusable()); i6++) {
        }
        return z6;
    }

    public void broadcastDuration() {
        DialogRecord.INSTANCE.updateRecordingDuration(this.duration);
    }

    private void broadcastRecorderInfo() {
        broadcastDuration();
        startAmplitudeUpdates();
    }

    public void bubbleLockState(Note note, ImageView imageView, TextView textView) {
        if (note.isLocked().booleanValue()) {
            note.setLocked(Boolean.FALSE);
            imageView.setImageResource(this.isDark ? R.drawable.ic_unlock_blue2_white : R.drawable.ic_unlock_blue2);
            textView.setText(R.string.lock);
            Toast.makeText(App.getAppContext(), R.string.unlock_success, 0).show();
            return;
        }
        note.setLocked(Boolean.TRUE);
        if (StringUtils.isNotEmpty(this.userPreferences.getPatternPassword())) {
            note.setPattern(this.userPreferences.getPatternPassword());
        } else if (StringUtils.isNotEmpty(this.userPreferences.getPwdCode())) {
            note.setLatitude(this.userPreferences.getPwdCode());
        }
        imageView.setImageResource(R.drawable.ic_lock_blue_menu);
        textView.setText(R.string.lock_remove);
        Toast.makeText(App.getAppContext(), R.string.add_success, 0).show();
        this.hasSedLocked = true;
        goHome();
    }

    private void changeBaseViewColor(int i6) {
        EditContentAdapter editContentAdapter = this.contentAdapter;
        if (editContentAdapter != null) {
            editContentAdapter.setDarkColor(ContextCompat.getColor(this.mainActivity, R.color.white_88alpha_E0fff) == i6);
        }
        for (int i7 = 0; i7 < this.baseEntrys.size(); i7++) {
            this.baseEntrys.get(i7).getmEditText().setTextColor(i6);
            try {
                this.baseEntrys.get(i7).getmEditText().setHintTextColor(ColorUtils.setAlphaComponent(i6, 64));
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    private void checkAllEditTextSpan(EditText editText, Editable editable, boolean z6) {
        Object[] objArr;
        MyBulletSpan[] myBulletSpanArr;
        if (editable != null) {
            Object[] objArr2 = editable.getSpans(0, editable.length(), StrikethroughSpan.class);
            Object[] objArr3 = editable.getSpans(0, editable.length(), UnderlineSpan.class);
            BackgroundColorSpan[] backgroundColorSpanArr = editable.getSpans(0, editable.length(), BackgroundColorSpan.class);
            RelativeSizeSpan[] relativeSizeSpanArr = editable.getSpans(0, editable.length(), RelativeSizeSpan.class);
            AbsoluteSizeSpan[] absoluteSizeSpanArr = editable.getSpans(0, editable.length(), AbsoluteSizeSpan.class);
            ForegroundColorSpan[] foregroundColorSpanArr = editable.getSpans(0, editable.length(), ForegroundColorSpan.class);
            StyleSpan[] styleSpanArr = editable.getSpans(0, editable.length(), StyleSpan.class);
            MyBulletSpan[] myBulletSpanArr2 = (MyBulletSpan[]) editable.getSpans(0, editable.length(), MyBulletSpan.class);
            if (myBulletSpanArr2 != null) {
                this.bulletLine.clear();
                int i6 = 0;
                while (i6 < myBulletSpanArr2.length) {
                    MyBulletSpan myBulletSpan = myBulletSpanArr2[i6];
                    if (myBulletSpan == null) {
                        myBulletSpanArr = myBulletSpanArr2;
                    } else {
                        String str = "digital".equalsIgnoreCase(myBulletSpan.getNlName()) ? Constants.SPAN_BULLET_Z : "Dots".equalsIgnoreCase(myBulletSpanArr2[i6].getNlName()) ? Constants.SPAN_BULLET_D : "checkList_selected".equalsIgnoreCase(myBulletSpanArr2[i6].getNlName()) ? Constants.SPAN_BULLET_CHECK_BOX : ("checkList_no".equalsIgnoreCase(myBulletSpanArr2[i6].getNlName()) || "checkList_no_white".equalsIgnoreCase(myBulletSpanArr2[i6].getNlName())) ? Constants.SPAN_BULLET_CHECK_BOX_N : "";
                        int spanStart = editable.getSpanStart(myBulletSpan);
                        myBulletSpanArr = myBulletSpanArr2;
                        int currentCursorLine = TextHelper.getCurrentCursorLine(editText, spanStart);
                        String str2 = "" + spanStart;
                        if (!this.bulletLine.contains(str2)) {
                            this.bulletLine.add(str2);
                            if (!TextUtils.isEmpty(str)) {
                                StringBuilder sb = this.stringBuilder;
                                sb.append(str);
                                sb.append(currentCursorLine);
                                sb.append(",");
                            }
                        }
                    }
                    i6++;
                    myBulletSpanArr2 = myBulletSpanArr;
                }
            }
            if (objArr2 != null) {
                this.strikethrough.clear();
                int i7 = 0;
                while (i7 < objArr2.length) {
                    Object obj = objArr2[i7];
                    if (obj == null) {
                        objArr = objArr2;
                    } else {
                        int spanStart2 = editable.getSpanStart(obj);
                        int spanEnd = editable.getSpanEnd(obj);
                        String str3 = "" + spanStart2 + spanEnd;
                        objArr = objArr2;
                        if (!this.strikethrough.contains(str3) && spanEnd > spanStart2) {
                            this.strikethrough.add(str3);
                            try {
                                StringBuilder sb2 = this.stringBuilder;
                                sb2.append(Constants.SPAN_STRIKETHROUGH);
                                sb2.append(spanStart2);
                                sb2.append("/");
                                sb2.append(spanEnd);
                                sb2.append(",");
                            } catch (Exception unused) {
                            }
                        }
                    }
                    i7++;
                    objArr2 = objArr;
                }
            }
            if (objArr3 != null) {
                this.underLine.clear();
                for (Object obj2 : objArr3) {
                    if (obj2 != null) {
                        int spanStart3 = editable.getSpanStart(obj2);
                        int spanEnd2 = editable.getSpanEnd(obj2);
                        String str4 = "" + spanStart3 + spanEnd2;
                        if (!this.underLine.contains(str4) && spanEnd2 > spanStart3) {
                            this.underLine.add(str4);
                            try {
                                StringBuilder sb3 = this.stringBuilder;
                                sb3.append(Constants.SPAN_U);
                                sb3.append(spanStart3);
                                sb3.append("/");
                                sb3.append(spanEnd2);
                                sb3.append(",");
                            } catch (Exception unused2) {
                            }
                        }
                    }
                }
            }
            if (foregroundColorSpanArr != null) {
                this.fontColorLine.clear();
                for (ForegroundColorSpan foregroundColorSpan : foregroundColorSpanArr) {
                    if (foregroundColorSpan != null) {
                        int spanStart4 = editable.getSpanStart(foregroundColorSpan);
                        int spanEnd3 = editable.getSpanEnd(foregroundColorSpan);
                        String str5 = "" + spanStart4 + spanEnd3;
                        if (!this.fontColorLine.contains(str5) && spanEnd3 > spanStart4) {
                            this.fontColorLine.add(str5);
                            try {
                                StringBuilder sb4 = this.stringBuilder;
                                sb4.append(Constants.SPAN_FONT_COLOR);
                                sb4.append(spanStart4);
                                sb4.append("/");
                                sb4.append(spanEnd3);
                                sb4.append("/");
                                sb4.append(foregroundColorSpan.getForegroundColor());
                                sb4.append(",");
                            } catch (Exception unused3) {
                            }
                        }
                    }
                }
            }
            if (backgroundColorSpanArr != null) {
                this.background.clear();
                for (BackgroundColorSpan backgroundColorSpan : backgroundColorSpanArr) {
                    if (backgroundColorSpan != null) {
                        int spanStart5 = editable.getSpanStart(backgroundColorSpan);
                        int spanEnd4 = editable.getSpanEnd(backgroundColorSpan);
                        String str6 = "" + spanStart5 + spanEnd4;
                        if (!this.background.contains(str6) && spanEnd4 > spanStart5) {
                            this.background.add(str6);
                            try {
                                StringBuilder sb5 = this.stringBuilder;
                                sb5.append("h");
                                sb5.append(spanStart5);
                                sb5.append("/");
                                sb5.append(spanEnd4);
                                sb5.append("/");
                                sb5.append(backgroundColorSpan.getBackgroundColor());
                                sb5.append(",");
                            } catch (Exception unused4) {
                            }
                        }
                    }
                }
            }
            if (absoluteSizeSpanArr != null) {
                this.fontSizeLine.clear();
                for (AbsoluteSizeSpan absoluteSizeSpan : absoluteSizeSpanArr) {
                    if (absoluteSizeSpan != null) {
                        int spanStart6 = editable.getSpanStart(absoluteSizeSpan);
                        int spanEnd5 = editable.getSpanEnd(absoluteSizeSpan);
                        String str7 = "" + spanStart6 + spanEnd5;
                        if (!this.fontSizeLine.contains(str7) && spanEnd5 > spanStart6) {
                            this.fontSizeLine.add(str7);
                            try {
                                StringBuilder sb6 = this.stringBuilder;
                                sb6.append("f");
                                sb6.append(spanStart6);
                                sb6.append("/");
                                sb6.append(spanEnd5);
                                sb6.append("/");
                                sb6.append(absoluteSizeSpan.getSize());
                                sb6.append(",");
                            } catch (Exception unused5) {
                            }
                        }
                    }
                }
            }
            if (styleSpanArr != null) {
                this.ColorPoint.clear();
                for (StyleSpan styleSpan : styleSpanArr) {
                    if (styleSpan != null) {
                        int spanStart7 = editable.getSpanStart(styleSpan);
                        int spanEnd6 = editable.getSpanEnd(styleSpan);
                        String str8 = "" + spanStart7 + spanEnd6 + styleSpan.getStyle();
                        if (!this.ColorPoint.contains(str8) && spanEnd6 > spanStart7) {
                            this.ColorPoint.add(str8);
                            try {
                                StringBuilder sb7 = this.stringBuilder;
                                sb7.append(Constants.SPAN_S);
                                sb7.append(spanStart7);
                                sb7.append("/");
                                sb7.append(spanEnd6);
                                sb7.append("/");
                                sb7.append(styleSpan.getStyle());
                                sb7.append(",");
                            } catch (Exception unused6) {
                            }
                        }
                    }
                }
                if (relativeSizeSpanArr != null) {
                    this.point.clear();
                    for (RelativeSizeSpan relativeSizeSpan : relativeSizeSpanArr) {
                        if (relativeSizeSpan != null) {
                            int spanStart8 = editable.getSpanStart(relativeSizeSpan);
                            int spanEnd7 = editable.getSpanEnd(relativeSizeSpan);
                            String str9 = "" + spanStart8 + spanEnd7;
                            if (!this.point.contains(str9) && spanEnd7 > spanStart8) {
                                if (relativeSizeSpan.getSizeChange() >= 1.1f && relativeSizeSpan.getSizeChange() <= 1.6f) {
                                    try {
                                        StringBuilder sb8 = this.stringBuilder;
                                        sb8.append(Constants.SPAN_R);
                                        sb8.append(spanStart8);
                                        sb8.append("/");
                                        sb8.append(spanEnd7);
                                        sb8.append("/");
                                        sb8.append(relativeSizeSpan.getSizeChange());
                                        sb8.append(",");
                                    } catch (Exception unused7) {
                                    }
                                }
                                this.point.add(str9);
                            }
                        }
                    }
                }
                if (z6) {
                    try {
                        this.stringBuilder.append(Marker.ANY_NON_NULL_MARKER);
                    } catch (Exception unused8) {
                    }
                }
            }
        }
    }

    private void checkNewCreateAndFirebase() {
        if (!TextUtils.isEmpty(this.noteTmp.getTitle()) || !TextUtils.isEmpty(this.noteTmp.getContent()) || (this.noteTmp.getAttachmentsList() != null && this.noteTmp.getAttachmentsList().size() > 0)) {
            this.isNewCreate = false;
            return;
        }
        Bundle bundle = new Bundle();
        EditActivity editActivity = this.mainActivity;
        String str = editActivity.editFrom;
        if (editActivity == null) {
            str = Constants.CATEGORY_HOME;
        }
        bundle.putString("pr_status", str);
        FirebaseReportUtils.getInstance().reportNew("M_create_show", bundle);
    }

    private void checkNoteLock(Note note) {
        this.noteTmp.setPasswordChecked(true);
        init();
    }

    private void checkNotifyAction() {
        int i6 = this.mainActivity.notifyType;
        if (i6 == DailyReminderReceiver.NEW_EMOJI) {
            bottomEmoji();
            FirebaseReportUtils.getInstance().reportNew("notify_new_emoji_click");
            return;
        }
        if (i6 == DailyReminderReceiver.NEW_FONT) {
            showFontDialog();
            FirebaseReportUtils.getInstance().reportNew("notify_new_font_click");
            return;
        }
        if (i6 == DailyReminderReceiver.NEW_DRAWING_BG) {
            bottomDrawImg();
            FirebaseReportUtils.getInstance().reportNew("notify_new_drawing_bg_click");
            return;
        }
        if (i6 == DailyReminderReceiver.NEW_DRAWING_PEN) {
            bottomDrawImg();
            FirebaseReportUtils.getInstance().reportNew("notify_new_drawing_pen_click");
            return;
        }
        if (i6 == 1) {
            choosePic();
            return;
        }
        if (i6 == DailyReminderReceiver.RECORD) {
            recordPermission();
        } else if (i6 == DailyReminderReceiver.EDIT_PIC) {
            choosePic();
        } else if (i6 == 1) {
            useCheckList();
        }
    }

    public void clipboard(boolean z6) {
        if (z6) {
            if (this.mainActivity.getWindow().getDecorView().findFocus() instanceof EditText) {
                this.detailFragmentEditText = (EditText) this.mainActivity.getWindow().getDecorView().findFocus();
            }
            if (StringUtils.isEmpty(App.userConfig.getClipboardContent())) {
                if (StringUtils.isEmpty(SystemHelper.getClipboardContent(this.mainActivity))) {
                    this.isShowClipboardView = false;
                    return;
                } else {
                    this.isShowClipboardView = true;
                    App.userConfig.setClipboardContent(SystemHelper.getClipboardContent(this.mainActivity));
                }
            } else if (SystemHelper.getClipboardContent(this.mainActivity).equals(App.userConfig.getClipboardContent())) {
                this.isShowClipboardView = false;
            } else {
                this.isShowClipboardView = true;
                App.userConfig.setClipboardContent(SystemHelper.getClipboardContent(this.mainActivity));
            }
            if (this.isShowClipboardView && App.userConfig.getClipboardSwitch()) {
                this.mBottomBar.setClipboardViewDisplayTime(ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg());
            }
        }
    }

    private boolean colorChanged() {
        if (this.noteOriginal.getBgId() != this.noteTmp.getBgId()) {
            return true;
        }
        return this.noteTmp.getBgId() == 10 && !TextUtils.equals(this.noteOriginal.getStickyColor(), this.noteTmp.getStickyColor());
    }

    private void customDate() {
        FirebaseReportUtils.getInstance().reportNew("edit_date_change_click");
        DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
        Note note = this.noteTmp;
        dialogAddCategory.showEditTimeDialog(false, note, this.mainActivity, note.getRecurrenceRule(), R.string.edit_time_edit, new DialogAddCategory.TimerChangedListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.4
            AnonymousClass4() {
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
            public void doRingTonChoose() {
                Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
                intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
                DetailFragmentNew.this.startActivityForResult(intent, 7);
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
            public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
                DetailFragmentNew.this.noteTmp.setShowDate(Long.valueOf(j6));
                DetailFragmentNew.this.noteTmp.setCalendarCreate(1);
                DetailFragmentNew.this.noteTmp.setLastModification(Long.valueOf(System.currentTimeMillis()));
                DetailFragmentNew.this.isCalendarActivityComeIn = true;
                try {
                    String str = Constants.dateFormatList[EasyNoteManager.getInstance().getDefaultDateIndex()];
                    if (System.currentTimeMillis() - j6 < 31536000000L) {
                        str = Constants.shortDateFormatList[EasyNoteManager.getInstance().getDefaultDateIndex()];
                    }
                    DetailFragmentNew.this.topTimeText.setText(new SimpleDateFormat(str).format(Long.valueOf(j6)) + ", " + DateUtils.formatDateTime(DetailFragmentNew.this.mainActivity, j6, 1));
                    DetailFragmentNew.this.lastTime = j6;
                    FirebaseReportUtils.getInstance().reportNew("edit_date_change_success");
                } catch (Exception unused) {
                }
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.isBackOrSave = true;
                detailFragmentNew.timeModified = true;
            }
        });
    }

    public void deleteRecording(Attachment attachment) {
        this.audioList.remove(attachment);
        this.recordGridAdapter.notifyDataSetChanged();
        this.noteTmp.removeAttachment(attachment);
        FirebaseReportUtils.getInstance().reportNew("record_in_notes_delete_OK");
    }

    public void doExit() {
        if (!this.isBackOrSave) {
            saveAndExit(this);
            reportSaveNoteEvent("edit_save_back");
            reportSaveNoteEvent("edit_save_all");
            FirebaseReportUtils.getInstance().reportNew("edit_default_color", Constants.THEME_EVENT_KEY, "" + this.noteTmp.getBgId());
            FirebaseReportUtils.getInstance().reportNew("edit_back");
            return;
        }
        needScrollToTop = true;
        reportSaveNoteEvent("edit_save_check");
        reportSaveNoteEvent("edit_save_all");
        FirebaseReportUtils.getInstance().reportNew("edit_default_color", Constants.THEME_EVENT_KEY, "" + this.noteTmp.getBgId());
        saveNotes();
        if (this.isNewCreate) {
            FirebaseReportUtils.getInstance().reportNew("edit_save_new_create");
        }
    }

    private void doSubViewLineLayoutState() {
        if (!this.content.isDrawLine() || this.baseEntrys.size() <= 0) {
            return;
        }
        final EditText editText = this.baseEntrys.get(this.baseEntrys.size() - 1).getmEditText();
        this.content.setMinHeight(0);
        final ArrayList arrayList = new ArrayList();
        for (int i6 = 0; i6 < this.baseEntrys.size() - 1; i6++) {
            EditText editText2 = this.baseEntrys.get(i6).getmEditText();
            arrayList.add(this.baseEntrys.get(i6));
            editText2.setMinHeight(0);
        }
        this.content.postDelayed(new Runnable() { // from class: b6.i2
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$doSubViewLineLayoutState$42(arrayList, editText);
            }
        }, 200L);
    }

    private void doTextRedo() {
        if (this.contentRedo.size() < 1) {
            return;
        }
        List<EditContentUndoRedoBean> list = this.contentRedo;
        EditContentUndoRedoBean editContentUndoRedoBean = list.get(list.size() - 1);
        String contentJson = editContentUndoRedoBean.getContentJson();
        this.contentBeanList.clear();
        Iterator<JsonElement> it2 = new JsonParser().parse(contentJson).getAsJsonArray().iterator();
        while (it2.hasNext()) {
            this.contentBeanList.add((EditContentBean) this.gson.fromJson(it2.next(), EditContentBean.class));
        }
        this.contentAdapter.setSelectedEditText(editContentUndoRedoBean.getSelectedEditText());
        EditContentAdapter.setCursorBeforeAfter(editContentUndoRedoBean.getCursorBeforeAfter());
        this.contentAdapter.setCursorEditingStop(true);
        notifyData();
        this.contentUndo.add(editContentUndoRedoBean);
        List<EditContentUndoRedoBean> list2 = this.contentRedo;
        list2.remove(list2.size() - 1);
        this.content_undo.setAlpha(this.contentUndo.size() > 1 ? 1.0f : 0.3f);
        this.content_redo.setAlpha(this.contentRedo.size() <= 0 ? 0.3f : 1.0f);
        for (int i6 = 0; i6 < this.contentRedo.size(); i6++) {
            StringBuilder sb = new StringBuilder();
            sb.append("doTextRedo: ");
            sb.append(this.gson.toJson(this.contentRedo.get(i6)));
        }
    }

    private void doTextUndo() {
        if (this.contentUndo.size() < 2) {
            return;
        }
        if (this.contentUndo.size() >= 3) {
            List<EditContentUndoRedoBean> list = this.contentUndo;
            EditContentUndoRedoBean editContentUndoRedoBean = list.get(list.size() - 2);
            List<EditContentUndoRedoBean> list2 = this.contentUndo;
            EditContentUndoRedoBean editContentUndoRedoBean2 = list2.get(list2.size() - 1);
            String contentJson = editContentUndoRedoBean.getContentJson();
            this.contentBeanList.clear();
            Iterator<JsonElement> it2 = new JsonParser().parse(contentJson).getAsJsonArray().iterator();
            while (it2.hasNext()) {
                this.contentBeanList.add((EditContentBean) this.gson.fromJson(it2.next(), EditContentBean.class));
            }
            this.contentAdapter.setSelectedEditText(editContentUndoRedoBean.getSelectedEditText());
            EditContentAdapter.setCursorBeforeAfter(editContentUndoRedoBean.getCursorBeforeAfter());
            this.contentAdapter.setCursorEditingStop(true);
            notifyData();
            this.contentRedo.add(editContentUndoRedoBean2);
            List<EditContentUndoRedoBean> list3 = this.contentUndo;
            list3.remove(list3.size() - 1);
        } else {
            EditContentUndoRedoBean editContentUndoRedoBean3 = this.contentUndo.get(0);
            EditContentUndoRedoBean editContentUndoRedoBean4 = this.contentUndo.get(1);
            String contentJson2 = editContentUndoRedoBean3.getContentJson();
            this.contentBeanList.clear();
            Iterator<JsonElement> it3 = new JsonParser().parse(contentJson2).getAsJsonArray().iterator();
            while (it3.hasNext()) {
                this.contentBeanList.add((EditContentBean) this.gson.fromJson(it3.next(), EditContentBean.class));
            }
            this.contentAdapter.setSelectedEditText(editContentUndoRedoBean3.getSelectedEditText());
            EditContentAdapter.setCursorBeforeAfter(editContentUndoRedoBean3.getCursorBeforeAfter());
            this.contentAdapter.setCursorEditingStop(true);
            notifyData();
            this.contentRedo.add(editContentUndoRedoBean4);
        }
        List<EditContentUndoRedoBean> list4 = this.contentUndo;
        list4.remove(list4.size() - 1);
        this.content_undo.setAlpha(this.contentUndo.size() > 1 ? 1.0f : 0.3f);
        this.content_redo.setAlpha(this.contentRedo.size() <= 0 ? 0.3f : 1.0f);
        for (int i6 = 0; i6 < this.contentUndo.size(); i6++) {
            StringBuilder sb = new StringBuilder();
            sb.append("doTextUndo: ");
            sb.append(this.gson.toJson(this.contentUndo.get(i6)));
        }
    }

    private boolean dontShowSoftInputMethod() {
        int i6;
        return Constants.NEW_RELEASE.equalsIgnoreCase(this.mainActivity.editFrom) || (i6 = this.mainActivity.notifyType) == DailyReminderReceiver.NEW_STICK_BG || i6 == DailyReminderReceiver.NEW_EMOJI || i6 == DailyReminderReceiver.NEW_FONT || i6 == DailyReminderReceiver.NEW_DRAWING_BG || i6 == DailyReminderReceiver.NEW_DRAWING_PEN;
    }

    private void drawLines(boolean z6, NoteBgBean.Line line) {
        this.content.setDrawLine(z6);
        if (z6) {
            this.content.setLineType(line.getLineType());
            this.content.setLineColor(line.getLineColor());
            this.content.setLineWidth(ScreenUtils.dpToPx(line.getLineWidth()));
            this.content.setLineDash(ScreenUtils.dpToPx(line.getLineLength()), ScreenUtils.dpToPx(line.getLineGap()));
        }
        EditContentAdapter editContentAdapter = this.contentAdapter;
        if (editContentAdapter != null) {
            editContentAdapter.setDrawLines(z6, line);
        } else {
            this.isLineTheme_ = z6;
            this.line_ = line;
        }
        this.content.invalidate();
        if (this.baseEntrys.size() > 0) {
            for (int i6 = 0; i6 < this.baseEntrys.size(); i6++) {
                EditText editText = this.baseEntrys.get(i6).getmEditText();
                editText.setDrawLine(z6);
                if (z6) {
                    editText.setLineType(line.getLineType());
                    editText.setLineColor(line.getLineColor());
                    editText.setLineWidth(ScreenUtils.dpToPx(line.getLineWidth()));
                    editText.setLineDash(ScreenUtils.dpToPx(line.getLineLength()), ScreenUtils.dpToPx(line.getLineGap()));
                }
                editText.invalidate();
            }
        }
    }

    private void editRedShow() {
        InputHelperView inputHelperView;
        int size = DbHelper.getInstance().getNotesActive().size();
        long currentTimeMillis = System.currentTimeMillis();
        if (size >= 6 && !this.userPreferences.getPdfClick() && (currentTimeMillis - this.userPreferences.getEditRedShowTime() > 86400000 || this.userPreferences.getEditRedShowTag() > 1)) {
            setMoreRed(true);
            this.userPreferences.setTimeEditPDFRed(1);
            this.showPdfRed = true;
            this.showLockRed = false;
            this.showShareRed = false;
            this.showRemindRed = false;
            this.showReadingRed = false;
            if (!this.userPreferences.getPdfRedFirstShow()) {
                this.userPreferences.setEditViewTimes(0);
                this.userPreferences.setPdfRedFirstShow(true);
            }
            FirebaseReportUtils.getInstance().reportNew("time_red_edit_pdf_show");
            if (this.userPreferences.getEditRedShowTag() <= 1) {
                this.userPreferences.setEditRedShowTag(1);
                this.userPreferences.setEditRedShowTime(currentTimeMillis);
            }
        }
        if (size >= 6 && !this.userPreferences.getChoosePicBtnClick() && (currentTimeMillis - this.userPreferences.getEditRedShowTime() > 86400000 || this.userPreferences.getEditRedShowTag() > 2)) {
            this.mBottomBar.setPicImgRed(true);
            if (!this.userPreferences.getPicRedFirstShow()) {
                this.userPreferences.setEditViewTimes(0);
                this.userPreferences.setPicRedFirstShow(true);
            }
            FirebaseReportUtils.getInstance().reportNew("time_red_edit_pic_insert_show");
            if (this.userPreferences.getEditRedShowTag() <= 2) {
                this.userPreferences.setEditRedShowTag(2);
                this.userPreferences.setEditRedShowTime(currentTimeMillis);
            }
        }
        if (size >= 9 && !this.userPreferences.getReminderClick() && (currentTimeMillis - this.userPreferences.getEditRedShowTime() > 86400000 || this.userPreferences.getEditRedShowTag() > 3)) {
            setMoreRed(true);
            this.userPreferences.setTimeEditRemindRed(1);
            this.showRemindRed = true;
            this.showLockRed = false;
            this.showPdfRed = false;
            this.showShareRed = false;
            this.showReadingRed = false;
            if (!this.userPreferences.getRemindRedFirstShow()) {
                this.userPreferences.setEditViewTimes(0);
                this.userPreferences.setRemindRedFirstShow(true);
            }
            if (this.userPreferences.getEditRedShowTag() <= 3) {
                this.userPreferences.setEditRedShowTag(3);
                this.userPreferences.setEditRedShowTime(currentTimeMillis);
            }
        }
        if (size >= 7 && !this.userPreferences.getLockClick() && (currentTimeMillis - this.userPreferences.getEditRedShowTime() > 86400000 || this.userPreferences.getEditRedShowTag() > 4)) {
            this.userPreferences.setTimeEditLockRed(1);
            setMoreRed(true);
            this.showLockRed = true;
            this.showPdfRed = false;
            this.showShareRed = false;
            this.showRemindRed = false;
            this.showReadingRed = false;
            if (!this.userPreferences.getLockRedFirstShow()) {
                this.userPreferences.setEditViewTimes(0);
                this.userPreferences.setLockRedFirstShow(true);
            }
            if (this.userPreferences.getEditRedShowTag() <= 4) {
                this.userPreferences.setEditRedShowTag(4);
                this.userPreferences.setEditRedShowTime(currentTimeMillis);
            }
            FirebaseReportUtils.getInstance().reportNew("time_red_edit_lock_show");
        }
        if (!this.userPreferences.getReadingDialogShowed2() && (currentTimeMillis - this.userPreferences.getEditRedShowTime() > 86400000 || this.userPreferences.getEditRedShowTag() > 5)) {
            this.userPreferences.setTimeEditLockRed(1);
            setMoreRed(true);
            this.showLockRed = false;
            this.showPdfRed = false;
            this.showShareRed = false;
            this.showRemindRed = false;
            this.showReadingRed = true;
            if (this.userPreferences.getReadingDialogShowed()) {
                this.userPreferences.setEditViewTimes(0);
                this.userPreferences.setReadingDialogShowed2(true);
            }
            if (this.userPreferences.getEditRedShowTag() <= 5) {
                this.userPreferences.setEditRedShowTag(5);
                this.userPreferences.setEditRedShowTime(currentTimeMillis);
            }
        }
        if (App.userConfig.getNewUser() || App.userConfig.isNewDrawReleasePromoteShow() || (inputHelperView = this.mBottomBar) == null) {
            return;
        }
        inputHelperView.setDrawImgRed(true, true);
        this.userPreferences.getEditRedShowTime();
    }

    public void exportPDF() {
        if (this.mBaseContext == null) {
            return;
        }
        saveNote(this, false);
        if (ScreenUtils.isPad(App.getAppContext()) || !NetworkUtils.isNetworkConnected(App.getAppContext())) {
            exportPdfDirectly();
            return;
        }
        NoteBgBean noteBg = ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId());
        if (ResNoteBgManager.getInstance().hasPadRes(noteBg)) {
            exportPdfDirectly();
            return;
        }
        if (getActivity() != null) {
            showLoadingDialog(getActivity(), App.getAppContext().getResources().getString(R.string.generating_pdf));
        }
        ResNoteBgManager.getInstance().startDownloadPadZip(noteBg, new ResNoteBgManager.BgDownloadListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.24
            AnonymousClass24() {
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
            public void onDownloadFailed(NoteBgBean noteBgBean) {
                if (DetailFragmentNew.this.isLoadingDialog()) {
                    DetailFragmentNew.this.exportPdfDirectly();
                    if (DetailFragmentNew.this.getActivity() != null) {
                        DetailFragmentNew.this.hideLoadingDialog();
                    }
                }
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
            public void onDownloadSuccess(NoteBgBean noteBgBean, String str) {
                if (DetailFragmentNew.this.isLoadingDialog()) {
                    DetailFragmentNew.this.exportPdfDirectly();
                    if (DetailFragmentNew.this.getActivity() != null) {
                        DetailFragmentNew.this.hideLoadingDialog();
                    }
                }
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
            public void onDownloadStart(NoteBgBean noteBgBean) {
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
            public void updateDownloadProgress(NoteBgBean noteBgBean, long j6, float f6, float f7) {
            }
        });
    }

    public void exportPdfDirectly() {
        if (this.mBaseContext == null) {
            return;
        }
        String str = "EasyNotes_" + new SimpleDateFormat(ConstantsBase.DATE_FORMAT_SORTABLE).format(Calendar.getInstance().getTime()) + ".pdf";
        PrintManager printManager = (PrintManager) this.mBaseContext.getSystemService("print");
        PrintAttributes build = new PrintAttributes.Builder().setColorMode(2).setMediaSize(PrintAttributes.MediaSize.ISO_A4).build();
        EditContentAdapter editContentAdapter = this.contentAdapter;
        if (editContentAdapter != null) {
            HashMap<Integer, EditText> editTextHashMap = editContentAdapter.getEditTextHashMap();
            ArrayList arrayList = new ArrayList();
            Iterator<Integer> it2 = editTextHashMap.keySet().iterator();
            int i6 = 0;
            while (it2.hasNext()) {
                int intValue = it2.next().intValue();
                if (intValue == i6) {
                    i6++;
                    arrayList.add(editTextHashMap.get(Integer.valueOf(intValue)));
                }
            }
            printManager.print(str, new NotePrintAdapterNew(this.mBaseContext, str, this.noteTmp, this.contentBeanList, arrayList, this.currentFontBean), build);
        }
    }

    private void findView(View view) {
        this.root = view.findViewById(R.id.detail_root);
        this.noteBgView = view.findViewById(R.id.detail_notebg);
        this.title = view.findViewById(R.id.detail_title);
        this.content = view.findViewById(R.id.detail_content);
        this.attachmentsBelow = view.findViewById(R.id.detail_attachments_below);
        this.mGridView = view.findViewById(R.id.gridview);
        this.recordGrid = view.findViewById(R.id.gridview2);
        this.scrollView = view.findViewById(R.id.content_wrapper);
        this.topTimeText = view.findViewById(R.id.detail_top_time);
        this.topCategoryLayout = view.findViewById(R.id.detail_top_category_layout);
        this.topCategoryArrow = view.findViewById(R.id.detail_top_category_img);
        this.mBottomBar = view.findViewById(R.id.input_helper_view);
        this.categoryName = view.findViewById(R.id.detail_top_category_tv);
        this.add_Layout = view.findViewById(R.id.additem_layout);
        this.exitView = view.findViewById(R.id.exit_edit);
        this.pinView = view.findViewById(R.id.pin_action);
        this.reminderView = view.findViewById(R.id.reminder_action);
        this.moreView = view.findViewById(R.id.more_action);
        this.categoryImg = view.findViewById(R.id.category_img);
        this.addchecklistImg = view.findViewById(R.id.addchecklist);
        this.addchecklistTv = view.findViewById(R.id.addchecklist_tv);
        this.content_undo = view.findViewById(R.id.content_undo);
        this.content_redo = view.findViewById(R.id.content_redo);
        this.detail_content_card = view.findViewById(R.id.detail_content_card);
        this.fragment_layout = view.findViewById(R.id.fragment_layout);
        this.topToolBar = view.findViewById(R.id.toolbar_top);
        this.detailTopView = view.findViewById(R.id.detail_top_layout);
        this.editDateView = view.findViewById(R.id.edit_date);
        this.contentLayout = view.findViewById(R.id.content_layout);
        this.tagRecycleView = view.findViewById(R.id.tag_recycler);
        this.searchLayout = view.findViewById(R.id.search_input_layout);
        this.searchButton = view.findViewById(R.id.cancel_search);
        this.searchEdit = view.findViewById(R.id.searc_edit);
        this.searchDelete = view.findViewById(R.id.search_delete);
        this.contentRecycler = view.findViewById(R.id.content_recycler);
        this.nestedScrollView = view.findViewById(R.id.nestedScrollView);
    }

    private boolean fontStyleChanged() {
        String str;
        return (!TextUtils.isEmpty(this.originFontStyle) || !TextUtils.isEmpty(this.stringBuilder.toString())) && (str = this.originFontStyle) != null && !str.equalsIgnoreCase(this.stringBuilder.toString());
    }

    private TimerTask getAmplitudeUpdateTask() {
        return new TimerTask() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.31
            AnonymousClass31() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (DetailFragmentNew.this.mRecorder != null) {
                    try {
                        DialogRecord.INSTANCE.updateRecordingWave(DetailFragmentNew.this.mRecorder.getMaxAmplitude());
                    } catch (Exception unused) {
                    }
                }
            }
        };
    }

    private Attachment getAttachFromUri(Uri uri) {
        Note note = this.noteTmp;
        if (note == null || note.getAttachmentsList().size() <= 0) {
            return null;
        }
        for (int i6 = 0; i6 < this.noteTmp.getAttachmentsList().size(); i6++) {
            Attachment attachment = this.noteTmp.getAttachmentsList().get(i6);
            if (attachment.getUri() == uri) {
                return attachment;
            }
        }
        return null;
    }

    private TimerTask getDurationUpdateTask() {
        return new TimerTask() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.32
            AnonymousClass32() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                DetailFragmentNew.access$4308(DetailFragmentNew.this);
                DetailFragmentNew.this.broadcastDuration();
            }
        };
    }

    private EditText getFocusView() {
        EditText editText = this.title;
        if (editText != null && editText.hasFocus()) {
            return this.title;
        }
        EditText editText2 = this.content;
        if (editText2 != null && editText2.hasFocus()) {
            return this.content;
        }
        for (int i6 = 0; i6 < this.baseEntrys.size(); i6++) {
            BaseEditView baseEditView = this.baseEntrys.get(i6);
            if (baseEditView != null && baseEditView.getmEditText().hasFocus()) {
                return baseEditView.getmEditText();
            }
        }
        return null;
    }

    private List<Attachment> getImageAttachments() {
        ArrayList arrayList = new ArrayList();
        for (int i6 = 0; i6 < this.noteTmp.getAttachmentsList().size(); i6++) {
            Attachment attachment = this.noteTmp.getAttachmentsList().get(i6);
            if ("image/jpeg".equals(attachment.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type()) || "video/mp4".equals(attachment.getMime_type())) {
                arrayList.add(attachment);
            }
        }
        return arrayList;
    }

    public String getNoteContent() {
        String obj;
        if (this.noteTmp.isChecklist().booleanValue()) {
            ChecklistManager checklistManager = this.mChecklistManager;
            if (checklistManager == null) {
                return "";
            }
            checklistManager.keepChecked(true).showCheckMarks(true);
            return this.mChecklistManager.getText();
        }
        View findViewById = this.root.findViewById(R.id.detail_content);
        if (findViewById instanceof EditText) {
            obj = ((EditText) findViewById).getText().toString();
        } else {
            if (!(findViewById instanceof android.widget.EditText)) {
                return "";
            }
            obj = ((android.widget.EditText) findViewById).getText().toString();
        }
        return obj;
    }

    public String getNoteTitle() {
        EditText editText = this.title;
        return (editText == null || TextUtils.isEmpty(editText.getText())) ? "" : this.title.getText().toString();
    }

    private String getSecondCheckContent() {
        ChecklistManager checklistManager = this.mChecklistManager2;
        if (checklistManager == null) {
            return "";
        }
        checklistManager.keepChecked(true).showCheckMarks(true);
        return this.mChecklistManager2.getText();
    }

    private void getSpan() {
        this.stringBuilder = new StringBuilder();
        EditText editText = this.content;
        if (editText != null) {
            checkAllEditTextSpan(this.content, editText.getEditableText(), this.baseEntrys.size() != 0);
            int i6 = 0;
            while (i6 < this.baseEntrys.size()) {
                checkAllEditTextSpan(this.baseEntrys.get(i6).getmEditText(), this.baseEntrys.get(i6).getmEditText().getEditableText(), i6 != this.baseEntrys.size() - 1);
                i6++;
            }
            try {
                String sb = this.stringBuilder.toString();
                if (TextUtils.isEmpty(sb)) {
                    return;
                }
                List asList = Arrays.asList(sb.split(","));
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (int i7 = 0; i7 < asList.size(); i7++) {
                    String str = (String) asList.get(i7);
                    if (!TextUtils.isEmpty(str)) {
                        if (!str.startsWith(Constants.SPAN_BULLET_D) && !str.startsWith(Constants.SPAN_BULLET_Z) && !str.startsWith(Constants.SPAN_BULLET_CHECK_BOX) && !str.startsWith(Constants.SPAN_BULLET_CHECK_BOX_N)) {
                            arrayList2.add(str);
                        }
                        arrayList.add(str);
                    }
                }
                Collections.sort(arrayList, new Comparator() { // from class: b6.d3
                    @Override // java.util.Comparator
                    public int compare(Object obj, Object obj2) {
                        int lambda$getSpan$54;
                        lambda$getSpan$54 = DetailFragmentNew.lambda$getSpan$54((String) obj, (String) obj2);
                        return lambda$getSpan$54;
                    }
                });
                String str2 = "";
                for (int i8 = 0; i8 < arrayList.size(); i8++) {
                    str2 = str2 + arrayList.get(i8) + ",";
                }
                for (int i9 = 0; i9 < arrayList2.size(); i9++) {
                    str2 = str2 + arrayList2.get(i9) + ",";
                }
                this.noteTmp.setAddress(str2);
            } catch (Exception unused) {
            }
        }
    }

    @SuppressLint({"NewApi"})
    private boolean goHome() {
        KeyboardUtils.hideKeyboard(this.content);
        FirebaseReportUtils.getInstance().comeAdReport(Constants.EDIT_SAVE_INTERS);
        if (!App.isAdOpen() && App.userConfig.getShowInterAd() && RemoteConfig.getLong("edit_back_inter_switch") == 0) {
            FirebaseReportUtils.getInstance().openAdReport(Constants.EDIT_SAVE_INTERS);
            if (System.currentTimeMillis() - this.onCreateTime < 30000) {
                FirebaseReportUtils.getInstance().reportNew("view_edit_too_short");
            }
            if (ScreenUtils.isPad(this.mainActivity) && ScreenUtils.isScreenOriatationLandscap(this.mainActivity)) {
                FirebaseReportUtils.getInstance().reportNew(Constants.EDIT_SAVE_INTERS_PAD_LAND);
                this.mainActivity.finish();
                return true;
            }
            if (NetworkUtils.isNetworkConnected(App.getAppContext())) {
                FirebaseReportUtils.getInstance().withNetworkReport(Constants.EDIT_SAVE_INTERS);
                if (DetailFragment.editExitTimes % (((int) this.timesSwitch) + 1) == 0) {
                    FirebaseReportUtils.getInstance().meetRuleReport(Constants.EDIT_SAVE_INTERS);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("ab_interstitial_h");
                    arrayList.add("ab_interstitial");
                    final IAdAdapter allTopAdByScenes = AdLoader.getAllTopAdByScenes(this.mainActivity, arrayList, Constants.EDIT_SAVE_INTERS);
                    if (allTopAdByScenes != null) {
                        FirebaseReportUtils.getInstance().reportNew("meetrule_with_ad");
                        allTopAdByScenes.setAdListener(new IAdLoadListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.19
                            AnonymousClass19() {
                            }

                            @Override // src.ad.adapters.IAdLoadListener
                            public void onAdClicked(IAdAdapter iAdAdapter) {
                                FirebaseReportUtils.getInstance().adClickReport(Constants.EDIT_SAVE_INTERS);
                            }

                            @Override // src.ad.adapters.IAdLoadListener
                            public void onAdClosed(IAdAdapter iAdAdapter) {
                            }

                            @Override // src.ad.adapters.IAdLoadListener
                            public void onAdLoaded(IAdAdapter iAdAdapter) {
                            }

                            @Override // src.ad.adapters.IAdLoadListener
                            public void onError(String str) {
                            }

                            @Override // src.ad.adapters.IAdLoadListener
                            public void onOpenAdDismiss(IAdAdapter iAdAdapter) {
                            }
                        });
                        if (this.mainActivity == null || !(allTopAdByScenes.getAdSource() == IAdAdapter.AdSource.admob || allTopAdByScenes.getAdSource() == IAdAdapter.AdSource.fb)) {
                            allTopAdByScenes.show("edit", getActivity());
                            this.mainActivity.finish();
                        } else {
                            this.mainActivity.showLoadingAds(true);
                            this.content.postDelayed(new Runnable() { // from class: b6.k2
                                @Override // java.lang.Runnable
                                public void run() {
                                    DetailFragmentNew.this.lambda$goHome$22(allTopAdByScenes);
                                }
                            }, 1300L);
                        }
                        this.adShowed = true;
                        DetailFragment.lastAdShow = System.currentTimeMillis();
                        App.lastOpenAdShowed = System.currentTimeMillis();
                        if (this.adShowed) {
                            DetailFragment.adShows++;
                            FirebaseReportUtils.getInstance().showAdReport(Constants.EDIT_SAVE_INTERS);
                            if (IAdAdapter.AdSource.fb == allTopAdByScenes.getAdSource()) {
                                FirebaseReportUtils.getInstance().showAdReport(Constants.EDIT_SAVE_INTERS, "fb");
                            } else if (IAdAdapter.AdSource.admob == allTopAdByScenes.getAdSource()) {
                                FirebaseReportUtils.getInstance().showAdReport(Constants.EDIT_SAVE_INTERS, "am");
                            } else if (IAdAdapter.AdSource.lovin == allTopAdByScenes.getAdSource()) {
                                FirebaseReportUtils.getInstance().showAdReport(Constants.EDIT_SAVE_INTERS, "lovin");
                            }
                        }
                    } else {
                        AdLoader.get(Constants.EDIT_SAVE_INTERS, this.mainActivity).preLoadAd(this.mainActivity);
                        FirebaseReportUtils.getInstance().reportNew("meetrule_without_ad");
                        FirebaseReportUtils.getInstance().withNetworkReportNoad(Constants.EDIT_SAVE_INTERS);
                        this.mainActivity.finish();
                    }
                    this.adShowed = false;
                } else {
                    this.mainActivity.finish();
                }
            } else {
                FirebaseReportUtils.getInstance().withOutNetworkReport(Constants.EDIT_SAVE_INTERS);
                this.mainActivity.finish();
            }
        } else {
            FirebaseReportUtils.getInstance().closeAdReport(Constants.EDIT_SAVE_INTERS);
            this.mainActivity.finish();
        }
        DetailFragment.editExitTimes++;
        return true;
    }

    private void handleIntents() {
        Intent intent = this.mainActivity.getIntent();
        this.isCalendarActivityComeIn = intent.getBooleanExtra(Constants.IS_CALENDAR_ACTIVITY_COME_IN, false);
        long longExtra = intent.getLongExtra(Constants.CALENDAR_SELECTION_TIME, 0L);
        if (longExtra > 0) {
            this.lastTime = longExtra;
        }
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_MERGE)) {
            this.noteOriginal = new Note();
            this.note = new Note(this.noteOriginal);
            try {
                this.noteTmp = (Note) getArguments().getParcelable(ConstantsBase.INTENT_NOTE);
            } catch (Exception unused) {
                Note note = MainActivity.deliverNote;
                if (note != null) {
                    this.noteTmp = note;
                } else {
                    this.noteTmp = new Note();
                }
            }
        }
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_SHORTCUT, ConstantsBase.ACTION_NOTIFICATION_CLICK)) {
            this.noteOriginal = DbHelper.getInstance().getNote(intent.getLongExtra("note_id", 0L));
            try {
                this.note = new Note(this.noteOriginal);
                this.noteTmp = new Note(this.noteOriginal);
            } catch (NullPointerException unused2) {
            }
        }
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET, ConstantsBase.ACTION_WIDGET_TAKE_PHOTO, ConstantsBase.ACTION_WIDGET_CHECKLIST, ConstantsBase.ACTION_WIDGET_DRAW, ConstantsBase.ACTION_WIDGET_RECORD, ConstantsBase.ACTION_WIDGET_BG)) {
            if (intent.hasExtra("widget_id")) {
                String obj = intent.getExtras().get("widget_id").toString();
                String checkIntentCategory = TextHelper.checkIntentCategory(this.prefs.getString(ConstantsBase.PREF_WIDGET_PREFIX + obj, ""));
                if (checkIntentCategory != null) {
                    try {
                        Category category = DbHelper.getInstance().getCategory(Long.valueOf(Long.parseLong(checkIntentCategory)));
                        Note note2 = new Note();
                        this.noteTmp = note2;
                        note2.setCategory(category);
                    } catch (NumberFormatException e7) {
                        MyLog.e("Category with not-numeric value!", e7);
                    }
                }
            }
            if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET_TAKE_PHOTO)) {
                this.isWidgetHideBoard = true;
                choosePic();
            } else if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET_CHECKLIST)) {
                useCheckList();
            } else if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET_RECORD)) {
                this.isWidgetHideBoard = true;
                recordPermission();
            } else if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET_DRAW)) {
                this.isWidgetHideBoard = true;
                bottomDrawImg();
            } else if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET_BG)) {
                this.isWidgetHideBoard = true;
                this.dialogType = -1;
                showColorDialog(0);
            }
            if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET)) {
                FirebaseReportUtils.getInstance().reportNew("widget_home_quickstar_click", "pr_status", AppSettingsData.STATUS_NEW);
            }
        }
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_SELECT)) {
            FirebaseReportUtils.getInstance().reportNew("widget_home_single_click");
        }
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_FAB_TAKE_PHOTO)) {
            choosePic();
        }
        if (IntentChecker.checkAction(intent, "android.intent.action.SEND", "android.intent.action.SEND_MULTIPLE", ConstantsBase.INTENT_GOOGLE_NOW) && intent.getType() != null) {
            if (this.noteTmp == null) {
                this.noteTmp = new Note();
            }
            String stringExtra = intent.getStringExtra("android.intent.extra.SUBJECT");
            if (stringExtra != null) {
                this.noteTmp.setTitle(stringExtra);
            }
            String stringExtra2 = intent.getStringExtra("android.intent.extra.TEXT");
            if (stringExtra2 != null) {
                this.noteTmp.setContent(stringExtra2);
            }
            importAttachments(intent);
        }
        IntentChecker.checkAction(intent, "android.intent.action.MAIN", ConstantsBase.ACTION_WIDGET_SHOW_LIST, ConstantsBase.ACTION_SHORTCUT_WIDGET, ConstantsBase.ACTION_WIDGET);
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_WIDGET, ConstantsBase.ACTION_SHORTCUT_WIDGET, ConstantsBase.ACTION_WIDGET_TAKE_PHOTO)) {
            FirebaseReportUtils.getInstance().reportNew("widget_create_show");
        }
        intent.setAction(null);
    }

    private void hideEdit() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private void importAttachments(Intent intent) {
        if (intent.hasExtra("android.intent.extra.STREAM")) {
            if (intent.getExtras().get("android.intent.extra.STREAM") instanceof Uri) {
                Uri uri = intent.getParcelableExtra("android.intent.extra.STREAM");
                if (ConstantsBase.INTENT_GOOGLE_NOW.equals(intent.getAction())) {
                    return;
                }
                new AttachmentTask(this, uri, FileHelper.getNameFromUri(this.mainActivity, uri), this).execute(new Void[0]);
                return;
            }
            Iterator it2 = intent.getParcelableArrayListExtra("android.intent.extra.STREAM").iterator();
            while (it2.hasNext()) {
                Uri uri2 = (Uri) it2.next();
                new AttachmentTask(this, uri2, FileHelper.getNameFromUri(this.mainActivity, uri2), this).execute(new Void[0]);
            }
        }
    }

    private void init() {
        this.editDateView.setOnClickListener(new View.OnClickListener() { // from class: b6.a3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$init$5(view);
            }
        });
        this.add_Layout.setOnClickListener(new View.OnClickListener() { // from class: b6.b3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$init$6(view);
            }
        });
        handleIntents();
        if (this.noteOriginal == null) {
            Note note = MainActivity.deliverNote;
            if (note != null) {
                this.noteOriginal = note;
                if (note.get_id() == null || this.noteOriginal.get_id().longValue() == 0) {
                    this.noteOriginal.setCreation(Long.valueOf(System.currentTimeMillis()));
                    this.noteOriginal.setLastModification(Long.valueOf(System.currentTimeMillis()));
                    this.noteOriginal.setUserLastModification(Long.valueOf(System.currentTimeMillis()));
                    if (this.isCalendarActivityComeIn) {
                        this.noteOriginal.setShowDate(Long.valueOf(this.lastTime));
                    }
                    this.isNewCreate = true;
                }
            } else {
                Note note2 = new Note();
                this.noteOriginal = note2;
                note2.setCreation(Long.valueOf(System.currentTimeMillis()));
                this.noteOriginal.setLastModification(Long.valueOf(System.currentTimeMillis()));
                this.noteOriginal.setUserLastModification(Long.valueOf(System.currentTimeMillis()));
                if (this.isCalendarActivityComeIn) {
                    this.noteOriginal.setShowDate(Long.valueOf(this.lastTime));
                }
                this.isNewCreate = true;
                Category category = MainActivity.deliverCategory;
                if (category != null) {
                    this.noteOriginal.setCategory(category);
                }
            }
        }
        if (this.note == null) {
            this.note = new Note(this.noteOriginal);
        }
        if (this.noteTmp == null) {
            this.noteTmp = new Note(this.note);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("init: ");
        sb.append(this.gson.toJson(this.noteTmp));
        this.noteTmp.setCalendarCreate(this.isCalendarActivityComeIn ? 1 : this.noteOriginal.getCalendarCreate());
        this.noteTmp.setIsPined(this.noteOriginal.getPinState());
        if (this.isNewCreate) {
            int defaultBackgroundId = this.userPreferences.getDefaultBackgroundId();
            if (defaultBackgroundId == 10) {
                boolean defaultBackgroundCustomDark = this.userPreferences.getDefaultBackgroundCustomDark();
                String defaultBackgroundFileName = this.userPreferences.getDefaultBackgroundFileName();
                NoteBgBean noteBg = ResNoteBgManager.getInstance().getNoteBg(defaultBackgroundFileName, defaultBackgroundCustomDark ? 11 : 10, 10);
                this.mOriginalNoteBg = noteBg;
                this.mRecentNoteBg = noteBg;
                this.noteTmp.setStickyType(defaultBackgroundCustomDark ? 11 : 10);
                this.noteTmp.setStickyColor(defaultBackgroundFileName);
                this.noteTmp.setBgId(10);
            } else {
                NoteBgBean noteBg2 = ResNoteBgManager.getInstance().getNoteBg(defaultBackgroundId);
                this.mOriginalNoteBg = noteBg2;
                this.mRecentNoteBg = noteBg2;
                this.noteTmp.setStickyType(ResNoteBgManager.getInstance().getStickyType(this.mRecentNoteBg.getId()));
                this.noteTmp.setStickyColor(ResNoteBgManager.getInstance().getStickyColor(this.mRecentNoteBg.getId()));
                this.noteTmp.setBgId(this.mRecentNoteBg.getId());
            }
            this.noteOriginal.setStickyType(this.noteTmp.getStickyType());
            this.noteOriginal.setStickyColor(this.noteTmp.getStickyColor());
            this.noteOriginal.setBgId(this.noteTmp.getBgId());
        } else {
            NoteBgBean noteBg3 = ResNoteBgManager.getInstance().getNoteBg(this.noteOriginal.getStickyColor(), this.noteOriginal.getStickyType(), this.noteOriginal.getBgId());
            this.mOriginalNoteBg = noteBg3;
            this.mRecentNoteBg = noteBg3;
        }
        if (this.noteOriginal.getBgId() == 0) {
            this.noteTmp.setBgId(this.mOriginalNoteBg.getId());
            this.noteOriginal.setBgId(this.mOriginalNoteBg.getId());
        }
        if (this.noteTmp.isLocked().booleanValue() && !this.noteTmp.isPasswordChecked()) {
            checkNoteLock(this.noteTmp);
            return;
        }
        initTags();
        initViews();
        if (this.note.getNewData() == null || this.note.getNewData().isEmpty()) {
            for (int i6 = 0; i6 < 20; i6++) {
                this.contentBeanList.add(EditContentBean.newText(""));
                this.contentOriginal.add(EditContentBean.newText(""));
            }
            if (this.noteTmp.getAttachmentsList().size() > 0) {
                ArrayList arrayList = new ArrayList();
                for (int i7 = 0; i7 < this.noteTmp.getAttachmentsList().size(); i7++) {
                    if (this.noteTmp.getAttachmentsList().get(i7).getMime_type().equals("image/jpeg") || this.noteTmp.getAttachmentsList().get(i7).getMime_type().equals(ConstantsBase.MIME_TYPE_SKETCH)) {
                        arrayList.add(this.noteTmp.getAttachmentsList().get(i7));
                    }
                }
                if (arrayList.size() > 0) {
                    this.contentBeanList.add(1, EditContentBean.newImg(arrayList));
                }
            }
            this.contentUndo.add(new EditContentUndoRedoBean(this.gson.toJson(this.contentBeanList), -1, -1));
            this.noteTmp.setChecklist(Boolean.FALSE);
            initContentRV();
        } else {
            JsonArray asJsonArray = new JsonParser().parse(this.note.getNewData()).getAsJsonArray();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Iterator<JsonElement> it2 = asJsonArray.iterator();
            while (it2.hasNext()) {
                EditContentBean editContentBean = (EditContentBean) this.gson.fromJson(it2.next(), EditContentBean.class);
                if (editContentBean.getViewType() == 5) {
                    arrayList3.add(editContentBean);
                } else {
                    arrayList2.add(editContentBean);
                }
            }
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            Iterator<JsonElement> it3 = asJsonArray.iterator();
            while (it3.hasNext()) {
                EditContentBean editContentBean2 = (EditContentBean) this.gson.fromJson(it3.next(), EditContentBean.class);
                if (editContentBean2.getViewType() == 5) {
                    arrayList5.add(editContentBean2);
                } else {
                    arrayList4.add(editContentBean2);
                }
            }
            this.contentBeanList.clear();
            this.contentBeanList.addAll(arrayList2);
            this.contentOriginal.clear();
            this.contentOriginal.addAll(arrayList4);
            this.contentSearched = this.gson.toJson(this.contentBeanList);
            this.contentUndo.add(new EditContentUndoRedoBean(this.gson.toJson(this.contentBeanList), -1, -1));
            this.noteTmp.setChecklist(Boolean.FALSE);
            for (int i8 = 0; i8 < this.contentBeanList.size(); i8++) {
                if (this.contentBeanList.get(i8).getViewType() == 1) {
                    if (this.contentBeanList.get(i8).isSelected()) {
                        this.contentBeanList.get(i8).setRichData(this.contentBeanList.get(i8).getRichData() + "b0");
                    } else {
                        this.contentBeanList.get(i8).setRichData(this.contentBeanList.get(i8).getRichData() + "n0");
                    }
                    this.contentBeanList.get(i8).setViewType(0);
                } else if (this.contentBeanList.get(i8).getViewType() == 2) {
                    this.contentBeanList.get(i8).setViewType(0);
                    this.contentBeanList.get(i8).setRichData(this.contentBeanList.get(i8).getRichData() + "d0");
                } else if (this.contentBeanList.get(i8).getViewType() == 3) {
                    this.contentBeanList.get(i8).setViewType(0);
                    this.contentBeanList.get(i8).setRichData(this.contentBeanList.get(i8).getRichData() + "z0");
                }
            }
            initContentRV();
        }
        for (int i9 = 0; i9 < this.contentBeanList.size(); i9++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("init: ");
            sb2.append(this.contentBeanList.get(i9).toString());
        }
        if (Constants.NEW_RELEASE.equalsIgnoreCase(this.mainActivity.editFrom) || this.mainActivity.notifyType == DailyReminderReceiver.NEW_STICK_BG) {
            this.dialogType = -1;
            showColorDialog(0);
            this.anyDialogShow = true;
            if (this.mainActivity.notifyType == DailyReminderReceiver.NEW_STICK_BG) {
                FirebaseReportUtils.getInstance().reportNew("notify_new_stick_bg_click");
            }
        }
        if (!TextUtils.isEmpty(this.noteTmp.getTitle()) || !TextUtils.isEmpty(this.noteTmp.getContent()) || this.noteTmp.getAttachmentsList().size() != 0) {
            this.newCreate = false;
        }
        checkNotifyAction();
        editRedShow();
        showDialog();
    }

    private void initBelowLayout(List<Attachment> list) {
        this.attachmentsBelow.inflate();
        this.recordGrid = this.root.findViewById(R.id.gridview2);
        RecordGridAdapter recordGridAdapter = new RecordGridAdapter(this.mainActivity, list, new RecordGridAdapter.RecordGridActionInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.16

            /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$16$1 */
            class AnonymousClass1 implements DialogAddCategory.Positive1Listener<String> {
                final /* synthetic */ Attachment val$attachment;

                AnonymousClass1(Attachment attachment) {
                    r2 = attachment;
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
                public void positiveClick(String str) {
                    DetailFragmentNew.this.deleteRecording(r2);
                    Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.delete_success, 0).show();
                }
            }

            AnonymousClass16() {
            }

            @Override // notes.easy.android.mynotes.models.adapters.RecordGridAdapter.RecordGridActionInterface
            public void closeImgClick(Attachment attachment) {
                FirebaseReportUtils.getInstance().reportNew("record_in_notes_delete_show");
                DialogAddCategory.INSTANCE.showOneTitleDialog(DetailFragmentNew.this.mainActivity, R.string.delete_recording, R.string.delete, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.16.1
                    final /* synthetic */ Attachment val$attachment;

                    AnonymousClass1(Attachment attachment2) {
                        r2 = attachment2;
                    }

                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
                    public void positiveClick(String str) {
                        DetailFragmentNew.this.deleteRecording(r2);
                        Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.delete_success, 0).show();
                    }
                }, null);
            }

            @Override // notes.easy.android.mynotes.models.adapters.RecordGridAdapter.RecordGridActionInterface
            public void playImgClick(Uri uri) {
            }
        });
        this.recordGridAdapter = recordGridAdapter;
        this.recordGrid.setAdapter((ListAdapter) recordGridAdapter);
        this.recordGrid.setGridNumColumns(1);
    }

    private void initCategoryName() {
        if (this.noteTmp.getCategory() == null || this.noteTmp.getCategory().getId() == Constants.DEFAULT_CATE_ID || this.noteTmp.getCategory().getName() == null) {
            this.categoryName.setText(App.getAppContext().getResources().getString(R.string.uncategorized));
        } else {
            this.categoryName.setText(this.noteTmp.getCategory().getName());
        }
    }

    private void initCheckListCustomEmoji(ChecklistManager checklistManager) {
        CheckListView checkListView;
        if (checklistManager == null || (checkListView = checklistManager.mCheckListView) == null) {
            return;
        }
        for (int i6 = 0; i6 < checkListView.getChildCount(); i6++) {
            try {
                CheckListViewItem childAt = checkListView.getChildAt(i6);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(childAt.getText());
                EmojiManager.parseCharSequence(spannableStringBuilder);
                childAt.getEditText().setText(spannableStringBuilder);
            } catch (Exception unused) {
                return;
            }
        }
    }

    private void initContentFont() {
        EditText editText = this.content;
        if (editText != null) {
            editText.setEditTextMenuListener(this);
        }
        this.currentFontName = ResNoteFontManager.DEFAULT_FONT;
        if (this.isNewCreate) {
            String defaultFloatStyle = this.userPreferences.getDefaultFloatStyle();
            if (!TextUtils.isEmpty(defaultFloatStyle)) {
                this.currentFontName = defaultFloatStyle;
            }
        } else if (TextUtils.isEmpty(this.noteTmp.getFontName()) && this.noteTmp.getLongitude() != null) {
            try {
                this.currentFontName = ResNoteFontManager.FONT_TYPES[this.noteTmp.getLongitude().intValue()];
            } catch (Exception unused) {
            }
        } else if (!TextUtils.isEmpty(this.noteTmp.getFontName())) {
            this.currentFontName = this.noteTmp.getFontName();
        }
        this.noteTmp.setFontName(this.currentFontName);
        setFontTypeface(this.currentFontName);
    }

    private void initContentRV() {
        if (this.content.getText() != null) {
            if (this.content.length() == 0) {
                new Timer().schedule(new AnonymousClass5(), 1000L);
            }
            if (this.content.getText() != null) {
                this.content.setText(this.content.getText().toString() + StringUtils.SPACE);
            }
        }
        this.contentAdapter = new EditContentAdapter(this.mRefreshHandler, this.mainActivity, this.contentBeanList, new EditContentAdapter.RecordGridActionInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.6
            AnonymousClass6() {
            }
        }, this, new EditTextMenuListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.7
            AnonymousClass7() {
            }

            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public boolean onMenuDismiss() {
                DetailFragmentNew.this.mBottomBar.hideSelectedState();
                DetailFragmentNew.this.setSaveIconColor();
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.isBackOrSave = true;
                detailFragmentNew.showSavingView();
                return false;
            }

            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public boolean onMenuShow() {
                DetailFragmentNew.this.onMenuShow();
                return false;
            }

            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public boolean onSelectedAreChanged(int i6, int i7) {
                DetailFragmentNew.this.onSelectedAreChanged(i6, i7);
                return false;
            }

            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public void updateURState(boolean z6, boolean z7) {
                DetailFragmentNew.this.onCanDo(z6, z7);
            }
        }, new EditContentAdapter.ImgAdapterClick() { // from class: b6.h3
            @Override // notes.easy.android.mynotes.ui.adapters.EditContentAdapter.ImgAdapterClick
            public void onPicClick(Attachment attachment, int i6, int i7) {
                DetailFragmentNew.this.lambda$initContentRV$7(attachment, i6, i7);
            }
        }, new EditContentAdapter.SaveAll() { // from class: b6.i3
            @Override // notes.easy.android.mynotes.ui.adapters.EditContentAdapter.SaveAll
            public void save() {
                DetailFragmentNew.this.lambda$initContentRV$8();
            }
        }, ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg());
        setFontTypeface(this.currentFontName);
        this.contentAdapter.setDrawLines(this.isLineTheme_, this.line_);
        this.contentRecycler.setLayoutManager(new LinearLayoutManager(this.mainActivity, 1, false) { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.8
            AnonymousClass8(Context context, int i6, boolean z6) {
                super(context, i6, z6);
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        this.contentRecycler.setAdapter(this.contentAdapter);
        ((SimpleItemAnimator) this.contentRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        this.nestedScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: b6.j3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$initContentRV$9;
                lambda$initContentRV$9 = DetailFragmentNew.this.lambda$initContentRV$9(view, motionEvent);
                return lambda$initContentRV$9;
            }
        });
    }

    private void initSearchLayout() {
        this.searchDelete.setOnClickListener(new View.OnClickListener() { // from class: b6.r3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$initSearchLayout$11(view);
            }
        });
        this.searchButton.setOnClickListener(new View.OnClickListener() { // from class: b6.s3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$initSearchLayout$12(view);
            }
        });
        this.searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: b6.t3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i6, KeyEvent keyEvent) {
                boolean lambda$initSearchLayout$13;
                lambda$initSearchLayout$13 = DetailFragmentNew.this.lambda$initSearchLayout$13(textView, i6, keyEvent);
                return lambda$initSearchLayout$13;
            }
        });
        this.searchEdit.addTextChangedListener(new TextWatcher() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.10
            AnonymousClass10() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    DetailFragmentNew.this.searchButton.setText(R.string.search);
                    DetailFragmentNew.this.searchDelete.setVisibility(View.VISIBLE);
                    DetailFragmentNew.this.needSearch = true;
                } else {
                    DetailFragmentNew.this.searchButton.setText(R.string.cancel);
                    DetailFragmentNew.this.needSearch = false;
                    DetailFragmentNew.this.resetSearchedText(true);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            }
        });
    }

    private void initSelectBottomView() {
        new SoftKeyBroadManager(this.title).addSoftKeyboardStateListener(new SoftKeyBroadManager.SoftKeyboardStateListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.13
            AnonymousClass13() {
            }

            @Override // notes.easy.android.mynotes.view.SoftKeyBroadManager.SoftKeyboardStateListener
            public void onSoftKeyboardClosed() {
                DetailFragmentNew.this.mBottomBar.requestLayout();
            }

            @Override // notes.easy.android.mynotes.view.SoftKeyBroadManager.SoftKeyboardStateListener
            public void onSoftKeyboardOpened(int i6) {
                DetailFragmentNew.this.mBottomBar.requestLayout();
            }
        });
    }

    private void initTags() {
        String tags = this.noteTmp.getTags();
        List arrayList = new ArrayList();
        if (tags != null) {
            arrayList = Arrays.asList(tags.split(","));
            for (int i6 = 0; i6 < arrayList.size(); i6++) {
                if (!TextUtils.isEmpty((CharSequence) arrayList.get(i6))) {
                    this.currentTagList.add((String) arrayList.get(i6));
                }
            }
        }
        if (arrayList.size() == 0 && !TextUtils.isEmpty(tags)) {
            this.currentTagList.add(tags);
        }
        this.tagsAdapter = new TagsAdapter(this.mainActivity, this.currentTagList, new TagsAdapter.setTabAdapterListener() { // from class: b6.e3
            @Override // notes.easy.android.mynotes.ui.adapters.TagsAdapter.setTabAdapterListener
            public void onSelectTag(String str) {
                DetailFragmentNew.this.lambda$initTags$10(str);
            }
        });
        this.tagRecycleView.setLayoutManager(new LinearLayoutManager(this.mainActivity, 0, false));
        this.tagRecycleView.setAdapter(this.tagsAdapter);
        if (this.currentTagList.size() > 0) {
            this.tagRecycleView.setVisibility(View.VISIBLE);
        }
    }

    private void initTopLayout() {
        String str = Constants.dateFormatList[EasyNoteManager.getInstance().getDefaultDateIndex()];
        if (this.noteTmp.getShowDate() == null || System.currentTimeMillis() - this.noteTmp.getShowDate().longValue() < 31536000000L) {
            str = Constants.shortDateFormatList[EasyNoteManager.getInstance().getDefaultDateIndex()];
        }
        if (this.noteTmp.getShowDate() != null) {
            this.lastTime = this.noteTmp.getShowDate().longValue();
        }
        this.topTimeText.setText(new SimpleDateFormat(str).format(Long.valueOf(this.lastTime)) + ", " + DateUtils.formatDateTime(this.mainActivity, this.lastTime, 1));
        this.topCategoryLayout.setOnClickListener(new View.OnClickListener() { // from class: b6.m3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$initTopLayout$16(view);
            }
        });
    }

    private void initViewAttachments() {
        this.imageList.clear();
        this.audioList.clear();
        boolean z6 = false;
        for (int i6 = 0; i6 < this.noteTmp.getAttachmentsList().size(); i6++) {
            try {
                Attachment attachment = this.noteOriginal.getAttachmentsList().get(i6);
                if (attachment != null) {
                    if ("audio/amr".equals(attachment.getMime_type())) {
                        this.audioList.add(attachment);
                    } else if (attachment.getOrder() == 0) {
                        this.imageList.add(attachment);
                        if (attachment.getSort() != 0) {
                            z6 = true;
                        }
                    } else {
                        if (attachment.getOrder() > this.baseEntrys.size()) {
                            attachment.setOrder(this.baseEntrys.size());
                        }
                        for (int i7 = 0; i7 < this.baseEntrys.size(); i7++) {
                            if (this.baseEntrys.get(i7).getmOrder() == attachment.getOrder()) {
                                this.baseEntrys.get(i7).addAttachment(attachment);
                            }
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        int size = this.baseEntrys.size();
        if (size > 0) {
            int i8 = size - 1;
            if (this.baseEntrys.get(i8).getAttachmentSize() > 0) {
                BaseEditView baseEditView = new BaseEditView(this.mainActivity);
                baseEditView.getmEditText().setOnTextLinkClickListener(this.textLinkClickListener);
                baseEditView.getmEditText().gatherLinksForText();
                setNewTextViewColor(baseEditView.getmEditText());
                baseEditView.setClickListener(this);
                baseEditView.getmEditText().setEditTextMenuListener(this);
                this.contentLayout.addView(baseEditView.getBaseView(), this.contentLayout.getChildCount() - 1);
                this.baseEntrys.add(baseEditView);
            } else if (i8 == 0 && TextUtils.isEmpty(this.baseEntrys.get(i8).getContent()) && this.imageList.size() == 0) {
                this.contentLayout.removeView(this.baseEntrys.get(i8).getBaseView());
                List<BaseEditView> list = this.baseEntrys;
                list.remove(list.get(i8));
            }
        }
        this.contentLayout.postDelayed(new Runnable() { // from class: b6.n3
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$initViewAttachments$17();
            }
        }, 500L);
        this.mGridView = this.root.findViewById(R.id.gridview);
        if (z6) {
            Collections.sort(this.imageList, new SortComparator());
        }
        PicGridAdapter picGridAdapter = new PicGridAdapter(this.mainActivity, this.imageList);
        this.mAttachmentAdapter = picGridAdapter;
        picGridAdapter.updateGradId();
        ScrollGridLayoutManager scrollGridLayoutManager = new ScrollGridLayoutManager((Context) this.mainActivity, ScreenUtils.isScreenOriatationLandscap(this.mainActivity) ? 4 : ScreenUtils.isPad(this.mainActivity) ? 3 : 2, 1, false);
        RecyclerView recyclerView = this.mGridView;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(scrollGridLayoutManager);
            this.mGridView.setAdapter(this.mAttachmentAdapter);
            new ItemTouchHelper(new ItemDragCallback(this.mAttachmentAdapter)).attachToRecyclerView(this.mGridView);
        }
        this.mAttachmentAdapter.setOnListCallbackListener(new PicGridAdapter.OnListCallback() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.15
            AnonymousClass15() {
            }

            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemClick(View view, Attachment attachment2, int i9) {
                if (attachment2 == null) {
                    return;
                }
                Uri shareableUri = FileProviderHelper.getShareableUri(attachment2);
                if (ConstantsBase.MIME_TYPE_FILES.equals(attachment2.getMime_type())) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(shareableUri, StorageHelper.getMimeType(DetailFragmentNew.this.mainActivity, shareableUri));
                    intent.addFlags(3);
                    if (IntentChecker.isAvailable(DetailFragmentNew.this.mainActivity.getApplicationContext(), intent, null)) {
                        DetailFragmentNew.this.startActivity(intent);
                        return;
                    }
                    return;
                }
                if ("image/jpeg".equals(attachment2.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment2.getMime_type()) || "video/mp4".equals(attachment2.getMime_type())) {
                    GalleryActivity.transforNote = DetailFragmentNew.this.noteTmp;
                    ArrayList arrayList = new ArrayList();
                    int i10 = 0;
                    for (Attachment attachment3 : DetailFragmentNew.this.noteTmp.getAttachmentsList()) {
                        if ("image/jpeg".equals(attachment3.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment3.getMime_type()) || "video/mp4".equals(attachment3.getMime_type())) {
                            arrayList.add(attachment2);
                            if (attachment3.equals(attachment2)) {
                                i10 = arrayList.size() - 1;
                            }
                        }
                    }
                    try {
                        Intent intent2 = new Intent(DetailFragmentNew.this.mainActivity, GalleryActivity.class);
                        intent2.putExtra(ConstantsBase.GALLERY_CLICKED_IMAGE, i10);
                        DetailFragmentNew.this.mainActivity.startActivityForResult(intent2, Constants.DETAIL_TO_GALLERY);
                        FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_preview");
                    } catch (Exception unused2) {
                    }
                }
            }

            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemDragFinished(List<Attachment> list2, int i9, int i10) {
                int i11 = 0;
                while (i11 < list2.size()) {
                    Attachment attachment2 = list2.get(i11);
                    i11++;
                    attachment2.setSort(i11);
                }
                for (int i12 = 0; i12 < DetailFragmentNew.this.imageList.size(); i12++) {
                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                    detailFragmentNew.noteTmp.removeAttachment((Attachment) detailFragmentNew.imageList.get(i12));
                }
                DetailFragmentNew.this.imageList = list2;
                for (int i13 = 0; i13 < DetailFragmentNew.this.imageList.size(); i13++) {
                    DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                    detailFragmentNew2.noteTmp.addAttachment((Attachment) detailFragmentNew2.imageList.get(i13));
                }
                DetailFragmentNew.this.timeModified = true;
                DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
                detailFragmentNew3.saveNote(new a2(detailFragmentNew3), false);
            }

            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemLongClick() {
            }
        });
    }

    private void initViewContent() {
        if (this.baseEntrys.size() > 0) {
            this.baseEntrys.clear();
        }
        boolean z6 = false;
        if (this.noteTmp.getContent().contains(Constants.GAP_BASE)) {
            String[] split = this.noteTmp.getContent().split(Constants.GAP_BASE);
            if (split == null || split.length < 1) {
                BaseEditView baseEditView = new BaseEditView(this.mainActivity);
                baseEditView.setClickListener(this);
                baseEditView.getmEditText().setEditTextMenuListener(this);
                baseEditView.setOrder(1);
                setNewTextViewColor(baseEditView.getmEditText());
                this.contentLayout.addView(baseEditView.getBaseView(), this.contentLayout.getChildCount() - 1);
                this.baseEntrys.add(baseEditView);
            } else {
                this.content.setText(split[0]);
                if (split.length == 1) {
                    BaseEditView baseEditView2 = new BaseEditView(this.mainActivity);
                    baseEditView2.setClickListener(this);
                    baseEditView2.getmEditText().setEditTextMenuListener(this);
                    baseEditView2.setOrder(1);
                    setNewTextViewColor(baseEditView2.getmEditText());
                    this.contentLayout.addView(baseEditView2.getBaseView(), this.contentLayout.getChildCount() - 1);
                    this.baseEntrys.add(baseEditView2);
                } else {
                    for (int i6 = 1; i6 < split.length; i6++) {
                        BaseEditView baseEditView3 = new BaseEditView(this.mainActivity);
                        baseEditView3.setClickListener(this);
                        baseEditView3.getmEditText().setEditTextMenuListener(this);
                        baseEditView3.setOrder(i6);
                        baseEditView3.getmEditText().setText(split[i6].trim());
                        setNewTextViewColor(baseEditView3.getmEditText());
                        this.contentLayout.addView(baseEditView3.getBaseView(), this.contentLayout.getChildCount() - 1);
                        this.baseEntrys.add(baseEditView3);
                        if (!TextUtils.isEmpty(split[i6].trim())) {
                            if (saveSkech) {
                                baseEditView3.setContent(split[i6].trim());
                            } else {
                                parseCustomCode(baseEditView3.getmEditText(), split[i6]);
                            }
                        }
                    }
                }
            }
        } else {
            this.content.setText(this.noteTmp.getContent());
            if (this.noteTmp.getAttachmentsList().size() > 0) {
                for (int i7 = 0; i7 < this.noteTmp.getAttachmentsList().size(); i7++) {
                    if (ConstantsBase.MIME_TYPE_SKETCH.equalsIgnoreCase(this.noteTmp.getAttachmentsList().get(i7).getMime_type()) || "image/jpeg".equalsIgnoreCase(this.noteTmp.getAttachmentsList().get(i7).getMime_type())) {
                        z6 = true;
                        break;
                    }
                }
                if (z6) {
                    BaseEditView baseEditView4 = new BaseEditView(this.mainActivity);
                    baseEditView4.setClickListener(this);
                    setNewTextViewColor(baseEditView4.getmEditText());
                    baseEditView4.getmEditText().setEditTextMenuListener(this);
                    this.contentLayout.addView(baseEditView4.getBaseView(), this.contentLayout.getChildCount() - 1);
                    this.baseEntrys.add(baseEditView4);
                }
            }
        }
        this.content.setOnFocusChangeListener(this.onFocusChangeListener);
        this.content.setOnTouchListener(new View.OnTouchListener() { // from class: b6.o3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$initViewContent$20;
                lambda$initViewContent$20 = DetailFragmentNew.this.lambda$initViewContent$20(view, motionEvent);
                return lambda$initViewContent$20;
            }
        });
        this.content.setOnTextLinkClickListener(this.textLinkClickListener);
        this.content.gatherLinksForText();
        this.content.addTextChangedListener(new TextWatcher() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.18
            int startPos = 0;
            int endPos = 0;

            AnonymousClass18() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                DetailFragmentNew.this.setSaveIconColor();
                if (!TextUtils.isEmpty(editable.toString())) {
                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                    detailFragmentNew.isBackOrSave = true;
                    if (!detailFragmentNew.beginEdit && DetailFragmentNew.this.mHander != null && DetailFragmentNew.this.autoSaveRunnable != null) {
                        DetailFragmentNew.this.beginEdit = true;
                        DetailFragmentNew.this.mHander.postDelayed(DetailFragmentNew.this.autoSaveRunnable, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US);
                    }
                }
                if (this.endPos <= this.startPos) {
                    if (DetailFragmentNew.this.bulletContorller != null) {
                        DetailFragmentNew.this.bulletContorller.executeDeleteAction(DetailFragmentNew.this.content, editable, this.startPos, this.endPos);
                    }
                } else if (DetailFragmentNew.this.bulletContorller != null) {
                    DetailFragmentNew.this.bulletContorller.executeInputAction(DetailFragmentNew.this.content, editable, this.startPos, this.endPos);
                }
                boolean z62 = DetailFragmentNew.this.mCurrentFontIndex != Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(DetailFragmentNew.this.userPreferences.getDefaultFloatSize()));
                if (DetailFragmentNew.this.fontStyleIsChange() || DetailFragmentNew.this.currentFontAbsoluteSize != ScreenUtils.dpToPx(16)) {
                    if (DetailFragmentNew.this.content.getSelectionEnd() < DetailFragmentNew.this.content.getEditableText().length()) {
                        DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                        EditText editText = detailFragmentNew2.content;
                        detailFragmentNew2.setTextShape(editText, editText.getEditableText(), DetailFragmentNew.this.isBold, DetailFragmentNew.this.isItalic, DetailFragmentNew.this.isUnderline, DetailFragmentNew.this.isStrikethrough, z62, DetailFragmentNew.this.highLightColor, DetailFragmentNew.this.startIndex, DetailFragmentNew.this.content.getSelectionEnd());
                    } else {
                        DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
                        EditText editText2 = detailFragmentNew3.content;
                        detailFragmentNew3.setTextShape(editText2, editText2.getEditableText(), DetailFragmentNew.this.isBold, DetailFragmentNew.this.isItalic, DetailFragmentNew.this.isUnderline, DetailFragmentNew.this.isStrikethrough, z62, DetailFragmentNew.this.highLightColor, DetailFragmentNew.this.startIndex, DetailFragmentNew.this.content.getEditableText().length());
                    }
                }
                DetailFragmentNew.this.showSavingView();
                DetailFragmentNew.this.userFoucChanged = false;
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i62, int i72, int i8) {
                DetailFragmentNew.this.scrollContent();
                if (DetailFragmentNew.this.fontStyleIsChange() || DetailFragmentNew.this.currentFontAbsoluteSize != ScreenUtils.dpToPx(16)) {
                    if (DetailFragmentNew.this.content.getSelectionStart() != DetailFragmentNew.this.content.getSelectionEnd() || DetailFragmentNew.this.content.getSelectionEnd() >= DetailFragmentNew.this.content.getEditableText().length()) {
                        DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                        detailFragmentNew.startIndex = detailFragmentNew.content.getEditableText().length();
                    } else {
                        DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                        detailFragmentNew2.startIndex = detailFragmentNew2.content.getSelectionStart();
                    }
                }
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i62, int i72, int i8) {
                this.startPos = i62;
                this.endPos = i62 + i8;
            }
        });
        this.toggleChecklistView = this.content;
        EditText editText = new EditText(this.mainActivity);
        this.toggleChecklistView2 = editText;
        if (Build.VERSION.SDK_INT >= 23) {
            editText.setTextAppearance(R.style.Text_Big);
            ((EditText) this.toggleChecklistView2).setCustomFont(App.getAppContext(), ResNoteFontManager.DEFAULT_FONT_PATH);
            ((EditText) this.toggleChecklistView2).setTextColor(App.getAppContext().getResources().getColor(R.color.text_color));
        }
        if (this.noteTmp.isChecklist().booleanValue()) {
            this.checkSize = 1;
            this.noteTmp.setChecklist(Boolean.FALSE);
            AlphaManager.setAlpha(this.toggleChecklistView, 0.0f);
            toggleChecklist2();
        }
        if (TextUtils.isEmpty(this.noteTmp.getTitle()) && TextUtils.isEmpty(this.noteTmp.getContent()) && this.noteTmp.getAttachmentsList().isEmpty()) {
            if (this.userPreferences.getEditBgLayoutShow() && !dontShowSoftInputMethod()) {
                this.mBottomBar.postDelayed(new Runnable() { // from class: b6.p3
                    @Override // java.lang.Runnable
                    public void run() {
                        DetailFragmentNew.this.lambda$initViewContent$21();
                    }
                }, 200L);
            }
            this.mBottomBar.requestLayout();
        }
    }

    private void initViewTitle() {
        if (!TextUtils.isEmpty(this.noteTmp.getTitle())) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.noteTmp.getTitle());
            EmojiManager.parseCharSequence(spannableStringBuilder);
            this.title.setText(spannableStringBuilder);
        }
        this.title.setOnDragListener(new View.OnDragListener() { // from class: b6.k3
            @Override // android.view.View.OnDragListener
            public boolean onDrag(View view, DragEvent dragEvent) {
                boolean lambda$initViewTitle$18;
                lambda$initViewTitle$18 = DetailFragmentNew.lambda$initViewTitle$18(view, dragEvent);
                return lambda$initViewTitle$18;
            }
        });
        this.title.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: b6.l3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i6, KeyEvent keyEvent) {
                boolean lambda$initViewTitle$19;
                lambda$initViewTitle$19 = DetailFragmentNew.this.lambda$initViewTitle$19(textView, i6, keyEvent);
                return lambda$initViewTitle$19;
            }
        });
        this.title.addTextChangedListener(new TextWatcher() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.17
            AnonymousClass17() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                DetailFragmentNew.this.setSaveIconColor();
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.isBackOrSave = true;
                detailFragmentNew.showSavingView();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
            }
        });
        this.title.setOnFocusChangeListener(this.onFocusChangeListener);
    }

    @SuppressLint({"NewApi"})
    private void initViews() {
        if (this.noteTmp != null) {
            checkNewCreateAndFirebase();
        }
        initSearchLayout();
        this.pinView.setOnClickListener(this);
        this.exitView.setOnClickListener(this);
        this.moreView.setOnClickListener(this);
        this.reminderView.setOnClickListener(this);
        this.content_undo.setOnClickListener(this);
        this.content_redo.setOnClickListener(this);
        this.contentLayout.setOnClickListener(this);
        initViewTitle();
        initViewContent();
        initViewAttachments();
        initTopLayout();
        initSelectBottomView();
        initCategoryName();
        initContentFont();
        setStickyColor(this.noteTmp.getStickyType(), this.noteTmp.getStickyColor(), this.noteTmp.getBgId(), null);
        Note note = this.noteTmp;
        if (note != null) {
            this.originFontStyle = note.getAddress();
            if (!this.noteTmp.isChecklist().booleanValue()) {
                applayFontStyle(this.originFontStyle);
            }
        }
        this.scrollView.setOnTouchListener(new View.OnTouchListener() { // from class: b6.g3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$initViews$14;
                lambda$initViews$14 = DetailFragmentNew.this.lambda$initViews$14(view, motionEvent);
                return lambda$initViews$14;
            }
        });
        String str = this.SearchText;
        if (str == null || str.isEmpty()) {
            return;
        }
        new Timer().schedule(new AnonymousClass11(), 100L);
    }

    public void insertEmoji(boolean z6, Editable editable, int i6, EmojiBean emojiBean) {
        if (z6) {
            editable.insert(i6, emojiBean.createEmojiSpan());
        } else {
            if (TextUtils.isEmpty(emojiBean.getUnicode())) {
                return;
            }
            editable.insert(i6, emojiBean.getUnicode());
        }
    }

    private void insertNewBaseEntry(Attachment attachment) {
        Handler handler;
        Runnable runnable;
        boolean z6;
        int size;
        boolean z7;
        this.contentAdapter.addImg(attachment);
        if (this.title.hasFocus() || this.content.hasFocus() || this.baseEntrys.size() == 0 || (this.noteTmp.isChecklist().booleanValue() && isFirstChecklistFocus())) {
            attachment.setOrder(0);
            addAttachment(attachment);
            if (this.baseEntrys.size() == 0) {
                newABaseView();
                doSubViewLineLayoutState();
            }
        } else {
            int size2 = this.baseEntrys.size() - 1;
            while (true) {
                if (size2 < 0) {
                    z6 = false;
                    break;
                }
                if (this.baseEntrys.get(size2).isFocusable()) {
                    if (TextUtils.isEmpty(this.baseEntrys.get(size2).getContent())) {
                        attachment.setOrder(size2);
                        if (size2 == 0) {
                            attachment.setOrder(0);
                            addAttachment(attachment);
                        } else {
                            attachment.setOrder(size2);
                            this.baseEntrys.get(size2 - 1).addAttachment(attachment);
                        }
                        z7 = false;
                    } else {
                        attachment.setOrder(size2 + 1);
                        this.baseEntrys.get(size2).addAttachment(attachment);
                        z7 = true;
                    }
                    this.noteTmp.addAttachment(attachment);
                    if (size2 == this.baseEntrys.size() - 1 && z7) {
                        newABaseView();
                        doSubViewLineLayoutState();
                    }
                    z6 = true;
                } else {
                    size2--;
                }
            }
            if (!z6 && (size = this.baseEntrys.size() - 1) >= 0) {
                if (!TextUtils.isEmpty(this.baseEntrys.get(size).getContent())) {
                    attachment.setOrder(size + 1);
                    this.baseEntrys.get(size).addAttachment(attachment);
                } else if (size == 0) {
                    attachment.setOrder(0);
                    addAttachment(attachment);
                } else {
                    attachment.setOrder(size);
                    this.baseEntrys.get(size - 1).addAttachment(attachment);
                }
                this.noteTmp.addAttachment(attachment);
            }
        }
        if (this.beginEdit || (handler = this.mHander) == null || (runnable = this.autoSaveRunnable) == null) {
            return;
        }
        this.beginEdit = true;
        handler.postDelayed(runnable, 10000L);
    }

    private void isClickLock(Note note, ImageView imageView, TextView textView) {
        if (note.isLocked().booleanValue()) {
            FirebaseReportUtils.getInstance().reportNew("edit_tool_more_unlock");
        }
        boolean z6 = this.noteOriginal.isLocked().booleanValue() || DbHelper.getInstance().getNotesWithLock(true).size() <= 0;
        if (note.isLocked().booleanValue() || App.isVip() || App.is6hFreeTry()) {
            clickAddLock(note, imageView, textView);
        } else {
            DialogAddCategory.INSTANCE.showLockingNoteDialog(this.mainActivity, z6, false, new DialogAddCategory.OnLockingInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.38
                final /* synthetic */ ImageView val$lockImg;
                final /* synthetic */ TextView val$lockTv;
                final /* synthetic */ Note val$note;

                AnonymousClass38(Note note2, ImageView imageView2, TextView textView2) {
                    r2 = note2;
                    r3 = imageView2;
                    r4 = textView2;
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                public void clickTryItOnce() {
                    DetailFragmentNew.this.clickAddLock(r2, r3, r4);
                    FirebaseReportUtils.getInstance().reportNew("iap_lock_try");
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                public void clickUpgradeVip() {
                    VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "lock_note");
                }
            });
        }
    }

    private boolean isFirstChecklistFocus() {
        return this.mChecklistManager.mCheckListView.getFocusEditText() != null;
    }

    private boolean isNotNoteEmpty() {
        Note note = this.noteTmp;
        if (note == null) {
            return false;
        }
        if (TextUtils.isEmpty(note.getTitle()) && TextUtils.isEmpty(this.noteTmp.getContent())) {
            return this.noteTmp.getAttachmentsList() != null && this.noteTmp.getAttachmentsList().size() > 0;
        }
        return true;
    }

    public /* synthetic */ void lambda$addNewChecklistView$37() {
        try {
            this.mChecklistManager2.mCheckListView.setChecklistColor(true);
        } catch (Exception unused) {
        }
    }

    public /* synthetic */ void lambda$addNewChecklistView$38() {
        try {
            this.mChecklistManager2.mCheckListView.setChecklistColor(false);
        } catch (Exception unused) {
        }
    }

    public /* synthetic */ void lambda$applayFontStyle$15(String str) {
        List asList = Arrays.asList(str.split("\\+"));
        if (asList.size() > 0) {
            for (int i6 = 0; i6 < asList.size(); i6++) {
                if (i6 == 0) {
                    this.defaultContentStyle = (String) asList.get(i6);
                } else {
                    int i7 = i6 - 1;
                    if (i7 < this.baseEntrys.size()) {
                        BaseEditView baseEditView = this.baseEntrys.get(i7);
                        applyCharacterStyle(baseEditView.getmEditText(), baseEditView.getmEditText().getEditableText(), (String) asList.get(i6), true);
                    }
                }
            }
        }
    }

    public /* synthetic */ void lambda$doSubViewLineLayoutState$42(List list, EditText editText) {
        int height = this.content.getHeight() + this.mGridView.getHeight();
        for (int i6 = 0; i6 < list.size(); i6++) {
            height = height + ((BaseEditView) list.get(i6)).getmEditText().getHeight() + ((BaseEditView) list.get(i6)).getGridView().getHeight();
        }
        if (this.fragment_layout.getHeight() > height) {
            editText.setMinHeight(this.fragment_layout.getHeight() - height);
        }
        editText.setDrawLine(true);
        NoteBgBean noteBgBean = this.mRecentNoteBg;
        if (noteBgBean != null && noteBgBean.getLine() != null) {
            NoteBgBean.Line line = this.mRecentNoteBg.getLine();
            editText.setLineType(line.getLineType());
            editText.setLineColor(line.getLineColor());
            editText.setLineWidth(ScreenUtils.dpToPx(line.getLineWidth()));
            editText.setLineDash(ScreenUtils.dpToPx(line.getLineLength()), ScreenUtils.dpToPx(line.getLineGap()));
        }
        editText.invalidate();
    }

    public static /* synthetic */ int lambda$getSpan$54(String str, String str2) {
        int parseInt;
        try {
            parseInt = Integer.parseInt(str.substring(1)) - Integer.parseInt(str2.substring(1));
        } catch (Exception unused) {
        }
        if (parseInt > 0) {
            return 1;
        }
        return parseInt < 0 ? -1 : 0;
    }

    public /* synthetic */ void lambda$goHome$22(IAdAdapter iAdAdapter) {
        this.mainActivity.showLoadingAds(false);
        iAdAdapter.show("edit", getActivity());
        this.mainActivity.finish();
    }

    public /* synthetic */ void lambda$init$5(View view) {
        KeyboardUtils.hideKeyboard(this.editDateView);
        customDate();
    }

    public /* synthetic */ void lambda$init$6(View view) {
        addCheckItem();
    }

    public /* synthetic */ void lambda$initContentRV$7(Attachment attachment, int i6, int i7) {
        onPicClick(attachment, i6);
    }

    public /* synthetic */ void lambda$initContentRV$8() {
        this.contentUndo.add(new EditContentUndoRedoBean(this.gson.toJson(this.contentBeanList), this.contentAdapter.getCursorBeforeAfter(), this.contentAdapter.getSelectedEditText()));
        method_1(this.contentUndo);
        method_1(this.contentRedo);
        this.content_undo.setAlpha(this.contentUndo.size() > 1 ? 1.0f : 0.3f);
        this.content_redo.setAlpha(this.contentRedo.size() <= 0 ? 0.3f : 1.0f);
        setSaveIconColor();
        this.isBackOrSave = true;
        showSavingView();
    }

    public /* synthetic */ boolean lambda$initContentRV$9(View view, MotionEvent motionEvent) {
        int y6 = (int) motionEvent.getY();
        int x6 = (int) motionEvent.getX();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.lastY = y6;
            this.lastX = x6;
        } else if (action == 1) {
            if (Math.abs(y6 - this.lastY) >= 4 || Math.abs(x6 - this.lastX) >= 4) {
                hideEdit();
            } else if (y6 > this.contentRecycler.getBottom()) {
                if (!this.isEdit) {
                    quitRead();
                    return false;
                }
                this.contentAdapter.cursorEnd();
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$initSearchLayout$11(View view) {
        this.searchEdit.setText("");
        this.searchDelete.setVisibility(View.GONE);
    }

    public /* synthetic */ void lambda$initSearchLayout$12(View view) {
        FirebaseReportUtils.getInstance().reportNew("edit_do_search");
        String lowerCase = this.searchEdit.getText().toString().trim().toLowerCase();
        if (lowerCase.length() > 0 && this.needSearch) {
            searchTextView(lowerCase);
        } else {
            resetSearchedText();
            this.needSearch = false;
        }
    }

    public /* synthetic */ boolean lambda$initSearchLayout$13(TextView textView, int i6, KeyEvent keyEvent) {
        if (i6 != 3 && i6 != 0) {
            return true;
        }
        FirebaseReportUtils.getInstance().reportNew("edit_do_search");
        String lowerCase = this.searchEdit.getText().toString().trim().toLowerCase();
        if (lowerCase.length() <= 0 || !this.needSearch) {
            return true;
        }
        searchTextView(lowerCase);
        return true;
    }

    public /* synthetic */ void lambda$initTags$10(String str) {
        DialogAddCategory.INSTANCE.showOneTitleDialog(this.mainActivity, R.string.delete_tag, R.string.yes, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.9
            final /* synthetic */ String val$str;

            AnonymousClass9(String str2) {
                r2 = str2;
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
            public void positiveClick(String str2) {
                DetailFragmentNew.this.currentTagList.remove(r2);
                if (DetailFragmentNew.this.tagsAdapter != null) {
                    DetailFragmentNew.this.tagsAdapter.notifyDataSetChanged();
                }
                if (DetailFragmentNew.this.currentTagList.size() == 0) {
                    DetailFragmentNew.this.tagRecycleView.setVisibility(View.GONE);
                }
                StringBuilder sb = new StringBuilder();
                for (int i6 = 0; i6 < DetailFragmentNew.this.currentTagList.size(); i6++) {
                    sb.append(DetailFragmentNew.this.currentTagList.get(i6));
                    sb.append(",");
                }
                DetailFragmentNew.this.noteTmp.setTags(sb.toString());
                DetailFragmentNew.this.timeModified = true;
            }
        }, null);
    }

    public /* synthetic */ void lambda$initTopLayout$16(View view) {
        if (KeyboardUtils.isKeyboardShowed(this.mainActivity) && (this.mainActivity.getWindow().getDecorView().findFocus() instanceof EditText)) {
            KeyboardUtils.hideKeyboard(this.mainActivity.getWindow().getDecorView().findFocus());
        }
        FirebaseReportUtils.getInstance().reportNew("edit_select_category");
        showCateDialog();
        this.topCategoryArrow.setImageResource(this.categoryArrowUpResource);
    }

    public /* synthetic */ void lambda$initViewAttachments$17() {
        initBelowLayout(this.audioList);
    }

    public /* synthetic */ boolean lambda$initViewContent$20(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        this.userFoucChanged = this.isEdit;
        return false;
    }

    public /* synthetic */ void lambda$initViewContent$21() {
        if (this.isWidgetHideBoard) {
            return;
        }
        KeyboardUtils.showKeyboard(this.content);
        this.isWidgetHideBoard = true;
    }

    public static /* synthetic */ boolean lambda$initViewTitle$18(View view, DragEvent dragEvent) {
        return true;
    }

    public /* synthetic */ boolean lambda$initViewTitle$19(TextView textView, int i6, KeyEvent keyEvent) {
        if (this.userPreferences.getEditBgLayoutShow()) {
            return false;
        }
        EditContentAdapter editContentAdapter = this.contentAdapter;
        if (editContentAdapter != null) {
            editContentAdapter.cursorOne();
        }
        EditText editText = this.content;
        editText.setSelection(editText.getText().length());
        return false;
    }

    public /* synthetic */ boolean lambda$initViews$14(View view, MotionEvent motionEvent) {
        int y6 = (int) motionEvent.getY();
        int x6 = (int) motionEvent.getX();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.lastY = y6;
            this.lastX = x6;
        } else if (action == 1) {
            if (Math.abs(y6 - this.lastY) >= 4 || Math.abs(x6 - this.lastX) >= 4) {
                hideEdit();
            } else {
                if (!this.isEdit) {
                    quitRead();
                    return false;
                }
                showFocusAndKeyBoard();
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$new$43(View view, boolean z6) {
        clipboard(z6);
    }

    public /* synthetic */ void lambda$onActivityCreated$2() {
        KeyboardUtils.hideKeyboard(this.mBottomBar);
        bottomDrawImg();
    }

    public /* synthetic */ void lambda$onActivityCreated$3() {
        for (int i6 = 0; i6 < this.baseEntrys.size(); i6++) {
            parseCustomCode(this.baseEntrys.get(i6).getmEditText(), this.baseEntrys.get(i6).getOriginStr());
        }
        if (!TextUtils.isEmpty(this.defaultContentStyle)) {
            EditText editText = this.content;
            applyCharacterStyle(editText, editText.getEditableText(), this.defaultContentStyle, true);
        }
        saveSkech = false;
    }

    public /* synthetic */ void lambda$onActivityCreated$4() {
        int i6 = this.mainActivity.notifyType;
        if (i6 == DailyReminderReceiver.SHARE_LONGPIC) {
            new Timer().schedule(new TimerTask() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.3
                AnonymousClass3() {
                }

                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    EditContentAdapter editContentAdapter = DetailFragmentNew.this.contentAdapter;
                    if (editContentAdapter != null) {
                        HashMap<Integer, EditText> editTextHashMap = editContentAdapter.getEditTextHashMap();
                        ArrayList arrayList = new ArrayList();
                        Iterator<Integer> it2 = editTextHashMap.keySet().iterator();
                        int i62 = 0;
                        while (it2.hasNext()) {
                            int intValue = it2.next().intValue();
                            if (intValue == i62) {
                                i62++;
                                arrayList.add(editTextHashMap.get(Integer.valueOf(intValue)));
                            }
                        }
                        DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                        ShareUtil.shareLongPictures(detailFragmentNew.noteTmp, detailFragmentNew.title, detailFragmentNew.contentBeanList, arrayList, detailFragmentNew.mainActivity, detailFragmentNew.toggleChecklistView, DetailFragmentNew.this.currentFontBean, DetailFragmentNew.this.topTimeText);
                    }
                }
            }, 100L);
        } else if (i6 == DailyReminderReceiver.SHARE_PDF) {
            exportPDF();
        }
    }

    public static /* synthetic */ void lambda$onActivityResult$39(boolean z6, File file) {
        CustomBgBean customBgBean = new CustomBgBean();
        customBgBean.setCreation(System.currentTimeMillis());
        customBgBean.setLastModification(System.currentTimeMillis());
        customBgBean.setDarkBg(z6);
        customBgBean.setUri(file.getName());
        DbHelper.getInstance().updateCustomBg(customBgBean);
        EventUtils.post(108, customBgBean);
    }

    public /* synthetic */ void lambda$onResume$0() {
        clipboard(true);
    }

    public /* synthetic */ void lambda$onResume$1() {
        DialogEmojiFragment dialogEmojiFragment = this.dialogEmojiFragment;
        if (dialogEmojiFragment == null || dialogEmojiFragment.getDialog() == null || this.dialogEmojiFragment.getDialog().isShowing()) {
            return;
        }
        showInputMethodIfHasFocus();
    }

    public /* synthetic */ void lambda$reportSaveNoteEvent$41(String str) {
        StringBuilder sb;
        NoteBgBean noteBgBean;
        int i6;
        if (this.mLastNoteBg == null) {
            sb = new StringBuilder();
            sb.append("");
            noteBgBean = this.mOriginalNoteBg;
        } else {
            sb = new StringBuilder();
            sb.append("");
            noteBgBean = this.mLastNoteBg;
        }
        sb.append(noteBgBean.getId());
        String sb2 = sb.toString();
        int i7 = !TextUtils.isEmpty(this.noteTmp.getAlarm()) ? 1 : 0;
        boolean equals = TextUtils.equals("1", "" + this.noteTmp.getPinState());
        EmojiManager.parseCharSequence(new SpannableStringBuilder(this.noteTmp.getContent()));
        boolean z6 = false;
        int customEmojiNum = !TextUtils.isEmpty(this.noteTmp.getContent()) ? Util.getCustomEmojiNum(this.noteTmp.getContent(), Constants.EMOJI_TAG) : 0;
        boolean booleanValue = this.noteTmp.isLocked().booleanValue();
        Iterator<Attachment> it2 = this.imageList.iterator();
        int i8 = 0;
        while (it2.hasNext()) {
            if (ConstantsBase.MIME_TYPE_SKETCH.equals(it2.next().getMime_type())) {
                i8++;
            }
        }
        int i9 = this.checkSize;
        String str2 = !TextUtils.isEmpty(this.currentFontName) ? this.currentFontName : ResNoteFontManager.DEFAULT_FONT;
        boolean isVip = App.isVip();
        if (40002 != this.noteTmp.getBgId() && !App.userConfig.getBgUsed()) {
            App.userConfig.setBgUsed(true);
            z6 = true;
        }
        if (this.imageList.size() > 0 && !App.userConfig.getImageUsed()) {
            App.userConfig.setImageUsed(true);
            z6 = true;
        }
        if (this.audioList.size() > 0 && !App.userConfig.getRecordUsed()) {
            App.userConfig.setRecordUsed(true);
            z6 = true;
        }
        if (i8 > 0 && !App.userConfig.getDrawUsed()) {
            App.userConfig.setDrawUsed(true);
            z6 = true;
        }
        if (customEmojiNum > 0 && !App.userConfig.getEmojiUsed()) {
            App.userConfig.setEmojiUsed(true);
            z6 = true;
        }
        if (z6) {
            reportFunctionUse();
        }
        if (!this.isShowClipboardView || ((i6 = this.clipboardDown) != 0 && i6 != 1)) {
            FirebaseReportUtils.getInstance().reportNew(str, "content_new", "" + this.noteTmp.getTitle().length() + "%" + this.noteTmp.getContent().length() + "%" + this.imageList.size() + "%" + i9 + "%" + sb2 + "%" + i7 + "%" + (equals ? 1 : 0) + "%" + this.audioList.size() + "%" + i8 + "%" + (booleanValue ? 1 : 0) + "%" + customEmojiNum + "%" + str2 + "%" + (isVip ? 1 : 0) + "%" + (this.isCalendarActivityComeIn ? 1 : 0));
            return;
        }
        FirebaseReportUtils.getInstance().reportNew(str, "content_new", "" + this.noteTmp.getTitle().length() + "%" + this.noteTmp.getContent().length() + "%" + this.imageList.size() + "%" + i9 + "%" + sb2 + "%" + i7 + "%" + (equals ? 1 : 0) + "%" + this.audioList.size() + "%" + i8 + "%" + (booleanValue ? 1 : 0) + "%" + customEmojiNum + "%" + this.clipboardDown + "%" + str2 + "%" + (isVip ? 1 : 0) + "%" + (this.isCalendarActivityComeIn ? 1 : 0));
    }

    public /* synthetic */ void lambda$saveNote$40() {
        WidgetUtils.updateWidget(this.noteTmp, this.mainActivity);
    }

    public /* synthetic */ void lambda$setDarkModeView$56(boolean z6) {
        try {
            this.mChecklistManager.mCheckListView.setChecklistColor(z6);
            ChecklistManager checklistManager = this.mChecklistManager2;
            if (checklistManager != null) {
                checklistManager.mCheckListView.setChecklistColor(z6);
            }
        } catch (Exception unused) {
        }
    }

    public /* synthetic */ void lambda$showBubbleDialog$23(BubbleDialog bubbleDialog, View view) {
        this.needSearch = false;
        this.searchLayout.setVisibility(View.VISIBLE);
        this.searchEdit.requestFocus();
        KeyboardUtils.showKeyboard(this.searchEdit);
        this.mainActivity.setStatusBarColor(false);
        FirebaseReportUtils.getInstance().reportNew("edit_more_search");
        bubbleDialog.dismiss();
    }

    public /* synthetic */ void lambda$showBubbleDialog$24(TextView textView, ImageView imageView, View view) {
        if (this.noteTmp.getFavorite() == 1) {
            this.noteTmp.setFavorite(0);
            textView.setText(R.string.add_to_fav);
            imageView.setImageResource(R.drawable.ic_baseline_star_border_24);
            Toast.makeText(App.getAppContext(), R.string.remove_from_fav_toast, 0).show();
            FirebaseReportUtils.getInstance().reportNew("edit_more_remove_fav");
        } else {
            this.noteTmp.setFavorite(1);
            textView.setText(R.string.remove_from_fav);
            imageView.setImageResource(R.drawable.ic_baseline_star_24);
            Toast.makeText(App.getAppContext(), R.string.add_to_fav_toast, 0).show();
            FirebaseReportUtils.getInstance().reportNew("edit_more_add_fav");
        }
        this.timeModified = true;
        FirebaseReportUtils.getInstance().reportNew("edit_more_search");
    }

    public /* synthetic */ void lambda$showBubbleDialog$25(View view, ImageView imageView, TextView textView, BubbleDialog bubbleDialog, View view2) {
        this.userPreferences.setLockClick(true);
        App.userConfig.setEnableNoteSwitch(true);
        if (this.showLockRed) {
            FirebaseReportUtils.getInstance().reportNew("time_red_edit_lock_click");
        }
        view.setVisibility(4);
        this.userPreferences.setTimeEditLockRed(2);
        setMoreRed(false);
        this.showLockRed = false;
        if (imageView != null) {
            imageView.setImageResource(this.isDark ? R.drawable.ic_unlock_blue2_white : R.drawable.ic_unlock_blue2);
        }
        FirebaseReportUtils.getInstance().reportNew("edit_top_lock_click");
        isClickLock(this.noteTmp, imageView, textView);
        bubbleDialog.dismiss();
    }

    public /* synthetic */ void lambda$showBubbleDialog$26(ImageView imageView, TextView textView, BubbleDialog bubbleDialog, View view) {
        this.userPreferences.setReadingDialogShowed(true);
        boolean z6 = !this.isEdit;
        this.isEdit = z6;
        this.nestedScrollView.setIntercept(!z6);
        refreshRead();
        this.showReadingRed = false;
        if (this.isEdit) {
            imageView.setImageResource(this.isDark ? R.drawable.ic_reading_mode_white : R.drawable.ic_reading_mode);
            textView.setText(R.string.reading_mode);
        } else {
            imageView.setImageResource(this.isDark ? R.drawable.ic_edit_white : R.drawable.ic_edit);
            textView.setText(R.string.editing_mode);
        }
        bubbleDialog.dismiss();
    }

    public /* synthetic */ void lambda$showBubbleDialog$27(BubbleDialog bubbleDialog, View view) {
        bubbleDialog.dismiss();
        DialogAddCategory.INSTANCE.showOneTitleDialog(this.mainActivity, R.string.archive_content, R.string.archive, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.20
            AnonymousClass20() {
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
            public void positiveClick(String str) {
                DetailFragmentNew.this.archiveNote(true);
                Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.note_archived, 0).show();
            }
        }, null);
        FirebaseReportUtils.getInstance().reportNew("edit_more_archive");
    }

    public /* synthetic */ void lambda$showBubbleDialog$28(ImageView imageView, TextView textView, View view) {
        this.editAction.append("_P");
        if (!TextUtils.isEmpty(this.noteTmp.getTitle()) || !TextUtils.isEmpty(this.noteTmp.getContent()) || (this.noteTmp.getAttachmentsList() != null && this.noteTmp.getAttachmentsList().size() > 0)) {
            this.isBackOrSave = true;
        }
        setSaveIconColor();
        Note note = this.noteTmp;
        note.setIsPined(note.getPinState() != 0 ? 0 : 1);
        imageView.setImageResource(this.noteTmp.getPinState() == 0 ? this.pinViewResource : R.drawable.ic_flag_blue_solid);
        textView.setText(this.mainActivity.getResources().getString(this.noteTmp.getPinState() == 0 ? R.string.menu_pin : R.string.menu_unpin));
        if (this.noteTmp.getPinState() == 0) {
            imageView.setImageResource(this.pinViewResource);
        }
        if (this.noteTmp.getPinState() == 0) {
            FirebaseReportUtils.getInstance().reportNew("edit_menu_pin_click", "key_pin_state", "unpin");
            Toast.makeText(App.getAppContext(), R.string.pin_cancel, 0).show();
        } else {
            FirebaseReportUtils.getInstance().reportNew("edit_menu_pin_click", "key_pin_state", "pin");
            Toast.makeText(App.getAppContext(), R.string.pin_success, 0).show();
        }
    }

    public /* synthetic */ void lambda$showBubbleDialog$29(BubbleDialog bubbleDialog, View view) {
        bubbleDialog.dismiss();
        FirebaseReportUtils.getInstance().reportNew("edit_more_delete");
        FirebaseReportUtils.getInstance().reportNew("edit_more_delete_show");
        DialogAddCategory.INSTANCE.showOneTitleDialog(this.mainActivity, R.string.delete_content, R.string.delete, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.21
            AnonymousClass21() {
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
            public void positiveClick(String str) {
                DetailFragmentNew.this.trashNote(true);
                Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.delete_success, 0).show();
                FirebaseReportUtils.getInstance().reportNew("edit_more_delete_OK");
            }
        }, null);
    }

    public /* synthetic */ void lambda$showBubbleDialog$30(View view, BubbleDialog bubbleDialog, View view2) {
        this.userPreferences.setPdfClick(true);
        this.userPreferences.setTimeEditPDFRed(2);
        this.showPdfRed = false;
        setMoreRed(false);
        view.setVisibility(4);
        FirebaseReportUtils.getInstance().reportNew("edit_more_pdf");
        if (App.isVip() || App.is6hFreeTry()) {
            exportPDF();
            FirebaseReportUtils.getInstance().reportNew("edit_more_pdf_vip");
        } else {
            VipDiscountUtil.jumpToVipPage(this.mainActivity, "pdf");
        }
        bubbleDialog.dismiss();
    }

    public /* synthetic */ void lambda$showBubbleDialog$31(String str, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
        Note note = this.noteTmp;
        note.setRecurrenceRule(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, note.getRecurrenceRule()));
        if (TextUtils.isEmpty(str)) {
            this.noteTmp.setAlarm((String) null);
        } else {
            this.noteTmp.setAlarm(str);
        }
        this.alarmChanged = true;
    }

    public /* synthetic */ void lambda$showBubbleDialog$32(View view, BubbleDialog bubbleDialog, View view2) {
        this.userPreferences.setReminderClick(true);
        if (this.showRemindRed) {
            FirebaseReportUtils.getInstance().reportNew("time_red_remind_click");
        }
        view.setVisibility(4);
        this.userPreferences.setTimeEditRemindRed(2);
        this.showRemindRed = false;
        setMoreRed(false);
        this.isBackOrSave = true;
        setSaveIconColor();
        KeyboardUtils.hideKeyboard(this.reminderView);
        if (this.noteTmp.getAlarm() != null) {
            DialogAddCategory.INSTANCE.showAlarmListDialog(this.noteTmp, this.mainActivity, new DialogAddCategory.AlarmActionListener() { // from class: b6.c3
                @Override // notes.easy.android.mynotes.view.DialogAddCategory.AlarmActionListener
                public void updateNewAlarm(String str, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
                    DetailFragmentNew.this.lambda$showBubbleDialog$31(str, recurrenceOption);
                }
            });
        } else {
            FirebaseReportUtils.getInstance().reportNew("edit_reminder_show");
            DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
            Note note = this.noteTmp;
            dialogAddCategory.showEditTimeDialog(true, note, this.mainActivity, note.getRecurrenceRule(), R.string.add_reminder, new DialogAddCategory.TimerChangedListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.22
                AnonymousClass22() {
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                public void doRingTonChoose() {
                    Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                    intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
                    intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
                    DetailFragmentNew.this.startActivityForResult(intent, 7);
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
                    if (DetailFragmentNew.nowUri != null) {
                        DetailFragmentNew.this.userPreferences.setRingTonNow(DetailFragmentNew.nowUri.toString());
                    }
                    if (DetailFragmentNew.this.noteTmp.isTrashed().booleanValue() || DetailFragmentNew.this.noteTmp.isArchived().booleanValue()) {
                        Toast.makeText(App.getAppContext(), R.string.edit_alarm_success, 0).show();
                        return;
                    }
                    Note note2 = DetailFragmentNew.this.noteTmp;
                    note2.setRecurrenceRule(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, note2.getRecurrenceRule()));
                    DetailFragmentNew.this.noteTmp.setAlarm(j6);
                    DetailFragmentNew.this.alarmChanged = true;
                    Toast.makeText(App.getAppContext(), R.string.set_alarm_success, 0).show();
                    FirebaseReportUtils.getInstance().reportNew("edit_reminder_OK");
                }
            });
        }
        FirebaseReportUtils.getInstance().logMainFlow(getContext(), "edit_more_reminder");
        bubbleDialog.dismiss();
    }

    public /* synthetic */ void lambda$showBubbleDialog$33(View view) {
        if (!DeviceUtils.isPinWidgetSupport(App.getAppContext())) {
            if (ScreenUtils.isPad(App.getAppContext())) {
                startActivity(new Intent(this.mainActivity, WidgetTutorialPad.class));
                return;
            } else {
                startActivity(new Intent(this.mainActivity, WidgetTutorial.class));
                return;
            }
        }
        this.userPreferences.setWidgetClick(true);
        WidgetCustomizeActivity.Companion.setNote(this.noteTmp);
        WidgetFirebaseReport widgetFirebaseReport = new WidgetFirebaseReport();
        widgetFirebaseReport.setEdit("edit");
        widgetFirebaseReport.setEditAdd(true);
        widgetFirebaseReport.setEditAddOk(true);
        if (ScreenUtils.isPad(this.mainActivity)) {
            Intent intent = new Intent(this.mainActivity, WidgetSelectActivityPad.class);
            intent.putExtra(Constants.EXTRA_IS_SIDEBAR, false);
            intent.putExtra(Constants.EXTRA_WIDGET_FIREBASE_REPORT, widgetFirebaseReport);
            startActivity(intent);
        } else {
            Intent intent2 = new Intent(this.mainActivity, WidgetSelectActivity.class);
            intent2.putExtra(Constants.EXTRA_IS_SIDEBAR, false);
            intent2.putExtra(Constants.EXTRA_WIDGET_FIREBASE_REPORT, widgetFirebaseReport);
            startActivity(intent2);
        }
        FirebaseReportUtils.getInstance().logMainFlow(getContext(), Constants.EDIT_MORE_WIDGET);
    }

    public /* synthetic */ void lambda$showBubbleDialog$34(BubbleDialog bubbleDialog, View view) {
        FirebaseReportUtils.getInstance().reportNew("edit_more_tags");
        bubbleDialog.dismiss();
        DialogAddCategory.INSTANCE.showAddTagDialog(this.mainActivity, true, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.23
            AnonymousClass23() {
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
            public void positiveClick(String str) {
                FirebaseReportUtils.getInstance().reportNew("edit_more_tags_added");
                List<String> tagList = EasyNoteManager.getInstance().getTagList(App.userConfig.getTags());
                if (!tagList.contains(str)) {
                    tagList.add(str);
                    App.userConfig.setTags(EasyNoteManager.getInstance().getTagString(tagList));
                    EventUtils.post(106);
                }
                String tags = DetailFragmentNew.this.noteTmp.getTags();
                if (!DetailFragmentNew.this.currentTagList.contains(str)) {
                    if (TextUtils.isEmpty(tags)) {
                        DetailFragmentNew.this.noteTmp.setTags(str);
                    } else {
                        DetailFragmentNew.this.noteTmp.setTags(tags + "," + str);
                    }
                    DetailFragmentNew.this.currentTagList.add(str);
                    if (DetailFragmentNew.this.tagsAdapter != null) {
                        DetailFragmentNew.this.tagsAdapter.notifyDataSetChanged();
                    }
                    if (DetailFragmentNew.this.tagRecycleView.getVisibility() != 0) {
                        DetailFragmentNew.this.tagRecycleView.setVisibility(View.VISIBLE);
                    }
                    DetailFragmentNew.this.timeModified = true;
                }
                KeyboardUtils.hideKeyboard(DetailFragmentNew.this.mainActivity);
            }
        });
    }

    public /* synthetic */ void lambda$showFontDialog$44(View view, View view2) {
        boolean z6 = !this.isBold;
        this.isBold = z6;
        if (z6) {
            view.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            view.setBackground(null);
        }
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_bold");
    }

    public /* synthetic */ void lambda$showFontDialog$45(View view, View view2) {
        boolean z6 = !this.isItalic;
        this.isItalic = z6;
        if (z6) {
            view.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            view.setBackground(null);
        }
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_italic");
    }

    public /* synthetic */ void lambda$showFontDialog$46(View view, View view2) {
        boolean z6 = !this.isUnderline;
        this.isUnderline = z6;
        if (z6) {
            view.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            view.setBackground(null);
        }
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_underline");
    }

    public /* synthetic */ void lambda$showFontDialog$47(View view, View view2) {
        boolean z6 = !this.isStrikethrough;
        this.isStrikethrough = z6;
        if (z6) {
            view.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            view.setBackground(null);
        }
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_strikethrough");
    }

    public /* synthetic */ void lambda$showFontDialog$48(TextView textView, View view) {
        int i6 = this.mCurrentFontIndex;
        if (i6 >= 1) {
            int i7 = i6 - 1;
            this.mCurrentFontIndex = i7;
            List<Integer> list = Constants.FONT_SIZE_LIST;
            if (i7 < list.size()) {
                textView.setText(Strings.valueOf(list.get(this.mCurrentFontIndex)));
            }
        }
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_size_minus");
    }

    public /* synthetic */ void lambda$showFontDialog$49(TextView textView, View view) {
        int i6 = this.mCurrentFontIndex;
        List<Integer> list = Constants.FONT_SIZE_LIST;
        if (i6 < list.size() - 1) {
            int i7 = this.mCurrentFontIndex + 1;
            this.mCurrentFontIndex = i7;
            if (i7 < list.size()) {
                textView.setText(Strings.valueOf(list.get(this.mCurrentFontIndex)));
            }
        }
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_size_plus");
    }

    public /* synthetic */ void lambda$showFontDialog$50(View view) {
        CustomDialog customDialog = this.fontDialog;
        if (customDialog != null) {
            customDialog.dismiss();
        }
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_cancel");
        this.highLightColor = "";
        this.isBold = false;
        this.isUnderline = false;
        this.isStrikethrough = false;
        this.isItalic = false;
        showInputMethodIfHasFocus();
    }

    public /* synthetic */ void lambda$showFontDialog$51(View view) {
        Bundle bundle = new Bundle();
        StringBuilder sb = new StringBuilder();
        if (this.isBold) {
            sb.append("Bold");
            sb.append("_");
        }
        if (this.isItalic) {
            sb.append("Italic");
            sb.append("_");
        }
        if (this.isUnderline) {
            sb.append("UnderLine");
            sb.append("_");
        }
        if (!TextUtils.isEmpty(this.highLightColor)) {
            sb.append(this.highLightColor);
            sb.append("_");
        }
        if (this.isStrikethrough) {
            sb.append("Strikethrough");
            sb.append("_");
        }
        bundle.putString("Key_status_newuser", sb.toString());
        FirebaseReportUtils.getInstance().reportOnlyNew("edit_tool_font_OK", bundle);
        Bundle bundle2 = new Bundle();
        bundle2.putString("Key_status", sb.toString());
        FirebaseReportUtils.getInstance().reportAll("edit_tool_font_OK", bundle2);
        CustomDialog customDialog = this.fontDialog;
        if (customDialog != null) {
            customDialog.dismiss();
        }
        showInputMethodIfHasFocus();
    }

    public /* synthetic */ void lambda$toggleChecklist2$35() {
        try {
            this.mChecklistManager.mCheckListView.setChecklistColor(true);
        } catch (Exception unused) {
        }
    }

    public /* synthetic */ void lambda$toggleChecklist2$36() {
        try {
            this.mChecklistManager.mCheckListView.setChecklistColor(false);
        } catch (Exception unused) {
        }
    }

    private boolean lastModificationUpdatedNeeded() {
        this.note.setCategory(this.noteTmp.getCategory());
        this.note.setArchived(this.noteTmp.isArchived());
        this.note.setTrashed(this.noteTmp.isTrashed());
        this.note.setLocked(this.noteTmp.isLocked());
        return this.noteTmp.isChanged(this.note);
    }

    private void lockUnlock() {
        if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) == null) {
            Toast.makeText(App.getAppContext(), R.string.password_not_set, 0).show();
            return;
        }
        Toast.makeText(App.getAppContext(), R.string.save_note_to_lock_it, 0).show();
        this.noteTmp.setLocked(Boolean.valueOf(!r0.isLocked().booleanValue()));
        this.noteTmp.setPasswordChecked(true);
    }

    private void newABaseView() {
        BaseEditView baseEditView = new BaseEditView(this.mainActivity);
        setNewTextViewColor(baseEditView.getmEditText());
        baseEditView.setClickListener(this);
        baseEditView.getmEditText().setEditTextMenuListener(this);
        this.baseEntrys.add(baseEditView);
        int childCount = this.contentLayout.getChildCount() - 1;
        if (this.checkSize == 2) {
            childCount = this.contentLayout.getChildCount() - 2;
        }
        this.contentLayout.addView(baseEditView.getBaseView(), childCount);
    }

    private boolean noteHasChanged() {
        return !this.noteTmp.getTitle().equalsIgnoreCase(this.noteOriginal.getTitle()) || !this.noteTmp.getContent().equalsIgnoreCase(this.noteOriginal.getContent()) || this.noteTmp.getPinState() != this.noteOriginal.getPinState() || this.noteTmp.isLocked() != this.noteOriginal.isLocked() || this.noteTmp.getCategory() != this.noteOriginal.getCategory() || this.noteOriginal.getBgId() != this.noteTmp.getBgId() || (this.noteTmp.getBgId() == 10 && !TextUtils.equals(this.noteOriginal.getStickyColor(), this.noteTmp.getStickyColor())) || attachmentChanged() || colorChanged();
    }

    private void notifyChanged(int i6) {
        Message message = new Message();
        message.what = 2;
        message.arg1 = i6;
        this.mRefreshHandler.sendMessage(message);
    }

    private void notifyData() {
        Message message = new Message();
        message.what = 0;
        message.arg1 = 0;
        this.mRefreshHandler.sendMessage(message);
    }

    private void notifyRemoved(int i6) {
        Message message = new Message();
        message.what = 3;
        message.arg1 = i6;
        this.mRefreshHandler.sendMessage(message);
    }

    private void parseCustomCode(EditText editText, String str) {
        if (TextUtils.isEmpty(str)) {
            editText.setText("");
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        EmojiManager.parseCharSequence(spannableStringBuilder);
        editText.setText(spannableStringBuilder);
    }

    private void pauseRecording() {
        this.durationTimer.cancel();
        this.amplitudeTimer.cancel();
        this.isRecordingPause = false;
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mRecorder.pause();
            }
        } catch (Exception unused) {
        }
    }

    private void quitRead() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mPressedTime > 1000) {
            this.mPressedTime = currentTimeMillis;
        } else {
            this.isEdit = true;
            refreshRead();
        }
    }

    /* renamed from: reLayoutLineBgState */
    public void lambda$setStickyColor$55() {
        if (this.baseEntrys.size() <= 0) {
            this.content.setMinHeight(this.fragment_layout.getHeight());
            return;
        }
        int height = this.content.getHeight() + this.mGridView.getHeight();
        for (int i6 = 0; i6 < this.baseEntrys.size(); i6++) {
            if (i6 == this.baseEntrys.size() - 1) {
                EditText editText = this.baseEntrys.get(i6).getmEditText();
                if (this.fragment_layout.getHeight() > height) {
                    editText.setMinHeight(this.fragment_layout.getHeight() - height);
                }
            } else {
                height = height + this.baseEntrys.get(i6).getmEditText().getHeight() + this.baseEntrys.get(i6).getGridView().getHeight();
            }
        }
    }

    private void refreshRead() {
        if (this.isEdit) {
            FirebaseReportUtils.getInstance().reportNew("editing_click");
            FirebaseReportUtils.getInstance().reportNew("editing_show");
            this.mBottomBar.setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.detail_content_card.getLayoutParams();
            layoutParams.bottomMargin = dpToPx(60);
            this.detail_content_card.setLayoutParams(layoutParams);
            Toast.makeText(getActivity(), R.string.reading_tips_off, 0).show();
        } else {
            FirebaseReportUtils.getInstance().reportNew("edit_reading_click");
            FirebaseReportUtils.getInstance().reportNew("edit_reading_show");
            closeKeyboard();
            this.mBottomBar.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.detail_content_card.getLayoutParams();
            layoutParams2.bottomMargin = 0;
            this.detail_content_card.setLayoutParams(layoutParams2);
            Toast.makeText(getActivity(), R.string.reading_tips_on, 0).show();
        }
        setEditText();
    }

    private void removeAttachment(Attachment attachment) {
        if (attachment.getMime_type() != null) {
            if (attachment.getMime_type().equals("audio/amr")) {
                this.audioList.remove(attachment);
            } else {
                this.imageList.remove(attachment);
            }
        }
        this.noteTmp.removeAttachment(attachment);
    }

    public void reportFunctionUse() {
        StringBuilder sb = new StringBuilder();
        if (App.userConfig.getFontUsed()) {
            sb.append("a1_");
        } else {
            sb.append("a0_");
        }
        if (App.userConfig.getRecordUsed()) {
            sb.append("r1_");
        } else {
            sb.append("r0_");
        }
        if (App.userConfig.getDrawUsed()) {
            sb.append("d1_");
        } else {
            sb.append("d0_");
        }
        if (App.userConfig.getBgUsed()) {
            sb.append("b1_");
        } else {
            sb.append("b0_");
        }
        if (App.userConfig.getImageUsed()) {
            sb.append("p1_");
        } else {
            sb.append("p0_");
        }
        if (App.userConfig.getEmojiUsed()) {
            sb.append("e1_");
        } else {
            sb.append("e0_");
        }
        if (App.userConfig.getShareUsed()) {
            sb.append("s1");
        } else {
            sb.append("s0");
        }
        if (TextUtils.isEmpty(sb.toString())) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("pr_status", sb.toString());
        FirebaseReportUtils.getInstance().reportNew("user_functions_status", bundle);
        App.userConfig.setAppFuncusage(sb.toString());
    }

    private void resetLayoutLineBgState() {
        if (this.baseEntrys.size() <= 0) {
            this.content.setMinHeight(0);
            return;
        }
        for (int i6 = 0; i6 < this.baseEntrys.size(); i6++) {
            if (i6 == this.baseEntrys.size() - 1) {
                this.baseEntrys.get(i6).getmEditText().setMinHeight(0);
            }
        }
    }

    private void resetStickerColor() {
        NoteBgBean noteBgBean;
        if (!App.isVip() && (noteBgBean = this.mRecentNoteBg) != null && noteBgBean.isVip()) {
            this.mRecentNoteBg = ResNoteBgManager.getDefaultBg();
        }
        NoteBgBean noteBgBean2 = this.mRecentNoteBg;
        this.mLastNoteBg = noteBgBean2;
        setStickyColor(0, "", 0, noteBgBean2);
        setSaveIconColor();
        notifyData();
    }

    private void resumeRecording() {
        this.isRecordingPause = true;
        broadcastRecorderInfo();
        Timer timer = new Timer();
        this.durationTimer = timer;
        timer.scheduleAtFixedRate(getDurationUpdateTask(), 1000L, 1000L);
        try {
            if (Build.VERSION.SDK_INT > 24) {
                this.mRecorder.resume();
            }
        } catch (Exception unused) {
        }
    }

    private boolean saveNotNeeded() {
        return !this.noteTmp.isChanged(this.note) && this.noteTmp.getPinState() == this.noteOriginal.getPinState() && ((this.noteTmp.getBgId() != 10 && this.noteOriginal.getBgId() == this.noteTmp.getBgId()) || (this.noteTmp.getBgId() == 10 && TextUtils.equals(this.noteOriginal.getStickyColor(), this.noteTmp.getStickyColor()))) && !this.fontStyleChange;
    }

    public void scrollContent() {
        if (this.noteTmp.isChecklist().booleanValue()) {
            if (this.mChecklistManager.getCount() > this.contentLineCounter) {
                this.scrollView.scrollBy(0, 60);
            }
            this.contentLineCounter = this.mChecklistManager.getCount();
        } else {
            if (this.content.getLineCount() > this.contentLineCounter) {
                this.scrollView.scrollBy(0, 60);
            }
            this.contentLineCounter = this.content.getLineCount();
        }
    }

    private void searchTextView(String str) {
        boolean z6;
        this.contentSearched = this.gson.toJson(this.contentBeanList);
        EditContentAdapter editContentAdapter = this.contentAdapter;
        if (editContentAdapter != null) {
            editContentAdapter.cursorNo();
        }
        int i6 = -1;
        if (this.title.getText() == null || !this.title.getText().toString().toLowerCase().contains(str)) {
            z6 = false;
        } else {
            int indexOf = this.title.getText().toString().toLowerCase().indexOf(str);
            while (indexOf != -1) {
                EditText editText = this.title;
                setTextBackground(editText, editText.getText(), indexOf, indexOf + str.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                this.searchedEditList.add(this.title);
                this.searchEditIndex.add(indexOf + "," + (str.length() + indexOf));
                indexOf = this.title.getText().toString().toLowerCase().indexOf(str, indexOf + 1);
            }
            z6 = true;
        }
        if (this.content.getText() != null && this.content.getText().toString().toLowerCase().contains(str)) {
            int indexOf2 = this.content.getText().toString().toLowerCase().indexOf(str);
            while (indexOf2 != -1) {
                EditText editText2 = this.content;
                setTextBackground(editText2, editText2.getText(), indexOf2, indexOf2 + str.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                this.searchedEditList.add(this.content);
                this.searchEditIndex.add(indexOf2 + "," + (str.length() + indexOf2));
                indexOf2 = this.content.getText().toString().toLowerCase().indexOf(str, indexOf2 + 1);
            }
            z6 = true;
        }
        int i7 = 0;
        boolean z7 = false;
        while (i7 < this.baseEntrys.size()) {
            BaseEditView baseEditView = this.baseEntrys.get(i7);
            if (baseEditView.getEditable().toString().toLowerCase().contains(str)) {
                int indexOf3 = baseEditView.getEditable().toString().toLowerCase().indexOf(str);
                while (indexOf3 != i6) {
                    int i8 = indexOf3;
                    setTextBackground(baseEditView.getmEditText(), baseEditView.getEditable(), indexOf3, indexOf3 + str.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                    this.searchedEditList.add(baseEditView.getmEditText());
                    this.searchEditIndex.add(i8 + "," + (i8 + str.length()));
                    indexOf3 = baseEditView.getmEditText().getText().toString().toLowerCase().indexOf(str, i8 + 1);
                    i6 = -1;
                }
                if (z7) {
                    z6 = true;
                } else {
                    int[] iArr = new int[2];
                    baseEditView.getmEditText().getLocationInWindow(iArr);
                    int i9 = iArr[1];
                    if (i9 < 0) {
                        i9 = 0;
                    }
                    this.scrollView.scrollTo(0, i9);
                    z6 = true;
                    z7 = true;
                }
            }
            i7++;
            i6 = -1;
        }
        StringBuilder sb = new StringBuilder();
        if (this.note.getNewData() != null && !this.note.getNewData().isEmpty()) {
            Iterator<JsonElement> it2 = new JsonParser().parse(this.note.getNewData()).getAsJsonArray().iterator();
            while (it2.hasNext()) {
                EditContentBean editContentBean = (EditContentBean) this.gson.fromJson(it2.next(), EditContentBean.class);
                if (editContentBean.getViewType() < 4) {
                    sb.append(editContentBean.getContent());
                }
            }
        }
        if (!z6) {
            z6 = sb.toString().toLowerCase().contains(str.toLowerCase());
        }
        this.contentAdapter.setSearchText(str);
        if (z6) {
            this.searchEdit.clearFocus();
            KeyboardUtils.hideKeyboard(this.searchEdit);
            FirebaseReportUtils.getInstance().reportNew("edit_do_search_success");
        } else {
            Toast.makeText(App.getAppContext(), R.string.search_fail, 0).show();
        }
        this.needSearch = false;
        this.searchButton.setText(R.string.cancel);
    }

    public void setAbsoluteFontSize(EditText editText, int i6, int i7, int i8, boolean z6) {
        if (i7 <= i6 || editText.getEditableText().length() < i7) {
            return;
        }
        editText.getEditableText().setSpan(new AbsoluteSizeSpan(i8), i6, i7, 33);
        if (z6) {
            editText.setSelection(i6, i7);
        } else {
            editText.setSelection(i7);
        }
    }

    private void setDarkModeView(final boolean z6) {
        if (this.noteTmp == null) {
            return;
        }
        if (z6) {
            this.categoryName.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_secondary));
            this.content_undo.setColorFilter(-1);
            this.content_redo.setColorFilter(-1);
            this.categoryArrowDownResource = R.drawable.ic_arrow_drop_down_white;
            this.categoryArrowUpResource = R.drawable.ic_arrow_drop_up_white;
            this.categoryImg.setImageResource(R.drawable.ic_category_white);
            this.title.setHintTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_five));
            this.title.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_primary));
            this.content.setHintTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_five));
            this.content.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_primary));
            changeBaseViewColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_primary));
            this.topTimeText.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_secondary));
            this.editDateView.setImageResource(R.drawable.ic_edit_date_white);
            this.addchecklistImg.setImageResource(R.drawable.ic_baseline_add_24);
            this.addchecklistTv.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_white_five));
            this.pinView.setImageResource(R.drawable.ic_share2_white);
            if (isNotNoteEmpty()) {
                this.exitView.setImageResource(R.drawable.ic_edit_save_white);
            } else {
                this.exitView.setImageResource(R.drawable.ic_arrow_back_24_darkmode);
            }
        } else {
            this.categoryName.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_secondary));
            this.content_undo.setColorFilter(-16777216);
            this.content_redo.setColorFilter(-16777216);
            this.categoryArrowDownResource = R.drawable.ic_arrow_drop_down_blue;
            this.categoryArrowUpResource = R.drawable.ic_arrow_drop_up_blue;
            this.categoryImg.setImageResource(R.drawable.ic_category_blue);
            this.title.setHintTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_five));
            this.title.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_primary));
            this.content.setHintTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_five));
            this.content.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_primary));
            changeBaseViewColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_primary));
            this.topTimeText.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_secondary));
            this.editDateView.setImageResource(R.drawable.ic_edit_date_blue);
            this.addchecklistImg.setImageResource(R.drawable.ic_additem);
            this.addchecklistTv.setTextColor(ContextCompat.getColor(this.mainActivity, R.color.theme_text_black_five));
            this.pinView.setImageResource(R.drawable.ic_share2_black);
            if (isNotNoteEmpty()) {
                this.exitView.setImageResource(R.drawable.ic_edit_save);
            } else {
                this.exitView.setImageResource(R.drawable.ic_arrow_back_24_black_always);
            }
        }
        if (this.showLockRed || this.showPdfRed || this.showShareRed || this.showRemindRed || this.showReadingRed) {
            this.moreView.setImageResource(z6 ? R.drawable.ic_more_white_red : R.drawable.ic_more_black_red);
        } else {
            this.moreView.setImageResource(z6 ? R.drawable.ic_more_white : R.drawable.ic_more_black);
        }
        this.mBottomBar.setDarkViewMode(z6);
        this.topCategoryArrow.setImageResource(this.categoryArrowDownResource);
        this.exitView.postDelayed(new Runnable() { // from class: b6.x2
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$setDarkModeView$56(z6);
            }
        }, 150L);
        TagsAdapter tagsAdapter = this.tagsAdapter;
        if (tagsAdapter != null) {
            tagsAdapter.setDarkMode(z6);
            this.tagsAdapter.notifyDataSetChanged();
        }
    }

    private void setEditRich(String str) {
        this.contentAdapter.setTextStyle(false);
    }

    public void setFontTypeface(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Typeface fontFromFile = ResNoteFontManager.getInstance().getFontFromFile(str);
        if (fontFromFile == null) {
            fontFromFile = ResNoteFontManager.getInstance().getFontFromFile(ResNoteFontManager.DEFAULT_FONT);
        }
        if (fontFromFile != null) {
            this.content.setTypeface(fontFromFile);
            this.title.setTypeface(fontFromFile);
            EditContentAdapter editContentAdapter = this.contentAdapter;
            if (editContentAdapter != null) {
                editContentAdapter.setStringStyle(fontFromFile);
            }
        }
    }

    private void setMoreRed(boolean z6) {
        if (this.noteTmp != null) {
            if (ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg()) {
                if (z6) {
                    this.moreView.setImageResource(R.drawable.ic_more_white_red);
                    return;
                } else {
                    this.moreView.setImageResource(R.drawable.ic_more_white);
                    return;
                }
            }
            if (z6) {
                this.moreView.setImageResource(R.drawable.ic_more_black_red);
            } else {
                this.moreView.setImageResource(R.drawable.ic_more_black);
            }
        }
    }

    private void setNewTextViewColor(EditText editText) {
        try {
            editText.setTextColor(this.content.getCurrentTextColor());
            editText.setHintTextColor(this.content.getHintTextColors());
            editText.setTypeface(this.content.getTypeface());
        } catch (Exception unused) {
        }
    }

    public void setSaveIconColor() {
        if (ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg()) {
            this.exitView.setImageResource(R.drawable.ic_edit_save_white);
        } else {
            this.exitView.setImageResource(R.drawable.ic_edit_save);
        }
    }

    private void setSelection(EditText editText) {
        try {
            editText.setSelection(editText.getText().length());
        } catch (Exception unused) {
        }
    }

    public void setTextBackground(EditText editText, Editable editable, int i6, int i7, int i8, boolean z6) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        editText.getEditableText().setSpan(new BackgroundColorSpan(i8), i6, i7, 33);
        if (z6) {
            editText.setSelection(i6, i7);
        }
    }

    public void setTextBold(EditText editText, Editable editable, boolean z6, int i6, int i7, boolean z7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        if (z6) {
            editable.setSpan(new StyleSpan(1), i6, i7, 33);
            if (z7) {
                editText.setSelection(i6, i7);
                return;
            }
            return;
        }
        for (StyleSpan styleSpan : editable.getSpans(i6, i7, StyleSpan.class)) {
            if (styleSpan != null) {
                int spanStart = editable.getSpanStart(styleSpan);
                int spanEnd = editable.getSpanEnd(styleSpan);
                if (i6 == spanStart && spanEnd == i7) {
                    editable.removeSpan(styleSpan);
                } else if (spanStart <= i6 && spanEnd >= i7) {
                    editable.removeSpan(styleSpan);
                    StyleSpan styleSpan2 = new StyleSpan(1);
                    StyleSpan styleSpan3 = new StyleSpan(1);
                    if (spanStart != i6) {
                        editable.setSpan(styleSpan2, spanStart, i6, 33);
                    }
                    if (spanEnd != i7) {
                        editable.setSpan(styleSpan3, i7, spanEnd, 33);
                    }
                    if (z7) {
                        editText.setSelection(i6, i7);
                    }
                }
            }
        }
    }

    public void setTextFontColor(EditText editText, int i6, int i7, int i8, boolean z6) {
        if (editText == null || i7 <= i6 || editText.getEditableText().length() < i7) {
            return;
        }
        editText.getEditableText().setSpan(new ForegroundColorSpan(i8), i6, i7, 33);
        if (z6) {
            editText.setSelection(i6, i7);
        } else {
            editText.setSelection(i7);
        }
    }

    public void setTextItalic(EditText editText, Editable editable, boolean z6, int i6, int i7, boolean z7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        if (z6) {
            editable.setSpan(new StyleSpan(2), i6, i7, 33);
            if (z7) {
                editText.setSelection(i6, i7);
                return;
            }
            return;
        }
        for (StyleSpan styleSpan : editable.getSpans(i6, i7, StyleSpan.class)) {
            if (styleSpan != null) {
                int spanStart = editable.getSpanStart(styleSpan);
                int spanEnd = editable.getSpanEnd(styleSpan);
                if (i6 == spanStart && spanEnd == i7) {
                    editable.removeSpan(styleSpan);
                } else if (spanStart <= i6 && spanEnd >= i7) {
                    editable.removeSpan(styleSpan);
                    StyleSpan styleSpan2 = new StyleSpan(2);
                    StyleSpan styleSpan3 = new StyleSpan(2);
                    if (spanStart != i6) {
                        editable.setSpan(styleSpan2, spanStart, i6, 33);
                    }
                    if (spanEnd != i7) {
                        editable.setSpan(styleSpan3, i7, spanEnd, 33);
                    }
                    if (z7) {
                        editText.setSelection(i6, i7);
                    }
                }
            }
        }
    }

    public void setTextRelativeSize(Editable editable, float f6, int i6, int i7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        editable.setSpan(new RelativeSizeSpan(f6), i6, i7, 33);
    }

    public void setTextShape(EditText editText, Editable editable, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str, int i6, int i7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        if (!TextUtils.isEmpty(str)) {
            editable.setSpan(new BackgroundColorSpan(Color.parseColor(str)), i6, i7, 33);
        }
        if (z7) {
            editable.setSpan(new StyleSpan(2), i6, i7, 33);
        }
        if (z9) {
            editable.setSpan(new StrikethroughSpan(), i6, i7, 33);
        }
        if (z8) {
            editable.setSpan(new UnderlineSpan(), i6, i7, 33);
        }
        if (z6) {
            editable.setSpan(new StyleSpan(1), i6, i7, 33);
        }
        int i8 = this.mCurrentFontIndex;
        if (i8 < 0) {
            this.mCurrentFontIndex = 0;
        } else {
            List<Integer> list = Constants.FONT_SIZE_LIST;
            if (i8 > list.size() - 1) {
                this.mCurrentFontIndex = list.size() - 1;
            }
        }
        editable.setSpan(new AbsoluteSizeSpan(ScreenUtils.dpToPx(Constants.FONT_SIZE_LIST.get(this.mCurrentFontIndex).intValue())), i6, i7, 33);
        editText.setSelection(i7);
        if (TextUtils.isEmpty(this.mCurrentFontColor) || Constants.DEFAULT_TEXT_COLOR.equalsIgnoreCase(this.mCurrentFontColor)) {
            return;
        }
        if (this.mCurrentFontColor.startsWith(Constants.SPECIAL_CHARACTOR)) {
            editable.setSpan(new ForegroundColorSpan(Color.parseColor(this.mCurrentFontColor)), i6, i7, 33);
        } else {
            try {
                editable.setSpan(new ForegroundColorSpan(Integer.parseInt(this.mCurrentFontColor)), i6, i7, 33);
            } catch (Exception unused) {
            }
        }
        editText.setSelection(i7);
    }

    public void setTextStrikethrough(Editable editable, boolean z6, int i6, int i7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        if (z6) {
            editable.setSpan(new StrikethroughSpan(), i6, i7, 33);
            return;
        }
        for (StrikethroughSpan strikethroughSpan : editable.getSpans(i6, i7, StrikethroughSpan.class)) {
            if (strikethroughSpan != null) {
                int spanStart = editable.getSpanStart(strikethroughSpan);
                int spanEnd = editable.getSpanEnd(strikethroughSpan);
                if (i6 == spanStart && spanEnd == i7) {
                    editable.removeSpan(strikethroughSpan);
                } else if (spanStart <= i6 && spanEnd >= i7) {
                    editable.removeSpan(strikethroughSpan);
                    StrikethroughSpan strikethroughSpan2 = new StrikethroughSpan();
                    StrikethroughSpan strikethroughSpan3 = new StrikethroughSpan();
                    if (spanStart != i6) {
                        editable.setSpan(strikethroughSpan2, spanStart, i6, 33);
                    }
                    if (spanEnd != i7) {
                        editable.setSpan(strikethroughSpan3, i7, spanEnd, 33);
                    }
                }
            }
        }
    }

    public void setTextUnderLine(Editable editable, boolean z6, int i6, int i7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        if (z6) {
            editable.setSpan(new UnderlineSpan(), i6, i7, 33);
            return;
        }
        for (UnderlineSpan underlineSpan : editable.getSpans(i6, i7, UnderlineSpan.class)) {
            if (underlineSpan != null) {
                int spanStart = editable.getSpanStart(underlineSpan);
                int spanEnd = editable.getSpanEnd(underlineSpan);
                if (i6 == spanStart && spanEnd == i7) {
                    editable.removeSpan(underlineSpan);
                } else if (spanStart <= i6 && spanEnd >= i7) {
                    editable.removeSpan(underlineSpan);
                    UnderlineSpan underlineSpan2 = new UnderlineSpan();
                    UnderlineSpan underlineSpan3 = new UnderlineSpan();
                    if (spanStart != i6) {
                        editable.setSpan(underlineSpan2, spanStart, i6, 33);
                    }
                    if (spanEnd != i7) {
                        editable.setSpan(underlineSpan3, i7, spanEnd, 33);
                    }
                }
            }
        }
    }

    private void showBubbleDialog() {
        final View view;
        View inflate = LayoutInflater.from(this.mainActivity).inflate(R.layout.dialog_bubble_view, null);
        final ImageView imageView = inflate.findViewById(R.id.item_popup_img0);
        final View findViewById = inflate.findViewById(R.id.item_dot_pupop_reminder_red);
        final View findViewById2 = inflate.findViewById(R.id.lock_red);
        View findViewById3 = inflate.findViewById(R.id.pdf_red);
        final TextView textView = inflate.findViewById(R.id.item_dot_pupop_pin_tv);
        textView.setText(this.mainActivity.getResources().getString(this.noteTmp.getPinState() == 0 ? R.string.menu_pin : R.string.menu_unpin));
        ((ImageView) inflate.findViewById(R.id.item_popup_img1)).setImageResource(this.isDark ? R.drawable.ic_archive_white : R.drawable.ic_archive_dark);
        final ImageView imageView2 = inflate.findViewById(R.id.item_popup_img2);
        imageView2.setImageResource(this.noteTmp.getPinState() == 0 ? this.pinViewResource : R.drawable.ic_flag_blue_solid);
        ImageView imageView3 = inflate.findViewById(R.id.item_popup_img_pdf);
        ((ImageView) inflate.findViewById(R.id.item_widget_image)).setImageResource(this.isDark ? R.drawable.ic_pop_widget2_white : R.drawable.ic_pop_widget2_dark);
        View findViewById4 = inflate.findViewById(R.id.item_dot_pupop_lock);
        ImageView imageView4 = inflate.findViewById(R.id.item_popup_img3);
        ImageView imageView5 = inflate.findViewById(R.id.item_popup_vip_pdf);
        ImageView imageView6 = inflate.findViewById(R.id.lock_vip_img);
        if (App.isVip()) {
            imageView5.setVisibility(View.GONE);
            imageView6.setVisibility(View.GONE);
        }
        imageView4.setImageResource(this.isDark ? R.drawable.ic_delete_white : R.drawable.ic_delete_dark);
        if (this.noteTmp.getCategory() != null && this.noteTmp.getCategory().getLocked() == 1) {
            findViewById4.setVisibility(View.GONE);
        }
        final TextView textView2 = inflate.findViewById(R.id.item_dot_pupop_lock_tv);
        try {
            ViewGroup.LayoutParams layoutParams = this.moreView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            int i6 = layoutParams.height;
            layoutParams2.topMargin = i6 - (i6 / 8);
            inflate.setLayoutParams(layoutParams2);
            inflate.setTop(this.moreView.getHeight());
        } catch (Exception unused) {
        }
        final ImageView imageView7 = inflate.findViewById(R.id.item_reading_image);
        final TextView textView3 = inflate.findViewById(R.id.item_reading_title);
        if (this.isEdit) {
            imageView7.setImageResource(this.isDark ? R.drawable.ic_reading_mode_white : R.drawable.ic_reading_mode);
            textView3.setText(R.string.reading_mode);
        } else {
            imageView7.setImageResource(this.isDark ? R.drawable.ic_edit_white : R.drawable.ic_edit);
            textView3.setText(R.string.editing_mode);
        }
        final BubbleDialog softShowUp = new BubbleDialog(getContext()).addContentView(inflate).setClickedView(this.moreView).softShowUp();
        if (this.noteTmp.isLocked().booleanValue()) {
            imageView.setImageResource(R.drawable.ic_lock_blue_menu);
            textView2.setText(R.string.lock_remove);
        } else {
            if (this.showLockRed) {
                findViewById2.setVisibility(View.VISIBLE);
            }
            imageView.setImageResource(this.isDark ? R.drawable.ic_unlock_blue2_white : R.drawable.ic_unlock_blue2);
            textView2.setText(R.string.lock);
        }
        ((ImageView) inflate.findViewById(R.id.item_popup_img_reminder)).setImageResource(this.isDark ? R.drawable.ic_alarm_pre_blue2_white : R.drawable.ic_alarm_pre_blue2);
        if (this.showRemindRed) {
            findViewById.setVisibility(View.VISIBLE);
        }
        inflate.findViewById(R.id.item_search).setOnClickListener(new View.OnClickListener() { // from class: b6.y1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$23(softShowUp, view2);
            }
        });
        final ImageView imageView8 = inflate.findViewById(R.id.item_fav_img1);
        final TextView textView4 = inflate.findViewById(R.id.item_fav_tv1);
        if (this.noteTmp.getFavorite() == 1) {
            textView4.setText(R.string.remove_from_fav);
            imageView8.setImageResource(R.drawable.ic_baseline_star_24);
        }
        inflate.findViewById(R.id.item_favorite).setOnClickListener(new View.OnClickListener() { // from class: b6.u2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$24(textView4, imageView8, view2);
            }
        });
        inflate.findViewById(R.id.item_dot_pupop_lock).setOnClickListener(new View.OnClickListener() { // from class: b6.f3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$25(findViewById2, imageView, textView2, softShowUp, view2);
            }
        });
        inflate.findViewById(R.id.item_reading_layout).setOnClickListener(new View.OnClickListener() { // from class: b6.q3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$26(imageView7, textView3, softShowUp, view2);
            }
        });
        ViewGroup viewGroup = inflate.findViewById(R.id.item_dot_pupop_layout1);
        if (this.noteTmp.isTrashed().booleanValue() || this.noteTmp.isArchived().booleanValue()) {
            viewGroup.setVisibility(View.GONE);
        }
        viewGroup.setOnClickListener(new View.OnClickListener() { // from class: b6.x3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$27(softShowUp, view2);
            }
        });
        inflate.findViewById(R.id.item_dot_pupop_pin).setOnClickListener(new View.OnClickListener() { // from class: b6.y3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$28(imageView2, textView, view2);
            }
        });
        ViewGroup viewGroup2 = inflate.findViewById(R.id.item_dot_pupop_layout3);
        if (this.noteTmp.isTrashed().booleanValue()) {
            viewGroup2.setVisibility(View.GONE);
        }
        viewGroup2.setOnClickListener(new View.OnClickListener() { // from class: b6.z3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$29(softShowUp, view2);
            }
        });
        if (imageView3 != null) {
            imageView3.setImageResource(this.isDark ? R.drawable.ic_pdf_white : R.drawable.ic_pdf_dark);
        }
        if (this.showPdfRed) {
            view = findViewById3;
            view.setVisibility(View.VISIBLE);
        } else {
            view = findViewById3;
        }
        inflate.findViewById(R.id.item_dot_pupop_pdf).setOnClickListener(new View.OnClickListener() { // from class: b6.a4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$30(view, softShowUp, view2);
            }
        });
        inflate.findViewById(R.id.item_dot_pupop_reminder).setOnClickListener(new View.OnClickListener() { // from class: b6.b4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$32(findViewById, softShowUp, view2);
            }
        });
        inflate.findViewById(R.id.item_widget_layout).setOnClickListener(new View.OnClickListener() { // from class: b6.z1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$33(view2);
            }
        });
        inflate.findViewById(R.id.item_add_tag).setOnClickListener(new View.OnClickListener() { // from class: b6.j2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DetailFragmentNew.this.lambda$showBubbleDialog$34(softShowUp, view2);
            }
        });
        FirebaseReportUtils.getInstance().reportNew("edit_more_dialog_show");
        try {
            softShowUp.show();
        } catch (Exception unused2) {
        }
    }

    private void showCateDialog() {
        ArrayList arrayList = new ArrayList();
        List<Category> categoryNoAllList = EasyNoteManager.getInstance().getCategoryNoAllList();
        Category makeDefaultCategory = new Category().makeDefaultCategory();
        makeDefaultCategory.setName(App.getAppContext().getResources().getString(R.string.uncategorized));
        Category makeAddCategory = new Category().makeAddCategory();
        makeAddCategory.setName(App.getAppContext().getResources().getString(R.string.add));
        categoryNoAllList.add(0, makeDefaultCategory);
        categoryNoAllList.add(0, makeAddCategory);
        for (int i6 = 0; i6 < categoryNoAllList.size(); i6++) {
            arrayList.add(categoryNoAllList.get(i6).getName());
        }
        new DetailPopupWindow(this.mainActivity, arrayList, (this.noteTmp.getCategory() == null || this.noteTmp.getCategory().getId() == Constants.DEFAULT_CATE_ID || this.noteTmp.getCategory().getName() == null) ? 1 : arrayList.indexOf(this.noteTmp.getCategory().getName()), this.topCategoryLayout, new DetailPopupWindow.DetailPopupWindowLinstener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.14
            final /* synthetic */ List val$categoryList;

            AnonymousClass14(List categoryNoAllList2) {
                r2 = categoryNoAllList2;
            }

            @Override // notes.easy.android.mynotes.view.DetailPopupWindow.DetailPopupWindowLinstener
            public void dismiss() {
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.topCategoryArrow.setImageResource(detailFragmentNew.categoryArrowDownResource);
            }

            @Override // notes.easy.android.mynotes.view.DetailPopupWindow.DetailPopupWindowLinstener
            public void selectTag(int i62) {
                if (i62 == 0) {
                    FirebaseReportUtils.getInstance().reportNew("edit_add_category");
                    DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                    dialogAddCategory.showAddCategoryDialog(detailFragmentNew.mainActivity, detailFragmentNew, true, detailFragmentNew.categoryName, false, detailFragmentNew);
                    return;
                }
                if (i62 == 1) {
                    DetailFragmentNew.this.noteTmp.setCategory(new Category().makeDefaultCategory());
                    DetailFragmentNew.this.categoryName.setText(R.string.uncategorized);
                    FirebaseReportUtils.getInstance().reportNew("edit_select_category_click", "key_select", Album.ALBUM_NAME_ALL);
                } else {
                    DetailFragmentNew.this.noteTmp.setCategory((Category) r2.get(i62));
                    DetailFragmentNew.this.categoryName.setText(((Category) r2.get(i62)).getName());
                    FirebaseReportUtils.getInstance().reportNew("edit_select_category_click", "key_select", ((Category) r2.get(i62)).getName());
                }
            }
        }).showMenuPopupwinodw();
    }

    private void showDialog() {
        String ccode = DeviceUtils.getCCODE(App.getAppContext());
        int size = DbHelper.getInstance().getNotesActive().size();
        long currentTimeMillis = System.currentTimeMillis();
        if (!DeviceUtilsKt.isReverseLanguage()) {
            ScreenUtils.isScreenOriatationLandscap(this.mainActivity);
        }
        if (!TextUtils.isEmpty(ccode) && !"kr".equals(ccode) && !"th".equals(ccode)) {
            "de".equals(ccode);
        }
        if (!App.isVip() && size >= 3 && !this.anyDialogShow && !this.userPreferences.getTimeDarkThemeShowed() && currentTimeMillis - this.userPreferences.getEditDialogShowTime() > 86400000) {
            this.anyDialogShow = true;
            this.isWidgetHideBoard = true;
            DialogAddCategory.INSTANCE.showDarkThemeDialog(this.mainActivity, this, 0);
            this.userPreferences.setTimeDarkThemeShowed(true);
            this.userPreferences.setTimeDarkThemeShowedTime(currentTimeMillis);
            this.userPreferences.setEditDialogShowTime(currentTimeMillis);
        }
        if (!App.isVip() && !this.anyDialogShow && this.userPreferences.getTimeDarkThemeShowed() && !this.userPreferences.getShowhowPaperBackground() && currentTimeMillis - this.userPreferences.getEditDialogShowTime() > 86400000 && currentTimeMillis - this.userPreferences.getTimeDarkThemeShowedTime() > Constants.FOUR_DAYS) {
            this.anyDialogShow = true;
            this.isWidgetHideBoard = true;
            this.userPreferences.setShowhowPaperBackground(true);
            this.userPreferences.setTimePaperThemeShowedTime(currentTimeMillis);
            DialogAddCategory.INSTANCE.showDarkThemeDialog(this.mainActivity, this, 1);
            this.userPreferences.setEditDialogShowTime(currentTimeMillis);
        }
        if (!App.isVip() && !this.anyDialogShow && this.userPreferences.getShowhowPaperBackground() && !this.userPreferences.getTimeSchollThemeShowed() && currentTimeMillis - this.userPreferences.getEditDialogShowTime() > 86400000 && currentTimeMillis - this.userPreferences.getTimePaperThemeShowedTime() > 172800000) {
            this.anyDialogShow = true;
            this.isWidgetHideBoard = true;
            this.userPreferences.setTimeSchollThemeShowed(true);
            this.userPreferences.setTimeSchoolThemeShowedTime(currentTimeMillis);
            DialogAddCategory.INSTANCE.showDarkThemeDialog(this.mainActivity, this, 2);
            this.userPreferences.setEditDialogShowTime(currentTimeMillis);
        }
        if (!App.isVip() && !this.anyDialogShow && !this.userPreferences.getTimeShopThemeShowed() && this.userPreferences.getTimeSchollThemeShowed() && currentTimeMillis - this.userPreferences.getEditDialogShowTime() > 86400000 && currentTimeMillis - this.userPreferences.getTimeSchoolThemeShowedTime() > 172800000) {
            this.anyDialogShow = true;
            this.isWidgetHideBoard = true;
            this.userPreferences.setTimeShopThemeShowed(true);
            this.userPreferences.setTimeShopThemeShowedTime(currentTimeMillis);
            DialogAddCategory.INSTANCE.showDarkThemeDialog(this.mainActivity, this, 3);
            this.userPreferences.setEditDialogShowTime(currentTimeMillis);
        }
        if (App.isVip() || this.anyDialogShow || !this.userPreferences.getTimeShopThemeShowed() || this.userPreferences.getTimeLandThemeShowed() || currentTimeMillis - this.userPreferences.getEditDialogShowTime() <= 86400000 || currentTimeMillis - this.userPreferences.getTimeShopThemeShowedTime() <= 172800000) {
            return;
        }
        this.anyDialogShow = true;
        this.isWidgetHideBoard = true;
        this.userPreferences.setTimeLandThemeShowed(true);
        DialogAddCategory.INSTANCE.showDarkThemeDialog(this.mainActivity, this, 4);
        this.userPreferences.setEditDialogShowTime(currentTimeMillis);
    }

    private void showFocusAndKeyBoard() {
        if (this.baseEntrys.size() == 0) {
            KeyboardUtils.showKeyboard(this.content);
            setSelection(this.content);
        } else {
            BaseEditView baseEditView = this.baseEntrys.get(r0.size() - 1);
            KeyboardUtils.showKeyboard(baseEditView.getmEditText());
            setSelection(baseEditView.getmEditText());
        }
    }

    private void showFontDialog() {
        AbsoluteSizeSpan[] absoluteSizeSpanArr;
        int size;
        int length;
        AbsoluteSizeSpan[] absoluteSizeSpanArr2;
        int length2;
        int i6 = 0;
        this.isSaved = false;
        View inflate = LayoutInflater.from(this.mainActivity).inflate(R.layout.font_action_layout, null);
        this.fontDialog = new CustomDialog.Builder(this.mainActivity).setView(inflate).setGravity(80).setCancelable(false).setStyle(CustomDialog.Style.STYLE_NO_PADDING).create();
        EditText editText = this.content;
        if (editText == null || !editText.hasFocus()) {
            int i7 = 0;
            while (true) {
                if (i7 >= this.baseEntrys.size()) {
                    break;
                }
                BaseEditView baseEditView = this.baseEntrys.get(i7);
                if (baseEditView == null || !baseEditView.getmEditText().hasFocus()) {
                    i7++;
                } else {
                    int selectionEnd = baseEditView.getmEditText().getSelectionEnd();
                    if (selectionEnd > 0) {
                        if (baseEditView.getmEditText().toString() != null && baseEditView.getmEditText().toString().trim() != null && baseEditView.getmEditText().toString().length() > selectionEnd && selectionEnd > (length = baseEditView.getmEditText().toString().substring(0, selectionEnd).trim().length())) {
                            selectionEnd = length;
                        }
                        if (selectionEnd > 1 && (absoluteSizeSpanArr = (AbsoluteSizeSpan[]) baseEditView.getmEditText().getEditableText().getSpans(selectionEnd - 1, selectionEnd, AbsoluteSizeSpan.class)) != null && absoluteSizeSpanArr.length > 0) {
                            size = absoluteSizeSpanArr[absoluteSizeSpanArr.length - 1].getSize();
                        }
                    }
                }
            }
            size = -1;
        } else {
            int selectionEnd2 = this.content.getSelectionEnd();
            if (selectionEnd2 > 0) {
                if (this.content.getEditableText().toString().trim() != null && selectionEnd2 > (length2 = this.content.getEditableText().toString().substring(0, selectionEnd2).trim().length())) {
                    selectionEnd2 = length2;
                }
                if (selectionEnd2 > 1 && (absoluteSizeSpanArr2 = this.content.getEditableText().getSpans(selectionEnd2 - 1, selectionEnd2, AbsoluteSizeSpan.class)) != null && absoluteSizeSpanArr2.length > 0) {
                    size = absoluteSizeSpanArr2[absoluteSizeSpanArr2.length - 1].getSize();
                }
            }
            size = -1;
        }
        View findViewById = inflate.findViewById(R.id.font_layout);
        if (findViewById != null && getActivity() != null) {
            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.height = (int) (ScreenUtils.getCurrentScreenDisplay(getActivity()).heightPixels * 0.46d);
            findViewById.setLayoutParams(layoutParams);
        }
        View findViewById2 = inflate.findViewById(R.id.font_content_layout);
        if (findViewById2 != null && getActivity() != null) {
            ViewGroup.LayoutParams layoutParams2 = findViewById2.getLayoutParams();
            layoutParams2.width = Math.min(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
            findViewById2.setLayoutParams(layoutParams2);
        }
        View findViewById3 = inflate.findViewById(R.id.alignment_start);
        final View findViewById4 = inflate.findViewById(R.id.alignment_start_img);
        View findViewById5 = inflate.findViewById(R.id.alignment_center);
        final View findViewById6 = inflate.findViewById(R.id.alignment_center_img);
        View findViewById7 = inflate.findViewById(R.id.alignment_end);
        final View findViewById8 = inflate.findViewById(R.id.alignment_end_img);
        View findViewById9 = inflate.findViewById(R.id.strikethrough);
        final View findViewById10 = inflate.findViewById(R.id.strikethrough_img);
        boolean z6 = this.isBold;
        int i8 = R.drawable.shape_theme_accent_10alpha_6dp;
        findViewById4.setBackgroundResource(z6 ? R.drawable.shape_theme_accent_10alpha_6dp : R.color.transparent);
        findViewById6.setBackgroundResource(this.isItalic ? R.drawable.shape_theme_accent_10alpha_6dp : R.color.transparent);
        findViewById8.setBackgroundResource(this.isUnderline ? R.drawable.shape_theme_accent_10alpha_6dp : R.color.transparent);
        if (!this.isStrikethrough) {
            i8 = R.color.transparent;
        }
        findViewById10.setBackgroundResource(i8);
        findViewById3.setOnClickListener(new View.OnClickListener() { // from class: b6.l2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$44(findViewById4, view);
            }
        });
        findViewById5.setOnClickListener(new View.OnClickListener() { // from class: b6.m2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$45(findViewById6, view);
            }
        });
        findViewById7.setOnClickListener(new View.OnClickListener() { // from class: b6.n2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$46(findViewById8, view);
            }
        });
        findViewById9.setOnClickListener(new View.OnClickListener() { // from class: b6.o2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$47(findViewById10, view);
            }
        });
        ImageView imageView = inflate.findViewById(R.id.font_smaller);
        ImageView imageView2 = inflate.findViewById(R.id.font_bigger);
        final TextView textView = inflate.findViewById(R.id.size_of_font);
        if (size != -1) {
            int indexOf = Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(this.userPreferences.getDefaultFloatSize()));
            int i9 = 0;
            while (true) {
                if (i9 >= DetailFragment.getFontAbsoluteSizeList().length) {
                    break;
                }
                if (size == DetailFragment.getFontAbsoluteSizeList(i9)) {
                    indexOf = i9;
                    break;
                }
                i9++;
            }
            textView.setText(Strings.valueOf(Constants.FONT_SIZE_LIST.get(indexOf)));
        } else {
            textView.setText(Strings.valueOf(Constants.FONT_SIZE_LIST.get(this.mCurrentFontIndex)));
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: b6.p2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$48(textView, view);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: b6.q2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$49(textView, view);
            }
        });
        RecyclerView recyclerView = inflate.findViewById(R.id.font_color_recycler);
        this.fontColorUnder = inflate.findViewById(R.id.font_color_underline);
        if (TextUtils.isEmpty(this.mCurrentFontColor)) {
            this.mCurrentFontColor = Constants.DEFAULT_TEXT_COLOR;
        }
        if (this.fontColorUnder != null) {
            String str = this.mCurrentFontColor;
            if (str == null || !str.startsWith(Constants.SPECIAL_CHARACTOR)) {
                try {
                    this.fontColorUnder.setBackgroundColor(Integer.parseInt(this.mCurrentFontColor));
                } catch (Exception unused) {
                }
            } else {
                this.fontColorUnder.setBackgroundColor(Color.parseColor(this.mCurrentFontColor));
            }
        }
        FontColorAdadpter fontColorAdadpter = new FontColorAdadpter(this.mainActivity, this.mCurrentFontColor, 1);
        fontColorAdadpter.setListsner(this);
        recyclerView.setAdapter(fontColorAdadpter);
        recyclerView.setLayoutManager(new GridLayoutManager(this.mainActivity, fontColorAdadpter.getItemCount()));
        RecyclerView recyclerView2 = inflate.findViewById(R.id.font_hightlight_recycler);
        View findViewById11 = inflate.findViewById(R.id.font_hightlight_underline);
        if (TextUtils.isEmpty(this.highLightColor)) {
            findViewById11.setBackgroundColor(Color.parseColor(HighLightAdapter.HIGHLIGHT_COLOR_SHOW[0]));
        } else {
            findViewById11.setBackgroundColor(Color.parseColor(this.highLightColor));
        }
        HighLightAdapter highLightAdapter = new HighLightAdapter();
        highLightAdapter.setSelectedColor(this.highLightColor);
        highLightAdapter.setOnListCallback(new HighLightAdapter.OnListCallback() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.41
            final /* synthetic */ View val$highlightBg;

            AnonymousClass41(View findViewById112) {
                r2 = findViewById112;
            }

            @Override // notes.easy.android.mynotes.models.adapters.HighLightAdapter.OnListCallback
            public void onHighLightColorClick(View view, String str2) {
                DetailFragmentNew.this.highLightColor = str2;
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.isBackOrSave = true;
                detailFragmentNew.setSaveIconColor();
                if (TextUtils.isEmpty(DetailFragmentNew.this.highLightColor)) {
                    r2.setBackgroundColor(Color.parseColor(HighLightAdapter.HIGHLIGHT_COLOR_SHOW[0]));
                } else {
                    r2.setBackgroundColor(Color.parseColor(DetailFragmentNew.this.highLightColor));
                }
                FirebaseReportUtils.getInstance().reportNew("edit_tool_font_highlight");
            }
        });
        recyclerView2.setAdapter(highLightAdapter);
        recyclerView2.setLayoutManager(new GridLayoutManager(this.mainActivity, highLightAdapter.getItemCount()));
        RecyclerView recyclerView3 = inflate.findViewById(R.id.font_recycler);
        if (ScreenUtils.isPad(this.mainActivity)) {
            recyclerView3.setLayoutManager(new GridLayoutManager(this.mainActivity, 4));
        } else {
            recyclerView3.setLayoutManager(new GridLayoutManager(this.mainActivity, 3));
        }
        List<NoteFontBean> noteFontList = ResNoteFontManager.getInstance().getNoteFontList();
        ArrayList arrayList = new ArrayList();
        if (this.isDark) {
            while (i6 < noteFontList.size()) {
                if (noteFontList.get(i6).isCoverDarkReady()) {
                    arrayList.add(noteFontList.get(i6));
                }
                i6++;
            }
        } else {
            while (i6 < noteFontList.size()) {
                if (noteFontList.get(i6).isCoverReady()) {
                    arrayList.add(noteFontList.get(i6));
                }
                i6++;
            }
        }
        NoteFontBean noteFontBean = new NoteFontBean();
        NoteFontAdapter noteFontAdapter = new NoteFontAdapter();
        noteFontAdapter.setList(arrayList);
        noteFontAdapter.setSelectFont(this.currentFontName);
        noteFontAdapter.setOnListCallbackListener(new NoteFontAdapter.OnListCallback() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.42
            final /* synthetic */ RecyclerView val$fontRecyclerView;
            final /* synthetic */ NoteFontBean val$lastNoteFontSelect;

            AnonymousClass42(NoteFontBean noteFontBean2, RecyclerView recyclerView32) {
                r2 = noteFontBean2;
                r3 = recyclerView32;
            }

            @Override // notes.easy.android.mynotes.ui.adapters.NoteFontAdapter.OnListCallback
            public void onClick(View view, NoteFontAdapter.NoteFontViewHolder noteFontViewHolder, NoteFontBean noteFontBean2, int i10) {
                if (DetailFragmentNew.this.getActivity() == null || DetailFragmentNew.this.getActivity().isFinishing()) {
                    return;
                }
                NoteFontBean copy = noteFontBean2.copy();
                r2.copy(copy);
                if (noteFontBean2.isFontReady()) {
                    DetailFragmentNew.this.currentFontBean = copy;
                    DetailFragmentNew.this.currentFontName = copy.getFontName();
                    DetailFragmentNew.this.noteTmp.setFontName(copy.getFontName());
                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                    detailFragmentNew.isBackOrSave = true;
                    detailFragmentNew.setFontTypeface(detailFragmentNew.currentFontName);
                    DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                    detailFragmentNew2.saveNote(detailFragmentNew2, true);
                    return;
                }
                if (!NetworkUtils.isNetworkConnected(App.getAppContext())) {
                    noteFontViewHolder.download.setVisibility(View.VISIBLE);
                    noteFontViewHolder.progress.setVisibility(View.GONE);
                    Toast.makeText(App.getAppContext(), R.string.no_network_error, 0).show();
                } else {
                    noteFontViewHolder.download.setVisibility(View.GONE);
                    noteFontViewHolder.progress.setVisibility(View.VISIBLE);
                    Toast.makeText(App.getAppContext(), R.string.downloading_toast, 0).show();
                    ResNoteFontManager.getInstance().startDownloadFont(noteFontBean2, new ResNoteFontManager.FontDownloadListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.42.1
                        final /* synthetic */ NoteFontBean val$fontBean;
                        final /* synthetic */ int val$position;

                        AnonymousClass1(int i102, NoteFontBean copy2) {
                            r2 = i102;
                            r3 = copy2;
                        }

                        @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                        public void onDownloadFailed(NoteFontBean noteFontBean3) {
                            if (r3 != null) {
                                Toast.makeText(App.getAppContext(), R.string.download_error_toast, 0).show();
                                RecyclerView.ViewHolder findViewHolderForItemId = r3.findViewHolderForItemId(r2);
                                if (findViewHolderForItemId == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                                    return;
                                }
                                NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                                noteFontViewHolder2.progress.setProgress(0);
                                noteFontViewHolder2.progress.setVisibility(View.GONE);
                                noteFontViewHolder2.download.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                        public void onDownloadSuccess(NoteFontBean noteFontBean3, String str2) {
                            RecyclerView.ViewHolder findViewHolderForItemId;
                            RecyclerView recyclerView4 = r3;
                            if (recyclerView4 != null && (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) != null && (findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                                NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                                noteFontViewHolder2.progress.setProgress(0);
                                noteFontViewHolder2.progress.setVisibility(View.GONE);
                                noteFontViewHolder2.download.setVisibility(View.GONE);
                            }
                            if (r2 == null || !TextUtils.equals(noteFontBean3.getFontName(), r2.getFontName())) {
                                return;
                            }
                            DetailFragmentNew.this.currentFontBean = r3;
                            DetailFragmentNew.this.currentFontName = r3.getFontName();
                            DetailFragmentNew.this.noteTmp.setFontName(r3.getFontName());
                            DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
                            detailFragmentNew3.isBackOrSave = true;
                            detailFragmentNew3.setFontTypeface(detailFragmentNew3.currentFontName);
                            DetailFragmentNew detailFragmentNew4 = DetailFragmentNew.this;
                            detailFragmentNew4.saveNote(detailFragmentNew4, true);
                        }

                        @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                        public void updateDownloadProgress(NoteFontBean noteFontBean3, long j6, float f6, float f7) {
                            RecyclerView.ViewHolder findViewHolderForItemId;
                            RecyclerView recyclerView4 = r3;
                            if (recyclerView4 == null || (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                                return;
                            }
                            NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                            noteFontViewHolder2.progress.setVisibility(View.VISIBLE);
                            noteFontViewHolder2.progress.setProgress(ResNoteFontManager.getInstance().mDownloadingProgress.get(noteFontBean3.getFontName()).intValue());
                        }

                        @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                        public void onDownloadStart(NoteFontBean noteFontBean3) {
                        }
                    });
                }
            }

            /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$42$1 */
            class AnonymousClass1 implements ResNoteFontManager.FontDownloadListener {
                final /* synthetic */ NoteFontBean val$fontBean;
                final /* synthetic */ int val$position;

                AnonymousClass1(int i102, NoteFontBean copy2) {
                    r2 = i102;
                    r3 = copy2;
                }

                @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                public void onDownloadFailed(NoteFontBean noteFontBean3) {
                    if (r3 != null) {
                        Toast.makeText(App.getAppContext(), R.string.download_error_toast, 0).show();
                        RecyclerView.ViewHolder findViewHolderForItemId = r3.findViewHolderForItemId(r2);
                        if (findViewHolderForItemId == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                            return;
                        }
                        NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                        noteFontViewHolder2.progress.setProgress(0);
                        noteFontViewHolder2.progress.setVisibility(View.GONE);
                        noteFontViewHolder2.download.setVisibility(View.VISIBLE);
                    }
                }

                @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                public void onDownloadSuccess(NoteFontBean noteFontBean3, String str2) {
                    RecyclerView.ViewHolder findViewHolderForItemId;
                    RecyclerView recyclerView4 = r3;
                    if (recyclerView4 != null && (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) != null && (findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                        NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                        noteFontViewHolder2.progress.setProgress(0);
                        noteFontViewHolder2.progress.setVisibility(View.GONE);
                        noteFontViewHolder2.download.setVisibility(View.GONE);
                    }
                    if (r2 == null || !TextUtils.equals(noteFontBean3.getFontName(), r2.getFontName())) {
                        return;
                    }
                    DetailFragmentNew.this.currentFontBean = r3;
                    DetailFragmentNew.this.currentFontName = r3.getFontName();
                    DetailFragmentNew.this.noteTmp.setFontName(r3.getFontName());
                    DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
                    detailFragmentNew3.isBackOrSave = true;
                    detailFragmentNew3.setFontTypeface(detailFragmentNew3.currentFontName);
                    DetailFragmentNew detailFragmentNew4 = DetailFragmentNew.this;
                    detailFragmentNew4.saveNote(detailFragmentNew4, true);
                }

                @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                public void updateDownloadProgress(NoteFontBean noteFontBean3, long j6, float f6, float f7) {
                    RecyclerView.ViewHolder findViewHolderForItemId;
                    RecyclerView recyclerView4 = r3;
                    if (recyclerView4 == null || (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                        return;
                    }
                    NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                    noteFontViewHolder2.progress.setVisibility(View.VISIBLE);
                    noteFontViewHolder2.progress.setProgress(ResNoteFontManager.getInstance().mDownloadingProgress.get(noteFontBean3.getFontName()).intValue());
                }

                @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                public void onDownloadStart(NoteFontBean noteFontBean3) {
                }
            }
        });
        recyclerView32.setAdapter(noteFontAdapter);
        View findViewById12 = inflate.findViewById(R.id.dialog_dismiss);
        View findViewById13 = inflate.findViewById(R.id.dialog_save);
        findViewById12.setOnClickListener(new View.OnClickListener() { // from class: b6.r2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$50(view);
            }
        });
        findViewById13.setOnClickListener(new View.OnClickListener() { // from class: b6.s2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DetailFragmentNew.this.lambda$showFontDialog$51(view);
            }
        });
        this.fontDialog.show();
        FirebaseReportUtils.getInstance().reportNew("edit_tool_font_show");
    }

    private void showInputMethodIfHasFocus() {
        EditText focusView = getFocusView();
        if (focusView != null) {
            KeyboardUtils.showKeyboardDontChange(focusView);
        }
    }

    public void showSavingView() {
        if (System.currentTimeMillis() - this.onCreateTime > ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
            this.mBottomBar.removeSaveRunnable(this.mHander);
            this.mBottomBar.showSavingTextView(this.mHander);
        }
    }

    private void startAmplitudeUpdates() {
        this.amplitudeTimer.cancel();
        Timer timer = new Timer();
        this.amplitudeTimer = timer;
        timer.scheduleAtFixedRate(getAmplitudeUpdateTask(), 0L, this.AMPLITUDE_UPDATE_MS);
    }

    private void startRealRecord() {
        this.isRecording = true;
        File createNewAttachmentFile = StorageHelper.createNewAttachmentFile(this.mainActivity, ConstantsBase.MIME_TYPE_AUDIO_EXT);
        if (createNewAttachmentFile == null) {
            return;
        }
        if (this.mRecorder == null) {
            MediaRecorder mediaRecorder = new MediaRecorder();
            this.mRecorder = mediaRecorder;
            mediaRecorder.setAudioSource(1);
            this.mRecorder.setOutputFormat(2);
            this.mRecorder.setAudioEncoder(3);
            this.mRecorder.setAudioEncodingBitRate(96000);
            this.mRecorder.setAudioSamplingRate(44100);
        }
        String absolutePath = createNewAttachmentFile.getAbsolutePath();
        this.recordName = absolutePath;
        this.mRecorder.setOutputFile(absolutePath);
        try {
            this.audioRecordingTimeStart = Calendar.getInstance().getTimeInMillis();
            this.mRecorder.prepare();
            this.mRecorder.start();
        } catch (Exception e7) {
            MyLog.e("prepare() failed", e7);
        }
    }

    private void startRecording() {
        if (DeviceUtils.verifyAudioPermissions(this, 1002)) {
            startRealRecord();
        }
    }

    private void stopRecording() {
        try {
            this.isRecording = false;
            MediaRecorder mediaRecorder = this.mRecorder;
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                this.audioRecordingTime = Calendar.getInstance().getTimeInMillis() - this.audioRecordingTimeStart;
                this.mRecorder.release();
                this.mRecorder = null;
            }
            this.durationTimer.cancel();
            this.amplitudeTimer.cancel();
            this.duration = 0;
        } catch (Exception unused) {
        }
    }

    private void takRecord() {
        Handler handler;
        Runnable runnable;
        if (StorageHelper.createNewAttachmentFile(getActivity(), ConstantsBase.MIME_TYPE_AUDIO_EXT) == null) {
            Toast.makeText(App.getAppContext(), R.string.error, 0).show();
            return;
        }
        Attachment attachment = new Attachment(Uri.fromFile(new File(this.recordName)), "audio/amr");
        attachment.setLength(this.audioRecordingTime);
        addAttachment(attachment);
        if (!this.beginEdit && (handler = this.mHander) != null && (runnable = this.autoSaveRunnable) != null) {
            this.beginEdit = true;
            handler.postDelayed(runnable, 10000L);
        }
        RecordGridAdapter recordGridAdapter = this.recordGridAdapter;
        if (recordGridAdapter != null) {
            recordGridAdapter.notifyDataSetChanged();
        }
        FirebaseReportUtils.getInstance().reportNew("record_in_notes_show");
        new Timer().schedule(new AnonymousClass25(), 100L);
    }

    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {
            File createNewAttachmentFile = StorageHelper.createNewAttachmentFile(this.mainActivity, ConstantsBase.MIME_TYPE_IMAGE_EXT);
            if (createNewAttachmentFile == null) {
                Toast.makeText(App.getAppContext(), R.string.error, 0).show();
                return;
            }
            this.attachmentUri = FileProviderHelper.getFileProvider(createNewAttachmentFile);
            intent.addFlags(1);
            intent.putExtra("output", this.attachmentUri);
            startActivityForResult(intent, 1);
        } catch (Exception unused) {
            Toast.makeText(App.getAppContext(), R.string.error, 0).show();
        }
    }

    private void takeSketch() {
        File createNewAttachmentFile = StorageHelper.createNewAttachmentFile(this.mainActivity, ConstantsBase.MIME_TYPE_SKETCH_EXT);
        if (createNewAttachmentFile == null) {
            Toast.makeText(App.getAppContext(), R.string.error, 0).show();
            return;
        }
        try {
            this.attachmentUri = Uri.fromFile(createNewAttachmentFile);
            this.mainActivity.animateTransition(this.mainActivity.getSupportFragmentManager().beginTransaction(), 1);
            this.mainActivity.setEditUri(null);
            SketchFragmentNew.baseUri = this.attachmentUri;
            if (!App.userConfig.getDrawUsed()) {
                App.userConfig.setDrawUsed(true);
                reportFunctionUse();
            }
            startActivityForResult(new Intent(this.mainActivity, DrawActivity.class).putExtra("promote", this.mainActivity.notifyType), 4);
        } catch (Exception unused) {
        }
    }

    private void toggleChecklist2() {
        this.isBackOrSave = true;
        if (ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg()) {
            this.exitView.setImageResource(R.drawable.ic_edit_save_white);
            this.addchecklistImg.setImageResource(R.drawable.ic_baseline_add_24);
            this.addchecklistTv.setTextColor(Color.parseColor("#8AFFFFFF"));
            this.exitView.postDelayed(new Runnable() { // from class: b6.v3
                @Override // java.lang.Runnable
                public void run() {
                    DetailFragmentNew.this.lambda$toggleChecklist2$35();
                }
            }, 150L);
        } else {
            this.addchecklistImg.setImageResource(R.drawable.ic_additem);
            this.exitView.setImageResource(this.checkImgResource);
            this.addchecklistTv.setTextColor(Color.parseColor("#52000000"));
            if (this.noteTmp.isChecklist().booleanValue()) {
                this.exitView.postDelayed(new Runnable() { // from class: b6.w3
                    @Override // java.lang.Runnable
                    public void run() {
                        DetailFragmentNew.this.lambda$toggleChecklist2$36();
                    }
                }, 150L);
            }
        }
        toggleChecklist2(true, true);
    }

    @SuppressLint({"NewApi"})
    public void trashNote(boolean z6) {
        if (this.noteTmp.get_id() == null) {
            goHome();
        }
        this.noteTmp.setTrashed(Boolean.valueOf(z6));
        this.goBack = true;
        if (z6) {
            ReminderHelper.removeReminder(App.getAppContext(), this.noteTmp);
            this.noteTmp.setCategory(new Category().makeDefaultCategory());
        } else {
            ReminderHelper.addReminder(App.getAppContext(), this.note);
        }
        saveNote(this, false);
    }

    private void updateRedrawingAttachment() {
        List<BaseEditView> list;
        int i6;
        if (DetailFragment.isReDrawing) {
            Attachment attachment = DetailFragment.reDrawingAttach;
            if (attachment != null) {
                int order = attachment.getOrder();
                if (order == 0) {
                    if (this.mGridView != null) {
                        this.mAttachmentAdapter.notifyDataSetChanged();
                        this.mGridView.setAdapter(this.mAttachmentAdapter);
                    }
                } else if (order >= 1 && (list = this.baseEntrys) != null && list.size() > (i6 = order - 1)) {
                    this.baseEntrys.get(i6).updateRedrawing();
                }
                this.timeModified = true;
            } else if (this.mGridView != null) {
                this.mAttachmentAdapter.notifyDataSetChanged();
                this.mGridView.setAdapter(this.mAttachmentAdapter);
            }
        }
        DetailFragment.isReDrawing = false;
        DetailFragment.reDrawingAttach = null;
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipQuitListener
    public void abandonFreeTry() {
        resetStickerColor();
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
    public void abandonVip() {
        if (RemoteConfig.getLong("edit_bg_free_try") != 1 || App.isVip() || this.freeTryDialogShow) {
            resetStickerColor();
        } else {
            if (this.userPreferences.getFreeTryShowTimes() == 0 || this.userPreferences.getFreeTryShowTimes() == 2) {
                this.freeTryDialogShow = true;
            } else {
                resetStickerColor();
            }
            UserConfig userConfig = this.userPreferences;
            userConfig.setFreeTryShowTimes(userConfig.getFreeTryShowTimes() + 1);
        }
        this.mBottomBar.setVisibility(View.VISIBLE);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.detail_content_card.getLayoutParams();
        layoutParams.bottomMargin = dpToPx(60);
        this.detail_content_card.setLayoutParams(layoutParams);
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void abortColor() {
        FirebaseReportUtils.getInstance().reportNew("edit_tool_color_cancel");
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.AudioListener
    public void addAudioAttachment() {
        if (DeviceUtils.verifyAudioPermissions(this, 1002)) {
            this.mBottomBar.showBottomRecordDialog();
        } else {
            Toast.makeText(App.getAppContext(), R.string.no_audio_permission, 0).show();
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void boldClick(boolean z6) {
        EditText editText = (EditText) this.mainActivity.getCurrentFocus();
        if (editText == null) {
            return;
        }
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        Editable editableText = editText.getEditableText();
        if (selectionEnd > selectionStart) {
            setTextBold(editText, editableText, z6, selectionStart, selectionEnd, true);
            this.isBackOrSave = true;
            this.fontStyleChange = true;
            setSaveIconColor();
        }
        setEditRich("");
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_bold");
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void bottomDrawImg() {
        takeSketch();
        this.editAction.append("_DR");
    }

    public void bottomEmoji() {
        bottomEmoji(0);
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void bottomFont() {
        KeyboardUtils.hideKeyboard(this.mBottomBar);
        showFontDialog();
        this.editAction.append("_A");
        if (!this.content.hasFocus()) {
            int i6 = 0;
            while (true) {
                if (i6 >= this.baseEntrys.size()) {
                    break;
                }
                BaseEditView baseEditView = this.baseEntrys.get(i6);
                if (baseEditView.getmEditText() != null && baseEditView.getmEditText().hasFocus()) {
                    this.currentFocus = baseEditView.getmEditText();
                    break;
                }
                i6++;
            }
        } else {
            this.currentFocus = this.content;
        }
        if (App.userConfig.getFontUsed()) {
            return;
        }
        App.userConfig.setFontUsed(true);
        reportFunctionUse();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f1, code lost:

        if (r5.equals("jp") == false) goto L122;
     */
    @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void bugVipNow(boolean r10) {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.bugVipNow(boolean):void");
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void chooseCustomePic() {
        if (App.isVip()) {
            EasyNoteManager.getInstance().requestMediaPermission(getActivity(), 104, new PermissionUtils.PermissionDirctListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.29
                AnonymousClass29() {
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
                public void onPermissionAlreadyGranted() {
                    DetailFragmentNew.this.startGetSingleContentAction();
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
                public void onRequestPermissionsResult(String[] strArr, int[] iArr) {
                    if (AndroidUpgradeUtils.isHaveVisualUserSelectedPerminssion(DetailFragmentNew.this.getActivity()) || (iArr.length > 0 && iArr[0] == 0)) {
                        DetailFragmentNew.this.startGetSingleContentAction();
                    }
                }
            });
        } else {
            DialogAddCategory.INSTANCE.showCustomPicDialog(getActivity(), new DialogAddCategory.vipConfiremListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.30
                AnonymousClass30() {
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
                public void bugVipNow(boolean z6) {
                    VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "custom_bg");
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
                public void abandonVip() {
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
                public void watchAdsNow() {
                }
            });
        }
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void choosePic() {
        this.editAction.append("_I");
        KeyboardUtils.hideKeyboard(this.mBottomBar);
        DialogAddCategory.INSTANCE.showBottomDialog(this.mainActivity, this);
        KeyboardUtils.hideKeyboard(this.mainActivity);
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void choosePicSource(int i6) {
        FirebaseReportUtils.getInstance().reportNew("edit_tool_add_pic");
        if (i6 == 1) {
            KeyboardUtils.hideKeyboard(this.mBottomBar);
            EasyNoteManager.getInstance().requestMediaPermission(getActivity(), 103, new PermissionUtils.PermissionDirctListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.28
                AnonymousClass28() {
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
                public void onPermissionAlreadyGranted() {
                    DetailFragmentNew.this.startGetContentAction();
                }

                @Override // notes.easy.android.mynotes.utils.permission.PermissionUtils.PermissionDirctListener
                public void onRequestPermissionsResult(String[] strArr, int[] iArr) {
                    if (AndroidUpgradeUtils.isHaveVisualUserSelectedPerminssion(DetailFragmentNew.this.getActivity()) || (iArr.length > 0 && iArr[0] == 0)) {
                        DetailFragmentNew.this.startGetContentAction();
                    }
                }
            });
        } else if (i6 == 0) {
            takePhoto();
        }
        if (App.userConfig.getEmojiUsed()) {
            return;
        }
        App.userConfig.setEmojiUsed(true);
        reportFunctionUse();
    }

    public void clickAddLock(Note note, ImageView imageView, TextView textView) {
        if (!TextUtils.isEmpty(this.userPreferences.getPwdCode()) || !TextUtils.isEmpty(this.userPreferences.getPatternPassword())) {
            bubbleLockState(note, imageView, textView);
            return;
        }
        int i6 = 1;
        if ((!TextUtils.isEmpty(this.userPreferences.getPwdCode()) || !TextUtils.isEmpty(this.userPreferences.getPatternPassword())) && note.isLocked().booleanValue()) {
            i6 = 4;
        }
        if (DeviceUtils.INSTANCE.isPixel6Brand()) {
            new DialogLockFragment(this.mainActivity, i6, new DialogLockFragment.OnUnlockStateInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.39
                final /* synthetic */ ImageView val$lockImg;
                final /* synthetic */ TextView val$lockTv;
                final /* synthetic */ Note val$note;

                AnonymousClass39(Note note2, ImageView imageView2, TextView textView2) {
                    r2 = note2;
                    r3 = imageView2;
                    r4 = textView2;
                }

                @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
                public void setPwdSucceed() {
                    DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
                }

                @Override // notes.easy.android.mynotes.ui.fragments.DialogLockFragment.OnUnlockStateInterface
                public void unlockSucceed(boolean z6) {
                    DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
                }
            }).show(getChildFragmentManager(), "322");
        } else {
            new DialogSetPwd(this.mainActivity, i6, new DialogSetPwd.OnUnlockStateInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.40
                final /* synthetic */ ImageView val$lockImg;
                final /* synthetic */ TextView val$lockTv;
                final /* synthetic */ Note val$note;

                AnonymousClass40(Note note2, ImageView imageView2, TextView textView2) {
                    r2 = note2;
                    r3 = imageView2;
                    r4 = textView2;
                }

                @Override // notes.easy.android.mynotes.view.setpw.DialogSetPwd.OnUnlockStateInterface
                public void setPwdSucceed() {
                    DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
                }

                @Override // notes.easy.android.mynotes.view.setpw.DialogSetPwd.OnUnlockStateInterface
                public void unlockSucceed(boolean z6) {
                    DetailFragmentNew.this.bubbleLockState(r2, r3, r4);
                }
            }).showSetPwdDialog();
        }
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void clickOkBtn() {
        showSavingView();
        FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_VIP_show_check");
        this.colorDialogConfirm = true;
        if (App.isVip() || App.is6hFreeTry() || this.isSaveBg) {
            if (this.mRecentNoteBg.getId() == 10) {
                this.noteTmp.setStickyType(this.mRecentNoteBg.isDarkBg() ? 11 : 10);
                this.noteTmp.setStickyColor(this.mRecentNoteBg.getCustom().getCustomBg());
            } else {
                this.noteTmp.setStickyType(ResNoteBgManager.getInstance().getStickyType(this.mRecentNoteBg.getId()));
                this.noteTmp.setStickyColor(ResNoteBgManager.getInstance().getStickyColor(this.mRecentNoteBg.getId()));
            }
            this.noteTmp.setBgId(this.mRecentNoteBg.getId());
        }
        setSaveIconColor();
        FirebaseReportUtils.getInstance().reportNew("edit_tool_color_check", "key_apply_color", "" + this.mRecentNoteBg.getId());
    }

    public void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        if (getActivity().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void colorSelected(NoteBgBean noteBgBean, int i6) {
        this.mLastNoteBg = noteBgBean;
        this.mLastNoteBgTabSelectIndex = i6;
        this.isSaveBg = true;
        setStickyColor(0, "", 0, noteBgBean);
        if (App.isVip() || !this.mLastNoteBg.isVip()) {
            this.bgChanged = true;
            NoteBgBean noteBgBean2 = this.mLastNoteBg;
            this.mRecentNoteBg = noteBgBean2;
            if (noteBgBean2.getId() == 10) {
                this.noteTmp.setStickyType(noteBgBean.isDarkBg() ? 11 : 10);
                this.noteTmp.setStickyColor(noteBgBean.getCustom().getCustomBg());
            } else {
                this.noteTmp.setStickyType(ResNoteBgManager.getInstance().getStickyType(this.mRecentNoteBg.getId()));
                this.noteTmp.setStickyColor(ResNoteBgManager.getInstance().getStickyColor(this.mRecentNoteBg.getId()));
            }
            this.noteTmp.setBgId(this.mRecentNoteBg.getId());
        } else {
            this.isSaveBg = false;
        }
        this.isBackOrSave = true;
        FirebaseReportUtils.getInstance().reportNew("edit_tool_color_click", "key_click_color", "" + this.mLastNoteBg.getId());
        this.contentAdapter.setDelRow(true);
        this.contentAdapter.notifyDataText();
        if (!App.userConfig.getBgUsed()) {
            App.userConfig.setBgUsed(true);
            reportFunctionUse();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("colorSelected:   edit_tool_color_click ");
        sb.append(this.mLastNoteBg.getId());
    }

    public StaticLayout createStaticLayout(CharSequence charSequence, int i6, int i7, TextPaint textPaint, int i8, float f6) {
        StaticLayout.Builder obtain;
        StaticLayout.Builder alignment;
        StaticLayout.Builder lineSpacing;
        StaticLayout.Builder includePad;
        StaticLayout.Builder maxLines;
        StaticLayout build;
        float f7 = f6 <= 0.0f ? 1.0f : f6;
        Layout.Alignment alignment2 = Layout.Alignment.ALIGN_NORMAL;
        int i9 = Build.VERSION.SDK_INT;
        if (i9 < 23) {
            return new StaticLayout(charSequence, textPaint, i8, alignment2, f7, 0.0f, false);
        }
        obtain = StaticLayout.Builder.obtain(charSequence, i6, i7, textPaint, i8);
        alignment = obtain.setAlignment(alignment2);
        lineSpacing = alignment.setLineSpacing(0.0f, f7);
        includePad = lineSpacing.setIncludePad(true);
        maxLines = includePad.setMaxLines(Integer.MAX_VALUE);
        if (i9 >= 28) {
            maxLines.setUseLineSpacingFromFallbacks(App.getAppContext().getApplicationInfo().targetSdkVersion >= 28);
        }
        build = maxLines.build();
        return build;
    }

    public List<EditContentBean> dataCompatible(String str, List<EditContentBean> list) {
        List<EditContentBean> editContentBeans;
        List<EditContentBean> list2 = list;
        for (String str2 : str.replace(",,", ",").split(",")) {
            if (!TextUtils.isEmpty(str2)) {
                if (str2.startsWith("f")) {
                    String[] position = getPosition("f", str2);
                    if (position != null) {
                        editContentBeans = setEditContentBeans(list2, "f", Integer.parseInt(position[0]), Integer.parseInt(position[1]), position[2]);
                        list2 = editContentBeans;
                    }
                } else if (str2.startsWith(Constants.SPAN_FONT_COLOR)) {
                    String[] position2 = getPosition(Constants.SPAN_FONT_COLOR, str2);
                    if (position2 != null) {
                        editContentBeans = setEditContentBeans(list2, Constants.SPAN_FONT_COLOR, Integer.parseInt(position2[0]), Integer.parseInt(position2[1]), position2[2]);
                        list2 = editContentBeans;
                    }
                } else if (str2.startsWith("h")) {
                    String[] position3 = getPosition("h", str2);
                    if (position3 != null) {
                        editContentBeans = setEditContentBeans(list2, "h", Integer.parseInt(position3[0]), Integer.parseInt(position3[1]), position3[2]);
                        list2 = editContentBeans;
                    }
                } else if (str2.startsWith(Constants.SPAN_U)) {
                    String[] position4 = getPosition(Constants.SPAN_U, str2);
                    if (position4 != null) {
                        editContentBeans = setEditContentBeans(list2, Constants.SPAN_U, Integer.parseInt(position4[0]), Integer.parseInt(position4[1]), "");
                        list2 = editContentBeans;
                    }
                } else if (str2.startsWith(Constants.SPAN_STRIKETHROUGH)) {
                    String[] position5 = getPosition(Constants.SPAN_STRIKETHROUGH, str2);
                    if (position5 != null) {
                        editContentBeans = setEditContentBeans(list2, Constants.SPAN_STRIKETHROUGH, Integer.parseInt(position5[0]), Integer.parseInt(position5[1]), "");
                        list2 = editContentBeans;
                    }
                } else if (str2.startsWith(Constants.SPAN_S)) {
                    String[] position6 = getPosition(Constants.SPAN_S, str2);
                    if (position6 != null) {
                        editContentBeans = setEditContentBeans(list2, Constants.SPAN_S, Integer.parseInt(position6[0]), Integer.parseInt(position6[1]), position6[2]);
                        list2 = editContentBeans;
                    }
                } else if (str2.startsWith(Constants.SPAN_R)) {
                    String[] position7 = getPosition(Constants.SPAN_R, str2);
                    if (position7 != null) {
                        editContentBeans = setEditContentBeans(list2, Constants.SPAN_R, Integer.parseInt(position7[0]), Integer.parseInt(position7[1]), position7[2]);
                        list2 = editContentBeans;
                    }
                } else {
                    String substring = str2.substring(1);
                    if (!TextUtils.isEmpty(substring)) {
                        int parseInt = Integer.parseInt(substring);
                        if (str2.startsWith(Constants.SPAN_BULLET_D)) {
                            for (int i6 = 0; i6 < list2.size(); i6++) {
                                if (parseInt <= i6) {
                                    list2.get(i6).setViewType(2);
                                }
                            }
                        } else if (str2.startsWith(Constants.SPAN_BULLET_Z)) {
                            for (int i7 = 0; i7 < list2.size(); i7++) {
                                if (parseInt <= i7) {
                                    list2.get(i7).setViewType(3);
                                }
                            }
                        } else if (str2.startsWith(Constants.SPAN_BULLET_CHECK_BOX)) {
                            for (int i8 = 0; i8 < list2.size(); i8++) {
                                if (parseInt <= i8) {
                                    list2.get(i8).setViewType(1);
                                }
                            }
                        } else if (str2.startsWith(Constants.SPAN_BULLET_CHECK_BOX_N)) {
                            for (int i9 = 0; i9 < list2.size(); i9++) {
                                if (parseInt <= i9) {
                                    list2.get(i9).setViewType(1);
                                }
                            }
                        }
                    }
                }
            }
        }
        return list2;
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.PhotoActionListener
    public void deleteAttachment(Attachment attachment) {
        removeAttachment(attachment);
    }

    int dpToPx(int i6) {
        return Math.round(getResources().getDisplayMetrics().density * i6);
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.CateFinishListener
    public void finishExit(String str) {
        doExit();
    }

    @Override // notes.easy.android.mynotes.models.adapters.FontColorAdadpter.FontColorListener
    public void fontColorDialogDismiss() {
        CustomDialog customDialog;
        if (this.currentFocus == null || (customDialog = this.fontDialog) == null || customDialog.isAdded()) {
            return;
        }
        KeyboardUtils.showKeyboardDontChange(this.currentFocus);
        this.currentFocus = null;
    }

    @Override // notes.easy.android.mynotes.models.adapters.FontColorAdadpter.FontColorListener
    public void fontColorPickerShow(int i6) {
        DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
        dialogAddCategory.disMissFontColorDialog();
        dialogAddCategory.showFontColorPickerDialog(this.mainActivity, this, this.mCurrentFontColor, i6);
    }

    public boolean fontStyleIsChange() {
        return this.mCurrentFontIndex != Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(this.userPreferences.getDefaultFloatSize())) || this.isBold || this.isItalic || this.isUnderline || this.isStrikethrough || !Constants.DEFAULT_TEXT_COLOR.equalsIgnoreCase(this.mCurrentFontColor) || !TextUtils.isEmpty(this.highLightColor);
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipQuitListener
    public void freeTryNow() {
        resetStickerColor();
    }

    public int getCurrentCursorLine(EditText editText) {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();
        if (-1 != selectionStart) {
            return layout.getLineForOffset(selectionStart);
        }
        return -1;
    }

    public Note getCurrentNote() {
        return this.note;
    }

    @Override // notes.easy.android.mynotes.ui.fragments.BaseNewFragment, androidx.fragment.app.Fragment, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public /* bridge */ /* synthetic */ CreationExtras getDefaultViewModelCreationExtras() {
        return androidx.lifecycle.b.a(this);
    }

    public java.lang.String[] getPosition(java.lang.String r8, java.lang.String r9) {
        throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.getPosition(java.lang.String, java.lang.String):java.lang.String[]");
    }

    @Override // notes.easy.android.mynotes.ui.fragments.BaseNewFragment
    public int getResID() {
        return R.layout.fragment_detail_new;
    }

    @Override // notes.easy.android.mynotes.ui.fragments.BaseNewFragment
    public void initView(View view) {
        findView(view);
        this.mBottomBar.setInterface(this);
        this.mBottomBar.setHighLightListener(this);
        this.mBottomBar.setFontColorListener(this);
    }

    public boolean isSearchState() {
        return this.searchEdit.hasFocus() || this.searchEdit.length() > 0;
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void italicClick(boolean z6) {
        EditText editText = (EditText) this.mainActivity.getCurrentFocus();
        if (editText == null) {
            return;
        }
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        Editable editableText = editText.getEditableText();
        if (selectionEnd > selectionStart) {
            setTextItalic(editText, editableText, z6, selectionStart, selectionEnd, true);
            this.isBackOrSave = true;
            this.fontStyleChange = true;
            setSaveIconColor();
        }
        setEditRich("");
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_italic");
    }

    public void method_1(List<EditContentUndoRedoBean> list) {
        ArrayList arrayList = new ArrayList();
        for (int i6 = 0; i6 < list.size(); i6++) {
            boolean z6 = true;
            for (int i7 = 0; i7 < arrayList.size(); i7++) {
                if (list.get(i6).getContentJson().equals(((EditContentUndoRedoBean) arrayList.get(i7)).getContentJson())) {
                    ((EditContentUndoRedoBean) arrayList.get(i7)).setCursorBeforeAfter(list.get(i6).getCursorBeforeAfter());
                    ((EditContentUndoRedoBean) arrayList.get(i7)).setSelectedEditText(list.get(i6).getSelectedEditText());
                    z6 = false;
                }
            }
            if (z6) {
                arrayList.add(list.get(i6));
            }
        }
        list.clear();
        list.addAll(arrayList);
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void newCateAdded(Category category) {
        this.noteTmp.setCategory(category);
        this.userPreferences.setHasCreateNewTag(true);
        FirebaseReportUtils.getInstance().reportNew("category_add_OK", "key_category", category.getName());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        int i6;
        super.onActivityCreated(bundle);
        this.timesSwitch = RemoteConfig.getLong("ad_create_notes_interval");
        this.autoSaveType = RemoteConfig.getRemoteLong("remote_edit_auto_save_time");
        if (this.timesSwitch == 0) {
            this.timesSwitch = 2L;
        }
        this.isNewCreate = true;
        this.bgChanged = false;
        EditActivity editActivity = (EditActivity) getActivity();
        this.mainActivity = editActivity;
        this.prefs = editActivity.prefs;
        if (bundle != null) {
            try {
                this.noteTmp = bundle.getParcelable("noteTmp");
                this.note = bundle.getParcelable(ConstantsBase.INTENT_NOTE);
                this.noteOriginal = bundle.getParcelable("noteOriginal");
                this.attachmentUri = bundle.getParcelable("attachmentUri");
            } catch (Exception unused) {
            }
        }
        this.bulletContorller = new BulletContorller();
        if (getArguments() == null || this.mainActivity.getCreateState()) {
            i6 = -1;
        } else {
            i6 = getArguments().getInt("note_type");
            this.SearchText = getArguments().getString("searchText");
            if (i6 == 1 || i6 == 2) {
                this.isDrawOrCheck = true;
            }
        }
        init();
        if (!this.isNewCreate && this.userPreferences.getDefaultReading()) {
            this.isEdit = true;
            this.nestedScrollView.setIntercept(false);
            refreshRead();
        }
        setHasOptionsMenu(true);
        setRetainInstance(false);
        if (getArguments() != null && !this.mainActivity.getCreateState()) {
            if (i6 == 1) {
                useCheckList();
            } else if (i6 == 2) {
                this.mBottomBar.postDelayed(new Runnable() { // from class: b6.f2
                    @Override // java.lang.Runnable
                    public void run() {
                        DetailFragmentNew.this.lambda$onActivityCreated$2();
                    }
                }, 200L);
            } else if (i6 == 3) {
                choosePic();
                KeyboardUtils.hideKeyboard(this.mBottomBar);
            } else if (i6 == 4) {
                recordPermission();
            } else if (i6 == 6) {
                this.dialogType = -1;
                showColorDialog(0);
                KeyboardUtils.hideKeyboard(this.content);
            }
            this.mainActivity.setCreateState(true);
        }
        if (this.userPreferences.getStarRecordViewTimes()) {
            UserConfig userConfig = this.userPreferences;
            userConfig.setEditViewTimes(userConfig.getEditViewTimes() + 1);
        }
        if (this.noteTmp.isChecklist().booleanValue()) {
            if (this.baseEntrys.size() == 0) {
                BaseEditView baseEditView = new BaseEditView(this.mainActivity);
                setNewTextViewColor(baseEditView.getmEditText());
                baseEditView.setClickListener(this);
                baseEditView.setOrder(1);
                baseEditView.getmEditText().setEditTextMenuListener(this);
                this.baseEntrys.add(baseEditView);
                this.contentLayout.addView(baseEditView.getBaseView(), this.contentLayout.getChildCount() - 1);
            }
            if (this.baseEntrys.size() > 1) {
                List<BaseEditView> list = this.baseEntrys;
                BaseEditView baseEditView2 = list.get(list.size() - 1);
                if (baseEditView2.getmEditText().getEditableText().toString().contains("[ ]") || baseEditView2.getmEditText().getEditableText().toString().contains("[x]")) {
                    ((EditText) this.toggleChecklistView2).setText(baseEditView2.getContent());
                    this.contentLayout.removeView(baseEditView2.getBaseView());
                    this.baseEntrys.remove(baseEditView2);
                    addNewChecklistView(true);
                }
            }
        } else {
            EditText editText = this.content;
            parseCustomCode(editText, editText.getText().toString());
            if (saveSkech) {
                this.content.postDelayed(new Runnable() { // from class: b6.g2
                    @Override // java.lang.Runnable
                    public void run() {
                        DetailFragmentNew.this.lambda$onActivityCreated$3();
                    }
                }, 800L);
            } else if (!TextUtils.isEmpty(this.defaultContentStyle)) {
                EditText editText2 = this.content;
                applyCharacterStyle(editText2, editText2.getEditableText(), this.defaultContentStyle, true);
            }
        }
        this.add_Layout.postDelayed(new Runnable() { // from class: b6.h2
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$onActivityCreated$4();
            }
        }, 1000L);
    }

    @Override // androidx.fragment.app.Fragment
    @SuppressLint({"NewApi"})
    public void onActivityResult(int i6, int i7, Intent intent) {
        List<Uri> obtainResult;
        List<Uri> obtainResult2;
        if (i7 == -1) {
            showSavingView();
            if (i6 == 1) {
                Uri uri = this.attachmentUri;
                new AttachmentTask(this, uri, FileHelper.getNameFromUri(this.mainActivity, uri), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                this.isBackOrSave = true;
                FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_camera_OK");
                FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_insert_OK");
            }
            if (i6 == 3) {
                this.noteTmp.setPasswordChecked(true);
                lockUnlock();
                return;
            }
            if (i6 == 4) {
                Uri uri2 = intent.getParcelableExtra(ConstantsBase.EDIT_DRAWS);
                if (uri2 != null) {
                    insertNewBaseEntry(new Attachment(uri2, ConstantsBase.MIME_TYPE_SKETCH));
                    return;
                }
                return;
            }
            if (i6 == 5) {
                Toast.makeText(App.getAppContext(), R.string.category_saved, 0).show();
                this.noteTmp.setCategory((Category) intent.getParcelableExtra(ConstantsBase.INTENT_CATEGORY));
                return;
            }
            if (i6 == 6) {
                Toast.makeText(App.getAppContext(), R.string.note_updated, 0).show();
                return;
            }
            if (i6 == 7) {
                Uri uri3 = intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
                if (uri3 != null) {
                    nowUri = uri3;
                    Intent intent2 = new Intent();
                    intent2.setAction(Constants.ALARM_RINGTON_CHOOSE_ACTION);
                    LocalBroadcastManager.getInstance(this.mainActivity).sendBroadcast(intent2);
                    return;
                }
                return;
            }
            switch (i6) {
                case 101:
                    ArrayList<Uri> arrayList = new ArrayList();
                    if (intent != null && (obtainResult = Matisse.obtainResult(intent)) != null && obtainResult.size() > 0) {
                        arrayList.addAll(obtainResult);
                    }
                    for (Uri uri4 : arrayList) {
                        new AttachmentTask(this, uri4, FileHelper.getNameFromUri(this.mainActivity, uri4), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                    }
                    FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_gallery_OK");
                    FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_insert_OK");
                    break;
                case 102:
                    ArrayList arrayList2 = new ArrayList();
                    if (intent != null && (obtainResult2 = Matisse.obtainResult(intent)) != null && obtainResult2.size() > 0) {
                        arrayList2.addAll(obtainResult2);
                    }
                    if (arrayList2.size() > 0) {
                        Intent intent3 = new Intent(this.mainActivity, EditCustomBgActivity.class);
                        intent3.putExtra("IMAGE_URI", (Parcelable) arrayList2.get(0));
                        intent3.putExtra("custom_bg", true);
                        startActivityForResult(intent3, 103);
                        break;
                    }
                    break;
                case 103:
                    if (intent != null && Objects.equals(intent.getStringExtra(ConstantsBase.EDIT_IMAGES_FROM), "PhotoEditActivity")) {
                        Uri uri5 = intent.getParcelableExtra(ConstantsBase.EDIT_IMAGES);
                        final boolean booleanExtra = intent.getBooleanExtra(ConstantsBase.EDIT_IMAGES_DARK, false);
                        if (uri5 != null) {
                            final File file = new File(uri5.getPath());
                            if (booleanExtra) {
                                this.noteTmp.setStickyType(11);
                            } else {
                                this.noteTmp.setStickyType(10);
                            }
                            this.noteTmp.setStickyColor(file.getName());
                            this.noteTmp.setBgId(10);
                            setStickyColor(this.noteTmp.getStickyType(), this.noteTmp.getStickyColor(), this.noteTmp.getBgId(), null);
                            NoteBgDialogFragment noteBgDialogFragment = this.mNoteBgDialogFragment;
                            if (noteBgDialogFragment != null) {
                                noteBgDialogFragment.dismiss();
                            }
                            App.executeOnGlobalExecutor(new Runnable() { // from class: b6.c2
                                @Override // java.lang.Runnable
                                public void run() {
                                    DetailFragmentNew.lambda$onActivityResult$39(booleanExtra, file);
                                }
                            });
                            break;
                        }
                    }
                    break;
                default:
                    MyLog.e("Wrong element choosen: " + i6);
                    break;
            }
        }
    }

    @Override // notes.easy.android.mynotes.ui.fragments.BaseNewFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // notes.easy.android.mynotes.models.listeners.OnAttachingFileListener
    public void onAttachingFileErrorOccurred(Attachment attachment) {
        if (this.noteTmp.getAttachmentsList().contains(attachment)) {
            removeAttachment(attachment);
            this.mAttachmentAdapter.notifyDataSetChanged();
        }
    }

    @Override // notes.easy.android.mynotes.models.listeners.OnAttachingFileListener
    public void onAttachingFileFinished(Attachment attachment) {
        this.isBackOrSave = true;
        insertNewBaseEntry(attachment);
    }

    @Override // notes.easy.android.mynotes.edit.view.BaseEditView.PicClicklistener
    public void onBaseViewDragFinish(List<Attachment> list, int i6, int i7, List<Attachment> list2) {
        int i8 = 0;
        while (i8 < list.size()) {
            Attachment attachment = list.get(i8);
            i8++;
            attachment.setSort(i8);
        }
        for (int i9 = 0; i9 < list2.size(); i9++) {
            this.noteTmp.removeAttachment(list2.get(i9));
        }
        for (int i10 = 0; i10 < list.size(); i10++) {
            this.noteTmp.addAttachment(list.get(i10));
        }
        this.timeModified = true;
        saveNote(new a2(this), false);
    }

    public void onCanDo(boolean z6, boolean z7) {
        this.content_undo.setEnabled(true);
        this.content_redo.setEnabled(true);
    }

    @Override // com.myview.android.checklistview.interfaces.CheckListChangedListener
    public void onCheckListChanged() {
        if (this.noteTmp.isChecklist().booleanValue() && this.checkSize == 1 && this.mChecklistManager.mCheckListView.getItemSize() == 0) {
            useCheckList();
        }
        scrollContent();
    }

    @Override // com.myview.android.checklistview.interfaces.CheckListChangedListener
    public void onCheckListItemFocusChange(boolean z6) {
        clipboard(z6);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.content_redo /* 2131362240 */:
                doTextRedo();
                FirebaseReportUtils.getInstance().reportNew("edit_redo");
                break;
            case R.id.content_undo /* 2131362241 */:
                doTextUndo();
                FirebaseReportUtils.getInstance().reportNew("edit_undo");
                break;
            case R.id.exit_edit /* 2131362474 */:
                if (this.newCreate) {
                    if (this.isBackOrSave) {
                        FirebaseReportUtils.getInstance().reportNew("M_create_success_manual");
                        reportSaveNoteEvent("M_create_success");
                        FirebaseReportUtils.getInstance().reportNew("M_create_OK");
                        if (!App.userConfig.getFirstEditOk()) {
                            App.userConfig.setFirstEditOk(true);
                            FirebaseReportUtils.getInstance().logMainFlow(getActivity(), "v1_f_main_create_ok");
                        }
                    } else {
                        String stringBuffer = this.editAction.toString();
                        Bundle bundle = new Bundle();
                        Bundle bundle2 = new Bundle();
                        bundle.putString("key_back", stringBuffer);
                        bundle2.putString("key_back_newuser", stringBuffer);
                        FirebaseReportUtils.getInstance().reportOnlyNew("M_create_empty_back", bundle2);
                        FirebaseReportUtils.getInstance().reportAll("M_create_empty_back", bundle);
                        FirebaseReportUtils.getInstance().reportNew("M_create_empty_back_manual");
                    }
                }
                if (!Util.meetRate(this.prefs, getNoteContent(), this.mainActivity.contentSize, this.noteTmp)) {
                    if (DbHelper.getInstance().getNotesActive().size() >= 1 && !App.userConfig.getWidgetDialogShow() && DeviceUtils.isPinWidgetSupport(App.getAppContext()) && !this.hasSedLocked && !this.userPreferences.getWidgetClick()) {
                        this.mainActivity.isShowWidgetDialog = true;
                        doExit();
                        this.mainActivity.showWidgetDialog(this.noteTmp);
                        break;
                    } else if (!this.noteTmp.isChecklist().booleanValue()) {
                        if (this.newCreate && DbHelper.getInstance().getNotesActive().size() > 10 && !this.userPreferences.getHasCreateNewTag() && !this.userPreferences.getHasCreateShowed()) {
                            FirebaseReportUtils.getInstance().reportNew("time_promote_edit_cate_show");
                            DialogAddCategory.INSTANCE.showCateProDialog(this.mainActivity, R.string.new_tag_title, R.string.cancel, R.string.creation, new DialogCancelInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.35
                                AnonymousClass35() {
                                }

                                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                                public void confirmDelete() {
                                    FirebaseReportUtils.getInstance().reportNew("time_promote_edit_cate_click");
                                    DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
                                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                                    dialogAddCategory.showAddCategoryDialog(detailFragmentNew.mainActivity, detailFragmentNew, true, detailFragmentNew.categoryName, true, detailFragmentNew);
                                }

                                @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                                public void doNothing() {
                                    DetailFragmentNew.this.doExit();
                                }
                            });
                            this.newCreate = false;
                            break;
                        } else if (!App.isVip() && DbHelper.getInstance().getNotesActive().size() > 2 && System.currentTimeMillis() - this.userPreferences.getFirstTime() > 86400000 && DbHelper.getInstance().getNotesWithLock(true).size() == 0 && !this.userPreferences.getLockGuideShowed()) {
                            DialogAddCategory.INSTANCE.showLockingNoteDialog(this.mainActivity, true, false, new DialogAddCategory.OnLockingInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.36
                                AnonymousClass36() {
                                }

                                @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                                public void clickUpgradeVip() {
                                    VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "lock_note");
                                }

                                @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                                public void clickTryItOnce() {
                                }
                            });
                            this.userPreferences.setLockGuideShowed(true);
                            break;
                        } else {
                            doExit();
                            break;
                        }
                    } else if (this.newCreate && !this.userPreferences.getNeverShowReminder() && this.noteTmp.getAlarm() == null && !this.userPreferences.getReminderClick()) {
                        FirebaseReportUtils.getInstance().reportNew("time_promote_edit_reminder_show");
                        DialogAddCategory.INSTANCE.showReminderDialog(this.mainActivity, new DialogCancelInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.33

                            /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$33$1 */
                            class AnonymousClass1 implements DialogAddCategory.TimerChangedListener {
                                AnonymousClass1() {
                                }

                                @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                                public void doRingTonChoose() {
                                    Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                                    intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
                                    intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
                                    DetailFragmentNew.this.startActivityForResult(intent, 7);
                                }

                                @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                                public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
                                    if (DetailFragmentNew.nowUri != null) {
                                        DetailFragmentNew.this.userPreferences.setRingTonNow(DetailFragmentNew.nowUri.toString());
                                    }
                                    Note note2 = DetailFragmentNew.this.noteTmp;
                                    note2.setRecurrenceRule(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, note2.getRecurrenceRule()));
                                    DetailFragmentNew.this.noteTmp.setAlarm(j6);
                                    DetailFragmentNew.this.alarmChanged = true;
                                    FirebaseReportUtils.getInstance().reportNew("edit_reminder_OK");
                                    if (DetailFragmentNew.this.noteTmp.getAlarm() != null) {
                                        Toast.makeText(App.getAppContext(), R.string.set_alarm_success, 0).show();
                                        DetailFragmentNew.this.doExit();
                                    }
                                }
                            }

                            AnonymousClass33() {
                            }

                            @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                            public void confirmDelete() {
                                FirebaseReportUtils.getInstance().reportNew("time_promote_edit_reminder_click");
                                DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
                                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                                Note note = detailFragmentNew.noteTmp;
                                dialogAddCategory.showEditTimeDialog(true, note, detailFragmentNew.mainActivity, note.getRecurrenceRule(), R.string.add_reminder, new DialogAddCategory.TimerChangedListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.33.1
                                    AnonymousClass1() {
                                    }

                                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                                    public void doRingTonChoose() {
                                        Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                                        intent.putExtra("android.intent.extra.ringtone.TYPE", 4);
                                        intent.putExtra("android.intent.extra.ringtone.TITLE", App.getAppContext().getResources().getString(R.string.settings_notification_ringtone));
                                        DetailFragmentNew.this.startActivityForResult(intent, 7);
                                    }

                                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.TimerChangedListener
                                    public void updateNewTime(long j6, SublimeRecurrencePicker.RecurrenceOption recurrenceOption) {
                                        if (DetailFragmentNew.nowUri != null) {
                                            DetailFragmentNew.this.userPreferences.setRingTonNow(DetailFragmentNew.nowUri.toString());
                                        }
                                        Note note2 = DetailFragmentNew.this.noteTmp;
                                        note2.setRecurrenceRule(RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, note2.getRecurrenceRule()));
                                        DetailFragmentNew.this.noteTmp.setAlarm(j6);
                                        DetailFragmentNew.this.alarmChanged = true;
                                        FirebaseReportUtils.getInstance().reportNew("edit_reminder_OK");
                                        if (DetailFragmentNew.this.noteTmp.getAlarm() != null) {
                                            Toast.makeText(App.getAppContext(), R.string.set_alarm_success, 0).show();
                                            DetailFragmentNew.this.doExit();
                                        }
                                    }
                                });
                                DetailFragmentNew.this.newCreate = false;
                            }

                            @Override // notes.easy.android.mynotes.view.DialogCancelInterface
                            public void doNothing() {
                                DetailFragmentNew.this.doExit();
                            }
                        });
                        break;
                    } else if (!App.isVip() && DbHelper.getInstance().getNotesActive().size() > 2 && System.currentTimeMillis() - this.userPreferences.getFirstTime() > 86400000 && DbHelper.getInstance().getNotesWithLock(true).size() == 0 && !this.userPreferences.getLockGuideShowed() && !this.userPreferences.getLockClick()) {
                        FirebaseReportUtils.getInstance().reportNew("lock_promote_show");
                        DialogAddCategory.INSTANCE.showLockingNoteDialog(this.mainActivity, true, false, new DialogAddCategory.OnLockingInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.34
                            AnonymousClass34() {
                            }

                            @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                            public void clickUpgradeVip() {
                                FirebaseReportUtils.getInstance().reportNew("lock_promote_click");
                                VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "lock_note");
                            }

                            @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
                            public void clickTryItOnce() {
                            }
                        });
                        this.userPreferences.setLockGuideShowed(true);
                        break;
                    } else {
                        doExit();
                        break;
                    }
                } else {
                    doExit();
                    break;
                }
                break;
            case R.id.more_action /* 2131363242 */:
                if (KeyboardUtils.isKeyboardShowed(this.mainActivity) && (this.mainActivity.getWindow().getDecorView().findFocus() instanceof EditText)) {
                    KeyboardUtils.hideKeyboard(this.mainActivity.getWindow().getDecorView().findFocus());
                }
                if (this.userPreferences.getTimeEditLockRed() == 1) {
                    setMoreRed(false);
                    FirebaseReportUtils.getInstance().reportNew("time_red_edit_pdf_click");
                } else if (this.userPreferences.getTimeEditPDFRed() == 1) {
                    setMoreRed(false);
                    FirebaseReportUtils.getInstance().reportNew("time_red_edit_pdf_click");
                } else if (this.userPreferences.getTimeEditRemindRed() == 1) {
                    setMoreRed(false);
                    FirebaseReportUtils.getInstance().reportNew("time_red_edit_pdf_click");
                }
                this.editAction.append("_M");
                FirebaseReportUtils.getInstance().reportNew("edit_more_click");
                showBubbleDialog();
                break;
            case R.id.pin_action /* 2131363447 */:
                this.isShareAction = true;
                DialogAddCategory.INSTANCE.showShareDialog(true, false, this.mainActivity, this.noteTmp, new DialogAddCategory.ShareListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.37

                    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$37$1 */
                    class AnonymousClass1 extends TimerTask {
                        AnonymousClass1() {
                        }

                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            EditContentAdapter editContentAdapter = DetailFragmentNew.this.contentAdapter;
                            if (editContentAdapter != null) {
                                HashMap<Integer, EditText> editTextHashMap = editContentAdapter.getEditTextHashMap();
                                ArrayList arrayList = new ArrayList();
                                Iterator<Integer> it2 = editTextHashMap.keySet().iterator();
                                int i6 = 0;
                                while (it2.hasNext()) {
                                    int intValue = it2.next().intValue();
                                    if (intValue == i6) {
                                        i6++;
                                        arrayList.add(editTextHashMap.get(Integer.valueOf(intValue)));
                                    }
                                }
                                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                                ShareUtil.shareLongPictures(detailFragmentNew.noteTmp, detailFragmentNew.title, detailFragmentNew.contentBeanList, arrayList, detailFragmentNew.mainActivity, detailFragmentNew.toggleChecklistView, DetailFragmentNew.this.currentFontBean, DetailFragmentNew.this.topTimeText);
                            }
                        }
                    }

                    AnonymousClass37() {
                    }

                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
                    public void shareAsLongPic(Note note) {
                        new Timer().schedule(new TimerTask() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.37.1
                            AnonymousClass1() {
                            }

                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                EditContentAdapter editContentAdapter = DetailFragmentNew.this.contentAdapter;
                                if (editContentAdapter != null) {
                                    HashMap<Integer, EditText> editTextHashMap = editContentAdapter.getEditTextHashMap();
                                    ArrayList arrayList = new ArrayList();
                                    Iterator<Integer> it2 = editTextHashMap.keySet().iterator();
                                    int i6 = 0;
                                    while (it2.hasNext()) {
                                        int intValue = it2.next().intValue();
                                        if (intValue == i6) {
                                            i6++;
                                            arrayList.add(editTextHashMap.get(Integer.valueOf(intValue)));
                                        }
                                    }
                                    DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                                    ShareUtil.shareLongPictures(detailFragmentNew.noteTmp, detailFragmentNew.title, detailFragmentNew.contentBeanList, arrayList, detailFragmentNew.mainActivity, detailFragmentNew.toggleChecklistView, DetailFragmentNew.this.currentFontBean, DetailFragmentNew.this.topTimeText);
                                }
                            }
                        }, 100L);
                    }

                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
                    public void shareAsPdf(Note note) {
                        if (App.isVip() || App.is6hFreeTry()) {
                            DetailFragmentNew.this.exportPDF();
                        } else {
                            VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "pdf");
                        }
                    }

                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
                    public void shareOnlyImg(Note note) {
                        ShareUtil.shareOnlyPic(new Note(DetailFragmentNew.this.noteTmp), DetailFragmentNew.this.mainActivity);
                    }

                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
                    public void shareOnlyText(Note note) {
                        Note note2 = new Note(DetailFragmentNew.this.noteTmp);
                        note2.setTitle(DetailFragmentNew.this.getNoteTitle());
                        String title = note2.getTitle();
                        StringBuffer stringBuffer2 = new StringBuffer();
                        for (int i6 = 0; i6 < DetailFragmentNew.this.contentBeanList.size(); i6++) {
                            stringBuffer2.append(DetailFragmentNew.this.contentBeanList.get(i6).getContent());
                            stringBuffer2.append("\n");
                        }
                        String str = title + System.getProperty("line.separator") + stringBuffer2;
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.SEND");
                        intent.setType(HTTP.PLAIN_TEXT_TYPE);
                        intent.putExtra("android.intent.extra.SUBJECT", title);
                        intent.putExtra("android.intent.extra.TEXT", str);
                        DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                        detailFragmentNew.startActivity(Intent.createChooser(intent, detailFragmentNew.getResources().getString(R.string.share)));
                    }

                    @Override // notes.easy.android.mynotes.view.DialogAddCategory.ShareListener
                    public void shareRecorings(Note note) {
                        ShareUtil.shareRecordings(new Note(DetailFragmentNew.this.noteTmp), DetailFragmentNew.this.mainActivity);
                    }
                });
                FirebaseReportUtils.getInstance().reportNew("edit_more_share");
                if (!App.userConfig.getShareUsed()) {
                    App.userConfig.setShareUsed(true);
                    reportFunctionUse();
                    break;
                }
                break;
        }
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void onClipboardClick() {
        this.clipboardDown = 1;
        this.mBottomBar.clipboardUnsubscribe();
        try {
            EditText editText = (EditText) this.mainActivity.getCurrentFocus();
            int selectionStart = editText.getSelectionStart();
            Editable editableText = editText.getEditableText();
            if (selectionStart >= 0 && selectionStart < editableText.length()) {
                editableText.insert(selectionStart, SystemHelper.getClipboardContent(this.mainActivity));
            }
            editableText.append((CharSequence) SystemHelper.getClipboardContent(this.mainActivity));
        } catch (Exception unused) {
        }
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void onClipboardNoClick() {
        this.clipboardDown = 0;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        CustomDialog customDialog;
        View findViewById;
        super.onConfigurationChanged(configuration);
        int i6 = configuration.orientation;
        if ((i6 != 2 && i6 != 1) || (customDialog = this.fontDialog) == null || customDialog.getView() == null || (findViewById = this.fontDialog.getView().findViewById(R.id.font_layout)) == null || getActivity() == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getCurrentScreenDisplay(getActivity()).heightPixels * 0.46d);
        findViewById.setLayoutParams(layoutParams);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragment = this;
        needScrollToTop = false;
        this.onCreateTime = System.currentTimeMillis();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        Runnable runnable;
        super.onDestroy();
        RecordGridAdapter recordGridAdapter = this.recordGridAdapter;
        if (recordGridAdapter != null) {
            recordGridAdapter.destoryPlay();
        }
        Handler handler = this.mHander;
        if (handler != null && (runnable = this.autoSaveRunnable) != null) {
            handler.removeCallbacks(runnable);
        }
        EditContentAdapter editContentAdapter = this.contentAdapter;
        if (editContentAdapter != null) {
            editContentAdapter.destoryPlay();
        }
    }

    public void onDo(String str, Object obj) {
        str.hashCode();
        if (str.equals("KEY_TEXT")) {
            this.content.setText((CharSequence) obj);
            EditText editText = this.content;
            applyCharacterStyle(editText, editText.getEditableText(), this.noteTmp.getAddress(), false);
        }
    }

    @Override // notes.easy.android.mynotes.edit.view.BaseEditView.PicClicklistener
    public void onEditAfter(Editable editable, EditText editText, int i6, int i7, int i8, int i9, boolean z6) {
        if (i9 <= i8) {
            BulletContorller bulletContorller = this.bulletContorller;
            if (bulletContorller != null) {
                bulletContorller.executeDeleteAction(editText, editable, i8, i9);
            }
        } else {
            BulletContorller bulletContorller2 = this.bulletContorller;
            if (bulletContorller2 != null) {
                bulletContorller2.executeInputAction(editText, editable, i8, i9);
            }
        }
        boolean z7 = this.mCurrentFontIndex != Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(this.userPreferences.getDefaultFloatSize()));
        if (fontStyleIsChange() || !TextUtils.isEmpty(this.highLightColor)) {
            setTextShape(editText, editable, this.isBold, this.isItalic, this.isUnderline, this.isStrikethrough, z7, this.highLightColor, i8, i9);
            if (i8 < i9 && this.contentAdapter != null && editText.getText().toString().substring(i8, i9).equals("\n") && (this.isBold || this.isItalic || this.isUnderline || this.isStrikethrough || z7 || !this.highLightColor.equals(""))) {
                int i10 = i9 + 1;
                if (editText.getText().length() > i10) {
                    editText.setSelection(i10);
                } else {
                    editText.setSelection(editText.getText().length());
                }
            }
        }
        this.userFoucChanged = false;
    }

    @Override // notes.easy.android.mynotes.edit.view.BaseEditView.PicClicklistener
    public void onEditTouch() {
        this.userFoucChanged = true;
    }

    @Override // notes.easy.android.mynotes.ui.fragments.BaseNewFragment
    public void onEvent(EventInfo eventInfo) {
        eventInfo.getId();
    }

    public void onEventMainThread(PushbulletReplyEvent pushbulletReplyEvent) {
        this.content.setText(getNoteContent() + System.getProperty("line.separator") + pushbulletReplyEvent.message);
    }

    @Override // notes.easy.android.mynotes.edit.view.BaseEditView.PicClicklistener
    public void onFocusChange(boolean z6) {
        clipboard(z6);
    }

    @Override // notes.easy.android.mynotes.ui.fragments.BaseNewFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z6) {
        super.onHiddenChanged(z6);
    }

    @Override // notes.easy.android.mynotes.models.adapters.HighLightAdapter.OnListCallback
    public void onHighLightColorClick(View view, String str) {
        int i6;
        Editable editable;
        int i7;
        EditText editText;
        EditText editText2 = (EditText) this.mainActivity.getCurrentFocus();
        if (editText2 == null) {
            return;
        }
        int selectionStart = editText2.getSelectionStart();
        int selectionEnd = editText2.getSelectionEnd();
        Editable editableText = editText2.getEditableText();
        if (selectionEnd <= selectionStart) {
            for (int i8 = 0; i8 < this.baseEntrys.size(); i8++) {
                BaseEditView baseEditView = this.baseEntrys.get(i8);
                if (baseEditView.getmEditText().getSelectionEnd() > baseEditView.getmEditText().getSelectionStart()) {
                    int selectionStart2 = baseEditView.getmEditText().getSelectionStart();
                    int selectionEnd2 = baseEditView.getmEditText().getSelectionEnd();
                    editable = baseEditView.getmEditText().getEditableText();
                    i6 = selectionEnd2;
                    editText = baseEditView.getmEditText();
                    i7 = selectionStart2;
                    break;
                }
            }
        }
        i6 = selectionEnd;
        editable = editableText;
        i7 = selectionStart;
        editText = editText2;
        if (i6 > i7) {
            for (BackgroundColorSpan backgroundColorSpan : editable.getSpans(i7, i6, BackgroundColorSpan.class)) {
                if (backgroundColorSpan != null) {
                    int spanStart = editable.getSpanStart(backgroundColorSpan);
                    int spanEnd = editable.getSpanEnd(backgroundColorSpan);
                    if (i7 == spanStart && spanEnd == i6) {
                        editable.removeSpan(backgroundColorSpan);
                    } else if (spanStart <= i7 && spanEnd >= i6) {
                        editable.removeSpan(backgroundColorSpan);
                        Object backgroundColorSpan2 = new BackgroundColorSpan(backgroundColorSpan.getBackgroundColor());
                        Object backgroundColorSpan3 = new BackgroundColorSpan(backgroundColorSpan.getBackgroundColor());
                        if (spanStart != i7) {
                            editable.setSpan(backgroundColorSpan2, spanStart, i7, 33);
                        }
                        if (spanEnd != i6) {
                            editable.setSpan(backgroundColorSpan3, i6, spanEnd, 33);
                        }
                    }
                }
            }
            setTextBackground(editText, editable, i7, i6, TextUtils.isEmpty(str) ? Color.parseColor(ConstantsBase.TRANSPARENT) : Color.parseColor(str), true);
            this.isBackOrSave = true;
            setSaveIconColor();
            FirebaseReportUtils.getInstance().reportNew("edit_select_font_highlight");
        }
        this.fontStyleChange = true;
        if (this.mBottomBar != null) {
            if (TextUtils.isEmpty(str) || ConstantsBase.TRANSPARENT.equals(str)) {
                this.mBottomBar.setHighLightDisplayColor(HighLightAdapter.HIGHLIGHT_COLOR_SHOW[0]);
            } else {
                this.mBottomBar.setHighLightDisplayColor(str);
            }
        }
        setEditRich("");
    }

    @Override // com.myview.android.checklistview.interfaces.CheckListChangedListener
    public void onItemAdded() {
        ChecklistManager checklistManager;
        if (this.noteTmp == null || !ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg() || (checklistManager = this.mChecklistManager) == null) {
            return;
        }
        try {
            checklistManager.mCheckListView.setChecklistColor(true);
            ChecklistManager checklistManager2 = this.mChecklistManager2;
            if (checklistManager2 != null) {
                checklistManager2.mCheckListView.setChecklistColor(true);
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
    public boolean onMenuDismiss() {
        this.mBottomBar.hideSelectedState();
        return false;
    }

    @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
    public boolean onMenuShow() {
        EditText editText;
        try {
            lock.lock();
            editText = (EditText) this.mainActivity.getCurrentFocus();
        } catch (Exception unused) {
            lock.lock();
            lock.unlock();
        }
        if (editText == null) {
            return false;
        }
        Editable editableText = editText.getEditableText();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        if (selectionEnd <= selectionStart) {
            int i6 = 0;
            while (true) {
                if (i6 >= this.baseEntrys.size()) {
                    break;
                }
                BaseEditView baseEditView = this.baseEntrys.get(i6);
                if (baseEditView.getmEditText().getSelectionEnd() > baseEditView.getmEditText().getSelectionStart()) {
                    selectionStart = baseEditView.getmEditText().getSelectionStart();
                    selectionEnd = baseEditView.getmEditText().getSelectionEnd();
                    editableText = baseEditView.getmEditText().getEditableText();
                    break;
                }
                i6++;
            }
        }
        InputHelperView inputHelperView = this.mBottomBar;
        inputHelperView.showSelectedState(true, editableText, selectionStart, selectionEnd, this.mRecentNoteBg.isDarkBg());
        lock.unlock();
        return false;
    }

    @Override // notes.easy.android.mynotes.models.listeners.OnNoteSaved
    public void onNoteSaved(Note note) {
        EventUtils.post(102, note.get_id());
        if (this.isNewCreate) {
            int parseInt = Integer.parseInt(notes.easy.android.mynotes.utils.date.DateUtils.getString(this.lastTime, notes.easy.android.mynotes.utils.date.DateUtils.YEAR));
            int parseInt2 = Integer.parseInt(notes.easy.android.mynotes.utils.date.DateUtils.getString(this.lastTime, notes.easy.android.mynotes.utils.date.DateUtils.MONTH));
            int parseInt3 = Integer.parseInt(notes.easy.android.mynotes.utils.date.DateUtils.getString(this.lastTime, notes.easy.android.mynotes.utils.date.DateUtils.DAY));
            int parseInt4 = Integer.parseInt(notes.easy.android.mynotes.utils.date.DateUtils.getString(System.currentTimeMillis(), notes.easy.android.mynotes.utils.date.DateUtils.YEAR));
            int parseInt5 = Integer.parseInt(notes.easy.android.mynotes.utils.date.DateUtils.getString(System.currentTimeMillis(), notes.easy.android.mynotes.utils.date.DateUtils.MONTH));
            int parseInt6 = Integer.parseInt(notes.easy.android.mynotes.utils.date.DateUtils.getString(System.currentTimeMillis(), notes.easy.android.mynotes.utils.date.DateUtils.DAY));
            if (parseInt > parseInt4) {
                FirebaseReportUtils.getInstance().reportNew("calendar_future_create_success");
            } else if (parseInt2 > parseInt5) {
                FirebaseReportUtils.getInstance().reportNew("calendar_future_create_success");
            } else if (parseInt3 > parseInt6) {
                FirebaseReportUtils.getInstance().reportNew("calendar_future_create_success");
            }
        }
        if (ConstantsBase.ACTION_WIDGET_NO_NOTES_CREATE_NEW_NOTES.equals(this.mainActivity.editFrom) && DbHelper.getInstance().getNotesActive().size() == 1 && this.WidgetSave) {
            WidgetCustomizeActivity.Companion.setNote(this.noteTmp);
            EventBus.getDefault().post(new WidgetEvent(true, this.noteTmp.get_id().longValue()));
            this.WidgetSave = false;
        }
        if ("widget_add_new".equals(this.mainActivity.editFrom)) {
            FirebaseReportUtils.getInstance().reportNew("widget_launcher_create_success");
        }
        if (this.mainActivity.bean != null) {
            FirebaseReportUtils.getInstance().reportNew("widget_sidebar_note_success");
            WidgetCustomizeActivity.Companion.setNote(this.noteTmp);
            Intent intent = new Intent(this.mainActivity, WidgetCustomizeActivity.class);
            if (ScreenUtils.isPad(this.mainActivity)) {
                intent = new Intent(this.mainActivity, WidgetCustomizeActivityPad.class);
            }
            intent.putExtra(WidgetUtils.EXTRA_SELECT_WIDGET_POSITION, this.mainActivity.bean.getSelectWidgetPosition());
            intent.putExtra(WidgetUtils.EXTRA_IS_POINT_REPORT, this.mainActivity.bean.isPointReport());
            intent.putExtra(Constants.EXTRA_WIDGET_FIREBASE_REPORT, this.mainActivity.bean.getWidgetFirebaseReport());
            startActivity(intent);
            ActivityStackManager.getInstance().finishActivity(SidebarSelectNotesListActivity.class);
        }
        this.note = new Note(note);
        if (this.goBack) {
            goHome();
        }
        this.hasSaveSuccess = true;
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void onNumListSelected(NumListEntry numListEntry) {
        int i6;
        MyBulletSpan myBulletSpan;
        int i7;
        int i8;
        MyBulletSpan myBulletSpan2;
        if (numListEntry == null || this.contentAdapter.getEditTextHashMap().get(Integer.valueOf(this.contentAdapter.getSelectedEditText())) == null) {
            return;
        }
        EditText editText = this.contentAdapter.getEditTextHashMap().get(Integer.valueOf(this.contentAdapter.getSelectedEditText())).hasFocus() ? this.contentAdapter.getEditTextHashMap().get(Integer.valueOf(this.contentAdapter.getSelectedEditText())) : null;
        if (editText == null) {
            if ("Dots".equals(numListEntry.getUniqueName())) {
                Toast.makeText(App.getAppContext(), R.string.bullet_click_toast, 0).show();
                return;
            } else {
                Toast.makeText(App.getAppContext(), R.string.numbers_click_toast, 0).show();
                return;
            }
        }
        Editable editableText = editText.getEditableText();
        if (editableText == null) {
            return;
        }
        String uniqueName = numListEntry.getUniqueName();
        if ("checkList_no".equals(uniqueName) || "checkList_no_white".equals(uniqueName)) {
            uniqueName = ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).isDarkBg() ? "checkList_no_white" : "checkList_no";
        }
        if (editableText.length() <= 0) {
            editableText.insert(0, Constants.ZERO_WIDTH_SPACE_STR);
            MyBulletSpan myBulletSpan3 = new MyBulletSpan(editText, uniqueName, 1, 1);
            editableText.setSpan(myBulletSpan3, 0, 1, 18);
            editableText.setSpan(myBulletSpan3.myImageSpan, 0, 1, 33);
            this.contentAdapter.setTextStyle(true);
            return;
        }
        BulletSpanInfo curLineInfo = MyBulletSpanHelper.getCurLineInfo(editText);
        if (curLineInfo == null) {
            return;
        }
        BulletSpanInfo preLineInfo = MyBulletSpanHelper.getPreLineInfo(editText, curLineInfo.getLine());
        if (curLineInfo.getLineStart() == curLineInfo.getLineEnd()) {
            if (preLineInfo == null || !preLineInfo.isSameName(uniqueName) || (myBulletSpan2 = preLineInfo.getMyBulletSpan()) == null) {
                i7 = -1;
                i8 = 1;
            } else {
                i8 = myBulletSpan2.getNlLevel();
                i7 = myBulletSpan2.getNlGroup();
            }
            if (i7 == -1) {
                i7 = MyBulletSpanHelper.createNewGroup(editText);
            }
            editableText.insert(curLineInfo.getLineStart(), Constants.ZERO_WIDTH_SPACE_STR);
            curLineInfo.setLineEnd(curLineInfo.getLineStart() + 1);
            MyBulletSpan myBulletSpan4 = new MyBulletSpan(editText, uniqueName, i8, i7);
            editableText.setSpan(myBulletSpan4, curLineInfo.getLineStart(), curLineInfo.getLineEnd(), 18);
            editableText.setSpan(myBulletSpan4.myImageSpan, curLineInfo.getLineStart(), curLineInfo.getLineStart() + 1, 33);
            EditContentAdapter editContentAdapter = this.contentAdapter;
            if (editContentAdapter != null) {
                editContentAdapter.setTextStyle(false);
                return;
            }
            return;
        }
        MyBulletSpan myBulletSpan5 = curLineInfo.getMyBulletSpan();
        if (myBulletSpan5 != null) {
            myBulletSpan5.setNlGroup(MyBulletSpanHelper.createNewGroup(editText));
            if (uniqueName.equals(myBulletSpan5.getNlName())) {
                editableText.removeSpan(myBulletSpan5);
                editableText.removeSpan(myBulletSpan5.myImageSpan);
                MyBulletSpanHelper.sortAllSpanByGroup(editText, myBulletSpan5.getNlGroup());
            } else {
                MyBulletSpanHelper.updateAllSpanInfoByGroup(editText, uniqueName, myBulletSpan5.getNlGroup());
            }
        } else {
            if (preLineInfo == null || !preLineInfo.isSameName(uniqueName) || (myBulletSpan = preLineInfo.getMyBulletSpan()) == null) {
                i6 = 1;
            } else {
                i6 = myBulletSpan.getNlLevel();
                myBulletSpan.getNlGroup();
            }
            int createNewGroup = MyBulletSpanHelper.createNewGroup(editText);
            try {
                editableText.insert(curLineInfo.getLineStart(), Constants.ZERO_WIDTH_SPACE_STR);
                curLineInfo.setLineEnd(curLineInfo.getLineStart() + 1);
                MyBulletSpan myBulletSpan6 = new MyBulletSpan(editText, uniqueName, i6, createNewGroup);
                myBulletSpan6.myImageSpan.setNlLevel(i6);
                editableText.setSpan(myBulletSpan6, curLineInfo.getLineStart(), curLineInfo.getLineEnd(), 18);
                editableText.setSpan(myBulletSpan6.myImageSpan, curLineInfo.getLineStart(), curLineInfo.getLineStart() + 1, 33);
                MyBulletSpanHelper.sortAllSpanByGroup(editText, createNewGroup);
            } catch (Exception unused) {
            }
        }
        EditContentAdapter editContentAdapter2 = this.contentAdapter;
        if (editContentAdapter2 != null) {
            editContentAdapter2.setTextStyle(true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.activityPausing = true;
        if (!this.goBack) {
            saveNote(this, false);
            if (!this.isShareAction) {
                reportSaveNoteEvent("edit_save_auto_home");
                reportSaveNoteEvent("edit_save_all");
                int id = ResNoteBgManager.getInstance().getNoteBg(this.noteTmp.getStickyColor(), this.noteTmp.getStickyType(), this.noteTmp.getBgId()).getId();
                FirebaseReportUtils.getInstance().reportNew("edit_default_color", Constants.THEME_EVENT_KEY, id + "");
                if (this.isBackOrSave && this.newCreate) {
                    FirebaseReportUtils.getInstance().reportNew("M_create_success_auto");
                    reportSaveNoteEvent("M_create_success");
                    FirebaseReportUtils.getInstance().reportNew("M_create_OK");
                    if (!App.userConfig.getFirstEditOk()) {
                        App.userConfig.setFirstEditOk(true);
                        FirebaseReportUtils.getInstance().logMainFlow(getActivity(), "v1_f_main_create_ok");
                    }
                }
            }
        }
        View view = this.toggleChecklistView;
        if (view != null) {
            KeyboardUtils.hideKeyboard(view);
        }
    }

    @Override // notes.easy.android.mynotes.edit.view.BaseEditView.PicClicklistener
    public void onPicClick(Attachment attachment, int i6) {
        Uri shareableUri = FileProviderHelper.getShareableUri(attachment);
        if (this.contentBeanList.size() > 0) {
            this.noteTmp.setAttachmentsList(new ArrayList<>());
            for (int i7 = 0; i7 < this.contentBeanList.size(); i7++) {
                if (this.contentBeanList.get(i7).getViewType() == 4) {
                    for (int i8 = 0; i8 < this.contentBeanList.get(i7).getAttachmentsList().size(); i8++) {
                        this.noteTmp.addAttachment(this.contentBeanList.get(i7).getAttachmentsList().get(i8));
                    }
                }
            }
        }
        if (ConstantsBase.MIME_TYPE_FILES.equals(attachment.getMime_type())) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(shareableUri, StorageHelper.getMimeType(this.mainActivity, shareableUri));
            intent.addFlags(3);
            if (IntentChecker.isAvailable(this.mainActivity.getApplicationContext(), intent, null)) {
                startActivity(intent);
                return;
            }
            return;
        }
        if ("image/jpeg".equals(attachment.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type()) || "video/mp4".equals(attachment.getMime_type())) {
            GalleryActivity.transforNote = this.noteTmp;
            ArrayList arrayList = new ArrayList();
            int i9 = 0;
            for (int i10 = 0; i10 < this.noteTmp.getAttachmentsList().size(); i10++) {
                Attachment attachment2 = this.noteTmp.getAttachmentsList().get(i10);
                if ("image/jpeg".equals(attachment2.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment2.getMime_type()) || "video/mp4".equals(attachment2.getMime_type())) {
                    arrayList.add(attachment2);
                    if (attachment2.getUri().getPath().equals(attachment.getUri().getPath())) {
                        i9 = arrayList.size() - 1;
                    }
                }
            }
            try {
                Intent intent2 = new Intent(this.mainActivity, GalleryActivity.class);
                intent2.putExtra(ConstantsBase.GALLERY_CLICKED_IMAGE, i9);
                this.mainActivity.startActivityForResult(intent2, Constants.DETAIL_TO_GALLERY);
                FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_preview");
            } catch (Exception unused) {
            }
        }
    }

    @Override // notes.easy.android.mynotes.models.listeners.OnReminderPickedListener
    public void onRecurrenceReminderPicked(String str) {
        this.noteTmp.setRecurrenceRule(str);
    }

    @Override // notes.easy.android.mynotes.models.listeners.OnReminderPickedListener
    public void onReminderPicked(long j6) {
        this.noteTmp.setAlarm(j6);
        this.alarmChanged = true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int i6, String[] strArr, int[] iArr) {
        EditText editText;
        boolean z6 = false;
        if (i6 == 1002) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(App.getAppContext(), R.string.no_audio_permission, 0).show();
                return;
            } else {
                this.mBottomBar.showBottomRecordDialog();
                return;
            }
        }
        if (i6 == 1004) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(App.getAppContext(), R.string.no_audio_permission, 0).show();
                return;
            }
            InputHelperView inputHelperView = this.mBottomBar;
            if (baseSttHasFocus() || ((editText = this.content) != null && editText.hasFocus())) {
                z6 = true;
            }
            inputHelperView.showBottomSpeechDialog(z6);
        }
    }

    @Override // notes.easy.android.mynotes.ui.fragments.BaseNewFragment, androidx.fragment.app.Fragment
    public void onResume() {
        int intValue;
        super.onResume();
        this.isShareAction = false;
        this.activityPausing = false;
        if (GalleryActivity.indexList.size() > 0) {
            for (int i6 = 0; i6 < GalleryActivity.indexList.size(); i6++) {
                try {
                    Attachment attachFromUri = getAttachFromUri(GalleryActivity.indexList.get(i6));
                    if (attachFromUri != null) {
                        int order = attachFromUri.getOrder();
                        if (order >= 1) {
                            BaseEditView baseEditView = this.baseEntrys.get(order - 1);
                            baseEditView.removeAttachment(attachFromUri);
                            if (TextUtils.isEmpty(baseEditView.getmEditText().getText().toString()) && baseEditView.getAttachmentSize() == 0) {
                                this.baseEntrys.remove(baseEditView);
                                this.contentLayout.removeView(baseEditView.getBaseView());
                            }
                        }
                        removeAttachment(attachFromUri);
                    }
                } catch (Exception unused) {
                }
                this.mAttachmentAdapter.notifyDataSetChanged();
            }
            for (int i7 = 0; i7 < this.contentBeanList.size(); i7++) {
                if (this.contentBeanList.get(i7).getViewType() == 4) {
                    List<Attachment> attachmentsList = this.contentBeanList.get(i7).getAttachmentsList();
                    ArrayList arrayList = new ArrayList();
                    for (int i8 = 0; i8 < attachmentsList.size(); i8++) {
                        for (int i9 = 0; i9 < GalleryActivity.indexList.size(); i9++) {
                            if (attachmentsList.get(i8).getUri().getPath().equals(GalleryActivity.indexList.get(i9).getPath())) {
                                arrayList.add(attachmentsList.get(i8));
                            }
                        }
                    }
                    this.contentBeanList.get(i7).getAttachmentsList().removeAll(arrayList);
                    if (this.contentBeanList.get(i7).getAttachmentsList().size() == 0) {
                        this.contentBeanList.remove(i7);
                        notifyRemoved(i7);
                    } else {
                        notifyChanged(i7);
                    }
                }
            }
            GalleryActivity.indexList.clear();
        }
        if (GalleryActivity.editIndex.size() > 0) {
            List<Attachment> imageAttachments = getImageAttachments();
            if (imageAttachments.size() == 0) {
                return;
            }
            for (int i10 = 0; i10 < GalleryActivity.editIndex.size(); i10++) {
                try {
                    intValue = GalleryActivity.editIndex.get(i10).intValue();
                } catch (Exception unused2) {
                }
                if (intValue > imageAttachments.size() - 1) {
                    return;
                }
                Attachment attachment = imageAttachments.get(intValue);
                int order2 = attachment.getOrder();
                if (order2 == 0) {
                    int i11 = 0;
                    while (true) {
                        if (i11 >= this.imageList.size()) {
                            break;
                        }
                        if (attachment == this.imageList.get(i11)) {
                            attachment.setUri(GalleryActivity.editUri.get(i10));
                            if (ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type())) {
                                attachment.setMime_type("image/jpeg");
                            }
                            this.imageList.set(i11, attachment);
                        } else {
                            i11++;
                        }
                    }
                    this.mAttachmentAdapter.notifyDataSetChanged();
                } else {
                    int i12 = order2 - 1;
                    if (i12 < this.baseEntrys.size()) {
                        BaseEditView baseEditView2 = this.baseEntrys.get(i12);
                        int i13 = 0;
                        while (true) {
                            if (i13 >= baseEditView2.getAttachmentSize()) {
                                break;
                            }
                            if (attachment == baseEditView2.getAttachments().get(i13)) {
                                attachment.setUri(GalleryActivity.editUri.get(i10));
                                if (ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type())) {
                                    attachment.setMime_type("image/jpeg");
                                }
                                baseEditView2.replaceAttach(attachment, i13);
                            } else {
                                i13++;
                            }
                        }
                    }
                }
                this.isBackOrSave = true;
            }
            this.timeModified = true;
            ArrayList arrayList2 = new ArrayList();
            if (this.imageList.size() > 0) {
                arrayList2.addAll(this.imageList);
            }
            for (int i14 = 0; i14 < this.baseEntrys.size(); i14++) {
                if (this.baseEntrys.get(i14).getAttachmentSize() > 0) {
                    arrayList2.addAll(this.baseEntrys.get(i14).getAttachments());
                }
            }
            if (this.audioList.size() > 0) {
                arrayList2.addAll(this.audioList);
            }
            for (int i15 = 0; i15 < GalleryActivity.editIndex.size(); i15++) {
                int i16 = 0;
                for (int i17 = 0; i17 < this.contentBeanList.size(); i17++) {
                    if (this.contentBeanList.get(i17).getViewType() == 4) {
                        for (int i18 = 0; i18 < this.contentBeanList.get(i17).getAttachmentsList().size(); i18++) {
                            if (i16 == GalleryActivity.editIndex.get(i15).intValue()) {
                                this.contentBeanList.get(i17).getAttachmentsList().get(i18).setUri(GalleryActivity.editUri.get(i15));
                                notifyChanged(i17);
                            }
                            i16++;
                        }
                    }
                }
            }
            if (this.contentBeanList.size() > 0) {
                this.noteTmp.setAttachmentsList(new ArrayList<>());
                for (int i19 = 0; i19 < this.contentBeanList.size(); i19++) {
                    if (this.contentBeanList.get(i19).getViewType() == 4) {
                        for (int i20 = 0; i20 < this.contentBeanList.get(i19).getAttachmentsList().size(); i20++) {
                            this.noteTmp.addAttachment(this.contentBeanList.get(i19).getAttachmentsList().get(i20));
                        }
                    }
                }
            }
            GalleryActivity.editIndex.clear();
            GalleryActivity.editUri.clear();
        }
        if (this.mainActivity.getWindow().getDecorView().findFocus() instanceof EditText) {
            EditText editText = (EditText) this.mainActivity.getWindow().getDecorView().findFocus();
            this.detailFragmentEditText = editText;
            editText.post(new Runnable() { // from class: b6.v2
                @Override // java.lang.Runnable
                public void run() {
                    DetailFragmentNew.this.lambda$onResume$0();
                }
            });
        }
        updateRedrawingAttachment();
        EditText editText2 = this.content;
        if (editText2 != null && PDFUtils.bullet_size_changed) {
            PDFUtils.checkSpanSize(editText2.getEditableText(), false);
            PDFUtils.bullet_size_changed = false;
        }
        this.content.postDelayed(new Runnable() { // from class: b6.w2
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$onResume$1();
            }
        }, 200L);
    }

    @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
    public boolean onSelectedAreChanged(int i6, int i7) {
        AbsoluteSizeSpan[] absoluteSizeSpanArr;
        int length;
        AbsoluteSizeSpan[] absoluteSizeSpanArr2;
        int length2;
        if (this.userFoucChanged) {
            this.currentFontAbsoluteSize = ScreenUtils.dpToPx(16);
            if (!this.content.hasFocus()) {
                int i8 = 0;
                while (true) {
                    if (i8 >= this.baseEntrys.size()) {
                        break;
                    }
                    BaseEditView baseEditView = this.baseEntrys.get(i8);
                    if (!baseEditView.getmEditText().hasFocus()) {
                        i8++;
                    } else if (i7 > 0) {
                        if (baseEditView.getmEditText().getEditableText().toString() != null && baseEditView.getmEditText().getEditableText().toString().trim() != null && baseEditView.getmEditText().getEditableText().toString().length() > i7 && i7 > (length = baseEditView.getmEditText().getEditableText().toString().substring(0, i7).trim().length())) {
                            i7 = length;
                        }
                        if (i7 > 1 && (absoluteSizeSpanArr = (AbsoluteSizeSpan[]) baseEditView.getmEditText().getEditableText().getSpans(i7 - 1, i7, AbsoluteSizeSpan.class)) != null && absoluteSizeSpanArr.length > 0) {
                            this.currentFontAbsoluteSize = absoluteSizeSpanArr[absoluteSizeSpanArr.length - 1].getSize();
                        }
                    }
                }
            } else if (i7 > 0) {
                if (this.content.getEditableText().toString() != null && this.content.getEditableText().toString().trim() != null && this.content.getEditableText().toString().length() > i7 && i7 > (length2 = this.content.getEditableText().toString().substring(0, i7).trim().length())) {
                    i7 = length2;
                }
                if (i7 > 1 && (absoluteSizeSpanArr2 = this.content.getEditableText().getSpans(i7 - 1, i7, AbsoluteSizeSpan.class)) != null && absoluteSizeSpanArr2.length > 0) {
                    this.currentFontAbsoluteSize = absoluteSizeSpanArr2[absoluteSizeSpanArr2.length - 1].getSize();
                }
            }
            if (this.searchLayout.getVisibility() == 0) {
                resetSearchedText();
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.PhotoActionListener
    public void photoPreview(int i6, Attachment attachment) {
        Uri shareableUri = FileProviderHelper.getShareableUri(attachment);
        if (ConstantsBase.MIME_TYPE_FILES.equals(attachment.getMime_type())) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(shareableUri, StorageHelper.getMimeType(this.mainActivity, shareableUri));
            intent.addFlags(3);
            if (IntentChecker.isAvailable(this.mainActivity.getApplicationContext(), intent, null)) {
                startActivity(intent);
                return;
            }
            return;
        }
        if ("image/jpeg".equals(attachment.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type()) || "video/mp4".equals(attachment.getMime_type())) {
            GalleryActivity.transforNote = this.noteTmp;
            ArrayList arrayList = new ArrayList();
            int i7 = 0;
            for (Attachment attachment2 : this.noteTmp.getAttachmentsList()) {
                if ("image/jpeg".equals(attachment2.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment2.getMime_type()) || "video/mp4".equals(attachment2.getMime_type())) {
                    arrayList.add(attachment2);
                    if (attachment2.equals(attachment)) {
                        i7 = arrayList.size() - 1;
                    }
                }
            }
            try {
                Intent intent2 = new Intent(this.mainActivity, GalleryActivity.class);
                intent2.putExtra(ConstantsBase.GALLERY_CLICKED_IMAGE, i7);
                this.mainActivity.startActivityForResult(intent2, Constants.DETAIL_TO_GALLERY);
                FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_preview");
            } catch (Exception unused) {
            }
        }
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void recordCancel() {
        if (this.isRecording) {
            this.duration = 0;
            stopRecording();
        }
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void recordFinish() {
        if (this.mRecorder != null) {
            showSavingView();
            takRecord();
        }
        stopRecording();
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void recordPermission() {
        this.editAction.append("_R");
        addAudioAttachment();
        if (App.userConfig.getRecordUsed()) {
            return;
        }
        App.userConfig.setRecordUsed(true);
        reportFunctionUse();
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void recordState() {
        boolean z6 = !this.isRecordingPause;
        this.isRecordingPause = z6;
        if (!this.isRecording) {
            this.isRecording = true;
            startRecording();
            resumeRecording();
        } else if (z6) {
            if (Build.VERSION.SDK_INT >= 24) {
                resumeRecording();
            }
        } else if (Build.VERSION.SDK_INT >= 24) {
            pauseRecording();
        }
    }

    public void reportSaveNoteEvent(final String str) {
        App.executeOnGlobalExecutor(new Runnable() { // from class: b6.t2
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$reportSaveNoteEvent$41(str);
            }
        });
    }

    public void resetSearchedText() {
        resetSearchedText(false);
    }

    public void saveAndExit(OnNoteSaved onNoteSaved) {
        if (isAdded()) {
            if (this.isBackOrSave) {
                needScrollToTop = true;
            }
            this.goBack = true;
            saveNote(onNoteSaved, true);
        }
    }

    void saveNote(OnNoteSaved onNoteSaved, boolean z6) {
        CheckListView checkListView;
        CheckListView checkListView2;
        UserConfig userConfig;
        RecordGridAdapter recordGridAdapter;
        this.noteTmp.setTitle(getNoteTitle());
        String json = this.gson.toJson(this.contentOriginal);
        String json2 = this.gson.toJson(this.contentBeanList);
        String replace = json.replace("\"aBoolean\":true", "\"aBoolean\":false");
        String replace2 = json2.replace("\"aBoolean\":true", "\"aBoolean\":false");
        if (!replace.equals(replace2)) {
            this.noteTmp.setNewData(replace2);
            if (this.contentBeanList.size() > 0) {
                this.noteTmp.setAttachmentsList(new ArrayList<>());
                for (int i6 = 0; i6 < this.contentBeanList.size(); i6++) {
                    if (this.contentBeanList.get(i6).getViewType() == 4) {
                        for (int i7 = 0; i7 < this.contentBeanList.get(i6).getAttachmentsList().size(); i7++) {
                            this.noteTmp.addAttachment(this.contentBeanList.get(i6).getAttachmentsList().get(i7));
                        }
                    } else {
                        this.contentBeanList.get(i6).getViewType();
                    }
                }
            }
            new SaveNoteTask(onNoteSaved, lastModificationUpdatedNeeded() && !this.timeModified).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.noteTmp);
            if (this.baseEntrys.size() == 0) {
                this.noteTmp.setContent(getNoteContent());
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(getNoteContent());
                stringBuffer.append(Constants.GAP_BASE);
                boolean z7 = true;
                for (int i8 = 0; i8 < this.baseEntrys.size(); i8++) {
                    if (!TextUtils.isEmpty(this.baseEntrys.get(i8).getContent())) {
                        stringBuffer.append(this.baseEntrys.get(i8).getContent());
                        stringBuffer.append(Constants.GAP_BASE);
                        z7 = false;
                    } else if (this.baseEntrys.get(i8).getAttachmentSize() > 0) {
                        stringBuffer.append(StringUtils.SPACE);
                        stringBuffer.append(Constants.GAP_BASE);
                    }
                }
                if (!TextUtils.isEmpty(getSecondCheckContent())) {
                    if (z7) {
                        stringBuffer.append(StringUtils.SPACE);
                        stringBuffer.append(Constants.GAP_BASE);
                    }
                    stringBuffer.append(getSecondCheckContent());
                }
                this.noteTmp.setContent(stringBuffer.toString());
            }
        }
        for (int i9 = 0; i9 < this.audioList.size(); i9++) {
            this.noteTmp.addAttachment(this.audioList.get(i9));
        }
        getSpan();
        if (this.goBack && TextUtils.isEmpty(this.noteTmp.getTitle()) && ((TextUtils.isEmpty(this.noteTmp.getContent()) || Constants.GAP_BASE.equals(this.noteTmp.getContent().trim())) && this.noteTmp.getAttachmentsList().isEmpty() && !this.timeModified)) {
            if (!TextUtils.isEmpty(this.noteOriginal.getTitle()) || !TextUtils.isEmpty(this.noteTmp.getContent()) || !this.noteOriginal.getAttachmentsList().isEmpty()) {
                new SaveNoteTask(onNoteSaved, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.noteTmp);
            }
            goHome();
            return;
        }
        if (saveNotNeeded() && !this.mainActivity.isFromOUtside && !fontStyleChanged() && !this.timeModified) {
            if (this.goBack) {
                goHome();
                return;
            }
            return;
        }
        if (!noteHasChanged() && !this.noteTmp.isArchived().booleanValue() && !this.noteTmp.isTrashed().booleanValue() && !this.bgChanged) {
            boolean z8 = this.fontStyleChange;
        }
        this.noteTmp.setAttachmentsListOld(this.note.getAttachmentsList());
        if (!this.title.getText().toString().isEmpty() || !TextUtils.isEmpty(getNoteContent()) || !TextUtils.isEmpty(this.noteTmp.getContent()) || ((this.noteTmp.getAttachmentsList() != null && this.noteTmp.getAttachmentsList().size() != 0) || (((recordGridAdapter = this.recordGridAdapter) != null && recordGridAdapter.getCount() != 0) || this.noteOriginal.getBgId() != this.noteTmp.getBgId() || ((this.noteTmp.getBgId() == 10 && !TextUtils.equals(this.noteOriginal.getStickyColor(), this.noteTmp.getStickyColor())) || this.timeModified)))) {
            if (this.noteTmp.isChecklist().booleanValue()) {
                ChecklistManager checklistManager = this.mChecklistManager;
                int itemSize = (checklistManager == null || (checkListView2 = checklistManager.mCheckListView) == null) ? 0 : checkListView2.getItemSize();
                ChecklistManager checklistManager2 = this.mChecklistManager2;
                if (checklistManager2 != null && (checkListView = checklistManager2.mCheckListView) != null) {
                    itemSize += checkListView.getItemSize();
                }
                Bundle bundle = new Bundle();
                bundle.putString("item_num", "" + itemSize);
                FirebaseReportUtils.getInstance().reportAll("checklist_item_num", bundle);
            }
            this.hasSaveSuccess = false;
            if (this.isNewCreate && !this.hasSavedFromCreate) {
                int differentDays = notes.easy.android.mynotes.utils.date.DateUtils.differentDays(new Date(App.userConfig.getLastNotesTimed()), new Date(System.currentTimeMillis()));
                if (App.userConfig.getLastNotesTimed() == 0 || differentDays >= 1) {
                    UserConfig userConfig2 = App.userConfig;
                    userConfig2.setSeriesNotesNumb(userConfig2.getSeriesNotesNumb() + 1);
                    if (App.userConfig.getSeriesNotesNumb() >= 2 && App.userConfig.getSeriesNotesNumb() < 5) {
                        App.userConfig.setMedalL1Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 5 && App.userConfig.getSeriesNotesNumb() < 10) {
                        App.userConfig.setMedalL2Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 10 && App.userConfig.getSeriesNotesNumb() < 20) {
                        App.userConfig.setMedalL3Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 20 && App.userConfig.getSeriesNotesNumb() < 40) {
                        App.userConfig.setMedalL4Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 40 && App.userConfig.getSeriesNotesNumb() < 70) {
                        App.userConfig.setMedalL5Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 70 && App.userConfig.getSeriesNotesNumb() < 100) {
                        App.userConfig.setMedalL6Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 100 && App.userConfig.getSeriesNotesNumb() < 200) {
                        App.userConfig.setMedalL7Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 200 && App.userConfig.getSeriesNotesNumb() < 365) {
                        App.userConfig.setMedalL8Reached(true);
                    } else if (App.userConfig.getSeriesNotesNumb() >= 365) {
                        App.userConfig.setMedalL9Reached(true);
                    }
                    EventUtils.post(107);
                }
            }
            App.userConfig.setLastNotesTimed(System.currentTimeMillis());
            this.hasSavedFromCreate = true;
        }
        new SaveNoteTask(onNoteSaved, lastModificationUpdatedNeeded() && !this.timeModified).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.noteTmp);
        int size = DbHelper.getInstance().getNotesActive().size();
        if (!this.userPreferences.getSaveNoteFirst()) {
            this.userPreferences.setSaveNoteFirst(true);
        }
        long j6 = RemoteConfig.getLong("ad_create_notes_threshold");
        if (j6 == 0) {
            j6 = 1;
        }
        if (!this.userPreferences.getShowInterAd() && size >= j6 - 1) {
            this.userPreferences.setShowInterAd(true);
        }
        this.root.postDelayed(new Runnable() { // from class: b6.b2
            @Override // java.lang.Runnable
            public void run() {
                DetailFragmentNew.this.lambda$saveNote$40();
            }
        }, 300L);
        if (this.isNewCreate && (userConfig = App.userConfig) != null && userConfig.getNewRealOpen()) {
            if (size == 1 || size == 5) {
                String str = size == 5 ? "save_5_note" : "save_1_note";
                App.userConfig.setAppDeepLevel(str);
                FirebaseReportUtils.getInstance().setUserPropertyKV("cp_main_depth", App.userConfig.getAppDeepLevel());
                Bundle bundle2 = new Bundle();
                bundle2.putString("pr_status", str);
                FirebaseReportUtils.getInstance().reportNew("user_main_depth", bundle2);
            }
        }
    }

    public void saveNotes() {
        saveAndExit(this);
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void selectBgDialogDiamiss() {
        if (!this.colorDialogConfirm) {
            FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_VIP_show_touch");
        }
        this.colorDialogConfirm = false;
        NoteBgBean noteBgBean = this.mLastNoteBg;
        if (noteBgBean != null) {
            if (!noteBgBean.isVip() || App.isVip()) {
                clickOkBtn();
                return;
            }
            this.mBottomBar.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.detail_content_card.getLayoutParams();
            layoutParams.bottomMargin = 0;
            this.detail_content_card.setLayoutParams(layoutParams);
            DialogAddCategory.INSTANCE.showVipConfiremDialog(this.mainActivity, this);
        }
    }

    public void setBaseContext(Context context) {
        this.mBaseContext = context;
    }

    public List<EditContentBean> setEditContentBeans(List<EditContentBean> list, String str, int i6, int i7, String str2) {
        String str3 = (str.equals(Constants.SPAN_U) || str.equals(Constants.SPAN_STRIKETHROUGH)) ? "" : "/" + str2;
        for (int i8 = 0; i8 < list.size(); i8++) {
            if (i6 <= list.get(i8).getStart() && i7 >= list.get(i8).getStart()) {
                String str4 = list.get(i8).getRichData().isEmpty() ? "" : list.get(i8).getRichData() + ",";
                if (i7 > list.get(i8).getEnd()) {
                    list.get(i8).setRichData(str4 + str + i6 + "/" + list.get(i8).getEnd() + str3);
                    int i9 = 0;
                    while (true) {
                        i9++;
                        int i10 = i8 + i9;
                        if (i10 < list.size()) {
                            if (i7 > list.get(i10).getEnd()) {
                                EditContentBean editContentBean = list.get(i10);
                                StringBuilder sb = new StringBuilder();
                                sb.append(list.get(i10).getRichData().isEmpty() ? "" : list.get(i10).getRichData() + ",");
                                sb.append(str);
                                sb.append(list.get(i10).getStart());
                                sb.append("/");
                                sb.append(list.get(i10).getEnd());
                                sb.append(str3);
                                editContentBean.setRichData(sb.toString());
                            } else {
                                EditContentBean editContentBean2 = list.get(i10);
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(list.get(i10).getRichData().isEmpty() ? "" : list.get(i10).getRichData() + ",");
                                sb2.append(str);
                                sb2.append(list.get(i10).getStart());
                                sb2.append("/");
                                sb2.append(i7);
                                sb2.append(str3);
                                editContentBean2.setRichData(sb2.toString());
                            }
                        }
                    }
                } else {
                    list.get(i8).setRichData(str4 + str + i6 + "/" + i7 + str3);
                }
            }
        }
        return list;
    }

    public void setEditText() {
        if (!this.isEdit || this.currentFocusEdit == null) {
            this.currentFocusEdit = getActivity().getCurrentFocus();
        }
        View view = this.currentFocusEdit;
        if (view == null) {
            return;
        }
        if (view instanceof android.widget.EditText) {
            setEditText((EditText) view);
        } else if (view instanceof EditText) {
            setEditText((EditText) view);
        }
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void setSelectedFontSizeChange(int i6) {
        EditText editText = (EditText) this.mainActivity.getCurrentFocus();
        if (editText == null) {
            return;
        }
        Editable editableText = editText.getEditableText();
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        if (selectionEnd <= selectionStart) {
            int i7 = 0;
            while (true) {
                if (i7 >= this.baseEntrys.size()) {
                    break;
                }
                BaseEditView baseEditView = this.baseEntrys.get(i7);
                if (baseEditView.getmEditText().getSelectionEnd() > baseEditView.getmEditText().getSelectionStart()) {
                    selectionStart = baseEditView.getmEditText().getSelectionStart();
                    selectionEnd = baseEditView.getmEditText().getSelectionEnd();
                    editText = baseEditView.getmEditText();
                    break;
                }
                i7++;
            }
        }
        EditText editText2 = editText;
        int i8 = selectionStart;
        int i9 = selectionEnd;
        if (i9 > i8 && editableText != null) {
            for (AbsoluteSizeSpan absoluteSizeSpan : editableText.getSpans(i8, i9, AbsoluteSizeSpan.class)) {
                if (absoluteSizeSpan != null) {
                    int spanStart = editableText.getSpanStart(absoluteSizeSpan);
                    int spanEnd = editableText.getSpanEnd(absoluteSizeSpan);
                    if (i8 == spanStart && spanEnd == i9) {
                        editableText.removeSpan(absoluteSizeSpan);
                    } else if (spanStart <= i8 && spanEnd >= i9) {
                        AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan(absoluteSizeSpan.getSize());
                        AbsoluteSizeSpan absoluteSizeSpan3 = new AbsoluteSizeSpan(absoluteSizeSpan.getSize());
                        editableText.removeSpan(absoluteSizeSpan);
                        if (spanStart != i8) {
                            editableText.setSpan(absoluteSizeSpan2, spanStart, i8, 33);
                        }
                        if (spanEnd != i9) {
                            editableText.setSpan(absoluteSizeSpan3, i9, spanEnd, 33);
                        }
                    }
                }
            }
            setAbsoluteFontSize(editText2, i8, i9, ScreenUtils.dpToPx(i6), true);
            this.isBackOrSave = true;
            this.fontStyleChange = true;
        }
        setSaveIconColor();
        setEditRich("fontSize");
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_h1");
    }

    public void setStickyColor(int i6, String str, int i7, NoteBgBean noteBgBean) {
        if (noteBgBean == null) {
            noteBgBean = ResNoteBgManager.getInstance().getNoteBg(str, i6, i7);
        }
        this.noteBgView.setNoteBg(noteBgBean);
        if (noteBgBean.getLine() == null || !noteBgBean.getLine().isFollowEdit()) {
            drawLines(false, null);
        } else {
            this.fragment_layout.postDelayed(new Runnable() { // from class: b6.e2
                @Override // java.lang.Runnable
                public void run() {
                    DetailFragmentNew.this.lambda$setStickyColor$55();
                }
            }, 300L);
            drawLines(true, noteBgBean.getLine());
        }
        setDarkModeView(noteBgBean.isDarkBg());
        if (this.content.isDrawLine()) {
            return;
        }
        resetLayoutLineBgState();
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void showColorDialog(int i6) {
        this.editAction.append("_T");
        KeyboardUtils.hideKeyboard(this.mBottomBar);
        if (i6 == 0) {
            i6 = this.noteTmp.getBgId();
        }
        String stickyColor = i6 == 10 ? this.noteTmp.getStickyColor() : "";
        try {
            NoteBgDialogFragment noteBgDialogFragment = new NoteBgDialogFragment(this.mainActivity);
            this.mNoteBgDialogFragment = noteBgDialogFragment;
            noteBgDialogFragment.setSelectBg(i6, stickyColor, this.mLastNoteBgTabSelectIndex);
            this.mNoteBgDialogFragment.setListener(this);
            this.mNoteBgDialogFragment.show(getChildFragmentManager(), "312");
        } catch (Exception unused) {
        }
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.chooseDarkListener
    public void showDarkTheme(int i6, int i7) {
        int i8;
        DeviceUtils.getCCODE(App.getAppContext());
        if (i7 == 1) {
            i8 = ConstantsColorBg.paperBackground.get(i6).intValue();
            colorSelected(ResNoteBgManager.getInstance().getNoteBg(i8), -1);
        } else if (i7 == 0) {
            i8 = ConstantsColorBg.DARK_THEME.get(i6).intValue();
            colorSelected(ResNoteBgManager.getInstance().getNoteBg(i8), -1);
        } else if (i7 == 2) {
            i8 = ConstantsColorBg.schoolBackground.get(i6).intValue();
            colorSelected(ResNoteBgManager.getInstance().getNoteBg(i8), -1);
        } else if (i7 == 3) {
            i8 = ConstantsColorBg.shoppingBackground.get(i6).intValue();
            colorSelected(ResNoteBgManager.getInstance().getNoteBg(i8), -1);
        } else if (i7 == 4) {
            i8 = ConstantsColorBg.landScapeBackground.get(i6).intValue();
            colorSelected(ResNoteBgManager.getInstance().getNoteBg(i8), -1);
        } else {
            i8 = 0;
        }
        this.dialogType = i7;
        showColorDialog(i8);
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.AudioListener
    public void speechToText() {
        EditText editText;
        boolean z6 = false;
        if (!DeviceUtils.verifyAudioPermissions(this, 1004)) {
            Toast.makeText(App.getAppContext(), R.string.no_audio_permission, 0).show();
            return;
        }
        InputHelperView inputHelperView = this.mBottomBar;
        if (baseSttHasFocus() || ((editText = this.content) != null && editText.hasFocus())) {
            z6 = true;
        }
        inputHelperView.showBottomSpeechDialog(z6);
    }

    public void startGetContentAction() {
        Matisse.from(this).choose(MimeType.ofImage(), false).countable(true).maxSelectable(9).gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size)).thumbnailScale(0.85f).imageEngine(new GlideEngine()).setOnSelectedListener(new OnSelectedListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.27
            AnonymousClass27() {
            }

            @Override // com.zhihu.matisse.listener.OnSelectedListener
            public void onTypeErrorSelected(Item item) {
                FirebaseReportUtils.getInstance().reportNew("matisse_error_file_type", "pr_status", EasyNoteManager.getInstance().getItemType(DetailFragmentNew.this.getActivity(), item) + "");
            }

            @Override // com.zhihu.matisse.listener.OnSelectedListener
            public void onSelected(List<Uri> list, List<String> list2) {
            }
        }).showSingleMediaType(true).originalEnable(true).autoHideToolbarOnSingleTap(false).forResult(101);
    }

    public void startGetSingleContentAction() {
        Matisse.from(this).choose(MimeType.ofImage(), false).countable(true).maxSelectable(1).gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size)).thumbnailScale(0.85f).imageEngine(new GlideEngine()).setOnSelectedListener(new OnSelectedListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.26
            AnonymousClass26() {
            }

            @Override // com.zhihu.matisse.listener.OnSelectedListener
            public void onTypeErrorSelected(Item item) {
                FirebaseReportUtils.getInstance().reportNew("matisse_error_file_type", "pr_status", EasyNoteManager.getInstance().getItemType(DetailFragmentNew.this.getActivity(), item) + "");
            }

            @Override // com.zhihu.matisse.listener.OnSelectedListener
            public void onSelected(List<Uri> list, List<String> list2) {
            }
        }).showSingleMediaType(true).originalEnable(true).forResult(102);
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void strikethrough(boolean z6) {
        EditText editText = (EditText) this.mainActivity.getCurrentFocus();
        if (editText == null) {
            return;
        }
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        Editable editableText = editText.getEditableText();
        if (selectionEnd > selectionStart) {
            setTextStrikethrough(editableText, z6, selectionStart, selectionEnd);
            this.isBackOrSave = true;
            this.fontStyleChange = true;
            setSaveIconColor();
        }
        setEditRich("");
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_strikethrough");
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void underLine(boolean z6) {
        EditText editText = (EditText) this.mainActivity.getCurrentFocus();
        if (editText == null) {
            return;
        }
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        Editable editableText = editText.getEditableText();
        if (selectionEnd > selectionStart) {
            setTextUnderLine(editableText, z6, selectionStart, selectionEnd);
            this.isBackOrSave = true;
            this.fontStyleChange = true;
            setSaveIconColor();
        }
        setEditRich("");
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_underline");
    }

    @Override // notes.easy.android.mynotes.models.adapters.FontColorAdadpter.FontColorListener
    public void updateFontColor(String str, int i6) {
        if (i6 == 1) {
            if (!TextUtils.isEmpty(str)) {
                this.mCurrentFontColor = str;
                FirebaseReportUtils.getInstance().reportNew("edit_tool_font_color_click");
            }
            if (this.fontColorUnder != null) {
                if (str.startsWith(Constants.SPECIAL_CHARACTOR)) {
                    this.fontColorUnder.setBackgroundColor(Color.parseColor(this.mCurrentFontColor));
                } else {
                    try {
                        this.fontColorUnder.setBackgroundColor(Integer.parseInt(str));
                    } catch (Exception unused) {
                    }
                }
            }
            Bundle bundle = new Bundle();
            bundle.putString(Constants.THEME_EVENT_KEY, str);
            DialogAddCategory dialogAddCategory = DialogAddCategory.INSTANCE;
            dialogAddCategory.disMissFontColorDialog();
            dialogAddCategory.disMissFontPickerColorDialog();
            FirebaseReportUtils.getInstance().reportNew("edit_tool_font_color_select", bundle);
        } else {
            EditText editText = (EditText) this.mainActivity.getCurrentFocus();
            Editable editableText = editText.getEditableText();
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();
            if (selectionEnd <= selectionStart && selectionStart != 0) {
                int i7 = 0;
                while (true) {
                    if (i7 >= this.baseEntrys.size()) {
                        break;
                    }
                    BaseEditView baseEditView = this.baseEntrys.get(i7);
                    if (baseEditView.getmEditText().getSelectionEnd() > baseEditView.getmEditText().getSelectionStart()) {
                        int selectionStart2 = baseEditView.getmEditText().getSelectionStart();
                        int selectionEnd2 = baseEditView.getmEditText().getSelectionEnd();
                        selectionStart = selectionStart2;
                        editText = baseEditView.getmEditText();
                        selectionEnd = selectionEnd2;
                        break;
                    }
                    i7++;
                }
            }
            if (selectionEnd > selectionStart) {
                for (ForegroundColorSpan foregroundColorSpan : editText.getText().getSpans(selectionStart, selectionEnd, ForegroundColorSpan.class)) {
                    if (foregroundColorSpan != null) {
                        int spanStart = editableText.getSpanStart(foregroundColorSpan);
                        int spanEnd = editableText.getSpanEnd(foregroundColorSpan);
                        if (selectionStart == spanStart && spanEnd == selectionEnd) {
                            editableText.removeSpan(foregroundColorSpan);
                        } else if (spanStart <= selectionStart && spanEnd >= selectionEnd) {
                            editableText.removeSpan(foregroundColorSpan);
                            ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(foregroundColorSpan.getForegroundColor());
                            ForegroundColorSpan foregroundColorSpan3 = new ForegroundColorSpan(foregroundColorSpan.getForegroundColor());
                            if (spanStart != selectionStart) {
                                editableText.setSpan(foregroundColorSpan2, spanStart, selectionStart, 33);
                            }
                            if (spanEnd != selectionEnd) {
                                editableText.setSpan(foregroundColorSpan3, selectionEnd, spanEnd, 33);
                            }
                        }
                    }
                }
                if (str.startsWith(Constants.SPECIAL_CHARACTOR)) {
                    setTextFontColor(editText, selectionStart, selectionEnd, Color.parseColor(str), true);
                } else {
                    try {
                        setTextFontColor(editText, selectionStart, selectionEnd, Integer.parseInt(str), true);
                    } catch (Exception unused2) {
                    }
                }
                this.isBackOrSave = true;
                this.fontStyleChange = true;
            }
            InputHelperView inputHelperView = this.mBottomBar;
            if (inputHelperView != null) {
                inputHelperView.setFontColorDisplayColor(str);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString(Constants.THEME_EVENT_KEY, str);
            FirebaseReportUtils.getInstance().reportNew("edit_select_font_color_select", bundle2);
            setEditRich("");
        }
        setSaveIconColor();
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void updateSpeechResult(String str) {
        int selectionStart;
        EditText focusView = getFocusView();
        if (focusView == null || TextUtils.isEmpty(str) || (selectionStart = focusView.getSelectionStart()) < 0 || focusView.getText() == null) {
            return;
        }
        focusView.getText().insert(selectionStart, str);
    }

    @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
    public void updateURState(boolean z6, boolean z7) {
        onCanDo(z6, z7);
    }

    public void useCheckList() {
        EditContentAdapter editContentAdapter = this.contentAdapter;
        if (editContentAdapter == null || editContentAdapter.getEditTextHashMap() == null || this.contentAdapter.getEditTextHashMap().get(Integer.valueOf(this.contentAdapter.getSelectedEditText())) == null) {
            return;
        }
        EditText editText = this.contentAdapter.getEditTextHashMap().get(Integer.valueOf(this.contentAdapter.getSelectedEditText())).hasFocus() ? this.contentAdapter.getEditTextHashMap().get(Integer.valueOf(this.contentAdapter.getSelectedEditText())) : null;
        Editable editableText = editText.getEditableText();
        if (editableText == null) {
            return;
        }
        MyBulletSpan myBulletSpan = new MyBulletSpan(editText, "checkList_no", 0, 0);
        String[] split = editableText.toString().split("\n");
        for (int i6 = 0; i6 <= split.length && i6 != getCurrentCursorLine(editText); i6++) {
            split[i6].length();
        }
        editableText.setSpan(myBulletSpan, 0, 1, 18);
        editableText.setSpan(myBulletSpan.myImageSpan, 0, 1, 33);
    }

    @Override // com.myview.android.checklistview.interfaces.CheckListChangedListener, notes.easy.android.mynotes.edit.view.BaseEditView.PicClicklistener
    public void afterTextChanged() {
        showSavingView();
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void bottomEmoji(int i6) {
        KeyboardUtils.hideKeyboard(this.mBottomBar);
        this.dialogEmojiFragment = null;
        DialogEmojiFragment dialogEmojiFragment = new DialogEmojiFragment(this.mainActivity, new AddEmojiInterface() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.43
            AnonymousClass43() {
            }

            @Override // notes.easy.android.mynotes.view.AddEmojiInterface
            public void selectEmoji(EmojiBean emojiBean, boolean z6) {
                Bundle bundle = new Bundle();
                bundle.putString("emoji_key", emojiBean.getUnicode());
                FirebaseReportUtils.getInstance().reportAll("edit_tool_emoji_insert", bundle);
                Bundle bundle2 = new Bundle();
                bundle2.putString("emoji_key_newuser", emojiBean.getUnicode());
                FirebaseReportUtils.getInstance().reportAll("edit_tool_emoji_insert", bundle2);
                if (DetailFragmentNew.this.title.hasFocus()) {
                    Editable editableText = DetailFragmentNew.this.title.getEditableText();
                    int selectionStart = DetailFragmentNew.this.title.getSelectionStart();
                    if (z6) {
                        editableText.insert(selectionStart, emojiBean.createEmojiSpan());
                    } else if (!TextUtils.isEmpty(emojiBean.getUnicode())) {
                        editableText.insert(selectionStart, emojiBean.getUnicode());
                    }
                } else {
                    EditText editText = (EditText) DetailFragmentNew.this.mainActivity.getCurrentFocus();
                    if (editText == null) {
                        return;
                    }
                    DetailFragmentNew.this.insertEmoji(z6, editText.getEditableText(), editText.getSelectionStart(), emojiBean);
                }
                if (App.userConfig.getEmojiUsed()) {
                    return;
                }
                App.userConfig.setEmojiUsed(true);
                DetailFragmentNew.this.reportFunctionUse();
            }

            @Override // notes.easy.android.mynotes.view.AddEmojiInterface
            public void emojiDialogDismiss() {
            }
        });
        this.dialogEmojiFragment = dialogEmojiFragment;
        dialogEmojiFragment.setCurrentItem(i6);
        this.dialogEmojiFragment.show(getChildFragmentManager(), "226");
    }

    public void resetSearchedText(boolean z6) {
        if (this.searchEdit.getText().length() > 0) {
            this.searchEdit.setText("");
        }
        if (z6) {
            if (this.contentSearched != null) {
                JsonArray asJsonArray = new JsonParser().parse(this.contentSearched).getAsJsonArray();
                this.contentBeanList.clear();
                Iterator<JsonElement> it2 = asJsonArray.iterator();
                while (it2.hasNext()) {
                    this.contentBeanList.add((EditContentBean) this.gson.fromJson(it2.next(), EditContentBean.class));
                }
                this.contentAdapter.setSearchText("");
                notifyData();
                return;
            }
            return;
        }
        if (this.searchEdit.hasFocus()) {
            this.searchEdit.clearFocus();
            KeyboardUtils.hideKeyboard(this.searchEdit);
        }
        this.mainActivity.setStatusBarColor(true);
        this.searchLayout.setVisibility(View.GONE);
        if (this.searchedEditList.size() > 0) {
            for (int i6 = 0; i6 < this.searchedEditList.size(); i6++) {
                EditText editText = this.searchedEditList.get(i6);
                String[] split = this.searchEditIndex.get(i6).split(",");
                if (split.length == 2) {
                    BackgroundColorSpan[] backgroundColorSpanArr = editText.getText().getSpans(Integer.parseInt(split[0]), Integer.parseInt(split[1]), BackgroundColorSpan.class);
                    int length = backgroundColorSpanArr.length;
                    int i7 = 0;
                    while (true) {
                        if (i7 < length) {
                            BackgroundColorSpan backgroundColorSpan = backgroundColorSpanArr[i7];
                            if (backgroundColorSpan.getBackgroundColor() == Color.parseColor(Constants.HIGHLIGHT_COLOR)) {
                                editText.getText().removeSpan(backgroundColorSpan);
                                break;
                            }
                            i7++;
                        }
                    }
                }
            }
        }
        this.searchedEditList.clear();
        this.searchEditIndex.clear();
        this.needSearch = false;
    }

    private void setEditText(EditText editText) {
        editText.setFocusable(this.isEdit);
        editText.setCursorVisible(this.isEdit);
        editText.setFocusableInTouchMode(this.isEdit);
    }

    @SuppressLint({"NewApi"})
    private void toggleChecklist2(boolean z6, boolean z7) {
        ChecklistManager checklistManager = this.mChecklistManager;
        if (checklistManager == null) {
            checklistManager = new ChecklistManager(this.mainActivity);
        }
        this.mChecklistManager = checklistManager;
        this.mChecklistManager.showCheckMarks(z7).newEntryHint(StringUtils.SPACE).keepChecked(z6).moveCheckedOnBottom(App.userConfig.getCheckSort() ? 1 : 0);
        this.mChecklistManager.addTextChangedListener(this.mFragment);
        this.mChecklistManager.setCheckListChangedListener(this.mFragment);
        View convert = this.mChecklistManager.convert(this.toggleChecklistView);
        if (convert != null) {
            this.mChecklistManager.replaceViews(this.toggleChecklistView, convert);
            this.toggleChecklistView = convert;
            if (TextUtils.isEmpty(this.noteTmp.getContent())) {
                this.toggleChecklistView.requestFocus();
            }
            ViewCompat.animate(this.toggleChecklistView).alpha(1.0f).scaleXBy(0.0f).scaleX(1.0f).scaleYBy(0.0f).scaleY(1.0f);
            this.noteTmp.setChecklist(Boolean.valueOf(!r4.isChecklist().booleanValue()));
        }
        if (z7) {
            this.add_Layout.setVisibility(View.VISIBLE);
            initCheckListCustomEmoji(this.mChecklistManager);
        } else {
            this.toggleChecklistView.setOnFocusChangeListener(this.onFocusChangeListener);
            this.add_Layout.setVisibility(View.GONE);
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$15 */
    class AnonymousClass15 implements PicGridAdapter.OnListCallback {
        AnonymousClass15() {
        }

        @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
        public void onItemClick(View view, Attachment attachment2, int i9) {
            if (attachment2 == null) {
                return;
            }
            Uri shareableUri = FileProviderHelper.getShareableUri(attachment2);
            if (ConstantsBase.MIME_TYPE_FILES.equals(attachment2.getMime_type())) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(shareableUri, StorageHelper.getMimeType(DetailFragmentNew.this.mainActivity, shareableUri));
                intent.addFlags(3);
                if (IntentChecker.isAvailable(DetailFragmentNew.this.mainActivity.getApplicationContext(), intent, null)) {
                    DetailFragmentNew.this.startActivity(intent);
                    return;
                }
                return;
            }
            if ("image/jpeg".equals(attachment2.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment2.getMime_type()) || "video/mp4".equals(attachment2.getMime_type())) {
                GalleryActivity.transforNote = DetailFragmentNew.this.noteTmp;
                ArrayList arrayList = new ArrayList();
                int i10 = 0;
                for (Attachment attachment3 : DetailFragmentNew.this.noteTmp.getAttachmentsList()) {
                    if ("image/jpeg".equals(attachment3.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment3.getMime_type()) || "video/mp4".equals(attachment3.getMime_type())) {
                        arrayList.add(attachment2);
                        if (attachment3.equals(attachment2)) {
                            i10 = arrayList.size() - 1;
                        }
                    }
                }
                try {
                    Intent intent2 = new Intent(DetailFragmentNew.this.mainActivity, GalleryActivity.class);
                    intent2.putExtra(ConstantsBase.GALLERY_CLICKED_IMAGE, i10);
                    DetailFragmentNew.this.mainActivity.startActivityForResult(intent2, Constants.DETAIL_TO_GALLERY);
                    FirebaseReportUtils.getInstance().reportNew("edit_tool_pic_preview");
                } catch (Exception unused2) {
                }
            }
        }

        @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
        public void onItemDragFinished(List<Attachment> list2, int i9, int i10) {
            int i11 = 0;
            while (i11 < list2.size()) {
                Attachment attachment2 = list2.get(i11);
                i11++;
                attachment2.setSort(i11);
            }
            for (int i12 = 0; i12 < DetailFragmentNew.this.imageList.size(); i12++) {
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.noteTmp.removeAttachment((Attachment) detailFragmentNew.imageList.get(i12));
            }
            DetailFragmentNew.this.imageList = list2;
            for (int i13 = 0; i13 < DetailFragmentNew.this.imageList.size(); i13++) {
                DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                detailFragmentNew2.noteTmp.addAttachment((Attachment) detailFragmentNew2.imageList.get(i13));
            }
            DetailFragmentNew.this.timeModified = true;
            DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
            detailFragmentNew3.saveNote(new a2(detailFragmentNew3), false);
        }

        @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
        public void onItemLongClick() {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$30 */
    class AnonymousClass30 implements DialogAddCategory.vipConfiremListener {
        AnonymousClass30() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
        public void bugVipNow(boolean z6) {
            VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "custom_bg");
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
        public void abandonVip() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
        public void watchAdsNow() {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$34 */
    class AnonymousClass34 implements DialogAddCategory.OnLockingInterface {
        AnonymousClass34() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
        public void clickUpgradeVip() {
            FirebaseReportUtils.getInstance().reportNew("lock_promote_click");
            VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "lock_note");
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
        public void clickTryItOnce() {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$36 */
    class AnonymousClass36 implements DialogAddCategory.OnLockingInterface {
        AnonymousClass36() {
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
        public void clickUpgradeVip() {
            VipDiscountUtil.jumpToVipPage(DetailFragmentNew.this.mainActivity, "lock_note");
        }

        @Override // notes.easy.android.mynotes.view.DialogAddCategory.OnLockingInterface
        public void clickTryItOnce() {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$43 */
    class AnonymousClass43 implements AddEmojiInterface {
        AnonymousClass43() {
        }

        @Override // notes.easy.android.mynotes.view.AddEmojiInterface
        public void selectEmoji(EmojiBean emojiBean, boolean z6) {
            Bundle bundle = new Bundle();
            bundle.putString("emoji_key", emojiBean.getUnicode());
            FirebaseReportUtils.getInstance().reportAll("edit_tool_emoji_insert", bundle);
            Bundle bundle2 = new Bundle();
            bundle2.putString("emoji_key_newuser", emojiBean.getUnicode());
            FirebaseReportUtils.getInstance().reportAll("edit_tool_emoji_insert", bundle2);
            if (DetailFragmentNew.this.title.hasFocus()) {
                Editable editableText = DetailFragmentNew.this.title.getEditableText();
                int selectionStart = DetailFragmentNew.this.title.getSelectionStart();
                if (z6) {
                    editableText.insert(selectionStart, emojiBean.createEmojiSpan());
                } else if (!TextUtils.isEmpty(emojiBean.getUnicode())) {
                    editableText.insert(selectionStart, emojiBean.getUnicode());
                }
            } else {
                EditText editText = (EditText) DetailFragmentNew.this.mainActivity.getCurrentFocus();
                if (editText == null) {
                    return;
                }
                DetailFragmentNew.this.insertEmoji(z6, editText.getEditableText(), editText.getSelectionStart(), emojiBean);
            }
            if (App.userConfig.getEmojiUsed()) {
                return;
            }
            App.userConfig.setEmojiUsed(true);
            DetailFragmentNew.this.reportFunctionUse();
        }

        @Override // notes.easy.android.mynotes.view.AddEmojiInterface
        public void emojiDialogDismiss() {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$42 */
    class AnonymousClass42 implements NoteFontAdapter.OnListCallback {
        final /* synthetic */ RecyclerView val$fontRecyclerView;
        final /* synthetic */ NoteFontBean val$lastNoteFontSelect;

        AnonymousClass42(NoteFontBean noteFontBean2, RecyclerView recyclerView32) {
            r2 = noteFontBean2;
            r3 = recyclerView32;
        }

        @Override // notes.easy.android.mynotes.ui.adapters.NoteFontAdapter.OnListCallback
        public void onClick(View view, NoteFontAdapter.NoteFontViewHolder noteFontViewHolder, NoteFontBean noteFontBean2, int i102) {
            if (DetailFragmentNew.this.getActivity() == null || DetailFragmentNew.this.getActivity().isFinishing()) {
                return;
            }
            NoteFontBean copy2 = noteFontBean2.copy();
            r2.copy(copy2);
            if (noteFontBean2.isFontReady()) {
                DetailFragmentNew.this.currentFontBean = copy2;
                DetailFragmentNew.this.currentFontName = copy2.getFontName();
                DetailFragmentNew.this.noteTmp.setFontName(copy2.getFontName());
                DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
                detailFragmentNew.isBackOrSave = true;
                detailFragmentNew.setFontTypeface(detailFragmentNew.currentFontName);
                DetailFragmentNew detailFragmentNew2 = DetailFragmentNew.this;
                detailFragmentNew2.saveNote(detailFragmentNew2, true);
                return;
            }
            if (!NetworkUtils.isNetworkConnected(App.getAppContext())) {
                noteFontViewHolder.download.setVisibility(View.VISIBLE);
                noteFontViewHolder.progress.setVisibility(View.GONE);
                Toast.makeText(App.getAppContext(), R.string.no_network_error, 0).show();
            } else {
                noteFontViewHolder.download.setVisibility(View.GONE);
                noteFontViewHolder.progress.setVisibility(View.VISIBLE);
                Toast.makeText(App.getAppContext(), R.string.downloading_toast, 0).show();
                ResNoteFontManager.getInstance().startDownloadFont(noteFontBean2, new ResNoteFontManager.FontDownloadListener() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.42.1
                    final /* synthetic */ NoteFontBean val$fontBean;
                    final /* synthetic */ int val$position;

                    AnonymousClass1(int i1022, NoteFontBean copy22) {
                        r2 = i1022;
                        r3 = copy22;
                    }

                    @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                    public void onDownloadFailed(NoteFontBean noteFontBean3) {
                        if (r3 != null) {
                            Toast.makeText(App.getAppContext(), R.string.download_error_toast, 0).show();
                            RecyclerView.ViewHolder findViewHolderForItemId = r3.findViewHolderForItemId(r2);
                            if (findViewHolderForItemId == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                                return;
                            }
                            NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                            noteFontViewHolder2.progress.setProgress(0);
                            noteFontViewHolder2.progress.setVisibility(View.GONE);
                            noteFontViewHolder2.download.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                    public void onDownloadSuccess(NoteFontBean noteFontBean3, String str2) {
                        RecyclerView.ViewHolder findViewHolderForItemId;
                        RecyclerView recyclerView4 = r3;
                        if (recyclerView4 != null && (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) != null && (findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                            NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                            noteFontViewHolder2.progress.setProgress(0);
                            noteFontViewHolder2.progress.setVisibility(View.GONE);
                            noteFontViewHolder2.download.setVisibility(View.GONE);
                        }
                        if (r2 == null || !TextUtils.equals(noteFontBean3.getFontName(), r2.getFontName())) {
                            return;
                        }
                        DetailFragmentNew.this.currentFontBean = r3;
                        DetailFragmentNew.this.currentFontName = r3.getFontName();
                        DetailFragmentNew.this.noteTmp.setFontName(r3.getFontName());
                        DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
                        detailFragmentNew3.isBackOrSave = true;
                        detailFragmentNew3.setFontTypeface(detailFragmentNew3.currentFontName);
                        DetailFragmentNew detailFragmentNew4 = DetailFragmentNew.this;
                        detailFragmentNew4.saveNote(detailFragmentNew4, true);
                    }

                    @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                    public void updateDownloadProgress(NoteFontBean noteFontBean3, long j6, float f6, float f7) {
                        RecyclerView.ViewHolder findViewHolderForItemId;
                        RecyclerView recyclerView4 = r3;
                        if (recyclerView4 == null || (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                            return;
                        }
                        NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                        noteFontViewHolder2.progress.setVisibility(View.VISIBLE);
                        noteFontViewHolder2.progress.setProgress(ResNoteFontManager.getInstance().mDownloadingProgress.get(noteFontBean3.getFontName()).intValue());
                    }

                    @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
                    public void onDownloadStart(NoteFontBean noteFontBean3) {
                    }
                });
            }
        }

        /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$42$1 */
        class AnonymousClass1 implements ResNoteFontManager.FontDownloadListener {
            final /* synthetic */ NoteFontBean val$fontBean;
            final /* synthetic */ int val$position;

            AnonymousClass1(int i1022, NoteFontBean copy22) {
                r2 = i1022;
                r3 = copy22;
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
            public void onDownloadFailed(NoteFontBean noteFontBean3) {
                if (r3 != null) {
                    Toast.makeText(App.getAppContext(), R.string.download_error_toast, 0).show();
                    RecyclerView.ViewHolder findViewHolderForItemId = r3.findViewHolderForItemId(r2);
                    if (findViewHolderForItemId == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                        return;
                    }
                    NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                    noteFontViewHolder2.progress.setProgress(0);
                    noteFontViewHolder2.progress.setVisibility(View.GONE);
                    noteFontViewHolder2.download.setVisibility(View.VISIBLE);
                }
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
            public void onDownloadSuccess(NoteFontBean noteFontBean3, String str2) {
                RecyclerView.ViewHolder findViewHolderForItemId;
                RecyclerView recyclerView4 = r3;
                if (recyclerView4 != null && (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) != null && (findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                    NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                    noteFontViewHolder2.progress.setProgress(0);
                    noteFontViewHolder2.progress.setVisibility(View.GONE);
                    noteFontViewHolder2.download.setVisibility(View.GONE);
                }
                if (r2 == null || !TextUtils.equals(noteFontBean3.getFontName(), r2.getFontName())) {
                    return;
                }
                DetailFragmentNew.this.currentFontBean = r3;
                DetailFragmentNew.this.currentFontName = r3.getFontName();
                DetailFragmentNew.this.noteTmp.setFontName(r3.getFontName());
                DetailFragmentNew detailFragmentNew3 = DetailFragmentNew.this;
                detailFragmentNew3.isBackOrSave = true;
                detailFragmentNew3.setFontTypeface(detailFragmentNew3.currentFontName);
                DetailFragmentNew detailFragmentNew4 = DetailFragmentNew.this;
                detailFragmentNew4.saveNote(detailFragmentNew4, true);
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
            public void updateDownloadProgress(NoteFontBean noteFontBean3, long j6, float f6, float f7) {
                RecyclerView.ViewHolder findViewHolderForItemId;
                RecyclerView recyclerView4 = r3;
                if (recyclerView4 == null || (findViewHolderForItemId = recyclerView4.findViewHolderForItemId(r2)) == null || !(findViewHolderForItemId instanceof NoteFontAdapter.NoteFontViewHolder)) {
                    return;
                }
                NoteFontAdapter.NoteFontViewHolder noteFontViewHolder2 = (NoteFontAdapter.NoteFontViewHolder) findViewHolderForItemId;
                noteFontViewHolder2.progress.setVisibility(View.VISIBLE);
                noteFontViewHolder2.progress.setProgress(ResNoteFontManager.getInstance().mDownloadingProgress.get(noteFontBean3.getFontName()).intValue());
            }

            @Override // notes.easy.android.mynotes.utils.ResNoteFontManager.FontDownloadListener
            public void onDownloadStart(NoteFontBean noteFontBean3) {
            }
        }
    }

    @Override // notes.easy.android.mynotes.view.InputHelperView.ChooseInterface
    public void affix() {
    }

    @Override // notes.easy.android.mynotes.view.DialogAddCategory.vipConfiremListener
    public void watchAdsNow() {
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$16 */
    class AnonymousClass16 implements RecordGridAdapter.RecordGridActionInterface {

        /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$16$1 */
        class AnonymousClass1 implements DialogAddCategory.Positive1Listener<String> {
            final /* synthetic */ Attachment val$attachment;

            AnonymousClass1(Attachment attachment2) {
                r2 = attachment2;
            }

            @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
            public void positiveClick(String str) {
                DetailFragmentNew.this.deleteRecording(r2);
                Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.delete_success, 0).show();
            }
        }

        AnonymousClass16() {
        }

        @Override // notes.easy.android.mynotes.models.adapters.RecordGridAdapter.RecordGridActionInterface
        public void closeImgClick(Attachment attachment2) {
            FirebaseReportUtils.getInstance().reportNew("record_in_notes_delete_show");
            DialogAddCategory.INSTANCE.showOneTitleDialog(DetailFragmentNew.this.mainActivity, R.string.delete_recording, R.string.delete, R.string.cancel, new DialogAddCategory.Positive1Listener<String>() { // from class: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew.16.1
                final /* synthetic */ Attachment val$attachment;

                AnonymousClass1(Attachment attachment22) {
                    r2 = attachment22;
                }

                @Override // notes.easy.android.mynotes.view.DialogAddCategory.Positive1Listener
                public void positiveClick(String str) {
                    DetailFragmentNew.this.deleteRecording(r2);
                    Toast.makeText(DetailFragmentNew.this.mainActivity, R.string.delete_success, 0).show();
                }
            }, null);
        }

        @Override // notes.easy.android.mynotes.models.adapters.RecordGridAdapter.RecordGridActionInterface
        public void playImgClick(Uri uri) {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$19 */
    class AnonymousClass19 implements IAdLoadListener {
        AnonymousClass19() {
        }

        @Override // src.ad.adapters.IAdLoadListener
        public void onAdClicked(IAdAdapter iAdAdapter) {
            FirebaseReportUtils.getInstance().adClickReport(Constants.EDIT_SAVE_INTERS);
        }

        @Override // src.ad.adapters.IAdLoadListener
        public void onAdClosed(IAdAdapter iAdAdapter) {
        }

        @Override // src.ad.adapters.IAdLoadListener
        public void onAdLoaded(IAdAdapter iAdAdapter) {
        }

        @Override // src.ad.adapters.IAdLoadListener
        public void onError(String str) {
        }

        @Override // src.ad.adapters.IAdLoadListener
        public void onOpenAdDismiss(IAdAdapter iAdAdapter) {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$24 */
    class AnonymousClass24 implements ResNoteBgManager.BgDownloadListener {
        AnonymousClass24() {
        }

        @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
        public void onDownloadFailed(NoteBgBean noteBgBean) {
            if (DetailFragmentNew.this.isLoadingDialog()) {
                DetailFragmentNew.this.exportPdfDirectly();
                if (DetailFragmentNew.this.getActivity() != null) {
                    DetailFragmentNew.this.hideLoadingDialog();
                }
            }
        }

        @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
        public void onDownloadSuccess(NoteBgBean noteBgBean, String str) {
            if (DetailFragmentNew.this.isLoadingDialog()) {
                DetailFragmentNew.this.exportPdfDirectly();
                if (DetailFragmentNew.this.getActivity() != null) {
                    DetailFragmentNew.this.hideLoadingDialog();
                }
            }
        }

        @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
        public void onDownloadStart(NoteBgBean noteBgBean) {
        }

        @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
        public void updateDownloadProgress(NoteBgBean noteBgBean, long j6, float f6, float f7) {
        }
    }

    public void hasRedo(EditText editText) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
    }

    @Override // notes.easy.android.mynotes.view.AddCategoryInterface
    public void sortSelectd(int i6) {
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$26 */
    class AnonymousClass26 implements OnSelectedListener {
        AnonymousClass26() {
        }

        @Override // com.zhihu.matisse.listener.OnSelectedListener
        public void onTypeErrorSelected(Item item) {
            FirebaseReportUtils.getInstance().reportNew("matisse_error_file_type", "pr_status", EasyNoteManager.getInstance().getItemType(DetailFragmentNew.this.getActivity(), item) + "");
        }

        @Override // com.zhihu.matisse.listener.OnSelectedListener
        public void onSelected(List<Uri> list, List<String> list2) {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$27 */
    class AnonymousClass27 implements OnSelectedListener {
        AnonymousClass27() {
        }

        @Override // com.zhihu.matisse.listener.OnSelectedListener
        public void onTypeErrorSelected(Item item) {
            FirebaseReportUtils.getInstance().reportNew("matisse_error_file_type", "pr_status", EasyNoteManager.getInstance().getItemType(DetailFragmentNew.this.getActivity(), item) + "");
        }

        @Override // com.zhihu.matisse.listener.OnSelectedListener
        public void onSelected(List<Uri> list, List<String> list2) {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$10 */
    class AnonymousClass10 implements TextWatcher {
        AnonymousClass10() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() > 0) {
                DetailFragmentNew.this.searchButton.setText(R.string.search);
                DetailFragmentNew.this.searchDelete.setVisibility(View.VISIBLE);
                DetailFragmentNew.this.needSearch = true;
            } else {
                DetailFragmentNew.this.searchButton.setText(R.string.cancel);
                DetailFragmentNew.this.needSearch = false;
                DetailFragmentNew.this.resetSearchedText(true);
            }
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
        }
    }

    /* renamed from: notes.easy.android.mynotes.ui.fragments.DetailFragmentNew$17 */
    class AnonymousClass17 implements TextWatcher {
        AnonymousClass17() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            DetailFragmentNew.this.setSaveIconColor();
            DetailFragmentNew detailFragmentNew = DetailFragmentNew.this;
            detailFragmentNew.isBackOrSave = true;
            detailFragmentNew.showSavingView();
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
    }
}
