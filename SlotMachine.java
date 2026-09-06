import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * A slot machine.
 *
 * The machine is made of several wheels and one strip of symbols that all of
 * them share. Adding or removing a symbol changes the whole machine, while
 * spinning changes only the wheel you asked for.
 *
 * From the outside, places are counted from 1 and symbols are named by their
 * color. Turning that into the places and objects used inside is the job of
 * this class. The machine also shares out the screen between its wheels and
 * repaints everything after each move.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 23-08-2026
 */
public class SlotMachine
{
    private static final int CUERPO_X = 15;
    private static final int CUERPO_Y = 45;
    private static final int CUERPO_ANCHO = 270;
    private static final int CUERPO_ALTO = 170;
    private static final int MARGEN = 12;
    private static final int SEPARACION = 6;
    private static final int RUEDA_Y = CUERPO_Y + 35;
    private static final int RUEDA_ALTO = 100;
    private static final String COLOR_CUERPO = "black";
    private static final String COLOR_GANADOR = "yellow";

    private List<Wheel> wheels;
    private Cinta cinta;
    private Rectangle cuerpo;
    private boolean isVisible;
    private boolean lastMove;

    /**
     * Makes a machine with no wheels and no symbols. The machine starts on
     * screen, but nothing is painted until it has wheels.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<>();
        cinta = new Cinta();
        cuerpo = new Rectangle();
        cuerpo.setCoordinates(CUERPO_X, CUERPO_Y);
        cuerpo.changeSize(CUERPO_ALTO, CUERPO_ANCHO);
        isVisible = true;
        lastMove = false;
    }

    /**
     * Adds a new wheel at the place you ask for and pushes the following ones
     * along. Places outside the range are moved to the nearest end.
     *
     * @param pos where the new wheel goes, counting from 1
     */
    public void addWheel(int pos)
    {
        int indice = ajustar(pos, wheels.size() + 1) - 1;
        wheels.add(indice, new Wheel(cinta));
        draw();
        lastMove = true;
    }

    /**
     * Removes the wheel sitting at the place you ask for. Places outside the
     * range are moved to the nearest end. Nothing happens when the machine has
     * no wheels.
     *
     * @param pos which wheel to remove, counting from 1
     */
    public void delWheel(int pos)
    {
        if (wheels.isEmpty()) {
            fallo("La máquina no tiene ruedas.");
            return;
        }
        Wheel eliminada = wheels.remove(ajustar(pos, wheels.size()) - 1);
        eliminada.makeInvisible();
        draw();
        lastMove = true;
    }

    /**
     * Adds a symbol of the given color to the strip, at the place you ask for.
     * Nothing happens when the machine already has a symbol of that color.
     *
     * @param pos where the new symbol goes, counting from 1
     * @param color the CSS name of the color
     */
    public void addSymbol(int pos, String color)
    {
        int indice = ajustar(pos, cinta.size() + 1) - 1;
        if (!cinta.addSymbol(indice, new Symbol(color))) {
            fallo("Ya existe un símbolo de color " + color + ".");
            return;
        }
        normalizarRuedas();
        draw();
        lastMove = true;
    }

    /**
     * Removes the symbol of the given color from the strip. Nothing happens
     * when there is no symbol of that color.
     *
     * @param symbol the CSS name of the color to remove
     */
    public void delSymbol(String symbol)
    {
        if (!cinta.delSymbol(new Symbol(symbol))) {
            fallo("No hay ningún símbolo de color " + symbol + ".");
            return;
        }
        normalizarRuedas();
        draw();
        lastMove = true;
    }

    /**
     * Turns a wheel until the symbol of the given color is the one showing.
     * Nothing happens when the machine has no wheels, or when that color is not
     * on the strip.
     *
     * @param wheel which wheel to turn, counting from 1
     * @param symbol the CSS name of the color that should end up showing
     */
    public void placeSymbol(int wheel, String symbol)
    {
        if (wheels.isEmpty()) {
            fallo("La máquina no tiene ruedas.");
            return;
        }
        int indice = cinta.positionOf(new Symbol(symbol));
        if (indice == -1) {
            fallo("No hay ningún símbolo de color " + symbol + ".");
            return;
        }
        wheels.get(ajustar(wheel, wheels.size()) - 1).placeAt(indice);
        draw();
        lastMove = true;
    }

    /**
     * Spins one wheel by a random amount. Nothing happens when the machine has
     * no wheels.
     *
     * @param wheel which wheel to spin, counting from 1
     */
    public void spin(int wheel)
    {
        if (wheels.isEmpty()) {
            fallo("La máquina no tiene ruedas.");
            return;
        }
        wheels.get(ajustar(wheel, wheels.size()) - 1).spin();
        draw();
        lastMove = true;
    }

    /**
     * Spins every wheel of the machine. Nothing happens when the machine has no
     * wheels.
     */
    public void spin()
    {
        if (wheels.isEmpty()) {
            fallo("La máquina no tiene ruedas.");
            return;
        }
        for (Wheel w : wheels) {
            w.spin();
        }
        draw();
        lastMove = true;
    }

    /**
     * Gives back the colors of all the symbols on the strip, in the order they
     * sit, starting with the first one.
     *
     * @return the colors of the symbols of the machine
     */
    public String[] symbols()
    {
        String[] resultado = new String[cinta.size()];
        for (int i = 0; i < cinta.size(); i++) {
            resultado[i] = cinta.symbolAt(i).getColor();
        }
        lastMove = true;
        return resultado;
    }

    /**
     * Gives back the colors showing in the windows of the wheels, read from
     * left to right.
     *
     * @return the colors on view, with null wherever the strip is empty
     */
    public String[] configuration()
    {
        String[] resultado = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            Symbol visible = wheels.get(i).visibleSymbol();
            if (visible != null) {
                resultado[i] = visible.getColor();
            }
        }
        lastMove = true;
        return resultado;
    }

    /**
     * Counts how many different colors are showing right now.
     *
     * @return how many different symbols are on view
     */
    public int distinctSymbols()
    {
        lastMove = true;
        return contarDistintos();
    }

    /**
     * Says whether the machine has won, that is, whether every wheel is showing
     * the same symbol.
     *
     * @return true when the machine is in a winning state
     */
    public boolean isJackpot()
    {
        lastMove = true;
        return esGanadora();
    }

    /**
     * Puts the machine on screen and paints how it looks right now.
     */
    public void makeVisible()
    {
        isVisible = true;
        draw();
        lastMove = true;
    }

    /**
     * Takes the machine off the screen. While it is hidden nothing is painted
     * and no messages pop up, so the machine can be used without any window.
     */
    public void makeInvisible()
    {
        isVisible = false;
        for (Wheel w : wheels) {
            w.makeInvisible();
        }
        cuerpo.makeInvisible();
        lastMove = true;
    }

    /**
     * Closes the simulator.
     */
    public void exit()
    {
        System.exit(0);
    }

    /**
     * Says whether the last thing you asked for could be done.
     *
     * @return true when the last move worked
     */
    public boolean ok()
    {
        return lastMove;
    }

    /**
     * Moves a place into the range that makes sense: anything below 1 becomes
     * 1, and anything above the top becomes the top.
     *
     * @param pos the place that was asked for
     * @param max the highest place allowed
     * @return the place once it fits in the range
     */
    private int ajustar(int pos, int max)
    {
        if (pos < 1) {
            return 1;
        }
        if (pos > max) {
            return max;
        }
        return pos;
    }
    
    /**
     * Swap the position between 2 wheels in the machine. Positions are counted
     * from 1. Nothing happens when either place does not exist.
     * 
     * @param int wheel1 index of wheel #1
     * @param int wheel2 index of wheel #2
     */
    public void swap(int wheel1, int wheel2){
        if(wheels.isEmpty()){
            fallo("La maquina no tiene ruedas");
            return;
        }
        if(!existe(wheel1) || !existe(wheel2)){
            fallo("Indice de rueda invalido");
            return;
        }
        
        int i1 = wheel1 - 1;
        int i2 = wheel2 - 1;
        Wheel temp = wheels.get(i1);
        wheels.set(i1, wheels.get(i2));
        wheels.set(i2, temp);
        
        draw();
        lastMove = true;
    }
    
    /**
     * locks a  wheel so that it cannot spin. Places are counted
     * from 1. Nothing happens when that place does not exist.
     * @param int wheel index of the wheel that user wants to block.
     */
    public void lock(int wheel){
        if (wheels.isEmpty()){
            fallo("La maquina no tiene ruedas.");
            return;
        }
        if(!existe(wheel)){
            fallo("Indice de rueda invalido.");
        }
        
        wheels.get(wheel - 1).setFixedWheel();
        lastMove = true;
    }
    
    /**
     * unlocks a wheel so that it can spin. Places are counted
     * from 1. Nothing happens when that place does not exist.
     * @param int wheel index of the wheel that user wants to unlock.
     */
    public void unlock(int wheel){
        if(wheels.isEmpty()){
            fallo("La maquina no tiene ruedas.");
            return;
        }
        if(!existe(wheel)){
            fallo("Indice de rueda invalido.");
            return;
        }
        
        wheels.get(wheel - 1).setNonFixedWheel();
        lastMove = true;
    }
    
    /**
     * Rotates the wheel at the given place by the given number of steps.
     * Places are counted from 1. Positive steps rotate the wheel one way
     * and negative steps rotate the wheel the other way.
     * 
     * @param wheel the place of the wheel to rotate
     * @param steps how many steps to rotate
     */
    public void spin(int wheel, int steps){
        if(wheels.isEmpty()){
            fallo("La maquina no tiene ruedas.");
            return;
        }
        if(!existe(wheel)){
            fallo("Indice de rueda invalido.");
            return;
        }
        
        wheels.get(wheel - 1).rotate(steps);
        draw();
        lastMove = true;
    
    }
    
    /**
     * says wheter the given place correspons to an existing wheel.
     * 
     * @param pos the place to check, counting from 1.
     * @return true when there is a wheel at that place
     */
    private boolean existe(int pos){
        return pos >= 1 && pos <= wheels.size();
    }
    

    /**
     * Notes that the last move did not work, and tells the user why, but only
     * while the machine is on screen.
     *
     * @param mensaje what went wrong
     */
    private void fallo(String mensaje)
    {
        lastMove = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, mensaje);
        }
    }

    /**
     * Moves every wheel back into a place that still exists, after the strip
     * has grown or shrunk.
     */
    private void normalizarRuedas()
    {
        for (Wheel w : wheels) {
            w.normalize();
        }
    }

    /**
     * Counts the different colors on view without changing what ok() will say.
     *
     * @return how many different colors are showing in the windows
     */
    private int contarDistintos()
    {
        Set<String> distintos = new HashSet<>();
        for (Wheel w : wheels) {
            Symbol visible = w.visibleSymbol();
            distintos.add(visible == null ? null : visible.getColor());
        }
        return distintos.size();
    }

    /**
     * Works out whether the machine has won, without changing what ok() will
     * say.
     *
     * @return true when every wheel shows the same symbol
     */
    private boolean esGanadora()
    {
        if (wheels.isEmpty() || cinta.size() == 0) {
            return false;
        }
        return contarDistintos() == 1;
    }

    /**
     * Shares out the width available between the wheels and tells each one
     * where it sits. It is worked out again every time the number of wheels
     * changes.
     */
    private void ubicarRuedas()
    {
        int n = wheels.size();
        if (n == 0) {
            return;
        }
        int disponible = CUERPO_ANCHO - 2 * MARGEN;
        int separacion = SEPARACION;
        int ancho = (disponible - (n - 1) * separacion) / n;
        if (ancho < 6) {
            separacion = 1;
            ancho = Math.max((disponible - (n - 1) * separacion) / n, 2);
        }
        int x = CUERPO_X + MARGEN;
        for (Wheel w : wheels) {
            w.setBounds(x, RUEDA_Y, ancho, RUEDA_ALTO);
            x += ancho + separacion;
        }
    }

    /**
     * Paints the whole machine again: it places the wheels, paints the body in
     * the color that matches the state, and then paints each wheel on top. It
     * does nothing while the machine is hidden.
     */
    private void draw()
    {
        if (!isVisible) {
            return;
        }
        ubicarRuedas();
        if (esGanadora()) {
            cuerpo.changeColor(COLOR_GANADOR);
        }
        else {
            cuerpo.changeColor(COLOR_CUERPO);
        }
        cuerpo.makeVisible();
        for (Wheel w : wheels) {
            w.makeVisible();
        }
    }
}
