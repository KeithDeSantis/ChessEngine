public class Main {

    public static void main(String[] args) {

        Board board = Board.getBoard();

        try {
            ((Pawn) board.getSquare(1, 6).getPiece()).move(board.getGameBoard()[1][4]);
        } catch (Exception e) {
            System.out.println("Illegal move detected \n");
        }

        try {
            ((Pawn) board.getSquare(1, 4).getPiece()).move(board.getGameBoard()[1][3]);
        } catch (Exception e) {
            System.out.println("Illegal move detected \n");
        }

        try {
            ((Pawn) board.getSquare(1, 3).getPiece()).move(board.getGameBoard()[1][2]);
        } catch (Exception e) {
            System.out.println("Illegal move detected \n");
        }

        System.out.println(board);

        Player p = new Player(true);
        p.takeTurn();

        System.out.println(board);

    }

}
