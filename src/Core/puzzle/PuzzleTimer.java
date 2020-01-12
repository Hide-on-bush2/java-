package Core.puzzle;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PuzzleTimer extends JTextField{
	boolean running;
	PuzzleTimerThread ptt;
	PuzzleTimer(){
		setText("00:00:00");
		setEditable(false);
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	void updTime(long time) {
		time/=1000;
		setText(String.format("%02d:%02d:%02d",time/3600,time/60%60,time%60));
	}
	void start() {
		running=true;
		ptt=new PuzzleTimerThread(this);
		ptt.start();//run
	}
	void stop() {
//		setText("00:00:00");
		running=false;
	}
	void clear() {
		setText("00:00:00");
	}
	long getTime() {
		return ptt.getTime()/1000;
	}
}

class PuzzleTimerThread extends Thread{
	PuzzleTimer pt;
	private long timeStart;
	PuzzleTimerThread(PuzzleTimer _pt){
		setDaemon(true);
		pt=_pt;
	}
	long getTime() {
		return System.currentTimeMillis()-timeStart;
	}
	@Override
	public void run() {
		timeStart=System.currentTimeMillis();
		while (true) {
			if (!pt.running) break;
			pt.updTime(System.currentTimeMillis()-timeStart);
			try {
				sleep(1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}