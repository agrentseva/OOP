package ru.nsu.ga.grentseva.primenumbers.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;

public class ResultGraph {

    public static void main(String[] args) throws Exception {
        double[] times = {
                2.159,

                2.162, 1.265, 0.901, 0.695, 0.568,
                0.496, 0.445, 0.435, 0.410, 0.387,
                0.373, 0.385, 0.390, 0.363, 0.348,
                0.371, 0.371, 0.347, 0.346, 0.349,

                0.296
        };

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(times[0], "Time", "Seq");
        for (int i = 1; i <= 20; i++) {
            dataset.addValue(times[i], "Time", "T" + i);
        }
        dataset.addValue(times[21], "Time", "Str");

        JFreeChart chart = ChartFactory.createLineChart(
                "Prime Search Performance",
                "Execution Mode",
                "Time (seconds)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        ChartUtils.saveChartAsPNG(
                new File("performance_graph.png"),
                chart,
                1200,
                700
        );
    }
}
