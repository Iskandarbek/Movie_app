package com.example.movieapp;

import java.util.List;

public class ApiResponseReviews {

    private int id;
    private int page;
    private List<MovieReviews> results;
    private int total_pages;
    private int total_results;

    public ApiResponseReviews(int id, int page, List<MovieReviews> results, int total_pages, int total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public List<MovieReviews> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}
