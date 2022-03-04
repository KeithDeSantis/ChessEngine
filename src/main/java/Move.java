package main.java;

public class Move {

    public boolean teamWhoMadeMove;
    public AbsPiece pieceMoved;
    public Square startingSquare;
    public Square endingSquare;

    public Move(boolean teamWhoMadeMove, AbsPiece pieceMoved, Square startingSquare, Square endingSquare) {
        this.teamWhoMadeMove = teamWhoMadeMove;
        this.pieceMoved = pieceMoved;
        this.startingSquare = startingSquare;
        this.endingSquare = endingSquare;
    }

    public int getStartX() {
        return this.startingSquare.getxAxis();
    }

    public int getStartY() {
        return this.startingSquare.getyAxis();
    }

    public int getEndX() {
        return this.endingSquare.getxAxis();
    }

    public int getEndY() {
        return this.endingSquare.getyAxis();
    }

}
