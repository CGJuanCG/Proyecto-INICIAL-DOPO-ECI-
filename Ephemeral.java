/**
 * A symbol that fades away. Every time the machine makes a spin, the symbol
 * gets smaller, until it is just a dot. It is drawn as a circle, so it can be
 * told apart from the other kinds of symbols.
 *
 * The strip is shared by every wheel, so the symbol shrinks in all of them at
 * once. Its color does not change, so for the game it is still the same
 * symbol: it still counts for the configuration and for the jackpot.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 21-10-2026
 */
public class Ephemeral extends Symbol
{
    private static final int PASO = 20;

    private int size;

    /**
     * Makes an ephemeral symbol of the given color, at its full size.
     *
     * @param color the CSS name of the color
     */
    public Ephemeral(String color)
    {
        super(color);
        size = 100;
    }

    /**
     * Gets a bit smaller, because the machine has just made a spin. Once it
     * is a dot it stays a dot.
     */
    @Override
    public void notifySpin()
    {
        size = Math.max(size - PASO, 0);
    }

    /**
     * Draws the symbol as a circle in the middle of the window, as big as its
     * current size.
     *
     * @param ventana the window of the wheel where the symbol shows
     */
    @Override
    public void paint(Ventana ventana)
    {
        ventana.paintCircle(getColor(), size);
    }

    /**
     * Says how big the symbol is now.
     *
     * @return the size, from 100 (full size) down to 0 (a dot)
     */
    public int getSize()
    {
        return size;
    }
}
