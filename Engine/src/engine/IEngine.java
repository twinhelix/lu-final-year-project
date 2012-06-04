package engine;

public interface IEngine
{
	public void run();

	public void showTally();

	public void plotResults();

	public void plotPerformance();

	public double getAverageOpponentScore(String name);

	public double getScore(String name);
}
