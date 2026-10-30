package clueGame;

public class Room {
    private final String name;
    private BoardCell centerCell;
    private BoardCell labelCell;

    public Room(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
    public BoardCell getCenterCell(){
        return this.centerCell;
    }
    public void setCenterCell(BoardCell centerCell){
        this.centerCell=centerCell;
    }
    public BoardCell getLabelCell(){
        return this.labelCell;
    }
    public void setLabelCell(BoardCell labelCell){
        this.labelCell=labelCell;
    }
}
