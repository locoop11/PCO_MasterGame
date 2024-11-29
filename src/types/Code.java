package types;

import java.util.ArrayList;
import java.util.List;


public class Code implements Cloneable {

    private final List<Colour> codeList;
    
    public Code(List<? extends Colour> inputCode) {   /* input code is the codeList used for the constructor of the class */
        if (inputCode == null ) {
            throw new IllegalArgumentException("Code sequence cannot be null.");
        }
        codeList = new ArrayList<>(inputCode); 
    }

	public List<Colour> getCode() {
        return new ArrayList<>(codeList); // devolve uma cópia da lista com as cores do código.
    }

	public int getLength() {
        return this.getCode().size(); // devolve o tamanho da sequência do código
    }

	public int[] howManyCorrect(Code other) {
        if (other == null || other.getLength() != this.getLength()) {  // se o código é null ou != comprimento é lançada exceção
            throw new IllegalArgumentException("Code to compare must not be null and must have the same length.");
        }

        int exactMatches = 0; // cor correta posição correta
        int partialMatches = 0; // cor correta posição errada

        boolean[] usedInOther = new boolean[other.getLength()];
        boolean[] usedInThis = new boolean[this.getLength()];

        // conta exactMatches
        for (int i = 0; i < codeList.size(); i++) {
            if (this.getCode().get(i).equals(other.getCode().get(i))) {
                exactMatches++;
                usedInOther[i] = true;
                usedInThis[i] = true;
            }
        }

        // conta partialMatches
        for (int i = 0; i < codeList.size(); i++) {
            if (!usedInThis[i]) {
                for (int j = 0; j < other.getLength(); j++) {
                    if (!usedInOther[j] && this.getCode().get(i).equals(other.getCode().get(j))) {
                        partialMatches++;
                        usedInOther[j] = true;
                        break;
                    }
                }
            }
        }

        return new int[]{exactMatches, partialMatches}; //vetor (a,b)
    }

	@Override
    public String toString() {
        String returnString = "[";
        for( int i = 0; i < this.getLength(); i++) {
            if( i == this.getLength() - 1) {
                returnString += this.getCode().get(i).toString() + "]";
            } else {
                returnString += this.getCode().get(i).toString() + ", ";
            }
        }
        return returnString;
    }

	@Override
    public Code clone() {
        try {
            return new Code(this.getCode()); // cria um novo Code usando a lista existente
        } catch (Exception e) {
            throw new RuntimeException("Cloning failed", e);
        }
    }

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Code other = (Code) obj;
        return this.getCode().equals(other.getCode());
    }

    public static void main(String[] args) {
        List<Colour> codeList = new ArrayList<>();
        codeList.add(BinaryColour.BLACK);
        codeList.add(BinaryColour.WHITE);
        Code code = new Code(codeList);
        System.out.println(code);
        System.out.println(code.getLength());
        System.out.println(code.clone());
        System.out.println(code.equals(code.clone()));
    }

}
