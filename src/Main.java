public class Main {

    public static void main(String[] args) {

        Board board = Board.getBoard();

        Player whitePlayer = new Player(false);
        Player blackPlayer = new Player(true);
        MoveHistory mvHistory = MoveHistory.getMoveHistory();

        while (board.kingsAlive()) {

            System.out.println("White Turn");
            board.printWhitesBoard();
            while(!whitePlayer.takeTurn()){
                System.out.println("White Turn");
                board.printWhitesBoard();
            }
            if (!board.kingsAlive()) break;
            System.out.println("Black Turn");
            board.printBlacksBoard();
            while(!blackPlayer.takeTurn()) {
                System.out.println("Black Turn");
                board.printBlacksBoard();
            }

        }

        board.printWhitesBoard();
        System.out.println("The game is over, " + board.checkForWinner() + " has won!");

    }

}
