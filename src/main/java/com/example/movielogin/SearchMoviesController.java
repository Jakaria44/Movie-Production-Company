package com.example.movielogin;

import Server.Movie;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import kotlin.TypeCastException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchMoviesController {

    public Label titleLabel;
    private Main main ;
    @FXML
    private Button backButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private HBox movieListHBox;

    @FXML
    private Label pcNameField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button searchButton;

    @FXML
    private ChoiceBox<String > searchChoiceBox;

    @FXML
    private TextField searchTextField;

    private Scene preScene;
    private List<Movie>movieList=new ArrayList<>() ;

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
        initiateChoiceBox();
    }

    void initiateChoiceBox(){
        String []choices = {"By Title", "By Release Year", "By Genre",};

        searchChoiceBox.getItems().setAll(FXCollections.observableArrayList(choices));
        searchChoiceBox.setValue("By Title");
    }
    @FXML
    void onSearchButtonClick(ActionEvent event) {
        movieList.clear();
        gridPane.getChildren().clear();

        if(searchTextField.getText().isEmpty()) {
            main.showAlert("Error Searching Movie", "Please Enter Valid Search");
        }
        else{
            String choice = searchChoiceBox.getValue();

            if(choice.equalsIgnoreCase("By Title" )){
                if(searchMovieTitle(searchTextField.getText())!=null){
                 movieList.add( searchMovieTitle(searchTextField.getText()));
                 }
            }
            else if (choice.equalsIgnoreCase("By Release Year" )) {
                int year = 0;
                try {
                    year = Integer.parseInt(searchTextField.getText());
                } catch (NumberFormatException e) {
                    main.showAlert("Wrong Input", "Please Enter a Number");
                }
                if (searchMovieReleaseYear(year) != null) {

                    movieList = searchMovieReleaseYear(year);
                }
            }
            else if (choice.equalsIgnoreCase("By Genre" )) {
                if(searchMovieGenre(searchTextField.getText())!=null) {
                    movieList = searchMovieGenre(searchTextField.getText());
                }
            }
        }
        if(movieList.isEmpty()){
            main.showAlert("No Movies Found", "Try Different Search");
        }
        else {
            loadMovieCards();
        }

    }

    public void loadMovieCards() {
        try {
            int row = 0;
            int col = 0;
            for (Movie movie :  movieList) {
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

    public Movie searchMovieTitle(String str){
        for(Movie i: main.getListOfMovies().getMovieList()){
            if(i.getTitle().toLowerCase().contains(str.toLowerCase())) {
                return i;
            }
        }
        return null;
    }
    public List<Movie> searchMovieReleaseYear(int x){
        List<Movie> mList = new ArrayList<Movie>();
        for(Movie i: main.getListOfMovies().getMovieList()) {
            int t = i.getYearORelease();
            if(x == t) {   mList.add(i);  }
        }
        return mList;
    }
    public List<Movie> searchMovieGenre(String str){

        List<Movie> mList= new ArrayList<Movie>();
        for(Movie i:main.getListOfMovies().getMovieList()) {
            String[] t = i.getGenre();
            for (String x : t) {
                if (x.toLowerCase().contains(str.toLowerCase())) {
                    mList.add(i);
                }
            }
        }
            return mList;
    }
}
