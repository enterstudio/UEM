
package ui.View;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import ui.UEM;

public class SettingsDialogController implements Initializable {
    private Stage dialogStage;
    private int timeoutAlert = 0;
    private boolean okClicked = false;
    private UEM mainApp;
    
    @FXML
    Slider overTimeSlider;
    @FXML
    Label hoursDisplayLabel;

    @FXML
    private void handleOK() {
        timeoutAlert = (int) overTimeSlider.getValue();
        
        okClicked = true;
        mainApp.setTimeout(timeoutAlert);
        dialogStage.close();
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleSliderChange() {
        int tmpHours = (int)overTimeSlider.getValue();
        hoursDisplayLabel.setText(tmpHours + " hours");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTimeOutAlert(int timeoutAlert) {
        this.timeoutAlert = timeoutAlert;
        overTimeSlider.setValue(timeoutAlert);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMainApp(UEM mainApp) {
        this.mainApp = mainApp;
    }
    
}
