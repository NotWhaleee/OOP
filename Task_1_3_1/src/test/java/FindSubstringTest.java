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
    @DisplayName("Large file")
    public void checkLargeFile() throws IOException {
        File file = new File("largefile.txt");
        //String outputName = "outLarge.txt";


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

        FindSubstring findSubstr = new FindSubstring();
        findSubstr.find("largefile.txt", target, "outLarge.txt");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 50000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }


        File output = new File("outLarge.txt");

        byte[] bytes = new byte[(int) output.length()];
        FileInputStream fis = new FileInputStream(output);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        long[] res = new long[valueStr.length];
        for (int i = 0; i < valueStr.length; i++) {
            res[i] = Long.parseLong(valueStr[i]);
        }
        if (output.delete()) {
            System.out.println("Output file deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
        assertEquals(2000000000, res[0]);
    }

    @Test
    @DisplayName("String between buffers")
    public void checkStrBetweenBuffs() throws IOException {
        File file = new File("between.txt");
        //String outputName = "outBetween.txt";

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

        FindSubstring findSubstr = new FindSubstring();
        findSubstr.find("between.txt", target, "outBetween.txt");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1000000000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }

        File output = new File("outBetween.txt");

        byte[] bytes = new byte[(int) output.length()];
        FileInputStream fis = new FileInputStream(output);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        long[] res = new long[valueStr.length];
        for (int i = 0; i < valueStr.length; i++) {
            res[i] = Long.parseLong(valueStr[i]);
        }
        if (output.delete()) {
            System.out.println("Output file deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
        assertEquals(999998, res[0]);
    }

    @Test
    @DisplayName("Same characters file")
    public void checkSameCharacters() throws IOException {
        File file = new File("sameChar.txt");
        //String outputName = "outSame.txt";

        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        for (int i = 0; i < 2000; i++) {
            writer.print((char) ('a'));
        }

        writer.close();
        System.out.println(file.length() / (1024 * 1024) + "Mb");

        long startTime = System.nanoTime();
        final String target = "a";

        FindSubstring findSubstr = new FindSubstring();
        findSubstr.find("sameChar.txt", target, "outSame.txt");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1000000000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }


        File output = new File("outSame.txt");

        byte[] bytes = new byte[(int) output.length()];
        FileInputStream fis = new FileInputStream(output);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        long[] res = new long[valueStr.length];
        for (int i = 0; i < valueStr.length; i++) {
            res[i] = Long.parseLong(valueStr[i]);
        }
        if (output.delete()) {
            System.out.println("Output file deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
        if (output.delete()) {
            System.out.println("Output file deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
        long[] answer = new long[2000];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = i;
        }


        assertArrayEquals(answer, res);
    }

    @Test
    @DisplayName("One character file")
    public void check1Char() throws IOException {
        File file = new File("oneChar.txt");
        //String outputName = "outChar.txt";

        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        writer.print((char) ('a'));

        writer.close();
        System.out.println(file.length() / (1024 * 1024) + "Mb");

        long startTime = System.nanoTime();
        final String target = "a";

        FindSubstring findSubstr = new FindSubstring();
        findSubstr.find("oneChar.txt", target, "outChar.txt");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1000000000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }


        File output = new File("outChar.txt");

        byte[] bytes = new byte[(int) output.length()];
        FileInputStream fis = new FileInputStream(output);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        long[] res = new long[valueStr.length];
        for (int i = 0; i < valueStr.length; i++) {
            res[i] = Long.parseLong(valueStr[i]);
        }
        if (output.delete()) {
            System.out.println("Output file deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
        if (output.delete()) {
            System.out.println("Output file deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
        long[] answer = {0};


        assertArrayEquals(answer, res);
    }



}