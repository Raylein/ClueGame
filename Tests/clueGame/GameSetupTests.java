package clueGame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameSetupTests {
    private static Board board;
    @BeforeAll
    public static void setUp() throws FileNotFoundException, BadConfigFormatException{
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }
    // Load people and weapons from ClueSetup.txt and ensure the data was loaded properly.
    @Test
    public void loadPeopleCards(){
        Card[] peopleCards = board.getPeopleCards();
        assertEquals("Luigi",peopleCards[0].getCardName());
        assertEquals("Princess Peach",peopleCards[1].getCardName());
        assertEquals("King Boo",peopleCards[2].getCardName());
        assertEquals("Mario",peopleCards[3].getCardName());
        assertEquals("Professor E. Gadd",peopleCards[4].getCardName());
        assertEquals("Bowser",peopleCards[5].getCardName());
    }

    @Test
    public void loadWeaponCards(){
        Card[] weaponCards = board.getWeaponCards();
        assertEquals("Mr. L",weaponCards[0].getCardName());
        assertEquals("Poltergust 3000",weaponCards[1].getCardName());
        assertEquals("Fire Flower", weaponCards[2].getCardName());
        assertEquals("Poisoned Mushroom",weaponCards[3].getCardName());
        assertEquals("Blue Shell",weaponCards[4].getCardName());
    }

    @Test
    public void loadRoomCards(){
        Card[] roomCards = board.getRoomCards();
        assertEquals("Conservatory",roomCards[0].getCardName());
        assertEquals("Kitchen",roomCards[1].getCardName());
        assertEquals("Projection Room",roomCards[2].getCardName());
        assertEquals("Dining Room",roomCards[3].getCardName());
        assertEquals("Laundry Room",roomCards[4].getCardName());
        assertEquals("Foyer",roomCards[5].getCardName());
        assertEquals("Graveyard",roomCards[6].getCardName());
        assertEquals("Bathroom",roomCards[7].getCardName());
        assertEquals("Ballroom",roomCards[8].getCardName());
        assertEquals("Recreation Room",roomCards[9].getCardName());
    }


    /*Create Player class with human and computer child classes.
    Use people data to instantiate 6 players (1 human and 5 computer)
     */

    /*
    When the board is loaded multiple times it adds the characters every time
    This is fine since we plan on only initiating the board once in actual code
    Just be wary of what happens when running all other test files at the same time
     */
    @Test
    public void loadPeople(){
        assertNotNull(board.getPlayerCharacter());
        Set<ComputerPlayer> nonPlayers=board.getNonPlayerCharacters();
        for(Player p : nonPlayers){
            assertNotNull(p);
        }
    }

    //initial setup has Luigi as the human character. This may change later
    @Test
    public void isLuigi(){
        assertEquals("Luigi",board.getPlayerCharacter().getName());
    }

    @Test
    public void fiveComputers(){
        Set<ComputerPlayer> nonPlayers=board.getNonPlayerCharacters();
        assertEquals(5,nonPlayers.size());
    }
    //Create complete deck of cards (weapons, people and rooms)

    @Test
    public void completeDeckMade(){
        Card[] deck = board.returnDeck();
        assertEquals("Luigi",deck[0].getCardName());
        assertEquals("Princess Peach",deck[1].getCardName());
        assertEquals("King Boo",deck[2].getCardName());
        assertEquals("Mario",deck[3].getCardName());
        assertEquals("Professor E. Gadd",deck[4].getCardName());
        assertEquals("Bowser",deck[5].getCardName());

        assertEquals("Mr. L",deck[6].getCardName());
        assertEquals("Poltergust 3000",deck[7].getCardName());
        assertEquals("Fire Flower", deck[8].getCardName());
        assertEquals("Poisoned Mushroom",deck[9].getCardName());
        assertEquals("Blue Shell",deck[10].getCardName());

        assertEquals("Conservatory",deck[11].getCardName());
        assertEquals("Kitchen",deck[12].getCardName());
        assertEquals("Projection Room",deck[13].getCardName());
        assertEquals("Dining Room",deck[14].getCardName());
        assertEquals("Laundry Room",deck[15].getCardName());
        assertEquals("Foyer",deck[16].getCardName());
        assertEquals("Graveyard",deck[17].getCardName());
        assertEquals("Bathroom",deck[18].getCardName());
        assertEquals("Ballroom",deck[19].getCardName());
        assertEquals("Recreation Room",deck[20].getCardName());
    }
    /*Deal cards to the Answer and the players
    (all cards dealt, players have roughly same # of cards, no card dealt twice)
     */
    @Test
    public void dealCards(){
        List<Player> players = new ArrayList<>();
        players.add(board.getPlayerCharacter());
        players.addAll(board.getNonPlayerCharacters());
        assertEquals(6, players.size());
        Set<Card> seen = new HashSet<>();
        int min = Integer.MAX_VALUE, max = 0;

        for (Player p : players){
            int size = p.getHand().size();
            min = Math.min(min, size);
            max = Math.max(max, size);
            for (Card c : p.getHand()){
                assertTrue(seen.add(c), "Duplicate card: " + c.getCardName());
            }
        }

        Solution sol = board.getSolution();
        assertNotNull(sol);
        assertEquals(CardType.PERSON, sol.getPerson().getType());
        assertEquals(CardType.WEAPON, sol.getWeapon().getType());
        assertEquals(CardType.ROOM, sol.getRoom().getType());
        assertTrue(seen.add(sol.getPerson()));
        assertTrue(seen.add(sol.getWeapon()));
        assertTrue(seen.add(sol.getRoom()));

        assertEquals(21, seen.size());
        assertTrue(max-min <= 1);
    }

}
