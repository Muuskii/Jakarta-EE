package pl.uo.strumienie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 3 – kolektory")
class Zadanie3Test {

    private final List<Order> orders = TestData.orders();

    @Test
    @DisplayName("a) przychód w miastach (bez anulowanych), posortowany po mieście")
    void revenueByCity() {
        Map<String, Double> revenue = Zadanie3.revenueByCity(orders);

        assertEquals(List.of("Gdańsk", "Katowice", "Kraków", "Opole", "Poznań", "Warszawa", "Wrocław", "Łódź"),
                new ArrayList<>(revenue.keySet()), "Mapa powinna być posortowana po nazwie miasta");
        assertEquals(240662.35, revenue.get("Opole"), 0.01);
        assertEquals(246711.67, revenue.get("Wrocław"), 0.01);
        assertEquals(96140.91, revenue.get("Łódź"), 0.01, "Czy anulowane zamówienia zostały pominięte?");
    }

    @Test
    @DisplayName("b) liczba zamówień według kategorii i statusu")
    void countByCategoryAndStatus() {
        Map<String, Map<OrderStatus, Long>> counts = Zadanie3.countByCategoryAndStatus(orders);

        assertEquals(6, counts.size(), "Sześć kategorii");
        assertEquals(Map.of(OrderStatus.ZREALIZOWANE, 121L, OrderStatus.WYSLANE, 25L, OrderStatus.NOWE, 19L,
                OrderStatus.OPLACONE, 23L, OrderStatus.ANULOWANE, 23L), counts.get("Elektronika"));
        assertEquals(17L, counts.get("Książki").get(OrderStatus.ANULOWANE));
    }

    @Test
    @DisplayName("c) podział na zamówienia powyżej średniej i pozostałe")
    void partitionByAverage() {
        Map<Boolean, List<Order>> parts = Zadanie3.partitionByAverage(orders);

        assertEquals(238, parts.get(true).size());
        assertEquals(762, parts.get(false).size());
        assertTrue(parts.get(true).stream().allMatch(o -> o.amount() > 1571.65647));
    }

    @Test
    @DisplayName("d) klient → identyfikatory zamówień")
    void orderIdsByCustomer() {
        Map<String, List<Integer>> ids = Zadanie3.orderIdsByCustomer(orders);

        assertEquals(30, ids.size(), "Trzydziestu klientów");
        assertEquals(List.of(3, 24, 92, 132, 166, 219, 221, 343, 358, 393, 474, 511, 517, 523, 538, 555,
                559, 569, 609, 623, 653, 678, 686, 798, 940), ids.get("Anna Nowak"));
    }

    @Test
    @DisplayName("e) najlepszy klient w każdym mieście")
    void bestCustomerByCity() {
        assertEquals(Map.of(
                "Opole", "Paweł Dąbrowski",
                "Wrocław", "Magdalena Woźniak",
                "Kraków", "Magdalena Woźniak",
                "Katowice", "Monika Nowicka",
                "Warszawa", "Adam Piotrowski",
                "Poznań", "Ewa Wiśniewska",
                "Gdańsk", "Agnieszka Zielińska",
                "Łódź", "Bartosz Nowakowski"), Zadanie3.bestCustomerByCity(orders));
    }
}
