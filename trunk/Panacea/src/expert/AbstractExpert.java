package expert;

import agent.IExpert;
import environment.GameHistory;

public abstract class AbstractExpert implements IExpert
{
	protected int playerNo;

	/***
	 * Expert base class
	 * 
	 * @param playerNo
	 */
	public AbstractExpert(int playerNo)
	{
		this.playerNo = playerNo;
	}

	public abstract boolean move(GameHistory history);

	public int getPlayerNumber()
	{
		return playerNo;
	}

	public void setPlayerNumber(int playerNo)
	{
		this.playerNo = playerNo;
	}

	@Override
	public int compareTo(IExpert e)
	{
		return this.getName().compareTo(e.getName());
	}

	@Override
	public void initialize()
	{
		// Do nothing by default since agents generally don't store any values
	}

	@Override
	public Object clone()
	{
		IExpert ex = null;
		try
		{
			ex = (IExpert) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return ex;
	}
}
