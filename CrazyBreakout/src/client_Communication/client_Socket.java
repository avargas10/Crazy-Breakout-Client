package client_Communication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import comunication_Handler.client_Sender;


public class client_Socket extends Thread {
	private Socket _socket;
	private BufferedReader _reader;
	private PrintWriter _writer;
	private static client_Socket _instance;
	private client_Sender _sender;

	private client_Socket(){
		_sender = new client_Sender();
	}
	
	public static client_Socket getInstance(){
		if(_instance==null){
			_instance = new client_Socket();
		}
		return _instance;
	}
	
    /** 
     * inicia una instancia del cliente
     */
	public void initClient(String pIp, int pPort) {
		try {
			_socket = new Socket(pIp, pPort);
			_writer = new PrintWriter(_socket.getOutputStream(), true);
			_reader = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
			this.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

	}
    /**
     * corre el thread de cada cliente
     */
	public void run() {
		while (true) {
			try {
				_sender.init_Protocol(this.clientReader());
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
    /**
     * metodo que escribe en el puerto
     * @param pData dato a escribir en el puerto
     */
	public void clientWrite(String pData) {
		
		_writer.println(pData);

	}
    /** 
     * lee el dato que hay en el puerto
     * @return dato del puerto en string
     * @throws IOException
     */
	public String clientReader() throws IOException {
		return _reader.readLine();
	}
}
