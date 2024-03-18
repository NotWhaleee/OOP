import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kozorez.Json;
import ru.kozorez.SetUpPizzeria;

/**
 * Test operations with json.
 */
class JsonTest {
    private static Json json;
    private static SetUpPizzeria pizzeria;

    @BeforeEach
    void setUp() {
        json = new Json("testJson.json");
        pizzeria = new SetUpPizzeria();
    }

    @Test
    void testParseJson() {

        try {
            pizzeria = json.parseJson();
            System.out.println(pizzeria);
            assertEquals("SetUpPizzeria{bakers="
                            + "[Baker{speed=800, isBusy=true},"
                            + " Baker{speed=500, isBusy=true},"
                            + " Baker{speed=300, isBusy=true}],"
                            + " couriers=[Courier{capacity=1, carries=0, isBusy=false, speed=400},"
                            + " Courier{capacity=2, carries=0, isBusy=false, speed=600}],"
                            + " storage=Storage{capacity=10, stored=0, delivered=0},"
                            + " openTime=7000, orders=10}",
                    pizzeria.toString());
        } catch (IOException e) {
            fail("Exception occurred while parsing JSON: " + e.getMessage());
        }
    }

    @AfterAll
    static void testWriteJson() {
        json.writeJson(pizzeria);
    }
}
