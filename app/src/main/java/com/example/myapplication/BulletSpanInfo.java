package com.example.myapplication;

import android.text.TextUtils;

public class BulletSpanInfo {
    private int line;
    private int lineEnd;
    private int lineStart;
    private MyBulletSpan myBulletSpan;

    public int getLine() {
        return this.line;
    }

    public int getLineEnd() {
        return this.lineEnd;
    }

    public int getLineStart() {
        return this.lineStart;
    }

    public MyBulletSpan getMyBulletSpan() {
        return this.myBulletSpan;
    }

    public boolean isSameName(String str) {
        MyBulletSpan myBulletSpan = this.myBulletSpan;
        if (myBulletSpan == null) {
            return false;
        }
        String nlName = myBulletSpan.getNlName();
        return !TextUtils.isEmpty(nlName) && nlName.equals(str);
    }

    public void setLine(int i6) {
        this.line = i6;
    }

    public void setLineEnd(int i6) {
        this.lineEnd = i6;
    }

    public void setLineStart(int i6) {
        this.lineStart = i6;
    }

    public void setMyBulletSpan(MyBulletSpan myBulletSpan) {
        this.myBulletSpan = myBulletSpan;
    }

    public String toString() {
        return "BulletSpanInfo{line=" + this.line + ", lineStart=" + this.lineStart + ", lineEnd=" + this.lineEnd + ", myBulletSpan=" + this.myBulletSpan + '}';
    }
}
