import java.util.ArrayList;

public interface IPiece {

    /**
     * Returns true if the piece can move to the given square
     * @return
     */
    public boolean canMove(Square dest, Board board);

    /**
     * Moves the piece to the given square
     * @param dest the destination square
     * @return the destination square
     * @throws Exception in case the move is invalid
     */
    public Square move(Square dest) throws Exception;

    /**
     * Returns a list of all possible moves a piece can make, different implementation based on piece type for optimization
     * @param board The board context being looked at
     * @return An ArrayList<Move> of possible moves
     * @throws Exception If the function tries to access a non-existent square
     */
    public ArrayList<Move> getAllMoves(Board board) throws Exception;

}
