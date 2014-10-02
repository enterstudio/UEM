
package serial_comm;


import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Serial {

    private static final List<String> USUAL_PORTS = Arrays.asList(
        "/dev/tty.usbmodem", "/dev/tty.usbserial", // Mac OS X
        "/dev/usbdev","/dev/ttyUSB","/dev/ttyACM", "/dev/serial", // Linux
        "COM3","COM4","COM5","COM6" // Windows
        );
    private final String ardPort;
    private SerialPort serPort;
    
    public static final String SEPARATOR = ";";
    private StringBuilder sb=new StringBuilder();
    private final StringProperty line=new SimpleStringProperty("");
    
    public Serial(){
        ardPort="";
    }
    
    public Serial(String port){
        ardPort=port;
    }

    public boolean connect(){
        Arrays.asList(SerialPortList.getPortNames())
                .stream()
                .filter(name-> 
                        ((!ardPort.isEmpty() && name.equals(ardPort)) || 
                         (ardPort.isEmpty() && 
                          USUAL_PORTS.stream()
                                     .anyMatch(p -> name.startsWith(p)))))
                .findFirst()
                .ifPresent(name -> {
                    try {
                        serPort = new SerialPort(name);
                        System.out.println("Connecting to "+serPort.getPortName()); 
                        if(serPort.openPort()){
                            serPort.setParams(SerialPort.BAUDRATE_9600, 
                                              SerialPort.DATABITS_8,
                                              SerialPort.STOPBITS_1, 
                                              SerialPort.PARITY_NONE);
                            serPort.setEventsMask(SerialPort.MASK_RXCHAR);
                            serPort.addEventListener(event -> {
                                if(event.isRXCHAR()){
                                    try {
                                        sb.append(serPort.readString(event.getEventValue()));
                                        String ch=sb.toString();
                                        if(ch.endsWith("\r\n")){
                                            line.set(ch.substring(0,
                                                                  ch.indexOf("\r\n")));
                                            sb=new StringBuilder();
                                        }
                                    } catch (SerialPortException e) {
                                        System.out.println("SerialEvent error:"+ e.toString());
                                    }
                                }
                            });
                        }
                    } catch (SerialPortException ex) {
                        System.out.println("ERROR: Port '" + name + "': "+ex.toString());
                    } 
                });
        return serPort!=null;
    }
    
    public void disconnect(){
        if (serPort != null) {
            try {
                serPort.removeEventListener();
                
                if(serPort.isOpened()){
                    serPort.closePort();
                }
            } catch (SerialPortException ex) {
                System.out.println("ERROR closing port exception: "+ex.toString());
            }
            System.out.println("Disconnecting: comm port closed.");
        }
    }

    public StringProperty getLine(){
        return line;
    }
    
    public String getPortName(){
        return serPort!=null?serPort.getPortName():"";
    }
}

