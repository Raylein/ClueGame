package clueGame;

import java.awt.*;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
    public ComputerPlayer(String name, String color, int row, int column) {
        super(name, color, row, column);
    }

    public Solution createSuggestion(Card player,Card room, Card weapon){
        return new Solution(player,room,weapon);
    }

    public BoardCell selectTarget(BoardCell[] targets, Card[] deck){
        for (Card card : deck) {
            if (card.getCardName().equals(currentCell.getRoom().getName())) {
                if (!seen.contains(card)) {
                    return currentCell;
                }
            }
        }
        int target = new Random().nextInt(targets.length);
        return targets[target];
    }

}
