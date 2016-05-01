import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;

public class KeyboardManager extends KeyAdapter {
	
		private Board board;
		
		public KeyboardManager(Board board)
		{
			this.board = board;
		}

        public void keyReleased(KeyEvent e) {
            board.player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e){

          board.player.keyPressed(e);

          int x = board.player.getX();
          int y = board.player.getY();

          if (board.ingame)
          {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) 
            {
                if (!board.shot.isVisible())
                {
                	board.SM.playLaser();
                	board.shot = new Shot(x, y);
                }         
          	}
          	else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
          	{
          		board.paused = !board.paused;
          	}
          }
          else if(board.levelPassed)
          {
          	switch(e.getKeyCode())
          	{
          		case KeyEvent.VK_ENTER: board.ready = true;
          								break;
          		case KeyEvent.VK_1: board.ILM.buyAD();
          							break;
          		case KeyEvent.VK_2: board.ILM.buyAS();
          							break;
          		case KeyEvent.VK_3: board.ILM.buyS();
          							break;
          	}
          	 
          }
          else if(board.highscore)
          {
          	if(e.getKeyCode() == KeyEvent.VK_ENTER)
          	{
          		board.highscore = false;
    			board.mainmenu = true;
    			board.repaint();
          	}
          }
        }
    }
