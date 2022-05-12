package ru.netology;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
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

        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldTransferBetweenCards() {
        val dashboard = new DashboardPage();
        val cardFirst = dashboard.getCard(DataHelper.getFirstCardInfo());
        val initialBalanceFirst = cardFirst.getCardBalance();
        val cardSecond = dashboard.getCard(DataHelper.getSecondCardInfo());
        val initialBalanceSecond = cardSecond.getCardBalance();
        int  transferAmount = DataHelper.getValidTransferAmount(initialBalanceSecond);
        cardFirst.topUp();
        val transferPage =new TransferPage();
        transferPage.topUp(cardSecond.info.getCardNumber(), transferAmount);
        int expectedFirst = initialBalanceFirst + transferAmount;
        int expectedSecond= initialBalanceSecond - transferAmount;
        assertEquals(expectedFirst, cardFirst.getCardBalance());
        assertEquals(expectedSecond, cardSecond.getCardBalance());
    }

    @Test
    void shouldTransferFromFirstCard() {
        val dashboard = new DashboardPage();
        val cardFirst = dashboard.getCard(DataHelper.getFirstCardInfo());
        val initialBalanceFirst = cardFirst.getCardBalance();
        val cardSecond = dashboard.getCard(DataHelper.getSecondCardInfo());
        val initialBalanceSecond = cardSecond.getCardBalance();
        int  transferAmount = DataHelper.getValidTransferAmount(initialBalanceFirst);
        cardSecond.topUp();
        val transferPage =new TransferPage();
        transferPage.topUp(cardFirst.info.getCardNumber(), transferAmount);
        int expectedFirst = initialBalanceFirst - transferAmount;
        int expectedSecond= initialBalanceSecond + transferAmount;
        assertEquals(expectedFirst, cardFirst.getCardBalance());
        assertEquals(expectedSecond, cardSecond.getCardBalance());
    }

    @Test
    void shouldFailIfAmountNotEnough() {
        val dashboard = new DashboardPage();
        val cardInfoFirst = dashboard.getCard(DataHelper.getFirstCardInfo());
        val initialBalanceFirst = cardInfoFirst.getCardBalance();
        val cardInfoSecond = dashboard.getCard(DataHelper.getSecondCardInfo());
        val initialBalanceSecond = cardInfoSecond.getCardBalance();
        int  transferAmount = DataHelper.getInvalidTransferAmount(initialBalanceFirst);
        cardInfoSecond.topUp();
        val transferPage =new TransferPage();
        transferPage.topUp(cardInfoFirst.info.getCardNumber(), transferAmount);
        val balanceFirst = cardInfoFirst.getCardBalance();
        val balanceSecond = cardInfoSecond.getCardBalance();
        int expectedFirst = initialBalanceFirst - transferAmount;
        int expectedSecond= initialBalanceSecond + transferAmount;
        Assertions.assertFalse(expectedFirst < 0);
        assertEquals(initialBalanceFirst, balanceFirst);
        assertEquals(initialBalanceSecond, balanceSecond);
    }
}
