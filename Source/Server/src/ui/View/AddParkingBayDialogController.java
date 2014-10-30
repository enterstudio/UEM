
package ui.View;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ui.UEM;


public class AddParkingBayDialogController implements Initializable {
    UEM mainApp;
    Stage dialogStage;
    private boolean addClicked = false;
    List<String> sensorIds = new ArrayList<>();
    //Single Bay Tab
    @FXML
    ComboBox sensorIdInput;
    @FXML
    TextField xInputField;
    @FXML
    TextField yInputField;
    @FXML
    Slider rotationInputField;
    @FXML
    private void handleSingleAdd() {
        addClicked = true;
        String tmpID = sensorIdInput.getValue().toString();
        int tmpX = Integer.parseInt(xInputField.getText());
        int tmpY = Integer.parseInt(yInputField.getText());
        Double tmpRot = rotationInputField.getValue();
        mainApp.addNewParkingBay(tmpID, tmpX, tmpY, tmpRot);
        dialogStage.close();
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    //Multiple Horizontal Bays Tab
    int numberOfBays = 0;
    @FXML
    TextField noBaysInputField;
    @FXML
    GridPane sensorAssignGrid;
    @FXML
    TextField xHInputField;
    @FXML
    TextField yHInputField;
    @FXML
    Slider rotationHInputField;
    //@FXML
    private void handleNumberOfBays() {
        try {
            sensorAssignGrid.getChildren().clear();
            int num = Integer.parseInt(noBaysInputField.getText());
            numberOfBays = num;
            for (int i = 1; i <= num; i++) {
                ComboBox sensorIdBox = new ComboBox(getSensorIds());
                sensorIdBox.setEditable(true);
                sensorIdBox.setId("sensorBox"+i);
                Label lbl = new Label("Bay "+i+": ");
                sensorAssignGrid.add(lbl, 0, i);
                sensorAssignGrid.add(sensorIdBox, 1, i);
            }
        } catch(Exception e) {
            //System.out.println("Not a number");
            noBaysInputField.setText("");
            //noBaysInputField.setEffect(new Shadow(0.1, Color.YELLOW));
        }
        
    }
    @FXML
    private void handleHAdd() {
        if (numberOfBays != 0) {
            ObservableList<Node> children = sensorAssignGrid.getChildren();
            for (int i = 1; i <= numberOfBays; i++) {
                String tmpID = new String();
                for (Node node : children) {
                    //int rI = sensorAssignGrid.getRowIndex(node);
                    //int cI = sensorAssignGrid.getColumnIndex(node);
                    if (sensorAssignGrid.getRowIndex(node) == i && sensorAssignGrid.getColumnIndex(node) == 1) {
                        ComboBox idBox = (ComboBox)node;
                        tmpID = idBox.getValue().toString();
                    }
                }
                Double tmpRot = rotationHInputField.getValue();
                Double delta = 50.0 * i;
                //if (tmpRot != 0)
                    //delta = 100*Math.cos(tmpRot)*i;
                delta = Math.sqrt(Math.pow(100.0, 2)+Math.pow(50.0, 2)-(2*100.0*50.0*Math.cos(Math.toRadians(tmpRot)))) * i;
                int tmpX = Integer.parseInt(xHInputField.getText()) + delta.intValue();
                int tmpY = Integer.parseInt(yHInputField.getText());
                mainApp.addNewParkingBay(tmpID, tmpX, tmpY, tmpRot);
            }
            dialogStage.close();
        }
    }
    
    //Multiple Vertical Bays Tab
    int numberOfBaysV = 0;
    @FXML
    TextField noBaysInputFieldV;
    @FXML
    GridPane sensorAssignGridV;
    @FXML
    TextField xVInputField;
    @FXML
    TextField yVInputField;
    @FXML
    Slider rotationVInputField;
    //@FXML
    private void handleNumberOfBaysV() {
        try {
            sensorAssignGridV.getChildren().clear();
            int num = Integer.parseInt(noBaysInputFieldV.getText());
            numberOfBaysV = num;
            for (int i = 1; i <= num; i++) {
                ComboBox sensorIdBox = new ComboBox(getSensorIds());
                sensorIdBox.setEditable(true);
                sensorIdBox.setId("sensorBoxV"+i);
                Label lbl = new Label("Bay "+i+": ");
                sensorAssignGridV.add(lbl, 0, i);
                sensorAssignGridV.add(sensorIdBox, 1, i);
            }
        } catch(Exception e) {
            //System.out.println("Not a number");
            noBaysInputFieldV.setText("");
            //noBaysInputFieldV.setEffect(new Shadow(0.2, Color.YELLOW));
        }
        
    }
    @FXML
    private void handleVAdd() {
        if (numberOfBaysV != 0) {
            ObservableList<Node> children = sensorAssignGridV.getChildren();
            for (int i = 1; i <= numberOfBaysV; i++) {
                String tmpID = new String();
                for (Node node : children) {
                    //int rI = sensorAssignGrid.getRowIndex(node);
                    //int cI = sensorAssignGrid.getColumnIndex(node);
                    if (sensorAssignGridV.getRowIndex(node) == i && sensorAssignGridV.getColumnIndex(node) == 1) {
                        ComboBox idBox = (ComboBox)node;
                        tmpID = idBox.getValue().toString();
                    }
                }
                Double tmpRot = rotationVInputField.getValue();
                Double delta = 50.0 * i;
                //if (tmpRot != 0)
                    //delta = 100*Math.cos(tmpRot)*i;
                delta = Math.sqrt(Math.pow(100.0, 2)+Math.pow(50.0, 2)-(2*100.0*50.0*Math.cos(Math.toRadians(tmpRot)))) * i;
                int tmpX = Integer.parseInt(xVInputField.getText());
                int tmpY = Integer.parseInt(yVInputField.getText()) + delta.intValue();
                mainApp.addNewParkingBay(tmpID, tmpX, tmpY, tmpRot);
            }
            dialogStage.close();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noBaysInputFieldV.textProperty().addListener((observable, oldValue, newValue) -> {
            handleNumberOfBaysV();
        });
        noBaysInputField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleNumberOfBays();
        });
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

    private ObservableList getSensorIds() {
        ObservableList tmp = FXCollections.observableArrayList("0000","0001","0010");
        return tmp;
    }
}
