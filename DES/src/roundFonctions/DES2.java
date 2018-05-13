package roundFonctions;
import core.*;

import core.RoundFonctionSteps;

public class DES2 {
	
	private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
	
	
	//DES roundFonction with S-box replaced by E-1 table step
	public Boolean[][] DES(Boolean[][] input, Boolean[] key) {
		
		// E-Table step
		Boolean[][] output = roundFonctionSteps.ETable(input);
		// XOR step
		output = roundFonctionSteps.XOR(output, key);
		// E-1-Table step
		output = roundFonctionSteps.E1Table(output);
		// permutation step
		output = roundFonctionSteps.P(output);
		
		return output;
	}

}
