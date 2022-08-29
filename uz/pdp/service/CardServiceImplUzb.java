package uz.pdp.service;

import uz.pdp.entity.Card;
import uz.pdp.entity.CardService;
import uz.pdp.entity.History;

import java.util.ArrayList;

public class CardServiceImplUzb implements CardService {


    @Override
    public void showMyCards(ArrayList<Card> arrayList) {
        if (arrayList.size() == 0) {
            System.out.println("\uD83D\uDEAB Sizda qo'shilgan kartalar yo'q");
        } else {
            arrayList.forEach(System.out::println);
        }
    }

    @Override
    public void showBalance(ArrayList<Card> arrayList) {
        if (arrayList.size() == 0) {
            System.out.println("\uD83D\uDEAB Sizda qo'shilgan kartalar yo'q");
        } else {
            System.out.println("\uD83D\uDC41\u200D\uD83D\uDDE8 Qaysi karta balansini ko'rmoqchisiz? ");
            displayCards(arrayList);
        }
    }

    @Override
    public String transfer(String pan, ArrayList<Card> arrayList, ArrayList<Card> list) {
        ArrayList<Card> cards = new ArrayList<>(arrayList);
        cards.addAll(list);
        for (Card card : cards) {
            if (card.getCardNum().equals(pan)) {
                return card.getCardHolderName();
            }
        }
        return null;
    }

    @Override
    public void showPaymentHistory(ArrayList<History> arrayList) {
        if (arrayList.size() == 0) {
            System.out.println("\uD83D\uDCCB Amaliyotlar tarixi bo'sh");
        } else {
            arrayList.forEach(System.out::println);
        }
    }

    @Override
    public void showClickWallet() {
    }

    @Override
    public void showSettings() {

    }

    @Override
    public void showMenu() {
        System.out.println("0. \uD83D\uDD19 Chiqish\n1. \uD83D\uDCB3 Kartalarim\n2. \uD83D\uDCB2 Balans\n3. ➡ O'tkazmalar\n4. ↔ To'lovlar tarixi\n5. \uD83D\uDED2 Click Wallet\n6. ⚙ Sozlamalar");
    }

    @Override
    public double checkAndExecute(double balance, double transfer) {
        if (balance >= 1.01 * transfer) {
            balance -= 1.01 * transfer;
            System.out.println("✅ O'tkazma muvaffaqqiyatli amalga oshdi");
        } else {
            System.out.println("❌ Kartada mablag' yetarli emas");
        }
        return balance;
    }

    @Override
    public void displayCards(ArrayList<Card> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println((i + 1) + ". " + arrayList.get(i).getCardNum());
        }
    }

    @Override
    public void removeCard(ArrayList<Card> arrayList, int index) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (index > i){
                System.out.println((i + 1) + ". " + arrayList.get(i).getCardNum());
            } else if (index < i) {
                System.out.println(i + ". " + arrayList.get(i).getCardNum());
            }
        }
    }

    @Override
    public double addFundToCard(double balance, double transfer) {
        return balance + transfer;
    }
}
