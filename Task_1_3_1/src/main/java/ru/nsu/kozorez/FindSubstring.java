package ru.nsu.kozorez;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for finding substrings.
 */
public class FindSubstring {

    /**
     * findes indexes of substrings.
     *
     * @param inputFile  input file
     * @param outputFile output file
     * @param substring substring
     * @throws IOException couldn't open the file
     */
    public static void find(String inputFile, String substring,
                            String outputFile) throws IOException {
        Charset encoding = StandardCharsets.UTF_8;

        File file = new File(inputFile);
        handleFile(file, encoding, substring, outputFile);
    }

    /**
     * opens file.
     *
     * @param file      input file
     * @param encoding  encoding(UTF8)
     * @param substring substring
     * @param outputFile output file
     * @throws IOException couldn't open the file
     */
    private static void handleFile(File file, Charset encoding,
                                   String substring, String outputFile) throws IOException {
        try (InputStream in = new FileInputStream(file);
             Reader reader = new InputStreamReader(in, encoding);
             // buffer for efficiency
             Reader buffer = new BufferedReader(reader)) {

            handleCharacters(buffer, substring, outputFile);
        }
    }

    /**
     * Reads characters from the buffer.
     *
     * @param reader      The reader to read characters from.
     * @param substring   The substring to search for in the buffer.
     * @param outputFile  The file to write the output to.
     * @throws IOException If an I/O error occurs.
     */
    private static void handleCharacters(Reader reader, String substring,
                                         String outputFile) throws IOException {
        long counter = 0;
        FileWriter myWriter = new FileWriter(outputFile);

        int[] occurrences = new int[substring.length()];
        List<Long> positions = new ArrayList<>();

        char[] buffer = new char[1000000];
        int numRead;

        while ((numRead = reader.read(buffer)) != -1) {
            for (int i = 0; i < numRead; i++) {
                counter++;
                handleCharacter(buffer[i], substring, occurrences, positions, counter);
            }

            writePositions(myWriter, positions);
            positions.clear();
        }

        myWriter.close();
    }

    private static void handleCharacter(char character, String substring,
                                        int[] occurrences, List<Long> positions, long counter) {
        for (int i = 0; i < occurrences.length; i++) {
            if (character == substring.charAt(occurrences[i])) {
                occurrences[i]++;
                if (occurrences[i] == substring.length()) {
                    positions.add(counter - substring.length());
                    resetOccurrences(occurrences, i);
                    break;
                }
            } else {
                if (substring.indexOf(character) == -1) {
                    Arrays.fill(occurrences, 0);
                } else {
                    shiftOccurrences(occurrences, i);
                }
                break;
            }
        }
    }

    private static void resetOccurrences(int[] occurrences, int index) {
        occurrences[index] = 0;
        Arrays.sort(occurrences);
        reverse(occurrences);
    }

    private static void shiftOccurrences(int[] occurrences, int index) {
        System.arraycopy(occurrences, index + 1,
                occurrences, index, occurrences.length - 1 - index);
        occurrences[occurrences.length - 1] = 0;
    }

    private static void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    private static void writePositions(FileWriter writer,
                                       List<Long> positions) throws IOException {
        for (Long position : positions) {
            writer.write(position + " ");
        }
    }

}

