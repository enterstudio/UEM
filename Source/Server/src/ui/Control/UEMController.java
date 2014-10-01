
package ui.Control;

import db_objects.ParkingBay;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import logic.Data_services;
import serial_comm.Serial;
import ui.Bay;

public class UEMController implements Initializable {
    // Serial Comm Elements //
    private final Serial serial = new Serial();
    private ChangeListener<String> listener;
    private final BooleanProperty connection = new SimpleBooleanProperty(false);
    
    // FXML Components //
    @FXML
    private Button newBay;
    @FXML
    private ToggleButton serialButton;
    @FXML
    private Pane ParkingLot;
    @FXML
    private Label serialStatus;
    // Event Handlers //
    @FXML
    private void handleNewBayButtonAction(ActionEvent event) {
        String id = JOptionPane.showInputDialog("Enter sensor ID of parking bay");
                Bay tmp = new Bay(100, 100, true, new Image(getClass().getResourceAsStream("resources\\available.gif")), id);
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String timeStamp = dateFormat.format(date);
                
                Data_services data = new Data_services();
                data.addNewParkingBay(id, "1", timeStamp);
                
                Tooltip bayTip = createToolTip(id, Boolean.TRUE, timeStamp);
                Tooltip.install(tmp, bayTip);
                ParkingLot.getChildren().add(tmp);
    }
    
    @FXML
    private void handleSerialButtonAction(ActionEvent event) {
        if (serialButton.isSelected())
            startSerial();
        else
            stopSerial();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load Parking Bays From DB //
        final List<Bay> bays = getParkingBaysFromDB();
        ParkingLot.getChildren().addAll(bays);

        // Add Serial Listener //
        listener=(ov, t, t1) -> {
            Platform.runLater(()->{
                try{
                    String[] data=t1.split(Serial.SEPARATOR);
                    String id = parseSensorID(data[1]);
                    Boolean state;
                    state = !parseState(data[1]).equals("1");
                    System.out.println(data[1]);
                    updateParkingBay(id, state);
                } catch(NumberFormatException nfe){
                    System.out.println("NFE: "+t1+" "+nfe.toString());
                }
            });
        };
        serial.getLine().addListener(listener);
        connection.addListener((ov, b, b1)->serialStatus.setText(b1?
                serial.getPortName():"Not connected"));
        
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
        Tooltip.install(newBay, new Tooltip("Create a new Parking Bay"));
        newBay.setGraphic(createView);
    }   

    private List<Bay> getParkingBaysFromDB() {
        Data_services data = new Data_services();
        List<ParkingBay> bays = data.getAllParkingBaysForUI();
        List<Bay> list = new ArrayList<>();
        Tooltip tooltip;
        for (ParkingBay pbay : bays) {
            Image img;
            if (pbay.state) {
                img = new Image(getClass().getResourceAsStream("resources\\available.gif"));
            } else {
                img = new Image(getClass().getResourceAsStream("resources\\occupied.gif"));
            }
            tooltip = createToolTip(pbay.identifier, pbay.state, pbay.datetime);
            Bay tmp = new Bay(pbay.x, pbay.y, pbay.state, img, pbay.identifier);
            Tooltip.install(tmp, tooltip);
            list.add(tmp);
        }
        
        return list;
    }

    private Tooltip createToolTip(String id, boolean state, String time) {
        return new Tooltip("Sensor ID: " + id + "\n"
                                    + "Available: " + state + "\n"
                                    + "Time of change: " + time);
    }

    private String parseSensorID(String iL) {
        return iL.substring(0, 4);
    }

    private Object parseState(String iL) {
        return String.valueOf(iL.charAt(5));
    }

    private void updateParkingBay(String id, Boolean state) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        
        logic.Data_services DS = new logic.Data_services();
        DS.sensorStateChange(id, state);
        
        Bay updateBay;
        ObservableList<Node> bays = ParkingLot.getChildren();
        Image image;
        int i = 0;
        if (state) {
            image = new Image(getClass().getResourceAsStream("resources\\available.gif")); 
        } else {
            image = new Image(getClass().getResourceAsStream("resources\\occupied.gif")); 
        }
        for (Node bay : bays) {
            Bay tmp = (Bay) bay;
            if (id.equals(tmp.getBayId())) {
                tmp.setState(state);
                tmp.setImage(image);
                Tooltip.install(tmp, createToolTip(id, state, timeStamp));
                ParkingLot.getChildren().set(i, tmp);
                break;
            }
            i++;
        }
    }

    private void startSerial() {
        serial.connect();
        connection.set(!serial.getPortName().isEmpty());
        serialStatus.setText("Sensors Running");
        
    }

    private void stopSerial() {
        serial.disconnect();
        serialStatus.setText("Sensors Stopped");
    }
    
}
