package Core.irregularPuzzle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
//???????????ƴͼ??ܷ?ص????©?????һ??h*w?ķ???Сƴͼ?????ת
//?????????h w n?ֱ?ʾ??ƴ??ͼ??ĸ߶ȣ???? ?Сƴͼ???Ŀ
//֮???ÿ??Сƴͼ???Ϣ ??????Сƴͼ????????????r?????Сƴͼ?????????
//֮???????r*c??1????1???????Сƴͼ???????λ???Ϊ0???Сƴͼ?????λ?Ϊ??
public class IrregularPuzzleRotate extends IrregularPuzzle{
	private DataOutputStream out = null;
	private DataInputStream in = null;
	
	public IrregularPuzzleRotate(DataOutputStream out, DataInputStream in){
		super(out, in);
		this.out = out;
		this.in = in;
	}
	
	public String listen()throws IOException{
		String temp = this.in.readUTF();
		return temp;
	}

	public void say(String words)throws IOException{
//		System.out.println(words);
		this.out.writeUTF(words);
		this.out.flush();
	}

	
	private void dfs(int now,int num) {
//		System.out.println("dd");
		if (num==tot) {
			ok=true;
			return;
		}
		if (now>n) return;
		for(int i=1;i<=h;i++)
			for(int j=1;j<=w;j++) {
				boolean can=true;
				for(int x=1;x<=f[now].getRow();x++)
					for(int y=1;y<=f[now].getColumn();y++) {
						if ((i+x-1>h||j+y-1>w)&&f[now].getGrid(x,y)) {
							can=false;
							break;
						}
						if (f[now].getGrid(x,y)&&a[i+x-1][j+y-1]>0) {
							can=false;
							break;
						}
					}
				if (!can) continue;
//				if (now==2&&i==2&&j==1) System.out.println("can");
				for(int x=1;x<=f[now].getRow();x++)
					for(int y=1;y<=f[now].getColumn();y++)
						if (f[now].getGrid(x,y))
							a[i+x-1][j+y-1]=now;
				dfs(now+1,num+f[now].getCnt());
				if (ok) return;
				for(int x=1;x<=f[now].getRow();x++)
					for(int y=1;y<=f[now].getColumn();y++)
						if (f[now].getGrid(x,y))
							a[i+x-1][j+y-1]=0;				
			}
		for(int i=1;i<=h;i++)
			for(int j=1;j<=w;j++) {
				boolean can=true;
				for(int x=1;x<=f[now].getColumn();x++)
					for(int y=f[now].getRow();y>=1;y--) {
						if ((i+x-1>h||j+f[now].getRow()-y>w)&&f[now].getGrid(y,x)) {
							can=false;
//							if (now==1&&i==1&&j==1) System.out.println("fail1 at"+x+y);
							break;
						}
						if (f[now].getGrid(y,x)&&a[i+x-1][j+f[now].getRow()-y]>0) {
//							if (now==1&&i==1&&j==1) {
//								System.out.println("fail2 at"+x+y);
//								for(int xx=1;xx<=h;xx++)
//									for(int yy=1;yy<=w;yy++)
//										System.out.print(a[xx][yy]);
//							}
							can=false;
							break;
						}
					}
//				if (now==1&&i==1&&j==1) System.out.println("can:"+can);
				if (!can) continue;
				for(int x=1;x<=f[now].getColumn();x++)
					for(int y=f[now].getRow();y>=1;y--) 
						if (f[now].getGrid(y,x))
							a[i+x-1][j+f[now].getRow()-y]=now;
//				if (now==1&&i==1&&j==1) {
//					System.out.println("can");
//					for(int xx=1;xx<=h;xx++)
//						for(int yy=1;yy<=w;yy++)
//							System.out.print(a[xx][yy]);
//				}
				dfs(now+1,num+f[now].getCnt());
				if (ok) return;
				for(int x=1;x<=f[now].getColumn();x++)
					for(int y=f[now].getRow();y>=1;y--) 
						if (f[now].getGrid(y,x))
							a[i+x-1][j+f[now].getRow()-y]=0;		
			}
		for(int i=1;i<=h;i++)
			for(int j=1;j<=w;j++) {
				boolean can=true;
				for(int x=f[now].getRow();x>=1;x--)
					for(int y=f[now].getColumn();y>=1;y--) {
						if ((i+f[now].getRow()-x>h||j+f[now].getColumn()-y>w)&&f[now].getGrid(x,y)) {
							can=false;
							break;
						}
						if (f[now].getGrid(x,y)&&a[i+f[now].getRow()-x][j+f[now].getColumn()-y]>0) {
							can=false;
							break;
						}
					}
				if (!can) continue;
				for(int x=f[now].getRow();x>=1;x--)
					for(int y=f[now].getColumn();y>=1;y--) 
						if (f[now].getGrid(x,y))
							a[i+f[now].getRow()-x][j+f[now].getColumn()-y]=now;
//				if (now==1&&i==1&&j==1) {
//					System.out.println("can");
//					for(int xx=1;xx<=h;xx++)
//						for(int yy=1;yy<=w;yy++)
//							System.out.print(a[xx][yy]);
//				}
				dfs(now+1,num+f[now].getCnt());
				if (ok) return;
				for(int x=f[now].getRow();x>=1;x--)
					for(int y=f[now].getColumn();y>=1;y--) 
						if (f[now].getGrid(x,y))
							a[i+f[now].getRow()-x][j+f[now].getColumn()-y]=0;
			}
		for(int i=1;i<=h;i++)
			for(int j=1;j<=w;j++) {
				boolean can=true;
				for(int x=f[now].getColumn();x>=1;x--)
					for(int y=1;y<=f[now].getRow();y++) {
						if ((i+f[now].getColumn()-x>h||j+y-1>w)&&f[now].getGrid(y,x)) {
							can=false;
							break;
						}
						if (f[now].getGrid(y,x)&&a[i+f[now].getColumn()-x][j+y-1]>0) {
							can=false;
							break;
						}
					}
				if (!can) continue;
				for(int x=f[now].getColumn();x>=1;x--)
					for(int y=1;y<=f[now].getRow();y++) 
						if (f[now].getGrid(y,x))
							a[i+f[now].getColumn()-x][j+y-1]=now;
				dfs(now+1,num+f[now].getCnt());
				if (ok) return;
				for(int x=f[now].getColumn();x>=1;x--)
					for(int y=1;y<=f[now].getRow();y++) 
						if (f[now].getGrid(y,x))
							a[i+f[now].getColumn()-x][j+y-1]=0;		
			}
	}	
	public void run() {
		try {
//			Scanner scn=new Scanner(System.in);
			h=Integer.parseInt(this.listen());
			w=Integer.parseInt(this.listen());
			n=Integer.parseInt(this.listen());
			f=new PuzzlePatch[n+1];
			tot=0;
			for(int i=1;i<=n;i++) {
				int rr=Integer.parseInt(this.listen());
				int cc=Integer.parseInt(this.listen());
				f[i]=new PuzzlePatch(rr,cc);
				for(int x=1;x<=rr;x++) {
					String ss=this.listen();
//					System.out.println(ss);
					for(int y=1;y<=cc;y++)
						if (ss.charAt(y-1)=='1')
							f[i].setGrid(x,y,true);
						else
							f[i].setGrid(x,y,false);
				}
				tot+=f[i].getCnt();
			}
//			scn.close();
			if (h*w!=tot) {
				this.say("No Solution.\n\n");
				return;
			}
			ok=false;
			a=new int[h+1][w+1];
			dfs(1,0);
			if (!ok)
				this.say("No Solution.\n\n");
			else {
				printAnswer();
			}
		}catch(Exception err) {
			err.printStackTrace();
		}
	}	
//	public static void main(String args[]) {
//		run();
//	}
}
//Input:
//2 2 2
//2 1
//1
//1
//1 2
//11
//Output:
//1 2
//1 2
//Input:
//4 2 2
//3 2
//01
//01
//11
//3 2
//01
//01
//11
//Output:
//1 1
//1 2
//1 2
//2 2
//Input:
//4 3 4
//2 3
//100
//111
//2 2
//10
//11
//2 3
//111
//010
//1 1
//1
//Output:
//2 2 3
//2 3 3
//1 4 3
//1 1 1