import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.time.Duration;

/**
 * The test class SlotMachineContestCTest.
 *
 * Creacion colectiva del curso. Las pruebas marcadas con @Disabled asumen
 * detalles de implementacion que el UML y el enunciado no garantizan para
 * todos los grupos. Se conservan con la razon escrita en lugar de borrarlas.
 * Reportado en el wiki el 21/09/2026.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineContestCTest
{
    /**
     * Default constructor for test class SlotMachineContestCTest
     */
    public SlotMachineContestCTest()
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

    // ------------------------------------------------------------------
    // Grupo: GomezB-CarreroC
    // ------------------------------------------------------------------

    /**
     * Verifies that solve returns
     * the expected number of movements.
     */
    @Disabled("Asume que solve(n) devuelve exactamente n acciones. El algoritmo "
            + "necesita fases de exploracion antes de alinear: n acciones no alcanzan.")
    @Test
    public void accordingCcGbShouldtestSolveNumberOfMovements()
    {
        SlotMachineContest contest = new SlotMachineContest();

        int[][] solution = contest.solve(5);

        assertEquals(5, solution.length);
    }

    /**
     * Verifies that all wheels share the same
     * symbol order according to the contest problem.
     */
    @Disabled("SlotMachine(n) baraja los colores y gira las ruedas al azar: no nace "
            + "ganada ni garantiza que 'red' este entre los n colores elegidos.")
    @Test
    public void accordingCcGbShouldtestAllWheelsHaveSameSymbolOrder()
    {
        SlotMachine machine = new SlotMachine(3);

        String[] configuration = machine.configuration();

        assertEquals("red", configuration[0]);
        assertEquals("red", configuration[1]);
        assertEquals("red", configuration[2]);
    }

    // ------------------------------------------------------------------
    // Grupo: CortazarJ-MartinezC
    // ------------------------------------------------------------------

    /**
     * Verifica que SlotMachine(n) crea exactamente n ruedas, cada una
     * con n simbolos montados
     */
    @Disabled("Espera n*n en symbols(); en esta implementacion symbols() devuelve "
            + "un valor por rueda, no la cinta completa de cada una.")
    @Test
    public void accordingCjMcShouldCreateEqualWheelsAndSymbolsPerWheel() {
        SlotMachine sm = new SlotMachine(5);
        assertEquals(5, sm.configuration().length);
        assertEquals(25, sm.symbols().length);
    }

    /**
     * Verifica que solve(n) nunca propone mas acciones que ruedas existen
     * en la maquina, y que cada accion referencia una rueda dentro de un
     * rango valido (1 a n).
     */
    @Disabled("Asume como mucho n acciones. Igual que arriba: la exploracion gasta "
            + "muchas mas. La parte del rango de rueda si es valida.")
    @Test
    public void accordingCjMcShouldProposeAtMostOneActionPerValidWheel() {
        int n = 4;
        int[][] actions = SlotMachineContest.solve(n);

        assertTrue(actions.length <= n);
        for (int[] action : actions) {
            int wheel = action[0];
            assertTrue(wheel >= 1 && wheel <= n);
        }
    }

    // ------------------------------------------------------------------
    // Grupo 3: CarrenoG-CastanoP
    // ------------------------------------------------------------------

    /**
     * solve nunca debe proponer una accion sobre una rueda que no existe.
     */
    @Test
    public void accordingCgCpShouldOnlyUseExistingWheels() {
        int n = 8;
        for (int[] action : SlotMachineContest.solve(n)) {
            assertTrue(action[0] >= 1 && action[0] <= n);
        }
    }

    /**
     * Una accion que gira 0 pasos no mueve nada: no deberia ocupar un turno.
     */
    @Test
    public void accordingCgCpShouldNotIncludeZeroStepActions() {
        for (int[] action : SlotMachineContest.solve(8)) {
            assertNotEquals(0, action[1]);
        }
    }

    /**
     * El problema de la maraton permite maximo 10000 acciones, incluso con
     * la maquina mas grande que admite el enunciado.
     */
    @Test
    public void accordingCgCpShouldNotExceedTheActionLimit() {
        assertTrue(SlotMachineContest.solve(50).length <= 10000);
    }

    // ------------------------------------------------------------------
    // Grupo 6: CanonA-PaezP
    // ------------------------------------------------------------------

    /**
     * Test #1: cada accion devuelta es una pareja de dos valores.
     */
    @Test
    public void accordingCaPpshouldReturnValidMovesStructure() {
        SlotMachineContest contest = new SlotMachineContest();
        int n = 3;
        int[][] moves = contest.solve(n);

        assertNotNull(moves);
        for (int[] move : moves) {
            assertEquals(2, move.length);
        }
    }

    /**
     * Test #2: simulate corre sin lanzar errores.
     * Nota: simulate hace visible la maquina y anima paso a paso, asi que esta
     * prueba abre ventana y tarda. El enunciado pide pruebas en modo invisible.
     */
    @Test
    public void accordingCaPpshouldRunSimulationWithoutErrors() {
        int n = 3;
        SlotMachineContest contest = new SlotMachineContest();
        contest.simulate(n);
    }

    // ------------------------------------------------------------------
    // Grupo 5: BustosL-GomezG
    // ------------------------------------------------------------------

    /**
     * Verifica que ninguna accion de la solucion tenga
     * un numero de pasos negativo.
     */
    @Disabled("El enunciado del problema permite pasos negativos (j entre -10^9 y "
            + "10^9). Esta implementacion usa -1 para retroceder una casilla.")
    @Test
    public void accordingSharedShouldNotHaveNegativeSteps()
    {
        int n = 5;

        int[][] solution = SlotMachineContest.solve(n);

        for (int[] move : solution)
        {
            assertTrue(move[1] >= 0);
        }
    }

    /**
     * Verifica que la solucion para una maquina de una sola
     * rueda tenga exactamente una accion.
     */
    @Disabled("n=1 esta fuera del rango 3..50 del enunciado. Ademas contradice a "
            + "accordingCcIcsolveShouldReturnAnEmptyPlanForOneWheel, que espera 0 acciones.")
    @Test
    public void accordingSharedShouldSolveOneWheelMachine()
    {
        int n = 1;

        int[][] solution = SlotMachineContest.solve(n);

        assertNotNull(solution);
        assertEquals(1, solution.length);
        assertEquals(1, solution[0][0]);
    }

    // ------------------------------------------------------------------
    // Grupo: DiazR-RojasM
    // ------------------------------------------------------------------

    /**
     * Verifica que si la rueda no esta resuelta al inicio debe tener
     * mas de 0 movimientos
     */
    @Test
    public void accordingDrRmShouldReturnNonEmptyMovesWhenNotInJackpot() {
        int n = 4;
        SlotMachine sM = new SlotMachine(n);
        int[][] moves = SlotMachineContest.solve(n);
        if (!sM.isJackpot()) {
            assertTrue(moves.length > 0);
        }
    }

    /**
     * Verifica que no se creen ruedas ni simbolos con un n negativo.
     */
    @Test
    public void accordingDrRmShouldNotCreateWheelAndSymbolWithNegativeNumber(){
        int n = -3;
        SlotMachine sM = new SlotMachine(n);
        int sizeW = sM.configuration().length;
        String[] s = sM.symbols();
        int sizeS = s.length;
        assertEquals(0, sizeW);
        assertEquals(0, sizeS);
    }

    // ------------------------------------------------------------------
    // Grupo: ForeroM-SalinasN-02
    // La variable 'slot' no estaba declarada; se reemplazo por la llamada
    // estatica para que el archivo compile.
    // ------------------------------------------------------------------

    /**
     * Tests that solve keeps returning the right amount of rows for several
     * different machine sizes, not just one lucky case.
     */
    @Disabled("Asume que solve(n) devuelve exactamente n acciones, igual que "
            + "accordingCcGbShouldtestSolveNumberOfMovements.")
    @Test
    public void shouldWorkforEveryWhee() throws InterruptedException {
        int[] sizes = {6, 3, 4};

        for (int n : sizes) {
            int[][] result = SlotMachineContest.solve(n);
            assertEquals(n, result.length);
        }
    }

    // ------------------------------------------------------------------
    // Grupo 6: Cuervo-Infante
    // ------------------------------------------------------------------

    /**
     * solve(1) devuelve un plan vacio.
     */
    @Disabled("n=1 esta fuera del rango 3..50 del enunciado. Desde que solve valida "
            + "el rango, lanza IllegalArgumentException en lugar de devolver vacio.")
    @Test
    public void accordingCcIcsolveShouldReturnAnEmptyPlanForOneWheel() {
        SlotMachineContest contest = new SlotMachineContest();

        int[][] result = contest.solve(1);

        assertNotNull(result);
        assertEquals(0, result.length);
    }

    /**
     * Cada accion devuelta tiene rueda y pasos validos.
     */
    @Disabled("assertEquals(1, action[1]) exige que todos los giros sean de un paso; "
            + "el enunciado permite cualquier tamano de giro.")
    @Test
    public void accordingCcIcsolveShouldReturnActionsWithValidWheelAndStepValues() {
        SlotMachineContest contest = new SlotMachineContest();

        int[][] result = assertTimeoutPreemptively(
                Duration.ofSeconds(5),
                () -> contest.solve(3));

        assertNotNull(result);
        for (int[] action : result) {
            assertNotNull(action);
            assertEquals(2, action.length);
            assertTrue(action[0] >= 1 && action[0] <= 3);
            assertEquals(1, action[1]);
        }
    }

    // ------------------------------------------------------------------
    // Grupo 2: DavilaO-OrozcoL
    // Los mensajes venian con la firma de JUnit 4 (mensaje primero); se
    // movieron al final, que es el orden de JUnit 5.
    // ------------------------------------------------------------------

    /**
     * solve no deberia correr con menos de tres ruedas.
     */
    @Disabled("Espera lista vacia para n=2. El curso no ha acordado que hace solve "
            + "con n fuera del rango 3..50; hay tres respuestas distintas en el wiki.")
    @Test
    public void accordingDoOlshouldNotRunAndReturnEmptyWhenLessThanThreeWheels() {
        SlotMachineContest contest = new SlotMachineContest();

        int[][] actions = contest.solve(2);

        assertNotNull(actions, "El resultado no debe ser nulo");
        assertEquals(0, actions.length,
            "No debio correr por los requisitos minimos, por ende las acciones deben ser 0");
    }

    /**
     * solve escala hasta la maquina mas grande sin pasarse del limite.
     * La variable 'contest' no estaba declarada; se agrego.
     */
    @Test
    public void accordingDoOlsolverShouldScaleEfficientlyWithMaximumWheels() {
        SlotMachineContest contest = new SlotMachineContest();

        int[][] actions = contest.solve(50);

        assertTrue(actions.length <= 10000, "El escalado asintotico fallo para N=50");
    }
}
