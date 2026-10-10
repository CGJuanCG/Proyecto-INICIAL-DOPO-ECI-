import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineC4Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineC4Test
{
    /**
     * Default constructor for test class SlotMachineC4Test
     */
    public SlotMachineC4Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    
    /**
     * Test that if a wheel is locked, it shouldn't spin and the last move should  be false
     **/
     @Test
     public void shouldFailWhenSpinningLockedWheel(){
            SlotMachine machine = new SlotMachine(3);
            machine.lock(1);
            machine.spin(1,1);
            assertFalse(machine.ok());
        }
    
    /**
     * The opposite case of a locked wheel, the last move should be true
     * if a wheel is free 
     **/
     @Test
     public void shouldSucceedWhenSpinningFreeWheel(){
            SlotMachine machine = new SlotMachine(3);
            machine.spin(1,1);
            assertTrue(machine.ok());
        }
    
}