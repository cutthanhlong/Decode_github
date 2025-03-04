package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.BulletSpan;
import android.widget.EditText;

/* loaded from: classes4.dex */
public class MyBulletSpan extends BulletSpan {
    public int innerStart = 0;
    public NumListSpan myImageSpan = new NumListSpan();
    public boolean noMargin;

    public MyBulletSpan(EditText editText, String str, int i6, int i7) {
        boolean z6 = false;
        if (editText != null && editText.getGravity() == 17) {
            z6 = true;
        }
        this.noMargin = z6;
        setNlLevel(i6);
        setNlGroup(i7);
        setNlName(str);
    }

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z6) {
        if (this.noMargin || !z6) {
            return 0;
        }
        return this.myImageSpan.isForPrint() ? this.innerStart + 21 : this.innerStart + ScreenUtils.dpToPx(21);
    }

    public int getNlGroup() {
        return this.myImageSpan.getNlGroup();
    }

    public int getNlLevel() {
        return this.myImageSpan.getNlLevel();
    }

    public String getNlName() {
        return this.myImageSpan.getNlName();
    }

    public void setForPrint(boolean z6) {
        this.myImageSpan.setForPrint(z6);
    }

    public void setNlGroup(int i6) {
        this.myImageSpan.setNlGroup(i6);
    }

    public void setNlLevel(int i6) {
        this.myImageSpan.setNlLevel(i6);
    }

    public void setNlName(String str) {
        this.myImageSpan.setNlName(str);
    }

    public void setNoMargin(boolean z6) {
        this.noMargin = z6;
    }

    @Override // android.text.style.BulletSpan
    public String toString() {
        return this.myImageSpan.toString();
    }

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i6, int i7, int i8, int i9, int i10, CharSequence charSequence, int i11, int i12, boolean z6, Layout layout) {
    }
}
