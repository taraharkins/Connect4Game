package HW4;

/**
 * Tara Harkins
 * CSIT 150: Homework 5
 * Con4Game Class:
 * This class contains the logic for Connect 4 game. Sets game array, resets game array, determines winning moves,
 * and current player.
 * 12/14/19
 *
 * @author Catherine Anderson
 * created Spring 2013
 * revised Spring 2014
 */
public class Con4Game {

    private int rows;
    private int cols;
    public int[][] gameArray; // array that indicated which players tokens
    private int currentPlayer;

    /**
     * Sets Con4Game
     * @param r
     * @param c
     */
    Con4Game(int r, int c) {
        rows = r;
        cols = c;
        setGameArray(new int[rows][cols]);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                getGameArray()[row][col] = -1;  // -1 indicated no token present
            }
        }
        currentPlayer = 0;
    }

    /**
     * Make move method for tokens
     * @param c
     */
    public void makeMove(int c) {
        for (int row = 0; row < rows; row++) {
            if (getGameArray()[row][c] == -1) {
                getGameArray()[row][c] = currentPlayer;
                break;
            }
        }
        nextPlayer();
    }

    /**
     * Resets game array to original to restart a game
     */
    public void resetGame() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                getGameArray()[row][col] = -1;  // -1 indicated no token present
            }
        }
        currentPlayer = 0;
    }

    /**
     * Gets tokens
     * @param r
     * @param c
     * @return
     */
    public int getToken(int r, int c) {
        return getGameArray()[r][c];
    }

    /**
     * Determines next player
     */
    public void nextPlayer() {
        currentPlayer++;
        currentPlayer %= 2;
    }

    /**
     * Gets current player
     * @return the currentPlayer
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets game array
     * @return gameArray
     */

    public int[][] getGameArray() {
        return gameArray;
    }

    /**
     * Sets game array
     * @param gameArray
     */

    public void setGameArray(int[][] gameArray) {
        this.gameArray = gameArray;
    }

    /**
     * Method to determine winning connect 4 moves
     * @return winning move
     */
    boolean checkWin() {
        int player = (currentPlayer + 1) % 2;
        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length; j++)
                //column wins
                if (j < (cols - 3) && gameArray[i][j] == player && gameArray[i][j + 1] == player &&
                        gameArray[i][j + 2] == player && gameArray[i][j + 3] == player) {
                    gameArray[i][j] = 2;
                    gameArray[i][j + 1] = 2;
                    gameArray[i][j + 2] = 2;
                    gameArray[i][j + 3] = 2;
                    currentPlayer = 2;
                    return true;
                //row wins
                } else if (i < (rows - 3) && gameArray[i][j] == player && gameArray[i + 1][j] == player &&
                        gameArray[i + 2][j] == player && gameArray[i + 3][j] == player) {
                    gameArray[i][j] = 2;
                    gameArray[i + 1][j] = 2;
                    gameArray[i + 2][j] = 2;
                    gameArray[i + 3][j] = 2;
                    currentPlayer=2;
                    return true;
                // diagonal / wins
                } else if (j < (cols - 3) && i < (rows - 3) && gameArray[i][j] == player && gameArray[i + 1][j + 1] == player
                        && gameArray[i + 2][j + 2] == player && gameArray[i + 3][j + 3] == player)
                {
                    gameArray[i][j] = 2;
                    gameArray[i + 1][j + 1] = 2;
                    gameArray[i + 2][j + 2] = 2;
                    gameArray[i + 3][j + 3] = 2;
                    currentPlayer = 2;
                    return true;
                // diagonal \ wins
                } else
                    if (j < (cols - 3) && i > 2 && gameArray[i][j] == player && gameArray[i - 1][j + 1] == player &&
                        gameArray[i - 2][j + 2] == player && gameArray[i - 3][j + 3] == player)
                {
                    gameArray[i][j] = 2;
                    gameArray[i - 1][j + 1] = 2;
                    gameArray[i - 2][j + 2] = 2;
                    gameArray[i - 3][j + 3] = 2;
                    currentPlayer = 2;
                    return true;
                }
        }
        return false;
    }
}
