package main.java;

public class Square {

    private AbsPiece piece;
    private int xAxis;
    private int yAxis;
    private boolean isUnderAttack;

    public Square(AbsPiece piece, int xAxis, int yAxis) {
        this.piece = piece;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.isUnderAttack = false;
    }

    public Square(int xAxis, int yAxis) {
        this.piece = null;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.isUnderAttack = false;
    }

    public AbsPiece getPiece() {
        return piece;
    }

    public void setPiece(AbsPiece piece) {
        this.piece = piece;
    }

    public int getxAxis() {
        return xAxis;
    }

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public boolean getIsUnderAttack() {
        return isUnderAttack;
    }

    public void setIsUnderAttack(boolean underAttack) {
        isUnderAttack = underAttack;
    }

    public String typeOfOccupant() {

        if (this.piece == null) return "Empty";

        return this.getPiece().getType();

    }
}
