package utils;

import model.Dot;
import model.Result;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class PrettyPrinter {
    public static void printString(String s) {
        System.out.println(s);
    }

    public static void clearFile(Path path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toString(), false))){
            bufferedWriter.write("");
        } catch (IOException e) {
            printString("Файла по заданному пути нет, попробуйте ещё раз");
        }
    }

    public static void printStringInFile(String s, Path path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toString(), true))){
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        } catch (IOException e) {
            printString("Файла по заданному пути нет, попробуйте ещё раз");
        }
    }

    public static void printResultInConsole(Result result) {
        printString(getApproximationCoefficients(result));
        printString("Стандартное отклонение: s = " + String.format("%.5f", result.standardDeviation()));
        printString("Среднеквадратичное отклонение: " + String.format("%.5f", result.squareDeviation()));
        printString(getApproximationDots(result));
        printString(getApproximationFunctionValues(result));
        printString(getEpsilon(result));
        printString(determinationMessage(result.determination()));
    }

    public static void printResultInFile(Result result, Path path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toString(), true))) {
            bufferedWriter.write(getApproximationCoefficients(result));
            bufferedWriter.write("Стандартное отклонение: " + String.format("%.5f", result.standardDeviation()) + "\n");
            bufferedWriter.write("Среднеквадратичное отклонение: " + String.format("%.5f", result.squareDeviation()) + "\n");
            bufferedWriter.write(getApproximationDots(result) + "\n");
            bufferedWriter.write(getApproximationFunctionValues(result) + "\n");
            bufferedWriter.write(getEpsilon(result) + "\n");
            bufferedWriter.write(determinationMessage(result.determination()) + "\n");
        } catch (IOException e) {
            printString("Файла по заданному пути нет, попробуйте ещё раз");
        }
    }
    public static String getHeaderForResultTable(int columns) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("№").append(String.format("%" + 13 + "s", ""));
        for (int i = 1; i <= columns; i++) {
            stringBuilder.append(i).append(String.format("%" + 13 + "s", ""));
        }

        return stringBuilder.append("\n").toString();
    }

    public static String getApproximationCoefficients(Result result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Коэффициенты аппроксимации: ").append("\n");
        int i = 0;
        for (Double coefficient: result.coefficients()) {
            stringBuilder.append("a").append(i++).append(" = ").append(coefficient).append("\n");
        }

        return stringBuilder.toString();
    }

    public static String getApproximationDots(Result result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getHeaderForResultTable(result.approximationDots().size()));

        StringBuilder xValues = new StringBuilder();
        xValues.append("X").append(String.format("%" + 10 + "s", ""));

        StringBuilder yValues = new StringBuilder();
        yValues.append("Y").append(String.format("%" + 10 + "s", ""));

        for (Dot dot: result.approximationDots()) {
            xValues.append(String.format("%.5f", dot.x())).append(String.format("%" + 7 + "s", ""));
            yValues.append(String.format("%.5f", dot.y())).append(String.format("%" + 7 + "s", ""));
        }

        stringBuilder.append(xValues).append("\n");
        stringBuilder.append(yValues);

        return stringBuilder.toString();
    }

    public static String getApproximationFunctionValues(Result result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phi(x)").append(String.format("%" + 5 + "s", ""));
        for (Double val: result.approximationFuncValues()) {
            stringBuilder.append(String.format("%.5f", val)).append(String.format("%" + 7 + "s", ""));
        }

        return stringBuilder.toString();
    }

    public static String getEpsilon(Result result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("e_i").append(String.format("%" + 7 + "s", ""));
        for (Double val: result.eps()) {
            stringBuilder.append(String.format("%.5f", val)).append(String.format("%" + 7 + "s", ""));
        }

        return stringBuilder.append("\n").toString();
    }

    public static String determinationMessage(double determination) {
        if (determination >= 0.95) {
            return "Детерминация:\nR^2 = " + String.format("%.5f", determination) + ": высокая точность аппроксимации\n";
        }
        if (determination >= 0.75) {
            return "Детерминация:\nR^2 = " + String.format("%.5f", determination) + ": удовлетворительная точность аппроксимации\n";
        }
        if (determination >= 0.5) {
            return "Детерминация:\nR^2 = " + String.format("%.5f", determination) + ": слабая точность аппроксимации\n";
        }

        return "Детерминация:\nR^2 = " + String.format("%.5f", determination) + ": недостаточная точность аппроксимации\n";
    }
}
