import  java.util.List;
import java.util.ArrayList;


/**
 * Write a description of class Cinta here.
 * 
 * @author (Juan Diego Carreño Gutierrez - Juan Diego Castaño Parra) 
 * @version (22/08/2026)
 */
public class Cinta
{
    private List<Symbol> cinta;

    /**
     * Constructor for objects of class Cinta
     */
    public Cinta()
    {
        cinta = new ArrayList<>();
        
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public boolean addSymbol(int pos,Symbol symbol)
    {
        
        cinta.add(symbol);
        return true;

    }
    
    public int size(){
        return cinta.size();
    }
}