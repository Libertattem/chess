import java.util.Objects;

public class Pawn extends ChessPiece{
    static String SYMBOL = "P";
    public Pawn(String color) {
        super(color, SYMBOL);
    }

    @Override
    public Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        ChessPiece chessPiece = chessBoard.board[toLine][toColumn];
        int moveX = Math.abs(toColumn - column);

        Boolean isEnemy = enemyIsHere(chessPiece);

        int allowStep = Objects.equals(getColor(), "White") ? 1 : -1;

        if (toLine - line  == allowStep){
            if (moveX == 0 && isEnemy == null){
                return true;
            } else return moveX == 1 && Boolean.TRUE.equals(isEnemy);
        } else return false;
    }
}
