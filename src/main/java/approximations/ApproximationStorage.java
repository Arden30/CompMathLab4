package approximations;

import java.util.ArrayList;
import java.util.List;

public class ApproximationStorage {
    private final static List<Approximation> APPROXIMATIONS = new ArrayList<>();

    public static List<Approximation> getApproximations() {
        setApproximations();

        return APPROXIMATIONS;
    }

    private static void setApproximations() {
        APPROXIMATIONS.add(new LinearApproximation());
        APPROXIMATIONS.add(new SquareApproximation());
        APPROXIMATIONS.add(new CubicApproximation());
        APPROXIMATIONS.add(new PowerApproximation());
        APPROXIMATIONS.add(new LogarithmicApproximation());
        APPROXIMATIONS.add(new ExponentialApproximation());
    }
}
