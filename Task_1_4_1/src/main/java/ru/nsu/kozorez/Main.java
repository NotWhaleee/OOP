package ru.nsu.kozorez;

import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        Exam[] exams_1st_sem = {new Exam("Введение в алгебру и анализ"),
                                                                                            new Exam("Введение в дискретную математику и математическую логику"),
                new Exam("Декларативное программирование"),
                new Exam("Императивное программирование"),
                new Exam("Основы культуры речи"),
                new Exam("История")};
        Exam[] exams_2nd_sem = {new Exam("Введение в алгебру и анализ"),
                new Exam("Введение в дискретную математику и математическую логику"),
                new Exam("Декларативное программирование"),
                new Exam("Императивное программирование"),
                new Exam("Иностранный язык"),
                new Exam("Цифровые платформы")};
        Exam[] exams_3rd_sem = {new Exam("Введение в искусственный интеллект"),
                new Exam("Дифференциальные уравнения и теория функций комплексного переменного"),
                new Exam("Иностранный язык"),
                new Exam("Модели вычислений"),
                new Exam("Объектно-ориентированное программирование"),
                new Exam("Операционные системы"),
                new Exam("Разработка программно-аппаратного комплекса для решения научных и прикладных задач (групповой проект)")};
        Exam[] exams_4th_sem = {new Exam("Введение в аналоговую электронику и технику измерений"),
                new Exam("Введение в компьютерные сети"),
                new Exam("Деловой английский язык"),
                new Exam("Модели вычислений"),
                new Exam("Объектно-ориентированное программирование"),
                new Exam("Встроенные цифровые системы управления"),
                new Exam("Разработка программно-аппаратного комплекса для решения научных и прикладных задач (групповой проект)"),
                new Exam("Контейнеры и системы оркестрации"),
                new Exam("Теория параллелизма"),
                new Exam("Философия")};

        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "", "Computer science", 2022);
        student.setExams(new Exam[][]{exams_1st_sem, exams_2nd_sem, exams_3rd_sem, exams_4th_sem});
        student.setSemester(1);
        System.out.println(student);
        student.setExamMark(1, "Введение в алгебру и анализ", 5, "01.06.2022", "Преподаватель");
        student.setExamMark(1, "Введение в дискретную математику и математическую логику", 4, "03.06.2022", "Преподаватель");
        System.out.println(student.getMarks(1));
        System.out.println("Student's avarege grade is: " + student.getAvg());
        student.setMegaScholarship();
        student.getExam(1, "Введение в алгебру и анализ");
        student.setSemester(2);
        student.testFillMarks(2);
        System.out.println(student.getMarks(2));
        student.setMegaScholarship();
    }
}