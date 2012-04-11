package Results;

import java.awt.Dimension;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class ResultFrame extends ApplicationFrame
{
	public ResultFrame(String title)
	{
		super(title);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void plot(TreeMap<String, Double> sorted_map)
	{

		CategoryDataset categoryDataset = createDataset(sorted_map);

		JFreeChart chart = ChartFactory.createBarChart(getTitle(), "Expert",
				"Score", categoryDataset, PlotOrientation.VERTICAL, false,
				false, false);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);

		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	private CategoryDataset createDataset(TreeMap<String, Double> sorted_map)
	{
		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Map.Entry<String, Double> entry : sorted_map.entrySet())
		{
			dataset.addValue(entry.getValue(), entry.getKey(), entry.getKey());
		}
		return dataset;

	}

}
