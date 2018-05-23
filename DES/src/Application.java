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

    //Method to call either encryption or decryption
    public void run(String[] args)
    {
        //Call function to read data from input file
        readData(args);
        
        //Run either encryption or decryption based on input file
        if(mode.equals("0"))
        {
            encrypt(args);
        }
        else
        {
            decrypt(args);
        }
    }
    
    //Method for encryption
    public void encrypt(String[] args)
    {
        String finalout = "";
        String output = "ENCRYPTION\r\n";
        
        //Initialises various instances of DES methods
        DES0 d0 = new DES0();
        DES1 d1 = new DES1();
        DES2 d2 = new DES2();
        DES3 d3 = new DES3();
        
        //Pads 56 bit key to 64 bits
        key = padKey();
        
        output += "Plaintext P: " + plaintext;
        output += "\r\nKey K: " + plain(key);

        //Array used to hold bit differences for avalanche effect
        int[][] diff = new int[4][17];

        String output2 = "\r\n\r\nAvalanche:\r\nP and Pi under K";
        
        //Matrix of strings to hold value of P at each round of DES, for each of 4 DES types
        String[][] pResults = new String[4][17];
        
        //Loops 4 times to complete each DES method
        for(int j = 0; j < 4; j++)
        {
            Boolean[][] p = copyInput();
            //Chooses which DES method to run
            switch(j)
            {
                case 0: p = d0.DES(p, key, false);
                        pResults[0] = d0.diffCheck();   //Obtains array of strings holding P at each round
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
            
            //Adds ciphertext output by method when DES0 is run
            if(j == 0)
            {
                output += "\r\nCiphertext C: " + plaintext(p);
                finalout += "\r\n" + output + output2;
            }
        }
        
        //Runs each instance of pi through each type of DES, collecting String arrays with message at each round in the process
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
        
        //Used to generate avalanche effect information for p and pi
        //Loops for each version of DES
        for(int i = 0; i < 4; i++)
        {
            //Loops for each round of DES
            for(int j = 0; j < 17; j++)
            {
                int count = 0;
                //Compares strings for p and every pi at current round
                for(int z = 0; z < 64; z++)
                {
                    count += getDifference(pResults[i][j], piResults[z][i][j]);
                }
                //Takes average result and adds it to difference matrix
                count /= 64;
                diff[i][j] = count;
            }
        }
        
        finalout += "\r\nRound     DES0     DES1     DES2     DES3";
        
        //Generates output table to avalanche analysis
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
        
        finalout += "\r\n\r\nAvalanche:\r\nP and P under Ki";
        
        //3D matrix of strings to hold value of P at each round of DES, for each of 4 DES types, for each ki
        String[][][] kiResults = new String[56][4][17];
        
        //Runs each type of DES 56 times, using every instance of ki
        for(int i = 0; i < 56; i ++)
        {
            for(int j = 0; j < 4; j++)
            {
            	Boolean[][] p = copyInput();
            	Boolean[][][] pi = getPi();
                Boolean[][] ki = getKi();
                switch(j)
                {
                    case 0: pi[i] = d0.DES(p, ki[i], false);
                    		kiResults[i][0] = d0.diffCheck();
                            break;
                    case 1: pi[i] = d1.DES(p, ki[i], false);
                    		kiResults[i][1] = d1.diffCheck();
                            break;
                    case 2: pi[i] = d2.DES(p, ki[i], false);
                    		kiResults[i][2] = d2.diffCheck();
                            break;
                    case 3: pi[i] = d3.DES(p, ki[i], false);
                    		kiResults[i][3] = d3.diffCheck();
                            break;
                }
            }
        }
        

        //Used to generate avalanche effect information for k and ki
        //Loops for each version of DES
        for(int i = 0; i < 4; i++)
        {
            //Loops for each round of DES
            for(int j = 0; j < 17; j++)
            {
                int count = 0;
                //Compares strings for p under k and every p under ki for current round
                for(int z = 0; z < 56; z++)
                {
                    count += getDifference(pResults[i][j], kiResults[z][i][j]);
                }
                //Takes average and adds it to difference matrix
                count /= 56;
                diff[i][j] = count;
            }
        }
        
        finalout += "\r\nRound     DES0     DES1     DES2     DES3";
        
        //Generates output for avalanche analysis
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
        
        //Outputs results to file
        outputData(args, finalout);
    }
    
    //Decryption method
    public void decrypt(String[] args)
    {
        DES0 d0 = new DES0();
        Boolean[][] c = copyInput();
        key = padKey();
        
        //Generating output for file
        String finalout = "DECRYPTION\r\nCiphertext C: ";
        finalout += plaintext;
        finalout += "\r\nKey K: ";
        finalout += keyString;
        
        //Performs DES0 on input, with true indicating decryption
        c = d0.DES(c, key, true);
        
        finalout += "\r\nPlaintext P: ";
        finalout += plaintext(c);
        
        //Outputs results to file
        outputData(args, finalout);
    }
    
    //Produces array of message matrices, each with 1 bit different from original
    public Boolean[][][] getPi()
    {
        //Copies original message multiple times
        Boolean[][][] pi = new Boolean[64][16][4];
        for(int i = 0; i < 64; i++)
        {
            pi[i] = copyInput();
        }
        
        //Goes through message arrays, changing next bit in each instance
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
    

    //Produces matrix of keys, each with 1 bit different from original
    public Boolean[][] getKi()
    {
        //Copies original key multiple times
        Boolean[][] ki = new Boolean[56][56];
        for(int i = 0; i < 56; i++)
        {
            ki[i] = copyKey();
        }
        
        //Goes through key array, changing next bit in each instance
        int c = 0;
        for(int i = 0; i < 56; i++)
        {
            ki[c][i] = !ki[c][i];
            c++;
        }
        return ki;
    }
    
    //Method to convert message matrix back to string
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

    //Method to convert key array back to string
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
    
    //Method to output DES data to a file
    public void outputData(String[] args, String output)
    {
        boolean success = true;
        PrintWriter outputStream = null;  
        
        //Attempts to start an output stream to the file name input by the user
        try
        {
            outputStream = new PrintWriter(args[1]);  
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Input filename not found");
            success = false;
        }
        
        //Outputs data to file
        if(success != false)
        {
            outputStream.print(output);
            outputStream.close();
        }
    }
   
    //Method to read input file data
    public void readData(String[] args)
    {
        boolean success = true;
        Scanner inputStream = null;
        String current;
        input = new Boolean[16][4];
        
        //Attempts to start an input stream from the file name input by the user
        try
        {
            inputStream = new Scanner(new File(args[0]));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Input filename not found");
            success = false;
        }
        
        //Runs if input file was found successfully
        if(success != false)
        {
            //Takes first bit to indicate encryption or decryption
            current = inputStream.next();
            mode = current;
            
            //Takes next line as input message
            current = inputStream.next();
            plaintext = current;
            
            //Takes input string and puts it into matrix form for use
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
            
            //Takes next line as key
            current = inputStream.next();
            keyString = current;
            
            //Takes key string and puts it into an array for use
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
    
    //Method to generate a copy of input message
    public Boolean[][] copyInput()
    {
        //Generates empty matrix and copies input message into it
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
    
    //Method to generate a copy of key
    public Boolean[] copyKey()
    {
        //Generates an empty array and copies key into it
        Boolean[] copy = new Boolean[key.length];
        for(int i = 0; i < key.length; i++)
        {
            copy[i] = key[i];
        }
        return copy;
    }
        
    //Method to pad 56 bit key to 64 bits
    public Boolean[] padKey()
    {
        Boolean[] padded = new Boolean[64];
        int j = 0;
        int onebits = 0;
        for(int i = 1; i <= 64; i++)
        {
            //Runs when not up to multiple of 8th bit in empty key array
            if(i % 8 != 0)
            {
                //Adds bit from original key to empty key
                padded[i-1] = key[j];
                j++;
                //Keeps track of bits that are 1 to implement odd parity
                if(padded[i-1] == true)
                {
                    onebits++;
                }
            }
            //Runs when up to multiple of 8th bit in empty key
            else
            {
                //Adds a 1 or a 0 based on parity of previous 7 bits in order to achieve odd parity
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
    
    //Compares 2 strings and returns amount of different bits
    public int getDifference(String p, String pi)
    {
        int count = 0;
        String[] s = p.split("");
        String[] t = pi.split("");
        
        //Cycles through each character of strings and checks if they are the same
        for(int i = 0; i < s.length; i++)
        {
            if(!s[i].equals(t[i]))
            {
                count++;
            }
        }
        return count;
    }
    
    //Method to add whitespace as needed for output table
    //Purely for cosmetic effect to have a well-aligned table
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
