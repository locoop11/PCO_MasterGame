package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import types.BinaryColour;
import types.BullsAndCows;
import types.Code;
import types.Colour;
import types.MastermindGame;
import types.MultiColour;
import types.MultiColourMastermindGame;

public class Main {

    public static void main(String[] args) {
        // Inicialização do Scanner
        Scanner scanner = new Scanner(System.in);

        // Menu de escolha do jogo
        System.out.println("Escolha o tipo de jogo:");
        System.out.println("1 - Bulls and Cows (Código binário)");
        System.out.println("2 - Mastermind (Código multicolor)");

        int escolha = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após a escolha

        MastermindGame game = null;
        Colour[] colours = null;
        int seed = 123; // Definindo um valor para a seed
        int size = 4;   // Tamanho do código

        // Definindo as cores e o tipo de jogo baseado na escolha
        if (escolha == 1) {
            colours = BinaryColour.values();  // Usando BinaryColour para Bulls and Cows
            game = new BullsAndCows(seed, size, colours);
            System.out.println("Digite seu palpite (exemplo: b w b w):");
        } else if (escolha == 2) {
            colours = MultiColour.values();  // Usando MultiColour para Mastermind
            game = new MultiColourMastermindGame(seed, size, colours);
            System.out.println("Digite seu palpite (cores: b r o p g y):");
        } else {
            System.out.println("Escolha inválida. O jogo será encerrado.");
            scanner.close();
            return; // Sai do programa se a escolha for inválida
        }

        // Loop principal do jogo
        boolean roundFinished = false;

        while (!roundFinished) {
            System.out.println(game.toString());

            // Solicitar o código do jogador (entrada do usuário)
            String input = scanner.nextLine();
            List<Colour> guessList = parseInput(input, colours);  // Método para processar a entrada do usuário
            Code guess = new Code(guessList);

            // Jogar a tentativa
            game.play(guess);

            // Verificar se a rodada foi concluída
            if (game.wasSecretRevealed()) {
                System.out.println("Acertaste o código!");
                System.out.println("Pontuação final: " + game.score());
                System.out.println("Código: " + game.toString());
                break; // Termina o loop
            } else {
                System.out.println("Código Errado!");
            }

            roundFinished = game.isRoundFinished();

        }

        // Mostrar o resultado final
        
        System.out.println("Pontuação final: " + game.score());
        scanner.close(); // Fechar o scanner
    }

    

    // Método para converter a entrada de string para uma lista de cores
    private static List<Colour> parseInput(String input, Colour[] colours) {
        List<Colour> guessList = new ArrayList<>();
        String[] parts = input.split("\\s+"); // Separar as cores por espaços
    
        for (String part : parts) {
            boolean found = false;
            for (Colour colour : colours) {
                if (colour.toString().equalsIgnoreCase(part)) {
                    guessList.add(colour);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Cor inválida: " + part);
            }
        }
        return guessList;
    }
} //o b y r g pr