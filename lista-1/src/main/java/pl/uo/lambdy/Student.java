package pl.uo.lambdy;

/**
 * Student – dane wykorzystywane we wszystkich zadaniach listy.
 * Uwaga: kierunek (major) może mieć wartość null – student nie wybrał jeszcze kierunku.
 */
public record Student(String firstName, String lastName, int year, double avg, String major) {

    public boolean isFirstYear() {
        return year == 1;
    }

    @Override
    public String toString() {
        return String.format("%-11s %-13s rok %d  śr. %.1f  %s",
                firstName, lastName, year, avg, major == null ? "(brak kierunku)" : major);
    }
}
