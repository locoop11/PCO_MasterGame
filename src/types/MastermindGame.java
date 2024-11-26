package types;


//Documentation needed.

public interface MastermindGame {
	
	public static final int MAX_TRIALS = 100;
	
	public void play(Code trial);
	
	public boolean isRoundFinished();

	public void startNewRound();
	
	public Code bestTrial();
	
	public Colour hint();

	public int getNumberOfTrials();
	
	public int score();
	
	public boolean wasSecretRevealed();

}
// Yes, you should create a new .java file for the abstract class.
// Create a new file named AbstractMastermindGame.java in the same package.

