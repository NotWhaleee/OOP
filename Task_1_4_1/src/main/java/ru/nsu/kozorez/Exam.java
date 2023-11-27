package ru.nsu.kozorez;

public class Exam{
    private final String subject;
    private int mark = 0;
    private String date = null;
    private String teacher;

    public Exam(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) throws IllegalArgumentException{
        if (mark < 2 || mark > 5){
            throw new IllegalArgumentException("Incorrect mark. Mark can be only in a range from 2 to 5");
        }
        this.mark = mark;
    }

    public String getSubject() {
        return subject;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public String getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + "\nmark: " + mark + "\ndate: " + date + "\nteacher: " + teacher +"\n\n";
    }
}
