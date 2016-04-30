public class LevelManager implements Commons
{
	private int bombChance, alienSpeed, bombSpeed, alienHP;
	private Board board;
	
	public LevelManager(Board board)
	{
		this.board = board;
	}
	
	public void newLevel(int level)
	{
		calculate(level-1);
		board.bombChance = bombChance;
		board.alienSpeed =  alienSpeed;
		board.bombSpeed = bombSpeed;
		board.alienHP = alienHP;
	}
	
	private void calculate(int level)
	{
		calculateBC(level);
		calculateAS(level);
		calculateBS(level);
		calculateAHP(level);
	}
	
	private void calculateBC(int level)
	{
		int x = BOMB_CHANCE - (level * 50);
		
		if(x < 5)
			this.bombChance = 5;
		else
			this.bombChance = x;
	}
	
	private void calculateAS(int level)
	{
		this.alienSpeed = ALIEN_SPEED + ((int)(level * 0.2));
	}
	
	private void calculateBS(int level)
	{
		bombSpeed = BOMB_SPEED + ((int)(level * 0.5)); 
	}
	
	private void calculateAHP(int level)
	{
		alienHP = ALIEN_HP + ((int)(level * 0.5));
	}
}