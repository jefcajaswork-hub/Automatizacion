OPCIÓN 1 · EJERCICIO 2

Descripción
- Automatización de validaciones sobre FakeStore API usando Karate 1.4.1.
- Se implementan 3 escenarios: producto estrella, conteo por categoría y límite de resultados.

Prerrequisitos
- JDK: 11 o 17 (recomendado). Probado con JDK 17.
- Maven: 3.8.x o superior.

Versiones usadas (POM)
- Karate JUnit5: 1.4.1 (scope test)
- Maven Surefire Plugin: 2.22.1
- Maven Compiler Plugin: 3.8.0

Proyecto
- Módulo: karate-api-test
- Feature: src/test/java/api/products.feature
- Runner JUnit5: src/test/java/api/ProductsTest.java

Cómo ejecutar
1) Clonar el repo y ubicarse en la carpeta del módulo:
   cd karate-api-test
2) Ejecutar pruebas:
   mvn -q test
3) Ver reporte HTML (abrir en el navegador):
   target/karate-reports/karate-summary.html

APIs consultadas
- API 1: GET https://fakestoreapi.com/products
- API 2: GET https://fakestoreapi.com/products/category/{category}

Escenarios implementados
1) Producto estrella con rate 4.8
   - Se obtiene el listado de productos y se filtra por rating.rate == 4.8.
   - Validaciones: id, category y título.
2) Conteo de productos por categoría electronics
   - Se consulta /products/category/electronics.
   - Se valida que el total sea 6 y que contenga el id esperado.
3) Límite de resultados (limit=3)
   - Se consulta /products/category/electronics?limit=3.
   - Se valida que la longitud del arreglo sea 3.

Nota sobre datos dinámicos de FakeStore
- El contenido de la API pública puede cambiar. Si el conteo o el id no coinciden, los asserts pueden fallar.
- Archivos y líneas relevantes para ajustar:
  - src/test/java/api/products.feature: escenarios 1–3.
  - Ajustar el id esperado y/o el conteo (6) si la API cambia.

Solución actual (robusta ante pequeños cambios)
- Se valida el título con contains y se usa el id que devuelve la API en tiempo de ejecución.
- El conteo de electronics se valida con tamaño exacto (6); si cambia, ajuste a '#[6]' o use una condición más flexible.

Reportes generados
- target/karate-reports/karate-summary.html
- target/karate-reports/api.products.html
- target/surefire-reports/TEST-api.ProductsTest.xml