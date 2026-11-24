package clueGame;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Player {
    private String name, color;
    private int row, column;
    protected Set<Card> hand= new HashSet<>();
    protected Set<Card> seen = new HashSet<>();
    protected BoardCell currentCell;
    private Map<Card, Player> seenBy = new HashMap<>();

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

    public void setLocation(int row, int column) {
        this.row = row;
        this.column = column;

        Board board = Board.getInstance();
        currentCell = board.getCell(row, column);

        board.repaint();
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

    public void updateSeen(Card card, Player player) {
        seen.add(card);
        seenBy.put(card, player);
    }

    public Map<Card, Player> getSeenBy() {
        return seenBy;
    }

    public void draw(Graphics g, int cellWidth, int cellHeight) {
        if (currentCell == null) {
            return;
        }

        int x = currentCell.getColumn() * cellWidth;
        int y = currentCell.getRow() * cellHeight;

        g.setColor(getPlayerColor());
        g.fillOval(x + 2, y + 2, cellWidth - 4, cellHeight - 4);
    }

    private Color getPlayerColor() {
        switch (color.toLowerCase()) {
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "purple":
                return new Color(128, 0, 128);
            case "yellow":
                return Color.YELLOW;
            case "white":
                return Color.LIGHT_GRAY;
            case "pink":
                return Color.PINK;
            default:
                return Color.WHITE;
        }
    }

}
