package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

class DeliveryCardTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCardDeliveryBeSuccessfully() {
        String planningDate = generateDate(5, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id=notification]").should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("04.12.2025"));//"04.12.2025" = текущая дата + количество days(5) класса generateDate
    }

    @Test
    void shouldCardDeliveryBeSuccessfullyByLists() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Се");
        $$(".menu-item").find(Condition.text("Севастополь")).click();
        $("[data-test-id=date] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("button").find(".icon_name_calendar").click();
        $$("[data-day]").get(4).click();
        $$("[data-test-id=name] input").find(Condition.visible).setValue("Иванов Иван");
        $$("[data-test-id=phone] input").find(Condition.visible).setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $$("button").filter(Condition.visible).find(Condition.text("Забронировать")).click();
        $("[data-test-id=notification]")
                .should(Condition.text("06.12.2025"), Duration.ofSeconds(15));
    }
}
