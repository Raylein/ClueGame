package Experiment;
    
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
    private final int row;
    private final int column;
    private final Set<TestBoardCell> adjList = new HashSet<>();
    private boolean room;
    private boolean occupied;

    public TestBoardCell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void addAdjacency(TestBoardCell cell) {
        adjList.add(cell);
    }

    public Set<TestBoardCell> getAdjList() {
        return adjList;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setRoom(boolean room) {
        this.room = room;
    }

    public boolean getRoom() {
        return room;
    }
}