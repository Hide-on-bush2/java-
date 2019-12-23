package Server;

import Core.Robot;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	private ServerSocket SS;
	private List<RobotThread> robotThreads = new ArrayList<RobotThread>();
	private boolean started = false;
	private int RobotID = 0;
	
	
	public void startServer(int port, String r_name)throws IOException{
		System.out.println("The robot is starting and waiting for connection...");
		
		try{
			started = true;
			SS = new ServerSocket(port);
			while(started){
				Socket client = SS.accept();
				System.out.println("Connect to client!");
				RobotThread rt = new RobotThread(client, this.RobotID, r_name);
				this.RobotID += 1;
				robotThreads.add(rt);
				new Thread(rt).start();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private class RobotThread implements Runnable{
		private Robot m_robot;
		private Socket client;
		private InputStream inFromClient;
		private DataInputStream in;
		private OutputStream outToClient;
		private DataOutputStream out;
		private int ID;

		public RobotThread(Socket server, int id, String t_name){
			this.client = server;
			this.ID = id;
			try{


				this.inFromClient = this.client.getInputStream();
				this.in = new DataInputStream(inFromClient);
				this.outToClient = this.client.getOutputStream();
				this.out = new DataOutputStream(outToClient);
				m_robot = new Robot(t_name, this.out);
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}

		public void send(String message){
			try{
				this.out.writeUTF(message);
				this.out.flush();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		public void run(){
			try{


				while(true){
					String message = this.in.readUTF();
					System.out.println(message);
					//根据接受到的来自客户端的信息进行相应的反应（这才是真正的bot啊）
					m_robot.execute(message);
				}
					
			}
			catch(SocketTimeoutException s){
				System.out.println("Socket timed out!!");
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
