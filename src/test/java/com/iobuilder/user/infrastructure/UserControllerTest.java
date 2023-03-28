package com.iobuilder.user.infrastructure;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Test
    @Order(1)
    public void testCreateUser() {
        given().when()
                .queryParam("username", "username")
                .queryParam("password", "password")
                .post("/user/create")
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    @Order(2)
    public void testLogin() {
        var response = given().when()
                .queryParam("username", "username")
                .queryParam("password", "password")
                .post("/user/login")
                .then().statusCode(OK.getStatusCode()).extract().response();

                Assert.assertNotNull(is(response.getBody().asString()));
    }


}
