package tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {

    private final Game game  = new Game();
    private BoardPane  board;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        MenuBar  menuBar = new MenuBar();
        Menu     menu    = new Menu("Game");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem exit    = new MenuItem("Exit");
        menu.getItems().addAll(newGame, exit);
        menuBar.getMenus().add(menu);

        board = new BoardPane(game);

        FXMLLoader          loader        = new FXMLLoader(getClass().getResource("StatusBar.fxml"));
        HBox                statusBar     = loader.load();
        StatusBarController statusControl = loader.getController();
        statusControl.bindTo(game);

        root.setTop(menuBar);
        root.setCenter(board);
        root.setBottom(statusBar);

        game.stateProperty().addListener((o, a, b) -> {
            if (b == GameState.WON || b == GameState.DRAW) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(b == GameState.WON
                        ? game.winnerProperty().get() + " gewinnt!"
                        : "Unentschieden");
                alert.setContentText("Neues Spiel über Game → New Game.");
                alert.showAndWait();
            }
        });

        newGame.setOnAction(e -> { game.reset(); board.refresh(); });
        exit.setOnAction(e -> stage.close());

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("board.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Tic-Tac-Toe");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
