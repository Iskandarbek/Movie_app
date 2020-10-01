package com.example.movieapp;

import java.util.List;

public class ApiResponseVideos {

    private int page;
    private List<MovieVideos> results;

    public ApiResponseVideos(int page, List<MovieVideos> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public List<MovieVideos> getResults() {
        return results;
    }
}
