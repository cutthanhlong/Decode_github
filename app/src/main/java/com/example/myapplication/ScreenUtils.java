package com.example.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes2.dex */
public final class ScreenUtils {

    /* renamed from: a, reason: collision with root package name */
    public final Context f3596a;

    /* renamed from: b, reason: collision with root package name */
    public final Lazy f3597b;

    /* renamed from: c, reason: collision with root package name */
    public final float f3598c;

    public static final class a extends Lambda implements Function0<Boolean> {
        public a() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public Boolean invoke() {
            return Boolean.valueOf((ScreenUtils.this.f3596a.getResources().getConfiguration().screenLayout & 15) >= 3);
        }
    }

    public ScreenUtils(Context context) {
        Lazy lazy;
        Intrinsics.checkNotNullParameter(context, "context");
        this.f3596a = context;
        lazy = LazyKt__LazyJVMKt.lazy(new a());
        this.f3597b = lazy;
        this.f3598c = a().density;
    }

    public DisplayMetrics a() {
        return this.f3596a.getResources().getDisplayMetrics();
    }

    public int dpToPx(int i6) {
        return (int) ((i6 * this.f3598c) + 0.5f);
    }

    public float getScreenDensity() {
        return this.f3598c;
    }

    public int getScreenHeight() {
        return a().heightPixels;
    }

    public String getScreenOrientation() {
        DisplayMetrics displayMetrics = this.f3596a.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels > displayMetrics.heightPixels ? "landscape" : "portrait";
    }

    public int getScreenWidth() {
        return a().widthPixels;
    }

    public boolean isTablet() {
        return ((Boolean) this.f3597b.getValue()).booleanValue();
    }

    public int pxToDp(int i6) {
        return !((this.f3598c > 0.0f ? 1 : (this.f3598c == 0.0f ? 0 : -1)) == 0) ? (int) Math.ceil(i6 / r0) : i6;
    }
}
