package com.example.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("discover/movie")
    Call<ApiResponseDiscover> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("sort_by") String sortBy,
            @Query("include_adult") boolean includeAdult,
            @Query("include_video") boolean includeVideo);

    @GET("movie/{movie_id}/videos")
    Call<ApiResponseVideos> getMovieVideos(
            @Path("movie_id") int movieID,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("movie/{movie_id}/reviews")
    Call<ApiResponseReviews> getMovieReviews(
            @Path("movie_id") int movieID,
            @Query("api_key") String apiKey,
            @Query("language") String language);
}
