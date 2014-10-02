
package ui.View;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ui.Model.Bay;
import ui.UEM;

public class ParkingLotController {
    private UEM mainApp;
    
    @FXML
    private Pane ParkingLot;
    
    public ParkingLotController() {
        
    }
    
    @FXML
    private void initialize() {
        
    }
    
    public void setMainApp(UEM mainApp) {
        this.mainApp = mainApp;
        
        ParkingLot.getChildren().addAll(mainApp.getParkingBays());
    }

    public void addNewParkingBay(Bay tmp) {
        ParkingLot.getChildren().add(tmp);
    }

}
