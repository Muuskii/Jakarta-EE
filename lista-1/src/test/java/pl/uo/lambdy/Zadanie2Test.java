package pl.uo.lambdy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Zadanie 2 – sortowanie komparatorami")
class Zadanie2Test {

    private static List<Student> sorted(Comparator<Student> order) {
        List<Student> list = new ArrayList<>(StudentData.students());
        list.sort(order);
        return list;
    }

    private static List<String> fullNames(List<Student> list) {
        return list.stream().map(s -> s.firstName() + " " + s.lastName()).toList();
    }

    @Test
    @DisplayName("a) nazwiska rosnąco, przy tym samym nazwisku imiona rosnąco")
    void byLastNameThenFirstName() {
        List<Student> list = sorted(Zadanie2.byLastNameThenFirstName());

        for (int i = 0; i + 1 < list.size(); i++) {
            Student a = list.get(i), b = list.get(i + 1);
            int byLast = a.lastName().compareTo(b.lastName());
            assertTrue(byLast < 0 || (byLast == 0 && a.firstName().compareTo(b.firstName()) <= 0),
                    "Zła kolejność: " + a.lastName() + " " + a.firstName()
                            + " przed " + b.lastName() + " " + b.firstName());
        }
        List<String> names = fullNames(list);
        assertTrue(names.indexOf("Anna Nowak") < names.indexOf("Piotr Nowak"),
                "Anna Nowak powinna być przed Piotrem Nowakiem");
    }

    @Test
    @DisplayName("b) średnia malejąco, przy równej średniej nazwiska rosnąco")
    void byAvgDescThenLastName() {
        List<Student> list = sorted(Zadanie2.byAvgDescThenLastName());

        assertEquals("Krzysztof Mazur", fullNames(list).get(0), "Na początku powinna być najwyższa średnia");
        for (int i = 0; i + 1 < list.size(); i++) {
            Student a = list.get(i), b = list.get(i + 1);
            assertTrue(a.avg() > b.avg() || (a.avg() == b.avg() && a.lastName().compareTo(b.lastName()) <= 0),
                    "Zła kolejność: " + a.lastName() + " (" + a.avg() + ") przed "
                            + b.lastName() + " (" + b.avg() + ")");
        }
        List<String> names = fullNames(list);
        int i = names.indexOf("Ewa Kowalska");
        assertEquals(List.of("Ewa Kowalska", "Jan Wójcik", "Marek Zieliński"), names.subList(i, i + 3),
                "Studenci ze średnią 4,5 powinni być ułożeni po nazwisku");
    }

    @Test
    @DisplayName("c) kierunki alfabetycznie, brak kierunku (null) na końcu")
    void byMajorNullsLast() {
        List<Student> list = sorted(Zadanie2.byMajorNullsLast());

        List<String> majors = list.stream().map(Student::major).toList();
        assertNull(majors.get(majors.size() - 1), "Na końcu powinni być studenci bez kierunku");
        assertNull(majors.get(majors.size() - 3));
        assertNotNull(majors.get(majors.size() - 4), "Studentów bez kierunku jest dokładnie trzech");
        for (int i = 0; i + 1 < majors.size() - 3; i++) {
            assertTrue(majors.get(i).compareTo(majors.get(i + 1)) <= 0,
                    "Zła kolejność kierunków: " + majors.get(i) + " przed " + majors.get(i + 1));
        }
    }
}
