package uz.pdp.entity;

import java.util.ArrayList;

public interface CardService {

    void showMyCards(ArrayList<Card> arrayList);

    void showBalance(ArrayList<Card> arrayList);

    String transfer(String pan, ArrayList<Card> arrayList, ArrayList<Card> list);

    void showPaymentHistory(ArrayList<History> arrayList);

    void showClickWallet();

    void showSettings();

    void showMenu();

    double checkAndExecute(double balance, double transfer);

    void displayCards(ArrayList<Card> arrayList);

    void removeCard(ArrayList<Card> arrayList, int index);
    double addFundToCard(double balance, double transfer);
}
