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
import java.util.Collections;
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
    public void find(String inputFile, String substring, String outputFile) throws IOException {
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
    private static void handleFile(File file,
                                   Charset encoding, String substring, String outputFile) throws IOException {
        try (InputStream in = new FileInputStream(file);
             Reader reader = new InputStreamReader(in, encoding);
             // buffer for efficiency
             Reader buffer = new BufferedReader(reader)) {

            handleCharacters(buffer, substring, outputFile);
        }
    }

    /**
     * reads characters from the buffer.
     *
     * @param reader    reader
     * @param substring substring
     * @param outputFile output file
     * @throws IOException couldn't open the file
     */
    private static void handleCharacters(Reader reader, String substring, String outputFile) throws IOException {
        long counter = 0;

        FileWriter myWriter = new FileWriter(outputFile);

        int[] occurrences = new int[substring.length()];
        List<Long> answer = new ArrayList<>();

        int r;
        char[] cbuf = new char[1000000];

        while ((r = reader.read(cbuf)) != -1) {
            for (int cRead = 0; cRead < r; cRead++) {
                counter++;
                for (int i = 0; i < occurrences.length; i++) {
                    if (cbuf[cRead] == substring.charAt(occurrences[i])) {
                        occurrences[i]++;
                        if (occurrences[i] == substring.length()) {
                            answer.add((long) (counter - substring.length()));
                            if (occurrences[i] == 1) { //if read 1st symbol
                                occurrences[i] = 0; //nulling substring search
                                break;
                            }
                            occurrences[i] = 0; //nulling substring search
                            i = -1; // start from 0 go through the substring search array again

                            //sort array in reverse order
                            occurrences = Arrays.stream(occurrences).boxed()
                                    .sorted(Collections.reverseOrder())
                                    .mapToInt(Integer::intValue)
                                    .toArray();
                            continue;
                        }
                        if (occurrences[i] == 1) { //if read 1st symbol
                            break;
                        }
                    } else {
                        //if char is not in substr, null all occurs
                        if (substring.indexOf(cbuf[cRead]) == -1) {
                            Arrays.fill(occurrences, 0);
                            break;
                        } else {
                            for (int j = i; j < occurrences.length - 1; j++) {
                                occurrences[j] = occurrences[j + 1];
                                if (occurrences[j + 1] == 0) {
                                    break;
                                }
                            }
                            occurrences[occurrences.length - 1] = 0;
                        }
                    }
                }
            }

            for (Long integer : answer) { //writes ans from the list before reading new buf
                myWriter.write(integer + " ");
            }
            answer.clear();

        }

        myWriter.close();
    }
}

