import java.util.ArrayList;
/**
 * Write a description of class MeepleGroup here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MeepleGroup
{
    private final int MAXMEEPS = 60;
    ArrayList<Meeple> red;
    ArrayList<Meeple> black;
    ArrayList<Meeple> green;
    ArrayList<Meeple> yellow;
    ArrayList<Meeple> purple;
    ArrayList<Meeple> white;

    //ArrayList<Meeple> playerMeep;

    public MeepleGroup()
    {
        red    = new ArrayList<Meeple>();
        black  = new ArrayList<Meeple>();
        green  = new ArrayList<Meeple>();
        yellow = new ArrayList<Meeple>();
        purple = new ArrayList<Meeple>();
        white  = new ArrayList<Meeple>();

        for(int i =0 ; i < 10 ; i++){
            red.add(new Meeple(Colors.RED));
            black.add(new Meeple(Colors.BLACK));
            green.add(new Meeple(Colors.GREEN));
            yellow.add(new Meeple(Colors.YELLOW));
            purple.add(new Meeple(Colors.PURPLE));
            white.add(new Meeple(Colors.WHITE));
        }


    }


    protected int remainingRed(){
        return red.size();
    }

    protected int remainingBlack(){
        return black.size();
    }

    protected int remainingWhite(){
        return white.size();
    }

    protected int remainingPurp(){
        return purple.size();
    }

    protected int remainingGreen(){
        return green.size();   
    }

    protected int remainingYellow(){
        return yellow.size();   
    }

}
