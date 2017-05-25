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

    private static Group squareGroup = new Group();
    private static Group pieceGroup = new Group();
    private static int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private static OnSquareClickListener onSquareClickListener;

    public static void main(String[] args) {
        launch(args);
    }

    public static void addPiece(int player, int i, int j) {
        Color color = null;
        if (player == 1) color = Color.WHITE;
        else if (player == -1) color = Color.BLACK;
        Piece piece = new Piece(color, i, j);
        pieceGroup.getChildren().add(piece);
        board[i][j] = player;
    }

    public static void setOnSquareClickListener(OnSquareClickListener listener) {
        onSquareClickListener = listener;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gomoku");
        primaryStage.setScene(new Scene(createPane()));
        primaryStage.show();
        primaryStage.setResizable(false);
        createBoard(board);
    }

    private static Pane createPane() {
        Pane pane = new Pane();
        pane.setPrefWidth(PREF_SIZE);
        pane.setPrefHeight(PREF_SIZE);
        pane.getChildren().addAll(squareGroup);
        pane.getChildren().addAll(pieceGroup);
        createBoard(board);
        return pane;
    }

    private static void createBoard(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Square square = new Square(i, j);
                squareGroup.getChildren().add(square);
                if (board[i][j] != 0) addPiece(board[i][j], i, j);
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

            setOnMouseClicked(event -> {
                if (onSquareClickListener != null && board[x][y] == 0) {
                    onSquareClickListener.onSquareClick(x, y);
                    addPiece(1, x, y);
                }
            });
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

    public interface OnSquareClickListener {
        void onSquareClick(int x, int y);
    }
}
