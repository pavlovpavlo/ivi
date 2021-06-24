package org.vse.zaimy.online.pojo.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppConfig {
    @SerializedName("privacy_policy_html")
    @Expose
    private String privacyPolicyHtml;
    @SerializedName("user_term_html")
    @Expose
    private String userTermHtml;
    @SerializedName("show_news_item")
    @Expose
    private String showNewsItem;
    @SerializedName("show_calculator_item")
    @Expose
    private String showCalculatorItem;
    @SerializedName("show_chat")
    @Expose
    private String showChat;
    @SerializedName("show_phone_authentication")
    @Expose
    private String showPhoneAuthentication;
    @SerializedName("extra_field_0")
    @Expose
    private String extraField0;
    @SerializedName("extra_field_1")
    @Expose
    private String extraField1;
    @SerializedName("extra_field_2")
    @Expose
    private String extraField2;
    @SerializedName("extra_field_3")
    @Expose
    private String extraField3;
    @SerializedName("extra_field_4")
    @Expose
    private String extraField4;
    @SerializedName("extra_field_5")
    @Expose
    private String extraField5;
    @SerializedName("extra_field_6")
    @Expose
    private String extraField6;
    @SerializedName("extra_field_7")
    @Expose
    private String extraField7;

    public String getPrivacyPolicyHtml() {
        return privacyPolicyHtml;
    }

    public void setPrivacyPolicyHtml(String privacyPolicyHtml) {
        this.privacyPolicyHtml = privacyPolicyHtml;
    }

    public String getUserTermHtml() {
        return userTermHtml;
    }

    public void setUserTermHtml(String userTermHtml) {
        this.userTermHtml = userTermHtml;
    }

    public String getShowNewsItem() {
        return showNewsItem;
    }

    public void setShowNewsItem(String showNewsItem) {
        this.showNewsItem = showNewsItem;
    }

    public String getShowCalculatorItem() {
        return showCalculatorItem;
    }

    public void setShowCalculatorItem(String showCalculatorItem) {
        this.showCalculatorItem = showCalculatorItem;
    }

    public String getShowChat() {
        return showChat;
    }

    public void setShowChat(String showChat) {
        this.showChat = showChat;
    }

    public String getShowPhoneAuthentication() {
        return showPhoneAuthentication;
    }

    public void setShowPhoneAuthentication(String showPhoneAuthentication) {
        this.showPhoneAuthentication = showPhoneAuthentication;
    }

    public String getExtraField0() {
        return extraField0;
    }

    public void setExtraField0(String extraField0) {
        this.extraField0 = extraField0;
    }

    public String getExtraField1() {
        return extraField1;
    }

    public void setExtraField1(String extraField1) {
        this.extraField1 = extraField1;
    }

    public String getExtraField2() {
        return extraField2;
    }

    public void setExtraField2(String extraField2) {
        this.extraField2 = extraField2;
    }

    public String getExtraField3() {
        return extraField3;
    }

    public void setExtraField3(String extraField3) {
        this.extraField3 = extraField3;
    }

    public String getExtraField4() {
        return extraField4;
    }

    public void setExtraField4(String extraField4) {
        this.extraField4 = extraField4;
    }

    public String getExtraField5() {
        return extraField5;
    }

    public void setExtraField5(String extraField5) {
        this.extraField5 = extraField5;
    }

    public String getExtraField6() {
        return extraField6;
    }

    public void setExtraField6(String extraField6) {
        this.extraField6 = extraField6;
    }

    public String getExtraField7() {
        return extraField7;
    }

    public void setExtraField7(String extraField7) {
        this.extraField7 = extraField7;
    }
}
