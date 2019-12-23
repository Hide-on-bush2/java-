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

	public Robot(String t_name, DataOutputStream out){
		this.m_name = t_name;
		this.out = out;
	}
	
//	public void setDataOutputStrean(DataOutputStream out) {
//		this.out = out;
//	}

	public String getName(){
		return this.m_name;
	}

	public String listen(){
		Scanner sc = new Scanner(System.in);

		String res = sc.next();

		return res;
	}

	public String say(String words){
//		System.out.println(words);
		return words;
	}

	public void execute(String request){
		if(request.equals("/FEater")){
			FEaterStart FES = new FEaterStart();
			FES.Run(this.out);
		}
		else if(request.equals("/Chess")){
			JoodClient jood = new JoodClient();
			try {
				jood.Init();
			} catch (InitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jood.Run(this.out);
		}
		else if(false){}
		else{
			this.say("I am sorry, but I can't understand what you have said...");
		}	
	}
}