package guru.qa.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class WdHubStatusTests extends TestBase {

  @BeforeEach
  public void setUpBasePath() {
    RestAssured.basePath = "/wd/hub/status";
  }

  @Test
  public void authorizedStatusTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .auth().basic("user1", "1234")
            .get()
            .then()
            .log().all()
            .statusCode(200);
  }

  @Test
  public void unauthorizedStatusTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .get()
            .then()
            .log().all()
            .statusCode(401);
  }

  @Test
  public void wrongUserStatusTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .auth().basic("user1001", "1234")
            .get()
            .then()
            .log().all()
            .statusCode(401);
  }

  @Test
  public void wrongPasswordStatusTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .auth().basic("user1", "12345")
            .get()
            .then()
            .log().all()
            .statusCode(401);
  }

  @Test
  public void wdHubSchemaTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .auth().basic("user1", "1234")
            .get()
            .then()
            .log().all()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/wd_hub_response_schema.json"));
  }

  @Test
  public void requiredKeysTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .auth().basic("user1", "1234")
            .get()
            .then()
            .log().all()
            .statusCode(200)
            .body("", hasKey("value"))
            .body("value", hasKey("message"))
            .body("value", hasKey("ready"));
  }

  @Test
  public void requiredValueTest() {
    given()
            .log().uri()
            .log().method()
            .log().headers()
            .auth().basic("user1", "1234")
            .get()
            .then()
            .log().all()
            .statusCode(200)
            .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"))
            .body("value.ready", is(true));
  }
}
