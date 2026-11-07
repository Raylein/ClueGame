import clueGame.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameSetupTests {
    private static Board board;
    @BeforeEach
    public void setUp() throws FileNotFoundException, BadConfigFormatException{
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


    /*Deal cards to the Answer and the players
    (all cards dealt, players have roughly same # of cards, no card dealt twice)
     */
}
