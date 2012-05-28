import org.jfree.ui.RefineryUtilities;

import Results.ResultFrame;

public class TestGraphs
{
	public static void main(String args[])
	{
		ResultFrame graph = new ResultFrame("Test");
		//graph.plot("hi");
		graph.pack();
		RefineryUtilities.centerFrameOnScreen(graph);
		graph.setVisible(true);
	}

}
