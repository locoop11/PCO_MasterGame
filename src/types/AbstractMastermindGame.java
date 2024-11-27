package types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public abstract class AbstractMastermindGame implements MastermindGame {
    private int numberOfTrials;
    private int score;
    private boolean secretRevealed;
    private int size;
    private Colour[] colours;
    protected Code secretCode;
    protected List<Code> trials;
    private Random random;

    public AbstractMastermindGame(int seed, int size, Colour[] colours) {
        this.numberOfTrials = 0;
        this.score = 0;
        this.secretRevealed = false;
        this.size = size;
        this.colours = colours;
        this.trials = new ArrayList<>();
        this.random = new Random(seed);
        this.secretCode = null;
    }



    public int getSize() {
        return size;
    }


    protected Code generateSecretCode() {
        Colour[] codeColours = new Colour[size];
        for (int i = 0; i < size; i++) {
            codeColours[i] = colours[random.nextInt(colours.length)];
        }
        return new Code(List.of(codeColours));
    }

    @Override
    public int getNumberOfTrials() {
        return numberOfTrials;
    }

    @Override
    public boolean wasSecretRevealed() {
        
        return true;
    }

    protected void incrementTrials() {
        numberOfTrials++;
    }

    protected void setScore(int score) {
        this.score = score;
    }

    protected void revealSecret() {
        secretRevealed = true;
    }

    @Override
    public String toString() {
        String message = "Number of Trials: " + numberOfTrials + "\n" +
                "Score: " + score + "\n" +
                "Board:\n";


        if (secretRevealed) {
            message += "Secret Code: " + secretCode + "\n";
        } else {
            message += "Secret Code: " + "?".repeat(size) + "\n";
        }

        int start = Math.max(0, trials.size() - 10);
        for (int i = start; i < trials.size(); i++) {
            message += trials.get(i) + "\n";
        }

        return message;
    }

    // Abstract methods to be implemented by subclasses
    public abstract void play(Code trial);

    public abstract boolean isRoundFinished();

    public abstract void startNewRound();

    public abstract Code bestTrial();

    public abstract Colour hint();

    public abstract boolean updateScore();

    public abstract int score();
}