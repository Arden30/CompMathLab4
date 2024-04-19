package approximations;

import model.Dot;
import model.Result;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static system_solver.SystemSolver.solveLinearSystem;

public class PowerApproximation implements Approximation {
    @Override
    public String name() {
        return "Степенная аппроксимация (a1x^(a0))";
    }

    @Override
    public String function(List<Double> coefficients) {
        double a = coefficients.get(1);
        double b = coefficients.get(0);

        return String.format("%.5f", a) + "x^" + String.format("%.5f", b);
    }

    @Override
    public double value(double x, List<Double> coefficients) {
        double a = coefficients.get(1);
        double b = coefficients.get(0);

        return a * pow(x,b);
    }

    @Override
    public Result approximate(List<Dot> dots) {
        List<Dot> lnDots = new ArrayList<>();
        for (Dot value : dots) {
            Dot dot = new Dot(log(value.x()), log(value.y()));
            lnDots.add(dot);
        }
        List<Double> coefficients = solveLinearSystem(lnDots);
        coefficients.set(1, exp(coefficients.get(1)));

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
}
