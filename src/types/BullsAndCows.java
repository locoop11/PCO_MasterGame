package types;

import java.util.HashMap;
import java.util.Map;

public class BullsAndCows extends AbstractMastermindGame {

    private int score;

    public BullsAndCows(int seed, int size, Colour[] colours) {
        super(seed, size, colours);
        this.score = 0;
    }

    public void play (Code trialCode){
        if (secretCode != null) {
            startNewRound();  
        }
        int[] matchResults = trialCode.howManyCorrect(trialCode);
        if (matchResults[0] == getSize()) {
            revealSecret();
        }
        trials.put(trialCode, matchResults);
        
        incrementTrials();  
    }


    public int score() {
        return score;
    }
    public boolean updateScore() {
        this.score += 2000;
        return true;
    }
    @Override
    public Colour hint() {
        Colour hintColour = secretCode.getCode().get(0);
        this.score /= 2;
        return hintColour;
    }
    
    public boolean isRoundFinished() {
        if (wasSecretRevealed() || getNumberOfTrials() >= MAX_TRIALS) {
            return true;
        }
        else {
            return false;
        }
    }

    public void startNewRound(){
        secretCode = generateSecretCode();
        


    }

    public Code bestTrial() {
        return secretCode ;
    }
}