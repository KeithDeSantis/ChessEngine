package main.java;

public class Rook extends AbsPiece {

    public Rook(boolean team, boolean isLive, Square square, boolean hasMoved) {
        this.setTeam(team);
        this.setLive(isLive);
        this.setSquare(square);
        this.setHasMoved(hasMoved);
        this.setType("main.java.Rook");

    }
    public Rook(boolean team, Square square) {
        this.setTeam(team);
        this.setSquare(square);
        this.setType("main.java.Rook");
    }

    /**
     * Checks if a move to a destination is legal.
     * @param dest The square the piece is trying to move to.
     * @return True if the move is legal
     */
    public boolean canMove(Square dest) {

        if (isSameTeam(dest.getPiece())) return false;

        Path hPath = new Path(this.getSquare());
        Path vPath = new Path(this.getSquare());

        boolean canGoHorizontal = true;
        boolean canGoVertical = true;

        try {
            hPath.generateHorizontal(dest);
        } catch (Exception e) { canGoHorizontal = false; }
        try {
            vPath.generateVertical(dest);
        } catch (Exception e) { canGoVertical = false; }

        if (canGoHorizontal) return hPath.getIsClear();
        if (canGoVertical) return vPath.getIsClear();

        return false;

    }

    /**
     * main.java.Move the piece to the given destination (if legal)
     * @param dest the destination square
     * @return The square that the piece ends on after a successful move
     * @throws Exception If the destination is illegal to move to
     * throw an "Invalid main.java.Move Exception"
     */
    public Square move(Square dest) throws Exception{

        if (!canMove(dest)) throw new Exception("Invalid move exception");

        Board.getBoard().editSquare(this.getSquare().getxAxis(), this.getSquare().getyAxis(), null); // Remove from initial position

        Board.getBoard().editSquare(dest.getxAxis(), dest.getyAxis(), this); // Place in new position

        this.setSquare(dest); // Update the piece

        return dest;

    }

    public Square castle() throws Exception {

        Board board = Board.getBoard();
        Square kingSpace;

        if (!this.getHasMoved()) {

            if (!this.getTeam()) { // White rook

                kingSpace = board.getSquare(4,7);

                AbsPiece wKing = kingSpace.getPiece();

                Path path = new Path(this.getSquare());

                try {
                    path.generateHorizontal(kingSpace);
                } catch (Exception e) { return this.getSquare(); }

                if (!kingSpace.getPiece().getHasMoved() && board.getSquare(4, 7).getPiece().getType().equals("main.java.King")) { // main.java.King hasn't moved and is in right place

                    if (this.getSquare().getxAxis() == 7) { // main.java.Rook on 7 x coord

                        if (path.getIsClear()) { // if there are no piece between

                            board.getSquare(5,7).setPiece(this); // the actual swap occurs
                            wKing.setSquare(board.getSquare(6,7));
                            this.setSquare(board.getSquare(5,7));

                        }
                    }

                    else { // main.java.Rook on 0 x coord

                        if (path.getIsClear()) { // if there are no piece between

                            board.getSquare(3,7).setPiece(this); // the actual swap occurs
                            wKing.setSquare(board.getSquare(2,7));
                            this.setSquare(board.getSquare(3,7));

                        }

                    }

                }

            }
            else { // Black rook

                kingSpace = board.getSquare(4,0);

                AbsPiece wKing = kingSpace.getPiece();

                Path path = new Path(this.getSquare());

                try {
                    path.generateHorizontal(kingSpace);
                } catch (Exception e) { return this.getSquare(); }

                if (!kingSpace.getPiece().getHasMoved() && kingSpace.getPiece().getType().equals("main.java.King")) { // main.java.King hasn't moved and is in right place

                    if (this.getSquare().getxAxis() == 7) { // main.java.Rook on 7 x coord

                        if (path.getIsClear()) { // if there are no piece between

                            board.getSquare(5,0).setPiece(this); // the actual swap occurs
                            wKing.setSquare(board.getSquare(6,0));
                            this.setSquare(board.getSquare(5,0));

                        }
                    }

                    else { // main.java.Rook on 0 x coord

                        if (path.getIsClear()) { // if there are no piece between

                            board.getSquare(3,0).setPiece(this); // the actual swap occurs
                            wKing.setSquare(board.getSquare(2,0));
                            this.setSquare(board.getSquare(3,0));

                        }

                    }

                }

            }
        }

        return this.getSquare();
    } //TODO make castling cleaner

}
