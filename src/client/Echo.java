package client;

import java.io.*;

import rmi.EchoInt;
import server.EchoObject;

public class Echo {
  private static EchoInt eo;
  
  public static void main(String[] args) {
 
    //EJERCICIO: crear una instancia del stub
    eo = new EchoObjectStub("127.0.0.1",4000);
	  
    BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
    PrintWriter stdOut = new PrintWriter(System.out);
    String input,output,fin;
    
    try {
    	//Bucle que lee de teclado, invoca el eco y escribe respuesta en la pantalla:
    	input="";
    	fin="fin";
    	while(!input.equals(fin)) {
    		stdOut.println("Escriba cadena para invocar su eco...");
    		stdOut.flush();
        	input = stdIn.readLine(); //Lee cadena introducida por teclado
			//EJERCICIO: Invocar para la cadena leida el metodo echo del stub
        	output = eo.echo(input); 
        	stdOut.println(output); //Escribe la respuesta del eco en la pantalla
    		stdOut.flush();
        }  	
    } catch (IOException e) {
    	System.err.println("I/O failed for connection to host: "+args[0]);
    }
  }
}