package Client;

import Core.*;

public class Client{

	private Robot m_robot;

	public void Init(){
		m_robot = new Robot("ptilopsis");
	}

	public void Run(){
		this.m_robot.say("Hello, I am " + m_robot.getName() + ", what can I do for you?");

		while(true){
			String request = this.m_robot.listen();
			
//			System.out.println(request);

			if(request.length() == 0){
				this.m_robot.say("You have said nothing anymore, master...");
			}
			//the external function 
			else{
				this.m_robot.execute(request);
			}
		}
	}


}
