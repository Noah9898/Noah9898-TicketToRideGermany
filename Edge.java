import java.awt.Color;
/**
 * Write a description of class Edge here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Edge
{
    Colors color;
    int length;
    Player owner;
    int src;
    int dst;
    
    public Edge(Colors color, int length, int src, int dst)
    {
        this.color = color;
        this.length = length;
        this.src = src;
        this.dst = dst;
    }
    
    public void setOwner(Player owner)
    {
        this.owner = owner;
    }
}
