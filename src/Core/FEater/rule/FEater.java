package Core.FEater.rule;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.util.Scanner;

import Core.FEater.fEaterLevels.*;
import Core.FEater.obj.*;

public class FEater {
	static int score = 0;
	static int step = 5;
//	private static Scanner sc;
	base[][] map = new base[10][10];
	
	private DataOutputStream out = null;
	private DataInputStream in = null;
	
	public FEater(DataOutputStream out, DataInputStream in) {
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

	
	public void play(int level) throws IOException {
//		sc = new Scanner(System.in);
		player you = new player(0,0);
		
		switch(level) {
		case 1: you = level1.Startlevel1(map); break;
		
		}
		
		while(step>=0) {
			show();
			
			String key = this.listen();
			
			if(key.equals("q")) {
				break;
			}
			else if(key.equals("w")) {
				if(check(you,key))
					you = move(you,-1,0);
			}
			else if(key.equals("a")) {
				if(check(you,key))
					you = move(you,0,-1);
			}
			else if(key.equals("s")) {
				if(check(you,key))
					you = move(you,1,0);
			}
			else if(key.equals("d")) {
				if(check(you,key))
					you = move(you,0,1);
				}
			
			step--;
		}
		return;
	}
	public void show() throws IOException {
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				this.say(map[i][j].draw());
			}
			this.say("\n");
		}
		this.say("SCORE: " + score + "\n");
		this.say("STEP: " + step + "\n");
	}
	public player move(player you,int x,int y) {
		map[you.getX()][you.getY()] = new base(you.getX(),you.getY());
		you = new player(you.getX()+x,you.getY()+y);
		map[you.getX()][you.getY()] = you;
		return you;
	}
	public base next(base pos,String where) {
		if(where.equals("w")) {
			return map[pos.getX()-1][pos.getY()];
		}
		else if(where.equals("a")) {
			return map[pos.getX()][pos.getY()-1];
		}
		else if(where.equals("s")) {
			return map[pos.getX()+1][pos.getY()];
		}
		else {
			return map[pos.getX()][pos.getY()+1];
		}
	}
	public boolean check(player you,String key) {
		if(next(you,key).isStop()) return false;
		else if(next(you,key).isEat()){
			score++;
			step+=5;
			return true;
		}
		return true;
	}
}
