package pl.uo.strumienie;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Zadanie 5 – Optional.
 *
 * W żadnej metodzie nie używaj get(), isPresent(), isEmpty() ani wartości null.
 * Korzystaj z map, orElse, orElseGet, orElseThrow.
 */
public class Zadanie5 {

    /** a) Zamówienie o największej kwocie w podanym mieście; pusty Optional, jeśli w mieście nie było zamówień. */
    public static Optional<Order> findLargestIn(List<Order> orders, String city) {
        return orders.stream()
                .filter(o -> o.city().equals(city))
                .max(Comparator.comparingDouble(Order::amount));
    }

    /**
     * b) Opis największego zamówienia w mieście, np.
     *    "Największe zamówienie w mieście Opole: #767 na kwotę 14400,83 zł",
     *    a gdy zamówień nie było: "Brak zamówień w mieście Zakopane".
     *    Skorzystaj z findLargestIn.
     */
    public static String describeLargestIn(List<Order> orders, String city) {
        return findLargestIn(orders, city)
                .map(o -> String.format(Locale.forLanguageTag("pl-PL"),
                        "Największe zamówienie w mieście %s: #%d na kwotę %.2f zł",
                        city, o.id(), o.amount()))
                .orElseGet(() -> "Brak zamówień w mieście " + city);
    }

    /** c) Zamówienie o podanym id. */
    public static Optional<Order> findById(List<Order> orders, int id) {
        return orders.stream()
                .filter(o -> o.id() == id)
                .findFirst();
    }

    /** d) Nazwa klienta, który złożył zamówienie o podanym id, albo "nieznany klient". Skorzystaj z findById. */
    public static String customerOf(List<Order> orders, int id) {
        return findById(orders, id)
                .map(Order::customer)
                .orElse("nieznany klient");
    }

    public static void main(String[] args) throws IOException {
        List<Order> orders = Zadanie1.load(DataFiles.ordersCsv()).orders();

        System.out.println(describeLargestIn(orders, "Opole"));
        System.out.println(describeLargestIn(orders, "Zakopane"));
        System.out.println("Zamówienie #500 złożył(a): " + customerOf(orders, 500));
        System.out.println("Zamówienie #5000 złożył(a): " + customerOf(orders, 5000));
    }
}