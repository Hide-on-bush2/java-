package Core.Jood.core;

import java.util.HashMap;
import Core.Jood.core.chessmen.*;
import java.util.List;
import java.util.Map;

public abstract class Chessman {
    public enum Status {
        King, Queen, Bishop, Rook, Knight, Pawn,
    }

    private Chessboard.Color _color = null;
    public Chessboard.Color getColor(){
        return _color;
    }
    public void setColor(Chessboard.Color color){
        _color = color;
    }
    /**
     * Create chessman using status and color
     * 
     * @param status status of chessman
     * @param color  color of chessman
     * @return {@link Chessman}
     */
    public static final Chessman create(Chessman.Status status, Chessboard.Color color) {
        if(status == Status.Bishop){
            Bishop tempBishop =  new Bishop();
            tempBishop.setColor(color);
            return tempBishop;
        }
        else if(status == Status.King){
            King tempKing =  new King();
            tempKing.setColor(color);
            return tempKing;
        }
        else if(status == Status.Knight){
            Knight tempKnight =  new Knight();
            tempKnight.setColor(color);
            return tempKnight;
        }
        else if(status == Status.Pawn){
            Pawn tempPawn =  new Pawn();
            tempPawn.setColor(color);
            return tempPawn;
        }
        else if(status == Status.Queen){
            Queen tempQueen =  new Queen();
            tempQueen.setColor(color);
            return tempQueen;
        }
        else if(status == Status.Rook){
            Rook tempRook =  new Rook();
            tempRook.setColor(color);
            return tempRook;
        }
        else{return null;}
    }

    public static final Chessman create(String status, Chessboard.Color color) {
        if(status == "Bishop"){
            Bishop tempBishop =  new Bishop();
            tempBishop.setColor(color);
            return tempBishop;
        }
        else if(status == "King"){
            King tempKing =  new King();
            tempKing.setColor(color);
            return tempKing;
        }
        else if(status == "Knight"){
            Knight tempKnight =  new Knight();
            tempKnight.setColor(color);
            return tempKnight;
        }
        else if(status == "Pawn"){
            Pawn tempPawn =  new Pawn();
            tempPawn.setColor(color);
            return tempPawn;
        }
        else if(status == "Queen"){
            Queen tempQueen =  new Queen();
            tempQueen.setColor(color);
            return tempQueen;
        }
        else if(status == "Rook"){
            Rook tempRook =  new Rook();
            tempRook.setColor(color);
            return tempRook;
        }
        else{return null;}
    }

    /**
     * Can chessman move ?
     * 
     * @param to destination
     * @return true if can move.
     */
    public abstract boolean canGo(Position to);

    /**
     * get path to destination.
     * if 
     * @param to destination
     * @return point of path
     */
    public abstract List<Position> path(Position to);

    /**
     * Move chessman to position.
     * Checking is not required.
     * Set it position.
     */
    public void go(Position to) {
        setPosition(to);
    } 

    /**
     * Get status of chessman
     * 
     * @return
     */
    public abstract Status status();

    public boolean sameColor(Chessman other) {
        return this._color.equals(other._color);
    }

    private Position position;
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
}
