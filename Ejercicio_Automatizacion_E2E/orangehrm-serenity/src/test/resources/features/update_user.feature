# language: es
Característica: Actualización de usuario en OrangeHRM

@smoke
Escenario: Editar el Username de un usuario existente
  Dado que el usuario ingresa a OrangeHRM con credenciales válidas
  Cuando busca al usuario "Jasmine.Morgan"
  Y actualiza el Username a "Jasmine.Morgan.Test"
  Entonces debería visualizar el mensaje "Success"
