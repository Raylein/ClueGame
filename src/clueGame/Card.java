package clueGame;

public class Card {
    private String cardName;
    private Cardtype type;

    public Card(String cardName, Cardtype type){
        this.cardName = cardName;
        this.type = type;
    }

    public boolean equals (Card target){
        return target != null
                && cardName.equals(target.cardName)
                && type == target.type;
    }

    public String getCardName() {
        return cardName;
    }

    public Cardtype getType(){
        return type;
    }
}
