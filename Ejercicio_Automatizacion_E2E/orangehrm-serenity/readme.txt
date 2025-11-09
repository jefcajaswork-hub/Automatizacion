OrangeHRM Serenity E2E
=======================

Objetivo
- Automatizar en OrangeHRM el login y la actualización del username de un usuario, validando el mensaje "Success".

Tecnologías y versiones usadas
- Java JDK: 21.0.8 LTS (Eclipse Adoptium)
- Maven: 3.9.11
- Google Chrome: 142.0.7444.60
- ChromeDriver: 142.0.7444.61
- Serenity BDD: 3.9.0 | Serenity Cucumber: 3.9.0 | Serenity JUnit: 3.9.0
- JUnit: 4.13.2 | Selenium (transitiva): 4.10.0

Pre-requisitos
1) Tener Java y Maven en PATH. Verificar:
   - java -version
   - mvn -version
2) Tener Google Chrome instalado.

Instalación del ChromeDriver (recomendado)
1) Habilitar scripts en PowerShell (una vez):
   - Set-ExecutionPolicy -Scope CurrentUser RemoteSigned -Force
2) Instalar driver compatible y configurar variable de usuario CHROMEDRIVER_PATH:
   - Ejercicio_Automatizacion_E2E\orangehrm-serenity\scripts\setup-chromedriver.ps1 -SetEnv -Headless
3) Cerrar y reabrir PowerShell para que CHROMEDRIVER_PATH esté disponible.

Ejecución de pruebas (headless por defecto)
1) Ir al módulo:
   - cd Ejercicio_Automatizacion_E2E\orangehrm-serenity
2) Ejecutar:
   - mvn clean verify

Si no reiniciaste la terminal y quieres pasar el driver explícito (PowerShell):
   - mvn clean verify '-Dwebdriver.driver=chrome' '-Dwebdriver.chrome.driver=C:\\ruta\\a\\chromedriver.exe'
   Ejemplo: mvn clean verify '-Dwebdriver.driver=chrome' '-Dwebdriver.chrome.driver=C:\Users\work\chromedriver\142.0.7444.61\chromedriver-win64\chromedriver.exe'

Reportes
- target\site\serenity\index.html

Datos del escenario (por defecto)
- URL: https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
- Usuario: Admin
- Password: admin123
- Usuario a buscar: Jasmine.Morgan
- Nuevo username: Jasmine.Morgan.Test

Notas
- serenity.conf ya define webdriver.chrome.driver y switches (--headless=new). No es necesario pasar flags adicionales.
