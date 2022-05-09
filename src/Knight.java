import java.lang.Math;
import java.util.ArrayList;

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
    public boolean canMove(Square dest, Board board) {

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

        if (!canMove(dest, Board.getBoard())) throw new Exception("Invalid move exception");

        // update the board singleton
        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null);

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this);

        this.setSquare(dest);

        return dest;

    }

    /**
     * Returns a list of all possible moves the Knight can make
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

        // 8 Moves Possible, for X and Y with pairings of +/-1 and +/- 2.
        // Using a gross set of four loops cause I'm tired.
        // Basically just looping over the sign of each coord (+/-) and over the value of each coord (1 or 2)
        for (int xSign = -1; xSign < 2; xSign+=2) {
            for (int xChange = 1; xChange < 3; xChange++) {
                for (int ySign = -1; ySign < 2; ySign += 2) {
                    for (int yChange = 1; yChange < 3; yChange++) {
                        if(xChange != yChange) { //ensure the changes are different so its an L jump
                            tempX = xAxis + (xSign * xChange);
                            tempY = yAxis + (ySign * yChange);
                            if (areCoordsInBoundsHelper(tempX, tempY)) {
                                if (canMove(board.getSquare(tempX, tempY), board)) {
                                    allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(tempX, tempY)));
                                }
                            }
                        }
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
    public Knight duplicatePiece() {
        return new Knight(this.getTeam(), this.isLive(), null, this.getHasMoved());
    }


}
