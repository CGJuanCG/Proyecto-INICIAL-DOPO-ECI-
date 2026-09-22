import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineContestTest.
 *
 * @author  Juan Carreño - Juan Castaño
 * @version (a version number or a date)
 */
public class SlotMachineContestTest
{
    /**
     * Default constructor for test class SlotMachineContestTest
     */
    public SlotMachineContestTest()
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
     * Should solve the slotmachine ending in jackpot in the worst case
     */
    @Test
    public void shouldBeWinning(){
        SlotMachine machine = new SlotMachine(50);
        SlotMachineContest.solucion(machine, 50);
        assertTrue(machine.isJackpot());
    }
     
    /**
     * The amount of movements must not pass over 10000
     */
    @Test
    public void shouldNotDoMoreThan10000Moves(){
        SlotMachine machine = new SlotMachine(50);
        int[][] moves = SlotMachineContest.solucion(machine, 50);
        assertTrue(moves.length<=10000);
        
    }
    
    /**
     * The amount of movements must pass in the minimun range too
     */
    
    @Test
    public void shouldPassWith3Wheels(){
        SlotMachine machine = new SlotMachine(3);
        SlotMachineContest.solucion(machine, 3);
        assertTrue(machine.isJackpot());
    }
    
    /**
     * The solution doesn't returns an empty list of moves
     */
    @Test
    public void shouldNotReturnAnEmptyList(){
        SlotMachine machine = new SlotMachine(15);
        int[][] moves = SlotMachineContest.solucion(machine, 15);
        assertTrue(moves.length>0);
    }
    
    /**
     * Every move must be different from 0, there is no move in which
     * you do nothing
     */
    @Test
    public void movesShouldNotBe0InAnyMove(){
        SlotMachine machine = new SlotMachine(35);
        int[][] moves = SlotMachineContest.solucion(machine, 35);
        for (int[] move: moves){
            assertNotEquals(0,moves[1]);
        }
    }
    
    
    /**
     * Each move given by the solution must be a legal move
     */
    @Test
    public void everyReturnedActionShouldBeAValidMove(){
        int n = 25;
        SlotMachine machine = new SlotMachine(n);
        int[][] moves = SlotMachineContest.solucion(machine, n);
        SlotMachine machine_test = new SlotMachine(n);
        for (int i = 0; i<moves.length;i++){
            int rueda = moves[i][0];
            int pasos = moves[i][1];
            machine_test.spin(rueda, pasos);
            
            assertTrue(machine_test.ok(), "La accion " +i+"{"+rueda+", "+ pasos+ "} fue rechazada"); 
        }
    }
    
    /**
     * Check manually if the given actions win the slotmachine
     */
    @Test
    public void returnedActionsShouldWinFromTheInitialConfiguration() {
        int n = 16;
        SlotMachine machine = new SlotMachine(n);
        String[] inicial = machine.configuration();          // estado antes de resolver
        int[][] moves = SlotMachineContest.solucion(machine, n);
        machine.spin(inicial);                               // rebobinar
        assertTrue(machine.ok(), "No se pudo restaurar la configuración inicial");
        
        //realiza las acciones entregadas
        for (int i = 0; i < moves.length; i++) { 
            machine.spin(moves[i][0], moves[i][1]);
            assertTrue(machine.ok());
        }
        assertTrue(machine.isJackpot(),
            "La secuencia devuelta no llega al jackpot desde la configuración inicial");
    }
    
    /**
     * Should not run because the number of wheels is lower than 3
     */
    @Test
    public void shouldRejectNBelowTheMinimum() {
        try {
            SlotMachineContest.solve(2);
            fail("Se esperaba IllegalArgumentException y no se lanzó nada");
        } catch (IllegalArgumentException e) {
            // esperado: la prueba pasa
        }
    }
    
    
}