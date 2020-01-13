package Core.irregularPuzzle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//???????????ƴͼ??ܷ?ص????©?????һ??h*w?ķ???Сƴͼ?鲻??ת
//?????????h w n?ֱ?ʾ??ƴ??ͼ??ĸ߶ȣ???? ?Сƴͼ???Ŀ
//֮???ÿ??Сƴͼ???Ϣ ??????Сƴͼ????????????r?????Сƴͼ?????????
//֮???????r*c??1????1???????Сƴͼ???????λ???Ϊ0???Сƴͼ?????λ?Ϊ??
public class IrregularPuzzle {
	private DataOutputStream out = null;
	private DataInputStream in = null;
	protected static int h,w,n;
	protected static PuzzlePatch f[];
	protected static boolean ok;
	protected static int tot;
	protected static int a[][];
	
	public IrregularPuzzle(DataOutputStream out, DataInputStream in) {
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
	}
	protected void printAnswer() throws IOException {
		for(int i=1;i<=h;i++) {
			for(int j=1;j<w;j++) {
//				System.out.print(a[i][j]);
//				System.out.print(' ');
				this.say(Integer.toString(a[i][j]));
				this.say(" ");
			}
//			System.out.println(a[i][w]);
			this.say(Integer.toString(a[i][w]));
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
//4 4 4
//1 4
//1111
//1 4
//1111
//2 3
//001
//111
//2 3
//001
//111
//Output:
//No Solution.
//Input:
//4 4 5
//2 2
//11
//11
//3 2
//11
//01
//01
//2 3
//111
//100
//1 3
//111
//1 1
//1
//Output:
//1 1 2 2
//1 1 5 2
//3 3 3 2
//3 4 4 4
//Input:
//3 4 4
//1 2
//11
//2 2
//11
//11
//2 2
//11
//10
//1 3
//111
//Output:
//1 1 2 2
//3 3 2 2
//3 4 4 4
//Input:
//3 4 4
//1 2
//11
//2 2
//11
//11
//2 2
//11
//01
//1 3
//111
//Output:
//2 2 1 1
//2 2 3 3
//4 4 4 3
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
//No Solution.