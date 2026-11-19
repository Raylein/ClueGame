package clueGame;

public class Card {
    private String cardName;
    private CardType type;

    public Card(String cardName, CardType type){
        this.cardName = cardName;
        this.type = type;
    }

    @Override
    public boolean equals (Object o){
       if (this == o){
           return true;
       }
        if (!(o instanceof Card)) {
            return false;
        }
        Card c = (Card) o;
        return cardName.equals(c.cardName) && type == c.type;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cardName, type);
    }

    public String getCardName() {
        return cardName;
    }

    public CardType getType(){
        return type;
    }
}
