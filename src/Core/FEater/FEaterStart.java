package Core.FEater;

//import java.util.Scanner;
import java.net.*;
import java.io.*;

import Core.FEater.rule.*;



public class FEaterStart {
//	private static Scanner sc;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	// public static void main(String args[]) {
	// 	String temp;
	// 	sc = new Scanner(System.in);
		
	// 	System.out.println("Welcome to FEater friend! please enter the level you want to challenge!");
	// 	int level = sc.nextInt();
	// 	FEater game = new FEater();
		
	// 	System.out.println("Loading...Remember 'q' is used to quit~ Enter any key to cotinue...");
	// 	temp = sc.next();
	// 	game.play(level);
		
	// 	System.out.println("Nice joy time, isn`t it?");
	// 	temp = sc.next();
	// 	return;
	// }
	
	public String listen()throws IOException{
		String temp = this.in.readUTF();
		return temp;
	}

	public void say(String words)throws IOException{
//		System.out.println(words);
		this.out.writeUTF(words);
		this.out.flush();
	}

	
	public void Run(DataOutputStream out, DataInputStream in) throws IOException {
		String temp;
//		sc = new Scanner(System.in);
		this.out = out;
		this.in = in;
		
		this.say("Welcome to FEater friend! please enter the level you want to challenge!");
		int level = Integer.parseInt(this.listen());
		FEater game = new FEater();
		
		this.say("Loading...Remember 'q' is used to quit~ Enter any key to cotinue...");
		temp = this.listen();
		game.play(level);
		
		this.say("Nice joy time, isn`t it?");
		temp = this.listen();
		return;
	}
}
