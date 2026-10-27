
/**
 * Type of Wheel which copy the left Offset Wheel 
 * 
 * @author Juan Carreño - Juan Castaño 
 */
public class Lefty extends Wheel
{

    /**
     * Uses itses parent contructor
     * 
     * @param cinta the strip of symbols the wheel reads from
     */
    public Lefty(Cinta cinta)
    {
        super(cinta);
    }
    
    /**
     * Copy the Offset of the left wheel
     * 
     * @param izquierda the Wheel on the left, or null if its the first one
     * @return if the move is done 
     */
    @Override
    public boolean spin(Wheel izquierda){
        if (izquierda == null){
            return spin();
        }
        else if (isFixed()){
            return false;
        }
        placeAt(izquierda.getOffset());
        return true;
    }

    /**
     * A lefty wheel has a blue frame, so it can be told apart on the screen.
     *
     * @return the CSS name of the color of the frame
     */
    @Override
    protected String colorMarco()
    {
        return "dodgerblue";
    }
}
