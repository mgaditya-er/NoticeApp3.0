package com.example.noteiceboard;

public class Notice1 {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
    private String key;

    public Notice1(String dataTitle, String dataDesc, String dataLang, String dataImage, String key) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;


    }

    public Notice1() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }
}
