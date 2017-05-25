package MinMax;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GomokuApp extends Application {
    private static final int BOARD_SIZE = 15;
    private static final float TILE_SIZE = 60f;
    private static final float PIECE_SIZE = 0.5f * TILE_SIZE;
    private static final float PREF_SIZE = BOARD_SIZE * TILE_SIZE;

    private Group squareGroup = new Group();
    private Group pieceGroup = new Group();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gomoku");
        primaryStage.setScene(new Scene(createPane()));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    private Pane createPane() {
        Pane pane = new Pane();
        pane.setPrefWidth(PREF_SIZE);
        pane.setPrefHeight(PREF_SIZE);
        pane.getChildren().addAll(squareGroup);
        pane.getChildren().addAll(pieceGroup);
        createBoard();
        return pane;
    }

    private void createBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Square square = new Square(i, j);
                squareGroup.getChildren().add(square);
                Piece piece = new Piece(Color.WHITE, i, j);
                pieceGroup.getChildren().add(piece);
            }
        }
    }

    private static class Square extends Rectangle {
        Square(int x, int y) {
            setWidth(TILE_SIZE);
            setHeight(TILE_SIZE);
            relocate(x * TILE_SIZE, y * TILE_SIZE);
            setStroke(Color.BLACK);
            setFill(Color.GRAY);
        }
    }

    private static class Piece extends Ellipse {
        Piece(Color color, int x, int y) {
            relocate(x * TILE_SIZE, y * TILE_SIZE);
            setFill(color);
            setRadiusX(PIECE_SIZE);
            setRadiusY(PIECE_SIZE);
            setStroke(Color.DARKGRAY);
            setStrokeWidth(0.01 * TILE_SIZE);
            float translation = TILE_SIZE - PIECE_SIZE;
            setTranslateX(translation);
            setTranslateY(translation);
        }
    }
}
