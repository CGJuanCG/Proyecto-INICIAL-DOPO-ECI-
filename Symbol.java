/**
 * A symbol of the slot machine.
 *
 * A symbol is just a color, written the way CSS writes colors: "red", "blue",
 * "green". Two symbols are the same thing when they have the same color, even
 * if they were created separately.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 23-08-2026
 */
public class Symbol
{
    private String color;

    /**
     * Makes a symbol of the given color.
     *
     * @param color the CSS name of the color
     */
    public Symbol(String color)
    {
        this.color = color;
    }

    /**
     * Gives back the color of this symbol.
     *
     * @return the CSS name of the color
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Says whether this symbol is the same as something else. Two symbols
     * match when their colors match. Anything that is not a symbol never
     * matches.
     *
     * @param o the object to compare with
     * @return true when the other object is a symbol of the same color
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Symbol otro = (Symbol) o;
        return color.equals(otro.color);
    }

    /**
     * Gives back a number built from the color. Two symbols of the same color
     * always give the same number, which is what lists and sets need in order
     * to find a symbol.
     *
     * @return a number that stands for this symbol
     */
    @Override
    public int hashCode()
    {
        return color.hashCode();
    }
}
