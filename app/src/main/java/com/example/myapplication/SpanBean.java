package com.example.myapplication;

public class SpanBean {
    String content;
    int start;

    public SpanBean(int i6, String str) {
        this.start = i6;
        this.content = str;
    }

    public String getContent() {
        return this.content;
    }

    public int getStart() {
        return this.start;
    }
}
