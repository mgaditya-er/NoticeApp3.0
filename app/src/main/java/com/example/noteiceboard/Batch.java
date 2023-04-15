package com.example.noteiceboard;

import java.util.List;
public class Batch {
    private String name;
    private int code;
    private List<String> emails;
    private List<String> notices;


    public Batch() {
    }

    public Batch(String name, int code, List<String> emails,List<String> notices) {
        this.name = name;
        this.code = code;
        this.emails = emails;
        this.notices = notices;
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


    public List<String> getNotices() {
        return notices;
    }

    public void setNotices(List<String> notices) {
        this.notices = notices;
    }
}


