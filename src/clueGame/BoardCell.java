package clueGame;
    
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
    private final int row;
    private final int column;
    private final Set<BoardCell> adjList = new HashSet<>();
    private char initial;
    private DoorDirection doorDirection;
    private boolean roomLabel;
    private boolean roomCenter;
    private char secretPassage;
    private boolean occupied;
    private boolean room;

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

    public void setDoorDirection(String direction){
        doorDirection = DoorDirection.valueOf(direction);
    }
    public DoorDirection getDoorDirection(){
        return doorDirection;
    }
    public void setInitial(char initial){
        this.initial=initial;
    }
    public char getInitial(){return initial;}
    
    public void setRoomLabel(boolean roomLabel){
        this.roomLabel = roomLabel;
    }
    public boolean isRoomLabel(){
        return roomLabel;
    }
    public void setRoomCenter(boolean roomCenter){
        this.roomCenter=roomCenter;
    }

    public boolean isRoomCenter() {
        return roomCenter;
    }
    
    public void setSecretPassage(char secretPassage){
        this.secretPassage=secretPassage;
    }
    public char getSecretPassage(){
        return secretPassage;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public boolean getOccupied() {return occupied;}
    public void setRoom(boolean room) {
        this.room = room;
    }

    public boolean getRoom() {
        return room;
    }
}