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
    public void play(Code trial) {
        //incrementTrials();
        // Verifica se o trial corresponde ao código secreto
        if (trial.equals(secretCode)) {
            revealSecret();
        } else {
            // Armazena o trial e os resultados no mapa de tentativas
            trials.put(trial, calculateFeedback(trial));
        }
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
    public void startNewRound() {
        this.secretCode = generateSecretCode();
        this.numberOfHintsUsed = 0;
        trials.clear();
        revealSecret();
    }

    @Override
    public Colour hint() {
        if (secretCode == null) {
            startNewRound();
        }
        Colour hintColour = secretCode.getCode().get(random.nextInt(getSize()));
        this.score /= 2;
        numberOfHintsUsed++;
        return hintColour;
    }

    @Override
    public boolean updateScore() {
        if (!isRoundFinished()) {
            return false;
        }

        int baseScore;
        int attempts = getNumberOfTrials();

        if (attempts <= 2) {
            baseScore = 100;
        } else if (attempts <= 5) {
            baseScore = 50;
        } else {
            baseScore = 20;
        }

        // Reduz o score com base nas ajudas usadas
        baseScore /= (numberOfHintsUsed + 1);

        //setScore(score() + baseScore);
        return true;
    }

    @Override
    public int score() {
        return score;
    }

    private int[] calculateFeedback(Code trial) {
        // Implementação do cálculo de feedback: combinação de cores certas e posições certas
        int correctPosition = 0;
        int correctColour = 0;

        List<Colour> secretColours = secretCode.getCode();
        List<Colour> trialColours = trial.getCode();

        // Verifica posições corretas
        for (int i = 0; i < getSize(); i++) {
            if (trialColours.get(i).equals(secretColours.get(i))) {
                correctPosition++;
            }
        }

        // Verifica cores corretas (independente da posição)
        for (Colour colour : trialColours) {
            if (secretColours.contains(colour)) {
                correctColour++;
            }
        }

        return new int[]{correctPosition, correctColour - correctPosition};
    }

    @Override
    public Code bestTrial() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bestTrial'");
    }
}
