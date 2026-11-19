package clueGame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

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
        List<BoardCell> unseenRooms = new ArrayList<>();
        for (BoardCell t : targets) {
            if (!t.getIsRoom()) continue;
            for (Card card : roomDeck) {
                if (card.getCardName().equals(t.getRoom().getName()) && !seen.contains(card)) {
                    unseenRooms.add(t);
                    break;
                }
            }
        }
        List<BoardCell> pool = unseenRooms.isEmpty() ? Arrays.asList(targets) : unseenRooms;
        return pool.get(new Random().nextInt(pool.size()));
    }
}
