package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    public static class Card {
        private final SelenideElement card;
        private final String balanceStart = "баланс: ";
        private final String balanceFinish = " р.";

        public Card(SelenideElement card, DataHelper.CardInfo cardInfo) {
            this.card = card;
            this.info = cardInfo;
        }

        public final DataHelper.CardInfo info;

        public int getCardBalance() {
            return extractBalance(card.text());
        }

        public SelenideElement getDepositButton() {
            return card.$("[data-test-id=action-deposit]");
        }

        public void topUp() {
            val depositButton = this.getDepositButton();
            if (depositButton != null) {
                depositButton.click();
            }
        }

        private int extractBalance(String text) {
            val start = text.indexOf(balanceStart);
            val finish = text.indexOf(balanceFinish);
            val value = text.substring(start + balanceStart.length(), finish);
            return Integer.parseInt(value);
        }
    }

    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public Card getCard(DataHelper.CardInfo cardInfo) {
        val cardElement = cards.findBy(Condition.text(cardInfo.getId()));

        return new Card(cardElement, cardInfo);
    }
}
