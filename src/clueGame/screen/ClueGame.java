package clueGame.screen;

import clueGame.*;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import javax.swing.*;

public class ClueGame extends JFrame {
    public ClueGame(Board board) {
        setTitle("Clue Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GameControlPanel controlPanel = new GameControlPanel();
        ClueCardPanel cardPanel = new ClueCardPanel();

        add(board, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.EAST);

        HumanPlayer human = board.getPlayerCharacter();
        controlPanel.setTurn(human);
        cardPanel.updatePanels(human);

        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        Board board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        try {
            board.initialize();
        } catch (FileNotFoundException | BadConfigFormatException e) {
            JOptionPane.showMessageDialog(null, "Could not load config: " + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    null,
                    "Welcome to Clue!\n\nSolve the mystery and find the culprit!",
                    "Welcome to Clue",
                    JOptionPane.INFORMATION_MESSAGE
            );

            ClueGame game = new ClueGame(board);
            game.setVisible(true);

            board.startGame();
        });
    }
}
