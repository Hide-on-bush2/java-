package Core.Jood;

import Core.Jood.*;

import Core.Jood.core.*;
import Core.Jood.gui.*;

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class JoodClient{
    public Jood core;
    private DataOutputStream out = null;
	private DataInputStream in = null;
    
    public Jood Core() {
        return null;
    }

//    public void Init(Configuration cfg) throws InitException {
//        this.core = new Jood();
//    }

    public void Init(DataOutputStream out, DataInputStream in) throws InitException {
    	this.out = out;
        this.in = in;
        this.core = new Jood(this.out, this.in);
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

    
    public void Run()throws IOException {
        String whitePlayer, blackPlayer;
//        Scanner scan = new Scanner(System.in);

	   	this.say("Enter name of user1 as white player:");
        whitePlayer = this.listen();
        this.say("Enter name of user2 as black player:");
        blackPlayer = this.listen();

        this.say("Enter \"Startgame\" to start a new game or" +
             "\"LoadBoard\" to load from file:");
        String t_start = this.listen();
        boolean isWhitePlayerTurn = true;
        if(t_start.compareTo("LoadBoard") == 0){
            try {
				boolean nextStep = core.startOnLoadBoard();
				isWhitePlayerTurn = nextStep;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        core.printChessboard();

        String action;
        while(true){
            if(isWhitePlayerTurn){
            	this.say("Enter the action of white player:");
            }
            else{
            	this.say("Enter the action of black player:");
            }
            Chessboard.Color player = isWhitePlayerTurn ? Chessboard.Color.White : Chessboard.Color.Black;

            action = this.listen();

            if(action.length() == 5){
                String t_from = action.substring(0, 2);
                String t_to = action.substring(3, 5);
                

                boolean ifSucceed = core.move(t_from, t_to, player);
                if(!ifSucceed){
                	this.say("Invalid\n");
                    continue;
                }
                core.printChessboard();
                isWhitePlayerTurn = !isWhitePlayerTurn;
                if(core.gameOver() == 1) {
                	this.say("the winner is white player.\n");
                	return;
                }
                else if(core.gameOver() == -1) {
                	this.say("the winner is black player.\n");
                	return;
                }
            }
            else if(action.compareTo("Undo") == 0){
                boolean ifSucceed = core.undo(player);
                if(!ifSucceed){
                	this.say("Invalid\n");
                    continue;
                }
                core.printChessboard();
                isWhitePlayerTurn = !isWhitePlayerTurn;                
            }
            else if(action.compareTo("save") == 0) {
            	try {
					core.save("BoardLog.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return;
            }
        }
	}












    
}


