import java.util.ArrayList;

public class Bishop extends AbsPiece {

    public Bishop(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("Bishop");

    }
    public Bishop(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("Bishop");
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
            path.generateDiagonal(dest, board);
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
    public Square move(Square dest) throws Exception{

        if (!canMove(dest, Board.getBoard())) throw new Exception("Invalid move exception");

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null); // Remove from initial position

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this); // Place in new position

        this.setSquare(dest); // Update the piece

        return dest;

    }

    /**
     * Returns a list of all possible moves the bishop can make
     * @param board The board context being looked at
     * @return An ArrayList<Move> of possible moves
     * @throws Exception If the function tries to access a non-existent square
     */
    public ArrayList<Move> getAllMoves(Board board) throws Exception {

        // Test in each direction up to the end of the board
        /*
        Direction variable encodes the direction of travel
        1: Up Right = X + 1, Y - 1
        2: Up Left = X - 1, Y - 1
        3: Down Right = X + 1, Y + 1
        4: Down Left = X - 1, Y + 1
         */

        ArrayList<Move> allMoves = new ArrayList<Move>();

        for (int dir = 1; dir < 5; dir++) { // For each direction

            int xAxis = this.getSquare().getxAxis();
            int yAxis = this.getSquare().getyAxis();

            switch(dir){
                case 1: // Up Right
                    while (areCoordsInBoundsHelper(xAxis, yAxis)) {
                        if (canMove(board.getSquare(xAxis, yAxis), board)) { // If its a valid move
                            allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis, yAxis))); // Add move to list
                        }
                        xAxis += 1; // Update coords in this direction
                        yAxis -= 1;
                    }
                    break;
                case 2: // Up Left
                    while (areCoordsInBoundsHelper(xAxis, yAxis)) {
                        if (canMove(board.getSquare(xAxis, yAxis), board)) { // If its a valid move
                            allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis, yAxis))); // Add move to list
                        }
                        xAxis -= 1; // Update coords in this direction
                        yAxis -= 1;
                    }
                    break;
                case 3: // Down Right
                    while (areCoordsInBoundsHelper(xAxis, yAxis)) {
                        if (canMove(board.getSquare(xAxis, yAxis), board)) { // If its a valid move
                            allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis, yAxis))); // Add move to list
                        }
                        xAxis += 1; // Update coords in this direction
                        yAxis += 1;
                    }
                    break;
                case 4: // Down Left
                    while (areCoordsInBoundsHelper(xAxis, yAxis)) {
                        if (canMove(board.getSquare(xAxis, yAxis), board)) { // If its a valid move
                            allMoves.add(new Move(this.getTeam(), this, this.getSquare(), board.getSquare(xAxis, yAxis))); // Add move to list
                        }
                        xAxis -= 1; // Update coords in this direction
                        yAxis += 1;
                    }
                    break;
                default:
                    return null; //Shouldn't get here
            }
        }

        return allMoves;

    }

    /**
     * Deep copies this piece
     * @return the deep copy
     */
    @Override
    public Bishop duplicatePiece() {
        return new Bishop(this.getTeam(), this.isLive(), null, this.getHasMoved());
    }

}
