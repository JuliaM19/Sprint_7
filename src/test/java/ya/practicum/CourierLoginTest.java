package ya.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import ya.practicum.api.CourierApi;
import ya.practicum.api.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    private static final CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Успешный логин")
    @Description("Авторизация курьером с корректными логином и паролем")
    public void testLoginCourierTrue() {
        courierApi.createCourier(Util.COURIER);

        Response response = courierApi.loginCourier(Util.COURIER_LOGIN);
        response.then().assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);

        courierApi.deleteCourier(Util.COURIER);
    }

    @Test
    @DisplayName("Авторизация с не корректным логином")
    @Description("Авторизация курьером с не корректными логином и корректным паролем")
    public void testLoginCourierWrongLoginAndCorrectPass() {
        courierApi.createCourier(Util.COURIER);

        CourierLogin courierLogin = new CourierLogin(
                Util.COURIER_LOGIN.getLogin() + "Baa",
                Util.COURIER_LOGIN.getPassword()
        );
        Response response = courierApi.loginCourier(courierLogin);
        response.then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        courierApi.deleteCourier(Util.COURIER);
    }

    @Test
    @DisplayName("Авторизация с не корректным паролем")
    @Description("Авторизация курьером с корректным логином и не корректными паролем")
    public void testLoginCourierCorrectLoginAndWrongPass() {
        courierApi.createCourier(Util.COURIER);

        CourierLogin courierPassword = new CourierLogin(
                Util.COURIER_LOGIN.getLogin(),
                Util.COURIER_LOGIN.getPassword() + "B34"
        );
        Response response = courierApi.loginCourier(courierPassword);
        response.then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        courierApi.deleteCourier(Util.COURIER);
    }

    @Test
    @DisplayName("Авторизация с пустым полем логин")
    @Description("Авторизация курьером с не заполненым полем логин и заполненым корректными данными полем пароль")
    public void testLoginCourierWithoutLogin() {
        courierApi.createCourier(Util.COURIER);
        CourierLogin courierWithoutLogin = new CourierLogin(
                null,
                Util.COURIER_LOGIN.getPassword()
        );
        Response response = given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(courierWithoutLogin)
                .when()
                .post(Paths.COURIER_LOGIN_PATH);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        courierApi.deleteCourier(Util.COURIER);
    }

    @Test
    @DisplayName("Авторизация с пустым полем пароль")
    @Description("Авторизация курьером заполненым полем логин и с не заполненым полем пароль")
    public void testLoginCourierWithoutPass() {
        courierApi.createCourier(Util.COURIER);

        CourierLogin courierWithoutPass = new CourierLogin(
                Util.COURIER_LOGIN.getLogin(),
                null
        );
        Response response = given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(courierWithoutPass)
                .when()
                .post(Paths.COURIER_LOGIN_PATH);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        courierApi.deleteCourier(Util.COURIER);
    }

}
