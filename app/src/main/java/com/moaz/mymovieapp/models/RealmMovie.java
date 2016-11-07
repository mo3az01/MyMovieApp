package com.moaz.mymovieapp.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by xkcl0301 on 11/7/2016.
 */
public class RealmMovie extends RealmObject {
    @PrimaryKey
    private int id;
    private String poster_path;
    private String title;
    private String overview;
    private String release_date;
    private double vote_average;

    RealmList<Trailer> trailerRealmList;
    RealmList<Review> reviewRealmList;

    public RealmMovie(){
        trailerRealmList = new RealmList<>();
        reviewRealmList = new RealmList<>();
    }
    public RealmMovie(Movie movie, List<Trailer> trailerList, List<Review> reviewList) {
        this.id = movie.getId();
        this.poster_path = movie.getPoster_path();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.release_date = movie.getRelease_date();
        this.vote_average = movie.getVote_average();
        trailerRealmList = new RealmList<>();
        reviewRealmList = new RealmList<>();
        this.trailerRealmList.addAll(trailerList);
        this.reviewRealmList.addAll(reviewList);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public RealmList<Trailer> getTrailerRealmList() {
        return trailerRealmList;
    }

    public void setTrailerRealmList(RealmList<Trailer> trailerRealmList) {
        this.trailerRealmList = trailerRealmList;
    }

    public RealmList<Review> getReviewRealmList() {
        return reviewRealmList;
    }

    public void setReviewRealmList(RealmList<Review> reviewRealmList) {
        this.reviewRealmList = reviewRealmList;
    }
}
