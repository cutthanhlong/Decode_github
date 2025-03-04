package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.custom.edittext.EditText;
import com.example.myapplication.custom.edittext.EditTextMenuListener;
import com.example.myapplication.custom.edittext.TextUndoRedo;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.NumListEntry;
import com.example.myapplication.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EditContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MP3Player.CompleteListener {
    private static int cursorBeforeAfter;
    public List<EditContentBean> contentBeanList;
    private boolean dark;
    private final EditTextMenuListener editTextMenuListener;
    private EditText editTextSelected;
    private final ImgAdapterClick imgAdapterClick;
    private final LayoutInflater inflater;
    private final Activity mActivity;
    private final Handler mRefreshHandler;
    private final MP3Player mp;
    private MP3Player mp2;
    private final BaseEditView.PicClicklistener picClicklistener;
    private final RecordGridActionInterface recordGridActionInterface;
    private final SaveAll saveAll;
    private boolean showHint;
    private Typeface stringStyle;
    private Runnable updateRunnale;
    public HashMap<Integer, EditText> editTextHashMap = new HashMap<>();
    public HashMap<Integer, TextView> numberHashMap = new HashMap<>();
    private boolean isPlaying = false;
    private final Handler handler = new Handler();
    private int addImgP = 0;
    private int startIndex = 0;
    private int selectedEditText = -1;
    private boolean showKeyboard = false;
    private boolean contentTouch = false;
    private boolean delRow = false;
    private boolean isLineTheme = false;
    private NoteBgBean.Line line = null;
    private final int checkListColor = Color.parseColor("#56000000");
    private final int checkListColorW = Color.parseColor("#56ffffff");
    private String searchText = "";
    private final List<String> searchEditIndex = new ArrayList();
    boolean cursorEditingStop = false;
    private long imgFirstTime = 0;


    View.OnTouchListener onTouchListener = (view, motionEvent) -> {
        if (motionEvent.getAction() == 0 && EditContentAdapter.this.mActivity != null && (EditContentAdapter.this.mActivity.getCurrentFocus() instanceof EditText)) {
            EditText editText = (EditText) EditContentAdapter.this.mActivity.getCurrentFocus();
            if (editText.getSelectionStart() != editText.getSelectionEnd()) {
                editText.setSelection(editText.getSelectionEnd());
            }
        }
        return false;
    };

    class AnonymousClass1 extends TimerTask {
        final boolean val$b;

        AnonymousClass1(boolean z6) {
            this.val$b = z6;
        }

        public void lambda$run$0(boolean z6) {
            EditContentAdapter.this.delRow = true;
            EditContentAdapter editContentAdapter = EditContentAdapter.this;
            editContentAdapter.saveEditRich(editContentAdapter.editTextSelected, EditContentAdapter.this.selectedEditText, z6);
        }

        @Override
        public void run() {
            Activity activity = EditContentAdapter.this.mActivity;
            final boolean z6 = this.val$b;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditContentAdapter.AnonymousClass1.this.lambda$run$0(z6);
                }
            });
        }
    }

    class AnonymousClass4 extends TimerTask {
        final EditText[] val$mEditText;

        AnonymousClass4(EditText[] editTextArr) {
            this.val$mEditText = editTextArr;
        }

        public static void lambda$run$0(EditText[] editTextArr) {
            ((InputMethodManager) editTextArr[0].getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editTextArr[0], 2);
        }

        @Override
        public void run() {
            Activity activity = EditContentAdapter.this.mActivity;
            final EditText[] editTextArr = this.val$mEditText;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditContentAdapter.AnonymousClass4.lambda$run$0(editTextArr);
                }
            });
        }
    }

    public interface ImgAdapterClick {
        void onPicClick(Attachment attachment, int i6, int i7);
    }

    public interface RecordGridActionInterface {
    }

    public interface SaveAll {
        void save();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    static class ViewHolderBullet extends RecyclerView.ViewHolder {
        public EditText content;
        public CardView vip_btn;

        public ViewHolderBullet(View view) {
            super(view);
            this.content = view.findViewById(R.id.content);
            this.vip_btn = view.findViewById(R.id.vip_btn);
        }
    }

    static class ViewHolderCheck extends RecyclerView.ViewHolder {
        public View checkBox;
        public EditText content;

        public ViewHolderCheck(View view) {
            super(view);
            this.content = view.findViewById(R.id.content);
            this.checkBox = view.findViewById(R.id.check_box);
        }
    }

    static class ViewHolderImg extends RecyclerView.ViewHolder {
        public RecyclerView photo_unit;

        public ViewHolderImg(View view) {
            super(view);
            this.photo_unit = view.findViewById(R.id.photo_unit);
        }
    }

    static class ViewHolderNumber extends RecyclerView.ViewHolder {
        public EditText content;
        public TextView number;

        public ViewHolderNumber(View view) {
            super(view);
            this.content = view.findViewById(R.id.content);
            this.number = view.findViewById(R.id.number);
        }
    }

    static class ViewHolderText extends RecyclerView.ViewHolder {
        public EditText content;
        public View mView;

        public ViewHolderText(View view) {
            super(view);
            this.content = view.findViewById(R.id.content);
            this.mView = view.findViewById(R.id.hold_view);
        }
    }

    public EditContentAdapter(Handler handler, Activity activity, List<EditContentBean> list, RecordGridActionInterface recordGridActionInterface, BaseEditView.PicClicklistener picClicklistener, EditTextMenuListener editTextMenuListener, ImgAdapterClick imgAdapterClick, SaveAll saveAll, boolean z6) {
        this.showHint = true;
        this.stringStyle = null;
        this.dark = false;
        this.mRefreshHandler = handler;
        this.contentBeanList = list;
        this.mActivity = activity;
        this.recordGridActionInterface = recordGridActionInterface;
        this.inflater = (LayoutInflater) activity.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        this.mp2 = new MP3Player(activity, this);
        this.mp = new MP3Player(activity, this);
        this.picClicklistener = picClicklistener;
        this.editTextMenuListener = editTextMenuListener;
        this.imgAdapterClick = imgAdapterClick;
        this.saveAll = saveAll;
        this.dark = z6;
        this.stringStyle = ResNoteFontManager.getInstance().getFontFromFile(ResNoteFontManager.DEFAULT_FONT);
        for (int i6 = 0; i6 < list.size(); i6++) {
            if (list.get(i6).getContent().length() > 0) {
                this.showHint = false;
                return;
            }
        }
    }

    private void initBullet(ViewHolderBullet viewHolderBullet, int i6) {
        viewHolderBullet.content.setText(EmojiManager.parseCharSequence(new SpannableStringBuilder(this.contentBeanList.get(i6).getContent())), TextView.BufferType.SPANNABLE);
        setEditListener(viewHolderBullet.content, viewHolderBullet);
        if (this.searchText.length() > 0 && viewHolderBullet.content.getText() != null && viewHolderBullet.content.getText().toString().toLowerCase().contains(this.searchText)) {
            int indexOf = viewHolderBullet.content.getText().toString().toLowerCase().indexOf(this.searchText);
            while (indexOf != -1) {
                EditText editText = viewHolderBullet.content;
                setTextBackground(editText, editText.getText(), indexOf, indexOf + this.searchText.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                this.searchEditIndex.add(indexOf + "," + (this.searchText.length() + indexOf));
                indexOf = viewHolderBullet.content.getText().toString().toLowerCase().indexOf(this.searchText, indexOf + 1);
            }
        }
        setRichText(viewHolderBullet.content, this.contentBeanList.get(i6).getRichData(), 0);
        if (this.dark) {
            viewHolderBullet.vip_btn.setCardBackgroundColor(-1);
        } else {
            viewHolderBullet.vip_btn.setCardBackgroundColor(-16777216);
        }
        this.editTextHashMap.put(Integer.valueOf(i6), viewHolderBullet.content);
        this.numberHashMap.put(Integer.valueOf(i6), null);
    }

    private void initCheck(final ViewHolderCheck viewHolderCheck, int i6) {
        final int[] iArr = {i6};
        viewHolderCheck.content.setText(EmojiManager.parseCharSequence(new SpannableStringBuilder(this.contentBeanList.get(iArr[0]).getContent())), TextView.BufferType.SPANNABLE);
        setEditListener(viewHolderCheck.content, viewHolderCheck);
        int i7 = iArr[0];
        setCheck(viewHolderCheck, i7, this.contentBeanList.get(i7).isSelected());
        if (this.searchText.length() > 0 && viewHolderCheck.content.getText() != null && viewHolderCheck.content.getText().toString().toLowerCase().contains(this.searchText)) {
            int indexOf = viewHolderCheck.content.getText().toString().toLowerCase().indexOf(this.searchText);
            while (indexOf != -1) {
                EditText editText = viewHolderCheck.content;
                setTextBackground(editText, editText.getText(), indexOf, indexOf + this.searchText.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                this.searchEditIndex.add(indexOf + "," + (this.searchText.length() + indexOf));
                indexOf = viewHolderCheck.content.getText().toString().toLowerCase().indexOf(this.searchText, indexOf + 1);
            }
        }
        setRichText(viewHolderCheck.content, this.contentBeanList.get(iArr[0]).getRichData(), 0);
        viewHolderCheck.checkBox.setOnClickListener(new View.OnClickListener() { // from class: notes.easy.android.mynotes.ui.adapters.d
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditContentAdapter.this.lambda$initCheck$4(viewHolderCheck, iArr, view);
            }
        });
        this.editTextHashMap.put(Integer.valueOf(iArr[0]), viewHolderCheck.content);
        this.numberHashMap.put(Integer.valueOf(iArr[0]), null);
    }

    private void initImg(ViewHolderImg viewHolderImg, int i6) {
        final int[] iArr = {i6};
        PicGridAdapter picGridAdapter = new PicGridAdapter(this.mActivity, this.contentBeanList.get(i6).getAttachmentsList());
        picGridAdapter.updateGradId();
        if (!this.contentBeanList.get(iArr[0]).aBoolean) {
            new ItemTouchHelper(new ItemDragCallback(picGridAdapter)).attachToRecyclerView(viewHolderImg.photo_unit);
            this.contentBeanList.get(iArr[0]).aBoolean = true;
        }
        picGridAdapter.setOnListCallbackListener(new PicGridAdapter.OnListCallback() { // from class: notes.easy.android.mynotes.ui.adapters.EditContentAdapter.6
            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemClick(View view, Attachment attachment, int i7) {
                if (EditContentAdapter.this.imgAdapterClick != null) {
                    EditContentAdapter.this.imgAdapterClick.onPicClick(attachment, i7, iArr[0]);
                }
            }

            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemDragFinished(List<Attachment> list, int i7, int i8) {
                if (EditContentAdapter.this.picClicklistener != null) {
                    EditContentAdapter.this.picClicklistener.onBaseViewDragFinish(list, i7, i8, EditContentAdapter.this.contentBeanList.get(iArr[0]).getAttachmentsList());
                }
            }

            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemLongClick() {
            }
        });
        viewHolderImg.photo_unit.setLayoutManager(new ScrollGridLayoutManager(this.mActivity, com.example.myapplication.utils.ScreenUtils.isScreenOriatationLandscap(this.mActivity) ? 4 : com.example.myapplication.utils.ScreenUtils.isPad(this.mActivity) ? 3 : 2, 1, false));
        viewHolderImg.photo_unit.setAdapter(picGridAdapter);
        viewHolderImg.photo_unit.setOnTouchListener(this.onTouchListener);
        picGridAdapter.notifyDataSetChanged();
        this.editTextHashMap.put(Integer.valueOf(iArr[0]), null);
        this.numberHashMap.put(Integer.valueOf(iArr[0]), null);
    }

    private void initNumber(ViewHolderNumber viewHolderNumber, int i6) {
        viewHolderNumber.content.setText(EmojiManager.parseCharSequence(new SpannableStringBuilder(this.contentBeanList.get(i6).getContent())), TextView.BufferType.SPANNABLE);
        setEditListener(viewHolderNumber.content, viewHolderNumber);
        if (i6 != 0) {
            int i7 = i6 - 1;
            if (this.contentBeanList.get(i7).getViewType() == 3) {
                this.contentBeanList.get(i6).setNum(this.contentBeanList.get(i7).getNum() + 1);
            } else {
                this.contentBeanList.get(i6).setNum(1);
            }
        }
        viewHolderNumber.number.setText(this.contentBeanList.get(i6).getNum() + " .\t");
        if (this.dark) {
            viewHolderNumber.number.setTextColor(ContextCompat.getColor(this.mActivity, R.color.white_86alpha_dbfff));
        } else {
            viewHolderNumber.number.setTextColor(ContextCompat.getColor(this.mActivity, R.color.black_87alpha_df000));
        }
        if (this.searchText.length() > 0 && viewHolderNumber.content.getText() != null && viewHolderNumber.content.getText().toString().toLowerCase().contains(this.searchText)) {
            int indexOf = viewHolderNumber.content.getText().toString().toLowerCase().indexOf(this.searchText);
            while (indexOf != -1) {
                EditText editText = viewHolderNumber.content;
                setTextBackground(editText, editText.getText(), indexOf, indexOf + this.searchText.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                this.searchEditIndex.add(indexOf + "," + (this.searchText.length() + indexOf));
                indexOf = viewHolderNumber.content.getText().toString().toLowerCase().indexOf(this.searchText, indexOf + 1);
            }
        }
        if (this.contentBeanList.get(i6).getRichData().contains("f0")) {
            for (String str : this.contentBeanList.get(i6).getRichData().split(",")) {
                if (!TextUtils.isEmpty(str) && str.startsWith("f")) {
                    String substring = str.substring(1);
                    if (!TextUtils.isEmpty(substring)) {
                        String[] split = substring.split("/");
                        if (split.length == 3 && Util.parseInt(split[0]) == 0) {
                            int parseInt = Integer.parseInt(split[2]);
                            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
                            ScreenUtils.dpToPx(parseInt);
                            viewHolderNumber.number.setTextSize(Math.round(parseInt / displayMetrics.density));
                        }
                    }
                }
            }
        }
        setRichText(viewHolderNumber.content, this.contentBeanList.get(i6).getRichData(), 0);
        this.editTextHashMap.put(Integer.valueOf(i6), viewHolderNumber.content);
        this.numberHashMap.put(Integer.valueOf(i6), viewHolderNumber.number);
    }

    private void initText(ViewHolderText viewHolderText, int i6) {
        viewHolderText.content.setText(EmojiManager.parseCharSequence(new SpannableStringBuilder(this.contentBeanList.get(i6).getContent())), TextView.BufferType.SPANNABLE);
        setRichText(viewHolderText.content, this.contentBeanList.get(i6).getRichData(), 0);
        setEditListener(viewHolderText.content, viewHolderText);
        this.contentBeanList.get(i6).getIndentation();
        this.editTextHashMap.put(Integer.valueOf(i6), viewHolderText.content);
        this.numberHashMap.put(Integer.valueOf(i6), null);
        if (this.searchText.length() > 0 && viewHolderText.content.getText() != null && viewHolderText.content.getText().toString().toLowerCase().contains(this.searchText)) {
            int indexOf = viewHolderText.content.getText().toString().toLowerCase().indexOf(this.searchText);
            while (indexOf != -1) {
                EditText editText = viewHolderText.content;
                setTextBackground(editText, editText.getText(), indexOf, indexOf + this.searchText.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                this.searchEditIndex.add(indexOf + "," + (this.searchText.length() + indexOf));
                indexOf = viewHolderText.content.getText().toString().toLowerCase().indexOf(this.searchText, indexOf + 1);
            }
        }
        viewHolderText.mView.setVisibility(View.GONE);
    }

    public  void lambda$initCheck$4(ViewHolderCheck viewHolderCheck, int[] iArr, View view) {
        this.cursorEditingStop = true;
        int i6 = iArr[0];
        setCheck(viewHolderCheck, i6, !this.contentBeanList.get(i6).isSelected());
        EditText editText = this.editTextSelected;
        if (editText == null) {
            cursorBeforeAfter = viewHolderCheck.content.getSelectionStart();
            StringBuilder sb = new StringBuilder();
            sb.append("onSelectedAreChanged: 008 ");
            sb.append(cursorBeforeAfter);
        } else {
            cursorBeforeAfter = editText.getSelectionStart();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onSelectedAreChanged: 009 ");
            sb2.append(cursorBeforeAfter);
        }
        notifyChanged(iArr[0]);
    }

    public static  int lambda$saveEditRich$23(SpanBean spanBean, SpanBean spanBean2) {
        if (spanBean.getStart() < spanBean2.getStart()) {
            return -1;
        }
        return spanBean.getStart() == spanBean2.getStart() ? 0 : 1;
    }

    public  boolean lambda$setEditListener$0(TextView textView, int i6, KeyEvent keyEvent) {
        int i7 = 0;
        if (keyEvent != null && keyEvent.getKeyCode() == 66 && keyEvent.getAction() == 0) {
            int selectionStart = textView.getSelectionStart();
            String[] split = textView.getText().toString().split("\n");
            int i8 = 0;
            int i9 = 0;
            while (true) {
                if (i8 >= split.length) {
                    i8 = -1;
                    break;
                }
                i9 = i9 + split[i8].length() + 1;
                if (selectionStart < i9) {
                    break;
                }
                i8++;
            }
            if (i8 == -1) {
                return false;
            }
            String[] split2 = this.contentBeanList.get(this.selectedEditText).getRichData().split(",");
            boolean z6 = false;
            for (String str : split2) {
                if (!TextUtils.isEmpty(str) && ((str.startsWith(Constants.SPAN_BULLET_CHECK_BOX_N) || str.startsWith(Constants.SPAN_BULLET_CHECK_BOX)) && Integer.parseInt(str.substring(1)) == i8)) {
                    z6 = true;
                }
            }
            boolean z7 = false;
            for (String str2 : split2) {
                if (!TextUtils.isEmpty(str2) && str2.startsWith(Constants.SPAN_BULLET_Z) && Integer.parseInt(str2.substring(1)) == i8) {
                    z7 = true;
                }
            }
            if (z7) {
                this.delRow = true;
                Editable editableText = textView.getEditableText();
                textView.getEditableText().insert(selectionStart, "\n\u200b");
                int i10 = selectionStart + 2;
                MyBulletSpan[] myBulletSpanArr = editableText.getSpans(selectionStart, i10, MyBulletSpan.class);
                int length = myBulletSpanArr.length;
                while (i7 < length) {
                    editableText.removeSpan(myBulletSpanArr[i7]);
                    i7++;
                }
                android.widget.EditText editText = (android.widget.EditText) textView;
                MyBulletSpan myBulletSpan = new MyBulletSpan(editText, "digital", 1, MyBulletSpanHelper.createNewGroup(editText));
                if (selectionStart >= editableText.length() - 1) {
                    editableText.setSpan(myBulletSpan, selectionStart, selectionStart + 1, 33);
                } else {
                    editableText.setSpan(myBulletSpan, selectionStart + 1, i10, 33);
                }
                this.delRow = true;
                saveEditRich(this.editTextSelected, this.selectedEditText, true);
                return true;
            }
            if (z6) {
                if (selectionStart != textView.getSelectionStart()) {
                    return false;
                }
                Editable editableText2 = textView.getEditableText();
                textView.getEditableText().insert(textView.getSelectionStart(), "\n");
                android.widget.EditText editText2 = (android.widget.EditText) textView;
                MyBulletSpan myBulletSpan2 = new MyBulletSpan(editText2, "checkList_no", 1, MyBulletSpanHelper.createNewGroup(editText2));
                int i11 = selectionStart + 1;
                int i12 = selectionStart + 2;
                MyBulletSpan[] myBulletSpanArr2 = editableText2.getSpans(i11, i12, MyBulletSpan.class);
                int length2 = myBulletSpanArr2.length;
                while (i7 < length2) {
                    editableText2.removeSpan(myBulletSpanArr2[i7]);
                    i7++;
                }
                this.delRow = true;
                if (selectionStart >= editableText2.length() - 1) {
                    editableText2.setSpan(myBulletSpan2, selectionStart, i11, 33);
                } else {
                    editableText2.setSpan(myBulletSpan2, i11, i12, 33);
                }
                saveEditRich(this.editTextSelected, this.selectedEditText, true);
                return true;
            }
        }
        if (i6 != 5 && keyEvent != null) {
            keyEvent.getKeyCode();
        }
        return false;
    }

    public  boolean lambda$setEditListener$1(EditText editText, int[] iArr, EditText[] editTextArr, View view, int i6, KeyEvent keyEvent) {
        int i7;
        String str;
        String[] strArr;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        int i8;
        String[] strArr2;
        String str7;
        String str8;
        int i9;
        if (keyEvent.getAction() != 0 || i6 != 67) {
            return false;
        }
        int selectionStart = editText.getSelectionStart();
        String str9 = Constants.SPAN_S;
        String str10 = "h";
        String str11 = "f";
        String str12 = "";
        String str13 = Constants.SPAN_FONT_COLOR;
        int i10 = 1;
        if (selectionStart == 0) {
            this.cursorEditingStop = true;
            if (this.contentBeanList.get(iArr[0]).getViewType() > 0 && this.contentBeanList.get(iArr[0]).getViewType() < 4) {
                this.contentBeanList.get(iArr[0]).setViewType(0);
                this.contentBeanList.get(iArr[0]).setIndentation(0);
                notifyChanged(iArr[0]);
                notifyDataText();
                return false;
            }
            if (this.contentBeanList.get(iArr[0]).getViewType() == 0 && this.contentBeanList.get(iArr[0]).getIndentation() == 1) {
                this.contentBeanList.get(iArr[0]).setIndentation(-1);
                notifyChanged(iArr[0]);
                notifyDataText();
                return false;
            }
            int i11 = iArr[0];
            if (i11 == 0) {
                return false;
            }
            this.selectedEditText = i11 - 1;
            if (this.contentBeanList.get(i11 - 1).getViewType() >= 4) {
                if (this.contentBeanList.get(iArr[0] - 1).getViewType() <= 3 || this.contentBeanList.get(iArr[0]).getContent().length() != 0 || iArr[0] == this.contentBeanList.size() - 1) {
                    return false;
                }
                if (iArr[0] + 1 >= this.contentBeanList.size()) {
                    this.contentBeanList.remove(iArr[0]);
                    notifyItemRemoved(iArr[0]);
                } else if (this.contentBeanList.get(iArr[0] + 1).getViewType() == 4) {
                    List<Attachment> attachmentsList = this.contentBeanList.get(iArr[0] - 1).getAttachmentsList();
                    attachmentsList.addAll(this.contentBeanList.get(iArr[0] + 1).getAttachmentsList());
                    this.contentBeanList.get(iArr[0] - 1).setAttachmentsList(attachmentsList);
                    this.contentBeanList.remove(iArr[0]);
                    notifyItemRemoved(iArr[0]);
                    this.contentBeanList.remove(iArr[0]);
                    notifyItemRemoved(iArr[0]);
                } else {
                    this.contentBeanList.remove(iArr[0]);
                    notifyItemRemoved(iArr[0]);
                }
                notifyChanged(iArr[0] - 1);
                notifyDataText();
                return false;
            }
            this.delRow = true;
            cursorBeforeAfter = this.contentBeanList.get(iArr[0] - 1).getContent().length();
            StringBuilder sb = new StringBuilder();
            sb.append("onSelectedAreChanged: 003 ");
            sb.append(cursorBeforeAfter);
            this.contentBeanList.get(iArr[0] - 1).setContent(this.contentBeanList.get(iArr[0] - 1).getContent() + this.contentBeanList.get(iArr[0]).getContent());
            String[] split = this.contentBeanList.get(iArr[0]).getRichData().split(",");
            int length = split.length;
            String str14 = "";
            int i12 = 0;
            while (i12 < length) {
                String str15 = split[i12];
                if (TextUtils.isEmpty(str15)) {
                    strArr2 = split;
                    str7 = str11;
                    str8 = str12;
                    i9 = length;
                } else {
                    if (str15.startsWith(str11)) {
                        String substring = str15.substring(i10);
                        if (TextUtils.isEmpty(substring)) {
                            strArr2 = split;
                        } else {
                            String[] split2 = substring.split("/");
                            strArr2 = split;
                            if (split2.length == 3) {
                                int parseInt = Util.parseInt(split2[0]) + cursorBeforeAfter;
                                int parseInt2 = Util.parseInt(split2[1]) + cursorBeforeAfter;
                                i9 = length;
                                if (parseInt != -1 && parseInt2 != -1) {
                                    str14 = str14 + "," + str11 + parseInt + "/" + parseInt2 + "/" + Integer.parseInt(split2[2]);
                                }
                            }
                        }
                        i9 = length;
                    } else {
                        strArr2 = split;
                        i9 = length;
                        if (str15.startsWith(Constants.SPAN_FONT_COLOR)) {
                            String substring2 = str15.substring(1);
                            if (!TextUtils.isEmpty(substring2)) {
                                String[] split3 = substring2.split("/");
                                if (split3.length == 3) {
                                    int parseInt3 = Util.parseInt(split3[0]) + cursorBeforeAfter;
                                    int parseInt4 = Util.parseInt(split3[1]) + cursorBeforeAfter;
                                    str7 = str11;
                                    int parseInt5 = Util.parseInt(split3[2]);
                                    if (TextUtils.isEmpty(split3[2])) {
                                        str8 = str12;
                                    } else {
                                        str8 = str12;
                                        if (split3[2].startsWith(Constants.SPECIAL_CHARACTOR)) {
                                            if (parseInt3 != -1 && parseInt4 != -1 && parseInt5 != -1) {
                                                str14 = str14 + "," + Constants.SPAN_FONT_COLOR + parseInt3 + "/" + parseInt4 + "/" + parseInt5;
                                            }
                                        }
                                    }
                                    str14 = str14 + "," + Constants.SPAN_FONT_COLOR + parseInt3 + "/" + parseInt4 + "/" + Integer.parseInt(split3[2]);
                                }
                            }
                        } else {
                            str7 = str11;
                            str8 = str12;
                            if (str15.startsWith("h")) {
                                String substring3 = str15.substring(1);
                                if (!TextUtils.isEmpty(substring3)) {
                                    String[] split4 = substring3.split("/");
                                    if (split4.length == 3) {
                                        int parseInt6 = Util.parseInt(split4[0]) + cursorBeforeAfter;
                                        int parseInt7 = Util.parseInt(split4[1]) + cursorBeforeAfter;
                                        int parseInt8 = Util.parseInt(split4[2]);
                                        if (parseInt6 != -1 && parseInt7 != -1 && parseInt8 != -1) {
                                            str14 = str14 + ",h" + parseInt6 + "/" + parseInt7 + "/" + parseInt8;
                                        }
                                    }
                                }
                            } else if (str15.startsWith(Constants.SPAN_STRIKETHROUGH)) {
                                String substring4 = str15.substring(1);
                                if (!TextUtils.isEmpty(substring4)) {
                                    String[] split5 = substring4.split("/");
                                    if (split5.length == 2) {
                                        int parseInt9 = Util.parseInt(split5[0]) + cursorBeforeAfter;
                                        int parseInt10 = Util.parseInt(split5[1]) + cursorBeforeAfter;
                                        if (parseInt9 != -1 && parseInt10 != -1) {
                                            str14 = str14 + "," + Constants.SPAN_STRIKETHROUGH + parseInt9 + "/" + parseInt10;
                                        }
                                    }
                                }
                            } else if (str15.startsWith(Constants.SPAN_U)) {
                                String substring5 = str15.substring(1);
                                if (!TextUtils.isEmpty(substring5)) {
                                    String[] split6 = substring5.split("/");
                                    if (split6.length == 2) {
                                        int parseInt11 = Util.parseInt(split6[0]) + cursorBeforeAfter;
                                        int parseInt12 = Util.parseInt(split6[1]) + cursorBeforeAfter;
                                        if (parseInt11 != -1 && parseInt12 != -1) {
                                            str14 = str14 + "," + Constants.SPAN_U + parseInt11 + "/" + parseInt12;
                                        }
                                    }
                                }
                            } else if (str15.startsWith(Constants.SPAN_S)) {
                                String substring6 = str15.substring(1);
                                if (!TextUtils.isEmpty(substring6)) {
                                    String[] split7 = substring6.split("/");
                                    if (split7.length == 3) {
                                        int parseInt13 = Util.parseInt(split7[0]) + cursorBeforeAfter;
                                        int parseInt14 = Util.parseInt(split7[1]) + cursorBeforeAfter;
                                        int parseInt15 = Util.parseInt(split7[2]);
                                        if (parseInt13 != -1 && parseInt14 != -1 && parseInt15 != -1) {
                                            str14 = str14 + "," + Constants.SPAN_S + parseInt13 + "/" + parseInt14 + "/" + parseInt15;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    str7 = str11;
                    str8 = str12;
                }
                i12++;
                split = strArr2;
                length = i9;
                str12 = str8;
                str11 = str7;
                i10 = 1;
            }
            this.contentBeanList.get(iArr[0] - 1).setRichData((this.contentBeanList.get(iArr[0] - 1).getRichData() + str14).replace(",,", ","));
            this.contentBeanList.get(iArr[0]).setContent(str12);
            this.contentBeanList.remove(iArr[0]);
            notifyRemoved(iArr[0]);
            notifyChanged(iArr[0] - 1);
            notifyDataText();
            return false;
        }
        String str16 = "f";
        int selectionStart2 = editText.getSelectionStart();
        String[] split8 = this.contentBeanList.get(this.selectedEditText).getContent().split("\n");
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        while (true) {
            if (i13 >= split8.length) {
                i13 = 0;
                int i16 = i15;
                i7 = i14;
                i14 = i16;
                break;
            }
            i7 = split8[i13].length() + i14 + 1;
            if (selectionStart2 <= i7) {
                break;
            }
            i13++;
            i15 = i14;
            i14 = i7;
        }
        if (split8.length == 0) {
            return false;
        }
        int i17 = cursorBeforeAfter;
        if (i17 != i14 + 1 && i17 != i14 + 2 && i17 != i7) {
            return false;
        }
        String[] split9 = this.contentBeanList.get(this.selectedEditText).getRichData().split(",");
        int length2 = split9.length;
        int i18 = 0;
        boolean z6 = false;
        while (true) {
            str = Constants.SPAN_BULLET_Z;
            if (i18 >= length2) {
                break;
            }
            int i19 = length2;
            String str17 = split9[i18];
            if (!TextUtils.isEmpty(str17) && str17.startsWith(Constants.SPAN_BULLET_Z) && Integer.parseInt(str17.substring(1)) == i13) {
                z6 = true;
            }
            i18++;
            length2 = i19;
        }
        if (!z6) {
            return false;
        }
        if (cursorBeforeAfter != split8[0].length() + 1) {
            this.delRow = true;
            setTextStyle(true);
            return false;
        }
        StringBuilder sb2 = new StringBuilder();
        this.delRow = true;
        saveEditRich(editTextArr[0], iArr[0], false);
        int i20 = 0;
        while (i20 < split9.length) {
            String str18 = split9[i20];
            if (!TextUtils.isEmpty(str18)) {
                if (str18.startsWith(Constants.SPAN_BULLET_D) || str18.startsWith(str) || str18.startsWith(Constants.SPAN_BULLET_CHECK_BOX_N)) {
                    strArr = split9;
                    str2 = str;
                    str3 = str10;
                    str4 = str13;
                    str5 = str16;
                    str6 = str9;
                    i8 = 1;
                } else if (str18.startsWith(Constants.SPAN_BULLET_CHECK_BOX)) {
                    strArr = split9;
                    str2 = str;
                    str3 = str10;
                    str4 = str13;
                    str5 = str16;
                    i8 = 1;
                    str6 = str9;
                } else if (!TextUtils.isEmpty(str18)) {
                    str5 = str16;
                    if (str18.startsWith(str5) || str18.startsWith(str13) || str18.startsWith(str10) || str18.startsWith(str9)) {
                        strArr = split9;
                        str2 = str;
                        str6 = str9;
                        String substring7 = str18.substring(1);
                        if (!TextUtils.isEmpty(substring7)) {
                            String[] split10 = substring7.split("/");
                            if (split10.length == 3) {
                                int parseInt16 = Util.parseInt(split10[0]);
                                int parseInt17 = Util.parseInt(split10[1]);
                                str3 = str10;
                                if (i14 != -1 && i7 != -1) {
                                    int i21 = cursorBeforeAfter;
                                    if (parseInt16 >= i21 || parseInt17 <= i21) {
                                        str4 = str13;
                                        if (parseInt17 > i21) {
                                            sb2.append(str18.charAt(0));
                                            sb2.append(parseInt16 - 1);
                                            sb2.append("/");
                                            sb2.append(parseInt17 - 1);
                                            sb2.append("/");
                                            sb2.append(split10[2]);
                                            sb2.append(",");
                                        } else {
                                            sb2.append(str18);
                                            sb2.append(",");
                                        }
                                        i20++;
                                        split9 = strArr;
                                        str = str2;
                                        str9 = str6;
                                        str10 = str3;
                                        str13 = str4;
                                        str16 = str5;
                                    } else {
                                        str4 = str13;
                                        sb2.append(str18.charAt(0));
                                        sb2.append(parseInt16);
                                        sb2.append("/");
                                        sb2.append(parseInt17 - 1);
                                        sb2.append("/");
                                        sb2.append(split10[2]);
                                        sb2.append(",");
                                        i20++;
                                        split9 = strArr;
                                        str = str2;
                                        str9 = str6;
                                        str10 = str3;
                                        str13 = str4;
                                        str16 = str5;
                                    }
                                }
                            }
                        }
                        str3 = str10;
                    } else {
                        if (str18.startsWith(Constants.SPAN_U)) {
                            strArr = split9;
                            str2 = str;
                            String substring8 = str18.substring(1);
                            if (TextUtils.isEmpty(substring8)) {
                                str6 = str9;
                            } else {
                                String[] split11 = substring8.split("/");
                                str6 = str9;
                                if (split11.length == 2) {
                                    int parseInt18 = Util.parseInt(split11[0]);
                                    int parseInt19 = Util.parseInt(split11[1]);
                                    int i22 = cursorBeforeAfter;
                                    if (parseInt18 < i22 && parseInt19 > i22) {
                                        sb2.append(Constants.SPAN_U);
                                        sb2.append(parseInt18);
                                        sb2.append("/");
                                        sb2.append(parseInt19 - 1);
                                        sb2.append(",");
                                    } else if (parseInt19 > i22) {
                                        sb2.append(Constants.SPAN_U);
                                        sb2.append(parseInt18 - 1);
                                        sb2.append("/");
                                        sb2.append(parseInt19 - 1);
                                        sb2.append(",");
                                    } else {
                                        sb2.append(str18);
                                        sb2.append(",");
                                    }
                                }
                            }
                        } else {
                            strArr = split9;
                            str2 = str;
                            str6 = str9;
                            if (str18.startsWith(Constants.SPAN_STRIKETHROUGH)) {
                                String substring9 = str18.substring(1);
                                if (!TextUtils.isEmpty(substring9)) {
                                    String[] split12 = substring9.split("/");
                                    if (split12.length == 2) {
                                        int parseInt20 = Util.parseInt(split12[0]);
                                        int parseInt21 = Util.parseInt(split12[1]);
                                        int i23 = cursorBeforeAfter;
                                        if (parseInt20 < i23 && parseInt21 > i23) {
                                            sb2.append(Constants.SPAN_STRIKETHROUGH);
                                            sb2.append(parseInt20);
                                            sb2.append("/");
                                            sb2.append(parseInt21 - 1);
                                            sb2.append(",");
                                        } else if (parseInt21 > i23) {
                                            sb2.append(Constants.SPAN_STRIKETHROUGH);
                                            sb2.append(parseInt20 - 1);
                                            sb2.append("/");
                                            sb2.append(parseInt21 - 1);
                                            sb2.append(",");
                                        } else {
                                            sb2.append(str18);
                                            sb2.append(",");
                                        }
                                    } else {
                                        str3 = str10;
                                        str4 = str13;
                                        i20++;
                                        split9 = strArr;
                                        str = str2;
                                        str9 = str6;
                                        str10 = str3;
                                        str13 = str4;
                                        str16 = str5;
                                    }
                                }
                            }
                        }
                        str3 = str10;
                    }
                    str4 = str13;
                    i20++;
                    split9 = strArr;
                    str = str2;
                    str9 = str6;
                    str10 = str3;
                    str13 = str4;
                    str16 = str5;
                }
                int parseInt22 = Integer.parseInt(str18.substring(i8));
                if (parseInt22 > 0) {
                    sb2.append(str18.charAt(0));
                    sb2.append(parseInt22 - 1);
                    sb2.append(",");
                } else {
                    sb2.append(str18.charAt(0));
                    sb2.append(parseInt22);
                    sb2.append(",");
                }
                i20++;
                split9 = strArr;
                str = str2;
                str9 = str6;
                str10 = str3;
                str13 = str4;
                str16 = str5;
            }
            strArr = split9;
            str2 = str;
            str3 = str10;
            str4 = str13;
            str5 = str16;
            str6 = str9;
            i20++;
            split9 = strArr;
            str = str2;
            str9 = str6;
            str10 = str3;
            str13 = str4;
            str16 = str5;
        }
        String substring10 = this.contentBeanList.get(this.selectedEditText).getContent().substring(0, cursorBeforeAfter - 1);
        String substring11 = cursorBeforeAfter >= this.contentBeanList.get(this.selectedEditText).getContent().length() ? "" : this.contentBeanList.get(this.selectedEditText).getContent().substring(cursorBeforeAfter);
        this.contentBeanList.get(this.selectedEditText).setContent(substring10 + substring11);
        this.contentBeanList.get(this.selectedEditText).setRichData(sb2.toString());
        this.delRow = true;
        cursorBeforeAfter = cursorBeforeAfter - 1;
        notifyDataText();
        return true;
    }

    public  void lambda$setEditListener$2(RecyclerView.ViewHolder viewHolder, int[] iArr, EditText[] editTextArr, View view, boolean z6) {
        BaseEditView.PicClicklistener picClicklistener = this.picClicklistener;
        if (picClicklistener != null) {
            picClicklistener.onFocusChange(z6);
        }
        if (viewHolder.getAdapterPosition() != -1) {
            iArr[0] = viewHolder.getAdapterPosition();
        }
        if (z6) {
            this.selectedEditText = iArr[0];
            this.editTextSelected = editTextArr[0];
        }
    }

    public  boolean lambda$setEditListener$3(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            if (motionEvent.getAction() != 1) {
                return false;
            }
            this.contentTouch = true;
            return false;
        }
        BaseEditView.PicClicklistener picClicklistener = this.picClicklistener;
        if (picClicklistener == null) {
            return false;
        }
        picClicklistener.onEditTouch();
        return false;
    }

    public  void lambda$setRichText$12(EditText editText, int i6, int i7, int i8) {
        setAbsoluteFontSize(editText, i6, i7, i8, false);
    }

    public  void lambda$setRichText$13(EditText editText, int i6, int i7, int i8) {
        setTextFontColor(editText, i6, i7, i8, false);
    }

    public  void lambda$setRichText$14(EditText editText, int i6, int i7, int i8) {
        setTextFontColor(editText, i6, i7, i8, false);
    }

    public  void lambda$setRichText$15(EditText editText, int i6, int i7, int i8) {
        setTextBackground(editText, editText.getEditableText(), i6, i7, i8, false);
    }

    public  void lambda$setRichText$16(EditText editText, int i6, int i7) {
        setTextStrikethrough(editText.getEditableText(), true, i6, i7);
    }

    public  void lambda$setRichText$17(EditText editText, int i6, int i7) {
        setTextUnderLine(editText.getEditableText(), true, i6, i7);
    }

    public  void lambda$setRichText$18(int i6, EditText editText, int i7, int i8) {
        if (i6 == 1) {
            setTextBold(editText, editText.getEditableText(), true, i7, i8, false);
        } else if (i6 == 2) {
            setTextItalic(editText, editText.getEditableText(), true, i7, i8, false);
        }
    }

    public  void lambda$setRichText$19(EditText editText, int i6) {
        onNumListSelected(editText, editText.getEditableText(), "Dots", i6);
    }

    public  void lambda$setRichText$20(EditText editText, int i6) {
        onNumListSelected(editText, editText.getEditableText(), "digital", i6);
    }

    public  void lambda$setRichText$21(EditText editText, int i6) {
        onNumListSelected(editText, editText.getEditableText(), "checkList_selected", i6);
    }

    public  void lambda$setRichText$22(EditText editText, int i6) {
        if (this.dark) {
            onNumListSelected(editText, editText.getEditableText(), "checkList_no_white", i6);
        } else {
            onNumListSelected(editText, editText.getEditableText(), "checkList_no", i6);
        }
    }

    private void newLine(EditText editText, int[] iArr, int i6) {
        int i7;
        String str;
        String str2;
        String[] strArr;
        String str3;
        String str4;
        if (iArr[0] == -1) {
            return;
        }
        String str5 = "";
        if (i6 == editText.getText().length()) {
            EditContentBean newBean = EditContentBean.newBean(this.contentBeanList.get(iArr[0]).getViewType());
            newBean.setContent("");
            saveEditRich(editText, iArr[0], false);
            this.contentBeanList.add(iArr[0] + 1, newBean);
            return;
        }
        String content = this.contentBeanList.get(iArr[0]).getContent();
        String substring = content.substring(i6);
        String substring2 = content.substring(0, i6);
        saveEditRich(editText, iArr[0], false);
        String[] split = this.contentBeanList.get(iArr[0]).getRichData().split(",");
        String str6 = "\n";
        String[] split2 = editText.getText().toString().split("\n");
        int i8 = 0;
        int i9 = 0;
        while (true) {
            if (i8 >= split2.length) {
                i8 = -1;
                break;
            }
            i9 = i9 + split2[i8].length() + 1;
            if (i6 < i9) {
                break;
            } else {
                i8++;
            }
        }
        if (i8 == -1) {
            i8 = split2.length - 1;
        }
        int length = split.length;
        String str7 = "";
        int i10 = 0;
        boolean z6 = false;
        while (i10 < length) {
            String str8 = split[i10];
            if (TextUtils.isEmpty(str8)) {
                i7 = length;
                str = substring2;
                str2 = substring;
                strArr = split;
            } else if (str8.startsWith("f")) {
                i7 = length;
                String substring3 = str8.substring(1);
                if (TextUtils.isEmpty(substring3)) {
                    str = substring2;
                    strArr = split;
                } else {
                    String[] split3 = substring3.split("/");
                    strArr = split;
                    if (split3.length == 3) {
                        int parseInt = Util.parseInt(split3[0]);
                        int parseInt2 = Util.parseInt(split3[1]);
                        str = substring2;
                        if (parseInt != -1 && parseInt2 != -1) {
                            int parseInt3 = Integer.parseInt(split3[2]);
                            if (parseInt > i6) {
                                str7 = str7 + ",f" + (parseInt - i6) + "/" + (parseInt2 - i6) + "/" + parseInt3;
                            } else if (parseInt2 > i6) {
                                str5 = str5 + ",f" + parseInt + "/" + i6 + "/" + parseInt3;
                                str7 = str7 + ",f0/" + (parseInt2 - i6) + "/" + parseInt3;
                            } else {
                                str5 = str5 + ",f" + parseInt + "/" + parseInt2 + "/" + parseInt3;
                            }
                        }
                    } else {
                        str = substring2;
                    }
                }
                str2 = substring;
            } else {
                i7 = length;
                str = substring2;
                strArr = split;
                if (str8.startsWith(Constants.SPAN_FONT_COLOR)) {
                    String substring4 = str8.substring(1);
                    if (!TextUtils.isEmpty(substring4)) {
                        String[] split4 = substring4.split("/");
                        if (split4.length == 3) {
                            int parseInt4 = Util.parseInt(split4[0]);
                            int parseInt5 = Util.parseInt(split4[1]);
                            str2 = substring;
                            int parseInt6 = Util.parseInt(split4[2]);
                            str3 = str6;
                            if (TextUtils.isEmpty(split4[2]) || !split4[2].startsWith(Constants.SPECIAL_CHARACTOR)) {
                                int parseInt7 = Integer.parseInt(split4[2]);
                                if (parseInt4 > i6) {
                                    str7 = str7 + "," + Constants.SPAN_FONT_COLOR + (parseInt4 - i6) + "/" + (parseInt5 - i6) + "/" + parseInt7;
                                } else if (parseInt5 > i6) {
                                    str5 = str5 + "," + Constants.SPAN_FONT_COLOR + parseInt4 + "/" + i6 + "/" + parseInt7;
                                    str7 = str7 + "," + Constants.SPAN_FONT_COLOR + "0/" + (parseInt5 - i6) + "/" + parseInt7;
                                } else {
                                    str5 = str5 + "," + Constants.SPAN_FONT_COLOR + parseInt4 + "/" + parseInt5 + "/" + parseInt7;
                                }
                            } else if (parseInt4 != -1 && parseInt5 != -1 && parseInt6 != -1) {
                                if (parseInt4 > i6) {
                                    str7 = str7 + "," + Constants.SPAN_FONT_COLOR + (parseInt4 - i6) + "/" + (parseInt5 - i6) + "/" + parseInt6;
                                } else if (parseInt5 > i6) {
                                    str5 = str5 + "," + Constants.SPAN_FONT_COLOR + parseInt4 + "/" + i6 + "/" + parseInt6;
                                    str7 = str7 + "," + Constants.SPAN_FONT_COLOR + "0/" + (parseInt5 - i6) + "/" + parseInt6;
                                } else {
                                    str5 = str5 + "," + Constants.SPAN_FONT_COLOR + parseInt4 + "/" + parseInt5 + "/" + parseInt6;
                                }
                            }
                        }
                    }
                    str2 = substring;
                } else {
                    str2 = substring;
                    str3 = str6;
                    if (str8.startsWith("h")) {
                        String substring5 = str8.substring(1);
                        if (!TextUtils.isEmpty(substring5)) {
                            String[] split5 = substring5.split("/");
                            if (split5.length == 3) {
                                int parseInt8 = Util.parseInt(split5[0]);
                                int parseInt9 = Util.parseInt(split5[1]);
                                int parseInt10 = Util.parseInt(split5[2]);
                                if (parseInt8 != -1 && parseInt9 != -1 && parseInt10 != -1) {
                                    if (parseInt8 > i6) {
                                        str7 = str7 + ",h" + (parseInt8 - i6) + "/" + (parseInt9 - i6) + "/" + parseInt10;
                                    } else if (parseInt9 > i6) {
                                        str5 = str5 + ",h" + parseInt8 + "/" + i6 + "/" + parseInt10;
                                        str7 = str7 + ",h0/" + (parseInt9 - i6) + "/" + parseInt10;
                                    } else {
                                        str5 = str5 + ",h" + parseInt8 + "/" + parseInt9 + "/" + parseInt10;
                                    }
                                }
                            }
                        }
                    } else if (str8.startsWith(Constants.SPAN_STRIKETHROUGH)) {
                        String substring6 = str8.substring(1);
                        if (!TextUtils.isEmpty(substring6)) {
                            String[] split6 = substring6.split("/");
                            if (split6.length == 2) {
                                int parseInt11 = Util.parseInt(split6[0]);
                                int parseInt12 = Util.parseInt(split6[1]);
                                if (parseInt11 != -1 && parseInt12 != -1) {
                                    if (parseInt11 > i6) {
                                        str7 = str7 + "," + Constants.SPAN_STRIKETHROUGH + (parseInt11 - i6) + "/" + (parseInt12 - i6);
                                    } else if (parseInt12 > i6) {
                                        str5 = str5 + "," + Constants.SPAN_STRIKETHROUGH + parseInt11 + "/" + i6;
                                        str7 = str7 + "," + Constants.SPAN_STRIKETHROUGH + "0/" + (parseInt12 - i6);
                                    } else {
                                        str5 = str5 + "," + Constants.SPAN_STRIKETHROUGH + parseInt11 + "/" + parseInt12;
                                    }
                                }
                            }
                        }
                    } else if (str8.startsWith(Constants.SPAN_U)) {
                        String substring7 = str8.substring(1);
                        if (!TextUtils.isEmpty(substring7)) {
                            String[] split7 = substring7.split("/");
                            if (split7.length == 2) {
                                int parseInt13 = Util.parseInt(split7[0]);
                                int parseInt14 = Util.parseInt(split7[1]);
                                if (parseInt13 != -1 && parseInt14 != -1) {
                                    if (parseInt13 > i6) {
                                        str7 = str7 + "," + Constants.SPAN_U + (parseInt13 - i6) + "/" + (parseInt14 - i6);
                                    } else if (parseInt14 > i6) {
                                        str5 = str5 + "," + Constants.SPAN_U + parseInt13 + "/" + i6;
                                        str7 = str7 + "," + Constants.SPAN_U + "0/" + (parseInt14 - i6);
                                    } else {
                                        str5 = str5 + "," + Constants.SPAN_U + parseInt13 + "/" + parseInt14;
                                    }
                                }
                            }
                        }
                    } else {
                        if (str8.startsWith(Constants.SPAN_S)) {
                            String substring8 = str8.substring(1);
                            if (!TextUtils.isEmpty(substring8)) {
                                String[] split8 = substring8.split("/");
                                if (split8.length == 3) {
                                    int parseInt15 = Util.parseInt(split8[0]);
                                    int parseInt16 = Util.parseInt(split8[1]);
                                    int parseInt17 = Util.parseInt(split8[2]);
                                    if (parseInt15 != -1 && parseInt16 != -1 && parseInt17 != -1) {
                                        if (parseInt15 > i6) {
                                            str7 = str7 + "," + Constants.SPAN_S + (parseInt15 - i6) + "/" + (parseInt16 - i6) + "/" + parseInt17;
                                        } else if (parseInt16 > i6) {
                                            str5 = str5 + "," + Constants.SPAN_S + parseInt15 + "/" + i6 + "/" + parseInt17;
                                            str7 = str7 + "," + Constants.SPAN_S + "0/" + (parseInt16 - i6) + "/" + parseInt17;
                                        } else {
                                            str5 = str5 + "," + Constants.SPAN_S + parseInt15 + "/" + parseInt16 + "/" + parseInt17;
                                        }
                                    }
                                }
                            }
                        } else if (str8.startsWith(Constants.SPAN_BULLET_D) || str8.startsWith(Constants.SPAN_BULLET_Z) || str8.startsWith(Constants.SPAN_BULLET_CHECK_BOX_N) || str8.startsWith(Constants.SPAN_BULLET_CHECK_BOX)) {
                            String substring9 = str8.substring(1);
                            String substring10 = str8.substring(0, 1);
                            int parseInt18 = Integer.parseInt(substring9);
                            if (TextUtils.isEmpty(substring9)) {
                                return;
                            }
                            if (i8 < parseInt18) {
                                if (i6 != i9 - 1) {
                                    str4 = str7 + "," + substring10 + (parseInt18 - i8);
                                } else {
                                    str4 = str7 + "," + substring10 + ((parseInt18 - i8) - 1);
                                }
                                str7 = str4;
                            } else if (i8 > parseInt18) {
                                str5 = str5 + "," + substring10 + parseInt18;
                            } else {
                                String str9 = str5 + "," + substring10 + parseInt18;
                                String str10 = substring10 + (parseInt18 - i8);
                                if (!str7.contains(str10) && i6 != i9 - 1) {
                                    str7 = str7 + "," + str10;
                                }
                                str5 = str9;
                            }
                            z6 = true;
                        }
                        i10++;
                        length = i7;
                        split = strArr;
                        substring2 = str;
                        substring = str2;
                        str6 = str3;
                    }
                }
                i10++;
                length = i7;
                split = strArr;
                substring2 = str;
                substring = str2;
                str6 = str3;
            }
            str3 = str6;
            i10++;
            length = i7;
            split = strArr;
            substring2 = str;
            substring = str2;
            str6 = str3;
        }
        String str11 = substring2;
        String str12 = substring;
        String str13 = str6;
        EditContentBean newBean2 = EditContentBean.newBean(this.contentBeanList.get(iArr[0]).getViewType());
        if (!z6) {
            newBean2.setContent(str12);
        } else if (str12.indexOf(str13) == 0) {
            newBean2.setContent(Constants.ZERO_WIDTH_SPACE_STR + str12.substring(1));
        } else {
            newBean2.setContent(Constants.ZERO_WIDTH_SPACE_STR + str12);
        }
        newBean2.setRichData(str7.replace(",,", ","));
        EditContentBean newBean3 = EditContentBean.newBean(this.contentBeanList.get(iArr[0]).getViewType());
        newBean3.setContent(str11);
        newBean3.setSelected(this.contentBeanList.get(iArr[0]).isSelected());
        newBean3.setRichData(str5.replace(",,", ","));
        this.contentBeanList.add(iArr[0] + 1, newBean2);
        this.contentBeanList.add(iArr[0] + 1, newBean3);
        this.contentBeanList.remove(iArr[0]);
    }

    private void notifyDataAll() {
        Message message = new Message();
        message.what = 0;
        message.arg1 = 0;
        this.mRefreshHandler.sendMessage(message);
    }

    public void saveEditRich(EditText editText, int i6, boolean z6) {
        saveEditRich(editText, i6, z6, false);
    }

    private void setAbsoluteFontSize(EditText editText, int i6, int i7, int i8, boolean z6) {
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

    private void setCheck(ViewHolderCheck viewHolderCheck, int i6, boolean z6) {
        if (this.searchText.length() > 0 && viewHolderCheck.content.getText() != null && viewHolderCheck.content.getText().toString().toLowerCase().contains(this.searchText)) {
            int indexOf = viewHolderCheck.content.getText().toString().toLowerCase().indexOf(this.searchText);
            while (indexOf != -1) {
                EditText editText = viewHolderCheck.content;
                setTextBackground(editText, editText.getText(), indexOf, indexOf + this.searchText.length(), Color.parseColor(Constants.HIGHLIGHT_COLOR), false);
                this.searchEditIndex.add(indexOf + "," + (this.searchText.length() + indexOf));
                indexOf = viewHolderCheck.content.getText().toString().toLowerCase().indexOf(this.searchText, indexOf + 1);
            }
        }
        if (z6) {
            viewHolderCheck.checkBox.setBackgroundResource(R.drawable.ic_checklist_svg);
            viewHolderCheck.content.getPaint().setFlags(17);
            viewHolderCheck.content.setTextColor(this.mActivity.getResources().getColor(R.color.color_52001C30));
            if (this.dark) {
                viewHolderCheck.content.setTextColor(ContextCompat.getColor(this.mActivity, R.color.white_48alpha_7afff));
            } else {
                viewHolderCheck.content.setTextColor(ContextCompat.getColor(this.mActivity, R.color.black_48alpha_7a000));
            }
        } else {
            viewHolderCheck.content.getPaint().setFlags(0);
            viewHolderCheck.content.getPaint().setAntiAlias(true);
            if (this.dark) {
                viewHolderCheck.checkBox.setBackgroundResource(R.drawable.shape_checklist_unchecked_white);
                viewHolderCheck.content.setTextColor(ContextCompat.getColor(this.mActivity, R.color.white_86alpha_dbfff));
            } else {
                viewHolderCheck.checkBox.setBackgroundResource(R.drawable.shape_checklist_unchecked);
                viewHolderCheck.content.setTextColor(ContextCompat.getColor(this.mActivity, R.color.black_87alpha_df000));
            }
        }
        this.contentBeanList.get(i6).setSelected(z6);
    }

    public static void setCursorBeforeAfter(int i6) {
        cursorBeforeAfter = i6;
    }

    private void setRichText(final EditText editText, String str, int i6) {
        if (editText.getText().length() == 0) {
            return;
        }
        for (String str2 : str.split(",")) {
            if (!TextUtils.isEmpty(str2)) {
                if (str2.startsWith("f")) {
                    String substring = str2.substring(1);
                    if (!TextUtils.isEmpty(substring)) {
                        String[] split = substring.split("/");
                        if (split.length == 3) {
                            final int parseInt = Util.parseInt(split[0]) - i6;
                            final int parseInt2 = Util.parseInt(split[1]) - i6;
                            if (parseInt != -1 && parseInt2 != -1) {
                                final int parseInt3 = Integer.parseInt(split[2]);
                                this.mActivity.runOnUiThread(new Runnable() { // from class: a6.v
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EditContentAdapter.this.lambda$setRichText$12(editText, parseInt, parseInt2, parseInt3);
                                    }
                                });
                            }
                        }
                    }
                } else if (str2.startsWith(Constants.SPAN_FONT_COLOR)) {
                    String substring2 = str2.substring(1);
                    if (!TextUtils.isEmpty(substring2)) {
                        String[] split2 = substring2.split("/");
                        if (split2.length == 3) {
                            final int parseInt4 = Util.parseInt(split2[0]) - i6;
                            final int parseInt5 = Util.parseInt(split2[1]) - i6;
                            final int parseInt6 = Util.parseInt(split2[2]);
                            if (TextUtils.isEmpty(split2[2]) || !split2[2].startsWith(Constants.SPECIAL_CHARACTOR)) {
                                final int parseInt7 = Integer.parseInt(split2[2]);
                                if (parseInt4 != -1 && parseInt5 != -1 && parseInt6 != -1) {
                                    this.mActivity.runOnUiThread(new Runnable() { // from class: a6.y
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            EditContentAdapter.this.lambda$setRichText$14(editText, parseInt4, parseInt5, parseInt7);
                                        }
                                    });
                                }
                            } else if (parseInt4 != -1 && parseInt5 != -1 && parseInt6 != -1) {
                                this.mActivity.runOnUiThread(new Runnable() { // from class: a6.x
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EditContentAdapter.this.lambda$setRichText$13(editText, parseInt4, parseInt5, parseInt6);
                                    }
                                });
                            }
                        }
                    }
                } else if (str2.startsWith("h")) {
                    String substring3 = str2.substring(1);
                    if (!TextUtils.isEmpty(substring3)) {
                        String[] split3 = substring3.split("/");
                        if (split3.length == 3) {
                            final int parseInt8 = Util.parseInt(split3[0]) - i6;
                            final int parseInt9 = Util.parseInt(split3[1]) - i6;
                            final int parseInt10 = Util.parseInt(split3[2]);
                            if (parseInt8 != -1 && parseInt9 != -1 && parseInt10 != -1) {
                                this.mActivity.runOnUiThread(new Runnable() { // from class: a6.k
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EditContentAdapter.this.lambda$setRichText$15(editText, parseInt8, parseInt9, parseInt10);
                                    }
                                });
                            }
                        }
                    }
                } else if (str2.startsWith(Constants.SPAN_STRIKETHROUGH)) {
                    String substring4 = str2.substring(1);
                    if (!TextUtils.isEmpty(substring4)) {
                        String[] split4 = substring4.split("/");
                        if (split4.length == 2) {
                            final int parseInt11 = Util.parseInt(split4[0]) - i6;
                            final int parseInt12 = Util.parseInt(split4[1]) - i6;
                            if (parseInt11 != -1 && parseInt12 != -1) {
                                this.mActivity.runOnUiThread(new Runnable() { // from class: a6.l
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EditContentAdapter.this.lambda$setRichText$16(editText, parseInt11, parseInt12);
                                    }
                                });
                            }
                        }
                    }
                } else if (str2.startsWith(Constants.SPAN_U)) {
                    String substring5 = str2.substring(1);
                    if (!TextUtils.isEmpty(substring5)) {
                        String[] split5 = substring5.split("/");
                        if (split5.length == 2) {
                            final int parseInt13 = Util.parseInt(split5[0]) - i6;
                            final int parseInt14 = Util.parseInt(split5[1]) - i6;
                            if (parseInt13 != -1 && parseInt14 != -1) {
                                this.mActivity.runOnUiThread(new Runnable() { // from class: a6.m
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EditContentAdapter.this.lambda$setRichText$17(editText, parseInt13, parseInt14);
                                    }
                                });
                            }
                        }
                    }
                } else if (str2.startsWith(Constants.SPAN_S)) {
                    String substring6 = str2.substring(1);
                    if (!TextUtils.isEmpty(substring6)) {
                        String[] split6 = substring6.split("/");
                        if (split6.length == 3) {
                            final int parseInt15 = Util.parseInt(split6[0]) - i6;
                            final int parseInt16 = Util.parseInt(split6[1]) - i6;
                            final int parseInt17 = Util.parseInt(split6[2]);
                            if (parseInt15 != -1 && parseInt16 != -1 && parseInt17 != -1) {
                                this.mActivity.runOnUiThread(new Runnable() { // from class: a6.n
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        EditContentAdapter.this.lambda$setRichText$18(parseInt17, editText, parseInt15, parseInt16);
                                    }
                                });
                            }
                        }
                    }
                } else if (str2.startsWith(Constants.SPAN_BULLET_D)) {
                    final int parseInt18 = Util.parseInt(str2.substring(1));
                    this.mActivity.runOnUiThread(new Runnable() { // from class: a6.o
                        @Override // java.lang.Runnable
                        public void run() {
                            EditContentAdapter.this.lambda$setRichText$19(editText, parseInt18);
                        }
                    });
                } else if (str2.startsWith(Constants.SPAN_BULLET_Z)) {
                    final int parseInt19 = Util.parseInt(str2.substring(1));
                    this.mActivity.runOnUiThread(new Runnable() { // from class: a6.p
                        @Override // java.lang.Runnable
                        public void run() {
                            EditContentAdapter.this.lambda$setRichText$20(editText, parseInt19);
                        }
                    });
                } else if (str2.startsWith(Constants.SPAN_BULLET_CHECK_BOX)) {
                    final int parseInt20 = Util.parseInt(str2.substring(1));
                    this.mActivity.runOnUiThread(new Runnable() { // from class: a6.q
                        @Override // java.lang.Runnable
                        public void run() {
                            EditContentAdapter.this.lambda$setRichText$21(editText, parseInt20);
                        }
                    });
                } else if (str2.startsWith(Constants.SPAN_BULLET_CHECK_BOX_N)) {
                    final int parseInt21 = Util.parseInt(str2.substring(1));
                    this.mActivity.runOnUiThread(new Runnable() { // from class: a6.w
                        @Override // java.lang.Runnable
                        public void run() {
                            EditContentAdapter.this.lambda$setRichText$22(editText, parseInt21);
                        }
                    });
                }
            }
        }
    }

    private void setTextBackground(EditText editText, Editable editable, int i6, int i7, int i8, boolean z6) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        editText.getEditableText().setSpan(new BackgroundColorSpan(i8), i6, i7, 33);
        if (z6) {
            editText.setSelection(i6, i7);
        }
    }

    private void setTextBold(EditText editText, Editable editable, boolean z6, int i6, int i7, boolean z7) {
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
        StyleSpan[] styleSpanArr = editable.getSpans(i6, i7, StyleSpan.class);
        for (StyleSpan styleSpan : styleSpanArr) {
            editable.removeSpan(styleSpan);
        }
    }

    private void setTextFontColor(EditText editText, int i6, int i7, int i8, boolean z6) {
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

    private void setTextItalic(EditText editText, Editable editable, boolean z6, int i6, int i7, boolean z7) {
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
        StyleSpan[] styleSpanArr = editable.getSpans(i6, i7, StyleSpan.class);
        for (StyleSpan styleSpan : styleSpanArr) {
            editable.removeSpan(styleSpan);
        }
    }

    private void setTextStrikethrough(Editable editable, boolean z6, int i6, int i7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        if (z6) {
            editable.setSpan(new StrikethroughSpan(), i6, i7, 33);
            return;
        }
        StrikethroughSpan[] strikethroughSpanArr = editable.getSpans(i6, i7, StrikethroughSpan.class);
        for (StrikethroughSpan strikethroughSpan : strikethroughSpanArr) {
            editable.removeSpan(strikethroughSpan);
        }
    }

    private void setTextUnderLine(Editable editable, boolean z6, int i6, int i7) {
        if (editable == null || i7 <= i6 || editable.length() < i7) {
            return;
        }
        if (z6) {
            editable.setSpan(new UnderlineSpan(), i6, i7, 33);
            return;
        }
        UnderlineSpan[] underlineSpanArr = editable.getSpans(i6, i7, UnderlineSpan.class);
        for (UnderlineSpan underlineSpan : underlineSpanArr) {
            editable.removeSpan(underlineSpan);
        }
    }

    public void addImg(Attachment attachment) {
        long currentTimeMillis = System.currentTimeMillis();
        this.cursorEditingStop = true;
        EditText editText = this.editTextSelected;
        if (editText == null) {
            this.delRow = true;
            int size = this.contentBeanList.size() - 1;
            int size2 = this.contentBeanList.size() - 1;
            while (true) {
                if (size2 >= 0) {
                    if (this.contentBeanList.get(size2).getViewType() == 4) {
                        size = size2;
                        break;
                    } else {
                        if (!this.contentBeanList.get(size2).getContent().equals("")) {
                            size = size2 + 1;
                            break;
                        }
                        size2--;
                    }
                } else {
                    break;
                }
            }
            if (this.contentBeanList.get(size).getViewType() == 4) {
                this.contentBeanList.get(size).getAttachmentsList().add(attachment);
                notifyDataAll();
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(attachment);
            this.contentBeanList.add(size, EditContentBean.newImg(arrayList));
            this.contentBeanList.add(size + 1, EditContentBean.newText(""));
            notifyDataAll();
            return;
        }
        int selectionStart = editText.getSelectionStart();
        cursorBeforeAfter = selectionStart;
        StringBuilder sb = new StringBuilder();
        sb.append("onSelectedAreChanged: 001 ");
        sb.append(cursorBeforeAfter);
        if (currentTimeMillis - this.imgFirstTime > 100) {
            int[] iArr = {this.selectedEditText};
            if (this.editTextSelected.getText() != null && this.editTextSelected.getText().length() != selectionStart) {
                newLine(this.editTextSelected, iArr, selectionStart);
                this.delRow = true;
                notifyDataAll();
            }
        }
        this.imgFirstTime = currentTimeMillis;
        int i6 = this.selectedEditText;
        this.addImgP = i6 + 1;
        this.delRow = true;
        if (i6 + 1 >= this.contentBeanList.size()) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(attachment);
            this.contentBeanList.add(EditContentBean.newImg(arrayList2));
            this.contentBeanList.add(EditContentBean.newText(""));
            notifyDataAll();
            return;
        }
        if (this.contentBeanList.get(this.selectedEditText + 1).getViewType() == 4) {
            List<Attachment> attachmentsList = this.contentBeanList.get(this.selectedEditText + 1).getAttachmentsList();
            if (attachmentsList != null) {
                attachmentsList.add(attachment);
            }
            notifyDataAll();
            return;
        }
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(attachment);
        this.contentBeanList.add(this.selectedEditText + 1, EditContentBean.newImg(arrayList3));
        notifyDataAll();
    }

    public void cursorEnd() {
        this.showKeyboard = true;
        List<EditContentBean> list = this.contentBeanList;
        if (list.get(list.size() - 1).getViewType() >= 4) {
            this.contentBeanList.add(EditContentBean.newText(""));
            notifyInserted(this.contentBeanList.size() - 1);
        } else {
            int size = this.contentBeanList.size() - 1;
            this.selectedEditText = size;
            this.delRow = true;
            notifyChanged(size);
        }
    }

    public void cursorNo() {
        this.selectedEditText = -1;
    }

    public void cursorOne() {
        this.selectedEditText = 0;
        notifyChanged(0);
    }

    public void destoryPlay() {
        MP3Player mP3Player = this.mp;
        if (mP3Player != null) {
            mP3Player.stop();
            this.mp.destroy();
        }
        MP3Player mP3Player2 = this.mp2;
        if (mP3Player2 != null) {
            mP3Player2.stop();
            this.mp2.destroy();
            this.mp2 = null;
        }
        this.handler.removeCallbacks(this.updateRunnale);
    }

    public int getCursorBeforeAfter() {
        return cursorBeforeAfter;
    }

    public HashMap<Integer, EditText> getEditTextHashMap() {
        return this.editTextHashMap;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.contentBeanList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i6) {
        return this.contentBeanList.get(i6).getViewType();
    }

    public int getSelectedEditText() {
        return this.selectedEditText;
    }

    public void notifyChanged(int i6) {
        Message message = new Message();
        message.what = 2;
        message.arg1 = i6;
        this.mRefreshHandler.sendMessage(message);
    }

    public void notifyChangedNotCursor(int i6) {
        Message message = new Message();
        message.what = 2;
        message.arg1 = i6;
        message.obj = Boolean.TRUE;
        this.mRefreshHandler.sendMessage(message);
    }

    public void notifyDataText() {
        for (int i6 = 0; i6 < this.contentBeanList.size(); i6++) {
            if (this.contentBeanList.get(i6).getViewType() < 4) {
                notifyChangedNotCursor(i6);
            }
        }
    }

    public void notifyInserted(int i6) {
        Message message = new Message();
        message.what = 1;
        message.arg1 = i6;
        this.mRefreshHandler.sendMessage(message);
    }

    public void notifyRemoved(int i6) {
        Message message = new Message();
        message.what = 3;
        message.arg1 = i6;
        this.mRefreshHandler.sendMessage(message);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i6) {
        int viewType = this.contentBeanList.get(i6).getViewType();
        if (viewType == 0) {
            initText((ViewHolderText) viewHolder, i6);
            return;
        }
        if (viewType == 1) {
            initCheck((ViewHolderCheck) viewHolder, i6);
            return;
        }
        if (viewType == 2) {
            initBullet((ViewHolderBullet) viewHolder, i6);
        } else if (viewType == 3) {
            initNumber((ViewHolderNumber) viewHolder, i6);
        } else {
            if (viewType != 4) {
                return;
            }
            initImg((ViewHolderImg) viewHolder, i6);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i6) {
        return i6 != 0 ? i6 != 1 ? i6 != 2 ? i6 != 3 ? i6 != 4 ? new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_err_layout, viewGroup, false)) : new ViewHolderImg(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_img_layout, viewGroup, false)) : new ViewHolderNumber(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_number_layout, viewGroup, false)) : new ViewHolderBullet(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_bullet_layout, viewGroup, false)) : new ViewHolderCheck(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_cheke_layout, viewGroup, false)) : new ViewHolderText(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_text_layout, viewGroup, false));
    }

    public void onNumListSelected(NumListEntry numListEntry, EditText editText) {
        MyBulletSpan myBulletSpan;
        MyBulletSpan myBulletSpan2;
        if (numListEntry == null) {
            return;
        }
        if (editText == null) {
            if ("Dots".equals(numListEntry.getUniqueName())) {
                Toast.makeText(mActivity, "bullet_click_toast", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, "numbers_click_toast", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        Editable editableText = editText.getEditableText();
        if (editableText == null) {
            return;
        }
        String uniqueName = numListEntry.getUniqueName();
        if (editableText.length() <= 0) {
            editableText.insert(0, Constants.ZERO_WIDTH_SPACE_STR);
            MyBulletSpan myBulletSpan3 = new MyBulletSpan(editText, uniqueName, 1, MyBulletSpanHelper.createNewGroup(editText));
            editableText.setSpan(myBulletSpan3, 0, 1, 33);
            editableText.setSpan(myBulletSpan3.myImageSpan, 0, 1, 33);
            return;
        }
        BulletSpanInfo curLineInfo = MyBulletSpanHelper.getCurLineInfo(editText);
        if (curLineInfo == null) {
            return;
        }
        BulletSpanInfo preLineInfo = MyBulletSpanHelper.getPreLineInfo(editText, curLineInfo.getLine());
        if (curLineInfo.getLineStart() == curLineInfo.getLineEnd()) {
            int nlLevel = (preLineInfo == null || !preLineInfo.isSameName(uniqueName) || (myBulletSpan2 = preLineInfo.getMyBulletSpan()) == null) ? 1 : myBulletSpan2.getNlLevel();
            int createNewGroup = MyBulletSpanHelper.createNewGroup(editText);
            editableText.insert(curLineInfo.getLineStart(), Constants.ZERO_WIDTH_SPACE_STR);
            curLineInfo.setLineEnd(curLineInfo.getLineStart() + 1);
            MyBulletSpan myBulletSpan4 = new MyBulletSpan(editText, uniqueName, nlLevel, createNewGroup);
            editableText.setSpan(myBulletSpan4, curLineInfo.getLineStart(), curLineInfo.getLineStart() + 1, 33);
            editableText.setSpan(myBulletSpan4.myImageSpan, curLineInfo.getLineStart(), curLineInfo.getLineStart() + 1, 33);
        } else {
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
                int nlLevel2 = (preLineInfo == null || !preLineInfo.isSameName(uniqueName) || (myBulletSpan = preLineInfo.getMyBulletSpan()) == null) ? 1 : myBulletSpan.getNlLevel();
                int createNewGroup2 = MyBulletSpanHelper.createNewGroup(editText);
                try {
                    editableText.insert(curLineInfo.getLineStart(), Constants.ZERO_WIDTH_SPACE_STR);
                    curLineInfo.setLineEnd(curLineInfo.getLineStart() + 1);
                    MyBulletSpan myBulletSpan6 = new MyBulletSpan(editText, uniqueName, nlLevel2, createNewGroup2);
                    editableText.setSpan(myBulletSpan6, curLineInfo.getLineStart(), curLineInfo.getLineStart() + 1, 33);
                    editableText.setSpan(myBulletSpan6.myImageSpan, curLineInfo.getLineStart(), curLineInfo.getLineStart() + 1, 33);
                    MyBulletSpanHelper.sortAllSpanByGroup(editText, createNewGroup2);
                } catch (Exception unused) {
                }
            }
        }
        setTextStyle(true);
    }

    @Override // notes.easy.android.mynotes.view.record.MP3Player.CompleteListener
    public void responseToComplete() {
        this.isPlaying = false;
        notifyDataAll();
    }

    public void setCursorEditingStop(boolean z6) {
        this.cursorEditingStop = z6;
    }

    public void setDarkColor(boolean z6) {
        this.dark = z6;
    }

    public void setDelRow(boolean z6) {
        this.delRow = z6;
    }

    public void setDrawLines(boolean z6, NoteBgBean.Line line) {
        this.isLineTheme = z6;
        this.line = line;
        notifyDataText();
    }

    public void setEditListener(final EditText editText, final RecyclerView.ViewHolder viewHolder) {
        NoteBgBean.Line line;
        final int[] iArr = {viewHolder.getAdapterPosition()};
        final EditText[] editTextArr = {editText};
        setFontType(editText);
        editTextArr[0].setDrawLine(this.isLineTheme);
        editTextArr[0].setDrawLeft(false);
        if (this.isLineTheme && (line = this.line) != null) {
            editTextArr[0].setLineType(line.getLineType());
            editTextArr[0].setLineColor(this.line.getLineColor());
            editTextArr[0].setLineWidth(ScreenUtils.dpToPx(this.line.getLineWidth()));
            editTextArr[0].setLineDash(ScreenUtils.dpToPx(this.line.getLineLength()), ScreenUtils.dpToPx(this.line.getLineGap()));
        }
        if (this.dark) {
            editTextArr[0].setTextColor(ContextCompat.getColor(this.mActivity, R.color.white_86alpha_dbfff));
            editTextArr[0].setHintTextColor(ColorUtils.setAlphaComponent(ContextCompat.getColor(this.mActivity, R.color.white_86alpha_dbfff), 64));
        } else {
            editTextArr[0].setTextColor(ContextCompat.getColor(this.mActivity, R.color.black_87alpha_df000));
            editTextArr[0].setHintTextColor(ColorUtils.setAlphaComponent(ContextCompat.getColor(this.mActivity, R.color.black_87alpha_df000), 64));
        }
        if (this.showHint && iArr[0] == 0) {
            editTextArr[0].setHint(this.mActivity.getResources().getString(R.string.content));
        } else {
            editTextArr[0].setHint("");
        }
        editTextArr[0].setEditTextMenuListener(new EditTextMenuListener() { // from class: notes.easy.android.mynotes.ui.adapters.EditContentAdapter.2
            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public boolean onMenuDismiss() {
                EditContentAdapter.this.editTextMenuListener.onMenuDismiss();
                return false;
            }

            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public boolean onMenuShow() {
                EditContentAdapter.this.editTextMenuListener.onMenuShow();
                return false;
            }

            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public boolean onSelectedAreChanged(int i6, int i7) {
                if (editTextArr[0].hasFocus() && EditContentAdapter.cursorBeforeAfter != -1) {
                    EditContentAdapter.this.selectedEditText = iArr[0];
                    EditContentAdapter.this.editTextSelected = editTextArr[0];
                }
                if (i6 == i7 && iArr[0] == EditContentAdapter.this.selectedEditText && EditContentAdapter.this.contentTouch) {
                    EditContentAdapter.this.contentTouch = false;
                    int i8 = EditContentAdapter.cursorBeforeAfter;
                    int unused = EditContentAdapter.cursorBeforeAfter = i6;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onSelectedAreChanged: a002 ");
                    sb.append(EditContentAdapter.cursorBeforeAfter);
                    BulletSpanInfo curLineInfo = MyBulletSpanHelper.getCurLineInfo(editTextArr[0]);
                    if (curLineInfo == null || curLineInfo.getMyBulletSpan() == null || curLineInfo.getMyBulletSpan().getNlName() == null) {
                        return false;
                    }
                    String nlName = curLineInfo.getMyBulletSpan().getNlName();
                    if (curLineInfo.getLineStart() == i6) {
                        EditContentAdapter.this.delRow = true;
                        if ("checkList_no_white".equals(nlName) || "checkList_no".equals(nlName)) {
                            EditContentAdapter.this.onNumListSelected(new NumListEntry("checkList_selected"), editTextArr[0]);
                        } else if ("checkList_selected".equals(nlName)) {
                            if (EditContentAdapter.this.dark) {
                                EditContentAdapter.this.onNumListSelected(new NumListEntry("checkList_no_white"), editTextArr[0]);
                            } else {
                                EditContentAdapter.this.onNumListSelected(new NumListEntry("checkList_no"), editTextArr[0]);
                            }
                        }
                        if (editTextArr[0].getText() != null) {
                            if (i8 >= editTextArr[0].length()) {
                                i8 = editTextArr[0].length() - 1;
                            }
                            if (i8 < 0) {
                                i8 = 0;
                            }
                            editTextArr[0].setSelection(i8);
                            int unused2 = EditContentAdapter.cursorBeforeAfter = i8;
                        }
                    }
                }
                EditContentAdapter.this.editTextMenuListener.onSelectedAreChanged(i6, i7);
                return false;
            }

            @Override // com.neopixl.pixlui.components.edittext.EditTextMenuListener
            public void updateURState(boolean z6, boolean z7) {
                EditContentAdapter.this.editTextMenuListener.updateURState(z6, z7);
            }
        });
        editTextArr[0].setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: a6.r
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i6, KeyEvent keyEvent) {
                boolean lambda$setEditListener$0;
                lambda$setEditListener$0 = EditContentAdapter.this.lambda$setEditListener$0(textView, i6, keyEvent);
                return lambda$setEditListener$0;
            }
        });
        editTextArr[0].setOnKeyListener(new View.OnKeyListener() { // from class: a6.s
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i6, KeyEvent keyEvent) {
                boolean lambda$setEditListener$1;
                lambda$setEditListener$1 = EditContentAdapter.this.lambda$setEditListener$1(editText, iArr, editTextArr, view, i6, keyEvent);
                return lambda$setEditListener$1;
            }
        });
        editTextArr[0].setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: a6.t
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z6) {
                EditContentAdapter.this.lambda$setEditListener$2(viewHolder, iArr, editTextArr, view, z6);
            }
        });
        editTextArr[0].setOnTouchListener(new View.OnTouchListener() { // from class: a6.u
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$setEditListener$3;
                lambda$setEditListener$3 = EditContentAdapter.this.lambda$setEditListener$3(view, motionEvent);
                return lambda$setEditListener$3;
            }
        });
        editTextArr[0].setChangedListener(new TextUndoRedo.MyTextChangedListener() { // from class: notes.easy.android.mynotes.ui.adapters.EditContentAdapter.3
            int startPos = 0;
            int endPos = 0;

            class AnonymousClass1 extends TimerTask {
                AnonymousClass1() {
                }

                public  void lambda$run$0(EditText[] editTextArr, int[] iArr) {
                    EditContentAdapter.this.saveEditRich(editTextArr[0], iArr[0], false);
                }

                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    Activity activity = EditContentAdapter.this.mActivity;
                    AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                    final EditText[] editTextArr = editTextArr;
                    final int[] iArr = iArr;
                    activity.runOnUiThread(new Runnable() { // from class: notes.easy.android.mynotes.ui.adapters.f
                        @Override // java.lang.Runnable
                        public void run() {
                            EditContentAdapter.AnonymousClass3.AnonymousClass1.this.lambda$run$0(editTextArr, iArr);
                        }
                    });
                }
            }

            @Override // com.neopixl.pixlui.components.edittext.TextUndoRedo.MyTextChangedListener
            public void afterTextChanged(Editable editable) {
                int i6;
                if (EditContentAdapter.this.picClicklistener != null) {
                    EditContentAdapter.this.picClicklistener.onEditAfter(editable, editTextArr[0], EditContentAdapter.this.startIndex, 1, this.startPos, this.endPos, true);
                    EditContentAdapter.this.picClicklistener.afterTextChanged();
                }
                if (EditContentAdapter.this.showHint && editable.length() > 0) {
                    EditContentAdapter.this.showHint = false;
                    if (iArr[0] != 0) {
                        EditContentAdapter.this.notifyChanged(0);
                    }
                }
                iArr[0] = viewHolder.getAdapterPosition();
                if (iArr[0] == EditContentAdapter.this.selectedEditText && (i6 = iArr[0]) != -1 && i6 < EditContentAdapter.this.contentBeanList.size()) {
                    String obj = editTextArr[0].getText().toString();
                    if (!EditContentAdapter.this.cursorEditingStop) {
                        int unused = EditContentAdapter.cursorBeforeAfter = editTextArr[0].getSelectionStart();
                    }
                    EditContentAdapter.this.cursorEditingStop = false;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onSelectedAreChanged: 005 ");
                    sb.append(EditContentAdapter.cursorBeforeAfter);
                    EditContentAdapter.this.contentBeanList.get(iArr[0]).setContent(obj);
                    new Timer().schedule(new AnonymousClass1(), 50L);
                }
            }

            @Override // com.neopixl.pixlui.components.edittext.TextUndoRedo.MyTextChangedListener
            public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
                if (editTextArr[0].getSelectionStart() != editTextArr[0].getSelectionEnd() || editTextArr[0].getSelectionEnd() >= editTextArr[0].getEditableText().length()) {
                    EditContentAdapter.this.startIndex = editTextArr[0].getEditableText().length();
                } else {
                    EditContentAdapter.this.startIndex = editTextArr[0].getSelectionStart();
                }
            }

            @Override // com.neopixl.pixlui.components.edittext.TextUndoRedo.MyTextChangedListener
            public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
                if (iArr[0] == EditContentAdapter.this.selectedEditText && EditContentAdapter.cursorBeforeAfter != -1 && !EditContentAdapter.this.delRow) {
                    this.startPos = i6;
                    this.endPos = i6 + i8;
                } else {
                    EditContentAdapter.this.delRow = false;
                    this.startPos = 0;
                    this.endPos = 0;
                }
            }
        });
        if (iArr[0] == this.selectedEditText) {
            if (this.showKeyboard) {
                this.showKeyboard = false;
                cursorBeforeAfter = editTextArr[0].getSelectionStart();
                StringBuilder sb = new StringBuilder();
                sb.append("onSelectedAreChanged: 006 ");
                sb.append(cursorBeforeAfter);
            } else {
                editTextArr[0].setFocusable(false);
                editTextArr[0].setFocusable(true);
                editTextArr[0].setFocusableInTouchMode(true);
                editTextArr[0].requestFocus();
                new Timer().schedule(new AnonymousClass4(editTextArr), 50L);
            }
            if (cursorBeforeAfter < 0) {
                cursorBeforeAfter = 0;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onSelectedAreChanged: 007 ");
                sb2.append(cursorBeforeAfter);
            }
            if (editTextArr[0].getText() != null) {
                int length = editTextArr[0].getText().length();
                int i6 = cursorBeforeAfter;
                if (length > i6) {
                    editTextArr[0].setSelection(i6);
                } else {
                    EditText editText2 = editTextArr[0];
                    editText2.setSelection(editText2.getText().length());
                }
            }
        }
    }

    public void setFontType(EditText editText) {
        try {
            editText.setTypeface(this.stringStyle);
        } catch (Exception unused) {
        }
    }

    public void setSearchText(String str) {
        this.searchText = str;
        notifyDataAll();
    }

    public void setSelectedEditText(int i6) {
        this.selectedEditText = i6;
    }

    public void setStringStyle(Typeface typeface) {
        this.stringStyle = typeface;
        notifyItemRangeChanged(0, this.contentBeanList.size() - 1);
        notifyDataAll();
    }

    public void setTextStyle(boolean z6) {
        new Timer().schedule(new AnonymousClass1(z6), 50L);
    }

    private void saveEditRich(EditText editText, int i6, boolean z6, boolean z7) {
        Editable editableText;
        MyBulletSpan[] myBulletSpanArr;
        RelativeSizeSpan[] relativeSizeSpanArr;
        StyleSpan[] styleSpanArr;
        AbsoluteSizeSpan[] absoluteSizeSpanArr;
        int i7;
        BackgroundColorSpan[] backgroundColorSpanArr;
        if (i6 == -1 || i6 >= this.contentBeanList.size() || editText == null || editText.getText() == null || editText.getText().length() == 0 || (editableText = editText.getEditableText()) == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        StrikethroughSpan[] strikethroughSpanArr = editableText.getSpans(0, editableText.length(), StrikethroughSpan.class);
        UnderlineSpan[] underlineSpanArr = editableText.getSpans(0, editableText.length(), UnderlineSpan.class);
        BackgroundColorSpan[] backgroundColorSpanArr2 = editableText.getSpans(0, editableText.length(), BackgroundColorSpan.class);
        RelativeSizeSpan[] relativeSizeSpanArr2 = editableText.getSpans(0, editableText.length(), RelativeSizeSpan.class);
        AbsoluteSizeSpan[] absoluteSizeSpanArr2 = editableText.getSpans(0, editableText.length(), AbsoluteSizeSpan.class);
        ForegroundColorSpan[] foregroundColorSpanArr = editableText.getSpans(0, editableText.length(), ForegroundColorSpan.class);
        StyleSpan[] styleSpanArr2 = editableText.getSpans(0, editableText.length(), StyleSpan.class);
        MyBulletSpan[] myBulletSpanArr2 = editableText.getSpans(0, editableText.length(), MyBulletSpan.class);
        StringBuilder str = new StringBuilder();
        if (myBulletSpanArr2 != null) {
            ArrayList arrayList2 = new ArrayList();
            int length = myBulletSpanArr2.length;
            int i8 = 0;
            while (i8 < length) {
                int i9 = length;
                MyBulletSpan myBulletSpan = myBulletSpanArr2[i8];
                if (myBulletSpan == null) {
                    backgroundColorSpanArr = backgroundColorSpanArr2;
                    relativeSizeSpanArr = relativeSizeSpanArr2;
                    absoluteSizeSpanArr = absoluteSizeSpanArr2;
                    styleSpanArr = styleSpanArr2;
                    myBulletSpanArr = myBulletSpanArr2;
                } else {
                    myBulletSpanArr = myBulletSpanArr2;
                    relativeSizeSpanArr = relativeSizeSpanArr2;
                    String str2 = "digital".equalsIgnoreCase(myBulletSpan.getNlName()) ? Constants.SPAN_BULLET_Z : "Dots".equalsIgnoreCase(myBulletSpan.getNlName()) ? Constants.SPAN_BULLET_D : "checkList_selected".equalsIgnoreCase(myBulletSpan.getNlName()) ? Constants.SPAN_BULLET_CHECK_BOX : ("checkList_no".equalsIgnoreCase(myBulletSpan.getNlName()) || "checkList_no_white".equalsIgnoreCase(myBulletSpan.getNlName())) ? Constants.SPAN_BULLET_CHECK_BOX_N : "";
                    int spanStart = editableText.getSpanStart(myBulletSpan);
                    styleSpanArr = styleSpanArr2;
                    String[] split = editableText.toString().split("\n");
                    absoluteSizeSpanArr = absoluteSizeSpanArr2;
                    int i10 = 0;
                    int i11 = 0;
                    while (true) {
                        if (i10 >= split.length) {
                            i7 = -1;
                            i10 = -1;
                            break;
                        }
                        int length2 = i11 + split[i10].length() + 1;
                        if (spanStart < length2) {
                            i7 = -1;
                            break;
                        } else {
                            i10++;
                            i11 = length2;
                        }
                    }
                    if (i10 == i7) {
                        i10 = split.length - 1;
                    }
                    if (z7) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        backgroundColorSpanArr = backgroundColorSpanArr2;
                        sb2.append(0);
                        arrayList.add(new SpanBean(0, sb2.toString()));
                    } else {
                        backgroundColorSpanArr = backgroundColorSpanArr2;
                    }
                    String str3 = "" + spanStart;
                    if (!arrayList2.contains(str3)) {
                        arrayList2.add(str3);
                        if (!TextUtils.isEmpty(str2)) {
                            arrayList.add(new SpanBean(i10, str2 + i10));
                        }
                    }
                }
                i8++;
                length = i9;
                myBulletSpanArr2 = myBulletSpanArr;
                relativeSizeSpanArr2 = relativeSizeSpanArr;
                styleSpanArr2 = styleSpanArr;
                backgroundColorSpanArr2 = backgroundColorSpanArr;
                absoluteSizeSpanArr2 = absoluteSizeSpanArr;
            }
        }
        BackgroundColorSpan[] backgroundColorSpanArr3 = backgroundColorSpanArr2;
        RelativeSizeSpan[] relativeSizeSpanArr3 = relativeSizeSpanArr2;
        AbsoluteSizeSpan[] absoluteSizeSpanArr3 = absoluteSizeSpanArr2;
        StyleSpan[] styleSpanArr3 = styleSpanArr2;
        if (strikethroughSpanArr != null) {
            ArrayList arrayList3 = new ArrayList();
            for (StrikethroughSpan strikethroughSpan : strikethroughSpanArr) {
                if (strikethroughSpan != null) {
                    int spanStart2 = editableText.getSpanStart(strikethroughSpan);
                    int spanEnd = editableText.getSpanEnd(strikethroughSpan);
                    String str4 = "" + spanStart2 + spanEnd;
                    if (!arrayList3.contains(str4) && spanEnd > spanStart2) {
                        arrayList3.add(str4);
                        try {
                            sb.append(Constants.SPAN_STRIKETHROUGH);
                            sb.append(spanStart2);
                            sb.append("/");
                            sb.append(spanEnd);
                            sb.append(",");
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
        if (underlineSpanArr != null) {
            ArrayList arrayList4 = new ArrayList();
            for (UnderlineSpan underlineSpan : underlineSpanArr) {
                if (underlineSpan != null) {
                    int spanStart3 = editableText.getSpanStart(underlineSpan);
                    int spanEnd2 = editableText.getSpanEnd(underlineSpan);
                    String str5 = "" + spanStart3 + spanEnd2;
                    if (!arrayList4.contains(str5) && spanEnd2 > spanStart3) {
                        arrayList4.add(str5);
                        try {
                            sb.append(Constants.SPAN_U);
                            sb.append(spanStart3);
                            sb.append("/");
                            sb.append(spanEnd2);
                            sb.append(",");
                        } catch (Exception unused2) {
                        }
                    }
                }
            }
        }
        if (foregroundColorSpanArr != null) {
            ArrayList arrayList5 = new ArrayList();
            for (ForegroundColorSpan foregroundColorSpan : foregroundColorSpanArr) {
                if (foregroundColorSpan != null && foregroundColorSpan.getForegroundColor() != this.checkListColor && foregroundColorSpan.getForegroundColor() != this.checkListColorW) {
                    int spanStart4 = editableText.getSpanStart(foregroundColorSpan);
                    int spanEnd3 = editableText.getSpanEnd(foregroundColorSpan);
                    String str6 = "" + spanStart4 + spanEnd3;
                    if (!arrayList5.contains(str6) && spanEnd3 > spanStart4) {
                        arrayList5.add(str6);
                        try {
                            sb.append(Constants.SPAN_FONT_COLOR);
                            sb.append(spanStart4);
                            sb.append("/");
                            sb.append(spanEnd3);
                            sb.append("/");
                            sb.append(foregroundColorSpan.getForegroundColor());
                            sb.append(",");
                        } catch (Exception unused3) {
                        }
                    }
                }
            }
        }
        if (backgroundColorSpanArr3 != null) {
            ArrayList arrayList6 = new ArrayList();
            for (BackgroundColorSpan backgroundColorSpan : backgroundColorSpanArr3) {
                if (backgroundColorSpan != null) {
                    int spanStart5 = editableText.getSpanStart(backgroundColorSpan);
                    int spanEnd4 = editableText.getSpanEnd(backgroundColorSpan);
                    String str7 = "" + spanStart5 + spanEnd4;
                    if (!arrayList6.contains(str7) && spanEnd4 > spanStart5) {
                        arrayList6.add(str7);
                        try {
                            sb.append("h");
                            sb.append(spanStart5);
                            sb.append("/");
                            sb.append(spanEnd4);
                            sb.append("/");
                            sb.append(backgroundColorSpan.getBackgroundColor());
                            sb.append(",");
                        } catch (Exception unused4) {
                        }
                    }
                }
            }
        }
        if (absoluteSizeSpanArr3 != null) {
            ArrayList arrayList7 = new ArrayList();
            for (AbsoluteSizeSpan absoluteSizeSpan : absoluteSizeSpanArr3) {
                if (absoluteSizeSpan != null) {
                    int spanStart6 = editableText.getSpanStart(absoluteSizeSpan);
                    int spanEnd5 = editableText.getSpanEnd(absoluteSizeSpan);
                    String str8 = "" + spanStart6 + spanEnd5;
                    if (!arrayList7.contains(str8) && spanEnd5 > spanStart6) {
                        arrayList7.add(str8);
                        try {
                            sb.append("f");
                            sb.append(spanStart6);
                            sb.append("/");
                            sb.append(spanEnd5);
                            sb.append("/");
                            sb.append(absoluteSizeSpan.getSize());
                            sb.append(",");
                        } catch (Exception unused5) {
                        }
                    }
                }
            }
        }
        if (styleSpanArr3 != null) {
            ArrayList arrayList8 = new ArrayList();
            for (StyleSpan styleSpan : styleSpanArr3) {
                if (styleSpan != null) {
                    int spanStart7 = editableText.getSpanStart(styleSpan);
                    int spanEnd6 = editableText.getSpanEnd(styleSpan);
                    String str9 = "" + spanStart7 + spanEnd6 + styleSpan.getStyle();
                    if (!arrayList8.contains(str9) && spanEnd6 > spanStart7) {
                        arrayList8.add(str9);
                        try {
                            sb.append(Constants.SPAN_S);
                            sb.append(spanStart7);
                            sb.append("/");
                            sb.append(spanEnd6);
                            sb.append("/");
                            sb.append(styleSpan.getStyle());
                            sb.append(",");
                        } catch (Exception unused6) {
                        }
                    }
                }
            }
            if (relativeSizeSpanArr3 != null) {
                ArrayList arrayList9 = new ArrayList();
                for (RelativeSizeSpan relativeSizeSpan : relativeSizeSpanArr3) {
                    if (relativeSizeSpan != null) {
                        int spanStart8 = editableText.getSpanStart(relativeSizeSpan);
                        int spanEnd7 = editableText.getSpanEnd(relativeSizeSpan);
                        String str10 = "" + spanStart8 + spanEnd7;
                        if (!arrayList9.contains(str10) && spanEnd7 > spanStart8) {
                            if (relativeSizeSpan.getSizeChange() >= 1.1f && relativeSizeSpan.getSizeChange() <= 1.6f) {
                                try {
                                    sb.append(Constants.SPAN_R);
                                    sb.append(spanStart8);
                                    sb.append("/");
                                    sb.append(spanEnd7);
                                    sb.append("/");
                                    sb.append(relativeSizeSpan.getSizeChange());
                                    sb.append(",");
                                } catch (Exception unused7) {
                                }
                            }
                            arrayList9.add(str10);
                        }
                    }
                }
            }
        }
        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object obj, Object obj2) {
                int lambda$saveEditRich$23;
                lambda$saveEditRich$23 = EditContentAdapter.lambda$saveEditRich$23((SpanBean) obj, (SpanBean) obj2);
                return lambda$saveEditRich$23;
            }
        });
        for (int i12 = 0; i12 < arrayList.size(); i12++) {
            str.append(((SpanBean) arrayList.get(i12)).getContent()).append(",");
        }
        this.contentBeanList.get(i6).setRichData(str.toString() + sb);
        this.saveAll.save();
        if (z6) {
            notifyDataText();
        }
    }

    public void onNumListSelected(EditText editText, Editable editable, String str, int i6) {
        int i7;
        String[] split = editable.toString().split("\n");
        int i8 = 0;
        if (i6 > 0) {
            int i9 = 0;
            int i10 = 0;
            while (true) {
                if (i9 >= split.length) {
                    i10 = 0;
                    break;
                } else if (i9 == i6 - 1) {
                    split[i9].length();
                    break;
                } else {
                    i10 = i10 + split[i9].length() + 1;
                    i9++;
                }
            }
            i7 = 0;
            for (MyBulletSpan myBulletSpan : editable.getSpans(i10, i10 + 1, MyBulletSpan.class)) {
                if (myBulletSpan != null && "digital".equalsIgnoreCase(myBulletSpan.getNlName())) {
                    i7 = myBulletSpan.getNlLevel();
                }
            }
        } else {
            i7 = 0;
        }
        int i11 = 0;
        int i12 = 0;
        while (true) {
            if (i11 >= split.length) {
                break;
            }
            if (i11 == i6) {
                i8 = i12;
                i12 = split[i11].length() + i12 + 1;
                break;
            } else {
                i12 = i12 + split[i11].length() + 1;
                i11++;
            }
        }
        int i13 = i7 + 1;
        MyBulletSpan myBulletSpan2 = new MyBulletSpan(editText, str, i13, MyBulletSpanHelper.createNewGroup(editText));
        myBulletSpan2.myImageSpan.setNlLevel(i13);
        int i14 = i8 + 1;
        editable.setSpan(myBulletSpan2, i8, i14, 33);
        editable.setSpan(myBulletSpan2.myImageSpan, i8, i14, 33);
        if (str.equals("checkList_selected")) {
            int i15 = i12 - 1;
            editable.setSpan(new StrikethroughSpan(), i8, i15, 18);
            if (this.dark) {
                editable.setSpan(new ForegroundColorSpan(this.checkListColorW), i8, i15, 18);
            } else {
                editable.setSpan(new ForegroundColorSpan(this.checkListColor), i8, i15, 18);
            }
        }
    }
}
