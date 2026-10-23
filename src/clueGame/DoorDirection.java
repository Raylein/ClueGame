package clueGame;

public enum DoorDirection {
    UP("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGHT("Right"),
    NONE("None");

    private String label;
    private DoorDirection(String label){
        this.label=label;
    }
    public String getLabel(){
        return label;
    }
}
