package dad.LaLagunaUrbanApp.Bd;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class CryptoUtils {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "1234567890123456"; // Clave secreta para el cifrado

    // Método para encriptar una contraseña
    public static String encryptPassword(String password) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para desencriptar una contraseña
    public static String decryptPassword(String encryptedPassword) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para generar la clave secreta
    private static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    }
/*
    public static void main(String[] args) {
        String originalPassword = "mypassword";
        String encryptedPassword = encryptPassword(originalPassword);
        System.out.println("Contraseña encriptada: " + encryptedPassword);
        String decryptedPassword = decryptPassword(encryptedPassword);
        System.out.println("Contraseña desencriptada: " + decryptedPassword);
    }*/
}
