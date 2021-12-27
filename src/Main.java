public class Main {

    public static void main(String[] args) {

        Board board = Board.getBoard();

        Player whitePlayer = new Player(false);
        Player blackPlayer = new Player(true);

        while (board.kingsAlive()) {

            System.out.println("White Turn");
            board.printWhitesBoard();
            whitePlayer.takeTurn();
            System.out.println("Black Turn");
            board.printBlacksBoard();
            blackPlayer.takeTurn();

        }

        board.printWhitesBoard();
        System.out.println("Game is over.");

    }

}
