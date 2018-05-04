package roundFonctions;
import core.*;

public class DES0 {
	
	private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
	
	
	//original DES roundFonction
	public Boolean[] DES(Boolean[] input, Boolean[] key) {
		
		// E-Table step
		Boolean[] output = roundFonctionSteps.ETable(input);
		// XOR step
		output = roundFonctionSteps.XOR(output, key);
		// S-box step
		output = roundFonctionSteps.SBox(output);
		// permutation step
		output = roundFonctionSteps.P(output);
		
		return output;
	}
}
