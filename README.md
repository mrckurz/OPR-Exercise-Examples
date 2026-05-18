# OPR – Exercise Examples

Dieses Repository enthält Beispiele zur Lehrveranstaltung **Objektorientierte Programmierung (OPR)** am Studiengang **Mobile Computing** der **FH Oberösterreich – Campus Hagenberg**. Die Beispiele begleiten die wöchentlichen Übungen und sollen dir helfen, die jeweiligen Sprachfeatures an einer kleinen, gut nachvollziehbaren Domäne zu sehen, bevor du sie in deiner Hausübung anwendest.

## Lehrveranstaltung

- **Titel:** Objektorientierte Programmierung (OPR)
- **Studiengang:** Mobile Computing
- **Institution:** FH OÖ – Campus Hagenberg
- **Verantwortlich (LV-Leitung):** FH-Prof. Dr. Marc Kurz

## Aufbau des Repositories

Jedes Beispiel liegt auf einem eigenen **Branch**, damit die Beispiele klar voneinander getrennt sind und du gezielt das Beispiel zu einer bestimmten Übung auschecken kannst.

### Verfügbare Branches

| Branch | Thema | Zugehörige Übung |
| --- | --- | --- |
| `main` | Übersicht und allgemeine Informationen zum Repository (dieses README) | – |
| `exception-example` | Einführungsbeispiel zu Exceptions und der Definition eigener Exception-Klassen | UE06 |
| `generics-example` | Generics, Bounded Type Parameter (`<T extends Comparable<T>>`), generisches Interface und innere Klasse mit generischem Typ – illustriert am Beispiel `Pair<T>` | UE07 |
| `lambda-example` | Lambda-Ausdrücke und funktionale Interfaces (`Predicate`, `Function`, `Comparator`, `Consumer`) sowie der Vergleich einer schleifenbasierten und einer Streams-API-basierten Implementierung – illustriert am Beispiel `Book` / `BookProcessor` | UE08 |
| `javafx-example` | Erstes JavaFX-Beispiel: `BorderPane`/`GridPane`-Layout, MVC-Trennung, Property Binding, FXML-Komponente mit Controller und CSS-Styling – illustriert am Beispiel `Tic-Tac-Toe` | UE09 |
| `visitor-example` | Design Patterns: **Composite** und **Visitor** mit doppeltem Dispatch, Visitor mit Rückgabewert (`<R>`) und Visitor mit Zustandsakkumulation, inkl. JUnit-5-Tests – illustriert am Beispiel eines arithmetischen Ausdrucksbaums (`Num` / `Add` / `Mul`) | UE10 |

Weitere Beispiele werden im Laufe der Lehrveranstaltung auf zusätzlichen Branches ergänzt.

## Nutzung

So checkst du ein bestimmtes Beispiel aus:

```bash
git clone https://github.com/mrckurz/OPR-Exercise-Examples.git
cd OPR-Exercise-Examples
git checkout <branch-name>
```

Im jeweiligen Branch findest du ein eigenes README, das das Beispiel und die Lernziele näher beschreibt.
