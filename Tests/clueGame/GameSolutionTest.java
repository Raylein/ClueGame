package clueGame;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import clueGame.*;

public class GameSolutionTest {
    private static Board board;
    @BeforeAll
    public static void setUp() throws Exception{
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
    public void accusationWrongPerson(){
        Solution s = board.getSolution();
        assertFalse(board.checkAccusation(person("Nobody"), s.getWeapon(), s.getWeapon()));
    }

    @Test
    public void accusationWrongRoom(){
        Solution s = board.getSolution();
        assertFalse(board.checkAccusation(s.getPerson(), s.getWeapon(), room("Nowhere")));
    }

    @Test
    public void accusationWrongWeapon(){
        Solution s = board.getSolution();
        assertFalse(board.checkAccusation(s.getPerson(), weapon("Nothing"), s.getRoom()));
    }

    @Test
    public void accusationAllWrong(){
        assertFalse(board.checkAccusation(person("Nobody"), weapon("Nothing"), room("Nowhere")));
    }

    @Test
    public void disproveNoMatchReturnsNull(){
        Player p = new ComputerPlayer("P","red", 0, 0);
        p.updateHand(person("A"));
        p.updateHand(weapon("B"));
        p.updateHand(room("C"));
        Solution sug = new Solution(person("X"), room("Y"), weapon("Z"));
        assertNull(p.disproveSuggestion(sug));
    }

    @Test
    public void disproveOneMatchReturnThatCard(){
        Player p = new ComputerPlayer("P","red", 0, 0);
        p.updateHand(person("A"));
        Card w = weapon("Gun");
        p.updateHand(w);
        Solution sug = new Solution(person("X"), room("Y"), weapon("Gun"));
        assertEquals(w, p.disproveSuggestion(sug));
    }

    @Test
    public void disproveMultipleMatchesIsRandom(){
        Player p = new ComputerPlayer("P","red", 0, 0);
        Card a = person("A");
        Card b = weapon("B");
        Card c = room("C");
        p.updateHand(a);
        p.updateHand(b);
        p.updateHand(c);
        Solution sug = new Solution(a,b,c);
        int ca = 0;
        int cb = 0;
        int cc = 0;
        for (int i = 0; i < 300; i++){
            Card r = p.disproveSuggestion(sug);
            if (r.equals(a)){
                ca++;
            }
            else if(r.equals(b)){
                cb++;
            }
            else if(r.equals(c)){
                cc++;
            }
        }

        assertEquals(300, ca+cb+cc);
        assertTrue(ca > 0 && cb > 0 && cc > 0);
    }

    private List<Player> clearedPlayers() {
        List<Player> ps = board.getPlayers();
        for (Player p : ps) p.getHand().clear();
        return ps;
    }

    @Test
    public void handleSuggestionNobodyDisproves(){
        List<Player> ps = clearedPlayers();
        Solution sug = new Solution(person("X"), room("Y"), weapon("Z"));
        assertNull(board.handleSuggestion(sug, ps.get(0)));
    }

    @Test
    public  void handleSuggestionOnlySuggesterCanDisprove(){
        List<Player> ps = clearedPlayers();
        ps.get(1).updateHand(person("X"));
        Solution sug = new Solution(person("X"), room("Y"), weapon("Z"));
        assertNull(board.handleSuggestion(sug, ps.get(1)));
    }

    @Test
    public void handleSuggestionOnlyHumanCanDisprove(){
        List<Player> ps = clearedPlayers();
        Card x = person("X");
        ps.get(0).updateHand(x);
        Solution sug = new Solution(person("X"), room("Y"), weapon("Z"));
        assertEquals(x, board.handleSuggestion(sug, ps.get(2)));
    }

}
