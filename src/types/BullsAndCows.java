package types;


public class BullsAndCows extends AbstractMastermindGame {
    private int score = 0;

    public BullsAndCows(int seed, int size, Colour[] colours) {
        super(seed, size, colours);
    }

    @Override
    public Colour hint() {
        this.score /= 2;
        return super.hint();
    }

    public int score() {
        return score;
    }
    
    public boolean updateScore() {
        this.score += 2000;
        return true;
    }
    
    public boolean isRoundFinished() {
        if (wasSecretRevealed() || getNumberOfTrials() >= MAX_TRIALS) {
            return true;
        }
        else {
            return false;
        }
    }
    

    public String toString() {
        String message = "Number of Trials = " + getNumberOfTrials() + "\n" +
                "Score = " + score + "\n";
        message += super.toString() + "\n";
        for (Code codePlayed : orderOfPlays) {
            message += codePlayed.toString() + "    ";
            int[] results = trials.get(codePlayed);
            message += results[0] + " " + results[1] + "\n";
        }
        return message;
    }
}