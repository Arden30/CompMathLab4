package approximations;

import model.Dot;
import model.Result;

import java.util.ArrayList;
import java.util.List;

import static system_solver.SystemSolver.*;

public class LinearApproximation implements Approximation {
    @Override
    public String name() {
        return "Линейная аппроксимация (ax + b)";
    }

    @Override
    public String function(List<Double> coefficients) {
        double a = coefficients.get(0);
        double b = coefficients.get(1);

        return String.format("%.5f", a) + "x" + getSign(b) + String.format("%.5f", b);
    }

    @Override
    public double value(double x, List<Double> coefficients) {
        double a = coefficients.get(0);
        double b = coefficients.get(1);

        return a * x + b;
    }

    @Override
    public Result approximate(List<Dot> dots) {
        List<Double> coefficients = solveLinearSystem(dots);
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
                correlation(dots),
                determination(dots, approximationFuncValues),
                squareDeviation(dots, approximationFuncValues));
    }
}
