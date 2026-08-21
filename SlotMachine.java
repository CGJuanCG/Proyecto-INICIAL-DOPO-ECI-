import java.util.ArrayList;

/**
 * Write a description of class SlotMachine here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
}