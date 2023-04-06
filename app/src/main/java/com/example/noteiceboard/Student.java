package com.example.noteiceboard;

import java.util.Map;

public class Student {
    private String studentId;
    private String studentName;
    private String email;
    private Map<String, Boolean> batchIds;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    }

    public Student(String studentId, String studentName, String email, Map<String, Boolean> batchIds) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.batchIds = batchIds;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Boolean> getBatchIds() {
        return batchIds;
    }

    public void setBatchIds(Map<String, Boolean> batchIds) {
        this.batchIds = batchIds;
    }
}

