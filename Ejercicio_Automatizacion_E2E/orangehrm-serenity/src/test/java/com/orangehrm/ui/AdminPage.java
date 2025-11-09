package com.orangehrm.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AdminPage {

    public static final Target EDIT_BUTTON = Target.the("Botón de editar usuario (primera fila)")
            .located(By.xpath("//div[contains(@class,'oxd-table-body')]//i[contains(@class,'bi-pencil-fill')]/ancestor::button"));

    public static final Target EDIT_BUTTON_FOR = Target.the("Botón editar para el usuario {0}")
            .locatedBy("//div[contains(@class,'oxd-table-body')]//div[@role='row'][.//div[normalize-space()='{0}']]//i[contains(@class,'bi-pencil-fill')]/ancestor::button");

    public static final Target USERNAME_FIELD = Target.the("Campo Username en el formulario")
            .located(By.xpath("//label[starts-with(normalize-space(),'Username')]/../following-sibling::div//input"));

    public static final Target SAVE_BUTTON = Target.the("Botón Guardar")
            .located(By.xpath("//button[@type='submit' and normalize-space()='Save']"));

    public static final Target SUCCESS_MESSAGE = Target.the("Mensaje de éxito")
            .located(By.xpath("//div[contains(@class,'oxd-toast-content')]//p[contains(@class,'oxd-text--toast-message')]"));

    public static final Target SUCCESS_TITLE = Target.the("Título del toast de éxito")
            .located(By.xpath("//div[contains(@class,'oxd-toast-content')]//p[contains(@class,'oxd-text--toast-title')]"));
}
