
package clueGame;

import java.awt.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class ClueCardPanel extends JPanel {
    private JPanel peopleInHand, peopleSeen;
    private JPanel roomsInHand, roomsSeen;
    private JPanel weaponsInHand, weaponsSeen;

    public ClueCardPanel() {
        setLayout(new GridLayout(3, 1));
        setBorder(BorderFactory.createTitledBorder("Known Cards"));

        peopleInHand = new JPanel();
        peopleSeen = new JPanel();
        roomsInHand = new JPanel();
        roomsSeen = new JPanel();
        weaponsInHand = new JPanel();
        weaponsSeen = new JPanel();

        add(buildSection("People", peopleInHand, peopleSeen));
        add(buildSection("Rooms", roomsInHand, roomsSeen));
        add(buildSection("Weapons", weaponsInHand, weaponsSeen));

        // start out empty
        updatePanels(null);
    }

    // Builds one titled section containing "In Hand:" and "Seen:" areas
    private JPanel buildSection(String title, JPanel inHand, JPanel seen) {
        JPanel section = new JPanel(new GridLayout(0, 1));
        section.setBorder(BorderFactory.createTitledBorder(title));

        inHand.setLayout(new GridLayout(0, 1));
        seen.setLayout(new GridLayout(0, 1));

        section.add(new JLabel("In Hand:"));
        section.add(inHand);
        section.add(new JLabel("Seen:"));
        section.add(seen);
        return section;
    }

    // Refreshes every area from the given player's hand and seen cards
    public void updatePanels(Player player) {
        Set<Card> hand = (player == null) ? Set.of() : player.getHand();

        fill(peopleInHand, hand, CardType.PERSON);
        fill(roomsInHand, hand, CardType.ROOM);
        fill(weaponsInHand, hand, CardType.WEAPON);

        fillSeen(peopleSeen, player, CardType.PERSON);
        fillSeen(roomsSeen, player, CardType.ROOM);
        fillSeen(weaponsSeen, player, CardType.WEAPON);

        revalidate();
        repaint();
    }

    private void fillSeen(JPanel panel, Player player, CardType type) {
        panel.removeAll();
        boolean any = false;

        if (player != null) {
            for (Map.Entry<Card, Player> entry : player.getSeenBy().entrySet()) {
                Card card = entry.getKey();
                Player owner = entry.getValue();

                if (card.getType() == type) {
                    JTextField field = makeField(card.getCardName());
                    field.setBackground(getPlayerColor(owner.getColor()));

                    panel.add(field);
                    any = true;
                }
            }
        }

        if (!any) {
            panel.add(makeField("None"));
        }
    }

    private Color getPlayerColor(String color) {
        if (color == null) {
            return Color.WHITE;
        }

        switch (color.toLowerCase()) {
            case "green":
                return Color.GREEN;
            case "pink":
                return Color.PINK;
            case "purple":
                return Color.MAGENTA;
            case "red":
                return Color.RED;
            case "white":
                return Color.LIGHT_GRAY;
            case "yellow":
                return Color.YELLOW;
            default:
                return Color.WHITE;
        }
    }

    // Replaces the contents of one area with a text field per matching card, or "None"
    private void fill(JPanel panel, Set<Card> cards, CardType type) {
        panel.removeAll();
        boolean any = false;

        for (Card c : cards) {
            if (c.getType() == type) {
                panel.add(makeField(c.getCardName()));
                any = true;
            }
        }

        if (!any) {
            panel.add(makeField("None"));
        }
    }

    private JTextField makeField(String text) {
        JTextField field = new JTextField(text);
        field.setEditable(false);
        return field;
    }

    // Just a test run to see how the field looks and works.
    static void main() throws FileNotFoundException, BadConfigFormatException {
        Board board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();

        HumanPlayer player = board.getPlayerCharacter();
        ComputerPlayer[] computers =
                board.getNonPlayerCharacters().toArray(new ComputerPlayer[0]);

        player.updateSeen(board.getPeopleCards()[0], computers[0]);

        JFrame frame = new JFrame("Known Cards");
        ClueCardPanel clueCardPanel = new ClueCardPanel();

        clueCardPanel.updatePanels(player);

        JButton addCard = new JButton("Show Next Card");

        addCard.addActionListener(e -> {
            player.updateSeen(board.getPeopleCards()[1], computers[2]);
            clueCardPanel.updatePanels(player);
        });

        frame.setLayout(new BorderLayout());
        frame.add(clueCardPanel, BorderLayout.CENTER);
        frame.add(addCard, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
