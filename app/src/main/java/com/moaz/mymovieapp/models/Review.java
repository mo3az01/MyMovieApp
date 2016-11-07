package com.moaz.mymovieapp.models;

import io.realm.RealmObject;

/**
 * Created by xkcl0301 on 10/27/2016.
 */
public class Review extends RealmObject{
    private String author;
    private String content;

    public Review(){

    }
    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
