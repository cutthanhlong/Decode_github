package com.example.myapplication;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Calendar;

/* loaded from: classes4.dex */
public class Attachment extends BaseAttachment implements Parcelable {
    public static final Parcelable.Creator<Attachment> CREATOR = new Parcelable.Creator<Attachment>() { // from class: notes.easy.android.mynotes.models.Attachment.1
        @Override // android.os.Parcelable.Creator
        public Attachment createFromParcel(Parcel parcel) {
            return new Attachment(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Attachment[] newArray(int i6) {
            return new Attachment[i6];
        }
    };
    private int height;
    private Uri uri;
    private int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        setUriPath(uri != null ? uri.toString() : "");
    }

    public String toString() {
        return "Attachment{uri=" + this.uri + ", width=" + this.width + ", height=" + this.height + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i6) {
        parcel.writeLong(getId().longValue());
        parcel.writeString(getUri().toString());
        parcel.writeString(getMime_type());
    }

    public Attachment(Uri uri, String str) {
        this(Calendar.getInstance().getTimeInMillis(), uri, null, 0L, 0L, str);
    }

    public Attachment(long j6, Uri uri, String str, long j7, long j8, String str2) {
        super(Long.valueOf(j6), uri != null ? uri.getPath() : null, str, j7, j8, str2, 0, 0);
        this.width = 0;
        this.height = 0;
        setUri(uri);
    }

    public Attachment(BaseAttachment baseAttachment) {
        super(baseAttachment.getId(), baseAttachment.getUriPath(), baseAttachment.getName(), baseAttachment.getSize(), baseAttachment.getLength(), baseAttachment.getMime_type(), baseAttachment.getOrder(), baseAttachment.getSort());
        this.width = 0;
        this.height = 0;
        this.uri = Uri.parse(baseAttachment.getUriPath());
    }

    private Attachment(Parcel parcel) {
        this.width = 0;
        this.height = 0;
        setId(Long.valueOf(parcel.readLong()));
        setUri(Uri.parse(parcel.readString()));
        setMime_type(parcel.readString());
    }
}
