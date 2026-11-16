package pl.uo.strumienie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dane do testów zadań 2–5 i dodatkowego: 1000 poprawnych zamówień z pliku
 * src/test/resources/orders-clean.csv. Dzięki temu testy tych zadań nie zależą
 * od rozwiązania zadania 1.
 */
final class TestData {

    private static List<Order> cache;

    private TestData() {
    }

    static synchronized List<Order> orders() {
        if (cache == null) {
            List<Order> list = new ArrayList<>();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    TestData.class.getResourceAsStream("/orders-clean.csv"), StandardCharsets.UTF_8))) {
                in.readLine(); // nagłówek
                String line;
                while ((line = in.readLine()) != null) {
                    String[] f = line.split(";");
                    list.add(new Order(Integer.parseInt(f[0]), f[1], f[2], LocalDate.parse(f[3]), f[4],
                            Double.parseDouble(f[5].replace(',', '.')), OrderStatus.valueOf(f[6])));
                }
            } catch (Exception e) {
                throw new IllegalStateException("Nie udało się wczytać danych testowych", e);
            }
            cache = Collections.unmodifiableList(list);
        }
        return cache;
    }
}
