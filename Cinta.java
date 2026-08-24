import java.util.List;
import java.util.ArrayList;

/**
 * The strip of symbols of a slot machine.
 *
 * Every wheel of the machine looks at this same strip, so there is only one of
 * them. The strip keeps its symbols in order and never holds two symbols of the
 * same color.
 *
 * Places are counted from 0, like any list in Java. Turning that into the
 * numbers the user types, which start at 1, is the job of SlotMachine.
 *
 * @author Juan Diego Carreño Gutierrez - Juan Diego Castaño Parra
 * @version 23/08/2026
 */
public class Cinta
{
    private List<Symbol> symbols;

    /**
     * Makes an empty strip.
     */
    public Cinta()
    {
        symbols = new ArrayList<>();
    }

    /**
     * Says how many symbols the strip has.
     *
     * @return how many symbols there are
     */
    public int size()
    {
        return symbols.size();
    }

    /**
     * Gives back the symbol sitting in a given place.
     *
     * @param index the place to look at, from 0 to size()-1
     * @return the symbol in that place, or null when the place does not exist
     */
    public Symbol symbolAt(int index)
    {
        if (index < 0 || index >= symbols.size()) {
            return null;
        }
        return symbols.get(index);
    }

    /**
     * Looks for a symbol and says where it is.
     *
     * @param symbol the symbol to look for
     * @return the place where it sits, or -1 when it is not on the strip
     */
    public int positionOf(Symbol symbol)
    {
        return symbols.indexOf(symbol);
    }

    /**
     * Says whether the strip already has a symbol like this one.
     *
     * @param symbol the symbol to look for
     * @return true when it is already on the strip
     */
    public boolean contains(Symbol symbol)
    {
        return positionOf(symbol) != -1;
    }

    /**
     * Puts a symbol into a given place and pushes the following ones along.
     * Nothing happens when the strip already has that color, or when the place
     * does not exist.
     *
     * @param index the place to put it in, from 0 to size()
     * @param symbol the symbol to add
     * @return true when the symbol was added
     */
    public boolean addSymbol(int index, Symbol symbol)
    {
        if (symbol == null || contains(symbol)) {
            return false;
        }
        if (index < 0 || index > symbols.size()) {
            return false;
        }
        symbols.add(index, symbol);
        return true;
    }

    /**
     * Takes a symbol off the strip.
     *
     * @param symbol the symbol to remove
     * @return true when it was on the strip and got removed
     */
    public boolean delSymbol(Symbol symbol)
    {
        return symbols.remove(symbol);
    }
}
