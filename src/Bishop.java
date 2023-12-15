import java.util.Objects;

public class Bishop extends ChessPiece implements CheckWay{
    static String SYMBOL = "B";
    public Bishop(String color) {
        super(color, SYMBOL);
    }

    @Override
    public Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        ChessPiece endPos = chessBoard.board[toLine][toColumn];

        int moveX = Math.abs(toColumn - column);
        int moveY = Math.abs(toLine - line);

        boolean goodEndPosition = (moveX == moveY && moveX + moveY != 0
                                    && Objects.requireNonNullElse(enemyIsHere(endPos), true));

        boolean wayIsOpen = false;
        if (goodEndPosition) {
            wayIsOpen = checkWay(this, chessBoard, line, column, toLine, toColumn);
        }
        return goodEndPosition && wayIsOpen;
    }
}
