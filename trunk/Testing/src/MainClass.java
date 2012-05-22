import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainClass
{

	private enum P
	{
		A, B, C;
	}

	public static void main(String[] args)
	{
		List<String> strats = new ArrayList<String>();
		strats.add("hi");
		strats.add("hello");
		strats.add("hello");
		strats.add("bye");
		strats.add("hi");

		Map<String, Integer> histo = new HashMap<String, Integer>();

		for (String expert : strats)
		{
			if (!histo.containsKey(expert))
			{
				histo.put(expert, new Integer(0));
				System.out.println(expert + " " + histo.get(expert));
			}
			histo.put(expert, new Integer(histo.get(expert).intValue() + 1));
			System.out.println(expert + " " + histo.get(expert));

		}

		System.out.println(histo.get("hi").intValue());

	}

	public static String replace(String str, int index)
	{

		int value = Character.getNumericValue(str.charAt(index));
		value = 1 - value;

		str = str.substring(0, index) + value
				+ str.substring(index + 1, str.length());

		return str;
	}

	private static String generateRandomBitString(int length)
	{

		String code = "";
		for (int i = length; i > 0; i -= 8)
		{
			int current_len = 8;
			if (i < 8)
			{
				current_len = i;
			}

			int bit_length = (int) Math.pow(2, current_len);

			int bit_in_int = (int) (Math.random() * bit_length);

			String current_code = Integer.toBinaryString(bit_in_int);
			current_code = pad(current_code, current_len);

			code = code + current_code;
		}

		return code;

	}

	private static String pad(String binaryString, int length)
	{
		for (int i = binaryString.length(); i < length; i++)
		{
			binaryString = "0" + binaryString;
		}
		return binaryString;
	}

}
