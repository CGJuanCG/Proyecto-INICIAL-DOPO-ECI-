import java.util.List;
import java.util.ArrayList;

/**
 * Representa la cinta de una máquina tragamonedas: la secuencia ordenada de
 * símbolos que todas las ruedas comparten. No se admiten dos símbolos iguales.
 *
 * Las posiciones se manejan como índices desde 0. Traducir desde las posiciones
 * desde 1 que usa el usuario es responsabilidad de SlotMachine.
 *
 * @author Juan Diego Carreño Gutierrez - Juan Diego Castaño Parra
 * @version 23/08/2026
 */
public class Cinta
{
    private List<Symbol> symbols;

    /**
     * Crea una cinta sin símbolos.
     */
    public Cinta()
    {
        symbols = new ArrayList<>();
    }

    /**
     * Indica cuántos símbolos tiene la cinta.
     *
     * @return la cantidad de símbolos de la cinta
     */
    public int size()
    {
        return symbols.size();
    }

    /**
     * Entrega el símbolo que ocupa el índice indicado.
     *
     * @param index índice del símbolo, entre 0 y size()-1
     * @return el símbolo en esa posición, o null si el índice está fuera de rango
     */
    public Symbol symbolAt(int index)
    {
        if (index < 0 || index >= symbols.size()) {
            return null;
        }
        return symbols.get(index);
    }

    /**
     * Busca la posición que ocupa un símbolo dentro de la cinta.
     *
     * @param symbol el símbolo buscado
     * @return el índice del símbolo, o -1 si no está en la cinta
     */
    public int positionOf(Symbol symbol)
    {
        return symbols.indexOf(symbol);
    }

    /**
     * Indica si la cinta ya contiene un símbolo igual al dado.
     *
     * @param symbol el símbolo buscado
     * @return true si el símbolo ya está en la cinta
     */
    public boolean contains(Symbol symbol)
    {
        return positionOf(symbol) != -1;
    }

    /**
     * Inserta un símbolo en el índice indicado, desplazando los siguientes.
     * Falla si el símbolo ya está en la cinta o si el índice está fuera de rango.
     *
     * @param index índice donde insertar, entre 0 y size()
     * @param symbol el símbolo que se agrega
     * @return true si el símbolo se agregó
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
     * Elimina de la cinta el símbolo igual al dado.
     *
     * @param symbol el símbolo que se elimina
     * @return true si el símbolo estaba en la cinta y fue eliminado
     */
    public boolean delSymbol(Symbol symbol)
    {
        return symbols.remove(symbol);
    }
}
