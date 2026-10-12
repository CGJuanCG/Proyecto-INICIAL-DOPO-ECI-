import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineC4Test.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 11-10-2026
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

    /**
     * A normal wheel accepts being locked, so the move should succeed.
     */
    @Test
    public void shouldSucceedWhenLockingNormalWheel(){
        SlotMachine machine = new SlotMachine(3);
        machine.lock(1);
        assertTrue(machine.ok());
    }

    /**
     * Two normal wheels accept being swapped: the move should succeed and
     * the colors they show should trade places.
     */
    @Test
    public void shouldSucceedWhenSwappingNormalWheels(){
        SlotMachine machine = new SlotMachine(3);
        String[] before = machine.configuration();
        machine.swap(1, 3);
        assertTrue(machine.ok());
        String[] after = machine.configuration();
        assertEquals(before[0], after[2]);
        assertEquals(before[2], after[0]);
    }

    /**
     * A normal wheel accepts being removed: the move should succeed and the
     * machine should have one wheel less.
     */
    @Test
    public void shouldSucceedWhenDeletingNormalWheel(){
        SlotMachine machine = new SlotMachine(3);
        machine.delWheel(2);
        assertTrue(machine.ok());
        assertEquals(2, machine.configuration().length);
    }
}
