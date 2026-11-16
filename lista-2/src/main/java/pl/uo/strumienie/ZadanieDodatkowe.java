package pl.uo.strumienie;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;

/**
 * Zadanie dodatkowe – własny kolektor.
 *
 * Za pomocą Collector.of napisz kolektor wyznaczający medianę liczb:
 *  - dla nieparzystej liczby elementów – środkowy element po posortowaniu,
 *  - dla parzystej – średnia dwóch środkowych,
 *  - dla pustego strumienia – Double.NaN.
 */
public final class ZadanieDodatkowe {

    private ZadanieDodatkowe() {
    }

    public static Collector<Double, ?, Double> median() {
        // TODO
        throw new UnsupportedOperationException("TODO: zadanie dodatkowe");
    }

    public static void main(String[] args) throws IOException {
        List<Order> orders = Zadanie1.load(DataFiles.ordersCsv()).orders();

        double median = orders.stream().map(Order::amount).collect(median());
        double average = orders.stream().mapToDouble(Order::amount).average().orElseThrow();
        System.out.printf("Mediana kwot: %.2f zł, średnia kwot: %.2f zł%n", median, average);
    }
}
