package main.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Board board = Board.getBoard();
        MoveHistory mvHistory = MoveHistory.getMoveHistory();
        Player whitePlayer;
        Player blackPlayer;

        System.out.println("Would you like to play Player vs Player or Player vs Computer? Please enter PvP or PvC");
        String gameType = scanner.nextLine();

        if (gameType.equals("PvC")) {

            whitePlayer = new Player(false);
            blackPlayer = new Agent(true, 10, whitePlayer);


        } else {

            whitePlayer = new Player(false);
            blackPlayer = new Player(true);
        }

        while (board.kingsAlive()) {

            System.out.println("\nWhite Turn");
            board.printWhitesBoard();
            while (!whitePlayer.takeTurn()) {
                System.out.println("\nWhite Turn");
                board.printWhitesBoard();
            }
            if (!board.kingsAlive()) break;
            System.out.println("\nBlack Turn");
            board.printBlacksBoard();
            while (!blackPlayer.takeTurn()) {
                System.out.println("\nBlack Turn");
                board.printBlacksBoard();
            }

        }

        board.printWhitesBoard();
        System.out.println("\nThe game is over, " + board.checkForWinner() + " has won!");

    }
}