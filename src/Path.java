import java.util.*;
import java.lang.Math;

/**
 * Class representing a path a piece must take from one Square to another
 */
public class Path {

    private LinkedList<Square> path;
    private boolean isClear; // True if there are no pieces between the start and end of the path

    public Path(Square start) {
        this.path = new LinkedList<Square>();
        this.path.add(start);
        this.isClear = true;
    }

    // ***NOTE*** When generating paths, we are assuming any piece in the destination square is of the opposing team,
    // since that is checked in the canMove function of AbsPiece, before any paths are generated.

    /**
     * Generates a diagonal path from the start to the given square
     * @param end The end square
     * @return The given diagonal path
     * @throws Exception If a diagonal path between the start and given end is impossible
     */
    public Path generateDiagonal(Square end, Board board) throws Exception {

        Square first = this.getPath().getFirst();
        Square last = first;

        if (first.getxAxis() == end.getxAxis() || first.getyAxis() == end.getyAxis()) throw new Exception("Invalid diagonal endpoints");

        /*

        Direction variable encodes the direction of travel

        Xs nor Ys of start and end can't be the same

        1: Up Right = X + 1, Y - 1
        2: Up Left = X - 1, Y - 1
        3: Down Right = X + 1, Y + 1
        4: Down Left = X - 1, Y + 1

         */

        int direction;

        if (first.getxAxis() < end.getxAxis()) { //Right movement
            if (first.getyAxis() < end.getyAxis()) { // Down Right
                direction = 3;
            }
            else direction = 1; // Up right
        }
        else { //Left movement
            if (first.getyAxis() < end.getyAxis()) { // Down Left
                direction = 4;
            }
            else direction = 2; // Up Left
        }

        while (!(last == end)) { // Did this because unclear if != checks memory of value

            switch (direction) {
                case 1: // Up Right
                    last = board.getSquare(last.getxAxis() + 1, last.getyAxis() - 1);
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                case 2: // Up Left
                    last = board.getSquare(last.getxAxis() - 1, last.getyAxis() - 1);
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                case 3: // Down Right
                    last = board.getSquare(last.getxAxis() + 1, last.getyAxis() + 1);
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                case 4: // Down Left
                    last = board.getSquare(last.getxAxis() - 1, last.getyAxis() + 1);
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                default:
                    throw new Exception("Unknown direction of movement");
            }

        }

        return this;

    }

    /**
     * Generates a horizontal path from the start to the given square
     * @param end The end square
     * @return The given horizontal path
     * @throws Exception If a horizontal path between the start and given end is impossible
     */
    public Path generateHorizontal(Square end, Board board) throws Exception {

        Square first = this.getPath().getFirst();
        Square last = first;

        if (first.getyAxis() != end.getyAxis()) throw new Exception("Invalid horizontal endpoints.");

        /*
        1: Move to the right (+ X)
        2: Move to the left (- X)
         */

        int direction;

        if (first.getxAxis() < end.getxAxis()) direction = 1;
        else direction = 2;

        while (!(last == end)) { // Did this because unclear if != checks memory of value

            switch (direction) {
                case 1: // Right
                    last = board.getSquare(last.getxAxis() + 1, last.getyAxis());
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                case 2: // Left
                    last = board.getSquare(last.getxAxis() - 1, last.getyAxis());
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                default:
                    throw new Exception("Unknown direction of movement");
            }

        }

        return this;

    }

    /**
     * Generates a vertical path from the start to the given square
     * @param end The end square
     * @return The given vertical path
     * @throws Exception If a vertical path between the start and given end is impossible
     */
    public Path generateVertical(Square end, Board board) throws Exception {

        Square first = this.getPath().getFirst();
        Square last = first;

        if (first.getxAxis() != end.getxAxis()) throw new Exception("Invalid vertical endpoints.");

        /*
        1: Move up (- Y)
        2: Move down (+ Y)
         */

        int direction;

        if (first.getyAxis() > end.getyAxis()) direction = 1;
        else direction = 2;

        while (!(last == end)) { // Did this because unclear if != checks memory of value

            switch (direction) {
                case 1: // Up
                    last = board.getSquare(last.getxAxis(), last.getyAxis() - 1);
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                case 2: // Down
                    last = board.getSquare(last.getxAxis(), last.getyAxis() + 1);
                    this.addToPath(last);
                    if (!(last.getPiece() == null) && !(last == end)) this.setClear(false);
                    break;
                default:
                    throw new Exception("Unknown direction of movement");
            }

        }

        return this;

    }

    public Path generatePawnPath(Square end, Board board) throws Exception {
        Square first = this.getPath().getFirst();

        /*
        Based on the team pawns have to move in a certain direction
        True: Black pawn moves in + Y
        False: White pawn moves in - Y
         */
        boolean direction = first.getPiece().getTeam();

        int startXAxis = first.getxAxis();
        int startYAxis = first.getyAxis();
        int endXAxis = end.getxAxis();
        int endYAxis = end.getyAxis();

        boolean destIsEmpty = board.getSquare(endXAxis, endYAxis).getPiece() == null;

        if (direction) { // Black Pawn
            if (startXAxis == endXAxis && startYAxis == endYAxis - 1) {
                if (!destIsEmpty) throw new Exception("Invalid pawn movement.");
                this.addToPath(end);
                return this; // BPawn moves forward
            }
            else if (startXAxis == endXAxis && startYAxis == endYAxis - 2 && !this.getPath().getFirst().getPiece().getHasMoved()) {
                if (!destIsEmpty) throw new Exception("Invalid pawn movement.");
                if (!(board.getSquare(startXAxis, startYAxis + 1).getPiece() == null)) this.isClear = false; // Only move that needs isClear checked
                this.addToPath(end);
                return this; // BPawn moves 2 forward as first move
            }
            else if (Math.abs(startXAxis - endXAxis) == 1 && startYAxis == endYAxis - 1) {
                if (destIsEmpty) throw new Exception("Invalid pawn movement.");
                this.addToPath(end);
                return this; // BPawn moves diagonally as an attack
            }
            else throw new Exception("Invalid pawn movement.");
        }
        else { // White Pawn
            if (startXAxis == endXAxis && startYAxis == endYAxis + 1) {
                if (!destIsEmpty) throw new Exception("Invalid pawn movement.");
                this.addToPath(end);
                return this; // WPawn moves forward
            }
            else if (startXAxis == endXAxis && startYAxis == endYAxis + 2 && !this.getPath().getFirst().getPiece().getHasMoved()) {
                if (!destIsEmpty) throw new Exception("Invalid pawn movement.");
                if (!(board.getSquare(startXAxis, startYAxis - 1).getPiece() == null)) this.isClear = false;
                this.addToPath(end);
                return this; // WPawn moves 2 forward as first move
            }
            else if (Math.abs(startXAxis - endXAxis) == 1 && startYAxis == endYAxis + 1) {
                if (destIsEmpty) throw new Exception("Invalid pawn movement.");
                this.addToPath(end);
                return this; // WPawn moves diagonally as an attack
            }
            else throw new Exception("Invalid pawn movement.");
        }

    }

    public Path addToPath(Square step) {

        path.add(step);
        return this;

    }

    public LinkedList<Square> getPath() {
        return path;
    }

    public void setPath(LinkedList<Square> path) {
        this.path = path;
    }

    public boolean getIsClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public int getLength() {

        int length = 0;

        ListIterator<Square> listOfSquares = this.getPath().listIterator();

        while (listOfSquares.hasNext()) {
            listOfSquares.next();
            length ++;
        }

        return length - 1; // The minus one accounts for counting the starting square in the length, which we don't want

    }
}
