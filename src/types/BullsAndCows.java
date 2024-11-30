package types;


public class BullsAndCows extends AbstractMastermindGame {
    private int score = 0;

    public BullsAndCows(int seed, int size, Colour[] colours) {
        super(seed, size, colours);
    }

    public void play (Code trialCode){
        if (secretCode == null) {
            startNewRound();
        }
        int[] matchResults = trialCode.howManyCorrect(secretCode);
        if (matchResults[0] == getSize()) {
            revealSecret();
        }
        
        incrementTrials(trialCode, matchResults);  
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
        if (secretCode == null) {
            startNewRound();
        }
        Colour hintColour = secretCode.getCode().get(random.nextInt(getSize()));
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
        return ;
    }

    public Code bestTrial() {
        Code bestTrial = null;
        int bestScore = -1;
        for (Code trial : trials.keySet()) {
            int[] matchResults = trial.howManyCorrect(secretCode);
            int currentScore = (matchResults[0] * 10) + matchResults[1];
            if (currentScore > bestScore) {
                bestScore = currentScore;
                bestTrial = trial;
            }
            if( currentScore == bestScore) {
                if( trial.toString().compareTo( bestTrial.toString()) < 0 ) {
                    bestTrial = trial;
                }
            }   
    }
        return bestTrial;
    }

    public String toString() {
        String message = "Número de Tentativas = " + getNumberOfTrials() + "\n" +
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