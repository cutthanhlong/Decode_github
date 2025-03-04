package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import java.io.File;
import java.util.List;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.ui.model.NoteBgBean;
import notes.easy.android.mynotes.ui.model.NoteBgImageBean;
import notes.easy.android.mynotes.utils.BitmapUtil;
import notes.easy.android.mynotes.utils.EventBus.EventUtils;
import notes.easy.android.mynotes.utils.ResNoteBgManager;
import notes.easy.android.mynotes.utils.ScreenUtils;

/* loaded from: classes5.dex */
public class NoteBgEditView extends View {
    private Drawable mBgColorDrawable;
    private Drawable mBgPicDrawable;
    private int mBottomFloatMargin;
    private int mFrameBottomMargin;
    private int mFrameHorizontalMargin;
    private int mFrameRadius;
    private int mFrameTopMargin;
    private boolean mFroceFollowEdit;
    private boolean mHasBottomImg;
    private boolean mInputPanShow;
    private Paint mLinePaint;
    private float mLineSpacing;
    private int mLineStyle;
    private List<NoteBgImageBean> mListImage;
    private NoteBgBean mNoteBg;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private Paint mPaint;
    private int mVisibleHeightPrevious;

    public NoteBgEditView(Context context) {
        this(context, null);
    }

    private int computeVisibleHeight() {
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        return rect.bottom - rect.top;
    }

    private void drawBg(Canvas canvas) {
        Drawable drawable = this.mBgColorDrawable;
        if (drawable != null) {
            drawBgClamp(drawable, canvas);
        }
        if (this.mBgPicDrawable != null) {
            if (TextUtils.equals(this.mNoteBg.getBg().getBgType(), ResNoteBgManager.IMAGES_TYPE_CLAMP)) {
                drawBgClamp(this.mBgPicDrawable, canvas);
            } else if (TextUtils.equals(this.mNoteBg.getBg().getBgType(), ResNoteBgManager.IMAGES_TYPE_REPEAT)) {
                drawBgRepeat(this.mBgPicDrawable, canvas);
            } else {
                drawBgCenterCrop(this.mBgPicDrawable, canvas);
            }
        }
    }

    private void drawBgCenterCrop(Drawable drawable, Canvas canvas) {
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        float max = Math.max((getMeasuredWidth() * 1.0f) / intrinsicWidth, (getMeasuredHeight() * 1.0f) / intrinsicHeight);
        int i6 = (int) (intrinsicWidth * max);
        int i7 = (int) (intrinsicHeight * max);
        int measuredWidth = (getMeasuredWidth() - i6) / 2;
        int measuredHeight = (getMeasuredHeight() - i7) / 2;
        drawable.setBounds(measuredWidth, measuredHeight, i6 + measuredWidth, i7 + measuredHeight);
        drawable.draw(canvas);
    }

    private void drawBgClamp(Drawable drawable, Canvas canvas) {
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
    }

    private void drawBgRepeat(Drawable drawable, Canvas canvas) {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Shader.TileMode tileMode = Shader.TileMode.REPEAT;
            this.mPaint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
            canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.mPaint);
            this.mPaint.setShader(null);
        }
    }

    private void drawFrame(Canvas canvas) {
        NoteBgBean.Frame frame = this.mNoteBg.getFrame();
        if (frame == null || TextUtils.isEmpty(frame.getEditBgColor())) {
            return;
        }
        this.mPaint.setColor(Color.parseColor(frame.getEditBgColor()));
        float f6 = this.mFrameHorizontalMargin;
        float f7 = this.mFrameTopMargin;
        float measuredWidth = getMeasuredWidth() - this.mFrameHorizontalMargin;
        float measuredHeight = getMeasuredHeight() - this.mFrameBottomMargin;
        int i6 = this.mFrameRadius;
        canvas.drawRoundRect(f6, f7, measuredWidth, measuredHeight, i6, i6, this.mPaint);
    }

    private void drawImgListBottom(Canvas canvas) {
        if (this.mListImage.size() <= 0 || this.mInputPanShow) {
            return;
        }
        boolean z6 = ScreenUtils.isPad(getContext()) && ScreenUtils.isScreenOriatationLandscap(getContext());
        for (int i6 = 0; i6 < this.mListImage.size(); i6++) {
            NoteBgImageBean noteBgImageBean = this.mListImage.get(i6);
            Drawable drawable = noteBgImageBean.img;
            int i7 = noteBgImageBean.position;
            float f6 = noteBgImageBean.landWidthRatio;
            float f7 = noteBgImageBean.portWidthRatio;
            boolean z7 = noteBgImageBean.isFloat;
            if (i7 != 2 && i7 != 1 && i7 != 3) {
                if (!z6) {
                    f6 = f7;
                }
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int measuredWidth = (int) (getMeasuredWidth() * 1.0f * f6);
                int intrinsicHeight = (int) (((measuredWidth * 1.0f) / intrinsicWidth) * drawable.getIntrinsicHeight());
                if (i7 != 4) {
                    if (i7 != 5) {
                        if (i7 == 6) {
                            if (z7) {
                                drawable.setBounds(getMeasuredWidth() - measuredWidth, (getMeasuredHeight() - intrinsicHeight) - this.mBottomFloatMargin, getMeasuredWidth(), getMeasuredHeight() - this.mBottomFloatMargin);
                            } else {
                                drawable.setBounds(getMeasuredWidth() - measuredWidth, getMeasuredHeight() - intrinsicHeight, getMeasuredWidth(), getMeasuredHeight());
                            }
                        }
                    } else if (z7) {
                        drawable.setBounds(0, (getMeasuredHeight() - intrinsicHeight) - this.mBottomFloatMargin, measuredWidth, getMeasuredHeight() - this.mBottomFloatMargin);
                    } else {
                        drawable.setBounds(0, getMeasuredHeight() - intrinsicHeight, measuredWidth, getMeasuredHeight());
                    }
                } else if (z7) {
                    drawable.setBounds((getMeasuredWidth() - measuredWidth) / 2, (getMeasuredHeight() - intrinsicHeight) - this.mBottomFloatMargin, ((getMeasuredWidth() - measuredWidth) / 2) + measuredWidth, getMeasuredHeight() - this.mBottomFloatMargin);
                } else {
                    drawable.setBounds((getMeasuredWidth() - measuredWidth) / 2, getMeasuredHeight() - intrinsicHeight, ((getMeasuredWidth() - measuredWidth) / 2) + measuredWidth, getMeasuredHeight());
                }
                drawable.draw(canvas);
            }
        }
    }

    private void drawImgListTop(Canvas canvas) {
        if (this.mListImage.size() > 0) {
            boolean z6 = ScreenUtils.isPad(getContext()) && ScreenUtils.isScreenOriatationLandscap(getContext());
            for (int i6 = 0; i6 < this.mListImage.size(); i6++) {
                NoteBgImageBean noteBgImageBean = this.mListImage.get(i6);
                Drawable drawable = noteBgImageBean.img;
                int i7 = noteBgImageBean.position;
                float f6 = noteBgImageBean.landWidthRatio;
                float f7 = noteBgImageBean.portWidthRatio;
                if (i7 != 5 && i7 != 4 && i7 != 6) {
                    if (!z6) {
                        f6 = f7;
                    }
                    int intrinsicWidth = drawable.getIntrinsicWidth();
                    int measuredWidth = (int) (getMeasuredWidth() * 1.0f * f6);
                    int intrinsicHeight = (int) (((measuredWidth * 1.0f) / intrinsicWidth) * drawable.getIntrinsicHeight());
                    if (i7 == 1) {
                        drawable.setBounds((getMeasuredWidth() - measuredWidth) / 2, 0, ((getMeasuredWidth() - measuredWidth) / 2) + measuredWidth, intrinsicHeight);
                    } else if (i7 == 2) {
                        drawable.setBounds(0, 0, measuredWidth, intrinsicHeight);
                    } else if (i7 == 3) {
                        drawable.setBounds(getMeasuredWidth() - measuredWidth, 0, getMeasuredWidth(), intrinsicHeight);
                    }
                    drawable.draw(canvas);
                }
            }
        }
    }

    private void drawLine(Canvas canvas) {
        int i6 = this.mLineStyle;
        int i7 = 0;
        if (i6 == 3) {
            float measuredHeight = getMeasuredHeight();
            float f6 = this.mLineSpacing;
            int i8 = (int) ((measuredHeight - (f6 / 2.0f)) / f6);
            float measuredWidth = getMeasuredWidth();
            float f7 = this.mLineSpacing;
            int i9 = (int) ((measuredWidth - (f7 / 2.0f)) / f7);
            for (int i10 = 0; i10 <= i8; i10++) {
                float f8 = i10 + 0.5f;
                canvas.drawLine(0.0f, this.mLineSpacing * f8, getMeasuredWidth(), this.mLineSpacing * f8, this.mLinePaint);
            }
            while (i7 <= i9) {
                float f9 = this.mLineSpacing;
                float f10 = i7 + 0.5f;
                canvas.drawLine(f9 * f10, 0.0f, f9 * f10, getMeasuredHeight(), this.mLinePaint);
                i7++;
            }
            return;
        }
        if (i6 == 1) {
            float measuredHeight2 = getMeasuredHeight();
            float f11 = this.mLineSpacing;
            int i11 = (int) ((measuredHeight2 - (f11 / 2.0f)) / f11);
            while (i7 <= i11) {
                float f12 = i7 + 0.5f;
                canvas.drawLine(0.0f, this.mLineSpacing * f12, getMeasuredWidth(), this.mLineSpacing * f12, this.mLinePaint);
                i7++;
            }
            return;
        }
        if (i6 == 4) {
            float measuredHeight3 = getMeasuredHeight();
            float f13 = this.mLineSpacing;
            int i12 = (int) ((measuredHeight3 - (f13 / 2.0f)) / f13);
            while (i7 <= i12) {
                float f14 = i7 + 0.5f;
                canvas.drawLine(0.0f, this.mLineSpacing * f14, getMeasuredWidth(), this.mLineSpacing * f14, this.mLinePaint);
                i7++;
            }
            return;
        }
        if (i6 == 2) {
            float measuredHeight4 = getMeasuredHeight();
            float f15 = this.mLineSpacing;
            int i13 = (int) ((measuredHeight4 - (f15 / 2.0f)) / f15);
            float measuredWidth2 = getMeasuredWidth();
            float f16 = this.mLineSpacing;
            int i14 = (int) ((measuredWidth2 - (f16 / 2.0f)) / f16);
            for (int i15 = 0; i15 <= i13; i15++) {
                for (int i16 = 0; i16 <= i14; i16++) {
                    float f17 = this.mLineSpacing;
                    canvas.drawCircle((i16 + 0.5f) * f17, f17 * (i15 + 0.5f), this.mLinePaint.getStrokeWidth(), this.mLinePaint);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void possiblyResizeChildOfContent() {
        int computeVisibleHeight = computeVisibleHeight();
        if (computeVisibleHeight != this.mVisibleHeightPrevious) {
            int height = getRootView().getHeight();
            if (height - computeVisibleHeight > height / 4) {
                this.mInputPanShow = true;
                if (this.mHasBottomImg) {
                    invalidate();
                }
            } else {
                this.mInputPanShow = false;
                if (this.mHasBottomImg) {
                    invalidate();
                }
            }
            this.mVisibleHeightPrevious = computeVisibleHeight;
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mNoteBg == null) {
            return;
        }
        drawBg(canvas);
        drawLine(canvas);
        drawImgListTop(canvas);
        drawFrame(canvas);
        drawImgListBottom(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i6, int i7) {
        super.onMeasure(i6, i7);
    }

    public void setForceFollowEdit(boolean z6) {
        this.mFroceFollowEdit = z6;
    }

    public void setNoteBg(NoteBgBean noteBgBean) {
        setNoteBg(noteBgBean, false);
    }

    public NoteBgEditView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setNoteBg(final NoteBgBean noteBgBean, boolean z6) {
        int i6;
        int i7;
        if (noteBgBean != null) {
            if (this.mNoteBg != null) {
                if (noteBgBean.getId() == 10) {
                    if (noteBgBean.getCustom() != null && this.mNoteBg.getCustom() != null && TextUtils.equals(noteBgBean.getCustom().getCustomBg(), this.mNoteBg.getCustom().getCustomBg())) {
                        invalidate();
                        return;
                    }
                } else if (!z6 && noteBgBean.getId() == this.mNoteBg.getId()) {
                    invalidate();
                    return;
                }
            }
            this.mNoteBg = noteBgBean;
            this.mBgColorDrawable = null;
            this.mBgPicDrawable = null;
            ResNoteBgManager.BgDownloadListener bgDownloadListener = new ResNoteBgManager.BgDownloadListener() { // from class: notes.easy.android.mynotes.view.NoteBgEditView.2
                @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
                public void onDownloadSuccess(NoteBgBean noteBgBean2, String str) {
                    EventUtils.post(117, Integer.valueOf(noteBgBean.getId()));
                }

                @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
                public void onDownloadFailed(NoteBgBean noteBgBean2) {
                }

                @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
                public void onDownloadStart(NoteBgBean noteBgBean2) {
                }

                @Override // notes.easy.android.mynotes.utils.ResNoteBgManager.BgDownloadListener
                public void updateDownloadProgress(NoteBgBean noteBgBean2, long j6, float f6, float f7) {
                }
            };
            if (noteBgBean.getBg() != null) {
                NoteBgBean.Background bg = noteBgBean.getBg();
                if (!TextUtils.isEmpty(bg.getBgName())) {
                    Drawable drawableFromFile = ResNoteBgManager.getDrawableFromFile(bg.getBgName());
                    this.mBgPicDrawable = drawableFromFile;
                    if (drawableFromFile == null) {
                        ResNoteBgManager.getInstance().startDownloadZip(noteBgBean, bgDownloadListener);
                    }
                }
                if (bg.getGradientColors() != null) {
                    this.mBgColorDrawable = ResNoteBgManager.getGradientDrawable(bg.getGradientColors(), bg.getOrientation());
                }
            }
            if (noteBgBean.getCustom() != null) {
                try {
                    Bitmap decodeFile = BitmapFactory.decodeFile(new File(App.getAppContext().getExternalFilesDir(null), noteBgBean.getCustom().getCustomBg()).getPath());
                    if (decodeFile != null) {
                        this.mBgPicDrawable = new BitmapDrawable(decodeFile);
                    }
                } catch (Exception unused) {
                }
                if (this.mBgPicDrawable == null) {
                    try {
                        Bitmap bitmapFromUri = BitmapUtil.getBitmapFromUri(App.getAppContext(), Uri.parse(noteBgBean.getCustom().getCustomBg()));
                        if (bitmapFromUri != null) {
                            this.mBgPicDrawable = new BitmapDrawable(bitmapFromUri);
                        }
                    } catch (Exception unused2) {
                        this.mBgPicDrawable = null;
                    }
                }
            }
            this.mLineStyle = 0;
            if (noteBgBean.getLine() != null) {
                NoteBgBean.Line line = noteBgBean.getLine();
                if (!line.isFollowEdit() || this.mFroceFollowEdit) {
                    this.mLinePaint.setColor(-1);
                    try {
                        this.mLinePaint.setColor(Color.parseColor(line.getLineColor()));
                    } catch (Exception unused3) {
                    }
                    float lineSpacing = line.getLineSpacing();
                    if (lineSpacing == 0.0f) {
                        lineSpacing = 15.0f;
                    }
                    this.mLineSpacing = ScreenUtils.dpToPx(lineSpacing);
                    float lineWidth = line.getLineWidth();
                    if (lineWidth == 0.0f) {
                        lineWidth = 1.0f;
                    }
                    float dpToPx = ScreenUtils.dpToPx(lineWidth);
                    this.mLinePaint.setStrokeWidth(dpToPx);
                    this.mLinePaint.setPathEffect(null);
                    if (TextUtils.equals(line.getLineStyle(), "line")) {
                        if (TextUtils.equals(line.getLineType(), ResNoteBgManager.LINE_STYLE_DASH)) {
                            this.mLineStyle = 4;
                            float lineLength = line.getLineLength();
                            float f6 = 5.0f;
                            if (lineLength == 0.0f) {
                                lineLength = line.isFollowEdit() ? 5.0f : ScreenUtils.dpToPx(4);
                            }
                            float lineGap = line.getLineGap();
                            if (lineGap != 0.0f) {
                                f6 = lineGap;
                            } else if (!line.isFollowEdit()) {
                                f6 = ScreenUtils.dpToPx(2);
                            }
                            this.mLinePaint.setPathEffect(new DashPathEffect(new float[]{lineLength, f6}, 0.0f));
                        } else {
                            this.mLineStyle = 1;
                        }
                    } else if (TextUtils.equals(line.getLineStyle(), "grid")) {
                        this.mLineStyle = 3;
                    } else if (TextUtils.equals(line.getLineStyle(), ResNoteBgManager.LINE_TYPE_POINT)) {
                        this.mLineStyle = 2;
                        this.mLinePaint.setStrokeWidth(dpToPx * 2.0f);
                    }
                }
            }
            this.mHasBottomImg = false;
            this.mListImage.clear();
            if (ScreenUtils.isPad(App.getAppContext())) {
                if (noteBgBean.getPadImages() != null && noteBgBean.getPadImages().length > 0) {
                    for (int i8 = 0; i8 < noteBgBean.getPadImages().length; i8++) {
                        NoteBgBean.PadImages padImages = noteBgBean.getPadImages()[i8];
                        Drawable drawableFromFile2 = ResNoteBgManager.getDrawableFromFile(padImages.getImageName());
                        if (drawableFromFile2 == null) {
                            ResNoteBgManager.getInstance().startDownloadZip(noteBgBean, bgDownloadListener);
                        } else {
                            if (TextUtils.equals(padImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_TOP_CENTER)) {
                                i7 = 1;
                            } else if (TextUtils.equals(padImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_TOP_LEFT)) {
                                i7 = 2;
                            } else if (TextUtils.equals(padImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_TOP_RIGHT)) {
                                i7 = 3;
                            } else if (TextUtils.equals(padImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_BOTTOM_CENTER)) {
                                this.mHasBottomImg = true;
                                i7 = 4;
                            } else if (TextUtils.equals(padImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_BOTTOM_LEFT)) {
                                this.mHasBottomImg = true;
                                i7 = 5;
                            } else if (TextUtils.equals(padImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_BOTTOM_RIGHT)) {
                                this.mHasBottomImg = true;
                                i7 = 6;
                            } else {
                                i7 = 0;
                            }
                            NoteBgImageBean noteBgImageBean = new NoteBgImageBean();
                            noteBgImageBean.img = drawableFromFile2;
                            noteBgImageBean.position = i7;
                            noteBgImageBean.isFloat = padImages.isFloat();
                            noteBgImageBean.portWidthRatio = padImages.getImagePortraitRatio();
                            noteBgImageBean.landWidthRatio = padImages.getImageLandscapeRatio();
                            this.mListImage.add(noteBgImageBean);
                        }
                    }
                }
            } else if (noteBgBean.getPhoneImages() != null && noteBgBean.getPhoneImages().length > 0) {
                for (int i9 = 0; i9 < noteBgBean.getPhoneImages().length; i9++) {
                    NoteBgBean.PhoneImages phoneImages = noteBgBean.getPhoneImages()[i9];
                    Drawable drawableFromFile3 = ResNoteBgManager.getDrawableFromFile(phoneImages.getImageName());
                    if (drawableFromFile3 == null) {
                        ResNoteBgManager.getInstance().startDownloadZip(noteBgBean, bgDownloadListener);
                    } else {
                        if (TextUtils.equals(phoneImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_TOP_CENTER)) {
                            i6 = 1;
                        } else if (TextUtils.equals(phoneImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_TOP_LEFT)) {
                            i6 = 2;
                        } else if (TextUtils.equals(phoneImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_TOP_RIGHT)) {
                            i6 = 3;
                        } else if (TextUtils.equals(phoneImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_BOTTOM_CENTER)) {
                            this.mHasBottomImg = true;
                            i6 = 4;
                        } else if (TextUtils.equals(phoneImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_BOTTOM_LEFT)) {
                            this.mHasBottomImg = true;
                            i6 = 5;
                        } else if (TextUtils.equals(phoneImages.getImagePosition(), ResNoteBgManager.IMAGE_POSITION_BOTTOM_RIGHT)) {
                            this.mHasBottomImg = true;
                            i6 = 6;
                        } else {
                            i6 = 0;
                        }
                        NoteBgImageBean noteBgImageBean2 = new NoteBgImageBean();
                        noteBgImageBean2.img = drawableFromFile3;
                        noteBgImageBean2.position = i6;
                        noteBgImageBean2.isFloat = phoneImages.isFloat();
                        noteBgImageBean2.portWidthRatio = phoneImages.getImageWidthRatio();
                        this.mListImage.add(noteBgImageBean2);
                    }
                }
            }
        }
        invalidate();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004a, code lost:

        if (r4 == null) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NoteBgEditView(android.content.Context r3, android.util.AttributeSet r4, int r5) {
        /*
            r2 = this;
            r2.<init>(r3, r4, r5)
            r4 = 0
            r2.mBgColorDrawable = r4
            r2.mBgPicDrawable = r4
            r5 = 0
            r2.mLineStyle = r5
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.mListImage = r0
            r2.mVisibleHeightPrevious = r5
            r2.mHasBottomImg = r5
            r2.mInputPanShow = r5
            r2.mFroceFollowEdit = r5
            android.graphics.Paint r0 = new android.graphics.Paint
            r0.<init>()
            r2.mPaint = r0
            r1 = 1
            r0.setAntiAlias(r1)
            android.graphics.Paint r0 = new android.graphics.Paint
            r0.<init>()
            r2.mLinePaint = r0
            r0.setAntiAlias(r1)
            int[] r0 = new int[r1]     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L49
            r1 = 16843499(0x10102eb, float:2.3695652E-38)
            r0[r5] = r1     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L49
            android.content.res.TypedArray r4 = r3.obtainStyledAttributes(r0)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L49
            int r5 = r4.getDimensionPixelOffset(r5, r5)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L49
        L3e:
            r4.recycle()
            goto L4d
        L42:
            r3 = move-exception
            if (r4 == 0) goto L48
            r4.recycle()
        L48:
            throw r3
        L49:
            if (r4 == 0) goto L4d
            goto L3e
        L4d:
            android.content.res.Resources r4 = r3.getResources()
            r0 = 2131166655(0x7f0705bf, float:1.7947561E38)
            int r4 = r4.getDimensionPixelOffset(r0)
            r2.mFrameHorizontalMargin = r4
            android.content.res.Resources r4 = r3.getResources()
            r1 = 2131166669(0x7f0705cd, float:1.794759E38)
            int r4 = r4.getDimensionPixelOffset(r1)
            r2.mFrameBottomMargin = r4
            android.content.res.Resources r4 = r3.getResources()
            r1 = 2131165385(0x7f0700c9, float:1.7944986E38)
            int r4 = r4.getDimensionPixelOffset(r1)
            int r4 = r4 + r5
            r2.mFrameTopMargin = r4
            android.content.res.Resources r4 = r3.getResources()
            int r4 = r4.getDimensionPixelOffset(r0)
            r2.mFrameRadius = r4
            android.content.res.Resources r3 = r3.getResources()
            r4 = 2131166649(0x7f0705b9, float:1.794755E38)
            int r3 = r3.getDimensionPixelOffset(r4)
            r2.mBottomFloatMargin = r3
            notes.easy.android.mynotes.view.NoteBgEditView$1 r3 = new notes.easy.android.mynotes.view.NoteBgEditView$1
            r3.<init>()
            r2.mOnGlobalLayoutListener = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: notes.easy.android.mynotes.view.NoteBgEditView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }
}
