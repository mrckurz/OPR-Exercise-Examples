# Beispiel: Exceptions & eigene Exception-Klassen (Stack)

Dieses Beispiel gehört zur Lehrveranstaltung **Objektorientierte Programmierung (OPR)** am Studiengang **Mobile Computing** der **FH OÖ – Campus Hagenberg** (LV-Leitung: FH-Prof. Dr. Marc Kurz).

## Worum geht es?

Als Anwendungsdomäne wird ein einfacher **Stack** (Kellerspeicher / LIFO-Datenstruktur) mit fester Kapazität auf Basis eines `int`-Arrays implementiert. Auf dem Stack sind die Operationen `push`, `pop` und `peek` möglich.

Beim Arbeiten mit dem Stack können **Fehlersituationen** auftreten:

- `push` auf einen bereits vollen Stack → es ist kein Platz mehr für ein weiteres Element.
- `pop` auf einen leeren Stack → es gibt nichts, das zurückgegeben werden könnte.

Anstatt diese Fehler mit Rückgabewerten (z. B. `-1`, `null`, Bool-Flags) zu signalisieren, werden in diesem Beispiel **eigene, anwendungsspezifische Exception-Klassen** definiert und genutzt:

- [`StackFullException`](src/StackFullException.java) – geworfen von `push`, wenn der Stack voll ist.
- [`StackEmptyException`](src/StackEmptyException.java) – geworfen von `pop`, wenn der Stack leer ist; enthält zusätzlich ein `status`-Feld, um zu zeigen, wie eine Exception auch Zusatzinformationen transportieren kann.

In [`App.java`](src/App.java) werden die Fehlerfälle bewusst provoziert (zwei Pushes auf einen Stack der Größe 2, anschließend drei Pops) und mit `try/catch` behandelt.

## Dateien

| Datei | Inhalt |
| --- | --- |
| [src/Stack.java](src/Stack.java) | Stack-Implementierung auf Basis eines `int`-Arrays; `push`/`pop` deklarieren `throws` für die eigenen Exceptions. |
| [src/StackFullException.java](src/StackFullException.java) | Eigene geprüfte Exception (`extends Exception`) mit Default- und Message-Konstruktor. |
| [src/StackEmptyException.java](src/StackEmptyException.java) | Eigene geprüfte Exception mit zusätzlichem `status`-Feld und Getter. |
| [src/App.java](src/App.java) | `main`-Methode mit `try/catch`-Blöcken, die die Exceptions auslösen und behandeln. |

## Lerneffekt

Nach Durcharbeiten dieses Beispiels sollen die Studierenden:

1. den **Unterschied zwischen geprüften (`checked`) und ungeprüften (`unchecked`) Exceptions** kennen und wissen, warum hier `extends Exception` (checked) gewählt wurde.
2. **eigene Exception-Klassen** definieren können, inklusive:
   - Vererbung von `Exception` (bzw. `RuntimeException`),
   - Weitergabe einer Fehlermeldung an den Superkonstruktor (`super(msg)`),
   - Ergänzung um **Zusatzattribute** (wie `status` in `StackEmptyException`), um Kontextinformationen zum Fehler weiterzureichen.
3. das **`throws`-Schlüsselwort** in Methodensignaturen korrekt einsetzen, um geprüfte Exceptions zu propagieren.
4. das **`throw`-Schlüsselwort** verwenden, um in einer konkreten Fehlersituation eine Exception auszulösen.
5. **`try/catch`-Blöcke** sinnvoll strukturieren und Exceptions gezielt auffangen, auswerten (`getMessage()`, eigene Getter wie `getStatus()`) und darauf reagieren.
6. verstehen, **warum Exceptions besser sind als Fehlercodes**: klare Trennung von „Normalfall“ und „Fehlerfall“, keine vergessene Fehlerprüfung, bessere Lesbarkeit und Wartbarkeit.

## Ausführen

Voraussetzung: installiertes JDK (z. B. Temurin 17+).

```bash
# Kompilieren
javac -d bin src/*.java

# Ausführen
java -cp bin App
```

Erwartete Ausgabe (gekürzt):

```
Value 2 not added to Stack --> full
1
Stack is empty: 2
```

(Der Stack hat Kapazität 2. `push(1)` gelingt, `push(2)` würde ebenfalls gelingen – je nach `maxSize` kann hier die `StackFullException` ausgelöst werden; das dritte `pop` führt schließlich zur `StackEmptyException`, deren Statuswert zusätzlich ausgegeben wird.)

## Übungsvorschläge

- Erweitere `Stack` um eine generische Variante `Stack<T>`, sodass beliebige Objekte gespeichert werden können.
- Füge eine `StackException` als **gemeinsame Oberklasse** für `StackFullException` und `StackEmptyException` ein und fange beide Fehler mit einem einzigen `catch`-Block ab.
- Überlege, in welchen Fällen eine **`RuntimeException`** (unchecked) die bessere Wahl wäre und diskutiere die Vor- und Nachteile.
- Nutze `try`-with-resources oder `finally`, um z. B. Logging konsistent durchzuführen.
