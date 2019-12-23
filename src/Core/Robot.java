package Core;

import Core.FEater.*;
import Core.Jood.*;
import Core.Jood.gui.InitException;

import java.util.*;
import java.net.*;
import java.io.*;


public class Robot{
	private String m_name;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	public Robot(String t_name, DataOutputStream out, DataInputStream in){
		this.m_name = t_name;
		this.out = out;
		this.in = in;
	}
	
//	public void setDataOutputStrean(DataOutputStream out) {
//		this.out = out;
//	}

	public String getName(){
		return this.m_name;
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

	public void execute(String request)throws IOException{
		if(request.equals("/FEater")){
			FEaterStart FES = new FEaterStart();
			FES.Run(this.out, this.in);
		}
		else if(request.equals("/Chess")){
			JoodClient jood = new JoodClient();
			try {
				jood.Init(this.out, this.in);
			} catch (InitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jood.Run();
		}
		else if(false){}
		else{
			this.say("I am sorry, but I can't understand what you have said...\n");
		}	
	}
}