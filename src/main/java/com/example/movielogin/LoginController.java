package com.example.movielogin;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import util.LoginDTO;

import java.io.IOException;


public class LoginController {

    public Button forgotPasswordButton;
    public ChoiceBox <String >pcChoiceBox;
    public Label titleLabel;
    private Main main;
    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    @FXML
    void loginAction(ActionEvent event) {

        String userName = userText.getText();
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        try {
            main.getNetworkUtil().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    public void initialize(Main main){
        this.main = main;
        initiateChoiceBox();
        pcChoiceBox.setOnAction(this::setPCName);
    }
    public void setPCName(ActionEvent event){
        userText.setText(pcChoiceBox.getValue());
    }
    void initiateChoiceBox(){
        pcChoiceBox.getItems().setAll(FXCollections.observableArrayList(main.getListofPC()));
//        pcChoiceBox.setValue("");
    }
    public void onForgotPasswordButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ResetPasswordWindow.fxml"));
        Parent root = loader.load();

//      ReadFromFile the controller
        ResetPasswordController resetPasswordController= loader.getController();
        resetPasswordController.initialize(this.main);
        resetPasswordController.setPreScene(forgotPasswordButton.getScene());

        main.getStage().setScene(new Scene(root));
        main.getStage().show();
    }
}
