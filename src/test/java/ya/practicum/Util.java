package ya.practicum;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class Util {
    private static final PodamFactory factory = new PodamFactoryImpl();
    public static final Courier COURIER = factory.manufacturePojo(Courier.class);
    public static final CourierLogin COURIER_LOGIN = getCourierLogin(COURIER);

    public static CourierLogin getCourierLogin(Courier courier) {
        return new CourierLogin(courier.getLogin(), courier.getPassword());
    }
}
