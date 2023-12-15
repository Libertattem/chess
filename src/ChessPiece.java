import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public abstract class ChessPiece {
    protected String color;
    protected Boolean check = true;
    protected String symbol;

    public ChessPiece(String color, String symbol) {
        this.color = color;
        this.symbol = symbol;
    }

    public String getColor() {
        return this.color;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public boolean getCheck() {
        return this.check;
    }

    public void turnCheck () {
        if (check) {check = false;}
    }
    @Nullable
    public Boolean enemyIsHere(ChessPiece chessPiece) {
        if (chessPiece == null){
            return null;
        } else {
            return !Objects.equals(chessPiece.getColor(), getColor());
        }
    }

    public abstract Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    public Boolean isUnderAttack(ChessBoard chessBoard, int line, int column){
        int i = 0;
        int k = 0;
        boolean attackable = false;

        while (i <= 7) {
            while (k <= 7) {
                ChessPiece piece = chessBoard.board[i][k];
                if (piece != null) {
                    if (!Objects.equals(piece.getColor(), getColor())
                            && piece.canMoveToPosition(chessBoard, i, k, line, column)) {
                        attackable = true;
                        break;
                    }
                }
                k++;
            }
            k = 0;
            i++;
        }
        return attackable;
    }

}
