package com.example.noteiceboard;

import java.util.List;
public class Batch {
    private String name;
    private int code;
    private List<String> emails;

    public Batch() {
    }

    public Batch(String name, int code, List<String> emails) {
        this.name = name;
        this.code = code;
        this.emails = emails;
    }

    public Batch(String code) {
        this.code = Integer.parseInt(code);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}



//    public List<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(List<Student> students) {
//        this.students = students;
//    }
