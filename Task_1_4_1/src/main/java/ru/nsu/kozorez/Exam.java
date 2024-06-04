package ru.nsu.kozorez;

/**
 * exam class.
 */
public class Exam {
    private final String subject;
    private int mark = 0;
    private String date = null;
    private String teacher;

    /**
     * create an exam.
     *
     * @param subject name of the subject
     */
    public Exam(String subject) {
        this.subject = subject;
    }

    /**
     * get the mark.
     *
     * @return int. mark.
     */
    public int getMark() {
        return mark;
    }


    /**
     * set mark.
     *
     * @param mark int. in a range from 2 to 5.
     * @throws IllegalArgumentException only in a range from 2 to 5.
     */
    public void setMark(int mark) throws IllegalArgumentException {
        if (mark < 2 || mark > 5) {
            throw new IllegalArgumentException("Incorrect mark."
                    + " Mark can be only in a range from 2 to 5");
        }
        this.mark = mark;
    }

    /**
     * gets subject.
     *
     * @return name of the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * gets date of exam.
     *
     * @return string.
     */
    public String getDate() {
        return date;
    }

    /**
     * sets date of exam.
     *
     * @param date string.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * sets exam teacher.
     *
     * @param teacher name of the teacher.
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * gets exam teacher.
     *
     * @return name.
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * to string.
     *
     * @return string.
     */
    @Override
    public String toString() {
        String humanMark;
        switch (mark) {
            case 5:
                humanMark = "EXCELLENT";
                break;
            case 4:
                humanMark = "GOOD";
                break;
            case 3:
                humanMark = "ACCEPTABLE";
                break;
            case 2:
                humanMark = "BAD, REALLY BAD :((";
                break;
            case 0:
                humanMark = "NO MARK YET";
                break;
            default:
                throw new IllegalArgumentException("Incorrect mark");
        }
        return "Subject: " + subject + "\nmark: "
                + humanMark + "\ndate: " + date + "\nteacher: " + teacher + "\n\n";
    }
}
