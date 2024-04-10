package approximations;

import model.Dot;
import model.Result;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static system_solver.SystemSolver.getSx;
import static system_solver.SystemSolver.getSy;

public interface Approximation {
    String name();
    Result approximate(List<Dot> dots);
    double value(double x, List<Double> coefficients);
    String function(List<Double> coefficients);

    default double standardDeviation(List<Double> epsilonList) {
        double sum = 0;
        for (Double eps: epsilonList) {
            sum += pow(eps, 2);
        }

        return sum;
    }

    default double squareDeviation(List<Dot> dots, List<Double> phi) {
        double sum = 0;
        for (int i = 0; i < dots.size(); i++) {
            sum += pow(phi.get(i) - dots.get(i).y(), 2);
        }

        return sqrt(sum / dots.size());
    }

    default double correlation(List<Dot> dots) {
        double xAvg = getSx(dots) / dots.size();
        double yAvg = getSy(dots) / dots.size();

        double sumX = 0;
        double sumY = 0;
        double sum = 0;

        for (Dot dot : dots) {
            sumX += pow(dot.x() - xAvg, 2);
        }

        for (Dot dot : dots) {
            sumY += pow(dot.y() - yAvg, 2);
        }

        for (Dot dot : dots) {
            sum += (dot.x() - xAvg) * (dot.y() - yAvg);
        }

        return sum / sqrt(sumX * sumY);
    }

    default double determination(List<Dot> dots, List<Double> phi) {
        double sum = 0;
        for (int i = 0; i < phi.size(); i++) {
            sum += pow(dots.get(i).y() - phi.get(i), 2);
        }

        double avg = 0;
        for (Double p: phi) {
            avg += p;
        }
        avg /= phi.size();

        double avgSum = 0;
        for (int i = 0; i < phi.size(); i++) {
            avgSum += pow(dots.get(i).y() - avg, 2);
        }

        return 1 - sum / avgSum;
    }

    default String getSign(double b) {
        return b >= 0 ? "+": "";
    }
}
