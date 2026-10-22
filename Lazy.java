/**
 * A lazy wheel. When it is asked to spin, it does not spin by a random amount:
 * it just moves forward one place on the strip. Like any wheel, it does not
 * move while it is locked.
 *
 * It is the kind of wheel proposed by the group for requirement 19.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 21-10-2026
 */
public class Lazy extends Wheel
{
    /**
     * Makes a lazy wheel that looks at the given strip.
     *
     * @param cinta the strip of symbols the wheel reads from
     */
    public Lazy(Cinta cinta)
    {
        super(cinta);
    }

    /**
     * Moves forward just one place instead of spinning by a random amount.
     * Since spin(Wheel) of a wheel ends up calling this method, a lazy wheel
     * also moves just one place when the whole machine spins.
     *
     * @return true when the wheel moved, false when it is locked or the strip
     *         is empty
     */
    @Override
    public boolean spin()
    {
        return rotate(1);
    }
}
