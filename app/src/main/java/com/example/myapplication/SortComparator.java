package com.example.myapplication;

import java.util.Comparator;

/* loaded from: classes5.dex */
public class SortComparator implements Comparator<Attachment> {
    @Override // java.util.Comparator
    public int compare(Attachment attachment, Attachment attachment2) {
        return attachment.getSort() - attachment2.getSort();
    }
}
