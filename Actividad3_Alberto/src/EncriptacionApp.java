import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Scanner;

public class EncriptacionApp {

	private static String mensajeCifrado;
	private static byte[] bytesFraseCifrada; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
        	
        	KeyGenerator generador = KeyGenerator.getInstance("AES");
        	//Generamos la clave simetrica.
        	SecretKey paloEspartano = generador.generateKey();
        	//Objeto que nos permitira encriptar o desencriptar a partir de una
        	Cipher cifrador = Cipher.getInstance("AES");
        	
        	

	        while (true) {
	            System.out.println("Menú:");
	            System.out.println("1. Encriptar frase");
	            System.out.println("2. Desencriptar frase");
	            System.out.println("3. Salir del programa");
	
	            int opcion = scanner.nextInt();
	            scanner.nextLine(); // Consumir el salto de línea
	
	            switch (opcion) {
	                case 1:
	                    System.out.println("Ingrese la frase a encriptar:");
	                    String fraseEncriptar = scanner.nextLine();
	                   // encriptarFrase(fraseEncriptar, cifrador);
	                    //Ahora el cifrador lo configuramos para que use la clave simetrica
	        			//para encriptar	                    
	        			cifrador.init(Cipher.ENCRYPT_MODE, paloEspartano);
	        			//Pasamos a bytes la frase original
	        			byte[] bytesMensajeOriginal = fraseEncriptar.getBytes();
	        			//Ciframos la frase
	        			bytesFraseCifrada = cifrador.doFinal(bytesMensajeOriginal);
	        			mensajeCifrado = new String(bytesFraseCifrada);
	        			System.out.println(mensajeCifrado);
	                    break;
	
	                case 2:
	                    if (mensajeCifrado == null) {
	                        System.out.println("No hay frase encriptada.");
	                    } else {
	                    	cifrador.init(Cipher.DECRYPT_MODE, paloEspartano);
	                    	byte[] bytesFraseDescifrada = cifrador.doFinal(bytesFraseCifrada);
	                    	System.out.println("Frase Descifrada: " + new String(bytesFraseDescifrada));
	                    }
	                    break;
	
	                case 3:
	                    System.out.println("Saliendo del programa.");
	                    System.exit(0);
	
	                default:
	                    System.out.println("Opción no válida. Intente de nuevo.");
	            }
	        }
        }catch (GeneralSecurityException gse) {
			System.out.println("Algo ha fallado.." + gse.getMessage());
			gse.printStackTrace();
		}
    }
    
    /*

    private static String encriptarFrase(String frase, Cipher cifrador) {
        try {
        	


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String desencriptarFrase(Cipher cifrador) {

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
