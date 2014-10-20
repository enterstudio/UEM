
package db_interface;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Db_dataTest {
    
    public Db_dataTest() {
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
     * Test of getAllParkingBays method, of class Db_data.
     */
    @Test
    public void testGetAllParkingBays() {
        System.out.println("getAllParkingBays");
        Db_data instance = new Db_data();
        LinkedHashMap expResult = null;
        LinkedHashMap result = instance.getAllParkingBays();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if (result.isEmpty())
            fail("No parking bays were loaded.");
    }

    /**
     * Test of getAllParkingLots method, of class Db_data.
     */
    @Test
    public void testGetAllParkingLots() {
        System.out.println("getAllParkingLots");
        Db_data instance = new Db_data();
        LinkedHashMap expResult = null;
        LinkedHashMap result = instance.getAllParkingLots();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if (result.isEmpty())
            fail("No parking lots loaded.");
    }

    /**
     * Test of getAllPersonnel method, of class Db_data.
     */
    @Test
    public void testGetAllPersonnel() {
        System.out.println("getAllPersonnel");
        Db_data instance = new Db_data();
        LinkedHashMap expResult = null;
        LinkedHashMap result = instance.getAllPersonnel();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if (result.isEmpty())
            fail("No personnel loaded.");
    }

    /**
     * Test of updateParkingBayFromIdentifier method, of class Db_data.
     */
    @Test
    public void testUpdateParkingBayFromIdentifier() {
        System.out.println("updateParkingBayFromIdentifier");
        String sensorID = "0001";
        boolean state = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        //String timeStamp = "";
        Db_data instance = new Db_data();
        instance.updateParkingBayFromIdentifier(sensorID, state, timeStamp);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class Db_data.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        String query = "";
        Db_data instance = new Db_data();
        ResultSet expResult = null;
        ResultSet result = instance.get(query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConn method, of class Db_data.
     */
    @Test
    public void testCloseConn() {
        System.out.println("closeConn");
        Db_data instance = new Db_data();
        instance.closeConn();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNewParkingBayToDB method, of class Db_data.
     */
    @Test
    public void testAddNewParkingBayToDB_3args() {
        System.out.println("addNewParkingBayToDB");
        String sensorID = "";
        String lotID = "";
        String timeStamp = "";
        Db_data instance = new Db_data();
        instance.addNewParkingBayToDB(sensorID, lotID, timeStamp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUIPosition method, of class Db_data.
     */
    @Test
    public void testUpdateUIPosition() {
        System.out.println("updateUIPosition");
        String id = "";
        int x = 0;
        int y = 0;
        Db_data instance = new Db_data();
        instance.updateUIPosition(id, x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeStampForUI method, of class Db_data.
     */
    @Test
    public void testGetTimeStampForUI() {
        System.out.println("getTimeStampForUI");
        String in = "";
        Db_data instance = new Db_data();
        LinkedHashMap expResult = null;
        LinkedHashMap result = instance.getTimeStampForUI(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteParkingBay method, of class Db_data.
     */
    @Test
    public void testDeleteParkingBay() {
        System.out.println("deleteParkingBay");
        String bayId = "";
        Db_data instance = new Db_data();
        instance.deleteParkingBay(bayId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNewParkingBayToDB method, of class Db_data.
     */
    @Test
    public void testAddNewParkingBayToDB_6args() {
        System.out.println("addNewParkingBayToDB");
        String sensorID = "";
        String lotID = "";
        int x = 0;
        int y = 0;
        Double rot = null;
        String timeStamp = "";
        Db_data instance = new Db_data();
        instance.addNewParkingBayToDB(sensorID, lotID, x, y, rot, timeStamp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logChange method, of class Db_data.
     */
    @Test
    public void testLogChange() {
        System.out.println("logChange");
        String sensorID = "";
        String type = "";
        String event = "";
        String timeStamp = "";
        Db_data instance = new Db_data();
        instance.logChange(sensorID, type, event, timeStamp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFromLog method, of class Db_data.
     */
    @Test
    public void testGetFromLog() {
        System.out.println("getFromLog");
        String type = "";
        String event = "";
        Db_data instance = new Db_data();
        LinkedHashMap expResult = null;
        LinkedHashMap result = instance.getFromLog(type, event);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserLevel method, of class Db_data.
     */
    @Test
    public void testGetUserLevel() {
        System.out.println("getUserLevel");
        String username = "";
        String password = "";
        Db_data instance = new Db_data();
        int expResult = 0;
        int result = instance.getUserLevel(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
