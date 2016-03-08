package comunication_Handler;

import com.google.gson.JsonObject;

import BreakoutGame.BreakoutGame;
import Data_Handler.IConstants;
import Data_Handler.Json_Code_Decode;
import client_Communication.client_Socket;

public class Protocol {
	private JsonObject _json;
    private BreakoutGame _game;
    private static Protocol _protocol;
    
    public static Protocol getInstance(){
    	if(_protocol == null){
    		_protocol = new Protocol();
    	}
    	return _protocol;
    }
    
    private Protocol(){}
    
    public void manage(String pFunction, JsonObject pJson){
    	_json = pJson;
    	switch (pFunction){
    	case IConstants.f_initGame:
    		initGame();
    		break;
    	case IConstants.f_newBall:
    		newBall();
    		break;
    	case IConstants.f_newBar:
    		newBar();
    		break;
    	}
    }
 
    public void error(String pData){
    	System.out.println(pData);
    }
    
    public void initGame(){
    	int width = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.widthKey, _json);
    	int heigth = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.heigthKey, _json);
    	int speed = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.speedKey, _json);
    	int time = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.timeLimitKey, _json);
    	_game = new BreakoutGame(width, heigth,time, speed);
    	System.out.println("ready");
    }

    public void newBall(){
    	int pX = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.K_posX, _json);
    	int pY = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.K_posY, _json);
    	int pNumber = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.K_listNumber, _json);
    	_game.newBall(pX, pY, pNumber);
    	System.out.println("new ball");  	
    }
    public void newBar(){
    	int pX = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.K_posX, _json);
    	int pY = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.K_posY, _json);
    	int pNumber = Json_Code_Decode.getInstance().get_Json_IntKey(IConstants.K_listNumber, _json);
    	_game.newBar(pX, pY, pNumber);
    	System.out.println("new bar");  	
    }
    

}
