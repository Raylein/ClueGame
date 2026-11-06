import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    public void loadRoomCards(){
        Card[] roomCards = board.getRoomCards();
    }


    /*Create Player class with human and computer child classes.
    Use people data to instantiate 6 players (1 human and 5 computer)
     */


    //Create complete deck of cards (weapons, people and rooms)


    /*Deal cards to the Answer and the players
    (all cards dealt, players have roughly same # of cards, no card dealt twice)
     */
}
