package nsga_expert.smartcrossover;

public class SmartCrossover
{
	public SmartCrossover()
	{

	}

	public String[] crossover(String first, String second, int move)
	{
		move--;
		int outcome = (int) (Math.random() * 4);
		
		String[] result = { first, second };
		switch (move)
		{
			case 0:
			{
				outcome = (int) (Math.random() * 5);
				int start = 16 * outcome;
				int finish = Math.min(start + 16, 70);

				String temp = new String(first);
				result[0] = result[0].substring(0, start)
						+ result[1].substring(start, finish)
						+ result[0].substring(finish, result[0].length());
				result[1] = result[1].substring(0, start)
						+ temp.substring(start, finish)
						+ result[1].substring(finish, result[1].length());
			}
				break;
			case 1:
			{
				String temp = new String(first);
				for (int i = 0; i < 64; i = i + 16)
				{
					int start = i + outcome * 4;
					int finish = Math.min(start + 4, 70);

					result[0] = result[0].substring(0, start)
							+ result[1].substring(start, finish)
							+ result[0].substring(finish, result[0].length());
					result[1] = result[1].substring(0, start)
							+ temp.substring(start, finish)
							+ result[1].substring(finish, result[1].length());
				}
			}
				break;
			case 2:
			{
				String temp = new String(first);
				for (int i = 0; i < 64; i = i + 16)
				{
					for (int j = 0; j < 16; j = j + 4)
					{
						int start = i + j + outcome;
						int finish = Math.min(start + 1, 70);

						result[0] = result[0].substring(0, start)
								+ result[1].substring(start, finish)
								+ result[0].substring(finish,
										result[0].length());
						result[1] = result[1].substring(0, start)
								+ temp.substring(start, finish)
								+ result[1].substring(finish,
										result[1].length());
					}
				}
			}
				break;
		}
		return result;
	}
}
