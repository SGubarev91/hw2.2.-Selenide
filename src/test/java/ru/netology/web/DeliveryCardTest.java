package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.$$;

class DeliveryCardTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void ShuldСardDeliveryBeSuccessfully() {
        String planningDate = generateDate(5, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $$("[data-test-id=city] input").find(Condition.visible).setValue("Москва");
        $$("[data-test-id=date] input").find(Condition.visible).
                press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE).setValue(planningDate);
        $$("[data-test-id=name] input").find(Condition.visible).setValue("Иванов Иван");
        $$("[data-test-id=phone] input").find(Condition.visible).setValue("+71234567890");
        $$("[data-test-id=agreement]").find(Condition.visible).click();
        $$("button").filter(Condition.visible).find(Condition.text("Забронировать")).click();
        $$("[data-test-id=notification]").find(Condition.visible)
                .should(Condition.visible, Duration.ofSeconds(15));
    }
}
