package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
    private String name;
    private String color;
    private int row, column;
    private Set<Card> hand = new HashSet<>();

    public Player(String name, String color, int row, int column){
        this.name = name;
        this.color = color;
        this.row = row;
        this.column = column;
    }
    public void updateHand(Card card){
        hand.add(card);
    }

    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public Set<Card> getHand(){
        return hand;
    }

    public void setLocation(int row, int column){
        this.row = row;
        this.column = column;
    }
}
