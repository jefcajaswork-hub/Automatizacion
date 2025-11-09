package com.orangehrm.steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Y;
import io.cucumber.java.es.Entonces;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

import com.orangehrm.tasks.Login;
import com.orangehrm.tasks.SearchUser;
import com.orangehrm.tasks.UpdateUser;
import com.orangehrm.ui.AdminPage;

public class LoginStepDefinitions {

    @Managed
    WebDriver navegador;

    Actor actor = Actor.named("María");
    private String usuarioBuscado;

    @Dado("que el usuario ingresa a OrangeHRM con credenciales válidas")
    public void login() {
        actor.can(BrowseTheWeb.with(navegador));
        actor.attemptsTo(
            Login.withCredentials("Admin", "admin123")
        );
    }

    @Cuando("busca al usuario {string}")
    public void buscarUsuario(String usuario) {
        this.usuarioBuscado = usuario;
        actor.attemptsTo(
            SearchUser.withName(usuario)
        );
    }

    @Y("actualiza el Username a {string}")
    public void actualizarUsername(String nuevoUsuario) {
        actor.attemptsTo(
            UpdateUser.to(usuarioBuscado, nuevoUsuario)
        );
    }

    @Entonces("debería visualizar el mensaje {string}")
    public void validar(String mensaje) {
        actor.attemptsTo(
                WaitUntil.the(AdminPage.SUCCESS_TITLE, isVisible()).forNoMoreThan(10).seconds(),
                Ensure.that(AdminPage.SUCCESS_TITLE).text().contains(mensaje)
        );
    }
}
