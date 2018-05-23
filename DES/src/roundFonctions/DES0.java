package roundFonctions;
import java.util.Arrays;

import core.*;

public class DES0 {
    
    //class containing common to DES0, DES1, DES2 and DES3
    private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
    
    
    //original DES roundFonction
    public Boolean[][] DES(Boolean[][] input, Boolean[] key, Boolean decryption) {
        //initial permutation
        input = roundFonctionSteps.P(input, 2);
        

        //initial key reduction
        Boolean[] newKey = roundFonctionSteps.PK1(key);
        Boolean[][] permutedKeys = new Boolean[16][48];
        
        for(int i = 0; i < 16; i++)
        {
            newKey = roundFonctionSteps.rotateKey(newKey, i, false);
            Boolean[] permutedKey = roundFonctionSteps.PK2(newKey);
            permutedKeys[i] = permutedKey;
        }

        if(decryption == true)
        {
            permutedKeys = reverse(permutedKeys);
        }
        
        //separation in 2 blocks
        Boolean[][] block1 = java.util.Arrays.copyOfRange(input, 0, 8);
        Boolean[][] block2 = java.util.Arrays.copyOfRange(input, 8, 16);
        
        for (int i = 0; i < 16; i++) {
            //prepare switching blocks at the end of the round
            Boolean[][] newBlock1 = block2;
            
            // E-Table step
            Boolean[][] output = roundFonctionSteps.ETable(block2);

            // XOR step
            output = roundFonctionSteps.XOR(output, permutedKeys[i]);

            // S-box step
            output = roundFonctionSteps.SBox(output);

            // permutation step
            output = roundFonctionSteps.P(output, 1);
            
            //switch the blocks for next round
            block2 = roundFonctionSteps.XOR(block1, output);
            block1 = newBlock1;
        }
        
        
        //get the blocks together again
        Boolean[][] output = new Boolean[16][4];
        output[0] = block2[0];
        output[1] = block2[1];
        output[2] = block2[2];
        output[3] = block2[3];
        output[4] = block2[4];
        output[5] = block2[5];
        output[6] = block2[6];
        output[7] = block2[7];
        output[8] = block1[0];
        output[9] = block1[1];
        output[10] = block1[2];
        output[11] = block1[3];
        output[12] = block1[4];
        output[13] = block1[5];
        output[14] = block1[6];
        output[15] = block1[7];
        
        //final inverse permutation
        output = roundFonctionSteps.P(output, 3);

        return output;
    }
    
    public Boolean[][] reverse(Boolean[][] k)
    {
        for(int i = 0; i < (k.length)/2; i++)
        {
            Boolean[] temp = k[i];
            k[i] = k[k.length-i-1];
            k[k.length-i-1] = temp;
        }
        return k;
    }
}
