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
            encrypt(args);
        }
        else
        {
            decrypt(args);
        }
    }
    
    public void encrypt(String[] args)
    {
        String finalout = "";
        String output = "ENCRYPTION\r\n";
        DES0 d0 = new DES0();
        DES1 d1 = new DES1();
        DES2 d2 = new DES2();
        DES3 d3 = new DES3();
        //key = padKey();
        
        output += "Plaintext P: " + plaintext;
        output += "\r\nKey K: " + plain(key);

        int[][] diff = new int[4][17];

        String output2 = "\r\nAvalanche:\r\nP and Pi under K";
        String[][] pResults = new String[4][17];
        
        for(int j = 0; j < 4; j++)
        {
            Boolean[][] p = copyInput();
            switch(j)
            {
                case 0: p = d0.DES(p, key, false);
                        pResults[0] = d0.diffCheck();
                        break;
                case 1: p = d1.DES(p, key, false);
                        pResults[1] = d1.diffCheck();
                        break;
                case 2: p = d2.DES(p, key, false);
                        pResults[2] = d2.diffCheck();
                        break;
                case 3: p = d3.DES(p, key, false);
                        pResults[3] = d3.diffCheck();
                        break;
            }
            if(j == 0)
            {
                output += "\r\nCiphertext C: " + plaintext(p);
                finalout += "\r\n" + output + output2;
            }
        }
        
        String[][][] piResults = new String[64][4][17];
        for(int i = 0; i < 64; i ++)
        {
            for(int j = 0; j < 4; j++)
            {
                Boolean[][][] pi = getPi();
                switch(j)
                {
                    case 0: pi[i] = d0.DES(pi[i], key, false);
                            piResults[i][0] = d0.diffCheck();
                            break;
                    case 1: pi[i] = d1.DES(pi[i], key, false);
                            piResults[i][1] = d1.diffCheck();
                            break;
                    case 2: pi[i] = d2.DES(pi[i], key, false);
                            piResults[i][2] = d2.diffCheck();
                            break;
                    case 3: pi[i] = d3.DES(pi[i], key, false);
                            piResults[i][3] = d3.diffCheck();
                            break;
                }
            }
        }
        
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 17; j++)
            {
                int count = 0;
                for(int z = 0; z < 64; z++)
                {
                    count += getDifference(pResults[i][j], piResults[z][i][j]);
                }
                count /= 64;
                diff[i][j] = count;
            }
        }
        finalout += "\r\nRound     DES0     DES1     DES2     DES3";
        for(int i = 0; i < 17; i++)
        {
            String out = ("   " + i);
            for(int j = 0; j < 4; j++)
            {
                if(j == 0)
                {
                    out += (space(0, i)) + diff[j][i];
                }
                else
                {
                    out += (space(1, diff[j-1][i])) + diff[j][i];
                }
            }
            finalout += "\r\n" + out;
        }
        System.out.println(finalout);
    }
    
    public Boolean[][][] getPi()
    {
        Boolean[][][] pi = new Boolean[64][16][4];
        for(int i = 0; i < 64; i++)
        {
            pi[i] = copyInput();
        }
        
        int c = 0;
        for(int i = 0; i < 16; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                pi[c][i][j] = !pi[c][i][j];
                c++;
            }
        }
        return pi;
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
    
    public void decrypt(String[] args)
    {
        DES0 d0 = new DES0();
        Boolean[][] c = copyInput();
        
        String finalout = "DECRYPTION\r\nCiphertext C: ";
        finalout += plaintext;
        finalout += "\r\nKey K: ";
        finalout += keyString;
        //key = padKey();
        //System.out.println(plain(key));
        //key = reverse(key);
        
        c = d0.DES(c, key, true);
        
        System.out.println("Plaintext: " + plaintext(c));
        
        finalout += "\r\nPlaintext P: ";
        finalout += plaintext(c);
        
        //outputData(args, finalout);
    }
    
    public Boolean[] reverse(Boolean[] k)
    {
        for(int i = 0; i < (k.length)/2; i++)
        {
            boolean temp = k[i];
            k[i] = k[k.length-i-1];
            k[k.length-i-1] = temp;
        }
        return k;
    }
    
    public String plain(Boolean[] k)
    {
        String s = "";
        for(int i = 0; i < k.length; i++)
        {
            if(k[i] == true)
            {
                s += "1";
            }
            else
            {
                s += "0";
            }
        }
        return s;
    }
    
    public void outputData(String[] args, String output)
    {
        boolean success = true;
        PrintWriter outputStream = null;  //Initiates the output stream
        try
        {
            outputStream = new PrintWriter(args[1]);  //Attempts to start an output stream to the file name input by the user
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Input filename not found");
            success = false;
        }
        
        if(success != false)
        {
            outputStream.print(output);
            outputStream.close();
        }
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
    
    public Boolean[] copyKey()
    {
        Boolean[] copy = new Boolean[key.length];
        for(int i = 0; i < key.length; i++)
        {
            copy[i] = key[i];
        }
        return copy;
    }
        
    public Boolean[] padKey()
    {
        Boolean[] padded = new Boolean[64];
        int j = 0;
        int onebits = 0;
        for(int i = 1; i <= 64; i++)
        {
            if(i % 8 != 0)
            {
                padded[i-1] = key[j];
                j++;
                if(padded[i-1] == true)
                {
                    onebits++;
                }
            }
            else
            {
                if(onebits % 2 != 0)
                {
                    padded[i-1] = false;
                }
                else
                {
                    padded[i-1] = true;
                }
                onebits = 0;
            }
        }
        return padded;
    }
    
    public int getDifference(String p, String pi)
    {
        int count = 0;
        String[] s = p.split("");
        String[] t = pi.split("");
        for(int i = 0; i < s.length; i++)
        {
            if(!s[i].equals(t[i]))
            {
                count++;
            }
        }
        return count;
    }
    
    public String space(int i, int j)
    {
        if(i == 0)
        {
            if(j < 10)
            {
                return "       ";
            }
            else
            {
                return "      ";
            }
        }
        else
        {
            if(j < 10)
            {
                return "        ";
            }
            else
            {
                return "       ";
            }
        }
    }
}
