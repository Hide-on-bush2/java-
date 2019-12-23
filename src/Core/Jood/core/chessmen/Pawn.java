package Core.Jood.core.chessmen;

import java.util.List;
import java.util.ArrayList;

import Core.Jood.core.Chessboard;
import Core.Jood.core.Chessman;
import Core.Jood.core.Position;
import Core.Jood.core.Chessboard.Color;


public class Pawn extends Chessman {
	private boolean passByPawn = false;
    
    @Override
    public boolean canGo(Position to) {
    	Position cur = super.getPosition();
		int col = cur.getCol();
		int row = cur.getRow();
        if(to.col > to.COL || to.col < 1 || to.row > to.ROW || to.row < 1 || (to.col == col && to.row == row)){return false;}
        if(super.getColor() == Chessboard.Color.White) {
        	if(super.getPosition().getRow() == 7) {
        		if((row - to.row == 1 || row - to.row == 2) && Math.abs(to.col - col) <= 1) {
        			return true;
        		}
        		else {
        			return false;
        		}
        	}
        	if(to.row == row - 1 && Math.abs(to.col - col) <= 1) {
        		return true;
        	}
        	else {
        		return false;
        	}
        }
        else {
        	if(super.getPosition().getRow() == 2) {
        		if((to.row - row == 1 || to.row - row == 2) && Math.abs(to.col - col) <= 1) {
        			return true;
        		}
        		else {
        			return false;
        		}
        	}
        	if(to.row == row + 1 && Math.abs(to.col - col) <= 1) {
        		return true;
        	}
        	else {
        		return false;
        	}
        }
    }

    @Override
    public Status status() {
        return Status.Pawn;
    }

    @Override
    public List<Position> path(Position to) {
        List<Position> thePath = new ArrayList<Position>();
        if(!canGo(to)){return thePath;}
        return thePath;
	}
    
    public boolean isPassbyPawn() {
    	return this.passByPawn;
    }
    
    public void setPassbyPawn(boolean condition) {
    	this.passByPawn = condition;
    }
    
}