package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromCard = $("[data-test-id=from] input");
    private SelenideElement toCard = $("[data-test-id=to] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    // public void topUp (int transferAmount) {
    //    amount.setValue(String.valueOf(transferAmount));
    //    if (toCard.getAttribute("value").contains("0001")==true) {
    //       fromCard.setValue(DataHelper.getSecondCardInfo().getCardNumber());
    //    } else
    //        fromCard.setValue(DataHelper.getFirstCardInfo().getCardNumber());
    //    transferButton.click();
    //}


    public void topUp (String cardNumber, int transferAmount) {
        amount.setValue(String.valueOf(transferAmount));
        fromCard.setValue(cardNumber);
        transferButton.click();
    }
}
