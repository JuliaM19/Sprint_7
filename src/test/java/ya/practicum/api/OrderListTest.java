package ya.practicum.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    @BeforeClass
    public static void beforeClass() throws Exception {
        RestAssured.baseURI = Paths.BASE_URL;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов")
    public void orderListTest() {

        Response response = given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .when()
                .get(Paths.ORDER_PATH);

        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);
    }
}
