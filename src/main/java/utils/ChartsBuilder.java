package utils;

import approximations.Approximation;
import model.Result;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartsBuilder extends JFrame {
    public ChartsBuilder(List<Approximation> approximationList, List<Result> results) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        XYSeriesCollection dataset = new XYSeriesCollection();

        // Создание набора данных для каждой функции
        for (int i = 0; i < approximationList.size(); i++) {
            XYSeries series = new XYSeries(approximationList.get(i).function(results.get(i).coefficients()));
            for (double x = -10.0; x <= 10.0; x += 0.1) {
                double y = approximationList.get(i).value(x, results.get(i).coefficients());
                series.add(x, y);
            }
            dataset.addSeries(series);
        }

        // Создание графика
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графики аппроксимирующих функций", "X", "Y",
                dataset
        );

        chart.getXYPlot().getDomainAxis().setRange(-10, 10);
        chart.getXYPlot().getRangeAxis().setRange(-5, 25);

        NumberAxis xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();

        xAxis.setTickUnit(new NumberTickUnit(1));
        yAxis.setTickUnit(new NumberTickUnit(1));

        // Настройка стилей линий осей координат
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setDomainZeroBaselinePaint(Color.BLACK); // черный цвет для вертикальной оси 0
        plot.setRangeZeroBaselinePaint(Color.BLACK); // черный цвет для горизонтальной оси 0

        // Устанавливаем толщину линии для функций
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setStroke(new BasicStroke(2)); // Установите желаемую толщину линии здесь

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        add(chartPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
