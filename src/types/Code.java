package types;

import java.util.ArrayList;
import java.util.List;


public class Code implements Cloneable {

    private final List<Colour> codeList;
    
    public Code(List<? extends Colour> inputCode) {   /* input code is the codeList used for the constructor of the class */
        if (inputCode == null || inputCode.isEmpty()) {
            throw new IllegalArgumentException("Code sequence cannot be null or empty.");
        }
        codeList = new ArrayList<>(inputCode); 
    }

	public List<Colour> getCodeList() {
        return new ArrayList<>(codeList); // devolve uma cópia da lista com as cores do código.
    }

	public int getLength() {
        return this.getCodeList().size(); // devolve o tamanho da sequência do código
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
            if (this.getCodeList().get(i).equals(other.getCodeList().get(i))) {
                exactMatches++;
                usedInOther[i] = true;
                usedInThis[i] = true;
            }
        }

        // conta partialMatches
        for (int i = 0; i < codeList.size(); i++) {
            if (!usedInThis[i]) {
                for (int j = 0; j < other.getLength(); j++) {
                    if (!usedInOther[j] && this.getCodeList().get(i).equals(other.getCodeList().get(j))) {
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
        return this.getCodeList().toString(); //devolve uma representação deste Code na forma [c1,c2,c3,c4].
    }

	@Override
    public Code clone() {
        try {
            return new Code(this.getCodeList()); // cria um novo Code usando a lista existente
        } catch (Exception e) {
            throw new RuntimeException("Cloning failed", e);
        }
    }

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Code other = (Code) obj;
        return this.getCodeList().equals(other.getCodeList());
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
