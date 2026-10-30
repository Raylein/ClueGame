package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
        board.initialize();
    }

    @Test
    void roomCenterConnectsToDoorsAndPassageButOtherRoomCellsDoNot() {
        assertEquals(Set.of(board.getCell(4, 6), board.getCell(20, 19)),
                board.getAdjList(2, 2));
        assertEquals(Set.of(), board.getAdjList(1, 1));
    }

    @Test
    void walkwayAdjacencyContainsOnlyWalkways() {
        Set<BoardCell> adjacent = board.getAdjList(19, 6);

        assertEquals(Set.of(
                board.getCell(19, 5),
                board.getCell(19, 7),
                board.getCell(18, 6),
                board.getCell(20, 6)), adjacent);
        assertTrue(adjacent.stream().allMatch(BoardCell::isWalkway));
    }

    @Test
    void edgeAndNonDoorwayRoomNeighborsAreHandled() {
        assertEquals(Set.of(board.getCell(23, 14)), board.getAdjList(24, 14));
        assertEquals(Set.of(
                board.getCell(18, 3),
                board.getCell(17, 4),
                board.getCell(18, 5)), board.getAdjList(18, 4));
    }

    @Test
    void walkwayLocationsOnEveryBoardEdgeHaveOnlyInBoundsNeighbors() {
        assertEquals(Set.of(board.getCell(1, 7)), board.getAdjList(0, 7));
        assertEquals(Set.of(board.getCell(23, 14)), board.getAdjList(24, 14));
        assertEquals(Set.of(board.getCell(5, 1)), board.getAdjList(5, 0));
        assertEquals(Set.of(board.getCell(7, 22)), board.getAdjList(7, 23));
    }

    @Test
    void doorwayConnectsWalkwaysAndItsRoomCenter() {
        assertEquals(Set.of(
                board.getCell(12, 20),
                board.getCell(7, 17),
                board.getCell(8, 18),
                board.getCell(8, 16)), board.getAdjList(8, 17));
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
