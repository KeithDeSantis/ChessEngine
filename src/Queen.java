import java.util.ArrayList;

public class Queen extends AbsPiece {

    public Queen(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("Queen");

    }
    public Queen(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("Queen");
    }

    /**
     * Checks if a move to a destination is legal.
     * @param dest The square the piece is trying to move to.
     * @return True if the move is legal
     */
    public boolean canMove(Square dest, Board board) {

        if (isSameTeam(dest.getPiece())) return false;

        Path hPath = new Path(this.getSquare());
        Path vPath = new Path(this.getSquare());
        Path dPath = new Path(this.getSquare());

        boolean canGoHorizontal = true;
        boolean canGoVertical = true;
        boolean canGoDiagonal = true;

        try {
            hPath.generateHorizontal(dest, board);
        } catch (Exception e) { canGoHorizontal = false; }
        try {
            vPath.generateVertical(dest, board);
        } catch (Exception e) { canGoVertical = false; }
        try {
            dPath.generateDiagonal(dest, board);
        } catch (Exception e) { canGoDiagonal = false; }

        if (canGoHorizontal) return hPath.getIsClear();
        if (canGoVertical) return vPath.getIsClear();
        if (canGoDiagonal) return dPath.getIsClear();

        return false;

    }

    /**
     * Move the piece to the given destination (if legal)
     * @param dest the destination square
     * @return The square that the piece ends on after a successful move
     * @throws Exception If the destination is illegal to move to
     * throw an "Invalid Move Exception"
     */
    public Square move(Square dest) throws Exception{

        if (!canMove(dest, Board.getBoard())) throw new Exception("Invalid move exception");

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null); // Remove from initial position

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this); // Place in new position

        this.setSquare(dest); // Update the piece

        return dest;

    }

    /**
     * Returns a list of all possible moves the Queen can make
     * @param board The board context being looked at
     * @return An ArrayList<Move> of possible moves
     * @throws Exception If the function tries to access a non-existent square
     */
    public ArrayList<Move> getAllMoves(Board board) throws Exception {

        // Effectively just a combination of all the Bishop's moves and all the Rook's moves so...

        ArrayList<Move> allMoves = new ArrayList<Move>();
        Rook pathFindingRook = new Rook(this.getTeam(), this.getIsLive(), this.getSquare(), this.getHasMoved());
        Bishop pathFindingBishop = new Bishop(this.getTeam(), this.getIsLive(), this.getSquare(), this.getHasMoved());

        allMoves.addAll(pathFindingBishop.getAllMoves(board));
        allMoves.addAll(pathFindingRook.getAllMoves(board));

        return allMoves;

    }

    /**
     * Deep copies this piece
     * @return the deep copy
     */
    @Override
    public Queen duplicatePiece() {
        return new Queen(this.getTeam(), this.isLive(), null, this.getHasMoved());
    }

}
