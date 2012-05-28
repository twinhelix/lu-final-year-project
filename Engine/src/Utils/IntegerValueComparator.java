package Utils;

import java.util.Comparator;
import java.util.Map;

public class IntegerValueComparator implements Comparator<Object>
{
	Map<String, Integer> base;

	public IntegerValueComparator(Map<String, Integer> base)
	{
		this.base = base;
	}

	public int compare(Object a, Object b)
	{

		if ((Integer) base.get(a) < (Integer) base.get(b))
		{
			return 1;
		}
		else if ((Integer) base.get(a) == (Integer) base.get(b))
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
}
