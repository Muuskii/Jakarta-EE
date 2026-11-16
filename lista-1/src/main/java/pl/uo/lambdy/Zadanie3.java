package pl.uo.lambdy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Zadanie 3 – własne metody przyjmujące funkcje.
 *
 * Metody filter i map napisz zwykłą pętlą – bez Stream API.
 * Metody nie mogą modyfikować listy źródłowej; zwracają nową listę.
 */
public class Zadanie3 {

    /** Zwraca nową listę studentów, dla których predykat p zwraca true (w kolejności z src). */
    public static List<Student> filter(List<Student> src, Predicate<Student> p) {
        List<Student> result = new ArrayList<>();
        for (Student s : src) {
            if (p.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    /** Zwraca nową listę wyników funkcji f – po jednym dla każdego studenta z src. */
    public static List<String> map(List<Student> src, Function<Student, String> f) {
        List<String> result = new ArrayList<>();
        for (Student s : src) {
            result.add(f.apply(s));
        }
        return result;
    }

    static final Predicate<Student> THIRD_YEAR = s -> s.year() == 3;

    static final Predicate<Student> AVG_AT_LEAST_4 = s -> s.avg() >= 4.0;

    // "Informatyka".equals(...) jest bezpieczne dla null: dla studenta bez kierunku zwraca false.
    static final Predicate<Student> IT_MAJOR = s -> "Informatyka".equals(s.major());

    /**
     * III rok, średnia co najmniej 4,0 i kierunek inny niż Informatyka.
     * Złóż wynik z trzech predykatów powyżej wyłącznie za pomocą and(...) i negate().
     * Pamiętaj, że kierunek może mieć wartość null.
     */
    public static Predicate<Student> thirdYearGoodAvgNotIt() {
        return THIRD_YEAR.and(AVG_AT_LEAST_4).and(IT_MAJOR.negate());
    }

    public static void main(String[] args) {
        List<Student> students = StudentData.students();

        System.out.println("=== Średnia powyżej 4,5 ===");
        filter(students, s -> s.avg() > 4.5).forEach(System.out::println);

        System.out.println("\n=== Same nazwiska ===");
        map(students, Student::lastName).forEach(System.out::println);

        System.out.println("\n=== Imię i nazwisko ===");
        map(students, s -> s.firstName() + " " + s.lastName()).forEach(System.out::println);

        System.out.println("\n=== Nazwiska wielkimi literami ===");
        map(students, s -> s.lastName().toUpperCase()).forEach(System.out::println);

        System.out.println("\n=== III rok, średnia >= 4,0, nie Informatyka ===");
        filter(students, thirdYearGoodAvgNotIt()).forEach(System.out::println);
    }
}