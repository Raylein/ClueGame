package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;

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

    @BeforeEach
    void setUp() throws FileNotFoundException {
        board = new Board();
        board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
        board.initialize();
    }

    @Test
    void roomCenterConnectsToDoorsAndPassageButOtherRoomCellsDoNot() {
        assertEquals(Set.of(board.getCell(4, 6), board.getCell(20, 19)),
                adj(board, 2, 2));
        assertEquals(Set.of(), adj(board, 1, 1));
    }

    @Test
    void walkwayAdjacencyContainsOnlyWalkways() {
        Set<BoardCell> adjacent = adj(board, 19, 6);

        assertEquals(Set.of(
                board.getCell(19, 5),
                board.getCell(19, 7),
                board.getCell(18, 6),
                board.getCell(20, 6)), adjacent);
        assertTrue(adjacent.stream().allMatch(BoardAdjTargetTest::isWalkway));
    }

    @Test
    void edgeAndNonDoorwayRoomNeighborsAreHandled() {
        assertEquals(Set.of(board.getCell(23, 14)), adj(board, 24, 14));
        assertEquals(Set.of(
                board.getCell(18, 3),
                board.getCell(17, 4),
                board.getCell(18, 5)), adj(board, 18, 4));
    }

    @Test
    void walkwayLocationsOnEveryBoardEdgeHaveOnlyInBoundsNeighbors() {
        assertEquals(Set.of(board.getCell(1, 7)), adj(board, 0, 7));
        assertEquals(Set.of(board.getCell(23, 14)), adj(board, 24, 14));
        assertEquals(Set.of(board.getCell(5, 1)), adj(board, 5, 0));
        assertEquals(Set.of(board.getCell(7, 22)), adj(board, 7, 23));
    }

    @Test
    void doorwayConnectsWalkwaysAndItsRoomCenter() {
        assertEquals(Set.of(
                board.getCell(12, 20),
                board.getCell(7, 17),
                board.getCell(8, 18),
                board.getCell(8, 16)), adj(board, 8, 17));
    }

    @Test
    void walkwayTargetsRespectRollDistanceAndRoomEntry() {
        board.calcTargets(board.getCell(11, 2), 1);
        assertEquals(Set.of(board.getCell(11, 1), board.getCell(11, 3)),
                board.getTargets());

        board.calcTargets(board.getCell(11, 2), 3);
        assertEquals(3, board.getTargets().size());
        assertTrue(board.getTargets().contains(board.getCell(14, 2)));

        board.calcTargets(board.getCell(8, 17), 1);
        assertTrue(board.getTargets().contains(board.getCell(12, 20)));
    }

    @Test
    void targetsCanLeaveRoomsWithOrWithoutSecretPassages() {
        board.calcTargets(board.getCell(12, 20), 1);
        assertEquals(Set.of(board.getCell(8, 17), board.getCell(12, 15)),
                board.getTargets());

        board.calcTargets(board.getCell(20, 19), 1);
        assertEquals(Set.of(board.getCell(17, 18), board.getCell(2, 2)),
                board.getTargets());
    }

    @Test
    void occupiedWalkwaysBlockTargetsButOccupiedRoomCentersRemainReachable() {
        board.getCell(15, 7).setOccupied(true);
        board.calcTargets(board.getCell(13, 7), 4);
        assertEquals(13, board.getTargets().size());
        assertFalse(board.getTargets().contains(board.getCell(15, 7)));

        board.getCell(12, 20).setOccupied(true);
        board.calcTargets(board.getCell(8, 17), 1);
        assertTrue(board.getTargets().contains(board.getCell(12, 20)));
    }
}
