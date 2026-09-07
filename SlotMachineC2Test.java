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
}