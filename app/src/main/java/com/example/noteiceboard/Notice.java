package com.example.noteiceboard;


public class Notice {
    private int id;
    private String title;
    private String content;
    private Batch batch;

    public Notice(int id, String title, String content, Batch batch) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.batch = batch;
    }

    public Notice() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }
}
