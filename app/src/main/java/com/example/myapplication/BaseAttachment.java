package com.example.myapplication;

import java.util.Calendar;

/* loaded from: classes4.dex */
public class BaseAttachment {
    private long gradId;
    private Long id;
    private long length;
    private String mime_type;
    private String name;
    private int order;
    private long size;
    private int sort;
    private String uriPath;

    public BaseAttachment() {
        this.id = Long.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public long getGradId() {
        return this.gradId;
    }

    public Long getId() {
        return this.id;
    }

    public long getLength() {
        return this.length;
    }

    public String getMime_type() {
        return this.mime_type;
    }

    public String getName() {
        return this.name;
    }

    public int getOrder() {
        return this.order;
    }

    public long getSize() {
        return this.size;
    }

    public int getSort() {
        return this.sort;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    public void setGradId(long j6) {
        this.gradId = j6;
    }

    public void setId(Long l6) {
        this.id = l6;
    }

    public void setLength(long j6) {
        this.length = j6;
    }

    public void setMime_type(String str) {
        this.mime_type = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOrder(int i6) {
        this.order = i6;
    }

    public void setSize(long j6) {
        this.size = j6;
    }

    public void setSort(int i6) {
        this.sort = i6;
    }

    public void setUriPath(String str) {
        this.uriPath = str;
    }

    public BaseAttachment(String str, String str2) {
        this.id = Long.valueOf(Calendar.getInstance().getTimeInMillis());
        this.uriPath = str;
        setMime_type(str2);
    }

    public BaseAttachment(Long l6, String str, String str2, long j6, long j7, String str3, int i6, int i7) {
        this.id = l6;
        this.uriPath = str;
        this.name = str2;
        this.size = j6;
        this.length = j7;
        setMime_type(str3);
        this.order = i6;
        this.sort = i7;
    }
}