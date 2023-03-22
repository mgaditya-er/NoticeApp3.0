package com.example.noteiceboard;

import java.util.List;

public class Batch {
    private String name;
    private int code;
//    private List<Student> students;

    public Batch() {
    }

    public Batch(String name, int code) {
        this.name = name;
        this.code = code;
//        this.students = students;
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

//    public List<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(List<Student> students) {
//        this.students = students;
//    }
}