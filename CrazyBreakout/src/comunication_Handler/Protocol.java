package comunication_Handler;

import com.google.gson.JsonObject;

import BreakoutGame.BreakoutGame;
import Data_Handler.IConstants;
import Data_Handler.Json_Code_Decode;
import client_Communication.client_Socket;

public class Protocol {
	private JsonObject _json;
    private BreakoutGame _game;
    public Protocol(){}
    
    public void manage(String pFunction, JsonObject pJson){
    	_json = pJson;
    	switch (pFunction){
    	case IConstants.f_initGame:
    		initGame();
    		break;
    	case IConstants.f_newBall:
    		newBall();
    		break;
    	}
    }
    public void error(String pData){
    	System.out.println(pData);
    }
    
    public void initGame(){
    	//int width = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.widthKey, _json);
    	//int heigth = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.heigthKey, _json);
    	_game = new BreakoutGame();
    	client_Socket.getInstance().clientWrite("iniciado");
    }
    public void newBall(){
    	int pX = Json_Code_Decode.getInstance().get_Json_IntKey("pX", _json);
    	int pY = Json_Code_Decode.getInstance().get_Json_IntKey("pY", _json);
    	_game.newBall(pX, pY);
    	System.out.println("new ball");
    	client_Socket.getInstance().clientWrite("new Ball");
    	
    }
    

}
