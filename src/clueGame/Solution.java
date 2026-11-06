package clueGame;

import java.util.Random;
import java.util.Set;

public class Solution {
    private final Card room;
    private final Card person;
    private final Card weapon;

    public Solution(Card[] peopleCards,Card[] roomCards,Card[] weaponCards){
        int peopleSize= peopleCards.length;
        int weaponSize = weaponCards.length;
        int roomSize = roomCards.length;

        int card = new Random().nextInt(peopleSize);
        person=peopleCards[card];
        card = new Random().nextInt(weaponSize);
        weapon=weaponCards[card];
        card=new Random().nextInt(roomSize);
        room=roomCards[card];
    }

}
