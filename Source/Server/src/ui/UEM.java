
package ui;

import db_objects.ParkingBay;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Data_services;
import serial_comm.Serial;
import ui.View.ParkingLotController;
import ui.View.RootLayoutController;
import ui.View.SettingsDialogController;
import ui.Model.Bay;

public class UEM extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ParkingLotController lotController;
    
    private ObservableList<Bay> parkingBays = FXCollections.observableArrayList();
    private int timeoutAlert;
    
    // Serial Comm Elements //
    private final Serial serial = new Serial();
    private ChangeListener<String> listener;
    private final BooleanProperty connection = new SimpleBooleanProperty(false);
    
    Timer timer = new java.util.Timer();
    
    public UEM() {
        try {
            parkingBays = getParkingBaysFromDB();
        } catch (ParseException ex) {
            Logger.getLogger(UEM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ObservableList<Bay> getParkingBays() {
        return parkingBays;
    }
    
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UEM.class.getResource("View/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
        
            primaryStage.show();
            
            setTimeline();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showParkingLot() {
        try {
            // Load ParkingLot View.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UEM.class.getResource("View/ParkingLot.fxml"));
            AnchorPane ParkingLotView = (AnchorPane) loader.load();

            // Set ParkingLot View into the center of root layout.
            rootLayout.setCenter(ParkingLotView);
            
            lotController = loader.getController();
            lotController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UEM");
        
        initRootLayout();
        
        showParkingLot();
        
        addSerialListener();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ObservableList<Bay> getParkingBaysFromDB() throws ParseException {
        Data_services data = new Data_services();
        List<ParkingBay> bays = data.getAllParkingBaysForUI();
        ObservableList<Bay> list = FXCollections.observableArrayList();
        Tooltip tooltip;
        for (ParkingBay pbay : bays) {
            Image img;
            if (pbay.state) {
                img = new Image(getClass().getResourceAsStream("View\\resources\\available.gif"));
            } else {
                img = new Image(getClass().getResourceAsStream("View\\resources\\occupied.gif"));
            }
            tooltip = createToolTip(pbay.identifier, pbay.state, pbay.datetime);
            Bay tmp = new Bay(pbay.x, pbay.y, pbay.state, img, pbay.identifier, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pbay.datetime), pbay.rotation);
            Tooltip.install(tmp, tooltip);
            list.add(tmp);
        }
        
        return list;
    }

    private Tooltip createToolTip(String identifier, boolean state, String datetime) {
        return new Tooltip("Sensor ID: " + identifier + "\n"
                                    + "Available: " + state + "\n"
                                    + "Time of change: " + datetime);
    }

    private void addSerialListener() {
        // Add Serial Listener //
        listener=(ov, t, t1) -> {
            Platform.runLater(()->{
                try{
                    String data=t1;
                    String id = parseSensorID(data);
                    Boolean state;
                    state = !parseState(data).equals("1");
                    System.out.println(data);
                    updateParkingBayUEM(id, state);
                } catch(NumberFormatException nfe){
                    System.out.println("NFE: "+t1+" "+nfe.toString());
                }
            });
        };
        serial.getLine().addListener(listener);
    }
    
    private String parseSensorID(String iL) {
        //return iL.substring(0, 4);
        int i = iL.indexOf(':');
        return iL.substring(0, i);
    }

    private Object parseState(String iL) {
        int i = iL.indexOf(':');
        return String.valueOf(iL.charAt(i+1));
    }

    private void updateParkingBayUEM(String id, Boolean state) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        
        logic.Data_services DS = new logic.Data_services();
        DS.sensorStateChange(id, state);
        
        Bay updateBay;
        Image image;
        int i = 0;
        if (state) {
            image = new Image(getClass().getResourceAsStream("View\\resources\\available.gif")); 
        } else {
            image = new Image(getClass().getResourceAsStream("View\\resources\\occupied.gif")); 
        }
        for (Node bay : parkingBays) {
            Bay tmp = (Bay) bay;
            if (id.equals(tmp.getBayId())) {
                tmp.setState(state);
                tmp.setImage(image);
                tmp.setDatetime(date);
                Tooltip.install(tmp, createToolTip(id, state, timeStamp));
                parkingBays.set(i, tmp);
                break;
            }
            i++;
        }
    }
    
    public void startSerial() {
        serial.connect();
        connection.set(!serial.getPortName().isEmpty());
        
        
    }

    public void stopSerial() {
        serial.disconnect();
    }

    public void addNewParkingBay(String id) {
        
                
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        
        Bay tmp = new Bay(100, 100, true, new Image(getClass().getResourceAsStream("View\\resources\\available.gif")), id, date, 0.0);
        
        Data_services data = new Data_services();
        data.addNewParkingBay(id, "1", timeStamp);
                
        Tooltip bayTip = createToolTip(id, Boolean.TRUE, timeStamp);
        Tooltip.install(tmp, bayTip);
        parkingBays.add(tmp);
        lotController.addNewParkingBay(tmp);
    }
    
    public boolean showSettingsDialog(int time) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UEM.class.getResource("view/SettingsDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Settings");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            //scene.getRoot().setEffect(new DropShadow(10.0, Color.BLACK));
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            SettingsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTimeOutAlert(timeoutAlert);
            controller.setMainApp(this);
            primaryStage.getScene().getRoot().setEffect(new BoxBlur());
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            primaryStage.getScene().getRoot().setEffect(null);
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTimeout() {
        return timeoutAlert;
    }

    public void setTimeout(int time) {
        this.timeoutAlert = time;
    }

    private void setTimeline() {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
        public void run() {
            Platform.runLater(new Runnable() {
                public void run() {
                    if (getTimeout() > 0)
                        markTimeoutBays(getTimeout());
                }

                private void markTimeoutBays(int timeoutAlert) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.HOUR_OF_DAY, 0-timeoutAlert);
                    Image expiredImage = new Image(getClass().getResourceAsStream("View\\resources\\Expired.gif"));
                    Image stateImage;
                    for (Node bay : getParkingBays()) {
                        Bay tmp = (Bay) bay;
                        Calendar then = Calendar.getInstance();
                        then.setTime(tmp.getDatetime());
                        if (!tmp.isState())
                            stateImage = new Image(getClass().getResourceAsStream("View\\resources\\occupied.gif"));
                        else
                            stateImage = new Image(getClass().getResourceAsStream("View\\resources\\available.gif"));
                        if (cal.after(then) && !tmp.isState()) {
                            tmp.setImage(expiredImage);
                        } else {
                            tmp.setImage(stateImage);
                        }
                    }
                }
            });
        }
        },0,10000);
    }

    public void addDeleteParkingBay() {
        int i = 0;
        List<Bay> removeList = new ArrayList<>();
        Data_services data = new Data_services();
        for (Node bay : parkingBays) {
            Bay tmp = (Bay) bay;
            if (tmp.isSelected()) {
                removeList.add(tmp);
            }
            i++;
        }
        
        for (Bay item : removeList) {
            parkingBays.remove(item);
            lotController.deleteParkingBay(item);
            data.deleteParkingBay(item.getBayId());
        }
    }
    
}
