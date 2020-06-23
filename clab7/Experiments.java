import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        int N = 5000;
        Random r = new Random();
        BST<Integer> random_inserts = new BST<Integer>();
        List<Integer> x_values_num_items = new ArrayList<>();
        List<Double> y_values_avg_depth = new ArrayList<>();
        List<Double> y_values_optimal_avg_depth = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            x_values_num_items.add(i);
            double optimal_avg_depth = ExperimentHelper.optimalAverageDepth(i);
            y_values_optimal_avg_depth.add(optimal_avg_depth);
            int rand = r.nextInt(1000000);
            random_inserts.add(rand);
            double actual_avg_depth = random_inserts.getAverageDepth();
            y_values_avg_depth.add(actual_avg_depth);
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Nodes").yAxisTitle("Average Depth").build();
        chart.addSeries("optimal_avg_depth", x_values_num_items, y_values_optimal_avg_depth);
        chart.addSeries("actual_avg_depth", x_values_num_items, y_values_avg_depth);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        int N = 5000;
        Random r = new Random();
        BST<Integer> random_inserts = new BST<Integer>();
        List<Integer> x_values_num_delete_and_replaces = new ArrayList<>();
        List<Double> y_values_avg_depth = new ArrayList<>();

        // initialize the tree with 5000 items
        for (int i = 1; i <= N; i++) {
            int rand = r.nextInt(1000000);
            random_inserts.add(rand);
        }

        // run the experiment
        for (int i = 1; i <= 1000000; i++) {
            x_values_num_delete_and_replaces.add(i);
            Integer rand_key = random_inserts.getRandomKey();
            random_inserts.deleteTakingSuccessor(rand_key);
            int rand = r.nextInt(1000000);
            random_inserts.add(rand);
            y_values_avg_depth.add(random_inserts.getAverageDepth());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Deletions and Insertions").yAxisTitle("Average Depth").build();
        chart.addSeries("actual_avg_depth", x_values_num_delete_and_replaces, y_values_avg_depth);

        new SwingWrapper(chart).displayChart();

    }

    public static void experiment3() {
        int N = 5000;
        Random r = new Random();
        BST<Integer> random_inserts = new BST<Integer>();
        List<Integer> x_values_num_delete_and_replaces = new ArrayList<>();
        List<Double> y_values_avg_depth = new ArrayList<>();

        // initialize the tree with 5000 items
        for (int i = 1; i <= N; i++) {
            int rand = r.nextInt(1000000);
            random_inserts.add(rand);
        }

        // run the experiment
        for (int i = 1; i <= 500000; i++) {
            x_values_num_delete_and_replaces.add(i);
            Integer rand_key = random_inserts.getRandomKey();
            random_inserts.deleteTakingRandom(rand_key);
            int rand = r.nextInt(1000000);
            random_inserts.add(rand);
            y_values_avg_depth.add(random_inserts.getAverageDepth());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Deletions and Insertions").yAxisTitle("Average Depth").build();
        chart.addSeries("actual_avg_depth", x_values_num_delete_and_replaces, y_values_avg_depth);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment2();
    }
}
