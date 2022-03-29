package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class CardsPage {
    private ElementsCollection buttons = $$("[data-test-id=action-deposit] ");
    private SelenideElement buttonFirstCard = buttons.first();
    private SelenideElement buttonSecondCard = buttons.last();

    public void topUpFirstCard() {
        buttonFirstCard.click();
    }

    public void topUpSecondCard() {
        buttonSecondCard.click();
    }
}
