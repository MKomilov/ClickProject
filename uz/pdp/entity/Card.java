package uz.pdp.entity;

public class Card {

    private String cardHolderName;
    private String cardNum;
    private String cardPhoneNum;
    private String expDate;
    private CardTypes cardType;
    private double initialBalance = 2000000;

    public Card() {
    }

    public Card(String cardHolderName, String cardNum) {
        this.cardHolderName = cardHolderName;
        this.cardNum = cardNum;
    }

    public Card(String cardNum, CardTypes cardType, String cardHolderName) {
        this.cardHolderName=cardHolderName;
        this.cardNum = cardNum;
        this.cardType = cardType;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardPhoneNum() {
        return cardPhoneNum;
    }

    public void setCardPhoneNum(String cardPhoneNum) {
        this.cardPhoneNum = cardPhoneNum;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public CardTypes getCardType() {
        return cardType;
    }

    public void setCardType(CardTypes cardType) {
        this.cardType = cardType;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNum='" + cardNum + '\'' +
                ", expDate='" + expDate + '\'' +
                ", cardType=" + cardType +
                '}';
    }
}
