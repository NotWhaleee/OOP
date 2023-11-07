import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.kozorez.FindSubstring;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
public class FindSubstringTest {
    @Test
    void checkLargeFile() {
        FindSubstring f = new FindSubstring();
        List<Integer> result = new ArrayList<>();
        List<Integer> answer = new ArrayList<>();
//        try {
//            result = f.find("input.txt", "aa");
//        }catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        assertEquals(answer, result);
        assertTrue(false);
    }
}
