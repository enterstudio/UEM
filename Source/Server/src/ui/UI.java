
package ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Data_services;
import db_objects.ParkingBay;

public class UI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        
        final Lot lot = new Lot(1000, 1000);
        
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
    
}
