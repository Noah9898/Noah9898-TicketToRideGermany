import java.util.Stack;
import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
/**
 * Write a description of class deck here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Deck
{
    private final int totalCards = 110;
    private final int maxCards = 12;
    ArrayList<Card> deck = new ArrayList<Card>();
    Random r = new Random();
    public Deck()
    {
        int i = 0;
        deck.add(new Card(Colors.LOCO));
        while(i < 12)
        {
            deck.add(new Card(Colors.RED));
            deck.add(new Card(Colors.ORANGE));
            deck.add(new Card(Colors.YELLOW));
            deck.add(new Card(Colors.GREEN));
            deck.add(new Card(Colors.BLUE));
            deck.add(new Card(Colors.PURPLE));
            deck.add(new Card(Colors.BLACK));
            deck.add(new Card(Colors.WHITE));
            deck.add(new Card(Colors.LOCO));
            i++;
        }
        deck.add(new Card(Colors.LOCO));

        shuffle();
    }

    /**
     * Shuffles the cards in the deck
     */
    public void shuffle()
    {
        Collections.shuffle(deck);
    }

    
}
