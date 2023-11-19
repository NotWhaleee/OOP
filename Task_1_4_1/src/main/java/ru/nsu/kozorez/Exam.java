package ru.nsu.kozorez;

public class Exam {
    private String subject;
    private int mark = 0;
    private String date = null;

    public Exam(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        if (mark < 2 || mark > 5){
            System.out.println("Incorrect mark. Mark can be only in range from 2 to 5");
            return;
        }
        this.mark = mark;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + "\nmark: " + mark + "\ndate: " + date + "\n";
    }
}
