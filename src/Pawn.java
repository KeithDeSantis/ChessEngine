import java.util.ArrayList;
import java.util.Scanner;

public class Pawn extends AbsPiece {

    public Pawn(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("Pawn");

    }
    public Pawn(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("Pawn");
    }

    /**
     * Checks if a move to a destination is legal.
     * @param dest The square the piece is trying to move to.
     * @return True if the move is legal
     */
    public boolean canMove(Square dest, Board board) {

        if (isSameTeam(dest.getPiece())) return false;

        Path path = new Path(this.getSquare());

        try {
            path.generatePawnPath(dest, board);
        } catch (Exception e) {
            return false;
        }

        return path.getIsClear();

    }

    /**
     * Move the piece to the given destination (if legal)
     * @param dest the destination square
     * @return The square that the piece ends on after a successful move
     * @throws Exception If the destination is illegal to move to
     * throw an "Invalid Move Exception"
     */
    public Square move(Square dest) throws Exception {

        if (!canMove(dest, Board.getBoard())) {
            throw new Exception("Invalid move exception"); // TODO weird bug was sometimes happening here??
        }

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null);

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this);

        this.setSquare(dest);

        if (dest.getyAxis() == 0 && !this.getTeam()) this.pawnSwapHelper(false); // white pawn can change to new piece

        if (dest.getyAxis() == 8 && this.getTeam()) this.pawnSwapHelper(true); // black pawn can change to new piece

        return dest;

    }

    /**
     * Helper function for Pawn's movement that takes the player's choice of piece to replace and replaces it
     * @param team The team whose pawn is being switched
     */
    private void pawnSwapHelper(boolean team) {

        Scanner scanner = new Scanner(System.in);

        this.setLive(false);

        boolean invalidPiece = true;

        System.out.println("Your pawn has reached the opposite side of the board, if you want to switch \nto another piece (other than King) write the piece's name (e.g. \"Queen\"). If not please write \"Pawn\".");

        while(invalidPiece) {

            invalidPiece = false;

            String chosenPiece = scanner.nextLine();
            chosenPiece = chosenPiece.toUpperCase().trim();

            switch (chosenPiece) {
                case "ROOK":
                    this.getSquare().setPiece(new Rook(team, true, this.getSquare(), true)); // **NOTE** idk why this would matter but should it count as having moved?
                    break;
                case "KNIGHT":
                    this.getSquare().setPiece(new Knight(team, true, this.getSquare(), true));
                    break;
                case "BISHOP":
                    this.getSquare().setPiece(new Bishop(team, true, this.getSquare(), true));
                    break;
                case "QUEEN":
                    this.getSquare().setPiece(new Queen(team, true, this.getSquare(), true));
                    break;
                case "PAWN":
                    this.setLive(true);
                    break;
                default:
                    System.out.println("Please input a valid piece name.");
                    invalidPiece = true;
                    break;
            }

        }

    }

    /**
     * Returns a list of all possible moves the Pawn can make
     * @param board The board context being looked at
     * @return An ArrayList<Move> of possible moves
     * @throws Exception If the function tries to access a non-existent square
     */
    public ArrayList<Move> getAllMoves(Board board) throws Exception { // TODO add en passant (too lazy rn)

        ArrayList<Move> allMoves = new ArrayList<Move>();

        int xAxis = this.getSquare().getxAxis();
        int yAxis = this.getSquare().getyAxis();
        int forwardY;
        int doubleJumpY;

        if (this.getTeam()) {
                forwardY = yAxis + 1;
                doubleJumpY = yAxis + 2;
        }
        else {
            forwardY = yAxis - 1;
            doubleJumpY = yAxis - 2;
        }

        if (areCoordsInBoundsHelper(xAxis, forwardY)) {
            if (canMove(board.getSquare(xAxis, yAxis), board)) allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis, forwardY))); // Forward move
        }
        if (areCoordsInBoundsHelper(xAxis, doubleJumpY)) {
            if (canMove(board.getSquare(xAxis, doubleJumpY), board)) allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis, doubleJumpY))); // Double Jump move
        }
        if (areCoordsInBoundsHelper(xAxis - 1, forwardY)) {
            if (canMove(board.getSquare(xAxis - 1, forwardY), board)) allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis - 1, forwardY))); // Capture 1
        }
        if (areCoordsInBoundsHelper(xAxis + 1, forwardY)) {
            if (canMove(board.getSquare(xAxis + 1, forwardY), board)) allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis + 1, forwardY))); // Capture 2
        }

        return allMoves;

    }

    /**
     * Deep copies this piece
     * @return the deep copy
     */
    @Override
    public Pawn duplicatePiece() {
        return new Pawn(this.getTeam(), this.isLive(), null, this.getHasMoved());
    }



} //TODO add En Passant
