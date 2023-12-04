package ru.nsu.kozorez;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * class for working with report cards.
 */
public class ReportCard {
    private final int number;
    private String lastName;
    private String firstName;
    private String patronymic;
    private final String speciality;
    private final int enrollmentYear;
    private int semester = 1;
    private boolean megaScholarship = false;

    private int finalWorkMark = 0;


    //private Exam[][] exams = {exams_1st_sem, exams_2nd_sem, exams_3rd_sem, exams_4th_sem};
    private Exam[][] exams;

    /**
     * create report card.
     *
     * @param number         number of report card.
     * @param lastName       last name.
     * @param firstName      first name.
     * @param patronymic     patronimic (optional).
     * @param speciality     name of speciality.
     * @param enrollmentYear enrollment year. int.
     */
    public ReportCard(int number, String lastName, String firstName,
                      String patronymic, String speciality, int enrollmentYear) {
        this.number = number;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.enrollmentYear = enrollmentYear;
    }

    /**
     * sets exams.
     *
     * @param exams exams
     */
    public void setExams(Exam[][] exams) {
        this.exams = exams;
    }

    /**
     * gets number of report card.
     *
     * @return int.
     */
    public int getNumber() {
        return number;
    }

    /**
     * gets name of speciality.
     *
     * @return string.
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * gets enrollment year.
     *
     * @return int.
     */
    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    /**
     * gets last name.
     *
     * @return string.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * gets first name.
     *
     * @return string.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * gets patronymic (could be empty).
     *
     * @return string.
     */
    public String getPatronymic() {
        return patronymic;
    }


    /**
     * gets current students semester.
     *
     * @return int.
     */
    public int getSemester() {
        return semester;
    }

    /**
     * can a student get increased scholarship.
     *
     * @return boolean.
     */
    public boolean isMegaScholarship() {
        return megaScholarship;
    }

    /**
     * gets exam.
     *
     * @param semester semester of the exam.
     * @param subject  name of subject.
     */
    public void getExam(int semester, String subject) {
        if (semester < 1 || semester > exams.length) {
            throw new ArrayIndexOutOfBoundsException("Incorrect semester number\n");
        }
        Optional<Exam> exam = Arrays.stream(exams[semester - 1])
                .filter(e -> e.getSubject().equals(subject))
                .findFirst();
        if (exam.isPresent()) {
            System.out.println(exam.get());
        } else {
            System.out.println("Couldn't find the subject "
                    + subject + "on " + semester + " semester");
        }
    }

    /**
     * sets last name.
     *
     * @param lastName last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * gets first name.
     *
     * @param firstName first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * sets patronimyc (optional).
     *
     * @param patronymic patronymic.
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * gets number of current semester.
     *
     * @param semester int.
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * gets finall work mark.
     *
     * @return int.
     */
    public int getFinalWorkMark() {
        return finalWorkMark;
    }

    /**
     * sets final work mark.
     *
     * @param finalWorkMark mark.
     * @throws IllegalArgumentException only in a range from 2 to 5.
     */
    public void setFinalWorkMark(int finalWorkMark) throws IllegalArgumentException {
        if (finalWorkMark < 2 || finalWorkMark > 5) {
            throw new IllegalArgumentException("Incorrect mark. "
                    + "Mark can be only in a range from 2 to 5");
        }
        this.finalWorkMark = finalWorkMark;
    }

    /**
     * sets increased scholarship, if possible.
     */
    public boolean setMegaScholarship() {
        if (semester == 1) {
            System.out.println("Unable to set increased scholarship in the 1st semester");
            megaScholarship = false;
            return false;
        }
        for (Exam exam : exams[semester - 2]) {
            if (exam.getMark() != 5) {
                System.out.println("Unable to set increased scholarship in the "
                        + semester + " semester. Not all exams were passed perfectly:\n" + exam);
                megaScholarship = false;
                return false;
            }
        }
        megaScholarship = true;
        System.out.println("Increased scholarship has been set successfully");
        return true;
    }

    /**
     * gets all marks for the semester.
     *
     * @param semester number of semester.
     * @return string of exams.
     */
    public String getMarks(int semester) {
        if (semester < 1 || semester > exams.length) {
            throw new ArrayIndexOutOfBoundsException("Incorrect semester number!\n");
        }
        String result = Arrays.stream(exams[semester - 1])
                .map(Exam::toString)
                .collect(Collectors.joining());
        return semester + " semester exams:\n" + result;
    }

    /**
     * gets avg mark of the diploma.
     *
     * @return double.
     */
    public double getAvg() {
        double avg = 0;
        HashSet<String> subjects;
        subjects = new HashSet<String>();
        for (int i = exams.length - 1; i >= 0; i--) {
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

    /**
     * sets an exam mark.
     *
     * @param semester number of the semester.
     * @param subject  name of the subject.
     * @param mark     mark.
     * @param date     date of the exam.
     * @param teacher  name of the exam teacher.
     */
    public void setExamMark(int semester, String subject,
                            int mark, String date, String teacher) {
        if (semester < 1 || semester > exams.length) {
            throw new ArrayIndexOutOfBoundsException("Incorrect semester number\n");
        }

        Optional<Exam> exam = Arrays.stream(exams[semester - 1])
                .filter(e -> e.getSubject().equals(subject))
                .findFirst();
        if (exam.isPresent()) {
            exam.get().setMark(mark);
            exam.get().setDate(date);
            exam.get().setTeacher(teacher);
        } else {
            System.out.println("Couldn't find the subject " + subject
                    + "on " + semester + " semester");
        }
    }


    /**
     * returns if a student can get red diploma or not.
     *
     * @return boolean.
     */
    public boolean getBloodyDiploma() {
        double excellentMarks = 0;
        int counter = 0;
        boolean acceptableMarks = false;
        HashSet<String> subjects = new HashSet<>();
        for (int i = exams.length - 1; i >= 0; i--) {
            for (int j = 0; j < exams[i].length; j++) {
                if (exams[i][j].getMark() < 4) {
                    acceptableMarks = true;
                    break;
                }
                if (exams[i][j].getMark() != 0 && !subjects.contains(exams[i][j].getSubject())) {
                    subjects.add(exams[i][j].getSubject());
                    excellentMarks += exams[i][j].getMark();
                    counter++;
                }
            }
        }
        excellentMarks /= counter;

        return !acceptableMarks && !(excellentMarks < 0.75) && finalWorkMark == 5;
    }


    /**
     * to string.
     *
     * @return string representation of a student.
     */
    @Override
    public String toString() {
        String result;
        result = "Report Card number: " + number + "\n"
                + "Last name: " + lastName + "\n"
                + "First name: " + firstName + "\n"
                + "Patronimyc: " + patronymic + "\n"
                + "Speciality: " + speciality + "\n";
        return result;
    }


}
