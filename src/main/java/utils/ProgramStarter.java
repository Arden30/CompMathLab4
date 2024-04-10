package utils;

import approximations.Approximation;
import approximations.ApproximationStorage;
import model.Dot;
import model.Result;
import reader.ReadFromConsole;
import reader.ReadFromFile;
import reader.Readable;

import javax.swing.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.PrettyPrinter.*;

public class ProgramStarter {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Approximation> approximations = ApproximationStorage.getApproximations();
    public void start() {
        while (true) {
            try {
                int formatInput = readFormatInput();

                Readable readable;
                switch (formatInput) {
                    case 1 -> readable = new ReadFromConsole();
                    case 2 -> readable = new ReadFromFile(readPath());
                    default -> throw new Exception("Такого номера нет, попробуйте ещё раз");
                }

                List<Dot> dots = readable.read();

                int formatOutput = readFormatOutput();
                Path outPath = null;
                if (formatOutput == 2) {
                    outPath = readPath();
                    clearFile(outPath);
                }
                double squareDeviation = Double.MAX_VALUE;
                Approximation bestApproximation = null;
                String bestApproximationName = "";

                List<Result> results = new ArrayList<>();

                int i = 1;
                List<Approximation> doneApproximations = new ArrayList<>();
                for (Approximation approximation: approximations) {
                    if (formatOutput == 1) {
                        printString(i++ + ")" + " " + approximation.name());
                    } else printStringInFile(i++ + ")" + " " + approximation.name(), outPath);

                    Result result = approximation.approximate(dots);
                    if (result.coefficients().get(0).isNaN()) {
                        printString("Проблемы с областью определения аппроксимирующей функции (например, отрицательная точка для логарифма). Аппроксимация невозможна\n");
                        continue;
                    }
                    results.add(result);

                    if (result.correlation() != null) {
                        String str = "Коэффициент корреляции: " + String.format("%.5f", result.correlation());
                        switch (formatOutput) {
                            case 1 -> printString(str);
                            case 2 -> printStringInFile(str, outPath);
                        }
                    }

                    if (result.squareDeviation() < squareDeviation) {
                        bestApproximation = approximation;
                        bestApproximationName = result.approximationFunction();
                        squareDeviation = result.squareDeviation();
                    }

                    doneApproximations.add(approximation);
                    outputResult(formatOutput, result, outPath);

                    String str = "Аппроксимация:\n" + approximation.function(result.coefficients());
                    switch (formatOutput) {
                        case 1 -> printString(str + "\n");
                        case 2 -> printStringInFile(str + "\n", outPath);
                    }
                }

                String str = "Лучшая аппроксимация:\n" + bestApproximation.name() + ": " + bestApproximationName;
                switch (formatOutput) {
                    case 1 -> printString(str);
                    case 2 -> {
                        printStringInFile(str, outPath);
                        printString("Результаты были успешно выведены в файл " + outPath);
                    }
                }

                SwingUtilities.invokeLater(() -> new ChartsBuilder(doneApproximations, results));

                if (scanner.hasNext()) {
                    System.exit(0);
                }

            } catch (Exception e) {
                printString(e.getMessage());
                scanner.next();
            }
        }
    }

    private int readFormatInput() {
        while (true) {
            try {
                printString("Выберите формат ввода: консоль (введите 1) или файл (введите 2)");
                int format = scanner.nextInt();

                if (format != 1 && format != 2) {
                    throw new IllegalArgumentException("Такого номера нет, попробуйте ещё раз");
                }

                return format;
            } catch (IllegalArgumentException e) {
                printString(e.getMessage());
            } catch (Exception e) {
                printString("Ошибка ввода формата, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private Path readPath() {
        while (true) {
            try {
                printString("Укажите путь к файлу: ");

                return Path.of(scanner.next());
            } catch (Exception e) {
                printString("Файла по заданному пути нет, попробуйте ещё раз");
            }
        }
    }

    private int readFormatOutput() {
        while (true) {
            try {
                printString("Выберите формат вывода: консоль (введите 1) или файл (введите 2)");
                int format = scanner.nextInt();

                if (format != 1 && format != 2) {
                    throw new IllegalArgumentException("Такого номера нет, попробуйте ещё раз");
                }

                return format;
            } catch (IllegalArgumentException e) {
                printString(e.getMessage());
            } catch (Exception e) {
                printString("Ошибка ввода формата, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private void outputResult(int format, Result result, Path path) throws Exception {
        switch (format) {
            case 1 -> printResultInConsole(result);
            case 2 -> printResultInFile(result, path);
            default -> throw new Exception("Такого формата нет, попробуйте ещё раз");
        }
    }
}
