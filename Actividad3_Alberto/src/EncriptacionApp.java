import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.GeneralSecurityException;
import java.util.Scanner;

public class EncriptacionApp {

	private static String mensajeCifrado;
	private static byte[] bytesFraseCifrada;
	
	//Para poder leer las opciones del menu	que estan en un metodo estatico
	private static Scanner leer;
	static {
			leer = new Scanner(System.in);
	}
	
	// Usuarios con nombre de usuario y contraseña hasheada
    private static Usuario[] usuarios = {
            new Usuario("usuario1", "contraseña1"),
            new Usuario("usuario2", "contraseña2"),
            new Usuario("usuario3", "contraseña3")
    };
    
    private static Usuario usuarioAutenticado;
	

    public static void main(String[] args) {
        
        try {
        	
        	// Autenticación de usuario
            autenticarUsuario();
        	
        	KeyGenerator generador = KeyGenerator.getInstance("AES");
        	//Generamos la clave simetrica.
        	SecretKey paloEspartano = generador.generateKey();
        	//Objeto que nos permitira encriptar o desencriptar a partir de una
        	Cipher cifrador = Cipher.getInstance("AES");
        	
        	boolean continuar = true;
        	
	        while (continuar) {
	
	            int opcion = menu();
	          //Si la opcion está fuera del rango de opciones se repetira el menu
				while (opcion<1 || opcion>5){
					opcion = menu();
				}

	            switch (opcion) {
	                case 1:
	            
	                	leer.nextLine(); // Limpiar el búfer de nueva línea
	                    System.out.println("Ingrese la frase a encriptar:");
	                    String fraseEncriptar = leer.nextLine();
	                   // encriptarFrase(fraseEncriptar, cifrador);
	                    //Ahora el cifrador lo configuramos para que use la clave simetrica
	        			//para encriptar	                    
	        			cifrador.init(Cipher.ENCRYPT_MODE, paloEspartano);
	        			//Pasamos a bytes la frase original
	        			byte[] bytesMensajeOriginal = fraseEncriptar.getBytes();
	        			//Ciframos la frase
	        			bytesFraseCifrada = cifrador.doFinal(bytesMensajeOriginal);
	        			mensajeCifrado = new String(bytesFraseCifrada);
	        			System.out.println("Frase encriptada: " + mensajeCifrado);
	        			
	                	//encriptarFrase(cifrador, paloEspartano);
	                    break;
	
	                case 2:
	                	
	                    if (mensajeCifrado == null) {
	                        System.out.println("No hay frase encriptada.");
	                    } else {
	                    	System.out.println("\nFrase encriptada: " + mensajeCifrado);
	                    	cifrador.init(Cipher.DECRYPT_MODE, paloEspartano);
	                    	byte[] bytesFraseDescifrada = cifrador.doFinal(bytesFraseCifrada);
	                    	System.out.println("Frase Descifrada: " + new String(bytesFraseDescifrada));
	                    }
	                    
	                   // desencriptarFrase(cifrador, paloEspartano,mensajeCifrado);
	                    break;
	
	                case 3:
	                    System.out.println("Saliendo del programa.");
	                    //System.exit(0);
	                    continuar=false;
	
	            }
	        }
        }catch (GeneralSecurityException gse) {
			System.out.println("Algo ha fallado.." + gse.getMessage());
			gse.printStackTrace();
		}
    }
    
    /*
    private static void encriptarFrase(Cipher cifrador, SecretKey paloEspartano) throws GeneralSecurityException {
        leer.nextLine(); // Limpiar el búfer de nueva línea
        System.out.println("Ingrese la frase a encriptar:");
        String fraseEncriptar = leer.nextLine();

        cifrador.init(Cipher.ENCRYPT_MODE, paloEspartano);
        byte[] bytesMensajeOriginal = fraseEncriptar.getBytes();
        byte[] bytesFraseCifrada = cifrador.doFinal(bytesMensajeOriginal);

        String mensajeCifrado = new String(bytesFraseCifrada);
        System.out.println("Frase encriptada: " + mensajeCifrado);
    }
    
    private static void desencriptarFrase(Cipher cifrador, SecretKey paloEspartano, String mensajeCifrado) throws GeneralSecurityException {
        if (mensajeCifrado == null) {
            System.out.println("No hay frase encriptada.");
        } else {
        	System.out.println("\nFrase encriptada: " + mensajeCifrado);
        	cifrador.init(Cipher.DECRYPT_MODE, paloEspartano);
        	byte[] bytesFraseDescifrada = cifrador.doFinal(bytesFraseCifrada);
        	System.out.println("Frase Descifrada: " + new String(bytesFraseDescifrada));
        }
    }*/
    
    
    private static int menu() {
		int opcion = 0;
		System.out.println("----------------------------------------------------");
		System.out.println("|                      MENU                        |");
		System.out.println("----------------------------------------------------");
        System.out.println("1. Encriptar frase");
        System.out.println("2. Desencriptar frase");
        System.out.println("3. Salir del programa");
		System.out.println("----------------------------------------------------");
		System.out.println("Introduzca una opción del 1 al 2, si quiere salir 3");
		System.out.println("----------------------------------------------------");
		
		try {
			opcion = leer.nextInt();
			
		} catch (java.util.InputMismatchException e) {
	        // Atrapar la excepción si se ingresa algo que no es un entero
	        System.out.println("Entrada no válida. Ingrese un número entero.");
	        leer.next(); // Limpiar el búfer de entrada para evitar un bucle infinito
	    }
		
		if (opcion<1 || opcion > 3) {
			System.out.println("OPCION INCORRECTA");
		}
		
		return opcion;	

    }
    
    private static void autenticarUsuario() {
        int intentos = 0;

        while (intentos < 3) {
            System.out.println("Ingrese el nombre de usuario:");
            String nombreUsuario = leer.next();
            System.out.println("Ingrese la contraseña:");
            String contraseña = leer.next();
            //Llamo al metodo que autenticara el nombre de usuario y contraseña puestos
            usuarioAutenticado = autenticar(nombreUsuario, contraseña);

            if (usuarioAutenticado != null) {
                System.out.println("Bienvenido, " + usuarioAutenticado.getNombreUsuario() + "!");
            	//System.out.println("Bienvenido, " + usuarioAutenticado.getPasswordHasheada() + "!");
                break;
            } else {
            	intentos++;
            	if (intentos<3) {
            		System.out.println("Nombre de usuario o contraseña incorrectos. Intente de nuevo.");
            	}else {
            		System.out.println("Nombre de usuario o contraseña incorrectos.");

            	}
                
                
            }
        }

        if (usuarioAutenticado == null) {
            System.out.println("Máximo de intentos alcanzado. Saliendo del programa.");
            System.exit(0);
        }
    }

    private static Usuario autenticar(String nombreUsuario, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.verificarPassword(contraseña)) {
                return usuario;
            }
        }
        return null;
    }
}
