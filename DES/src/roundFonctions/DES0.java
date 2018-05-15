package roundFonctions;
import core.*;

public class DES0 {
	
	private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
	
	
	//original DES roundFonction
	public Boolean[][] DES(Boolean[][] input, Boolean[] key) {
		System.out.println("DES0");
		//todo initial permutation
		
		Boolean[][] block1 = java.util.Arrays.copyOfRange(input, 0, 2);
		Boolean[][] block2 = java.util.Arrays.copyOfRange(input, 2, 4);

		// E-Table step
		Boolean[][] output = roundFonctionSteps.ETable(block2);
		// XOR step
		output = roundFonctionSteps.XOR(output, key);
		// S-box step
		output = roundFonctionSteps.SBox(output);
		// permutation step
		output = roundFonctionSteps.P(output);
		
		block1 = block2;
		block2 = roundFonctionSteps.XOR(block1, output);
		
		
		//todo inver initial permutation
		return output;
	}
}
