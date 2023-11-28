import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

class Usuario {
    private String nombreUsuario;
    private String passwordHasheada;

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.passwordHasheada = hashearPassword(password);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPasswordHasheada() {
        return passwordHasheada;
    }
    
    //Metodo que entra como argumento la contraseña normal, la hashea y la compara con la que tiene el objeto
    public boolean verificarPassword(String password) {
    	// hashea la contraseña pasada por teclado y la compara con la que tiene el objeto
        return passwordHasheada.equals(hashearPassword(password));
    }

    private String hashearPassword(String password) {
        //lógica para hashear la contraseña aquí (puede ser con MessageDigest y Base64)
    	byte[] passwordByte = password.getBytes();
    	try {
    		//Creamos un objeto MessageDigest a través del método estático 
    		//getInstance() al que se le pasa el tipo de algoritmo que vamos a 
    		//utilizar.
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(passwordByte);
			byte[] resumen = md.digest();
			//Pasamos a la codificación BASE 64 si queremos reducir el alfa
			//alfabeto resultante. Puede ser util si queremos guardar la información
			//o enviar la información.
			String mensajeHashBase64 = Base64.getEncoder().encodeToString(resumen);
	    	return mensajeHashBase64; 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

    	
    }
}