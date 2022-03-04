package main.java;

import java.util.ArrayList;

public class Agent extends Player {

    private float difficulty;

    public Agent(boolean team, int difficulty) {
        super(team);
        this.difficulty = difficulty * 1000 + 1; // TODO Testing to determine difficulty and what coefficient should be
    }

    /**
     * AI Agent Takes Turn
     */
    @Override
    public boolean takeTurn() {

        Board board = Board.getBoard();
        ArrayList<AbsPiece> allPieces;
        try {
            allPieces = this.getAllPieces(board);
        } catch(Exception e) { System.out.println("Tried to access square outside board."); return false; }

        ArrayList<Move> allMoves = new ArrayList<Move>();
        ArrayList<Double> simulatedUtilities = new ArrayList<Double>(); // This will hold the win % of each move
        for (AbsPiece allPiece : allPieces) {
            try {
                allMoves.addAll(allPiece.getAllMoves());
            } catch (Exception e) {
                System.out.println("Tried to access square outside board.");
                return false;
            }
        }

        int numSimulationsPerMove = (int) Math.ceil(allMoves.size()/this.difficulty);

        for (Move curMove : allMoves) {

            int wins = 0;
            int totalSims = 0;

            for (int i = 0; i < numSimulationsPerMove; i++) {
                if (this.simulateGame(Board.getBoard(), curMove)) {
                    wins++;
                }
                totalSims++;
            }

            simulatedUtilities.add(allMoves.indexOf(curMove), (double) wins/totalSims);

        }

        Move chosenMove = this.findBestMove(allMoves, simulatedUtilities);
        int startX = chosenMove.getStartX();
        int startY = chosenMove.getStartY();
        int endX = chosenMove.getEndX();
        int endY = chosenMove.getEndY();


        try {
            board.getSquare(startX, startY).getPiece().move(board.getGameBoard()[endX][endY]);
        } catch (Exception e) { System.out.println("Tried to access square outside board."); return false; }

        MoveHistory.getMoveHistory().addToHistory(chosenMove);

        return true;
    }

    /**TODO
     * Simulates random games from that move
     * @param board Copy of the board
     * @param chosenMove Move chosen by Agent
     * @return True if the agent won, false if it didn't
     */
    public boolean simulateGame(Board board, Move chosenMove) {

        Board simulatedBoard = board.duplicateBoard();

        // Method to get random move is in Player.java

        return false;
    }

    /**
     * Uses estimated utilites to find best move from two given lists that match 1 to 1
     * @param allMoves the moves
     * @param simulatedUtilities each move's simulated utility
     * @return the move with highest simulated utility
     */
    public Move findBestMove(ArrayList<Move> allMoves, ArrayList<Double> simulatedUtilities) {
        double maxUtil = 0;
        int maxIndex = 0;
        for (Double utility : simulatedUtilities) {
            if (utility > maxUtil) {
                maxUtil = utility.doubleValue();
                maxIndex = simulatedUtilities.indexOf(utility);
            }
        }
        return allMoves.get(maxIndex);
    }

    public float getDifficulty() { return this.difficulty; }

}
