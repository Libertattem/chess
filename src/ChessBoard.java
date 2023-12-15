import java.util.Map;
import java.util.Objects;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer;
    protected Map<String, Map<String, int[]>> rooksPositions = Map.of(
            "Black", Map.of("0", new int[]{7, 0}, "7", new int[]{7, 7}),
            "White", Map.of("0", new int[]{0, 0}, "7", new int[]{0, 7}));

    protected Map<String, Map<String, int[]>> castlingRooksPositions = Map.of(
            "Black", Map.of("0", new int[]{7, 3}, "7", new int[]{7, 5}),
            "White", Map.of("0", new int[]{0, 3}, "7", new int[]{0, 5}));

    protected Map<String, Map<String, int[]>> castlingKingsPositions = Map.of(
            "Black", Map.of("0", new int[]{7, 2}, "7", new int[]{7, 6}),
            "White", Map.of("0", new int[]{0, 2}, "7", new int[]{0, 6}));

    public int[] getKingPos(String color) {
        int[] kingPos = new int[2];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getSymbol().equals("K") && board[i][j].getColor().equals(color)) {
                        kingPos = new int[]{i, j};
                        break;
                    }
                }
            }
        }
        return kingPos;
    }

    public void nextPlayer() {
        this.nowPlayer = nowPlayerColor().equals("White") ? "Black" : "White";
    }

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean checkKing (String color) {
        int[] kingPos = getKingPos(color);
        return board[kingPos[0]][kingPos[1]].isUnderAttack(this, kingPos[0], kingPos[1]);
    }

    boolean pieceIsReady(int[] pos, String symbol) {
        ChessPiece chessPiece = board[pos[0]][pos[1]];
        if (chessPiece != null) {
            boolean check = chessPiece.getCheck();
            boolean checkSymbol = Objects.equals(chessPiece.getSymbol(), symbol);
            return check && checkSymbol;
        } else return false;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {

                board[startLine][startColumn].turnCheck();

                ChessPiece startPosPiece = board[startLine][startColumn];
                ChessPiece endPosPiece = board[endLine][endColumn];

                board[endLine][endColumn] = board[startLine][startColumn]; // if piece can move, we moved a piece
                board[startLine][startColumn] = null; // set null to previous cell

                if (checkKing(nowPlayer)) {
                    board[startLine][startColumn] = startPosPiece; // if piece can move, we moved a piece
                    board[endLine][endColumn] = endPosPiece; // set null to previous cell
                    System.out.println("Королю поставлен Шах!");
                    return false;
                } else {
                    nextPlayer();
                    return true;
                }
            } else return false;
        } else return false;
    }

    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean castling (String type){
        int[] rookPos = rooksPositions.get(nowPlayer).get(type);
        int[] kingPos = getKingPos(nowPlayer);

        if (pieceIsReady(rookPos, "R") && pieceIsReady(kingPos, "K")){
            Rook rook = (Rook) board[rookPos[0]][rookPos[1]];
            if (rook.checkWay(rook,this, rookPos[0], rookPos[1], kingPos[0], kingPos[1])) {
                int[] newKingPos = castlingKingsPositions.get(nowPlayer).get(type);
                int[] newRookPos = castlingRooksPositions.get(nowPlayer).get(type);

                board[rookPos[0]][rookPos[1]] = null;
                board[kingPos[0]][kingPos[1]] = null;

                board[newRookPos[0]][newRookPos[1]] = new Rook(nowPlayer);
                board[newKingPos[0]][newKingPos[1]] = new King(nowPlayer);

                if (checkKing(nowPlayer)) {
                    board[rookPos[0]][rookPos[1]] = new Rook(nowPlayer);
                    board[kingPos[0]][kingPos[1]] = new King(nowPlayer);

                    board[newRookPos[0]][newRookPos[1]] = null;
                    board[newKingPos[0]][newKingPos[1]] = null;
                    System.out.println("Королю поставлен Шах!");
                    return false;

                } else {
                    nextPlayer();
                    return true;
                }
            } else return false;
        } else return false;
    }
}
