package clueGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;

import clueGame.BadConfigFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

class BoardAdjTargetTest {
    private static Set<BoardCell> adj(Board board, int row, int col) {
        return board.getCell(row, col).getAdjList();
    }

    private static boolean isWalkway(BoardCell cell) {
        return cell.getInitial() == 'W';
    }

    private static boolean isDoorway(BoardCell cell) {
        return cell.getDoorDirection() != null && cell.getDoorDirection() != DoorDirection.NONE;
    }

    private Board board;

    //Setup for all the tests
    @BeforeEach
    void setUp() throws FileNotFoundException, BadConfigFormatException {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }


    @Test
    void roomCenterConnectsToDoors() {
        assertEquals(Set.of(board.getCell(4, 8)), adj(board, 4, 15));
        assertEquals(Set.of(board.getCell(15, 9), board.getCell(16, 9)), adj(board, 15, 16));
        assertEquals(Set.of(
                board.getCell(7, 13), board.getCell(7, 14),
                board.getCell(7, 15), board.getCell(9, 23)), adj(board, 10, 13));
    }

    @Test
    void roomWithNoDoorandNonCenterRoomCellsHavenoAdjacencies() {
        assertEquals(Set.of(), adj(board, 19, 1));
        assertEquals(Set.of(), adj(board, 14, 3));
        assertEquals(Set.of(), adj(board, 0, 0));
    }

    @Test
    void walkwayNextToNonDoorwayRoomCekkIgnores() {
        assertEquals(Set.of(board.getCell(4, 8), board.getCell(6, 8)), adj(board, 5, 8));
        assertEquals(Set.of(board.getCell(13, 9), board.getCell(15, 9)), adj(board, 14, 9));
        assertEquals(Set.of(board.getCell(18, 7), board.getCell(18, 9)), adj(board, 18, 8));
    }

    @Test
    void doorwayConnectsWalkwaysAndItsRoomCenter() {
        assertEquals(Set.of(
                board.getCell(3, 8), board.getCell(5, 8), board.getCell(4, 15)),
                adj(board, 4, 8));
        assertEquals(Set.of(
                board.getCell(7, 12), board.getCell(7, 14), board.getCell(10, 13)),
                adj(board, 7, 13));
        assertEquals(Set.of(
                board.getCell(17, 9), board.getCell(18, 8),
                board.getCell(18, 10), board.getCell(20, 9)), adj(board, 18, 9));
        assertEquals(Set.of(
                board.getCell(8, 4), board.getCell(10, 4), board.getCell(6, 1)),
                adj(board, 9, 4));
    }

    @Test
    void walkwayAdjecencyIsOnlyWalkways() {
        Set<BoardCell> adjacent = adj(board, 13, 10);
        assertEquals(Set.of(board.getCell(13, 9), board.getCell(13, 11)), adjacent);
        assertTrue(adjacent.stream().allMatch(BoardAdjTargetTest::isWalkway));
    }

    @Test
    void walkwayOnBorderHaveInBoundNeighboors() {
        assertEquals(Set.of(board.getCell(1, 4)), adj(board, 0, 4));
        assertEquals(Set.of(board.getCell(13, 1)), adj(board, 13, 0));
        assertEquals(Set.of(board.getCell(7, 32)), adj(board, 7, 33));
        assertEquals(Set.of(board.getCell(13, 22), board.getCell(12, 23), board.getCell(14, 23)),
                adj(board, 13, 23));
        assertEquals(Set.of(), adj(board, 21, 9));
        assertEquals(Set.of(), adj(board, 0, 0));
        assertEquals(Set.of(), adj(board, 0, 33));
        assertEquals(Set.of(), adj(board, 21, 0));
    }

    @Test
    void targetFromRoomCenter() {
        board.calcTargets(board.getCell(4, 15), 1);
        assertEquals(Set.of(board.getCell(4, 8)), board.getTargets());

        board.calcTargets(board.getCell(4, 15), 3);
        assertEquals(Set.of(board.getCell(2, 8), board.getCell(6, 8)), board.getTargets());

        board.calcTargets(board.getCell(10, 13), 3);
        assertEquals(Set.of(
                board.getCell(7, 11), board.getCell(7, 12), board.getCell(7, 13),
                board.getCell(7, 15), board.getCell(7, 16), board.getCell(7, 17),
                board.getCell(7, 23), board.getCell(11, 23)), board.getTargets());
    }
}
