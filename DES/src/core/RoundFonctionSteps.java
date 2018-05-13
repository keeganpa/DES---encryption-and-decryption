package core;

public class RoundFonctionSteps {
	
	private int [][] SBOX1=
	    {{14 ,4 ,13 ,1 ,2 ,15 ,11 ,8 ,3 ,10 ,6 ,12 ,5 ,9 ,0 ,7},
	     {0 ,15 ,7 ,4 ,14 ,2 ,13 ,1 ,10 ,6 ,12 ,11 ,9 ,5 ,3 ,8},
	     {4 ,1 ,14 ,8 ,13 ,6 ,2 ,11 ,15 ,12 ,9 ,7 ,3 ,10 ,5 ,0},
	     {15, 12, 8, 2, 4, 9, 1, 7 ,5, 11, 3, 14, 10, 0, 6, 13}
	    };
	     
	private int [][] SBOX2=
	    {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12 ,0, 5, 1},
	     {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9 ,11, 5},
	     {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
	     {13 ,8 ,10 ,1 ,3 ,15 ,4 ,2 ,11 ,6 ,7 ,12 ,0 ,5 ,14 ,9}
	    };
	     
	private int [][] SBOX3={{10 ,0 ,9 ,14 ,6 ,3 ,15 ,5 ,1 ,13 ,12 ,7 ,11 ,4 ,2 ,8},
	    {13, 7, 0, 9, 3, 4, 6, 10, 2 ,8 ,5 ,14, 12, 11, 15, 1},
	    {13, 6, 4, 9, 8 ,15 ,3 ,0, 11, 1, 2, 12, 5, 10, 14, 7},
	    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
	    };
	     
	private int [][] SBOX4={{7 ,13 ,14 ,3 ,0 ,6 ,9 ,10 ,1 ,2 ,8 ,5 ,11 ,12 ,4 ,15},
	    {13 ,8 ,11 ,5, 6 ,15, 0 ,3 ,4 ,7 ,2 ,12 ,1 ,10 ,14 ,9},
	    {10, 6, 9, 0, 12, 11, 7 ,13, 15, 1, 3, 14, 5, 2, 8, 4},
	    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4 ,5 ,11 ,12, 7, 2, 14}
	    };
	     
	private int [][] SBOX5={{2 ,12 ,4 ,1 ,7 ,10 ,11 ,6 ,8 ,5 ,3 ,15 ,13 ,0 ,14 ,9},
	    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3 ,9 ,8 ,6},
	    {4 ,2, 1, 11, 10, 13, 7 ,8 ,15, 9, 12, 5, 6, 3, 0, 14},
	    {11, 8, 12 ,7, 1, 14 ,2 ,13 ,6 ,15, 0 ,9, 10, 4, 5, 3}
	    };
	     
	private int [][] SBOX6={{12 ,1 ,10 ,15, 9 ,2, 6 ,8 ,0 ,13 ,3 ,4 ,14 ,7 ,5 ,11},
	    {10, 15, 4 ,2 ,7 ,12 ,9, 5, 6, 1, 13 ,14 ,0, 11, 3 ,8},
	    {9, 14, 15, 5, 2 ,8 ,12, 3, 7 ,0 ,4 ,10, 1, 13 ,11 ,6},
	    {4, 3, 2, 12, 9, 5, 15 ,10 ,11, 14, 1, 7, 6, 0, 8, 13}
	    };
	     
	private int [][] SBOX7={ {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7 ,5 ,10 ,6 ,1},
	    {13, 0, 11, 7, 4, 9, 1, 10, 14 ,3 ,5, 12, 2, 15, 8, 6},
	    {1, 4, 11, 13, 12, 3, 7 ,14, 10, 15, 6, 8, 0, 5, 9, 2},
	    {6, 11, 13, 8 ,1 ,4, 10, 7, 9, 5 ,0 ,15 ,14, 2, 3, 12}
	    };
	    
	private int [][] SBOX8={ {13, 2, 8, 4, 6, 15, 11, 1 ,10, 9, 3, 14, 5, 0, 12, 7},
	    {1, 15, 13, 8 ,10, 3 ,7 ,4 ,12, 5, 6 ,11, 0, 14, 9, 2},
	    {7, 11, 4, 1 ,9 ,12 ,14 ,2 ,0 ,6 ,10, 13 ,15 ,3 ,5 ,8},
	    {2, 1, 14, 7 ,4 ,10, 8 ,13, 15, 12, 9, 0, 3, 5, 6, 11}
	    };
	
	private int[][][] SBOX = {SBOX1, SBOX2, SBOX3, SBOX4, SBOX5, SBOX6, SBOX7, SBOX8};
	    
	private int [][] P={{16 ,7 ,20 ,21},
			{29, 12, 28, 17},
		    {1, 15, 23, 26},
		    {5, 18, 31, 10},
		    {2 ,8 ,24 ,14},
		    {32, 27, 3, 9},
		    {19, 13, 30, 6},
		    {22, 11, 4, 25}
		    };
	
	//ETable step
	public Boolean[][] ETable(Boolean[][] input) {
		int inputRows = input.length;
		int inputCols = input[0].length;
		Boolean[][] output = new Boolean[inputRows][inputCols];
		for (int i = 0; i < inputRows; i++) {
			for (int j = 0; j < inputCols; j++) {
				if (j<15) {
					output[i][j+1] = input[i][j];
				} else {
					output[i][0] = input[i][j];
				}
				if (j == 0) {
					if (i<3) {
						output[i][output[0].length-1] = input[i+1][0];
					} else {
						output[i][output[0].length-1] = input[0][0];
					}
				}
				if (j == inputCols - 1) {
					if (i>0) {
						output[i][0] = input[i-1][j];
					} else {
						output[i][0] = input[input.length-1][j];
					}
				}
			}
		}
		return output;
	}
	
	//XOR step
	public Boolean[][] XOR(Boolean[][] input, Boolean[] key) {
		int inputRows = input.length;
		int inputCols = input[0].length;
		Boolean[][] output = new Boolean[inputRows][inputCols];
		for (int i = 0; i < inputRows; i++) {
			for (int j = 0; j < inputCols; j++) {
				output[i][j] = input[i][j] ^ key[(j + i*inputCols) % 56];
			}
		}
		return output;
	}
	
	//SBox step
	public Boolean[][] SBox(Boolean[][] input) {
		int inputRows = input.length;
		int inputCols = input[0].length;
		Boolean[][] output = new Boolean[inputRows][inputCols - 2];

		for (int i = 0; i < inputRows; i++) {
			//We take care of the S-boxes one by one 
			output[i] = DoSBOX(i, input);
		}
		return output;
	}
	
	//for one of the 8 S-Boxes
	private Boolean[] DoSBOX(int i, Boolean[][] input) {
		int inputCols = input[0].length;
		Boolean[] line = {false, false, false, false};
		int num = SBOX[i][2*boolToInt(input[i][0])+boolToInt(input[i][input[0].length-1])%SBOX[0].length][(8*boolToInt(input[i][1])+4*boolToInt(input[i][2])+2*boolToInt(input[i][3])+boolToInt(input[i][0]))%SBOX[0][0].length];
		for (int j = 0; j < inputCols - 2; j++ ) {
			if (num/(Math.pow(2, 3 - j)) > 1) {
				if (j<4) {
					line[j] = true;
				} else {
					line[0] = true;
				}
				num -= Math.pow(2, 3 - j);
			}
		}
		return line;
	}
	
	//E1Table step
	public Boolean[][] E1Table(Boolean[][] input) {
		int inputRows = input.length;
		int inputCols = input[0].length;
		Boolean[][] output = new Boolean[inputRows][inputCols - 2];
		for (int i = 0; i < inputRows; i++) {
			for (int j = 1; j < inputCols - 1; j++) {
				output[i][j] = input[i][j];
			}
		}
		return output;
	}
	
	//Permutation step
		public Boolean[][] P(Boolean[][] input) {
			int inputRows = input.length;
			int inputCols = input[0].length;
			Boolean[][] output = new Boolean[inputRows][inputCols];
			for (int i = 0; i < inputRows; i++) {
				for (int j = 0; j < inputCols; j++) {
					int num = P[i][j] - 1;
					output[i][j] = input[((num - num % 4) / 4)%input.length][(num % 4)%input[0].length];
				}
			}
			return output;
		}
	
		
		
		
		
	public int boolToInt(boolean b) {
	    return b ? 1 : 0;
	}
}
