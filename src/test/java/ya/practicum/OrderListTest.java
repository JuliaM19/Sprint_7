package ya.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ya.practicum.api.CourierApi;

import static org.hamcrest.CoreMatchers.*;

public class OrderListTest {
    private final CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов")
    public void orderListTest() {
        Response response = courierApi.getOrders();

        response.then().assertThat()
                .body("orders", notNullValue())
                .and()
                .body("orders.size()", is(not(0)))
                .and()
                .statusCode(HttpStatus.SC_OK);
    }
}
