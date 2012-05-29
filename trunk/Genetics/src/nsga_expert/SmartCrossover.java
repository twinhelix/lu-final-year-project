package nsga_expert;

public class SmartCrossover
{
	public SmartCrossover()
	{

	}

	public String[] crossover(String first, String second, int move)
	{
		move--;
		int outcome = (int) Math.random() * 4;
		switch (move)
		{
			case 0:
			{
				int start = 16 * outcome;
				int finish = start + 16;

				String temp = new String(first);
				first = first.substring(0, start)
						+ second.substring(start, finish)
						+ first.substring(finish, first.length());
				second = second.substring(0, start)
						+ temp.substring(start, finish)
						+ second.substring(finish, second.length());
			}
				break;
			case 1:
			{

			}
				break;
			case 2:
			{

			}
				break;
		}
		return null;
	}
}
