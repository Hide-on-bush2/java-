package GUI;

import javax.swing.*;

import Client.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class gui extends JFrame{
//	private DataOutputStream out = null;
//	private DataInputStream in = null;
	
//	public String listen()throws IOException{
//		while(true) {
//			
//		}
//	}
//
//	public void say(String words)throws IOException{
////		System.out.println(words);
//		this.out.writeUTF(words);
//		this.out.flush();
//	}
	private Client client = null;
	
	public gui(Client client) throws IOException {
//		this.out = out;
//		this.in = in;
		this.client = client;
		super.setTitle("Robot Client");
		super.setSize(800, 1000);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new BorderLayout());
//		super.setExtendedState(JFrame.MAXIMIZED_BOTH);// JFrame最大化
		JPanel jp = new JPanel();
		
		//创建白咕咕的头像
		JLabel label = new JLabel();
		ImageIcon head = new ImageIcon("src/images/baigugu2.jpg");
		head.setImage(head.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
		label.setIcon(head);
		jp.add(label);
		
		//显示框和发送框
		String init_word = "Hello, master, I am ptilopsis, I am very powerful and be able to :\n" + 
				"\t</Chess>: load a chess to play,\n" + 
				"\t</FEater>: load a game to play, \n" + 
				"\t</LetterImage>: transfer your letters to a image, \n" + 
				"\t</NumberImage>: transfer your numbers to a image, \n" +
				"\t</Stereogram>: draw a stereogram, \n" +
				"\t</Triangle>: draw a triangle, \n" +
				"\t</ChemistryCheck>: determine if the chemical equation is balanced, \n" +
				"\t</PRNetwork>: draw a physics resistance network, \n" + 
				"So what can I do for you?\n";
				
		JTextArea server_content = new JTextArea(init_word, 25, 100);
		server_content.setLineWrap(true);
		server_content.setEditable(false);
		server_content.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		server_content.setForeground(Color.BLUE);
		JScrollPane jsp1=new JScrollPane(server_content);//滚动窗口
		Dimension size1 = server_content.getPreferredSize();    //获得文本域的首选大小
		jsp1.setBounds(110,90,size1.width,size1.height);
		jp.add(jsp1);
		
		JTextArea client_content = new JTextArea("", 4, 35);
		client_content.setLineWrap(true);
//		client_content.setForeground(Color.GREEN);
		JScrollPane jsp2=new JScrollPane(client_content);//滚动窗口
		Dimension size2 = client_content.getPreferredSize();    //获得文本域的首选大小
		jsp2.setBounds(110,90,size2.width,size2.height);
		jp.add(jsp2);
		
		//消息发送按钮
		JButton  button = new JButton("发送");
		button.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				String message = client_content.getText();
                client.send(message);
                client_content.setText("");
            }
		});
		jp.add(button);
		
		super.add(jp);
		super.setVisible(true);
		
		while(true) {
			String tmp = client.listen();
			server_content.append(tmp);
		}
	}
	
	
	
	
//	public static void main(String args[]) {
//		new gui();
//	} 
	
public static void main(String[] args) throws IOException{
		
		
//		int port = Integer.parseInt(args[0]);
		int port = 8080;
		Client client = new Client();
		client.clientStart(port);
		gui g = new gui(client);
		
//		Scanner sc = new Scanner(System.in);
//		while(true){
//			// out.writeUTF(sc.nextLine());
//			client.send(sc.nextLine());
//		}
	}
}
