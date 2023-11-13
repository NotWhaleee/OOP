import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kozorez.FindSubstring;


/**
 * Tester class.
 */
public class FindSubstringTest {

    @Test
    @DisplayName("String between buffers")
    public void checkStrBetweenBuffs() throws IOException {
        File file = new File("file.txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        for (int i = 0; i < 999998; i++) {
            writer.print((char) ('a'));
        }
        writer.print("someline");
        for (int i = 0; i < 1000000; i++) {
            writer.print((char) ('a'));
        }
        writer.close();
        System.out.println(file.length() / (1024 * 1024) + "Mb");

        long startTime = System.nanoTime();
        final String target = "someline";

        FindSubstring Find = new FindSubstring();
        Find.find("file.txt", target);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1000000000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }


        File output = new File("output.txt");
        byte[] bytes = new byte[(int) output.length()];
        FileInputStream fis = new FileInputStream(output);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        int[] tail = new int[valueStr.length];
        for (int i = 0; i < valueStr.length; i++) {
            tail[i] = Integer.parseInt(valueStr[i]);
        }
        assertEquals(999998, tail[0]);
    }

    @Test
    @DisplayName("Same characters file")
    public void checkSameCharacters() throws IOException {
        File file = new File("file.txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        for (int i = 0; i < 2000; i++) {
            writer.print((char) ('a'));
        }

        writer.close();
        System.out.println(file.length() / (1024 * 1024) + "Mb");

        long startTime = System.nanoTime();
        final String target = "a";

        FindSubstring Find = new FindSubstring();
        Find.find("file.txt", target);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1000000000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }


        File output = new File("output.txt");
        byte[] bytes = new byte[(int) output.length()];
        FileInputStream fis = new FileInputStream(output);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        int[] tail = new int[valueStr.length];
        for (int i = 0; i < valueStr.length; i++) {
            tail[i] = Integer.parseInt(valueStr[i]);
        }
        int[] answer = new int[2000];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = i;
        }
        assertArrayEquals(answer, tail);
    }


    @Test
    @DisplayName("Large file")
    public void checkLargeFile() throws IOException {
        File file = new File("largefile.txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        Random random = new Random();
        for (int i = 0; i < 2000000000; i++) {
            writer.print((char) ('a' + random.nextInt(26)));
        }
        writer.print("something");
        for (int i = 0; i < 2000000000; i++) {
            writer.print((char) ('a' + random.nextInt(26)));
        }

        writer.close();
        System.out.println(file.length() / (1024 * 1024) + "Mb");

        long startTime = System.nanoTime();
        final String target = "something";

        FindSubstring Find = new FindSubstring();
        Find.find("largefile.txt", target);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1000000000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }


        File output = new File("output.txt");
        byte[] bytes = new byte[(int) output.length()];
        FileInputStream fis = new FileInputStream(output);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        int[] tail = new int[valueStr.length];
        for (int i = 0; i < valueStr.length; i++) {
            tail[i] = Integer.parseInt(valueStr[i]);
        }
        assertEquals(2000000000, tail[0]);
    }
}