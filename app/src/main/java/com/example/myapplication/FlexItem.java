package com.example.myapplication;

import android.os.Parcelable;

interface FlexItem extends Parcelable {
    public static final float FLEX_BASIS_PERCENT_DEFAULT = -1.0f;
    public static final float FLEX_GROW_DEFAULT = 0.0f;
    public static final float FLEX_SHRINK_DEFAULT = 1.0f;
    public static final float FLEX_SHRINK_NOT_SET = 0.0f;
    public static final int MAX_SIZE = 16777215;
    public static final int ORDER_DEFAULT = 1;

    int getAlignSelf();

    float getFlexBasisPercent();

    float getFlexGrow();

    float getFlexShrink();

    int getHeight();

    int getMarginBottom();

    int getMarginEnd();

    int getMarginLeft();

    int getMarginRight();

    int getMarginStart();

    int getMarginTop();

    int getMaxHeight();

    int getMaxWidth();

    int getMinHeight();

    int getMinWidth();

    int getOrder();

    int getWidth();

    boolean isWrapBefore();

    void setAlignSelf(int i6);

    void setFlexBasisPercent(float f6);

    void setFlexGrow(float f6);

    void setFlexShrink(float f6);

    void setHeight(int i6);

    void setMaxHeight(int i6);

    void setMaxWidth(int i6);

    void setMinHeight(int i6);

    void setMinWidth(int i6);

    void setOrder(int i6);

    void setWidth(int i6);

    void setWrapBefore(boolean z6);
}
