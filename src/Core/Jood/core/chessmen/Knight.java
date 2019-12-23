package Core.Jood.core.chessmen;

import java.util.List;
import java.util.ArrayList;

import Core.Jood.core.Chessboard;
import Core.Jood.core.Chessman;
import Core.Jood.core.Position;

public class Knight extends Chessman {
    @Override
    public boolean canGo(Position to) {
    	Position cur = super.getPosition();
		int col = cur.getCol();
		int row = cur.getRow();
        if(to.col > to.COL || to.col < 1 || to.row > to.ROW || to.row < 1 || (to.col == col && to.row == row)){return false;}
        return (Math.abs(to.col - col) == 1 && Math.abs(to.row - row) == 2) || (Math.abs(to.col - col) == 2 && Math.abs(to.row - row) == 1);
    }

    @Override
    public Status status() {
        return Status.Knight;
    }

    @Override
    public List<Position> path(Position to) {
        List<Position> thePath = new ArrayList<Position>();
        if(!canGo(to)){return thePath;}
        return thePath;
	}    
}