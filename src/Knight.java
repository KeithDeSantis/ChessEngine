import java.lang.Math;

public class Knight extends AbsPiece implements IPiece {

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

    public Square move(Square dest) throws Exception{

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        // update the board singleton
        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null);

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this);

        this.setSquare(dest);

        return dest;

    }

}
