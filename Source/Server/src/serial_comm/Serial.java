/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial_comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Arrays;
import java.util.Enumeration;


/**
 *
 * @author zaphlor
 */
public class Serial implements SerialPortEventListener{
    SerialPort serialPort;
    private String line = new String();
        /** The port we're normally going to use. */
 private static final String PORT_NAMES[] = { 
                        "/dev/ttyACM0", //for Ubuntu
   "/dev/tty.usbserial-A9007UX1", // Mac OS X
   "/dev/ttyUSB0", // Linux
   "COM3", // Windows
 };
 /**
 * A BufferedReader which will be fed by a InputStreamReader 
 * converting the bytes into characters 
 * making the displayed results codepage independent
 */
 private BufferedReader input;
 /** The output stream to the port */
 private OutputStream output;
 /** Milliseconds to block while waiting for port open */
 private static final int TIME_OUT = 2000;
 /** Default bits per second for COM port. */
 private static final int DATA_RATE = 9600;
 
 private char[] oldState;
 
 public void initialize() {
  CommPortIdentifier portId = null;
  Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
 
  //First, Find an instance of serial port as set in PORT_NAMES.
  while (portEnum.hasMoreElements()) {
   CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
   for (String portName : PORT_NAMES) 
   {
    System.out.println(currPortId.getName());//   
    if (currPortId.getName().equals(portName)) {
     portId = currPortId;
     break;
    }
   }
  }
  if (portId == null) {
   System.out.println("Could not find COM port.");
   return;
  }
 
  try {
   // open serial port, and use class name for the appName.
   serialPort = (SerialPort) portId.open(this.getClass().getName(),
     TIME_OUT);
 
   // set port parameters
   serialPort.setSerialPortParams(DATA_RATE,
     SerialPort.DATABITS_8,
     SerialPort.STOPBITS_1,
     SerialPort.PARITY_NONE);
 
   // open the streams
   input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
   output = serialPort.getOutputStream();
 
   // add event listeners
   serialPort.addEventListener(this);
   serialPort.notifyOnDataAvailable(true);
  } catch (Exception e) {
   System.err.println(e.toString());
  }
 }
 
 /**
  * This should be called when you stop using the port.
  * This will prevent port locking on platforms like Linux.
  */
 public synchronized void close() {
  if (serialPort != null) {
   serialPort.removeEventListener();
   serialPort.close();
  }
 }
 
 protected boolean testForChange(String iL)
 {
    String sensorID, state;
    int idIndex;
    int tmpOldState;
    
    sensorID = parseSensorID(iL); //the 4 bit ID eg 000
    state = parseState(iL); // new state 1 or 0
    idIndex = getSIDIndex(sensorID); //int id of sensor 1..4etc
    /*old version using locally stored state
    if(oldState == null)
    {
        char tmp[] = {'0','0','0','0'};
        oldState = Arrays.copyOf(tmp, 3);
        oldState[idIndex] = '1';
        //oldState = "0000"; //four sensors init
        //oldState.
        updateDatabase(oldState, sensorID, state);
    }
    else
    {
        //test agin then possibly
        //update DB
        //set global flag for UI thread to query updated database
    }
    */
    //new version test where query database
    
    tmpOldState = getOldState(sensorID);
    if (tmpOldState == Integer.parseInt(state))
    {
        System.out.println("State not changed, Db not Updated");
        System.out.println("Sensor ID : " + sensorID + ", was in state : " + tmpOldState + ", now in state : " + state);
        return false;
    }
    else
    {
        updateDatabase(sensorID, state);
        System.out.println("State changed, Db not Updated");
        System.out.println("Sensor ID : " + sensorID + ", was in state : " + tmpOldState + ", now in state : " + state);
        return true;
    }
 }
 
 protected int getOldState(String sensorID)//get from databse to decide whether or not to update
 {
     //TODO
     return 0;
 }
 
 protected void updateDatabase(String sID, String sState)
 {
     logic.Data_services DS = new logic.Data_services();
     
     if(sState.equals("1"))
     {
         
         DS.sensorStateChange(sID, true);
     }
     else
     {
         DS.sensorStateChange(sID, false);
     }
     
     
     //TODO
 }
 
 protected String parseSensorID(String iL)
 {
     return iL.substring(0, 4);
 }
 
 protected String parseState(String iL)
 {
     return String.valueOf(iL.charAt(5));
 }
 
 protected int getSIDIndex(String sID)
 {
     switch(sID)
     {
        case "0000" : return 0;
        case "0001" : return 1;
        case "0010" : return 2;
        case "0011" : return 3;
        default     : return 4; // error in input...for now     
     }
 }
 
 public String getLine() {
     return line;
 }
 private String getSensorIDFromEvent(String input) {
     return parseSensorID(input);
 }
 /**
  * Handle an event on the serial port. Read the data and print it.
  */
 public synchronized void serialEvent(SerialPortEvent oEvent) {
  if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
   try {
    String inputLine=input.readLine();
    //added
    boolean change = testForChange(inputLine);
    line = getSensorIDFromEvent(inputLine);
    //end
    System.out.println(inputLine);
   } catch (Exception e) {
    //System.err.println(e.toString());
   }
  }
  // Ignore all the other eventTypes, but you should consider the other ones.
 }
 /**
 public static void main(String[] args) throws Exception {
  SerialTest main = new SerialTest();
  main.initialize();
  Thread t=new Thread() {
   public void run() {
    //the following line will keep this app alive for 1000 seconds,
    //waiting for events to occur and responding to them (printing incoming messages to console).
    try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
   }
  };
  t.start();
  System.out.println("Started");
 }
 **/ 
}
