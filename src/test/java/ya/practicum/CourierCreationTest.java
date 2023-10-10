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

public class CourierCreationTest {

    private final CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Повторное cоздание курьера")
    @Description("Пытаемся создать курьера с одними и теми же данными второй раз")
    public void testNewCourierRepeat() {
        courierApi.createCourier(Util.COURIER);

        Response response = courierApi.createCourier(Util.COURIER);

        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(HttpStatus.SC_CONFLICT);

        courierApi.deleteCourier(Util.COURIER);
    }

    @Test
    @DisplayName("Ошибка создания курьера без логина")
    @Description("Пытаемся создать курьера без указания логина")
    public void testCWithoutLogin() {
        Courier courierWithoutLogin = new Courier(
                null,
                Util.COURIER.getPassword(),
                Util.COURIER.getFirstName()
        );

        Response response = courierApi.createCourier(courierWithoutLogin);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Ошибка  создания курьера без пароля")
    @Description("Пытаемся создать курьера без указания пароля")
    public void courierWithoutPassword() {
        Courier courierWithoutPassword = new Courier(
                Util.COURIER.getLogin(),
                null,
                Util.COURIER.getFirstName()
        );
        Response response = courierApi.createCourier(courierWithoutPassword);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }


    @Test
    @DisplayName("Cоздание курьера без имени")
    @Description("Создаем курьера без имени")
    public void courierWithoutFirstName() {
        Courier courierWithoutFirstName = new Courier(
                Util.COURIER.getLogin(),
                Util.COURIER.getPassword(),
                null
        );
        Response response = courierApi.createCourier(courierWithoutFirstName);
        response.then().assertThat()
                .body("ok", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_CREATED);

        courierApi.deleteCourier(Util.COURIER);
    }

    @Test
    @DisplayName("Успешное Создание курьера")
    @Description("Создаем курьера с логином? паролем и именем")
    public void testNewCourier() {
        Response response = given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body(Util.COURIER)
                .when()
                .post(Paths.COURIER_CREATE_PATH);

        response.then().assertThat().body("ok", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_CREATED);

        courierApi.deleteCourier(Util.COURIER);
    }

}
