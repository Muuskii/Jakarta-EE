package pl.uo.lambdy;

import java.util.List;

/** Dane testowe: 20 studentów. Nie zmieniaj tego pliku – korzystają z niego testy. */
public final class StudentData {

    private StudentData() {
    }

    /** Zwraca listę niemodyfikowalną (List.of). */
    public static List<Student> students() {
        return List.of(
                new Student("Anna", "Nowak", 3, 4.6, "Matematyka"),
                new Student("Piotr", "Nowak", 2, 3.8, "Informatyka"),
                new Student("Ewa", "Kowalska", 3, 4.5, "Informatyka"),
                new Student("Marek", "Zieliński", 1, 4.5, "Fizyka"),
                new Student("Jan", "Wójcik", 3, 4.5, null),
                new Student("Katarzyna", "Lewandowska", 3, 3.9, "Ekonomia"),
                new Student("Tomasz", "Kamiński", 4, 4.8, "Informatyka"),
                new Student("Agnieszka", "Dąbrowska", 2, 4.2, "Matematyka"),
                new Student("Michał", "Szymański", 1, 3.2, "Informatyka"),
                new Student("Magdalena", "Woźniak", 3, 4.0, "Fizyka"),
                new Student("Paweł", "Kozłowski", 5, 4.1, null),
                new Student("Joanna", "Jankowska", 1, 3.5, "Ekonomia"),
                new Student("Krzysztof", "Mazur", 2, 4.9, "Informatyka"),
                new Student("Aleksandra", "Krawczyk", 3, 3.7, "Matematyka"),
                new Student("Łukasz", "Piotrowski", 4, 3.6, "Fizyka"),
                new Student("Natalia", "Grabowska", 2, 4.0, null),
                new Student("Adam", "Pawłowski", 3, 4.3, "Informatyka"),
                new Student("Zofia", "Michalska", 1, 4.7, "Matematyka"),
                new Student("Bartosz", "Król", 5, 3.4, "Ekonomia"),
                new Student("Julia", "Wieczorek", 4, 4.4, "Informatyka")
        );
    }
}
