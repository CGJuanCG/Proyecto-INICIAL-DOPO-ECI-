
/**
 *  A wheel that doesn't let be blocked, swap or delete
 * 
 * @Juan Carreño - Juan Castaño
 */
public class Rebel extends Wheel
{


    /**
     * Rebel: A wheel that doesn't let be blocked, swap or delete
     * 
     * Makes a rebel wheel
     * @param cinta the strip of symbols the wheel reads from
     */
    public Rebel(Cinta cinta)
    {
        super(cinta);
    }
    
    /**
     * A rebel Wheel could never be fixed
     * @return false because never fix a wheel
     **/
    @Override
    public boolean setFixedWheel(){
        return false;
    }
    
    /**
     * A rebel Wheel could never be swapped
     * @return false because never swap a wheel
     */
    @Override
    public boolean canBeSwapped(){
        return false;
    }
    
    /**
     * A rebel Wheel could never be removed
     * @return false because never remove a wheel
     */
    @Override
    public boolean canBeRemoved(){
        return false;
    }

    /**
     * A rebel wheel has a red frame, so it can be told apart on the screen.
     *
     * @return the CSS name of the color of the frame
     */
    @Override
    protected String colorMarco()
    {
        return "red";
    }
}
