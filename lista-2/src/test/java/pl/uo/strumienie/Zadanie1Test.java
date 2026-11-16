package pl.uo.strumienie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 1 – wczytanie danych")
class Zadanie1Test {

    @Test
    @DisplayName("1a) parse: poprawny wiersz daje zamówienie")
    void parsesValidLine() {
        Optional<Order> order = Order.parse("1;Ewa Wiśniewska;Poznań;2025-01-01;Elektronika;9235,29;ZREALIZOWANE");

        assertEquals(Optional.of(new Order(1, "Ewa Wiśniewska", "Poznań", LocalDate.of(2025, 1, 1),
                "Elektronika", 9235.29, OrderStatus.ZREALIZOWANE)), order);
    }

    @Test
    @DisplayName("1a) parse: niepoprawne wiersze dają pusty Optional (bez wyjątku)")
    void rejectsInvalidLines() {
        List<String> invalid = List.of(
                "9001;Jan Kowalski;Opole;2025-03-12;Dom i ogród;abc;NOWE",
                "9002;Jan Kowalski;Opole;2025-03-12;Dom i ogród;120,00",
                "9003;Anna Nowak;Kraków;2025-02-30;Książki;45,90;ZREALIZOWANE",
                "9004;Anna Nowak;Kraków;2025-05-10;Książki;45,90;ZAGUBIONE",
                "9005;Piotr Kowalski;Wrocław;2025-06-01;Sport;-50,00;ZREALIZOWANE",
                "9006;Piotr Kowalski;Wrocław;2025-06-02;Sport;0,00;ZREALIZOWANE",
                "9007;Ewa Wiśniewska;Gdańsk;2025-07-15;Odzież;199,99;WYSLANE;pilne",
                "9008; ;Gdańsk;2025-07-16;Odzież;89,00;WYSLANE",
                "9009;Marek Wójcik;Poznań;2025-08-20;Zabawki;59,00;zrealizowane",
                "X10;Marek Wójcik;Poznań;2025-08-21;Zabawki;59,00;NOWE",
                "9011;Marek Wójcik;Poznań;2025-08-21;Zabawki;59,00;");

        for (String line : invalid) {
            Optional<Order> result = assertDoesNotThrow(() -> Order.parse(line),
                    "parse nie może rzucać wyjątku – wiersz: " + line);
            assertEquals(Optional.empty(), result, "Ten wiersz powinien zostać odrzucony: " + line);
        }
    }

    @Test
    @DisplayName("1b) load: 1000 poprawnych zamówień, 10 odrzuconych wierszy")
    void loadsFile() throws Exception {
        LoadResult result = Zadanie1.load(DataFiles.ordersCsv());

        assertEquals(1000, result.orders().size(), "Liczba poprawnych zamówień");
        assertEquals(10, result.rejected(), "Liczba odrzuconych wierszy (pusta linia się nie liczy)");
        assertEquals(1, result.orders().get(0).id(), "Pierwsze zamówienie (nagłówek pominięty?)");
        assertEquals(1000, result.orders().get(999).id(), "Ostatnie zamówienie");
    }
}
