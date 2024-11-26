package types;

import java.util.List;

public class BullsAndCowsCode extends Code {

    public BullsAndCowsCode(List<BinaryColour> code) {
        super(code); // Inicializa os atributos usando o construtor da superclasse
    }
  
    public int[] howManyCorrect(Code other) {
        return new int[] { super.howManyCorrect(other)[1] };

        
}
}
