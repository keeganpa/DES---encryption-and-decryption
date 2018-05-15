package roundFonctions;
import core.*;

import core.RoundFonctionSteps;

public class DES3 {
	
	private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
	
	
	//DES roundFonction with Permutation missing and S-box replaced by E-1-table step
	public Boolean[][] DES(Boolean[][] input, Boolean[] key) {
		System.out.println("DES3");
		// E-Table step
		Boolean[][] output = roundFonctionSteps.ETable(input);
		// XOR step
		output = roundFonctionSteps.XOR(output, key);
		// E-1-table step
		output = roundFonctionSteps.E1Table(output);
		
		return output;
	}
}
