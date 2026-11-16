package pl.uo.strumienie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie dodatkowe – kolektor mediany")
class ZadanieDodatkoweTest {

    @Test
    @DisplayName("mediana dla nieparzystej i parzystej liczby elementów")
    void medianOfSmallSets() {
        assertEquals(2.0, Stream.of(3.0, 1.0, 2.0).collect(ZadanieDodatkowe.median()), 1e-9);
        assertEquals(2.5, Stream.of(4.0, 1.0, 3.0, 2.0).collect(ZadanieDodatkowe.median()), 1e-9);
        assertEquals(7.0, Stream.of(7.0).collect(ZadanieDodatkowe.median()), 1e-9);
    }

    @Test
    @DisplayName("mediana pustego strumienia to NaN")
    void medianOfEmptyStream() {
        assertTrue(Double.isNaN(Stream.<Double>empty().collect(ZadanieDodatkowe.median())));
    }

    @Test
    @DisplayName("mediana kwot wszystkich zamówień")
    void medianOfOrders() {
        assertEquals(358.42, TestData.orders().stream().map(Order::amount).collect(ZadanieDodatkowe.median()), 0.001);
    }
}
