
package ru.kozorez;

import groovy.lang.GroovyShell;
import ru.kozorez.groovy.CourseConfig;
import ru.kozorez.groovy.Group;
import ru.kozorez.groovy.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
public class Checker {

    String configFile = "/home/notwhalee/Documents/Studies/2nd course/OOP/OOP/Task_2_4_1/src/main/java/ru/kozorez/groovy/config.groovy";


    public void run() {
        CourseConfig config = new ConfigParser(configFile).getCourse();

        for (Group group : config.getGroups()) {
            for (Student student : group.getStudents()) {
                downloadRepository(student.getRepoUrl());
                compileJavaProjects(student.getRepoUrl());
            }
        }
    }

    public static void downloadRepository(String repoUrl) {
        String command = "git clone " + repoUrl;
        executeCommand(command);
    }

    public static void compileJavaProjects(String repoUrl) {
        Path repoPath = Paths.get(repoUrl.substring(repoUrl.lastIndexOf('/') + 1));
        try {
            List<Path> javaFiles = Files.walk(repoPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .toList();

            for (Path javaFile : javaFiles) {
                String command = "javac " + javaFile.toString();
                executeCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

