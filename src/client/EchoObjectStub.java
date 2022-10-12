package client;

import java.net.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import rmi.*;

public class EchoObjectStub implements EchoInt {

  private Socket echoSocket = null;
  private PrintWriter os = null;
  private BufferedReader is = null;
  private String host = "127.0.0.1";
  private int port=4000;
  private String output = "Error";

  
  public EchoObjectStub(String host, int port) {
    this.host= host; this.port =port;
  }


  public String echo(String input) {
    connect();
    if (echoSocket != null && os != null && is != null) {
  	try {
             os.println(input);
             os.flush();
             output= is.readLine();
      } catch (IOException e) {
        System.err.println("I/O failed in reading/writing socket");
      }
    }
    disconnect();
    return output;
  }

  private synchronized void connect()
  {
	//EJERCICIO: Implemente el metodo connect
	try {
          echoSocket = new Socket(host, port);
          is = new BufferedReader( new InputStreamReader(echoSocket.getInputStream()));
          os = new PrintWriter(echoSocket.getOutputStream());
        } catch (Exception e) {
          System.out.println("Error de conexion: "+e.getMessage());
          echoSocket = null;   
          is = null;
          os = null; 
        }		  
  }

    private synchronized void disconnect(){ 
	//EJERCICIO: Implemente el metodo disconnect
	 
        try {
            echoSocket.close();
	} 
        catch (IOException e) {
            System.out.print(e.getMessage());
	}  
        echoSocket = null;   
        is = null;
        os = null;   
    }
}