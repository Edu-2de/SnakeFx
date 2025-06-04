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

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private Direction currentDirection = Direction.RIGHT;
    private boolean moved = false; // para evitar reversão imediata
    private boolean running = true;
    private int score = 0; // Corrigido: score é atributo da classe, não do construtor

    public SnakeGame() {
        this.canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        this.getChildren().add(canvas);

        this.snake = new LinkedList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));

        setFocusTraversable(true);
        // Adiciona suporte ao teclado para setas e WASD
        setOnKeyPressed(event -> {
            if (!moved) return;
            switch (event.getCode()) {
                case UP:
                case W:
                    if (currentDirection != Direction.DOWN) currentDirection = Direction.UP;
                    break;
                case DOWN:
                case S:
                    if (currentDirection != Direction.UP) currentDirection = Direction.DOWN;
                    break;
                case LEFT:
                case A:
                    if (currentDirection != Direction.RIGHT) currentDirection = Direction.LEFT;
                    break;
                case RIGHT:
                case D:
                    if (currentDirection != Direction.LEFT) currentDirection = Direction.RIGHT;
                    break;
                case R:
                    if (!running) restartGame();
                    break;
                default: break;
            }
            moved = false;
        });

        spawnFood();
        startGameLoop();

        // Solicita foco para garantir que as teclas funcionem
        this.setOnMouseClicked(e -> requestFocus());
        requestFocus();
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
        final long[] lastUpdate = {0};
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate[0] > 150_000_000) { // 150ms por movimento (~6.6 fps)
                    if (running) {
                        moveSnake();
                        draw();
                        moved = true;
                    } else {
                        draw(); // Redesenha para mostrar Game Over
                    }
                    lastUpdate[0] = now;
                }
            }
        };
        timer.start();
    }

    private void moveSnake() {
        Point head = snake.getFirst();
        Point newPoint = null;
        switch (currentDirection) {
            case UP: newPoint = new Point(head.x, head.y - 1); break;
            case DOWN: newPoint = new Point(head.x, head.y + 1); break;
            case LEFT: newPoint = new Point(head.x - 1, head.y); break;
            case RIGHT: newPoint = new Point(head.x + 1, head.y); break;
        }

        // Verifica colisão com borda ou consigo mesmo
        if (newPoint.x < 0 || newPoint.x >= WIDTH || newPoint.y < 0 || newPoint.y >= HEIGHT || snake.contains(newPoint)) {
            running = false;
            return;
        }

        snake.addFirst(newPoint);

        if (newPoint.equals(food)) {
            score++;
            spawnFood();
            // Cobra cresce: não remove a cauda!
        } else {
            snake.removeLast();
        }
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

        // Exibe a pontuação
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 10, 20);

        // Exibe Game Over se aplicável
        if (!running) {
            gc.setFill(Color.WHITE);
            gc.fillText("GAME OVER! Pressione R para reiniciar", 100, 200);
        }
    }

    private void restartGame() {
        snake.clear();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        currentDirection = Direction.RIGHT;
        score = 0;
        running = true;
        spawnFood();
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