package com.orangehrm.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.waits.WaitUntil;
import com.orangehrm.interactions.ReplaceText;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import com.orangehrm.ui.AdminPage;

public class UpdateUser implements Task {

    private final String currentUsername;
    private final String newUsername;

    public UpdateUser(String currentUsername, String newUsername) {
        this.currentUsername = currentUsername;
        this.newUsername = newUsername;
    }

    public static UpdateUser to(String currentUsername, String newUsername) {
        return Tasks.instrumented(UpdateUser.class, currentUsername, newUsername);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitUntil.the(AdminPage.EDIT_BUTTON_FOR.of(currentUsername), isVisible()).forNoMoreThan(10).seconds(),
                Click.on(AdminPage.EDIT_BUTTON_FOR.of(currentUsername)),
                WaitUntil.the(AdminPage.USERNAME_FIELD, isVisible()).forNoMoreThan(10).seconds(),
                ReplaceText.on(AdminPage.USERNAME_FIELD, newUsername),
                Click.on(AdminPage.SAVE_BUTTON)
        );
    }
}
