package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

public abstract class Player {
    private String name, color;
    private int row, column;
    protected Set<Card> hand= new HashSet<>();
    protected Set<Card> seen = new HashSet<>();
    protected BoardCell currentCell;

    public Player(String name, String color, int row, int column){
        this.name = name;
        this.color = color;
        this.row = row;
        this.column = column;
    }
    public void setCurrentCell(BoardCell newCell){
        currentCell=newCell;
    }
    public BoardCell getCurrentCell(){
        return currentCell;
    }
    public void updateHand(Card card){
        hand.add(card);
    }

    public void updateSeen(Card card){ seen.add(card); }

    public Set<Card> getSeen(){ return seen; }

    public void clearSeen(){seen.clear();}

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

    public Card disproveSuggestion(Solution suggestion){
        List<Card> matches = new ArrayList<>();
        for (Card c : hand){
            if (c.equals(suggestion.getPerson()) || c.equals(suggestion.getWeapon()) || c.equals(suggestion.getRoom())){
                matches.add(c);
            }
        }

        if (matches.isEmpty()){
            return null;
        }
        return matches.get(new Random().nextInt(matches.size()));
    }

}
