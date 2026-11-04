package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
    private final int row, column;
    private boolean roomLabel, roomCenter, occupied, isRoom;
    private final Set<BoardCell> adjList = new HashSet<>();
    private char initial, secretPassage;
    private DoorDirection doorDirection = DoorDirection.NONE;
    private Room room;

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

    public void setDoorDirection(DoorDirection direction) {
        this.doorDirection = direction;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    public void setInitial(char initial) {
        this.initial = initial;
    }

    public char getInitial() {
        return initial;
    }

    public void setRoomLabel(boolean roomLabel) {
        this.roomLabel = roomLabel;
    }

    public boolean isLabel() {
        return roomLabel;
    }

    public void setRoomCenter(boolean roomCenter) {
        this.roomCenter = roomCenter;
    }

    public boolean isRoomCenter() {
        return roomCenter;
    }

    public void setSecretPassage(char secretPassage) {
        this.secretPassage = secretPassage;
    }

    public char getSecretPassage() {
        return secretPassage;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setIsRoom(boolean room) {
        this.isRoom = room;
    }

    public boolean getIsRoom() {
        return isRoom;
    }

    public boolean isDoorway() {
        if (this.getDoorDirection() != DoorDirection.NONE) {
            return true;
        } else {
            return false;
        }
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return this.room;
    }

}