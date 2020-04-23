package HW4;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Tara Harkins
 * CSIT 150: Homework 5
 * Connect 4 Window class:
 * This class contains game board by extending application.
 * Sets up stage, scene, and Connect 4 Pane.
 * 12/14/19
 */


public class Connect4Window extends Application {
    private Connect4Pane pane;
    private int row = 6;
    private int col = 7;
    private Con4Game game;
    private Menu Help, About, KeepScores, Save, Load;
    private BorderPane display;
    private Button clearButton = new Button("Restart Game");
    File fileName;

    /**
     * Constructor class that calls Connect4Pane, Con4Game class, and paint() method.
     */

    public Connect4Window() {
        this.game = new Con4Game(row, col);
        this.pane = new Connect4Pane(game);
        pane.paint();
    }

    /**
     * Stage class that sets up border pane, scene, and stage. Also listens for mouse clicks, checks winner,
     * and has restart button.
     *
     * @param primaryStage
     */

    public void start(Stage primaryStage) {
        display = new BorderPane();
        MenuBar bar = buildMenuBar();
        display.setTop(bar);
        display.setCenter(pane);
        display.setRight(clearButton);
        Scene scene = new Scene(display, 420, 420);
        primaryStage.setTitle("Connect 4 Game. Enjoy! (:");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        clearButton.setOnMouseClicked(event -> restartGame());

        pane.setOnMouseReleased(event -> //Listens to mouse clicks and places tokens in correct spot.
        {
            pane.getTokens().makeMove((int) event.getX() / 60); //calls MakeMove method to move tokens
            if (pane.getTokens().checkWin()) {//if there is a winner, sets win alert and disables board until restarted
                pane.setWinAlert();
                pane.setDisable(true);
            }
            pane.paint(); //paints pane
        });
    }

    /**
     * Resets game to original format
     */
    public void restartGame() {
        game.resetGame();
        game = new Con4Game(row, col);
        pane = new Connect4Pane(game);
        display.setCenter(pane);
//        display.setRight(clearButton);
        pane.paint();
        clearButton.toFront();
        clearButton.setOnMouseClicked(event -> {
            restartGame();
        });
        pane.setOnMouseReleased(event ->
        {
            pane.getTokens().makeMove((int) event.getX() / 60);
            if (pane.getTokens().checkWin()) {
                pane.setWinAlert();
                pane.setDisable(true);
            }
            pane.paint();
        });
    }

    /**
     * Builds menu bar
     *
     * @return menu bar with file and help options
     */
    private MenuBar buildMenuBar() {
        Menu aboutMenu = buildAboutMenu();
        Menu scoreMenu = buildScoreMenu();
        Menu fileMenu = buildFileMenu();
        return new MenuBar(aboutMenu, scoreMenu, fileMenu);
    }

    /**
     * Creates About menu with About and Help options
     *
     * @return About menu item
     */
    private Menu buildAboutMenu() {
        Menu about = new Menu("About");
        About = new Menu("About this Game");
        Help = new Menu("Helpful Tips");
        about.getItems().addAll(About);
        about.getItems().addAll(Help);
        AboutList listener = new AboutList();
        About.setOnAction(listener);
        Help.setOnAction(listener);
        return about;
    }

    /**
     * About menu window
     */
    private void aboutMenu() {
        String s = "This is my Connect 4 Game. Each player has their own color of token and will place it in column they choose.\n" +
                "The player who is the first to get four tokens in a row wins! The tokens that have won the game will turn turquoise.\n" +
                "Made by Tara Harkins";
        Alert a = (new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK));
        a.setHeaderText("About Connect 4 Game:");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // stretch box to show all of message
        a.show();
    }

    /**
     * Help menu window
     */
    private void helpMenu() {
        String s = "+ Player one has the pink token. Player two has the white token.\n" +
                "+ Using your mouse, click on the column you want to place your token in.  The first player to get 4 tokens in a row wins!\n" +
                "+ If you want to restart the game, click on the Restart Game button located in top right corner." +
                "\n+ To check the score, click Scores menu option.\n+ To save a current game score so you can pick up where you left off later, go to File menu and select Save option.\n" +
                "+ To load a past game score, go to File menu and select Load option.\n\n\t\tEnjoy playing my Connect 4 Game! (:\t\t";
        Alert a = (new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK));
        a.setHeaderText("Helpful Tips:");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // stretch box to show all of message
        a.show();
    }

    /**
     * Class for active listener in About menu
     */
    private class AboutList implements EventHandler<javafx.event.ActionEvent> {
        //If the About MiniTextEditor is clicked
        public void handle(javafx.event.ActionEvent e) {
            if (e.getSource() == About) {
                //Prompt to show about menu.
                aboutMenu();
            } else if (e.getSource() == Help) {
                helpMenu();
            } else {
                //closes program
                System.exit(0);
            }
        }
    }

    /**
     * Creates Score menu with Scores for Players option
     *
     * @return Score menu item
     */
    private Menu buildScoreMenu() {
        Menu Scores = new Menu("Score");
        KeepScores = new Menu("Score for Players");
        Scores.getItems().addAll(KeepScores);
        ScoreList listener = new ScoreList();
        KeepScores.setOnAction(listener);
        return Scores;
    }

    /**
     * Score menu window
     */
    private void scoreMenu() {
        String s = "Player 1 Winning Score: " + pane.player1Wins + "\n\nPlayer 2 Winning Score: " + pane.player2Wins;
        Alert a = (new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK));
        a.setHeaderText("Score:");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // stretch box to show all of message
        a.show();
    }

    /**
     * Class for Active Listener in Score menu
     */
    private class ScoreList implements EventHandler<javafx.event.ActionEvent> {
        //If the Score item is clicked
        public void handle(javafx.event.ActionEvent e) {
            if (e.getSource() == KeepScores) {
                //Prompt to show Score menu.
                scoreMenu();
            } else {
                //closes program
                System.exit(0);
            }
        }
    }

    /**
     * Builds File menu
     *
     * @return file menu items
     */
    private Menu buildFileMenu() {
        Menu file = new Menu("File");

        Save = new Menu("Save Scores");
        Load = new Menu("Load Scores");
        ObservableList<MenuItem> items = file.getItems();
        items.addAll(Save, Load);
        MyListener listener = new MyListener();

        Save.setOnAction(listener);
        Load.setOnAction(listener);
        return file;
    }

    /**
     * Class listens to what user chooses for File menu
     */
    private class MyListener implements EventHandler<ActionEvent> {

        @Override
        //if File menu item is clicked
        public void handle(ActionEvent event) {
            if (event.getSource() == Load) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("./"));

                File result = fileChooser.showOpenDialog(null);
                if (result == null) {
                    showInfoDialog("You did not select a file.");
                    return;
                }

                fileName = result;
                showFile();

            } else if (event.getSource() == Save) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("./"));
                File result = fileChooser.showSaveDialog(null);
                if (result != null) {
                    fileName = result;
                    writeFile();
                }
            }
        }
    }

    /**
     * Scans in scores from saved file
     */
    private void showFile() {
        try {
            Scanner scan = new Scanner(fileName);
            pane.player1Wins = scan.nextInt();
            pane.player2Wins = scan.nextInt();
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Writes scores to file
     */
    private void writeFile() {
        try {
            String info = pane.player1Wins + " " + pane.player2Wins;
            FileWriter out = new FileWriter(fileName);
            out.write(info);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * creates alert pop-up that displays message
     *
     * @param s
     * @return message to display
     */
    private String showInfoDialog(String s) {
        Alert a = (new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK));
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // stretch box to show all of message
        a.show();
        return s;
    }

    /**
     * Main method that launches application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
