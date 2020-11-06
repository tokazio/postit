package fr.tokazio.postit;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
public class PostitResourceTest {

    @Test
    public void testIndexEndpoint() {
        RestAssured.given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body(startsWith("<!DOCTYPE html>"));
    }

    @Test
    public void testListEndpoint() {
        RestAssured.given()
                .when().get("/api/postit")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetEndpoint() {
        RestAssured.given()
                .when().get("/api/postit/abcd")
                .then()
                .statusCode(200);
    }

    @Test
    public void testEditEndpoint() {
        RestAssured.given()
                .when().post("/api/postit/abcd")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLikeEndpointWhenNOTAllreadyLiked() {
        RestAssured.given()
                .when().post("/api/postit/like/abcd")
                .then()
                .statusCode(200)
                .body(is("Vous aimez"));
    }

    @Test
    public void testLikeEndpointWhenAllreadyLiked() {
        RestAssured.given()
                .when().post("/api/postit/like/abcd")
                .then()
                .statusCode(200)
                .body(is("Vous n'aimez plus"));
    }

    @Test
    public void testAddEndpoint() {
        RestAssured.given()
                .when().post("/api/postit/abcd")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteEndpoint() {
        RestAssured.given()
                .when().delete("/api/postit/abcd")
                .then()
                .statusCode(200);
    }
}