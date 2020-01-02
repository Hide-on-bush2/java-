package Core.Chemistry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChemistryEquationCheck {
	private DataOutputStream out = null;
	private DataInputStream in = null;
	
	public ChemistryEquationCheck(DataOutputStream out, DataInputStream in) {
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

	private long[] calform(String s,int l,int r) {
		long c[]=new long[900];
		long cc[]=new long [900];
		for(int i=0;i<=800;i++) c[i]=0;
		int i=l,j=-1,k=-1;
		long co=0;
		while (i<=r) {
			j=i+1;
			if (s.charAt(i)=='(') {
				int t=1;
				while (true) {
					if (s.charAt(j)=='(') t++;
					if (s.charAt(j)==')') t--;
					if (t==0) break;
					j++;
				}
			}
			k=j;
			while (j<=r&&s.charAt(j)!='('&&(s.charAt(j)<'A'||s.charAt(j)>'Z')) j++;
			if (s.charAt(i)=='(') {
				cc=calform(s,i+1,k-1);
				k++;
				if (k<j&&s.charAt(k)>='0'&&s.charAt(k)<='9') {
					co=0;
					while (k<j&&s.charAt(k)>='0'&&s.charAt(k)<='9') {
						co=co*10+s.charAt(k)-'0';
						k++;
					}
				}else co=1;
				for(int t=0;t<=800;t++) c[t]+=cc[t]*co;
			}else {
				int num;
				if (i+1<j&&s.charAt(i+1)>='a'&&s.charAt(i+1)<='z') {
					num=(s.charAt(i)-'A')*30+s.charAt(i+1)-'a'+1;
					k=i+2;
				}else {
					num=(s.charAt(i)-'A')*30;
					k=i+1;
				}
				if (k<j&&s.charAt(k)>='0'&&s.charAt(k)<='9') {
					co=0;
					while (k<j&&s.charAt(k)>='0'&&s.charAt(k)<='9') {
						co=co*10+s.charAt(k)-'0';
						k++;
					}
				}else co=1;
				c[num]+=co;
			}
			i=j;
		}
		return c;
	}
	private void calcoform(String s,long a[],int l,int r) {
		long co=0;
		if (s.charAt(l)>='0'&&s.charAt(l)<='9') {
			while (s.charAt(l)>='0'&&s.charAt(l)<='9') {
				co=co*10+s.charAt(l)-'0';
				l++;
			}
		}else co=1;
		long c[]=calform(s,l,r);
		for(int i=0;i<=800;i++) a[i]+=c[i]*co;
	}
	private void calnum(String s,long a[],int l,int r) {
		for(int i=0;i<=800;i++) a[i]=0;
		int las=l-1;
		for(int i=l;i<=r;i++) if (s.charAt(i)=='+') {
			calcoform(s,a,las+1,i-1);
			las=i;
		}
		calcoform(s,a,las+1,r);
//		for(int i=0;i<=800;i++) if (a[i]>0) {
//			if (i%30>0) System.out.print((char)('A'+i/30)+(char)(i%30-1+'a'));else System.out.print((char)('A'+i/30));
//			System.out.println(":"+a[i]);
//		}
	}
	public boolean check(String s) {
		int len=s.length(),equpos=-1;
		for(int i=0;i<len;i++)
			if (s.charAt(i)=='=') {
				equpos=i;
				break;
			}
		if (equpos==-1) return false;
		long an[]=new long[900];
		long bn[]=new long[900];
		try{
			calnum(s,an,0,equpos-1);
			calnum(s,bn,equpos+1,len-1);
			for(int i=0;i<=800;i++)
				if (an[i]!=bn[i])
					return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public void run() throws IOException {
		this.say("H2=2H:"+check("H2=2H") + "\n");
		this.say("2H2+O2=2H2O:"+check("2H2+O2=2H2O") + "\n");
		this.say("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+16H2O:"+check("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+16H2O") + "\n");
		this.say("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+15H2O:"+check("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+15H2O") + "\n");
		this.say("H2SO4+2NaOH=Na2SO4+2H2O:"+check("H2SO4+2NaOH=Na2SO4+2H2O") + "\n");
		this.say("BaSO4+2NaOH=Na2SO4+2H2O:"+check("BaSO4+2NaOH=Na2SO4+2H2O") + "\n");
		this.say("2KMnO4+5H2C2O4+3H2SO4=K2SO4+2MnSO4+10CO2+8H2O:"+check("2KMnO4+5H2C2O4+3H2SO4=K2SO4+2MnSO4+10CO2+8H2O") + "\n");
		this.say("2KMnO4+5H2C2O4+3H2SO4=K2SO3+2MnSO4+10CO2+8H2O:"+check("2KMnO4+5H2C2O4+3H2SO4=K2SO3+2MnSO4+10CO2+8H2O") + "\n");
//		guirun();
		
		String s=this.listen();
		this.say(s+":"+check(s) + "\n");
		
	}
	public void guirun() {
//	    JTextField textField;
//        textField = new JTextField();   
//        textField.setEditable(true);
//        textField.setHorizontalAlignment (JTextField.RIGHT);
//        textField.setFont(new Font(null, Font.PLAIN, 30));
//        textField.setText("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2");
//
//   
//        JPanel panel = new JPanel();
//       
//        Container container = getContentPane();
//        container.add(textField, BorderLayout.NORTH);//¼ÆËã½á¹ûÀ¸
//        container.add(panel, BorderLayout.CENTER);//¼üÅÌ²¼¾Ö
//       
//        panel.add(createButton("MC",1,1,0,0,0));//°´¼ü²¼¾Ö
//		ChemistryEquationCheckGUI CECGUI=new ChemistryEquationCheckGUI();
//		ChemistryEquationCheckGUI.main(null);
	}
//	public static void main(String[] args){
//		System.out.println("H2=2H:"+check("H2=2H"));
//		System.out.println("2H2+O2=2H2O:"+check("2H2+O2=2H2O"));
//		System.out.println("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+16H2O:"+check("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+16H2O"));
//		System.out.println("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+15H2O:"+check("(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+15H2O"));
//		System.out.println("H2SO4+2NaOH=Na2SO4+2H2O:"+check("H2SO4+2NaOH=Na2SO4+2H2O"));
//		System.out.println("BaSO4+2NaOH=Na2SO4+2H2O:"+check("BaSO4+2NaOH=Na2SO4+2H2O"));
//		System.out.println("2KMnO4+5H2C2O4+3H2SO4=K2SO4+2MnSO4+10CO2+8H2O:"+check("2KMnO4+5H2C2O4+3H2SO4=K2SO4+2MnSO4+10CO2+8H2O"));
//		System.out.println("2KMnO4+5H2C2O4+3H2SO4=K2SO3+2MnSO4+10CO2+8H2O:"+check("2KMnO4+5H2C2O4+3H2SO4=K2SO3+2MnSO4+10CO2+8H2O"));
////		guirun();
//		run();
//	}	
}
//H2=2H:true
//2H2+O2=2H2O:true
//(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+16H2O:true
//(CHOCH2C(CH3)2)3CH(OH)CH(COOH)2+23O2=19CO2+15H2O:false
//H2SO4+2NaOH=Na2SO4+2H2O:true
//BaSO4+2NaOH=Na2SO4+2H2O:false
//2KMnO4+5H2C2O4+3H2SO4=K2SO4+2MnSO4+10CO2+8H2O:true
//2KMnO4+5H2C2O4+3H2SO4=K2SO3+2MnSO4+10CO2+8H2O:false
//2H2O2=2H2O+O2
//2H2O2=2H2O+O2:true