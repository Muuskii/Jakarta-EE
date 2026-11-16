package pl.uo.lambdy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 4 – reguły cenowe")
class RulesTest {

    private static final double EPS = 1e-9;

    @Test
    @DisplayName("discount, minus i minimum")
    void simpleRules() {
        assertEquals(80.00, Rules.discount(20).apply(100), EPS);
        assertEquals(70.00, Rules.minus(10).apply(80), EPS);
        assertEquals(50.00, Rules.minimum(50).apply(30), EPS);
        assertEquals(70.00, Rules.minimum(50).apply(70), EPS);
    }

    @ParameterizedTest(name = "{0} → {1}")
    @CsvSource({"72.30, 71.99", "72.00, 71.99", "71.99, 71.99", "15.50, 14.99"})
    @DisplayName("roundTo99 zaokrągla w dół do końcówki ,99")
    void roundTo99(double price, double expected) {
        assertEquals(expected, Rules.roundTo99().apply(price), EPS);
    }

    @Test
    @DisplayName("fromConfig składa reguły w podanej kolejności")
    void fromConfigComposesInOrder() {
        PriceRule rule = Rules.fromConfig(List.of("discount:20", "minus:10", "minimum:50", "round99"));

        assertEquals(69.99, rule.apply(100), EPS);
        assertEquals(70.00, Rules.fromConfig(List.of("discount:20", "minus:10")).apply(100), EPS);
        assertEquals(72.00, Rules.fromConfig(List.of("minus:10", "discount:20")).apply(100), EPS);
    }

    @Test
    @DisplayName("fromConfig: pusta konfiguracja nie zmienia ceny")
    void emptyConfigIsIdentity() {
        assertEquals(123.45, Rules.fromConfig(List.of()).apply(123.45), EPS);
    }

    @Test
    @DisplayName("fromConfig: nieznana reguła → IllegalArgumentException")
    void unknownRuleIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> Rules.fromConfig(List.of("bonus:5")));
    }
}
