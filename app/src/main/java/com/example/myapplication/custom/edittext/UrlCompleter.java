package com.example.myapplication.custom.edittext;

import java.util.Locale;
import java.util.regex.Matcher;


public class UrlCompleter {
    public static String complete(String str) {
        if (RegexPatternsConstants.EMAIL.matcher(str).matches()) {
            return "mailto:" + str;
        }
        if (RegexPatternsConstants.HASH_TAG.matcher(str).find()) {
            return "hashtag:" + parseHashtag(str);
        }
        if (RegexPatternsConstants.HYPER_LINK.matcher(str).matches() && !str.toLowerCase(Locale.getDefault()).startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            return "http://" + str;
        }
        if (!RegexPatternsConstants.PHONE.matcher(str).matches()) {
            return str;
        }
        return "tel:" + str;
    }

    public static String parseHashtag(String str) {
        Matcher matcher = RegexPatternsConstants.HASH_TAG.matcher(str);
        return matcher.find() ? matcher.group() : "";
    }
}
