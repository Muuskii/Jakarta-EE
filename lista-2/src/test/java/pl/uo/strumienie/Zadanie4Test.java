package pl.uo.strumienie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 4 – statystyki")
class Zadanie4Test {

    private final List<Order> orders = TestData.orders();

    @Test
    @DisplayName("a) zakres dat zamówień")
    void dateRange() {
        assertEquals(new DateRange(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31)),
                Zadanie4.dateRange(orders));
    }

    @Test
    @DisplayName("b) statystyki kwot")
    void amountStatistics() {
        DoubleSummaryStatistics stats = Zadanie4.amountStatistics(orders);

        assertEquals(1000L, stats.getCount());
        assertEquals(15.06, stats.getMin(), 0.001);
        assertEquals(14844.48, stats.getMax(), 0.001);
        assertEquals(1571656.47, stats.getSum(), 0.01);
        assertEquals(1571.65647, stats.getAverage(), 0.0001);
    }

    @Test
    @DisplayName("c) przychód w miesiącach, chronologicznie")
    void revenueByMonth() {
        Map<YearMonth, Double> revenue = Zadanie4.revenueByMonth(orders);

        List<YearMonth> months = new ArrayList<>(revenue.keySet());
        assertEquals(12, months.size());
        assertEquals(YearMonth.of(2025, 1), months.get(0), "Mapa powinna być posortowana chronologicznie");
        assertEquals(YearMonth.of(2025, 12), months.get(11));
        assertEquals(111581.83, revenue.get(YearMonth.of(2025, 1)), 0.01);
        assertEquals(1394655.09, revenue.values().stream().mapToDouble(Double::doubleValue).sum(), 0.05,
                "Suma przychodów miesięcznych (bez anulowanych)");
    }
}
