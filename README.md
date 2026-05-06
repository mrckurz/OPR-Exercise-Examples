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
src/tictactoe/
├── App.java                  # Application, Menü, Wiring, Game-Over-Dialog
├── Game.java                 # Spiellogik + Properties
├── GameState.java            # enum: RUNNING / WON / DRAW
├── Player.java               # enum: X / O / NONE
├── BoardPane.java            # GridPane mit 9 Buttons, reagiert auf Properties
├── StatusBarController.java  # FXML-Controller mit StringBindings
├── StatusBar.fxml            # FXML-Definition der Statusleiste
└── board.css                 # Styling für Board, Zellen, Statusleiste
```

## Voraussetzungen

- JDK 17 oder neuer (https://adoptium.net)
- JavaFX SDK 17 oder neuer, entpackt in einem stabilen Verzeichnis (https://openjfx.io)
  - macOS:  `/Users/<name>/javafx-sdk-21`
  - Windows:`C:\javafx-sdk-21`
  - Linux:  `~/javafx-sdk-21`

Im Folgenden steht `<JFX>` als Platzhalter für deinen Pfad zum entpackten `javafx-sdk/lib`-Ordner.

## Variante A — VS Code

1. Repo klonen, Branch wechseln, Ordner in VS Code öffnen:
   ```bash
   git clone https://github.com/mrckurz/OPR-Exercise-Examples.git
   cd OPR-Exercise-Examples
   git checkout javafx-example
   code .
   ```
2. **Extension Pack for Java** installieren (falls noch nicht vorhanden).
3. Lege `.vscode/settings.json` mit folgendem Inhalt an (Pfad anpassen):
   ```json
   {
     "java.project.sourcePaths": ["src"],
     "java.project.outputPath":  "bin",
     "java.project.referencedLibraries": [
       "<JFX>/*.jar"
     ]
   }
   ```
4. Lege `.vscode/launch.json` an, damit JavaFX die richtigen Module bekommt:
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "Tic-Tac-Toe",
         "request": "launch",
         "mainClass": "tictactoe.App",
         "vmArgs": "--module-path <JFX> --add-modules javafx.controls,javafx.fxml"
       }
     ]
   }
   ```
5. **Run** → Konfiguration „Tic-Tac-Toe" wählen → ▶︎.

Alternativ direkt im Terminal (kompilieren + starten ohne IDE-Wizard):
```bash
javac --module-path <JFX> --add-modules javafx.controls,javafx.fxml -d bin src/tictactoe/*.java
cp src/tictactoe/StatusBar.fxml src/tictactoe/board.css bin/tictactoe/
java  --module-path <JFX> --add-modules javafx.controls,javafx.fxml -cp bin tictactoe.App
```

## Variante B — IntelliJ IDEA

1. **File → Open…** den Repo-Ordner wählen, anschließend Branch `javafx-example` über die VCS-Leiste auschecken.
2. **File → Project Structure… → Project**: SDK auf JDK 17+ setzen, Language Level entsprechend.
3. **File → Project Structure… → Modules → Sources**: `src` als *Sources Root* markieren.
4. **File → Project Structure… → Libraries → +**: `<JFX>` (den `lib`-Ordner) als Java-Library hinzufügen und dem Modul zuordnen.
5. **Run → Edit Configurations… → + → Application**:
   - *Main class:* `tictactoe.App`
   - *Modify options → Add VM options*:
     ```
     --module-path <JFX> --add-modules javafx.controls,javafx.fxml
     ```
6. ▶︎ Run.

## Variante C — Eclipse

1. **File → Import… → Git → Projects from Git** → Repo + Branch `javafx-example` auswählen → **Import as general project**.
2. Rechtsklick auf das Projekt → **Configure → Convert to Java Project**.
3. Rechtsklick → **Properties → Java Build Path → Source**: `src` als Source-Folder hinzufügen (falls nicht automatisch erkannt).
4. **Properties → Java Build Path → Libraries → Classpath → Add External JARs…**: alle `.jar`-Dateien aus `<JFX>` hinzufügen.
5. Rechtsklick auf `App.java` → **Run As → Run Configurations…**:
   - Tab *Arguments* → **VM arguments**:
     ```
     --module-path <JFX> --add-modules javafx.controls,javafx.fxml
     ```
6. ▶︎ Run.

## Bedienung

- Klick auf eine freie Zelle = Zug des aktuellen Spielers (X beginnt).
- **Game → New Game** setzt das Brett zurück.
- **Game → Exit** schließt das Fenster.
- Bei Sieg oder Unentschieden öffnet sich automatisch ein Dialog.
