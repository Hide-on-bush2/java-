package Core.Jood;

import Core.Jood.*;

import Core.Jood.core.*;
import Core.Jood.gui.*;

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class JoodClient{
    public Jood core;
    public Jood Core() {
        return null;
    }

//    public void Init(Configuration cfg) throws InitException {
//        this.core = new Jood();
//    }

    public void Init() throws InitException {
        this.core = new Jood();
    }
    
    public void Run(DataOutputStream out) {
        String whitePlayer, blackPlayer;
        Scanner scan = new Scanner(System.in);

	   	System.out.printf("Enter name of user1 as white player:");
        whitePlayer = scan.nextLine();
        System.out.printf("Enter name of user2 as black player:");
        blackPlayer = scan.nextLine();

        System.out.printf("Enter \"Startgame\" to start a new game or" +
             "\"LoadBoard\" to load from file:");
        String t_start = scan.next();
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
                System.out.printf("Enter the action of white player:");
            }
            else{
                System.out.printf("Enter the action of black player:");
            }
            Chessboard.Color player = isWhitePlayerTurn ? Chessboard.Color.White : Chessboard.Color.Black;

            action = scan.nextLine();

            if(action.length() == 5){
                String t_from = action.substring(0, 2);
                String t_to = action.substring(3, 5);
                

                boolean ifSucceed = core.move(t_from, t_to, player);
                if(!ifSucceed){
                    System.out.println("Invalid");
                    continue;
                }
                core.printChessboard();
                isWhitePlayerTurn = !isWhitePlayerTurn;
                if(core.gameOver() == 1) {
                	System.out.println("the winner is white player.");
                	return;
                }
                else if(core.gameOver() == -1) {
                	System.out.println("the winner is black player.");
                	return;
                }
            }
            else if(action.compareTo("Undo") == 0){
                boolean ifSucceed = core.undo(player);
                if(!ifSucceed){
                    System.out.println("Invalid");
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


