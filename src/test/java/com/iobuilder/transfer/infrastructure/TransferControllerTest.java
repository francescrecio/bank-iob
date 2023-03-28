package com.iobuilder.transfer.infrastructure;

import com.iobuilder.shared.infrastructure.security.JWTManager;
import com.iobuilder.wallet.application.WalletDTO;
import com.iobuilder.wallet.domain.Wallet;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferControllerTest {
    @Inject
    JWTManager jwtManager;

    private String token;
    private String walletId;
    private String secondWalletId;

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
        token = response.getBody().asString();

        Assert.assertNotNull(is(response.getBody().asString()));
    }

    @Test
    @Order(3)
    public void testCreateWallet() {
        var response = given().auth().oauth2(token)
                .when().post("/wallet/create")
                .then().statusCode(CREATED.getStatusCode()).extract().response();

        var responseWallet = response.getBody().as(WalletDTO.class);

        Assert.assertNotNull(is(responseWallet.getId()));

        walletId = responseWallet.getId();
    }

    @Test
    @Order(4)
    public void testCreateSecondWallet() {
        var response = given().auth().oauth2(token)
                .when().post("/wallet/create")
                .then().statusCode(CREATED.getStatusCode()).extract().response();

        var responseWallet = response.getBody().as(WalletDTO.class);

        Assert.assertNotNull(is(responseWallet.getId()));

        secondWalletId = responseWallet.getId();
    }

    @Test
    @Order(5)
    public void testDepositWallet() {
        var response = given().auth().oauth2(token)
                .when()
                .queryParam("amount", 30)
                .put("/wallet/deposit/" + walletId)
                .then()
                .statusCode(OK.getStatusCode()).extract().response();
    }

    @Test
    @Order(6)
    public void testBalanceWallet() {
        var response = given().auth().oauth2(token)
                .when()
                .get("/wallet/balance/" + walletId)
                .then()
                .statusCode(OK.getStatusCode()).extract().response();

        var responseWallet = response.getBody().as(WalletDTO.class);

        assertThat(30f, is(responseWallet.getBalance()));
        assertThat("DEPOSIT", is(responseWallet.getTransfers().get(0).getTransferType()));
    }

    @Test
    @Order(7)
    public void testCreateTransfer() {
        String userId = "aeda0c63-22a5-4df4-9b98-c2a2c7586e34";

        String bodyTransfer = "{"+
                "\"userOrigin\":\""+userId+"\","+
                "\"userDestination\":\""+userId+"\","+
                "\"walletOrigin\":\""+walletId+"\","+
                "\"walletDestination\":\""+secondWalletId+"\","+
                "\"transferType\":\"TRANSFER\","+
                "\"amount\":10"+
        "}";

        var response = given().auth().oauth2(token)
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyTransfer)
                .post("/transfer/create")
                .then()
                .statusCode(OK.getStatusCode()).extract().response();
    }
}
