package com.example.myapplication.custom.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditText extends AppCompatEditText implements View.OnClickListener, TextUndoRedo.TextChangeInfo {
    private boolean drawLeft;
    private boolean drawLine;
    private EditTextMenuListener editTextMenuListener;
    private PathEffect effects;
    private String lineColor;
    private boolean lineNormal;
    private float lineWidth;
    private ArrayList<Hyperlink> listOfLinks;
    private boolean mAutoFocus;
    private InputMethodManager mImm;
    private boolean mOldDeviceTextAllCaps;
    private Paint mPaint;
    private WeakReference<TextLinkClickListener> textLinkClickListenerWeakReference;
    private TextUndoRedo undoRedo;

    private class CustomInputConnection extends InputConnectionWrapper {
        private EditText mEdittext;
        private KeyEvent mKeyEvent;
        private int mLastLength;

        public CustomInputConnection(InputConnection inputConnection, boolean z6, EditText editText) {
            super(inputConnection, z6);
            setEdittext(editText);
        }

        private void autoAddOrderNum() {
            throw new UnsupportedOperationException("Method not decompiled: com.neopixl.pixlui.components.edittext.EditText.CustomInputConnection.autoAddOrderNum():void");
        }

        @Override
        public boolean beginBatchEdit() {
            this.mLastLength = EditText.this.length();
            return super.beginBatchEdit();
        }

        @Override
        public boolean commitText(CharSequence charSequence, int i6) {
            try {
                return super.commitText(charSequence, i6);
            } catch (IndexOutOfBoundsException unused) {
                return true;
            }
        }

        @Override
        public boolean endBatchEdit() {
            autoAddOrderNum();
            this.mKeyEvent = null;
            return super.endBatchEdit();
        }

        public EditText getEdittext() {
            return this.mEdittext;
        }

        @Override
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            this.mKeyEvent = keyEvent;
            return super.sendKeyEvent(keyEvent);
        }

        public void setEdittext(EditText editText) {
            this.mEdittext = editText;
        }
    }

    public EditText(Context context) {
        super(context);
        this.effects = new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 5.0f);
        this.drawLine = false;
        this.lineNormal = true;
        this.lineColor = "#F3CFB5";
        this.lineWidth = 3.0f;
        this.drawLeft = true;
        editTextVersion();
        this.mPaint = new Paint();
        setSaveEnabled(false);
        setOnClickListener(this);
    }

    public boolean checkDigit(String str) {
        boolean z6 = true;
        for (char c7 : str.toCharArray()) {
            if (!Character.isDigit(c7)) {
                z6 = false;
            }
        }
        return z6;
    }

    private void editTextVersion() {
        setOldDeviceTextAllCaps(false);
        TextUndoRedo textUndoRedo = new TextUndoRedo(this, this);
        this.undoRedo = textUndoRedo;
        addTextChangedListener(textUndoRedo);
        this.mImm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mAutoFocus = false;
    }

    private final void gatherLinks2(ArrayList<Hyperlink> arrayList, String[] strArr, Pattern pattern) {
        try {
            int i6 = 0;
            for (String str : strArr) {
                Matcher matcher = pattern.matcher(str);
                int length = str.length() + i6;
                if (matcher.matches()) {
                    Hyperlink hyperlink = new Hyperlink();
                    hyperlink.textSpan = str;
                    InternalURLSpan internalURLSpan = new InternalURLSpan(hyperlink.textSpan.toString());
                    hyperlink.span = internalURLSpan;
                    WeakReference<TextLinkClickListener> weakReference = this.textLinkClickListenerWeakReference;
                    if (weakReference != null) {
                        internalURLSpan.setTextLinkClickListener(weakReference.get());
                    }
                    hyperlink.start = i6;
                    hyperlink.end = length;
                    arrayList.add(hyperlink);
                }
                i6 = length + 1;
            }
        } catch (Exception ignored) {
        }
    }

    private void setAllCaps(Context context, AttributeSet attributeSet) {
        if (isInEditMode()) {
            return;
        }
        int attributeCount = attributeSet.getAttributeCount();
        boolean z6 = false;
        int i6 = 0;
        while (true) {
            if (i6 >= attributeCount) {
                break;
            }
            if (attributeSet.getAttributeName(i6).equals("textAllCaps")) {
                z6 = attributeSet.getAttributeBooleanValue(i6, false);
                break;
            }
            i6++;
        }
        if (!z6 || isInEditMode()) {
            return;
        }
        setAllCaps(z6);
    }

    private void setAutoFocus(Context context, AttributeSet attributeSet) {
        if (isInEditMode()) {
            return;
        }
        int attributeCount = attributeSet.getAttributeCount();
        boolean z6 = false;
        int i6 = 0;
        while (true) {
            if (i6 >= attributeCount) {
                break;
            }
            if (attributeSet.getAttributeName(i6).equals("autofocus")) {
                z6 = attributeSet.getAttributeBooleanValue(i6, false);
                break;
            }
            i6++;
        }
        if (!z6 || isInEditMode()) {
            return;
        }
        setAutoFocus(z6);
    }

    public void exeRedo() {
        this.undoRedo.exeRedo();
    }

    public void exeUndo() {
        this.undoRedo.exeUndo();
    }

    public void gatherLinksForText() {
        String obj = getText().toString();
        this.listOfLinks = new ArrayList<>();
        for (Pattern pattern : RegexPatternsConstants.patterns) {
            gatherLinks2(this.listOfLinks, obj.split("\\s"), pattern);
        }
    }

    public EditTextMenuListener getEditTextListener() {
        return this.editTextMenuListener;
    }

    public TextLinkClickListener getOnTextLinkClickListener() {
        WeakReference<TextLinkClickListener> weakReference = this.textLinkClickListenerWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public boolean isDrawLine() {
        return this.drawLine;
    }

    public boolean isOldDeviceTextAllCaps() {
        return this.mOldDeviceTextAllCaps;
    }

    @Override
    public void onClick(View view) {
        if (this.textLinkClickListenerWeakReference != null) {
            int selectionStart = getSelectionStart();
            for (Hyperlink next : this.listOfLinks) {
                if (getText() == null || getText().toString().contains(next.textSpan)) {
                    if (selectionStart > next.start && selectionStart < next.end) {
                        this.textLinkClickListenerWeakReference.get().onTextLinkClick(view, next.textSpan.toString(), UrlCompleter.complete(next.textSpan.toString()));
                    }
                }
            }
        }
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return new CustomInputConnection(super.onCreateInputConnection(editorInfo), true, this);
    }

    @Override
    public boolean onDragEvent(DragEvent dragEvent) {
        if (dragEvent.getAction() != 1) {
            return super.onDragEvent(dragEvent);
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.drawLine) {
            this.mPaint.setStrokeWidth(this.lineWidth);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(Color.parseColor(this.lineColor));
            if (this.lineNormal) {
                this.mPaint.setPathEffect(null);
            } else {
                this.mPaint.setPathEffect(this.effects);
            }
            int left = getLeft();
            int right = getRight();
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int height = getHeight();
            int lineHeight = getLineHeight();
            int lineSpacingExtra = (int) getLineSpacingExtra();
            int i6 = (((height - paddingTop) - paddingBottom) / lineHeight) + 1;
            int i7 = 0;
            while (i7 < i6) {
                i7++;
                int i8 = ((lineHeight * i7) + paddingTop) - (lineSpacingExtra / 2);
                if (this.drawLeft) {
                    float f6 = i8;
                    canvas.drawLine(left + paddingLeft, f6, right - paddingRight, f6, this.mPaint);
                } else {
                    float f7 = i8;
                    canvas.drawLine(0.0f, f7, right - paddingRight, f7, this.mPaint);
                }
            }
        }
        try {
            super.onDraw(canvas);
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onFocusChanged(boolean z6, int i6, Rect rect) {
        super.onFocusChanged(z6, i6, rect);
        if (this.mAutoFocus) {
            if (z6) {
                this.mImm.showSoftInput(this, 3);
            } else {
                this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
            this.mImm.toggleSoftInput(0, 0);
        }
        if (!z6 || this.textLinkClickListenerWeakReference == null) {
            return;
        }
        onClick(this);
    }

    @Override
    protected void onSelectionChanged(int i6, int i7) {
        super.onSelectionChanged(i6, i7);
        if (i6 == i7) {
            EditTextMenuListener editTextMenuListener = this.editTextMenuListener;
            if (editTextMenuListener != null) {
                editTextMenuListener.onMenuDismiss();
            }
        } else {
            EditTextMenuListener editTextMenuListener2 = this.editTextMenuListener;
            if (editTextMenuListener2 != null) {
                editTextMenuListener2.onMenuShow();
            }
        }
        EditTextMenuListener editTextMenuListener3 = this.editTextMenuListener;
        if (editTextMenuListener3 != null) {
            editTextMenuListener3.onSelectedAreChanged(i6, i7);
        }
    }

    @Override
    public boolean onTextContextMenuItem(int i6) {
        if (i6 != 16908322) {
            return super.onTextContextMenuItem(i6);
        }
        boolean onTextContextMenuItem = super.onTextContextMenuItem(i6);
        gatherLinksForText();
        return onTextContextMenuItem;
    }

    public void setChangedListener(TextUndoRedo.MyTextChangedListener myTextChangedListener) {
        TextUndoRedo textUndoRedo = this.undoRedo;
        if (textUndoRedo != null) {
            textUndoRedo.setChangedListener(myTextChangedListener);
        }
    }

    public boolean setCustomFont(Context context, String str) {
        Typeface font = FontFactory.getInstance(context).getFont(str);
        if (font == null) {
            return false;
        }
        setTypeface(font);
        return true;
    }

    public void setDrawLeft(boolean z6) {
        this.drawLeft = z6;
    }

    public void setDrawLine(boolean z6) {
        this.drawLine = z6;
    }

    public void setEditTextMenuListener(EditTextMenuListener editTextMenuListener) {
        this.editTextMenuListener = editTextMenuListener;
    }

    public void setLineColor(String str) {
        this.lineColor = str;
    }

    public void setLineDash(float f6, float f7) {
        if (f6 == 0.0f || f7 == 0.0f) {
            this.effects = new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 5.0f);
        } else {
            this.effects = new DashPathEffect(new float[]{f6, f7, f6, f7}, 5.0f);
        }
    }

    public void setLineType(String str) {
        if (TextUtils.equals(str, ResNoteBgManager.LINE_STYLE_DASH)) {
            this.lineNormal = false;
        } else {
            this.lineNormal = true;
        }
    }

    public void setLineWidth(float f6) {
        this.lineWidth = f6;
    }

    public void setOldDeviceTextAllCaps(boolean z6) {
        this.mOldDeviceTextAllCaps = z6;
    }

    public void setOnTextLinkClickListener(TextLinkClickListener textLinkClickListener) {
        this.textLinkClickListenerWeakReference = new WeakReference<>(textLinkClickListener);
    }

    @Override
    public void textAction() {
        EditTextMenuListener editTextMenuListener = this.editTextMenuListener;
        if (editTextMenuListener != null) {
            editTextMenuListener.updateURState(this.undoRedo.canUndo(), this.undoRedo.canRedo());
        }
    }

    @Override
    @SuppressLint({"NewApi"})
    public void setAllCaps(boolean z6) {
        if (!isOldDeviceTextAllCaps()) {
            super.setAllCaps(z6);
        } else if (z6) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod(null);
        }
    }

    public void setAutoFocus(boolean z6) {
        this.mAutoFocus = z6;
    }

    public EditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.effects = new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 5.0f);
        this.drawLine = false;
        this.lineNormal = true;
        this.lineColor = "#F3CFB5";
        this.lineWidth = 3.0f;
        this.drawLeft = true;
        this.mPaint = new Paint();
        editTextVersion();
        setAutoFocus(context, attributeSet);
        if (isOldDeviceTextAllCaps()) {
            setAllCaps(context, attributeSet);
        }
        setSaveEnabled(false);
        setOnClickListener(this);
    }

    public EditText(Context context, AttributeSet attributeSet, int i6) {
        super(context, attributeSet, i6);
        this.effects = new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 5.0f);
        this.drawLine = false;
        this.lineNormal = true;
        this.lineColor = "#F3CFB5";
        this.lineWidth = 3.0f;
        this.drawLeft = true;
        this.mPaint = new Paint();
        editTextVersion();
        setAutoFocus(context, attributeSet);
        if (isOldDeviceTextAllCaps()) {
            setAllCaps(context, attributeSet);
        }
        setSaveEnabled(false);
        setOnClickListener(this);
    }
}
