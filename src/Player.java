import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {

    // 0 is white, 1 is black
    public boolean team;
    private Player opponent;

    public Player(boolean team) {
        this.team = team;
    }

    /**
     * Returns true if a valid turn was completed, and records the move in MoveHistory
     * @return True if a legal move was made.
     */
    public boolean takeTurn() {

        Board board = Board.getBoard();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Identify the piece you want to move first and then where you want it moved to using coordinates and a space between the inputs.\nExample input: a,6 a,5");


        boolean wrongInput = true;
        int[] startCoords = new int[2];
        int[] endCoords = new int[2];

        while(wrongInput) {

            try {
                String input = scanner.nextLine();
                String move = this.inputToCoords(input);
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
     * @throws Exception Shouldn't happen, but throws exception if it tries to access a non-existent square.
     */
    public ArrayList<AbsPiece> getAllPieces(Board board) throws Exception {

        ArrayList<AbsPiece> allPieces = new ArrayList<AbsPiece>();

        for (int x = 0; x < 8; x ++) {
            for (int y = 0; y < 8; y ++) {
                if (board.getSquare(x,y).getPiece() == null) {
                    int dummy = 0;
                }
                else if (board.getSquare(x,y).getPiece().getTeam() == this.team) {
                    allPieces.add(board.getSquare(x,y).getPiece());
                }

            }
        }

        return allPieces;

    }

    /**
     * Chooses a random possible move and does it
     * @board The board that the move is being chosen from (want to specify in case we're doing AI rollout simulations)
     * @throws Exception wont happen
     */
    public void chooseRandomMove(Board board) throws Exception {
        Random random = new Random();
        ArrayList<AbsPiece> allPieces = this.getAllPieces(board);
        AbsPiece randPiece = allPieces.get(random.nextInt(allPieces.size()));
        ArrayList<Move> allMoves = randPiece.getAllMoves(board);

        while (allMoves.size() <= 0) {
            randPiece = allPieces.get(random.nextInt(allPieces.size()));
            allMoves = randPiece.getAllMoves(board);
        }


        Move chosenMove = allMoves.get(random.nextInt(allMoves.size()));
        Square startSquare = board.getSquare(chosenMove.getStartX(), chosenMove.getStartY());
        Square endSquare = board.getGameBoard()[chosenMove.getEndX()][chosenMove.getEndY()];
        startSquare.setPiece(null);
        endSquare.setPiece(randPiece);
        randPiece.setSquare(endSquare);
        randPiece.setHasMoved(true);
    }

    /**
     * Returns the number of live pieces the player has
     * @param board The board looked at
     * @return the number of live pieces
     */
    public int numberOfLivePieces(Board board) {

        try {
            return this.getAllPieces(board).size();
        } catch (Exception e) { System.out.println("Exception in Player.numberOfLivePieces"); return -99; }
    }

    public void setOpponent(Player opponent) { this.opponent = opponent; }

    /**
     * Takes in a user input in the form of <letter><number> for coordinates and returns to numerical coordinataes of our system
     * TODO very messy, I'll deal with it later
     * @param userInput The user input
     * @return A string of the same move in our system's coords
     */
    public String inputToCoords(String userInput) {

        StringBuilder strBld = new StringBuilder();
        String[] startCoords = userInput.split(" ")[0].split(",");
        String[] endCoords = userInput.split(" ")[1].split(",");

        String startColNumber = Integer.toString((int) (startCoords[0].toLowerCase().charAt(0)) - 97); // Gives number of column (0 for 'a', 1 for 'b', etc)
        String endColNumber = Integer.toString((int) (endCoords[0].toLowerCase().charAt(0)) - 97);

        strBld.append(startColNumber);
        strBld.append(",");
        strBld.append(Integer.toString(8 - Integer.parseInt(startCoords[1])));
        strBld.append(" ");
        strBld.append(endColNumber);
        strBld.append(",");
        strBld.append(Integer.toString(8 - Integer.parseInt(endCoords[1])));

        return strBld.toString();

    }

}
