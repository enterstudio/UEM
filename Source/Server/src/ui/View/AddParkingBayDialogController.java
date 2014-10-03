
package ui.View;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.UEM;


public class AddParkingBayDialogController implements Initializable {
    UEM mainApp;
    Stage dialogStage;
    private boolean addClicked = false;
    List<String> sensorIds = new ArrayList<>();

    @FXML
    ComboBox sensorIdInput;
    @FXML
    TextField xInputField;
    @FXML
    TextField yInputField;
    @FXML
    Slider rotationInputField;
    @FXML
    private void handleAdd() {
        addClicked = true;
        String tmpID = sensorIdInput.getValue().toString();
        int tmpX = Integer.parseInt(xInputField.getText());
        int tmpY = Integer.parseInt(yInputField.getText());
        Double tmpRot = rotationInputField.getValue();
        mainApp.addNewParkingBay(tmpID, tmpY, tmpY, tmpRot);
        dialogStage.close();
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setMainApp(UEM mainApp) {
        this.mainApp = mainApp;        
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSensorIDs(List<String> sensorIDs) {
        sensorIDs.stream().forEach((id) -> {
            sensorIdInput.getItems().add(id);
        });
    }

    public boolean isAddClicked() {
        return addClicked;
    }
}
