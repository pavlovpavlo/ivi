package org.vse.zaimy.online.pojo.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Proposition implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("orderButtonText")
    @Expose
    private String orderButtonText;
    @SerializedName("percent")
    @Expose
    private String percent;
    @SerializedName("percentPostfix")
    @Expose
    private String percentPostfix;
    @SerializedName("percentPrefix")
    @Expose
    private String percentPrefix;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("summPrefix")
    @Expose
    private String summPrefix;
    @SerializedName("summMin")
    @Expose
    private String summMin;
    @SerializedName("summMid")
    @Expose
    private String summMid;
    @SerializedName("summMax")
    @Expose
    private String summMax;
    @SerializedName("summPostfix")
    @Expose
    private String summPostfix;
    @SerializedName("show_mastercard")
    @Expose
    private String showMastercard;
    @SerializedName("show_mir")
    @Expose
    private String showMir;
    @SerializedName("show_visa")
    @Expose
    private String showVisa;
    @SerializedName("show_qiwi")
    @Expose
    private String showQiwi;
    @SerializedName("show_yandex")
    @Expose
    private String showYandex;
    @SerializedName("show_cash")
    @Expose
    private String showCash;
    @SerializedName("hide_TermFields")
    @Expose
    private String hideTermFields;
    @SerializedName("hide_PercentFields")
    @Expose
    private String hidePercentFields;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("screen")
    @Expose
    private String screen;
    @SerializedName("position")
    @Expose
    private Integer position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderButtonText() {
        return orderButtonText;
    }

    public void setOrderButtonText(String orderButtonText) {
        this.orderButtonText = orderButtonText;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getPercentPostfix() {
        return percentPostfix;
    }

    public void setPercentPostfix(String percentPostfix) {
        this.percentPostfix = percentPostfix;
    }

    public String getPercentPrefix() {
        return percentPrefix;
    }

    public void setPercentPrefix(String percentPrefix) {
        this.percentPrefix = percentPrefix;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSummPrefix() {
        return summPrefix;
    }

    public void setSummPrefix(String summPrefix) {
        this.summPrefix = summPrefix;
    }

    public String getSummMin() {
        return summMin;
    }

    public void setSummMin(String summMin) {
        this.summMin = summMin;
    }

    public String getSummMid() {
        return summMid;
    }

    public void setSummMid(String summMid) {
        this.summMid = summMid;
    }

    public String getSummMax() {
        return summMax;
    }

    public void setSummMax(String summMax) {
        this.summMax = summMax;
    }

    public String getSummPostfix() {
        return summPostfix;
    }

    public void setSummPostfix(String summPostfix) {
        this.summPostfix = summPostfix;
    }

    public String getShowMastercard() {
        return showMastercard;
    }

    public void setShowMastercard(String showMastercard) {
        this.showMastercard = showMastercard;
    }

    public String getShowMir() {
        return showMir;
    }

    public void setShowMir(String showMir) {
        this.showMir = showMir;
    }

    public String getShowVisa() {
        return showVisa;
    }

    public void setShowVisa(String showVisa) {
        this.showVisa = showVisa;
    }

    public String getShowQiwi() {
        return showQiwi;
    }

    public void setShowQiwi(String showQiwi) {
        this.showQiwi = showQiwi;
    }

    public String getShowYandex() {
        return showYandex;
    }

    public void setShowYandex(String showYandex) {
        this.showYandex = showYandex;
    }

    public String getShowCash() {
        return showCash;
    }

    public void setShowCash(String showCash) {
        this.showCash = showCash;
    }

    public String getHideTermFields() {
        return hideTermFields;
    }

    public void setHideTermFields(String hideTermFields) {
        this.hideTermFields = hideTermFields;
    }

    public String getHidePercentFields() {
        return hidePercentFields;
    }

    public void setHidePercentFields(String hidePercentFields) {
        this.hidePercentFields = hidePercentFields;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
