package pl.uo.lambdy;

import java.util.function.Function;

/**
 * Zadanie dodatkowe – pamięć podręczna wyników (memoizacja).
 *
 * Metoda memoize zwraca nową funkcję, która dla argumentu widzianego po raz pierwszy
 * wywołuje f i zapamiętuje wynik w HashMap, a przy kolejnych wywołaniach z tym samym
 * argumentem zwraca zapamiętany wynik bez ponownego wywoływania f.
 */
public final class ZadanieDodatkowe {

    private ZadanieDodatkowe() {
    }

    public static Function<Integer, Long> memoize(Function<Integer, Long> f) {
        // TODO
        throw new UnsupportedOperationException("TODO: zadanie dodatkowe");
    }

    /** Celowo wolna funkcja: symuluje kosztowne obliczenie (np. zapytanie do bazy). */
    static long slowSquare(int n) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return (long) n * n;
    }

    public static void main(String[] args) {
        Function<Integer, Long> fast = memoize(ZadanieDodatkowe::slowSquare);
        int[] inputs = {5, 7, 5, 5, 7, 9, 5};

        long start = System.nanoTime();
        for (int n : inputs) {
            System.out.println(n + "² = " + fast.apply(n));
        }
        System.out.printf("Czas: %d ms (bez pamięci podręcznej byłoby ok. %d ms)%n",
                (System.nanoTime() - start) / 1_000_000, inputs.length * 300);
    }
}
