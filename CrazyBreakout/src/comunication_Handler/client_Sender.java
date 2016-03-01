package comunication_Handler;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.JsonObject;

import Data_Handler.IConstants;
import Data_Handler.Json_Code_Decode;
import client_Communication.client_Socket;

public class client_Sender {
  
	
	private Protocol _protocol;

	public client_Sender(){
	  _protocol = new Protocol();
	}
	
	public void init_Protocol(String pData){
		if(pData==null){}
		else{
			_protocol.manage(getFunction(pData),
					(JsonObject) Json_Code_Decode.getInstance().get_Json(pData));
			System.out.println(pData);
		
	}}
	
	private String getFunction(String pData){
		if(Json_Code_Decode.getInstance().isJson(pData)){
			return Json_Code_Decode.getInstance().get_Json_StringKey(IConstants.functionKey,
					Json_Code_Decode.getInstance().get_Json(pData)); 
		}
		else{
			_protocol.error("Not Json");
			return null;
		}
	}

public  void audio(){  

try {
    // Open an audio input stream.
    URL url = this.getClass().getClassLoader().getResource("/home/vargas/Documentos/sprites/attack.wav");
    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
    // Get a sound clip resource.
    Clip clip = AudioSystem.getClip();
    // Open audio clip and load samples from the audio input stream.
    clip.open(audioIn);
    clip.start();
 } catch (UnsupportedAudioFileException e) {
    e.printStackTrace();
 } catch (IOException e) {
    e.printStackTrace();
 } catch (LineUnavailableException e) {
    e.printStackTrace();
 }
}
}


		
	
		
		

	
	



