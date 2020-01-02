package Core;

import Core.FEater.*;
import Core.Jood.*;
import Core.Jood.gui.InitException;
import Core.Image.*;
import Core.Chemistry.*;
import Core.PhysicsResistanceNetwork.*;

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
			this.say("Welcome to NumberImage.\n");
			FES.Run(this.out, this.in);
			this.say("you have quitd FEater.\n");
		}
		else if(request.equals("/Chess")){
			JoodClient jood = new JoodClient();
			try {
				jood.Init(this.out, this.in);
			} catch (InitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.say("Welcome to Chess.\n");
			jood.Run();
			this.say("you have quitd Chess.\n");
		}
		else if(request.equals("/LetterImage")){
			LetterImage LI = new LetterImage(this.out, this.in);
			this.say("Welcome to LetterImage.\n");
			LI.run();
			this.say("you have quitd LetterImage.\n");
		}
		else if(request.equals("/NumberImage")) {
			NumberImage NI = new NumberImage(this.out, this.in);
			NI.run();
			this.say("you have quitd NumberImage.\n");
		}
		else if(request.equals("/Stereogram")) {
			Stereogram S = new Stereogram(this.out, this.in);
			this.say("Welcome to Stereogram.\n");
			S.run();
			this.say("you have quitd Stereogram.\n");
		}
		else if(request.equals("/Triangle")) {
			Triangle T = new Triangle(this.out, this.in);
			this.say("Welcome to Triangle.\n");
			T.run();
			this.say("you have quitd Triangle.\n");
		}
		else if(request.equals("/ChemistryCheck")) {
			ChemistryEquationCheck CEC = new ChemistryEquationCheck(this.out, this.in);
			this.say("Welcome to ChemistryCheck.\n");
			CEC.run();
			this.say("you have quitd ChemistryCheck.\n");
		}
		else if(request.equals("/PRNetwork")) {
			PhysicsResistanceNetwork PRN = new PhysicsResistanceNetwork(this.out, this.in);
			this.say("Welcome to PRNetwork.\n");
			PRN.run();
			this.say("you have quitd PRNetwork.\n");
		}
		else{
			this.say("I am sorry, but I can't understand what you have said...\n");
		}	
	}
}