package pl.uo.lambdy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Zadanie 1 – klasy anonimowe → wyrażenia lambda.
 *
 * Zamień trzy klasy anonimowe oznaczone TODO na wyrażenia lambda, a tam, gdzie się da,
 * na referencje do metod. Program po zmianach ma wypisywać dokładnie to samo co przed nimi.
 * W pliku ODPOWIEDZI.md napisz, którą klasę udało się zamienić na referencję do metody,
 * a której nie da się zamienić – i dlaczego.
 */
public class Zadanie1 {

    @FunctionalInterface
    interface StudentCheck {
        boolean check(Student s);
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>(StudentData.students());

        // TODO 1: lambda (referencja do metody nie wystarcza)
        Comparator<Student> byAvg = (a, b) -> Double.compare(a.avg(), b.avg());

        // TODO 2: lambda (stały napis w wywołaniu println wyklucza referencję do metody)
        Runnable header = () -> System.out.println("=== Studenci I roku, od najniższej średniej ===");

        // TODO 3: referencja do metody
        StudentCheck firstYear = Student::isFirstYear;

        students.sort(byAvg);
        header.run();
        for (Student s : students) {
            if (firstYear.check(s)) {
                System.out.println(s);
            }
        }
    }
}