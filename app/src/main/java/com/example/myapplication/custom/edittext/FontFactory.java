package com.example.myapplication.custom.edittext;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class FontFactory {
    private static FontFactory instance;
    private Context context;
    private HashMap<String, Typeface> fontMap = new HashMap<>();

    private FontFactory(Context context) {
        this.context = context;
    }

    public static FontFactory getInstance(Context context) {
        FontFactory fontFactory = instance;
        if (fontFactory != null) {
            return fontFactory;
        }
        FontFactory fontFactory2 = new FontFactory(context);
        instance = fontFactory2;
        return fontFactory2;
    }

    public Typeface getFont(String str) {
        Typeface typeface = this.fontMap.get(str);
        if (typeface != null) {
            return typeface;
        }
        try {
            Typeface createFromAsset = Typeface.createFromAsset(this.context.getResources().getAssets(), str);
            this.fontMap.put(str, createFromAsset);
            return createFromAsset;
        } catch (Exception e7) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not get typeface: ");
            sb.append(e7.getMessage());
            sb.append(" with name: ");
            sb.append(str);
            return null;
        }
    }
}