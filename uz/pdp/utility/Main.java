package uz.pdp.utility;

import uz.pdp.clickLan.EnglishMain;
import uz.pdp.clickLan.RussianMain;
import uz.pdp.clickLan.UzbekMain;
import uz.pdp.entity.Card;
import uz.pdp.entity.CardTypes;
import uz.pdp.entity.History;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static ArrayList<Card> listOfRegularCards = new ArrayList<>();
    public static ArrayList<History> cardHistories = new ArrayList<>();
    public static ArrayList<Card> listOfFCards = new ArrayList<>(Arrays.asList(
            new Card("4309345267124307", CardTypes.MASTERCARD, "Abdummanonov Tohir"),
            new Card("3809245366721543", CardTypes.VISA, "Sobirov Sanjar"),
            new Card("5120547893266712", CardTypes.UNIONPAY, "Bobojonov Umrbek")
    ));

    public static ArrayList<Card> listOfReceivers = new ArrayList<>(Arrays.asList(
            new Card("Avezov Shamsiddin", "8600370637634034"),
            new Card("Kushnazarov Abdullatiyf", "8600637634034073"),
            new Card("Odilov Oybek", "9860363403407543"),
            new Card("Rustamov Shohrux", "9860763403445073")
    ));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UzbekMain uzbekInterface = new UzbekMain();
        RussianMain russianMain = new RussianMain();
        EnglishMain englishMain = new EnglishMain();
        System.out.println("===============\tTilni tanlang:\tВыберите язык\tSelect a language\t===============");
        System.out.println("0. \uD83D\uDD19 Tizimdan chiqish\n1. \uD83C\uDDFA\uD83C\uDDFF O'zbekcha\n2. \uD83C\uDDF7\uD83C\uDDFA Русский\n3. \uD83C\uDDEC\uD83C\uDDE7 English");
        switch (scanner.nextInt()) {
            case 0 -> {
                return;
            }
            case 1 -> uzbekInterface.main();
            case 2 -> russianMain.main();
            case 3 -> englishMain.main();
        }
    }
}
