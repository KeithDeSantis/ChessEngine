package main.java;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {

    // 0 is white, 1 is black
    public boolean team;

    public Player(boolean team) {
        this.team = team;
    }

    /**
     * Returns true if a valid turn was completed, and records the move in main.java.MoveHistory
     * @return True if a legal move was made.
     */
    public boolean takeTurn() {

        Board board = Board.getBoard();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Identify first, the piece you want to move and then, where you was it moved to using coordinates and a space between the inputs.\nExample input: 1,6 1,5");


        boolean wrongInput = true;
        int[] startCoords = new int[2];
        int[] endCoords = new int[2];

        while(wrongInput) {

            try {
                String move = scanner.nextLine();
                String[] start = move.split(" ")[0].split(",");
                String[] end = move.split(" ")[1].split(",");
                startCoords[0] = Integer.parseInt(start[0]);
                startCoords[1] = Integer.parseInt(start[1]);
                endCoords[0] = Integer.parseInt(end[0]);
                endCoords[1] = Integer.parseInt(end[1]);
                wrongInput = false;
            } catch (Exception e) {
                System.out.println("Invalid input. Please use the form: 'x1,y1 x2,y2' with x1,y1 being the starting piece and x2,y2 being the destination.");
            }
        }

        try {

            if (board.getSquare(startCoords[0], startCoords[1]).getPiece().getTeam() != this.team) {
                System.out.println("Illegal move detected.");
                return false;
            }

            board.getSquare(startCoords[0], startCoords[1]).getPiece().move(board.getGameBoard()[endCoords[0]][endCoords[1]]);

            Move finishedMove = new Move(this.team, board.getSquare(endCoords[0], endCoords[1]).getPiece(), board.getSquare(startCoords[0], startCoords[1]), board.getSquare(endCoords[0], endCoords[1]));
            MoveHistory.getMoveHistory().addToHistory(finishedMove);
            return true;

        } catch (Exception e) {
            System.out.println("Illegal move detected.");
            return false;
        }

    }

    /**
     * Gets all of the player's current pieces
     * @return A list of all the current pieces
     * @throws Exception Shouldn't happen, but throws expception if it tries to access a non-existent square.
     */
    public ArrayList<AbsPiece> getAllPieces(Board board) throws Exception {

        ArrayList<AbsPiece> allPieces = new ArrayList<AbsPiece>();

        for (int x = 0; x < 8; x ++) {
            for (int y = 0; y < 8; y ++) {

                if (board.getSquare(x,y).getPiece().getTeam() == this.team) {
                    allPieces.add(board.getSquare(x,y).getPiece());
                }

            }
        }

        return allPieces;

    }

    /**
     * Chooses a random possible move
     * @board The board that the move is being chosen from (want to specify in case we're doing AI rollout simulations)
     * @return the random possible move
     * @throws Exception wont happen
     */
    public Move choseRandomMove(Board board) throws Exception {
        Random random = new Random();
        ArrayList<AbsPiece> allPieces = this.getAllPieces(board);
        AbsPiece randPiece = allPieces.get(random.nextInt(allPieces.size()));
        ArrayList<Move> allMoves = randPiece.getAllMoves();
        return allMoves.get(random.nextInt(allMoves.size()));
    }

}
