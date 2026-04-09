package guru.qa.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class StatusTests {
  /*
  1. Make request to https://selenoid.autotests.cloud/status
  2. Get response {"total":5,"used":1,"queued":0,"pending":0,"browsers":{"chrome":{"127.0":{}
  3. Check that 'total' is 5 http://localhost:8080/lotto
   */

  @Test
  public void totalAmountTest() {
    get("https://selenoid.autotests.cloud/status")
            .then()
            .log().all()
            .body("total", is(5));
  }

  @Test
  public void status200Test() {
    get("https://selenoid.autotests.cloud/status")
            .then()
            .log().all()
            .statusCode(200);
  }

  @Test
  public void requiredKeysTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .get("https://selenoid.autotests.cloud/status")
            .then()
            .log().all()
            .statusCode(200)
            .body("", hasKey("total"))
            .body("", hasKey("used"))
            .body("", hasKey("queued"))
            .body("", hasKey("pending"))
            .body("", hasKey("browsers"));
  }

  @Test
  public void browserVersionsTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .get("https://selenoid.autotests.cloud/status")
            .then()
            .log().all()
            .statusCode(200)
            .body("browsers.chrome", hasKey("127.0"))
            .body("browsers.chrome", hasKey("128.0"))
            .body("browsers.firefox", hasKey("124.0"))
            .body("browsers.firefox", hasKey("125.0"))
            .body("browsers.opera", hasKey("108.0"))
            .body("browsers.opera", hasKey("109.0"));
  }

  @Test
  public void verifySchemaTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .get("https://selenoid.autotests.cloud/status")
            .then()
            .log().all()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/schema_response_status_selenide.txt"));
  }

  @Test
  public void bestTotalAmountTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .get("https://selenoid.autotests.cloud/status")
            .then()
            .log().all()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/schema_response_status_selenide.json"))
            .body("total", is(5));
  }
}
