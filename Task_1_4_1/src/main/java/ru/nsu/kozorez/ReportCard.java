package ru.nsu.kozorez;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ReportCard {
    private final int number;
    private String lastName;
    private String firstName;
    private String patronymic;
    private final String speciality;
    private final int enrollmentYear;
    private int semester = 1;
    private boolean megaScholarship = false;


    //private Exam[][] exams = {exams_1st_sem, exams_2nd_sem, exams_3rd_sem, exams_4th_sem};
    private Exam[][] exams;
    public ReportCard(int number, String lastName, String firstName, String patronymic, String speciality, int enrollmentYear) {
        this.number = number;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.enrollmentYear = enrollmentYear;
    }

    public void setExams(Exam[][] exams) {
        this.exams = exams;
    }

    public int getNumber() {
        return number;
    }

    public String getSpeciality() {
        return speciality;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }


    public int getSemester() {
        return semester;
    }

    public boolean isMegaScholarship() {
        return megaScholarship;
    }
    public void getExam(int semester, String subject){
        if (semester < 1 || semester > exams.length) {
            throw new ArrayIndexOutOfBoundsException("Incorrect semester number\n");
        }
        for (int i = 0; i < exams[semester - 1].length; i++) {
            if (exams[semester - 1][i].getSubject().equals(subject)) {
                System.out.println(exams[semester-1][i]);
                return;
            }
        }
        System.out.println("Couldn't find the subject " + subject + "on " + semester + " semester");
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    public void setMegaScholarship(){
        if(semester == 1){
            System.out.println("Unable to set increased scholarship in the 1st semester");
            megaScholarship = false;
            return;
        }

        for (Exam exam : exams[semester - 2]) {
            if(exam.getMark()!= 5){
                System.out.println("Unable to set increased scholarship in the " + semester +" semester. Not all exams were passed perfectly:\n" + exam);
                megaScholarship = false;
                return;
            }
        }
        megaScholarship = true;
        System.out.println("Increased scholarship has been set successfully");
    }

    public String getMarks(int semester) {
        if (semester < 1 || semester > exams.length) {
            throw new ArrayIndexOutOfBoundsException("Incorrect semester number!\n");
        }
        String result = "";
        result += semester + " semester exams:\n";
        for (Exam exam : exams[semester - 1]) {
            result = result.concat(exam.toString());
        }
        return result;
    }

    public double getAvg() {
        int counter = 0;
        double avg = 0;
        HashSet<String> subjects = new HashSet<String>();
        for (int i = semester - 1; i >= 0; i--) {
            for (int j = 0; j < exams[i].length; j++) {
                if (exams[i][j].getMark() != 0 && !subjects.contains(exams[i][j].getSubject())) {
                    subjects.add(exams[i][j].getSubject());
                    avg += exams[i][j].getMark();
                }
            }
        }
        if (subjects.isEmpty()) {
            return 0;
        } else {
            return avg / subjects.size();
        }
    }

    public void setExamMark(int semester, String subject, int mark, String date, String teacher) {
        if (semester < 1 || semester > exams.length) {
            throw new ArrayIndexOutOfBoundsException("Incorrect semester number\n");
        }
        for (int i = 0; i < exams[semester - 1].length; i++) {
            if (exams[semester - 1][i].getSubject().equals(subject)) {
                exams[semester - 1][i].setMark(mark);
                exams[semester - 1][i].setDate(date);
                exams[semester - 1][i].setTeacher(teacher);
                return;
            }
        }
        System.out.println("Couldn't find the subject " + subject + "on " + semester + " semester");
    }

    public void testFillMarks(int semester){
        if(semester < 1 || semester > exams.length){
            throw new ArrayIndexOutOfBoundsException("Incorrect semester number\n");
        }
        for (int i = 0; i < exams[semester-1].length; i++) {
            exams[semester-1][i].setMark(5);
            exams[semester-1][i].setTeacher("Teacher");
            exams[semester-1][i].setDate("01.01.2023");
        }
    }


    @Override
    public String toString() {
        String result;
        result = "Report Card number: " + number + "\n" +
                "Last name: " + lastName + "\n" +
                "First name: " + firstName + "\n" +
                "Patronimyc: " + patronymic + "\n" +
                "Speciality: " + speciality + "\n";
        return result;
    }
}
