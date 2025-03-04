package com.example.myapplication.custom.edittext;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;

public class TextUndoRedo implements TextWatcher {
    private TextChangeInfo info;
    private boolean isUndoOrRedo;
    private final EditText mEditText;
    private Record offset;
    private boolean hasInited = false;
    private MyTextChangedListener changedListener = null;

    public interface MyTextChangedListener {
        void afterTextChanged(Editable editable);

        void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8);

        void onTextChanged(CharSequence charSequence, int i6, int i7, int i8);
    }

    private class Record {
        private int end;
        private Record next;
        private Record prior;
        private int start;
        private CharSequence text;

        Record(int i6, int i7, CharSequence charSequence) {
            this.start = i6;
            this.end = i7;
            this.text = charSequence;
            if (TextUndoRedo.this.offset != null) {
                TextUndoRedo.this.offset.next = this;
                this.prior = TextUndoRedo.this.offset;
            }
            TextUndoRedo.this.offset = this;
        }
    }

    public interface TextChangeInfo {
        void textAction();
    }

    public TextUndoRedo(EditText editText, TextChangeInfo textChangeInfo) {
        this.mEditText = editText;
        editText.postDelayed(new Runnable() { // from class: com.neopixl.pixlui.components.edittext.TextUndoRedo.1
            @Override // java.lang.Runnable
            public void run() {
                TextUndoRedo.this.hasInited = true;
            }
        }, 1000L);
        this.info = textChangeInfo;
        new Record(0, 0, null);
    }

    private void cleanNext() {
        while (this.offset.next != null) {
            Record record = this.offset.next;
            this.offset.next = record.next;
            record.prior = null;
            record.next = null;
        }
    }

    private void exeUndoOrRedo(boolean z6) {
        Record record = this.offset;
        if (record == null) {
            return;
        }
        if (!z6) {
            this.offset = record.next;
        }
        try {
            this.isUndoOrRedo = true;
            Editable text = this.mEditText.getText();
            Record record2 = this.offset;
            if (record2 == null || text == null || record2.text == null) {
                return;
            }
            CharSequence subSequence = text.subSequence(this.offset.start, this.offset.end);
            text.replace(this.offset.start, this.offset.end, this.offset.text);
            Record record3 = this.offset;
            record3.end = record3.start + this.offset.text.length();
            Selection.setSelection(text, this.offset.end);
            this.offset.text = subSequence;
            this.isUndoOrRedo = false;
            if (z6) {
                this.offset = this.offset.prior;
            }
            noticeTextChang();
        } catch (Exception unused) {
            this.isUndoOrRedo = false;
        }
    }

    private void noticeTextChang() {
        TextChangeInfo textChangeInfo = this.info;
        if (textChangeInfo != null) {
            textChangeInfo.textAction();
        }
    }

    @Override // android.text.TextWatcher
    @Deprecated
    public void afterTextChanged(Editable editable) {
        MyTextChangedListener myTextChangedListener = this.changedListener;
        if (myTextChangedListener != null) {
            myTextChangedListener.afterTextChanged(editable);
        }
    }

    @Override // android.text.TextWatcher
    @Deprecated
    public void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
        MyTextChangedListener myTextChangedListener = this.changedListener;
        if (myTextChangedListener != null) {
            myTextChangedListener.beforeTextChanged(charSequence, i6, i7, i8);
        }
        if (this.isUndoOrRedo || !this.hasInited) {
            return;
        }
        new Record(i6, i8 + i6, charSequence.subSequence(i6, i7 + i6));
        cleanNext();
        noticeTextChang();
    }

    public boolean canRedo() {
        return this.offset.next != null;
    }

    public boolean canUndo() {
        return this.offset.prior != null;
    }

    public void exeRedo() {
        exeUndoOrRedo(false);
    }

    public void exeUndo() {
        exeUndoOrRedo(true);
    }

    @Override // android.text.TextWatcher
    @Deprecated
    public void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
        MyTextChangedListener myTextChangedListener = this.changedListener;
        if (myTextChangedListener != null) {
            myTextChangedListener.onTextChanged(charSequence, i6, i7, i8);
        }
    }

    public void setChangedListener(MyTextChangedListener myTextChangedListener) {
        this.changedListener = myTextChangedListener;
    }
}
