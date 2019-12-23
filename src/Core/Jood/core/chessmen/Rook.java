package Core.Jood.core.chessmen;

import java.util.List;
import java.util.ArrayList;

import Core.Jood.core.Chessboard;
import Core.Jood.core.Chessman;
import Core.Jood.core.Position;
public class Rook extends Chessman {
	private boolean hasMoved = false;
	
	
    @Override
    public boolean canGo(Position to) {
    	Position cur = super.getPosition();
		int col = cur.getCol();
		int row = cur.getRow();
        if(to.col > to.COL || to.col < 1 || to.row > to.ROW || to.row < 1 || (to.col == col && to.row == row)){return false;}
        return to.col == col || to.row == row;
    }

    @Override
    public Status status() {
        return Status.Rook;
    }

    @Override
    public List<Position> path(Position to) {
    	Position cur = super.getPosition();
		int col = cur.getCol();
		int row = cur.getRow();
        List<Position> thePath = new ArrayList<Position>();
        if(!canGo(to)){return thePath;}
        if(to.col == col){
            if(to.row > row){
                for(int i = row + 1;i < to.row;i++){thePath.add(new Position(i, to.col));}
            }
            else{
                for(int i = row - 1;i > to.row;i--){thePath.add(new Position(i, to.col));}
            }
        }
        else{
            if(to.col > col){
                for(int i = col + 1;i < to.col;i++){thePath.add(new Position(to.row, i));}
            }
            else{
                for(int i = col - 1;i > to.col;i--){thePath.add(new Position(to.row, i));}
            }
        }
		return thePath;
	}
    
    public boolean hasMoved() {
    	return this.hasMoved;
    }
    
    public void setMove(boolean condition) {
    	this.hasMoved = condition;
    }
}