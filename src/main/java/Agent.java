package main.java;

import java.util.ArrayList;

public class Agent extends Player {

    private float difficulty;
    private Player opponent;

    public Agent(boolean team, int difficulty, Player opponent) {
        super(team);
        this.opponent = opponent;
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

        try {
            simulatedBoard.getSquare(chosenMove.getStartX(), chosenMove.getStartY()).getPiece().move(board.getGameBoard()[chosenMove.getEndX()][chosenMove.getEndY()]);
        } catch (Exception e) {System.out.println("Tried to access square outside of board."); return false; }

        while(simulatedBoard.kingsAlive()) {

            try {
                this.opponent.chooseRandomMove(simulatedBoard);
            } catch (Exception e) {System.out.println("Tried to access square outside of board."); return false; }
            if (!simulatedBoard.kingsAlive()) break;
            try {
                this.chooseRandomMove(simulatedBoard);
            } catch (Exception e) {System.out.println("Tried to access square outside of board."); return false; }

        }

        return checkAgentWonHelper(simulatedBoard);
    }

    /**
     * Helper to interpret board.checkForWinner() output
     * @param board the board
     * @return true if the Agent won
     */
    public boolean checkAgentWonHelper(Board board) {

        if (board.checkForWinner().equals("Black")) {
            return this.team;
        }
        else if (board.checkForWinner().equals("White")) {
            return !this.team;
        }

        System.out.println("Got to a line you shouldn't have in checkAgentWonHelper!!\n");
        return true; //should never get here
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
