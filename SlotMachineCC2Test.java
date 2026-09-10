

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineCC2Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineCC2Test
{
    private SlotMachine slotMachine;
    /**
     * Default constructor for test class SlotMachineCC2Test
     */
    public SlotMachineCC2Test()
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
        slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addSymbol(3, "green");
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");
    }
    


    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
   @AfterEach
    public void tearDown()
    {
        slotMachine = null;
    }
   

   /**
     * Verifies that attempting to swap two wheels fails when one of
     * them is locked, setting the machine status to not ok.
     */
    //No pasa debido a diferente logica en implementacion, ellos hacen 
    //lock de que la rueda no se mueva, nosotros de que no cambie de simbolo
    @Test
    public void shouldNotSwap() {
        // Add some symbols
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
       
        // Add some wheels
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
       
        // Lock the first one
        slotMachine.lock(1);
       
        // Try to swap the 1st and the 3rd
        slotMachine.swap(1, 3);
       
        // Check that the action wasn't succesful due to the wheel to spin is locked
        assertFalse(slotMachine.ok());
    }
    
    //G01: DiazR- RojasM, no pasan debido a que nuestro addsymbol solo permite un color, no 2
    
    /**
     * debe de ser jackpot cuando todas las ruedas muestren el mismo color
     */
    /*
    @Test
        public void accordingDrRmShouldBeJackpotWhenAllWheelsMatch()
        {
            slotMachine.addWheel(1);
            slotMachine.addWheel(2);
            slotMachine.addWheel(3);
            slotMachine.addSymbol(1, "red", "triangle");
            slotMachine.addSymbol(2, "blue", "rectangle");
            slotMachine.spin(new String[]{"red", "red", "red"});
            assertTrue(slotMachine.isJackpot());
        }
    */
    /**
     * debe mantenerse el numero de ruedas antes y despues de un delete 
     */
    
    /*
    public void accordingDrRmShouldKeepCorrectWheelCountAfterAddAndDelete()
    {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
        slotMachine.delWheel(2);
        slotMachine.addSymbol(1, "red", "triangle");
        slotMachine.spin(new String[]{"red", "red"});
        assertEquals(2, slotMachine.configuration().length);
    }
    */
    
    //G01: FrancoS - GarciaC
    //se cambia sm por slotMachine

 
    /**
    * QUE NO DEBERIA HACER: intercambiar dos ruedas cuando una de ellas
    * esta fija (lock). La operacion debe fallar y dejar ok() en false,
    * sin alterar la configuracion de ninguna rueda.
    */
   
    @Test
    public void fsGcShouldNotSwapWhenAWheelIsLocked()
    {
       slotMachine.lock(1);
       String[] before = slotMachine.configuration().clone();
        slotMachine.swap(1, 3);
        assertFalse(slotMachine.ok());
       assertArrayEquals(before, slotMachine.configuration());
    }
    /**
    * QUE DEBERIA HACER: intercambiar dos ruedas no debe alterar la
    * cantidad de simbolos distintos que se reportan para la maquina
    * (swap mueve el contenido, no lo modifica).
    */
    @Test
    public void fsGcShouldKeepDistinctSymbolCountAfterSwap()
    {
       int before = slotMachine.distinctSymbols();
        slotMachine.swap(1, 2);
        assertTrue(slotMachine.ok());
       assertEquals(before, slotMachine.distinctSymbols());
    }
    
    
        //G01 : BarraganA-GuerreroB
    /** Prueba que una rueda existente pueda ser bloqueada correctamente.
        * La maquina debe permitir bloquear una rueda que existe.
        * La operacion debe realizarse correctamente y ok() debe retornar true.
    */
            
        @Test
        public void accordingBaGqShouldLockWheel() {
            SlotMachine maquinaTraga = new SlotMachine();
            maquinaTraga.makeInvisible();
            maquinaTraga.addWheel(1);
            maquinaTraga.lock(1);
            assertTrue(maquinaTraga.ok());
        }
     
    /** * Prueba que una rueda bloqueada no pueda girar.
    * ¿Que no deberia hacer? * Una rueda que se encuentra bloqueada no debe avanzar
    * cuando se intenta realizar un spinStep.*/
    
    @Test
        public void accordingBaGqShouldNotSpinLockedWheel() {
            SlotMachine maq = new SlotMachine();
            maq.makeInvisible();
            maq.addWheel(1);
            maq.addSymbol(1, "red");
            maq.addSymbol(2, "blue");
            maq.placeSymbol(1, "red");
            maq.lock(1);
            maq.spin(1, 1);
            assertFalse(maq.ok());
            String[] config = maq.configuration();
            assertEquals("red", config[0]);
        }
        
        
    //Grupo Coronado - Horta:
     
    /*
    * A single wheel should never trigger a jackpot even if it already has a symbol placed
    */
     
    @Test
    public void accordingCgHnIsJackpotShouldBeFalseWithOnlyOneWheelEvenIfSymbolIsSet()
    {
        slotMachine.addWheel(1);
        slotMachine.addSymbol(1, "red");
        slotMachine.placeSymbol(1, "red");

        assertFalse(slotMachine.isJackpot());
    }
    /* Adding a color that already exists on a wheel should fail */

    @Test
    public void accordingCgHnAddSymbolShouldFailWhenColorAlreadyExists() {
        slotMachine.addWheel(1);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "red");
        assertFalse(slotMachine.ok());
    }
    
    //G03: CanchonL-PaezC


    @Test
        public void accordingClPcShouldKeepDistinctSymbolCountAfterSwap(){
            slotMachine.addWheel(1);
            slotMachine.addWheel(2);
            slotMachine.addSymbol(1, "red");
            slotMachine.addSymbol(2, "blue");
            slotMachine.placeSymbol(1, "red");
            slotMachine.placeSymbol(2, "blue");

            int before = slotMachine.distinctSymbols();
            slotMachine.swap(1, 2);
            int after = slotMachine.distinctSymbols();

            assertTrue(slotMachine.ok());
            assertEquals(before, after, "swap no deberia alterar el catalogo de simbolos");
        }

    // EJEMPLO 2 
    // "que NO deberia hacer": dejar una configuracion dada (spin con
    // arreglo) no deberia declarar jackpot si los simbolos asignados no
    // coinciden entre si.
    
    @Test
    public void accordingClPcShouldNotReportJackpotWhenSetConfigurationDiffers(){
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");

        slotMachine.spin(new String[]{"red", "blue"});

        assertTrue(slotMachine.ok());
        assertFalse(slotMachine.isJackpot(), "simbolos distintos en las ruedas no es jackpot");
    }
    
        //Grupo 07 - Juan C-Santiago R
    /** Prueba que se puedan intercambiar dos ruedas */
    @Test
    public void swapShouldExchangeWheels()
    {
             String[] before = slotMachine.configuration();
             slotMachine.swap(1, 3);
             String[] after = slotMachine.configuration();
             assertEquals(before[0], after[2]);
             assertEquals(before[2], after[0]);
    }
    /** Una rueda fijada no debe girar*/
    @Test
    public void lockedWheelShouldNotSpin()
    {
            String[] before = slotMachine.configuration();
            slotMachine.lock(1);
            slotMachine.spin(1);
            String[] after = slotMachine.configuration();
            assertArrayEquals(before, after);
    }
    
    //Grupo-02 ForeroJ-SalinasS

    //Unit Test:
    // (setUp() duplicado eliminado: ya existe el @BeforeEach de la linea 30)

    /**
     * Tests that configuration() reports each wheel's current color
     */
    @Test
    
    public void accordingFmSnShouldShowCorrectConfiguration(){
    
        slotMachine.addSymbol(1, "red");  // Add a new symbol [black,red] - With black being the default. 
        slotMachine.addSymbol(1, "green"); // Add another symbol [black,green,red].
        slotMachine.placeSymbol(1, "red"); // Change the first wheel's symbol to red.
        slotMachine.placeSymbol(2, "green"); // Change the second wheel's symbol to green.
        slotMachine.placeSymbol(3, "black"); // Change the third wheel's symbol to black.
    
        
        String[] config = slotMachine.configuration(); // Create config with the machine's current configuration.
        
        assertEquals("red", config[0]); // Verify that positions match.
        assertEquals("green", config[1]); //
        assertEquals("black", config[2]); //
    }
    
    //Grupo: BustosL-GomezG

    @Test
    
    /* * Verifica que la maquina pueda quedar en una configuracion * solicitada cuando los simbolos existen en todas las ruedas. */
    
    public void accordingIbPoShouldSetValidConfiguration()
    {
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();

        slotMachine.addWheel(0);
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
    
        slotMachine.addSymbol(0, "red");
        slotMachine.addSymbol(0, "blue");
    
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
    
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "blue");
    
        slotMachine.spin(
            new String[]{"blue", "red", "blue"}
        );
    
        String[] configuration = slotMachine.configuration();
    
        assertEquals("blue", configuration[0]);
        assertEquals("red", configuration[1]);
        assertEquals("blue", configuration[2]);
    }
    
    @Test
    
    /** * Verifica que una configuracion con un numero incorrecto * de simbolos no modifique la configuracion actual */
    public void accordingIbPoShouldNotChangeConfigurationWithWrongSize()
    {
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();

        slotMachine.addWheel(0);
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
    
        slotMachine.addSymbol(0, "red");
        slotMachine.addSymbol(0, "blue");
    
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
    
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "blue");
    
        slotMachine.spin(
            new String[]{"red", "red", "red"}
        );
    
        String[] before = slotMachine.configuration();
    
        // Configuración incorrecta: solo tiene 2 posiciones
        slotMachine.spin(
            new String[]{"blue", "blue"}
        );
    
        String[] after = slotMachine.configuration();
    
        assertArrayEquals(before, after);
    }
    //G03: GonzalezM-LesmesA

    //Prueba 1:
    /**
     * Una rueda fija no debería poder girar.
     */
    @Test
    public void accordingGmLaShouldNotAllowSpinningALockedWheel() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.lock(1);
        machine.spin(1);
        assertFalse(machine.ok(), "una rueda fija no debería poder girar");
    }
    
    //Prueba 2:
    /**
     * spin(setSymbols) debe dejar la máquina justo en esos colores.
     */
    @Test
    public void accordingGmLaShouldReflectExactConfigurationAfterSpinWithGivenSymbols() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.spin(new String[] { "yellow", "red", "blue" });
        assertTrue(machine.ok(), "dejar la máquina en una configuración dada debe ser exitoso");
        assertArrayEquals(new String[] { "yellow", "red", "blue" }, machine.configuration());
    }
    //G06: CañonA - PaezP

     //Prueba #1

    /**
     * Verifica el intercambio entre ruedas bloqueadas y desbloqueadas,
     * y la rotación de una rueda por pasos.
     */
    @Test
    public void accordingCaPpShouldNotSwapWhenWheelIsLockedAndWorkWhenUnlocked(){
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);

        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");

        slotMachine.addSymbol(2, "green");
        slotMachine.addSymbol(2, "yellow");

        slotMachine.addSymbol(3, "magenta");
        slotMachine.addSymbol(3, "black");

        slotMachine.spin(new String[]{"blue", "yellow", "magenta"});
        assertEquals("blue", slotMachine.configuration()[0]);

        slotMachine.lock(2);
        slotMachine.swap(1, 2);
        
        assertFalse(slotMachine.ok());
        assertEquals("yellow", slotMachine.configuration()[1]);

        slotMachine.unlock(2);
        slotMachine.swap(1, 2);
        
        assertTrue(slotMachine.ok());
        assertEquals("yellow", slotMachine.configuration()[0]);
        assertEquals("blue", slotMachine.configuration()[1]);

        slotMachine.spin(3, 1);
        assertTrue(slotMachine.ok());
        assertNotNull(slotMachine.configuration());
        assertEquals(3, slotMachine.configuration().length);
    }

    //Prueba #2
    
    /**
     * Verifica la rotación por pasos y la detección de un jackpot.
     */
    @Test
    public void accordingCaPpShouldSpinByStepsAndDetectJackpotCorrectly(){
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);

        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
        
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "green");

        slotMachine.spin(new String[]{"red", "green"});
        assertTrue(slotMachine.ok());
        
        assertEquals(3, slotMachine.distinctSymbols());
        assertFalse(slotMachine.isJackpot());


        slotMachine.spin(2, 1);
        assertTrue(slotMachine.ok());

        String[] currentConfiguration = slotMachine.configuration();
        assertEquals("red", currentConfiguration[0]);
        assertEquals("red", currentConfiguration[1]);
        
        assertTrue(slotMachine.isJackpot());
    }
    //G07: GarcíaJ-QuezadaK

    /**
     * spin(wheel, 0) no debería mover la rueda — girar cero pasos es una
     * operación válida que no cambia nada, no un error.
     */
    @Test
    public void accordingGjQkShouldSucceedWithoutChangingConfigurationWhenSpinningZeroSteps() {
        SlotMachine m = new SlotMachine();
        m.makeInvisible();
        m.addWheel(1);
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        String before = m.configuration()[0];
        m.spin(1, 0);
        assertTrue(m.ok());
        assertEquals(before, m.configuration()[0]);
    }
    
    /**
     * Fijar una rueda que ya estaba fija no debería fallar — lock() es
     * idempotente, no un error repetir la operación.
     */
    @Test
    public void accordingGjQkShouldSucceedWhenLockingAnAlreadyLockedWheel() {
        SlotMachine m = new SlotMachine();
        m.makeInvisible();
        m.addWheel(1);
        m.lock(1);
        m.lock(1);
        assertTrue(m.ok());
    }
    
    //G02: GarzonR- MendivelsoS:

    /**
     * If a symbol doesn't exists, wheels should change their symbols except 
     * that one with the strange symbol.
     */
    @Test
    public void accordingGrMsShouldNotSetSymbolsThatDoesNotExists(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addSymbol(1,"red");
        slotMachine.addSymbol(2,"blue");
        slotMachine.addSymbol(3,"green");
        while(slotMachine.configuration().length < 3){
             slotMachine.addWheel(1);   
        }

        
        String[] begin = {"red", "blue", "green"};
        slotMachine.spin(begin);
        
        String[] strangeSymbols = {"red", "null", "blue"};
        slotMachine.spin(strangeSymbols);
        
        String[] waited = {"red", "blue", "blue"};
        String[] afterSpin = slotMachine.configuration();
        
        assertEquals(waited, afterSpin);
    }
    /**
     * If a symbol doesn't exists it shouldn't be added and 
     * you shouldn't win if you try to set the machine with that symbol.
     */
    @Test
    public void accordingGrMsShouldNotAddStrangeSymbolsAndWinWithThatSymbol(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addSymbol(1,"red");
        slotMachine.addSymbol(2,"blue");
        slotMachine.addSymbol(3,"green");
        while(slotMachine.configuration().length < 3){
             slotMachine.addWheel(1);   
        }

        slotMachine.addSymbol(4, "strange");
        
        String[] strangeCombination = {"strange", "strange", "strange"};
        
        assertFalse(slotMachine.isJackpot());
    }
    
    //G02: Carvajal- Largo:

    /**
     * Verifies that spinning a wheel advances it to the next symbol
     * in the machine's symbol list.
     */
    @Test
    public void accordingCxLxShouldAdvanceToNextSymbol() {
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");
        slotMachine.spin(1);
        assertEquals("blue", slotMachine.configuration()[0]);
        assertTrue(slotMachine.ok());
    }

    /**
     * Verifies that deleting a symbol that is not in the machine fails
     * and leaves the symbol list unchanged.
     */
    @Test
    public void accordingCxLxShouldNotDeleteMissingSymbol() {
        slotMachine.addSymbol(1, "red");
        slotMachine.delSymbol("blue");
        assertFalse(slotMachine.ok());
        assertEquals(1, slotMachine.symbols().length);
    }
    
    //G02: MeloR-SanabriaE

    /**
     * Debería: una rueda fijada no debe moverse ante un intento de
     * giro, y debe volver a poder girar después de un unlock.
     */
    @Test
    public void accordingMrSeShouldKeepLockedWheelFixedAndAllowSpinAfterUnlock() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.placeSymbol(1, "red"); // estado conocido, sin asumir orden de addSymbol
    
        machine.lock(1);
        machine.spin(1, 2); // intenta girar la rueda fija
    
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]); // no debió moverse
    
        machine.unlock(1);
        machine.spin(1, 1); // ahora sí debe poder girar
    
        assertTrue(machine.ok());
    }
    
    /**
     * No debería: aplicar ningún cambio si uno de los colores pedidos
     * no existe en su rueda correspondiente (comportamiento todo o nada).
     */
    @Test
    public void accordingMrSeShouldRejectSpinSetSymbolsWhenColorMissing() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "green");
    
        String[] before = machine.configuration();
        machine.spin(new String[]{"red", "purple"}); // "purple" no existe
    
        assertFalse(machine.ok());
        assertArrayEquals(before, machine.configuration()); // nada se movió
    }
}