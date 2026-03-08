package Side.Projects.PasswordManager;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class PasswordManager {
    public static String FILE_NAME = "passwords.dat";
    public static SecretKeySpec secretKey;

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter master password");
        String mPassword = scanner.nextLine();

        setKey(mPassword);

        while(true){
            System.out.println("\n1: Add password");
            System.out.println("2. View Password");
            System.out.println("3. Exit");
            System.out.println("Choose Option");

            int choice = Integer.parseInt(scanner.nextLine());

            if(choice == 1){
                System.out.println("Service name: ");
                String service = scanner.nextLine();

                System.out.println("Password: ");
                String password = scanner.nextLine();

                savePassword(service, password);
                System.out.println("saved securely");
            }else if(choice == 2){
                System.out.println("Service name: ");
                String service = scanner.nextLine();

                String password = getPassword(service);
                if(password != null){
                    System.out.println("password: " + password);
                }else{
                    System.out.println("Service not found");
                }
            }else{
                break;
            }
        }

        scanner.close();

    }

    public static void setKey(String myKey) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte [] key = sha.digest(myKey.getBytes("UTF-8"));
        secretKey = new SecretKeySpec(Arrays.copyOf(key,16), "AES");
    }

    public static String encrypt(String strToEncrypt) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }

    public static String decrypt(String strToDecrypt) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }

    public static void savePassword(String service, String password) throws Exception{
        String encrypted = encrypt(password);

        try (FileWriter writer = new FileWriter(FILE_NAME, true)){
             writer.write(service + ":" + encrypted + "\n");
        }
    }

    public static String getPassword(String service) throws Exception{
        File file = new File(FILE_NAME);
        if(!file.exists())return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while((line = reader.readLine()) != null){
            String [] parts = line.split(":");
            if(parts[0].equals(service)){
                reader.close();
                return decrypt(parts[1]);
            }
        }
        reader.close();
        return null;
    }
}
