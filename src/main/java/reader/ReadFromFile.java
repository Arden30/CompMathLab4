package reader;

import model.Dot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.PrettyPrinter.printString;

public class ReadFromFile implements Readable {
    private final Path path;

    public ReadFromFile(Path path) {
        this.path = path;
    }

    @Override
    public List<Dot> read() {
        List<Dot> dots = new ArrayList<>();
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextDouble()) {
                Dot dot = new Dot(scanner.nextDouble(), scanner.nextDouble());
                dots.add(dot);
            }

            if (dots.size() < 7) {
                printString("Количество точек должно быть не меньше 8, обновите файл");
                System.exit(1);
            }
        } catch (IOException e) {
            printString("Такого пути не существует");
            System.exit(1);
        }

        return dots;
    }
}
