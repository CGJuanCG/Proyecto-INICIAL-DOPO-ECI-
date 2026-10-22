/**
 * A shy symbol. Every time a wheel stops on it, it switches between showing
 * itself and hiding. It is drawn as a triangle, so it can be told apart from
 * the other kinds of symbols.
 *
 * The strip is shared by every wheel, so when it hides, it hides in all of
 * them. If two wheels stop on it in the same spin, it switches twice. Hiding
 * only changes how it looks: for the game it is still there, so it still
 * counts for the configuration and for the jackpot.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 21-10-2026
 */
public class Shy extends Symbol
{
    private boolean shown;

    /**
     * Makes a shy symbol of the given color, showing itself.
     *
     * @param color the CSS name of the color
     */
    public Shy(String color)
    {
        super(color);
        shown = true;
    }

    /**
     * Switches between showing itself and hiding, because a wheel has just
     * stopped on it.
     */
    @Override
    public void select()
    {
        shown = !shown;
    }

    /**
     * Draws the symbol as a triangle, or leaves the window empty while it is
     * hiding.
     *
     * @param ventana the window of the wheel where the symbol shows
     */
    @Override
    public void paint(Ventana ventana)
    {
        if (shown) {
            ventana.paintTriangle(getColor());
        }
        else {
            ventana.clear();
        }
    }

    /**
     * Says whether the symbol is showing itself now.
     *
     * @return true when it is on view, false while it is hiding
     */
    public boolean isShown()
    {
        return shown;
    }
}
