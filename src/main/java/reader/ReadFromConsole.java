package reader;

import model.Dot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.PrettyPrinter.printString;

public class ReadFromConsole implements Readable {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<Dot> read() {
        List<Dot> dots = new ArrayList<>();
        while (true) {
            printString("Введите точки и значения функции в них (парами через пробел, каждая пара на новой строке):");
            while (scanner.hasNextDouble()) {
                Dot dot = new Dot(scanner.nextDouble(), scanner.nextDouble());
                dots.add(dot);
            }

            if (dots.size() >= 8) {
                return dots;
            } else {
                printString("Количество точек должно быть не меньше 8, попробуйте ещё раз");
                scanner.next();
            }
        }
    }
}
