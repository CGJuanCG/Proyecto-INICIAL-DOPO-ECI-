import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * Representa una máquina tragamonedas. La máquina está compuesta por varias
 * ruedas y una única cinta de símbolos que todas ellas comparten: agregar o
 * eliminar un símbolo afecta a la máquina completa, mientras que girar afecta
 * sólo a la rueda indicada.
 *
 * Hacia afuera las posiciones se numeran desde 1 y los símbolos se identifican
 * por su color; la traducción a los índices y objetos internos ocurre en esta
 * clase. La máquina también reparte el espacio de la pantalla entre sus ruedas
 * y coordina el repintado después de cada operación.
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
     * Crea una máquina tragamonedas sin ruedas y sin símbolos. La máquina
     * queda visible, pero no se dibuja nada hasta que tenga ruedas.
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
     * Agrega una rueda nueva en la posición indicada, desplazando las
     * siguientes. Las posiciones fuera de rango se ajustan al extremo.
     *
     * @param pos posición donde se inserta la rueda, empezando en 1
     */
    public void addWheel(int pos)
    {
        int indice = ajustar(pos, wheels.size() + 1) - 1;
        wheels.add(indice, new Wheel(cinta));
        draw();
        lastMove = true;
    }

    /**
     * Elimina la rueda que ocupa la posición indicada. Las posiciones fuera de
     * rango se ajustan al extremo. La operación falla si no hay ruedas.
     *
     * @param pos posición de la rueda que se elimina, empezando en 1
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
     * Agrega a la cinta un símbolo del color indicado, en la posición dada.
     * La operación falla si ya existe un símbolo de ese color.
     *
     * @param pos posición donde se inserta el símbolo, empezando en 1
     * @param color nombre CSS del color del nuevo símbolo
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
     * Elimina de la cinta el símbolo del color indicado. La operación falla si
     * no hay ningún símbolo de ese color.
     *
     * @param symbol nombre CSS del color del símbolo que se elimina
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
     * Ubica la rueda indicada en el símbolo del color dado, de modo que ese
     * símbolo quede visible. La operación falla si no hay ruedas o si el color
     * no está en la cinta.
     *
     * @param wheel posición de la rueda, empezando en 1
     * @param symbol nombre CSS del color que debe quedar visible
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
     * Hace girar la rueda indicada una cantidad aleatoria de posiciones. La
     * operación falla si la máquina no tiene ruedas.
     *
     * @param wheel posición de la rueda que gira, empezando en 1
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
     * Hace girar todas las ruedas de la máquina. La operación falla si la
     * máquina no tiene ruedas.
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
     * Entrega los colores de todos los símbolos de la cinta, en el orden en
     * que están, empezando por el primero.
     *
     * @return los colores de los símbolos de la máquina
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
     * Entrega los colores de los símbolos visibles en las ruedas, ordenados de
     * izquierda a derecha.
     *
     * @return los colores visibles, con null donde la cinta esté vacía
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
     * Cuenta cuántos colores diferentes se ven en la configuración actual.
     *
     * @return la cantidad de símbolos distintos visibles
     */
    public int distinctSymbols()
    {
        lastMove = true;
        return contarDistintos();
    }

    /**
     * Indica si la configuración actual es ganadora, es decir, si todas las
     * ruedas muestran el mismo símbolo.
     *
     * @return true si la máquina está en un estado ganador
     */
    public boolean isJackpot()
    {
        lastMove = true;
        return esGanadora();
    }

    /**
     * Hace visible el simulador y pinta su estado actual.
     */
    public void makeVisible()
    {
        isVisible = true;
        draw();
        lastMove = true;
    }

    /**
     * Hace invisible el simulador. En este estado no se dibuja nada ni se
     * muestran mensajes, de modo que la máquina puede usarse sin interfaz.
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
     * Termina el simulador.
     */
    public void exit()
    {
        System.exit(0);
    }

    /**
     * Indica si la última operación solicitada se pudo realizar.
     *
     * @return true si la última operación tuvo éxito
     */
    public boolean ok()
    {
        return lastMove;
    }

    /**
     * Ajusta una posición al rango válido: las menores a 1 se llevan a 1 y las
     * mayores al máximo se llevan al máximo.
     *
     * @param pos posición solicitada
     * @param max posición más alta admitida
     * @return la posición ya ajustada al rango
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
     * Registra que la última operación falló y avisa al usuario, únicamente
     * si el simulador está visible.
     *
     * @param mensaje explicación de por qué falló la operación
     */
    private void fallo(String mensaje)
    {
        lastMove = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, mensaje);
        }
    }

    /**
     * Reajusta la posición de todas las ruedas después de que la cinta cambie
     * de tamaño, para que ninguna quede fuera de rango.
     */
    private void normalizarRuedas()
    {
        for (Wheel w : wheels) {
            w.normalize();
        }
    }

    /**
     * Cuenta los colores diferentes visibles sin alterar el resultado de ok().
     *
     * @return la cantidad de colores distintos en las ventanas de las ruedas
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
     * Determina si la máquina está en estado ganador sin alterar ok().
     *
     * @return true si todas las ruedas muestran el mismo símbolo
     */
    private boolean esGanadora()
    {
        if (wheels.isEmpty() || cinta.size() == 0) {
            return false;
        }
        return contarDistintos() == 1;
    }

    /**
     * Reparte el ancho disponible entre las ruedas y le asigna a cada una el
     * lugar que ocupa en la pantalla. Se recalcula cada vez que cambia el
     * número de ruedas.
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
     * Vuelve a pintar la máquina completa: reubica las ruedas, pinta el cuerpo
     * con el aspecto que corresponda al estado, y encima cada una de las
     * ruedas. No hace nada si el simulador está invisible.
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