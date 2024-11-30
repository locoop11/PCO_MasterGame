package types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



public abstract class AbstractMastermindGame implements MastermindGame {
    private int numberOfTrials = 0;
    private boolean secretRevealed;
    private int size;
    private Colour[] colours;
    protected Code secretCode;
    protected Map<Code, int[]> trials;
    protected Random random;
    protected List<Code> orderOfPlays;


    public AbstractMastermindGame(int seed, int size, Colour[] colours) {
        this.secretRevealed = false;
        this.size = size;
        this.colours = colours;
        this.trials = new HashMap<Code, int[]>();
    
        // Use nanoTime para gerar uma semente única a cada execução
        this.random = new Random(System.nanoTime());
    
        this.secretCode = null;
        this.orderOfPlays = new ArrayList<Code>();
    }

    public void play (Code trialCode){
        if (secretCode == null) {
            startNewRound();
        }
        if (secretRevealed == false) {
            int[] matchResults = trialCode.howManyCorrect(secretCode);
            incrementTrials(trialCode, matchResults);
            if (matchResults[0] == getSize()) {
                revealSecret();
            }
        }
    }


    public void startNewRound(){
        secretCode = generateSecretCode();
        secretRevealed = false;
        numberOfTrials = 0;
        trials.clear();
        orderOfPlays.clear();
    }

    public Colour hint() {
        if (secretCode == null) {
            startNewRound();
        }
        Colour hintColour = secretCode.getCode().get(random.nextInt(getSize()));
        return hintColour;
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

    protected void incrementTrials(Code trialCode, int[] matchResults) {
        numberOfTrials++;
        if (trials.containsKey(trialCode)) {
            return; // Não registra novamente se já existe
        }
        trials.put(trialCode, matchResults);      // Armazena o resultado do feedback
        orderOfPlays.add(trialCode);              // Registra a ordem da tentativa
    }
    


    protected void revealSecret() {
        if (secretRevealed == false) {
            secretRevealed = true;
            updateScore();
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
    
        // Exibir código secreto (revelado ou oculto)
        if (wasSecretRevealed()) {
            message.append(secretCode.toString()).append("\n");
        } else {
            message.append("[");
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    message.append("?"); // Não adiciona vírgula após o último elemento
                } else {
                    message.append("?, ");
                }
            }
            message.append("]\n"); // Fecha os colchetes corretamente
        }
    
        // Mostrar todas as tentativas e os respectivos resultados
        for (Code codePlayed : orderOfPlays) {
            message.append(codePlayed.toString()).append("    ");
            int[] results = trials.get(codePlayed);
            message.append(results[0]).append(" ").append(results[1]).append("\n");
        }
    
        return message.toString();
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
    
    public abstract boolean updateScore();

    public abstract int score();

    public abstract boolean isRoundFinished();
}