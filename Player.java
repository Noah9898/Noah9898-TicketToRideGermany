import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    String name;
    Colors color;
    Hand hand;
    ArrayList<Meeple> playerMeep;
    int trainCount;

    public Player()
    {
        name = "Jimmy-Jo";
        color = Colors.PURPLE;
        hand = new Hand();
        playerMeep = new ArrayList<Meeple>();
        trainCount = 45;
    }

    public Player(String playerName, Colors col)
    {
        name = playerName;
        color = col;
        hand = new Hand();
        playerMeep = new ArrayList<Meeple>();
        trainCount = 45;
    }

    protected void addMeeple(Colors col){

        playerMeep.add(new Meeple(col));   

    }

    /**
     * Total number of red meeples for the player
     * 
     * @return the total number of red meeples the player has
     */
    protected int totalRedMeep(){
        int counter = 0;
        for(int i =0; i < playerMeep.size(); i++){
            if(playerMeep.get(i).getColor() == Colors.RED){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Total number of red meeples for the player
     * 
     * @return the total number of yellow meeples the player has
     */
    protected int totalYellowMeep(){
        int counter = 0;
        for(int i =0; i < playerMeep.size(); i++){
            if(playerMeep.get(i).getColor() == Colors.YELLOW){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Total number of red meeples for the player
     * 
     * @return the total number of black meeples the player has
     */
    protected int totalBlackMeep(){
        int counter = 0;
        for(int i =0; i < playerMeep.size(); i++){

            if(playerMeep.get(i).getColor() == Colors.BLACK){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Total number of green meeples for the player
     * 
     * @return int the total number of green meeples the player has
     */
    protected int totalGreenMeep(){
        int counter = 0;
        for(int i =0; i < playerMeep.size(); i++){

            if(playerMeep.get(i).getColor() == Colors.GREEN){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Total number of purple meeples for the player
     * 
     * @return int the total number of purple meeples the player has
     */
    protected int totalPurpleMeep(){
        int counter = 0;
        for(int i =0; i < playerMeep.size(); i++){

            if(playerMeep.get(i).getColor() == Colors.PURPLE){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Total number of white meeples for the player
     * 
     * @return int the total number of white meeples the player has
     */
    protected int totalWhiteMeep(){
        int counter = 0;
        for(int i =0; i < playerMeep.size(); i++){

            if(playerMeep.get(i).getColor() == Colors.WHITE){
                counter++;
            }
        }
        return counter;
    }
}
