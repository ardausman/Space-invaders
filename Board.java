import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Board extends JPanel implements Runnable, Commons { 

    protected ArrayList aliens;
    protected Player player;
    protected Shot shot;

    private int direction;
    private int deaths;
    
    protected int coins = 0;
    protected int points = 0;
    protected int level = 1;
    
    protected int missileSpeed = 4;
    protected int playerSpeed = 1;
    protected int attackDamage = 1;
    
    protected int bombChance;
    protected int alienHP;
    protected int alienSpeed;
    protected int bombSpeed;

	protected boolean mainmenu;
	protected boolean paused = false;
	protected boolean sound = true;
	protected boolean highscore = false;
	protected boolean help = false;
	protected boolean cds = false;
	protected boolean ready = false;
	protected boolean hardMode = false;
	protected boolean difficulty = false;
    protected boolean ingame = false;
    protected boolean levelPassed = false;
    protected boolean quit = false;

    private Thread animator;
    protected LevelManager LM;
    protected InterLevelMenu ILM;
    protected SoundManager SM;
    protected HighscoreManager HM;
    protected GraphicsManager GM;
    private int counter = 0;    
 
 	// Collusion Manager
 	// boss fights
 	// powerups
 	
    public Board() 
    {	
    	HM = new HighscoreManager(this);
    	SM = new SoundManager(this);
    	GM = new GraphicsManager(this);
		ILM = new InterLevelMenu(this);
		LM = new LevelManager(this);
        addKeyListener(new KeyboardManager(this));
        addMouseListener(new MouseManager(this));
        setFocusable(true);
		LM.newLevel(level);
		player = new Player();
        setDoubleBuffered(true); 
        mainmenu=true;   
    }

    public void gameInit() 
    {
		deaths = 0;
		direction = -1;
		ingame = true;
		levelPassed = false;
		highscore = false;
		mainmenu = false;

        aliens = GM.initAliens();

        player.setStart();
        player.speed = playerSpeed;
        shot = new Shot();

       	paint(this.getGraphics());
       	GM.countdown(this.getGraphics());		
       
        if (animator == null || !ingame) 
        {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void paint(Graphics g)
    {
      super.paint(g);
      GM.paint(g);
    }

    public void gameOver() 
    {
    	SM.stopBackgroundMusic();
    	SM.playGameover();
        HM.writeHighscore(points);
		reset();
    }
    
    public void interLevelMenu()
    {
    	SM.stopBackgroundMusic();
    	
    	ready = false;
    	
    	while(!ready)
    		repaint();
    			
    	nextLevel();
    }

	public void nextLevel()
	{
		level++;
		LM.newLevel(level);
		gameInit();
		coins += 50;
		setDoubleBuffered(true);
	}

    public void animationCycle()  
    {
		if(paused)
			return;
		
        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) 
        {
            levelPassed = true;
            ingame = false;
            points = hardMode ? points+500 : points+150;
        }
		
		if(points>0)
			counter++;
		if(counter == 100)
		{
			points = (points-10>0)? points-10 : 0;
			counter = 0;
		}
		
        // player

        player.act();

        // shot
        if (shot.isVisible()) 
        {
            Iterator it = aliens.iterator();
            int shotX = shot.getX();
            int shotY = shot.getY();

            while (it.hasNext()) 
            {
                Alien alien = (Alien) it.next();
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) 
                {
                    if (shotX >= (alienX) && 
                        shotX <= (alienX + ALIEN_WIDTH) &&
                        shotY >= (alienY) &&
                        shotY <= (alienY+ALIEN_HEIGHT) ) 
                    {
                    	alien.damage(attackDamage);
                    	
                    	if(alien.getCurrentHP()<=0)
                    	{
                    		SM.playExplosion();
                    		GM.explosionImage(alien);
                        	alien.setDying(true);
                        	deaths++;
                        	coins += 10;
                        	points = hardMode ? points + 40 : points + 20;
                    	}
                        shot.die();
                     }
                }
            }

            int y = shot.getY();
            y -= missileSpeed;
            if (y < 0)
            {
            	shot.die();
            	if(coins - 50 < 0)
            		coins = 0;
            	else
            		coins -= 50;
            }   
            else 
            	shot.setY(y);
        }

        // aliens

         Iterator it1 = aliens.iterator();

         while (it1.hasNext()) 
         {
             Alien a1 = (Alien) it1.next();
             int x = a1.getX();

             if (x  >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) 
             {
                 direction = -1;
                 Iterator i1 = aliens.iterator();
                 while (i1.hasNext()) 
                 {
                     Alien a2 = (Alien) i1.next();
                     a2.setY(a2.getY() + GO_DOWN);
                 }
             }

            if (x <= BORDER_LEFT && direction != 1) 
            {
                direction = 1;

                Iterator i2 = aliens.iterator();
                while (i2.hasNext()) 
                {
                    Alien a = (Alien)i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }


        Iterator it = aliens.iterator();

        while (it.hasNext()) 
        {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) 
            {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) 
                {
                    ingame = false;
                }

                alien.act(direction*alienSpeed);
            }
        }

        // bombs

        Iterator i3 = aliens.iterator();
        Random generator = new Random();
        while (i3.hasNext()) 
        {
            int shot = generator.nextInt(bombChance);
            
            Alien a = (Alien) i3.next();
            Alien.Bomb b = a.getBomb();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) 
            {
                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());   
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) 
            {
                if ( bombX >= (playerX) && 
                    bombX <= (playerX+PLAYER_WIDTH) &&
                    bombY >= (playerY) && 
                    bombY <= (playerY+PLAYER_HEIGHT) ) 
                {
                        GM.explosionImage(player);
                        player.setDying(true);
                        b.setDestroyed(true);;
                }
            }

            if (!b.isDestroyed()) 
            {
                b.setY(b.getY() + bombSpeed);
                if(hardMode)
                {
	                if(b.getY()<GROUND/2+60)
	                {
	                	if(b.getX()>player.getX())
	                		b.setX(b.getX() - 1);
	                	else if(b.getX()<player.getX())
	                	 	b.setX(b.getX() + 1);  
	                }
                }
                if (b.getY() >= GROUND - BOMB_HEIGHT) 
                    b.setDestroyed(true);   
            }
        }
    }

    public void run()
    {	
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
		
        while (ingame) 
        {
            repaint();
            animationCycle();
            
			if(levelPassed)
				interLevelMenu();
				
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) 
                sleep = 2;
            
            try 
            {
                Thread.sleep(sleep);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace(System.out);
            }
            beforeTime = System.currentTimeMillis();
        } 
        gameOver();
    }
     
    public void reset()
    {
    	player = new Player();
		coins = 0;
    	points = 0;
    	level = 1;
    	counter = 0;
    	
    	alienHP = 1;
    	alienSpeed = 1;
    	bombChance = 1000;
    	bombSpeed = 1;
    
   	 	missileSpeed = 4;
   		playerSpeed = 1;
    	attackDamage = 1;
		
        animator = null;
        highscore = true;
        hardMode = false;
        ingame = false;
        repaint();
    }
}