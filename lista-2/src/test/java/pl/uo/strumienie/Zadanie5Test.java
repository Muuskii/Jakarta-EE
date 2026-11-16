package pl.uo.strumienie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 5 – Optional")
class Zadanie5Test {

    private final List<Order> orders = TestData.orders();

    @Test
    @DisplayName("a) największe zamówienie w mieście")
    void findLargestIn() {
        assertEquals(Optional.of(767), Zadanie5.findLargestIn(orders, "Opole").map(Order::id));
        assertEquals(Optional.of(230), Zadanie5.findLargestIn(orders, "Łódź").map(Order::id));
        assertEquals(Optional.empty(), Zadanie5.findLargestIn(orders, "Zakopane"));
    }

    @Test
    @DisplayName("b) opis największego zamówienia")
    void describeLargestIn() {
        String opole = Zadanie5.describeLargestIn(orders, "Opole");
        assertTrue(opole.startsWith("Największe zamówienie w mieście Opole: #767 na kwotę 14400"),
                "Otrzymano: " + opole);
        assertTrue(opole.endsWith(" zł"), "Otrzymano: " + opole);

        assertEquals("Brak zamówień w mieście Zakopane", Zadanie5.describeLargestIn(orders, "Zakopane"));
    }

    @Test
    @DisplayName("c) zamówienie po id")
    void findById() {
        assertEquals(Optional.of("Ewa Wiśniewska"), Zadanie5.findById(orders, 1).map(Order::customer));
        assertEquals(Optional.empty(), Zadanie5.findById(orders, 5000));
    }

    @Test
    @DisplayName("d) klient zamówienia albo „nieznany klient”")
    void customerOf() {
        assertEquals("Ewa Wiśniewska", Zadanie5.customerOf(orders, 1));
        assertEquals("nieznany klient", Zadanie5.customerOf(orders, 5000));
    }
}
