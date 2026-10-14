package Experiment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTestsExp {
    private TestBoard testBoard;

    @BeforeEach
    void setUp() {
        testBoard = new TestBoard();
    }

    @Test
    void calculatesTargetsOnEmptyFourByFourBoard() {
        testBoard.calcTargets(testBoard.getCell(0, 0), 3);

        assertEquals(Set.of(
                testBoard.getCell(0, 1),
                testBoard.getCell(0, 3),
                testBoard.getCell(1, 0),
                testBoard.getCell(1, 2),
                testBoard.getCell(2, 1),
                testBoard.getCell(3, 0)
        ), testBoard.getTargets());
    }

    @Test
    void occupiedCellCannotBeEntered() {
        testBoard.getCell(0, 1).setOccupied(true);

        testBoard.calcTargets(testBoard.getCell(0, 0), 2);

        assertEquals(Set.of(
                testBoard.getCell(1, 1),
                testBoard.getCell(2, 0)
        ), testBoard.getTargets());
    }

    @Test
    void enteringRoomUsesAllRemainingMovementPoints() {
        testBoard.getCell(0, 1).setRoom(true);

        testBoard.calcTargets(testBoard.getCell(0, 0), 2);

        assertEquals(Set.of(
                testBoard.getCell(0, 1),
                testBoard.getCell(1, 1),
                testBoard.getCell(2, 0)
        ), testBoard.getTargets());
    }
}
