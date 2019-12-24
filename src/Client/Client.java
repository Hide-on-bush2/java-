package Client;

import GUI.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client{

//	private gui g = null;
	private Socket socket = null;
	private OutputStream outToServer;
	private DataOutputStream out;
	private InputStream inFromServer;
	private DataInputStream in;
	private boolean beConnected = false;
	private Thread listenThread = null;

	public void clientStart(int port){
		try{

//			g = new gui(this.out, this.in);
			InetAddress host = InetAddress.getLocalHost();
			this.socket = new Socket(host.getHostName(), port);
			this.beConnected = true;
			outToServer = this.socket.getOutputStream();
			out = new DataOutputStream(outToServer);
			inFromServer = this.socket.getInputStream();
			in = new DataInputStream(inFromServer);
		}
		catch(IOException e){
			e.printStackTrace();
		}
//		this.listenThread = new Thread(new listen());
//		this.listenThread.start();
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
	
	public String listen() throws IOException {
		String message = in.readUTF();
		return message;
	}
//
//	private class listen implements Runnable{
//		public void run(){
//			try{
//				while(beConnected){
//					listen();
//				}
//			}
//			catch(SocketException e){
//				System.out.println("Exit");
//			}
//			catch(IOException e){
//				e.printStackTrace();
//			}
//		}
//	}

	
}
