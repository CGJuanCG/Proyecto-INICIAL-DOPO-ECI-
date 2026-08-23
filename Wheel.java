import java.util.Random;
 
/**
* Representa una rueda de la máquina tragamonedas. Una rueda es una ventana
* asomada a la cinta de símbolos que comparten todas las ruedas: lo único
* propio de cada rueda es la posición de la cinta que está mostrando.
*
* La rueda también es responsable de su representación visual, porque es quien
* conoce el lugar que ocupa en la pantalla. El símbolo sólo aporta su color.
*
* @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
* @version 23-08-2026
*/
public class Wheel
{
    private static final String COLOR_MARCO = "white";
    private static final int MARGEN_INTERNO = 4;
 
    private Cinta cinta;
    private int offset;
    private Random randomSpin;
    private Rectangle marco;
    private Rectangle vista;
    private boolean isVisible;
 
    /**
     * Crea una rueda asomada a la cinta indicada, mostrando su primer símbolo.
     * La rueda nace invisible y sin ubicación asignada.
     *
     * @param cinta la cinta de símbolos que la rueda consulta
     */
    public Wheel(Cinta cinta)
    {
        this.cinta = cinta;
        offset = 0;
        randomSpin = new Random();
        marco = new Rectangle();
        vista = new Rectangle();
        isVisible = false;
    }
 
    /**
     * Gira la rueda una cantidad aleatoria de posiciones. Si la cinta está
     * vacía la rueda no se mueve.
     */
    public void spin()
    {
        if (cinta.size() == 0) {
            return;
        }
        rotate(randomSpin.nextInt(cinta.size()));
    }
 
    /**
     * Gira la rueda la cantidad de posiciones indicada. Los valores negativos
     * giran en sentido contrario y los mayores al tamaño de la cinta dan
     * vueltas completas.
     *
     * @param steps cantidad de posiciones que avanza la rueda
     */
    public void rotate(int steps)
    {
        int n = cinta.size();
        if (n == 0) {
            return;
        }
        offset = ((offset + steps) % n + n) % n;
    }
 
    /**
     * Ubica la rueda directamente en la posición indicada de la cinta.
     *
     * @param index índice de la cinta que la rueda debe mostrar
     */
    public void placeAt(int index)
    {
        int n = cinta.size();
        if (n == 0) {
            offset = 0;
            return;
        }
        offset = ((index % n) + n) % n;
    }
 
    /**
     * Entrega el símbolo que la rueda muestra en su ventana.
     *
     * @return el símbolo visible, o null si la cinta está vacía
     */
    public Symbol visibleSymbol()
    {
        return cinta.symbolAt(offset);
    }
 
    /**
     * Indica la posición de la cinta que la rueda está mostrando.
     *
     * @return el índice actual dentro de la cinta
     */
    public int getOffset()
    {
        return offset;
    }
 
    /**
     * Reajusta la posición de la rueda para que siga siendo válida después de
     * que la cinta cambie de tamaño.
     */
    public void normalize()
    {
        int n = cinta.size();
        if (n == 0) {
            offset = 0;
        }
        else if (offset >= n) {
            offset = offset % n;
        }
    }
 
    /**
     * Asigna el lugar y el tamaño que la rueda ocupa en la pantalla. La
     * máquina es quien reparte estas medidas, porque es la única que conoce
     * cuántas ruedas hay.
     *
     * @param x coordenada horizontal de la esquina superior izquierda
     * @param y coordenada vertical de la esquina superior izquierda
     * @param width ancho de la rueda en pixeles
     * @param height alto de la rueda en pixeles
     */
    public void setBounds(int x, int y, int width, int height)
    {
        marco.setCoordinates(x, y);
        marco.changeSize(height, width);
        int interno = MARGEN_INTERNO;
        if (width <= 2 * interno || height <= 2 * interno) {
            interno = 1;
        }
        vista.setCoordinates(x + interno, y + interno);
        vista.changeSize(Math.max(height - 2 * interno, 1),
                         Math.max(width - 2 * interno, 1));
    }
 
    /**
     * Hace visible la rueda y pinta el símbolo que está mostrando.
     */
    public void makeVisible()
    {
        isVisible = true;
        draw();
    }
 
    /**
     * Borra la rueda de la pantalla.
     */
    public void makeInvisible()
    {
        isVisible = false;
        vista.makeInvisible();
        marco.makeInvisible();
    }
 
    /**
     * Vuelve a pintar la rueda con el símbolo que muestra en este momento. No
     * hace nada si la rueda está invisible, de modo que la máquina puede
     * funcionar sin abrir ninguna ventana.
     */
    public void draw()
    {
        if (!isVisible) {
            return;
        }
        marco.changeColor(COLOR_MARCO);
        marco.makeVisible();
        Symbol visible = visibleSymbol();
        if (visible == null) {
            vista.makeInvisible();
        }
        else {
            vista.changeColor(visible.getColor());
            vista.makeVisible();
        }
    }
}