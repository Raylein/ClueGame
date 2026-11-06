package clueGame;

public enum Cardtype {
    ROOM("Room"),
    PERSON("Person"),
    WEAPON("Weapon");

    private String label;

    private Cardtype(String label){
        this.label=label;
    }
    public String getLabel(){
        return label;
    }
}
