package com.example.tictactoe;

public class TicTacToeGame {

    private char[][] board;
    private char currentPlayer;

    public TicTacToeGame() {
        board = new char[3][3];
        reset();
    }

    public void reset() {
        currentPlayer = 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Try to make a move at (row, col).
     * @return true if move is valid and placed, false otherwise.
     */
    public boolean makeMove(int row, int col) {
        // row, col must be between 0 and 2
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }
        // cell must be empty
        if (board[row][col] != ' ') {
            return false;
        }

        board[row][col] = currentPlayer;
        return true;
    }

    public boolean checkWin() {
        return hasWon(board, currentPlayer);
    }

    public boolean isDraw() {
        // if any empty cell exists, not a draw
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        // no empty cells, check there is no winner
        return !hasWon(board, 'X') && !hasWon(board, 'O');
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    /**
     * Static method to check if a player has won.
     * Useful for both game and JUnit tests.
     */
    public static boolean hasWon(char[][] board, char player) {
        // rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player &&
                board[i][1] == player &&
                board[i][2] == player) {
                return true;
            }
        }

        // columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player &&
                board[1][j] == player &&
                board[2][j] == player) {
                return true;
            }
        }

        // main diagonal
        if (board[0][0] == player &&
            board[1][1] == player &&
            board[2][2] == player) {
            return true;
        }

        // anti-diagonal
        if (board[0][2] == player &&
            board[1][1] == player &&
            board[2][0] == player) {
            return true;
        }

        return false;
    }

    public char[][] getBoard() {
        return board;
    }
}
