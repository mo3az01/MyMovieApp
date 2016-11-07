package com.moaz.mymovieapp.response;

import com.moaz.mymovieapp.models.Movie;

import java.util.List;

/**
 * Created by xkcl0301 on 10/25/2016.
 */
public class MoviesResponse {
    private int page;
    private List<Movie> results;
    private int total_results;
    private int total_pages;

    public MoviesResponse(int page, List<Movie> results, int total_results, int total_pages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
