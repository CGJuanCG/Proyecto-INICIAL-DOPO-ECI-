import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de aceptación para la sustentación del Ciclo 2.
 *
 * A diferencia de SlotMachineC2Test (pruebas de unidad, en modo invisible),
 * estas dos pruebas dejan la máquina visible a propósito, para que durante
 * la presentación se vea el movimiento paso a paso, el cambio de color al
 * ganar, y el mensaje de rueda bloqueada. Por eso hay que correrlas una por
 * una, en persona: la primera va a abrir un JOptionPane real que alguien
 * tiene que cerrar con clic para que la prueba continúe.
 *
 * @author  Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 10-09-2026
 */
public class SlotMachineC2TestSustentacion
{
    /**
     * Default constructor for test class SlotMachineC2TestSustentacion
     */
    public SlotMachineC2TestSustentacion()
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
     * Prueba de aceptación 1: bloquear una rueda la protege mientras las
     * otras dos giran, paso a paso y visiblemente, hasta lograr el jackpot;
     * luego desbloquearla y volver a girarla rompe el jackpot.
     *
    @Test
    public void pruebaAceptacion1_bloqueoProtegeMientrasSeLograElJackpot()
    {
        SlotMachine m = new SlotMachine();
        m.makeVisible();

        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        m.addSymbol(3, "green");
        m.addWheel(1);
        m.addWheel(2);
        m.addWheel(3);

        m.placeSymbol(1, "green");
        m.placeSymbol(2, "red");
        m.placeSymbol(3, "blue");
        assertFalse(m.isJackpot());

        m.lock(1);
        m.spin(1, 5);
        assertArrayEquals(new String[]{"green", "red", "blue"}, m.configuration());

        m.spin(2, 2);
        m.spin(3, 1);
        assertArrayEquals(new String[]{"green", "green", "green"}, m.configuration());
        assertTrue(m.isJackpot());

        m.unlock(1);
        m.spin(1, 1);
        assertFalse(m.isJackpot());
    }

    /**
     * Prueba de aceptación 2: spin(setSymbols) aplica todo o nada (un color
     * inválido no cambia absolutamente nada), y swap intercambia posiciones,
     * no símbolos.
     */
    @Test
    public void pruebaAceptacion2_configuracionAtomicaEIntercambio()
    {
        SlotMachine m = new SlotMachine();
        m.makeVisible();

        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        m.addSymbol(3, "green");
        m.addWheel(1);
        m.addWheel(2);
        m.addWheel(3);

        String[] before = m.configuration();
        m.spin(new String[]{"red", "blue", "purple"});
        boolean ok = m.ok();
        assertArrayEquals(before, m.configuration());
        assertFalse(ok);

        m.spin(new String[]{"red", "blue", "green"});
        assertArrayEquals(new String[]{"red", "blue", "green"}, m.configuration());
        assertFalse(m.isJackpot());

        m.swap(1, 3);
        assertArrayEquals(new String[]{"green", "blue", "red"}, m.configuration());
        assertEquals(3, m.distinctSymbols());
    }
}
