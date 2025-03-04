package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.utils.NumListEntry;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import java.util.List;
import java.util.concurrent.TimeUnit;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.constant.Constants;
import notes.easy.android.mynotes.constant.UserConfig;
import notes.easy.android.mynotes.firebase.FirebaseReportUtils;
import notes.easy.android.mynotes.models.adapters.BottomToolsAdadpter;
import notes.easy.android.mynotes.models.adapters.FontColorAdadpter;
import notes.easy.android.mynotes.models.adapters.HighLightAdapter;
import notes.easy.android.mynotes.ui.adapters.LinearPaddingDecoration;
import notes.easy.android.mynotes.utils.KeyboardUtils;
import notes.easy.android.mynotes.utils.ScreenUtils;
import notes.easy.android.mynotes.utils.SystemHelper;
import notes.easy.android.mynotes.view.InputHelperView;
import notes.easy.android.mynotes.view.record.DialogRecord;
import notes.easy.android.mynotes.view.record.DialogSpeech2Text;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/* loaded from: classes5.dex */
public class InputHelperView extends FrameLayout {
    private ChooseInterface chooseInterface;
    private boolean isBold;
    private boolean isClipboardViewShowing;
    private boolean isItalic;
    private boolean isStrikethrough;
    private boolean isUnderline;
    private View mBoldImg;
    private TextView mClipboardContent;
    private View mClipboardLayout;
    private int mCurrentFontIndex;
    private BottomToolsAdadpter mEditingToolbarAdapter;
    private RecyclerView mEditingToolbarRecycleView;
    private final int mFontColor;
    private FontColorAdadpter mFontColorAdadpter;
    private View mFontColorDisplay;
    private RecyclerView mFontColorRecycler;
    private View mFontColorRecyclerLayout;
    private Handler mHandler;
    private HighLightAdapter mHighLightAdapter;
    private int mHighLightColor;
    private View mHighLightDisplay;
    private RecyclerView mHightLightRecycler;
    private View mHightLightRecyclerLayout;
    private View mItalicImg;
    private View mRootView;
    public Runnable mSavedRunnable;
    public Runnable mSavingRunnable;
    private TextView mSavingTextView;
    private View mStrikethroughImg;
    private View mTextBig;
    private View mTextSelectedLayout;
    private TextView mTextSizeNum;
    private View mTextSmall;
    private View mUnderLineImg;
    private Subscription subscription;
    private UserConfig userConfig;

    /* renamed from: notes.easy.android.mynotes.view.InputHelperView$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            InputHelperView.this.mSavingTextView.setVisibility(View.GONE);
        }

        @Override // java.lang.Runnable
        public void run() {
            FirebaseReportUtils.getInstance().reportNew("edit_autosaved_show");
            InputHelperView.this.mSavingTextView.setText(InputHelperView.this.getContext().getResources().getString(R.string.saved));
            InputHelperView.this.mHandler.postDelayed(new Runnable() { // from class: notes.easy.android.mynotes.view.m6
                @Override // java.lang.Runnable
                public void run() {
                    InputHelperView.AnonymousClass2.this.lambda$run$0();
                }
            }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        }
    }

    public interface ChooseInterface {
        void affix();

        void boldClick(boolean z6);

        void bottomDrawImg();

        void bottomEmoji(int i6);

        void bottomFont();

        void choosePic();

        void italicClick(boolean z6);

        void onClipboardClick();

        void onClipboardNoClick();

        void onNumListSelected(NumListEntry numListEntry);

        void recordCancel();

        void recordFinish();

        void recordPermission();

        void recordState();

        void setSelectedFontSizeChange(int i6);

        void showColorDialog(int i6);

        void strikethrough(boolean z6);

        void underLine(boolean z6);

        void updateSpeechResult(String str);
    }

    interface OnItemPositionCallback {
        void onPosition(Rect rect);
    }

    public InputHelperView(Context context) {
        super(context);
        this.isBold = false;
        this.isItalic = false;
        this.isUnderline = false;
        this.isStrikethrough = false;
        this.mHighLightColor = 0;
        this.mFontColor = 0;
        this.mCurrentFontIndex = Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(App.userConfig.getDefaultFloatSize()));
        this.isClipboardViewShowing = true;
        this.mSavingRunnable = new Runnable() { // from class: notes.easy.android.mynotes.view.InputHelperView.1
            @Override // java.lang.Runnable
            public void run() {
                FirebaseReportUtils.getInstance().reportNew("edit_autosaving_show");
                InputHelperView.this.mSavingTextView.setVisibility(View.VISIBLE);
                InputHelperView.this.mSavingTextView.setText(InputHelperView.this.getContext().getResources().getString(R.string.saving));
                InputHelperView.this.mHandler.postDelayed(InputHelperView.this.mSavedRunnable, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            }
        };
        this.mSavedRunnable = new AnonymousClass2();
        init(context);
    }

    private void getActionViewCenterPositionAfterScroll(final int i6, final OnItemPositionCallback onItemPositionCallback) {
        this.mEditingToolbarRecycleView.post(new Runnable() { // from class: notes.easy.android.mynotes.view.c6
            @Override // java.lang.Runnable
            public void run() {
                InputHelperView.this.lambda$getActionViewCenterPositionAfterScroll$1(i6, onItemPositionCallback);
            }
        });
    }

    private void init(Context context) {
        this.userConfig = UserConfig.Companion.newInstance(context);
        LayoutInflater.from(context).inflate(R.layout.input_helper_layout, this, true);
        this.mRootView = findViewById(R.id.input_helper_str_view);
        if (ScreenUtils.isPad(getContext())) {
            this.mRootView.getLayoutParams().width = ScreenUtils.dpToPx(488);
        }
        initEditingToolbarView();
        initHighLightView();
        initFontColorView();
        initTextSelectView();
        initClipboardView();
        initSavingView();
    }

    private void initClipboardView() {
        this.mClipboardLayout = findViewById(R.id.clipboard_layout);
        this.mClipboardContent = findViewById(R.id.clipboard_text);
        this.mClipboardLayout.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.l6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initClipboardView$10(view);
            }
        });
    }

    private void initEditingToolbarView() {
        this.mEditingToolbarRecycleView = findViewById(R.id.tool_recycleview);
        mEditingToolbarAdapter = new BottomToolsAdadpter();
        this.mEditingToolbarRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.mEditingToolbarRecycleView.addItemDecoration(new LinearPaddingDecoration(0, ScreenUtils.dpToPx(8)));
        this.mEditingToolbarRecycleView.setAdapter(this.mEditingToolbarAdapter);
        this.mEditingToolbarAdapter.initData();
        this.mEditingToolbarAdapter.setItemClickListener(new BottomToolsAdadpter.OnItemClickListener() { // from class: notes.easy.android.mynotes.view.InputHelperView.3
            @Override // notes.easy.android.mynotes.models.adapters.BottomToolsAdadpter.OnItemClickListener
            public void onItemClick(int i6) {
                switch (i6) {
                    case 101: //ACTION_ID_RICH_FONT
                        InputHelperView.this.userConfig.setFontBtnClick(true);
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.bottomFont();
                        }
                        break;
                    case 102: //ACTION_ID_CHECKLIST
                        InputHelperView.this.userConfig.setChecklistBtnClick(true);
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.onNumListSelected(new NumListEntry("checkList_no"));
                        }
                        break;
                    case 103: //ACTION_ID_RECORDING
                        InputHelperView.this.userConfig.setRecordBtnClick(true);
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.recordPermission();
                        }
                        break;
                    case 104: //ACTION_ID_DRAWING
                        KeyboardUtils.hideKeyboard(InputHelperView.this);
                        InputHelperView.this.userConfig.setDrawBtnClick(true);
                        InputHelperView.this.userConfig.setNewDrawReleasePromoteShow(true);
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.bottomDrawImg();
                        }
                        break;
                    case 105: //ACTION_ID_ADD_PIC
                        InputHelperView.this.userConfig.setChoosePicBtnClick(true);
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.choosePic();
                        }
                        break;
                    case 106: //ACTION_ID_EMOJI
                        InputHelperView.this.userConfig.setEmojiBtnClick(true);
                        if (InputHelperView.this.mEditingToolbarAdapter.getCurrentRedId() == 106) {
                            InputHelperView.this.userConfig.setNewEmoReleasePromoteShow(true);
                            InputHelperView.this.chooseInterface.bottomEmoji(1);
                        } else if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.bottomEmoji(0);
                        }
                        break;
                    case 107: //ACTION_ID_THEME
                        InputHelperView.this.userConfig.setBgBtnClick(true);
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.showColorDialog(0);
                        }
                        break;
                    case 108: //ACTION_ID_BULLET
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.onNumListSelected(new NumListEntry("Dots"));
                        }
                        break;
                    case 109: //ACTION_ID_NUMBER
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.onNumListSelected(new NumListEntry("digital"));
                        }
                        break;
                    case 110: //ACTION_ID_AFFIX
                        if (InputHelperView.this.chooseInterface != null) {
                            InputHelperView.this.chooseInterface.affix();
                        }
                        break;
                }
            }
        });
    }

    private void initFontColorView() {
        this.mFontColorRecyclerLayout = findViewById(R.id.fontcolor_recycler_layout);
        this.mFontColorRecycler = findViewById(R.id.fontcolor_recycler);
        FontColorAdadpter fontColorAdadpter = new FontColorAdadpter(getContext(), "0", 2);
        this.mFontColorAdadpter = fontColorAdadpter;
        this.mFontColorRecycler.setAdapter(fontColorAdadpter);
        this.mFontColorRecycler.setLayoutManager(new GridLayoutManager(getContext(), this.mFontColorAdadpter.getItemCount()));
    }

    private void initHighLightView() {
        this.mHightLightRecyclerLayout = findViewById(R.id.hightlight_recycler_layout);
        this.mHightLightRecycler = findViewById(R.id.hightlight_recycler);
        HighLightAdapter highLightAdapter = new HighLightAdapter();
        this.mHighLightAdapter = highLightAdapter;
        this.mHightLightRecycler.setAdapter(highLightAdapter);
        this.mHightLightRecycler.setLayoutManager(new GridLayoutManager(getContext(), this.mHighLightAdapter.getItemCount()));
    }

    private void initSavingView() {
        this.mSavingTextView = findViewById(R.id.saving_text_view);
    }

    private void initTextSelectView() {
        this.mTextSelectedLayout = findViewById(R.id.font_selected_layout);
        this.mBoldImg = findViewById(R.id.bold_img);
        this.mItalicImg = findViewById(R.id.italic_img);
        this.mUnderLineImg = findViewById(R.id.underline_img);
        this.mStrikethroughImg = findViewById(R.id.strikethrough_img);
        this.mTextSmall = findViewById(R.id.font_smaller);
        this.mTextBig = findViewById(R.id.font_bigger);
        this.mTextSizeNum = findViewById(R.id.size_of_font);
        View findViewById = findViewById(R.id.font_color_layout);
        this.mFontColorDisplay = findViewById(R.id.font_color_underline);
        View findViewById2 = findViewById(R.id.font_highlight_layout);
        this.mHighLightDisplay = findViewById(R.id.font_highlight_display);
        this.mBoldImg.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.d6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$2(view);
            }
        });
        this.mItalicImg.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.e6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$3(view);
            }
        });
        this.mUnderLineImg.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.f6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$4(view);
            }
        });
        this.mStrikethroughImg.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.g6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$5(view);
            }
        });
        this.mTextSmall.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.h6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$6(view);
            }
        });
        this.mTextBig.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.i6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$7(view);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.j6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$8(view);
            }
        });
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.view.k6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputHelperView.this.lambda$initTextSelectView$9(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getActionViewCenterPosition$0(int i6, OnItemPositionCallback onItemPositionCallback) {
        RecyclerView recyclerView = this.mEditingToolbarRecycleView;
        if (recyclerView == null || recyclerView.getLayoutManager() == null) {
            return;
        }
        try {
            int dpToPx = ScreenUtils.dpToPx(30);
            View findViewByPosition = this.mEditingToolbarRecycleView.getLayoutManager().findViewByPosition(this.mEditingToolbarAdapter.getPositionById(i6));
            int itemCount = this.mEditingToolbarRecycleView.getAdapter().getItemCount() - 1;
            if (findViewByPosition == null) {
                if (((LinearLayoutManager) this.mEditingToolbarRecycleView.getLayoutManager()).findFirstCompletelyVisibleItemPosition() != 0) {
                    this.mEditingToolbarRecycleView.scrollToPosition(0);
                } else {
                    this.mEditingToolbarRecycleView.scrollToPosition(itemCount);
                }
                getActionViewCenterPositionAfterScroll(i6, onItemPositionCallback);
                return;
            }
            int[] iArr = new int[2];
            findViewByPosition.getLocationOnScreen(iArr);
            int i7 = iArr[0];
            int i8 = iArr[1];
            int width = findViewByPosition.getWidth();
            int height = findViewByPosition.getHeight();
            float f6 = i7 + (width / 2.0f);
            if (f6 > ScreenUtils.getScreenWidth(getContext()) - dpToPx) {
                this.mEditingToolbarRecycleView.scrollToPosition(itemCount);
                getActionViewCenterPositionAfterScroll(i6, onItemPositionCallback);
            } else if (f6 < dpToPx) {
                this.mEditingToolbarRecycleView.scrollToPosition(0);
                getActionViewCenterPositionAfterScroll(i6, onItemPositionCallback);
            } else if (onItemPositionCallback != null) {
                onItemPositionCallback.onPosition(new Rect(i7, i8, width + i7, height + i8));
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getActionViewCenterPositionAfterScroll$1(int i6, OnItemPositionCallback onItemPositionCallback) {
        RecyclerView recyclerView = this.mEditingToolbarRecycleView;
        if (recyclerView == null || recyclerView.getLayoutManager() == null) {
            return;
        }
        try {
            ScreenUtils.dpToPx(40);
            View findViewByPosition = this.mEditingToolbarRecycleView.getLayoutManager().findViewByPosition(this.mEditingToolbarAdapter.getPositionById(i6));
            if (findViewByPosition != null) {
                int[] iArr = new int[2];
                findViewByPosition.getLocationOnScreen(iArr);
                int i7 = iArr[0];
                int i8 = iArr[1];
                int width = findViewByPosition.getWidth();
                int height = findViewByPosition.getHeight();
                if (onItemPositionCallback != null) {
                    onItemPositionCallback.onPosition(new Rect(i7, i8, width + i7, height + i8));
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initClipboardView$10(View view) {
        this.chooseInterface.onClipboardClick();
        FirebaseReportUtils.getInstance().reportNew("edit_autopaste_click");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$2(View view) {
        boolean z6 = !this.isBold;
        this.isBold = z6;
        setBold(z6);
        this.chooseInterface.boldClick(this.isBold);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$3(View view) {
        boolean z6 = !this.isItalic;
        this.isItalic = z6;
        setItalic(z6);
        this.chooseInterface.italicClick(this.isItalic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$4(View view) {
        boolean z6 = !this.isUnderline;
        this.isUnderline = z6;
        setUnderline(z6);
        this.chooseInterface.underLine(this.isUnderline);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$5(View view) {
        boolean z6 = !this.isStrikethrough;
        this.isStrikethrough = z6;
        setStrikethrough(z6);
        this.chooseInterface.strikethrough(this.isStrikethrough);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$6(View view) {
        int i6 = this.mCurrentFontIndex;
        if (i6 > 0) {
            int i7 = i6 - 1;
            this.mCurrentFontIndex = i7;
            TextView textView = this.mTextSizeNum;
            List<Integer> list = Constants.FONT_SIZE_LIST;
            textView.setText(String.valueOf(list.get(i7)));
            this.chooseInterface.setSelectedFontSizeChange(list.get(this.mCurrentFontIndex).intValue());
        }
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_size_minus");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$7(View view) {
        int i6 = this.mCurrentFontIndex;
        List<Integer> list = Constants.FONT_SIZE_LIST;
        if (i6 < list.size() - 1) {
            int i7 = this.mCurrentFontIndex + 1;
            this.mCurrentFontIndex = i7;
            this.mTextSizeNum.setText(String.valueOf(list.get(i7)));
            this.chooseInterface.setSelectedFontSizeChange(list.get(this.mCurrentFontIndex).intValue());
        }
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_size_plus");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$8(View view) {
        if (this.mHightLightRecyclerLayout.getVisibility() == 0) {
            this.mHightLightRecyclerLayout.setVisibility(View.GONE);
            return;
        }
        this.mHightLightRecyclerLayout.setVisibility(View.VISIBLE);
        this.mFontColorRecyclerLayout.setVisibility(View.GONE);
        HighLightAdapter highLightAdapter = this.mHighLightAdapter;
        if (highLightAdapter != null) {
            highLightAdapter.setSelectedColor(this.mHighLightColor);
            this.mHighLightAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTextSelectView$9(View view) {
        FirebaseReportUtils.getInstance().reportNew("edit_select_font_color_click");
        if (this.mFontColorRecyclerLayout.getVisibility() == 0) {
            this.mFontColorRecyclerLayout.setVisibility(View.GONE);
            return;
        }
        this.mFontColorRecyclerLayout.setVisibility(View.VISIBLE);
        this.mHightLightRecyclerLayout.setVisibility(View.GONE);
        FontColorAdadpter fontColorAdadpter = this.mFontColorAdadpter;
        if (fontColorAdadpter != null) {
            fontColorAdadpter.setSelected();
            this.mFontColorAdadpter.notifyDataSetChanged();
        }
    }

    public void clipboardUnsubscribe() {
        Subscription subscription = this.subscription;
        if (subscription == null || subscription.isUnsubscribed()) {
            return;
        }
        this.subscription.unsubscribe();
        this.isClipboardViewShowing = true;
        setClipboardViewVisible(false);
    }

    public void getActionViewCenterPosition(final int i6, final OnItemPositionCallback onItemPositionCallback) {
        RecyclerView recyclerView = this.mEditingToolbarRecycleView;
        if (recyclerView != null) {
            recyclerView.post(new Runnable() { // from class: notes.easy.android.mynotes.view.b6
                @Override // java.lang.Runnable
                public void run() {
                    InputHelperView.this.lambda$getActionViewCenterPosition$0(i6, onItemPositionCallback);
                }
            });
        }
    }

    public void hideSelectedState() {
        showSelectedState(false, null, 0, 0, false);
    }

    public void removeSaveRunnable(Handler handler) {
        this.mSavingTextView.setVisibility(View.GONE);
        Runnable runnable = this.mSavedRunnable;
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        Runnable runnable2 = this.mSavingRunnable;
        if (runnable2 != null) {
            handler.removeCallbacks(runnable2);
        }
    }

    public void setBold(boolean z6) {
        this.isBold = z6;
        if (z6) {
            this.mBoldImg.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            this.mBoldImg.setBackgroundResource(R.color.transparent);
        }
    }

    public void setClipboardViewDisplayTime(boolean z6) {
        setClipboardViewVisible(true);
        FirebaseReportUtils.getInstance().reportNew("edit_autopaste_show");
        if (this.isClipboardViewShowing) {
            this.isClipboardViewShowing = false;
            this.subscription = Observable.interval(0L, 1L, TimeUnit.SECONDS).take(6).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() { // from class: notes.easy.android.mynotes.view.InputHelperView.6
                @Override // rx.Observer
                public void onNext(Long l6) {
                    if (l6.longValue() == 5) {
                        InputHelperView.this.isClipboardViewShowing = true;
                        InputHelperView.this.setClipboardViewVisible(false);
                        InputHelperView.this.chooseInterface.onClipboardNoClick();
                    }
                }

                @Override // rx.Observer
                public void onCompleted() {
                }

                @Override // rx.Observer
                public void onError(Throwable th) {
                }
            });
        }
    }

    public void setClipboardViewVisible(boolean z6) {
        if (!z6) {
            this.mClipboardLayout.setVisibility(View.GONE);
            return;
        }
        this.mClipboardContent.setText(SystemHelper.getClipboardContent(getContext()));
        if (TextUtils.isEmpty(this.mClipboardContent.getText())) {
            return;
        }
        this.mClipboardLayout.setVisibility(View.VISIBLE);
    }

    public void setDarkViewMode(boolean z6) {
        if (z6) {
            this.mSavingTextView.setTextColor(getContext().getResources().getColor(R.color.theme_text_white_secondary));
            this.mClipboardLayout.setBackgroundResource(R.drawable.shape_clipboard_text_bg_dark);
            this.mClipboardContent.setTextColor(getContext().getResources().getColor(R.color.theme_text_white_secondary));
        } else {
            this.mSavingTextView.setTextColor(getContext().getResources().getColor(R.color.theme_text_black_secondary));
            this.mClipboardLayout.setBackgroundResource(R.drawable.shape_clipboard_text_bg);
            this.mClipboardContent.setTextColor(getContext().getResources().getColor(R.color.theme_text_black_secondary));
        }
    }

    public void setDrawImgRed(boolean z6, boolean z7) {
        if ((!z6 || this.userConfig.getDrawBtnClick()) && !z7) {
            BottomToolsAdadpter bottomToolsAdadpter = this.mEditingToolbarAdapter;
            if (bottomToolsAdadpter == null || bottomToolsAdadpter.getCurrentRedId() != 104) {
                return;
            }
            this.mEditingToolbarAdapter.updateCurrentToolRed(-1);
            return;
        }
        BottomToolsAdadpter bottomToolsAdadpter2 = this.mEditingToolbarAdapter;
        if (bottomToolsAdadpter2 == null || bottomToolsAdadpter2.getCurrentRedId() != -1) {
            return;
        }
        this.mEditingToolbarAdapter.updateCurrentToolRed(104);
    }

    public void setEmojiImgRed(boolean z6, boolean z7) {
        if ((!z6 || this.userConfig.getEmojiBtnClick()) && !z7) {
            BottomToolsAdadpter bottomToolsAdadpter = this.mEditingToolbarAdapter;
            if (bottomToolsAdadpter == null || bottomToolsAdadpter.getCurrentRedId() != 106) {
                return;
            }
            this.mEditingToolbarAdapter.updateCurrentToolRed(-1);
            return;
        }
        BottomToolsAdadpter bottomToolsAdadpter2 = this.mEditingToolbarAdapter;
        if (bottomToolsAdadpter2 == null || bottomToolsAdadpter2.getCurrentRedId() != -1) {
            return;
        }
        this.mEditingToolbarAdapter.updateCurrentToolRed(106);
    }

    public void setFontColorDisplayColor(String str) {
        if (str.startsWith(Constants.SPECIAL_CHARACTOR)) {
            this.mFontColorDisplay.setBackgroundColor(Color.parseColor(str));
        } else {
            try {
                this.mFontColorDisplay.setBackgroundColor(Integer.parseInt(str));
            } catch (Exception unused) {
            }
        }
    }

    public void setFontColorListener(FontColorAdadpter.FontColorListener fontColorListener) {
        FontColorAdadpter fontColorAdadpter = this.mFontColorAdadpter;
        if (fontColorAdadpter != null) {
            fontColorAdadpter.setListsner(fontColorListener);
        }
    }

    public void setGuideWithWave(int i6) {
        BottomToolsAdadpter bottomToolsAdadpter = this.mEditingToolbarAdapter;
        if (bottomToolsAdadpter != null) {
            bottomToolsAdadpter.updateWaveState(i6);
        }
    }

    public void setHighLightDisplayColor(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mHighLightDisplay.setBackgroundColor(Color.parseColor(HighLightAdapter.HIGHLIGHT_COLOR_SHOW[0]));
            this.mHighLightColor = 0;
        } else {
            int parseColor = Color.parseColor(str);
            this.mHighLightDisplay.setBackgroundColor(parseColor);
            this.mHighLightColor = parseColor;
        }
    }

    public void setHighLightListener(HighLightAdapter.OnListCallback onListCallback) {
        HighLightAdapter highLightAdapter = this.mHighLightAdapter;
        if (highLightAdapter != null) {
            highLightAdapter.setOnListCallback(onListCallback);
        }
    }

    public void setInterface(ChooseInterface chooseInterface) {
        this.chooseInterface = chooseInterface;
    }

    public void setItalic(boolean z6) {
        this.isItalic = z6;
        if (z6) {
            this.mItalicImg.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            this.mItalicImg.setBackgroundResource(R.color.transparent);
        }
    }

    public void setPicImgRed(boolean z6) {
        if (!z6 || this.userConfig.getChoosePicBtnClick()) {
            BottomToolsAdadpter bottomToolsAdadpter = this.mEditingToolbarAdapter;
            if (bottomToolsAdadpter == null || bottomToolsAdadpter.getCurrentRedId() != 105) {
                return;
            }
            this.mEditingToolbarAdapter.updateCurrentToolRed(-1);
            return;
        }
        BottomToolsAdadpter bottomToolsAdadpter2 = this.mEditingToolbarAdapter;
        if (bottomToolsAdadpter2 == null || bottomToolsAdadpter2.getCurrentRedId() != -1) {
            return;
        }
        this.mEditingToolbarAdapter.updateCurrentToolRed(105);
    }

    public void setRedOfRecycleTool2(int i6) {
        this.mEditingToolbarAdapter.updateCurrentToolRed(i6);
        this.mEditingToolbarRecycleView.post(new Runnable() { // from class: notes.easy.android.mynotes.view.InputHelperView.7
            @Override // java.lang.Runnable
            public void run() {
                InputHelperView.this.mEditingToolbarRecycleView.scrollToPosition(InputHelperView.this.mEditingToolbarRecycleView.getAdapter().getItemCount() - 1);
            }
        });
    }

    public void setStrikethrough(boolean z6) {
        this.isStrikethrough = z6;
        if (z6) {
            this.mStrikethroughImg.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            this.mStrikethroughImg.setBackgroundResource(R.color.transparent);
        }
    }

    public void setTextSizeNum(int i6) {
        this.mTextSizeNum.setText(String.valueOf(i6));
    }

    public void setUnderline(boolean z6) {
        this.isUnderline = z6;
        if (z6) {
            this.mUnderLineImg.setBackgroundResource(R.drawable.shape_theme_accent_10alpha_6dp);
        } else {
            this.mUnderLineImg.setBackgroundResource(R.color.transparent);
        }
    }

    public void setV1(boolean z6) {
        BottomToolsAdadpter bottomToolsAdadpter = this.mEditingToolbarAdapter;
        if (bottomToolsAdadpter != null) {
            bottomToolsAdadpter.setIsV1(z6);
        }
    }

    public void showBottomRecordDialog() {
        DialogRecord.INSTANCE.showBottomRecordDialog(getContext(), new DialogRecord.RecordActionInterface() { // from class: notes.easy.android.mynotes.view.InputHelperView.4
            @Override // notes.easy.android.mynotes.view.record.DialogRecord.RecordActionInterface
            public void cancel() {
                InputHelperView.this.chooseInterface.recordCancel();
            }

            @Override // notes.easy.android.mynotes.view.record.DialogRecord.RecordActionInterface
            public void finish() {
                InputHelperView.this.chooseInterface.recordFinish();
            }

            @Override // notes.easy.android.mynotes.view.record.DialogRecord.RecordActionInterface
            public void recordState() {
                InputHelperView.this.chooseInterface.recordState();
            }
        });
        KeyboardUtils.hideKeyboard(this);
    }

    public void showBottomSpeechDialog(boolean z6) {
        new DialogSpeech2Text().showBottomRecordDialog(z6, getContext(), new DialogSpeech2Text.RecordActionInterface() { // from class: notes.easy.android.mynotes.view.InputHelperView.5
            @Override // notes.easy.android.mynotes.view.record.DialogSpeech2Text.RecordActionInterface
            public void recordState(String str) {
                InputHelperView.this.chooseInterface.updateSpeechResult(str);
            }

            @Override // notes.easy.android.mynotes.view.record.DialogSpeech2Text.RecordActionInterface
            public void cancel() {
            }

            @Override // notes.easy.android.mynotes.view.record.DialogSpeech2Text.RecordActionInterface
            public void finish() {
            }
        });
        KeyboardUtils.hideKeyboard(this);
    }

    public void showSavingTextView(Handler handler) {
        this.mHandler = handler;
        handler.postDelayed(this.mSavingRunnable, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public void showSelectedState(boolean z6, Editable editable, int i6, int i7, boolean z7) {
        if (!z6) {
            if (this.mTextSelectedLayout.getVisibility() == 0) {
                HighLightAdapter highLightAdapter = this.mHighLightAdapter;
                if (highLightAdapter != null) {
                    highLightAdapter.setSelectedColor("");
                    this.mHighLightAdapter.notifyDataSetChanged();
                }
                FontColorAdadpter fontColorAdadpter = this.mFontColorAdadpter;
                if (fontColorAdadpter != null) {
                    fontColorAdadpter.setSelected();
                    this.mFontColorAdadpter.notifyDataSetChanged();
                }
                this.mTextSelectedLayout.setVisibility(View.GONE);
                this.mHightLightRecyclerLayout.setVisibility(View.GONE);
                this.mFontColorRecyclerLayout.setVisibility(View.GONE);
            }
            this.mEditingToolbarRecycleView.setVisibility(View.VISIBLE);
            return;
        }
        if (this.mTextSelectedLayout.getVisibility() != 0) {
            FirebaseReportUtils.getInstance().reportNew("edit_select_show");
        }
        this.mTextSelectedLayout.setVisibility(View.VISIBLE);
        this.mEditingToolbarRecycleView.setVisibility(View.GONE);
        this.mHightLightRecyclerLayout.setVisibility(View.GONE);
        this.mFontColorRecyclerLayout.setVisibility(View.GONE);
        setBold(false);
        setItalic(false);
        setUnderline(false);
        setStrikethrough(false);
        setHighLightDisplayColor("");
        if (z7) {
            this.mFontColorDisplay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme_text_white_primary));
        } else {
            this.mFontColorDisplay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme_text_black_primary));
        }
        List<Integer> list = Constants.FONT_SIZE_LIST;
        int indexOf = list.indexOf(Integer.valueOf(App.userConfig.getDefaultFloatSize()));
        this.mCurrentFontIndex = indexOf;
        if (indexOf >= 0 && indexOf <= list.size() - 1) {
            setTextSizeNum(list.get(this.mCurrentFontIndex).intValue());
        }
        if (editable == null) {
            return;
        }
        int i8 = i6 + 1;
        Object[] objArr = editable.getSpans(i6, i8, StrikethroughSpan.class);
        Object[] objArr2 = editable.getSpans(i6, i8, UnderlineSpan.class);
        BackgroundColorSpan[] backgroundColorSpanArr = editable.getSpans(i6, i8, BackgroundColorSpan.class);
        AbsoluteSizeSpan[] absoluteSizeSpanArr = editable.getSpans(i6, i8, AbsoluteSizeSpan.class);
        ForegroundColorSpan[] foregroundColorSpanArr = editable.getSpans(i6, i8, ForegroundColorSpan.class);
        StyleSpan[] styleSpanArr = editable.getSpans(i6, i8, StyleSpan.class);
        if (objArr != null) {
            int length = objArr.length;
            int i9 = 0;
            while (true) {
                if (i9 >= length) {
                    break;
                }
                Object obj = objArr[i9];
                if (obj != null) {
                    int spanStart = editable.getSpanStart(obj);
                    int spanEnd = editable.getSpanEnd(obj);
                    if (i6 >= spanStart && i7 <= spanEnd) {
                        setStrikethrough(true);
                        break;
                    }
                }
                i9++;
            }
        }
        if (objArr2 != null) {
            int length2 = objArr2.length;
            int i10 = 0;
            while (true) {
                if (i10 >= length2) {
                    break;
                }
                Object obj2 = objArr2[i10];
                if (obj2 != null) {
                    int spanStart2 = editable.getSpanStart(obj2);
                    int spanEnd2 = editable.getSpanEnd(obj2);
                    if (i6 >= spanStart2 && i7 <= spanEnd2) {
                        setUnderline(true);
                        break;
                    }
                }
                i10++;
            }
        }
        if (foregroundColorSpanArr != null) {
            int length3 = foregroundColorSpanArr.length;
            int i11 = 0;
            while (true) {
                if (i11 >= length3) {
                    break;
                }
                ForegroundColorSpan foregroundColorSpan = foregroundColorSpanArr[i11];
                if (foregroundColorSpan != null) {
                    int spanStart3 = editable.getSpanStart(foregroundColorSpan);
                    int spanEnd3 = editable.getSpanEnd(foregroundColorSpan);
                    if (i6 >= spanStart3 && i7 <= spanEnd3) {
                        setFontColorDisplayColor(foregroundColorSpan.getForegroundColor());
                        break;
                    }
                }
                i11++;
            }
        }
        if (backgroundColorSpanArr != null) {
            int length4 = backgroundColorSpanArr.length;
            int i12 = 0;
            while (true) {
                if (i12 >= length4) {
                    break;
                }
                BackgroundColorSpan backgroundColorSpan = backgroundColorSpanArr[i12];
                if (backgroundColorSpan != null) {
                    int spanStart4 = editable.getSpanStart(backgroundColorSpan);
                    int spanEnd4 = editable.getSpanEnd(backgroundColorSpan);
                    if (i6 >= spanStart4 && i7 <= spanEnd4) {
                        setHighLightDisplayColor(backgroundColorSpan.getBackgroundColor());
                        break;
                    }
                }
                i12++;
            }
        }
        if (absoluteSizeSpanArr != null) {
            int length5 = absoluteSizeSpanArr.length;
            int i13 = 0;
            while (true) {
                if (i13 >= length5) {
                    break;
                }
                AbsoluteSizeSpan absoluteSizeSpan = absoluteSizeSpanArr[i13];
                if (absoluteSizeSpan != null) {
                    int spanStart5 = editable.getSpanStart(absoluteSizeSpan);
                    int spanEnd5 = editable.getSpanEnd(absoluteSizeSpan);
                    if (i6 >= spanStart5 && i7 <= spanEnd5) {
                        int round = Math.round(absoluteSizeSpan.getSize() / Resources.getSystem().getDisplayMetrics().density);
                        int i14 = 0;
                        int i15 = -1;
                        while (true) {
                            List<Integer> list2 = Constants.FONT_SIZE_LIST;
                            if (i14 >= list2.size()) {
                                break;
                            }
                            int abs = Math.abs(round - list2.get(i14).intValue());
                            if (i15 != -1) {
                                if (abs < i15) {
                                    this.mCurrentFontIndex = i14;
                                    if (abs == 0) {
                                        break;
                                    }
                                } else {
                                    continue;
                                    i14++;
                                }
                            }
                            i15 = abs;
                            i14++;
                        }
                        setTextSizeNum(round);
                    }
                }
                i13++;
            }
        }
        if (styleSpanArr != null) {
            for (StyleSpan styleSpan : styleSpanArr) {
                if (styleSpan != null) {
                    int spanStart6 = editable.getSpanStart(styleSpan);
                    int spanEnd6 = editable.getSpanEnd(styleSpan);
                    if (i6 >= spanStart6 && i7 <= spanEnd6) {
                        if (styleSpan.getStyle() == 1) {
                            setBold(true);
                        } else if (styleSpan.getStyle() == 2) {
                            setItalic(true);
                        }
                    }
                }
            }
        }
    }

    public void setFontColorDisplayColor(int i6) {
        this.mFontColorDisplay.setBackgroundColor(i6);
    }

    public void setHighLightDisplayColor(int i6) {
        if (i6 == 0) {
            this.mHighLightDisplay.setBackgroundColor(Color.parseColor(HighLightAdapter.HIGHLIGHT_COLOR_SHOW[0]));
            this.mHighLightColor = 0;
        } else {
            this.mHighLightDisplay.setBackgroundColor(i6);
            this.mHighLightColor = i6;
        }
    }

    public InputHelperView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isBold = false;
        this.isItalic = false;
        this.isUnderline = false;
        this.isStrikethrough = false;
        this.mHighLightColor = 0;
        this.mFontColor = 0;
        this.mCurrentFontIndex = Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(App.userConfig.getDefaultFloatSize()));
        this.isClipboardViewShowing = true;
        this.mSavingRunnable = new Runnable() { // from class: notes.easy.android.mynotes.view.InputHelperView.1
            @Override // java.lang.Runnable
            public void run() {
                FirebaseReportUtils.getInstance().reportNew("edit_autosaving_show");
                InputHelperView.this.mSavingTextView.setVisibility(View.VISIBLE);
                InputHelperView.this.mSavingTextView.setText(InputHelperView.this.getContext().getResources().getString(R.string.saving));
                InputHelperView.this.mHandler.postDelayed(InputHelperView.this.mSavedRunnable, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            }
        };
        this.mSavedRunnable = new AnonymousClass2();
        init(context);
    }

    public InputHelperView(Context context, AttributeSet attributeSet, int i6) {
        super(context, attributeSet, i6);
        this.isBold = false;
        this.isItalic = false;
        this.isUnderline = false;
        this.isStrikethrough = false;
        this.mHighLightColor = 0;
        this.mFontColor = 0;
        this.mCurrentFontIndex = Constants.FONT_SIZE_LIST.indexOf(Integer.valueOf(App.userConfig.getDefaultFloatSize()));
        this.isClipboardViewShowing = true;
        this.mSavingRunnable = new Runnable() { // from class: notes.easy.android.mynotes.view.InputHelperView.1
            @Override // java.lang.Runnable
            public void run() {
                FirebaseReportUtils.getInstance().reportNew("edit_autosaving_show");
                InputHelperView.this.mSavingTextView.setVisibility(View.VISIBLE);
                InputHelperView.this.mSavingTextView.setText(InputHelperView.this.getContext().getResources().getString(R.string.saving));
                InputHelperView.this.mHandler.postDelayed(InputHelperView.this.mSavedRunnable, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            }
        };
        this.mSavedRunnable = new AnonymousClass2();
        init(context);
    }
}
