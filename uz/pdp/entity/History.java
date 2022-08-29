package uz.pdp.entity;

import java.util.Date;

public class History {
    private double transferAmount;
    private String receiverPan;
    private String senderPan;
    private String receiverName;
    private Date date = new Date();

    public History() {
    }

    public History(double transferAmount, String receiverPan, String senderPan) {
        this.transferAmount = transferAmount;
        this.receiverPan = receiverPan;
        this.senderPan = senderPan;
    }

    public History(double transferAmount, String receiverPan, String senderPan, String receiverName) {
        this.transferAmount = transferAmount;
        this.receiverPan = receiverPan;
        this.senderPan = senderPan;
        this.receiverName = receiverName;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getReceiverPan() {
        return receiverPan;
    }

    public void setReceiverPan(String receiverPan) {
        this.receiverPan = receiverPan;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "\n====================================================" +
                "\nTransferAmount =    " + transferAmount +
                "\nReceiverPan    =    " + receiverPan.substring(0, 4) + "******" + receiverPan.substring(12) +
                "\nSenderPan      =    " + senderPan.substring(0, 4) + "******" +senderPan.substring(12) +
                "\nReceiverName   =    " + receiverName +
                "\nDate           =    " + date +
                "\n===================================================";
    }
}
