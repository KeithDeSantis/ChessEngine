import java.util.ArrayList;

public class King extends AbsPiece {

    public King(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("King");

    }
    public King(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("King");
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

        if (canGoHorizontal) return hPath.getIsClear() && hPath.getLength() == 1;
        if (canGoVertical) return vPath.getIsClear() && vPath.getLength() == 1;
        if (canGoDiagonal) return dPath.getIsClear() && dPath.getLength() == 1;

        return false; //TODO need to add that Kings can't move into check

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
     * Returns a list of all possible moves the King can make
     * @param board The board context being looked at
     * @return An ArrayList<Move> of possible moves
     * @throws Exception If the function tries to access a non-existent square
     */
    public ArrayList<Move> getAllMoves(Board board) throws Exception {

        ArrayList<Move> allMoves = new ArrayList<Move>();
        int xAxis = this.getSquare().getxAxis();
        int yAxis = this.getSquare().getyAxis();
        int tempX;
        int tempY;

        // 8 Moves Possible, for X and Y could have -1, +0, or +1 (not +0,+0 though)

        for (int xChange = -1; xChange < 2; xChange++) {
            for (int yChange = -1; yChange < 2; yChange++) {
                if (xChange == 0 && yChange == 0) break; // Staying in place isn't a valid move
                tempX = xAxis + xChange;
                tempY = yAxis + yChange;
                if (areCoordsInBoundsHelper(tempX, tempY)) {
                    if (canMove(board.getSquare(tempX, tempY), board)) {
                        allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(tempX, tempY)));
                    }
                }
            }
        }

        return allMoves;
    }

    /**
     * Deep copies this piece
     * @return the deep copy
     */
    @Override
    public King duplicatePiece() {
        return new King(this.getTeam(), this.isLive(), null, this.getHasMoved());
    }

}
