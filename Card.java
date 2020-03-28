import java.awt.*;


/**
 * Write a description of class Card here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Card
{
    Colors color;

    Image cardImage;

    public Card(Image img)
    {
        color = Colors.RED;

        cardImage = img;
    }

    public Card(Colors c)
    {
        color = c;

    }

    

    public Colors getColor()
    {
        return color;
    }

    public void setImage(Image img){
        cardImage = img;
    }

    public void setColor(Color c){

    }
}
