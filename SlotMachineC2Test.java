import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * The test class SlotMachineC2Test.
 *
 * @author  Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 6-09-2026
 */
public class SlotMachineC2Test
{
    private SlotMachine machine;

    /**
     * Default constructor for test class SlotMachineC2Test
     */
    public SlotMachineC2Test()
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
        machine = new SlotMachine();
        machine.makeInvisible();
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        machine = null;
    }

    /**
     * Should not change the configuration of a locked wheel when spin is
     * called on it.
     */
    @Test
    public void shouldNotChangeConfigurationWhenWheelIsLocked()
    {
        machine.lock(1);

        String[] before = machine.configuration();
        machine.spin(1, 1);
        String[] after = machine.configuration();

        assertArrayEquals(before, after);
    }

    /**
     * Should change the configuration of a wheel that was locked and then
     * unlocked when spin is called on it.
     */
    @Test
    public void shouldChangeConfigurationWhenWheelIsUnlockedAfterLock()
    {
        machine.lock(1);
        machine.unlock(1);

        String[] before = machine.configuration();
        machine.spin(1, 1);
        String[] after = machine.configuration();

        assertFalse(Arrays.equals(before, after));
    }

    /**
     * Should exchange the colors shown by two wheels when they swap places,
     * without touching any other wheel.
     */
    @Test
    public void shouldSwapWheelPositions()
    {
        machine.addWheel(2);
        machine.addWheel(3);
        machine.placeSymbol(2, "blue");
        machine.placeSymbol(3, "green");

        machine.swap(1, 3);

        assertArrayEquals(new String[]{"green", "blue", "red"}, machine.configuration());
    }

    /**
     * Should not change anything when asked to swap a wheel that does not
     * exist, and should report the move as failed.
     */
    @Test
    public void shouldNotSwapWithInvalidWheelIndex()
    {
        machine.addWheel(2);
        machine.placeSymbol(2, "blue");
        String[] before = machine.configuration();

        machine.swap(1, 5);
        boolean ok = machine.ok();

        assertArrayEquals(before, machine.configuration());
        assertFalse(ok);
    }

    /**
     * Should move a wheel exactly the number of steps given, landing on the
     * symbol at that place of the strip.
     */
    @Test
    public void shouldRotateWheelByGivenSteps()
    {
        machine.spin(1, 2);

        assertArrayEquals(new String[]{"green"}, machine.configuration());
    }

    /**
     * Should not move any wheel, and should report the move as failed, when
     * asked to rotate a wheel that does not exist.
     */
    @Test
    public void shouldNotRotateWheelWithInvalidIndex()
    {
        String[] before = machine.configuration();

        machine.spin(5, 2);
        boolean ok = machine.ok();

        assertArrayEquals(before, machine.configuration());
        assertFalse(ok);
    }

    /**
     * Should leave every wheel showing exactly the requested color when the
     * whole list of colors is valid and matches the number of wheels.
     */
    @Test
    public void shouldApplyValidSetSymbolsConfiguration()
    {
        machine.addWheel(2);
        machine.addWheel(3);

        machine.spin(new String[]{"blue", "green", "red"});

        assertArrayEquals(new String[]{"blue", "green", "red"}, machine.configuration());
    }

    /**
     * Should not change anything when one of the requested colors does not
     * exist on the strip, even if the rest of the colors were valid.
     */
    @Test
    public void shouldNotChangeConfigurationWhenSetSymbolsHasInvalidColor()
    {
        machine.addWheel(2);
        machine.placeSymbol(2, "blue");
        String[] before = machine.configuration();

        machine.spin(new String[]{"green", "purple"});
        boolean ok = machine.ok();

        assertArrayEquals(before, machine.configuration());
        assertFalse(ok);
    }

    /**
     * Should not throw an exception when asked to lock a wheel that does not
     * exist, and should report the move as failed.
     */
    @Test
    public void shouldNotThrowWhenLockingInvalidWheelIndex()
    {
        assertDoesNotThrow(() -> machine.lock(99));
        assertFalse(machine.ok());
    }

    /**
     * Should not throw an exception when asked to unlock a wheel that does
     * not exist, and should report the move as failed.
     */
    @Test
    public void shouldNotThrowWhenUnlockingInvalidWheelIndex()
    {
        assertDoesNotThrow(() -> machine.unlock(99));
        assertFalse(machine.ok());
    }

    // --- Ciclo 1: crear la máquina ---

    /**
     * Should start with no wheels and no symbols right after being created.
     */
    @Test
    public void shouldCreateEmptyMachineWithNoWheelsAndNoSymbols()
    {
        SlotMachine fresh = new SlotMachine();
        fresh.makeInvisible();

        assertEquals(0, fresh.configuration().length);
        assertEquals(0, fresh.symbols().length);
    }

    /**
     * Should not be a jackpot right after being created, since there are no
     * wheels yet to agree on a symbol.
     */
    @Test
    public void shouldNotBeJackpotWhenFreshlyCreated()
    {
        SlotMachine fresh = new SlotMachine();
        fresh.makeInvisible();

        assertFalse(fresh.isJackpot());
    }

    // --- Ciclo 1: adicionar o eliminar una rueda ---

    /**
     * Should add a new wheel at the requested position, growing the
     * configuration by one place.
     */
    @Test
    public void shouldAddWheelAtRequestedPosition()
    {
        machine.addWheel(1);

        assertEquals(2, machine.configuration().length);
    }

    /**
     * Should not throw or misbehave when asked to add a wheel at a position
     * far outside the valid range; it should just clamp to the nearest end.
     */
    @Test
    public void shouldClampWheelPositionWhenOutOfRange()
    {
        assertDoesNotThrow(() -> machine.addWheel(999));

        assertEquals(2, machine.configuration().length);
    }

    /**
     * Should remove the wheel sitting at the given position, shrinking the
     * configuration by one place.
     */
    @Test
    public void shouldRemoveWheelAtGivenPosition()
    {
        machine.addWheel(2);

        machine.delWheel(1);

        assertEquals(1, machine.configuration().length);
    }

    /**
     * Should not remove anything, and should report the move as failed,
     * when the machine has no wheels at all.
     */
    @Test
    public void shouldNotDeleteWheelWhenMachineHasNone()
    {
        SlotMachine fresh = new SlotMachine();
        fresh.makeInvisible();

        fresh.delWheel(1);

        assertFalse(fresh.ok());
    }

    // --- Ciclo 1: adicionar o eliminar un símbolo ---

    /**
     * Should add a new color to the strip, growing the list of symbols by
     * one.
     */
    @Test
    public void shouldAddSymbolToStrip()
    {
        machine.addSymbol(4, "yellow");

        assertEquals(4, machine.symbols().length);
    }

    /**
     * Should not add a second symbol of a color that is already on the
     * strip, and should report the move as failed.
     */
    @Test
    public void shouldNotAddDuplicateColorSymbol()
    {
        machine.addSymbol(1, "red");
        boolean ok = machine.ok();

        assertEquals(3, machine.symbols().length);
        assertFalse(ok);
    }

    /**
     * Should remove a color from the strip, shrinking the list of symbols
     * by one.
     */
    @Test
    public void shouldRemoveSymbolFromStrip()
    {
        machine.delSymbol("blue");

        assertEquals(2, machine.symbols().length);
    }

    /**
     * Should not remove anything, and should report the move as failed,
     * when asked to remove a color that is not on the strip.
     */
    @Test
    public void shouldNotRemoveSymbolThatDoesNotExist()
    {
        machine.delSymbol("purple");
        boolean ok = machine.ok();

        assertEquals(3, machine.symbols().length);
        assertFalse(ok);
    }

    // --- Ciclo 1: girar las ruedas ---

    /**
     * Should leave the wheel showing one of the colors that are actually on
     * the strip after spinning it.
     */
    @Test
    public void shouldLandOnAValidSymbolAfterSpin()
    {
        machine.spin(1);

        String[] config = machine.configuration();
        String[] validColors = machine.symbols();
        assertTrue(Arrays.asList(validColors).contains(config[0]));
    }

    /**
     * Should not spin anything, and should report the move as failed, when
     * the machine has no wheels.
     */
    @Test
    public void shouldNotSpinWhenMachineHasNoWheels()
    {
        SlotMachine fresh = new SlotMachine();
        fresh.makeInvisible();

        fresh.spin(1);

        assertFalse(fresh.ok());
    }

    // --- Ciclo 1: consultar si la configuración es la ganadora ---

    /**
     * Should be a jackpot when every wheel ends up showing the same color.
     */
    @Test
    public void shouldBeJackpotWhenAllWheelsShowSameColor()
    {
        machine.addWheel(2);
        machine.placeSymbol(2, "red");

        assertTrue(machine.isJackpot());
    }

    /**
     * Should not be a jackpot when the wheels show different colors.
     */
    @Test
    public void shouldNotBeJackpotWhenWheelsShowDifferentColors()
    {
        machine.addWheel(2);
        machine.placeSymbol(2, "blue");

        assertFalse(machine.isJackpot());
    }

    // --- Ciclo 1: hacer visible o invisible el simulador ---

    /**
     * Should not attempt to show a dialog when an action fails while the
     * machine is invisible — it should just report the failure quietly.
     */
    @Test
    public void shouldNotThrowWhenFailingInInvisibleMode()
    {
        assertDoesNotThrow(() -> machine.addSymbol(1, "red"));
        assertFalse(machine.ok());
    }

    /**
     * Should not let an invalid action succeed just because no dialog is
     * shown — invisible mode silences the message, not the validation.
     */
    @Test
    public void shouldNotSilentlySucceedWhenInvisibleActionIsInvalid()
    {
        String[] before = machine.symbols();

        machine.addSymbol(1, "red");
        boolean ok = machine.ok();

        assertArrayEquals(before, machine.symbols());
        assertFalse(ok);
    }
}