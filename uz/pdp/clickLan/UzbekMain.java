package uz.pdp.clickLan;

import uz.pdp.entity.Card;
import uz.pdp.entity.CardTypes;
import uz.pdp.entity.ClickWallet;
import uz.pdp.entity.History;
import uz.pdp.service.CardServiceImplUzb;
import uz.pdp.utility.Main;
import java.util.Objects;
import java.util.Scanner;

public class UzbekMain {
    public static Scanner scanner = new Scanner(System.in);
    public static Scanner scannerText = new Scanner(System.in);

    public void main() {
        CardServiceImplUzb cardService = new CardServiceImplUzb();
        ClickWallet wallet = new ClickWallet(0);
        RussianMain russianMain = new RussianMain();
        EnglishMain englishMain = new EnglishMain();
        do {
            System.out.print("\uD83D\uDCF1 Telefon raqamingizni kiriting: ");
        } while (!scannerText.nextLine().matches("^[+](998)\\d{9}$"));
        while (true) {
            int random_int = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
            System.out.println(random_int);
            System.out.println("\uD83D\uDCF2 Tasdiqlash kodini kiriting: ");
            if (random_int == scanner.nextInt()) {

                while (true){
                    cardService.showMenu();
                    Card card = new Card();
                    loop:
                    switch (scanner.nextInt()){
                        case 0 -> {return;}
                        case 1 -> {
                            cardService.showMyCards(Main.listOfRegularCards);
                            System.out.println("0. \uD83D\uDD19 Orqaga qaytish\n1. \uD83D\uDCB3 Karta qo'shish");
                            switch (scanner.nextInt()){
                                case 0 -> {break loop;}
                                case 1 -> {
                                    String num;
                                    do {
                                        System.out.print("Karta raqamini kiriting: ");
                                        num = scannerText.nextLine();
                                        card.setCardNum(num);
                                        if (Objects.equals(num.substring(0, 4), "9860")){
                                            card.setCardType(CardTypes.HUMO);
                                        }else if (Objects.equals(num.substring(0, 4), "8600")){
                                            card.setCardType(CardTypes.UZCARD);
                                        }
                                    }while (!num.matches("^(8600|9860)(\\d{12})"));
                                    String exp;
                                    do {
                                        System.out.print("Karta amal qilish muddatini kiriting(mm/yy): ");
                                        exp = scannerText.nextLine();
                                    }while (!exp.matches("^(0[1-9]|1[0-2])/(\\d{2})$"));
                                    card.setExpDate(exp);
                                    Main.listOfRegularCards.add(card);
                                    System.out.println("✅ Karta muvafaqqiyatli qo'shildi");
                                }
                            }
                        }
                        case 2 -> {
                            System.out.println("0. \uD83D\uDD19 Orqaga qaytish");
                            cardService.showBalance(Main.listOfRegularCards);
                            int option = scanner.nextInt();
                            if (option == 0){
                                break loop;
                            }else if (option > Main.listOfRegularCards.size()){
                                System.out.println("❌ Xato buyruq kiritildi");
                            }else {
                                System.out.println("\uD83D\uDCB2 Sizning kartangizda " + Main.listOfRegularCards.get(option-1).getInitialBalance() + " so'm pul bor");
                            }
                        }
                        case 3 -> {
                            if (Main.listOfRegularCards.size()==0){
                                System.out.println("\uD83D\uDEAB Sizda qo'shilgan kartalar yo'q");
                            }else if (Main.listOfRegularCards.size()==1){
                                System.out.print("\uD83D\uDCB3 Qabul qiluvchining karta raqamini kiriting: ");
                                String receiver = scannerText.nextLine();
                                String response = cardService.transfer(receiver, Main.listOfReceivers, Main.listOfFCards);
                                if (response == null) {
                                    System.out.println("❌ Karta topilmadi");
                                    break;
                                }
                                System.out.println("\uD83D\uDC64 Qabul qiuvchi: " + response);
                                System.out.print("\uD83D\uDCB0 Jo'natmoqchi bo'lgan summangizni kiriting: ");
                                double transfer = scanner.nextDouble();
                                double balance = cardService.checkAndExecute(Main.listOfRegularCards.get(0).getInitialBalance(), transfer);
                                Main.listOfRegularCards.get(0).setInitialBalance(balance);
                                History history = new History(transfer, receiver, Main.listOfRegularCards.get(0).getCardNum(), response);
                                Main.cardHistories.add(history);
                                wallet.setWallet(wallet.getWallet()+transfer*0.001);
                            }else {
                                System.out.println("0. \uD83D\uDD19 Orqaga qaytish\n1. \uD83D\uDD1B O'z kartalarim orasida\n2. ➡ Boshqa kartaga o'tkazish");
                                switch (scanner.nextInt()){
                                    case 0 -> {break loop;}
                                    case 1 -> {
                                        System.out.println("\uD83D\uDCB3 Qabul qiluvchi kartangizni tanlang: ");
                                        cardService.displayCards(Main.listOfRegularCards);
                                        int receive = scanner.nextInt()-1;
                                        System.out.println("\uD83D\uDCB3 O'tkazma uchun karta tanlang: ");
                                        cardService.removeCard(Main.listOfRegularCards, receive);
                                        int op;
                                        if (Main.listOfRegularCards.size()<= receive+1){
                                            op = scanner.nextInt()-1;
                                        }else {
                                            op = scanner.nextInt();
                                        }
                                        System.out.print("\uD83D\uDCB0 Jo'natmoqchi bo'lgan summangizni kiriting: ");
                                        double transfer = scanner.nextDouble();
                                        double balance = cardService.checkAndExecute(Main.listOfRegularCards.get(op).getInitialBalance(), transfer);
                                        Main.listOfRegularCards.get(op).setInitialBalance(balance);
                                        double sum = cardService.addFundToCard(Main.listOfRegularCards.get(receive).getInitialBalance(), transfer);
                                        Main.listOfRegularCards.get(receive).setInitialBalance(sum);
                                        History history = new History(transfer, Main.listOfRegularCards.get(receive).getCardNum(), Main.listOfRegularCards.get(op).getCardNum());
                                        Main.cardHistories.add(history);
                                        wallet.setWallet(wallet.getWallet()+transfer*0.001);
                                    }
                                    case 2 -> {
                                        System.out.print("\uD83D\uDCB3 Qabul qiluvchining karta raqamini kiriting: ");
                                        String receiver = scannerText.nextLine();
                                        String response = cardService.transfer(receiver, Main.listOfReceivers, Main.listOfFCards);
                                        if (response == null) {
                                            System.out.println("❌ Karta topilmadi");
                                            break;
                                        }
                                        System.out.println("\uD83D\uDC64 Qabul qiuvchi: " + response);
                                        cardService.displayCards(Main.listOfRegularCards);
                                        System.out.print("\uD83D\uDCB3 O'tkazma uchun karta tanlang: ");
                                        int op = scanner.nextInt()-1;
                                        System.out.print("\uD83D\uDCB0 Jo'natmoqchi bo'lgan summangizni kiriting: ");
                                        double transfer = scanner.nextDouble();
                                        double balance = cardService.checkAndExecute(Main.listOfRegularCards.get(op).getInitialBalance(), transfer);
                                        Main.listOfRegularCards.get(op).setInitialBalance(balance);
                                        History history = new History(transfer, receiver, Main.listOfRegularCards.get(op).getCardNum(), response);
                                        Main.cardHistories.add(history);
                                        wallet.setWallet(wallet.getWallet()+transfer*0.001);
                                    }
                                }
                            }
                        }
                        case 4 -> cardService.showPaymentHistory(Main.cardHistories);
                        case 5 -> System.out.println(wallet);
                        case 6 -> {
                            System.out.println("0. \uD83D\uDD19 Orqaga qaytish\n1. \uD83D\uDCB3 Kartani o'chirish\n2. \uD83C\uDF10 Tilni o'zgartirish");
                            switch (scanner.nextInt()){
                                case 0 -> {break loop;}
                                case 1 -> {
                                    if (Main.listOfRegularCards.size() == 0){
                                        System.out.println("\uD83D\uDEAB Sizda qo'shilgan kartalar yo'q");
                                    }else {
                                        cardService.displayCards(Main.listOfRegularCards);
                                        Main.listOfRegularCards.remove(scanner.nextInt()-1);
                                        System.out.println("✅ Karta muvaffaqiyatli o'chirildi");
                                    }
                                }
                                case 2 -> {
                                    System.out.println("1. \uD83C\uDDF7\uD83C\uDDFA Русский\n2. \uD83C\uDDEC\uD83C\uDDE7 English");
                                    if (scanner.nextInt()==1){
                                        russianMain.main();
                                    }else {
                                        englishMain.main();
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("❌ Tasdiqlash kodi xato kiritildi");
            }
        }
    }
}