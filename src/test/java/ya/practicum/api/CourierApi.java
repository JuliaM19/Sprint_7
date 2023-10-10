package ya.practicum.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import ya.practicum.Courier;
import ya.practicum.CourierLogin;
import ya.practicum.Order;
import ya.practicum.Util;

import static io.restassured.RestAssured.given;
import static ya.practicum.api.Paths.*;

public class CourierApi {

    public CourierApi() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH);
    }

    @Step("Авторизоваться курьером")
    public Response loginCourier(CourierLogin courierLogin) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(courierLogin)
                .when()
                .post(COURIER_LOGIN_PATH);
    }

    @Step("Удалить курьера")
    public void deleteCourier(Courier courier) {
        CourierLogin courierLogin = Util.getCourierLogin(courier);
        Response response = loginCourier(courierLogin);
        int courierId = response.getBody().jsonPath().getInt("id");
        given().header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .when()
                .delete(COURIER_DELETE_PATH, courierId);
    }

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(order)
                .when()
                .post(Paths.ORDER_PATH);
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .when()
                .get(Paths.ORDER_PATH);
    }
}
