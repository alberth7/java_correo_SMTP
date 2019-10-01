package java_correo_SMTP;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSMTP {

	String host;
	int puerto;
	Socket cliente;
	PrintStream mensajeRequest;
	Scanner mensajeResponse;
	String dominioCorreo;
	String mailFrom;
	String rcptTo;	
	String subject;
	String mensaje;
	
public ClienteSMTP(String h, int p, String d) {
	puerto = p;
	host = h;
	dominioCorreo = d;
}

public void enviarCorreo() {
	try {
		
		Scanner leer = new Scanner(System.in);
		cliente = new Socket(host, puerto);
		mensajeRequest = new PrintStream(cliente.getOutputStream());
		mensajeResponse = new Scanner(cliente.getInputStream());
		System.out.println(mensajeResponse.nextLine());
		
		System.out.println("Enviar de: ");
		mailFrom = leer.nextLine();
		mensajeRequest.println("mail from: " + mailFrom + dominioCorreo);
		System.out.println(mensajeResponse.nextLine());
		
		
		boolean sw = true;
		while(sw) {
			
			System.out.println("Enviar correo a:  ");
			rcptTo = leer.nextLine();
			mensajeRequest.println("rcpt to: " + rcptTo + dominioCorreo);
			//System.out.println(mensajeResponse.nextLine());
			
			String res[] = mensajeResponse.nextLine().split(" ");
			if(res[0].equals("250")) {
				//System.out.println("yes usuario  existente");
				mensajeRequest.println("data");
				res = mensajeResponse.nextLine().split(" ");
				if(res[0].equals("354")) {
					
					System.out.println("subject: ");
					subject = leer.nextLine();
					mensajeRequest.println("subject: " + subject);
					
					System.out.println("mensaje:");
					mensaje = leer.nextLine();
					mensajeRequest.println(mensaje);
					mensajeRequest.println(".");
					
					res = mensajeResponse.nextLine().split(" ");
					if(res[0].equals("250")) {
						System.out.println(".... mensaje enviado a " + rcptTo + " correctamente");
						
						mensajeRequest.println("quit");
						System.out.println(mensajeResponse.nextLine());
						System.out.println("------------------");
						sw = false;
						finalizar();					
					}else {
						System.out.println("ocurio un error en el mensaje");
					}			
					
				}else {
					
					System.out.println("ocurio un error en el mensaje");
				}
		
			}else {
				System.out.println("error el usuario " + rcptTo + " no exsite, intente de nuevo....");
			}
 			
		}
		
		
		
	} catch (Exception e) {
		e.printStackTrace();
		finalizar();
	}
	
}


private void finalizar() {

try {
	cliente.close();
	mensajeRequest.close();
	mensajeResponse.close();
	System.out.println("conexion cerrada y finalizada");
} catch (Exception e) {
	e.printStackTrace();
}	
}


}

