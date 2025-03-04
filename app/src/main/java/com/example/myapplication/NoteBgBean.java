package com.example.myapplication;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class NoteBgBean implements Serializable {
    private Background bg;
    private Custom custom;
    private Frame frame;
    private int id;
    private boolean imagesReady;
    private boolean isDarkBg;
    private boolean isVip;
    private Line line;
    private String packZip;
    private PadImages[] padImages;
    private PhoneImages[] phoneImages;
    private String thumbnail;
    private boolean thumbnailReady;
    private int version;

    public static class Background implements Serializable {
        private String bgName;
        private String bgType;
        private String[] gradientColors;
        private String orientation;

        public Background copy() {
            Background background = new Background();
            background.gradientColors = this.gradientColors;
            background.orientation = this.orientation;
            background.bgName = this.bgName;
            background.bgType = this.bgType;
            return background;
        }

        public String getBgName() {
            return this.bgName;
        }

        public String getBgType() {
            return this.bgType;
        }

        public String[] getGradientColors() {
            return this.gradientColors;
        }

        public String getOrientation() {
            return this.orientation;
        }

        public void setBgName(String str) {
            this.bgName = str;
        }

        public void setBgType(String str) {
            this.bgType = str;
        }

        public void setGradientColors(String[] strArr) {
            this.gradientColors = strArr;
        }

        public void setOrientation(String str) {
            this.orientation = str;
        }
    }

    public static class Custom implements Serializable {
        private String customBg;

        public Custom copy() {
            Custom custom = new Custom();
            custom.customBg = this.customBg;
            return custom;
        }

        public String getCustomBg() {
            return this.customBg;
        }

        public void setCustomBg(String str) {
            this.customBg = str;
        }
    }

    public static class Frame implements Serializable {
        private String editBgColor;

        public Frame copy() {
            Frame frame = new Frame();
            frame.editBgColor = this.editBgColor;
            return frame;
        }

        public String getEditBgColor() {
            return this.editBgColor;
        }

        public void setEditBgColor(String str) {
            this.editBgColor = str;
        }
    }

    public static class Line implements Serializable {
        private boolean isFollowEdit;
        private String lineColor;
        private float lineGap;
        private float lineLength;
        private float lineSpacing;
        private String lineStyle;
        private String lineType;
        private float lineWidth;

        public Line copy() {
            Line line = new Line();
            line.lineStyle = this.lineStyle;
            line.lineType = this.lineType;
            line.isFollowEdit = this.isFollowEdit;
            line.lineColor = this.lineColor;
            line.lineWidth = this.lineWidth;
            line.lineSpacing = this.lineSpacing;
            line.lineLength = this.lineLength;
            line.lineGap = this.lineGap;
            return line;
        }

        public String getLineColor() {
            return this.lineColor;
        }

        public float getLineGap() {
            return this.lineGap;
        }

        public float getLineLength() {
            return this.lineLength;
        }

        public float getLineSpacing() {
            return this.lineSpacing;
        }

        public String getLineStyle() {
            return this.lineStyle;
        }

        public String getLineType() {
            return this.lineType;
        }

        public float getLineWidth() {
            return this.lineWidth;
        }

        public boolean isFollowEdit() {
            return this.isFollowEdit;
        }

        public void setFollowEdit(boolean z6) {
            this.isFollowEdit = z6;
        }

        public void setLineColor(String str) {
            this.lineColor = str;
        }

        public void setLineGap(float f6) {
            this.lineGap = f6;
        }

        public void setLineLength(float f6) {
            this.lineLength = f6;
        }

        public void setLineSpacing(float f6) {
            this.lineSpacing = f6;
        }

        public void setLineStyle(String str) {
            this.lineStyle = str;
        }

        public void setLineType(String str) {
            this.lineType = str;
        }

        public void setLineWidth(float f6) {
            this.lineWidth = f6;
        }
    }

    public static class PadImages implements Serializable {
        private float imageLandscapeRatio;
        private String imageName;
        private float imagePortraitRatio;
        private String imagePosition;
        private boolean isFloat;

        public PadImages copy() {
            PadImages padImages = new PadImages();
            padImages.imageName = this.imageName;
            padImages.imagePosition = this.imagePosition;
            padImages.imageLandscapeRatio = this.imageLandscapeRatio;
            padImages.imagePortraitRatio = this.imagePortraitRatio;
            padImages.isFloat = this.isFloat;
            return padImages;
        }

        public float getImageLandscapeRatio() {
            return this.imageLandscapeRatio;
        }

        public String getImageName() {
            return this.imageName;
        }

        public float getImagePortraitRatio() {
            return this.imagePortraitRatio;
        }

        public String getImagePosition() {
            return this.imagePosition;
        }

        public boolean isFloat() {
            return this.isFloat;
        }

        public void setFloat(boolean z6) {
            this.isFloat = z6;
        }

        public void setImageLandscapeRatio(float f6) {
            this.imageLandscapeRatio = f6;
        }

        public void setImageName(String str) {
            this.imageName = str;
        }

        public void setImagePortraitRatio(float f6) {
            this.imagePortraitRatio = f6;
        }

        public void setImagePosition(String str) {
            this.imagePosition = str;
        }
    }

    public static class PhoneImages implements Serializable {
        private String imageName;
        private String imagePosition;
        private float imageWidthRatio;
        private boolean isFloat;

        public PhoneImages copy() {
            PhoneImages phoneImages = new PhoneImages();
            phoneImages.imageName = this.imageName;
            phoneImages.imagePosition = this.imagePosition;
            phoneImages.imageWidthRatio = this.imageWidthRatio;
            phoneImages.isFloat = this.isFloat;
            return phoneImages;
        }

        public String getImageName() {
            return this.imageName;
        }

        public String getImagePosition() {
            return this.imagePosition;
        }

        public float getImageWidthRatio() {
            return this.imageWidthRatio;
        }

        public boolean isFloat() {
            return this.isFloat;
        }

        public void setFloat(boolean z6) {
            this.isFloat = z6;
        }

        public void setImageName(String str) {
            this.imageName = str;
        }

        public void setImagePosition(String str) {
            this.imagePosition = str;
        }

        public void setImageWidthRatio(float f6) {
            this.imageWidthRatio = f6;
        }
    }

    public NoteBgBean copy() {
        NoteBgBean noteBgBean = new NoteBgBean();
        noteBgBean.id = this.id;
        noteBgBean.isVip = this.isVip;
        noteBgBean.thumbnail = this.thumbnail;
        noteBgBean.isDarkBg = this.isDarkBg;
        noteBgBean.version = this.version;
        noteBgBean.packZip = this.packZip;
        noteBgBean.thumbnailReady = this.thumbnailReady;
        noteBgBean.imagesReady = this.imagesReady;
        Background background = this.bg;
        if (background != null) {
            noteBgBean.bg = background.copy();
        }
        Line line = this.line;
        if (line != null) {
            noteBgBean.line = line.copy();
        }
        Frame frame = this.frame;
        if (frame != null) {
            noteBgBean.frame = frame.copy();
        }
        Custom custom = this.custom;
        if (custom != null) {
            noteBgBean.custom = custom.copy();
        }
        PhoneImages[] phoneImagesArr = this.phoneImages;
        int i6 = 0;
        if (phoneImagesArr != null) {
            PhoneImages[] phoneImagesArr2 = new PhoneImages[phoneImagesArr.length];
            int i7 = 0;
            while (true) {
                PhoneImages[] phoneImagesArr3 = this.phoneImages;
                if (i7 >= phoneImagesArr3.length) {
                    break;
                }
                phoneImagesArr2[i7] = phoneImagesArr3[i7].copy();
                i7++;
            }
            noteBgBean.phoneImages = phoneImagesArr2;
        }
        PadImages[] padImagesArr = this.padImages;
        if (padImagesArr != null) {
            PadImages[] padImagesArr2 = new PadImages[padImagesArr.length];
            while (true) {
                PadImages[] padImagesArr3 = this.padImages;
                if (i6 >= padImagesArr3.length) {
                    break;
                }
                padImagesArr2[i6] = padImagesArr3[i6].copy();
                i6++;
            }
            noteBgBean.padImages = padImagesArr2;
        }
        return noteBgBean;
    }

    public Background getBg() {
        return this.bg;
    }

    public Custom getCustom() {
        return this.custom;
    }

    public Frame getFrame() {
        return this.frame;
    }

    public int getId() {
        return this.id;
    }

    public Line getLine() {
        return this.line;
    }

    public String getPackZip() {
        return this.packZip;
    }

    public PadImages[] getPadImages() {
        return this.padImages;
    }

    public PhoneImages[] getPhoneImages() {
        return this.phoneImages;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public int getVersion() {
        return this.version;
    }

    public boolean isDarkBg() {
        return this.isDarkBg;
    }

    public boolean isImagesReady() {
        return this.imagesReady;
    }

    public boolean isThumbnailReady() {
        return this.thumbnailReady;
    }

    public boolean isVip() {
        return this.isVip;
    }

    public void setBg(Background background) {
        this.bg = background;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }

    public void setDarkBg(boolean z6) {
        this.isDarkBg = z6;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public void setId(int i6) {
        this.id = i6;
    }

    public void setImagesReady(boolean z6) {
        this.imagesReady = z6;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void setPackZip(String str) {
        this.packZip = str;
    }

    public void setPadImages(PadImages[] padImagesArr) {
        this.padImages = padImagesArr;
    }

    public void setPhoneImages(PhoneImages[] phoneImagesArr) {
        this.phoneImages = phoneImagesArr;
    }

    public void setThumbnail(String str) {
        this.thumbnail = str;
    }

    public void setThumbnailReady(boolean z6) {
        this.thumbnailReady = z6;
    }

    public void setVersion(int i6) {
        this.version = i6;
    }

    public void setVip(boolean z6) {
        this.isVip = z6;
    }

    public void copy(NoteBgBean noteBgBean) {
        this.id = noteBgBean.id;
        this.isVip = noteBgBean.isVip;
        this.thumbnail = noteBgBean.thumbnail;
        this.isDarkBg = noteBgBean.isDarkBg;
        this.version = noteBgBean.version;
        this.packZip = noteBgBean.packZip;
        this.thumbnailReady = noteBgBean.thumbnailReady;
        this.imagesReady = noteBgBean.imagesReady;
        Background background = noteBgBean.bg;
        if (background != null) {
            this.bg = background.copy();
        } else {
            this.bg = null;
        }
        Line line = noteBgBean.line;
        if (line != null) {
            this.line = line.copy();
        } else {
            this.line = null;
        }
        Frame frame = noteBgBean.frame;
        if (frame != null) {
            this.frame = frame.copy();
        } else {
            this.frame = null;
        }
        Custom custom = noteBgBean.custom;
        if (custom != null) {
            this.custom = custom.copy();
        } else {
            this.custom = null;
        }
        int i6 = 0;
        if (noteBgBean.phoneImages != null) {
            PhoneImages[] phoneImagesArr = new PhoneImages[this.phoneImages.length];
            int i7 = 0;
            while (true) {
                PhoneImages[] phoneImagesArr2 = this.phoneImages;
                if (i7 >= phoneImagesArr2.length) {
                    break;
                }
                phoneImagesArr[i7] = phoneImagesArr2[i7].copy();
                i7++;
            }
            this.phoneImages = phoneImagesArr;
        } else {
            this.phoneImages = null;
        }
        if (noteBgBean.padImages != null) {
            PadImages[] padImagesArr = new PadImages[this.padImages.length];
            while (true) {
                PadImages[] padImagesArr2 = this.padImages;
                if (i6 < padImagesArr2.length) {
                    padImagesArr[i6] = padImagesArr2[i6].copy();
                    i6++;
                } else {
                    this.padImages = padImagesArr;
                    return;
                }
            }
        } else {
            this.padImages = null;
        }
    }
}
