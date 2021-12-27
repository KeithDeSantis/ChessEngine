import java.lang.Math;

public class Knight extends AbsPiece {

    public Knight(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("Knight");

    }
    public Knight(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("Knight");
    }

    /**
     * Checks if a move to a destination is legal.
     * @param dest The square the piece is trying to move to.
     * @return True if the move is legal
     */
    public boolean canMove(Square dest) {

        /*
        For a knight to move, its destination must be +/- 2 in the x and +/- 1 in the y or vice versa
         */

        if (isSameTeam(dest.getPiece())) return false;

        if ((Math.abs(this.getSquare().getxAxis() - dest.getxAxis()) == 2 && (Math.abs(this.getSquare().getyAxis() - dest.getyAxis()) == 1) ||
                (Math.abs(this.getSquare().getyAxis() - dest.getyAxis()) == 2 && (Math.abs(this.getSquare().getxAxis() - dest.getxAxis()) == 1)))) {
            return true;
        }

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

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        // update the board singleton
        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null);

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this);

        this.setSquare(dest);

        return dest;

    }

}
