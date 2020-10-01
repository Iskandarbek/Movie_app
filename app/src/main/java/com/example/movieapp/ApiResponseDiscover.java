package com.example.movieapp;

import java.util.List;

public class ApiResponseDiscover {

    private String page;
    private String total_results;
    private String total_pages;
    private List<Movie> results;

    public ApiResponseDiscover(String page, String total_results, String total_pages, List<Movie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public String getPage() {
        return page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }
}
