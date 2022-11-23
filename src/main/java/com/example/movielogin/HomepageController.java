package com.example.movielogin;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HomepageController{
    public Button addMovieButton;
    public Button searchButton;
    public Button totalProfitButton;
    public Button statisticsButton;
    public VBox pcNameVbox;
    public Label titleLabel;
    private Stage stage;

    public Label pcNameField;

    private Main main;// new

    @FXML
    private Button exitButton;

    @FXML
    private Button recentMoviesButton;

    @FXML
    private Button showAllMoviesButton;


    public void setMain(Main main ){ this.main = main; }

    @FXML
    public void exitAll(ActionEvent event) throws IOException {
        main.logOut();
    }

    @FXML
    public void onShowAllMoviesButtonClick(ActionEvent event) throws IOException {
        showAllMovies();

    }

    public void showAllMovies() throws IOException {

        main.toReload = true;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("movieListView.fxml"));
        Parent root = loader.load();

//      ReadFromFile the controller

        ShowAllMoviesController controller = loader.getController();
        controller.initialize(this.main);
        controller.loadMovieCards(main.getListOfMovies().getMovieList());
        controller.setPreScene(showAllMoviesButton.getScene());

        main.transition(root);
        final String content ="All MOVIES";
        final Animation titleAnimation = new Transition(){
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                controller.titleLabel.setText(content.substring(0, n));
            }

            {
                setCycleDuration(Duration.millis(1400));
            }

        };
        titleAnimation.play();

        main.getStage().setOnCloseRequest(e->{
            try {
                main.logOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        main.getStage().setScene(new Scene(root));
        main.getStage().show();

    }
    public void onAddMovieButtonClick(ActionEvent event) throws IOException {

        main.showAddMovieWindow(addMovieButton.getScene());

    }

    public void reloadMovies() throws IOException {
        main.toReload = true;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("movieListView.fxml"));
        Parent root = loader.load();

        ShowAllMoviesController controller = loader.getController();
        controller.initialize(this.main);
        controller.loadMovieCards(main.getListOfMovies().getMovieList());
        controller.setPreScene(showAllMoviesButton.getScene());

        main.getStage().setScene(new Scene(root));
        main.getStage().show();

    }

    public void onSearchButtonClick(ActionEvent event) throws IOException {
        main.showSearchMoviesWindow(searchButton.getScene());
    }



    public void onStatisticsButtonClick(ActionEvent event) throws Exception {
        main.showStatisticsWindow(statisticsButton.getScene());
    }
}
