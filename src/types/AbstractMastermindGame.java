package types;

import java.util.*;



public abstract class AbstractMastermindGame implements MastermindGame {
    private int numberOfTrials = 0;
    private boolean secretRevealed;
    private int size;
    private Colour[] colours;
    protected Code secretCode;
    protected Map<Code, int[]> trials;
    private Random random;
    protected List<Code> orderOfPlays;


    public AbstractMastermindGame(int seed, int size, Colour[] colours) {
        this.secretRevealed = false;
        this.size = size;
        this.colours = colours;
        this.trials = new HashMap<Code, int[]>();
        this.random = new Random(seed);
        this.secretCode = null;
        this.orderOfPlays = new ArrayList<Code>();
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
        return secretRevealed;
    }

    protected void incrementTrials(Code trialCode, int [] matchResults) {
        numberOfTrials++;
        if( trials.containsKey(trialCode)) {
            return;
        }
        trials.put(trialCode, matchResults);
        orderOfPlays.add(trialCode);
    }


    protected void revealSecret() {
        secretRevealed = true;
        updateScore();
    }

    @Override
    public String toString() {
        String message = "";

        if (secretRevealed) {
            message += secretCode.toString() + "\n";
        } else {
            message += unrevealedSecretCodeToString() + "\n";
        }
        return message;
    }

    private String unrevealedSecretCodeToString() {
        String message = "[";
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                message += "?]";
            } else {
                message += "?, ";
            }
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