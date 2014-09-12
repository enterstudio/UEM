
package ui;

import db_objects.ParkingBay;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import logic.Data_services;
import serial_comm.Serial;

public class UI extends Application {
    ////    Serial Comm Elements    ////
    private final Serial serial = new Serial();
    private ChangeListener<String> listener;
    private final BooleanProperty connection = new SimpleBooleanProperty(false);
    ////////////
    private final Lot lot = new Lot(800, 800);
    
    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
        listener=(ov, t, t1) -> {
            Platform.runLater(()->{
                try{
                    String[] data=t1.split(Serial.SEPARATOR);
                    String id = parseSensorID(data[1]);
                    Boolean state;
                    if (parseState(data[1]).equals("1"))
                        state = false;
                    else
                        state = true;
                    System.out.println(data[1]);
                    updateParkingBay(id, state);
                } catch(NumberFormatException nfe){
                    System.out.println("NFE: "+t1+" "+nfe.toString());
                }
            });
        };
        serial.getLine().addListener(listener);
        Label lbl = new Label("Not connected");
        connection.addListener((ov, b, b1)->lbl.setText(b1?
                "Connected to: "+serial.getPortName():"Not connected"));
        
        
    }
    
    private void startSerial() {
        System.out.println("Opening serial port");
        serial.connect();
        connection.set(!serial.getPortName().isEmpty());
    }

    private void stopSerial(){
        System.out.println("Closing serial port");
        serial.disconnect();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        
        ToolBar toolBar = new ToolBar();
        ToggleButton serialButton = new ToggleButton();
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
        serialButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (serialButton.isSelected())
                    startSerial();
                else
                    stopSerial();
            }
        });
        Image createImage = new Image(getClass().getResourceAsStream("resources\\newbay.gif"));
        ImageView createView = new ImageView(createImage);
        createView.setFitHeight(48);
        createView.setFitWidth(24);
        createView.preserveRatioProperty();
        Button createBay = new Button("Create Bay");
        Tooltip.install(createBay, new Tooltip("Create a new Parking Bay"));
        createBay.setGraphic(createView);
        createBay.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /*final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("This is a Dialog"));
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();*/
                String id = JOptionPane.showInputDialog("Enter sensor ID of parking bay");
                Bay tmp = new Bay(100, 100, true, new Image(getClass().getResourceAsStream("resources\\available.gif")), id);
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String timeStamp = dateFormat.format(date);
                
                Data_services data = new Data_services();
                data.addNewParkingBay(id, "1", timeStamp);
                
                Tooltip bayTip = createToolTip(id, Boolean.TRUE, timeStamp);
                Tooltip.install(tmp, bayTip);
                lot.getChildren().add(tmp);
            }
        });
        
        toolBar.getItems().addAll(serialButton,createBay);
        
        final List<Bay> bays = getParkingBaysFromDB();
        lot.getChildren().addAll(bays);
 
       VBox vb = new VBox(10);
        vb.getChildren().addAll(toolBar);
        vb.getChildren().addAll(lot);
        root.getChildren().addAll(vb);
        
        
    }
    
    private List<Bay> getParkingBaysFromDB() {
        // TEST DATA //
        /*Image available = new Image(getClass().getResourceAsStream("resources\\available.gif"));
        Image occupied = new Image(getClass().getResourceAsStream("resources\\occupied.gif"));
        List<Bay> tmp = new ArrayList<>();
        Bay bay1 = new Bay(1, 1, true, available, "0000 0000");
        Bay bay2 = new Bay(101, 1, false, occupied, "0000 0001");
        Bay bay3 = new Bay(201, 1, true, available, "0000 0010");
        tmp.add(bay1);
        tmp.add(bay2);
        tmp.add(bay3);*/
        
        // END TEST //
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
    
    private Tooltip createToolTip(String id, Boolean state, String time) {
        return new Tooltip("Sensor ID: " + id + "\n"
                                    + "Available: " + state + "\n"
                                    + "Time of change: " + time);
    }
    
    private void updateParkingBay(String id, Boolean state) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        
        logic.Data_services DS = new logic.Data_services();
        DS.sensorStateChange(id, state);
        
        Bay updateBay;
        ObservableList<Node> bays = lot.getChildren();
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
                lot.getChildren().set(i, tmp);
                break;
            }
            i++;
        }    
    }
    
     protected String parseSensorID(String iL)
     {
         return iL.substring(0, 4);
     }
 
     protected String parseState(String iL)
     {
         return String.valueOf(iL.charAt(5));
     }
    
}
