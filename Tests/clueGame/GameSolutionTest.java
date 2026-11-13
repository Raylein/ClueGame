package clueGame;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import clueGame.*;

public class GameSolutionTest {
    private static Board board;
    @BeforeAll
    public static void setUp() throws Exception {
        board = Board.getInstance();
        // Adjust file names/paths to match your project
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    private static Card person(String n) { return new Card(n, CardType.PERSON); }
    private static Card weapon(String n) { return new Card(n, CardType.WEAPON); }
    private static Card room(String n)   { return new Card(n, CardType.ROOM); }

    @Test
    public void accusationCorrect(){
        Solution s = board.getSolution();
        assertTrue(board.checkAccusation(s.getPerson(), s.getWeapon(), s.getRoom()));
    }

    @Test
    public void accusationWrongPerson() {
        Solution s = board.getSolution();
        assertFalse(board.checkAccusation(person("Nobody"), s.getWeapon(), s.getWeapon()));
    }

    @Test
    public void accusationWrongRoom() {
        Solution s = board.getSolution();
        assertFalse(board.checkAccusation(s.getPerson(), s.getWeapon(), room("Nowhere")));
    }

    @Test
    public void accusationAllWrong() {
        assertFalse(board.checkAccusation(person("Nobody"), weapon("Nothing"), room("Nowhere")));
    }
}
