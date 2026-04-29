# OPR – Exercise Examples

Dieses Repository enthält Beispiele zur Lehrveranstaltung **Objektorientierte Programmierung (OPR)** am Studiengang **Mobile Computing** der **FH Oberösterreich – Campus Hagenberg**.

## Lehrveranstaltung

- **Titel:** Objektorientierte Programmierung (OPR)
- **Studiengang:** Mobile Computing
- **Institution:** FH OÖ – Campus Hagenberg
- **Verantwortlich (LV-Leitung):** FH-Prof. Dr. Marc Kurz

## Aufbau des Repositories

In diesem Repository werden verschiedene Beispiele und Übungen zur Lehrveranstaltung bereitgestellt. Jedes Beispiel liegt auf einem eigenen **Branch**, damit die Beispiele klar voneinander getrennt sind und bei Bedarf gezielt ausgecheckt werden können.

### Verfügbare Branches

| Branch | Thema | Zugehörige Übung |
| --- | --- | --- |
| `main` | Übersicht und allgemeine Informationen zum Repository (dieses README) | – |
| `exception-example` | Einführungsbeispiel zu Exceptions und der Definition eigener Exception-Klassen | UE06 |
| `generics-example` | Generics, Bounded Type Parameter (`<T extends Comparable<T>>`), generisches Interface und innere Klasse mit generischem Typ – illustriert am Beispiel `Pair<T>` | UE07 |
| `lambda-example` | Lambda-Ausdrücke und funktionale Interfaces (`Predicate`, `Function`, `Comparator`, `Consumer`) sowie der Vergleich einer schleifenbasierten und einer Streams-API-basierten Implementierung – illustriert am Beispiel `Book` / `BookProcessor` | UE08 |

Weitere Beispiele werden im Laufe der Lehrveranstaltung auf zusätzlichen Branches ergänzt.

## Nutzung

Um ein bestimmtes Beispiel auszuchecken:

```bash
git clone https://github.com/mrckurz/OPR-Exercise-Examples.git
cd OPR-Exercise-Examples
git checkout <branch-name>
```

Im jeweiligen Branch befindet sich ein eigenes README, das das Beispiel und den Lerneffekt näher beschreibt.
