# JavaFX – Tic-Tac-Toe (UE09)

Begleitbeispiel zur **UE09** der Lehrveranstaltung *Objektorientierte Programmierung* (Mobile Computing, FH OÖ – Campus Hagenberg). Das Projekt ist absichtlich klein gehalten und soll die zentralen JavaFX-Bausteine zeigen, die in der Hausübung (Snake) gebraucht werden:

- **Layout** mit `BorderPane` + `GridPane`
- **MVC-Trennung**: `Game` / `Player` (Modell, kein JavaFX-Import) ⟷ `BoardPane` (View) ⟷ `App` (Wiring)
- **Property Binding** über `ObjectProperty`/`ReadOnlyObjectProperty` und `Bindings.createStringBinding(...)`
- **FXML** für die Statusleiste (`StatusBar.fxml` + `StatusBarController`)
- **CSS** für das Spielfeld (`board.css`, inkl. Hover-Effekten und farbigen Spielsymbolen)
- Eingaben über `MenuBar` und `Button.setOnAction(...)`
- Game-Over-Dialog via `Alert`, getriggert durch einen `ChangeListener` auf `stateProperty()`

> Snake hat zusätzlich `AnimationTimer`/`Timeline`, `onKeyPressed` mit `requestFocus()` und einen Konfigurationsdialog mit Validierung. Diese Bausteine sind hier **nicht** enthalten und bleiben Aufgabe der Hausübung.

## Projektstruktur

```
pom.xml                                     # Maven-Projektbeschreibung
src/main/java/tictactoe/
├── App.java                                # Application, Menü, Wiring, Game-Over-Dialog
├── Game.java                               # Spiellogik + Properties
├── GameState.java                          # enum: RUNNING / WON / DRAW
├── Player.java                             # enum: X / O / NONE
├── BoardPane.java                          # GridPane mit 9 Buttons, reagiert auf Properties
└── StatusBarController.java                # FXML-Controller mit StringBindings
src/main/resources/tictactoe/
├── StatusBar.fxml                          # FXML-Definition der Statusleiste
└── board.css                               # Styling für Board, Zellen, Statusleiste
```

## Voraussetzungen

- **JDK 17 oder neuer** (https://adoptium.net) — z.&nbsp;B. Eclipse Temurin 17 oder 21.
- Sonst **nichts**. Maven wird über den mitgelieferten Wrapper (`mvnw` / `mvnw.cmd`) automatisch in der richtigen Version geholt, JavaFX wird beim ersten Build von Maven Central heruntergeladen. Du musst keine JavaFX-SDK-ZIP von Hand herunterladen oder Pfade konfigurieren.

## Schnellstart (alle Plattformen)

```bash
git clone https://github.com/mrckurz/OPR-Exercise-Examples.git
cd OPR-Exercise-Examples
git checkout javafx-example

# macOS / Linux
./mvnw javafx:run

# Windows
mvnw.cmd javafx:run
```

Beim ersten Aufruf lädt der Wrapper Maven + JavaFX-Artefakte herunter (~30 Sekunden). Danach öffnet sich das Spielfenster.

## In der IDE öffnen

Alle drei IDEs erkennen das Projekt am `pom.xml` selbständig — keine manuellen Bibliotheks- oder VM-Optionen nötig.

### VS Code

1. **Extension Pack for Java** installieren (falls noch nicht vorhanden).
2. Ordner in VS Code öffnen → das Java-Plugin importiert das Maven-Projekt automatisch.
3. Im **Maven**-Tab links: `tictactoe-javafx → Plugins → javafx → javafx:run` doppelklicken.
4. Alternativ in `App.java` über dem `main` auf **Run** klicken.

### IntelliJ IDEA

1. **File → Open…** → den Repo-Ordner wählen → IntelliJ erkennt `pom.xml` und importiert das Projekt.
2. Im **Maven**-Toolfenster (rechts): `tictactoe-javafx → Plugins → javafx → javafx:run` doppelklicken.
3. Alternativ: Rechtsklick auf `App.java` → **Run 'App.main()'**.

### Eclipse

1. **File → Import… → Maven → Existing Maven Projects** → den Repo-Ordner wählen → Finish.
2. Rechtsklick auf das Projekt → **Run As → Maven build…** → im Feld *Goals* `javafx:run` eintragen → Run.

## Bedienung

- Klick auf eine freie Zelle = Zug des aktuellen Spielers (X beginnt).
- **Game → New Game** setzt das Brett zurück.
- **Game → Exit** schließt das Fenster.
- Bei Sieg oder Unentschieden öffnet sich automatisch ein Dialog.

## Was macht Maven hier eigentlich?

Maven liest `pom.xml`, lädt die JavaFX-Artefakte aus Maven Central und ruft `javac` / `java` mit den korrekten `--module-path`- und `--add-modules`-Argumenten auf. Wer das von Hand machen will, kann jederzeit:

```bash
./mvnw clean compile
java --module-path ~/.m2/repository/org/openjfx/javafx-controls/21.0.4 \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes tictactoe.App
```

— aber genau diesen Boilerplate nimmt einem Maven ab.
