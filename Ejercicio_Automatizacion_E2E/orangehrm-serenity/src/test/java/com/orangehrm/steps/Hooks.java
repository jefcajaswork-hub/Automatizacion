package com.orangehrm.steps;

import io.cucumber.java.Before;

public class Hooks {

    @Before(order = 0)
    public void ensureWebDriverExecutable() {
        String envDriver = System.getenv("CHROMEDRIVER_PATH");
        if (envDriver != null && !envDriver.isEmpty()) {
            System.setProperty("webdriver.chrome.driver", envDriver);
        }
        // Fuerza el browser por si la config no se cargó aún
        System.setProperty("webdriver.driver", "chrome");
    }
}

