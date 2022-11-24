package com.example.movielogin;

import Server.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ShowAllMoviesController {
    public VBox movieListVBox2;
    public HBox movieListHBox;
    public ScrollPane scrollPane;
    public GridPane gridPane;

    public Label pcNameField = new Label();

    public Button backButton;
    public VBox pcNameVbox;
    public Label titleLabel;
    private Main main;

    private Scene preScene;

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void loadMovieCards(List<Movie> movieList) {
        gridPane.getChildren().clear();
        try {
            int row = 0;
            int col = 0;
            for (Movie movie :
                    movieList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("movieCard.fxml"));

                Parent card = fxmlLoader.load();

                MovieCardController movieCardController = fxmlLoader.getController();
                movieCardController.setData(movie);
                movieCardController.setMain(this.main);
                gridPane.add(card, col, row++);
                card.getStyleClass().add(movie.getProductionCompany().replace(' ', '_'));

                //set grid width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
                double width = 464;
                gridPane.setMinWidth(width);
                gridPane.setPrefWidth(width);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(card, new Insets(10));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initialize(Main main){
        this.main = main;

        pcNameField.setText(main.getListOfMovies().getUserName());
    }

    public void onBackButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(preScene);

        main.toReload = false;
        stage.show();
    }
}
