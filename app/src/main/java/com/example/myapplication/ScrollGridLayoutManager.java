package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;

/* loaded from: classes5.dex */
public class ScrollGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled;

    public ScrollGridLayoutManager(Context context, AttributeSet attributeSet, int i6, int i7) {
        super(context, attributeSet, i6, i7);
        this.isScrollEnabled = true;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return false;
    }

    public void setScrollEnabled(boolean z6) {
        this.isScrollEnabled = z6;
    }

    public ScrollGridLayoutManager(Context context, int i6) {
        super(context, i6);
        this.isScrollEnabled = true;
    }

    public ScrollGridLayoutManager(Context context, int i6, int i7, boolean z6) {
        super(context, i6, i7, z6);
        this.isScrollEnabled = true;
    }
}
