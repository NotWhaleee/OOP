package ru.nsu.kozorez;


import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * starts the application.
 */
public class Main {
    @Option(name = "-add", usage = "add a note")
    private boolean add;

    @Option(name = "-show", usage = "show a note")
    private boolean show;

    @Option(name = "-rm", usage = "delete a note")
    private boolean rm;
    @Argument
    private List<String> arguments = new ArrayList<>();


    /**
     * starts the application.
     *
     * @param args arguments for the notebook.
     * @throws IOException exception.
     */
    public static void main(String[] args) throws IOException {

        Main main = new Main();
        try {
            //main.parseArgs(args);
            main.parseArgs(args);
        } catch (IOException ioe) {
            //no-op
        }
        main.handleArgs();
    }

    /**
     * do some operations depending on the arguments.
     */
    public void handleArgs() {
        Date currentDate = new Date();
        Notebook myDiarDiary = new Notebook();

        if (add) {
            if (arguments.size() != 2) {
                throw new IllegalArgumentException("add takes 2 arguments");
            }
            myDiarDiary.add(arguments.get(0), arguments.get(1), currentDate);
        }
        if (rm) {
            if (arguments.size() != 1) {
                throw new IllegalArgumentException("add takes 2 argument");
            }
            myDiarDiary.delete(arguments.getFirst());
        }
        if (show) {
            if (arguments.isEmpty()) {
                myDiarDiary.show();
            } else if (arguments.size() > 2) {
                String[] keyWords = new String[arguments.size() - 2];
                for (int i = 2; i < arguments.size(); i++) {
                    keyWords[i - 2] = arguments.get(i);
                }
                Date date1 = new Date(Long.parseLong(arguments.get(0)));
                Date date2 = new Date(Long.parseLong(arguments.get(1)));
                myDiarDiary.show(date1, date2, keyWords);
            } else {
                throw new IllegalArgumentException("show takes 0 or more than 3 arguments");
            }

        }
    }


    /**
     * parses the arguments form the command line.
     *
     * @param args arguments.
     * @throws IOException exception.
     */
    public void parseArgs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        parser.setUsageWidth(80);
        try {
            // parse the arguments.
            parser.parseArgument(args);

        } catch (CmdLineException e) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
        }
    }
}