package Core.Jood.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

public class Jood {
	private Player whitePlayer;
	private Player blackPlayer;

	private Chessboard m_chessboard;

	private int whiteUndoTime;
	private int blackUndoTime;

	

	public Jood(){
		whitePlayer = null;
		blackPlayer = null;
		m_chessboard = new Chessboard();
		whiteUndoTime = 0;
		blackUndoTime = 0;
	}

	public boolean setPlayer(String whitePlayer, String blackPlayer){
		if(whitePlayer != null || blackPlayer != null){
			return false;
		}

		this.whitePlayer = new Player(whitePlayer);
		this.blackPlayer = new Player(blackPlayer);
		return true;
	}

	public boolean startOnLoadBoard() throws IOException{
		byte[] content = new byte[10 * 10];
		try {
			InputStream f = new FileInputStream("BoardLog.txt");
			f.read(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean nextStep = m_chessboard.load(content);
		return nextStep;
	}

	boolean isValid(String pos){
		if(pos.length() != 2){
			return false;
		}

		return Character.isDigit(pos.charAt(0)) && Character.isDigit(pos.charAt(1));
	}

	public boolean move(String from, String to, Chessboard.Color t_color){
		if(!(isValid(from) && isValid(to))){
			return false;
		}
		
	
		Position t_from = new Position(from.charAt(0) - '0', from.charAt(1) - '0');
		Position t_to = new Position(to.charAt(0) - '0', to.charAt(1) - '0');
		
		
		return m_chessboard.move(t_from, t_to, t_color);
	}

	public boolean undo(Chessboard.Color player){
		if(player == Chessboard.Color.Black){
			if(blackUndoTime > 0){
				return false;
			}
			return m_chessboard.undo();
		}
		else{
			if(whiteUndoTime > 0){
				return false;
			}
			return m_chessboard.undo();
		}
	}
	
	
	public void printChessboard(){
		m_chessboard.printBoard();
	}
	
	
	
	
	public void save(String file) throws FileNotFoundException {
		OutputStream f = new FileOutputStream(file);
	
		try {
			f.write(m_chessboard.save());
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int gameOver() {
		return m_chessboard.gameOver();
	}
	
}

























