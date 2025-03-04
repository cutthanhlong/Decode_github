package com.example.myapplication.custom.edittext;

import android.text.style.ClickableSpan;
import android.view.View;

public class InternalURLSpan extends ClickableSpan {
    private String clickedSpan;
    private TextLinkClickListener mTextLinkClickListener;

    public InternalURLSpan(String str) {
        this.clickedSpan = str;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
        TextLinkClickListener textLinkClickListener = this.mTextLinkClickListener;
        String str = this.clickedSpan;
        textLinkClickListener.onTextLinkClick(view, str, UrlCompleter.complete(str));
    }

    public void setTextLinkClickListener(TextLinkClickListener textLinkClickListener) {
        this.mTextLinkClickListener = textLinkClickListener;
    }
}
