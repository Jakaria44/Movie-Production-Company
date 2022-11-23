package com.example.movielogin;

import Server.Movie;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MovieDetailsController {
    public Label movieNameLabel;
    public Label genreLabel;
    public Label releaseYearLabel;
    public Label revenueLabel;
    public Label durationLabel;
    public Label budgetLabel;
    public AnchorPane movieDetailsCard;
    private Movie movie;

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void initialize(Main main, Movie movie){

        this.main = main;
        this.movie = movie;
        setData();

        Animation anim1 = main.numberGeneratingAnimation((long) movie.getBudget(),budgetLabel);
        Animation anim2 = main.numberGeneratingAnimation((long) movie.getRevenue(),revenueLabel);
        Animation anim3 = main.numberGeneratingAnimation((long) movie.getYearORelease(),releaseYearLabel);
        Animation anim4 = main.numberGeneratingAnimation((long) movie.getRunTime(),durationLabel);

        anim1.play();
        anim1.setOnFinished(e->{
            anim2.play();
        });
        anim2.setOnFinished(e->{
            anim3.play();
        });
        anim3.setOnFinished(e->{
            anim4.play();
        });
    }
    public void setData() {
        String []genreArray = movie.getGenre();
        String genre = genreArray[0];
        if(!genreArray[1].equalsIgnoreCase("")) genre = genre + " , "+ genreArray[1];
        if(!genreArray[2].equalsIgnoreCase("")) genre = genre + " , "+ genreArray[2];
        movieNameLabel.setText(movie.getTitle());


        final String content =genre;
        final Animation titleAnimation = new Transition(){
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                genreLabel.setText(content.substring(0, n));
            }

            {
                setCycleDuration(Duration.millis(1400));
            }

        };
        titleAnimation.play();

    }

}
