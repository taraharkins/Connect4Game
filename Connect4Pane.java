package HW4;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * Tara Harkins
 * CSIT 150: Homework 5
 * Connect 4 Pane Class:
 * Creates graphic display extending Pane. Also responsible for setting game over alert.
 * 12/14/19
 */


public class Connect4Pane extends Pane {
    private final int ROWCOUNT = 6;
    private final int COLCOUNT = 7;
    private Con4Game tokens;
    private final int colWidth = 60;
    private final int rowWidth = 60;
    private final int radius = 29;
    private final int menuOffset = 30;
    Rectangle col;
    Circle circ;
    public Label label = new Label("");
    int currentPlayer;
    int[][] piece;
    public static int player1Wins = 0;
    public static int player2Wins = 0;

    /**
     * Constructor method that assigns Con4Game token and calls paint method
     *
     * @param tokenIn
     */
    public Connect4Pane(Con4Game tokenIn) {
        tokens = tokenIn;
        paint();
        currentPlayer = 0;
    }

    /**
     * Returns tokens on gameboard
     *
     * @return tokens
     */
    public Con4Game getTokens() {
        return tokens;
    }

    /**
     * Sets win alert if there is a winner, also counts number of wins per player
     */

    public void setWinAlert() {
        String s = "";
        if (currentPlayer == 0) {
            s = "PLAYER 2 WINS!";
            player2Wins++;
        } else if (currentPlayer == 1) {
            s = "PLAYER 1 WINS!";
            player1Wins++;
        }
        Alert a = (new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK));
        a.setHeaderText("Game Over");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // stretch box to show all of message
        a.show();
    }

    /**
     * Paint method to draw game board, circles, and current player label
     */
    public void paint() {
        getChildren().clear();//clear the last time pane was painted
        InnerShadow dropShadow = new InnerShadow();
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(1);
        Rectangle topRect = new Rectangle(0, 0, 420, 30); //add rectangle for top of pane
        topRect.setFill(Color.BLACK);
        getChildren().add(topRect);
        //label for player turn to top of pane
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("serif", 20));
        label.setText("Player 1's turn!");
        getChildren().addAll(label);
        int x = 0;
        //Creates columns for game board
        for (int r = 0; r < ROWCOUNT; r++) {
            for (int c = 0; c < COLCOUNT; c++) {
                col = new Rectangle(x, 0, 60, 365);
                col.setStroke(Color.WHITE);
                col.setStrokeWidth(2);
                col.setFill(Color.SLATEGRAY);
                col.setY(30);
                getChildren().add(col);
                x += 60;
            }
        }

        //Creates circles for each player to put on board
        piece = tokens.getGameArray();

        for (int r = 0; r < ROWCOUNT; r++) {
            for (int c = 0; c < COLCOUNT; c++) {
                int new_r = (5 - r);
                if (piece[r][c] == 0) { //player 1 circle
                    circ = new Circle(colWidth * c + radius, rowWidth * new_r + radius + menuOffset, radius);
                    circ.setFill(Color.LIGHTCORAL);
                    circ.setEffect(dropShadow);
                    getChildren().add(circ);
                } else if (piece[r][c] == 1) { //player 2 circle
                    circ = new Circle(colWidth * c + radius, rowWidth * new_r + radius + menuOffset, radius);
                    circ.setFill(Color.ANTIQUEWHITE);
                    circ.setEffect(dropShadow);
                    getChildren().add(circ);
                } else if (piece[r][c] == 2) { //winner circle that turns green when to highlight winner move
                    circ = new Circle(colWidth * c + radius, rowWidth * new_r + radius + menuOffset, radius);
                    circ.setStroke(Color.DARKORCHID);
                    col.setStrokeWidth(1);
                    circ.setFill(Color.LIGHTSEAGREEN);
                    circ.setEffect(dropShadow);
                    getChildren().add(circ);
                }
            }
        }
        //sets label for which players turn it is
        if (currentPlayer == 0) {
            label.setText("Player 1's turn!");
        } else if (currentPlayer == 1) {
            label.setText("Player 2's turn!");
        }
        currentPlayer = (currentPlayer + 1) % 2;
    }
}









