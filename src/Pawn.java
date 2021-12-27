import java.util.Locale;
import java.util.Scanner;

public class Pawn extends AbsPiece implements IPiece {

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
    public boolean canMove(Square dest) {

        if (isSameTeam(dest.getPiece())) return false;

        Path path = new Path(this.getSquare());

        try {
            path.generatePawnPath(dest);
        } catch (Exception e) { return false; }

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

        if (!canMove(dest)) throw new Exception("Invalid move exception");

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



} //TODO add En Passant
