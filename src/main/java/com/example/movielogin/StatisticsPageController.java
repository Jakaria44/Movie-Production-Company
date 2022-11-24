package com.example.movielogin;

import Server.Movie;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsPageController {
    public Label titleLabel;
    public Button totalProfitButton;
    public Label totalProfitLabel;
    public Label totalProfitLabel1;
    public Label HeaderLabel;
    private Main main;
    @FXML
    private Button backButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button latestMovieButton;

    @FXML
    private Button maxRevenueMovieButton;

    @FXML
    private HBox movieListHBox;

    @FXML
    private Label pcNameField;

    @FXML
    private ScrollPane scrollPane;

    private Scene preScene;
    private List<Movie> movieList=new ArrayList<>() ;

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    void onBackButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(preScene);
        stage.show();
    }

    public void initialize(Main main){
        this.main = main;
        pcNameField.setText(main.getListOfMovies().getUserName());
    }


    @FXML
    void onLatestMovieButtonClick(ActionEvent event) {
        totalProfitLabel1.setVisible(false);
        totalProfitLabel.setVisible(false);
        movieListHBox.setVisible(true);

        movieList.clear();
        gridPane.getChildren().clear();
        movieList = mostRecentMovies();
        loadMovieCards();
        titleLabel.setText("Showing Latest Movie");

        latestMovieButton.setStyle("-fx-background-color: white; -fx-text-fill: green;");

        maxRevenueMovieButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        totalProfitButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
    }

    @FXML
    void onMaxRevenueMovieButtonClick(ActionEvent event) {
        totalProfitLabel1.setVisible(false);
        totalProfitLabel.setVisible(false);
        movieListHBox.setVisible(true);

        movieList.clear();
        gridPane.getChildren().clear();
        movieList = maxRevenueMovies();
        loadMovieCards();

        titleLabel.setText("Showing Movie with Maximum Revenue");

        maxRevenueMovieButton.setStyle("-fx-background-color: white; -fx-text-fill: green;");

        latestMovieButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        totalProfitButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
    }

    public List <Movie> mostRecentMovies() {
        int x = 0, maxReleasedYear = 0;
        List<Movie> mList = main.getListOfMovies().getMovieList();
        List<Movie> returnList = new ArrayList<Movie>();
        for(Movie m: mList){
            if(m.getYearORelease() >maxReleasedYear)  maxReleasedYear= m.getYearORelease();
        }
        for(Movie m: mList){
            if(m.getYearORelease() == maxReleasedYear) returnList.add(m);
        }
        return returnList;
    }

    public List <Movie> maxRevenueMovies() {
        int x = 0;
        long maxRevenue = 0;
        List<Movie> mList = main.getListOfMovies().getMovieList();
        List<Movie> returnList = new ArrayList<Movie>();
        for(Movie m: mList){
            if(m.getRevenue() >maxRevenue)  maxRevenue= m.getRevenue();
        }
        for(Movie m: mList){
            if(m.getRevenue() == maxRevenue) returnList.add(m);
        }
        return returnList;
    }

    public void loadMovieCards() {
        try {
            int row = 0;
            int col = 0;
            for (Movie movie : movieList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieCardsWithoutTransfer.fxml"));

                Parent card = fxmlLoader.load();

                MovieCardWithoutTransferController movieCardController = fxmlLoader.getController();
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

    public void onTotalProfitButtonClick(ActionEvent event) throws Exception {

        movieListHBox.setVisible(false);
        totalProfitLabel1.setVisible(true);
        totalProfitLabel.setVisible(true);

        movieList.clear();
        gridPane.getChildren().clear();


        titleLabel.setText("Showing Total Profit");
        maxRevenueMovieButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        latestMovieButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        totalProfitButton.setStyle("-fx-background-color: white; -fx-text-fill: green;");

        Animation numAnimation = main.numberGeneratingAnimation(main.getListOfMovies().getTotalProfit(), totalProfitLabel);
        numAnimation.play();

        numAnimation.setOnFinished(e->{
            totalProfitLabel.setText("$ "+ totalProfitLabel.getText() );
        });
    }
}
