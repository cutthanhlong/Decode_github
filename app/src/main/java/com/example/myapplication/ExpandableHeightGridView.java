package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/* loaded from: classes4.dex */
public class ExpandableHeightGridView extends GridView {
    private int itemHeight;

    public ExpandableHeightGridView(Context context) {
        super(context);
    }

    public void autoresize() {
        setNumColumns(2);
    }

    public int getItemHeight() {
        return this.itemHeight;
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public void onMeasure(int i6, int i7) {
        super.onMeasure(i6, View.MeasureSpec.makeMeasureSpec(FlexItem.MAX_SIZE, MeasureSpec.AT_MOST));
        getLayoutParams().height = getMeasuredHeight();
    }

    public void setGridNumColumns(int i6) {
        setNumColumns(i6);
    }

    public ExpandableHeightGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ExpandableHeightGridView(Context context, AttributeSet attributeSet, int i6) {
        super(context, attributeSet, i6);
    }
}
