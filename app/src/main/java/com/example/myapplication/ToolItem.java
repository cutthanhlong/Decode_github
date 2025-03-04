package com.example.myapplication;

public class ToolItem {
    public static final int ACTION_ID_ADD_PIC = 105;
    public static final int ACTION_ID_AFFIX = 110;
    public static final int ACTION_ID_BULLET = 108;
    public static final int ACTION_ID_CHECKLIST = 102;
    public static final int ACTION_ID_DRAWING = 104;
    public static final int ACTION_ID_EMOJI = 106;
    public static final int ACTION_ID_NUMBER = 109;
    public static final int ACTION_ID_RECORDING = 103;
    public static final int ACTION_ID_RICH_FONT = 101;
    public static final int ACTION_ID_THEME = 107;
    private int drawableId;
    private int id;

    public ToolItem() {
    }

    public int getDrawableId() {
        return this.drawableId;
    }

    public int getId() {
        return this.id;
    }

    public void setDrawableId(int i6) {
        this.drawableId = i6;
    }

    public void setId(int i6) {
        this.id = i6;
    }

    public ToolItem(int i6, int i7) {
        this.id = i6;
        this.drawableId = i7;
    }
}
