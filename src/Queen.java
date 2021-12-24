public class Queen extends AbsPiece implements IPiece {

    public Queen(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("Queen");

    }
    public Queen(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("Queen");
    }

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

        if (canGoHorizontal) return hPath.getIsClear();
        if (canGoVertical) return vPath.getIsClear();
        if (canGoDiagonal) return dPath.getIsClear();

        return false;

    }

    public Square move(Square dest) throws Exception{

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null); // Remove from initial position

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this); // Place in new position

        this.setSquare(dest); // Update the piece

        return dest;

    }

}
