public class Bishop extends AbsPiece implements IPiece {

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

    public boolean canMove(Square dest) {

        if (isSameTeam(dest.getPiece())) return false;

        Path path = new Path(this.getSquare());

        try {
            path.generateDiagonal(dest);
        } catch (Exception e) { return false; }

        return path.getIsClear();

    }

    public Square move(Square dest) throws Exception{

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null); // Remove from initial position

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this); // Place in new position

        this.setSquare(dest); // Update the piece

        return dest;

    }

}
