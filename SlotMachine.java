import java.util.ArrayList;

/**
 * Write a description of class SlotMachine here.
 *
 * @author Juan Diego Castaño
 * @version 21-08-2026
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean isVisible;
    private boolean lastMove;
    
    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine()
    {
        wheels = new ArrayList<>();
        isVisible = true;
        lastMove = false;
    }
    
    public void addWheel(int pos){
        if(pos < 1){
            pos = 1;
        }
        if (pos > wheels.size() + 1){
            pos = wheels.size() + 1;
        }
        int indice = pos - 1;
        wheels.add(indice, new Wheel());
        lastMove = true;
    }
    
    public void delWheel(int pos){
        if (wheels.isEmpty()){
            lastMove = false;
        }
        else {
            if (pos < 1){
                pos = 1;
            }
            if(pos > wheels.size()){
                pos = wheels.size();
            }
            int indice = pos -1;
            wheels.remove(indice);
            lastMove = true;
        }
    }
}