package ru.kozorez;

public class Main {

    public static void main(String[] args) {
        String gitUrl = "https://github.com/MurenMurenus/OOP.git";
        String localPath = "clonedRep";

        Executor manager = new Executor(gitUrl, localPath);

        // Скачиваем репозиторий
        //manager.downloadProject();


        // Собираем проект с помощью Gradle
        try {
            manager.buildProjectWithGradle();

        } catch (Exception e) {
            System.out.println(e);
        }

        // Генерируем Javadoc
        manager.generateJavadoc();

        // Запускаем тесты
        manager.runTests();

        manager.touchGFrass();
    }

}
