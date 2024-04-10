package approximations;

import model.Dot;
import model.Result;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static system_solver.SystemSolver.solveSquareSystem;

public class SquareApproximation implements Approximation {
    @Override
    public String name() {
        return "Квадратичная аппроксимация (a0 + a1*x + a2*x^2)";
    }
    @Override
    public Result approximate(List<Dot> dots) {
        List<Double> coefficients = solveSquareSystem(dots);
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
        double a0 = coefficients.get(0);
        double a1 = coefficients.get(1);
        double a2 = coefficients.get(2);

        return a2 * pow(x, 2) + a1 * x + a0;
    }

    @Override
    public String function(List<Double> coefficients) {
        double a0 = coefficients.get(0);
        double a1 = coefficients.get(1);
        double a2 = coefficients.get(2);

        return String.format("%.5f", a0) + " " + getSign(a1) + " " + String.format("%.5f", a1) + "x " + getSign(a2) + " " + String.format("%.5f", a2) + "x^2";
    }
}
