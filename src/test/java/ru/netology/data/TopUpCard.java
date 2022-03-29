package ru.netology.data;

import com.github.javafaker.Faker;
import ru.netology.page.DashboardPage;

import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;

public class TopUpCard {

    public static int amountTopUpFirstCard() {
        DataHelper.CardInfo cardInfoSecond = getSecondCardInfo();
        int balanceSecondCard = new DashboardPage().getCardBalance(cardInfoSecond);
        Faker faker = new Faker();
        int transferAmount = faker.random().nextInt(1, balanceSecondCard);
        return transferAmount;
    }

    public static int amountTopUpSecondCard() {
        DataHelper.CardInfo cardInfoFirst = getFirstCardInfo();
        int balanceFirstCard = new DashboardPage().getCardBalance(cardInfoFirst)+1;
        Faker faker = new Faker();
        int transferAmount = faker.random().nextInt(balanceFirstCard);
        return transferAmount;
    }

    public static int invalidAmountTopUpFirstCard(){
        DataHelper.CardInfo cardInfoSecond = getSecondCardInfo();
        int balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond)+1;
        Faker faker = new Faker();
        int transferAmount = faker.random().nextInt(balanceSecond,100000);
        return transferAmount;
    }

    public static int invalidAmountForTopUpSecondCard(){
        DataHelper.CardInfo cardInfoFirst = getFirstCardInfo();
        int balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        Faker faker = new Faker();
        int transferAmount = faker.random().nextInt(balanceFirst,100000);
        return transferAmount;
    }
}
