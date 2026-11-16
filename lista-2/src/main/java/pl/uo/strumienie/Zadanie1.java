package pl.uo.strumienie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Zadanie 1 – wczytanie danych.
 *
 * Najpierw zaimplementuj Order.parse (zadanie 1a), potem metodę load (zadanie 1b).
 * Od działania metody load zależą programy main w pozostałych zadaniach (testy – nie).
 */
public class Zadanie1 {

    /**
     * ZADANIE 1b. Wczytuje plik CSV za pomocą Files.lines.
     *  - pomija pierwszy wiersz (nagłówek),
     *  - pomija wiersze puste (nie liczą się jako błędne),
     *  - pozostałe wiersze zamienia na zamówienia metodą Order.parse,
     *  - zwraca listę poprawnych zamówień i liczbę wierszy odrzuconych.
     */
    public static LoadResult load(Path file) throws IOException {
        // try-with-resources: strumień z Files.lines trzyma otwarty plik, więc trzeba go zamknąć
        try (Stream<String> lines = Files.lines(file)) {
            Map<Boolean, List<Optional<Order>>> parsed = lines
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(Order::parse)
                    .collect(Collectors.partitioningBy(Optional::isPresent));

            List<Order> orders = parsed.get(true).stream()
                    .map(Optional::get)
                    .toList();
            int rejected = parsed.get(false).size();

            return new LoadResult(orders, rejected);
        }
    }

    public static void main(String[] args) throws IOException {
        LoadResult result = load(DataFiles.ordersCsv());

        System.out.println("Wczytano zamówień: " + result.orders().size());
        System.out.println("Odrzucono wierszy: " + result.rejected());
        System.out.println("Pierwsze trzy zamówienia:");
        result.orders().stream().limit(3).forEach(System.out::println);
    }
}