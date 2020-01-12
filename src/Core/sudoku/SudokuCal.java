package Core.sudoku;

import java.util.Scanner;
//ÊäÈëÒ»ÐÐ81³¤¶ÈµÄ×Ö·û´®£¬±íÊ¾´ÓÉÏµ½ÏÂ´Ó×óµ½ÓÒÊý¶ÀÃ¿¸öÎ»ÖÃÒÑ¾­ÌîµÄÊý£¬0±íÊ¾Î´Ìî(ÎªÁËÓÃ»§ÓÑºÃ£¬Á¬ÐøÊäÈë81¸ö×Ö·û¼´¿É£¬²»ÐèÒªÓÃ¿Õ°×·û·Ö¸ô)
//Èç¹ûÊý¶ÀÓÐ½â£¬Êä³öÌîºÃÊýµÄÊý¶À·½°¸
public class SudokuCal {
	private static int gx[]=new int[10];
	private static int gy[]=new int[10];
	private static int px[][]=new int[10][10];
	private static int py[][]=new int[10][10];
	private static int pg[][][]=new int[4][4][10];
	private static int a[][]=new int[10][10];
	private static int cnt=81;
	private static boolean ok;
	private static void init() {
		gx[1]=gx[2]=gx[3]=gy[1]=gy[2]=gy[3]=1;
		gx[4]=gx[5]=gx[6]=gy[4]=gy[5]=gy[6]=2;
		gx[7]=gx[8]=gx[9]=gy[7]=gy[8]=gy[9]=3;
		Scanner scn=new Scanner(System.in);
		String s=scn.next();
		for(int i=1;i<=9;i++)
			for(int j=1;j<=9;j++) {
				px[i][j]=0;
				py[i][j]=0;
				a[i][j]=0;
			}
		cnt=81;
		for(int i=0;i<81;i++)
			if (s.charAt(i)>'0') {
				int x=i/9+1,y=i%9+1;
				int num=s.charAt(i)-'0';
				a[x][y]=num;
				px[x][num]=1;
				py[y][num]=1;
				pg[gx[x]][gy[y]][num]=1;
				cnt--;
			}
		ok=false;
		scn.close();
	}
	private static void printAnswer() {
		for(int i=1;i<=9;i++) {
			for(int j=1;j<=9;j++) System.out.print((char)('0'+a[i][j]));
			System.out.println();
		}
	}
	private static void dfs(int x,int y,int cnt) {
		if (cnt==0) {
			ok=true;
			printAnswer();
			return;
		}
		if (cnt>10) {
			int nx=0,ny=0,maxn=10;
			for(int i=1;i<=9;i++)
				for(int j=1;j<=9;j++)
					if (a[i][j]==0) {
						int s=0;
						for(int k=1;k<=9;k++)
							if (px[i][k]==0&&py[j][k]==0&&pg[gx[i]][gy[j]][k]==0) s++;
						if (s<maxn) {
							maxn=s;
							nx=i;
							ny=j;
						}
					}
			for(int i=1;i<=9;i++)
				if (px[nx][i]==0&&py[ny][i]==0&&pg[gx[nx]][gy[ny]][i]==0) {
					px[nx][i]=py[ny][i]=pg[gx[nx]][gy[ny]][i]=1;
					a[nx][ny]=i;
					dfs(x,y,cnt-1);
					if (ok) return;
					a[nx][ny]=0;
					px[nx][i]=py[ny][i]=pg[gx[nx]][gy[ny]][i]=0;
				}
		}else {
			int nx,ny;
			if (y==9) {
				nx=x+1;
				ny=1;
			}else {
				nx=x;
				ny=y+1;
			}
			if (a[x][y]>0) {
				dfs(nx,ny,cnt);
				return;
			}
			for(int i=1;i<=9;i++)
				if (px[x][i]==0&&py[y][i]==0&&pg[gx[x]][gy[y]][i]==0) {
					px[x][i]=py[y][i]=pg[gx[x]][gy[y]][i]=1;
					a[x][y]=i;
					dfs(nx,ny,cnt-1);
					if (ok) return;
					a[x][y]=0;
					px[x][i]=py[y][i]=pg[gx[x]][gy[y]][i]=0;					
				}
		}
	}
	public static void run() {
		try {
			init();
			dfs(1,1,cnt);
			if (!ok)
				System.out.println("No Answer.");
		}catch(Exception err) {
			err.printStackTrace();
		}
	}
	public static void main(String args[]) {
		run();
	}
}
//Input:
//000000000000601040700060003090205060008040900060109080500090002040308010006000700
//Output:
//No Answer.
//Input:
//000000000000701040700060003090205060008040900060109080500090002040308010006000700
//Output:
//154923678
//683751249
//729864153
//497285361
//318647925
//265139487
//571496832
//942378516
//836512794
//Input:
//000000000000000000000000000000000000000000000000000000000000000000000000000000000
//Output:
//123456789
//456789123
//789123456
//231674895
//875912364
//694538217
//317265948
//542897631
//968341572
