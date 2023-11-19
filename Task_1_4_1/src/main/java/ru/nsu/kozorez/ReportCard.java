package ru.nsu.kozorez;

public class ReportCard {
    private final int number;
    private String lastName;
    private String firstName;
    private String patronymic;
    private final String speciality;
    private final int enrollmentYear;
    private int course = 1;
    private Exam[] exams_1st_course = {new Exam("Введение в алгебру и анализ"),
            new Exam("Введение в дискретную математику и математическую логику"),
            new Exam("Декларативное программирование"),
            new Exam("Императивное программирование"),
            new Exam("Иностранный язык"),
            new Exam("Цифровые платформы"),
            new Exam("История")};
    private Exam[] exams_2nd_course = {new Exam("Введение в искусственный интеллект"),
            new Exam("Дифференциальные уравнения и теория функций комплексного переменного"),
            new Exam("Иностранный язык"),
            new Exam("Модели вычислений"),
            new Exam("Объектно-ориентированное программирование"),
            new Exam("Операционные системы"),
            new Exam("Разработка программно-аппаратного комплекса для решения научных и прикладных задач (групповой проект)")};

    public ReportCard(int number, String lastName, String firstName, String patronymic, String speciality, int enrollmentYear) {
        this.number = number;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.enrollmentYear = enrollmentYear;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getMarks(int course) {
        String result = "";
        switch (course) {
            case 1:
                result += "1st course exams:\n";
                for (Exam exam : exams_1st_course) {
                    result = result.concat(exam.toString());
                }
                break;
            case 2:
                result += "2nd course exams:" + "\n";
                for (Exam exam : exams_2nd_course) {
                    result = result.concat(exam.toString());
                }
                break;
            case 3:
                //...
                break;
            case 4:
                //...
                break;
            default:
                System.out.println("Entered not valid course number\n");
        }
        return result;
    }

    public double getAvg() {
        int counter = 0;
        double avg = 0;
        if (course > 0) {
            for (Exam exam : exams_1st_course) {
                if (exam.getMark() != 0) {
                    avg += exam.getMark();
                    counter++;
                }
            }
        }

        if (course > 1) {
            for (Exam exam : exams_2nd_course) {
                if (exam.getMark() != 0) {
                    avg += exam.getMark();
                    counter++;
                }
            }
        }

        if (course > 2) {
            //...
        }
        if (course > 3) {
            //...
        }
        if (counter != 0) {
            return avg / counter;
        } else {
            return 0;
        }
    }

    public void setExamMark(int course, String subject, int mark, String date) {
        switch (course) {
            case 1:
                for (Exam exam : exams_1st_course) {
                    if (exam.getSubject().equals(subject)) {
                        exam.setMark(mark);
                        exam.setDate(date);
                        return;
                    }
                }
                System.out.println("Couldn't find the subject " + subject + "on 1st course");
                break;
            case 2:
                for (Exam exam : exams_2nd_course) {
                    if (exam.getSubject().equals(subject)) {
                        exam.setMark(mark);
                        exam.setDate(date);
                    }
                    return;
                }
                System.out.println("Couldn't find the subject " + subject + "on 2nd course");
                break;
            case 3:
                //..
                break;
            case 4:
                //..
                break;
            default:
                System.out.println("Incorrect course number");
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
