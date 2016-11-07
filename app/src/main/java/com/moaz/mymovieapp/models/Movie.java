package com.moaz.mymovieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xkcl0301 on 6/19/2016.
 */
public class Movie implements Parcelable {
    private int id;
    private String poster_path;
    private Boolean adult;
    private String overview;
    private String release_date;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private double popularity;
    private double vote_average;
    private int vote_count;
    private boolean video;

    public Movie() {

    }

    public Movie(int id, String poster_path, String overview, String release_date, String title, double vote_average) {
        this.id = id;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.vote_average = vote_average;
    }

    public Movie(int id, String poster_path, Boolean adult, String overview, String release_date, String original_title, String original_language, String title, String backdrop_path, double popularity, double vote_average, int vote_count, boolean video) {
        this.id = id;
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.video = video;
    }

    public Movie(Parcel parcel) {
        String[] data = new String[13];
        parcel.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.poster_path = data[1];
        this.adult = Boolean.parseBoolean(data[2]);
        this.overview = data[3];
        this.release_date = data[4];
        this.original_title = data[5];
        this.original_language = data[6];
        this.title = data[7];
        this.backdrop_path = data[8];
        this.popularity = Double.parseDouble(data[9]);
        this.vote_average = Double.parseDouble(data[10]);
        this.vote_count = Integer.parseInt(data[11]);
        this.video = Boolean.parseBoolean(data[12]);
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

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
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

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(id), poster_path, String.valueOf(adult), overview, release_date, original_title, original_language, title, backdrop_path, String.valueOf(popularity), String.valueOf(vote_average), String.valueOf(vote_count), String.valueOf(video)});
    }


    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
