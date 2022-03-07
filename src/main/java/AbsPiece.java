package main.java;

import java.util.ArrayList;

public abstract class AbsPiece implements IPiece {

    // boolean  where 0 is white, 1 is black team
    private boolean team;
    // boolean where 0 if not in play, 1 if in play
    private boolean isLive;
    private Square square;
    private boolean hasMoved;
    private String type;

    /**
     * Check if another piece is on the same team
     * @param otherPiece The other piece
     * @return True if the other piece is on the team
     */
    public boolean isSameTeam(AbsPiece otherPiece) {

        if (otherPiece == null) return false;

        if (this.team) return otherPiece.getTeam();

        else return !otherPiece.getTeam();

    }

    /**
     * Creates a deep copy of this piece
     * MUST BE OVERRIDEN IN EACH SUBCLASS
     * @return the deepcopy
     */
    public AbsPiece duplicatePiece() {
        return new Pawn(false,false,null,false);
    }

    public boolean getTeam() {
        return this.team;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean isTeam() {
        return team;
    }

    public boolean isLive() {
        return isLive;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * A helper that determines if given coords are within the bounds
     * @param xAxis the xAxis
     * @param yAxis the yAxis
     * @return True if they are in bounds
     */
    public boolean areCoordsInBoundsHelper(int xAxis, int yAxis) {
        return (xAxis >= 0 && xAxis <= 7 && yAxis >= 0 && yAxis <= 7);
    }
}
