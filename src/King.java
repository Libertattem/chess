import java.util.Objects;

public class King extends ChessPiece{
    static String SYMBOL = "K";
    public King(String color) {
        super(color, SYMBOL);
    }

    @Override
    public Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        ChessPiece endPos = chessBoard.board[toLine][toColumn];

        int moveX = Math.abs(toColumn - column);
        int moveY = Math.abs(toLine - line);

        boolean goodEndPosition = (moveX <= 1 && moveY <= 1 && moveX + moveY != 0)
                && Objects.requireNonNullElse(enemyIsHere(endPos), true);

        return goodEndPosition;
    }
}
