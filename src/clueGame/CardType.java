package clueGame;

public enum CardType {
    ROOM("Room"),
    PERSON("Person"),
    WEAPON("Weapon");

    private String label;

    private CardType(String label){
        this.label=label;
    }
    public String getLabel(){
        return label;
    }
}
