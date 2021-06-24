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
}
