package ru.nsu.kozorez;

import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "", "Computer science", 2022);
        student.setSemester(1);
        System.out.println(student);
        student.setExamMark(1, "Введение в алгебру и анализ", 5, "01.06.2022", "Васкевич");
        student.setExamMark(1, "Введение в дискретную математику и математическую логику", 4, "03.06.2022", "Стукачев");
        System.out.println(student.getMarks(1));
        System.out.println("Student's avarege grade is: " + student.getAvg());
        student.setMegaScholarship();
        student.getExam(1,"Введение в алгебру и анализ");
        student.setSemester(2);
        student.testFillMarks(2);
        System.out.println(student.getMarks(2));
        student.setMegaScholarship();
    }
}