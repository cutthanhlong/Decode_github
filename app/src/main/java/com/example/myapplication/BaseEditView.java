package com.example.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.custom.edittext.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseEditView {
    private View baseEditView;
    private PicGridAdapter mAdapter;
    private EditText mEditText;
    private int mLength;
    private int mOrder;
    private RecyclerView mRecyclerView;
    private PicClicklistener picClicklistener;
    private final List<Attachment> imageList = new ArrayList();
    private int startIndex = 0;
    private String mContent = "";
    private boolean mUserEdited = false;

    public interface PicClicklistener {
        void afterTextChanged();

        void onBaseViewDragFinish(List<Attachment> list, int i6, int i7, List<Attachment> list2);

        void onEditAfter(Editable editable, EditText editText, int i6, int i7, int i8, int i9, boolean z6);

        void onEditTouch();

        void onFocusChange(boolean z6);

        void onPicClick(Attachment attachment, int i6);
    }

    public BaseEditView(Context context) {
        init(context, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view, boolean z6) {
        PicClicklistener picClicklistener = this.picClicklistener;
        if (picClicklistener != null) {
            picClicklistener.onFocusChange(z6);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$1(View view, MotionEvent motionEvent) {
        PicClicklistener picClicklistener;
        if (motionEvent.getAction() != 0 || (picClicklistener = this.picClicklistener) == null) {
            return false;
        }
        picClicklistener.onEditTouch();
        return false;
    }

    private void updateAdapter() {
        PicGridAdapter picGridAdapter = this.mAdapter;
        if (picGridAdapter != null) {
            picGridAdapter.notifyDataSetChanged();
        }
    }

    public void addAttachment(Attachment attachment) {
        this.imageList.add(attachment);
        if (attachment.getSort() != 0) {
            Collections.sort(this.imageList, new SortComparator());
        }
        updateAdapter();
    }

    public int getAttachmentSize() {
        return this.imageList.size();
    }

    public List<Attachment> getAttachments() {
        return this.imageList;
    }

    public View getBaseView() {
        return this.baseEditView;
    }

    public String getContent() {
        return this.mEditText.getText().toString();
    }

    public Editable getEditable() {
        return this.mEditText.getText();
    }

    public RecyclerView getGridView() {
        return this.mRecyclerView;
    }

    public String getOriginStr() {
        return this.mContent;
    }

    public EditText getmEditText() {
        return this.mEditText;
    }

    public int getmOrder() {
        return this.mOrder;
    }

    public void init(Context context, boolean z6) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.edit_base_layout, null);
        this.baseEditView = inflate;
        EditText editText = inflate.findViewById(R.id.content_unit);
        this.mEditText = editText;
        editText.setTextSize(App.userConfig.getDefaultFloatSize());
        this.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: r5.a
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z7) {
                BaseEditView.this.lambda$init$0(view, z7);
            }
        });

        this.mEditText.setOnTouchListener((view, motionEvent) -> {
            boolean lambda$init$1;
            lambda$init$1 = BaseEditView.this.lambda$init$1(view, motionEvent);
            return lambda$init$1;
        });
        this.mEditText.addTextChangedListener(new TextWatcher() { // from class: notes.easy.android.mynotes.edit.view.BaseEditView.1
            int startPos = 0;
            int endPos = 0;

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (BaseEditView.this.picClicklistener != null) {
                    if (!BaseEditView.this.mUserEdited && editable != null && editable.length() != BaseEditView.this.mLength) {
                        BaseEditView.this.mUserEdited = true;
                    }
                    BaseEditView.this.picClicklistener.onEditAfter(editable, BaseEditView.this.mEditText, BaseEditView.this.startIndex, BaseEditView.this.mOrder, this.startPos, this.endPos, BaseEditView.this.mUserEdited);
                    BaseEditView.this.picClicklistener.afterTextChanged();
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
                if (BaseEditView.this.mEditText.getSelectionStart() != BaseEditView.this.mEditText.getSelectionEnd() || BaseEditView.this.mEditText.getSelectionEnd() >= BaseEditView.this.mEditText.getEditableText().length()) {
                    BaseEditView baseEditView = BaseEditView.this;
                    baseEditView.startIndex = baseEditView.mEditText.getEditableText().length();
                } else {
                    BaseEditView baseEditView2 = BaseEditView.this;
                    baseEditView2.startIndex = baseEditView2.mEditText.getSelectionStart();
                }
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
                this.startPos = i6;
                this.endPos = i6 + i8;
            }
        });
        this.mRecyclerView = this.baseEditView.findViewById(R.id.photo_unit);
        PicGridAdapter picGridAdapter = new PicGridAdapter(context, this.imageList);
        this.mAdapter = picGridAdapter;
        picGridAdapter.updateGradId();
        ItemDragCallback itemDragCallback = new ItemDragCallback(this.mAdapter);
        if (!z6) {
            new ItemTouchHelper(itemDragCallback).attachToRecyclerView(this.mRecyclerView);
        }
        this.mAdapter.setOnListCallbackListener(new PicGridAdapter.OnListCallback() { // from class: notes.easy.android.mynotes.edit.view.BaseEditView.2
            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemClick(View view, Attachment attachment, int i6) {
                if (BaseEditView.this.picClicklistener != null) {
                    BaseEditView.this.picClicklistener.onPicClick(attachment, i6);
                }
            }

            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemDragFinished(List<Attachment> list, int i6, int i7) {
                if (BaseEditView.this.picClicklistener != null) {
                    BaseEditView.this.picClicklistener.onBaseViewDragFinish(list, i6, i7, BaseEditView.this.imageList);
                }
            }

            @Override // notes.easy.android.mynotes.models.adapters.PicGridAdapter.OnListCallback
            public void onItemLongClick() {
            }
        });
        this.mRecyclerView.setLayoutManager(new ScrollGridLayoutManager(context, com.example.myapplication.utils.ScreenUtils.isScreenOriatationLandscap(context) ? 4 : com.example.myapplication.utils.ScreenUtils.isPad(context) ? 3 : 2, 1, false));
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    public boolean isFocusable() {
        return this.mEditText.hasFocus();
    }

    public void removeAttachment(Attachment attachment) {
        this.imageList.remove(attachment);
        updateAdapter();
    }

    public void replaceAttach(Attachment attachment, int i6) {
        this.imageList.set(i6, attachment);
        updateAdapter();
    }

    public void setClickListener(PicClicklistener picClicklistener) {
        this.picClicklistener = picClicklistener;
    }

    public void setContent(String str) {
        this.mContent = str;
    }

    public void setInitLength(int i6) {
        this.mLength = i6;
    }

    public void setOrder(int i6) {
        this.mOrder = i6;
    }

    public void setTypeface(Typeface typeface) {
        this.mEditText.setTypeface(typeface);
    }

    public void updateRedrawing() {
        PicGridAdapter picGridAdapter = this.mAdapter;
        if (picGridAdapter == null || this.mRecyclerView == null) {
            return;
        }
        picGridAdapter.notifyDataSetChanged();
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    public BaseEditView(Context context, boolean z6) {
        init(context, z6);
    }
}
