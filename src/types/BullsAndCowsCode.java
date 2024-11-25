package types;

import java.util.List;

public class BullsAndCowsCode extends Code {

    public BullsAndCowsCode(List<BinaryColour> code) {
        super(code); // Inicializa os atributos usando o construtor da superclasse
    }
  
    public int[] howManyCorrect(Code other) {
        if (other == null || other.getLength() != this.getLength()) {
            throw new IllegalArgumentException("Code to compare must not be null and must have the same length.");
        }

        int blackPins = 0; // Cor e posição corretas
        int whitePins = 0; // Cor correta, posição errada

        boolean[] usedInOther = new boolean[other.getLength()];
        boolean[] usedInThis = new boolean[this.getLength()];

        // Calcula nº de "pins" pretos 
        for (int i = 0; i < getLength(); i++) {
            if (getCode().get(i).equals(other.getCode().get(i))) {
                blackPins++;
                usedInOther[i] = true;
                usedInThis[i] = true;
            }
        }

        // Calcula nº de pins brancos
        for (int i = 0; i < getLength(); i++) {
            if (!usedInThis[i]) { // Não foi usado como preto
                for (int j = 0; j < other.getLength(); j++) {
                    if (!usedInOther[j] && getCode().get(i).equals(other.getCode().get(j))) {
                        whitePins++;
                        usedInOther[j] = true;
                        break;
                    }
                }
            }
        }

        return new int[]{blackPins, whitePins};
    }
}
