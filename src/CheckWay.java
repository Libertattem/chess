public interface CheckWay {
    default Boolean checkWay(ChessPiece chessPiece, ChessBoard chessBoard, int line,
                                    int column, int toLine, int toColumn){

        int moveX = toColumn - column;
        int moveY = toLine - line;

        int baseVecX = moveX != 0 ? moveX / Math.abs(moveX) : 0;
        int baseVecY = moveY != 0 ? moveY / Math.abs(moveY): 0;

        boolean wayIsOpen = true;

        while (!(line == toLine && column == toColumn)){
            line += baseVecY;
            column += baseVecX;
            Boolean isEnemy = chessPiece.enemyIsHere(chessBoard.board[line][column]);

            if (isEnemy != null && !(line == toLine && column == toColumn)){
                wayIsOpen = false;
                break;
            }
        }
        return wayIsOpen;
    }
}
