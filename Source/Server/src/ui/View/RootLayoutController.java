
package ui.View;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import ui.UEM;

public class RootLayoutController {
    private UEM mainApp;
    
    @FXML
    private Label serialStatus;
    @FXML
    private ToggleButton serialButton;
    @FXML
    private Button newBayButton;
    
    @FXML
    private void handleNewBayButtonAction(ActionEvent event) {
        String id = JOptionPane.showInputDialog("Enter sensor ID of parking bay");
        mainApp.addNewParkingBay(id);         
    }
    @FXML
    private void handleSerialButtonAction(ActionEvent event) {
        if (serialButton.isSelected()) {
            mainApp.startSerial();
            serialStatus.setText("Sensors Running");
        } else {
            mainApp.stopSerial();
            serialStatus.setText("Sensors Stopped");
        }
    }
    @FXML
    private void handleSettingsButtonAction(ActionEvent event) {
        int time = mainApp.getTimeout();
        boolean okClicked = mainApp.showSettingsDialog(time);
        /*if (okClicked) {
            //mainApp.setTimeout(time); 
        }*/
    }
    
    @FXML
    private void initialize() {
        // Serial Button Decoration //
        Image startImage = new Image(getClass().getResourceAsStream("resources\\start.png"));
        Image stopImage = new Image(getClass().getResourceAsStream("resources\\stop.png"));
        ImageView toggleImage = new ImageView();
        Tooltip tooltip = new Tooltip("Start/Stop Serial Communication");
        serialButton.setGraphic(toggleImage);
        serialButton.setUserData("start");
        Tooltip.install(serialButton, tooltip);
        toggleImage.imageProperty().bind(Bindings
                .when(serialButton.selectedProperty())
                    .then(stopImage)
                    .otherwise(startImage)
        );
        // Create Bay Button Decoration //
        Image createImage = new Image(getClass().getResourceAsStream("resources\\newbay.gif"));
        ImageView createView = new ImageView(createImage);
        createView.setFitHeight(48);
        createView.setFitWidth(24);
        createView.preserveRatioProperty();
        Tooltip.install(newBayButton, new Tooltip("Create a new Parking Bay"));
        newBayButton.setGraphic(createView);
    }
    
    public void setMainApp(UEM mainApp) {
        this.mainApp = mainApp;
    }
    
}
