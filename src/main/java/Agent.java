package main.java;

import java.util.ArrayList;
import java.util.Random;

public class Agent extends Player {

    private float difficulty;
    private Player opponent;

    public Agent(boolean team, int difficulty, Player opponent) {
        super(team);
        this.opponent = opponent;
        this.difficulty = difficulty * 500 + 1; // TODO Testing to determine difficulty and what coefficient should be
    }

    /**
     * AI Agent Takes Turn
     */
    @Override
    public boolean takeTurn() {

        System.out.println("\nCalculating Move......");
        long start = System.currentTimeMillis();

        Board board = Board.getBoard();
        Board simulatedBoard = board.duplicateBoard();
        ArrayList<AbsPiece> allPieces;
        try {
            allPieces = this.getAllPieces(simulatedBoard);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Tried to access square outside board IN Agent.takeTurn().");
            System.exit(1);
            return false;
        }

        ArrayList<Move> allMoves = new ArrayList<Move>();
        ArrayList<Double> simulatedUtilities = new ArrayList<Double>(); // This will hold the win % of each move
        for (AbsPiece allPiece : allPieces) {
            try {
                allMoves.addAll(allPiece.getAllMoves(simulatedBoard));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Tried to access square outside board IN Agent.takeTurn() (2nd exception).");
                System.exit(1);
            }
        }

        int numSimulationsPerMove = (int) Math.ceil(this.difficulty/allMoves.size());

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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Tried to access square outside board IN Agent.takeTurn() (3rd Exception).");
            System.exit(1);
        }

        MoveHistory.getMoveHistory().addToHistory(chosenMove);

        long end = System.currentTimeMillis();

        long timeTaken = end - start;

        System.out.println(timeTaken/1000 + " second(s) taken to find move.\n");

        return true;
    }

    /**
     * Simulates random games from that move
     * @param board Copy of the board
     * @param chosenMove Move chosen by Agent
     * @return True if the agent won, false if it didn't
     */
    public boolean simulateGame(Board board, Move chosenMove) {

        Board simulatedBoard = board.duplicateBoard();
        int numberTurns = 0;

        try {
            AbsPiece movedPiece = simulatedBoard.getSquare(chosenMove.getStartX(), chosenMove.getStartY()).getPiece();
            Square startSquare = simulatedBoard.getSquare(chosenMove.getStartX(), chosenMove.getStartY());
            Square endSquare = simulatedBoard.getGameBoard()[chosenMove.getEndX()][chosenMove.getEndY()];
            startSquare.setPiece(null);
            endSquare.setPiece(movedPiece);
            movedPiece.setSquare(endSquare);
            movedPiece.setHasMoved(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Tried to access square outside of board IN Agent.simulateGame().");
            System.exit(1);
        }

        while(simulatedBoard.kingsAlive()) {

            //simulatedBoard.printBlacksBoard();

            numberTurns++; // Used to limit how long the sim can run

            if (numberTurns > this.difficulty/2) { // For now I'll just say its allowed to run for half as many turns as simulations will happen

                if (this.numberOfLivePieces(simulatedBoard) > this.opponent.numberOfLivePieces(simulatedBoard)) {
                    return true; // Base winning on who has more pieces
                }
                else if (this.numberOfLivePieces(simulatedBoard) < this.opponent.numberOfLivePieces(simulatedBoard)) {
                    return false;
                }
                Random rand = new Random();
                return rand.nextBoolean(); // If they have the same number of pieces then just choose a random winner

            }

            try {
                this.opponent.chooseRandomMove(simulatedBoard);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Tried to access square outside of board IN Agent.simulateGame() (2nd Exception).");
                System.exit(1);
            }
            if (!simulatedBoard.kingsAlive()) break;
            try {
                this.chooseRandomMove(simulatedBoard);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Tried to access square outside of board IN Agent.simulateGame() (3rd Exception).");
                System.exit(1);
            }

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

    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}
