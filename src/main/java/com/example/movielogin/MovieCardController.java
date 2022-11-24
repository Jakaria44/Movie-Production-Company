package com.example.movielogin;

import Server.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieCardController {
    public Label movieNameLabel;
    public Button movieDetailsButton;
    public Button movieTransferButton;
    public HBox hBoxOfCard;

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

    public void showMovieDetails(ActionEvent event) {
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

    public void setData(Movie movie){
        this.movie = movie;

        movieNameLabel.setText(movie.getTitle());
    }
    public void movieTransfer(ActionEvent event) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(movie.getTitle());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("TransferWindow.fxml"));
        Parent root = fxmlLoader.load();

        TransferWindowController transferWindowController = fxmlLoader.getController();
        transferWindowController.initialize(this.main, movie, window);
        transferWindowController.setPreScene(movieTransferButton.getScene());

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }
}
