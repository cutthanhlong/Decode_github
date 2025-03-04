package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserConfig.kt */
/* loaded from: classes4.dex */
public final class UserConfig {
    public static final Companion Companion = new Companion(null);
    private final Context context;
    private int newDrawReleaseVersion;
    private int newEmojiReleaseVersion;
    private int newFontVersion;
    private int newWidgetVersion;
    private final SharedPreferences prefs;
    private int whatsNewVersion;

    /* compiled from: UserConfig.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final UserConfig newInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new UserConfig(context);
        }
    }

    public UserConfig(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.prefs = ContextKt.getSharedPrefs(context);
        this.whatsNewVersion = 10;
        this.newDrawReleaseVersion = 10;
        this.newEmojiReleaseVersion = 2;
        this.newFontVersion = 1;
        this.newWidgetVersion = 14;
    }

    public final boolean getAbTest3DaysTrialExist() {
        return this.prefs.getBoolean("abTest3DaysTrialExist", true);
    }

    public final int getAbTestWelcome() {
        return this.prefs.getInt("abTestWelcome20250115", 0);
    }

    public final int getAbTestcoherentFuidance() {
        return this.prefs.getInt("abTestcoherentFuidance", 0);
    }

    public final int getActiveDay() {
        return this.prefs.getInt("activeDay", 0);
    }

    public final int getActiveNewDay() {
        return this.prefs.getInt("activeDay1", 0);
    }

    public final long getActiveNewTime() {
        return this.prefs.getLong("activeTime1", 0L);
    }

    public final long getActiveTime() {
        return this.prefs.getLong("activeTime", 0L);
    }

    public final int getAdGroup() {
        return this.prefs.getInt("adGroup20241230", 0);
    }

    public final boolean getAddPicUsed() {
        return this.prefs.getBoolean(Constants.IMG_USED, false);
    }

    public final boolean getAddPicUsedClick() {
        return this.prefs.getBoolean(Constants.IMG_USED_CLICK, false);
    }

    public final boolean getAffixBtnClick() {
        return this.prefs.getBoolean("affixBtnClick", false);
    }

    public final int getAllCateIndex() {
        return this.prefs.getInt(Constants.CATE_INDEX, -1);
    }

    public final String getAppDeepLevel() {
        return this.prefs.getString("deep_level", "");
    }

    public final String getAppFuncusage() {
        return this.prefs.getString("app_use_level", "");
    }

    public final boolean getAppLockRed() {
        return this.prefs.getBoolean("appLockRed", false);
    }

    public final boolean getAppNotificationSwitch() {
        return this.prefs.getBoolean("appNotificationSwitch1", true);
    }

    public final boolean getArchiveBugfix() {
        return this.prefs.getBoolean("archiveBugfix", false);
    }

    public final boolean getAutoBackupRed() {
        return this.prefs.getBoolean(Constants.BACKUP_AUTO_RED, false);
    }

    public final boolean getAutoBackupSwitch() {
        return this.prefs.getBoolean(Constants.BACKUP_AUTO_SWITCH, false);
    }

    public final long getAutoBackupToast() {
        return this.prefs.getLong(Constants.BACKUP_AUTO_TOAST, 0L);
    }

    public final boolean getAutoSyncChecked() {
        return this.prefs.getBoolean("autoSyncCheck", false);
    }

    public final boolean getAutoSyncDialogNeedShow() {
        return this.prefs.getBoolean("autoSyncDialogNeedShow", true);
    }

    public final boolean getAutoSyncSwitch() {
        return this.prefs.getBoolean(Constants.SYNC_AUTO_SWITCH, false);
    }

    public final int getAutoSyncSwitchState() {
        return this.prefs.getInt("autoSyncSwitchState", 1);
    }

    public final long getBackupBannerSHowTime() {
        return this.prefs.getLong("backupBannerSHowTime", 0L);
    }

    public final boolean getBackup_unknown_red() {
        return this.prefs.getBoolean("backup_unknown_red", false);
    }

    public final boolean getBgBtnClick() {
        return this.prefs.getBoolean("bgClick", false);
    }

    public final boolean getBgPenUsed() {
        return this.prefs.getBoolean(Constants.BG_USED, false);
    }

    public final int getBgResConfigVersion() {
        return this.prefs.getInt("bgResConfigVersion", 0);
    }

    public final String getBgResNewDialogHasShowedId() {
        return this.prefs.getString("bgResNewDialogHasShowedId", "");
    }

    public final String getBgResNewNeedShowId() {
        return this.prefs.getString("bgResNewNeedShowId", "");
    }

    public final String getBgResNewNotiHasShowedId() {
        return this.prefs.getString("bgResNewNotiHasShowedId", "");
    }

    public final boolean getBgUsed() {
        return this.prefs.getBoolean("first_open5", false);
    }

    public final long getBillingDiscount50LifetimeLongT() {
        return this.prefs.getLong(Constants.PRODUCT_LIFETIME_V1_50OFF_LONG_T, 0L);
    }

    public final String getBillingDiscount50LifetimeT() {
        return this.prefs.getString(Constants.PRODUCT_LIFETIME_V1_50OFF_T, "");
    }

    public final long getBillingDiscount50YearlyLongT() {
        return this.prefs.getLong(Constants.PRODUCT_YEARLY_V1_50OFF_LONG_T, 0L);
    }

    public final String getBillingDiscount50YearlyT() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_V1_50OFF_T, "");
    }

    public final long getBillingDiscount70LifetimeLongT() {
        return this.prefs.getLong(Constants.PRODUCT_LIFETIME_V1_70OFF_LONG_T, 0L);
    }

    public final String getBillingDiscount70LifetimeT() {
        return this.prefs.getString(Constants.PRODUCT_LIFETIME_V1_70OFF_T, "");
    }

    public final long getBillingDiscount70YearlyLongT() {
        return this.prefs.getLong(Constants.PRODUCT_YEARLY_V1_70OFF_LONG_T, 0L);
    }

    public final String getBillingDiscount70YearlyT() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_V1_70OFF_T, "");
    }

    public final long getBillingDiscount90LifetimeLongT() {
        return this.prefs.getLong(Constants.PRODUCT_LIFETIME_V1_90OFF_LONG_T, 0L);
    }

    public final String getBillingDiscount90LifetimeT() {
        return this.prefs.getString(Constants.PRODUCT_LIFETIME_V1_90OFF_T, "");
    }

    public final long getBillingDiscount90YearlyLongT() {
        return this.prefs.getLong(Constants.PRODUCT_YEARLY_V1_90OFF_LONG_T, 0L);
    }

    public final String getBillingDiscount90YearlyT() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_V1_90OFF_T, "");
    }

    public final String getBillingMonthlyFreeTry7DaysPrice() {
        return this.prefs.getString("billingMonthlyFreeTry7DaysPrice", "");
    }

    public final String getBillingMonthlyPrice() {
        return this.prefs.getString(Constants.PRODUCT_MONTHLY_PRICE, "");
    }

    public final String getBillingMonthlyPriceT() {
        return this.prefs.getString(Constants.PRODUCT_MONTHLY_V1_T, "");
    }

    public final boolean getBillingMonthlyTestOpen() {
        return this.prefs.getBoolean(Constants.PRODUCT_MONTHLY_TEST_OPEN, false);
    }

    public final String getBillingMonthlyTestPrice() {
        return this.prefs.getString(Constants.PRODUCT_MONTHLY_TEST_PRICE, "");
    }

    public final long getBillingNormalMonthlyPriceNum() {
        return this.prefs.getLong("month_price_num", 0L);
    }

    public final long getBillingNormalMonthlyTestPriceNum() {
        return this.prefs.getLong("month_test_price_num", 0L);
    }

    public final long getBillingNormalYearlyLongT() {
        return this.prefs.getLong(Constants.PRODUCT_YEARLY_V1_LONG_T, 0L);
    }

    public final long getBillingNormalYearlyPriceNum() {
        return this.prefs.getLong("symble_umn_price", 0L);
    }

    public final String getBillingOneMonthTimePrice() {
        return this.prefs.getString(Constants.PRODUCT_MONTHLY_PRICE_NEW, "");
    }

    public final String getBillingOneTimeFake70() {
        return this.prefs.getString("lifetime1_fake_70", "");
    }

    public final String getBillingOneTimeFake90() {
        return this.prefs.getString("lifetime1_fake_90", "");
    }

    public final String getBillingOneTimeFakePrice() {
        return this.prefs.getString(Constants.PRODUCT_LIFETIME_PRICE_FAKE, "");
    }

    public final String getBillingOneTimeFakePriceT() {
        return this.prefs.getString(Constants.PRODUCT_LIFETIME_ORIGIN_T, "");
    }

    public final String getBillingOneTimePrice() {
        return this.prefs.getString(Constants.PRODUCT_PRICE, "");
    }

    public final String getBillingOneTimePriceDiscount() {
        return this.prefs.getString(Constants.PRODUCT_PRICE_DISCOUNT, "");
    }

    public final String getBillingOneTimePriceDiscount10() {
        return this.prefs.getString(Constants.PRODUCT_PRICE_DISCOUNT10, "");
    }

    public final long getBillingOneTimePriceDiscount10Num() {
        return this.prefs.getLong(Constants.PRODUCT_PRICE_DISCOUNT10_NUM, 0L);
    }

    public final String getBillingOneTimePriceDiscount30() {
        return this.prefs.getString(Constants.PRODUCT_PRICE_DISCOUNT30, "");
    }

    public final long getBillingOneTimePriceDiscount30Num() {
        return this.prefs.getLong(Constants.PRODUCT_PRICE_DISCOUNT30_NUM, 0L);
    }

    public final long getBillingOneTimePriceDiscountNum() {
        return this.prefs.getLong(Constants.PRODUCT_PRICE_DISCOUNT_NUM, 0L);
    }

    public final long getBillingOneTimePriceLongT() {
        return this.prefs.getLong(Constants.PRODUCT_LIFETIME_V1_LONG_T, 0L);
    }

    public final long getBillingOneTimePriceNum() {
        return this.prefs.getLong(Constants.PRODUCT_PRICE_NUM, 0L);
    }

    public final String getBillingOneTimePriceT() {
        return this.prefs.getString(Constants.PRODUCT_LIFETIME_V1_T, "");
    }

    public final String getBillingYearFake70() {
        return this.prefs.getString("yearr_fake_70", "");
    }

    public final String getBillingYearFake90() {
        return this.prefs.getString("yearr_fake_90", "");
    }

    public final String getBillingYearlyFreeTry7DaysPrice() {
        return this.prefs.getString("billingYearlyFreeTry7DaysPrice", "");
    }

    public final String getBillingYearlyFreeTryPriceT() {
        return this.prefs.getString(Constants.PRODUCT_FREE_TRY_YEARLY_PRICE_T, "");
    }

    public final String getBillingYearlyPrice() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_PRICE, "");
    }

    public final String getBillingYearlyPriceDiscount() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_PRICE_DISCOUNT, "");
    }

    public final String getBillingYearlyPriceDiscount10() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_PRICE_DISCOUNT10, "");
    }

    public final String getBillingYearlyPriceDiscount30() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_PRICE_DISCOUNT30, "");
    }

    public final String getBillingYearlyPriceFake() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_PRICE_ORIGIN, "");
    }

    public final String getBillingYearlyPriceFakeT() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_PRICE_ORIGIN_T, "");
    }

    public final String getBillingYearlyPriceT() {
        return this.prefs.getString(Constants.PRODUCT_YEARLY_V1_T, "");
    }

    public final long getCalendarSelectionTime() {
        return this.prefs.getLong(Constants.CALENDAR_SELECTION_TIME, 0L);
    }

    public final long getCancelDayVip() {
        return this.prefs.getLong("cancel_vip", 0L);
    }

    public final boolean getCategoryBugfix() {
        return this.prefs.getBoolean("categoryBugfix", false);
    }

    public final boolean getCheckSort() {
        return this.prefs.getBoolean("checklist_sort", false);
    }

    public final boolean getChecklistBtnClick() {
        return this.prefs.getBoolean("checklistBtnClick", false);
    }

    public final boolean getChoosePicBtnClick() {
        return this.prefs.getBoolean("choosePicBtnClick", false);
    }

    public final String getClipboardContent() {
        return this.prefs.getString(Constants.CLIPBOARD_CONTENT, "");
    }

    public final boolean getClipboardSwitch() {
        return this.prefs.getBoolean(Constants.CLIPBOARD_SWITCH, true);
    }

    public final boolean getCoherentGuidance() {
        return this.prefs.getBoolean("coherentGuidance", false);
    }

    public final Context getContext() {
        return this.context;
    }

    public final String getCpPermissionStatus() {
        return this.prefs.getString("cp_perm_status", "");
    }

    public final String getCpPurchaseStatus() {
        return this.prefs.getString("cp_purchase_status", "");
    }

    public final long getCreateNotesNumber() {
        return this.prefs.getLong("create_notes_number", 0L);
    }

    public final String getCurrentDiscount() {
        return this.prefs.getString(Constants.CURRENT_DISCOUNT, "");
    }

    public final String getCurrentFestival2Discount() {
        return this.prefs.getString("school202503discount", "");
    }

    public final String getCurrentFestival3Discount() {
        return this.prefs.getString("spring2025discount", "");
    }

    public final String getCurrentFestivalDiscount() {
        return this.prefs.getString("freshstart2025discount", "");
    }

    public final int getCurrentFontAppVersion() {
        return this.prefs.getInt("currentFontAppVersion", 0);
    }

    public final String getCurrentFontConfig() {
        return this.prefs.getString(Constants.JSON_CONFIG, "");
    }

    public final int getCurrentFontVersion() {
        return this.prefs.getInt(Constants.JSON_VERSION, -1);
    }

    public final boolean getCustomBgAddGuideNeedShow() {
        return this.prefs.getBoolean("customBgAddGuideNeedShow", true);
    }

    public final boolean getCustomBgItemGuideNeedShow() {
        return this.prefs.getBoolean("customBgItemGuideNeedShow", true);
    }

    public final int getDailyNotesClick() {
        return this.prefs.getInt("notes_click", 0);
    }

    public final long getDailyPushTime() {
        return this.prefs.getLong(Constants.DAILY_LOCAL, 0L);
    }

    public final String getDebugCountry() {
        return this.prefs.getString("debugCountry", "");
    }

    public final boolean getDebugFunctionShow() {
        return this.prefs.getBoolean("debugFunctionShow", false);
    }

    public final boolean getDeepClick() {
        return this.prefs.getBoolean("deep_click", false);
    }

    public final boolean getDeepIap() {
        return this.prefs.getBoolean("deep_iap", false);
    }

    public final boolean getDeepMain() {
        return this.prefs.getBoolean("deep_main", false);
    }

    public final boolean getDeepSplash() {
        return this.prefs.getBoolean("deep_splash", false);
    }

    public final boolean getDeepWelcome() {
        return this.prefs.getBoolean("deep_welcome", false);
    }

    public final boolean getDefaultBackgroundCustomDark() {
        return this.prefs.getBoolean("defaultBackgroundCustomDark", false);
    }

    public final String getDefaultBackgroundFileName() {
        return this.prefs.getString("defaultBackgroundFileName", "");
    }

    public final int getDefaultBackgroundId() {
        return this.prefs.getInt("defaultBackgroundId", ResNoteBgManager.DEFAULT_BG_ID);
    }

    public final int getDefaultBackgroundPage() {
        return this.prefs.getInt("defaultBackgroundPage", 0);
    }

    public final int getDefaultDateIndex() {
        return this.prefs.getInt(Constants.PREF_DATE, -1);
    }

    public final int getDefaultFloatSize() {
        return this.prefs.getInt("defaultFloatSize", 16);
    }

    public final String getDefaultFloatStyle() {
        return this.prefs.getString("defaultFloatStyle", "");
    }

    public final int getDefaultLanguageIndex() {
        return this.prefs.getInt("settings_language", 0);
    }

    public final boolean getDefaultReading() {
        return this.prefs.getBoolean("defaultReading", false);
    }

    public final int getDefaultStartWeek() {
        return this.prefs.getInt("defaultStartWeek", 0);
    }

    public final int getDefaultSttLanguageIndex() {
        return this.prefs.getInt(Constants.STT_LANG, -1);
    }

    public final boolean getDiscountShow70off() {
        return this.prefs.getBoolean("discountShow70off", false);
    }

    public final boolean getDrawBtnClick() {
        return this.prefs.getBoolean("drawBtnClick", false);
    }

    public final int getDrawEraserSize() {
        return this.prefs.getInt(Constants.DRAW_ERASER_SIZE, 48);
    }

    public final boolean getDrawPageBackgroundImgClick() {
        return this.prefs.getBoolean("drawPageBackgroundImgClick", false);
    }

    public final int getDrawPageShowTimes() {
        return this.prefs.getInt(Constants.DRAW_TIMES, 0);
    }

    public final int getDrawPaintDotteColor() {
        return this.prefs.getInt(Constants.DRAW_DOTTE_COLOR, -14701465);
    }

    public final int getDrawPaintDotteSize() {
        return this.prefs.getInt(Constants.DRAW_DOTTE_SIZE, 40);
    }

    public final int getDrawPaintFountainColor() {
        return this.prefs.getInt(Constants.DRAW_PAINT_FOUNTAIN_COLOR, -10312968);
    }

    public final int getDrawPaintFountainPenSize() {
        return this.prefs.getInt(Constants.DRAW_PAINT_FOUNTAIN_SIZE, 30);
    }

    public final int getDrawPaintPenColor() {
        return this.prefs.getInt(Constants.DRAW_PAINT_PEN_COLOR, -17599);
    }

    public final int getDrawPaintPenSize() {
        return this.prefs.getInt(Constants.DRAW_PAINT_PEN_SIZE, 50);
    }

    public final int getDrawPaintPencilColor() {
        return this.prefs.getInt(Constants.DRAW_PAINT_COLOR, -6275594);
    }

    public final int getDrawPaintPencilSize() {
        return this.prefs.getInt(Constants.DRAW_PAINT_SIZE, 50);
    }

    public final int getDrawPaintQianPenColor() {
        return this.prefs.getInt(Constants.DRAW_PAINT_QIAN_COLOR, Constants.BLACK);
    }

    public final int getDrawPaintQianPenSize() {
        return this.prefs.getInt(Constants.DRAW_PAINT_QIAN_SIZE, 35);
    }

    public final int getDrawPencil() {
        return this.prefs.getInt(Constants.PAINT_TYPE, 1);
    }

    public final boolean getDrawRedFirstShow() {
        return this.prefs.getBoolean(Constants.DRAW_FIRST_SHOW, false);
    }

    public final boolean getDrawUsed() {
        return this.prefs.getBoolean("first_open4", false);
    }

    public final boolean getEditBgLayoutShow() {
        return this.prefs.getBoolean(Constants.EDIT_RECOMMEND_LAYOUT_SHOW, false);
    }

    public final int getEditDefaultFloatSize() {
        return this.prefs.getInt("editDefaultFloatSize", 16);
    }

    public final long getEditDialogShowTime() {
        return this.prefs.getLong(Constants.EDIT_DIALOG_TIME, 0L);
    }

    public final boolean getEditEmojiLayoutShow() {
        return this.prefs.getBoolean(Constants.EDIT_EMOJI_GUID_SHOW, false);
    }

    public final boolean getEditGuideShowed() {
        return this.prefs.getBoolean(Constants.EDIT_GUIDE_SHOW, false);
    }

    public final boolean getEditMoreWidget() {
        return this.prefs.getBoolean(Constants.EDIT_MORE_WIDGET_SP, false);
    }

    public final long getEditOnExitDialogShowTime() {
        return this.prefs.getLong("editOnExitDialogShowTime", 0L);
    }

    public final int getEditRedShowTag() {
        return this.prefs.getInt(Constants.EDIT_RED_TAG, 0);
    }

    public final long getEditRedShowTime() {
        return this.prefs.getLong(Constants.EDIT_RED_TIME, 0L);
    }

    public final int getEditViewTimes() {
        return this.prefs.getInt(Constants.VIEW_TIMES, 0);
    }

    public final String getEditingToolSorting() {
        return this.prefs.getString("editingToolSorting", "");
    }

    public final long getEditingToolSortingUpdateTime() {
        return this.prefs.getLong("editingToolSortingUpdateTime", 0L);
    }

    public final boolean getEmojiBtnClick() {
        return this.prefs.getBoolean("emojiBtnClick", false);
    }

    public final boolean getEmojiRedFirstShow() {
        return this.prefs.getBoolean(Constants.EMOJI_FIRST_SHOW, false);
    }

    public final boolean getEmojiUsed() {
        return this.prefs.getBoolean("first_open7", false);
    }

    public final boolean getExpiredSubscribe() {
        return this.prefs.getBoolean(Constants.EXPIRED_SUBSCRIBE, false);
    }

    public final long getExpiredType() {
        return this.prefs.getLong("expired_period", 0L);
    }

    public final boolean getFacebookRedFlag() {
        return this.prefs.getBoolean("facebookRedFlag", true);
    }

    public final int getFanShowTimes() {
        return this.prefs.getInt("fan_show_times", 0);
    }

    public final boolean getFestival2BanClicked() {
        return this.prefs.getBoolean("school202503ban_click", false);
    }

    public final long getFestival2BanStartShowed() {
        return this.prefs.getLong("school202503ban_start_show", 0L);
    }

    public final boolean getFestival2Show() {
        return this.prefs.getBoolean("school202503show", false);
    }

    public final boolean getFestival2ShowLastDay() {
        return this.prefs.getBoolean("school202503show_last_day", false);
    }

    public final boolean getFestival3BanClicked() {
        return this.prefs.getBoolean("spring2025ban_click", false);
    }

    public final long getFestival3BanStartShowed() {
        return this.prefs.getLong("spring2025ban_start_show", 0L);
    }

    public final boolean getFestival3Show() {
        return this.prefs.getBoolean("spring2025show", false);
    }

    public final boolean getFestival3ShowLastDay() {
        return this.prefs.getBoolean("spring2025show_last_day", false);
    }

    public final boolean getFestivalBanClicked() {
        return this.prefs.getBoolean("freshstart2025ban_click", false);
    }

    public final long getFestivalBanStartShowed() {
        return this.prefs.getLong("freshstart2025ban_start_show", 0L);
    }

    public final boolean getFestivalShow() {
        return this.prefs.getBoolean("freshstart2025show", false);
    }

    public final boolean getFestivalShowLastDay() {
        return this.prefs.getBoolean("freshstart2025show_last_day", false);
    }

    public final boolean getFirstAdShow() {
        return this.prefs.getBoolean("first_ad_show", false);
    }

    public final boolean getFirstEditOk() {
        return this.prefs.getBoolean("firstEditOk", false);
    }

    public final boolean getFirstEditOkActually() {
        return this.prefs.getBoolean("firstEditOkActually", false);
    }

    public final boolean getFirstEditShow() {
        return this.prefs.getBoolean("firstEditShow", false);
    }

    public final boolean getFirstMainShow() {
        return this.prefs.getBoolean("firstMainShow", false);
    }

    public final boolean getFirstNewOpen() {
        return this.prefs.getBoolean("first_open1", false);
    }

    public final long getFirstNewTime() {
        return this.prefs.getLong("first_time1", 0L);
    }

    public final boolean getFirstOpen() {
        return this.prefs.getBoolean(Constants.FIRST_OPEN, false);
    }

    public final boolean getFirstOpendWithCate() {
        return this.prefs.getBoolean("first_cate_cr", false);
    }

    public final boolean getFirstSplashShow() {
        return this.prefs.getBoolean("firstSplashShow", false);
    }

    public final long getFirstSub() {
        return this.prefs.getLong("first_sub", 0L);
    }

    public final long getFirstTime() {
        return this.prefs.getLong(Constants.FIRST_OPEN_TIME, 0L);
    }

    public final boolean getFiveDayOff() {
        return this.prefs.getBoolean(Constants.FIVE_DAYS_OFF, false);
    }

    public final boolean getFontBtnClick() {
        return this.prefs.getBoolean("fontBtnClick", false);
    }

    public final boolean getFontRedFirstShow() {
        return this.prefs.getBoolean(Constants.FONT_FIRST_SHOW, false);
    }

    public final boolean getFontUsed() {
        return this.prefs.getBoolean("first_open40", false);
    }

    public final boolean getForceBlackFriday() {
        return this.prefs.getBoolean("force_friday", false);
    }

    public final boolean getFountainPenBtnClick() {
        return this.prefs.getBoolean("fountainPenClick", false);
    }

    public final boolean getFoutainPenUsed() {
        return this.prefs.getBoolean(Constants.FOUNTAIN_USED, false);
    }

    public final int getFreeTryShowTimes() {
        return this.prefs.getInt("free_try", 0);
    }

    public final boolean getHasAddCateClicked() {
        return this.prefs.getBoolean("has_click_added", false);
    }

    public final boolean getHasAutoSaveClick() {
        return this.prefs.getBoolean("auto_click", false);
    }

    public final boolean getHasBuyed() {
        return this.prefs.getBoolean(Constants.HAS_BUYED, false);
    }

    public final boolean getHasBuyedNeedCheck() {
        return this.prefs.getBoolean(Constants.HAS_BUYED_NEED_CHECK, true);
    }

    public final boolean getHasClickNewNote() {
        return this.prefs.getBoolean(Constants.NEW_TAG_NEW_NOTE, false);
    }

    public final boolean getHasClickNewNoteDialog() {
        return this.prefs.getBoolean(Constants.NEW_TAG_NEW_NOTE_DIALOG, false);
    }

    public final boolean getHasCreateNewTag() {
        return this.prefs.getBoolean(Constants.NEW_TAG_AL, false);
    }

    public final boolean getHasCreateShowed() {
        return this.prefs.getBoolean(Constants.NEW_TAG_SHOW, false);
    }

    public final boolean getHasHandGuideShowed() {
        return this.prefs.getBoolean(Constants.HAND_SHOW, false);
    }

    public final boolean getHasInitCate() {
        return this.prefs.getBoolean(Constants.ALREADY_INIT_CATE, false);
    }

    public final boolean getHasLocalBackuped() {
        return this.prefs.getBoolean(Constants.BACKUPED, false);
    }

    public final boolean getHasMonthlySubscribe() {
        return this.prefs.getBoolean(Constants.HAS_MONTH_SUBSD, false);
    }

    public final boolean getHasMoved() {
        return this.prefs.getBoolean(Constants.MOVE_LIST, false);
    }

    public final boolean getHasNewFontUpdated() {
        return this.prefs.getBoolean("new_font_release" + this.newFontVersion, false);
    }

    public final boolean getHasNewWidgetClick() {
        return this.prefs.getBoolean("new_widget" + this.newWidgetVersion, false);
    }

    public final boolean getHasNoDefaultCate() {
        return this.prefs.getBoolean("no_default_cates", false);
    }

    public final boolean getHasPromoteShowed() {
        return this.prefs.getBoolean("auto_banner_showed", false);
    }

    public final boolean getHasPulledOff5() {
        return this.prefs.getBoolean("seventy_off50", false);
    }

    public final boolean getHasPulledOff7() {
        return this.prefs.getBoolean("seventy_off", false);
    }

    public final boolean getHasReversed() {
        return this.prefs.getBoolean(Constants.VIP_REVERSE, false);
    }

    public final boolean getHasSubscribe() {
        return this.prefs.getBoolean(Constants.HAS_SUBSD, false);
    }

    public final boolean getHasSyncClick() {
        return this.prefs.getBoolean("sync_click", false);
    }

    public final String getHasUnlockTheme() {
        return this.prefs.getString(Constants.UNLOCKED_THEME, "");
    }

    public final boolean getHasVipDebug() {
        return this.prefs.getBoolean("HAS_VIPDEBUG", false);
    }

    public final boolean getHasYearlySubscribe() {
        return this.prefs.getBoolean(Constants.HAS_YEAR_SUBSD, false);
    }

    public final boolean getHome55VipBanner() {
        return this.prefs.getBoolean("home55VipBanner", false);
    }

    public final long getHome55VipBannerTime() {
        return this.prefs.getLong("home55VipBannerTime", 0L);
    }

    public final boolean getHomeAbNoteExample() {
        return this.prefs.getBoolean(Constants.HOME_AB_NOTE_EXAMPLE, false);
    }

    public final boolean getHomeAbNoteShowRate() {
        return this.prefs.getBoolean(Constants.HOME_AB_NOTE_SHOW_RATE, false);
    }

    public final long getHomeCloseBannerTime() {
        return this.prefs.getLong("homeCloseBannerTime", 0L);
    }

    public final long getHomePageDialogShowTime() {
        return this.prefs.getLong("homePageDialogShowTime", 0L);
    }

    public final boolean getHomeWidgetMgtSinglenoteClick() {
        return this.prefs.getBoolean(Constants.HOME_WIDGET_MGT_SINGLENOTE_CLICK, false);
    }

    public final boolean getImageUsed() {
        return this.prefs.getBoolean("first_open6", false);
    }

    public final boolean getInAppReFund() {
        return this.prefs.getBoolean("refund_inapp", false);
    }

    public final int getInstallVersion() {
        return this.prefs.getInt(Constants.INSTALL_VERSION, 0);
    }

    public final long getLastAdReportTime() {
        return this.prefs.getLong("ad_report_time", 0L);
    }

    public final long getLastAdShowTime() {
        return this.prefs.getLong(Constants.LAST_AD_SHOW, 0L);
    }

    public final long getLastAdmobShowtimes() {
        return this.prefs.getLong("admob_show", 0L);
    }

    public final long getLastLifeTimePurchaseTime() {
        return this.prefs.getLong("LAST_LIFETIME_PURCHASE_TIME", 0L);
    }

    public final long getLastMedalReached() {
        return this.prefs.getLong("last_medal_reached", 0L);
    }

    public final long getLastNotesTimed() {
        return this.prefs.getLong("last_notes_time", 0L);
    }

    public final long getLastOpenTime() {
        return this.prefs.getLong(Constants.LAST_OPEN_TIME, 0L);
    }

    public final long getLastPushTime() {
        return this.prefs.getLong(Constants.LAST_PUSH_TIME, 0L);
    }

    public final long getLastSizeReport() {
        return this.prefs.getLong(Constants.LAST_SIZE_REPOET, 0L);
    }

    public final long getLastSyncStartTime() {
        return this.prefs.getLong("lastSyncStartTime", 0L);
    }

    public final boolean getLockClick() {
        return this.prefs.getBoolean("lockClick", false);
    }

    public final boolean getLockGuideShowed() {
        return this.prefs.getBoolean(Constants.LOCK_GUDE_SHOW, false);
    }

    public final boolean getLockRedFirstShow() {
        return this.prefs.getBoolean(Constants.LOCK_FIRST_SHOW, false);
    }

    public final boolean getMedalL1Reached() {
        return this.prefs.getBoolean("medal_l1_reached", false);
    }

    public final boolean getMedalL1Showed() {
        return this.prefs.getBoolean("medal_l1_showed", false);
    }

    public final boolean getMedalL2Reached() {
        return this.prefs.getBoolean("medal_l2_reached", false);
    }

    public final boolean getMedalL2Showed() {
        return this.prefs.getBoolean("medal_l2_showed", false);
    }

    public final boolean getMedalL3Reached() {
        return this.prefs.getBoolean("medal_l3_reached", false);
    }

    public final boolean getMedalL3Showed() {
        return this.prefs.getBoolean("medal_l3_showed", false);
    }

    public final boolean getMedalL4Reached() {
        return this.prefs.getBoolean("medal_l4_reached", false);
    }

    public final boolean getMedalL4Showed() {
        return this.prefs.getBoolean("medal_l4_showed", false);
    }

    public final boolean getMedalL5Reached() {
        return this.prefs.getBoolean("medal_l5_reached", false);
    }

    public final boolean getMedalL5Showed() {
        return this.prefs.getBoolean("medal_l5_showed", false);
    }

    public final boolean getMedalL6Reached() {
        return this.prefs.getBoolean("medal_l6_reached", false);
    }

    public final boolean getMedalL6Showed() {
        return this.prefs.getBoolean("medal_l6_showed", false);
    }

    public final boolean getMedalL7Reached() {
        return this.prefs.getBoolean("medal_l7_reached", false);
    }

    public final boolean getMedalL7Showed() {
        return this.prefs.getBoolean("medal_l7_showed", false);
    }

    public final boolean getMedalL8Reached() {
        return this.prefs.getBoolean("medal_l8_reached", false);
    }

    public final boolean getMedalL8Showed() {
        return this.prefs.getBoolean("medal_l8_showed", false);
    }

    public final boolean getMedalL9Reached() {
        return this.prefs.getBoolean("medal_l9_reached", false);
    }

    public final boolean getMedalL9Showed() {
        return this.prefs.getBoolean("medal_l9_showed", false);
    }

    public final boolean getMediaPermissionRationaleRequested() {
        return this.prefs.getBoolean("media_permission_rationale_requested", false);
    }

    public final boolean getMonthlyFree7DaysExist() {
        return this.prefs.getBoolean("monthlyFree7DaysExist", true);
    }

    public final int getMopubShowTimes() {
        return this.prefs.getInt("mopub_show_times", 0);
    }

    public final boolean getNeverShowReminder() {
        return this.prefs.getBoolean(Constants.NEVER_SHOW_REMINDER, false);
    }

    public final long getNewDrawReleasePromoteShowTime() {
        return this.prefs.getLong("new_draw_release_version_time" + this.newDrawReleaseVersion, 0L);
    }

    public final int getNewDrawReleaseVersion() {
        return this.newDrawReleaseVersion;
    }

    public final long getNewEmoReleasePromoteShowTime() {
        return this.prefs.getLong("new_Emo_release_version_time" + this.newEmojiReleaseVersion, 0L);
    }

    public final int getNewEmojiReleaseVersion() {
        return this.newEmojiReleaseVersion;
    }

    public final long getNewFontUpdatedShowTime() {
        return this.prefs.getLong("new_font_updated_show_time" + this.newFontVersion, 0L);
    }

    public final int getNewFontVersion() {
        return this.newFontVersion;
    }

    public final boolean getNewRealOpen() {
        return this.prefs.getBoolean("first_open2", false);
    }

    public final boolean getNewReleasesNotificationSwitch() {
        return this.prefs.getBoolean("newReleasesNotificationSwitch1", true);
    }

    public final boolean getNewUser() {
        return this.prefs.getBoolean(Constants.NEW_USERS, true);
    }

    public final int getNewUserClickHomepageCreateNoteButton() {
        return this.prefs.getInt("newUserClickHomepageCreateNoteButton", 0);
    }

    public final boolean getNewUserShowed() {
        return this.prefs.getBoolean(Constants.NEWUSER_VIP_SHOW, false);
    }

    public final boolean getNewUserVipBanner() {
        return this.prefs.getBoolean("newUserVipBanner", false);
    }

    public final boolean getNewViphasShowed() {
        return this.prefs.getBoolean(Constants.NEW_SHOW, false);
    }

    public final long getNewWidgetShowTime() {
        return this.prefs.getLong("new_widget_show_time" + this.newWidgetVersion, 0L);
    }

    public final int getNewWidgetVersion() {
        return this.newWidgetVersion;
    }

    public final boolean getNoSetPasswordBuriedPoint() {
        return this.prefs.getBoolean("noSetPasswordBuriedPoint", false);
    }

    public final boolean getNote20Writing() {
        return this.prefs.getBoolean(Constants.NOTE_20_WRITING, false);
    }

    public final boolean getNoteBgPreCopyFinished() {
        return this.prefs.getBoolean("noteBgPreCopyFinished", false);
    }

    public final boolean getNoteNotCreateNotification1() {
        return this.prefs.getBoolean(Constants.NOTE_NOT_CREATE_NOTIFICATION_1, false);
    }

    public final boolean getNoteNotCreateNotification2() {
        return this.prefs.getBoolean(Constants.NOTE_NOT_CREATE_NOTIFICATION_2, false);
    }

    public final boolean getNotificationPermissionRationaleRequested() {
        return this.prefs.getBoolean("notification_permission_rationale_requested", false);
    }

    public final int getNotifyPermissionCount() {
        return this.prefs.getInt("getNotifyPermissionCount", 0);
    }

    public final boolean getOldNotiShowed() {
        return this.prefs.getBoolean(Constants.OLD_BILLING_SHOW, false);
    }

    public final boolean getOriginSizeG() {
        return this.prefs.getBoolean(Constants.BACKUP_IMAGE_G, false);
    }

    public final boolean getOriginSizeL() {
        return this.prefs.getBoolean(Constants.BACKUP_IMAGE_L, false);
    }

    public final int getPaintType() {
        return this.prefs.getInt(Constants.PAINT_TYPE, 1);
    }

    public final String getPatternPassword() {
        return this.prefs.getString("savePatternPassword", "");
    }

    public final boolean getPatternPwdSetOk() {
        return this.prefs.getBoolean("patternPwdSetOk", false);
    }

    public final boolean getPdfClick() {
        return this.prefs.getBoolean("pdfClick", false);
    }

    public final boolean getPdfRedFirstShow() {
        return this.prefs.getBoolean(Constants.PDF_FIRST_SHOW, false);
    }

    public final boolean getPencilBtnClick() {
        return this.prefs.getBoolean("pencilBtnClick", false);
    }

    public final boolean getPencilPenUsed() {
        return this.prefs.getBoolean(Constants.PENCIL_USED, false);
    }

    public final boolean getPicRedFirstShow() {
        return this.prefs.getBoolean(Constants.PIC_FIRST_SHOW, false);
    }

    public final int getPictureEditingABTest() {
        return this.prefs.getInt(Constants.PICTURE_EDITING_AB_TEST, 0);
    }

    public final boolean getPinpwdSetOk() {
        return this.prefs.getBoolean(Constants.PWD_SET_OK, false);
    }

    public final SharedPreferences getPrefs() {
        return this.prefs;
    }

    public final boolean getPreviewGuideShowed() {
        return this.prefs.getBoolean(Constants.PIC_GUDE_SHOW, false);
    }

    public final long getPromoteRedPointLockShowTime() {
        return this.prefs.getLong("promoteRedPointLockShowTime", 0L);
    }

    public final long getPromoteRedPointPDFShowTime() {
        return this.prefs.getLong("promoteRedPointPDFShowTime", 0L);
    }

    public final long getPromoteRedPointPICShowTime() {
        return this.prefs.getLong("promoteRedPointPICShowTime", 0L);
    }

    public final long getPromoteRedPointReminderShowTime() {
        return this.prefs.getLong("promoteRedPointReminderShowTime", 0L);
    }

    public final long getPurchaseTimeOfCancel() {
        return this.prefs.getLong("purchase_cancel_time", 0L);
    }

    public final int getPushOnFriday() {
        return this.prefs.getInt(Constants.PUSH_ON_FRIDAY, 0);
    }

    public final int getPushOnSaturday() {
        return this.prefs.getInt(Constants.PUSH_ON_SATURDAY, 0);
    }

    public final int getPushOnTuesday() {
        return this.prefs.getInt(Constants.PUSH_ON_TUESDAY, 0);
    }

    public final int getPushOnWednesday() {
        return this.prefs.getInt(Constants.PUSH_ON_WEDNESDAY, 0);
    }

    public final String getPwdCode() {
        return this.prefs.getString(Constants.PWD_CODE, "");
    }

    public final String getPwdQuestionContent() {
        return this.prefs.getString(Constants.PWD_QUESTION_CONTENT, "");
    }

    public final int getPwdQuestionPosition() {
        return this.prefs.getInt(Constants.PWD_QUESTION_POSITION, 0);
    }

    public final boolean getReadingDialogShowed() {
        return this.prefs.getBoolean("reading_dialog_showed", false);
    }

    public final boolean getReadingDialogShowed2() {
        return this.prefs.getBoolean("reading_dialog_showed2", false);
    }

    public final boolean getRecRedFirstShow() {
        return this.prefs.getBoolean(Constants.REC_FIRST_SHOW, false);
    }

    public final String getRecentColors() {
        return this.prefs.getString(Constants.RECENT_COLOR, "");
    }

    public final boolean getRecordBtnClick() {
        return this.prefs.getBoolean("recordBtnClick", false);
    }

    public final boolean getRecordUsed() {
        return this.prefs.getBoolean("first_open41", false);
    }

    public final String getRememberBgColor() {
        return this.prefs.getString(Constants.REMEMBER_BG_COLOR, Constants.DEFAULT_COLOR_YELLOW);
    }

    public final int getRememberBgColorType() {
        return this.prefs.getInt(Constants.REMEMBER_BG_COLOR_TYPE, 0);
    }

    public final int getRememberBgId() {
        return this.prefs.getInt(Constants.REMEMBER_BG_ID, 0);
    }

    public final boolean getRememberBgSwitch() {
        return this.prefs.getBoolean(Constants.REMEMBER_SWITCH, true);
    }

    public final long getRemindChannelId() {
        return this.prefs.getLong("remind_id", 0L);
    }

    public final boolean getRemindRedFirstShow() {
        return this.prefs.getBoolean(Constants.REMIND_FIRST_SHOW, false);
    }

    public final boolean getReminderClick() {
        return this.prefs.getBoolean("reminderClick", false);
    }

    public final String getRingTonNow() {
        return this.prefs.getString("rington_name", "");
    }

    public final boolean getSamsungCaseTipsShowed() {
        return this.prefs.getBoolean("samsungCaseTipsShowed", false);
    }

    public final boolean getSaveNoteFirst() {
        return this.prefs.getBoolean(Constants.SAVE_NOTE_FIRST, false);
    }

    public final int getSelectColorRoundShow() {
        return this.prefs.getInt(Constants.SELECT_COLOR_POSITION, -1);
    }

    public final int getSelectWidget() {
        return this.prefs.getInt(Constants.SELECT_WIDGET, 1);
    }

    public final long getSeriesNotesNumb() {
        return this.prefs.getLong("series_notes_num", 0L);
    }

    public final boolean getSevenDayOff() {
        return this.prefs.getBoolean(Constants.SEVEN_DAYS_OFF, false);
    }

    public final boolean getShareRedFirstShow() {
        return this.prefs.getBoolean(Constants.SHARE_FIRST_SHOW, false);
    }

    public final boolean getShareUsed() {
        return this.prefs.getBoolean("first_open8", false);
    }

    public final boolean getShowBlackFridayPage() {
        return this.prefs.getBoolean("black_page_showed", false);
    }

    public final int getShowCheckListReminder() {
        return this.prefs.getInt("showCheckListReminder", 1);
    }

    public final boolean getShowClipBoardNoti() {
        return this.prefs.getBoolean("show_clipboard", true);
    }

    public final boolean getShowEditBannerAd() {
        return this.prefs.getBoolean("showEditBannerAd", false);
    }

    public final boolean getShowFaceBookDialog() {
        return this.prefs.getBoolean("showFaceBookDialog", true);
    }

    public final boolean getShowInterAd() {
        return this.prefs.getBoolean(Constants.FIRST_WRITE, false);
    }

    public final boolean getShowMedalEnter() {
        return this.prefs.getBoolean("show_medal_enter", false);
    }

    public final boolean getShowWelcom() {
        return this.prefs.getBoolean("show_welcom", false);
    }

    public final boolean getShowWidgetUpdateDialog() {
        return this.prefs.getBoolean("showWidgetUpdateDialog", false);
    }

    public final boolean getShowhowPaperBackground() {
        return this.prefs.getBoolean("isShowPaperBackground", false);
    }

    public final boolean getSiderMedalRed() {
        return this.prefs.getBoolean("sider_medal_red", false);
    }

    public final boolean getSixHourDialogShowed() {
        return this.prefs.getBoolean("six_hour_dialog_show", false);
    }

    public final boolean getSkipedSplash() {
        return this.prefs.getBoolean(Constants.splash_SWITCH, false);
    }

    public final boolean getSortGridBugfix() {
        return this.prefs.getBoolean("sortGridBugfix", false);
    }

    public final boolean getStarRecordViewTimes() {
        return this.prefs.getBoolean(Constants.START_RECORD_TIMES, false);
    }

    public final String getSubsActiveList() {
        return this.prefs.getString("subs_active_list", "");
    }

    public final boolean getSummer70OffShowed() {
        return this.prefs.getBoolean("summer_70ff", false);
    }

    public final boolean getSurveyBannerClicked() {
        return this.prefs.getBoolean("surveyBannerClicked", false);
    }

    public final long getSurveyBannerShowTime() {
        return this.prefs.getLong("surveyBannerShowTime", 0L);
    }

    public final long getSyncBannerFirstShowTime() {
        return this.prefs.getLong("sync_show_time", 0L);
    }

    public final long getSyncBannerSHowTime() {
        return this.prefs.getLong("syncBannerSHowTime", 0L);
    }

    public final int getSyncBannerSerialNoSyncClickCount() {
        return this.prefs.getInt("syncBannerSerialNoSyncClickCount", 0);
    }

    public final int getSyncBannerSerialNoSyncShowCount() {
        return this.prefs.getInt("syncBannerSerialNoSyncShowCount", 1);
    }

    public final long getSyncBannerSerialNoSyncShowTime() {
        return this.prefs.getLong("syncBannerSerialNoSyncShowTime", 0L);
    }

    public final boolean getSyncClick() {
        return this.prefs.getBoolean("sync_click1", false);
    }

    public final boolean getSyncCreateConfig() {
        return this.prefs.getBoolean("sync_create_config1", false);
    }

    public final boolean getSyncLastTimeErrorClosed() {
        return this.prefs.getBoolean("sync_last_time_error_closed", false);
    }

    public final String getSyncLastTimeErrorCode() {
        return this.prefs.getString("sync_last_time_error_code", "");
    }

    public final String getSyncLoginDriStatus() {
        return this.prefs.getString("sync_okk_dri", "login_OK_with_drive");
    }

    public final boolean getSyncLoginOkWithDri() {
        return this.prefs.getBoolean("sync_login_ok_dri1", false);
    }

    public final boolean getSyncLoginOkWithoutDri() {
        return this.prefs.getBoolean("sync_login_ok_nodri1", false);
    }

    public final boolean getSyncLoginPage() {
        return this.prefs.getBoolean("sync_login_page1", false);
    }

    public final String getSyncLoginStatus() {
        return this.prefs.getString("sync_okk_status", "login_account_show_with_net");
    }

    public final boolean getSyncNever() {
        return this.prefs.getBoolean("sync_never1", false);
    }

    public final boolean getSyncOk() {
        return this.prefs.getBoolean("sync_okk2023", false);
    }

    public final boolean getSyncShowAccount() {
        return this.prefs.getBoolean("sync_account_show1", false);
    }

    public final boolean getSyncShowWithNet() {
        return this.prefs.getBoolean("sync_with_net1", false);
    }

    public final boolean getSyncShowWithoutNet() {
        return this.prefs.getBoolean("sync_without_net1", false);
    }

    public final long getSyncSteps() {
        return this.prefs.getLong("sync_steps", 0L);
    }

    public final String getTags() {
        return this.prefs.getString(Constants.TAGS_CONTENT, "");
    }

    public final boolean getThemeRedFirstShow() {
        return this.prefs.getBoolean(Constants.THEME_FIRST_SHOW, false);
    }

    public final int getThemeState() {
        return this.prefs.getInt(Constants.THEME_STATE, 0);
    }

    public final boolean getThemeTestHasRun() {
        return this.prefs.getBoolean("theme_test", false);
    }

    public final boolean getThreeDayOff() {
        return this.prefs.getBoolean(Constants.THREE_DAYS_OFF, false);
    }

    public final long getTimeBackupLastNotifyTime() {
        return this.prefs.getLong(Constants.TIME_BACKUP_LAST_TIME, 0L);
    }

    public final boolean getTimeBackupNotification() {
        return this.prefs.getBoolean(Constants.TIME_BACKUP_NOTIFICATION, false);
    }

    public final boolean getTimeBackupTimeNotification() {
        return this.prefs.getBoolean(Constants.TIME_BACKUP_TIME_NOTIFICATION, false);
    }

    public final boolean getTimeClickRateNow() {
        return this.prefs.getBoolean(Constants.TIME_CLICK_RATE_NOW, false);
    }

    public final boolean getTimeDarkThemeShowed() {
        return this.prefs.getBoolean("time_dark_showed", false);
    }

    public final long getTimeDarkThemeShowedTime() {
        return this.prefs.getLong("timeDarkThemeShowedTime", 0L);
    }

    public final int getTimeEditLockRed() {
        return this.prefs.getInt(Constants.TIME_EDIT_LOCK_RED, 0);
    }

    public final int getTimeEditPDFRed() {
        return this.prefs.getInt(Constants.TIME_EDIT_PDF_RED, 0);
    }

    public final int getTimeEditRemindRed() {
        return this.prefs.getInt(Constants.TIME_EDIT_REMIND_RED, 0);
    }

    public final int getTimeEditShareRed() {
        return this.prefs.getInt(Constants.TIME_EDIT_SHARE_RED, 0);
    }

    public final boolean getTimeFamilyAppRed() {
        return this.prefs.getBoolean(Constants.TIME_FAMILY_APP_RED, false);
    }

    public final boolean getTimeHomeBackupDialog() {
        return this.prefs.getBoolean(Constants.TIME_HOME_BACKUP_DIALOG, false);
    }

    public final boolean getTimeHomeBackupRed() {
        return this.prefs.getBoolean(Constants.TIME_HOME_BACKUP_Red, false);
    }

    public final int getTimeHomeLockDialog() {
        return this.prefs.getInt(Constants.TIME_HOME_LOCK_DIALOG, 0);
    }

    public final long getTimeHomeVipDialogShowTime() {
        return this.prefs.getLong("timeHomeVipDialogShowTime", 0L);
    }

    public final boolean getTimeLandThemeShowed() {
        return this.prefs.getBoolean("time_land_showed", false);
    }

    public final long getTimeLandThemeShowedTime() {
        return this.prefs.getLong("timeLandThemeShowedTime", 0L);
    }

    public final boolean getTimeNotificationPermissionDialog() {
        return this.prefs.getBoolean("time_notification_permission_dialog", false);
    }

    public final long getTimeOfEnterVip() {
        return this.prefs.getLong(Constants.TIME_ENTER_VIP, 0L);
    }

    public final long getTimePaperThemeShowedTime() {
        return this.prefs.getLong("timePaThemeShowedTime", 0L);
    }

    public final long getTimeRateUsDialogShowNum() {
        return this.prefs.getLong(Constants.TIME_SECOND_RATE_DIALOG_NUM, 0L);
    }

    public final long getTimeRateUsDialogShowTime() {
        return this.prefs.getLong(Constants.TIME_SECOND_RATE_DIALOG_TIME, 0L);
    }

    public final long getTimeSchduleFive() {
        return this.prefs.getLong("time_vip_after9", 0L);
    }

    public final boolean getTimeSchduleNineDisShowed() {
        return this.prefs.getBoolean("time_discount_showed", false);
    }

    public final long getTimeSchduleOne() {
        return this.prefs.getLong("time_vip_1", 0L);
    }

    public final boolean getTimeSchduleOneShowed() {
        return this.prefs.getBoolean("time_vip_1_showed", false);
    }

    public final boolean getTimeSchduleThreeShowed() {
        return this.prefs.getBoolean("time_vip_3_showed", false);
    }

    public final long getTimeSchduleTwo() {
        return this.prefs.getLong("time_vip_2", 0L);
    }

    public final long getTimeSchduleTwo2() {
        return this.prefs.getLong("time_vip_02", 0L);
    }

    public final long getTimeSchduleTwo3() {
        return this.prefs.getLong("time_vip_03", 0L);
    }

    public final boolean getTimeSchduleTwoShowed() {
        return this.prefs.getBoolean("time_vip_2_showed", false);
    }

    public final boolean getTimeSchollThemeShowed() {
        return this.prefs.getBoolean("time_school_showed", false);
    }

    public final long getTimeSchoolThemeShowedTime() {
        return this.prefs.getLong("timeSchoolThemeShowedTime", 0L);
    }

    public final boolean getTimeSecondRateDialogShow() {
        return this.prefs.getBoolean(Constants.TIME_SECOND_RATE_DIALOG, false);
    }

    public final boolean getTimeShopThemeShowed() {
        return this.prefs.getBoolean("time_shop_showed", false);
    }

    public final long getTimeShopThemeShowedTime() {
        return this.prefs.getLong("timeShopThemeShowedTime", 0L);
    }

    public final long getUpDataVersionNumber() {
        return this.prefs.getLong("upDataVersionNumber", 0L);
    }

    public final boolean getUsaNewReleaseShowed() {
        return this.prefs.getBoolean("new_release_version_us", false);
    }

    public final boolean getUserLastModifiedBugfix() {
        return this.prefs.getBoolean("userLastModifiedBugfix2", false);
    }

    public final String getUserPurchaseFrom() {
        return this.prefs.getString("user_purchase_from", "");
    }

    public final long getUserPurchaseTime() {
        return this.prefs.getLong("user_purchase_time", 0L);
    }

    public final long getVipFirstOldCountDown() {
        return this.prefs.getLong(Constants.FIRST_OLD_COUNT_DOWN, 0L);
    }

    public final boolean getVipFirstOldEnter() {
        return this.prefs.getBoolean(Constants.FIRST_OLD_ENTER, false);
    }

    public final boolean getVipFirstOldEnter11() {
        return this.prefs.getBoolean(Constants.FIRST_OLD_ENTER11, false);
    }

    public final boolean getVipFirstOldTimeLine() {
        return this.prefs.getBoolean(Constants.FIRST_OLD_ENTER_TIMELINE, false);
    }

    public final boolean getVipFirstOldTimeLineSecond() {
        return this.prefs.getBoolean(Constants.FIRST_OLD_ENTER_TIMELINE_TWO, false);
    }

    public final boolean getVipHasShowed() {
        return this.prefs.getBoolean(Constants.VIP_SHOWED, false);
    }

    public final String getVipId() {
        return this.prefs.getString(Constants.VIP_IDS, "");
    }

    public final long getVipNewUserPageStartTime() {
        return this.prefs.getLong("vip_new_user_start_time", 0L);
    }

    public final long getVipOldUserPageStartTime() {
        return this.prefs.getLong("vip_old_user_start_time", 0L);
    }

    public final long getVipPageEndTime() {
        return this.prefs.getLong("vip_page_end_time", 0L);
    }

    public final int getVipPageShowTimes() {
        return this.prefs.getInt(Constants.VIP_PAGE_SHOW, 0);
    }

    public final int getVipShowTimes() {
        return this.prefs.getInt(Constants.VIP_SHOW_TIMES, 0);
    }

    public final int getVipStateDebug() {
        return this.prefs.getInt("vipStateDebug", 0);
    }

    public final int getVipUserCancelFreeTrialMonthlyCount() {
        return this.prefs.getInt("vipUserCancelFreeTrialMonthlyCount", 0);
    }

    public final long getVipUserCancelFreeTrialStartTime() {
        return this.prefs.getLong("vipUserCancelFreeTrialStartTime", 0L);
    }

    public final int getVipUserCancelFreeTrialYearlyCount() {
        return this.prefs.getInt("vipUserCancelFreeTrialYearlyCount", 0);
    }

    public final int getVipWelcomePageContinueClick() {
        return this.prefs.getInt("vipWelcomePageContinueClick", 0);
    }

    public final long getVipWelcomePagePurchaseTime() {
        return this.prefs.getLong("vipWelcomePagePurchaseTime", 0L);
    }

    public final int getVipWelcomePageSkuClick() {
        return this.prefs.getInt("vipWelcomePageSkuClick", 0);
    }

    public final boolean getVoteMindNotesClicked() {
        return this.prefs.getBoolean("voteMindNotesClicked1", false);
    }

    public final int getVoteMindNotesCount() {
        return this.prefs.getInt("voteMindNotesCount1", 0);
    }

    public final long getVoteMindNotesShowTime() {
        return this.prefs.getLong("voteMindNotesShowTime1", 0L);
    }

    public final long getWelcomShowtime() {
        return this.prefs.getLong("welcom_show", 0L);
    }

    public final int getWhatsNewVersion() {
        return this.whatsNewVersion;
    }

    public final long getWidgetBannerSHowTime() {
        return this.prefs.getLong("widgetBannerSHowTime", 0L);
    }

    public final boolean getWidgetClick() {
        return this.prefs.getBoolean("widgetClick", false);
    }

    public final boolean getWidgetDialogShow() {
        return this.prefs.getBoolean(Constants.WIDGET_DIALOG_SHOW, false);
    }

    public final boolean getWidgetNotifySwitch() {
        return this.prefs.getBoolean("widget_notify_switch1", true);
    }

    public final boolean getWidgetPromoteSinglenoteAdd() {
        return this.prefs.getBoolean(Constants.WIDGET_PROMOTE_SINGLENOTE_ADD, false);
    }

    public final boolean getWidgetSiderBar() {
        return this.prefs.getBoolean(Constants.WIDGET_SIDEBAR, false);
    }

    public final String getWidgetUsage() {
        return this.prefs.getString("widget_usage", "");
    }

    public final boolean getYearlyFree7DaysExist() {
        return this.prefs.getBoolean("yearlyFree7DaysExist", true);
    }

    public final long getYearlyPriceDiscount10Num() {
        return this.prefs.getLong("symble_umn", 0L);
    }

    public final String getYearlyPriceDiscount10Symble() {
        return this.prefs.getString("symble_mon", "");
    }

    public final long getYearlyPriceDiscount30Num() {
        return this.prefs.getLong("symble_umn_30", 0L);
    }

    public final String getYearlyPriceDiscount30Symble() {
        return this.prefs.getString("symble_mon_30", "");
    }

    public final long getYearlyPriceDiscountNum() {
        return this.prefs.getLong("symble_umn_50", 0L);
    }

    public final String getYearlyToMonthCoin() {
        return this.prefs.getString("symble_mon_unit", "");
    }

    public final boolean isCalendarGuideDialogShow() {
        return this.prefs.getBoolean("isCalendarGuideDialogShow", true);
    }

    public final boolean isEnableAppSwitch() {
        return this.prefs.getBoolean("isEnableAppSwitch", false);
    }

    public final boolean isEnableCategorySwitch() {
        return this.prefs.getBoolean("isEnableCategorySwitch", false);
    }

    public final boolean isEnableFingerprintSwitch() {
        return this.prefs.getBoolean("isEnableFingerprintSwitch", false);
    }

    public final boolean isEnableNoteSwitch() {
        return this.prefs.getBoolean("isEnableNoteSwitch", false);
    }

    public final boolean isHomepageCalendarViewClick() {
        return this.prefs.getBoolean("isHomepageCalendarViewClick", false);
    }

    public final boolean isNewDrawBgRelea() {
        return this.prefs.getBoolean("new_drawbg_release1", false);
    }

    public final boolean isNewDrawBgRelea1() {
        return this.prefs.getBoolean("new_drawbg_release11", false);
    }

    public final boolean isNewDrawPenRelea() {
        return this.prefs.getBoolean("new_drawpen_release1", false);
    }

    public final boolean isNewDrawPenRelea1() {
        return this.prefs.getBoolean("new_drawpen_release11", false);
    }

    public final boolean isNewDrawReleasePromoteShow() {
        return this.prefs.getBoolean("new_draw_release_version" + this.newDrawReleaseVersion, false);
    }

    public final boolean isNewEmoReleasePromoteShow() {
        return this.prefs.getBoolean("new_Emo_release_version" + this.newEmojiReleaseVersion, false);
    }

    public final boolean isNewEmojiRelea() {
        return this.prefs.getBoolean("new_emoji_release1", true);
    }

    public final boolean isNewEmojiRelea1() {
        return this.prefs.getBoolean("new_emoji_release11", false);
    }

    public final boolean isNewFontRelea() {
        return this.prefs.getBoolean("new_font_release1", true);
    }

    public final boolean isNewFontRelea1() {
        return this.prefs.getBoolean("new_font_release11", false);
    }

    public final boolean isShowSidebarCalendarRed() {
        return this.prefs.getBoolean("isShowSidebarCalendarRed", true);
    }

    public final boolean isShowSidebarHelpCenterRed() {
        return this.prefs.getBoolean("isShowSidebarHelpCenterRed", true);
    }

    public final boolean isShowSyncFAQRed() {
        return this.prefs.getBoolean("isShowSyncFAQRed", true);
    }

    public final boolean isWhatsNewPromoteShow() {
        return this.prefs.getBoolean("whats_new_version" + this.whatsNewVersion, false);
    }

    public final void setAbTest3DaysTrialExist(boolean z6) {
        this.prefs.edit().putBoolean("abTest3DaysTrialExist", z6).apply();
    }

    public final void setAbTestWelcome(int i6) {
        this.prefs.edit().putInt("abTestWelcome20250115", i6).apply();
    }

    public final void setAbTestcoherentFuidance(int i6) {
        this.prefs.edit().putInt("abTestcoherentFuidance", i6).apply();
    }

    public final void setActiveDay(int i6) {
        this.prefs.edit().putInt("activeDay", i6).apply();
    }

    public final void setActiveNewDay(int i6) {
        this.prefs.edit().putInt("activeDay1", i6).apply();
    }

    public final void setActiveNewTime(long j6) {
        this.prefs.edit().putLong("activeTime1", j6).apply();
    }

    public final void setActiveTime(long j6) {
        this.prefs.edit().putLong("activeTime", j6).apply();
    }

    public final void setAdGroup(int i6) {
        this.prefs.edit().putInt("adGroup20241230", i6).apply();
    }

    public final void setAddPicUsed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.IMG_USED, z6).apply();
    }

    public final void setAddPicUsedClick(boolean z6) {
        this.prefs.edit().putBoolean(Constants.IMG_USED_CLICK, z6).apply();
    }

    public final void setAffixBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("affixBtnClick", z6).apply();
    }

    public final void setAllCateIndex(int i6) {
        this.prefs.edit().putInt(Constants.CATE_INDEX, i6).apply();
    }

    public final void setAppDeepLevel(String str) {
        this.prefs.edit().putString("deep_level", str).apply();
    }

    public final void setAppFuncusage(String str) {
        this.prefs.edit().putString("app_use_level", str).apply();
    }

    public final void setAppLockRed(boolean z6) {
        this.prefs.edit().putBoolean("appLockRed", z6).apply();
    }

    public final void setAppNotificationSwitch(boolean z6) {
        this.prefs.edit().putBoolean("appNotificationSwitch1", z6).apply();
    }

    public final void setArchiveBugfix(boolean z6) {
        this.prefs.edit().putBoolean("archiveBugfix", z6).apply();
    }

    public final void setAutoBackupRed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.BACKUP_AUTO_RED, z6).apply();
    }

    public final void setAutoBackupSwitch(boolean z6) {
        this.prefs.edit().putBoolean(Constants.BACKUP_AUTO_SWITCH, z6).apply();
    }

    public final void setAutoBackupToast(long j6) {
        this.prefs.edit().putLong(Constants.BACKUP_AUTO_TOAST, j6).apply();
    }

    public final void setAutoSyncChecked(boolean z6) {
        this.prefs.edit().putBoolean("autoSyncCheck", z6).apply();
    }

    public final void setAutoSyncDialogNeedShow(boolean z6) {
        this.prefs.edit().putBoolean("autoSyncDialogNeedShow", z6).apply();
    }

    public final void setAutoSyncSwitch(boolean z6) {
        this.prefs.edit().putBoolean(Constants.SYNC_AUTO_SWITCH, z6).apply();
    }

    public final void setAutoSyncSwitchState(int i6) {
        this.prefs.edit().putInt("autoSyncSwitchState", i6).apply();
    }

    public final void setBackupBannerSHowTime(long j6) {
        this.prefs.edit().putLong("backupBannerSHowTime", j6).apply();
    }

    public final void setBackup_unknown_red(boolean z6) {
        this.prefs.edit().putBoolean("backup_unknown_red", z6).apply();
    }

    public final void setBgBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("bgClick", z6).apply();
    }

    public final void setBgPenUsed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.BG_USED, z6).apply();
    }

    public final void setBgResConfigVersion(int i6) {
        this.prefs.edit().putInt("bgResConfigVersion", i6).apply();
    }

    public final void setBgResNewDialogHasShowedId(String str) {
        this.prefs.edit().putString("bgResNewDialogHasShowedId", str).apply();
    }

    public final void setBgResNewNeedShowId(String str) {
        this.prefs.edit().putString("bgResNewNeedShowId", str).apply();
    }

    public final void setBgResNewNotiHasShowedId(String str) {
        this.prefs.edit().putString("bgResNewNotiHasShowedId", str).apply();
    }

    public final void setBgUsed(boolean z6) {
        this.prefs.edit().putBoolean("first_open5", z6).apply();
    }

    public final void setBillingDiscount50LifetimeLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_LIFETIME_V1_50OFF_LONG_T, j6).apply();
    }

    public final void setBillingDiscount50LifetimeT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_LIFETIME_V1_50OFF_T, str).apply();
    }

    public final void setBillingDiscount50YearlyLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_YEARLY_V1_50OFF_LONG_T, j6).apply();
    }

    public final void setBillingDiscount50YearlyT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_V1_50OFF_T, str).apply();
    }

    public final void setBillingDiscount70LifetimeLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_LIFETIME_V1_70OFF_LONG_T, j6).apply();
    }

    public final void setBillingDiscount70LifetimeT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_LIFETIME_V1_70OFF_T, str).apply();
    }

    public final void setBillingDiscount70YearlyLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_YEARLY_V1_70OFF_LONG_T, j6).apply();
    }

    public final void setBillingDiscount70YearlyT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_V1_70OFF_T, str).apply();
    }

    public final void setBillingDiscount90LifetimeLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_LIFETIME_V1_90OFF_LONG_T, j6).apply();
    }

    public final void setBillingDiscount90LifetimeT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_LIFETIME_V1_90OFF_T, str).apply();
    }

    public final void setBillingDiscount90YearlyLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_YEARLY_V1_90OFF_LONG_T, j6).apply();
    }

    public final void setBillingDiscount90YearlyT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_V1_90OFF_T, str).apply();
    }

    public final void setBillingMonthlyFreeTry7DaysPrice(String str) {
        this.prefs.edit().putString("billingMonthlyFreeTry7DaysPrice", str).apply();
    }

    public final void setBillingMonthlyPrice(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_MONTHLY_PRICE, str).apply();
    }

    public final void setBillingMonthlyPriceT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_MONTHLY_V1_T, str).apply();
    }

    public final void setBillingMonthlyTestOpen(boolean z6) {
        this.prefs.edit().putBoolean(Constants.PRODUCT_MONTHLY_TEST_OPEN, z6).apply();
    }

    public final void setBillingMonthlyTestPrice(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_MONTHLY_TEST_PRICE, str).apply();
    }

    public final void setBillingNormalMonthlyPriceNum(long j6) {
        this.prefs.edit().putLong("month_price_num", j6).apply();
    }

    public final void setBillingNormalMonthlyTestPriceNum(long j6) {
        this.prefs.edit().putLong("month_test_price_num", j6).apply();
    }

    public final void setBillingNormalYearlyLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_YEARLY_V1_LONG_T, j6).apply();
    }

    public final void setBillingNormalYearlyPriceNum(long j6) {
        this.prefs.edit().putLong("symble_umn_price", j6).apply();
    }

    public final void setBillingOneMonthTimePrice(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_MONTHLY_PRICE_NEW, str).apply();
    }

    public final void setBillingOneTimeFake70(String str) {
        this.prefs.edit().putString("lifetime1_fake_70", str).apply();
    }

    public final void setBillingOneTimeFake90(String str) {
        this.prefs.edit().putString("lifetime1_fake_90", str).apply();
    }

    public final void setBillingOneTimeFakePrice(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_LIFETIME_PRICE_FAKE, str).apply();
    }

    public final void setBillingOneTimeFakePriceT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_LIFETIME_ORIGIN_T, str).apply();
    }

    public final void setBillingOneTimePrice(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_PRICE, str).apply();
    }

    public final void setBillingOneTimePriceDiscount(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_PRICE_DISCOUNT, str).apply();
    }

    public final void setBillingOneTimePriceDiscount10(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_PRICE_DISCOUNT10, str).apply();
    }

    public final void setBillingOneTimePriceDiscount10Num(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_PRICE_DISCOUNT10_NUM, j6).apply();
    }

    public final void setBillingOneTimePriceDiscount30(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_PRICE_DISCOUNT30, str).apply();
    }

    public final void setBillingOneTimePriceDiscount30Num(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_PRICE_DISCOUNT30_NUM, j6).apply();
    }

    public final void setBillingOneTimePriceDiscountNum(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_PRICE_DISCOUNT_NUM, j6).apply();
    }

    public final void setBillingOneTimePriceLongT(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_LIFETIME_V1_LONG_T, j6).apply();
    }

    public final void setBillingOneTimePriceNum(long j6) {
        this.prefs.edit().putLong(Constants.PRODUCT_PRICE_NUM, j6).apply();
    }

    public final void setBillingOneTimePriceT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_LIFETIME_V1_T, str).apply();
    }

    public final void setBillingYearFake70(String str) {
        this.prefs.edit().putString("yearr_fake_70", str).apply();
    }

    public final void setBillingYearFake90(String str) {
        this.prefs.edit().putString("yearr_fake_90", str).apply();
    }

    public final void setBillingYearlyFreeTry7DaysPrice(String str) {
        this.prefs.edit().putString("billingYearlyFreeTry7DaysPrice", str).apply();
    }

    public final void setBillingYearlyFreeTryPriceT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_FREE_TRY_YEARLY_PRICE_T, str).apply();
    }

    public final void setBillingYearlyPrice(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_PRICE, str).apply();
    }

    public final void setBillingYearlyPriceDiscount(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_PRICE_DISCOUNT, str).apply();
    }

    public final void setBillingYearlyPriceDiscount10(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_PRICE_DISCOUNT10, str).apply();
    }

    public final void setBillingYearlyPriceDiscount30(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_PRICE_DISCOUNT30, str).apply();
    }

    public final void setBillingYearlyPriceFake(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_PRICE_ORIGIN, str).apply();
    }

    public final void setBillingYearlyPriceFakeT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_PRICE_ORIGIN_T, str).apply();
    }

    public final void setBillingYearlyPriceT(String str) {
        this.prefs.edit().putString(Constants.PRODUCT_YEARLY_V1_T, str).apply();
    }

    public final void setCalendarGuideDialogShow(boolean z6) {
        this.prefs.edit().putBoolean("isCalendarGuideDialogShow", z6).apply();
    }

    public final void setCalendarSelectionTime(long j6) {
        this.prefs.edit().putLong(Constants.CALENDAR_SELECTION_TIME, j6).apply();
    }

    public final void setCancelDayVip(long j6) {
        this.prefs.edit().putLong("cancel_vip", j6).apply();
    }

    public final void setCategoryBugfix(boolean z6) {
        this.prefs.edit().putBoolean("categoryBugfix", z6).apply();
    }

    public final void setCheckSort(boolean z6) {
        this.prefs.edit().putBoolean("checklist_sort", z6).apply();
    }

    public final void setChecklistBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("checklistBtnClick", z6).apply();
    }

    public final void setChoosePicBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("choosePicBtnClick", z6).apply();
    }

    public final void setClipboardContent(String str) {
        this.prefs.edit().putString(Constants.CLIPBOARD_CONTENT, str).apply();
    }

    public final void setClipboardSwitch(boolean z6) {
        this.prefs.edit().putBoolean(Constants.CLIPBOARD_SWITCH, z6).apply();
    }

    public final void setCoherentGuidance(boolean z6) {
        this.prefs.edit().putBoolean("coherentGuidance", z6).apply();
    }

    public final void setCpPermissionStatus(String str) {
        this.prefs.edit().putString("cp_perm_status", str).apply();
    }

    public final void setCpPurchaseStatus(String str) {
        this.prefs.edit().putString("cp_purchase_status", str).apply();
    }

    public final void setCreateNotesNumber(long j6) {
        this.prefs.edit().putLong("create_notes_number", j6).apply();
    }

    public final void setCurrentDiscount(String str) {
        this.prefs.edit().putString(Constants.CURRENT_DISCOUNT, str).apply();
    }

    public final void setCurrentFestival2Discount(String str) {
        this.prefs.edit().putString("school202503discount", str).apply();
    }

    public final void setCurrentFestival3Discount(String str) {
        this.prefs.edit().putString("spring2025discount", str).apply();
    }

    public final void setCurrentFestivalDiscount(String str) {
        this.prefs.edit().putString("freshstart2025discount", str).apply();
    }

    public final void setCurrentFontAppVersion(int i6) {
        this.prefs.edit().putInt("currentFontAppVersion", i6).apply();
    }

    public final void setCurrentFontConfig(String str) {
        this.prefs.edit().putString(Constants.JSON_CONFIG, str).apply();
    }

    public final void setCurrentFontVersion(int i6) {
        this.prefs.edit().putInt(Constants.JSON_VERSION, i6).apply();
    }

    public final void setCustomBgAddGuideNeedShow(boolean z6) {
        this.prefs.edit().putBoolean("customBgAddGuideNeedShow", z6).apply();
    }

    public final void setCustomBgItemGuideNeedShow(boolean z6) {
        this.prefs.edit().putBoolean("customBgItemGuideNeedShow", z6).apply();
    }

    public final void setDailyNotesClick(int i6) {
        this.prefs.edit().putInt("notes_click", i6).apply();
    }

    public final void setDailyPushTime(long j6) {
        this.prefs.edit().putLong(Constants.DAILY_LOCAL, j6).apply();
    }

    public final void setDebugCountry(String str) {
        this.prefs.edit().putString("debugCountry", str).apply();
    }

    public final void setDebugFunctionShow(boolean z6) {
        this.prefs.edit().putBoolean("debugFunctionShow", z6).apply();
    }

    public final void setDeepClick(boolean z6) {
        this.prefs.edit().putBoolean("deep_click", z6).apply();
    }

    public final void setDeepIap(boolean z6) {
        this.prefs.edit().putBoolean("deep_iap", z6).apply();
    }

    public final void setDeepMain(boolean z6) {
        this.prefs.edit().putBoolean("deep_main", z6).apply();
    }

    public final void setDeepSplash(boolean z6) {
        this.prefs.edit().putBoolean("deep_splash", z6).apply();
    }

    public final void setDeepWelcome(boolean z6) {
        this.prefs.edit().putBoolean("deep_welcome", z6).apply();
    }

    public final void setDefaultBackgroundCustomDark(boolean z6) {
        this.prefs.edit().putBoolean("defaultBackgroundCustomDark", z6).apply();
    }

    public final void setDefaultBackgroundFileName(String str) {
        this.prefs.edit().putString("defaultBackgroundFileName", str).apply();
    }

    public final void setDefaultBackgroundId(int i6) {
        this.prefs.edit().putInt("defaultBackgroundId", i6).apply();
    }

    public final void setDefaultBackgroundPage(int i6) {
        this.prefs.edit().putInt("defaultBackgroundPage", i6).apply();
    }

    public final void setDefaultDateIndex(int i6) {
        this.prefs.edit().putInt(Constants.PREF_DATE, i6).apply();
    }

    public final void setDefaultFloatSize(int i6) {
        this.prefs.edit().putInt("defaultFloatSize", i6).apply();
    }

    public final void setDefaultFloatStyle(String str) {
        this.prefs.edit().putString("defaultFloatStyle", str).apply();
    }

    public final void setDefaultLanguageIndex(int i6) {
        this.prefs.edit().putInt("settings_language", i6).apply();
    }

    public final void setDefaultReading(boolean z6) {
        this.prefs.edit().putBoolean("defaultReading", z6).apply();
    }

    public final void setDefaultStartWeek(int i6) {
        this.prefs.edit().putInt("defaultStartWeek", i6).apply();
    }

    public final void setDefaultSttLanguageIndex(int i6) {
        this.prefs.edit().putInt(Constants.STT_LANG, i6).apply();
    }

    public final void setDiscountShow70off(boolean z6) {
        this.prefs.edit().putBoolean("discountShow70off", z6).apply();
    }

    public final void setDrawBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("drawBtnClick", z6).apply();
    }

    public final void setDrawEraserSize(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_ERASER_SIZE, i6).apply();
    }

    public final void setDrawPageBackgroundImgClick(boolean z6) {
        this.prefs.edit().putBoolean("drawPageBackgroundImgClick", z6).apply();
    }

    public final void setDrawPageShowTimes(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_TIMES, i6).apply();
    }

    public final void setDrawPaintDotteColor(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_DOTTE_COLOR, i6).apply();
    }

    public final void setDrawPaintDotteSize(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_DOTTE_SIZE, i6).apply();
    }

    public final void setDrawPaintFountainColor(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_FOUNTAIN_COLOR, i6).apply();
    }

    public final void setDrawPaintFountainPenSize(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_FOUNTAIN_SIZE, i6).apply();
    }

    public final void setDrawPaintPenColor(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_PEN_COLOR, i6).apply();
    }

    public final void setDrawPaintPenSize(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_PEN_SIZE, i6).apply();
    }

    public final void setDrawPaintPencilColor(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_COLOR, i6).apply();
    }

    public final void setDrawPaintPencilSize(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_SIZE, i6).apply();
    }

    public final void setDrawPaintQianPenColor(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_QIAN_COLOR, i6).apply();
    }

    public final void setDrawPaintQianPenSize(int i6) {
        this.prefs.edit().putInt(Constants.DRAW_PAINT_QIAN_SIZE, i6).apply();
    }

    public final void setDrawPencil(int i6) {
        this.prefs.edit().putInt(Constants.PAINT_TYPE, i6).apply();
    }

    public final void setDrawRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.DRAW_FIRST_SHOW, z6).apply();
    }

    public final void setDrawUsed(boolean z6) {
        this.prefs.edit().putBoolean("first_open4", z6).apply();
    }

    public final void setEditBgLayoutShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.EDIT_RECOMMEND_LAYOUT_SHOW, z6).apply();
    }

    public final void setEditDefaultFloatSize(int i6) {
        this.prefs.edit().putInt("editDefaultFloatSize", i6).apply();
    }

    public final void setEditDialogShowTime(long j6) {
        this.prefs.edit().putLong(Constants.EDIT_DIALOG_TIME, j6).apply();
    }

    public final void setEditEmojiLayoutShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.EDIT_EMOJI_GUID_SHOW, z6).apply();
    }

    public final void setEditGuideShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.EDIT_GUIDE_SHOW, z6).apply();
    }

    public final void setEditMoreWidget(boolean z6) {
        this.prefs.edit().putBoolean(Constants.EDIT_MORE_WIDGET_SP, z6).apply();
    }

    public final void setEditOnExitDialogShowTime(long j6) {
        this.prefs.edit().putLong("editOnExitDialogShowTime", j6).apply();
    }

    public final void setEditRedShowTag(int i6) {
        this.prefs.edit().putInt(Constants.EDIT_RED_TAG, i6).apply();
    }

    public final void setEditRedShowTime(long j6) {
        this.prefs.edit().putLong(Constants.EDIT_RED_TIME, j6).apply();
    }

    public final void setEditViewTimes(int i6) {
        this.prefs.edit().putInt(Constants.VIEW_TIMES, i6).apply();
    }

    public final void setEditingToolSorting(String str) {
        this.prefs.edit().putString("editingToolSorting", str).apply();
    }

    public final void setEditingToolSortingUpdateTime(long j6) {
        this.prefs.edit().putLong("editingToolSortingUpdateTime", j6).apply();
    }

    public final void setEmojiBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("emojiBtnClick", z6).apply();
    }

    public final void setEmojiRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.EMOJI_FIRST_SHOW, z6).apply();
    }

    public final void setEmojiUsed(boolean z6) {
        this.prefs.edit().putBoolean("first_open7", z6).apply();
    }

    public final void setEnableAppSwitch(boolean z6) {
        this.prefs.edit().putBoolean("isEnableAppSwitch", z6).apply();
    }

    public final void setEnableCategorySwitch(boolean z6) {
        this.prefs.edit().putBoolean("isEnableCategorySwitch", z6).apply();
    }

    public final void setEnableFingerprintSwitch(boolean z6) {
        this.prefs.edit().putBoolean("isEnableFingerprintSwitch", z6).apply();
    }

    public final void setEnableNoteSwitch(boolean z6) {
        this.prefs.edit().putBoolean("isEnableNoteSwitch", z6).apply();
    }

    public final void setExpiredSubscribe(boolean z6) {
        this.prefs.edit().putBoolean(Constants.EXPIRED_SUBSCRIBE, z6).apply();
    }

    public final void setExpiredType(long j6) {
        this.prefs.edit().putLong("expired_period", j6).apply();
    }

    public final void setFacebookRedFlag(boolean z6) {
        this.prefs.edit().putBoolean("facebookRedFlag", z6).apply();
    }

    public final void setFanShowTimes(int i6) {
        this.prefs.edit().putInt("fan_show_times", i6).apply();
    }

    public final void setFestival2BanClicked(boolean z6) {
        this.prefs.edit().putBoolean("school202503ban_click", z6).apply();
    }

    public final void setFestival2BanStartShowed(long j6) {
        this.prefs.edit().putLong("school202503ban_start_show", j6).apply();
    }

    public final void setFestival2Show(boolean z6) {
        this.prefs.edit().putBoolean("school202503show", z6).apply();
    }

    public final void setFestival2ShowLastDay(boolean z6) {
        this.prefs.edit().putBoolean("school202503show_last_day", z6).apply();
    }

    public final void setFestival3BanClicked(boolean z6) {
        this.prefs.edit().putBoolean("spring2025ban_click", z6).apply();
    }

    public final void setFestival3BanStartShowed(long j6) {
        this.prefs.edit().putLong("spring2025ban_start_show", j6).apply();
    }

    public final void setFestival3Show(boolean z6) {
        this.prefs.edit().putBoolean("spring2025show", z6).apply();
    }

    public final void setFestival3ShowLastDay(boolean z6) {
        this.prefs.edit().putBoolean("spring2025show_last_day", z6).apply();
    }

    public final void setFestivalBanClicked(boolean z6) {
        this.prefs.edit().putBoolean("freshstart2025ban_click", z6).apply();
    }

    public final void setFestivalBanStartShowed(long j6) {
        this.prefs.edit().putLong("freshstart2025ban_start_show", j6).apply();
    }

    public final void setFestivalShow(boolean z6) {
        this.prefs.edit().putBoolean("freshstart2025show", z6).apply();
    }

    public final void setFestivalShowLastDay(boolean z6) {
        this.prefs.edit().putBoolean("freshstart2025show_last_day", z6).apply();
    }

    public final void setFirstAdShow(boolean z6) {
        this.prefs.edit().putBoolean("first_ad_show", z6).apply();
    }

    public final void setFirstEditOk(boolean z6) {
        this.prefs.edit().putBoolean("firstEditOk", z6).apply();
    }

    public final void setFirstEditOkActually(boolean z6) {
        this.prefs.edit().putBoolean("firstEditOkActually", z6).apply();
    }

    public final void setFirstEditShow(boolean z6) {
        this.prefs.edit().putBoolean("firstEditShow", z6).apply();
    }

    public final void setFirstMainShow(boolean z6) {
        this.prefs.edit().putBoolean("firstMainShow", z6).apply();
    }

    public final void setFirstNewOpen(boolean z6) {
        this.prefs.edit().putBoolean("first_open1", z6).apply();
    }

    public final void setFirstNewTime(long j6) {
        this.prefs.edit().putLong("first_time1", j6).apply();
    }

    public final void setFirstOpen(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FIRST_OPEN, z6).apply();
    }

    public final void setFirstOpendWithCate(boolean z6) {
        this.prefs.edit().putBoolean("first_cate_cr", z6).apply();
    }

    public final void setFirstSplashShow(boolean z6) {
        this.prefs.edit().putBoolean("firstSplashShow", z6).apply();
    }

    public final void setFirstSub(long j6) {
        this.prefs.edit().putLong("first_sub", j6).apply();
    }

    public final void setFirstTime(long j6) {
        this.prefs.edit().putLong(Constants.FIRST_OPEN_TIME, j6).apply();
    }

    public final void setFiveDayOff(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FIVE_DAYS_OFF, z6).apply();
    }

    public final void setFontBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("fontBtnClick", z6).apply();
    }

    public final void setFontRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FONT_FIRST_SHOW, z6).apply();
    }

    public final void setFontUsed(boolean z6) {
        this.prefs.edit().putBoolean("first_open40", z6).apply();
    }

    public final void setForceBlackFriday(boolean z6) {
        this.prefs.edit().putBoolean("force_friday", z6).apply();
    }

    public final void setFountainPenBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("fountainPenClick", z6).apply();
    }

    public final void setFoutainPenUsed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FOUNTAIN_USED, z6).apply();
    }

    public final void setFreeTryShowTimes(int i6) {
        this.prefs.edit().putInt("free_try", i6).apply();
    }

    public final void setHasAddCateClicked(boolean z6) {
        this.prefs.edit().putBoolean("has_click_added", z6).apply();
    }

    public final void setHasAutoSaveClick(boolean z6) {
        this.prefs.edit().putBoolean("auto_click", z6).apply();
    }

    public final void setHasBuyed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HAS_BUYED, z6).apply();
    }

    public final void setHasBuyedNeedCheck(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HAS_BUYED_NEED_CHECK, z6).apply();
    }

    public final void setHasClickNewNote(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEW_TAG_NEW_NOTE, z6).apply();
    }

    public final void setHasClickNewNoteDialog(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEW_TAG_NEW_NOTE_DIALOG, z6).apply();
    }

    public final void setHasCreateNewTag(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEW_TAG_AL, z6).apply();
    }

    public final void setHasCreateShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEW_TAG_SHOW, z6).apply();
    }

    public final void setHasHandGuideShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HAND_SHOW, z6).apply();
    }

    public final void setHasInitCate(boolean z6) {
        this.prefs.edit().putBoolean(Constants.ALREADY_INIT_CATE, z6).apply();
    }

    public final void setHasLocalBackuped(boolean z6) {
        this.prefs.edit().putBoolean(Constants.BACKUPED, z6).apply();
    }

    public final void setHasMonthlySubscribe(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HAS_MONTH_SUBSD, z6).apply();
    }

    public final void setHasMoved(boolean z6) {
        this.prefs.edit().putBoolean(Constants.MOVE_LIST, z6).apply();
    }

    public final void setHasNewFontUpdated(boolean z6) {
        this.prefs.edit().putBoolean("new_font_release1", z6).apply();
    }

    public final void setHasNewWidgetClick(boolean z6) {
        this.prefs.edit().putBoolean("new_widget" + this.newWidgetVersion, z6).apply();
    }

    public final void setHasNoDefaultCate(boolean z6) {
        this.prefs.edit().putBoolean("no_default_cates", z6).apply();
    }

    public final void setHasPromoteShowed(boolean z6) {
        this.prefs.edit().putBoolean("auto_banner_showed", z6).apply();
    }

    public final void setHasPulledOff5(boolean z6) {
        this.prefs.edit().putBoolean("seventy_off50", z6).apply();
    }

    public final void setHasPulledOff7(boolean z6) {
        this.prefs.edit().putBoolean("seventy_off", z6).apply();
    }

    public final void setHasReversed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.VIP_REVERSE, z6).apply();
    }

    public final void setHasSubscribe(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HAS_SUBSD, z6).apply();
    }

    public final void setHasSyncClick(boolean z6) {
        this.prefs.edit().putBoolean("sync_click", z6).apply();
    }

    public final void setHasUnlockTheme(String str) {
        this.prefs.edit().putString(Constants.UNLOCKED_THEME, str).apply();
    }

    public final void setHasVipDebug(boolean z6) {
        this.prefs.edit().putBoolean("HAS_VIPDEBUG", z6).apply();
    }

    public final void setHasYearlySubscribe(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HAS_YEAR_SUBSD, z6).apply();
    }

    public final void setHome55VipBanner(boolean z6) {
        this.prefs.edit().putBoolean("home55VipBanner", z6).apply();
    }

    public final void setHome55VipBannerTime(long j6) {
        this.prefs.edit().putLong("home55VipBannerTime", j6).apply();
    }

    public final void setHomeAbNoteExample(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HOME_AB_NOTE_EXAMPLE, z6).apply();
    }

    public final void setHomeAbNoteShowRate(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HOME_AB_NOTE_SHOW_RATE, z6).apply();
    }

    public final void setHomeCloseBannerTime(long j6) {
        this.prefs.edit().putLong("homeCloseBannerTime", j6).apply();
    }

    public final void setHomePageDialogShowTime(long j6) {
        this.prefs.edit().putLong("homePageDialogShowTime", j6).apply();
    }

    public final void setHomeWidgetMgtSinglenoteClick(boolean z6) {
        this.prefs.edit().putBoolean(Constants.HOME_WIDGET_MGT_SINGLENOTE_CLICK, z6).apply();
    }

    public final void setHomepageCalendarViewClick(boolean z6) {
        this.prefs.edit().putBoolean("isHomepageCalendarViewClick", z6).apply();
    }

    public final void setImageUsed(boolean z6) {
        this.prefs.edit().putBoolean("first_open6", z6).apply();
    }

    public final void setInAppReFund(boolean z6) {
        this.prefs.edit().putBoolean("refund_inapp", z6).apply();
    }

    public final void setInstallVersion(int i6) {
        this.prefs.edit().putInt(Constants.INSTALL_VERSION, i6).apply();
    }

    public final void setLastAdReportTime(long j6) {
        this.prefs.edit().putLong("ad_report_time", j6).apply();
    }

    public final void setLastAdShowTime(long j6) {
        this.prefs.edit().putLong(Constants.LAST_AD_SHOW, j6).apply();
    }

    public final void setLastAdmobShowtimes(long j6) {
        this.prefs.edit().putLong("admob_show", j6).apply();
    }

    public final void setLastLifeTimePurchaseTime(long j6) {
        this.prefs.edit().putLong("LAST_LIFETIME_PURCHASE_TIME", j6).apply();
    }

    public final void setLastMedalReached(long j6) {
        this.prefs.edit().putLong("last_medal_reached", j6).apply();
    }

    public final void setLastNotesTimed(long j6) {
        this.prefs.edit().putLong("last_notes_time", j6).apply();
    }

    public final void setLastOpenTime(long j6) {
        this.prefs.edit().putLong(Constants.LAST_OPEN_TIME, j6).apply();
    }

    public final void setLastPushTime(long j6) {
        this.prefs.edit().putLong(Constants.LAST_PUSH_TIME, j6).apply();
    }

    public final void setLastSizeReport(long j6) {
        this.prefs.edit().putLong(Constants.LAST_SIZE_REPOET, j6).apply();
    }

    public final void setLastSyncStartTime(long j6) {
        this.prefs.edit().putLong("lastSyncStartTime", j6).apply();
    }

    public final void setLockClick(boolean z6) {
        this.prefs.edit().putBoolean("lockClick", z6).apply();
    }

    public final void setLockGuideShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.LOCK_GUDE_SHOW, z6).apply();
    }

    public final void setLockRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.LOCK_FIRST_SHOW, z6).apply();
    }

    public final void setMedalL1Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l1_reached", z6).apply();
    }

    public final void setMedalL1Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l1_showed", z6).apply();
    }

    public final void setMedalL2Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l2_reached", z6).apply();
    }

    public final void setMedalL2Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l2_showed", z6).apply();
    }

    public final void setMedalL3Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l3_reached", z6).apply();
    }

    public final void setMedalL3Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l3_showed", z6).apply();
    }

    public final void setMedalL4Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l4_reached", z6).apply();
    }

    public final void setMedalL4Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l4_showed", z6).apply();
    }

    public final void setMedalL5Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l5_reached", z6).apply();
    }

    public final void setMedalL5Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l5_showed", z6).apply();
    }

    public final void setMedalL6Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l6_reached", z6).apply();
    }

    public final void setMedalL6Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l6_showed", z6).apply();
    }

    public final void setMedalL7Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l7_reached", z6).apply();
    }

    public final void setMedalL7Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l7_showed", z6).apply();
    }

    public final void setMedalL8Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l8_reached", z6).apply();
    }

    public final void setMedalL8Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l8_showed", z6).apply();
    }

    public final void setMedalL9Reached(boolean z6) {
        this.prefs.edit().putBoolean("medal_l9_reached", z6).apply();
    }

    public final void setMedalL9Showed(boolean z6) {
        this.prefs.edit().putBoolean("medal_l9_showed", z6).apply();
    }

    public final void setMediaPermissionRationaleRequested(boolean z6) {
        this.prefs.edit().putBoolean("media_permission_rationale_requested", z6).apply();
    }

    public final void setMonthlyFree7DaysExist(boolean z6) {
        this.prefs.edit().putBoolean("monthlyFree7DaysExist", z6).apply();
    }

    public final void setMopubShowTimes(int i6) {
        this.prefs.edit().putInt("mopub_show_times", i6).apply();
    }

    public final void setNeverShowReminder(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEVER_SHOW_REMINDER, z6).apply();
    }

    public final void setNewDrawBgRelea(boolean z6) {
        this.prefs.edit().putBoolean("new_drawbg_release1", z6).apply();
    }

    public final void setNewDrawBgRelea1(boolean z6) {
        this.prefs.edit().putBoolean("new_drawbg_release11", z6).apply();
    }

    public final void setNewDrawPenRelea(boolean z6) {
        this.prefs.edit().putBoolean("new_drawpen_release1", z6).apply();
    }

    public final void setNewDrawPenRelea1(boolean z6) {
        this.prefs.edit().putBoolean("new_drawpen_release11", z6).apply();
    }

    public final void setNewDrawReleasePromoteShow(boolean z6) {
        this.prefs.edit().putBoolean("new_draw_release_version" + this.newDrawReleaseVersion, z6).apply();
    }

    public final void setNewDrawReleasePromoteShowTime(long j6) {
        this.prefs.edit().putLong("new_draw_release_version_time" + this.newDrawReleaseVersion, j6).apply();
    }

    public final void setNewDrawReleaseVersion(int i6) {
        this.newDrawReleaseVersion = i6;
    }

    public final void setNewEmoReleasePromoteShow(boolean z6) {
        this.prefs.edit().putBoolean("new_Emo_release_version" + this.newEmojiReleaseVersion, z6).apply();
    }

    public final void setNewEmoReleasePromoteShowTime(long j6) {
        this.prefs.edit().putLong("new_Emo_release_version_time" + this.newEmojiReleaseVersion, j6).apply();
    }

    public final void setNewEmojiRelea(boolean z6) {
        this.prefs.edit().putBoolean("new_emoji_release1", z6).apply();
    }

    public final void setNewEmojiRelea1(boolean z6) {
        this.prefs.edit().putBoolean("new_emoji_release11", z6).apply();
    }

    public final void setNewEmojiReleaseVersion(int i6) {
        this.newEmojiReleaseVersion = i6;
    }

    public final void setNewFontRelea(boolean z6) {
        this.prefs.edit().putBoolean("new_font_release1", z6).apply();
    }

    public final void setNewFontRelea1(boolean z6) {
        this.prefs.edit().putBoolean("new_font_release11", z6).apply();
    }

    public final void setNewFontUpdatedShowTime(long j6) {
        this.prefs.edit().putLong("new_font_updated_show_time" + this.newFontVersion, j6).apply();
    }

    public final void setNewFontVersion(int i6) {
        this.newFontVersion = i6;
    }

    public final void setNewRealOpen(boolean z6) {
        this.prefs.edit().putBoolean("first_open2", z6).apply();
    }

    public final void setNewReleasesNotificationSwitch(boolean z6) {
        this.prefs.edit().putBoolean("newReleasesNotificationSwitch1", z6).apply();
    }

    public final void setNewUser(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEW_USERS, z6).apply();
    }

    public final void setNewUserClickHomepageCreateNoteButton(int i6) {
        this.prefs.edit().putInt("newUserClickHomepageCreateNoteButton", i6).apply();
    }

    public final void setNewUserShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEWUSER_VIP_SHOW, z6).apply();
    }

    public final void setNewUserVipBanner(boolean z6) {
        this.prefs.edit().putBoolean("newUserVipBanner", z6).apply();
    }

    public final void setNewViphasShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NEW_SHOW, z6).apply();
    }

    public final void setNewWidgetShowTime(long j6) {
        this.prefs.edit().putLong("new_widget_show_time" + this.newWidgetVersion, j6).apply();
    }

    public final void setNewWidgetVersion(int i6) {
        this.newWidgetVersion = i6;
    }

    public final void setNoSetPasswordBuriedPoint(boolean z6) {
        this.prefs.edit().putBoolean("noSetPasswordBuriedPoint", z6).apply();
    }

    public final void setNote20Writing(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NOTE_20_WRITING, z6).apply();
    }

    public final void setNoteBgPreCopyFinished(boolean z6) {
        this.prefs.edit().putBoolean("noteBgPreCopyFinished", z6).apply();
    }

    public final void setNoteNotCreateNotification1(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NOTE_NOT_CREATE_NOTIFICATION_1, z6).apply();
    }

    public final void setNoteNotCreateNotification2(boolean z6) {
        this.prefs.edit().putBoolean(Constants.NOTE_NOT_CREATE_NOTIFICATION_2, z6).apply();
    }

    public final void setNotificationPermissionRationaleRequested(boolean z6) {
        this.prefs.edit().putBoolean("notification_permission_rationale_requested", z6).apply();
    }

    public final void setNotifyPermissionCount(int i6) {
        this.prefs.edit().putInt("getNotifyPermissionCount", i6).apply();
    }

    public final void setOldNotiShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.OLD_BILLING_SHOW, z6).apply();
    }

    public final void setOriginSizeG(boolean z6) {
        this.prefs.edit().putBoolean(Constants.BACKUP_IMAGE_G, z6).apply();
    }

    public final void setOriginSizeL(boolean z6) {
        this.prefs.edit().putBoolean(Constants.BACKUP_IMAGE_L, z6).apply();
    }

    public final void setPaintType(int i6) {
        this.prefs.edit().putInt(Constants.PAINT_TYPE, i6).apply();
    }

    public final void setPatternPassword(String str) {
        this.prefs.edit().putString("savePatternPassword", str).apply();
    }

    public final void setPatternPwdSetOk(boolean z6) {
        this.prefs.edit().putBoolean("patternPwdSetOk", z6).apply();
    }

    public final void setPdfClick(boolean z6) {
        this.prefs.edit().putBoolean("pdfClick", z6).apply();
    }

    public final void setPdfRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.PDF_FIRST_SHOW, z6).apply();
    }

    public final void setPencilBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("pencilBtnClick", z6).apply();
    }

    public final void setPencilPenUsed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.PENCIL_USED, z6).apply();
    }

    public final void setPicRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.PIC_FIRST_SHOW, z6).apply();
    }

    public final void setPictureEditingABTest(int i6) {
        this.prefs.edit().putInt(Constants.PICTURE_EDITING_AB_TEST, i6).apply();
    }

    public final void setPinpwdSetOk(boolean z6) {
        this.prefs.edit().putBoolean(Constants.PWD_SET_OK, z6).apply();
    }

    public final void setPreviewGuideShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.PIC_GUDE_SHOW, z6).apply();
    }

    public final void setPromoteRedPointLockShowTime(long j6) {
        this.prefs.edit().putLong("promoteRedPointLockShowTime", j6).apply();
    }

    public final void setPromoteRedPointPDFShowTime(long j6) {
        this.prefs.edit().putLong("promoteRedPointPDFShowTime", j6).apply();
    }

    public final void setPromoteRedPointPICShowTime(long j6) {
        this.prefs.edit().putLong("promoteRedPointPICShowTime", j6).apply();
    }

    public final void setPromoteRedPointReminderShowTime(long j6) {
        this.prefs.edit().putLong("promoteRedPointReminderShowTime", j6).apply();
    }

    public final void setPurchaseTimeOfCancel(long j6) {
        this.prefs.edit().putLong("purchase_cancel_time", j6).apply();
    }

    public final void setPushOnFriday(int i6) {
        this.prefs.edit().putInt(Constants.PUSH_ON_FRIDAY, i6).apply();
    }

    public final void setPushOnSaturday(int i6) {
        this.prefs.edit().putInt(Constants.PUSH_ON_SATURDAY, i6).apply();
    }

    public final void setPushOnTuesday(int i6) {
        this.prefs.edit().putInt(Constants.PUSH_ON_TUESDAY, i6).apply();
    }

    public final void setPushOnWednesday(int i6) {
        this.prefs.edit().putInt(Constants.PUSH_ON_WEDNESDAY, i6).apply();
    }

    public final void setPwdCode(String str) {
        this.prefs.edit().putString(Constants.PWD_CODE, str).apply();
    }

    public final void setPwdQuestionContent(String str) {
        this.prefs.edit().putString(Constants.PWD_QUESTION_CONTENT, str).apply();
    }

    public final void setPwdQuestionPosition(int i6) {
        this.prefs.edit().putInt(Constants.PWD_QUESTION_POSITION, i6).apply();
    }

    public final void setReadingDialogShowed(boolean z6) {
        this.prefs.edit().putBoolean("reading_dialog_showed", z6).apply();
    }

    public final void setReadingDialogShowed2(boolean z6) {
        this.prefs.edit().putBoolean("reading_dialog_showed2", z6).apply();
    }

    public final void setRecRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.REC_FIRST_SHOW, z6).apply();
    }

    public final void setRecentColors(String str) {
        this.prefs.edit().putString(Constants.RECENT_COLOR, str).apply();
    }

    public final void setRecordBtnClick(boolean z6) {
        this.prefs.edit().putBoolean("recordBtnClick", z6).apply();
    }

    public final void setRecordUsed(boolean z6) {
        this.prefs.edit().putBoolean("first_open41", z6).apply();
    }

    public final void setRememberBgColor(String str) {
        this.prefs.edit().putString(Constants.REMEMBER_BG_COLOR, str).apply();
    }

    public final void setRememberBgColorType(int i6) {
        this.prefs.edit().putInt(Constants.REMEMBER_BG_COLOR_TYPE, i6).apply();
    }

    public final void setRememberBgId(int i6) {
        this.prefs.edit().putInt(Constants.REMEMBER_BG_ID, i6).apply();
    }

    public final void setRememberBgSwitch(boolean z6) {
        this.prefs.edit().putBoolean(Constants.REMEMBER_SWITCH, z6).apply();
    }

    public final void setRemindChannelId(long j6) {
        this.prefs.edit().putLong("remind_id", j6).apply();
    }

    public final void setRemindRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.REMIND_FIRST_SHOW, z6).apply();
    }

    public final void setReminderClick(boolean z6) {
        this.prefs.edit().putBoolean("reminderClick", z6).apply();
    }

    public final void setRingTonNow(String str) {
        this.prefs.edit().putString("rington_name", str).apply();
    }

    public final void setSamsungCaseTipsShowed(boolean z6) {
        this.prefs.edit().putBoolean("samsungCaseTipsShowed", z6).apply();
    }

    public final void setSaveNoteFirst(boolean z6) {
        this.prefs.edit().putBoolean(Constants.SAVE_NOTE_FIRST, z6).apply();
    }

    public final void setSelectColorRoundShow(int i6) {
        this.prefs.edit().putInt(Constants.SELECT_COLOR_POSITION, i6).apply();
    }

    public final void setSelectWidget(int i6) {
        this.prefs.edit().putInt(Constants.SELECT_WIDGET, i6).apply();
    }

    public final void setSeriesNotesNumb(long j6) {
        this.prefs.edit().putLong("series_notes_num", j6).apply();
    }

    public final void setSevenDayOff(boolean z6) {
        this.prefs.edit().putBoolean(Constants.SEVEN_DAYS_OFF, z6).apply();
    }

    public final void setShareRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.SHARE_FIRST_SHOW, z6).apply();
    }

    public final void setShareUsed(boolean z6) {
        this.prefs.edit().putBoolean("first_open8", z6).apply();
    }

    public final void setShowBlackFridayPage(boolean z6) {
        this.prefs.edit().putBoolean("black_page_showed", z6).apply();
    }

    public final void setShowCheckListReminder(int i6) {
        this.prefs.edit().putInt("showCheckListReminder", i6).apply();
    }

    public final void setShowClipBoardNoti(boolean z6) {
        this.prefs.edit().putBoolean("show_clipboard", z6).apply();
    }

    public final void setShowEditBannerAd(boolean z6) {
        this.prefs.edit().putBoolean("showEditBannerAd", z6).apply();
    }

    public final void setShowFaceBookDialog(boolean z6) {
        this.prefs.edit().putBoolean("showFaceBookDialog", z6).apply();
    }

    public final void setShowInterAd(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FIRST_WRITE, z6).apply();
    }

    public final void setShowMedalEnter(boolean z6) {
        this.prefs.edit().putBoolean("show_medal_enter", z6).apply();
    }

    public final void setShowSidebarCalendarRed(boolean z6) {
        this.prefs.edit().putBoolean("isShowSidebarCalendarRed", z6).apply();
    }

    public final void setShowSidebarHelpCenterRed(boolean z6) {
        this.prefs.edit().putBoolean("isShowSidebarHelpCenterRed", z6).apply();
    }

    public final void setShowSyncFAQRed(boolean z6) {
        this.prefs.edit().putBoolean("isShowSyncFAQRed", z6).apply();
    }

    public final void setShowWelcom(boolean z6) {
        this.prefs.edit().putBoolean("show_welcom", z6).apply();
    }

    public final void setShowWidgetUpdateDialog(boolean z6) {
        this.prefs.edit().putBoolean("showWidgetUpdateDialog", z6).apply();
    }

    public final void setShowhowPaperBackground(boolean z6) {
        this.prefs.edit().putBoolean("isShowPaperBackground", z6).apply();
    }

    public final void setSiderMedalRed(boolean z6) {
        this.prefs.edit().putBoolean("sider_medal_red", z6).apply();
    }

    public final void setSixHourDialogShowed(boolean z6) {
        this.prefs.edit().putBoolean("six_hour_dialog_show", z6).apply();
    }

    public final void setSkipedSplash(boolean z6) {
        this.prefs.edit().putBoolean(Constants.splash_SWITCH, z6).apply();
    }

    public final void setSortGridBugfix(boolean z6) {
        this.prefs.edit().putBoolean("sortGridBugfix", z6).apply();
    }

    public final void setStarRecordViewTimes(boolean z6) {
        this.prefs.edit().putBoolean(Constants.START_RECORD_TIMES, z6).apply();
    }

    public final void setSubsActiveList(String str) {
        this.prefs.edit().putString("subs_active_list", str).apply();
    }

    public final void setSummer70OffShowed(boolean z6) {
        this.prefs.edit().putBoolean("summer_70ff", z6).apply();
    }

    public final void setSurveyBannerClicked(boolean z6) {
        this.prefs.edit().putBoolean("surveyBannerClicked", z6).apply();
    }

    public final void setSurveyBannerShowTime(long j6) {
        this.prefs.edit().putLong("surveyBannerShowTime", j6).apply();
    }

    public final void setSyncBannerFirstShowTime(long j6) {
        this.prefs.edit().putLong("sync_show_time", j6).apply();
    }

    public final void setSyncBannerSHowTime(long j6) {
        this.prefs.edit().putLong("syncBannerSHowTime", j6).apply();
    }

    public final void setSyncBannerSerialNoSyncClickCount(int i6) {
        this.prefs.edit().putInt("syncBannerSerialNoSyncClickCount", i6).apply();
    }

    public final void setSyncBannerSerialNoSyncShowCount(int i6) {
        this.prefs.edit().putInt("syncBannerSerialNoSyncShowCount", i6).apply();
    }

    public final void setSyncBannerSerialNoSyncShowTime(long j6) {
        this.prefs.edit().putLong("syncBannerSerialNoSyncShowTime", j6).apply();
    }

    public final void setSyncClick(boolean z6) {
        this.prefs.edit().putBoolean("sync_click1", z6).apply();
    }

    public final void setSyncCreateConfig(boolean z6) {
        this.prefs.edit().putBoolean("sync_create_config1", z6).apply();
    }

    public final void setSyncLastTimeErrorClosed(boolean z6) {
        this.prefs.edit().putBoolean("sync_last_time_error_closed", z6).apply();
    }

    public final void setSyncLastTimeErrorCode(String str) {
        this.prefs.edit().putString("sync_last_time_error_code", str).apply();
    }

    public final void setSyncLoginDriStatus(String str) {
        this.prefs.edit().putString("sync_okk_dri", str).apply();
    }

    public final void setSyncLoginOkWithDri(boolean z6) {
        this.prefs.edit().putBoolean("sync_login_ok_dri1", z6).apply();
    }

    public final void setSyncLoginOkWithoutDri(boolean z6) {
        this.prefs.edit().putBoolean("sync_login_ok_nodri1", z6).apply();
    }

    public final void setSyncLoginPage(boolean z6) {
        this.prefs.edit().putBoolean("sync_login_page1", z6).apply();
    }

    public final void setSyncLoginStatus(String str) {
        this.prefs.edit().putString("sync_okk_status", str).apply();
    }

    public final void setSyncNever(boolean z6) {
        this.prefs.edit().putBoolean("sync_never1", z6).apply();
    }

    public final void setSyncOk(boolean z6) {
        this.prefs.edit().putBoolean("sync_okk2023", z6).apply();
    }

    public final void setSyncShowAccount(boolean z6) {
        this.prefs.edit().putBoolean("sync_account_show1", z6).apply();
    }

    public final void setSyncShowWithNet(boolean z6) {
        this.prefs.edit().putBoolean("sync_with_net1", z6).apply();
    }

    public final void setSyncShowWithoutNet(boolean z6) {
        this.prefs.edit().putBoolean("sync_without_net1", z6).apply();
    }

    public final void setSyncSteps(long j6) {
        this.prefs.edit().putLong("sync_steps", j6).apply();
    }

    public final void setTags(String str) {
        this.prefs.edit().putString(Constants.TAGS_CONTENT, str).apply();
    }

    public final void setThemeRedFirstShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.THEME_FIRST_SHOW, z6).apply();
    }

    public final void setThemeState(int i6) {
        this.prefs.edit().putInt(Constants.THEME_STATE, i6).apply();
    }

    public final void setThemeTestHasRun(boolean z6) {
        this.prefs.edit().putBoolean("theme_test", z6).apply();
    }

    public final void setThreeDayOff(boolean z6) {
        this.prefs.edit().putBoolean(Constants.THREE_DAYS_OFF, z6).apply();
    }

    public final void setTimeBackupLastNotifyTime(long j6) {
        this.prefs.edit().putLong(Constants.TIME_BACKUP_LAST_TIME, j6).apply();
    }

    public final void setTimeBackupNotification(boolean z6) {
        this.prefs.edit().putBoolean(Constants.TIME_BACKUP_NOTIFICATION, z6).apply();
    }

    public final void setTimeBackupTimeNotification(boolean z6) {
        this.prefs.edit().putBoolean(Constants.TIME_BACKUP_TIME_NOTIFICATION, z6).apply();
    }

    public final void setTimeClickRateNow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.TIME_CLICK_RATE_NOW, z6).apply();
    }

    public final void setTimeDarkThemeShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_dark_showed", z6).apply();
    }

    public final void setTimeDarkThemeShowedTime(long j6) {
        this.prefs.edit().putLong("timeDarkThemeShowedTime", j6).apply();
    }

    public final void setTimeEditLockRed(int i6) {
        this.prefs.edit().putInt(Constants.TIME_EDIT_LOCK_RED, i6).apply();
    }

    public final void setTimeEditPDFRed(int i6) {
        this.prefs.edit().putInt(Constants.TIME_EDIT_PDF_RED, i6).apply();
    }

    public final void setTimeEditRemindRed(int i6) {
        this.prefs.edit().putInt(Constants.TIME_EDIT_REMIND_RED, i6).apply();
    }

    public final void setTimeEditShareRed(int i6) {
        this.prefs.edit().putInt(Constants.TIME_EDIT_SHARE_RED, i6).apply();
    }

    public final void setTimeFamilyAppRed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.TIME_FAMILY_APP_RED, z6).apply();
    }

    public final void setTimeHomeBackupDialog(boolean z6) {
        this.prefs.edit().putBoolean(Constants.TIME_HOME_BACKUP_DIALOG, z6).apply();
    }

    public final void setTimeHomeBackupRed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.TIME_HOME_BACKUP_Red, z6).apply();
    }

    public final void setTimeHomeLockDialog(int i6) {
        this.prefs.edit().putInt(Constants.TIME_HOME_LOCK_DIALOG, i6).apply();
    }

    public final void setTimeHomeVipDialogShowTime(long j6) {
        this.prefs.edit().putLong("timeHomeVipDialogShowTime", j6).apply();
    }

    public final void setTimeLandThemeShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_land_showed", z6).apply();
    }

    public final void setTimeLandThemeShowedTime(long j6) {
        this.prefs.edit().putLong("timeLandThemeShowedTime", j6).apply();
    }

    public final void setTimeNotificationPermissionDialog(boolean z6) {
        this.prefs.edit().putBoolean("time_notification_permission_dialog", z6).apply();
    }

    public final void setTimeOfEnterVip(long j6) {
        this.prefs.edit().putLong(Constants.TIME_ENTER_VIP, j6).apply();
    }

    public final void setTimePaperThemeShowedTime(long j6) {
        this.prefs.edit().putLong("timePaThemeShowedTime", j6).apply();
    }

    public final void setTimeRateUsDialogShowNum(long j6) {
        this.prefs.edit().putLong(Constants.TIME_SECOND_RATE_DIALOG_NUM, j6).apply();
    }

    public final void setTimeRateUsDialogShowTime(long j6) {
        this.prefs.edit().putLong(Constants.TIME_SECOND_RATE_DIALOG_TIME, j6).apply();
    }

    public final void setTimeSchduleFive(long j6) {
        this.prefs.edit().putLong("time_vip_after9", j6).apply();
    }

    public final void setTimeSchduleNineDisShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_discount_showed", z6).apply();
    }

    public final void setTimeSchduleOne(long j6) {
        this.prefs.edit().putLong("time_vip_1", j6).apply();
    }

    public final void setTimeSchduleOneShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_vip_1_showed", z6).apply();
    }

    public final void setTimeSchduleThreeShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_vip_3_showed", z6).apply();
    }

    public final void setTimeSchduleTwo(long j6) {
        this.prefs.edit().putLong("time_vip_2", j6).apply();
    }

    public final void setTimeSchduleTwo2(long j6) {
        this.prefs.edit().putLong("time_vip_02", j6).apply();
    }

    public final void setTimeSchduleTwo3(long j6) {
        this.prefs.edit().putLong("time_vip_03", j6).apply();
    }

    public final void setTimeSchduleTwoShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_vip_2_showed", z6).apply();
    }

    public final void setTimeSchollThemeShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_school_showed", z6).apply();
    }

    public final void setTimeSchoolThemeShowedTime(long j6) {
        this.prefs.edit().putLong("timeSchoolThemeShowedTime", j6).apply();
    }

    public final void setTimeSecondRateDialogShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.TIME_SECOND_RATE_DIALOG, z6).apply();
    }

    public final void setTimeShopThemeShowed(boolean z6) {
        this.prefs.edit().putBoolean("time_shop_showed", z6).apply();
    }

    public final void setTimeShopThemeShowedTime(long j6) {
        this.prefs.edit().putLong("timeShopThemeShowedTime", j6).apply();
    }

    public final void setUpDataVersionNumber(long j6) {
        this.prefs.edit().putLong("upDataVersionNumber", j6).apply();
    }

    public final void setUsaNewReleaseShowed(boolean z6) {
        this.prefs.edit().putBoolean("new_release_version_us", z6).apply();
    }

    public final void setUserLastModifiedBugfix(boolean z6) {
        this.prefs.edit().putBoolean("userLastModifiedBugfix2", z6).apply();
    }

    public final void setUserPurchaseFrom(String str) {
        this.prefs.edit().putString("user_purchase_from", str).apply();
    }

    public final void setUserPurchaseTime(long j6) {
        this.prefs.edit().putLong("user_purchase_time", j6).apply();
    }

    public final void setVipFirstOldCountDown(long j6) {
        this.prefs.edit().putLong(Constants.FIRST_OLD_COUNT_DOWN, j6).apply();
    }

    public final void setVipFirstOldEnter(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FIRST_OLD_ENTER, z6).apply();
    }

    public final void setVipFirstOldEnter11(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FIRST_OLD_ENTER11, z6).apply();
    }

    public final void setVipFirstOldTimeLine(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FIRST_OLD_ENTER_TIMELINE, z6).apply();
    }

    public final void setVipFirstOldTimeLineSecond(boolean z6) {
        this.prefs.edit().putBoolean(Constants.FIRST_OLD_ENTER_TIMELINE_TWO, z6).apply();
    }

    public final void setVipHasShowed(boolean z6) {
        this.prefs.edit().putBoolean(Constants.VIP_SHOWED, z6).apply();
    }

    public final void setVipId(String str) {
        this.prefs.edit().putString(Constants.VIP_IDS, str).apply();
    }

    public final void setVipNewUserPageStartTime(long j6) {
        this.prefs.edit().putLong("vip_new_user_start_time", j6).apply();
    }

    public final void setVipOldUserPageStartTime(long j6) {
        this.prefs.edit().putLong("vip_old_user_start_time", j6).apply();
    }

    public final void setVipPageEndTime(long j6) {
        this.prefs.edit().putLong("vip_page_end_time", j6).apply();
    }

    public final void setVipPageShowTimes(int i6) {
        this.prefs.edit().putInt(Constants.VIP_PAGE_SHOW, i6).apply();
    }

    public final void setVipShowTimes(int i6) {
        this.prefs.edit().putInt(Constants.VIP_SHOW_TIMES, i6).apply();
    }

    public final void setVipStateDebug(int i6) {
        this.prefs.edit().putInt("vipStateDebug", i6).apply();
    }

    public final void setVipUserCancelFreeTrialMonthlyCount(int i6) {
        this.prefs.edit().putInt("vipUserCancelFreeTrialMonthlyCount", i6).apply();
    }

    public final void setVipUserCancelFreeTrialStartTime(long j6) {
        this.prefs.edit().putLong("vipUserCancelFreeTrialStartTime", j6).apply();
    }

    public final void setVipUserCancelFreeTrialYearlyCount(int i6) {
        this.prefs.edit().putInt("vipUserCancelFreeTrialYearlyCount", i6).apply();
    }

    public final void setVipWelcomePageContinueClick(int i6) {
        this.prefs.edit().putInt("vipWelcomePageContinueClick", i6).apply();
    }

    public final void setVipWelcomePagePurchaseTime(long j6) {
        this.prefs.edit().putLong("vipWelcomePagePurchaseTime", j6).apply();
    }

    public final void setVipWelcomePageSkuClick(int i6) {
        this.prefs.edit().putInt("vipWelcomePageSkuClick", i6).apply();
    }

    public final void setVoteMindNotesClicked(boolean z6) {
        this.prefs.edit().putBoolean("voteMindNotesClicked1", z6).apply();
    }

    public final void setVoteMindNotesCount(int i6) {
        this.prefs.edit().putInt("voteMindNotesCount1", i6).apply();
    }

    public final void setVoteMindNotesShowTime(long j6) {
        this.prefs.edit().putLong("voteMindNotesShowTime1", j6).apply();
    }

    public final void setWelcomShowtime(long j6) {
        this.prefs.edit().putLong("welcom_show", j6).apply();
    }

    public final void setWhatsNewPromoteShow(boolean z6) {
        this.prefs.edit().putBoolean("whats_new_version" + this.whatsNewVersion, z6).apply();
    }

    public final void setWhatsNewVersion(int i6) {
        this.whatsNewVersion = i6;
    }

    public final void setWidgetBannerSHowTime(long j6) {
        this.prefs.edit().putLong("widgetBannerSHowTime", j6).apply();
    }

    public final void setWidgetClick(boolean z6) {
        this.prefs.edit().putBoolean("widgetClick", z6).apply();
    }

    public final void setWidgetDialogShow(boolean z6) {
        this.prefs.edit().putBoolean(Constants.WIDGET_DIALOG_SHOW, z6).apply();
    }

    public final void setWidgetNotifySwitch(boolean z6) {
        this.prefs.edit().putBoolean("widget_notify_switch1", z6).apply();
    }

    public final void setWidgetPromoteSinglenoteAdd(boolean z6) {
        this.prefs.edit().putBoolean(Constants.WIDGET_PROMOTE_SINGLENOTE_ADD, z6).apply();
    }

    public final void setWidgetSiderBar(boolean z6) {
        this.prefs.edit().putBoolean(Constants.WIDGET_SIDEBAR, z6).apply();
    }

    public final void setWidgetUsage(String str) {
        this.prefs.edit().putString("widget_usage", str).apply();
    }

    public final void setYearlyFree7DaysExist(boolean z6) {
        this.prefs.edit().putBoolean("yearlyFree7DaysExist", z6).apply();
    }

    public final void setYearlyPriceDiscount10Num(long j6) {
        this.prefs.edit().putLong("symble_umn", j6).apply();
    }

    public final void setYearlyPriceDiscount10Symble(String str) {
        this.prefs.edit().putString("symble_mon", str).apply();
    }

    public final void setYearlyPriceDiscount30Num(long j6) {
        this.prefs.edit().putLong("symble_umn_30", j6).apply();
    }

    public final void setYearlyPriceDiscount30Symble(String str) {
        this.prefs.edit().putString("symble_mon_30", str).apply();
    }

    public final void setYearlyPriceDiscountNum(long j6) {
        this.prefs.edit().putLong("symble_umn_50", j6).apply();
    }

    public final void setYearlyToMonthCoin(String str) {
        this.prefs.edit().putString("symble_mon_unit", str).apply();
    }

    public static /* synthetic */ void getAutoSyncSwitch$annotations() {
    }
}
