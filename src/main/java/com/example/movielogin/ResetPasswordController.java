package com.example.movielogin;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.ResetPasswordUtil;

import java.io.IOException;

public class ResetPasswordController {
    public TextField password1;
    public TextField password2;
    public Button savePasswordButton;
    public Label newPasswordLabel;
    public Label resetPasswordLabel;
    public Button backButton;
    public Main main;
    public TextField pcName;
    public ChoiceBox<String > pcChoiceBox;


    private Scene preScene ;
    public void onSavePasswordButtonClick(ActionEvent event) throws IOException {

        if(pcName.getText().isEmpty() || password1.getText().isEmpty()|| password2.getText().isEmpty()){
            System.out.println(pcName.getText()+" "+ password1.getText() + " "+ password2.getText());
            main.showAlert("Error Saving Password", "No Field can be EMPTY");
        }
        else if( !password1.getText().equals(password2.getText())){
            main.showAlert("Error Saving Password","New Passwords are different!");
        }
        else {
            ResetPasswordUtil resetPasswordUtil = new ResetPasswordUtil(pcName.getText(), password1.getText());
            main.getNetworkUtil().write(resetPasswordUtil);
            main.showInformation("Reset Password", "Password Reset Successfully", "");
            main.getStage().setScene(preScene);
            main.getStage().show();
        }
    }

    public void setPreScene(Scene scene) {
        this.preScene = scene;
    }

    public void initialize(Main main) {
        this.main = main;

        initiateChoiceBox();
        pcChoiceBox.setOnAction(this::setPCName);
    }
    public void setPCName(ActionEvent event){
        pcName.setText(pcChoiceBox.getValue());
    }
    void initiateChoiceBox(){
        pcChoiceBox.getItems().setAll(FXCollections.observableArrayList(main.getListofPC()));
//        pcChoiceBox.setValue("");
    }

    public void onBackButtonClick(ActionEvent event) {

        main.getStage().setScene(preScene);
        main.getStage().show();

    }
}
