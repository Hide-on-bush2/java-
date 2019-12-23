package Client;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client{

	private Socket socket = null;
	private OutputStream outToServer;
	private DataOutputStream out;
	private InputStream inFromServer;
	private DataInputStream in;
	private boolean beConnected = false;
	private Thread listenThread = null;

	public void clientStart(int port){
		try{


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
		this.listenThread = new Thread(new listen());
		this.listenThread.start();
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

	private class listen implements Runnable{
		public void run(){
			try{
				while(beConnected){
					String message = in.readUTF();
					System.out.printf(message);
				}
			}
			catch(SocketException e){
				System.out.println("Exit");
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
//		int port = Integer.parseInt(args[0]);
		int port = 8080;
		Client client = new Client();
		client.clientStart(port);

		Scanner sc = new Scanner(System.in);
		while(true){
			// out.writeUTF(sc.nextLine());
			client.send(sc.nextLine());
		}
	}
}
