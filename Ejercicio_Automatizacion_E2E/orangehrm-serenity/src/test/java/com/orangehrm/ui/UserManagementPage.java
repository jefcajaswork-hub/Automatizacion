package com.orangehrm.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class UserManagementPage {

    public static final Target ADMIN_MENU =
            Target.the("Menú Admin")
                  .located(By.xpath("//span[normalize-space()='Admin']"));

    public static final Target SEARCH_USERNAME_FIELD = 
            Target.the("Campo de búsqueda de usuario")
                  .located(By.xpath("//label[normalize-space()='Username']/../following-sibling::div//input"));

    public static final Target SEARCH_BUTTON = 
            Target.the("Botón Buscar")
                  .located(By.xpath("//button[@type='submit']"));
}
