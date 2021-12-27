import java.lang.reflect.Array;
import java.lang.StringBuilder;

public class Board {

    private Square[][] gameBoard;

    private Board() {

        gameBoard = new Square[8][8];

        for (int x = 0; x < 8; x++) { // creation of white pawns

            Pawn whitePawn = new Pawn(false, true, null, false); // Square is set to null...
            gameBoard[x][6] = new Square(whitePawn, x, 6);
            whitePawn.setSquare(gameBoard[x][6]); // then updated after the square is made

        }

        for (int x = 0; x < 8; x++) { // creation of black pawns

            Pawn blackPawn = new Pawn(true, true, null, false); // Square is set to null...
            gameBoard[x][1] = new Square(blackPawn, x, 1);
            blackPawn.setSquare(gameBoard[x][1]); // then updated after the square is made

        }

        // Rooks
        Rook whiteRook1 = new Rook(false, true, null, false);
        gameBoard[0][7] = new Square(whiteRook1, 0, 7);
        whiteRook1.setSquare(gameBoard[0][7]);
        Rook whiteRook2 = new Rook(false, true, null, false);
        gameBoard[7][7] = new Square(whiteRook2, 7, 7);
        whiteRook2.setSquare(gameBoard[7][7]);
        Rook blackRook1 = new Rook(true, true, null, false);
        gameBoard[0][0] = new Square(blackRook1, 0, 0);
        blackRook1.setSquare(gameBoard[0][0]);
        Rook blackRook2 = new Rook(true, true, null, false);
        gameBoard[7][0] = new Square(blackRook2, 7, 0);
        blackRook2.setSquare(gameBoard[7][0]);

        // Knights
        Knight whiteKnight1 = new Knight(false, true, null, false);
        gameBoard[1][7] = new Square(whiteKnight1, 1, 7);
        whiteKnight1.setSquare(gameBoard[1][7]);
        Knight whiteKnight2 = new Knight(false, true, null, false);
        gameBoard[6][7] = new Square(whiteKnight2, 6, 7);
        whiteKnight2.setSquare(gameBoard[6][7]);
        Knight blackKnight1 = new Knight(true, true, null, false);
        gameBoard[1][0] = new Square(blackKnight1, 1, 0);
        blackKnight1.setSquare(gameBoard[1][0]);
        Knight blackKnight2 = new Knight(true, true, null, false);
        gameBoard[6][0] = new Square(blackKnight2, 6, 0);
        blackKnight2.setSquare(gameBoard[6][0]);

        // Bishops
        Bishop whiteBishop1 = new Bishop(false, true, null, false);
        gameBoard[2][7] = new Square(whiteBishop1, 2, 7);
        whiteBishop1.setSquare(gameBoard[2][7]);
        Bishop whiteBishop2 = new Bishop(false, true, null, false);
        gameBoard[5][7] = new Square(whiteBishop2, 5, 7);
        whiteBishop2.setSquare(gameBoard[5][7]);
        Bishop blackBishop1 = new Bishop(true, true, null, false);
        gameBoard[2][0] = new Square(blackBishop1, 2, 0);
        blackBishop1.setSquare(gameBoard[2][0]);
        Bishop blackBishop2 = new Bishop(true, true, null, false);
        gameBoard[5][0] = new Square(blackBishop2, 5, 0);
        blackBishop2.setSquare(gameBoard[5][0]);

        // Kings and Queens
        Queen whiteQueen = new Queen(false, true, null, false);
        gameBoard[3][7] = new Square(whiteQueen, 3, 7);
        whiteQueen.setSquare(gameBoard[3][7]);
        Queen blackQueen = new Queen(true, true, null, false);
        gameBoard[3][0] = new Square(blackQueen, 3, 0);
        blackQueen.setSquare(gameBoard[3][0]);
        King whiteKing = new King(false, true, null, false);
        gameBoard[4][7] = new Square(whiteKing, 4, 7);
        whiteKing.setSquare(gameBoard[4][7]);
        King blackKing = new King(true, true, null, false);
        gameBoard[4][0] = new Square(blackKing, 4, 0);
        blackKing.setSquare(gameBoard[4][0]);

        // All empty squares
        for (int j = 0; j < 8; j++) {
            for (int k = 2; k < 6; k++) {
                gameBoard[j][k] = new Square(j, k);
            }
        }

    }

    private static class BoardSingletonHelper {
        private static final Board board = new Board();
    }

    public static Board getBoard() {
        return BoardSingletonHelper.board;
    }

    public Square getSquare(int xAxis, int yAxis) throws Exception {

        if (xAxis < 0 || xAxis > 7 || yAxis < 0 || yAxis > 7) throw new Exception("Invalid Square");

        return this.gameBoard[xAxis][yAxis];

    }

    public Square editSquare(int xAxis, int yAxis, AbsPiece piece) {

        this.gameBoard[xAxis][yAxis].setPiece(piece);

        return this.gameBoard[xAxis][yAxis];

    }

    public Square[][] getGameBoard() {
        return this.gameBoard;
    }

    @Override
    public String toString() {

        StringBuilder strBld = new StringBuilder();

        for (int y = 7; y >= 0; y--) {
            strBld.append(y + " ");
            for (int x = 0; x < 8; x++) {
                switch (gameBoard[x][y].typeOfOccupant()) {

                    case "Pawn":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BP");
                        else strBld.append("WP");
                        break;
                    case "Rook":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BR");
                        else strBld.append("WR");
                        break;
                    case "Knight":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BN");
                        else strBld.append("WN");
                        break;
                    case "Bishop":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BB");
                        else strBld.append("WB");
                        break;
                    case "Queen":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BQ");
                        else strBld.append("WQ");
                        break;
                    case "King":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BK");
                        else strBld.append("WK");
                        break;
                    case "Empty":
                        strBld.append("--");
                        break;
                    default:
                        return "Error, type of square occupancy not found on square " + x + " " + y;

                }
                strBld.append(" ");
            }

            strBld.append("\n");

        }
        strBld.append("  0  1  2  3  4  5  6  7");
        return strBld.toString();
    } // Old version of printing which was white forward facing

    public boolean kingsAlive() {

        int numWhiteKings = 0;
        int numBlackKings = 0;

        for (int x = 0; x < 8; x ++) {
            for (int y = 0; y < 8; y ++) {

                if (!(gameBoard[x][y].getPiece() == null)) {
                    if (gameBoard[x][y].getPiece().getType() == "King") {

                        if (gameBoard[x][y].getPiece().getTeam()) numBlackKings++;
                        else numWhiteKings++;

                    }
                }

            }
        }

        if (numBlackKings > 1 || numWhiteKings > 1) {
            System.out.println("Multiple Kings of one color detected.");
            return false;
        }

        if (numBlackKings == 1 && numWhiteKings == 1) return true;

        return false;
    }

    public void printWhitesBoard() {

            StringBuilder strBld = new StringBuilder();

            for (int y = 0; y < 8; y++) {
                strBld.append(y + " ");
                for (int x = 0; x < 8; x++) {
                    switch (gameBoard[x][y].typeOfOccupant()) {

                        case "Pawn":
                            if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BP");
                            else strBld.append("WP");
                            break;
                        case "Rook":
                            if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BR");
                            else strBld.append("WR");
                            break;
                        case "Knight":
                            if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BN");
                            else strBld.append("WN");
                            break;
                        case "Bishop":
                            if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BB");
                            else strBld.append("WB");
                            break;
                        case "Queen":
                            if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BQ");
                            else strBld.append("WQ");
                            break;
                        case "King":
                            if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BK");
                            else strBld.append("WK");
                            break;
                        case "Empty":
                            strBld.append("--");
                            break;
                        default:
                            strBld.append("%%");
                            break;

                    }
                    strBld.append(" ");
                }

                strBld.append("\n");

            }
            strBld.append("  0  1  2  3  4  5  6  7");

            System.out.println(strBld.toString());
        }

    public void printBlacksBoard() {

        StringBuilder strBld = new StringBuilder();

        for (int y = 7; y >= 0; y--) {
            strBld.append(y + " ");
            for (int x = 0; x < 8; x++) {
                switch (gameBoard[x][y].typeOfOccupant()) {

                    case "Pawn":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BP");
                        else strBld.append("WP");
                        break;
                    case "Rook":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BR");
                        else strBld.append("WR");
                        break;
                    case "Knight":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BN");
                        else strBld.append("WN");
                        break;
                    case "Bishop":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BB");
                        else strBld.append("WB");
                        break;
                    case "Queen":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BQ");
                        else strBld.append("WQ");
                        break;
                    case "King":
                        if (gameBoard[x][y].getPiece().getTeam()) strBld.append("BK");
                        else strBld.append("WK");
                        break;
                    case "Empty":
                        strBld.append("--");
                        break;
                    default:
                        strBld.append("%%");
                        break;

                }
                strBld.append(" ");
            }

            strBld.append("\n");

        }
        strBld.append("  0  1  2  3  4  5  6  7");

        System.out.println(strBld.toString());
    }
}
