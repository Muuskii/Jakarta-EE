package pl.uo.strumienie;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

/** Zwraca ścieżkę do pliku z danymi (src/main/resources/orders.csv). Nie zmieniaj tego pliku. */
public final class DataFiles {

    private DataFiles() {
    }

    public static Path ordersCsv() {
        URL url = DataFiles.class.getResource("/orders.csv");
        if (url == null) {
            throw new IllegalStateException("Nie znaleziono pliku orders.csv w src/main/resources");
        }
        try {
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}
