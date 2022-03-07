package main.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Board board = Board.getBoard();
        MoveHistory mvHistory = MoveHistory.getMoveHistory();
        Player whitePlayer = null;
        Player blackPlayer = null;

        System.out.println("Would you like to play in terms of Players and Computers? Please enter PvP, PvC or CvC.");
        String gameType = scanner.nextLine();

        if (gameType.equals("PvC")) {

            whitePlayer = new Player(false);
            blackPlayer = new Agent(true, 10, whitePlayer);
            whitePlayer.setOpponent(blackPlayer);

        } else if (gameType.equals("PvP")) {

            whitePlayer = new Player(false);
            blackPlayer = new Player(true);
            whitePlayer.setOpponent(blackPlayer);
            blackPlayer.setOpponent(whitePlayer);

        } else if (gameType.equals("CvC")) {

            whitePlayer = new Agent(false, 10, null);
            blackPlayer = new Agent(true, 10, whitePlayer);
            whitePlayer.setOpponent(blackPlayer);

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
            if (gameType.equals("PvP") || gameType.equals("CvC")) board.printBlacksBoard(); // Don't want to print the AI's board everytime, its just confusing looking
            while (!blackPlayer.takeTurn()) {
                System.out.println("\nBlack Turn");
                board.printBlacksBoard();
            }

        }

        board.printWhitesBoard();
        System.out.println("\nThe game is over, " + board.checkForWinner() + " has won!");

    }
}