package ya.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Util {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static final Courier COURIER = readFile("src/test/resources/NewCourier.json", Courier.class);
    public static final CourierLogin COURIER_LOGIN = readFile("src/test/resources/LoginCourier.json", CourierLogin.class);

    private static <T> T readFile(String path, Class<T> clazz) {
        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
