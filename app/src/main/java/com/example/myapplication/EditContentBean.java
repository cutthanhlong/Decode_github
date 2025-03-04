package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EditContentBean {
    public static final int AUDIO = 5;
    public static final int BULLET = 2;
    public static final int CHECK = 1;
    public static final int IMG = 4;
    public static final int NUMBER = 3;
    public static final int TEXT = 0;
    public boolean aBoolean;
    private Attachment attachment;
    private List<Attachment> attachmentsList;
    private String content;
    private int end;
    private int indentation;
    private int num;
    private String richData;
    private boolean selected;
    private int start;
    private int viewType;

    public EditContentBean(int i6, String str, Attachment attachment, List<Attachment> list, String str2, boolean z6, int i7, int i8) {
        this.viewType = 0;
        this.content = "";
        this.richData = "";
        new ArrayList();
        this.aBoolean = false;
        this.viewType = i6;
        this.content = str;
        this.attachment = attachment;
        this.attachmentsList = list;
        this.richData = str2;
        this.selected = z6;
        this.num = i7;
        this.indentation = i8;
    }

    public static EditContentBean newAudio(Attachment attachment) {
        return new EditContentBean(5, "", attachment, new ArrayList(), "", false, 1, 0);
    }

    public static EditContentBean newBean(int i6) {
        return new EditContentBean(i6, "", null, new ArrayList(), "", false, 1, 0);
    }

    public static EditContentBean newBullet(String str) {
        return new EditContentBean(2, str, null, new ArrayList(), "", false, 1, 0);
    }

    public static EditContentBean newCheck(String str) {
        return new EditContentBean(1, str, null, new ArrayList(), "", false, 1, 0);
    }

    public static EditContentBean newImg(List<Attachment> list) {
        return new EditContentBean(4, "", null, list, "", false, 1, 0);
    }

    public static EditContentBean newNumber(String str, int i6) {
        return new EditContentBean(3, str, null, new ArrayList(), "", false, i6, 0);
    }

    public static EditContentBean newText(String str) {
        return new EditContentBean(0, str, null, new ArrayList(), "", false, 1, 0);
    }

    public Attachment getAttachment() {
        return this.attachment;
    }

    public List<Attachment> getAttachmentsList() {
        return this.attachmentsList;
    }

    public String getContent() {
        return this.content;
    }

    public int getEnd() {
        return this.end;
    }

    public int getIndentation() {
        return this.indentation;
    }

    public int getNum() {
        return this.num;
    }

    public String getRichData() {
        return this.richData;
    }

    public int getStart() {
        return this.start;
    }

    public int getViewType() {
        return this.viewType;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentsList(List<Attachment> list) {
        this.attachmentsList = list;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setEnd(int i6) {
        this.end = i6;
    }

    public void setIndentation(int i6) {
        this.indentation = i6;
    }

    public void setNum(int i6) {
        this.num = i6;
    }

    public void setRichData(String str) {
        this.richData = str;
    }

    public void setSelected(boolean z6) {
        this.selected = z6;
    }

    public void setStart(int i6) {
        this.start = i6;
    }

    public void setViewType(int i6) {
        this.viewType = i6;
    }

    public String toString() {
        return "EditContentBean{viewType=" + this.viewType + ", content='" + this.content + "', richData='" + this.richData + "', selected=" + this.selected + ", attachment=" + this.attachment + ", attachmentsList=" + this.attachmentsList + ", num=" + this.num + ", start=" + this.start + ", end=" + this.end + '}';
    }
}
