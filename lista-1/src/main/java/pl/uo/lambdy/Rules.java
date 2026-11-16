package pl.uo.lambdy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Zadanie 4 – fabryki funkcji.
 *
 * Każda z metod discount, minus, minimum i roundTo99 nie liczy niczego sama:
 * zwraca regułę (lambdę), która zrobi to dopiero przy wywołaniu apply(cena).
 */
public final class Rules {

    private Rules() {
    }

    /** Obniża cenę o podany procent, np. discount(20): 100,00 → 80,00. */
    public static PriceRule discount(double percent) {
        return price -> price * (1 - percent / 100.0);
    }

    /** Odejmuje od ceny stałą kwotę, np. minus(10): 80,00 → 70,00. */
    public static PriceRule minus(double amount) {
        return price -> price - amount;
    }

    /** Pilnuje ceny minimalnej, np. minimum(50): 30,00 → 50,00, a 70,00 → 70,00. */
    public static PriceRule minimum(double min) {
        return price -> Math.max(price, min);
    }

    /**
     * Zaokrągla cenę w dół do najbliższej kwoty z końcówką ,99:
     * 72,30 → 71,99;  72,00 → 71,99;  71,99 → 71,99.
     * Podpowiedź: Math.floor.
     */
    public static PriceRule roundTo99() {
        return price -> {
            // Liczymy w groszach, żeby uniknąć błędów double (np. 71,99 + 0,01 != 72,00).
            long cents = Math.round(price * 100);
            double wholeZlotys = Math.floor((cents + 1) / 100.0);
            return (wholeZlotys * 100 - 1) / 100.0;
        };
    }

    /** Nazwy reguł z konfiguracji → funkcje tworzące regułę z podanego parametru. */
    private static final Map<String, Function<Double, PriceRule>> FACTORIES = Map.of(
            "discount", Rules::discount,
            "minus", Rules::minus,
            "minimum", Rules::minimum,
            "round99", ignored -> roundTo99()   // reguła bez parametru
    );

    /**
     * Zamienia konfigurację tekstową na jedną złożoną regułę. Reguły stosowane są w podanej kolejności.
     * Format wpisu: "nazwa:wartość", np. "discount:20", albo sama nazwa dla reguły bez parametru: "round99".
     * Dla nieznanej nazwy reguły rzuca IllegalArgumentException.
     * Pusta lista daje regułę, która nie zmienia ceny.
     *
     * Przykład: ["discount:20", "minus:10", "minimum:50", "round99"] dla ceny 100,00 daje 69,99.
     */
    public static PriceRule fromConfig(List<String> config) {
        PriceRule result = PriceRule.identity();
        for (String entry : config) {
            String[] parts = entry.split(":", 2);
            String name = parts[0].trim();

            Function<Double, PriceRule> factory = FACTORIES.get(name);
            if (factory == null) {
                throw new IllegalArgumentException("Nieznana reguła: " + name);
            }

            Double value = null;
            if (parts.length == 2) {
                value = Double.parseDouble(parts[1].trim()); // zły format → NumberFormatException (to też IAE)
            } else if (!name.equals("round99")) {
                throw new IllegalArgumentException("Reguła '" + name + "' wymaga parametru");
            }

            result = result.then(factory.apply(value));
        }
        return result;
    }

    public static void main(String[] args) {
        PriceRule rule = fromConfig(List.of("discount:20", "minus:10", "minimum:50", "round99"));
        System.out.printf("100,00 zł → %.2f zł%n", rule.apply(100));
        
        System.out.printf("discount:20, minus:10 → %.2f zł%n",
                fromConfig(List.of("discount:20", "minus:10")).apply(100));
        System.out.printf("minus:10, discount:20 → %.2f zł%n",
                fromConfig(List.of("minus:10", "discount:20")).apply(100));
    }
}