import java.util.Scanner;

public class Player {

    // 0 is white, 1 is black
    public boolean team;

    public Player(boolean team) {
        this.team = team;
    }

    /**
     * Returns true if a valid turn was completed, and records the move in MoveHistory
     * @return True if a legal move was made.
     */
    public boolean takeTurn() {

        Board board = Board.getBoard();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Identify first, the piece you want to move and then, where you was it moved to using coordinates and a space between the inputs.\nExample input: 1,6 1,5");


        boolean wrongInput = true;
        int[] startCoords = new int[2];
        int[] endCoords = new int[2];

        while(wrongInput) {

            try {
                String move = scanner.nextLine();
                String[] start = move.split(" ")[0].split(",");
                String[] end = move.split(" ")[1].split(",");
                startCoords[0] = Integer.parseInt(start[0]);
                startCoords[1] = Integer.parseInt(start[1]);
                endCoords[0] = Integer.parseInt(end[0]);
                endCoords[1] = Integer.parseInt(end[1]);
                wrongInput = false;
            } catch (Exception e) {
                System.out.println("Invalid input. Please use the form: 'x1,y1 x2,y2' with x1,y1 being the starting piece and x2,y2 being the destination.");
            }
        }

        try {

            if (board.getSquare(startCoords[0], startCoords[1]).getPiece().getTeam() != this.team) {
                System.out.println("Illegal move detected.");
                return false;
            }

            board.getSquare(startCoords[0], startCoords[1]).getPiece().move(board.getGameBoard()[endCoords[0]][endCoords[1]]);

            Move finishedMove = new Move(this.team, board.getSquare(endCoords[0], endCoords[1]).getPiece(), board.getSquare(startCoords[0], startCoords[1]), board.getSquare(endCoords[0], endCoords[1]));
            MoveHistory.getMoveHistory().addToHistory(finishedMove);
            return true;

        } catch (Exception e) {
            System.out.println("Illegal move detected.");
            return false;
        }

    }

}
