package Performance;

import org.jfree.ui.RefineryUtilities;

public class GraphingPerformance
{
	private String title;

	public GraphingPerformance(String title)
	{
		this.title = title;
	}

	public void plot()
	{
		final PerformanceFrame demo = new PerformanceFrame(title);
		demo.plot(null);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}
