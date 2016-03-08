package comunication_Handler;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Data_Handler.DataType_References;
import Data_Handler.IConstants;
import Data_Handler.Json_Code_Decode;
import client_Communication.client_Socket;

public class client_Sender {
  
	private static client_Sender _instance;
	
	public static client_Sender getInstance(){
		if(_instance==null){
			_instance = new client_Sender();
		}
		return _instance;
	}
	
	private client_Sender(){
	}
	
	public void init_Protocol(String pData){
		if(pData==null){}
		else{
			Protocol.getInstance().manage(getFunction(pData),
					(JsonObject) Json_Code_Decode.getInstance().get_Json(pData));
			System.out.println(pData);
		
	}}
	
	private String getFunction(String pData){
		if(Json_Code_Decode.getInstance().isJson(pData)){
			return Json_Code_Decode.getInstance().get_Json_StringKey(IConstants.functionKey,
					Json_Code_Decode.getInstance().get_Json(pData)); 
		}
		else{
			Protocol.getInstance().error("Not Json");
			return null;
		}
	}


}


		
	
		
		

	
	



