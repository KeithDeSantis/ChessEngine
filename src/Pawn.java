public class Pawn extends AbsPiece implements IPiece {

    public Pawn(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("Pawn");

    }
    public Pawn(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("Pawn");
    }

    public boolean canMove(Square dest) {

        if (isSameTeam(dest.getPiece())) return false; //TODO added after, can remove all the same team checking

        Path path = new Path(this.getSquare());

        try {
            path.generatePawnPath(dest);
        } catch (Exception e) { return false; }

        return path.getIsClear();

    }

    public Square move(Square dest) throws Exception {

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        // update the board singleton here
        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null);

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this);

        this.setSquare(dest);

        if (dest.getyAxis() == 0 && !this.getTeam()) {// pawn can change to new piece
            // will entail deleting this piece and replacing the square's piece with a new one
            }

        if (dest.getyAxis() == 8 && this.getTeam()) {// pawn can change to new piece
            // will entail deleting this piece and replacing the square's piece with a new one
            }

        return dest;

    } //TODO add En Passant and Pawn Switching



}
