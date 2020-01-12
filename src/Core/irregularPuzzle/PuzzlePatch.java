package Core.irregularPuzzle;

public class PuzzlePatch {
	private int r,c,cnt;
	private boolean d[][];
	PuzzlePatch(int _r,int _c){
		r=_r;
		c=_c;
		cnt=0;
		d=new boolean[r+1][c+1];
		for(int i=1;i<=r;i++)
			for(int j=1;j<=c;j++)
				d[i][j]=false;
	}
	public int getRow() {
		return r;
	}
	public int getColumn() {
		return c;
	}
	public boolean getGrid(int x,int y) {
		return d[x][y];
	}
	public void setGrid(int x,int y,boolean b) {
		if (b==d[x][y]) return;
		d[x][y]=b;
		if (b) cnt++;else cnt--;
	}
	public int getCnt() {
		return cnt;
	}
}
