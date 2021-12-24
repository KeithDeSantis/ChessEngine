public interface IPiece {

    /**
     * Returns true if the piece can move to the given square
     * @return
     */
    public boolean canMove(Square dest);

    /**
     * Moves the piece to the given square
     * @param dest the destination square
     * @return the destination square
     * @throws Exception in case the move is invalid
     */
    public Square move(Square dest) throws Exception;

}
