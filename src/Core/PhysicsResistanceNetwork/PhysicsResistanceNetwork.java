package Core.PhysicsResistanceNetwork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PhysicsResistanceNetwork {
	private static double I=10000;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	public PhysicsResistanceNetwork(DataOutputStream out, DataInputStream in) {
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
	
	public void run() throws NumberFormatException, IOException {
//		Scanner scn=new Scanner(System.in);
		int N=Integer.parseInt(this.listen());
		int M=Integer.parseInt(this.listen());
		int S=Integer.parseInt(this.listen());
		int T=Integer.parseInt(this.listen());
		double a[][]=new double[N+2][N+2];
		double f[][]=new double[N+2][N+2];
		boolean v[][]=new boolean[N+2][N+2];
		for(int i=1;i<=N;i++)
			for(int j=1;j<=N;j++) {
				v[i][j]=false;
				a[i][j]=-1;
				f[i][j]=0;
			}
		for(int i=1;i<=M;i++) {
			int x=Integer.parseInt(this.listen());
			int y=Integer.parseInt(this.listen());
			double r=Double.valueOf(this.listen());
			v[x][y]=v[y][x]=true;
			a[x][y]=a[y][x]=r;
		}
		//floyd
		for(int k=1;k<=N;k++)
			for(int i=1;i<=N;i++)
				for(int j=1;j<=N;j++)
					if (v[i][k]&&v[k][j]) v[i][j]=true;
		if (!v[S][T]) {
			this.say("The total resistance value of the network is: INF\n");
			return;
		}		
		int[] b=new int[N+2];
		int[] d=new int[N+2];
		int nt=0;
		for(int i=1;i<=N;i++)
			if (v[S][i]) {
				b[++nt]=i;
				d[i]=nt;
			}
		
		for(int i=1;i<=N;i++)
			if (v[S][i]) {
				for(int j=1;j<=N;j++)
					if (a[i][j]!=-1) {
						f[d[i]][d[i]]+=I/a[i][j];
						f[d[i]][d[j]]-=I/a[i][j];
					}
				if (i==S) f[d[i]][0]+=I;
				if (i==T) f[d[i]][0]-=I;
			}
		f[nt+1][d[T]]++;
		
		for(int i=1;i<=nt+1;i++) {
			int r=i;
			for(int j=i+1;j<=nt+1;j++)
				if (Math.abs(f[j][i])>Math.abs(f[r][i])) r=j;
			if (r!=i)
				for(int j=0;j<=nt;j++) {
					double te=f[r][j];
					f[r][j]=f[i][j];
					f[i][j]=te;
				}
			for(int k=i+1;k<=nt+1;k++)
				f[k][0]-=f[k][i]/f[i][i]*f[i][0];
			for(int j=nt;j>=i;j--)
				for(int k=i+1;k<=nt+1;k++)
					f[k][j]-=f[k][i]/f[i][i]*f[i][j];
		}
		for(int i=nt+1;i>=1;i--) {
			for(int j=i+1;j<=nt;j++)
				f[i][0]-=f[i][j]*f[j][0];
			f[i][0]/=f[i][i];
		}
		this.say("The total resistance value of the network is: "+String.format("%.12f",(f[d[S]][0]-f[d[T]][0])) + "\n");
		
	}
//	public static void main(String[] args){
//		run();
//	}		
}
//3 3 1 3
//1 2 1
//1 3 1
//2 3 1
//The total resistance value of the network is: 0.666666666667
//4 6 1 4
//1 3 2
//3 2 2
//1 4 2
//3 4 2
//2 4 2
//1 2 2
//The total resistance value of the network is: 1.000000000000
//5 8 4 3
//1 4 4
//1 3 3
//4 3 6
//3 5 9
//3 2 12
//4 5 2
//5 2 10
//4 2 8
//The total resistance value of the network is: 2.205902858471
//5 3 4 3
//4 5 2
//5 2 10
//4 2 8
//The total resistance value of the network is: INF