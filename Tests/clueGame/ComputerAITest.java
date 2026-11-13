package clueGame;

import org.junit.jupiter.api.BeforeAll;

public class ComputerAITest {
    private static Board board;
    @BeforeAll
    public static void setUp() throws Exception {
        board = Board.getInstance();
        // Adjust file names/paths to match your project
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }
}
