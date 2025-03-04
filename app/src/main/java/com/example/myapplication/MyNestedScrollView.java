package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.widget.NestedScrollView;

/* loaded from: classes5.dex */
public class MyNestedScrollView extends NestedScrollView {
    private boolean intercept;
    private OnScrollChangedListener onScrollChangedListener;

    public interface OnScrollChangedListener {
        void onScrollChanged(MyNestedScrollView myNestedScrollView, int i6, int i7, int i8, int i9);
    }

    public MyNestedScrollView(Context context) {
        super(context);
        this.onScrollChangedListener = null;
    }

    public boolean isIntercept() {
        return this.intercept;
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.intercept) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    protected void onScrollChanged(int i6, int i7, int i8, int i9) {
        super.onScrollChanged(i6, i7, i8, i9);
        OnScrollChangedListener onScrollChangedListener = this.onScrollChangedListener;
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, i6, i7, i8, i9);
        }
    }

    public void setIntercept(boolean z6) {
        this.intercept = z6;
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    public MyNestedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.onScrollChangedListener = null;
    }

    public MyNestedScrollView(Context context, AttributeSet attributeSet, int i6) {
        super(context, attributeSet, i6);
        this.onScrollChangedListener = null;
    }
}
