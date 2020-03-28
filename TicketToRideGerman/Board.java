import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Stack;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board extends JPanel implements MouseListener
{
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Deck deck;
    MeepleGroup bMeeples;
    int curPlays;
    Stack discard;
    TAndMcolor[] cols;
    Image image;
    Track [] tracks;
    int arrayCount;

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }

    protected static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//for now

        String[] choices = {"2", "3", "4", "5"};
        JComboBox<String> cb = new JComboBox<String>(choices);
        // f.add(cb);

        int s = JOptionPane.showOptionDialog(new JFrame(), cb, 
                "Please Select a Number of Players", JOptionPane.YES_NO_OPTION,
                JOptionPane.DEFAULT_OPTION, null, new Object[] 
                { "OK", "Close" }, JOptionPane.CLOSED_OPTION);

        if (s != 0) {
            System.exit(s);
        }

        String level = (String) cb.getSelectedItem();

        f.setTitle("Ticket to Ride: Germany");
        Board b = new Board();
        f.getContentPane().add(b);
        f.setResizable(false);
        f.setSize(1000,950);

        f.setVisible(true);

    }

    private void loadImage() {

        try {
            URL url = getClass().getResource("board.jpg");
            image = ImageIO.read(url);
            repaint();
        } catch (MalformedURLException mue) {
            System.err.println(mue.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public Board()
    {
        player1 = new Player();
        player2 = new Player();
        if(curPlays > 2)
        {
            player3 = new Player();
            if(curPlays > 3)
            {
                player4 = new Player(); 
                if(curPlays == 5)
                {
                    player5 = new Player();
                }
            }
        }
        deck = new Deck();
        discard = new Stack();
        bMeeples = new MeepleGroup(false);
        loadImage();
        addMouseListener(this);
        setTrack();
        cols = new TAndMcolor[6];
        cols[0] = TAndMcolor.RED;
        cols[1] = TAndMcolor.WHITE;
        cols[2] = TAndMcolor.YELLOW;
        cols[3] = TAndMcolor.PURPLE;
        cols[4] = TAndMcolor.BLACK;
        cols[5] = TAndMcolor.GREEN;
    }

    private String pickPlayersName(String args[])
    {
        System.out.println("Please Enter a name: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        return name;
    }

    private TAndMcolor pickPlayersColor()
    {
        System.out.println("Please pick a color :\n Must be one of the following");  
        String[] choices = new String[6];
        for(int i = 0; i < 6; i++)
        {
            if(cols[i] != null)
            {
                choices[i] = "" + cols[i];
            }
        }
        //return TAndMcolor.RED;
        
        
        
        JComboBox<String> cb = new JComboBox<String>(choices);
        // f.add(cb);

        int s = JOptionPane.showOptionDialog(new JFrame(), cb, 
                "Please Select a Color", JOptionPane.YES_NO_OPTION,
                JOptionPane.DEFAULT_OPTION, null, new Object[] 
                { "OK", "Close" }, JOptionPane.CLOSED_OPTION);

        if (s != 0) {
            System.exit(s);
        }

        String level = (String) cb.getSelectedItem();
        return TAndMcolor.YELLOW;
    }

    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(image,0,0,800,800,this);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {

        //System.out.println("The X:" + e.getX()+ ",Y: " + e.getY() + "counter :" + counterr);
        //System.out.println(e.getX()+ " " + e.getY() + " counter:" + counterr); 
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {

    }

    private void setTrack()
    {
        // Initialize the tracks
        arrayCount = 0;
        tracks = new Track [313];

        //reads in file data
        Scanner inFile = null;
        // Green
        File filename = new File("green");
        setArray(filename,arrayCount,Ccolor.GREEN);
        // Orange
        filename = new File("orange.txt");
        setArray(filename,arrayCount,Ccolor.ORANGE);
        // Blue
        filename = new File("blue");
        setArray(filename,arrayCount,Ccolor.BLUE);
        // Red
        filename = new File("red");
        setArray(filename,arrayCount,Ccolor.RED);
        // Black
        filename = new File("black");
        setArray(filename,arrayCount,Ccolor.BLACK);
        // Yellow
        filename = new File("yellow");
        setArray(filename,arrayCount,Ccolor.YELLOW);
        // Purple
        filename = new File("purple");
        setArray(filename,arrayCount,Ccolor.PURPLE);
        // White
        filename = new File("white");
        setArray(filename,arrayCount,Ccolor.WHITE);
        // Grey
        filename = new File("grey");
        setArray(filename,arrayCount,Ccolor.GREY);

    }

    private void setArray(File f,int counter,Ccolor c)
    {
        Scanner inFile = null;

        try { inFile = new Scanner(f); }
        catch (FileNotFoundException e) 
        {System.out.println("Cannot open "+f+" does it exist in this project directory?");
            return;
        }
        int check = 0;
        while(inFile.hasNextLine()){
            if(c == Ccolor.BLACK && check == 28)
            {
                return;
            }
            String line = inFile.nextLine();

            StringTokenizer st = new StringTokenizer(line);
            int x = Integer.parseInt(st.nextToken()); 
            int y = Integer.parseInt(st.nextToken());            
            tracks[counter] = new Track(x,y, c);
            arrayCount++;
            check++;
            counter++;

        }
    }
}

