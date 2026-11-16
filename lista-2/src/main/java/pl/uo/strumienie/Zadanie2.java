package pl.uo.strumienie;

import java.io.IOException;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Zadanie 2 – proste raporty.
 *
 * Każdą metodę napisz jednym potokiem strumienia (bez pętli for / while).
 */
public class Zadanie2 {

    /** a) Liczba zamówień ze statusem ZREALIZOWANE. */
    public static long countCompleted(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.status() == OrderStatus.ZREALIZOWANE)
                .count();
    }

    /** b) Pięć zamówień o największej kwocie, od największej. */
    public static List<Order> top5ByAmount(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::amount).reversed())
                .limit(5)
                .toList();
    }

    /** c) Nazwy miast bez powtórzeń, posortowane alfabetycznie (naturalny porządek String). */
    public static List<String> sortedCities(List<Order> orders) {
        return orders.stream()
                .map(Order::city)
                .distinct()
                .sorted()
                .toList();
    }

    /** d) Czy istnieje zamówienie o kwocie większej niż threshold. */
    public static boolean anyAbove(List<Order> orders, double threshold) {
        return orders.stream()
                .anyMatch(o -> o.amount() > threshold);
    }

    /** e) Średnia kwota zamówień złożonych w podanym miesiącu; pusta, jeśli w tym miesiącu nie było zamówień. */
    public static OptionalDouble averageAmountIn(List<Order> orders, YearMonth month) {
        return orders.stream()
                .filter(o -> YearMonth.from(o.date()).equals(month))
                .mapToDouble(Order::amount)
                .average();
    }

    public static void main(String[] args) throws IOException {
        List<Order> orders = Zadanie1.load(DataFiles.ordersCsv()).orders();

        System.out.println("a) Zrealizowane: " + countCompleted(orders));
        System.out.println("b) Pięć największych:");
        top5ByAmount(orders).forEach(System.out::println);
        System.out.println("c) Miasta: " + sortedCities(orders));
        System.out.println("d) Czy jest zamówienie powyżej 10 000 zł? " + anyAbove(orders, 10_000));
        System.out.println("e) Średnia w marcu 2025: " + averageAmountIn(orders, YearMonth.of(2025, 3)));
        System.out.println("e) Średnia w styczniu 2024: " + averageAmountIn(orders, YearMonth.of(2024, 1)));
    }
}