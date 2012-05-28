package Results;

import java.util.Map;

import org.jfree.ui.RefineryUtilities;

public class GraphingResults
{
	private String title;

	public GraphingResults(String title)
	{
		this.title = title;
	}

	public void plotDouble(Map<String, Double> sorted_map)
	{
		ResultFrame graph = new ResultFrame(title);
		graph.plotDouble(sorted_map);
		graph.pack();
		RefineryUtilities.centerFrameOnScreen(graph);
		graph.setVisible(true);
	}

	public void plotInteger(Map<String, Integer> sorted_map)
	{
		ResultFrame graph = new ResultFrame(title);
		graph.plotInteger(sorted_map);
		graph.pack();
		RefineryUtilities.centerFrameOnScreen(graph);
		graph.setVisible(true);
	}
}
