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
public class Board extends JPanel implements MouseListener, MouseMotionListener
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
    //Colors[] cols;
    ArrayList<Colors> colors = new ArrayList<Colors>();
    Image image;
    Image background, intro;
    Image beginningImage;
    Track [] tracks;
    int arrayCount;
    int imageWidth, imageHeight;
    String temp = "";
    boolean hover = false;
    int xHov,yHov;
    boolean startClick = false;
    //MusicLoopPlayer loop;
    static JButton start = new JButton();
    static JFrame f = new JFrame();
    Image i1,i2,i3,i4,i5,i6,i7,i8,i9,back;
    Image[] images = new Image[10];
    Vertex[] vertices = new Vertex[40];
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

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//for now
        start.setBounds(380,560,300,65);

        f.add(start);
        f.setTitle("Ticket to Ride: Germany");
        Board b = new Board();
        f.getContentPane().add(b);
        f.setResizable(false);
        f.setSize(1000,950);

        f.setVisible(true);
    }

    private void loadImage() {
        try {
            URL url = getClass().getResource("images/board.jpg");

            image = ImageIO.read(url);
            url = getClass().getResource("images/background.jpg");
            background = ImageIO.read(url);

        } 
        catch (MalformedURLException mue) {
            System.err.println(mue.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public void introS(ActionEvent e){
        //loop.stop();
        //loop.music();
        startClick = true;

        repaint();
    }

    public Board()
    {
        loadBeg();
        /*
         * re-add this later
         */
        start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    introS(e);

                    colors.add(Colors.RED);
                    colors.add(Colors.WHITE);
                    colors.add(Colors.YELLOW);
                    colors.add(Colors.PURPLE);
                    colors.add(Colors.BLACK);
                    colors.add(Colors.GREEN);

                    f.getContentPane().remove(start);
                    String[] choices = {"2", "3", "4", "5"};
                    JComboBox<String> cb = new JComboBox<String>(choices);

                    int s = JOptionPane.showOptionDialog(new JFrame(), cb, 
                            "Number of Players", JOptionPane.YES_NO_OPTION,
                            JOptionPane.DEFAULT_OPTION, null, new Object[] 
                            { "OK", "Close" }, JOptionPane.CLOSED_OPTION);

                    if (s != 0) {
                        System.exit(s);
                    }

                    String level = (String) cb.getSelectedItem();

                    player1 = new Player(pickPlayersName(), pickPlayersColor());
                    player2 = new Player(pickPlayersName(), pickPlayersColor());
                    if(Integer.parseInt(level) > 2)
                    {
                        player3 = new Player(pickPlayersName(), pickPlayersColor());
                        if(Integer.parseInt(level) > 3)
                        {
                            player4 = new Player(pickPlayersName(), pickPlayersColor()); 
                            if(Integer.parseInt(level) == 5)
                            {
                                player5 = new Player(pickPlayersName(), pickPlayersColor());
                            }
                        }
                    }
                }
            });
        start.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        start.setOpaque(true);

        start.setContentAreaFilled(false);
        start.setBorderPainted(false);

        deck = new Deck();
        discard = new Stack();
        bMeeples = new MeepleGroup();
        loadImage();
        addMouseListener(this);
        addMouseMotionListener(this);
        imageWidth = image.getWidth(this);
        imageHeight = image.getHeight(this);
        setTrack();
        //loop = new MusicLoopPlayer();
        //loop.intro();
        loadBeg();
        loadImages();
        
        
    }

    public void loadImages(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(this);
        Image i1 = toolkit.getImage("images/RainbowCard.jpg");
        tracker.addImage(i1, 0);
        Image i2 = toolkit.getImage("images/RedCard.jpg");
        tracker.addImage(i2, 0);
        Image i3 = toolkit.getImage("images/YellowCard.jpg");
        tracker.addImage(i3, 0);
        Image i4 = toolkit.getImage("images/GreenCard.jpg");
        tracker.addImage(i4, 0);
        Image i5 = toolkit.getImage("images/WhiteCard.jpg");
        tracker.addImage(i5, 0);
        Image i6 = toolkit.getImage("images/PurpleCard.jpg");
        tracker.addImage(i6, 0);
        Image i7 = toolkit.getImage("images/OrangeCard.jpg");
        tracker.addImage(i7, 0);
        Image i8 = toolkit.getImage("images/BlackCard.jpg");
        tracker.addImage(i8, 0);
        Image i9 = toolkit.getImage("images/BlueCard.jpg");
        tracker.addImage(i9, 0);
        Image back = toolkit.getImage("images/TrainCardBack.jpg");
        tracker.addImage(back, 0);
        images[0] = i1;
        images[1] = i2;
        images[2] = i3;
        images[3] = i4;
        images[4] = i5;
        images[5] = i6;
        images[6] = i7;
        images[7] = i8;
        images[8] = i9;
        images[9] = back;
        try{
            tracker.waitForAll();
        }
        catch (InterruptedException e) {}
    }
    
    public String pickPlayersName()
    {
        String s = JOptionPane.showInputDialog(new JFrame(), "Please input a name", JOptionPane.YES_NO_OPTION);
        return s;
    }

    public Colors pickPlayersColor()
    {        
        JComboBox<Colors> cb = new JComboBox<Colors>();

        cb.setModel(new DefaultComboBoxModel(colors.toArray()));

        int s = JOptionPane.showOptionDialog(new JFrame(), cb, 
                "Please Select a Color", JOptionPane.YES_NO_OPTION,
                JOptionPane.DEFAULT_OPTION, null, new Object[] 
                { "OK", "Close" }, JOptionPane.CLOSED_OPTION);

        if (s != 0) {
            System.exit(s);
        }

        Colors color = (Colors) cb.getSelectedItem();

        for(int i =0; i < colors.size(); i++){
            if(colors.get(i).equals(color)){
                colors.remove(i);   
            }
        }

        return color;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint
        (RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        // draws the board
        int w = getWidth();
        int h = getHeight();

        int x = (w - imageWidth) / 2;
        int y = (h - imageHeight) / 2;
        g2.drawImage(background,0,0,getWidth(),getHeight(),this);
        
        g2.drawImage(image, 0, 0, this);
        g2.setColor(Color.WHITE);
        g2.fillRect(525,50,450,350);
        g2.setColor(Color.RED);
        g2.drawString("Face up Cards here", 600, 125);
        g2.drawImage(images[0], 30,30,this);

        if(hover){
            g2.setColor(Color.WHITE);
            g2.fillRect(xHov,yHov,150,50);
            g2.setColor(Color.BLACK);
            g2.drawRect(xHov,yHov,150,50);
            g2.drawString(temp,xHov+30, yHov+32);
            hover = false;
            temp = "";
        }

        if(!startClick){
            g2.drawImage(intro,0,0,getWidth(),getHeight(),this);
        }
     
    }
    
    public void setBoard()
    {
        vertices[0] = new Vertex(Cities.DENMARK);
        vertices[0].addEdge(new Edge(Colors.GREEN,5,0,2));
        vertices[0].addEdge(new Edge(Colors.GRAY,2,0,3));
        
        vertices[1] = new Vertex(Cities.EMDEN);
        vertices[1].addEdge(new Edge(Colors.GRAY,3,1,2));
        vertices[1].addEdge(new Edge(Colors.BLUE,3,1,7));
        vertices[1].addEdge(new Edge(Colors.RED,4,1,9));
        vertices[1].addEdge(new Edge(Colors.WHITE,2,1,8));
        
        vertices[2] = new Vertex(Cities.BREMERHAVEN);
        vertices[2].addEdge(new Edge(Colors.GRAY,3,2,1));
        vertices[2].addEdge(new Edge(Colors.GREEN,5,2,0));
        vertices[2].addEdge(new Edge(Colors.GRAY,3,2,3));
        vertices[2].addEdge(new Edge(Colors.GRAY,3,2,5));
        vertices[2].addEdge(new Edge(Colors.WHITE,1,2,7));
        
        vertices[3] = new Vertex(Cities.KIEL);
        vertices[3].addEdge(new Edge(Colors.GRAY,4,3,2));
        vertices[3].addEdge(new Edge(Colors.GRAY,2,3,0));
        vertices[3].addEdge(new Edge(Colors.ORANGE,4,3,4));
        vertices[3].addEdge(new Edge(Colors.YELLOW,3,3,6));
        vertices[3].addEdge(new Edge(Colors.PURPLE,2,3,5));
        vertices[3].addEdge(new Edge(Colors.BLACK,2,3,5));
        
        vertices[4] = new Vertex(Cities.ROSTOCK);
        vertices[4].addEdge(new Edge(Colors.ORANGE,4,4,3));
        vertices[4].addEdge(new Edge(Colors.RED,2,4,6));
        vertices[4].addEdge(new Edge(Colors.PURPLE,6,4,12));
        
        vertices[5] = new Vertex(Cities.HAMBURG);
        vertices[5].addEdge(new Edge(Colors.GRAY,3,5,2));
        vertices[5].addEdge(new Edge(Colors.BLACK,2,5,3));
        vertices[5].addEdge(new Edge(Colors.PURPLE,2,5,3));
        vertices[5].addEdge(new Edge(Colors.GREEN,2,5,6));
        vertices[5].addEdge(new Edge(Colors.BLUE,7,5,12));
        vertices[5].addEdge(new Edge(Colors.YELLOW,7,5,12));
        vertices[5].addEdge(new Edge(Colors.WHITE,4,5,10));
        vertices[5].addEdge(new Edge(Colors.RED,4,5,10));
        vertices[5].addEdge(new Edge(Colors.ORANGE,3,5,7));
        
        vertices[6] = new Vertex(Cities.SCHWERIN);
        vertices[6].addEdge(new Edge(Colors.GREEN,2,6,5));
        vertices[6].addEdge(new Edge(Colors.YELLOW,3,6,3));
        vertices[6].addEdge(new Edge(Colors.RED,2,6,4));
        vertices[6].addEdge(new Edge(Colors.WHITE,5,6,12));
        
        vertices[7] = new Vertex(Cities.BREMEN);
        vertices[7].addEdge(new Edge(Colors.WHITE,1,7,2));
        vertices[7].addEdge(new Edge(Colors.ORANGE,3,7,5));
        vertices[7].addEdge(new Edge(Colors.YELLOW,3,7,10));
        vertices[7].addEdge(new Edge(Colors.BLACK,3,7,9));
        vertices[7].addEdge(new Edge(Colors.BLUE,3,7,1));
        
        vertices[8] = new Vertex(Cities.LUXEMBORG);
        vertices[8].addEdge(new Edge(Colors.WHITE,2,8,2));
        vertices[8].addEdge(new Edge(Colors.ORANGE,2,8,9));
        vertices[8].addEdge(new Edge(Colors.PURPLE,3,8,13));
        
        vertices[9] = new Vertex(Cities.MUNSTER);
        vertices[9].addEdge(new Edge(Colors.ORANGE,2,9,8));
        vertices[9].addEdge(new Edge(Colors.RED,4,9,1));
        vertices[9].addEdge(new Edge(Colors.BLACK,3,9,7));
        vertices[9].addEdge(new Edge(Colors.PURPLE,4,9,10));
        vertices[9].addEdge(new Edge(Colors.GRAY,1,9,14));
        
        vertices[10] = new Vertex(Cities.HANNOVER);
        vertices[10].addEdge(new Edge(Colors.PURPLE,4,10,9));
        vertices[10].addEdge(new Edge(Colors.YELLOW,3,10,7));
        vertices[10].addEdge(new Edge(Colors.RED,4,10,5));
        vertices[10].addEdge(new Edge(Colors.WHITE,4,10,5));
        vertices[10].addEdge(new Edge(Colors.BLACK,7,10,12));
        vertices[10].addEdge(new Edge(Colors.BLUE,4,10,11));
        vertices[10].addEdge(new Edge(Colors.GREEN,5,10,16));
        vertices[10].addEdge(new Edge(Colors.ORANGE,5,10,16));
        vertices[10].addEdge(new Edge(Colors.GRAY,3,10,15));
        
        vertices[11] = new Vertex(Cities.MAGDEBURG);
        vertices[11].addEdge(new Edge(Colors.BLUE,4,11,10));
        vertices[11].addEdge(new Edge(Colors.RED,3,11,12));
        vertices[11].addEdge(new Edge(Colors.YELLOW,2,11,17));
        
        vertices[12] = new Vertex(Cities.BERLIN);
        vertices[12].addEdge(new Edge(Colors.PURPLE,6,12,4));
        vertices[12].addEdge(new Edge(Colors.WHITE,5,12,6));
        vertices[12].addEdge(new Edge(Colors.BLUE,7,12,5));
        vertices[12].addEdge(new Edge(Colors.YELLOW,7,12,5));
        vertices[12].addEdge(new Edge(Colors.BLACK,7,12,10));
        vertices[12].addEdge(new Edge(Colors.RED,3,12,11));
        vertices[12].addEdge(new Edge(Colors.ORANGE,4,12,17));
        vertices[12].addEdge(new Edge(Colors.GREEN,5,12,19));
        
        vertices[13] = new Vertex(Cities.DUSSELDORF);
        vertices[13].addEdge(new Edge(Colors.PURPLE,3,13,8));
        vertices[13].addEdge(new Edge(Colors.GRAY,1,13,14));
        vertices[13].addEdge(new Edge(Colors.GRAY,1,13,20));
        
        vertices[14] = new Vertex(Cities.DORTMUND);
        vertices[14].addEdge(new Edge(Colors.GRAY,1,14,13));
        vertices[14].addEdge(new Edge(Colors.GRAY,1,14,9));
        vertices[14].addEdge(new Edge(Colors.GREEN,4,14,15));
        
        vertices[15] = new Vertex(Cities.KASSEL);
        vertices[15].addEdge(new Edge(Colors.GREEN,4,15,14));
        vertices[15].addEdge(new Edge(Colors.GRAY,3,15,10));
        vertices[15].addEdge(new Edge(Colors.GRAY,3,15,16));
        vertices[15].addEdge(new Edge(Colors.WHITE,4,15,22));
        vertices[15].addEdge(new Edge(Colors.BLUE,4,15,22));
        
        vertices[16] = new Vertex(Cities.ERFURT);
        vertices[16].addEdge(new Edge(Colors.GRAY,3,16,15));
        vertices[16].addEdge(new Edge(Colors.ORANGE,5,16,10));
        vertices[16].addEdge(new Edge(Colors.GREEN,5,16,10));
        vertices[16].addEdge(new Edge(Colors.RED,3,16,17));
        vertices[16].addEdge(new Edge(Colors.BLACK,4,16,18));
        vertices[16].addEdge(new Edge(Colors.WHITE,7,16,33));
        vertices[16].addEdge(new Edge(Colors.PURPLE,4,16,24));
        vertices[16].addEdge(new Edge(Colors.YELLOW,4,16,24));
        
        vertices[17] = new Vertex(Cities.LEIPZIG);
        vertices[17].addEdge(new Edge(Colors.RED,3,17,16));
        vertices[17].addEdge(new Edge(Colors.YELLOW,2,17,11));
        vertices[17].addEdge(new Edge(Colors.ORANGE,4,17,12));
        vertices[17].addEdge(new Edge(Colors.BLACK,3,17,19));
        vertices[17].addEdge(new Edge(Colors.BLUE,2,17,18));
        
        vertices[18] = new Vertex(Cities.CHEMNITZ);
        vertices[18].addEdge(new Edge(Colors.BLACK,4,18,16));
        vertices[18].addEdge(new Edge(Colors.BLUE,2,18,17));
        vertices[18].addEdge(new Edge(Colors.YELLOW,1,18,19));
        vertices[18].addEdge(new Edge(Colors.PURPLE,6,18,33));
        
        vertices[19] = new Vertex(Cities.DRESDEN);
        vertices[19].addEdge(new Edge(Colors.RED,7,19,33));
        vertices[19].addEdge(new Edge(Colors.YELLOW,1,19,18));
        vertices[19].addEdge(new Edge(Colors.BLACK,3,19,17));
        vertices[19].addEdge(new Edge(Colors.GREEN,5,19,12));
        
        vertices[20] = new Vertex(Cities.KOLN);
        vertices[20].addEdge(new Edge(Colors.GRAY,1,20,13));
        vertices[20].addEdge(new Edge(Colors.GRAY,4,20,22));
        vertices[20].addEdge(new Edge(Colors.GRAY,1,20,21));
        
        vertices[21] = new Vertex(Cities.KOBLENZ);
        vertices[21].addEdge(new Edge(Colors.GRAY,1,21,20));
        vertices[21].addEdge(new Edge(Colors.GRAY,2,21,25));
        vertices[21].addEdge(new Edge(Colors.GRAY,3,21,26));
        
        vertices[22] = new Vertex(Cities.FRANKFURT);
        vertices[22].addEdge(new Edge(Colors.GRAY,4,22,20));
        vertices[22].addEdge(new Edge(Colors.BLUE,4,22,15));
        vertices[22].addEdge(new Edge(Colors.WHITE,4,22,15));
        vertices[22].addEdge(new Edge(Colors.GRAY,2,22,23));
        vertices[22].addEdge(new Edge(Colors.GRAY,2,22,27));
        vertices[22].addEdge(new Edge(Colors.GRAY,1,22,25));
        
        vertices[23] = new Vertex(Cities.WURZBURG);
        vertices[23].addEdge(new Edge(Colors.GRAY,2,23,22));
        vertices[23].addEdge(new Edge(Colors.GRAY,2,23,24));
        
        vertices[24] = new Vertex(Cities.NURNBERG);
        vertices[24].addEdge(new Edge(Colors.GRAY,2,24,23));
        vertices[24].addEdge(new Edge(Colors.YELLOW,4,24,16));
        vertices[24].addEdge(new Edge(Colors.PURPLE,4,24,16));
        vertices[24].addEdge(new Edge(Colors.GREEN,3,24,33));
        vertices[24].addEdge(new Edge(Colors.BLACK,5,24,32));
        vertices[24].addEdge(new Edge(Colors.BLUE,5,24,32));
        vertices[24].addEdge(new Edge(Colors.ORANGE,4,24,31));
        
        vertices[25] = new Vertex(Cities.MAINZ);
        vertices[25].addEdge(new Edge(Colors.GRAY,1,25,27));
        vertices[25].addEdge(new Edge(Colors.GRAY,1,25,22));
        vertices[25].addEdge(new Edge(Colors.GRAY,2,25,21));
        vertices[25].addEdge(new Edge(Colors.GRAY,3,25,26));
        
        vertices[26] = new Vertex(Cities.SAARBRUCKEN);
        vertices[26].addEdge(new Edge(Colors.GRAY,3,26,21));
        vertices[26].addEdge(new Edge(Colors.GRAY,3,26,25));
        vertices[26].addEdge(new Edge(Colors.GRAY,3,26,27));
        vertices[26].addEdge(new Edge(Colors.GRAY,3,26,28));
        vertices[26].addEdge(new Edge(Colors.GREEN,1,26,34));
        
        vertices[27] = new Vertex(Cities.MANNHEIM);
        vertices[27].addEdge(new Edge(Colors.GRAY,3,27,26));
        vertices[27].addEdge(new Edge(Colors.GRAY,1,27,25));
        vertices[27].addEdge(new Edge(Colors.GRAY,2,27,22));
        vertices[27].addEdge(new Edge(Colors.GRAY,2,27,29));
        vertices[27].addEdge(new Edge(Colors.BLUE,1,27,28));
        
        vertices[28] = new Vertex(Cities.KARLSRUHE);
        vertices[28].addEdge(new Edge(Colors.BLUE,1,28,27));
        vertices[28].addEdge(new Edge(Colors.PURPLE,1,28,29));
        vertices[28].addEdge(new Edge(Colors.WHITE,3,28,35));
        vertices[28].addEdge(new Edge(Colors.BLACK,2,28,34));
        vertices[28].addEdge(new Edge(Colors.GRAY,3,28,26));
        
        vertices[29] = new Vertex(Cities.STUTTGART);
        vertices[29].addEdge(new Edge(Colors.GRAY,2,29,27));
        vertices[29].addEdge(new Edge(Colors.GRAY,2,29,30));
        vertices[29].addEdge(new Edge(Colors.GREEN,3,29,37));
        vertices[29].addEdge(new Edge(Colors.GRAY,3,29,35));
        vertices[29].addEdge(new Edge(Colors.PURPLE,1,29,28));
        
        vertices[30] = new Vertex(Cities.ULM);
        vertices[30].addEdge(new Edge(Colors.GRAY,2,30,29));
        vertices[30].addEdge(new Edge(Colors.GRAY,2,30,31));
        vertices[30].addEdge(new Edge(Colors.RED,2,30,38));
        
        vertices[31] = new Vertex(Cities.AUGSBURG);
        vertices[31].addEdge(new Edge(Colors.GRAY,1,31,30));
        vertices[31].addEdge(new Edge(Colors.ORANGE,4,31,24));
        vertices[31].addEdge(new Edge(Colors.GRAY,2,31,32));
        
        vertices[32] = new Vertex(Cities.MUNCHEN);
        vertices[32].addEdge(new Edge(Colors.GRAY,2,32,31));
        vertices[32].addEdge(new Edge(Colors.BLUE,5,32,24));
        vertices[32].addEdge(new Edge(Colors.BLACK,5,32,24));
        vertices[32].addEdge(new Edge(Colors.ORANGE,3,32,33));
        vertices[32].addEdge(new Edge(Colors.RED,3,32,39));
        vertices[32].addEdge(new Edge(Colors.GRAY,5,32,38));
        
        vertices[33] = new Vertex(Cities.REGENSBURG);
        vertices[33].addEdge(new Edge(Colors.GREEN,3,33,24));
        vertices[33].addEdge(new Edge(Colors.WHITE,7,33,16));
        vertices[33].addEdge(new Edge(Colors.PURPLE,6,33,18));
        vertices[33].addEdge(new Edge(Colors.RED,7,33,19));
        vertices[33].addEdge(new Edge(Colors.YELLOW,4,33,39));
        vertices[33].addEdge(new Edge(Colors.ORANGE,3,33,32));
        
        vertices[34] = new Vertex(Cities.FRANCE);
        vertices[34].addEdge(new Edge(Colors.GREEN,1,34,26));
        vertices[34].addEdge(new Edge(Colors.BLACK,2,34,28));
        vertices[34].addEdge(new Edge(Colors.YELLOW,2,34,35));
        
        vertices[35] = new Vertex(Cities.FREIBURG);
        vertices[35].addEdge(new Edge(Colors.YELLOW,2,35,34));
        vertices[35].addEdge(new Edge(Colors.WHITE,3,35,28));
        vertices[35].addEdge(new Edge(Colors.GRAY,3,35,29));
        vertices[35].addEdge(new Edge(Colors.BLACK,2,35,37));
        vertices[35].addEdge(new Edge(Colors.ORANGE,2,35,36));
        
        vertices[36] = new Vertex(Cities.SWITZERLAND);
        vertices[36].addEdge(new Edge(Colors.ORANGE,2,36,35));
        vertices[36].addEdge(new Edge(Colors.WHITE,1,36,37));
        vertices[36].addEdge(new Edge(Colors.BLUE,2,36,38));
        
        vertices[37] = new Vertex(Cities.KONSTANZ);
        vertices[37].addEdge(new Edge(Colors.BLACK,2,37,35));
        vertices[37].addEdge(new Edge(Colors.GREEN,3,37,29));
        vertices[37].addEdge(new Edge(Colors.YELLOW,1,37,38));
        vertices[37].addEdge(new Edge(Colors.WHITE,1,37,36));
        
        vertices[38] = new Vertex(Cities.LINDAU);
        vertices[38].addEdge(new Edge(Colors.YELLOW,1,38,37));
        vertices[38].addEdge(new Edge(Colors.RED,2,38,30));
        vertices[38].addEdge(new Edge(Colors.GRAY,5,38,32));
        vertices[38].addEdge(new Edge(Colors.PURPLE,2,38,39));
        vertices[38].addEdge(new Edge(Colors.BLUE,2,38,36));
        
        vertices[39] = new Vertex(Cities.AUSTRIA);
        vertices[39].addEdge(new Edge(Colors.PURPLE,2,39,38));
        vertices[39].addEdge(new Edge(Colors.RED,3,39,32));
        vertices[39].addEdge(new Edge(Colors.YELLOW,4,39,33));
        
        
    }
    /////////////////////////////////////////////////////////////////////////temp////////////
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        // System.out.println("XCoord: "+e.getX() + "YCoord: "+ e.getY());
    }

    public void mouseMoved(MouseEvent e){
        xHov = e.getX();
        yHov = e.getY();

        if((xHov<=94 && xHov >=74) && (yHov<=179 && yHov >= 159))
        {
            temp = "Emden";
        }
        else if((xHov<=175 && xHov >=155) && (yHov<=166 && yHov >= 146))
        {
            temp = "Bremerhaven";
        }
        else if((xHov<=179 && xHov >=159) && (yHov<=210 && yHov >= 190))
        {
            temp = "Bremer";
        }
        else if((xHov<=117 && xHov >=97) && (yHov<=297 && yHov >= 277))
        {
            temp = "Münster";
        }
        else if((xHov<=113 && xHov >=93) && (yHov<=344 && yHov >= 324))
        {
            temp = "Dortmund";
        }
        else if((xHov<=72 && xHov >=52) && (yHov<=365 && yHov >= 345))
        {
            temp = "Düsseldorf";
        }
        else if((xHov<=68 && xHov >=48) && (yHov<=410 && yHov >= 390))
        {
            temp = "Köln";
        }
        else if((xHov<=78 && xHov >=58) && (yHov<=460 && yHov >= 440))
        {
            temp = "Kolbenz";
        }
        else if((xHov<=135 && xHov >=115) && (yHov<=500 && yHov >= 480))
        {
            temp = "Mainz";
        }
        else if((xHov<=175 && xHov >=155) && (yHov<=475 && yHov >= 455))
        {
            temp = "Frankfurt";
        }
        else if((xHov<=153 && xHov >=133) && (yHov<=542 && yHov >= 522))
        {
            temp = "Mannheim";
        }
        else if((xHov<=159 && xHov >=129) && (yHov<=584 && yHov >= 564))
        {
            temp = "Kalsruhe";
        }
        else if((xHov<=57 && xHov >=37) && (yHov<=554 && yHov >= 534))
        {
            temp = "Saarbrücken";
        }
        else if((xHov<=242 && xHov >=222) && (yHov<=497 && yHov >= 477))
        {
            temp = "Würzburg";
        }
        else if((xHov<=198 && xHov >=178) && (yHov<=596 && yHov >= 576))
        {
            temp = "Stuttgart";
        }
        else if((xHov<=124 && xHov >=104) && (yHov<=676 && yHov >= 656))
        {
            temp = "Frieburg";
        }
        else if((xHov<=195 && xHov >175) && (yHov<=690 && yHov >= 670))
        {
            temp = "Konstanz";
        }
        else if((xHov<=237 && xHov >=217) && (yHov<=699 && yHov >= 679))
        {
            temp = "Lindau";
        }
        else if((xHov<=254 && xHov >=234) && (yHov<=634 && yHov >= 614))
        {
            temp = "Ulm";
        }
        else if((xHov<=299 && xHov >=279) && (yHov<=639 && yHov >= 619))
        {
            temp = "Ausburg";
        }
        else if((xHov<=367 && xHov >=347) && (yHov<=654 && yHov >= 634))
        {
            temp = "Munchen";
        }   
        else if((xHov<=400 && xHov >=380) && (yHov<=572 && yHov >= 552))
        {
            temp = "Regensburg";
        }
        else if((xHov<=312 && xHov >=292) && (yHov<=520 && yHov >= 500))
        {
            temp = "Nürnberg";
        }
        else if((xHov<=228 && xHov >=208) && (yHov<=372 && yHov >= 352))
        {
            temp = "Kassel";
        }
        else if((xHov<=309 && xHov >=289) && (yHov<=402 && yHov >= 382))
        {
            temp = "Erfurt";
        }
        else if((xHov<=385 && xHov >=365) && (yHov<=360 && yHov >= 340))
        {
            temp = "Leipzig";
        }
        else if((xHov<=430 && xHov >=410) && (yHov<=413 && yHov >= 393))
        {
            temp = "Chemnitz";
        }
        else if((xHov<=472 && xHov >=452) && (yHov<=394 && yHov >= 374))
        {
            temp = "Dresden";
        }
        else if((xHov<=355 && xHov >=335) && (yHov<=296 && yHov >= 276))
        {
            temp = "Magdeburg";
        }
        else if((xHov<=441 && xHov >=421) && (yHov<=254 && yHov >= 234))
        {
            temp = "Berlin";
        }
        else if((xHov<=236 && xHov >=216) && (yHov<=279 && yHov >= 259))
        {
            temp = "Hannover";
        }
        else if((xHov<=253 && xHov >=233) && (yHov<=98 && yHov >= 78))
        {
            temp = "Kiel";
        }
        else if((xHov<=264 && xHov >=244) && (yHov<=168 && yHov >= 148))
        {
            temp = "Hamburg";
        }
        else if((xHov<=330 && xHov >=310) && (yHov<=158 && yHov >= 138))
        {
            temp = "Schwerin";
        }
        else if((xHov<=370 && xHov >=350) && (yHov<=101 && yHov >= 81))
        {
            temp = "Restock";
        }     
        if(!temp.equals("")){
            hover = true;   
        }
        repaint();
    }

    public void mouseDragged(MouseEvent e) {}

    private void setTrack()
    {
        // Initialize the tracks
        arrayCount = 0;
        tracks = new Track [313];

        //reads in file data
        Scanner inFile = null;
        // Green
        File filename = new File("green");
        setArray(filename,arrayCount,Colors.GREEN);
        // Orange
        filename = new File("orange.txt");
        setArray(filename,arrayCount,Colors.ORANGE);
        // Blue
        filename = new File("blue");
        setArray(filename,arrayCount,Colors.BLUE);
        // Red
        filename = new File("red");
        setArray(filename,arrayCount,Colors.RED);
        // Black
        filename = new File("black");
        setArray(filename,arrayCount,Colors.BLACK);
        // Yellow
        filename = new File("yellow");
        setArray(filename,arrayCount,Colors.YELLOW);
        // Purple
        filename = new File("purple");
        setArray(filename,arrayCount,Colors.PURPLE);
        // White
        filename = new File("white");
        setArray(filename,arrayCount,Colors.WHITE);
        // Grey
        filename = new File("grey");
        setArray(filename,arrayCount,Colors.GRAY);

    }

    public void loadBeg(){
        try {
            URL url = getClass().getResource("images/intro.png");
            intro = ImageIO.read(url);
        } 
        catch (MalformedURLException mue) {
            System.err.println(mue.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

    }

    private void setArray(File f,int counter,Colors c)
    {
        Scanner inFile = null;

        try { inFile = new Scanner(f); }
        catch (FileNotFoundException e) 
        {System.out.println("Cannot open "+f+" does it exist in this project directory?");
            return;
        }
        int check = 0;
        while(inFile.hasNextLine()){
            if(c == Colors.BLACK && check == 28)
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
