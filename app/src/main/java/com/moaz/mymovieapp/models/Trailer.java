package com.moaz.mymovieapp.models;

import io.realm.RealmObject;

/**
 * Created by xkcl0301 on 10/27/2016.
 */
public class Trailer extends RealmObject{
    private String key;
    private String name;

    public Trailer(){

    }
    public Trailer(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
