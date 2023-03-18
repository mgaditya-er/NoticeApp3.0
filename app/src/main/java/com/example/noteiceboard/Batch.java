package com.example.noteiceboard;

public class Batch {
    private int id;
    private String name;
    private String code;


    public Batch(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Batch() {

    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

