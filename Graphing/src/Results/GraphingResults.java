package Results;

import java.util.TreeMap;

import org.jfree.ui.RefineryUtilities;

public class GraphingResults
{
	private String title;
	public GraphingResults(String title){
		this.title = title;
	}
	
	public void plot(TreeMap<String, Double> sorted_map){
		ResultFrame graph = new ResultFrame(title);
		graph.plot(sorted_map);
		graph.pack();
		RefineryUtilities.centerFrameOnScreen(graph);
		graph.setVisible(true);
	}
}
