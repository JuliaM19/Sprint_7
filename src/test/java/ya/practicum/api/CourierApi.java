package ya.practicum.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import ya.practicum.Util;

import static io.restassured.RestAssured.given;
import static ya.practicum.api.Paths.*;

public class CourierApi {

    private int courierId = -1;

    public CourierApi() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Создание курьера")
    public void createCourier() {
        given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(Util.COURIER)
                .when()
                .post(COURIER_CREATE_PATH);
    }

    @Step("Авторизоваться курьером")
    public int loginCourier() {
        Response response = given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(Util.COURIER_LOGIN)
                .when()
                .post(COURIER_LOGIN_PATH);

        courierId = response.getBody().jsonPath().getInt("id");
        return courierId;
    }

    @Step("Удалить курьера")
    public void deleteCourier() {
        if (courierId == -1) {
            loginCourier();
        }
        given().header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .when()
                .delete(COURIER_DELETE_PATH, courierId);
    }

}
