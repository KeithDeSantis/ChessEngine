public class Main {

    public static void main(String[] args) {

        Board board = Board.getBoard();

        Player whitePlayer = new Player(false);
        Player blackPlayer = new Player(true);

        while (board.kingsAlive()) {

            System.out.println("White Turn");
            board.printBoard();
            whitePlayer.takeTurn();
            System.out.println("Black Turn");
            board.printBoard();
            blackPlayer.takeTurn();

        }

        /*
        try {
            ((Pawn) board.getSquare(1, 6).getPiece()).move(board.getGameBoard()[1][4]);
        } catch (Exception e) {
            System.out.println("Illegal move detected \n");
        }

        Player p = new Player(true);
        p.takeTurn();

        System.out.println(board);

         */

    }

}
