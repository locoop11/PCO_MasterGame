package types;

import java.util.List;

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
        if (secretCode == null) {
            startNewRound();
        }
    
        int[] feedback = calculateFeedback(trial);
        incrementTrials(trial, feedback);
    
        // Verifica se o código foi acertado
        if (feedback[0] == getSize()) {
            revealSecret();
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
        if (wasSecretRevealed()) {
            // Se o código foi decifrado, define uma pontuação alta
            int baseScore = 2000; // A pontuação inicial é 2000 para a vitória
            int attempts = getNumberOfTrials();
    
            // Reduz a pontuação com base nas dicas usadas
            baseScore /= (numberOfHintsUsed + 1);
    
            // Define o score final
            this.score = baseScore;
            return true;
        }
    
        return false;
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
        Code bestTrial = null;
        int bestScore = -1;
        for (Code trial : trials.keySet()) {
            int[] feedback = trials.get(trial); // Obtém o feedback (corretas e posição correta)
            int currentScore = (feedback[0] * 10) + feedback[1]; // Calcula a pontuação: 10 pontos para corretas na posição certa, 1 ponto para cores corretas na posição errada
            if (currentScore > bestScore) {
                bestScore = currentScore;
                bestTrial = trial;
            } else if (currentScore == bestScore && trial.toString().compareTo(bestTrial.toString()) < 0) {
                // Se a pontuação for igual, escolhe o código que vem antes lexicograficamente
                bestTrial = trial;
            }
        }
        return bestTrial;
    }
}
