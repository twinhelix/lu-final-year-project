package environment;

public class ScoringSystem
{
	public double[] cc, cd, dc, dd;

	/***
	 * Class for scoring system, includes points for cooperate-cooperate,
	 * cooperate-defect, defect-cooperate, defect-defect
	 * 
	 * @param cc
	 * @param cd
	 * @param dc
	 * @param dd
	 */
	public ScoringSystem(double[] cc, double[] cd, double[] dc, double[] dd)
	{
		this.cc = cc;
		this.cd = cd;
		this.dc = dc;
		this.dd = dd;
	}

	public double[] getcc()
	{
		return cc;
	}

	public double[] getcd()
	{
		return cd;
	}

	public double[] getdc()
	{
		return dc;
	}

	public double[] getdd()
	{
		return dd;
	}

	/***
	 * Returns the points scored for given move
	 * 
	 * @param move
	 * @return
	 */
	public double[] getPoints(boolean[] move)
	{
		if (move[0] && move[1])
			return cc;
		if (move[0] && !move[1])
			return cd;
		if (!move[0] && move[1])
			return dc;
		else
			return dd;
	}

}
