import java.util.ArrayList;
/**
 * Write a description of class Vertex here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Vertex
{
    ArrayList edges;
    ArrayList<Meeple> meeples;
    Cities name;

    public Vertex(Cities name)
    {
        this.name = name;
        meeples = new ArrayList<>();
    }
    
    public void addMeeple(Meeple m)
    {
        meeples.add(m);
    }
    
    public int getNumMeeples()
    {
        return meeples.size();
    }
    
    public Cities getName()
    {
        return name;
    }
    
    public void addEdge(Edge e)
    {
        edges.add(e);
    }
}
