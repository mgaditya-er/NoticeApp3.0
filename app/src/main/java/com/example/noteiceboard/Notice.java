package com.example.noteiceboard;

import java.util.List;

public class Notice {

    String title;
    private String body;
    private List<String> response;


    public Notice() {
    }

    public Notice(String title, String body, List<String> response) {
        this.title = title;
        this.body = body;
        this.response = response;
    }

    public Notice(String title) {
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }
}



