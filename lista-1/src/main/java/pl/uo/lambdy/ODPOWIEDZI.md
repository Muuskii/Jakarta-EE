## Zadanie 1

**Zamienione na referencję do metody:** `StudentCheck` → `Student::isFirstYear`.
Metoda `check(Student s)` robi tylko jedno: wywołuje `s.isFirstYear()` na swoim
jedynym argumencie. Lambda `s -> s.isFirstYear()` przekazuje argument bez żadnej
zmiany, więc można ją skrócić do referencji do metody instancji (`Klasa::metoda`).

**Nie da się zamienić na referencję do metody:**

- `Comparator<Student>` → tylko lambda `(a, b) -> Double.compare(a.avg(), b.avg())`.
  Komparator musi przyjąć dwóch studentów, ale porównywać ich średnie, więc przed
  wywołaniem `Double.compare` trzeba z każdego wyciągnąć `avg()`. Referencja do metody
  przekazuje argumenty bez przekształceń, a tu są one przekształcane. (Gdyby `Student`
  implementował `Comparable` po średniej, można by użyć `Student::compareTo`, ale tak nie jest.)
  Uwaga: `Comparator.comparingDouble(Student::avg)` też działa i używa referencji,
  ale to wywołanie metody fabrycznej, a nie referencja zastępująca cały komparator.

- `Runnable` → tylko lambda `() -> System.out.println("...")`.
  `run()` nie przyjmuje argumentów, a `println` ma dostać konkretny, stały napis.
  Referencja do metody nie pozwala wpisać stałego argumentu, więc potrzebna jest lambda.

  ## Zadanie 5

Lambda może odczytać zmienną lokalną z otoczenia tylko wtedy, gdy jest ona
final albo effectively final (po inicjalizacji nigdy nie zmienia wartości).
Lambda dostaje bowiem kopię wartości zmiennej lokalnej, a nie dostęp do jej
miejsca na stosie. Zmienna lokalna żyje na stosie metody i może zniknąć, zanim
lambda zostanie wywołana, więc modyfikacja nie miałaby sensu.

- **Fragment A – nie kompiluje się.** `limit` jest przechwycony przez lambdę,
  a potem przypisanie `limit = 4` sprawia, że nie jest effectively final.
  Błąd: "local variables referenced from a lambda expression must be final
  or effectively final".
- **Fragment B – nie kompiluje się.** `count++` w lambdzie próbuje zmienić
  przechwyconą zmienną lokalną. Ten sam błąd co w A.
- **Fragment C – kompiluje się.** `count` jest polem obiektu `Stats`, a nie
  zmienną lokalną. Lambda nie kopiuje pola, tylko przechwytuje referencję `this`
  i przez nią odwołuje się do pola, które żyje na stercie razem z obiektem,
  więc można je swobodnie zmieniać.

**Różnica B vs C:** w obu przypadkach lambda robi `count++`, ale w B `count`
to zmienna lokalna (kopiowana do lambdy, więc nie wolno jej zmieniać), a w C
to pole obiektu (dostęp przez `this`, więc zmiana jest dozwolona).
Uwaga: pole zmieniane z lambdy nie jest bezpieczne wątkowo (np. w strumieniu
równoległym).

**Część 2:** zwykły `int` nie zadziała z tego samego powodu co w B. Rozwiązanie:
licznik jako obiekt (`AtomicInteger`, ewentualnie `int[]`). Zmienna wskazująca na
obiekt pozostaje effectively final, a zmienia się stan obiektu. Predykat `goodAvg`
owinąłem w drugi predykat, który zwiększa licznik i woła `goodAvg.test(s)`.
Liczba wywołań jest równa liczbie studentów na liście, bo `filter` testuje
każdy element dokładnie raz.