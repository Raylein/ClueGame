package clueGame;

import java.util.HashSet;
import java.util.Set;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class BoardCell {
    private final int row, column;
    private boolean roomLabel, roomCenter, occupied, isRoom, highlighted;
    private final Set<BoardCell> adjList = new HashSet<>();
    private char initial, secretPassage;
    private DoorDirection doorDirection = DoorDirection.NONE;
    private Room room;

    //Sorts the cells by rows and columns
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

    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {

        if (isRoom) {
            g.setColor(getRoomColor());
            g.fillRect(x, y, width, height);
        }

        else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }

        if (isDoorway()) {
            g.setColor(Color.RED);

            switch (doorDirection) {
                case UP:
                    g.drawLine(x + width / 2, y,
                            x + width / 2, y + height / 3);
                    break;

                case DOWN:
                    g.drawLine(x + width / 2, y + 2 * height / 3,
                            x + width / 2, y + height);
                    break;

                case LEFT:
                    g.drawLine(x, y + height / 2,
                            x + width / 3, y + height / 2);
                    break;

                case RIGHT:
                    g.drawLine(x + 2 * width / 3, y + height / 2,
                            x + width, y + height / 2);
                    break;

                default:
                    break;
            }
        }

        if (highlighted) {
            g.setColor(new Color(255, 255, 0, 130));
            g.fillRect(x, y, width, height);
        }
    }

    private Color getRoomColor() {
        switch (initial) {
            case 'C':
                return new Color(43, 59, 65);   // Conservatory - Dark slate blue

            case 'K':
                return new Color(75, 35, 42);   // Kitchen - Blood burgundy

            case 'P':
                return new Color(62, 45, 75);   // Projection Room - Dark violet

            case 'D':
                return new Color(91, 65, 48);   // Dining Room - Dark walnut

            case 'L':
                return new Color(48, 68, 55);   // Laundry Room - Forest green

            case 'F':
                return new Color(83, 76, 57);   // Foyer - Antique bronze

            case 'G':
                return new Color(55, 55, 59);   // Graveyard - Charcoal

            case 'B':
                return new Color(53, 65, 77);   // Bathroom - Storm blue

            case 'A':
                return new Color(91, 43, 43);   // Ballroom - Dark crimson

            case 'R':
                return new Color(74, 67, 54);   // Recreation Room - Muted olive

            default:
                return Color.DARK_GRAY;
        }
    }

}