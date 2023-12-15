import java.util.Objects;

public class Queen extends ChessPiece implements CheckWay{
    static String SYMBOL = "Q";
    public Queen(String color) {
        super(color, SYMBOL);
    }

    @Override
    public Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        ChessPiece endPos = chessBoard.board[toLine][toColumn];

        int moveX = Math.abs(column - toColumn);
        int moveY = Math.abs(line - toLine);
        Boolean isEnemy = enemyIsHere(endPos);

        //Rook and Bishop Combination
        boolean goodEndPosition = ((moveX == moveY && moveX + moveY != 0)
                || ((moveY == 0 || moveX == 0) && moveX + moveY != 0))
                && Objects.requireNonNullElse(isEnemy, true);

        boolean wayIsOpen = false;

        if (goodEndPosition) { wayIsOpen = checkWay(this, chessBoard, line, column, toLine, toColumn); }

        return goodEndPosition && wayIsOpen;

    }
}
