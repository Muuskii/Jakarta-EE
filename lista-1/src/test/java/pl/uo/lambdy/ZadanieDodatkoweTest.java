package pl.uo.lambdy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Zadanie dodatkowe – memoizacja")
class ZadanieDodatkoweTest {

    @Test
    @DisplayName("dla tego samego argumentu oryginalna funkcja wywoływana jest tylko raz")
    void callsOriginalFunctionOncePerArgument() {
        AtomicInteger calls = new AtomicInteger();
        Function<Integer, Long> cube = n -> {
            calls.incrementAndGet();
            return (long) n * n * n;
        };

        Function<Integer, Long> memo = ZadanieDodatkowe.memoize(cube);

        assertEquals(27L, memo.apply(3));
        assertEquals(27L, memo.apply(3));
        assertEquals(64L, memo.apply(4));
        assertEquals(27L, memo.apply(3));
        assertEquals(2, calls.get(), "Funkcja powinna zostać wywołana raz dla 3 i raz dla 4");
    }
}
