import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

public class GraphicsManager implements Commons
{
	private ImageIcon ii;
	private Image img;
	private Dimension d;
	private final String expl = "explosion.jpg";
    private final String alienpix = "alien.gif";
    private final String bg = "stars.jpg";
	Color backgroundColor = Color.BLACK;
	private Board board;
	private int alienX = 150;
    private int alienY = 5;
	
	public GraphicsManager(Board board)
	{
		this.board = board;
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
		ii = new ImageIcon(this.getClass().getResource(alienpix));
		img = Toolkit.getDefaultToolkit().createImage("stars.jpg");
	}
	
	public ArrayList<Alien> initAliens()
	{
		ArrayList<Alien> aliens = new ArrayList<Alien>();
		
		for (int i=0; i < 4; i++) 
        {
            for (int j=0; j < 6; j++) 
            {
                Alien alien = new Alien(alienX + 25*j, alienY + 18*i, board.alienHP);
                alien.setImage(ii.getImage());
                aliens.add(alien);
            }
        }
        
        return aliens;
	}
	
	public void countdown(Graphics g) 
	{
		long init;
		board.SM.playCountdown();
		g.drawImage(img, 0, 0, null);
		drawAliens(g);
		Font small = new Font("Helvetica", Font.BOLD, 14);
	    g.setFont(small);
	    g.setColor(Color.GREEN);
	    g.drawLine(0, GROUND + 2, BOARD_WIDTH, GROUND + 2);
	    drawPlayer(g);
	    g.drawString("Coins: " + board.coins, 35, GROUND + 30);
	    g.drawString("LEVEL " + board.level , 150 , GROUND + 30);
	    g.drawString("Points: " + board.points , 250 , GROUND + 30);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica", Font.BOLD, 20));
		g.drawString(3 + "", BOARD_WIDTH / 2, BOARD_HEIGTH / 2);
		init = System.currentTimeMillis();
		while(System.currentTimeMillis() - init < 1000);
		g.drawImage(img, 0, 0, null);
		drawAliens(g);
	    g.setFont(small);
	    g.setColor(Color.GREEN);
	    g.drawLine(0, GROUND+2, BOARD_WIDTH, GROUND+2);
	    drawPlayer(g);
	    g.drawString("Coins: " + board.coins , 35 , GROUND + 30);
	    g.drawString("LEVEL " + board.level , 150 , GROUND + 30);
	    g.drawString("Points: " + board.points , 250 , GROUND + 30);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica", Font.BOLD, 20));
		g.drawString(2+"",BOARD_WIDTH/2,BOARD_HEIGTH/2);
		init = System.currentTimeMillis();
		while(System.currentTimeMillis() - init < 1000);
		g.drawImage(img, 0, 0, null);
		drawAliens(g);
	    g.setFont(small);
	    g.setColor(Color.GREEN);
	    g.drawLine(0, GROUND+2, BOARD_WIDTH, GROUND+2);
	    drawPlayer(g);
	    g.drawString("Coins: " + board.coins , 35 , GROUND + 30);
	    g.drawString("LEVEL " + board.level , 150 , GROUND + 30);
	    g.drawString("Points: " + board.points , 250 , GROUND + 30);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica", Font.BOLD, 20));
		g.drawString(1+"",BOARD_WIDTH/2,BOARD_HEIGTH/2);
		init = System.currentTimeMillis();
		while(System.currentTimeMillis() - init < 1000);
		board.SM.playBackground();
	}
	
	public void drawAliens(Graphics g) 
    {
        Iterator it = board.aliens.iterator();

        while (it.hasNext()) 
        {
            Alien alien = (Alien) it.next();

            if (alien.isVisible()) 
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), board);
            
            if (alien.isDying()) 
                alien.die();            
        }
    }
	
	public void drawPlayer(Graphics g) 
    {
        if (board.player.isVisible()) 
            g.drawImage(board.player.getImage(), board.player.getX(), board.player.getY(), board);
        
        if (board.player.isDying()) 
        {
            board.player.die();
            board.ingame = false;
        }
    }
    
    public void drawShot(Graphics g) 
    {
        if (board.shot.isVisible())
            g.drawImage(board.shot.getImage(), board.shot.getX(), board.shot.getY(), board);
    }
    
    public void drawBombing(Graphics g) 
    {
        Iterator i3 = board.aliens.iterator();

        while (i3.hasNext()) 
        {
            Alien a = (Alien) i3.next();

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) 
                g.drawImage(b.getImage(), b.getX(), b.getY(), board); 
        }
    }
    
    public void explosionImage(Sprite sprite)
    {
    	ImageIcon ii2 = new ImageIcon(getClass().getResource(expl));
    	sprite.setImage(ii2.getImage());
    }
    
    public void paint(Graphics g)
    {
	   	g.setColor(backgroundColor);
      	g.fillRect(0, 0, d.width, d.height);
      	g.setColor(Color.green);   
		
		if(board.levelPassed)
		{
			g.setFont(new Font("Helvetica", Font.BOLD, 14));
			g.setColor(Color.GREEN);
			g.drawString("Coins: " + board.coins, 150,BOARD_WIDTH/2-100);
			g.setColor(new Color(0, 32, 48));
        	g.fillRect(30, BOARD_WIDTH/2 - 50, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2 + 50, BOARD_WIDTH-80, 40);
        	g.setColor(Color.WHITE);
        	g.drawString("ATTACK DAMAGE:" + board.attackDamage + "----->" + (board.attackDamage+1) + "                  150",31,BOARD_WIDTH/2 - 25);
        	g.drawString("ATTACK SPEED: " + board.missileSpeed + "----->" + (board.missileSpeed+1) + "                     125",31,BOARD_WIDTH/2 + 25);
        	g.drawString("SPEED:" + board.playerSpeed + "----->" + (board.playerSpeed+1) + "                                    100",31,BOARD_WIDTH/2 +75);
        	g.setColor(new Color(0, 32, 48));
        	g.fillRect(150, BOARD_WIDTH/2 + 65 + 50, 50, 30);
        	g.setColor(Color.WHITE);
        	g.drawString("OK",165,BOARD_WIDTH/2+135);
		}
		else if(board.mainmenu)
		{
			
			g.setFont(new Font("Helvetica", Font.BOLD, 24));
			g.setColor(Color.GREEN);
			g.drawString("ALIEN INVASION",75,BOARD_WIDTH/2-120);
			g.setColor(new Color(0, 32, 48));
			g.fillRect(30, BOARD_WIDTH/2 - 100, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2 - 50, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2 + 50, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2 + 100, BOARD_WIDTH-80, 40);
        	g.setColor(Color.WHITE);
        	g.drawString("PLAY",75,BOARD_WIDTH/2-70);
        	g.drawString("HIGHSCORES",75,BOARD_WIDTH/2-20);
        	g.drawString("HELP",75,BOARD_WIDTH/2+30);
        	g.drawString("CREDITS",75,BOARD_WIDTH/2+80);
        	g.drawString("QUIT",75,BOARD_WIDTH/2+130);
		}
		else if(board.difficulty)
		{
			g.setColor(new Color(0, 32, 48));
        	g.fillRect(30, BOARD_WIDTH/2 - 50, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2, BOARD_WIDTH-80, 40);
        	g.setColor(Color.WHITE);
        	g.setFont(new Font("Helvetica", Font.BOLD, 24));
        	g.drawString("EASY",120,BOARD_WIDTH/2 - 25);
        	g.drawString("HARD",120,BOARD_WIDTH/2 + 25);
		}
		else if(board.highscore)
		{
			g.setFont(new Font("Helvetica", Font.BOLD, 12));
			g.setColor(Color.GREEN);
			for(int i=0; i<10; i++)
				g.drawString(""+(i+1)+") "+ board.HM.getNameAt(i) + "   " + board.HM.getScoreAt(i),20,BOARD_WIDTH/2+(i-5)*30);	
			g.setColor(new Color(0, 32, 48));
        	g.fillRect(200, BOARD_WIDTH/2-20, 50, 30);
        	g.setColor(Color.WHITE);
        	g.drawString("OK",210,BOARD_WIDTH/2);
		}
		else if(board.help)
		{
			g.setFont(new Font("Helvetica", Font.BOLD, 18));
			g.setColor(Color.GREEN);
			g.drawString("Press SPACE to shoot",50,BOARD_WIDTH/2-100);
			g.drawString("Press ARROW KEYS to move",50,BOARD_WIDTH/2-50);
			g.drawString("Press ESC to pause",50,BOARD_WIDTH/2);
			g.setColor(new Color(0, 32, 48));
        	g.fillRect(30, BOARD_WIDTH/2 + 50, BOARD_WIDTH-80, 40);
        	g.setColor(Color.WHITE);
        	g.drawString("MAIN MENU",100,BOARD_WIDTH/2 +75);
		}
		else if(board.cds)
		{
			g.setFont(new Font("Helvetica", Font.BOLD, 18));
			g.setColor(Color.GREEN);
			g.drawString("T.Arda Usman",60,79);
			g.drawString("Ayse Oyku Ozer",60,129);
			g.drawString("Yonca Yunatci",60,179);
			g.setColor(new Color(0, 32, 48));
        	g.fillRect(30, BOARD_WIDTH/2 + 50, BOARD_WIDTH-80, 40);
        	g.setColor(Color.WHITE);
        	g.drawString("MAIN MENU",100,BOARD_WIDTH/2 +75);
		}
		else if(board.paused)
		{
			g.setFont(new Font("Helvetica", Font.BOLD,24));
			g.setColor(Color.GREEN);
			g.drawString("PAUSE", 130,BOARD_WIDTH/2-100);
			g.setColor(new Color(0, 32, 48));
			g.fillRect(30, BOARD_WIDTH/2 - 50, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2, BOARD_WIDTH-80, 40);
        	g.fillRect(30, BOARD_WIDTH/2+50, BOARD_WIDTH-80, 40);
        	g.setColor(Color.WHITE);
        	g.drawString("RESUME",75,BOARD_WIDTH/2-20);
        	g.drawString("MAIN MENU",75,BOARD_WIDTH/2+30);
        	if(board.sound)
        		g.drawString("MUTE",75,BOARD_WIDTH/2+80);
        	else
        		g.drawString("UNMUTE",75,BOARD_WIDTH/2+80);
		}
		else if (board.ingame) 
		{
			g.drawImage(img, 0, 0, null);
	        g.drawLine(0, GROUND+2, BOARD_WIDTH, GROUND+2);
	        drawAliens(g);
	        drawPlayer(g);
	        drawShot(g);
	        drawBombing(g);
	        Font small = new Font("Helvetica", Font.BOLD, 14);
	        g.setFont(small);
	        g.drawString("Coins: " + board.coins , 35 , GROUND + 30);
	        g.drawString("LEVEL " + board.level , 150 , GROUND + 30);
	        g.drawString("Points: " + board.points , 250 , GROUND + 30);
      	}
    }
    
}

