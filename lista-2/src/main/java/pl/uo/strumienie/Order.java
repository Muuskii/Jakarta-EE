package pl.uo.strumienie;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Zamówienie ze sklepu internetowego.
 *
 * Uwaga: dla prostoty kwota jest typu double. W prawdziwym systemie kwoty
 * przechowuje się jako BigDecimal albo w groszach (long).
 */
public record Order(int id, String customer, String city, LocalDate date,
                    String category, double amount, OrderStatus status) {

    /** Czy zamówienie zostało anulowane. Anulowane zamówienia nie liczą się do przychodu. */
    public boolean isCancelled() {
        return status == OrderStatus.ANULOWANE;
    }

    /**
     * ZADANIE 1a. Zamienia jeden wiersz pliku CSV na zamówienie.
     *
     * Format wiersza: id;klient;miasto;data;kategoria;kwota;status, np.
     *   1;Ewa Wiśniewska;Poznań;2025-01-01;Elektronika;9235,29;ZREALIZOWANE
     *
     * Wiersz jest poprawny, gdy:
     *  - ma dokładnie 7 pól oddzielonych średnikiem,
     *  - id jest liczbą całkowitą,
     *  - klient, miasto i kategoria nie są puste (po obcięciu spacji),
     *  - data jest poprawną datą w formacie rrrr-mm-dd,
     *  - kwota jest liczbą z przecinkiem dziesiętnym i jest większa od zera,
     *  - status jest dokładnie jedną z nazw z OrderStatus (wielkimi literami).
     *
     * @return zamówienie albo Optional.empty() dla wiersza niepoprawnego – metoda nigdy nie rzuca wyjątku
     */
    public static Optional<Order> parse(String line) {
        if (line == null) {
            return Optional.empty();
        }

        // limit -1: puste pola na końcu wiersza (np. "a;b;;") też się liczą jako pola
        String[] parts = line.split(";", -1);
        if (parts.length != 7) {
            return Optional.empty();
        }

        try {
            int id = Integer.parseInt(parts[0]);

            String customer = parts[1].trim();
            String city = parts[2].trim();
            String category = parts[4].trim();
            if (customer.isEmpty() || city.isEmpty() || category.isEmpty()) {
                return Optional.empty();
            }

            LocalDate date = LocalDate.parse(parts[3]);

            // tylko cyfry z opcjonalnym przecinkiem dziesiętnym (odrzuca kropkę, minus, "NaN", "1e5")
            if (!parts[5].matches("\\d+(,\\d+)?")) {
                return Optional.empty();
            }
            double amount = Double.parseDouble(parts[5].replace(',', '.'));
            if (amount <= 0) {
                return Optional.empty();
            }

            OrderStatus status = OrderStatus.valueOf(parts[6]);

            return Optional.of(new Order(id, customer, city, date, category, amount, status));
        } catch (IllegalArgumentException | DateTimeException e) {
            // NumberFormatException i błędny status (valueOf) to IllegalArgumentException,
            // błędna data to DateTimeParseException (podklasa DateTimeException)
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return String.format("#%-4d %-20s %-9s %s  %-12s %10.2f zł  %s",
                id, customer, city, date, category, amount, status);
    }
}