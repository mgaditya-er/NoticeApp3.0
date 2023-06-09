package com.example.noteiceboard;



public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
    private String dataPdf;

    private String key;

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

    public String getDataPdf() {
        return dataPdf;
    }

    public void setDataPdf(String dataPdf) {
        this.dataPdf = dataPdf;
    }

    public DataClass(String dataTitle, String dataDesc, String dataLang, String dataImage, String dataPdf) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        this.dataPdf = dataPdf;
    }

    public DataClass(String dataTitle, String dataDesc, String dataLang, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
    }
    public DataClass(){

    }
}
