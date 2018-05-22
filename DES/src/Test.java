import roundFonctions.*;
import core.*;

public class Test {
	
	Boolean[] key = {true, true, false, false, true, false};
	private RoundFonctionSteps roundFonctionSteps = new RoundFonctionSteps();
	
	public static void main(String[] args) {
		Test test = new Test();
		test.test();
	}
	
	public void test() {
		
		Boolean[] newKey = roundFonctionSteps.rotateKey(key, 0);
	}
}
