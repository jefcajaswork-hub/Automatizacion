package api;

import com.intuit.karate.junit5.Karate;

class ProductsTest {

  @Karate.Test
  Karate testProducts() {
    return Karate.run("products").relativeTo(getClass());
  }

}
