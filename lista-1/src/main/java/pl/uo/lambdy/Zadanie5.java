package pl.uo.lambdy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Zadanie 5 – domknięcia (zmienne z otoczenia lambdy).
 *
 * Część 1. Dla fragmentów A, B i C ustal, czy się kompilują, i wyjaśnij dlaczego.
 *          Możesz je odkomentować, żeby sprawdzić. Odpowiedzi zapisz w ODPOWIEDZI.md.
 *
 * Część 2. W metodzie main policz, ile razy predykat goodAvg został wywołany podczas
 *          wykonania Zadanie3.filter(...). Nie zmieniaj metody filter ani samego warunku.
 *          Podpowiedź: zwykła zmienna lokalna typu int nie zadziała – dlaczego?
 */
public class Zadanie5 {

    // ── Fragment A ──────────────────────────────────────────
    // NIE KOMPILUJE SIĘ: limit jest zmieniany po przechwyceniu przez lambdę,
    // więc nie jest (effectively) final.
    // static void fragmentA() {
    //     int limit = 3;
    //     Predicate<Student> p = s -> s.year() > limit;
    //     limit = 4;
    // }

    // ── Fragment B ──────────────────────────────────────────
    // NIE KOMPILUJE SIĘ: lambda próbuje zmienić przechwyconą zmienną lokalną (count++).
    // static void fragmentB() {
    //     int count = 0;
    //     StudentData.students().forEach(s -> count++);
    //     System.out.println(count);
    // }

    // ── Fragment C ──────────────────────────────────────────
    // KOMPILUJE SIĘ: count to pole obiektu, a nie zmienna lokalna.
    static class Stats {
        int count;

        void countAll(List<Student> list) {
            list.forEach(s -> count++);
        }
    }

    public static void main(String[] args) {
        List<Student> students = StudentData.students();
        Predicate<Student> goodAvg = s -> s.avg() >= 4.0;

        // Licznik jest obiektem: zmienna calls się nie zmienia (jest effectively final),
        // zmienia się tylko stan obiektu, na który wskazuje.
        AtomicInteger calls = new AtomicInteger();
        Predicate<Student> countingGoodAvg = s -> {
            calls.incrementAndGet();
            return goodAvg.test(s);
        };

        List<Student> good = Zadanie3.filter(students, countingGoodAvg);

        System.out.println("Studenci ze średnią >= 4,0: " + good.size());
        System.out.println("Predykat wywołano " + calls.get() + " razy");
    }
}