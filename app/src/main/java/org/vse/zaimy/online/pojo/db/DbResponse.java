package org.vse.zaimy.online.pojo.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DbResponse {
    @SerializedName("app_config")
    @Expose
    private AppConfig appConfig;
    @SerializedName("loans")
    @Expose
    private List<Proposition> loans = null;
    @SerializedName("cards")
    @Expose
    private List<Card> cards = null;
    @SerializedName("credits")
    @Expose
    private List<Proposition> credits = null;

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public List<Proposition> getLoans() {
        return loans;
    }

    public void setLoans(List<Proposition> loans) {
        this.loans = loans;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Proposition> getCredits() {
        return credits;
    }

    public void setCredits(List<Proposition> credits) {
        this.credits = credits;
    }
}
