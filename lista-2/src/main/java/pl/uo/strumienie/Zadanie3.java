package pl.uo.strumienie;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Zadanie 3 – kolektory.
 *
 * Każdą metodę napisz jednym potokiem strumienia zakończonym collect(...)
 * (w metodzie c dozwolony jest jeden dodatkowy potok liczący średnią).
 * Przychód = suma kwot zamówień, które NIE są anulowane.
 */
public class Zadanie3 {

    /** a) Przychód w każdym mieście; mapa posortowana po nazwie miasta (TreeMap). */
    public static Map<String, Double> revenueByCity(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::city,
                        TreeMap::new,
                        Collectors.filtering(
                                o -> !o.isCancelled(),
                                Collectors.summingDouble(Order::amount))));
    }

    /** b) Liczba zamówień: kategoria → (status → liczba zamówień). */
    public static Map<String, Map<OrderStatus, Long>> countByCategoryAndStatus(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        Collectors.groupingBy(Order::status, Collectors.counting())));
    }

    /**
     * c) Podział zamówień: true → kwota większa od średniej kwoty wszystkich zamówień,
     *    false → pozostałe.
     */
    public static Map<Boolean, List<Order>> partitionByAverage(List<Order> orders) {
        // dodatkowy potok liczący średnią (dozwolony w tym zadaniu)
        double average = orders.stream()
                .mapToDouble(Order::amount)
                .average()
                .orElse(0.0);

        return orders.stream()
                .collect(Collectors.partitioningBy(o -> o.amount() > average));
    }

    /** d) Klient → lista identyfikatorów jego zamówień (w kolejności z pliku). */
    public static Map<String, List<Integer>> orderIdsByCustomer(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::customer,
                        Collectors.mapping(Order::id, Collectors.toList())));
    }

    /** e) Miasto → klient, który przyniósł w tym mieście największy przychód. */
    public static Map<String, String> bestCustomerByCity(List<Order> orders) {
        return orders.stream()
                .filter(o -> !o.isCancelled())
                .collect(Collectors.groupingBy(
                        Order::city,
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(Order::customer, Collectors.summingDouble(Order::amount)),
                                revenueByCustomer -> revenueByCustomer.entrySet().stream()
                                        .max(Map.Entry.comparingByValue())
                                        .orElseThrow()
                                        .getKey())));
    }

    public static void main(String[] args) throws IOException {
        List<Order> orders = Zadanie1.load(DataFiles.ordersCsv()).orders();

        System.out.println("a) Przychód w miastach:");
        revenueByCity(orders).forEach((city, revenue) -> System.out.printf("   %-10s %12.2f zł%n", city, revenue));
        System.out.println("b) Elektronika według statusów: " + countByCategoryAndStatus(orders).get("Elektronika"));
        Map<Boolean, List<Order>> parts = partitionByAverage(orders);
        System.out.println("c) Powyżej średniej: " + parts.get(true).size() + ", pozostałe: " + parts.get(false).size());
        System.out.println("d) Zamówienia Anny Nowak: " + orderIdsByCustomer(orders).get("Anna Nowak"));
        System.out.println("e) Najlepsi klienci w miastach: " + bestCustomerByCity(orders));
    }
}