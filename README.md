# Beispiel: Generics & Interfaces (Pair)

Dieses Beispiel gehört zur Lehrveranstaltung **Objektorientierte Programmierung (OPR)** am Studiengang **Mobile Computing** der **FH OÖ – Campus Hagenberg** (LV-Leitung: FH-Prof. Dr. Marc Kurz).

Es dient als **begleitendes Live-Coding-Beispiel zu Übung 7** (Interfaces, Generics, `Comparable`) und zeigt dieselben Konzepte an einer deutlich kleineren Domäne als `DoubleLinkedList` / `RandomAccessDoubleLinkedList` – damit der Transfer zur Hausübung von den Studierenden selbst geleistet wird.

## Worum geht es?

Als Anwendungsdomäne wird eine einfache generische Klasse **`Pair<T>`** verwendet, die genau **zwei Werte eines beliebigen Typs** halten kann. Auf einem `Pair` lassen sich folgende Operationen ausführen:

- `min()` – gibt den kleineren der beiden Werte zurück
- `max()` – gibt den größeren der beiden Werte zurück
- `isEqual()` – prüft, ob die beiden Werte gleich sind
- `swap()` – tauscht die beiden Werte
- `getStats()` – liefert ein `Stats`-Objekt (innere Klasse), das `min`, `max` und `equal` gebündelt zurückgibt

Damit `min()` und `max()` funktionieren, müssen sich die gespeicherten Werte **vergleichen** lassen. Statt eines Typ-Castings oder einer eigenen Vergleichslogik wird dafür das Standard-Interface [`Comparable<T>`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Comparable.html) genutzt. Die generische Typvariable wird daher mit einer **Oberschranke (Bounded Type Parameter)** versehen:

```java
public class Pair<T extends Comparable<T>> { ... }
```

Zusätzlich wird ein eigenes Interface **`MinMaxable<T>`** definiert und von `Pair` implementiert. Das zeigt exakt das Muster, das in der Übung für das Interface `Sortable` gefordert wird: ein generisches Interface mit derselben Bound, das von einer konkreten generischen Klasse implementiert wird.

## Lernziele

Nach Durcharbeiten dieses Beispiels sollen die Studierenden:

1. die **Grundsyntax generischer Klassen** (`class Pair<T> { ... }`) kennen und wissen, warum Generics besser sind als `Object`-Container mit Casts.
2. den **Bounded Type Parameter** `<T extends Comparable<T>>` verstehen – insbesondere *warum* die Einschränkung nötig ist, sobald innerhalb der Klasse `compareTo()` aufgerufen werden soll.
3. den Unterschied zwischen den Operatoren `<`, `>`, `==` (nur für primitive Typen) und der Methode **`compareTo()`** (für Objekte) sicher anwenden können.
4. eine **innere Klasse** (`Pair<T>.Stats`) schreiben können, die den **generischen Typ der äußeren Klasse** mitverwendet – exakt das Muster, das auch für die Klasse `Node` innerhalb von `DoubleLinkedList<T>` benötigt wird.
5. ein **generisches Interface** (`MinMaxable<T extends Comparable<T>>`) entwerfen und in einer konkreten Klasse implementieren können.
6. eine eigene Klasse (`Person`) schreiben können, die `Comparable<Person>` implementiert, und damit als Typ-Argument in ein `Pair<Person>` eingesetzt werden kann.

## Dateien

| Datei | Inhalt |
| --- | --- |
| [src/MinMaxable.java](src/MinMaxable.java) | Generisches Interface `MinMaxable<T extends Comparable<T>>` mit den Methoden `min()`, `max()`, `isEqual()`. |
| [src/Pair.java](src/Pair.java) | Generische Klasse `Pair<T extends Comparable<T>>`, die `MinMaxable<T>` implementiert. Enthält die innere Klasse `Stats`. |
| [src/Person.java](src/Person.java) | Beispielklasse, die `Comparable<Person>` implementiert (Vergleich nach Alter). Dient als Typ-Argument in der Demo. |
| [src/App.java](src/App.java) | `main`-Methode; erzeugt `Pair<Integer>`, `Pair<String>`, `Pair<Person>` und demonstriert `min`/`max`/`swap`/`Stats`. |

## Didaktischer Aufbau (Live-Coding, ~45–60 Minuten)

Dieses Beispiel ist bewusst so geschnitten, dass es **in sechs Schritten live vor der Gruppe entwickelt** werden kann. Die Studierenden können dabei jeweils mitcoden:

### Schritt 1 – Ausgangspunkt: `IntPair` (nicht-generisch)

Zunächst eine Klasse `IntPair` mit zwei `int`-Feldern und den Methoden `min()`, `max()`, `swap()` schreiben. Hier funktioniert der Vergleich noch mit `<` und `>`.

```java
public int min() {
    return (first < second) ? first : second;
}
```

→ *Frage an die Studierenden:* „Was passiert, wenn ich morgen statt `int` auch `String` in dieser Struktur speichern möchte?" Antwort: Man müsste die ganze Klasse duplizieren.

### Schritt 2 – Refactor zu `Pair<T>` (noch ohne Bound)

`int` durch `T` ersetzen, Klasse als `public class Pair<T> { ... }` deklarieren.

→ **Der Compiler schlägt an**: `first < second` geht nicht mehr, weil `<` nur für primitive Typen definiert ist. Auch `first.compareTo(second)` schlägt fehl, weil für `T` nichts bekannt ist – `Object` kennt keine `compareTo`-Methode.

### Schritt 3 – Bounded Type Parameter einführen

Die Typvariable einschränken:

```java
public class Pair<T extends Comparable<T>> { ... }
```

Und die Vergleiche auf `compareTo()` umstellen:

```java
public T min() {
    return (first.compareTo(second) <= 0) ? first : second;
}
```

→ Der Compiler ist zufrieden. Erklären, *warum*: Durch die Bound garantiert der Compiler, dass jedes Typ-Argument, das für `T` eingesetzt wird, eine `compareTo`-Methode mitbringt.

### Schritt 4 – Innere Klasse `Stats` mit demselben `T`

Innerhalb von `Pair<T>` die innere Klasse `Stats` anlegen. Sie nutzt `T` aus der äußeren Klasse, ohne `T` selbst nochmal in eckigen Klammern zu deklarieren:

```java
public class Stats {
    private T minimum;
    private T maximum;
    // ...
}
```

→ **Wichtiger Transfer zur Übung**: Genau so ist auch `Node` innerhalb von `DoubleLinkedList<T>` zu implementieren – die innere Klasse erbt den generischen Typ der äußeren Klasse.

### Schritt 5 – Interface `MinMaxable<T>` und Implementierung

Das Interface schreiben:

```java
public interface MinMaxable<T extends Comparable<T>> {
    T min();
    T max();
    boolean isEqual();
}
```

`Pair<T>` um `implements MinMaxable<T>` ergänzen und `@Override` vor `min` / `max` / `isEqual` setzen.

→ **Wichtiger Transfer zur Übung**: Exakt dieses Muster wird für das Interface `Sortable<T extends Comparable<T>>` und die Klasse `SortableList<T extends Comparable<T>> extends RandomAccessDoubleLinkedList<T> implements Sortable<T>` benötigt.

### Schritt 6 – Einsatz in `App.java`

Drei Instanzierungen zeigen, dass die Klasse wirklich generisch ist:

```java
Pair<Integer> numbers = new Pair<>(42, 5);
Pair<String>  words   = new Pair<>("Hagenberg", "Linz");
Pair<Person>  people  = new Pair<>(new Person("Alice", 29), new Person("Bob", 34));
```

Die Klasse `Person` implementiert dazu `Comparable<Person>` (Vergleich nach Alter). Das illustriert, dass eigene Klassen nur „Comparable-fähig" sein müssen, um als Typ-Argument eingesetzt werden zu können.

## Ausführen

Voraussetzung: installiertes JDK (z. B. Temurin 17+).

```bash
# Kompilieren
javac -d bin src/*.java

# Ausführen
java -cp bin App
```

Erwartete Ausgabe:

```
Pair[first=42, second=5]
min   = 5
max   = 42
equal = false

Pair[first=Hagenberg, second=Linz]
min (lex.) = Hagenberg
max (lex.) = Linz

Pair[first=Alice (29), second=Bob (34)]
younger = Alice (29)
older   = Bob (34)

stats.min   = 5
stats.max   = 42
stats.equal = false

after swap: Pair[first=5, second=42]
```

## Bezug zur Übung 7

Dieses Beispiel deckt **alle Konzepte** ab, die für UE07 benötigt werden – aber an einer deutlich kleineren Domäne:

| Konzept | Hier (`Pair`) | In der Übung (`DoubleLinkedList` / `SortableList`) |
| --- | --- | --- |
| Generische Klasse mit Bound | `Pair<T extends Comparable<T>>` | `DoubleLinkedList<T extends Comparable<T>>` |
| Innere Klasse mit generischem Typ der Outer-Klasse | `Pair<T>.Stats` | `DoubleLinkedList<T>.Node` |
| Vergleich über `compareTo()` statt `<`/`>` | in `min()` / `max()` | in `insertSorted()` / `sortAscending()` |
| Generisches Interface mit Bound | `MinMaxable<T extends Comparable<T>>` | `Sortable<T extends Comparable<T>>` |
| Klasse implementiert generisches Interface | `Pair<T> implements MinMaxable<T>` | `SortableList<T> extends RADLL<T> implements Sortable<T>` |

Die Übung verlangt zusätzlich noch:

- **Vererbung** zwischen generischen Klassen (`SortableList<T> extends RandomAccessDoubleLinkedList<T>`) – dieses Thema ist hier bewusst **nicht** abgedeckt, um die Hausübung nicht vorwegzunehmen.
- **Zwei konkrete Sortieralgorithmen** (`sortAscending`, `sortDescending`) auf der Listenstruktur.

## Diskussions- und Übungsvorschläge

- Warum würde `Pair<T>` ohne die Bound `extends Comparable<T>` nicht kompilieren, sobald `compareTo()` in der Klasse aufgerufen wird? Was genau meldet der Compiler?
- Was würde passieren, wenn man versucht, ein `Pair<Object>` zu erzeugen? Warum lässt der Compiler das nicht zu?
- Ergänze `Pair<T>` um eine Methode `contains(T value)`, die prüft, ob `value` einem der beiden gespeicherten Elemente gleich ist (`compareTo(...) == 0`).
- Erweitere `MinMaxable<T>` um eine Methode `median()` – lässt sie sich für `Pair<T>` überhaupt sinnvoll implementieren? Und was passiert, wenn das Interface später auch von einer Klasse mit drei oder mehr Werten implementiert werden soll?
- Schreibe eine zweite `Comparable`-Klasse (z. B. `Product` mit Preis als Vergleichskriterium) und verwende sie in einem neuen `Pair<Product>`.
