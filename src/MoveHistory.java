import java.util.LinkedList;

public class MoveHistory {

    private LinkedList<Move> moves = new LinkedList<Move>();

    private static class MoveHistorySingletonHelper {
        private static final MoveHistory moveHistory = new MoveHistory();
    }

    public static MoveHistory getMoveHistory() {
        return MoveHistory.MoveHistorySingletonHelper.moveHistory;
    }

    public void addToHistory(Move move) {
        this.moves.add(move);
    }

    public LinkedList<Move> getMoves() {
        return moves;
    }

    public void setMoves(LinkedList<Move> moves) {
        this.moves = moves;
    }
}
