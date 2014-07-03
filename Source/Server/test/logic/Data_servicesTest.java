
package logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        boolean state = false;
        Data_services instance = new Data_services();
        instance.sensorStateChange(sensorID, state);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
