package clueGame;

import java.awt.Color;
import javax.swing.*;
import java.util.Set;
import java.awt.GridLayout;

public class ClueCardPanel extends JPanel{
    private JPanel peopleInHand, peopleSeen;
    private JPanel roomsInHand, roomsSeen;
    private JPanel weaponsInHand, weaponsSeen;

    public void KnownCardsPanel() {
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

    // Refreshes every area from the given player's hand and seen cards (null = show "None")
    public void updatePanels(Player player) {
        Set<Card> hand = (player == null) ? Set.of() : player.getHand();
        Set<Card> seen = (player == null) ? Set.of() : player.getSeen();

        fill(peopleInHand, hand, CardType.PERSON);
        fill(peopleSeen, seen, CardType.PERSON);
        fill(roomsInHand, hand, CardType.ROOM);
        fill(roomsSeen, seen, CardType.ROOM);
        fill(weaponsInHand, hand, CardType.WEAPON);
        fill(weaponsSeen, seen, CardType.WEAPON);

        revalidate();
        repaint();
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
        field.setBackground(Color.WHITE);
        return field;
    }
}
