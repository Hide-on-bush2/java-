package Core.Jood.core;

import java.util.ArrayList;
import java.util.List;

import Core.Jood.core.Chessman.Status;
class LogItem {
    public Position from, to;
    public int moveID;
    public int beEatenID;
    public LogItem(int move_id, int beEaten_id, Position from, Position to){
    	moveID = move_id;
    	this.from = from;
    	this.to = to;
    	beEatenID = beEaten_id;
    }
}

class Logs {
    private List<LogItem> logs;
    public Logs(){
    	logs = new ArrayList<LogItem>();
    }
    public void addBack(LogItem tempLog){
    	logs.add(tempLog);
    }
    public void removeBack(){
    	logs.remove(logs.size() - 1);
    }
    public LogItem back(){
    	return logs.get(logs.size() - 1);
    }
    public boolean isEmpty(){
    	return logs.size() == 0;
    }
}
