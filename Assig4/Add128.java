//Simran Gidwani
//CS 1501 Summer 2018
import java.util.*;

/**
 *
 * @author simrangidwani
 */
public class Add128 implements SymCipher {
    private byte [] key;
    private Random rand = new Random();
    
    
    public Add128(byte [] key2)
    {
        key = key2;
    }
    
    public Add128()
    {
        key = new byte[128];
        rand.nextBytes(key);
    }
           
    public byte [] getKey()
    {
        return key;
    }
    
    public byte [] encode(String S)
    {
        //original string
        System.out.println("Original String: " + S); 
        byte [] stringKeys = S.getBytes();
        
        //stores in array
        System.out.println("Array of bytes: ");
        for (int i= 0; i < stringKeys.length; i++)
        {
            System.out.println(stringKeys[i] + " ");
        }
        
        byte [] encrypted = new byte[stringKeys.length];
        
        //encrypted bytes
        for (int k = 0; k < encrypted.length; k++)
        {
            encrypted[k] = (byte)(stringKeys[k] + key[k %128]); 
        }
        
        System.out.println("Encrypted array of bytes: ");
        for (int j = 0; j < encrypted.length; j++)
        {
            System.out.println(encrypted[j] + " ");
        }
        
        return encrypted;       
    }
    
    public String decode(byte [] bytes)
    {
        //encrypted bytes
        System.out.println("Array of bytes recieved: ");
        for (int i = 0; i < bytes.length; i++)
        {
            System.out.println(bytes[i] + " ");
        }
        
        byte [] decrypted = new byte[bytes.length];
        for (int j = 0; j < bytes.length; j ++)
        {
            decrypted[j] = (byte)(bytes[j] - key[j%128]);
        }
        
        //decrypted array
        System.out.println("Decrypted array of bytes: ");
        for (int j = 0; j < decrypted.length; j++)
        {
            System.out.println(decrypted[j] + " ");
        }
        
        //original string
        System.out.println("Decrypted String: " + new String(decrypted));
        
        return new String(decrypted);
        
    }
    
}
