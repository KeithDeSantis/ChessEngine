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
    public boolean canMove(Square dest) {

        if (isSameTeam(dest.getPiece())) return false;

        Path hPath = new Path(this.getSquare());
        Path vPath = new Path(this.getSquare());
        Path dPath = new Path(this.getSquare());

        boolean canGoHorizontal = true;
        boolean canGoVertical = true;
        boolean canGoDiagonal = true;

        try {
            hPath.generateHorizontal(dest);
        } catch (Exception e) { canGoHorizontal = false; }
        try {
            vPath.generateVertical(dest);
        } catch (Exception e) { canGoVertical = false; }
        try {
            dPath.generateDiagonal(dest);
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

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null); // Remove from initial position

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this); // Place in new position

        this.setSquare(dest); // Update the piece

        return dest;

    }

}
