package org.vse.zaimy.online.pojo.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Card {
    @SerializedName("cards_credit")
    @Expose
    private List<Proposition> cardsCredit = null;
    @SerializedName("cards_debit")
    @Expose
    private List<Proposition> cardsDebit = null;
    @SerializedName("cards_installment")
    @Expose
    private List<Proposition> cardsInstallment = null;

    public List<Proposition> getCardsCredit() {
        return cardsCredit;
    }

    public void setCardsCredit(List<Proposition> cardsCredit) {
        this.cardsCredit = cardsCredit;
    }

    public List<Proposition> getCardsDebit() {
        return cardsDebit;
    }

    public void setCardsDebit(List<Proposition> cardsDebit) {
        this.cardsDebit = cardsDebit;
    }

    public List<Proposition> getCardsInstallment() {
        return cardsInstallment;
    }

    public void setCardsInstallment(List<Proposition> cardsInstallment) {
        this.cardsInstallment = cardsInstallment;
    }
}
