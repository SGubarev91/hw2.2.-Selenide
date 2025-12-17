package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        $("[data-test-id=name] input").setValue("Степанов Степан");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id=notification]")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Успешно!"))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldCardDeliveryBeSuccessfullyByLists() {
        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(7, "dd.MM.yyyy");
        String planningDay = generateDate(7, "d");
        String minVolidtMonth = generateDate(3, "MMM");
        String planningMonth = generateDate(7, "MMM");
        $("[data-test-id=city] input").setValue("Се");
        $$(".menu-item").find(Condition.text("Севастополь")).click();
        $("[data-test-id=date] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("button").click();
        if (!Objects.equals(minVolidtMonth, planningMonth))
            {
            $("[data-step='1']").click();
            }
        $$(".calendar__day").find(Condition.text(planningDay)).click();
        $$("[data-test-id=name] input").find(Condition.visible).setValue("Иванов Иван");
        $$("[data-test-id=phone] input").find(Condition.visible).setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id=notification]")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Успешно!"))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}