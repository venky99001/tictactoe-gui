package com.example.tictactoe;

import javax.swing.*;
import java.awt.*;

public class TicTacToeGUI extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private TicTacToeGame game = new TicTacToeGame();
    private JLabel statusLabel = new JLabel("Player X's turn");

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout());

        // Status label at the top
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(statusLabel, BorderLayout.NORTH);

        // Grid of 3x3 buttons in the center
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        Font buttonFont = new Font("Arial", Font.BOLD, 40);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(buttonFont);
                final int r = row;
                final int c = col;

                button.addActionListener(e -> handleButtonClick(r, c, button));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        // Reset button at the bottom
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);
    }

    private void handleButtonClick(int row, int col, JButton button) {
        char currentPlayer = game.getCurrentPlayer();

        if (!game.makeMove(row, col)) {
            JOptionPane.showMessageDialog(this, "Invalid move!", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        button.setText(String.valueOf(currentPlayer));

        if (game.checkWin()) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            JOptionPane.showMessageDialog(this,
                    "Player " + currentPlayer + " wins!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            disableBoard();
        } else if (game.isDraw()) {
            statusLabel.setText("It's a draw!");
            JOptionPane.showMessageDialog(this,
                    "It's a draw!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            disableBoard();
        } else {
            game.switchPlayer();
            statusLabel.setText("Player " + game.getCurrentPlayer() + "'s turn");
        }
    }

    private void disableBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        game.reset();
        statusLabel.setText("Player " + game.getCurrentPlayer() + "'s turn");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI gui = new TicTacToeGUI();
            gui.setVisible(true);
        });
    }
}
