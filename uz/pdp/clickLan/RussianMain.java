package uz.pdp.clickLan;

import uz.pdp.entity.Card;
import uz.pdp.entity.CardTypes;
import uz.pdp.entity.ClickWallet;
import uz.pdp.entity.History;
import uz.pdp.service.CardServiceImplRus;
import uz.pdp.utility.Main;

import java.util.Objects;
import java.util.Scanner;

public class RussianMain {

    public static Scanner scanner = new Scanner(System.in);
    public static Scanner scannerText = new Scanner(System.in);

    public void main() {
        CardServiceImplRus cardService = new CardServiceImplRus();
        ClickWallet wallet = new ClickWallet(0);
        UzbekMain uzbekMain = new UzbekMain();
        EnglishMain englishMain = new EnglishMain();
        do {
            System.out.print("\uD83D\uDCF1 Введите свой номер телефона: ");
        } while (!scannerText.nextLine().matches("^[+](998)\\d{9}$"));
        while (true) {
            int random_int = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
            System.out.println(random_int);
            System.out.println("\uD83D\uDCF2 Введите код подтверждения: ");
            if (random_int == scanner.nextInt()) {

                while (true) {
                    cardService.showMenu();
                    Card card = new Card();
                    loop:
                    switch (scanner.nextInt()) {
                        case 0 -> {
                            return;
                        }
                        case 1 -> {
                            cardService.showMyCards(Main.listOfRegularCards);
                            System.out.println("0. \uD83D\uDD19 Назад\n1. \uD83D\uDCB3 Добавьте карту");
                            switch (scanner.nextInt()) {
                                case 0 -> {
                                    break loop;
                                }
                                case 1 -> {
                                    String num;
                                    do {
                                        System.out.print("Введите номер карты: ");
                                        num = scannerText.nextLine();
                                        card.setCardNum(num);
                                        if (Objects.equals(num.substring(0, 4), "9860")) {
                                            card.setCardType(CardTypes.HUMO);
                                        } else if (Objects.equals(num.substring(0, 4), "8600")) {
                                            card.setCardType(CardTypes.UZCARD);
                                        }
                                    } while (!num.matches("^(8600|9860)(\\d{12})$"));
                                    String exp;
                                    do {
                                        System.out.print("Введите срок действия карты (мм/гг): ");
                                        exp = scannerText.nextLine();
                                    } while (!exp.matches("^(0[1-9]|1[0-2])/(\\d{2})$"));
                                    card.setExpDate(exp);
                                    Main.listOfRegularCards.add(card);
                                    System.out.println("✅ Карта успешно добавлена");
                                }
                            }
                        }
                        case 2 -> {
                            System.out.println("0. \uD83D\uDD19 Назад");
                            cardService.showBalance(Main.listOfRegularCards);
                            int option = scanner.nextInt();
                            if (option == 0) {
                                break loop;
                            } else if (option > Main.listOfRegularCards.size()) {
                                System.out.println("❌ что-то введено неправильно");
                            } else {
                                System.out.println("\uD83D\uDCB2 У вас на карте " + Main.listOfRegularCards.get(option - 1).getInitialBalance() + " сум");
                            }
                        }
                        case 3 -> {
                            if (Main.listOfRegularCards.size() == 0) {
                                System.out.println("\uD83D\uDEAB У вас нет добавленных карт");
                            } else if (Main.listOfRegularCards.size() == 1) {
                                System.out.print("\uD83D\uDCB3 Введите номер карты получателя: ");
                                String receiver = scannerText.nextLine();
                                String response = cardService.transfer(receiver, Main.listOfReceivers, Main.listOfFCards);
                                if (response == null) {
                                    System.out.println("❌ Карта не найдена");
                                    break;
                                }
                                System.out.println("\uD83D\uDC64 Получатель: " + response);
                                System.out.print("\uD83D\uDCB0  Введите сумму, которую хотите отправить: ");
                                double transfer = scanner.nextDouble();
                                double balance = cardService.checkAndExecute(Main.listOfRegularCards.get(0).getInitialBalance(), transfer);
                                Main.listOfRegularCards.get(0).setInitialBalance(balance);
                                History history = new History(transfer, receiver, Main.listOfRegularCards.get(0).getCardNum(), response);
                                Main.cardHistories.add(history);
                                wallet.setWallet(wallet.getWallet() + transfer * 0.001);
                            } else {
                                System.out.println("0. \uD83D\uDD19 Назад\n1. \uD83D\uDD1B Среди моих карт\n2. ➡ Перевод на другую карту");
                                switch (scanner.nextInt()) {
                                    case 0 -> {
                                        break loop;
                                    }
                                    case 1 -> {
                                        System.out.println("\uD83D\uDCB3 Выберите одну из ваших карт получения: ");
                                        cardService.displayCards(Main.listOfRegularCards);
                                        int receive = scanner.nextInt() - 1;
                                        System.out.println("\uD83D\uDCB3 Выберите карту для перевода: ");
                                        cardService.removeCard(Main.listOfRegularCards, receive);
                                        int op;
                                        if (Main.listOfRegularCards.size() <= receive + 1) {
                                            op = scanner.nextInt() - 1;
                                        } else {
                                            op = scanner.nextInt();
                                        }
                                        System.out.print("\uD83D\uDCB0 Введите сумму, которую хотите отправить: ");
                                        double transfer = scanner.nextDouble();
                                        double balance = cardService.checkAndExecute(Main.listOfRegularCards.get(op).getInitialBalance(), transfer);
                                        Main.listOfRegularCards.get(op).setInitialBalance(balance);
                                        double sum = cardService.addFundToCard(Main.listOfRegularCards.get(receive).getInitialBalance(), transfer);
                                        Main.listOfRegularCards.get(receive).setInitialBalance(sum);
                                        History history = new History(transfer, Main.listOfRegularCards.get(receive).getCardNum(), Main.listOfRegularCards.get(op).getCardNum());
                                        Main.cardHistories.add(history);
                                        wallet.setWallet(wallet.getWallet() + transfer * 0.001);
                                    }
                                    case 2 -> {
                                        System.out.print("\uD83D\uDCB3 Введите номер карты получателя: ");
                                        String receiver = scannerText.nextLine();
                                        String response = cardService.transfer(receiver, Main.listOfReceivers, Main.listOfFCards);
                                        if (response == null) {
                                            System.out.println("❌ Карта не найдена");
                                            break;
                                        }
                                        System.out.println("\uD83D\uDC64  Получатель: " + response);
                                        cardService.displayCards(Main.listOfRegularCards);
                                        System.out.print("\uD83D\uDCB3 Выберите карту для перевода: ");
                                        int op = scanner.nextInt() - 1;
                                        System.out.print("\uD83D\uDCB0 Введите сумму, которую хотите отправить: ");
                                        double transfer = scanner.nextDouble();
                                        double balance = cardService.checkAndExecute(Main.listOfRegularCards.get(op).getInitialBalance(), transfer);
                                        Main.listOfRegularCards.get(op).setInitialBalance(balance);
                                        History history = new History(transfer, receiver, Main.listOfRegularCards.get(op).getCardNum(), response);
                                        Main.cardHistories.add(history);
                                        wallet.setWallet(wallet.getWallet() + transfer * 0.001);
                                    }
                                }
                            }
                        }
                        case 4 -> cardService.showPaymentHistory(Main.cardHistories);
                        case 5 -> System.out.println(wallet);
                        case 6 -> {
                            System.out.println("0. \uD83D\uDD19  Назад\n1. \uD83D\uDCB3 Удалить карту\n2. \uD83C\uDF10 Изменить язык");
                            switch (scanner.nextInt()){
                                case 0 -> {break loop;}
                                case 1 -> {
                                    if (Main.listOfRegularCards.size() == 0){
                                        System.out.println("\uD83D\uDEAB У вас нет добавленных карт");
                                    }else {
                                        cardService.displayCards(Main.listOfRegularCards);
                                        Main.listOfRegularCards.remove(scanner.nextInt()-1);
                                        System.out.println("✅ Карта успешно удалена");
                                    }
                                }
                                case 2 -> {
                                    System.out.println("1. \uD83C\uDDFA\uD83C\uDDFF Uzbekcha\n2. \uD83C\uDDEC\uD83C\uDDE7 English");
                                    if (scanner.nextInt()==1){
                                        uzbekMain.main();
                                    }else {
                                        englishMain.main();
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("❌ Проверочный код был введен неправильно");
            }
        }
    }
}
