package com.example.movieapp;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Movie_details extends AppCompatActivity {

    Movie movie;
    ImageView poster, star1, star2, star3, star4, star5;
    TextView desc, originalTitle, originalLanguage, releaseDate, rating, reviewContent;
    Spinner spinner;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_movie_details);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        spinner = findViewById(R.id.spinner);
        reviewContent = findViewById(R.id.review_content);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String object = getIntent().getStringExtra("movie");
        movie = (Movie) new Gson().fromJson(object, Movie.class);
        poster = findViewById(R.id.poster);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(movie.getTitle());
        }

        if (movie.getBack_path() == null){
            Picasso.get().load(movie.getPoster_path()).into(poster);
        }
        else {
            Picasso.get().load(movie.getBackdrop_path()).into(poster);
        }

        desc = findViewById(R.id.desc);
        originalTitle = findViewById(R.id.orig_title);
        originalLanguage = findViewById(R.id.language);
        releaseDate = findViewById(R.id.release_date);
        rating = findViewById(R.id.rating);

        desc.setText(movie.getOverview());
        originalTitle.setText(movie.getOriginal_title());
        originalLanguage.setText(movie.getOriginal_language().toUpperCase());
        releaseDate.setText(movie.getRelease_date());

        double ratingValue = movie.getVote_average();
        rating.setText(String.valueOf(ratingValue));

        if (ratingValue < 10 && ratingValue >= 9){
            star5.setImageResource(R.drawable.ic_baseline_star_half_24);
        }
        if (ratingValue < 9 && ratingValue >= 8){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        if (ratingValue < 8 && ratingValue >= 7){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_half_24);
        }
        if (ratingValue < 7 && ratingValue >= 6){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        if (ratingValue < 6 && ratingValue >= 5){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            star3.setImageResource(R.drawable.ic_baseline_star_half_24);
        }
        if (ratingValue < 5 && ratingValue >= 4){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            star3.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        if (ratingValue < 4 && ratingValue >= 3){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            star3.setImageResource(R.drawable.ic_baseline_star_border_24);
            star2.setImageResource(R.drawable.ic_baseline_star_half_24);
        }
        if (ratingValue < 3 && ratingValue >= 2){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            star3.setImageResource(R.drawable.ic_baseline_star_border_24);
            star2.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        if (ratingValue < 2 && ratingValue >= 1){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            star3.setImageResource(R.drawable.ic_baseline_star_border_24);
            star2.setImageResource(R.drawable.ic_baseline_star_border_24);
            star1.setImageResource(R.drawable.ic_baseline_star_half_24);
        }
        if (ratingValue < 1 && ratingValue >= 0){
            star5.setImageResource(R.drawable.ic_baseline_star_border_24);
            star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            star3.setImageResource(R.drawable.ic_baseline_star_border_24);
            star2.setImageResource(R.drawable.ic_baseline_star_border_24);
            star1.setImageResource(R.drawable.ic_baseline_star_border_24);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        // calling for Videos
        Call<ApiResponseVideos> requestVideos = api.getMovieVideos(movie.getId(), Util.API_KEY, Util.LANGUAGE);
        requestVideos.enqueue(new Callback<ApiResponseVideos>() {
            @Override
            public void onResponse(Call<ApiResponseVideos> call, Response<ApiResponseVideos> response) {
                if (response.isSuccessful()){
                    List<MovieVideos> videosList = response.body().getResults();
                    List<String> videoNames = new ArrayList<>();
                    for (MovieVideos video: videosList) {
                        videoNames.add(video.getName());
                    }

                    videoNames.add(0,"Select videos...");

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Movie_details.this, R.layout.custom_starting_spinner_item, videoNames);
                    spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                    spinner.setAdapter(spinnerAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            if (position != 0 ) {
                                Intent intent = new  Intent(Intent.ACTION_VIEW);
                                intent.setPackage("com.google.android.youtube");
                                intent.setData(Uri.parse(Util.YOUTUBE_PATH + videosList.get(position-1).getKey()));
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponseVideos> call, Throwable t) {

            }
        });

        // calling for Reviews
        Call<ApiResponseReviews> requestReviews = api.getMovieReviews(movie.getId(), Util.API_KEY, Util.LANGUAGE);
        requestReviews.enqueue(new Callback<ApiResponseReviews>() {
            @Override
            public void onResponse(Call<ApiResponseReviews> call, Response<ApiResponseReviews> response) {
                if (response.isSuccessful()){
                    List<MovieReviews> reviewsList  = response.body().getResults();
                    String reviewsAll = "";

                    for (MovieReviews r: reviewsList) {
                        reviewsAll += "Author:  " + r.getAuthor() + "\n";
                        reviewsAll += "Content:  " + r.getContent() + "\n";
                        reviewsAll += "Link:  " + r.getUrl() + "\n" + "\n" + "\n";
                    }
                    if (reviewsAll.equals("")){
                        reviewContent.setText("No reviews yet. Add your review!");
                    }
                    else {
                        reviewContent.setText(reviewsAll);
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponseReviews> call, Throwable t) {

            }
        });
    }
}