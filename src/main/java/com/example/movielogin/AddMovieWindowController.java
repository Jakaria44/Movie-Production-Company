package com.example.movielogin;

import Server.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddMovieWindowController {
    private Stage stage;

    public Label pcNameField;
    public Button backButton;
    public Button submitButton;
    @FXML
    private Label budgetLabel;

    @FXML
    private TextField budgetTexField;

    @FXML
    private Label durationLabel;

    @FXML
    private TextField durationTexField;

    @FXML
    private Label genre1Label;

    @FXML
    private TextField genre1TexField;

    @FXML
    private Label genre2Label;

    @FXML
    private TextField genre2TexField;

    @FXML
    private Label genre3Label;

    @FXML
    private TextField genre3TexField;

    @FXML
    private Label releaseYearLabel;

    @FXML
    private TextField releaseYearTexField;

    @FXML
    private Label revenueLabel;

    @FXML
    private TextField revenueTexField;

    @FXML
    public Label titleLabel;

    @FXML
    private TextField titleTexField;

    private Main main;

    private Scene preScene;

     private int year = 0, time = 0;
     private long budget = 0 , revenue = 0;

//   it is set to get back to previous scene
    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void initialize(Main main){
        this.main = main;

        stage = main.getStage();
        pcNameField.setText(main.getListOfMovies().getUserName());
    }
//    after submitting all field should be reset
    private void resetFields(){
        titleTexField.setText(null);
        revenueTexField.setText(null);
        budgetTexField.setText(null);
        durationTexField.setText(null);
        genre1TexField.setText(null);
        genre2TexField.setText(null);
        genre3TexField.setText(null);
        releaseYearTexField.setText(null);
    }
    public void onSubmitButtonClick(ActionEvent event) throws IOException {
        int i = checkNumber();
//        firstly checking if all the fields are given input
        if(!checkAddMovie()) {
            main.showAlert("Error Adding New Movie", "Please Fill Up Correctly");
        }
//        then checking whether all fields are given appropriate input
        else if(i>0){
            String []str ={"Year", "Duration", "Budget", "Revenue"};
            main.showAlert("Wrong Input", "Please Enter a Number on "+str[i-1]+" Field");
        }

//        then checking if a movie with this title already exists or not
        else if(main.getListofTitle().contains(titleTexField.getText())){
            main.showAlert("Error Adding Movie..","Title is already Taken!!");
        }

//        finally create the movie and make sure to add , then add to the main list
//        which will be saved when closing the window
        else{
            Movie movie = createMovie();
            Optional<ButtonType> response =  main.showConfirmationWindow("Adding New Movie", "Are you sure want to Add this Movie?  ");

            if(response.isPresent()){
                if(response.get()== ButtonType.OK){
                    main.getNetworkUtil().write(movie);
                    main.addMovie(movie);
                    main.showInformation("Adding new Movie","You can now see it in All Movies window" ,"\""+movie.getTitle()+"\" Has Been Successfully Added ");
                    resetFields();
                }
            }

        }

    }
    public Movie createMovie(){
        String title = titleTexField.getText();
        String [] genre = { genre1TexField.getText() ,genre2TexField.getText(), genre3TexField.getText()};
        String productionCompany = main.getListOfMovies().getUserName();


        return new Movie(title, year, genre, time, productionCompany, budget,revenue);
    }
    public int checkNumber(){
        try {
            year = Integer.parseInt(releaseYearTexField.getText());
        } catch (NumberFormatException e) {
            return 1;
        }
        try {

            time = Integer.parseInt(durationTexField.getText());
        } catch (NumberFormatException e) {
            return 2;
        }
        try {
            budget = Long.parseLong(budgetTexField.getText());
        } catch (NumberFormatException e) {
            return 3;
        }
        try {
            revenue = Long.parseLong(revenueTexField.getText());
        } catch (NumberFormatException e) {
            return 4;
        }

        return 0;
    }
    public boolean checkAddMovie(){
        return !titleTexField.getText().isEmpty()
                && !releaseYearTexField.getText().isEmpty()
                && !genre1TexField.getText().isEmpty()
                && !durationTexField.getText().isEmpty()
                && !budgetTexField.getText().isEmpty()
                && !revenueTexField.getText().isEmpty();
    }

    public void onBackButtonClick(ActionEvent event) {
        stage.setScene(preScene);
        stage.show();
    }

}
