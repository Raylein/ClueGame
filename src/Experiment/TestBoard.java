package Experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
    private final TestBoardCell[][] grid = new TestBoardCell[4][4];
    private final Set<TestBoardCell> targets = new HashSet<>();

    public TestBoard() {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                grid[row][column] = new TestBoardCell(row, column);
            }
        }

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (row > 0) {
                    grid[row][column].addAdjacency(grid[row - 1][column]);
                }
                if (row < grid.length - 1) {
                    grid[row][column].addAdjacency(grid[row + 1][column]);
                }
                if (column > 0) {
                    grid[row][column].addAdjacency(grid[row][column - 1]);
                }
                if (column < grid[row].length - 1) {
                    grid[row][column].addAdjacency(grid[row][column + 1]);
                }
            }
        }
    }

    public void calcTargets(TestBoardCell startCell, int pathlength) {
        if (pathlength < 0) {
            throw new IllegalArgumentException("Path length cannot be negative");
        }
        targets.clear();
        findTargets(startCell, pathlength, new HashSet<>(Set.of(startCell)));
    }

    private void findTargets(TestBoardCell currentCell, int remainingSteps,
                             Set<TestBoardCell> visitedCells) {
        if (remainingSteps == 0) {
            targets.add(currentCell);
            return;
        }

        for (TestBoardCell adjacentCell : currentCell.getAdjList()) {
            if (adjacentCell.getOccupied() || visitedCells.contains(adjacentCell)) {
                continue;
            }

            if (adjacentCell.getRoom() || remainingSteps == 1) {
                targets.add(adjacentCell);
                continue;
            }

            visitedCells.add(adjacentCell);
            findTargets(adjacentCell, remainingSteps - 1, visitedCells);
            visitedCells.remove(adjacentCell);
        }
    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }
}
