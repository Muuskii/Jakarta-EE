# Lista 2 – Stream API

**Java EE (Jakarta EE) · tydzień 3 · czas pracy na zajęciach: ok. 80 minut**

**Cel:** przetwarzanie i analiza danych wyłącznie za pomocą strumieni – od wczytania pliku, przez filtrowanie i sortowanie, po grupowanie kolektorami i bezpieczną pracę z `Optional`.

## Dane

Plik `src/main/resources/orders.csv` zawiera zamówienia sklepu internetowego z 2025 roku. Pierwszy wiersz to nagłówek, kolejne mają postać:

```
id;klient;miasto;data;kategoria;kwota;status
1;Ewa Wiśniewska;Poznań;2025-01-01;Elektronika;9235,29;ZREALIZOWANE
```

Pola rozdziela średnik, a kwota ma **przecinek dziesiętny** (tak jak w polskim Excelu). Status to jedna z wartości `NOWE`, `OPLACONE`, `WYSLANE`, `ZREALIZOWANE`, `ANULOWANE`. **Uwaga: w pliku są celowo błędne wiersze i jedna pusta linia.**

W projekcie są gotowe: rekord `Order` (zamówienie), wyliczenie `OrderStatus`, rekordy `LoadResult` i `DateRange` oraz klasa `DataFiles`, która zwraca ścieżkę do pliku z danymi. Nie zmieniaj ich – poza metodą `Order.parse`, którą piszesz w zadaniu 1.

**Przychód** to w całej liście suma kwot zamówień, które **nie są anulowane** (przyda się metoda `Order.isCancelled()`).

## Zasady

- W zadaniach 2–5 **nie używaj pętli** `for` ani `while` – tylko strumieni.
- Każdą metodę staraj się zapisać jednym potokiem: źródło → operacje pośrednie → operacja końcowa.
- Nie zmieniaj testów ani sygnatur metod.

## Jak uruchamiać (Eclipse)

1. *File → Import → Maven → Existing Maven Projects* i wskaż katalog z plikiem `pom.xml`. Wymagany JDK 21.
2. **Testy:** prawy przycisk na projekcie → *Run As → JUnit Test*. Na początku wszystkie są czerwone – to normalne.
3. **Program z danego zadania:** prawy przycisk na pliku, np. `Zadanie2.java` → *Run As → Java Application* (albo Ctrl+F11 w otwartym pliku). Programy `main` w zadaniach 2–5 wczytują dane metodą z zadania 1, więc zacznij od niego. Testy zadań 2–5 działają niezależnie od zadania 1.

## Zadanie 1 – wczytanie danych (10 min)

Pliki: `Order.java`, `Zadanie1.java`

**a)** Zaimplementuj `Optional<Order> parse(String line)` w rekordzie `Order`. Metoda zwraca zamówienie albo pusty `Optional`, gdy wiersz jest niepoprawny – **nigdy nie rzuca wyjątku**. Wiersz jest poprawny, gdy:

- ma dokładnie 7 pól,
- `id` jest liczbą całkowitą,
- klient, miasto i kategoria nie są puste (po obcięciu spacji),
- data jest poprawną datą w formacie `rrrr-mm-dd`,
- kwota jest liczbą z przecinkiem dziesiętnym i jest większa od zera,
- status jest dokładnie jedną z nazw z `OrderStatus` (wielkimi literami).

**b)** Zaimplementuj `LoadResult load(Path file)` w klasie `Zadanie1`: wczytaj plik przez `Files.lines`, pomiń nagłówek i puste wiersze, zamień wiersze na zamówienia metodą `parse` i zwróć listę poprawnych zamówień oraz liczbę odrzuconych wierszy. Pamiętaj, że strumień z `Files.lines` trzeba zamknąć.

Wynik: 1000 poprawnych zamówień i 10 odrzuconych wierszy.

## Zadanie 2 – proste raporty (20 min)

Plik: `Zadanie2.java`

- **a)** liczba zamówień zrealizowanych,
- **b)** pięć zamówień o największej kwocie, od największej,
- **c)** posortowana lista miast bez powtórzeń,
- **d)** czy istnieje zamówienie o kwocie większej niż podany próg,
- **e)** średnia kwota zamówień z podanego miesiąca (`YearMonth`) – jako `OptionalDouble`, pusta, gdy w tym miesiącu nie było zamówień.

## Zadanie 3 – kolektory (25 min)

Plik: `Zadanie3.java`

- **a)** przychód w każdym mieście, w mapie posortowanej po nazwie miasta (`TreeMap`),
- **b)** liczba zamówień według kategorii i statusu – mapa zagnieżdżona: kategoria → (status → liczba),
- **c)** podział zamówień na te o kwocie większej od średniej i pozostałe (`partitioningBy`),
- **d)** mapa: klient → lista identyfikatorów jego zamówień (`mapping`),
- **e)** najlepszy klient w każdym mieście, czyli ten, który przyniósł w danym mieście największy przychód.

## Zadanie 4 – statystyki (15 min)

Plik: `Zadanie4.java`

- **a)** najwcześniejsza i najpóźniejsza data zamówienia jako rekord `DateRange`, wyznaczone w **jednym** przejściu przez dane (kolektor `Collectors.teeing`),
- **b)** statystyki kwot wszystkich zamówień (`DoubleSummaryStatistics`),
- **c)** przychód w każdym miesiącu, w mapie posortowanej chronologicznie.

## Zadanie 5 – Optional (10 min)

Plik: `Zadanie5.java`

W tym zadaniu **nie używaj** `get()`, `isPresent()`, `isEmpty()` ani wartości `null` – korzystaj z `map`, `orElse`, `orElseGet`, `orElseThrow`.

- **a)** `findLargestIn` – największe zamówienie w podanym mieście (pusty `Optional`, gdy brak zamówień),
- **b)** `describeLargestIn` – opis tego zamówienia, np. `Największe zamówienie w mieście Opole: #767 na kwotę 14400,83 zł`, a gdy brak zamówień: `Brak zamówień w mieście Zakopane`,
- **c)** `findById` – zamówienie o podanym id,
- **d)** `customerOf` – klient, który złożył zamówienie o podanym id, albo `nieznany klient`.

## Zadanie dodatkowe – własny kolektor

Plik: `ZadanieDodatkowe.java`

Za pomocą `Collector.of` napisz kolektor wyznaczający **medianę**. Uruchom `main` i porównaj medianę kwot ze średnią – skąd tak duża różnica?

## Oddanie

- Projekt w katalogu `lista-2` w Twoim repozytorium Git, wypchnięty do zdalnego repozytorium przed początkiem kolejnych zajęć.
- Wymagane: zadania 1–5 z zielonymi testami i bez pętli w zadaniach 2–5.
- Przy odbiorze listy prowadzący poprosi o wyjaśnienie wybranych fragmentów Twojego kodu.
- Zadanie dodatkowe podnosi ocenę z listy, ale nie jest wymagane.
