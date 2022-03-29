package ru.netology;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.TopUpCard;
import ru.netology.page.CardsPage;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferCardToCardTest {

    @BeforeEach
    void setUpEach() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferBetweenCards() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val cardsPage = new CardsPage();
        val cardFirst = DataHelper.getFirstCardInfo();
        val initialBalanceFirst = new DashboardPage().getCardBalance(cardFirst);
        val cardSecond = DataHelper.getSecondCardInfo();
        val initialBalanceSecond = new DashboardPage().getCardBalance(cardSecond);
        int  transferAmount = TopUpCard.amountTopUpFirstCard();
        cardsPage.topUpFirstCard();
        val transferPage =new TransferPage();
        transferPage.topUp(transferAmount);
        val balanceFirst = new DashboardPage().getCardBalance(cardFirst);
        val balanceSecond = new DashboardPage().getCardBalance(cardSecond);
        int expectedFirst = initialBalanceFirst + transferAmount;
        int expectedSecond= initialBalanceSecond - transferAmount;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldTransferFromFirstCard() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val cardsPage = new CardsPage();
        val cardInfoFirst = DataHelper.getFirstCardInfo();
        val initialBalanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val cardInfoSecond = DataHelper.getSecondCardInfo();
        val initialBalanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int  transferAmount = TopUpCard.amountTopUpSecondCard();
        cardsPage.topUpSecondCard();
        val transferPage =new TransferPage();
        transferPage.topUp(transferAmount);
        val balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst - transferAmount;
        int expectedSecond= initialBalanceSecond + transferAmount;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldFailIfAmountNotEnough() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val cardsPage = new CardsPage();
        val cardInfoFirst = DataHelper.getFirstCardInfo();
        val initialBalanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val cardInfoSecond = DataHelper.getSecondCardInfo();
        val initialBalanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int  transferAmount = TopUpCard.invalidAmountForTopUpSecondCard();
        cardsPage.topUpSecondCard();
        val transferPage =new TransferPage();
        transferPage.topUp(transferAmount);
        val balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst - transferAmount;
        int expectedSecond= initialBalanceSecond + transferAmount;
        Assertions.assertFalse(expectedFirst < 0);
        assertEquals(initialBalanceSecond, balanceSecond);
    }
}
