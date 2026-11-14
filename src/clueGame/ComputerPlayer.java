package clueGame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
    public ComputerPlayer(String name, String color, int row, int column) {
        super(name, color, row, column);
    }

    public Solution createSuggestion(Card[] players,Card[] rooms, Card weapons[]){
        Room thisRoom = currentCell.getRoom();
        Card room = null;
        for(Card card : rooms){
            if(thisRoom.getName().equals(card.getCardName())){
                room = card;
            }
        }
        Set<Card> play = new HashSet<>();
        for(Card card : players){
            if(!seen.contains(card)){
                play.add(card);
            }
        }

        Card[] Play = play.toArray(new Card[0]);
        Card player = Play[new Random().nextInt(Play.length)];

        Set<Card> weap = new HashSet<>();
        for(Card card : weapons){
            if(!seen.contains(card)){
                weap.add(card);
            }
        }

        Card[] Weap = weap.toArray(new Card[0]);
        Card weapon = Weap[new Random().nextInt(Weap.length)];

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
