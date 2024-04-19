package ru.kozorez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor {

    private String gitUrl;
    private String localPath;

    public Executor(String gitUrl, String localPath) {
        this.gitUrl = gitUrl;
        this.localPath = localPath;
    }

    public void downloadProject() {
        executeCommand("git clone " + gitUrl + " " + localPath);
    }

    public boolean buildProjectWithGradle() throws IOException {
        //executeCommand(/*"cd " + */"./"+localPath + "/Task_1_1_1/gradlew build");

        //executeCommand("chmod +x clonedRep/Task_1_1_1/gradlew");
        //CommandResult result = executeCommand("./" + localPath + "/Task_1_1_1/gradlew build");
        //executeCommand("chmod +x clonedRep/Task_1_1_1/gradlew");
        CommandResult result = executeCommand("./" + localPath + "/Task_1_2_1/gradlew build");

        if (result.isSuccess()) {
            System.out.println("Build project: SUCCESS");
            System.out.println(result.getOutput());
            return true;
        } else {
            System.out.println("Build project: FAILED");

            // Поиск строки с ошибкой сборки
            String output = result.getOutput();
            String[] lines = output.split("\n");
            for (String line : lines) {
                if (line.contains("BUILD FAILED")) {
                    System.out.println("Build error details:");
                    System.out.println(line);
                    break;
                }
            }

            return false;
        }
    }

    public boolean generateJavadoc() {
        CommandResult result = executeCommand("./" + localPath + "/Task_1_2_1/gradlew javadoc");
        System.out.println("Generate Javadoc: " + (result.isSuccess() ? "SUCCESS" : "FAILED"));
        System.out.println(result.getOutput());
        return result.isSuccess();
    }
    public boolean touchGFrass() {
        CommandResult result = executeCommand("touch " + localPath + "/Task_1_2_1/grass");
        System.out.println("Touch grass: " + (result.isSuccess() ? "SUCCESS" : "FAILED"));
        System.out.println(result.getOutput());
        return result.isSuccess();
    }

    public void runTests() {
        executeCommand("./" + localPath + "/Task_1_2_1/gradlew test");
    }

    private CommandResult executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        boolean isSuccess = false;

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                isSuccess = true;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new CommandResult(isSuccess, output.toString());


/*        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitVal = process.waitFor();
            if (exitVal != 0) {
                System.err.println("Command execution failed: " + command);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
