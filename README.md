# Beispiel: Lambda Expressions & Functional Interfaces (Book)

Dieses Beispiel gehört zur Lehrveranstaltung **Objektorientierte Programmierung (OPR)** am Studiengang **Mobile Computing** der **FH OÖ – Campus Hagenberg** (LV-Leitung: FH-Prof. Dr. Marc Kurz).

Es dient als **begleitendes Live-Coding-Beispiel zu Übung 8** (Lambda-Ausdrücke, funktionale Interfaces, Streams API) und zeigt dieselben Konzepte an einer deutlich kleineren Domäne als der Backblaze-Festplatten-Datensatz – damit der Transfer zur Hausübung von den Studierenden selbst geleistet wird.

## Worum geht es?

Als Anwendungsdomäne wird eine sehr einfache Klasse **`Book`** verwendet (Titel, Autor, Seitenanzahl, Preis, Genre). Auf einer Liste von Büchern werden über das Interface **`BookProcessor`** typische Auswertungen durchgeführt – jeweils parametrisiert durch ein **funktionales Interface** aus der Standardbibliothek:

| Methode | Funktionales Interface | Was wird übergeben? |
| --- | --- | --- |
| `count()` | – | – |
| `filter(Predicate<Book>)` | `Predicate<T>` | „Trifft das Buch eine Bedingung?" |
| `max(Comparator<Book>)` / `min(Comparator<Book>)` | `Comparator<T>` | „Welches Buch ist größer/kleiner bzgl. eines Kriteriums?" |
| `mean(Function<Book, Long>)` | `Function<T, R>` | „Welche Zahl extrahiere ich aus dem Buch?" |
| `countDistinctStrings(Function<Book, String>)` | `Function<T, R>` | „Welcher String soll gruppiert werden?" |
| `forEach(Consumer<Book>)` | `Consumer<T>` | „Was tue ich mit jedem Buch?" |

Das Interface `BookProcessor` wird **zwei Mal implementiert** – exakt das Muster, das in der Hausübung für `DataProcessorManual` und `DataProcessorStreams` gefordert wird:

- **`BookProcessorManual`** – klassisch mit `for`-Schleifen.
- **`BookProcessorStreams`** – mit der Streams-API (`stream().filter(...).max(...).mapToLong(...).count()` …).

Beide Implementierungen liefern in `App.java` exakt dieselbe Ausgabe; die Studierenden sehen damit unmittelbar, dass die funktionale Schreibweise eine *andere Form*, aber kein *anderes Ergebnis* produziert.

## Lernziele

Nach Durcharbeiten dieses Beispiels sollen die Studierenden:

1. die **Grundsyntax von Lambda-Ausdrücken** kennen (`b -> b.getPrice() > 20`, `(a, b) -> a.getPages() - b.getPages()`, Block-Body mit `{ ... return ...; }`).
2. die vier wichtigsten **funktionalen Interfaces** aus `java.util.function` (sowie `java.util.Comparator`) sicher unterscheiden können:
    - `Predicate<T>` → `boolean test(T)`
    - `Function<T, R>` → `R apply(T)`
    - `Consumer<T>` → `void accept(T)`
    - `Comparator<T>` → `int compare(T, T)`
3. den Zusammenhang zwischen einem **Lambda-Ausdruck** und dem **funktionalen Interface**, auf das er „passt", verstehen (Target-Typ-Inferenz).
4. **Methodenreferenzen** (`Book::getPages`, `Comparator.comparingInt(Book::getPages)`) als kompaktere Schreibweise für triviale Lambdas einsetzen können.
5. eine **manuelle, schleifenbasierte** Implementierung gegen eine **Streams-basierte** Implementierung austauschen können – ohne das öffentliche API zu ändern.
6. einschätzen können, wann die Streams-API klar besser lesbar ist und wann eine klassische Schleife besser wäre.

## Dateien

| Datei | Inhalt |
| --- | --- |
| [src/Book.java](src/Book.java) | Einfache Datenklasse mit Titel, Autor, Seitenanzahl, Preis, Genre. |
| [src/BookProcessor.java](src/BookProcessor.java) | Interface mit den Methoden `count`, `filter`, `min`, `max`, `mean`, `countDistinctStrings`, `forEach`. |
| [src/BookProcessorManual.java](src/BookProcessorManual.java) | Implementierung mit klassischen `for`-Schleifen. |
| [src/BookProcessorStreams.java](src/BookProcessorStreams.java) | Implementierung mit der Streams-API. |
| [src/App.java](src/App.java) | `main`-Methode; baut eine Liste von Büchern auf und führt **dieselben** Abfragen einmal über die manuelle und einmal über die Streams-Implementierung aus. |

## Didaktischer Aufbau (Live-Coding, ~45–60 Minuten)

Dieses Beispiel ist bewusst so geschnitten, dass es **in sechs Schritten live vor der Gruppe entwickelt** werden kann. Die Studierenden können dabei jeweils mitcoden:

### Schritt 1 – Ausgangspunkt: Hardcodierte Schleife

Zunächst eine schlichte `main`, die alle „teuren" Bücher (Preis > 20 €) aus einer Liste herausfiltert:

```java
List<Book> expensive = new ArrayList<>();
for (Book b : books) {
    if (b.getPrice() > 20.0) {
        expensive.add(b);
    }
}
```

→ *Frage an die Studierenden:* „Was mache ich morgen, wenn ich alle Bücher mit mehr als 400 Seiten brauche? Klasse duplizieren? Methode duplizieren?" Antwort: Wir bräuchten eine Möglichkeit, die **Bedingung selbst** als Parameter zu übergeben.

### Schritt 2 – Eigene anonyme innere Klasse / Predicate einführen

Auftritt von `Predicate<Book>`: ein Interface mit einer Methode `boolean test(Book)`. Erst klassisch als anonyme innere Klasse zeigen:

```java
Predicate<Book> isExpensive = new Predicate<Book>() {
    @Override
    public boolean test(Book b) {
        return b.getPrice() > 20.0;
    }
};
```

→ Sehr verbose. Genau hier setzt die Lambda-Syntax an.

### Schritt 3 – Refactor zur Lambda-Schreibweise

Drei Stufen der Lambda-Verkürzung zeigen:

```java
Predicate<Book> isExpensive = (Book b) -> { return b.getPrice() > 20.0; };
Predicate<Book> isExpensive = (Book b) -> b.getPrice() > 20.0;
Predicate<Book> isExpensive = b       -> b.getPrice() > 20.0;
```

→ *Erklären:* Der Compiler weiß durch den Target-Typ `Predicate<Book>`, dass `b` ein `Book` sein muss – die Typannotation kann entfallen. Bei einem einzigen Ausdruck kann auch `{ return ... }` weggelassen werden.

### Schritt 4 – `BookProcessor` mit `filter`, `max`, `min`, `mean`

Schleifenbasierten Filter in eine wiederverwendbare Klasse heben:

```java
public List<Book> filter(Predicate<Book> p) {
    List<Book> result = new ArrayList<>();
    for (Book b : books) {
        if (p.test(b)) result.add(b);
    }
    return result;
}
```

Analog `max` und `min` mit `Comparator<Book>` und `mean` mit `Function<Book, Long>`. Wichtig: Die **Schleifenimplementierung** liegt vollständig **innerhalb** der Methode – der Aufrufer sieht nur das funktionale Interface.

→ *Aufrufseitig:*

```java
processor.filter(b -> b.getPrice() > 20.0);
processor.max   (Comparator.comparingInt(Book::getPages));
processor.mean  (b -> (long) b.getPages());
```

### Schritt 5 – Streams-Implementierung

Eine zweite Klasse `BookProcessorStreams` schreiben, die dasselbe Interface erfüllt – aber jede Methode nur eine einzige Stream-Pipeline ist:

```java
public List<Book> filter(Predicate<Book> p) {
    return books.stream().filter(p).collect(Collectors.toList());
}

public Book max(Comparator<Book> cmp) {
    return books.stream().max(cmp).orElseThrow();
}

public double mean(Function<Book, Long> f) {
    return books.stream().mapToLong(f::apply).average().orElseThrow();
}
```

→ **Wichtiger Transfer zur Übung**: Genauso ist auch der `DataProcessorStreams` aufgebaut. Der Trick ist immer: Stream öffnen → ein- oder mehrere Zwischen­operationen (`filter`, `map`, `mapToLong`, `distinct`, `sorted`) → eine **terminale** Operation (`count`, `max`, `average`, `collect`, `forEach`).

### Schritt 6 – Beide Implementierungen gegeneinander laufen lassen

In `App.java` denselben Block einmal mit `new BookProcessorManual(books)` und einmal mit `new BookProcessorStreams(books)` ausführen:

```java
private static void run(BookProcessor processor) {
    Predicate<Book>          isExpensive = b -> b.getPrice() > 20.0;
    Comparator<Book>         byPages     = Comparator.comparingInt(Book::getPages);
    Function<Book, Long>     pages       = b -> (long) b.getPages();
    Function<Book, String>   genre       = Book::getGenre;

    System.out.println("# expensive books = " + processor.filter(isExpensive).size());
    System.out.println("longest book      = " + processor.max(byPages).getTitle());
    // ...
}
```

→ *Aha-Effekt:* Die Methode `run` weiß nicht, ob sie gerade Schleifen oder Streams ausführt – sie kennt nur das Interface. Genau das ist die Trennung von **WAS** (Predicate, Comparator, Function) und **WIE** (Schleifen vs. Streams).

## Ausführen

Voraussetzung: installiertes JDK (Temurin 17+).

```bash
# Kompilieren
javac -d bin src/*.java

# Ausführen
java -cp bin App
```

Erwartete Ausgabe:

```
=== Manual implementation ===
count             = 6
# expensive books = 4
longest book      = Sapiens
shortest book     = Faust I
mean pages        = 370,3
# distinct genres = 4
titles:
  - Clean Code
  - Effective Java
  - The Pragmatic Programmer
  - Der Process
  - Faust I
  - Sapiens

=== Streams implementation ===
... identische Ausgabe ...
```

Die identische Ausgabe ist beabsichtigt – sie ist der zentrale Beweis dafür, dass beide Implementierungen semantisch äquivalent sind.

## Bezug zur Übung 8

Dieses Beispiel deckt **alle Konzepte** ab, die für UE08 benötigt werden – aber an einer deutlich kleineren Domäne:

| Konzept | Hier (`Book` / `BookProcessor`) | In der Übung (`HardDisk` / `DataProcessor`) |
| --- | --- | --- |
| Datenklasse | `Book` | `HardDisk` |
| Datenquelle | `List.of(...)` direkt im Code | `LiveHardDiskDataSource` (aus `backblazeData.jar`) |
| Manuelle Implementierung | `BookProcessorManual` | `DataProcessorManual` |
| Streams-Implementierung | `BookProcessorStreams` | `DataProcessorStreams` |
| `Predicate<T>` | `b -> b.getPrice() > 20` | `hdd -> hdd.isFailing()` |
| `Comparator<T>` | `Comparator.comparingInt(Book::getPages)` | `(a, b) -> Long.compare(a.getCapacityInBytes(), b.getCapacityInBytes())` |
| `Function<T, R>` | `b -> (long) b.getPages()` | `hdd -> hdd.getCapacityInBytes()` |
| `Function<T, String>` für Distinct | `Book::getGenre` | `HardDisk::getModel` |

Die Übung verlangt zusätzlich noch:

- **Median**-Berechnung über `Comparator` + `Function` kombiniert (`median(...)`).
- **JUnit-Tests** mit einer `DummyHardDiskDataSource` – dieses Thema ist hier bewusst **nicht** abgedeckt, um die Hausübung nicht vorwegzunehmen.

## Diskussions- und Übungsvorschläge

- Welche der vier Interfaces (`Predicate`, `Function`, `Consumer`, `Comparator`) hat **keinen** Rückgabewert in seiner Methode? Warum ist das logisch konsistent?
- Schreibe `Comparator.comparingInt(Book::getPages)` einmal als reine Lambda (`(a, b) -> ...`) und einmal mit `Integer.compare(...)`. Welche der drei Varianten ist am robustesten gegen Overflow?
- Ergänze in `BookProcessor` eine Methode `median(Comparator<Book>, Function<Book, Long>)` analog zur Hausübung. Wie sieht sie in der Stream-Variante aus?
- Was ist der Unterschied zwischen `books.stream().forEach(...)` und einer klassischen `for`-Schleife? Welcher der beiden Stile darf parallelisiert werden?
- Warum liefert `mean()` im Streams-Beispiel `OptionalDouble` und nicht direkt `double`? Was wäre die saubere Behandlung des leeren Falls?
