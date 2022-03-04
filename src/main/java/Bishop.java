package main.java;

public class Bishop extends AbsPiece {

    public Bishop(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("main.java.Bishop");

    }
    public Bishop(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("main.java.Bishop");
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
            path.generateDiagonal(dest);
        } catch (Exception e) { return false; }

        return path.getIsClear();

    }

    /**
     * main.java.Move the piece to the given destination (if legal)
     * @param dest the destination square
     * @return The square that the piece ends on after a successful move
     * @throws Exception If the destination is illegal to move to
     * throw an "Invalid main.java.Move Exception"
     */
    public Square move(Square dest) throws Exception{

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null); // Remove from initial position

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this); // Place in new position

        this.setSquare(dest); // Update the piece

        return dest;

    }

    /**
     * Deep copies this piece
     * @return the deep copy
     */
    @Override
    public Bishop duplicatePiece() {
        return new Bishop(this.getTeam(), this.isLive(), this.getSquare(), this.getHasMoved());
    }

}
