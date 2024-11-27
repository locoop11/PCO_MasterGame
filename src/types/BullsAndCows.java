package types;

import java.util.List;

public class BullsAndCows extends AbstractMastermindGame {

    public BullsAndCows(int seed, int size, Colour[] colours) {
        super(seed, size, colours);
    }

    @Override
    public int score() {
        return super.getScore();
    }

    @Override
    public boolean updateScore() {
        setScore(getScore() + 2000); //2000 pontos quando a ronda termina
        return true;
    }

    @Override
    public boolean isRoundFinished() {
        // a ronda termina se o código for decifrado
        if (super.wasSecretRevealed()) {
            return true;
        }
        // Verifica se a última tentativa corresponde ao código secreto 
        List<Code> trials = super.getTrials();
        if (!trials.isEmpty() && trials.get(trials.size() - 1).equals(super.getSecretCode())) {
            revealSecret();
            return true;
        }
        return false;
    }

    @Override
    public Colour hint() {
        // retorna a primeira cor do código secreto
        Colour hintColour = super.getSecretCode().getColours().get(0);

        // atualiza o score para metade do valor atual
        setScore(getScore() / 2);

        return hintColour;
    }
