package clueGame;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import clueGame.*;

public class GameSolutionTest {
    private static Board board;

    private BoardCell centerOf(Card roomCard) {
        for (Room r : board.getRoomMap().values())
            if (r.getName().equals(roomCard.getCardName()) && r.getCenterCell() != null)
                return r.getCenterCell();
        return null;
    }

    private List<BoardCell> walkways() {
        List<BoardCell> l = new ArrayList<>();
        for (int r = 0; r < board.getNumRows(); r++)
            for (int c = 0; c < board.getNumColumns(); c++)
                if (board.getCell(r, c).getInitial() == 'W') l.add(board.getCell(r, c));
        return l;
    }

    private List<BoardCell> roomCenters() {
        List<BoardCell> l = new ArrayList<>();
        for (Room r : board.getRoomMap().values())
            if (r.getCenterCell() != null) l.add(r.getCenterCell());
        return l;
    }

    private Card cardFor(BoardCell cell) {
        for (Card c : board.getRoomCards())
            if (c.getCardName().equals(cell.getRoom().getName())) return c;
        return null;
    }

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

    @Test
    public void handleSuggestionFirstInOrderWins() {
        List<Player> ps = clearedPlayers();
        Card w = weapon("Z"), r = room("Y");
        ps.get(2).updateHand(w);
        ps.get(3).updateHand(r);
        Solution sug = new Solution(person("X"), room("Y"), weapon("Z"));
        assertEquals(w, board.handleSuggestion(sug, ps.get(1)));
        assertEquals(w, board.handleSuggestion(sug, ps.get(3)));
        assertEquals(r, board.handleSuggestion(sug, ps.get(2)));
    }

    @Test
    public void computerSuggestionUsesGivenRoomAndOnlyUnseenCards() {
        ComputerPlayer cp = new ComputerPlayer("CP", "red", 0, 0);
        Card[] people = board.getPeopleCards();
        Card[] weapons = board.getWeaponCards();
        Card[] rooms = board.getRoomCards();
        for (int i = 0; i < people.length; i++) if (i != 2) cp.updateSeen(people[i]);
        for (int i = 0; i < weapons.length; i++) if (i != 3) cp.updateSeen(weapons[i]);
        cp.currentCell = centerOf(rooms[0]);
        Solution s = cp.createSuggestion(people, rooms, weapons);
        assertEquals(rooms[0], s.getRoom());
        assertEquals(people[2], s.getPerson());
        assertEquals(weapons[3], s.getWeapon());
    }

    @Test
    public void computerSuggestionRandomAmongMultipleUnseen() {
        ComputerPlayer cp = new ComputerPlayer("CP", "red", 0, 0);
        Card[] people = board.getPeopleCards();
        Card[] weapons = board.getWeaponCards();
        Card[] rooms = board.getRoomCards();
        for (int i = 2; i < people.length; i++) cp.updateSeen(people[i]);
        for (int i = 2; i < weapons.length; i++) cp.updateSeen(weapons[i]);
        cp.currentCell = centerOf(rooms[0]);
        Set<Card> ps = new HashSet<>(), ws = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            Solution s = cp.createSuggestion(people, rooms, weapons);
            ps.add(s.getPerson());
            ws.add(s.getWeapon());
        }
        assertEquals(Set.of(people[0], people[1]), ps);
        assertEquals(Set.of(weapons[0], weapons[1]), ws);
    }

    @Test
    public void selectTargetNoRoomsIsRandom() {
        ComputerPlayer cp = new ComputerPlayer("CP", "red", 0, 0);
        cp.currentCell = walkways().get(0);
        BoardCell[] targets = walkways().subList(1, 4).toArray(new BoardCell[0]);
        Set<BoardCell> picked = new HashSet<>();
        for (int i = 0; i < 200; i++) picked.add(cp.selectTarget(targets, board.getRoomCards()));
        assertEquals(new HashSet<>(Arrays.asList(targets)), picked);
    }

    @Test
    public void selectTargetPrefersUnseenRoom() {
        BoardCell roomCell = roomCenters().get(0);
        List<BoardCell> list = new ArrayList<>(walkways().subList(1, 4));
        list.add(roomCell);
        BoardCell[] targets = list.toArray(new BoardCell[0]);
        for (int i = 0; i < 50; i++) {
            ComputerPlayer cp = new ComputerPlayer("CP", "red", 0, 0);
            cp.currentCell = walkways().get(0);
            assertEquals(roomCell, cp.selectTarget(targets, board.getRoomCards()));
        }
    }

    @Test
    public void selectTargetMultipleUnseenRoomsRandomAmongRooms() {
        List<BoardCell> centers = roomCenters();
        List<BoardCell> list = new ArrayList<>(walkways().subList(1, 4));
        list.add(centers.get(0));
        list.add(centers.get(1));
        BoardCell[] targets = list.toArray(new BoardCell[0]);
        Set<BoardCell> picked = new HashSet<>();
        for (int i = 0; i < 300; i++) {
            ComputerPlayer cp = new ComputerPlayer("CP", "red", 0, 0);
            cp.currentCell = walkways().get(0);
            picked.add(cp.selectTarget(targets, board.getRoomCards()));
        }
        assertEquals(Set.of(centers.get(0), centers.get(1)), picked);
    }

    @Test
    public void selectTargetSeenRoomIsRandomAmongAll() {
        BoardCell roomCell = roomCenters().get(0);
        List<BoardCell> list = new ArrayList<>(walkways().subList(1, 4));
        list.add(roomCell);
        BoardCell[] targets = list.toArray(new BoardCell[0]);
        ComputerPlayer cp = new ComputerPlayer("CP", "red", 0, 0);
        cp.currentCell = walkways().get(0);
        cp.updateSeen(cardFor(roomCell));      // the room has already been seen
        Set<BoardCell> picked = new HashSet<>();
        for (int i = 0; i < 300; i++) picked.add(cp.selectTarget(targets, board.getRoomCards()));
        assertEquals(new HashSet<>(Arrays.asList(targets)), picked);
    }
}

