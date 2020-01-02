package Core.Image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//ÊäÈëÎ»¿íºÍÊý×Ö´® »­³öÊý×Ö´®µÄÍ¼Ïñ
public class NumberImage {
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private static int k,l;
	private static String s;
	
	public NumberImage(DataOutputStream out, DataInputStream in) {
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

	
	private  void write1(char c,int x) throws IOException {
		switch (c){
			case '1':
				for(int i=1;i<=k+2;i++) this.say(" ");
				break;
			case '2':
			case '3':
			case '5':
			case '6':
			case '8':
			case '9':
				this.say(" ");
				for(int i=1;i<=k;i++) this.say("-");
				this.say(" ");
				break;
			case '4':
				this.say(" ");
				if (x==2) 
					for(int i=1;i<=k;i++) this.say("-");
				else
					for(int i=1;i<=k;i++) this.say(" ");
				this.say(" ");
				break;
			case '7':
				this.say(" ");
				if (x==1) 
					for(int i=1;i<=k;i++) this.say("-");
				else
					for(int i=1;i<=k;i++) this.say(" ");
				this.say(" ");
				break;				
			case '0':
				this.say(" ");
				if (x==2) 
					for(int i=1;i<=k;i++) this.say(" ");
				else
					for(int i=1;i<=k;i++) this.say("-");
				this.say(" ");
				break;				
		}
	}
	private void write2(char c,int x) throws IOException {
		switch (c){
			case '1':
				for(int i=1;i<=k+1;i++) this.say(" ");
				this.say("|");
				break;
			case '2':
				if (x==1) 
					this.say("-");
				else
					this.say("|");
				for(int i=1;i<=k;i++) this.say(" ");
				if (x==1) 
					this.say("|");
				else
					this.say("-");
				break;
			case '3':
				for(int i=1;i<=k+1;i++) this.say(" ");
				this.say("|");
				break;
			case '4':
				if (x==1)
					this.say("|");
				else
					this.say(" ");
				for(int i=1;i<=k;i++) this.say(" ");
				this.say("|");
				break;
			case '5':
				if (x==1)
					this.say("|");
				else
					this.say(" ");
				for(int i=1;i<=k;i++) this.say(" ");
				if (x==1)
					this.say(" ");
				else
					this.say("|");
				break;
			case '6':
				System.out.print('|');
				for(int i=1;i<=k;i++) this.say(" ");
				if (x==2)
					this.say("|");
				else
					this.say(" ");	
				break;
			case '7':
				for(int i=1;i<=k+1;i++) this.say(" ");
				this.say("|");
				break;				
			case '8':
				this.say("|");
				for(int i=1;i<=k;i++) this.say(" ");
				this.say("|");
				break;
			case '9':
				if (x==1)
					this.say("|");
				else
					this.say(" ");		
				for(int i=1;i<=k;i++) this.say(" ");
				this.say("|");
				break;
			case '0':
				this.say("|");
				for(int i=1;i<=k;i++) this.say(" ");
				this.say("|");
				break;				
		}
	}
	public  void run() {
		try{
			
			k=Integer.parseInt(this.listen());
			s=this.listen();
			l=s.length();
			for(int i=0;i<l;i++) {
				write1(s.charAt(i),1);
				if (i+1<l)
					this.say(" ");
				else
					this.say("\n");
			}
			for(int j=1;j<=k;j++)
				for(int i=0;i<l;i++) {
					write2(s.charAt(i),1);
					if (i+1<l)
						this.say(" ");
					else
						this.say("\n");
				}
			for(int i=0;i<l;i++) {
				write1(s.charAt(i),2);
				if (i+1<l)
					this.say(" ");
				else
					this.say("\n");
			}
			for(int j=1;j<=k;j++)
				for(int i=0;i<l;i++) {
					write2(s.charAt(i),2);
					if (i+1<l)
						this.say(" ");
					else
						this.say("\n");
				}			
			for(int i=0;i<l;i++) {
				write1(s.charAt(i),3);
				if (i+1<l)
					this.say(" ");
				else
					this.say("\n");
			}
			
		}catch(Exception err) {
			err.printStackTrace();
		}
	}
//	public static void main(String args[]) {
//		run();
//	}
}
//3
//0123456789
//---         ---   ---         ---   ---   ---   ---   --- 
//|   |     |     |     | |   | |     |         | |   | |   |
//|   |     |     |     | |   | |     |         | |   | |   |
//|   |     |     |     | |   | |     |         | |   | |   |
//           ---   ---   ---   ---   ---         ---   --- 
//|   |     | |         |     |     | |   |     | |   |     |
//|   |     | |         |     |     | |   |     | |   |     |
//|   |     | |         |     |     | |   |     | |   |     |
//---         ---   ---         ---   ---         ---   --- 