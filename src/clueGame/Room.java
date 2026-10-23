package clueGame;

public class Room {
    private final String name;
    private final BoardCell centerCell;
    private final BoardCell labelCell;

    public Room(String name,BoardCell centerCell,BoardCell labelCell){
        this.name=name;
        this.centerCell=centerCell;
        this.labelCell=labelCell;
    }

    public String getName(){
        return this.name;
    }
    public BoardCell getCenterCell(){
        return this.centerCell;
    }
    public BoardCell getLabelCell(){
        return this.labelCell;
    }
}
