package com.moaz.mymovieapp.utils;

/**
 * Created by xkcl0301 on 10/25/2016.
 */

import com.moaz.mymovieapp.response.MoviesResponse;
import com.moaz.mymovieapp.response.ReviewsResponse;
import com.moaz.mymovieapp.response.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("{type}")
    Call<MoviesResponse> getMoviesByType(@Path("type") String type,@Query("api_key") String apiKey,@Query("page") int page);

    @GET("{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("{id}/videos")
    Call<TrailersResponse> getTrailers(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("{id}/reviews")
    Call<ReviewsResponse> getReviews(@Path("id") int id, @Query("api_key") String apiKey);

}
