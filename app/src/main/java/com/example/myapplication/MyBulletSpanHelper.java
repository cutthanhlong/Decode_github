package com.example.myapplication;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Iterator;
import notes.easy.android.mynotes.utils.TextHelper;

/* loaded from: classes4.dex */
public class MyBulletSpanHelper {
    public static int createNewGroup(EditText editText) {
        Editable editableText;
        if (editText == null || (editableText = editText.getEditableText()) == null || editableText.length() < 0) {
            return 1;
        }
        int i6 = 0;
        MyBulletSpan[] myBulletSpanArr = (MyBulletSpan[]) editableText.getSpans(0, editableText.length(), MyBulletSpan.class);
        if (myBulletSpanArr != null) {
            int length = myBulletSpanArr.length;
            int i7 = 0;
            while (i6 < length) {
                MyBulletSpan myBulletSpan = myBulletSpanArr[i6];
                if (myBulletSpan != null) {
                    i7 = Math.max(i7, myBulletSpan.getNlGroup());
                }
                i6++;
            }
            i6 = i7;
        }
        return i6 + 1;
    }

    public static BulletSpanInfo getCurLineInfo(EditText editText) {
        return getLineInfoByLine(editText, TextHelper.getCurrentCursorLine(editText));
    }

    public static BulletSpanInfo getLineInfoByLine(EditText editText, int i6) {
        Editable editableText;
        BulletSpanInfo bulletSpanInfo = null;
        if (editText != null && i6 >= 0 && (editableText = editText.getEditableText()) != null && editableText.length() > 0) {
            bulletSpanInfo = new BulletSpanInfo();
            int thisLineStart = TextHelper.getThisLineStart(editText, i6);
            int thisLineEnd = TextHelper.getThisLineEnd(editText, i6);
            bulletSpanInfo.setLine(i6);
            bulletSpanInfo.setLineStart(thisLineStart);
            bulletSpanInfo.setLineEnd(thisLineEnd);
            MyBulletSpan[] myBulletSpanArr = (MyBulletSpan[]) editableText.getSpans(thisLineStart, thisLineEnd, MyBulletSpan.class);
            if (myBulletSpanArr != null && myBulletSpanArr.length > 0) {
                MyBulletSpan myBulletSpan = myBulletSpanArr[0];
                if (editableText.getSpanStart(myBulletSpan) != thisLineStart) {
                    int i7 = 1;
                    while (true) {
                        if (i7 >= myBulletSpanArr.length) {
                            break;
                        }
                        if (editableText.getSpanStart(myBulletSpanArr[i7]) == thisLineStart) {
                            myBulletSpan = myBulletSpanArr[i7];
                            break;
                        }
                        i7++;
                    }
                }
                for (MyBulletSpan myBulletSpan2 : myBulletSpanArr) {
                    if (myBulletSpan != myBulletSpan2) {
                        editableText.removeSpan(myBulletSpan2);
                        editableText.removeSpan(myBulletSpan2.myImageSpan);
                    }
                }
                bulletSpanInfo.setMyBulletSpan(myBulletSpan);
            }
        }
        return bulletSpanInfo;
    }

    public static BulletSpanInfo getPreLineInfo(EditText editText, int i6) {
        Editable editableText;
        int i7;
        MyBulletSpan[] myBulletSpanArr;
        BulletSpanInfo bulletSpanInfo = null;
        if (editText != null && (editableText = editText.getEditableText()) != null) {
            int thisLineStart = TextHelper.getThisLineStart(editText, i6);
            int i8 = thisLineStart - 1;
            int i9 = i8;
            while (true) {
                if (i9 < 0) {
                    break;
                }
                if (editableText.charAt(i9) == '\n') {
                    if (bulletSpanInfo != null) {
                        bulletSpanInfo.setLineStart(i9 + 1);
                        break;
                    }
                    bulletSpanInfo = new BulletSpanInfo();
                    bulletSpanInfo.setLineEnd(i9);
                }
                i9--;
            }
            if (bulletSpanInfo == null && i8 > 0) {
                bulletSpanInfo = new BulletSpanInfo();
                bulletSpanInfo.setLineStart(0);
                bulletSpanInfo.setLineEnd(i8);
            }
            if (bulletSpanInfo != null && (myBulletSpanArr = (MyBulletSpan[]) editableText.getSpans(bulletSpanInfo.getLineStart(), bulletSpanInfo.getLineEnd(), MyBulletSpan.class)) != null && myBulletSpanArr.length > 0) {
                MyBulletSpan myBulletSpan = myBulletSpanArr[0];
                if (editableText.getSpanStart(myBulletSpan) != thisLineStart) {
                    int i10 = 1;
                    while (true) {
                        if (i10 >= myBulletSpanArr.length) {
                            break;
                        }
                        if (editableText.getSpanStart(myBulletSpanArr[i10]) == thisLineStart) {
                            myBulletSpan = myBulletSpanArr[i10];
                            break;
                        }
                        i10++;
                    }
                }
                if (myBulletSpanArr.length > 1) {
                    for (i7 = 1; i7 < myBulletSpanArr.length; i7++) {
                        MyBulletSpan myBulletSpan2 = myBulletSpanArr[i7];
                        if (myBulletSpan != myBulletSpan2) {
                            editableText.removeSpan(myBulletSpan2);
                            editableText.removeSpan(myBulletSpanArr[i7].myImageSpan);
                        }
                    }
                }
                bulletSpanInfo.setMyBulletSpan(myBulletSpan);
            }
        }
        return bulletSpanInfo;
    }

    public static void sortAllSpanByGroup(EditText editText, int i6) {
        updateAllSpanInfoByGroup(editText, "", i6);
    }

    public static void updateAllSpanInfoByGroup(EditText editText, String str, int i6) {
        Editable editableText;
        if (editText == null || (editableText = editText.getEditableText()) == null) {
            return;
        }
        if (editableText.length() < 0) {
            MyBulletSpan[] myBulletSpanArr = (MyBulletSpan[]) editableText.getSpans(0, editableText.length(), MyBulletSpan.class);
            if (myBulletSpanArr != null) {
                for (MyBulletSpan myBulletSpan : myBulletSpanArr) {
                    editableText.removeSpan(myBulletSpan);
                    editableText.removeSpan(myBulletSpan.myImageSpan);
                }
                return;
            }
            return;
        }
        ArrayList<BulletSpanInfo> arrayList = new ArrayList();
        BulletSpanInfo bulletSpanInfo = new BulletSpanInfo();
        for (int i7 = 0; i7 < editableText.length(); i7++) {
            if (editableText.charAt(i7) == '\n') {
                bulletSpanInfo.setLineEnd(i7);
                arrayList.add(bulletSpanInfo);
                bulletSpanInfo = new BulletSpanInfo();
                bulletSpanInfo.setLineStart(i7 + 1);
            }
        }
        if (bulletSpanInfo.getLineStart() <= editableText.length() - 1) {
            bulletSpanInfo.setLineEnd(editableText.length() - 1);
            arrayList.add(bulletSpanInfo);
        }
        for (BulletSpanInfo bulletSpanInfo2 : arrayList) {
            MyBulletSpan[] myBulletSpanArr2 = (MyBulletSpan[]) editableText.getSpans(bulletSpanInfo2.getLineStart(), bulletSpanInfo2.getLineEnd(), MyBulletSpan.class);
            if (myBulletSpanArr2 != null && myBulletSpanArr2.length > 0) {
                MyBulletSpan myBulletSpan2 = myBulletSpanArr2[0];
                if (editableText.getSpanStart(myBulletSpan2) != bulletSpanInfo2.getLineStart()) {
                    int i8 = 1;
                    while (true) {
                        if (i8 >= myBulletSpanArr2.length) {
                            break;
                        }
                        if (editableText.getSpanStart(myBulletSpanArr2[i8]) == bulletSpanInfo2.getLineStart()) {
                            myBulletSpan2 = myBulletSpanArr2[i8];
                            break;
                        }
                        i8++;
                    }
                }
                for (MyBulletSpan myBulletSpan3 : myBulletSpanArr2) {
                    if (myBulletSpan2 != myBulletSpan3) {
                        editableText.removeSpan(myBulletSpan3);
                        editableText.removeSpan(myBulletSpan3.myImageSpan);
                    }
                }
                bulletSpanInfo2.setMyBulletSpan(myBulletSpan2);
            }
        }
        Iterator it2 = arrayList.iterator();
        int i9 = 1;
        while (it2.hasNext()) {
            MyBulletSpan myBulletSpan4 = ((BulletSpanInfo) it2.next()).getMyBulletSpan();
            if (myBulletSpan4 != null && myBulletSpan4.getNlGroup() == i6) {
                if ((i9 == myBulletSpan4.getNlLevel() && (TextUtils.isEmpty(str) || str.equals(myBulletSpan4.getNlName()))) ? false : true) {
                    int spanStart = editableText.getSpanStart(myBulletSpan4);
                    int spanEnd = editableText.getSpanEnd(myBulletSpan4);
                    editableText.removeSpan(myBulletSpan4);
                    editableText.removeSpan(myBulletSpan4.myImageSpan);
                    myBulletSpan4.setNlLevel(i9);
                    if (!TextUtils.isEmpty(str)) {
                        myBulletSpan4.setNlName(str);
                    }
                    if (spanStart >= 0 && spanEnd >= spanStart) {
                        editableText.setSpan(myBulletSpan4, spanStart, spanEnd, 18);
                        editableText.setSpan(myBulletSpan4.myImageSpan, spanStart, spanStart + 1, 33);
                    }
                }
                i9++;
            }
        }
    }
}
