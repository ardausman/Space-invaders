import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.InputStream;
import java.io.BufferedInputStream;

public class SoundManager
{
	private Board board;
	
	private String laserSound = "laser.wav";
	private String backgroundSound = "background.wav";
	private String countdownSound = "countdown.wav";
	private String explosionSound = "explosion.wav";
	private String gameoverSound = "gameover.wav";
	
	private Clip laser;
	private Clip background;
	private Clip countdown;
	private Clip explosion;
	private Clip gameover;
	
	public SoundManager(Board board)
	{
		this.board = board;
	}
    
    public void playLaser()
    {
    	if(board.sound)
    	{
    		try
            {
				laser = AudioSystem.getClip();
				InputStream audioSrc = getClass().getResourceAsStream(laserSound);
				InputStream bufferedIn = new BufferedInputStream(audioSrc);
    			AudioInputStream ais = AudioSystem.getAudioInputStream( bufferedIn );
				laser.open(ais);
				laser.loop(0);				
            }
            catch(Exception e)
            {
            	e.printStackTrace(System.out);
            }
        }
    }
    
    public void playBackground()
    {
    	if(board.sound)
    	{
    		try
    		{
    			background = AudioSystem.getClip();
    			InputStream audioSrc = getClass().getResourceAsStream(backgroundSound);
				InputStream bufferedIn = new BufferedInputStream(audioSrc);
    			AudioInputStream ais = AudioSystem.getAudioInputStream( bufferedIn );
    			background.open(ais);
    			background.loop(Clip.LOOP_CONTINUOUSLY);
    		}
    		catch(Exception e)
       		{
            	e.printStackTrace(System.out);
       		 }
    	}   
    }
    
    public void stopBackgroundMusic()
    {
    	background.stop();
    }
    
    public void playCountdown()
    {
    	if(board.sound)
    	{
    		try
    		{
    			countdown = AudioSystem.getClip();
    			InputStream audioSrc = getClass().getResourceAsStream(countdownSound);
				InputStream bufferedIn = new BufferedInputStream(audioSrc);
    			AudioInputStream ais = AudioSystem.getAudioInputStream( bufferedIn );
    			countdown.open(ais);
    			countdown.loop(0);
    		}
    		catch(Exception e)
       		{
            	e.printStackTrace(System.out);
       		 }
    	}   
    }
    
    public void playExplosion()
    {
    	if(board.sound)
    	{
    		try
            {
				explosion = AudioSystem.getClip();
				InputStream audioSrc = getClass().getResourceAsStream(explosionSound);
				InputStream bufferedIn = new BufferedInputStream(audioSrc);
    			AudioInputStream ais = AudioSystem.getAudioInputStream( bufferedIn );
				explosion.open(ais);
				explosion.loop(0);				
            }
            catch(Exception e)
            {
            	e.printStackTrace(System.out);
            }
        }
    } 
    	
   	public void playGameover()
    {
    	if(board.sound && !board.quit)
    	{
    		try
            {
				gameover = AudioSystem.getClip();
				InputStream audioSrc = getClass().getResourceAsStream(gameoverSound);
				InputStream bufferedIn = new BufferedInputStream(audioSrc);
    			AudioInputStream ais = AudioSystem.getAudioInputStream( bufferedIn );
				gameover.open(ais);
				gameover.loop(0);				
            }
            catch(Exception e)
            {
            	e.printStackTrace(System.out);
            }
        }
    }  		
}
