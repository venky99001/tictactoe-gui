package com.example.tictactoe;

import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;

public class TicTacToeGUI extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private TicTacToeGame game = new TicTacToeGame();
    private JLabel statusLabel = new JLabel("Player X's turn");

    // Scoreboard
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;
    private JLabel xScoreLabel;
    private JLabel oScoreLabel;
    private JLabel drawScoreLabel;

    // Game mode: true = vs Computer (O), false = 2 players
    private boolean vsComputer = true;
    private JRadioButton vsComputerRadio;
    private JRadioButton twoPlayerRadio;

    // Theme colors
    private static final Color BG_COLOR = new Color(18, 18, 30);
    private static final Color BOARD_BG = new Color(32, 32, 48);
    private static final Color TILE_BG = new Color(45, 45, 65);
    private static final Color X_COLOR = new Color(129, 230, 217);
    private static final Color O_COLOR = new Color(248, 189, 150);
    private static final Color TEXT_MUTED = new Color(210, 210, 230);
    private static final Color ACCENT = new Color(130, 170, 255);

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 520);
        setResizable(false);
        setLocationRelativeTo(null); // center on screen

        // Root panel
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_COLOR);
        setContentPane(root);

        // ----- TOP: Title + Status + Scoreboard -----
        JPanel topPanel = new JPanel();
        topPanel.setBackground(BG_COLOR);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));

        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(ACCENT);

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setForeground(TEXT_MUTED);

        // Scoreboard row
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        scorePanel.setBackground(BG_COLOR);

        xScoreLabel = new JLabel("X Wins: 0");
        oScoreLabel = new JLabel("O Wins: 0");
        drawScoreLabel = new JLabel("Draws: 0");

        Font scoreFont = new Font("Segoe UI", Font.PLAIN, 14);
        xScoreLabel.setFont(scoreFont);
        oScoreLabel.setFont(scoreFont);
        drawScoreLabel.setFont(scoreFont);

        xScoreLabel.setForeground(X_COLOR);
        oScoreLabel.setForeground(O_COLOR);
        drawScoreLabel.setForeground(TEXT_MUTED);

        scorePanel.add(xScoreLabel);
        scorePanel.add(oScoreLabel);
        scorePanel.add(drawScoreLabel);

        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(6));
        topPanel.add(statusLabel);
        topPanel.add(Box.createVerticalStrut(4));
        topPanel.add(scorePanel);

        root.add(topPanel, BorderLayout.NORTH);

        // ----- CENTER: Board -----
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        boardPanel.setBackground(BOARD_BG);

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 40);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(buttonFont);
                button.setBackground(TILE_BG);
                button.setForeground(TEXT_MUTED);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                button.setOpaque(true);

                final int r = row;
                final int c = col;

                button.addActionListener(e -> handleButtonClick(r, c, button));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        root.add(boardPanel, BorderLayout.CENTER);

        // ----- BOTTOM: Mode selection + Reset -----
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));

        // Mode panel: vs Computer / 2 Players
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        modePanel.setBackground(BG_COLOR);

        vsComputerRadio = new JRadioButton("Vs Computer");
        twoPlayerRadio = new JRadioButton("2 Players");

        vsComputerRadio.setSelected(true); // default
        vsComputerRadio.setBackground(BG_COLOR);
        twoPlayerRadio.setBackground(BG_COLOR);
        vsComputerRadio.setForeground(TEXT_MUTED);
        twoPlayerRadio.setForeground(TEXT_MUTED);
        vsComputerRadio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        twoPlayerRadio.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(vsComputerRadio);
        modeGroup.add(twoPlayerRadio);

        modePanel.add(new JLabel("Mode: "));
        modePanel.add(vsComputerRadio);
        modePanel.add(twoPlayerRadio);

        // Change mode listeners
        vsComputerRadio.addActionListener(e -> {
            if (!vsComputer) {
                vsComputer = true;
                resetGame();
                JOptionPane.showMessageDialog(this,
                        "Mode changed to: Vs Computer\nYou are X, Computer is O.",
                        "Mode Changed",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        twoPlayerRadio.addActionListener(e -> {
            if (vsComputer) {
                vsComputer = false;
                resetGame();
                JOptionPane.showMessageDialog(this,
                        "Mode changed to: 2 Players\nX and O are both human players.",
                        "Mode Changed",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Reset button
        JButton resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resetButton.setBackground(ACCENT);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        resetButton.addActionListener(e -> resetGame());

        bottomPanel.add(modePanel, BorderLayout.NORTH);
        bottomPanel.add(resetButton, BorderLayout.SOUTH);

        root.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handleButtonClick(int row, int col, JButton button) {
        // If vsComputer and it's O's turn, ignore human clicks
        if (vsComputer && game.getCurrentPlayer() == 'O') {
            JOptionPane.showMessageDialog(this,
                    "Wait for computer's move!",
                    "Hold on",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        char currentPlayer = game.getCurrentPlayer();

        if (!game.makeMove(row, col)) {
            JOptionPane.showMessageDialog(this,
                    "That spot is already taken!",
                    "Invalid move",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        applyMoveToButton(button, currentPlayer);
        playClickSound();

        if (checkGameEnd(currentPlayer)) {
            return;
        }

        game.switchPlayer();
        statusLabel.setText("Player " + game.getCurrentPlayer() + "'s turn");

        // Computer's turn (if mode is vsComputer)
        if (vsComputer && game.getCurrentPlayer() == 'O') {
            performComputerMove();
        }
    }

    private void performComputerMove() {
        int[] move = findBestMoveForComputer();
        int row = move[0];
        int col = move[1];

        if (row == -1) {
            return; // no move
        }

        game.makeMove(row, col);
        JButton button = buttons[row][col];
        applyMoveToButton(button, 'O');
        playClickSound();

        if (checkGameEnd('O')) {
            return;
        }

        game.switchPlayer();
        statusLabel.setText("Player " + game.getCurrentPlayer() + "'s turn");
    }

    private int[] findBestMoveForComputer() {
        char[][] board = game.getBoard();

        // 1) Try to win as O
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == ' ') {
                    board[r][c] = 'O';
                    if (TicTacToeGame.hasWon(board, 'O')) {
                        board[r][c] = ' ';
                        return new int[]{r, c};
                    }
                    board[r][c] = ' ';
                }
            }
        }

        // 2) Block X from winning
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == ' ') {
                    board[r][c] = 'X';
                    if (TicTacToeGame.hasWon(board, 'X')) {
                        board[r][c] = ' ';
                        return new int[]{r, c};
                    }
                    board[r][c] = ' ';
                }
            }
        }

        // 3) Take center if available
        if (board[1][1] == ' ') {
            return new int[]{1, 1};
        }

        // 4) Take first empty cell
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == ' ') {
                    return new int[]{r, c};
                }
            }
        }

        return new int[]{-1, -1};
    }

    private void applyMoveToButton(JButton button, char player) {
        button.setText(String.valueOf(player));
        button.setForeground(player == 'X' ? X_COLOR : O_COLOR);
    }

    private boolean checkGameEnd(char currentPlayer) {
        if (game.checkWin()) {
            if (currentPlayer == 'X') {
                xWins++;
            } else {
                oWins++;
            }
            updateScoreLabels();
            statusLabel.setText("Player " + currentPlayer + " wins!");
            playWinSound();
            JOptionPane.showMessageDialog(this,
                    "Player " + currentPlayer + " wins! 🎉",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            disableBoard();
            return true;
        } else if (game.isDraw()) {
            draws++;
            updateScoreLabels();
            statusLabel.setText("It's a draw!");
            playWinSound();
            JOptionPane.showMessageDialog(this,
                    "It's a draw! 🤝",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            disableBoard();
            return true;
        }
        return false;
    }

    private void updateScoreLabels() {
        xScoreLabel.setText("X Wins: " + xWins);
        oScoreLabel.setText("O Wins: " + oWins);
        drawScoreLabel.setText("Draws: " + draws);
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
                buttons[i][j].setForeground(TEXT_MUTED);
            }
        }
        // Scores stay; only board reset
    }

    private void playClickSound() {
        Toolkit.getDefaultToolkit().beep();
    }

    private void playWinSound() {
        Toolkit.getDefaultToolkit().beep();
        try {
            Thread.sleep(120);
        } catch (InterruptedException ignored) {
        }
        Toolkit.getDefaultToolkit().beep();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI gui = new TicTacToeGUI();
            gui.setVisible(true);
        });
    }
}
