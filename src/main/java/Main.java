import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SnakeGame game = new SnakeGame();
        Scene scene = new Scene(game);

        primaryStage.setTitle("SnakeFx");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Garante que o SnakeGame receba o foco após a janela abrir
        game.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}