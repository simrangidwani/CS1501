//Simran Gidwani
//CS 1501 Summer 2018
import java.util.*;

/**
 *
 * @author simrangidwani
 */
public class Substitute implements SymCipher {   
    byte [] key;
    byte [] reversedBytes;
    Random rand = new Random();
    
    public Substitute(byte [] key2)
    {
        key = key2;     
    }
    
    public Substitute()
    {
        key = new byte[256];
        rand.nextBytes(key);
    }
    
    public byte [] getKey()
    {
        return key;
    }
    
    public byte [] encode(String S)
    {
        int index;
        System.out.println("Original String: " + S);
        byte [] stringKeys = S.getBytes();
        System.out.println("Array of bytes: ");
        for (int i = 0; i< stringKeys.length; i++)
        {
            System.out.println(stringKeys[i] + " ");           
        }
        
        byte [] encrypted = new byte[stringKeys.length];
        
        for (int i = 0; i < encrypted.length; i++)
        {
            index = (int) S.charAt(i);
            encrypted[i] = (byte)(key[index] & 0xff);
        }
        
        System.out.println("Encrypted array of bytes: ");
        for (int i = 0; i < encrypted.length; i++)
        {
            System.out.println(encrypted[i] + " ");
        }
        return encrypted;
    }
    
    public String decode(byte [] bytes)
    {
        System.out.println("Array of bytes recieved: ");
        for (int i = 0; i < bytes.length; i++)
        {
            System.out.println(bytes[i] + " ");
        }
        
        byte [] decrypted = new byte[bytes.length];
        char [] c = new char[256];
        
        for (int i = 0; i < c.length; i++)
        {
            c[i] = (char) i;
        }      
        byte [] origBytes = new String(c).getBytes();
        byte [] inverse = new byte[256];
        int index;
        //inverse mapping
        for (int i =0; i < c.length; i++)
        {
            index = (int)(key[i] & 0xff);
            inverse[index] = origBytes[i];
        }
        //decoding
        for (int i=0; i< bytes.length; i++)
        {
            index = (int)(bytes[i] & 0xff);
            decrypted[i] = inverse[index];
        }
        
        System.out.println("Decrypted array of bytes: ");
        for (int i = 0; i < decrypted.length; i++)
        {
            System.out.println(decrypted[i] + " ");
        }
        
        char [] decoded = new char[decrypted.length];
        for (int i = 0; i < decrypted.length; i++)
        {
            decoded[i]= (char)(decrypted[i] & 0xff);
        }
        
        System.out.println("Decrypted String: " + new String(decoded));
        return new String(decoded);
    }
}
