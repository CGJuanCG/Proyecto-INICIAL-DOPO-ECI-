/**
 * The window of a wheel: the space inside its frame where a symbol shows.
 *
 * Every wheel owns one window and lends it to the symbol it is stopped at, so
 * the symbol can draw itself. The window knows where it sits and how big it
 * is, and offers a few ways of painting; the symbol decides which one to use.
 * Only one shape is on view at a time.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez

 */
public class Ventana
{
    private static final int PUNTO = 4;

    private Rectangle rectangulo;
    private Circle circulo;
    private Triangle triangulo;
    private int x;
    private int y;
    private int ancho;
    private int alto;

    /**
     * Makes an empty window with no place on the screen yet.
     */
    public Ventana()
    {
        rectangulo = new Rectangle();
        circulo = new Circle(0, 0);
        triangulo = new Triangle(0, 0);
        ancho = 1;
        alto = 1;
    }

    /**
     * Tells the window where it sits and how big it is. Nothing is painted
     * until a symbol draws itself on it.
     *
     * @param x how far from the left the window starts
     * @param y how far from the top the window starts
     * @param ancho how wide the window is
     * @param alto how tall the window is
     */
    public void setBounds(int x, int y, int ancho, int alto)
    {
        this.x = x;
        this.y = y;
        this.ancho = Math.max(ancho, 1);
        this.alto = Math.max(alto, 1);
    }

    /**
     * Fills the whole window with a color.
     *
     * @param color the CSS name of the color
     */
    public void paintRectangle(String color)
    {
        circulo.makeInvisible();
        triangulo.makeInvisible();
        rectangulo.setCoordinates(x, y);
        rectangulo.changeSize(alto, ancho);
        rectangulo.changeColor(color);
        rectangulo.makeVisible();
    }

    /**
     * Paints a circle in the middle of the window. At 100 percent the circle
     * is as big as the window allows; at 0 percent it is just a dot.
     *
     * @param color the CSS name of the color
     * @param percent how big the circle is, from 0 to 100
     */
    public void paintCircle(String color, int percent)
    {
        rectangulo.makeInvisible();
        triangulo.makeInvisible();
        int lado = Math.min(ancho, alto);
        int diametro = Math.max(lado * percent / 100, PUNTO);
        circulo.setCoordinates(x + (ancho - diametro) / 2, y + (alto - diametro) / 2);
        circulo.changeSize(diametro);
        circulo.changeColor(color);
        circulo.makeVisible();
    }

    /**
     * Paints a triangle that fills the window.
     *
     * @param color the CSS name of the color
     */
    public void paintTriangle(String color)
    {
        rectangulo.makeInvisible();
        circulo.makeInvisible();
        triangulo.changeSize(alto, ancho);
        triangulo.setCoordinates(x + ancho / 2, y + alto / 2);
        triangulo.changeColor(color);
        triangulo.makeVisible();
    }

    /**
     * Leaves the window empty.
     */
    public void clear()
    {
        rectangulo.makeInvisible();
        circulo.makeInvisible();
        triangulo.makeInvisible();
    }
}
