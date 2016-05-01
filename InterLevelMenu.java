public class InterLevelMenu implements Commons
{
	
	private Board board;
	
	public InterLevelMenu(Board board)
	{
		this.board = board;
	}
	
	public void buyAD()
	{
		if(board.coins>=AD_COST)
		{
			board.coins=board.coins-AD_COST;
			board.attackDamage=board.attackDamage+1;
		}
	}
	
	public void buyAS()
	{
		if(board.coins>=AS_COST)
		{
			board.coins=board.coins-AS_COST;
			board.missileSpeed=board.missileSpeed+1;
		}
	}
	
	public void buyS()
	{
		if(board.coins>=S_COST)
		{
			board.coins=board.coins-S_COST;
			board.playerSpeed=board.playerSpeed+1;
		}
	}
}