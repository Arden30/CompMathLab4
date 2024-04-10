package system_solver;

import model.Dot;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class SystemSolver {
    public static List<Double> solveLinearSystem(List<Dot> dots) {
        List<Double> coefficients = new ArrayList<>();
        double sx = getSx(dots);
        double sxx = getSxx(dots);
        double sy = getSy(dots);
        double sxy = getSxy(dots);
        double n = dots.size();

        double det = sxx * n - sx * sx;
        double det1 = sxy * n - sx * sy;
        double det2 = sxx * sy - sx * sxy;

        double a = det1 / det;
        double b = det2 / det;

        coefficients.add(a);
        coefficients.add(b);

        return coefficients;
    }

    public static List<Double> solveSquareSystem(List<Dot> dots) {
        List<Double> coefficients = new ArrayList<>();
        double sx = getSx(dots);
        double sxx = getSxx(dots);
        double sxxx = getSxxx(dots);
        double sxxxx = getSxxxx(dots);
        double sy = getSy(dots);
        double sxy = getSxy(dots);
        double sxxy = getSxxy(dots);
        double n = dots.size();

        double det = n * sxx * sxxxx + sx * sxxx * sxx + sxx * sx * sxxx - sxx * sxx * sxx - sx * sx * sxxxx - n * sxxx * sxxx;
        double det1 = sy * sxx * sxxxx + sxy * sxxx * sxx + sxxy * sx * sxxx - sxxy * sxx * sxx - sxy * sx * sxxxx - sy * sxxx * sxxx;
        double det2 = n * sxy * sxxxx + sx * sxxy * sxx + sxx * sy * sxxx - sxx * sxy * sxx - sx * sy * sxxxx - n * sxxx * sxxy;
        double det3 = n * sxx * sxxy + sx * sxxx * sy + sxx * sx * sxy - sxx * sxx * sy - sx * sx * sxxy - n * sxy * sxxx;

        double a = det1 / det;
        double b = det2 / det;
        double c = det3 / det;

        coefficients.add(a);
        coefficients.add(b);
        coefficients.add(c);

        return coefficients;
    }

    public static List<Double> solveCubicSystem(List<Dot> dots) {
        List<Double> coefficients = new ArrayList<>();
        double sx = getSx(dots);
        double sxx = getSxx(dots);
        double sxxx = getSxxx(dots);
        double sxxxx = getSxxxx(dots);
        double sxxxxx = getSxxxxx(dots);
        double sxxxxxx = getSxxxxxx(dots);
        double sy = getSy(dots);
        double sxy = getSxy(dots);
        double sxxy = getSxxy(dots);
        double sxxxy = getSxxxy(dots);
        double n = dots.size();

        double[][] forDetMatrix = {
                {n, sx, sxx, sxxx},
                {sx, sxx, sxxx, sxxxx},
                {sxx, sxxx, sxxxx, sxxxxx},
                {sxxx, sxxxx, sxxxxx, sxxxxxx}
        };
        double det = countDeterminant(forDetMatrix);

        double[][] forDet1Matrix = {
                {sy, sx, sxx, sxxx},
                {sxy, sxx, sxxx, sxxxx},
                {sxxy, sxxx, sxxxx, sxxxxx},
                {sxxxy, sxxxx, sxxxxx, sxxxxxx}
        };
        double det1 = countDeterminant(forDet1Matrix);

        double[][] forDet2Matrix = {
                {n, sy, sxx, sxxx},
                {sx, sxy, sxxx, sxxxx},
                {sxx, sxxy, sxxxx, sxxxxx},
                {sxxx, sxxxy, sxxxxx, sxxxxxx}
        };
        double det2 = countDeterminant(forDet2Matrix);

        double[][] forDet3Matrix = {
                {n, sx, sy, sxxx},
                {sx, sxx, sxy, sxxxx},
                {sxx, sxxx, sxxy, sxxxxx},
                {sxxx, sxxxx, sxxxy, sxxxxxx}
        };
        double det3 = countDeterminant(forDet3Matrix);

        double[][] forDet4Matrix = {
                {n, sx, sxx, sy},
                {sx, sxx, sxxx, sxy},
                {sxx, sxxx, sxxxx, sxxy},
                {sxxx, sxxxx, sxxxxx, sxxxy}
        };
        double det4 = countDeterminant(forDet4Matrix);

        double a = det1 / det;
        double b = det2 / det;
        double c = det3 / det;
        double d = det4 / det;

        coefficients.add(a);
        coefficients.add(b);
        coefficients.add(c);
        coefficients.add(d);

        return coefficients;
    }

    public static double getSx(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += dot.x();
        }

        return sum;
    }

    public static double getSxx(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += pow(dot.x(), 2);
        }

        return sum;
    }

    public static double getSxxx(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += pow(dot.x(), 3);
        }

        return sum;
    }

    public static double getSxxxx(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += pow(dot.x(), 4);
        }

        return sum;
    }

    public static double getSxxxxx(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += pow(dot.x(), 5);
        }

        return sum;
    }

    public static double getSxxxxxx(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += pow(dot.x(), 6);
        }

        return sum;
    }

    public static double getSy(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += dot.y();
        }

        return sum;
    }

    public static double getSxy(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += dot.x() * dot.y();
        }

        return sum;
    }

    public static double getSxxy(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += pow(dot.x(), 2) * dot.y();
        }

        return sum;
    }

    public static double getSxxxy(List<Dot> dots) {
        double sum = 0;
        for (Dot dot : dots) {
            sum += pow(dot.x(), 3) * dot.y();
        }

        return sum;
    }

    public static double countDeterminant(double[][] matrixData) {
        SimpleMatrix matrix = new SimpleMatrix(matrixData);

        return matrix.determinant();
    }
}
