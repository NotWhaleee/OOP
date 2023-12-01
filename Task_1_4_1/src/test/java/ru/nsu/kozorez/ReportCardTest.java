package ru.nsu.kozorez;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test for report card class.
 */
public class ReportCardTest {
    @Test
    void setUpExams() {
        Exam[] exams1stsem = {new Exam("История")};
        Exam[] exams2nsem = {new Exam("Введение в алгебру и анализ")};

        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "",
                "Computer science", 2022);
        student.setExams(new Exam[][]{exams1stsem, exams2nsem});
        String result = "Report Card number: 220111\n" +
                        "Last name: Oconnell\n" +
                        "First name: Michael\n" +
                        "Patronimyc: \n" +
                        "Speciality: Computer science\n";
        assertEquals(result, student.toString());
    }

    @Test
    void setExamTest() {
        Exam[] exams1stsem = {new Exam("Введение в дискретную математику и математическую логику")};

        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "",
                "Computer science", 2022);
        student.setExams(new Exam[][]{exams1stsem});
        student.setExamMark(1, "Введение в дискретную математику и математическую логику",
                4, "03.06.2022", "Преподаватель");
        String result = "1 semester exams:\n" +
                        "Subject: Введение в дискретную математику и математическую логику\n" +
                        "mark: GOOD\n" +
                        "date: 03.06.2022\n" +
                        "teacher: Преподаватель\n" +
                        "\n";
        assertEquals(result, student.getMarks(1));
    }

    @Test
    void getAvg() {
        Exam[] exams1stsem = {new Exam("Введение в дискретную математику и математическую логику")};
        Exam[] exams2nsem = {new Exam("Введение в алгебру и анализ")};


        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "",
                "Computer science", 2022);
        student.setExams(new Exam[][]{exams1stsem, exams2nsem});
        student.setExamMark(1, "Введение в дискретную математику и математическую логику",
                4, "03.06.2022", "Преподаватель");
        student.setExamMark(2, "Введение в алгебру и анализ", 5, "01.01.2023", "Преподаватель");
        double result = 4.5;
        assertEquals(result, student.getAvg());
    }

    @Test
    void setMegaScholarship() {
        Exam[] exams1stsem = {new Exam("Введение в дискретную математику и математическую логику")};
        Exam[] exams2nsem = {new Exam("Введение в алгебру и анализ")};


        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "",
                "Computer science", 2022);
        student.setExams(new Exam[][]{exams1stsem, exams2nsem});
        student.setExamMark(1, "Введение в дискретную математику и математическую логику",
                4, "03.06.2022", "Преподаватель");
        assertFalse(student.setMegaScholarship());
        student.setExamMark(2, "Введение в алгебру и анализ", 5, "01.01.2023", "Преподаватель");
        student.setSemester(3);
        assertTrue(student.setMegaScholarship());
    }

    @Test
    void getRedDiploma() {
        Exam[] exams1stsem = {new Exam("Введение в дискретную математику и математическую логику")};
        Exam[] exams2nsem = {new Exam("Введение в алгебру и анализ")};


        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "",
                "Computer science", 2022);
        student.setExams(new Exam[][]{exams1stsem, exams2nsem});
        student.setExamMark(1, "Введение в дискретную математику и математическую логику",
                5, "03.06.2022", "Преподаватель");
        student.setExamMark(2, "Введение в алгебру и анализ", 5, "01.01.2023", "Преподаватель");
        student.setFinalWorkMark(5);
        assertTrue(student.getBloodyDiploma());
    }

    @Test
    void failRedDiploma() {
        Exam[] exams1stsem = {new Exam("Введение в дискретную математику и математическую логику")};
        Exam[] exams2nsem = {new Exam("Введение в алгебру и анализ")};


        ReportCard student = new ReportCard(220111, "Oconnell", "Michael", "",
                "Computer science", 2022);
        student.setExams(new Exam[][]{exams1stsem, exams2nsem});
        student.setExamMark(1, "Введение в дискретную математику и математическую логику",
                3, "03.06.2022", "Преподаватель");
        student.setExamMark(2, "Введение в алгебру и анализ", 5, "01.01.2023", "Преподаватель");
        student.setFinalWorkMark(5);
        assertFalse(student.getBloodyDiploma());
    }

}
