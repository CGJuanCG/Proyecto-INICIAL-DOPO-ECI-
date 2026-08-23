import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Representa la rueda (Wheel) de una maquina tragamonedas (SlotMachine), dentro
 * de cada rueda existen simbolos que van girando de manera aleatoria.
 * 
 * @author Juan Diego Castaño
 * @version 21-08-2026
 */
public class Wheel
{
    private List<Symbol> symbols;
    private Random randomSpin;
    private int offset;
    
    
    /**
     * Crea una nueva rueda
     */
    public Wheel(){
        symbols = new ArrayList<>();
        randomSpin = new Random ();
        offset = 0;
    }
    
    /**
     * Hace girar la rueda una única rueda
     */
    private void rotateOnce(){
        offset=(offset+1)%(symbols.size());        

    }
    
    /**
     * Hace girar la rueda un n veces de forma aleatoria
     */
    
    public void spin(){
        int n = randomSpin.nextInt(symbols.size());
        for (int i = 0; i < n; i++){
            rotateOnce();
        }
    }
    
    /**
     * Ubica el offset en el simbolo(indice)
     * 
     * @param String symbol es el simbolo que se quiere ubicar
     */
    public void placeSymbol(String symbol){
        offset = symbols.indexOf(symbol);
    }
}