public class MainClass
{

	private enum P{
		A, B, C;
	}
	public static void main(String[] args)
	{
		Iface f = new Face2();
		f.setName("hey");
		Iface ff = (Iface) f.clone();
		f.setName("g-unit");
		//ff.setName("man");
		// System.out.println(f.getName() + ff.getName());

		String x = generateRandomBitString(3);
		System.out.println(x + " " + x.length());
		// System.out.println(replace(x, 5));
		System.out.println(P.B.ordinal());
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
