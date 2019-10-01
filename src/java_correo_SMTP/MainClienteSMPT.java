package java_correo_SMTP;

public class MainClienteSMPT {
	public static void main(String[] args) {
		ClienteSMTP Cliente1 = new ClienteSMTP("192.168.0.11", 25, "@correo.alberth.bo");
		Cliente1.enviarCorreo();
	}

}
