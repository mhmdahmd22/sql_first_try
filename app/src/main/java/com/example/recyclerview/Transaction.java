package com.example.recyclerview;

public class Transaction {
    private String transDate;
    private String transTime;
    private String pan;
    private String aplAr;
    private String amountAr;

    public Transaction(String transDate, String transTime, String pan, String aplAr, String amountAr) {
        this.transDate = transDate;
        this.transTime = transTime;
        this.pan = pan;
        this.aplAr = aplAr;
        this.amountAr = amountAr;
    }

    public String getTransDate() { return transDate; }
    public String getTransTime() { return transTime; }
    public String getPan() { return pan; }
    public String getAplAr() { return aplAr; }
    public String getAmountAr() { return amountAr; }
}
