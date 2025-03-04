package com.example.myapplication;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import androidx.appcompat.widget.SearchView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import easynotes.notes.notepad.notebook.privatenotes.note.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import notes.easy.android.mynotes.App;
import notes.easy.android.mynotes.models.EmojiPack;
import notes.easy.android.mynotes.ui.model.EmojiBean;

/* loaded from: classes5.dex */
public class EmojiManager {
    public static final String EMOJI_PREFIX = "emoji_";
    public static final String TAG = "EmojiManager";
    public static final String TAG_EMOJI = "emoji";
    public static final String TAG_EMOJI_COVER_NAME = "coverName";
    public static final String TAG_EMOJI_ENTRY = "entry";
    public static final String TAG_EMOJI_KEY = "key";
    public static final String TAG_EMOJI_NEW_PACK = "newPack";
    public static final String TAG_EMOJI_PACK = "emojipack";
    public static final String TAG_EMOJI_PACK_NAME = "packName";
    public static final String TAG_EMOJI_PREMIUM = "premium";
    public static final String EMOJI_REGEX = "\\[&[^\\]]*\\]";
    public static final Pattern PATTERN = Pattern.compile(EMOJI_REGEX);
    private static Map<String, EmojiBean> sEmojiEntryMap = new HashMap();
    private static List<EmojiPack> sAllEmojiPackList = new ArrayList();

    public static List<EmojiPack> getEmojiPackList(Context context) {
        return new ArrayList(getEmojiPackList(context, R.xml.easynotes_emoji_map));
    }

    public static int getEmojiSize(SpannableStringBuilder spannableStringBuilder, int i6, int i7) {
        if (spannableStringBuilder == null) {
            return 0;
        }
        if (sEmojiEntryMap.size() <= 0) {
            getEmojiPackList(App.getAppContext());
        }
        if (i7 > spannableStringBuilder.length()) {
            i7 = spannableStringBuilder.length();
        }
        if (i6 >= i7) {
            return 0;
        }
        Matcher matchEmoji = matchEmoji(spannableStringBuilder.subSequence(i6, i7));
        while (matchEmoji.find()) {
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) spannableStringBuilder.getSpans(matchEmoji.start() + i6, matchEmoji.end() + i6, EmojiSpan.class);
            if (emojiSpanArr != null) {
                return emojiSpanArr.length;
            }
        }
        return 0;
    }

    public static Map<String, String> getHashMap(Context context, int i6) {
        XmlResourceParser xml = context.getResources().getXml(i6);
        try {
            Map<String, String> map = null;
            String str = null;
            String str2 = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        if (xml.getName().equals("map")) {
                            map = xml.getAttributeBooleanValue(null, "linked", false) ? new LinkedHashMap<>() : new HashMap<>();
                        } else if (xml.getName().equals(TAG_EMOJI_ENTRY) && (str = xml.getAttributeValue(null, "key")) == null) {
                            xml.close();
                            return null;
                        }
                    } else if (eventType == 3) {
                        if (xml.getName().equals(TAG_EMOJI_ENTRY)) {
                            map.put(str, str2);
                            str = null;
                            str2 = null;
                        }
                    } else if (eventType == 4 && str != null) {
                        str2 = xml.getText();
                    }
                }
            }
            return map;
        } catch (Exception e7) {
            e7.printStackTrace();
            return null;
        }
    }

    public static StringBuilder getStringWithoutEmoji(SpannableStringBuilder spannableStringBuilder) {
        if (spannableStringBuilder == null) {
            return null;
        }
        if (sEmojiEntryMap.size() <= 0) {
            getEmojiPackList(App.getAppContext());
        }
        StringBuilder sb = new StringBuilder();
        spannableStringBuilder.length();
        System.currentTimeMillis();
        Matcher matchEmoji = matchEmoji(spannableStringBuilder);
        boolean find = matchEmoji.find();
        if (find) {
            int i6 = 0;
            while (find) {
                int start = matchEmoji.start();
                int end = matchEmoji.end();
                matchEmoji.group();
                sb.append((CharSequence) spannableStringBuilder, i6, start);
                find = matchEmoji.find();
                i6 = end;
            }
        } else {
            sb.append((CharSequence) spannableStringBuilder);
        }
        return sb;
    }

    public static Matcher matchEmoji(CharSequence charSequence) {
        return PATTERN.matcher(charSequence);
    }

    public static SpannableStringBuilder parseCharSequence(String str) {
        if (str == null) {
            return null;
        }
        return parseCharSequence(new SpannableStringBuilder(str), 0, str.length());
    }

    public static boolean parseSearchViewTextChange(SearchView searchView) {
        if (searchView == null) {
            return false;
        }
        if (sEmojiEntryMap.size() <= 0) {
            getEmojiPackList(App.getAppContext());
        }
        CharSequence query = searchView.getQuery();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(query);
        Matcher matchEmoji = matchEmoji(spannableStringBuilder);
        boolean z6 = false;
        while (true) {
            if (!matchEmoji.find()) {
                break;
            }
            int start = matchEmoji.start();
            int end = matchEmoji.end();
            String group = matchEmoji.group();
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) spannableStringBuilder.getSpans(start, end, EmojiSpan.class);
            if (emojiSpanArr == null || emojiSpanArr.length <= 0) {
                EmojiBean emojiBean = sEmojiEntryMap.get(group);
                if (emojiBean != null) {
                    EmojiSpan emojiSpan = new EmojiSpan();
                    emojiSpan.setIconName(emojiBean.getIconName());
                    emojiSpan.setReplaceStr(emojiBean.getReplaceStr());
                    spannableStringBuilder.setSpan(emojiSpan, start, end, 33);
                    z6 = true;
                }
            } else if (emojiSpanArr.length > 1) {
                for (int i6 = 1; i6 < emojiSpanArr.length; i6++) {
                    spannableStringBuilder.removeSpan(emojiSpanArr[i6]);
                }
            }
        }
        if (z6) {
            searchView.setQuery(query, false);
        }
        return true;
    }

    public static void parseSpannable(Spannable spannable) {
        if (spannable == null) {
            return;
        }
        parseSpannable(spannable, 0, spannable.length());
    }

    public static synchronized List<EmojiPack> getEmojiPackList(Context context, int i6) {
        synchronized (EmojiManager.class) {
            if (sAllEmojiPackList.size() > 0) {
                return sAllEmojiPackList;
            }
            ArrayList arrayList = new ArrayList();
            try {
                XmlResourceParser xml = context.getResources().getXml(i6);
                String str = null;
                ArrayList arrayList2 = null;
                EmojiPack emojiPack = null;
                String str2 = null;
                String str3 = null;
                for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                    if (eventType != 0) {
                        if (eventType == 2) {
                            if (xml.getName().equals(TAG_EMOJI)) {
                                arrayList2 = new ArrayList();
                            } else if (xml.getName().equals(TAG_EMOJI_PACK)) {
                                str3 = xml.getAttributeValue(null, TAG_EMOJI_PACK_NAME);
                                String attributeValue = xml.getAttributeValue(null, TAG_EMOJI_COVER_NAME);
                                boolean equals = "true".equals(xml.getAttributeValue(null, TAG_EMOJI_PREMIUM));
                                boolean equals2 = "true".equals(xml.getAttributeValue(null, TAG_EMOJI_NEW_PACK));
                                if (!TextUtils.isEmpty(str3)) {
                                    emojiPack = new EmojiPack();
                                    emojiPack.setPackName(str3);
                                    if (!TextUtils.isEmpty(attributeValue)) {
                                        if (attributeValue.startsWith("_")) {
                                            emojiPack.setCoverIconName(EMOJI_PREFIX + str3 + attributeValue);
                                        } else {
                                            emojiPack.setCoverIconName(attributeValue);
                                        }
                                    }
                                    emojiPack.setPackPremium(equals);
                                    emojiPack.setNewPack(equals2);
                                }
                            } else if (xml.getName().equals(TAG_EMOJI_ENTRY)) {
                                str = xml.getAttributeValue(null, "key");
                            }
                        } else if (eventType == 4) {
                            if (!TextUtils.isEmpty(str)) {
                                str2 = xml.getText();
                            }
                        } else if (eventType == 3) {
                            if (xml.getName().equals(TAG_EMOJI)) {
                                if (arrayList2 != null) {
                                    arrayList.addAll(arrayList2);
                                }
                                arrayList2 = null;
                            } else if (xml.getName().equals(TAG_EMOJI_PACK)) {
                                if (arrayList2 != null) {
                                    arrayList2.add(emojiPack);
                                }
                                emojiPack = null;
                            } else if (xml.getName().equals(TAG_EMOJI_ENTRY)) {
                                if (emojiPack != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                                    if (str.startsWith("_")) {
                                        str = EMOJI_PREFIX + str3 + str;
                                    }
                                    EmojiBean emojiBean = new EmojiBean(str, str2);
                                    emojiPack.getEmojiList().add(emojiBean);
                                    sEmojiEntryMap.put(emojiBean.getReplaceStr(), emojiBean);
                                }
                                str = null;
                            }
                        }
                    }
                }
            } catch (Exception e7) {
                FirebaseCrashlytics.getInstance().recordException(new Exception("EmojiManager_" + e7.getMessage(), e7));
            }
            sAllEmojiPackList.clear();
            try {
                sAllEmojiPackList.addAll(arrayList);
            } catch (Exception unused) {
            }
            return arrayList;
        }
    }

    public static SpannableStringBuilder parseCharSequence(SpannableStringBuilder spannableStringBuilder) {
        return spannableStringBuilder == null ? spannableStringBuilder : parseCharSequence(spannableStringBuilder, 0, spannableStringBuilder.length());
    }

    public static void parseSpannable(Spannable spannable, int i6, int i7) {
        if (spannable == null) {
            return;
        }
        if (sEmojiEntryMap.size() <= 0) {
            getEmojiPackList(App.getAppContext());
        }
        if (i7 > spannable.length()) {
            i7 = spannable.length();
        }
        if (i6 >= i7) {
            return;
        }
        Matcher matchEmoji = matchEmoji(spannable.subSequence(i6, i7));
        while (matchEmoji.find()) {
            int start = matchEmoji.start();
            int end = matchEmoji.end();
            String group = matchEmoji.group();
            int i8 = start + i6;
            int i9 = end + i6;
            Object[] objArr = (EmojiSpan[]) spannable.getSpans(i8, i9, EmojiSpan.class);
            if (objArr == null || objArr.length <= 0) {
                EmojiBean emojiBean = sEmojiEntryMap.get(group);
                if (emojiBean != null) {
                    EmojiSpan emojiSpan = new EmojiSpan();
                    emojiSpan.setIconName(emojiBean.getIconName());
                    emojiSpan.setReplaceStr(emojiBean.getReplaceStr());
                    spannable.setSpan(emojiSpan, i8, i9, 33);
                }
            } else {
                if (objArr.length > 1) {
                    for (int i10 = 1; i10 < objArr.length; i10++) {
                        spannable.removeSpan(objArr[i10]);
                    }
                }
            }
        }
    }

    public static SpannableStringBuilder parseCharSequence(CharSequence charSequence, int i6, int i7) {
        if (charSequence == null) {
            return null;
        }
        return parseCharSequence(new SpannableStringBuilder(charSequence), i6, i7);
    }

    public static SpannableStringBuilder parseCharSequence(SpannableStringBuilder spannableStringBuilder, int i6, int i7) {
        if (spannableStringBuilder == null) {
            return spannableStringBuilder;
        }
        if (sEmojiEntryMap.size() <= 0) {
            getEmojiPackList(App.getAppContext());
        }
        if (i7 > spannableStringBuilder.length()) {
            i7 = spannableStringBuilder.length();
        }
        if (i6 >= i7) {
            return spannableStringBuilder;
        }
        System.currentTimeMillis();
        Matcher matchEmoji = matchEmoji(spannableStringBuilder.subSequence(i6, i7));
        while (matchEmoji.find()) {
            int start = matchEmoji.start();
            int end = matchEmoji.end();
            String group = matchEmoji.group();
            int i8 = start + i6;
            int i9 = end + i6;
            Object[] objArr = (EmojiSpan[]) spannableStringBuilder.getSpans(i8, i9, EmojiSpan.class);
            if (objArr != null && objArr.length > 0) {
                if (objArr.length > 1) {
                    for (int i10 = 1; i10 < objArr.length; i10++) {
                        spannableStringBuilder.removeSpan(objArr[i10]);
                    }
                }
            } else {
                EmojiBean emojiBean = sEmojiEntryMap.get(group);
                if (emojiBean != null) {
                    EmojiSpan emojiSpan = new EmojiSpan();
                    emojiSpan.setIconName(emojiBean.getIconName());
                    emojiSpan.setReplaceStr(emojiBean.getReplaceStr());
                    spannableStringBuilder.setSpan(emojiSpan, i8, i9, 33);
                }
            }
        }
        System.currentTimeMillis();
        return spannableStringBuilder;
    }
}