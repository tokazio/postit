package fr.tokazio.postitrest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PostitResourceTest {

    @Test
    public void testHelloEndpoint() {
        RestAssured.given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body(is("hello"));
    }

}