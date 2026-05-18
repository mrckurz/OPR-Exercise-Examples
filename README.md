# Beispiel: Design Patterns – Composite & Visitor (Expression-Baum)

Dieses Beispiel gehört zur Lehrveranstaltung **Objektorientierte Programmierung (OPR)** am Studiengang **Mobile Computing** der **FH OÖ – Campus Hagenberg** (LV-Leitung: FH-Prof. Dr. Marc Kurz).

Es ergänzt **Übung 10** (*Design Patterns: Composite & Visitor*) an einer deutlich kleineren Domäne als der Ressourcen-Baum aus der Hausübung. Hier siehst du die zentralen Bausteine — `accept`-Methoden, `visit`-Überladungen, doppelter Dispatch — an einem klassischen Lehrbuchbeispiel: einem Baum aus Zahlen, Plus- und Mal-Operatoren.

## Worum geht es?

Als Anwendungsdomäne wird ein **arithmetischer Ausdrucksbaum** verwendet. Ein Ausdruck ist entweder eine Zahl (`Num`) oder ein binärer Operator (`Add`, `Mul`), der wieder Ausdrücke als Kinder enthält — also genau dasselbe **Composite-Pattern** wie ein `Folder`, der `Resource`-Kinder hat.

```
Mul
├── Add
│   ├── Num(2)
│   └── Num(3)
└── Add
    ├── Num(4)
    └── Num(5)
```

Die `Expression`-Klassen selbst enthalten **keine** Funktionalität (kein `evaluate()`, kein `toString()`). Alle Operationen sind als **Visitor** implementiert. Jeder Knoten bietet eine `accept(ExpressionVisitor<R>)`-Methode, die *genau* die zum eigenen statischen Typ passende `visit(...)`-Überladung des Besuchers aufruft. Dieses Muster nennt sich **doppelter Dispatch** und ist der Kern des Visitor-Patterns.

## Lernziele

Wenn du dieses Beispiel durchgearbeitet hast, kannst du:

1. das **Composite-Pattern** identifizieren — gemeinsame abstrakte Basisklasse, Leaves (`Num`) und Composites (`Add`, `Mul`), bei denen Kinder vom gleichen Basistyp sind.
2. den Zweck einer `accept`-Methode erklären (Eintrittspunkt für den Besucher, der den korrekt-überladenen `visit`-Aufruf auslöst — das ist der **doppelte Dispatch**).
3. einen **Visitor mit Rückgabewert** schreiben (`EvaluateVisitor<Double>`, `PrintVisitor<String>`), bei dem `visit(Composite)` rekursiv `child.accept(this)` aufruft und die Teilergebnisse kombiniert.
4. einen **Visitor mit interner Zustandsakkumulation** schreiben (`CountNodesVisitor<Void>`), bei dem der Wert am Ende über einen Getter abgefragt wird — genau das Muster, das `ComputeSizeVisitor` in der Hausübung verwendet.
5. neue Operationen auf einer bestehenden Klassenhierarchie hinzufügen, **ohne** die Knotenklassen anzufassen — der zentrale Vorteil von Visitor gegenüber „einfach eine Methode in jede Klasse legen".

## Dateien

| Datei | Inhalt |
| --- | --- |
| [src/main/java/expressions/Expression.java](src/main/java/expressions/Expression.java) | Abstrakte Basisklasse mit `<R> R accept(ExpressionVisitor<R>)`. |
| [src/main/java/expressions/Num.java](src/main/java/expressions/Num.java) | Leaf-Knoten — hält einen `double`-Wert. |
| [src/main/java/expressions/Add.java](src/main/java/expressions/Add.java) | Composite-Knoten — hat `left` und `right`. |
| [src/main/java/expressions/Mul.java](src/main/java/expressions/Mul.java) | Zweiter Composite-Knoten — analog zu `Add`. |
| [src/main/java/expressions/ExpressionVisitor.java](src/main/java/expressions/ExpressionVisitor.java) | Generisches Interface `ExpressionVisitor<R>` mit drei `visit(...)`-Überladungen. |
| [src/main/java/expressions/EvaluateVisitor.java](src/main/java/expressions/EvaluateVisitor.java) | Visitor mit Rückgabewert `Double` — berechnet das Ergebnis des Baums. |
| [src/main/java/expressions/PrintVisitor.java](src/main/java/expressions/PrintVisitor.java) | Visitor mit Rückgabewert `String` — liefert Infix-Notation mit Klammern. |
| [src/main/java/expressions/CountNodesVisitor.java](src/main/java/expressions/CountNodesVisitor.java) | Visitor mit internem Zähler-Feld und `includeLeaves`-Flag (analog zum `includeBinary`-Flag aus UE10). |
| [src/main/java/expressions/App.java](src/main/java/expressions/App.java) | `main`-Methode; baut den Baum `(2+3)*(4+5)` und ruft alle drei Visitor an demselben Objekt auf. |
| [src/test/java/expressions/ExpressionVisitorTest.java](src/test/java/expressions/ExpressionVisitorTest.java) | Sechs JUnit-5-Testfälle für die drei Visitor und den Sonderfall „einzelner Leaf-Knoten". |

## Voraussetzungen

- **JDK 17 oder neuer** (https://adoptium.net) — z.&nbsp;B. Eclipse Temurin 17 oder 21.
- Sonst **nichts**. Maven wird über den mitgelieferten Wrapper (`mvnw` / `mvnw.cmd`) automatisch in der richtigen Version geholt, JUnit 5 wird beim ersten Build von Maven Central heruntergeladen.

## Schnellstart (alle Plattformen)

```bash
git clone https://github.com/mrckurz/OPR-Exercise-Examples.git
cd OPR-Exercise-Examples
git checkout visitor-example

# Tests ausführen (macOS / Linux)
./mvnw test
# Tests ausführen (Windows)
mvnw.cmd test

# Demo-Main ausführen
./mvnw exec:java            # macOS / Linux
mvnw.cmd exec:java          # Windows
```

Beim ersten Aufruf lädt der Wrapper Maven + JUnit-Artefakte herunter (~15–30 Sekunden). Erwartete Ausgabe der Demo:

```
Ausdruck       = ((2 + 3) * (4 + 5))
Ergebnis       = 45.0
# Knoten ges.  = 7
# Knoten innen = 3
```

Erwartetes Test-Ergebnis: `Tests run: 6, Failures: 0, Errors: 0, Skipped: 0`.

## In der IDE öffnen

Alle drei IDEs erkennen das Projekt am `pom.xml` selbständig — keine manuellen Bibliotheks-Einstellungen nötig.

### VS Code

1. **Extension Pack for Java** installieren (falls noch nicht vorhanden).
2. Ordner in VS Code öffnen → das Java-Plugin importiert das Maven-Projekt automatisch.
3. Im **Testing**-Tab links auf das grüne Play-Icon neben `ExpressionVisitorTest` klicken.
4. Alternativ: in `App.java` über der `main`-Methode auf **Run** klicken.

### IntelliJ IDEA

1. **File → Open…** → den Repo-Ordner wählen → IntelliJ erkennt `pom.xml` und importiert das Projekt.
2. Rechtsklick auf `ExpressionVisitorTest` → **Run 'ExpressionVisitorTest'**.
3. Alternativ: Rechtsklick auf `App.java` → **Run 'App.main()'**.

### Eclipse

1. **File → Import… → Maven → Existing Maven Projects** → den Repo-Ordner wählen → Finish.
2. Rechtsklick auf `ExpressionVisitorTest` → **Run As → JUnit Test**.

## Aufbau des Beispiels

Das Beispiel ist in fünf nachvollziehbaren Schritten aufgebaut. Wenn du den Code Schritt für Schritt liest, erkennst du, wie aus einer „naiven" polymorphen Lösung die Visitor-Architektur entsteht.

### Schritt 1 — Naiver Ansatz: Methode in jeder Knotenklasse

Eine erste Lösung könnte so aussehen — jede Knotenklasse weiß, wie sie sich selbst auswerten und drucken kann:

```java
abstract class Expression {
    abstract double evaluate();
    abstract String print();
}

class Add extends Expression {
    Expression left, right;
    double evaluate() { return left.evaluate() + right.evaluate(); }
    String print()    { return "(" + left.print() + " + " + right.print() + ")"; }
}
```

Funktioniert. Aber wenn morgen `simplify()`, `derivative()`, `countNodes()`, `toLatex()`, `toGraphviz()` hinzukommen — sechs neue Methoden in **jeder** der drei Knotenklassen. Bei jedem neuen Operator (z.&nbsp;B. `Sub`, `Div`) wandern die sechs Methoden mit. Die Knotenklassen werden zu Sammelstellen für **alle** Operationen, die jemand jemals auf dem Baum ausführen möchte.

### Schritt 2 — `accept` statt konkreter Methoden

Das Visitor-Pattern dreht das um. Die Knotenklassen verlieren ihre Operationen und behalten nur eine einzige Methode:

```java
abstract class Expression {
    abstract <R> R accept(ExpressionVisitor<R> visitor);
}

class Num extends Expression {
    @Override public <R> R accept(ExpressionVisitor<R> v) { return v.visit(this); }
}
class Add extends Expression {
    @Override public <R> R accept(ExpressionVisitor<R> v) { return v.visit(this); }
}
class Mul extends Expression {
    @Override public <R> R accept(ExpressionVisitor<R> v) { return v.visit(this); }
}
```

`accept` macht nichts weiter, als den Besucher mit dem Hinweis „ich bin gerade ein `Add`" zurückzurufen. Dadurch wählt der Compiler für `v.visit(this)` zur Übersetzungszeit die richtige Überladung — denn `this` hat in `Add.accept(...)` den statischen Typ `Add`. Das ist der **doppelte Dispatch**: dynamische Dispatch wählt `accept`, statische Dispatch wählt `visit(...)`.

### Schritt 3 — Erster Visitor: `PrintVisitor`

Die String-Repräsentation ist jetzt eine eigene Klasse. Wichtig: die rekursiven Aufrufe gehen über `child.accept(this)`, nicht über `child.print()` — die Knoten haben kein `print()` mehr.

```java
public class PrintVisitor implements ExpressionVisitor<String> {
    @Override public String visit(Num n) { return Long.toString((long) n.getValue()); }
    @Override public String visit(Add a) {
        return "(" + a.getLeft().accept(this) + " + " + a.getRight().accept(this) + ")";
    }
    @Override public String visit(Mul m) {
        return "(" + m.getLeft().accept(this) + " * " + m.getRight().accept(this) + ")";
    }
}
```

Aufrufseitig:

```java
String text = tree.accept(new PrintVisitor());   // "((2 + 3) * (4 + 5))"
```

### Schritt 4 — Zweiter Visitor mit anderem Rückgabetyp: `EvaluateVisitor`

Demonstriert, warum `ExpressionVisitor<R>` **generisch** ist: derselbe Baum, andere Operation, anderer Ergebnistyp — ohne Cast.

```java
public class EvaluateVisitor implements ExpressionVisitor<Double> {
    @Override public Double visit(Num n) { return n.getValue(); }
    @Override public Double visit(Add a) { return a.getLeft().accept(this) + a.getRight().accept(this); }
    @Override public Double visit(Mul m) { return m.getLeft().accept(this) * m.getRight().accept(this); }
}
```

Aufrufseitig:

```java
double value = tree.accept(new EvaluateVisitor());   // 45.0
```

### Schritt 5 — Stateful Visitor mit Flag: `CountNodesVisitor`

Drittes Muster: der Visitor sammelt seinen Zustand intern und wird am Ende abgefragt. Der Konstruktor nimmt ein Flag entgegen — das ist exakt das Muster, das `ComputeSizeVisitor` in der Hausübung mit `includeBinary` verwendet.

```java
public class CountNodesVisitor implements ExpressionVisitor<Void> {
    private final boolean includeLeaves;
    private int count = 0;

    public CountNodesVisitor(boolean includeLeaves) { this.includeLeaves = includeLeaves; }
    public int getCount() { return count; }

    @Override public Void visit(Num n) { if (includeLeaves) count++; return null; }
    @Override public Void visit(Add a) { count++; a.getLeft().accept(this); a.getRight().accept(this); return null; }
    @Override public Void visit(Mul m) { count++; m.getLeft().accept(this); m.getRight().accept(this); return null; }
}
```

Aufrufseitig:

```java
CountNodesVisitor counter = new CountNodesVisitor(true);
tree.accept(counter);
int total = counter.getCount();   // 7
```

## Übertragung auf die Übung 10

| Konzept | Hier (`Expression`-Baum) | In der Hausübung (`Resource`-Baum) |
| --- | --- | --- |
| Abstrakte Basisklasse | `Expression` | `Resource` |
| Leaf | `Num` | `File`, `SourceFile`, `BinaryFile` |
| Composite | `Add`, `Mul` (binär, 2 Kinder) | `Folder` (n-är, `List<Resource>`) |
| `accept`-Methode | identische Schablone in jeder Knotenklasse | `Folder.accept` ruft zusätzlich rekursiv `child.accept(...)` für alle Kinder auf |
| Visitor mit Rückgabewert | `EvaluateVisitor<Double>`, `PrintVisitor<String>` | (in UE10 nicht gefordert — alle Visitor sind `Void`) |
| Stateful Visitor mit Flag | `CountNodesVisitor` (`includeLeaves`) | `ComputeSizeVisitor` (`includeBinary`) |
| Mutierender Visitor | (in diesem Beispiel nicht enthalten) | `CompileVisitor` — erzeugt neue `BinaryFile`-Objekte und hängt sie an den Folder |
| Validierender Visitor mit Exception | (in diesem Beispiel nicht enthalten) | `AssertStructureVisitor` mit `AssertionException` |

**Was du in der Hausübung zusätzlich brauchst und hier *nicht* gezeigt ist:**

- Die `Folder.accept`-Methode muss **rekursiv** alle Kinder besuchen. In diesem Beispiel ist die Rekursion *im Visitor selbst* (`a.getLeft().accept(this)`); in der Hausübung kann die Rekursion auch in `Folder.accept(...)` selbst stehen. Beide Varianten sind legitim, die Aufgabenstellung lässt das offen.
- Ein Visitor, der den Baum **verändert** (`CompileVisitor`): Während des Besuchs neue Knoten anzulegen und in die `children`-Liste eines Folders zu hängen, ist heikel — am besten erst **sammeln**, dann am Ende einfügen, um `ConcurrentModificationException` zu vermeiden.
- Ein Visitor, der **Fehler signalisiert** (`AssertStructureVisitor`): wirft eine `AssertionException extends RuntimeException`, die nicht gefangen werden muss.
- `equals` und `hashCode` auf `Resource` (zwei Resourcen sind gleich, wenn Name und Parent gleich sind).

## Aufgaben zum Vertiefen

- Ergänze einen neuen Knotentyp `Sub` (Subtraktion). Was musst du anpassen — und was bewusst **nicht**? (Tipp: das Interface `ExpressionVisitor`, alle bestehenden Visitor-Implementierungen — die Knotenklassen selbst kommen mit der identischen `accept`-Schablone aus.)
- Warum brauchen wir `accept` überhaupt? Versuche, in `App.main` direkt `visitor.visit(tree)` aufzurufen — was sagt der Compiler, und warum?
- Schreibe einen `MaxDepthVisitor`, der die Tiefe des Baums zurückgibt (`ExpressionVisitor<Integer>`). Warum lässt sich das **ohne** internes Feld lösen, im Gegensatz zu `CountNodesVisitor`?
- Welche Visitor-Variante (Rückgabewert vs. Zustandsfeld) ist thread-safe-freundlicher? Warum verwendet `ComputeSizeVisitor` in der Hausübung trotzdem die Feld-Variante?
- Ändere `PrintVisitor` so, dass er **unnötige Klammern weglässt** (Mul hat Vorrang vor Add). Wie viel Information braucht der Visitor dafür, die er bisher nicht hat? (Stichwort: Kontext-Information durchreichen.)
