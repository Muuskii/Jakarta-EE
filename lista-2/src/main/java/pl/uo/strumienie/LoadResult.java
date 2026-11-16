package pl.uo.strumienie;

import java.util.List;

/** Wynik wczytania pliku: poprawne zamówienia i liczba odrzuconych wierszy. Nie zmieniaj tego pliku. */
public record LoadResult(List<Order> orders, int rejected) {
}
