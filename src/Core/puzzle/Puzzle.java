package Core.puzzle;

//Æ´Í¼ÓÎÏ· ×Ô¶¯¸´Ô­ GUI
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.swing.*;

//import com.sun.tools.javac.code.Attribute.Array;

//import com.sun.tools.javac.util.List;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import javax.imageio.ImageIO;

public class Puzzle extends JPanel implements MouseListener{
	private static final long serialVersionUID=1L;
	private int row=4;
	private int col=4;
	private int width;
	private int height;
	private int num;
	private BlockImage pic[];//,blank;
	private int[] val; 
	private PuzzleFrame pf;
	Puzzle(int _r,int _c,PuzzleFrame _pf){
		pf=_pf;
		setLayout(null);
		init(_r,_c);
	}
	Puzzle(PuzzleFrame _pf){
		pf=_pf;
		setLayout(null);
		init(4,4);
	}
	int getCol() {
		return col;
	}
	public void init(int _r,int _c) {
		removeAll();
		repaint();
		row=_r;
		col=_c;
		num=0;
		BufferedImage buf=null;
		BufferedImage bufn=null;
		ImageIcon icon=null;
		try {
//			System.out.println(getClass().getResource("/image.jpg").getPath());
//			System.out.println(new File("image.jpg").getAbsolutePath());
			buf=ImageIO.read(new File("src/images/image.jpg"));
			int ImageWidth=buf.getWidth();
			int ImageHeight=buf.getHeight();
			width=ImageWidth/col;
			height=ImageHeight/row;
		}catch(Exception err) {
			err.printStackTrace();
		}
		pic=new BlockImage[row*col];
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++) {
				if ((i<row-1)||(j<col-1)){
//					System.out.println(i+" "+j);
					bufn=buf.getSubimage(width*j,height*i,width,height);
					icon=new ImageIcon(bufn);
				}else {
					icon=new ImageIcon("src/images/background.jpg");
					break;
				}
				pic[num]=new BlockImage(icon,num,width,height);
//				System.out.println(i+" "+j+" "+(width*j)+" "+(height*i));
				pic[num].setLocation(width*j,height*i);//pic[i]!!!!!!!!!!
				num++;
//				System.out.println(pic[i].getLocation().x);
//				System.out.println(pic[i].getLocation().y);
			}
		num++;//num->row*col
//		System.out.print(num);
//		System.out.println(pic[0].toString());
//		blank=pic[num-1];
		for(int i=0;i+1<num;i++) {
			add(pic[i]);
			if (i<num-1) {
				pic[i].addMouseListener(this);
			}
		}
		val=new int[num];
		for(int i=0;i<num;i++) val[i]=i;
	}
	public void sufflePic() {
		int pos=-1;
		for(int i=0;i<num;i++) if (val[i]==num-1) {
			pos=i;
			break;
		}
		int cnt[]=new int[num];
		int tt[]=new int[num];
		while (true) {
			for(int i=0;i<num;i++)
				cnt[i]=0;
			cnt[num-1]=10000000;
			for(int i=0;i<1000;i++) {
				int ttl=0;
				if (pos+col<num) {
					if (ttl==0||cnt[val[pos+col]]<cnt[val[tt[0]]]){ 
						ttl=0;
						tt[ttl++]=pos+col;
					}else
						tt[ttl++]=pos+col;
				}
				if (pos>=col) {
					if (ttl==0||cnt[val[pos-col]]<cnt[val[tt[0]]]){ 
						ttl=0;
						tt[ttl++]=pos-col;
					}else
						tt[ttl++]=pos-col;
				}
				if (pos%col>0) {
					if (ttl==0||cnt[val[pos-1]]<cnt[val[tt[0]]]){ 
						ttl=0;
						tt[ttl++]=pos-1;
					}else
						tt[ttl++]=pos-1;
				}				
				if (pos%col<col-1) {
					if (ttl==0||cnt[val[pos+1]]<cnt[val[tt[0]]]){ 
						ttl=0;
						tt[ttl++]=pos+1;
					}else
						tt[ttl++]=pos+1;
				}	
				int ty=(int)(Math.random()*ttl);
				if (ty==ttl) ty--;
				cnt[val[tt[ty]]]++;
				val[pos]=val[tt[ty]];
				val[tt[ty]]=num-1;
				pos=tt[ty];
			}
			while (pos+col<num){
				val[pos]=val[pos+col];
				val[pos+col]=num-1;
				pos=pos+col;			
			}
			while (pos%col<col-1){
				val[pos]=val[pos+1];
				val[pos+1]=num-1;
				pos=pos+1;			
			}			
			int ok=0;
			for(int i=0;i<num-1;i++) if (val[i]!=i) ok++;
			if (ok+3>num) break;
		}
		for(int i=0;i<num;i++)
			if (val[i]!=num-1) {
				pic[val[i]].setLocation(width*(i%col),height*(i/col));
			}
	}
	public void mouseClicked(MouseEvent e) {
		if (pf.getGameStatus()==1) {
			BlockImage apic=(BlockImage)e.getSource();
			int pid=apic.getID();
			int pos=-1;
			for(int i=0;i<num;i++) if (val[i]==pid){
				pos=i;
				break;
			}
			if (pos%col>0&&val[pos-1]==num-1){
				swappic(pos,-1);
				return;
			}
			if (pos%col<col-1&&val[pos+1]==num-1){
				swappic(pos,+1);
				return;
			}
			if (pos>=col&&val[pos-col]==num-1){
				swappic(pos,-col);
				return;
			}
			if (pos+col<num&&val[pos+col]==num-1){
				swappic(pos,+col);
				return;
			}			
		}
	}
	void swappic(int pos,int di){
		BlockImage apic=pic[val[pos]];
		apic.setLocation(width*((pos+di)%col),height*((pos+di)/col));
		val[pos+di]=val[pos];
		val[pos]=num-1;
		
		boolean ok=true;
		for(var i=0;i<num;i++) if (val[i]!=i){
			ok=false;
			break;
		}
		pf.addStep();
		if (ok==true) pf.win();		
	}	
	int getnum() {
		return num;
	}
	List<Integer> astar() {
		List<Integer> list=new ArrayList<Integer>();
		PQnode te=new PQnode(),tt=null;
		for(int i=0;i<num;i++) {
			te.dat[i]=val[i];
			if (val[i]==num-1) te.pos=i;
		}
		te.calCost();
		Comparator<PQnode> cmp=new Comparator<PQnode>() {
			public int compare(PQnode a,PQnode b) {
				return a.cost+a.step-b.cost-b.step;//a.cost-b.cost;
			}
		};
		PriorityQueue<PQnode> pq=new PriorityQueue<PQnode>(cmp);
		pq.add(te);
		HashSet<Long> vis=new HashSet<Long>();
		vis.add(te.calHash());
		while (!pq.isEmpty()) {
			te=pq.poll();
			if (te.pos%col>0) {
				tt=te.copy();
				tt.dat[tt.pos]=tt.dat[tt.pos-1];
				tt.dat[tt.pos-1]=num-1;
				tt.pos-=1;
				if (!vis.contains(tt.calHash())){
					tt.step+=1;
					tt.calCost();
					tt.predir=0;
					tt.prenod=te;
					if (tt.cost==0) break;
					pq.add(tt);
					vis.add(tt.calHash());
				}
			}
			if (te.pos%col<col-1) {
				tt=te.copy();
				tt.dat[tt.pos]=tt.dat[tt.pos+1];
				tt.dat[tt.pos+1]=num-1;
				tt.pos+=1;
				if (!vis.contains(tt.calHash())){
					tt.step+=1;
					tt.calCost();
					tt.predir=1;
					tt.prenod=te;
					if (tt.cost==0) break;
					pq.add(tt);
					vis.add(tt.calHash());
				}
			}
			if (te.pos>=col) {
				tt=te.copy();
				tt.dat[tt.pos]=tt.dat[tt.pos-col];
				tt.dat[tt.pos-col]=num-1;
				tt.pos-=col;
				if (!vis.contains(tt.calHash())){
					tt.step+=1;
					tt.calCost();
					tt.predir=2;
					tt.prenod=te;
					if (tt.cost==0) break;
					pq.add(tt);
					vis.add(tt.calHash());
				}
			}
			if (te.pos+col<num) {
				tt=te.copy();
				tt.dat[tt.pos]=tt.dat[tt.pos+col];
				tt.dat[tt.pos+col]=num-1;
				tt.pos+=col;
				if (!vis.contains(tt.calHash())){
					tt.step+=1;
					tt.calCost();
					tt.predir=3;
					tt.prenod=te;
					if (tt.cost==0) break;
					pq.add(tt);
					vis.add(tt.calHash());
				}
			}
		}
		while (tt.predir!=-1) {
			list.add(new Integer(tt.predir));
			tt=tt.prenod;
		}
		return list;
	}
	int getPos() {
		for(int i=0;i<num;i++) if (val[i]==num-1) {
			return i;
		}
		return -1;
	}
  @Override
  public void mousePressed(MouseEvent e) {
  }
  @Override
  public void mouseReleased(MouseEvent e) {
  }
  @Override
  public void mouseEntered(MouseEvent e) {
  }
  @Override
  public void mouseExited(MouseEvent e) {
  }
//  public static void run() {
////  	new Puzzle();
//  }
//	public static void main(String args[]) {
////		run();
//	}    
	class PQnode{
		static final int mod1=1000000007;
		static final int mod2=1000000009;
		int step,cost,pos,predir;
		PQnode prenod;
		int[] dat;
		PQnode(){
			dat=new int[num];
			step=cost=pos=0;
			predir=-1;
			prenod=null;
		}
		void calCost() {
			cost=0;
			for(int i=0;i<num;i++)
				cost+=Math.abs(dat[i]%col-i%col)*col+Math.abs(dat[i]/col-i/col)*row;
		}
		Long calHash() {
			long hash1=0,hash2=0;
			for(int i=0;i<num;i++) {
				hash1=(hash1*num+dat[i])%mod1;
				hash2=(hash2*num+dat[i])%mod2;
			}
			return new Long(hash1*hash2);
		}
		PQnode copy() {
			PQnode c;
			c=new PQnode();
			c.step=step;
			c.cost=cost;
			c.pos=pos;
			c.predir=predir;
			c.prenod=prenod;
			c.dat=new int[num];
			for(int i=0;i<num;i++) c.dat[i]=dat[i];
			return c;
		}
	}
}

