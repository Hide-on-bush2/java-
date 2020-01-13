package Core.puzzle;

import javax.swing.*;
import java.awt.*;

public class PuzzleRadioBox extends JPanel{
	ButtonGroup bg;
	private PuzzleFrame pf;
	JRadioButton jrb1,jrb2,jrb3,jrb4,jrb5,jrb6,jrb7,jrb8,jrb9,jrb10,jrb11,jrb12,jrb13,jrb14,jrb15,jrb16;
	PuzzleRadioBox(PuzzleFrame _pf){
		pf=_pf;
		bg=new ButtonGroup();
//		jrb1=new JRadioButton("2x2");//jrb1.setEnabled(false);
//		jrb2=new JRadioButton("3x2");//jrb2.setEnabled(false);
//		jrb3=new JRadioButton("3x3");//jrb3.setEnabled(false);
//		jrb4=new JRadioButton("4x2");//jrb4.setEnabled(false);
//		jrb5=new JRadioButton("4x3");//jrb5.setEnabled(false);
//		jrb6=new JRadioButton("4x4");//jrb6.setEnabled(false);
//		jrb7=new JRadioButton("5x2");//jrb7.setEnabled(false);
//		jrb8=new JRadioButton("5x3");//jrb8.setEnabled(false);
//		jrb9=new JRadioButton("6x2");//jrb9=new JRadioButton("5x4");//jrb9.setEnabled(false);
//		jrb10=new JRadioButton("6x3");//jrb10=new JRadioButton("5x5");//jrb10.setEnabled(false);
		jrb1=new JRadioButton("2x2");//jrb1.setEnabled(false);
		jrb2=new JRadioButton("3x2");//jrb2.setEnabled(false);
		jrb3=new JRadioButton("4x2");//jrb3.setEnabled(false);
		jrb4=new JRadioButton("2x3");//jrb4.setEnabled(false);
		jrb5=new JRadioButton("3x3");//jrb5.setEnabled(false);
		jrb6=new JRadioButton("4x3");//jrb6.setEnabled(false);
		jrb7=new JRadioButton("2x4");//jrb7.setEnabled(false);
		jrb8=new JRadioButton("3x4");//jrb8.setEnabled(false);
		jrb9=new JRadioButton("4x4");//jrb9=new JRadioButton("5x4");//jrb9.setEnabled(false);
		jrb10=new JRadioButton("2x5");
		jrb11=new JRadioButton("3x5");
		jrb12=new JRadioButton("4x5");
		jrb13=new JRadioButton("5x5");
		jrb14=new JRadioButton("2x6");
		jrb15=new JRadioButton("3x6");
		jrb16=new JRadioButton("2x7");
		setLayout(new GridLayout(16,1));
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		bg.add(jrb4);
		bg.add(jrb5);
		bg.add(jrb6);
		bg.add(jrb7);
		bg.add(jrb8);
		bg.add(jrb9);
		bg.add(jrb10);
		bg.add(jrb11);
		bg.add(jrb12);
		bg.add(jrb13);
		bg.add(jrb14);
		bg.add(jrb15);
		bg.add(jrb16);
		add(jrb1);
		add(jrb2);
		add(jrb3);
		add(jrb4);
		add(jrb5);
		add(jrb6);
		add(jrb7);
		add(jrb8);
		add(jrb9);
		add(jrb10);
		add(jrb11);
		add(jrb12);
		add(jrb13);
		add(jrb14);
		add(jrb15);
		add(jrb16);
		
//		jrb6.setSelected(true);
//		pf.setSJRB(jrb6);
		jrb9.setSelected(true);
		pf.setSJRB(jrb9);
		
//		PuzzleRadioBoxActionListener new PuzzleRadioBoxActionListener()=new PuzzleRadioBoxActionListener();
		
		jrb1.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb2.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb3.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb4.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb5.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb6.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb7.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb8.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb9.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb10.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb11.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb12.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb13.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb14.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb15.addMouseListener(new PuzzleRadioBoxActionListener(pf));
		jrb16.addMouseListener(new PuzzleRadioBoxActionListener(pf));
	}

}
