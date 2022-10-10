package server;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoMultiServer {
    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {

      try {
           serverSocket = new ServerSocket(4000);
      } catch (IOException e) {
           System.out.println("EchoMultiServer: could not listen on port: 4000, " + e.toString());
           System.exit(1);
      }
      System.out.println("EchoMultiServer listening on port: 4000");

      boolean listening = true;
      while (listening) {
            //EJERCICIO: aceptar una nueva conexi�n
            //EJERCICIO: y crear un Thread para que la gestione
        try {
            new EchoMultiServerThread(serverSocket.accept()).start();
            Thread.sleep(3000);
        } catch (IOException e) {
            System.err.println("Error sending/receiving" + e.getMessage());
            e.printStackTrace();
      }   catch (InterruptedException ex) {
              Logger.getLogger(EchoMultiServer.class.getName()).log(Level.SEVERE, null, ex);
          }

     	
     }

     try {
          serverSocket.close();
     } catch (IOException e) {
          System.err.println("Could not close server socket." + e.getMessage());
     }
   }
}

//----------------------------------------------------------------------------
//  class EchoMultiServerThread
//----------------------------------------------------------------------------

class EchoMultiServerThread extends Thread {
    private EchoObject eo;
    private Socket clientSocket = null;
    private String myURL = "localhost";
    private BufferedReader is = null;
    private PrintWriter os = null;
    private String inputline = new String();

    EchoMultiServerThread(Socket socket) {
        super("EchoMultiServerThread");
        clientSocket = socket;
        eo = new EchoObject();
        try {
        	is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        	os = new PrintWriter(clientSocket.getOutputStream());			
        } catch (IOException e) {
            System.err.println("Error sending/receiving" + e.getMessage());
            e.printStackTrace();
        }
        try {
           myURL=InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
           System.out.println("Unknown Host :" + e.toString());
           System.exit(1);
       }
    }

    public void run() {
       try {
            while ((inputline = is.readLine()) != null) {
            	//EJERCICIO: Invocar el objeto 
            	//EJERCICIO: y devolver la respuesta por el socket 
            	os= new PrintWriter(clientSocket.getOutputStream(), true);
                is= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = is.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        os.println("bye");
                        break;
                    }
                    os.println(inputLine);
                }	
	
            }
            os.close();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error sending/receiving: " + e.getMessage());
            e.printStackTrace();
        }
    }
}