package com.example.movielogin;

import Server.Movie;
import javafx.animation.*;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.*;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {

    private Stage stage;
    public boolean toReload = false;

    private NetworkUtil networkUtil;

    private HomepageController homePageController;
    private ListOfMovies listOfMovies = new ListOfMovies();
    private List<String>listofTitle = new ArrayList<>();
    private List<String >listofPC = new ArrayList<>();

    public List<String> getListofPC() {
        return listofPC;
    }

    public void setListofPC(List<String> listofPC) {
        this.listofPC = listofPC;
    }

    public List<String> getListofTitle() {
        return listofTitle;
    }

    public void makeListofTitle(List<Movie>movieList){
        for(Movie m:movieList){
            listofTitle.add(m.getTitle());
        }
    }
    public HomepageController getHomePageController() {
        return homePageController;
    }

    public void setListOfMovies(ListOfMovies listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    public ListOfMovies getListOfMovies() {
        return listOfMovies;
    }

    public Stage getStage() {
        return stage;
    }
    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();

    }


    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 26262;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThread(this);
    }

    public void showLoginPage() throws Exception {
        boolean givePCList = true;
//        takes the production company list from server which will be set on the choice box
        networkUtil.write(givePCList);


        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = loader.load();

        // ReadFromFile the controller
        LoginController controller = loader.getController();
        controller.initialize(this);


        final String content ="Movie Production Company Log In";
        final Animation titleAnimation = new Transition(){
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                controller.titleLabel.setText(content.substring(0, n));
            }

            { setCycleDuration(Duration.millis(2000));    }

        };
        titleAnimation.play();

        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();

        stage.setOnCloseRequest(e->{
            try {
                logOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void showHomePage(ListOfMovies listOfMovies ) throws Exception {
//      this function is called from ReadServer
//        which will pass the List of movie with name of the production company
        toReload = false;

        this.listOfMovies = listOfMovies;
        makeListofTitle(listOfMovies.getMovieList());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();

        homePageController = loader.getController();
        homePageController.setMain(this);
        homePageController.pcNameField.setText(listOfMovies.getUserName());

        transition(root);

        final String content ="Main Menu";
        final Animation titleAnimation = new Transition(){
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                homePageController.titleLabel.setText(content.substring(0, n));
            }

            { setCycleDuration(Duration.millis(1400));  }

        };
        titleAnimation.play();

        stage.setTitle("Home");
        stage.setScene(new Scene(root));
        stage.show();

        stage.setOnCloseRequest(e->{
            try {
                logOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void showAddMovieWindow(Scene scene) throws IOException {
        toReload = false;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AddMovieWindow.fxml"));
        Parent root = loader.load();

        AddMovieWindowController addMovieWindowController  = loader.getController();
        addMovieWindowController.initialize(this);
        addMovieWindowController.setPreScene(scene);

        transition(root);

        final String content ="Add New Movie";
        final Animation titleAnimation = new Transition(){
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                addMovieWindowController.titleLabel.setText(content.substring(0, n));
            }

            { setCycleDuration(Duration.millis(1400));  }

        };
        titleAnimation.play();

        stage.setScene(new Scene(root));
        stage.show();

        stage.setOnCloseRequest(e->{
            try {
                logOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    public void showSearchMoviesWindow(Scene scene) throws  IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("SearchMovies.fxml"));
        Parent root = loader.load();
        toReload = false;

//      ReadFromFile the controller
        SearchMoviesController searchMoviesController  = loader.getController();
        searchMoviesController.initialize(this);
        searchMoviesController.setPreScene(scene);

        transition(root);
        final String content ="Search Movies";
        final Animation titleAnimation = new Transition(){
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                searchMoviesController.titleLabel.setText(content.substring(0, n));
            }

            { setCycleDuration(Duration.millis(1400));   }

        };
        titleAnimation.play();

        stage.setOnCloseRequest(e->{
            try {
                logOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showStatisticsWindow(Scene scene) throws  Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StatisticsPage.fxml"));
        Parent root = loader.load();
        toReload = false;

//      ReadFromFile the controller
        StatisticsPageController Controller  = loader.getController();
        Controller.initialize(this);
        Controller.setPreScene(scene);

        transition(root);
        final String content ="Statistics ";
        final Animation titleAnimation = new Transition(){
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                Controller.HeaderLabel.setText(content.substring(0, n));
            }

            {
                setCycleDuration(Duration.millis(1400));
            }

        };
        titleAnimation.play();

        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(e->{
            try {
                logOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }



    public void transition(Parent root){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(450), root);
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.setInterpolator(Interpolator.EASE_IN);
        fadeTransition.play();
    }
    public Animation numberGeneratingAnimation(long n, Label label){
        return new Transition() {
            {
                setCycleDuration(Duration.millis(1500));
            }
            @Override
            protected void interpolate(double frac) {
                final long n1 = Math.round(n * (float) frac);
                label.setText(String.valueOf(n1));
            }
        };
    }
    public  Optional<ButtonType> showConfirmationWindow(String header, String content){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
    public void showAlert(String title, String headerText) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText("Please try Again");
        alert.showAndWait();
    }

    public void showInformation(String title, String content, String header){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    public void addMovie(Movie movie) throws IOException {

        System.out.println(movie.getTitle());
        if(!listofTitle.contains(movie.getTitle())){
            System.out.println(movie.getTitle());
            listOfMovies.getMovieList().add(movie);
            listofTitle.add(movie.getTitle());
            System.out.println(toReload);
            if(toReload){
                homePageController.showAllMovies();
            }
        }
    }

    public void logOut() throws IOException {
        if(listOfMovies!=null) networkUtil.write(new CloseRequest(listOfMovies.getUserName()));
        stage.close();
        networkUtil.closeConnection();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

