/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.View;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.UEM;


public class LoginDialogController implements Initializable {
    private UEM mainApp;
    private boolean loginClicked = false;
    private Stage dialogStage;
    
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label validationLabel;
    
    @FXML
    private void handleLoginButtonAction() {
        loginClicked = true;
        if (mainApp.authenticateUser(usernameField.getText(), passwordField.getText()))
            dialogStage.close();
        else
            validationLabel.setText("Invalid Username or Password");       
    }
    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        mainApp.stopSerial();
        mainApp.stopTimeLine();
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(UEM mainApp) {
        this.mainApp = mainApp;
    }

    public boolean isLoginClicked() {
        return loginClicked;
    }
    
}
