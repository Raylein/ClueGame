package clueGame;

import java.util.Arrays;
import java.util.Random;

public class ComputerPlayer extends Player{
    public ComputerPlayer(String name, String color, int row, int column) {
        super(name, color, row, column);
    }

    public Solution createSuggestion(Card player,Card room, Card weapon){
        return new Solution(player,room,weapon);
    }

    public BoardCell selectTarget(BoardCell[] targets, Card[] roomDeck){
        int i=0;
       for(BoardCell target:targets){
           if((target.getInitial()!='W') && (target.getInitial() !='X')){
               break;
           }
           if(i>=targets.length-1){
               int t = new Random().nextInt(targets.length);
               return targets[t];
           }
           i+=1;
       }

        for (Card card : roomDeck) {
            if (card.getCardName().equals(currentCell.getRoom().getName())) {
                if (seen.contains(card)) {
                    BoardCell[] newTargets = new BoardCell[targets.length+1];
                    for(int j=0; j<targets.length;j++){
                        newTargets[j] = targets[j];
                    }
                    newTargets[targets.length]=currentCell;
                    int target = new Random().nextInt(newTargets.length);
                    return newTargets[target];
                }
            }
        }
        return currentCell;
    }

}
