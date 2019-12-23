package Core.Jood.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Core.Jood.core.Chessman.Status;
import Core.Jood.core.chessmen.King;
import Core.Jood.core.chessmen.Pawn;
import Core.Jood.core.chessmen.Rook;

public class Chessboard {
    public static final int ROW = 8;
    public static final int COL = 8;
    
    //两个list分别用来存储黑棋和白棋
    private List<Chessman> white_chess;
    private List<Chessman> black_chess;

    //走棋日志
    private Logs m_logs;

    //棋盘地图
    private int[][] map = new int [ROW + 1][COL + 1];
    
    //颜色
    public enum Color {
        Black, White,
    }
    
    //标记变量判断是否能吃过路兵
    private boolean canEatPassByPawn = false;
    
    //标记变量判断save的时候下一步是谁走
    private int nextStepIsWhite = 0;
    
    /**
     * Construactor of the Chessboard which sets the ChessBoard
     */
    //初始化棋盘
    public Chessboard() {
        white_chess = new ArrayList<Chessman>();
        black_chess = new ArrayList<Chessman>();
        
        white_chess.add(null);
        black_chess.add(null);
        /*List中下标为1-8位pawn，9-10为knight，11-12为bishop，13-14为rook，15为queen，16为king
        */
        for(int i = 1;i <= 8;i++){
            Chessman whitePawn = Chessman.create("Pawn", Color.White);
            whitePawn.setPosition(new Position(7, i));
            white_chess.add(whitePawn);

            Chessman blackPawn = Chessman.create("Pawn", Color.Black);
            blackPawn.setPosition(new Position(2, i));
            black_chess.add(blackPawn);
        }

        Chessman whiteKnight_1 = Chessman.create("Knight", Color.White);
        whiteKnight_1.setPosition(new Position(8, 2));
        white_chess.add(whiteKnight_1);
        Chessman whiteKnight_2 = Chessman.create("Knight", Color.White);
        whiteKnight_2.setPosition(new Position(8, 7));
        white_chess.add(whiteKnight_2);
        Chessman blackKnight_1 = Chessman.create("Knight", Color.Black);
        blackKnight_1.setPosition(new Position(1, 2));
        black_chess.add(blackKnight_1);
        Chessman blackKnight_2 = Chessman.create("Knight", Color.Black);
        blackKnight_2.setPosition(new Position(1, 7));
        black_chess.add(blackKnight_2);

        Chessman whiteBishop_1 = Chessman.create("Bishop", Color.White);
        whiteBishop_1.setPosition(new Position(8, 3));
        white_chess.add(whiteBishop_1);
        Chessman whiteBishop_2 = Chessman.create("Bishop", Color.White);
        whiteBishop_2.setPosition(new Position(8, 6));
        white_chess.add(whiteBishop_2);
        Chessman blackBishop_1 = Chessman.create("Bishop", Color.Black);
        blackBishop_1.setPosition(new Position(1, 3));
        black_chess.add(blackBishop_1);
        Chessman blackBishop_2 = Chessman.create("Bishop", Color.Black);
        blackBishop_2.setPosition(new Position(1, 6));
        black_chess.add(blackBishop_2);

        Chessman whiteRook_1 = Chessman.create("Rook", Color.White);
        whiteRook_1.setPosition(new Position(8, 1));
        white_chess.add(whiteRook_1);
        Chessman whiteRook_2 = Chessman.create("Rook", Color.White);
        whiteRook_2.setPosition(new Position(8, 8));
        white_chess.add(whiteRook_2);
        Chessman blackRook_1 = Chessman.create("Rook", Color.Black);
        blackRook_1.setPosition(new Position(1, 1));
        black_chess.add(blackRook_1);
        Chessman blackRook_2 = Chessman.create("Rook", Color.Black);
        blackRook_2.setPosition(new Position(1, 8));
        black_chess.add(blackRook_2);

        Chessman whiteQueen = Chessman.create("Queen", Color.White);
        whiteQueen.setPosition(new Position(8, 4));
        white_chess.add(whiteQueen);
        Chessman blackQueen = Chessman.create("Queen", Color.Black);
        blackQueen.setPosition(new Position(1, 4));
        black_chess.add(blackQueen);

        Chessman whiteKing = Chessman.create("King", Color.White);
        whiteKing.setPosition(new Position(8, 5));
        white_chess.add(whiteKing);
        Chessman blackKing = Chessman.create("King", Color.Black);
        blackKing.setPosition(new Position(1, 5));
        black_chess.add(blackKing);

        for(int i = 1;i <= 8;i++){
            for(int j = 1;j <= 8;j++){
                map[i][j] = 0;
            }
        }

        map[8][1] = 13;
        map[8][2] = 9;
        map[8][3] = 11;
        map[8][4] = 15;
        map[8][5] = 16;
        map[8][6] = 12;
        map[8][7] = 10;
        map[8][8] = 14;
        for(int i = 1;i <= 8;i++){
            map[7][i] = i;
        }

        map[1][1] = -13;
        map[1][2] = -9;
        map[1][3] = -11;
        map[1][4] = -15;
        map[1][5] = -16;
        map[1][6] = -12;
        map[1][7] = -10;
        map[1][8] = -14;
        for(int i = 1;i <= 8;i++){
            map[2][i] = -i;
        }

        m_logs = new Logs();
    }

    /**
     * can not perform two or more consecutive undo
     * @return 
     */
    //撤销对手的一步棋
    public boolean undo() {
    	//如果日志为空，返回错误
        if(m_logs.isEmpty()){
        	System.out.println("the last step not exit!!");
            return false;
        }
        //从日志中取出最近的一步棋，将棋子归位
        LogItem t_log = m_logs.back();
        map[t_log.from.getRow()][t_log.from.getCol()] = t_log.moveID;
        map[t_log.to.getRow()][t_log.to.getCol()] = 0;
        
        //将棋子的Position复原
        if(t_log.moveID > 0) {
        	white_chess.get(t_log.moveID).setPosition(new Position(t_log.from.getRow(), t_log.from.getCol()));
        }
        else {
        	black_chess.get(-t_log.moveID).setPosition(new Position(t_log.from.getRow(), t_log.from.getCol()));
        }
        
//        System.out.println(t_log.to.getRow());
//        System.out.println(t_log.to.getCol());
        
        //恢复被吃的棋子
        if(t_log.beEatenID != 0){
            map[t_log.to.getRow()][t_log.to.getCol()] = t_log.beEatenID;
        }
        
        //将日志取出
        m_logs.removeBack();
        return true;
    }

    /**
     * Move the chessman
     * @param from
     * @param to
     * @return
     */
    public boolean move(Position from, Position to, Chessboard.Color t_color) {
    	//如果from和to的位置不对，返回false
        if(!from.valid() || !to.valid()){return false;}
        int chessId = map[from.getRow()][from.getCol()];
        if(chessId == 0){
        	System.out.println("the place has no chess!!");
            return false;
        }
        Chessman t_chess;
       
      //不能吃己方的棋子（除了车王换位）
        int toChessId = map[to.getRow()][to.getCol()];

        if(chessId > 0){
            t_chess = white_chess.get(chessId);
            if(t_color == Color.Black){
            	System.out.println("It's not your turn!!");
                return false;
            }
            if(t_chess.status() != Status.King && toChessId > 0) {
            	System.out.println("You can not eat your chess!!");
        		return false;
            }
        }
        else{
            t_chess = black_chess.get(-chessId);
            if(t_color == Color.White){
            	System.out.println("It's not your turn!!");            	
                return false;
            }
            if(t_chess.status() != Status.King && toChessId < 0) {
            	System.out.println("You can not eat your chess!!");
        		return false;
            }
        }
//        if(!t_chess.canGo(to)){
//        	System.out.println("You can not go here!!");        	
//            return false;
//        }
//        if(!canCross(to, t_chess)){
//        	System.out.println("The chess can not go cross the other chesses!!");        	
//            return false;
//        }
        
        boolean chewangyiwei = false;
        boolean chiguolubing = false;
        //针对各种棋子进行单独的判断
        if(t_chess.status() == Chessman.Status.Pawn) {
        	//棋子是兵
        	if(!t_chess.canGo(to)){
            	System.out.println("You can not go here!!");        	
                return false;
            }
       
        	if(Math.abs(from.getRow() - to.getRow()) == 2) {
        		//第一次移动可以直走两步的情况
        		if(!canCross(to, t_chess)) {
        			//直走两步不能越子
        			System.out.println("The chess can not go cross the other chesses!!");        	
        			return false;
        		}
        		if(from.getCol() != to.getCol()) {
        			//直走两步不能斜着走
        			System.out.println("The  fist move can't go bias!!");
        			return false;
        		}
        		if(map[to.getRow()][to.getCol()] != 0) {
        			//直走不可以吃子
        			System.out.println("can not eat when going straight!!");
        			return false;
        		}
        		canEatPassByPawn = true;//之后的一步可以吃过路兵，延迟无效
        		((Pawn)t_chess).setPassbyPawn(true);//将该兵的状态设置为“过路兵”
        	}
        	else {
        		//不是第一次的情况
        		if(from.getCol() == to.getCol()) {
        			//直走不吃子
        			if(map[to.getRow()][to.getCol()] != 0) {
            			System.out.println("can not eat when going straight!!");
            			return false;
        			}
        		}
        		else {
        			//斜走吃子
        			
        	
//        			if(t_color == Color.White) {
//        				int tempChessId = map[to.getRow() + 1][to.getCol()];
//        				Chessman tempMan = white_chess.get(tempChessId);
//        				if(tempMan.status() == Chessman.Status.Pawn && ((Pawn) tempMan).passByPawn && canEatPassByPawn) {
//        					map[to.getRow() + 1][to.getCol()] = 0;
//        				}
//        			}
        			int tempChessId = t_color == Color.White ? map[to.getRow() + 1][to.getCol()] : map[to.getRow() - 1][to.getCol()];
        			Chessman tempMan = tempChessId < 0 ? black_chess.get(-tempChessId) : white_chess.get(tempChessId);
        			boolean differentChess = (t_color == Color.White && tempChessId < 0) || (t_color == Color.Black && tempChessId > 0);
        			
        			if(differentChess && tempMan != null && tempMan.status() == Chessman.Status.Pawn && ((Pawn) tempMan).isPassbyPawn() && canEatPassByPawn) {
        				
        				//吃过路兵
        				if(t_color == Color.White) {
       						map[to.getRow() + 1][to.getCol()] = 0;
       					}
       					else {
       						map[to.getRow() - 1][to.getCol()] = 0;
       					}
       					chiguolubing = true;
       				}
        			else if(map[to.getRow()][to.getCol()] == 0) {
        				
        				//斜吃
        				System.out.println("can not go bias when do not to eat chess!!");
        				return false;
        			}
        			
            		((Pawn)t_chess).setPassbyPawn(false);//将该兵的状态设置为“非过路兵”
        		}
        		canEatPassByPawn = false;//延时不能吃过路兵
        	}
        	
        	
            t_chess.setPosition(new Position(to.getRow(), to.getCol()));//更新棋子的位置
           
        }
        else if(t_chess.status() == Chessman.Status.Bishop) {
        	//棋子是象
        	
        	//延时不能吃过路兵
        	canEatPassByPawn = false;
        	
        	//是否延斜线走
        	if(!t_chess.canGo(to)){
        		System.out.println("You can not go here!!");        	
        		return false;
        	}
        	
        	//不能越子
        	if(!canCross(to, t_chess)){
        		System.out.println("The chess can not go cross the other chesses!!");        	
        		return false;
        	}
        }
        else if(t_chess.status() == Chessman.Status.King) {
        	//延时不能吃过路兵
        	canEatPassByPawn = false;
        	int tempChessId = map[to.getRow()][to.getCol()];
        	Chessman tempChess = t_color == Color.White ? white_chess.get(tempChessId) : black_chess.get(-tempChessId);
        	if(tempChess != null && tempChess.status() == Status.Rook) {
        		
        		//判断是否能车王移位
        		
        		//如果车和王都没移动过
        		if(((King)t_chess).hasMoved() || ((Rook)tempChess).hasMoved()) {
        			System.out.println("the king or rook have been moved, can not be replaced!!");
        			return false;
        		}
        		
        		int tempRow = t_chess.getPosition().getRow();
        		int kCol = t_chess.getPosition().getCol();
        		int rCol = tempChess.getPosition().getCol();
        		
        		//判断车和王之间是否有其他棋子
        		if(kCol > rCol) {
        			for(int i = rCol + 1;i < kCol;i++) {
        				if(map[tempRow][i] != 0) {
        					System.out.println("there are chesses exit between king and rook!!");
        					return false;
        				}
        			}
        		}
        		else {
        			for(int i = kCol + 1;i < rCol;i++) {
        				if(map[tempRow][i] != 0) {
        					System.out.println("there are chesses exit between king and rook!!");
        					return false;
        				}
        			}
        		}
        		
        		chewangyiwei = true;
        		
        		//对地图和棋子进行更新
        		map[from.getRow()][from.getCol()] = tempChessId;
        		tempChess.setPosition(new Position(from.getRow(), from.getCol()));
        	}
        	else if((map[to.getRow()][to.getCol()] > 0 && t_color == Color.White) || map[to.getRow()][to.getCol()] < 0 && t_color == Color.Black) {
        		
        		//不是车王移位的时候不能吃自己的棋子
        		System.out.println("You can not eat your chessss!!");
        		return false;
        	}
        	else if(!t_chess.canGo(to)){
        		
        		//不是车王移位的时候按照正常走法
        		System.out.println("You can not go here!!");        	
        		return false;
        	}
        }
        else if(t_chess.status() == Chessman.Status.Knight) {
        	//骑士（马）
        	canEatPassByPawn = false;
        	if(!t_chess.canGo(to)){
        		System.out.println("You can not go here!!");        	
        		return false;
        	}
        }
        else if(t_chess.status() == Chessman.Status.Queen) {
        	//王后
        	canEatPassByPawn = false;
        	if(!t_chess.canGo(to)){
        		System.out.println("You can not go here!!");        	
        		return false;
        	}
        	if(!canCross(to, t_chess)){
        		System.out.println("The chess can not go cross the other chesses!!");        	
        		return false;
        	}
        	
        }
        else if(t_chess.status() == Chessman.Status.Rook) {
        	//车
        	canEatPassByPawn = false;
        	if(!t_chess.canGo(to)){
        		System.out.println("You can not go here!!");        	
        		return false;
        	}
        	if(!canCross(to, t_chess)){
        		System.out.println("The chess can not go cross the other chesses!!");        	
        		return false;
        	}
        }
        
        LogItem t_log;
       if(chiguolubing) {
    	   //吃过路兵的记录方法
    	   if(t_color == Color.White) {
    		   t_log = new LogItem(chessId, map[to.getRow() + 1][to.getCol()], from, to);
    	   }
    	   else {
    		   t_log = new LogItem(chessId, map[to.getRow() - 1][to.getCol()], from, to);
    	   }
       }
       else {
    	   //不是吃过路兵的记录方法
    	   t_log = new LogItem(chessId, map[to.getRow()][to.getCol()], from, to);
       }
        m_logs.addBack(t_log);
        
        map[to.getRow()][to.getCol()] = chessId;
        if(!chewangyiwei) {
        	//不是车王移位的话，将from移零
        	map[from.getRow()][from.getCol()] = 0;
        }
        t_chess.setPosition(new Position(to.getRow(), to.getCol()));
        nextStepIsWhite = t_color == Color.White ? 0 : 1;
        
      //晋升判断
    	if((t_chess.status() == Status.Pawn && t_color == Color.White && to.getRow() == 1) || (t_color == Color.Black && to.getRow() == 8)) {
    		System.out.printf("Now the pawn can promote, do you want to promote him?<Y> or <N>:");
    		Scanner scan = new Scanner(System.in);
    		String res = scan.nextLine();
    		
    		if(res.compareTo("Y") == 0) {
        		while(true) {
        			System.out.printf("Whitch one do you promote to ?\n<Queen>\n<Bishop>\n<Rook>\n<Knight>\n");
            		String typeOfChess = scan.nextLine();
            		if(typeOfChess.compareTo("Queen") == 0) {
            			promote(t_chess, Status.Queen);
            			break;
            		}
            		else if(typeOfChess.compareTo("Bishop") == 0) {
            			promote(t_chess, Status.Bishop);
            			break;
            		}
            		else if(typeOfChess.compareTo("Rook") == 0) {
            			promote(t_chess, Status.Rook);
            			break;
            		}
            		else if(typeOfChess.compareTo("Knight") == 0) {
            			promote(t_chess, Status.Knight);
            			break;
            		}
        		}
    		}
    		
    	}
        return true;
    }
    



    
    
    
    
    /**
     * Only knight can cross other chessman. 
     * 
     * @return
     */
    public boolean canCross(Position to, Chessman moving_chess) {
        if(
        	//王和骑士肯定不会越子
            moving_chess.status() == Status.King||
            moving_chess.status() == Status.Knight){
            return true;
        }
        
        List<Position> t_path = moving_chess.path(to);
        for(int i = 0;i < t_path.size();i++){
            int t_row = t_path.get(i).getRow();
            int t_col = t_path.get(i).getCol();
            if(map[t_row][t_col] != 0){
                return false;
            }
        }
        return true;
    }


    /**
     * A pawn can be promoted when it reachs eight rank or one file.
     * @return chessman can be promoted.
     */
//    public boolean canPromote(Chessman pawn) {
//        Position t_pos = pawn.getPosition();
//        if(map[t_pos.getRow()][t_pos.getCol()] > 0 && t_pos.getRow() == 1){
//            return true;
//        }
//        if(map[t_pos.getRow()][t_pos.getCol()] < 0 && t_pos.getRow() == 8){
//            return true;
//        }
//        return false;
//    }

    /**
     *  A pawn can promote to other status (except King & Pawn)
     * @param pawn the pawn waiting to be promote
     * @param status the new status
     */
    public void promote(Chessman pawn, Status status) {
//        if(!canPromote(pawn)){
//            return;
//        }
    	//兵的升变
        Position t_pos = pawn.getPosition();
        Color t_color = map[t_pos.getRow()][t_pos.getCol()] > 0 ? Color.White : Color.Black;
        Chessman newChess = Chessman.create(status, t_color);
        newChess.setPosition(new Position(t_pos.getRow(), t_pos.getCol()));

        white_chess.add(newChess);
        map[t_pos.getRow()][t_pos.getCol()] = white_chess.size() - 1;
    }

    /**
    * print the chessboard
    */

    public void printBoard(){
    	//打印一个棋盘
        for(int i = 9;i >= 1;i--){
            if(i == 9){
                System.out.printf("   ");
                for(int j = 1;j <= 8;j++){
                    System.out.printf("%d  ", j);
                }
                System.out.println();
                continue;
            }
            System.out.printf("%d  ", i);
            for(int j = 1;j <= 8;j++){
                int chessId = map[i][j];
                if(chessId == 0){
                    System.out.printf("   ");
                }
                else if(chessId > 0){
                    Chessman t_chess = white_chess.get(chessId);
                    if(t_chess.status() == Status.Pawn){
                        System.out.printf("6P ");
                    }
                    else if(t_chess.status() == Status.Rook){
                        System.out.printf("3R ");
                    }
                    else if(t_chess.status() == Status.Knight){
                        System.out.printf("5N ");
                    }
                    else if(t_chess.status() == Status.Bishop){
                        System.out.printf("4B ");
                    }
                    else if(t_chess.status() == Status.Queen){
                        System.out.printf("2Q ");
                    }
                    else if(t_chess.status() == Status.King){
                        System.out.printf("1K ");
                    }
                    else{
                        System.out.printf("   ");
                    }
                }
                else{
                    Chessman t_chess = white_chess.get(- chessId);
                    if(t_chess.status() == Status.Pawn){
                        System.out.printf("P6 ");
                    }
                    else if(t_chess.status() == Status.Rook){
                        System.out.printf("R3 ");
                    }
                    else if(t_chess.status() == Status.Knight){
                        System.out.printf("N5 ");
                    }
                    else if(t_chess.status() == Status.Bishop){
                        System.out.printf("B4 ");
                    }
                    else if(t_chess.status() == Status.Queen){
                        System.out.printf("Q2 ");
                    }
                    else if(t_chess.status() == Status.King){
                        System.out.printf("K1 ");
                    }
                    else{
                        System.out.printf("   ");
                    }   
                }
            }
            System.out.println();
        }
    }

    /**
     * Return the data to JOOD which will write into file to save the chessboard
     * @return
     */
    public byte[] save() {
    	//将棋盘保存在文件中
        byte[] saveBoard = new byte[100];
        for(int i = 1;i <= 8;i++) {
        	for(int j = 1;j <= 8;j++) {
        		saveBoard[i * 8 + j] = (byte)map[i][j];
        	}
        }
        saveBoard[0] = (byte)nextStepIsWhite;
        return saveBoard;
    }

    /**
     * only from the saved chess manual to new a chessboard
     * @param board
     */
    public boolean load(byte[] board) {
    	//从文件中加载一个键盘
        for(int i = 1;i <= 8;i++) {
        	for(int j = 1;j <= 8;j++) {
        		map[i][j] = (int)board[i * 8 + j];
        		if(map[i][j] > 0) {
        			Chessman tempChess = white_chess.get(map[i][j]);
        			tempChess.setPosition(new Position(i, j));
        		}
        	}
        }
        return board[0] == (byte)1;
    }
    
    public int gameOver() {
    	//判断游戏是否结束
    	int whiteKing = 0;
    	int blackKing = 0;
    	for(int i = 1;i <= 8;i++) {
    		for(int j = 1;j <= 8;j++) {
    			if(map[i][j] == 16) {
    				whiteKing++;
    			}
    			else if(map[i][j] == -16) {
    				blackKing++;
    			}
    		}
    	}
    	return whiteKing - blackKing;
    }
}

