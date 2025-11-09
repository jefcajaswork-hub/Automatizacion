Feature: Validar productos de FakeStore API

  Background:
    * url 'https://fakestoreapi.com'

  Scenario: Validar producto estrella con rate 4.8 y categoría electronics
    Given path '/products'
    When method get
    Then status 200
    And match response == '#[]'

    # Buscar el producto con rate 4.8
    * def estrella = response.filter(x => x.rating.rate == 4.8)[0]

    # Validar sus campos
    And match estrella.id == 11
    And match estrella.category == 'electronics'
    And match estrella.title contains 'Silicon Power 256GB SSD 3D NAND A55 SLC Cache Performance Boost SATA III 2.5'

  Scenario: Validar cantidad de productos electronics y que incluya el ID 11
    Given path '/products/category/electronics'
    When method get
    Then status 200
    And match response == '#[]'

    # Validar 6 productos
    And match response == '#[6]'

    # Validar que el producto ID 11 está en el listado
    * def ids = response.map(x => x.id)
    And match ids contains 11

  Scenario: Visualizar solo 3 productos electronics usando limit
    Given path '/products/category/electronics'
    And param limit = 3
    When method get
    Then status 200
    And match response == '#[3]'
