package comunication_Handler;

import client_Communication.client_Socket;
import BreakoutGame.BreakoutGame;

public class client_handler {



public void connect(String pUsername, String pIp, String pPort){
   client_Socket.getInstance().initClient(pIp, Integer.parseInt(pPort));
   client_Socket.getInstance().clientWrite(pUsername);
}
}