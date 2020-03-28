
/**
 * Write a description of class Track here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Track
{
    // instance variables - replace the example below with your own
    Colors color;
    double x;
    double y;
    boolean status;

    /**
     * Constructor for objects of class Track
     */
    public Track()
    {
        // initialise instance variables
        x = 0.0;
        y = 0.0;
        color = null;
        status = false;
    }

    public Track(int xx, int yy,Colors c)
    {
        x = xx;
        y = yy;
        color = c;
        status = false;
    }

    public void ChangeStatus()
    {
        status = true;   
    }

    public double getX()
    {
        return x;   
    }

    public double getY()
    {
        return y;   
    }

    public boolean getStatus()
    {
        return status;   
    }

    public int addPoints(int trainLength){

        if(trainLength == 3){return 4;}
        else if(trainLength == 4){return 7;}
        else if(trainLength == 5){return 10;}
        else if(trainLength == 6){return 15;}
        else if(trainLength == 7){return 18;}
        return trainLength;   
    }
}
