package com.orangehrm.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ReplaceText implements Interaction {

    private final Target field;
    private final String value;

    public ReplaceText(Target field, String value) {
        this.field = field;
        this.value = value;
    }

    public static ReplaceText on(Target field, String value) {
        return Tasks.instrumented(ReplaceText.class, field, value);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebElement element = field.resolveFor(actor);
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        element.click();

        try {
            // Intento 1: Ctrl+A + Delete
            new Actions(driver)
                    .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                    .sendKeys(Keys.DELETE)
                    .perform();
        } catch (Exception ignored) { }

        try {
            // Intento 2: clear()
            element.clear();
        } catch (Exception ignored) { }

        // Si aún queda texto, usar JS como último recurso
        try {
            if (element.getAttribute("value") != null && !element.getAttribute("value").isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
            }
        } catch (Exception ignored) { }

        element.sendKeys(value);
    }
}

