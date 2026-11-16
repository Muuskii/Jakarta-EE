package pl.uo.strumienie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.List;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 2 – proste raporty")
class Zadanie2Test {

    private final List<Order> orders = TestData.orders();

    @Test
    @DisplayName("a) liczba zamówień zrealizowanych")
    void countCompleted() {
        assertEquals(533L, Zadanie2.countCompleted(orders));
    }

    @Test
    @DisplayName("b) pięć największych zamówień, od największego")
    void top5ByAmount() {
        List<Order> top = Zadanie2.top5ByAmount(orders);

        assertEquals(List.of(59, 66, 689, 193, 825), top.stream().map(Order::id).toList());
        assertEquals(14844.48, top.get(0).amount(), 0.001);
    }

    @Test
    @DisplayName("c) miasta bez powtórzeń, posortowane")
    void sortedCities() {
        assertEquals(List.of("Gdańsk", "Katowice", "Kraków", "Opole", "Poznań", "Warszawa", "Wrocław", "Łódź"),
                Zadanie2.sortedCities(orders));
    }

    @Test
    @DisplayName("d) czy istnieje zamówienie powyżej progu")
    void anyAbove() {
        assertTrue(Zadanie2.anyAbove(orders, 10_000));
        assertFalse(Zadanie2.anyAbove(orders, 14844.48), "Próg równy największej kwocie – „większa niż” jest ostra");
    }

    @Test
    @DisplayName("e) średnia kwota w miesiącu; pusta, gdy brak zamówień")
    void averageAmountIn() {
        OptionalDouble march = Zadanie2.averageAmountIn(orders, YearMonth.of(2025, 3));
        assertTrue(march.isPresent());
        assertEquals(2354.452551, march.getAsDouble(), 0.001);

        assertTrue(Zadanie2.averageAmountIn(orders, YearMonth.of(2024, 1)).isEmpty());
    }
}
