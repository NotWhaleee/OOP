import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.kozorez.IsPrime;

public class IsPrimeTest {


    @Test
    void testSimple() {
        IsPrime simple = new IsPrime();

        assertTrue(simple.isPrime(1559861749));
    }

}
