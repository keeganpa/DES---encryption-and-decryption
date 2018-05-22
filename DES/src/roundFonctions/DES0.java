package roundFonctions;
import core.*;

public class DES0 {
	
	//class containing common to DES0, DES1, DES2 and DES3
	private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
	
	
	//original DES roundFonction
	public Boolean[][] DES(Boolean[][] input, Boolean[] key) {
		//initial permutation
		input = roundFonctionSteps.P(input, 2);
		
		//separation in 2 blocks
		Boolean[][] block1 = java.util.Arrays.copyOfRange(input, 0, 8);
		Boolean[][] block2 = java.util.Arrays.copyOfRange(input, 8, 16);
		
		for (int i = 0; i < 16; i++) {
			//prepare switching blocks at the end of the round
			Boolean[][] newBlock1 = block2;
			
			//prepare key for round
			key = roundFonctionSteps.rotateKey(key, i);
			
			//permute key
			Boolean[] permutedKey = roundFonctionSteps.PK2(key);
			
			// E-Table step
			Boolean[][] output = roundFonctionSteps.ETable(block2);
			// XOR step
			output = roundFonctionSteps.XOR(output, permutedKey);
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
		output[0] = block1[0];
		output[1] = block1[1];
		output[2] = block1[2];
		output[3] = block1[3];
		output[4] = block1[4];
		output[5] = block1[5];
		output[6] = block1[6];
		output[7] = block1[7];
		output[8] = block2[0];
		output[9] = block2[1];
		output[10] = block2[2];
		output[11] = block2[3];
		output[12] = block2[4];
		output[13] = block2[5];
		output[14] = block2[6];
		output[15] = block2[7];
		
		//final inverse permutation
		output = roundFonctionSteps.P(output, 3);

		return output;
	}
}
