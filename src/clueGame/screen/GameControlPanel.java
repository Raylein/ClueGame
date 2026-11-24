package clueGame.screen;

import clueGame.ComputerPlayer;
import clueGame.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class GameControlPanel extends JPanel {
    private JTextField textField1, textField2, textField3, textField4;

    public GameControlPanel(){
        setLayout(new BorderLayout());
        setSize(1000,450);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel panel1 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Whose turn?");
        textField1 = new JTextField("Luigi");
        textField1.setEditable(false);
        panel1.add(label1,BorderLayout.NORTH);
        panel1.add(textField1,BorderLayout.CENTER);

        JPanel panel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Roll:");
        textField2 = new JTextField("5");
        textField2.setEditable(false);
        JPanel labelTextPanel = new JPanel(new BorderLayout());
        labelTextPanel.add(label2, BorderLayout.WEST);
        labelTextPanel.add(textField2, BorderLayout.CENTER);
        panel2.add(labelTextPanel,BorderLayout.NORTH);

        JButton button1 = new JButton("Make Accusation");
        JButton button2 = new JButton("NEXT!");

        JPanel topContents = new JPanel(new BorderLayout());
        JPanel leftContents = new JPanel(new BorderLayout());
        leftContents.add(panel1,BorderLayout.WEST);
        leftContents.add(panel2,BorderLayout.CENTER);
        topContents.add(leftContents,BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());

        buttonPanel.add(button1, BorderLayout.WEST);
        buttonPanel.add(button2, BorderLayout.CENTER);

        topContents.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(topContents,BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel bottomLeft = new JPanel(new BorderLayout());
        JLabel label3 = new JLabel("Guess");

        textField3 = new JTextField("I have no guess!");
        textField3.setEditable(false);

        bottomLeft.add(label3,BorderLayout.NORTH);
        bottomLeft.add(textField3,BorderLayout.CENTER);

        JPanel bottomRight = new JPanel(new BorderLayout());
        JLabel label4 = new JLabel("Guess Result");
        textField4 = new JTextField("So you have nothing?");
        textField4.setEditable(false);

        bottomRight.add(label4,BorderLayout.NORTH);
        bottomRight.add(textField4,BorderLayout.CENTER);

        bottomPanel.add(bottomLeft,BorderLayout.WEST);
        bottomPanel.add(bottomRight,BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel,BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    private static Color toColor(String name){
        if (name == null) return Color.LIGHT_GRAY;
        switch (name.trim().toLowerCase()) {
            case "red":     return Color.RED;
            case "yellow":  return Color.YELLOW;
            case "white":   return Color.WHITE;
            case "green":   return Color.GREEN;
            case "blue":    return Color.BLUE;
            case "purple":  return new Color(128, 0, 128);   // not a java.awt.Color constant
            case "orange":  return Color.ORANGE;
            case "pink":    return Color.PINK;
            case "black":   return Color.BLACK;
            case "gray":
            case "grey":    return Color.GRAY;
            case "cyan":    return Color.CYAN;
            case "magenta": return Color.MAGENTA;
            default:
                try {
                    return Color.decode(name.trim());        // allows "#800080" style values
                } catch (NumberFormatException e) {
                    return Color.LIGHT_GRAY;
                }
        }
    }

    public void setTurn(Player player){
        textField1.setText(player.getName());
        Color bg = toColor(player.getColor());
        textField1.setBackground(bg);
        double brightness = 0.299*bg.getRed() + 0.587*bg.getGreen() + 0.114*bg.getBlue();
        textField1.setForeground(brightness < 128 ? Color.WHITE : Color.BLACK);

    }

    public void setRoll(int roll){
        textField2.setText(String.valueOf(roll));
    }

    public void setGuess(String guess){
        textField3.setText(guess);
    }

    public void setGuessResult(String result){
        textField4.setText(result);
    }

    public static void main(String[] args){

        GameControlPanel panel = new GameControlPanel();
        panel.setTurn(new ComputerPlayer("Mario", "Red", 0,0));
        panel.setRoll(3);
        panel.setGuess("I have no guess!");
        panel.setGuessResult("So you have nothing?");


        JFrame frame = new JFrame("Game Control");
        frame.add(panel);
        frame.setSize(1000, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}