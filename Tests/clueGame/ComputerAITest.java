package clueGame;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;


public class ComputerAITest {
    private static Board board;
    private static ComputerPlayer[] computers;
    private static BoardCell[] targets;
    @BeforeAll
    public static void setUp() throws Exception {
        board = Board.getInstance();
        // Adjust file names/paths to match your project
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        computers = board.getNonPlayerCharacters().toArray(new ComputerPlayer[0]);
        targets = new BoardCell[3];
    }

    @Test
    public void noRooms(){
        targets[0] = board.getCell(4,4);
        targets[1]=board.getCell(7,8);
        targets[2]=board.getCell(13,4);

        computers[0].updateSeen(board.getPeopleCards()[0]);
        computers[0].updateSeen(board.getPeopleCards()[1]);
        computers[0].updateSeen(board.getPeopleCards()[2]);
        computers[0].updateSeen(board.getWeaponCards()[0]);
        computers[0].updateSeen(board.getWeaponCards()[1]);
        computers[0].updateSeen(board.getWeaponCards()[2]);
        BoardCell target = computers[0].selectTarget(targets,board.getRoomCards());

        assertTrue(Arrays.asList(targets).contains(target));
    }

    @Test
    public void currentRoomNotSeen(){
        targets[0] =board.getCell(4,11);
        targets[1]=board.getCell(8,6);
        targets[2]=board.getCell(13,27);
        computers[0].updateSeen(board.getRoomCards()[0]);
        computers[0].updateSeen(board.getRoomCards()[1]);

        assertEquals(computers[0].getCurrentCell(), computers[0].selectTarget(targets, board.getRoomCards()));
    }

    @Test
    public void currentRoomSeen(){
        targets[0] =board.getCell(4,11);
        targets[1]=board.getCell(8,6);
        targets[2]=board.getCell(13,27);
        computers[0].updateSeen((board.getRoomCards()[6]));

        BoardCell target = computers[0].selectTarget(targets,board.getRoomCards());
        BoardCell[] newTargets = new BoardCell[4];
        newTargets[0]=targets[0];
        newTargets[1]=targets[1];
        newTargets[2]=targets[2];
        newTargets[3]=computers[0].getCurrentCell();
        assertTrue(Arrays.asList(newTargets).contains(target));
    }

    @Test
    public void roomMatchesLocation(){
        computers[0].updateSeen(board.getPeopleCards()[0]);
        computers[0].updateSeen(board.getPeopleCards()[1]);
        computers[0].updateSeen(board.getPeopleCards()[2]);
        computers[0].updateSeen(board.getWeaponCards()[0]);
        computers[0].updateSeen(board.getWeaponCards()[1]);
        computers[0].updateSeen(board.getWeaponCards()[2]);
        computers[0].updateSeen(board.getRoomCards()[0]);
        computers[0].updateSeen(board.getRoomCards()[1]);

        Solution suggestion = computers[0].createSuggestion(board.getPeopleCards(),board.getRoomCards(),board.getWeaponCards());

        //All initialized players at the very start are set up to start in Graveyard. This may change later so the test
        //may change later as a result
        assertEquals(suggestion.getRoom().getCardName(), board.getCell(0, 0).getRoom().getName());
    }

    @Test
    public void onlyOneWeaponAndPerson(){
        for(int k=0;k<50;k++) {
            for (int i = 0; i < board.getWeaponCards().length - 1; i++) {
                computers[0].updateSeen(board.getWeaponCards()[i]);
            }
            for (int i = 0; i < board.getPeopleCards().length - 1; i++) {
                computers[0].updateSeen(board.getPeopleCards()[i]);
            }

            Solution suggestion = computers[0].createSuggestion(board.getPeopleCards(), board.getRoomCards(), board.getWeaponCards());

            assertEquals(suggestion.getWeapon().getCardName(), board.getWeaponCards()[board.getWeaponCards().length - 1].getCardName());

            assertEquals(suggestion.getPerson().getCardName(), board.getPeopleCards()[board.getPeopleCards().length - 1].getCardName());
        }
    }

    @Test
    public void ManyWeapon(){
        for(int k=0;k<50;k++) {
            for (int i = 0; i < board.getWeaponCards().length - 3; i++) {
                computers[0].updateSeen(board.getWeaponCards()[i]);
            }
            for (int i = 0; i < board.getPeopleCards().length - 1; i++) {
                computers[0].updateSeen(board.getPeopleCards()[i]);
            }

            Solution suggestion = computers[0].createSuggestion(board.getPeopleCards(), board.getRoomCards(), board.getWeaponCards());

            Set<Card> unseenWeapons = new HashSet<>();
            unseenWeapons.add(board.getWeaponCards()[board.getWeaponCards().length - 3]);
            unseenWeapons.add(board.getWeaponCards()[board.getWeaponCards().length - 2]);
            unseenWeapons.add(board.getWeaponCards()[board.getWeaponCards().length - 1]);

            assertTrue(unseenWeapons.contains(suggestion.getWeapon()));
        }
    }

    @Test
    public void ManyPeople() {
        for(int k=0;k<50;k++) {
            for (int i = 0; i < board.getWeaponCards().length - 1; i++) {
                computers[0].updateSeen(board.getWeaponCards()[i]);
            }
            for (int i = 0; i < board.getPeopleCards().length - 3; i++) {
                computers[0].updateSeen(board.getPeopleCards()[i]);
            }

            Solution suggestion = computers[0].createSuggestion(board.getPeopleCards(), board.getRoomCards(), board.getWeaponCards());

            Set<Card> unseenPeople = new HashSet<>();
            unseenPeople.add(board.getPeopleCards()[board.getPeopleCards().length - 3]);
            unseenPeople.add(board.getPeopleCards()[board.getPeopleCards().length - 2]);
            unseenPeople.add(board.getPeopleCards()[board.getPeopleCards().length - 1]);

            assertTrue(unseenPeople.contains(suggestion.getPerson()));
        }
    }
}
