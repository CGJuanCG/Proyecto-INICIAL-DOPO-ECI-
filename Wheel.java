import java.util.Random;

/**
 * A wheel of the slot machine.
 *
 * A wheel is a window onto the strip of symbols that all the wheels share. The
 * only thing a wheel owns is the place of the strip it is stopped at.
 *
 * The wheel also draws itself, because it is the one that knows where it sits
 * on the screen. The symbol only lends its color.
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
     * Makes a wheel that looks at the given strip, stopped at its first symbol.
     * The wheel starts hidden and with no place on the screen yet.
     *
     * @param cinta the strip of symbols the wheel reads from
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
     * Spins the wheel by a random amount. Nothing happens when the strip has no
     * symbols.
     */
    public void spin()
    {
        if (cinta.size() == 0) {
            return;
        }
        rotate(randomSpin.nextInt(cinta.size()));
    }

    /**
     * Moves the wheel forward by a number of places. Negative numbers move it
     * backwards, and numbers larger than the strip simply go around.
     *
     * @param steps how many places the wheel moves
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
     * Stops the wheel right at a given place of the strip.
     *
     * @param index the place the wheel should show
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
     * Gives back the symbol showing in the window of this wheel.
     *
     * @return the symbol on view, or null when the strip is empty
     */
    public Symbol visibleSymbol()
    {
        return cinta.symbolAt(offset);
    }

    /**
     * Says which place of the strip the wheel is stopped at.
     *
     * @return the place the wheel is showing
     */
    public int getOffset()
    {
        return offset;
    }

    /**
     * Moves the wheel back into a place that still exists, after the strip has
     * grown or shrunk.
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
     * Tells the wheel where it sits on the screen and how big it is. The
     * machine hands out these numbers, because it is the only one that knows
     * how many wheels there are.
     *
     * @param x how far from the left the wheel starts
     * @param y how far from the top the wheel starts
     * @param width how wide the wheel is
     * @param height how tall the wheel is
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
     * Shows the wheel and paints the symbol it is stopped at.
     */
    public void makeVisible()
    {
        isVisible = true;
        draw();
    }

    /**
     * Takes the wheel off the screen.
     */
    public void makeInvisible()
    {
        isVisible = false;
        vista.makeInvisible();
        marco.makeInvisible();
    }

    /**
     * Paints the wheel again with whatever symbol it is showing now. It does
     * nothing while the wheel is hidden, so the machine can run without opening
     * any window.
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
