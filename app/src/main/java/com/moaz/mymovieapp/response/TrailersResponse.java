package com.moaz.mymovieapp.response;

import com.moaz.mymovieapp.models.Trailer;

import java.util.List;

/**
 * Created by xkcl0301 on 10/27/2016.
 */
public class TrailersResponse {
    private int id;
    private List<Trailer> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
