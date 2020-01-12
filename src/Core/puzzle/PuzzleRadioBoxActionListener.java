package Core.puzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class PuzzleRadioBoxActionListener implements MouseListener{
	private boolean before;
	private PuzzleFrame pf;
	PuzzleRadioBoxActionListener(PuzzleFrame _pf){
		pf=_pf;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.println("±»µã»÷");
		JRadioButton jrb=(JRadioButton)e.getSource();
		if (before) {
//			System.out.println("ÒÑÑ¡");
			return;
		}
//		if (jrb.isSelected()) return;
//		jrb.setSelected(true);
		if (pf.getGameStatus()==3) {
			jrb.setSelected(false);
			pf.getSJRB().setSelected(true);
			return;
		}
		if (pf.getGameStatus()==1) {
			if (JOptionPane.showConfirmDialog(null,"ÓÎÏ·ÕýÔÚ½øÐÐ£¬È·ÈÏ¸Ä±ä³ß´çÂð?¸Ä±ä³ß´ç±¾¾Ö½«²»»á»ñµÃ·ÖÊýÓ´~","ÌáÊ¾", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION) {
				jrb.setSelected(false);
				pf.getSJRB().setSelected(true);
				return;				
			}
			pf.setGameStatus(0);
			pf.getPT().stop();
			pf.getPanel().init(jrb.getText().charAt(0)-'0',jrb.getText().charAt(2)-'0');
			pf.setSJRB(jrb);
			return;
		}
		pf.puzzleFrameInit();
		pf.getPanel().init(jrb.getText().charAt(0)-'0',jrb.getText().charAt(2)-'0');
		pf.setSJRB(jrb);
	}
    @Override
    public void mouseEntered(MouseEvent e) {
//    	System.out.println("½øÈë");
    	JRadioButton jrb=(JRadioButton)e.getSource();
    	before=jrb.isSelected();
    }
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
