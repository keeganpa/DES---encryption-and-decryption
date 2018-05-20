import java.io.*;
import java.util.*;
import roundFonctions.*;

public class Application
{
    Boolean[][] input;
    Boolean[] key;
    String mode;
    String plaintext;
    String keyString;
    String ciphertext;

    
    public static void main(String[] args)
    {
        Application app = new Application();
        app.run(args);
    }

    public void run(String[] args)
    {
        readData(args);
        if(mode.equals("0"))
        {
            encrypt();
        }
        else
        {
            decrypt();
        }
    }
    
    public void encrypt()
    {
        String output = "ENCRYPTION\n";
        DES0 d0 = new DES0();
        DES1 d1 = new DES1();
        DES2 d2 = new DES2();
        DES3 d3 = new DES3();
        
        output += "Plaintext P: " + plaintext;
        output += "\nKey K: " + keyString;
        
        int[][] diff = new int[4][17];

        String output2 = "\nAvalanche:\nP and Pi under K\n";
        
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
                            pi = d0.DES(pi, key);
                            System.out.println("newcase");
                            break;
                    case 1: p = d1.DES(p, key);
                            pi = d1.DES(pi, key);
                            break;
                    case 2: p = d2.DES(p, key);
                            pi = d2.DES(pi, key);
                            break;
                    case 3: p = d3.DES(p, key);
                            pi = d3.DES(pi, key);
                            break;
                }
                diff[j][i] = getDifference(p, pi);
            }
            if(j == 0)
            {
                output += "\nCiphertext C: " + plaintext(p);
                System.out.println(output + output2);
            }
        }
        System.out.println("Round     DES0     DES1     DES2     DES3");
        for(int i = 0; i < 17; i++)
        {
            String out = ("    " + i);
            for(int j = 0; j < 4; j++)
            {
                out += ("       " + diff[j][i]);
            }
            System.out.println(out);
        }
    }
    
    public String plaintext(Boolean[][] input)
    {
        String output = "";
        for(int i = 0; i < input.length; i++)
        {
            for(int j = 0; j < input[0].length; j++)
            {
                if(input[i][j] == false)
                {
                    output += "0";
                }
                else
                {
                    output += "1";
                }
            }
        }
        return output;
    }
    
    public void decrypt()
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
        input = new Boolean[16][4];
        
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
            mode = current;
            current = inputStream.next();
            plaintext = current;
            String[] split = current.split("");
            int count = 0;
            for(int i = 0; i < 16; i++)
            {
                for(int j = 0; j < 4; j++)
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
            key = new Boolean[keySplit.length];
            for(int i = 0; i < keySplit.length; i++)
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
        Boolean[][] copy = new Boolean[16][4];
        for(int i = 0; i < 16; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                 copy[i][j] = input[i][j];   
            }
        }
        return copy;
    }
    
    public int getDifference(Boolean[][] p, Boolean[][] pi)
    {
        int count = 0;
        for(int i = 0; i < p.length; i++)
        {
            for(int j = 0; j < p[0].length; j++)
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
