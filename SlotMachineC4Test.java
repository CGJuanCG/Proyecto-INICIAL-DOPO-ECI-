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

    /**
     * Makes an empty machine that stays off the screen, so the tests can build
     * exactly the strip they need.
     */
    private SlotMachine maquinaVacia(){
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        return machine;
    }

    /**
     * The machine should accept one symbol of every kind, in the places asked
     * for.
     */
    @Test
    public void shouldAddSymbolsOfEveryKind(){
        SlotMachine machine = maquinaVacia();
        machine.addSymbol("normal", 1, "red");
        assertTrue(machine.ok());
        machine.addSymbol("ephemeral", 2, "blue");
        assertTrue(machine.ok());
        machine.addSymbol("shy", 3, "green");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "green"}, machine.symbols());
    }

    /**
     * An unknown kind of symbol should not be added.
     */
    @Test
    public void shouldNotAddASymbolOfUnknownKind(){
        SlotMachine machine = maquinaVacia();
        machine.addSymbol("xyz", 1, "red");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Two symbols of the same color are the same symbol, even if they are of
     * different kinds, so the second one should not be added.
     */
    @Test
    public void shouldNotAddTwoSymbolsOfTheSameColorEvenOfDifferentKinds(){
        SlotMachine machine = maquinaVacia();
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 2, "red");
        assertFalse(machine.ok());
        assertEquals(1, machine.symbols().length);
    }

    /**
     * A symbol that is not normal should still be found by its color.
     */
    @Test
    public void shouldDeleteAnEphemeralSymbolByItsColor(){
        SlotMachine machine = maquinaVacia();
        machine.addSymbol("ephemeral", 1, "red");
        machine.delSymbol("red");
        assertTrue(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * An ephemeral symbol should get smaller on every spin until it is a dot,
     * and then stay a dot.
     */
    @Test
    public void shouldShrinkAnEphemeralSymbolUntilItIsADot(){
        Ephemeral symbol = new Ephemeral("red");
        assertEquals(100, symbol.getSize());
        symbol.notifySpin();
        assertEquals(80, symbol.getSize());
        for (int i = 0; i < 10; i++) {
            symbol.notifySpin();
        }
        assertEquals(0, symbol.getSize());
    }

    /**
     * When the machine spins, the strip should tell its ephemeral symbols, so
     * they shrink.
     */
    @Test
    public void shouldShrinkEphemeralSymbolsWhenTheStripHearsOfASpin(){
        Cinta cinta = new Cinta();
        Ephemeral symbol = new Ephemeral("red");
        cinta.addSymbol(0, symbol);
        cinta.notifySpin();
        assertEquals(80, symbol.getSize());
    }

    /**
     * A shy symbol should switch between showing and hiding every time it is
     * selected.
     */
    @Test
    public void shouldSwitchAShySymbolEveryTimeItIsSelected(){
        Shy symbol = new Shy("red");
        assertTrue(symbol.isShown());
        symbol.select();
        assertFalse(symbol.isShown());
        symbol.select();
        assertTrue(symbol.isShown());
    }

    /**
     * When a wheel stops on a shy symbol, the symbol should hear about it.
     */
    @Test
    public void shouldHideAShySymbolWhenAWheelStopsOnIt(){
        Cinta cinta = new Cinta();
        Shy symbol = new Shy("red");
        cinta.addSymbol(0, symbol);
        Wheel wheel = new Wheel(cinta);
        wheel.placeAt(0);
        assertFalse(symbol.isShown());
    }

    /**
     * Hiding only changes how a shy symbol looks: for the game it is still the
     * symbol on view.
     */
    @Test
    public void shouldStillShowTheColorOfAHiddenShySymbol(){
        SlotMachine machine = maquinaVacia();
        machine.addSymbol("shy", 1, "red");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Makes an empty machine with three normal symbols, "red", "blue" and
     * "green", in that order, and one wheel of the given kind stopped on
     * "red".
     */
    private SlotMachine maquinaConUnaRueda(String type){
        SlotMachine machine = maquinaVacia();
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addWheel(type, 1);
        machine.placeSymbol(1, "red");
        return machine;
    }

    /**
     * A lazy wheel should move forward just one place every time it spins.
     */
    @Test
    public void shouldMoveALazyWheelJustOnePlace(){
        SlotMachine machine = maquinaConUnaRueda("lazy");
        machine.spin(1);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
        machine.spin(1);
        assertEquals("green", machine.configuration()[0]);
    }

    /**
     * A lazy wheel should also move just one place when the whole machine
     * spins.
     */
    @Test
    public void shouldMoveALazyWheelJustOnePlaceWhenAllWheelsSpin(){
        SlotMachine machine = maquinaConUnaRueda("lazy");
        machine.spin();
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * A locked lazy wheel should not move.
     */
    @Test
    public void shouldNotMoveALockedLazyWheel(){
        SlotMachine machine = maquinaConUnaRueda("lazy");
        machine.lock(1);
        machine.spin(1);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }
}
