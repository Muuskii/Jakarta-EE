package pl.uo.lambdy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 3 – własne filter i map")
class Zadanie3Test {

    private final List<Student> students = StudentData.students();

    @Test
    @DisplayName("filter zwraca pasujących studentów w kolejności ze źródła")
    void filterKeepsMatchingInOrder() {
        List<Student> result = Zadanie3.filter(students, s -> s.year() == 1);

        assertEquals(List.of("Zieliński", "Szymański", "Jankowska", "Michalska"),
                result.stream().map(Student::lastName).toList());
    }

    @Test
    @DisplayName("filter nie zmienia listy źródłowej i zwraca pustą listę, gdy nikt nie pasuje")
    void filterDoesNotModifySource() {
        List<Student> source = new ArrayList<>(students);

        List<Student> result = Zadanie3.filter(source, s -> s.avg() > 5.0);

        assertTrue(result.isEmpty());
        assertEquals(students, source);
    }

    @Test
    @DisplayName("map zwraca po jednym wyniku dla każdego studenta")
    void mapAppliesFunctionToEveryStudent() {
        List<String> result = Zadanie3.map(students, s -> s.firstName().substring(0, 1) + "." + s.lastName());

        assertEquals(students.size(), result.size());
        assertEquals("A.Nowak", result.get(0));
        assertEquals("J.Wieczorek", result.get(result.size() - 1));
    }

    @Test
    @DisplayName("złożony warunek: III rok, średnia >= 4,0, kierunek inny niż Informatyka")
    void compositeRule() {
        List<Student> result = Zadanie3.filter(students, Zadanie3.thirdYearGoodAvgNotIt());

        assertEquals(List.of("Anna Nowak", "Jan Wójcik", "Magdalena Woźniak"),
                result.stream().map(s -> s.firstName() + " " + s.lastName()).toList());
    }

    @Test
    @DisplayName("złożony warunek działa dla studenta bez kierunku (null)")
    void compositeRuleHandlesNullMajor() {
        Student noMajor = new Student("Test", "Testowy", 3, 4.2, null);

        assertDoesNotThrow(() -> Zadanie3.thirdYearGoodAvgNotIt().test(noMajor),
                "Kierunek może być null – porównuj tak, żeby nie było NullPointerException");
        assertTrue(Zadanie3.thirdYearGoodAvgNotIt().test(noMajor));
    }
}
