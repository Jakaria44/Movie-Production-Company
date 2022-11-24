package com.example.movielogin;

import Server.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieCardWithoutTransferController {
    public Label movieNameLabel;
    public Button movieDetailsButton;
    public Button movieTransferButton;

    private Movie movie;

    private HomepageController homepageController;
    private Main main;

    public Main getMain() {
        return main;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMain(Main main) {
        this.main = main;
    }


    public void setData(Movie movie){
        this.movie = movie;
        movieNameLabel.setText(movie.getTitle());
    }

    public void onMovieDetailsButtonClick(ActionEvent event) {
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(movie.getTitle());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieDetailsShow.fxml"));
            Parent root = fxmlLoader.load();

            MovieDetailsController movieDetailsController = fxmlLoader.getController();
            movieDetailsController.initialize(this.main, this.movie);


            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
