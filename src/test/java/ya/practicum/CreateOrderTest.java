package ya.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ya.practicum.api.CourierApi;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final CourierApi courierApi = new CourierApi();
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа. " +
            "Тестовые данные: {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}")
    public static Object[][] getTestData() {
        // генерация тестовых данных
        return new Object[][]{
                {"Анна", "Марусина", "Москва ул Ленина 12", "Горьковская", "89005553635", 24, "2020-06-06", "не звонить", new String[]{}},
                {"Илья", "Петрова", "Санкт-Петербург ул Ленина 11", "Горьковская", "89005553678", 48, "2020-06-06", "не звонить", new String[]{"BLACK", "GREY"}},
                {"Анна", "Марусина", "Москва ул Ленина 12", "Горьковская", "89005553635", 24, "2020-06-06", "не звонить", new String[]{"BLACK"}},
                {"Илья", "Петрова", "Санкт-Петербург ул Ленина 11", "Горьковская", "89005553678", 3, "2020-06-06", "не звонить", new String[]{"GREY"}},
        };

    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с различными цветами или без них")
    public void createOrder() {
        Order order = new Order(
                this.firstName,
                this.lastName,
                this.address,
                this.metroStation,
                this.phone,
                this.rentTime,
                this.deliveryDate,
                this.comment,
                this.color
        );
        Response response = courierApi.createOrder(order);

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_CREATED);
    }

}