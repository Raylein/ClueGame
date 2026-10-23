package clueGame;
    
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
    private final int row;
    private final int column;
    private final Set<BoardCell> adjList = new HashSet<>();
    private boolean room;
    private boolean occupied;

    public BoardCell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void addAdjacency(BoardCell cell) {
        adjList.add(cell);
    }

    public Set<BoardCell> getAdjList() {
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