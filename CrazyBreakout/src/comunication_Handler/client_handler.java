package comunication_Handler;

import client_Communication.client_Socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import BreakoutGame.BreakoutGame;
import Data_Handler.DataType_References;
import Data_Handler.IConstants;
import Data_Handler.Json_Code_Decode;

public class client_handler {

private static client_handler _instance;

public static client_handler getInstance(){
	if(_instance == null){
		_instance = new client_handler();
	}
	return _instance;
}
private client_handler(){}

public void connect(String pUsername, String pIp, String pPort){
  client_Socket.getInstance().initClient(pIp, Integer.parseInt(pPort));
  initGame();
  newPlayer(pUsername);

	
   
	
}

public void write(String pData){
	client_Socket.getInstance().clientWrite(pData);
}

public void initGame(){
	JsonObject json = new JsonObject();
	Json_Code_Decode.getInstance().addJsonKey(IConstants.functionKey, json , IConstants.f_initGame, DataType_References.stringFile);
	Gson gson = new Gson();
	this.write(gson.toJson(json));
}

public void newPlayer(String pUsername){
	JsonObject json = new JsonObject();
	Json_Code_Decode.getInstance().addJsonKey(IConstants.functionKey, json , IConstants.f_newPlayer, DataType_References.stringFile);
	Json_Code_Decode.getInstance().addJsonKey(IConstants.usernameKey, json , pUsername, DataType_References.stringFile);
	Gson gson = new Gson();
	this.write(gson.toJson(json));
}

public void createBrick(){
	
}

public void newBall(){
	JsonObject json = new JsonObject();
	Json_Code_Decode.getInstance().addJsonKey(IConstants.functionKey, json , IConstants.f_newBall, DataType_References.stringFile);
	Gson gson = new Gson();
	this.write(gson.toJson(json));
}

public void newBar(){
	JsonObject json = new JsonObject();
	Json_Code_Decode.getInstance().addJsonKey(IConstants.functionKey, json , IConstants.f_newBar, DataType_References.stringFile);
	Gson gson = new Gson();
	this.write(gson.toJson(json));
}
}
