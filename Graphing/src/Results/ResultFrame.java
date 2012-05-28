package Results;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
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

	public void plotDouble(Map<String, Double> sorted_map)
	{

		CategoryDataset categoryDataset = createDataset(sorted_map);

		JFreeChart chart = ChartFactory.createBarChart(getTitle(), "Expert",
				"Score", categoryDataset, PlotOrientation.VERTICAL, false,
				false, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		chart.setBackgroundPaint(ChartColor.WHITE);
		try
		{
			ChartUtilities.saveChartAsPNG(new File("chart.png"), chart, 400,
					300);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 600));
		setContentPane(chartPanel);

		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	public void plotInteger(Map<String, Integer> sorted_map)
	{

		CategoryDataset categoryDataset = createDataset(sorted_map);

		JFreeChart chart = ChartFactory.createBarChart(getTitle(), "Expert",
				"Occurrances", categoryDataset, PlotOrientation.VERTICAL, false,
				false, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		chart.setBackgroundPaint(ChartColor.WHITE);
		try
		{
			ChartUtilities.saveChartAsPNG(new File("chart.png"), chart, 800,
					600);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);

		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	private CategoryDataset createDataset(
			Map<String, ? extends Number> sorted_map)
	{
		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Map.Entry<String, ? extends Number> entry : sorted_map.entrySet())
		{
			dataset.addValue(entry.getValue(), entry.getKey(), entry.getKey());
		}
		return dataset;
	}
}
