package roundFonctions;
import core.*;

import core.RoundFonctionSteps;

public class DES1 {
	
	private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
	
	
	//DES roundFonction with missing P
	public Boolean[][] DES(Boolean[][] input, Boolean[] key) {
		
		// E-Table step
		Boolean[][] output = roundFonctionSteps.ETable(input);
		// XOR step
		output = roundFonctionSteps.XOR(output, key);
		// S-box step
		output = roundFonctionSteps.SBox(output);
		
		return output;
	}

}
