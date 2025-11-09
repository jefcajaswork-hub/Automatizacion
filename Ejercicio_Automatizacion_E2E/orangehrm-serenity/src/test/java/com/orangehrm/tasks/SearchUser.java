package com.orangehrm.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Click;
import com.orangehrm.ui.UserManagementPage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import net.serenitybdd.screenplay.actions.Clear;
import com.orangehrm.interactions.ReplaceText;

public class SearchUser implements Task {

    private final String username;

    public SearchUser(String username){
        this.username = username;
    }

    public static SearchUser withName(String username){
        return Tasks.instrumented(SearchUser.class, username);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(UserManagementPage.ADMIN_MENU),
                WaitUntil.the(UserManagementPage.SEARCH_USERNAME_FIELD, isVisible()).forNoMoreThan(10).seconds(),
                ReplaceText.on(UserManagementPage.SEARCH_USERNAME_FIELD, username),
                Click.on(UserManagementPage.SEARCH_BUTTON)
        );
    }
}
