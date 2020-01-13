package Core.puzzle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TimerTask;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PuzzleFrame extends JFrame{
	private JButton buttonStart,buttonAuto,buttonHint,buttonChangePicture;
	private Puzzle panel;
	private PuzzleRadioBox prb;
	private JRadioButton sjrb;
	private PuzzleTimer jtfTim;
	private JTextField jtfSta,jtfSte,jtfSco;
	private PuzzleTimer timer=new PuzzleTimer();
	private int gameStatus=0,hin,ste,sco;
	private AutoDoTimer atimer;
	private int lastHint;
	public PuzzleFrame(){
		super("Æ´Í¼ÓÎÏ·");
		setLayout(null);
		setResizable(false);
		setSize(660,480);
		sco=0;
		
		buttonStart=new JButton("¿ªÊ¼/½áÊø");
		buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (getGameStatus()==1) {
        			if (JOptionPane.showConfirmDialog(null,"ÓÎÏ·ÕýÔÚ½øÐÐ£¬È·ÈÏ½áÊøÓÎÏ·Âð?½áÊø±¾¾Ö½«²»»á»ñµÃ·ÖÊýÓ´~","ÌáÊ¾", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)
        				return;
            		setGameStatus(0);
            		jtfTim.stop();
            		return;
            	}
            	if (getGameStatus()==3) {
        			if (JOptionPane.showConfirmDialog(null,"×Ô¶¯»¹Ô­ÕýÔÚ½øÐÐ£¬È·ÈÏ½áÊøÓÎÏ·Âð?","ÌáÊ¾", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)
        				return;  
        			atimer.cancel();
        			setGameStatus(0);
        			jtfTim.stop();
        			return;
            	}
            	puzzleFrameInit();
            	jtfTim.start();
            	panel.init(sjrb.getText().charAt(0)-'0',sjrb.getText().charAt(2)-'0');
            	setGameStatus(1);
            	panel.sufflePic();
            }
        });
		
		buttonAuto=new JButton("×Ô¶¯¸´Ô­");
		buttonAuto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (getGameStatus()!=1) return;
            	setGameStatus(3);
  
//            	ArrayList dir=new ArrayList();
            	List<Integer> dir=panel.astar();
//            	System.out.println(dir);
            	atimer=new AutoDoTimer(true,dir.size()-1);
            	atimer.schedule(new TimerTask() {
            		public void run() {
            			int pos=panel.getPos();
//            			System.out.println(timer.now);
//            			System.out.println(dir.get(timer.now));
            			if (dir.get(atimer.now)==0) panel.swappic(pos-1,+1);
            			if (dir.get(atimer.now)==1) panel.swappic(pos+1,-1);
            			if (dir.get(atimer.now)==2) panel.swappic(pos-panel.getCol(),+panel.getCol());
            			if (dir.get(atimer.now)==3) panel.swappic(pos+panel.getCol(),-panel.getCol());	
            			if (atimer.now==0) {
            				win();
            				atimer.cancel();
            			}else
            				atimer.now--;
            		}
            	},600,260);
            }
        });		
		
		buttonHint=new JButton("ÌáÊ¾");
		buttonHint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (getGameStatus()!=1) return;
            	List<Integer> dir=panel.astar();
            	hin++;
            	int pos=panel.getPos();
            	int trydir=dir.get(dir.size()-1);
//            	if ((trydir^1)==lastHint) trydir^=2;
            	if (trydir==0) panel.swappic(pos-1,+1);
    			if (trydir==1) panel.swappic(pos+1,-1);
    			if (trydir==2) panel.swappic(pos-panel.getCol(),+panel.getCol());
    			if (trydir==3) panel.swappic(pos+panel.getCol(),-panel.getCol());	
    			lastHint=dir.get(dir.size()-1);
            }
        });	
		
		Container contentPane=getContentPane();
		contentPane.setBackground(Color.WHITE);
		
		panel=new Puzzle(this);
		panel.setBounds(120,10,420,420);
		contentPane.add(panel);
		
		buttonStart.setBounds(550,25,90,20);
		contentPane.add(buttonStart);
		buttonAuto.setBounds(550,65,90,20);
		contentPane.add(buttonAuto);
		buttonHint.setBounds(550,105,90,20);
		contentPane.add(buttonHint);
		
		prb=new PuzzleRadioBox(this);
		prb.setBounds(570,140,45,280);
		contentPane.add(prb);
		
		JLabel labelDest=new JLabel("Ä¿±êÍ¼");
		labelDest.setBounds(40,10,90,20);
		contentPane.add(labelDest);
		
//		JLabel labelIma=new JLabel(new ImageIcon("image.jpg"));
		try {
			BufferedImage buf=ImageIO.read(new File("src/images/image.jpg"));
			JLabel labelIma=new JLabel(new ImageIcon(buf.getScaledInstance(100,100,Image.SCALE_DEFAULT)));
			labelIma.setBounds(10,35,100,100);
			contentPane.add(labelIma);
		}catch(Exception err){
			err.printStackTrace();
		}

		JLabel labelSta=new JLabel("ÓÎÏ·×´Ì¬");
		labelSta.setBounds(30,140,90,20);
		contentPane.add(labelSta);		
		
		jtfSta=new JTextField("ÓÎÏ·Î´¿ªÊ¼");
		jtfSta.setEditable(false);
		jtfSta.setHorizontalAlignment(SwingConstants.CENTER);
		jtfSta.setBounds(12,160,90,20);
		jtfSta.setForeground(Color.BLUE);
//		jtfSta.setCaretColor(Color.BLUE);
		contentPane.add(jtfSta);			

		JLabel labelTim=new JLabel("ÓÃÊ±");
		labelTim.setBounds(40,185,90,20);
		contentPane.add(labelTim);			

		jtfTim=new PuzzleTimer();//new JTextField("00:00:00");
		jtfTim.setBounds(12,205,90,20);
//		jtfTim.setForeground(Color.BLUE);
		contentPane.add(jtfTim);		
		
		JLabel labelSte=new JLabel("²½Êý");
		labelSte.setBounds(40,230,90,20);
		contentPane.add(labelSte);	
		
		jtfSte=new JTextField("0");
		jtfSte.setEditable(false);
		jtfSte.setHorizontalAlignment(SwingConstants.CENTER);
		jtfSte.setBounds(12,249,90,20);
//		jtfSte.setForeground(Color.BLUE);
		contentPane.add(jtfSte);		

		JLabel labelSco=new JLabel("µÃ·Ö");
		labelSco.setBounds(40,274,90,20);
		contentPane.add(labelSco);	
		
		jtfSco=new JTextField("0");
		jtfSco.setEditable(false);
		jtfSco.setHorizontalAlignment(SwingConstants.CENTER);
		jtfSco.setBounds(12,294,90,20);
//		jtfSco.setForeground(Color.BLUE);
		contentPane.add(jtfSco);	

		JLabel labelRul=new JLabel("¹æÔò");
		labelRul.setBounds(40,316,90,20);
		contentPane.add(labelRul);	
		
		JTextArea jtfRul=new JTextArea("»¹Ô­Æ´Í¼£¬»ñµÃ·ÖÊý\r\n¿éÊýÔ½¶à£¬µÃ·ÖÔ½¸ß\r\nÓÃÊ±Ô½ÉÙ£¬µÃ·ÖÔ½¸ß\r\n²½ÊýÔ½ÉÙ£¬µÃ·ÖÔ½¸ß\r\nÊ¹ÓÃÌáÊ¾£¬µÃ·Ö½µµÍ\r\n×Ô¶¯»¹Ô­²»»ñµÃ·ÖÊý");
		jtfRul.setEditable(false);
		jtfRul.setFont(new Font("ËÎÌå",Font.BOLD,12));
		jtfRul.setBounds(5,337,110,100);
		contentPane.add(jtfRul);
		
//		buttonChangePicture=new JButton("¸ü¸ÄÍ¼Æ¬");
//		buttonChangePicture.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            	JFileChooser fileChooser=new JFileChooser();
//            	if (fileChooser.showOpenDialog(getContentPane())==JFileChooser.APPROVE_OPTION) {
//            		File selectedFile = fileChooser.getSelectedFile();
//            		
//            	}
//            }
//        });	
		
		this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	void puzzleFrameInit() {
		setGameStatus(0);
		jtfTim.stop();
		jtfTim.clear();
		jtfSte.setText("0");
		ste=0;
		hin=0;
		lastHint=-1;
	}
	void win() {
		jtfTim.stop();
		if (getGameStatus()==1) {
			int ns=panel.getnum();
			ns=ns-1+(ns-4)*(ns-4);
			ns-=jtfTim.getTime()/300+ste/60+hin/3;
			if (ns<1) ns=1;
			sco+=ns;
			jtfSco.setText(sco+"");
			setGameStatus(2);
			JOptionPane.showMessageDialog(null,"¹§Ï²Äú£¡ÓÎÏ·Ê¤Àû£¡","Ê¤Àû",JOptionPane.PLAIN_MESSAGE);
		}
		setGameStatus(2);
	}
//	public static void main(String[] args) {
//		new PuzzleFrame();
//	}
	int getGameStatus() {
		return gameStatus;
	}
	JRadioButton getSJRB() {
		return sjrb;
	}
	void setSJRB(JRadioButton _sjrb) {
		sjrb=_sjrb;
	}
	Puzzle getPanel() {
		return panel;
	}
	PuzzleTimer getPT() {
		return jtfTim;
	}
	void setGameStatus(int gs) {
		gameStatus=gs;
		switch(gs) {
			case 0:jtfSta.setText("ÓÎÏ·Î´¿ªÊ¼");break;
			case 1:jtfSta.setText("ÓÎÏ·½øÐÐÖÐ");break;
			case 2:jtfSta.setText("ÓÎÏ·Ê¤Àû");break;
			case 3:jtfSta.setText("×Ô¶¯¸´Ô­ÖÐ");break;
			default:jtfSta.setText("´íÎó£ºÎ´Öª×´Ì¬");
		}
	}
	void addStep() {
		ste++;
		jtfSte.setText(ste+"");
	}
}
class AutoDoTimer extends Timer {
	int now;
	AutoDoTimer(boolean _b,int _n){
		super(_b);
		now=_n;
	}
}
