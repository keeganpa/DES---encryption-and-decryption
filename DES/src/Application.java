import java.io.*;
import java.util.*;
import roundFonctions.*;

public class Application
{
    Boolean[][] input;
    Boolean[] key;
    String plaintext;
    String keyString;
    String ciphertext;

    
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter: e for encryption, d for decryption");
        Application app = new Application();
        String s = keyboard.next();
        switch(s)
        {
            case "e": app.encrypt(args);
                      break;
            case "d": app.decrypt(args);
                      break;
            default:  System.out.println("Invalid Selection");
                      break;
        }
    }

    public void encrypt(String[] args)
    {
        String output = "ENCRYPTION\n";
        DES0 d0 = new DES0();
        DES1 d1 = new DES1();
        DES2 d2 = new DES2();
        DES3 d3 = new DES3();
        readData(args);
        output += "Plaintext P: " + plaintext;
        output += "\nKey K: " + keyString;
        
        int[][] diff = new int[4][17];

        String output2 = "Avalanche:\nP and Pi under K\n";
        
        for(int j = 0; j < 4; j++)
        {
            Boolean[][] p = copyInput();
            Boolean[][] pi = copyInput(); 
            pi[0][0] = !pi[0][0];
            diff[j][0] = getDifference(p, pi);
            for(int i = 1; i <= 16; i++)
            {
                switch(j)
                {
                    case 0: p = d0.DES(p, key);
                            pi = d0.DES(p, key);
                            break;
                    case 1: p = d1.DES(p, key);
                            pi = d1.DES(p, key);
                            break;
                    case 2: p = d2.DES(p, key);
                            pi = d2.DES(p, key);
                            break;
                    case 3: p = d3.DES(p, key);
                            pi = d3.DES(p, key);
                            break;
                }
                diff[j][i] = getDifference(p, pi);
            }
        }
    }
    
    public void decrypt(String[] args)
    {
        DES0 d0 = new DES0();
        DES1 d1 = new DES1();
        DES2 d2 = new DES2();
        DES3 d3 = new DES3();
    }
   
    public void readData(String[] args)
    {
        boolean success = true;
        Scanner inputStream = null;
        String current;        
        input = new Boolean[4][16];
        key = new Boolean[56];
        
        try
        {
            inputStream = new Scanner(new File(args[0]));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Input filename not found");
            success = false;
        }
        
        
        if(success != false)
        {
            current = inputStream.next();
            plaintext = current;
            String[] split = current.split("");
            int count = 0;
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 16; j++)
                {
                    switch(split[count])
                    {
                        case "0": input[i][j] = false;
                                  break;
                        case "1": input[i][j] = true;
                                  break;
                    }
                    count++;
                }
            }
            
            current = inputStream.next();
            keyString = current;
            String[] keySplit = current.split("");
            for(int i = 0; i < 56; i++)
            {
                switch(keySplit[i])
                {
                    case "0": key[i] = false;
                              break;
                    case "1": key[i] = true;
                              break;
                }
            }
        }
    }
    
    public Boolean[][] copyInput()
    {
        Boolean[][] copy = new Boolean[4][16];
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                 copy[i][j] = input[i][j];   
            }
        }
        return copy;
    }
    
    public int getDifference(Boolean[][] p, Boolean[][] pi)
    {
        int count = 0;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                 if(p[i][j] != pi[i][j])
                 {
                     count++;
                 }
            }
        }
        return count;
    }
}
