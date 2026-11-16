package pl.uo.strumienie;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Zadanie 4 – statystyki.
 *
 * Każdą metodę napisz jednym potokiem strumienia.
 */
public class Zadanie4 {

    /**
     * a) Najwcześniejsza i najpóźniejsza data zamówienia, wyznaczone w JEDNYM przejściu
     *    przez dane – użyj kolektora Collectors.teeing. Zakładamy, że lista nie jest pusta.
     */
    public static DateRange dateRange(List<Order> orders) {
        return orders.stream()
                .map(Order::date)
                .collect(Collectors.teeing(
                        Collectors.minBy(Comparator.<LocalDate>naturalOrder()),
                        Collectors.maxBy(Comparator.<LocalDate>naturalOrder()),
                        (min, max) -> new DateRange(min.orElseThrow(), max.orElseThrow())));
    }

    /** b) Statystyki kwot wszystkich zamówień: liczba, minimum, maksimum, suma, średnia. */
    public static DoubleSummaryStatistics amountStatistics(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.summarizingDouble(Order::amount));
    }

    /** c) Przychód (bez anulowanych) w każdym miesiącu; mapa posortowana chronologicznie. */
    public static Map<YearMonth, Double> revenueByMonth(List<Order> orders) {
        return orders.stream()
                .filter(o -> !o.isCancelled())
                .collect(Collectors.groupingBy(
                        o -> YearMonth.from(o.date()),
                        TreeMap::new,
                        Collectors.summingDouble(Order::amount)));
    }

    public static void main(String[] args) throws IOException {
        List<Order> orders = Zadanie1.load(DataFiles.ordersCsv()).orders();

        System.out.println("a) Zakres dat: " + dateRange(orders));
        System.out.println("b) Statystyki kwot: " + amountStatistics(orders));
        System.out.println("c) Przychód w miesiącach:");
        revenueByMonth(orders).forEach((month, revenue) -> System.out.printf("   %s %12.2f zł%n", month, revenue));
    }
}