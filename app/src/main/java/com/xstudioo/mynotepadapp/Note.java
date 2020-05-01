package com.xstudioo.mynotepadapp;

public class Note {
    private long id;
    private String title;
    private String content;
    private String date;
    private String time;
    private String style;
    private String color;

    Note(String title, String content, String date, String time, String style, String color) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.style = style;
        this.color = color;
    }

    Note(long id, String title, String content, String date, String time, String style, String color) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.style = style;
        this.color = color;
    }

    Note() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
