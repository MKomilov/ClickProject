package uz.pdp.entity;

public class ClickWallet {
    private double wallet;

    public ClickWallet() {
    }

    public ClickWallet(double wallet) {
        this.wallet = wallet;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "Click wallet: " + wallet;
    }
}
