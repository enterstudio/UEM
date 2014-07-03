
package db_interface;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Db_connectTest {
    
    public Db_connectTest() {
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
     * Test of connect method, of class Db_connect.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        Db_connect instance = new Db_connect();
        instance.connect();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class Db_connect.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        Db_connect instance = new Db_connect();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getConnection method, of class Db_connect.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Db_connect instance = new Db_connect();
        Connection expResult = null;
        Connection result = instance.getConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
