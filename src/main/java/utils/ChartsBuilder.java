package utils;

import approximations.Approximation;
import model.Dot;
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
import java.awt.geom.Ellipse2D;
import java.util.List;

public class ChartsBuilder extends JFrame {
    public ChartsBuilder(List<Dot> dots, List<Approximation> approximationList, List<Result> results) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        XYSeriesCollection dataset = new XYSeriesCollection();

        // Создание набора данных для каждой функции
        for (int i = 0; i < approximationList.size(); i++) {
            XYSeries series = new XYSeries(approximationList.get(i).function(results.get(i).coefficients()));
            for (double x = -15.0; x <= 15.0; x += 0.1) {
                double y = approximationList.get(i).value(x, results.get(i).coefficients());
                series.add(x, y);
            }
            dataset.addSeries(series);
        }

        // Создание набора данных для точек
        XYSeries pointsSeries = new XYSeries("Точки");
        for (Dot dot : dots) {
            pointsSeries.add(dot.x(), dot.y());
        }
        dataset.addSeries(pointsSeries);

        // Создание графика
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графики аппроксимирующих функций", "X", "Y",
                dataset
        );

        chart.getXYPlot().getDomainAxis().setRange(-15, 15);
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

        // Настройка отображения точек
        renderer.setSeriesLinesVisible(approximationList.size(), false); // Отключаем соединение линиями для точек
        renderer.setSeriesShapesVisible(approximationList.size(), true); // Включаем отображение формы для точек
        renderer.setSeriesShape(approximationList.size(), new Ellipse2D.Double(-2, -2, 8, 8)); // Задаем форму точки (в данном случае - круг)
        renderer.setSeriesPaint(approximationList.size(), Color.RED); // Задаем цвет точек


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1100, 630));
        add(chartPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
