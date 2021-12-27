import java.util.Scanner;

public class Player {

    // 0 is white, 1 is black
    public boolean team;

    public Player(boolean team) {
        this.team = team;
    }

    public Move takeTurn() { //TODO Add better input system and input string checking/parsing

        Board board = Board.getBoard();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Identify first, the piece you want to move and then, where you was it moved to using coordinates and a space between the inputs.\nExample input: 1,6 1,5");

        String move = scanner.nextLine();
        String[] start = move.split(" ")[0].split(",");
        String[] end = move.split(" ")[1].split(",");
        int[] startCoords = new int[2];
        startCoords[0] = Integer.parseInt(start[0]);
        startCoords[1] = Integer.parseInt(start[1]);
        int[] endCoords = new int[2];
        endCoords[0] = Integer.parseInt(end[0]);
        endCoords[1] = Integer.parseInt(end[1]);

        try {

            if (board.getSquare(startCoords[0], startCoords[1]).getPiece().getTeam() != this.team) {
                System.out.println("Illegal move detected.");
                return null;
            }

            ((IPiece) board.getSquare(startCoords[0], startCoords[1]).getPiece()).move(board.getGameBoard()[endCoords[0]][endCoords[1]]);

            Move finishedMove = new Move(this.team, board.getSquare(endCoords[0], endCoords[1]).getPiece(), board.getSquare(startCoords[0], startCoords[1]), board.getSquare(endCoords[0], endCoords[1]));
            MoveHistory.getMoveHistory().addToHistory(finishedMove);
            return finishedMove;

        } catch (Exception e) { System.out.println("Illegal move detected."); }

        return null;

    }

}
