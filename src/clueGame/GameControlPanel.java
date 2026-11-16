package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class GameControlPanel extends JFrame {
    public GameControlPanel(){
        setTitle("Game Control");
        setSize(1000,450);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel panel1 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Whose turn?");
        JTextField textField1 = new JTextField("Luigi");
        textField1.setEditable(false);
        panel1.add(label1,BorderLayout.NORTH);
        panel1.add(textField1,BorderLayout.CENTER);

        JPanel panel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Roll:");
        JTextField textField2 = new JTextField("5");
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

        JTextField textField3 = new JTextField("I have no guess!");
        textField3.setEditable(false);

        bottomLeft.add(label3,BorderLayout.NORTH);
        bottomLeft.add(textField3,BorderLayout.CENTER);

        JPanel bottomRight = new JPanel(new BorderLayout());
        JLabel label4 = new JLabel("Guess Result");
        JTextField textField4 = new JTextField("So you have nothing?");
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

    public static void main(String[] args){
        new GameControlPanel();
    }
}