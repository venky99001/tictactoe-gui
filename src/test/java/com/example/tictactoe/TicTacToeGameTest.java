package com.example.tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeGameTest {

    @Test
    void testRowWin() {
        char[][] board = {
            {'X', 'X', 'X'},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };
        assertTrue(TicTacToeGame.hasWon(board, 'X'));
    }

    @Test
    void testDiagonalWin() {
        char[][] board = {
            {'O', ' ', ' '},
            {' ', 'O', ' '},
            {' ', ' ', 'O'}
        };
        assertTrue(TicTacToeGame.hasWon(board, 'O'));
    }

    @Test
    void testNoWin() {
        char[][] board = {
            {'X', 'O', 'X'},
            {'O', 'X', 'O'},
            {'O', 'X', 'O'}
        };
        assertFalse(TicTacToeGame.hasWon(board, 'O'));
    }

    @Test
    void testValidMove() {
        TicTacToeGame game = new TicTacToeGame();
        assertTrue(game.makeMove(1, 1)); // center move
        assertEquals('X', game.getBoard()[1][1]);
    }

    @Test
    void testInvalidMoveAlreadyTaken() {
        TicTacToeGame game = new TicTacToeGame();
        game.makeMove(1, 1);
        assertFalse(game.makeMove(1, 1)); // can't play same spot
    }
}
