import java.util.Objects;

public class Horse extends ChessPiece{
    static String SYMBOL = "H";

    public Horse(String color) {
        super(color, SYMBOL);
    }

    public Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        ChessPiece endPos = chessBoard.board[toLine][toColumn];

        int moveX = Math.abs(toColumn - column);
        int moveY = Math.abs(toLine - line);

        return (moveY + moveX == 3 && (line != toLine || column != toColumn))
                && Objects.requireNonNullElse(enemyIsHere(endPos), true);
    }
}
