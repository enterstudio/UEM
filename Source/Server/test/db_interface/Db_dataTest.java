
package db_interface;

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
        String sensorID = "00000001";
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
    
}
