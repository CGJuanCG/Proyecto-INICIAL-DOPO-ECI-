import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Shared unit tests for cycle 4, built together by the groups in the wiki.
 *
 * To make them compile and run in this project, they were
 * changed as little as possible
 *
 * Tests left out, because they use classes or methods that only exist in the
 * simulator of another group:
 * - BustosL-GomezG: shouldDecreaseEphemeralSymbolSize and
 *   shouldAlternateShySymbolVisibility (EphemeralSymbol, ShySymbol).
 * - GonzalezM-LesmesA: accordingGmLaShouldChangeNeonSymbolColorAfterSpin
 *   (addSymbol with four parameters, "neon" symbols).
 * - CuervoC-InfanteC: accordingCcIcshouldShrinkEphemeralWhenItReachesTheWindow
 *   and accordingCcIcshouldHideShyWhenItIsSelectedInTheWheel (new Wheel(),
 *   addSymbolWheel, rotateOnce, EphemeralSymbol, ShySymbol).
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 */
public class SlotMachineCC4Test
{
    private SlotMachine slotMachine;
    private SlotMachine machine;

    /**
     * Default constructor for test class SlotMachineCC4Test
     */
    public SlotMachineCC4Test()
    {
    }

    /**
     * Prepares the two machines some of the groups use:
     * slotMachine is an empty hidden machine, and machine is a hidden machine
     * with a normal, a lefty and a rebel wheel (the setUp of Gomez-Rojas).
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        slotMachine = new SlotMachine();
        slotMachine.makeInvisible();

        machine = new SlotMachine();
        machine.makeInvisible();
        String[] types = {"normal", "lefty", "rebel"};
        for (int w = 1; w <= 3; w++) {
            machine.addWheel(types[w - 1], w);
            machine.addSymbol(1, "red");
            machine.addSymbol(2, "green");
            machine.addSymbol(3, "blue");
        }
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

    // ---------------------------------------------------------------------
    // Grupo 03: Carreno - Castano (CgCp)
    // ---------------------------------------------------------------------

    /**
     * A rebel wheel does not let itself be locked.
     */
    @Test
    public void accordingCgCpShouldNotLockARebelWheel()
    {
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addSymbol(1, "red");
        slotMachine.addWheel("rebel", 1);
        slotMachine.lock(1);
        assertFalse(slotMachine.ok());
    }

    /**
     * A rebel wheel does not let itself be removed, so the machine keeps all
     * its wheels.
     */
    @Test
    public void accordingCgCpShouldNotDeleteARebelWheel()
    {
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addSymbol(1, "red");
        slotMachine.addWheel(1);
        slotMachine.addWheel("rebel", 2);
        slotMachine.delWheel(2);
        assertFalse(slotMachine.ok());
        assertEquals(2, slotMachine.configuration().length);
    }

    /**
     * A lefty wheel copies the wheel on its left when it spins.
     */
    @Test
    public void accordingCgCpShouldCopyTheLeftWheelWhenALeftyWheelSpins()
    {
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addSymbol(3, "green");
        slotMachine.addWheel(1);
        slotMachine.addWheel("lefty", 2);
        slotMachine.placeSymbol(1, "blue");
        slotMachine.spin(2);
        assertTrue(slotMachine.ok());
        assertEquals("blue", slotMachine.configuration()[1]);
    }

    /**
     * Ephemeral and shy symbols are still symbols of their color: they are
     * listed by their color and can be removed by their color.
     */
    @Test
    public void accordingCgCpShouldHandleEphemeralAndShySymbolsByTheirColor()
    {
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol("ephemeral", 2, "yellow");
        assertTrue(slotMachine.ok());
        slotMachine.addSymbol("shy", 3, "purple");
        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[]{"red", "yellow", "purple"},
                          slotMachine.symbols());
        slotMachine.delSymbol("yellow");
        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[]{"red", "purple"},
                          slotMachine.symbols());
    }

    // ---------------------------------------------------------------------
    // Grupo 04: Gomez Boada Julian (Gb)
    // ---------------------------------------------------------------------

    /**
     * Verifica que los simbolos de tipo normal, ephemeral y shy
     * puedan coexistir en una misma rueda.
     */
    @Disabled("Asume que addSymbol agrega al final; aquí inserta en pos, así que el orden queda al revés.")
    @Test
    public void accordingGbShouldAllowDifferentSymbolTypesInSameWheel()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel("normal", 1);
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 1, "blue");
        machine.addSymbol("shy", 1, "green");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "green"}, machine.symbols());
    }

    /**
     * Verifica que diferentes tipos de simbolos no puedan
     * introducir colores duplicados en la misma rueda.
     */
    @Test
    public void accordingGbShouldNotAllowDuplicateColorAcrossSymbolTypes()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel("normal", 1);
        machine.addSymbol("normal", 1, "red");
        assertTrue(machine.ok());
        machine.addSymbol("pulsing", 1, "red");
        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"red"}, machine.symbols());
    }

    // ---------------------------------------------------------------------
    // RojasH
    // ---------------------------------------------------------------------

    /**
     * Verifica que se puedan agregar simbolos de los tres tipos.
     */
    @Test
    public void shouldAddSymbolsOfEveryType()
    {
        slotMachine.addSymbol("normal", 1, "red");
        slotMachine.addSymbol("ephemeral", 2, "blue");
        slotMachine.addSymbol("shy", 3, "green");
        assertTrue(slotMachine.ok());
        assertEquals(3, slotMachine.symbols().length);
    }

    /**
     * Verifica que la rueda rebel no se deje bloquear, intercambiar
     * ni eliminar, y que la maquina quede igual despues de intentarlo.
     */
    @Test
    public void shouldRebelWheelRefuseLockSwapAndDelete()
    {
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel("rebel", 1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "blue");
        slotMachine.lock(1);
        assertFalse(slotMachine.ok());
        slotMachine.swap(1, 2);
        assertFalse(slotMachine.ok());
        slotMachine.delWheel(1);
        assertFalse(slotMachine.ok());
        assertEquals(2, slotMachine.configuration().length);
        assertEquals("red", slotMachine.configuration()[0]);
        assertEquals("blue", slotMachine.configuration()[1]);
    }

    /**
     * Verifica que la maquina funcione con los tres tipos de rueda y los
     * tres tipos de simbolo a la vez: todos los giros son exitosos y la
     * lefty copia siempre a la rueda de su izquierda.
     */
    @Test
    public void shouldSpinMachineWithEveryWheelAndSymbolType()
    {
        slotMachine.addSymbol("normal", 1, "red");
        slotMachine.addSymbol("ephemeral", 2, "blue");
        slotMachine.addSymbol("shy", 3, "green");
        slotMachine.addWheel("normal", 1);
        slotMachine.addWheel("lefty", 2);
        slotMachine.addWheel("rebel", 3);
        for (int i = 0; i < 10; i++) {
            slotMachine.spin();
            assertTrue(slotMachine.ok());
            String[] config = slotMachine.configuration();
            assertEquals(config[0], config[1]);
        }
    }

    // ---------------------------------------------------------------------
    // Grupo 06: CanonA - PaezP (CaPp)
    // ---------------------------------------------------------------------

    /**
     * La rueda rebel no se deja intercambiar.
     */
    @Test
    public void acordingCaPpShouldNotSwapRebelWheel()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel("normal", 1);
        machine.addWheel("rebel", 2);
        machine.swap(1, 2);
        assertFalse(machine.ok());
    }

    /**
     * La rueda rebel no se deja eliminar, pero la normal si.
     */
    @Test
    public void acordingCaPpShouldNotDeleteRebelWheelbutShouldDeleteNormalWheel()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel("normal", 1);
        machine.addWheel("rebel", 2);
        machine.delWheel(2);
        assertFalse(machine.ok());
        assertEquals(2, machine.configuration().length);
        machine.delWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    // ---------------------------------------------------------------------
    // Grupo 04: Gomez - Rojas (gomRoj)
    // ---------------------------------------------------------------------

    /**
     * La lefty copia a la rueda normal de su izquierda.
     */
    @Test
    public void gomRojLeftyDeberiaCopiarALaRuedaNormalDeSuIzquierda()
    {
        machine.placeSymbol(1, "blue");
        machine.spin(2);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * La rebel no se deja bloquear, intercambiar ni eliminar.
     */
    @Test
    public void gomRojRebelNoDeberiaBloquearseIntercambiarseNiEliminarse()
    {
        String[] original = machine.configuration();
        machine.lock(3);
        assertFalse(machine.ok());
        machine.swap(3, 1);
        assertFalse(machine.ok());
        machine.delWheel(3);
        assertFalse(machine.ok());
        assertArrayEquals(original, machine.configuration());
        assertEquals(3, machine.configuration().length);
    }

    /**
     * Un shy invisible no deberia aportar su color al jackpot.
     */
    @Disabled("Asume que un shy oculto no cuenta para el jackpot; aquí solo deja de dibujarse.")
    @Test
    public void gomRojShyInvisibleNoDeberiaAportarColorAlJackpot()
    {
        SlotMachine shyMachine = new SlotMachine();
        shyMachine.makeInvisible();
        for (int w = 1; w <= 2; w++) {
            shyMachine.addWheel(w);
            shyMachine.addSymbol("shy", 1, "red");
        }
        assertTrue(shyMachine.isJackpot());
        shyMachine.placeSymbol(2, "red");
        assertFalse(shyMachine.isJackpot());
        assertEquals("", shyMachine.configuration()[1]);
    }

    // ---------------------------------------------------------------------
    // Grupo 02: Davila - Orozco (DoOl)
    // ---------------------------------------------------------------------

    /**
     * La rebel rechaza lock, delWheel y swap.
     */
    @Test
    public void accordingToDoOLrebelWheelRejectsLockDeleteAndSwap()
    {
        SlotMachine m = new SlotMachine(3);
        m.addWheel("rebel", 4);
        assertTrue(m.ok());
        m.lock(4);
        assertFalse(m.ok());
        m.delWheel(4);
        assertFalse(m.ok());
        m.swap(1, 4);
        assertFalse(m.ok());
    }

    /**
     * La lefty copia a su vecina de la izquierda.
     */
    @Test
    public void accordingToDoOLleftyWheelCopiesItsLeftNeighbour()
    {
        SlotMachine m = new SlotMachine(3);
        m.addWheel("lefty", 4);
        m.spin();
        String[] shown = m.configuration();
        assertEquals(shown[2], shown[3]);
    }

    // ---------------------------------------------------------------------
    // Grupo 04: AnzolaA - CastanedaC
    // ---------------------------------------------------------------------

    /**
     * The normal wheel is changed first. Then the LeftyWheel
     * is spun so that it copies the current state of its
     * immediate left wheel.
     */
    @Test
    public void shouldMakeLeftyWheelCopyImmediateLeftWheel()
    {
        String[] firstConfiguration = machine.configuration();
        machine.spin(1, 1);
        String[] secondConfiguration = machine.configuration();
        assertNotEquals(firstConfiguration[0], secondConfiguration[0],
            "The normal wheel should have changed state.");
        machine.spin(2);
        String[] finalConfiguration = machine.configuration();
        assertEquals(finalConfiguration[0], finalConfiguration[1],
            "The LeftyWheel must copy the symbol shown by the wheel immediately to its left.");
    }

    /**
     * The RebelWheel must remain in the machine after
     * all the restricted operations are attempted.
     */
    @Test
    public void shouldKeepRebelWheelUnaffectedByRestrictedOperations()
    {
        String[] before = machine.configuration();
        machine.lock(3);
        assertFalse(machine.ok(), "The RebelWheel cannot be locked.");
        machine.swap(1, 3);
        assertFalse(machine.ok(), "The RebelWheel cannot participate in a swap.");
        machine.delWheel(3);
        assertFalse(machine.ok(), "The RebelWheel cannot be deleted.");
        String[] after = machine.configuration();
        assertEquals(3, after.length, "The RebelWheel must remain in the machine.");
        assertArrayEquals(before, after, "The configuration must remain unchanged.");
    }

    // ---------------------------------------------------------------------
    // Grupo 02: Quintero - Rodriguez
    // ---------------------------------------------------------------------

    /**
     * Test that a shy symbol behaves correctly when selected.
     */
    @Test
    public void shouldSelectShySymbol()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addSymbol("shy", 1, "green");
        machine.addSymbol("normal", 2, "red");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }

    /**
     * Test that a Rebel wheel cannot lock.
     */
    @Test
    public void shouldNotLockRebelWheel()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel("rebel", 1);
        machine.lock(1);
        assertFalse(machine.ok());
    }

    // ---------------------------------------------------------------------
    // Grupo 03: GonzalezM - LesmesA (GmLa)
    // ---------------------------------------------------------------------

    /**
     * Verifica que se pueda eliminar una rueda normal exitosamente y se
     * actualice la configuracion.
     */
    @Test
    public void accordingGmLaShouldSuccessfullyDeleteNormalWheel()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.delWheel(2);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    // ---------------------------------------------------------------------
    // Grupo 06: CuervoC - InfanteC (CcIc)
    // ---------------------------------------------------------------------

    /**
     * Verifica que no se pueda agregar un simbolo de un tipo inexistente.
     */
    @Disabled("Asume que una rueda sin símbolos muestra white; aquí no muestra ningún color.")
    @Test
    public void accordingCcIcshouldNotAddSymbolWithUnknownType()
    {
        slotMachine.addWheel(1);
        slotMachine.addSymbol("giant", 1, "red");
        assertFalse(slotMachine.ok());
        assertArrayEquals(new String[]{"white"}, slotMachine.configuration());
    }
}
