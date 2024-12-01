package types;

import java.util.*;

public class MultiColourMastermindGame extends AbstractMastermindGame {
    private int numberOfHintsUsed; // Contador de ajudas dadas na ronda atual
    private int score = 0;

    public MultiColourMastermindGame(int seed, int size, Colour[] colours) {
        super(seed, size, colours);
        this.secretCode = generateSecretCode();
        this.numberOfHintsUsed = 0;
    }

    @Override
    public int score() {
        return score;
    }

    @Override
    public boolean isRoundFinished() {
        if (wasSecretRevealed() || getNumberOfTrials() >= MAX_TRIALS) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Colour hint() {
        numberOfHintsUsed++;
        return super.hint();
    }

    @Override
    public boolean updateScore() {

        int incrementedScore = 0;
        int attempts = getNumberOfTrials();

        if (attempts <= 2 ) {
            incrementedScore = 100;
        } else if (attempts <= 5) {
            incrementedScore = 50;
        } else {
            incrementedScore = 20;
        }

        incrementedScore /= (numberOfHintsUsed + 1);
        score += incrementedScore;
        
        return true;
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
