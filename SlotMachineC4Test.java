import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
    
    @Test
    public void shouldCreateARebelWheel(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("rebel",1);
        assertTrue(machine.ok());
        assertEquals(4,machine.configuration().length);
    }
    
    @Test
    public void shouldNotLockARebelWheel(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("rebel",1);
        machine.lock(1);
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldNotSwapARebelWheel(){
        SlotMachine machine = new SlotMachine(3);
        String[] antes;
        machine.addWheel("rebel",1);
        antes = machine.configuration();
        machine.swap(1,2);
        assertFalse(machine.ok());
        assertArrayEquals(antes,machine.configuration());
    }
    
    @Test
    public void shouldNotDeleteARebelWheel(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("rebel",1);
        machine.delWheel(1);
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldNotDeleteARebelWheel2(){
        SlotMachine machine = new SlotMachine(3);
        String[] antes;
        machine.addWheel("rebel",1);
        antes = machine.configuration();
        machine.delWheel(1);
        assertEquals(antes.length,machine.configuration().length);
    }
    
    @Test
    public void shouldCopyLeftNeighborWhenSpinningALeftyWheel(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("lefty",2);
        machine.spin(2);
        assertTrue(machine.ok());
        String[] configuracion = machine.configuration();
        assertEquals(4,configuracion.length);
        assertEquals(configuracion[0],configuracion[1]);
    }

    /**
     * A lefty wheel in the first place has no wheel on its left, so it should
     * spin like a normal wheel and the move should succeed.
     */
    @Test
    public void shouldSpinNormallyWhenLeftyHasNoLeftNeighbor(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("lefty", 1);
        machine.spin(1);
        assertTrue(machine.ok());
        assertEquals(4, machine.configuration().length);
    }

    /**
     * A locked lefty wheel should not copy its neighbor: the move fails and
     * nothing changes.
     */
    @Test
    public void shouldNotMoveALockedLeftyWheel(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("lefty", 2);
        machine.lock(2);
        String[] antes = machine.configuration();
        machine.spin(2);
        assertFalse(machine.ok());
        assertArrayEquals(antes, machine.configuration());
    }

    /**
     * When every wheel spins, the lefty wheel should end up showing the same
     * symbol as the wheel on its left.
     */
    @Test
    public void shouldCopyLeftNeighborWhenSpinningAllWheels(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("lefty", 2);
        machine.spin();
        assertTrue(machine.ok());
        String[] configuracion = machine.configuration();
        assertEquals(configuracion[0], configuracion[1]);
    }

    /**
     * After a swap the lefty wheel has a new neighbor, and it should copy that
     * new neighbor, not the old one.
     */
    @Test
    public void shouldCopyTheNewNeighborAfterASwap(){
        SlotMachine machine = new SlotMachine(3);
        machine.addWheel("lefty", 2);
        machine.swap(2, 3);
        machine.spin(3);
        assertTrue(machine.ok());
        String[] configuracion = machine.configuration();
        assertEquals(configuracion[1], configuracion[2]);
    }
}
