package pl.uo.lambdy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Zadanie 2 – sortowanie komparatorami.
 *
 * Każdą z trzech metod zaimplementuj w jednej instrukcji return, używając wyłącznie
 * metod: Comparator.comparing, thenComparing, reversed, nullsLast (oraz naturalOrder).
 * Nie pisz własnej metody compare ani lambdy (a, b) -> ...
 */
public class Zadanie2 {

    /** a) Po nazwisku rosnąco, a przy tym samym nazwisku – po imieniu rosnąco. */
    public static Comparator<Student> byLastNameThenFirstName() {
        return Comparator.comparing(Student::lastName).thenComparing(Student::firstName);
    }

    /** b) Po średniej malejąco, a przy równej średniej – po nazwisku rosnąco. */
    public static Comparator<Student> byAvgDescThenLastName() {
        return Comparator.comparing(Student::avg).reversed().thenComparing(Student::lastName);
    }

    /** c) Po kierunku alfabetycznie; studenci bez kierunku (null) na końcu listy. */
    public static Comparator<Student> byMajorNullsLast() {
        return Comparator.comparing(Student::major, Comparator.nullsLast(Comparator.naturalOrder()));
    }

    public static void main(String[] args) {
        // List.of(...) zwraca listę niemodyfikowalną, a sort() ją modyfikuje,
        // dlatego robimy modyfikowalną kopię.
        List<Student> students = new ArrayList<>(StudentData.students());

        print("a) nazwisko, imię", students, byLastNameThenFirstName());
        print("b) średnia malejąco, nazwisko", students, byAvgDescThenLastName());
        print("c) kierunek, brak kierunku na końcu", students, byMajorNullsLast());
    }

    private static void print(String title, List<Student> students, Comparator<Student> order) {
        students.sort(order);
        System.out.println("=== " + title + " ===");
        students.forEach(System.out::println);
        System.out.println();
    }
}