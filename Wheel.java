import java.util.Deque;
import java.util.LinkedList;
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
    private Deque<Symbol> symbols;
    private Random randomSpin;
    
    
    /**
     * Crea una nueva rueda
     */
    public Wheel(){
        symbols = new LinkedList<>();
        randomSpin = new Random ();
    }
    
    /**
     * Hace girar la rueda una única rueda
     */
    private void rotateOnce(){
        Symbol temp = symbols.removeFirst();
        symbols.addLast(temp);
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
}