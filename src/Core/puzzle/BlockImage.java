package Core.puzzle;
import java.awt.*;
import javax.swing.*;

class BlockImage extends JButton{
	private static int width;
	private static int height;
	private int id=0;
	BlockImage(Icon icon,int _id,int _w,int _h){
		setIcon(icon);
		id=_id;
		setSize(_w,_h);
	}
	void move(int di) {
		Rectangle rec=getBounds();
		if (di==-1) {//left
			setLocation(rec.x-width,rec.y);
		}
		if (di==1) {//right
			setLocation(rec.x+width,rec.y);
		}
		if (di<-1) {//up
			setLocation(rec.x,rec.y+height);
		}
		if (di>1) {//down
			setLocation(rec.x,rec.y-height);
		}
	}
	public int getID() {
		return id;
	}
	public int getX() {
		return getBounds().x;
	}
	public int getY() {
		return getBounds().y;
	}
}
