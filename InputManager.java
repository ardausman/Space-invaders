import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter
{
    private Board board;

    public InputManager(Board board)
    {
        this.board = board;
    }

    public void keyReleased(KeyEvent e) {
        this.board.player.keyReleased(e);
    }

    public void keyPressed(KeyEvent e)
    {
        this.board.player.keyPressed(e);

        int x = this.board.player.getX();
        int y = this.board.player.getY();

        if (this.board.ingame)
        {
            if (e.getKeyCode() == 32)
            {
                if (!this.board.shot.isVisible())
                {
                    if (this.board.sound)
                    {
                        this.board.SM.playLaser();
                    }

                    this.board.shot = new Shot(x, y);
                }
            }
            else if (e.getKeyCode() == 27)
            {
                this.board.paused = (!this.board.paused);
            }
        }
        else if (this.board.levelPassed)
        {
            if (e.getKeyCode() == 10)
            {
                this.board.ready = true;
            }
        }
    }
}