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
		super.setSize(400, 200);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		
		//显示框和发送框
		JTextArea server_content = new JTextArea("Server\n", 7, 30);
		server_content.setLineWrap(true);
		server_content.setEditable(false);
		JScrollPane jsp1=new JScrollPane(server_content);//滚动窗口
		Dimension size1 = server_content.getPreferredSize();    //获得文本域的首选大小
		jsp1.setBounds(110,90,size1.width,size1.height);
		jp.add(jsp1);
		
		JTextArea client_content = new JTextArea("", 2, 25);
		client_content.setLineWrap(true);
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
