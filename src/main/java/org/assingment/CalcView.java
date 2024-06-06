package org.assingment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CalcView extends JFrame implements ActionListener {
    boolean evaluated = false;
    JTextField canvas = new JTextField();
    JButton[] numButtons = new JButton[10];
    JButton[] opButtons = new JButton[6];
    JPanel numButtonPanel = new JPanel();
    JPanel topActionPanel = new JPanel();
    JPanel bottomActionPanel = new JPanel();
    JButton clearButton = new JButton("Clear");
    JButton backSpace = new JButton("BackSpace");
    JButton equalButton = new JButton("=");
    JButton dotButton = new JButton();

    CalcView() {
        setTitle("Basic Calculator");
        setSize(500, 500);
        setResizable(false);
        setBackground(Color.DARK_GRAY);

        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        int canvasHeight = 60;
        canvas.setBounds(0, 0, 485, canvasHeight);
        canvas.setBackground(Color.GRAY);
        canvas.setFont(new Font("Arial", Font.PLAIN, 30));
        canvas.setHorizontalAlignment(SwingConstants.RIGHT);
        canvas.setEnabled(false);
        add(canvas);

        int topPanelHeightY = 60;
        int numPanelHeightY = 110;
        int buttonPanelHeightY = 410;
        addTopPanel(topPanelHeightY);
        addNumPanel(numPanelHeightY);
        addButtonPanel(buttonPanelHeightY);

    }

    private void initNumButtons() {
        for (int i = 0; i < 10; i++) {
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].setFocusPainted(false);
            numButtons[i].addActionListener(this);
            numButtons[i].setBackground(Color.DARK_GRAY);
            numButtons[i].setForeground(Color.WHITE);
            numButtons[i].setFont(new Font("Arial", Font.PLAIN, 25));
        }
        String[] opStrings = {"+", "-", "*", "/", "Sqrt", "Mod"};
        for (int i = 0; i < 6; i++) {
            opButtons[i] = new JButton(opStrings[i]);
            opButtons[i].setFocusPainted(false);
            opButtons[i].addActionListener(this);
            opButtons[i].setBackground(Color.DARK_GRAY);
            opButtons[i].setForeground(Color.WHITE);
            opButtons[i].setFont(new Font("Arial", Font.PLAIN, 25));
        }
    }

    private void addTopPanel(int y) {
        topActionPanel.setBounds(0, y, 500, 50);
        topActionPanel.setLayout(null);
        topActionPanel.setBackground(Color.DARK_GRAY);

        clearButton.setBounds(0, 0, 250, 50);
        clearButton.addActionListener(this);
        clearButton.setFocusPainted(false);
        clearButton.setBackground(Color.DARK_GRAY);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.ITALIC, 15));
        topActionPanel.add(clearButton);

        backSpace.setBounds(250, 0, 250, 50);
        backSpace.addActionListener(this);
        backSpace.setFocusPainted(false);
        backSpace.setBackground(Color.DARK_GRAY);
        backSpace.setForeground(Color.WHITE);
        backSpace.setFont(new Font("Arial", Font.ITALIC, 15));
        topActionPanel.add(backSpace);

        add(topActionPanel);
    }

    private void addNumPanel(int y) {
        initNumButtons();
        numButtonPanel.setLayout(new GridLayout(4, 4, 0, 0));
        numButtonPanel.setBounds(0, y, 500, 300);
        numButtonPanel.add(numButtons[1]);
        numButtonPanel.add(numButtons[2]);
        numButtonPanel.add(numButtons[3]);
        numButtonPanel.add(opButtons[0]); // +
        numButtonPanel.add(numButtons[4]);
        numButtonPanel.add(numButtons[5]);
        numButtonPanel.add(numButtons[6]);
        numButtonPanel.add(opButtons[1]); // -
        numButtonPanel.add(numButtons[7]);
        numButtonPanel.add(numButtons[8]);
        numButtonPanel.add(numButtons[9]);
        numButtonPanel.add(opButtons[2]); // *
        numButtonPanel.add(numButtons[0]);
        numButtonPanel.add(opButtons[5]); // Mod
        numButtonPanel.add(opButtons[4]); // Sqrt
        numButtonPanel.add(opButtons[3]);  // /
        add(numButtonPanel);
    }

    private void addButtonPanel(int y) {
        bottomActionPanel.setBounds(0, y, 500, 65);
        bottomActionPanel.setLayout(null);
        bottomActionPanel.setBackground(Color.DARK_GRAY);

        dotButton.setBounds(0, 0, 124, 50);
        dotButton.addActionListener(this);
        dotButton.setFocusPainted(false);
        dotButton.setBackground(Color.DARK_GRAY);
        dotButton.setForeground(Color.WHITE);
        dotButton.setText(".");
        dotButton.setFont(new Font("Arial", Font.PLAIN, 30));
        bottomActionPanel.add(dotButton);

        equalButton.setBounds(125, 0, 370, 50);
        equalButton.addActionListener(this);
        equalButton.setFocusPainted(false);
        equalButton.setBackground(Color.DARK_GRAY);
        equalButton.setForeground(Color.WHITE);
        equalButton.setFont(new Font("Arial", Font.PLAIN, 30));
        bottomActionPanel.add(equalButton);


        add(bottomActionPanel);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (evaluated) {
            canvas.setText("");
            evaluated = false;
        }
        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            // just append the number
            canvas.setText(canvas.getText() + command);
        } else if (command.charAt(0) == 'C') {
            canvas.setText("");
        } else if (command.equals("BackSpace")) {
            String text = canvas.getText();
            if (!text.isEmpty()) {
                canvas.setText(text.substring(0, text.length() - 1));
            }
        } else if (command.charAt(0) == '=') {
            // Calculate the result
            String text = canvas.getText();
            evaluated = true;
            String result = "";
            try {
                result = String.valueOf(CalculationModel.evaluate(text));
            } catch (ArithmeticException aex) {
                JOptionPane.showMessageDialog(this, aex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                result = aex.getMessage();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid expression", "Error", JOptionPane.ERROR_MESSAGE);
                result = ex.getMessage();
            }
            canvas.setText(result);
        } else if (command.equals("Mod")) {
            canvas.setText(canvas.getText() + "%");
        } else if (command.equals("Sqrt")) {
            // if the last character is a number or a dot, then append *√
            // if the last character is √, then do nothing
            // else append √
            if (canvas.getText().isEmpty()) {
                canvas.setText("√");
                return;
            } else {
                char lastChar = canvas.getText().charAt(canvas.getText().length() - 1);
                if ((lastChar >= '0' && lastChar <= '9') || lastChar == '.') {
                    canvas.setText(canvas.getText() + "*√");
                } else if (lastChar == '√') {
                    return;
                } else {
                    canvas.setText(canvas.getText() + "√");
                }
            }

        } else {
            canvas.setText(canvas.getText() + command);
        }
    }

}
