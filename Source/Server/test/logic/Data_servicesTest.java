
package logic;

import db_objects.ParkingBay;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Data_servicesTest {
    
    public Data_servicesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sensorStateChange method, of class Data_services.
     */
    @Test
    public void testSensorStateChange() {
        System.out.println("sensorStateChange");
        String sensorID = "00000010";
        boolean state = true;
        Data_services instance = new Data_services();
        instance.sensorStateChange(sensorID, state);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addNewParkingBay method, of class Data_services.
     */
    @Test
    public void testAddNewParkingBay() {
        System.out.println("addNewParkingBay");
        String sensorID = "";
        String lotID = "";
        String timeStamp = "";
        Data_services instance = new Data_services();
        instance.addNewParkingBay(sensorID, lotID, timeStamp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllParkingBaysForUI method, of class Data_services.
     */
    @Test
    public void testGetAllParkingBaysForUI() {
        System.out.println("getAllParkingBaysForUI");
        Data_services instance = new Data_services();
        List<ParkingBay> expResult = null;
        List<ParkingBay> result = instance.getAllParkingBaysForUI();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of savePositionForUI method, of class Data_services.
     */
    @Test
    public void testSavePositionForUI() {
        System.out.println("savePositionForUI");
        String id = "";
        double x = 0.0;
        double y = 0.0;
        Data_services instance = new Data_services();
        instance.savePositionForUI(id, x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeStampByID method, of class Data_services.
     */
    @Test
    public void testGetTimeStampByID() {
        System.out.println("getTimeStampByID");
        String id = "0000";
        Data_services instance = new Data_services();
        Boolean expResult = true;
        Boolean result = instance.getTimeStampByID(id);
        assertEquals(expResult, result);
        
    }
    
}
