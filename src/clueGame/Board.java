package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final BoardCell[][] grid = new BoardCell[4][4];
    private final Set<BoardCell> targets = new HashSet<>();

    public Board() {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                grid[row][column] = new BoardCell(row, column);
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

    public void calcTargets(BoardCell startCell, int pathlength) {
        if (pathlength < 0) {
            throw new IllegalArgumentException("Path length cannot be negative");
        }
        targets.clear();
        findTargets(startCell, pathlength, new HashSet<>(Set.of(startCell)));
    }

    private void findTargets(BoardCell currentCell, int remainingSteps,
                             Set<BoardCell> visitedCells) {
        if (remainingSteps == 0) {
            targets.add(currentCell);
            return;
        }

        for (BoardCell adjacentCell : currentCell.getAdjList()) {
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

    public BoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public Set<BoardCell> getTargets() {
        return targets;
    }
}
