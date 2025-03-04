package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.utils.ScreenUtils;

/* loaded from: classes4.dex */
public class NumListSpan extends DynamicDrawableSpan {
    private Bitmap bitmap;
    private Rect bmpRect;
    private int checkWH;
    private Drawable drawable;
    private boolean forPrint;
    private Context mContext;
    private int mDp2;
    private int mSize;
    private int nlGroup;
    private int nlLevel;
    private String nlName;
    private Rect viewRect;

    public NumListSpan() {
        super(2);
        this.bmpRect = new Rect();
        this.viewRect = new Rect();
        this.mContext = App.getAppContext();
        this.mSize = ScreenUtils.dpToPx(12);
        this.checkWH = ScreenUtils.dpToPx(18);
        this.mDp2 = ScreenUtils.dpToPx(2);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return createBitmap;
    }

    private Drawable zoomDrawable(Drawable drawable, int i6, int i7) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap drawableToBitmap = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        matrix.postScale(i6 / intrinsicWidth, i7 / intrinsicHeight);
        return new BitmapDrawable((Resources) null, Bitmap.createBitmap(drawableToBitmap, 0, 0, intrinsicWidth, intrinsicHeight, matrix, true));
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i6, int i7, float f6, int i8, int i9, int i10, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int i11 = (int) f6;
        canvas.save();
        float f7 = fontMetrics.leading;
        float f8 = fontMetrics.descent;
        float f9 = ((f7 + f8) - fontMetrics.ascent) + this.mDp2;
        float f10 = i8;
        float f11 = (f10 + f9) - f8;
        if (((Spanned) charSequence).getSpanStart(this) == i6 && !TextUtils.isEmpty(this.nlName)) {
            Rect rect = this.viewRect;
            int i12 = this.mSize;
            rect.set(i11, (int) (((f9 - i12) / 2.0f) + f10), i11 + i12, (int) (((f9 - i12) / 2.0f) + f10 + i12));
            if ("digital".equals(this.nlName)) {
                canvas.drawText(this.nlLevel + ".", i11, f11, paint);
            } else if ("Dots".equals(this.nlName)) {
                float f12 = ((this.mSize * 8) / 12.0f) / 2.0f;
                canvas.drawCircle(i11 + f12, f10 + (f9 / 2.0f), f12, paint);
            } else if ("checkList_selected".equals(this.nlName)) {
                Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.ic_checkbox10sp);
                int i13 = this.checkWH;
                Drawable zoomDrawable = zoomDrawable(drawable, i13, i13);
                int i14 = this.mDp2;
                int i15 = (int) ((i9 - (f9 / 2.0f)) - i14);
                int i16 = this.checkWH;
                zoomDrawable.setBounds(i11 - i14, i15, (i11 + i16) - i14, i16 + i15);
                if ("done".equals(this.nlName)) {
                    zoomDrawable.setTint(paint.getColor());
                }
                zoomDrawable.draw(canvas);
            } else if ("checkList_no".equals(this.nlName)) {
                Drawable drawable2 = this.mContext.getResources().getDrawable(R.drawable.shape_checklist_unchecked2);
                int i17 = this.checkWH;
                Drawable zoomDrawable2 = zoomDrawable(drawable2, i17, i17);
                int i18 = this.mDp2;
                int i19 = (int) ((i9 - (f9 / 2.0f)) - i18);
                int i20 = this.checkWH;
                zoomDrawable2.setBounds(i11 - i18, i19, (i11 + i20) - i18, i20 + i19);
                if ("done".equals(this.nlName)) {
                    zoomDrawable2.setTint(paint.getColor());
                }
                zoomDrawable2.draw(canvas);
                zoomDrawable2.draw(canvas);
            } else if ("checkList_no_white".equals(this.nlName)) {
                Drawable drawable3 = this.mContext.getResources().getDrawable(R.drawable.shape_checklist_unchecked_white2);
                int i21 = this.checkWH;
                Drawable zoomDrawable3 = zoomDrawable(drawable3, i21, i21);
                int i22 = this.mDp2;
                int i23 = (int) ((i9 - (f9 / 2.0f)) - i22);
                int i24 = this.checkWH;
                zoomDrawable3.setBounds(i11 - i22, i23, (i11 + i24) - i22, i24 + i23);
                if ("done".equals(this.nlName)) {
                    zoomDrawable3.setTint(paint.getColor());
                }
                zoomDrawable3.draw(canvas);
                zoomDrawable3.draw(canvas);
            } else {
                Drawable drawable4 = this.drawable;
                if (drawable4 != null) {
                    drawable4.setBounds(this.viewRect);
                    if ("done".equals(this.nlName)) {
                        this.drawable.setTint(paint.getColor());
                    }
                    this.drawable.draw(canvas);
                } else {
                    Bitmap bitmap = this.bitmap;
                    if (bitmap == null || bitmap.isRecycled()) {
                        this.bitmap = null;
                        return;
                    } else {
                        this.bmpRect.set(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
                        canvas.drawBitmap(this.bitmap, this.bmpRect, this.viewRect, (Paint) null);
                    }
                }
            }
        }
        canvas.restore();
    }

    @Override // android.text.style.DynamicDrawableSpan
    public Drawable getDrawable() {
        return null;
    }

    public int getNlGroup() {
        return this.nlGroup;
    }

    public int getNlLevel() {
        return this.nlLevel;
    }

    public String getNlName() {
        return this.nlName;
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i6, int i7, Paint.FontMetricsInt fontMetricsInt) {
        return this.mSize * 2;
    }

    public boolean isForPrint() {
        return this.forPrint;
    }

    public void setForPrint(boolean z6) {
        this.forPrint = z6;
        if (z6) {
            this.mSize = 12;
            this.mDp2 = 2;
            this.checkWH = 18;
        } else {
            this.mSize = ScreenUtils.dpToPx(12);
            this.mDp2 = ScreenUtils.dpToPx(2);
            this.checkWH = ScreenUtils.dpToPx(18);
        }
    }

    public void setNlGroup(int i6) {
        this.nlGroup = i6;
    }

    public void setNlLevel(int i6) {
        this.nlLevel = i6;
    }

    public void setNlName(String str) {
        setNumListName(str, this.nlLevel);
    }

    public void setNumListName(String str, int i6) {
        this.nlName = str;
        this.nlLevel = i6;
        this.bitmap = null;
        this.drawable = null;
        if ("digital".equals(str) || "Dots".equals(str)) {
            return;
        }
        TextUtils.isEmpty(str);
    }

    @Override // android.text.style.DynamicDrawableSpan
    public String toString() {
        return "MyBulletSpan{nlName='" + this.nlName + "', nlLevel=" + this.nlLevel + ", nlGroup=" + this.nlGroup + '}';
    }
}
