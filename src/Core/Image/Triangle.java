package Core.Image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//¶ÁÈën »­³ön½×·ÖÐÎÕýÈý½ÇÐÎÍ¼Ïñ
public class Triangle {
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private static char[][] s;
	private static int m,n;
	
	public Triangle(DataOutputStream out, DataInputStream in) {
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

	
	private void init() throws NumberFormatException, IOException {
		
		n=Integer.parseInt(this.listen());
		m=1<<(n+1);
		s=new char[m+2][m+m+2];
		for(int i=1;i<=m;i++)
			for(int j=-m+1;j<=m;j++) s[i][j+m]=' ';
	}
	private void work() {
		int mm=2;
		s[1][m+0]='/';
		s[1][m+1]='\\';
		s[2][m-1]='/';
		s[2][m+0]='_';
		s[2][m+1]='_';
		s[2][m+2]='\\';
		for(int i=2;i<=n;i++) {
			for(int j=1;j<=mm;j++)
				for(int k=-mm+1;k<=mm;k++)
					s[j+mm][k-(mm>>1)-(1<<(i-2))+m]=s[j][k+m];
			for(int j=1;j<=mm;j++)
				for(int k=-mm+1;k<=mm;k++)
					s[j+mm][k+(mm>>1)+(1<<(i-2))+m]=s[j][k+m];	
			mm<<=1;
		}
	}
	private void printImage() throws IOException {
		for(int i=1;i<=m;i++) {
			for(int j=-m+1;j<=i;j++) this.say("" + s[i][j+m]);
			this.say("\n");
		}
	}
	public void run() {
		try {
			init();
			work();
			printImage();
		}catch(Exception err) {
			err.printStackTrace();
		}
	}
//	public static void main(String args[]) {
//		run();
//	}
}
//3
//     /\
//    /__\
//   /\  /\
//  /__\/__\
// /\      /\
///__\    /__\
///\  /\  /\  /\
///__\/__\/__\/__\
        