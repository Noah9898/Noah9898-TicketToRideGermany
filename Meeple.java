
/**
 * Write a description of class Meople here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Meeple
{
    Colors color;

    public Meeple(Colors c)
    {
        color = c;
    }

    /**
     * How many meeples can be in the city in total
     * 
     * @param c the city to check
     * @return the total number of meeples allowed per city
     */
    public int totalMeeplePerCity(Cities c){
        switch(c){
            case LEIPZIG:
            STUTTGART:
            return 3;

            case FRANKFURT:
            KOLN:
            MUNCHEN:
            HAMBURG:
            return 4;

            case BERLIN:
            return 5;

            default:
            return 1;

        }

    }

    protected Colors getColor(){
        return color;   
    }
}
