import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de aceptación para la sustentación del Ciclo 4.
 *
 * Igual que en el ciclo 2, la máquina queda visible a propósito, para que en
 * la presentación se vean los tipos de ruedas (cada uno con su color de
 * marco) y de símbolos (cada uno con su forma). Hay que correrlas una por una
 * y en persona: la primera abre dos JOptionPane reales (rebel no se deja
 * bloquear ni eliminar) que alguien tiene que cerrar con clic para que la
 * prueba continúe.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez

 */
@Tag("aceptacion")
public class SlotMachineC4AcceptanceTest
{
    private static final int PAUSA = 1000;

    /**
     * Default constructor for test class SlotMachineC4AcceptanceTest
     */
    public SlotMachineC4AcceptanceTest()
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
     * Deja quieta la pantalla un momento, para que el público alcance a ver
     * cada paso.
     */
    private void pausa()
    {
        try {
            Thread.sleep(PAUSA);
        }
        catch (InterruptedException e) {
            return;
        }
    }

    /**
     * Prueba de aceptación 1: los cuatro tipos de rueda en una misma máquina.
     * La rebel no se deja bloquear ni eliminar, la lefty copia a su vecina de
     * la izquierda (la rebel) y la lazy avanza una sola posición.
     */
    @Test
    public void pruebaAceptacion1TiposDeRueda()
    {
        SlotMachine m = new SlotMachine();
        m.makeVisible();

        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        m.addSymbol(3, "green");
        m.addWheel("normal", 1);
        m.addWheel("rebel", 2);
        m.addWheel("lefty", 3);
        m.addWheel("lazy", 4);
        m.spin(new String[]{"red", "blue", "green", "red"});
        pausa();

        m.lock(2);
        assertFalse(m.ok());
        m.delWheel(2);
        assertFalse(m.ok());
        assertEquals(4, m.configuration().length);
        pausa();

        m.spin(3);
        assertEquals("blue", m.configuration()[2]);
        pausa();

        m.spin(4);
        assertEquals("blue", m.configuration()[3]);
        pausa();
    }

    /**
     * Prueba de aceptación 2: los tres tipos de símbolo. El ephemeral se
     * encoge con cada giro, el shy se esconde y reaparece cada vez que una
     * rueda se detiene en él, y aun así ambos cuentan por su color: con las
     * tres ruedas en el ephemeral, ya pequeño, la máquina gana.
     */
    @Test
    public void pruebaAceptacion2TiposDeSimbolo()
    {
        SlotMachine m = new SlotMachine();
        m.makeVisible();

        m.addSymbol("normal", 1, "red");
        m.addSymbol("ephemeral", 2, "blue");
        m.addSymbol("shy", 3, "green");
        m.addWheel(1);
        m.addWheel(2);
        m.addWheel(3);
        m.spin(new String[]{"red", "blue", "green"});
        pausa();

        m.placeSymbol(3, "green");
        assertEquals("green", m.configuration()[2]);
        pausa();

        m.spin(1, 3);
        m.spin(1, 3);
        m.spin(1, 3);
        assertEquals("red", m.configuration()[0]);
        assertEquals("blue", m.configuration()[1]);
        pausa();

        m.spin(new String[]{"blue", "blue", "blue"});
        assertTrue(m.isJackpot());
        pausa();
    }
}
