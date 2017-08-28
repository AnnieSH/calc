package Calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class calculator{
    // numbers
    private static JButton one   = new JButton("1");
    private static JButton two   = new JButton("2");
    private static JButton three = new JButton("3");
    private static JButton four  = new JButton("4");
    private static JButton five  = new JButton("5");
    private static JButton six   = new JButton("6");
    private static JButton seven = new JButton("7");
    private static JButton eight = new JButton("8");
    private static JButton nine  = new JButton("9");
    private static JButton zero  = new JButton("0");
    
    // functions
    private static JButton clear    = new JButton("C");
    private static JButton plus     = new JButton("+");
    private static JButton minus    = new JButton("-");
    private static JButton multiply = new JButton("*");
    private static JButton divide   = new JButton("/");
    private static JButton equals   = new JButton("=");
    // TODO: add back button
    
    // The expression to be evaluated and shown
    private static StringBuilder displayString = new StringBuilder();
    // The text display for the expression and result
    private static JTextField numField = new JTextField(12);
    
    // Order of buttons to be shown
    private static final JButton[] buttonOrder = {
            seven, eight, nine, divide,
            four, five, six, multiply,
            one, two, three, minus,
            clear, zero, equals, plus
            };
    
    /**
     * Constructor that sets up the GUI
     */
    public calculator() {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttons = new JPanel(new GridLayout(4, 4));
        
        JPanel display = new JPanel();
        //numField.setEnabled(false);
        display.add(numField);
        numField.setHorizontalAlignment(JTextField.RIGHT);
        numField.setFont(new Font("Roboto", Font.PLAIN, 24));
        
        buttonSetup();

        // Adds each button into the panel
        for(JButton i:buttonOrder)
            buttons.add(i);
        
        // Adds each component into the frame
        frame.add(buttons, BorderLayout.SOUTH);
        frame.add(display, BorderLayout.NORTH);
        frame.pack();
        
        frame.setSize(300, 180);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new calculator();
    }
    
    /**
     * Provides function to buttons
     */
    private static void buttonSetup() {
        
        // For all buttons except clear and equals=, update display
        for(JButton i:buttonOrder) {
            if(i.equals(clear) || i.equals(equals))
                continue;
            i.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(displayString.toString().equals("ERROR")) {
                        displayString.replace(0, displayString.length(), i.getText());
                        numField.setText(displayString.toString());
                    } else {
                        displayString.append(i.getText());
                        numField.setText(displayString.toString());
                    }
                }
            });
        }
        
        // Clears display
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                displayString.delete(0, displayString.length());
                numField.setText(displayString.toString());
            }
        });
        
        // Clears display, shows result
        equals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                equationParse(displayString);
            }
        });
    }

    public static void equationParse(StringBuilder eqn) {
        // TODO: make my own parser
        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
        
        try {
            Object result = engine.eval(displayString.toString());
            displayString.replace(0, displayString.length(), result.toString());
            numField.setText(displayString.toString());
        } catch (ScriptException e) {
            displayString.replace(0, displayString.length(), "ERROR");
            numField.setText(displayString.toString());
            //e.printStackTrace();
        }
    }
    
    // TODO: add functionality for typing into the display
}
