package com.example.movielogin;

import Server.Movie;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.TransferMoviesUtil;

import java.io.IOException;
import java.util.Optional;

public class TransferWindowController {

    public ChoiceBox<String > pcChoiceBox;
    @FXML
    private Button cancelButton;

    @FXML
    private TextField pcTransferNameField;

    private Stage stage;
    @FXML
    private Button transferButton;
    private Scene preScene;
    private Movie movie;
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;

    }

    public void initialize(Main main , Movie movie, Stage stage){
        this.main = main;
        this.movie = movie;
        this.stage  = stage;
        initiateChoiceBox();
        pcChoiceBox.setOnAction(this::setPCName);

    }

    public void setPCName(ActionEvent event){
        pcTransferNameField.setText(pcChoiceBox.getValue());
    }
    void initiateChoiceBox(){
        pcChoiceBox.getItems().setAll(FXCollections.observableArrayList(main.getListofPC()));
////        SETTING second production company by default. because first one is a single space
    }

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        stage.close();
    }

    @FXML
    void onTransferButtonClick(ActionEvent event) throws IOException {
        System.out.println(pcTransferNameField.getText());


        if(pcTransferNameField.getText()!= null){
            showConfirmationWindow(event);
        }else {
            showAlert("Production Company can not be null ");
        }
    }

    private void showConfirmationWindow(ActionEvent event) throws IOException {
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();

        Optional<ButtonType> result = main.showConfirmationWindow("Transfer Movie","Are you sure want to transfer?");
        if(result.isPresent()){
            if(result.get()== ButtonType.OK){

                if(main.getListofPC().contains(pcTransferNameField.getText())){
                    stage.close();
                    main.getListOfMovies().getMovieList().remove(movie);
                    main.getListofTitle().remove(movie.getTitle());
                    main.getHomePageController().showAllMovies();
                    TransferMoviesUtil transferMoviesUtil = new TransferMoviesUtil(pcTransferNameField.getText(), movie, true);
                    main.getNetworkUtil().write(transferMoviesUtil);
                    main.showInformation("Confirmation", "\"" + movie.getTitle()+"\" has been transferred to the Production Company: "+pcTransferNameField.getText(), "");
                }
                else {
                    showAlert("Production company doesn't exist.");
                }
            }else if(result.get()== ButtonType.CANCEL){
                System.out.println("canceled");
            }
        }


    }
    public void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning!");
        alert.setHeaderText("Incorrect Production Company Name");
        alert.setContentText(content);
        alert.showAndWait();
    }

}
