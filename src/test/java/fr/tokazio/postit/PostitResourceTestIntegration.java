package fr.tokazio.postit;

import fr.tokazio.postit.api.Liking;
import fr.tokazio.postit.api.PostitService;
import fr.tokazio.postit.api.ResponseCode;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static fr.tokazio.postit.api.Version.V1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
public class PostitResourceTestIntegration {

    public static final String API_POSTIT = "/api/postits";

    @Inject
    private PostitService service;

    @BeforeEach
    public void setup() {
        weHaveTwoPostit();
    }

    @Test
    public void index() {
        RestAssured.given()
                .when().get("/")
                .then()
                .statusCode(ResponseCode.OK)
                .body(startsWith("<!DOCTYPE html>")).log().ifValidationFails();
    }

    @Test
    public void list() {
        final List<Postit> actual = RestAssured.given()
                .when().get(API_POSTIT)
                .then()
                .statusCode(ResponseCode.OK)
                .log()
                .ifValidationFails()
                .extract()
                .as(new TypeRef<List<Postit>>() {
                });
        assertThat(actual).hasSize(2);
    }

    @Test
    public void get() {
        final Postit actual = RestAssured.given()
                .when().get(API_POSTIT + "/abcd")
                .then()
                .statusCode(ResponseCode.OK)
                .log()
                .ifValidationFails()
                .extract()
                .as(Postit.class);
        PostitAssert.assertThat(actual).hasText("one");
    }

    @Test
    public void getNotFound() {

        service.clear();

        RestAssured.given()
                .when().get(API_POSTIT + "/abcd")
                .then()
                .statusCode(ResponseCode.NOT_FOUND)
                .log()
                .ifValidationFails();
    }

    @Test
    public void edit() {
        final Postit actual = RestAssured
                .given()
                .contentType(V1)
                .when()
                .body(PostItDummies.postitTwo())
                .post(API_POSTIT + "/abcd")
                .then()
                .statusCode(ResponseCode.OK)
                .log()
                .ifValidationFails()
                .extract()
                .as(Postit.class);
        PostitAssert.assertThat(actual).hasText("two");
    }

    @Test
    public void editNotFound() {

        service.clear();

        RestAssured.given()
                .contentType(V1)
                .when()
                .body(PostItDummies.postitTwo())
                .post(API_POSTIT + "/abcd")
                .then()
                .statusCode(ResponseCode.NOT_FOUND)
                .log()
                .ifValidationFails();
    }

    @Test
    public void likeWhenNoUser() {
        RestAssured.given()
                .contentType(V1)
                .when()
                .post(API_POSTIT + "/like/abcd")
                .then()
                .statusCode(ResponseCode.BAD_ARGUMENT)
                .log()
                .ifValidationFails();
    }

    @Test
    public void like() {
        final Liking actual = RestAssured.given()
                .contentType(V1)
                .when()
                .body(PostItDummies.userB())
                .post(API_POSTIT + "/like/abcd")
                .then()
                .statusCode(ResponseCode.ACCEPTED)
                .log()
                .ifValidationFails().extract().as(Liking.class);

        assertThat(actual).isEqualTo(Liking.LIKE);
    }

    @Test
    public void cantlikeWhenYouOwn() {
        RestAssured.given()
                .contentType(V1)
                .when()
                .body(PostItDummies.userA())
                .post(API_POSTIT + "/like/abcd")
                .then()
                .statusCode(ResponseCode.BAD_ARGUMENT)
                .log()
                .ifValidationFails();
    }

    @Test
    public void unlike() {
        final Liking actual = RestAssured.given()
                .contentType(V1)
                .when()
                .body(PostItDummies.userA())
                .post(API_POSTIT + "/like/efgh")
                .then()
                .statusCode(ResponseCode.ACCEPTED)
                .log()
                .ifValidationFails().extract().as(Liking.class);

        assertThat(actual).isEqualTo(Liking.UNLIKE);
    }

    @Test
    public void add() {

        service.clear();

        final Postit actual = RestAssured.given()
                .contentType(V1)
                .when()
                .body(PostItDummies.postitOne())
                .post(API_POSTIT)
                .then()
                .statusCode(ResponseCode.CREATED)
                .log()
                .ifValidationFails()
                .extract()
                .as(Postit.class);
        PostitAssert.assertThat(actual).hasText("one");
    }

    @Test
    public void delete() {
        final Postit actual = RestAssured.given()
                .when().delete(API_POSTIT + "/abcd")
                .then()
                .statusCode(ResponseCode.OK)
                .log()
                .ifValidationFails()
                .extract()
                .as(Postit.class);
        PostitAssert.assertThat(actual).hasText("one");
    }

    @Test
    public void deleteNotFound() {

        service.clear();

        RestAssured.given()
                .when().delete(API_POSTIT + "/abcd")
                .then()
                .statusCode(ResponseCode.NOT_FOUND)
                .log()
                .ifValidationFails();
    }


    //==========================================
    //Utilities
    //==========================================

    private void weHaveTwoPostit() {
        service.clear();
        service.add(PostItDummies.postitOne());
        service.add(PostItDummies.postitTwo());
    }
}