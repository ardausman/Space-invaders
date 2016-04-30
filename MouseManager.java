import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter implements Commons
    {
    	private Board board;
    	
    	public MouseManager(Board board)
    	{
    		this.board = board;
    	}
    	
    	public void mouseClicked(MouseEvent e)
    	{
    		int mx = e.getX();
    		int my = e.getY();
    		
    		if(board.mainmenu)
    		{
        		if(mx>=30 && mx<=30+BOARD_WIDTH-80)
        		{
        			if(my>= BOARD_WIDTH/2-100 && my<= BOARD_WIDTH/2-100+40)//play
        			{
        				board.quit = false;
        				board.mainmenu = false;
        				board.difficulty = true;
        				board.repaint();
        			}
        			else if(my>= BOARD_WIDTH/2-50 && my<= BOARD_WIDTH/2-50+40)//highscore
        			{
        				board.mainmenu = false;
        				board.highscore = true;
        				board.HM.getHighscores();
        				board.repaint();
        			}
        			else if(my>= BOARD_WIDTH/2 && my<= BOARD_WIDTH/2+40)//help
        			{
        				board.help = true;
        				board.mainmenu = false;
        				board.repaint();	
        			}
        			else if(my>= BOARD_WIDTH/2+50 && my<= BOARD_WIDTH/2+50+40)//credits
        			{
        				board.mainmenu = false;
        				board.cds = true;
        				board.repaint();
        			}
        			else if(my>= BOARD_WIDTH/2+100 && my<= BOARD_WIDTH/2+100+40)//quit
        			{
        				System.exit(0);
        			}
        		}
    		}
    		else if(board.difficulty)
    		{
				if(my>=BOARD_WIDTH/2-50 && my<=BOARD_WIDTH/2-50+40) //noob
				{
					board.hardMode = false;
					board.difficulty = false;
					board.gameInit();
				}
				if(my>=BOARD_WIDTH/2 && my<=BOARD_WIDTH/2+40) //pro
				{
					board.hardMode = true;
					board.difficulty = false;
					board.gameInit();
				}
    		}
    		else if(board.highscore)
    		{
    			if(mx>=200 && mx<= 250 && my>=BOARD_WIDTH/2-20 && my<=BOARD_WIDTH/2+10)
    			{
    				board.highscore = false;
    				board.mainmenu = true;
    				board.repaint();
    			}
    		}
    		else if(board.cds)
    		{
    			if(mx>=30 && mx<=30+BOARD_WIDTH-80)
    				if(my>=BOARD_WIDTH/2+50 && my<=BOARD_WIDTH/2+50+40)
    				{
    					board.cds = false;
    					board.mainmenu = true;
    					board.repaint();
    				}
    		}
    		else if(board.help)
    		{
    			if(mx>=30 && mx<=30+BOARD_WIDTH-80)
    				if(my>=BOARD_WIDTH/2+50 && my<=BOARD_WIDTH/2+50+40)
    				{
    					board.help = false;
    					board.mainmenu = true;
    					board.repaint();
    				}
    				
    		}
    		if(board.paused)
    		{
    			if(mx>=30 && mx<=30+BOARD_WIDTH-80)
        		{
        			if(my>= BOARD_WIDTH/2-50 && my<= BOARD_WIDTH/2-50+40)//continue
        			{
        				board.paused = false;
        			}
        			if(my>= BOARD_WIDTH/2 && my<= BOARD_WIDTH/2+40)//quit
        			{
        				board.mainmenu = true;
        				board.paused = false;
        				board.quit = true;
        				board.reset();
        			}
        			if(my>= BOARD_WIDTH/2+50 && my<= BOARD_WIDTH/2+50+40)//sound
        			{
        				board.sound = !board.sound;
        				if(board.sound)
        					board.SM.playBackground();
        				else
        					board.SM.stopBackgroundMusic();	
        			}
        		}			
    		}
    		if(board.levelPassed)
    		{
    			if(mx>=30 && mx<=30+BOARD_WIDTH-80)
    			{
    				if(my>=BOARD_WIDTH/2-50 && my<=BOARD_WIDTH/2-50+40) //ad
    				{
    					board.ILM.buyAD();	
    				}
    				if(my>=BOARD_WIDTH/2 && my<=BOARD_WIDTH/2+40) //as
    				{
    					board.ILM.buyAS();
    				}	
    				if(my>=BOARD_WIDTH/2+50 && my<=BOARD_WIDTH/2+50+40) //s
    				{
    					board.ILM.buyS();
    				}
    			}
    			if(mx>=150 && mx<=200 && my>=BOARD_WIDTH/2+65+50 && my<=BOARD_WIDTH/2+65+50+30)//ok
    			{
    				board.ready = true;
    			}	
    		}
    	}
    }