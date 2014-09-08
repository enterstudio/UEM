
package ui;

import db_objects.ParkingBay;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Data_services;
import serial_comm.Serial2;

public class UI extends Application {
    ////    Serial Comm Elements    ////
    private final Serial2 serial = new Serial2();
    private ChangeListener<String> listener;
    private final BooleanProperty connection = new SimpleBooleanProperty(false);
    ////////////
    private final Lot lot = new Lot(1000, 1000);
    
    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
        listener=(ov, t, t1) -> {
            Platform.runLater(()->{
                try{
                    String[] data=t1.split(Serial2.SEPARATOR);
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
        
        startSerial();
    }
    
    private void startSerial() {
        serial.connect();
        connection.set(!serial.getPortName().isEmpty());
    }

    public static void main(String[] args) 
    {
        /*//serial_comm.Serial2 main = new serial_comm.Serial2();
        //main.initialize();
        Thread t=new Thread() 
        {
            public void run() 
            {
                //the following line will keep this app alive for 1000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
            }
        };
        t.start();
        System.out.println("SC Thread Started");
        */
        launch(args);

    }

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        
        final List<Bay> bays = getParkingBaysFromDB();
        lot.getChildren().addAll(bays);

        VBox vb = new VBox(10);
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
        for (ParkingBay pbay : bays) {
            Image img;
            if (pbay.state) {
                img = new Image(getClass().getResourceAsStream("resources\\available.gif"));
            } else {
                img = new Image(getClass().getResourceAsStream("resources\\occupied.gif"));
            }
            list.add(new Bay(pbay.x, pbay.y, pbay.state, img, pbay.identifier));
        }
        
        return list;
    }
    
    private void updateParkingBay(String id, Boolean state) {
        /*// TEST DATA //
        Image img = new Image(getClass().getResourceAsStream("resources\\available.gif"));
        Bay test = new Bay(1, 1, false, img, "0000");
        lot.getChildren().set(0, test);
        // END TEST //*/
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
