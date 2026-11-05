package clueGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class FileInitTests {
    final static int NUM_ROWS = 22;
    final static int NUM_COLUMNS = 34;

    private static Board board;

    @BeforeAll
    public static void setUp() throws FileNotFoundException, BadConfigFormatException {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    @Test
    public void testRoomLabels() {
        assertEquals("Conservatory", board.getRoom('C').getName());
        assertEquals("Kitchen", board.getRoom('K').getName());
        assertEquals("Projection Room", board.getRoom('P').getName());
        assertEquals("Dining Room", board.getRoom('D').getName());
        assertEquals("Laundry Room", board.getRoom('L').getName());
        assertEquals("Foyer", board.getRoom('F').getName());
        assertEquals("Graveyard", board.getRoom('G').getName());
        assertEquals("Bathroom", board.getRoom('B').getName());
        assertEquals("Ballroom", board.getRoom('A').getName());
        assertEquals("Recreation Room", board.getRoom('R').getName());
        assertEquals("Walkway", board.getRoom('W').getName());
    }

    @Test
    public void testBoardDimensions() {
        assertEquals(NUM_ROWS, board.getNumRows());
        assertEquals(NUM_COLUMNS, board.getNumColumns());
    }

    @Test
    public void FourDoorDirections() {
        BoardCell cell = board.getCell(9, 4);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
        cell = board.getCell(7, 6);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.UP, cell.getDoorDirection());
        cell = board.getCell(4, 8);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
        cell = board.getCell(7, 14);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.DOWN, cell.getDoorDirection());

        cell = board.getCell(7, 9);
        assertFalse(cell.isDoorway());
    }

    @Test
    public void testNumberOfDoorways() {
        int numDoors = 0;
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumColumns(); col++) {
                BoardCell cell = board.getCell(row, col);
                if (cell.isDoorway())
                    numDoors++;
            }
        assertEquals(18, numDoors);
    }

    @Test
    public void testRooms() {
        BoardCell cell = board.getCell(15, 2);
        Room room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Kitchen");
        assertFalse(cell.isLabel());
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isDoorway());

        cell = board.getCell(19, 9);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Foyer");
        assertTrue(cell.isLabel());
        assertTrue(room.getLabelCell() == cell);

        cell = board.getCell(20, 9);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Foyer");
        assertTrue(cell.isRoomCenter());
        assertTrue(room.getCenterCell() == cell);

        cell = board.getCell(7, 9);
        room = board.getRoom(cell);
        // Note for our purposes, walkways and closets are rooms
        assertTrue(room != null);
        assertEquals(room.getName(), "Walkway");
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isLabel());

        cell = board.getCell(21, 6);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Unused");
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isLabel());

    }
}
