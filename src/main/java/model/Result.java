package model;

import java.util.List;

public record Result(List<Double> coefficients,
                     double standardDeviation,
                     List<Dot> approximationDots,
                     List<Double> approximationFuncValues,
                     List<Double> eps,
                     String approximationFunction,
                     Double correlation,
                     double determination,
                     double squareDeviation) {
}
