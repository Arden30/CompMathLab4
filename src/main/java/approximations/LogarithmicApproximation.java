package approximations;

import model.Dot;
import model.Result;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.log;
import static system_solver.SystemSolver.solveLinearSystem;

public class LogarithmicApproximation implements Approximation {
    @Override
    public String name() {
        return "Логарифмическая аппроксимация (a0*ln(x) + a1)";
    }

    @Override
    public Result approximate(List<Dot> dots) {
        List<Dot> lnDots = new ArrayList<>();
        for (Dot value : dots) {
            Dot dot = new Dot(log(value.x()), value.y());
            lnDots.add(dot);
        }
        List<Double> coefficients = solveLinearSystem(lnDots);
        List<Double> approximationFuncValues = new ArrayList<>();
        List<Double> epsilonList = new ArrayList<>();

        for (Dot dot : dots) {
            double approximateVal = value(dot.x(), coefficients);
            approximationFuncValues.add(approximateVal);
            epsilonList.add(approximateVal - dot.y());
        }

        String approximationFunc = function(coefficients);

        return new Result(coefficients,
                standardDeviation(epsilonList),
                dots,
                approximationFuncValues,
                epsilonList,
                approximationFunc,
                null,
                determination(dots, approximationFuncValues),
                squareDeviation(dots, approximationFuncValues));
    }

    @Override
    public double value(double x, List<Double> coefficients) {
        double a = coefficients.get(0);
        double b = coefficients.get(1);

        return a * log(x) + b;
    }

    @Override
    public String function(List<Double> coefficients) {
        double a = coefficients.get(0);
        double b = coefficients.get(1);

        return String.format("%.5f", a) + "ln(x)" + getSign(b) + String.format("%.5f", b);
    }
}
