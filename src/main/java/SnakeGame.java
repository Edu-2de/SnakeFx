import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends Pane {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    private final Canvas canvas;
    private final LinkedList<Point> snake;
    private Point food;

    public SnakeGame() {
        this.canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        this.getChildren().add(canvas);

        this.snake = new LinkedList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));

        spawnFood();
        startGameLoop();
    }

    private void spawnFood() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
            food = new Point(x, y);
        } while (snake.contains(food));
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        };
        timer.start();
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.RED);
        gc.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        gc.setFill(Color.LIMEGREEN);
        for (Point p : snake) {
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    private static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
        @Override
        public int hashCode() {
            return x * 31 + y;
        }
    }
}