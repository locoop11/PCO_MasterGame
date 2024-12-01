package main;

import java.util.*;

import types.BinaryColour;
import types.BullsAndCows;
import types.Code;
import types.Colour;
import types.MastermindGame;
import types.MultiColour;
import types.MultiColourMastermindGame;

public class Main {
    private static SortedMap<String, String> mainMenuOptions = new TreeMap<>(Map.of(new String("1"), "Bulls and Cows (Código binário)", new String("2"), "Mastermind (Código multicolor)", new String("q"), "Quit"));
    private static SortedMap<String, String> gameOptions = new TreeMap<>(Map.of(new String("1"), "Make a guess", new String("2"), "Hint", new String("e"), "Exit Game"));
    private final static int CODE_SIZE = 4;


    public static void main(String[] args) {
        MasterMingGameUI mainMenuUi = new MasterMingGameUI("Mastermind Game Menu", mainMenuOptions);
        MasterMingGameUI bullsAndCowsUi = new MasterMingGameUI("Bulls and Cows Game", gameOptions);
        MasterMingGameUI multicolorUi = new MasterMingGameUI("Mastermind Multicolor Game", gameOptions);
        MasterMingGameUI currentScreen = null;

        MastermindGame game = null;
        Colour[] colours = null;
        int seed = -1; // Definindo um valor para a seed
        int size = Main.CODE_SIZE;   // Tamanho do código
        
        // Menu de escolha do jogo
        // Inicialização do Scanner
        Scanner scanner = new Scanner(System.in);   
        boolean validChoice = false;
        while (!validChoice) {
            mainMenuUi.setPrompt(new String[]{"Escolha o jogo que deseja jogar:"});
            mainMenuUi.draw();
                String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    colours = BinaryColour.values();  // Usando BinaryColour para Bulls and Cows
                    if (game != null) {
                        game.startNewRound();
                    } else {
                        game = new BullsAndCows(seed, size, colours);
                    }
                    bullsAndCowsUi.setPrompt(new String [] {"Cores Validas (exemplo: b w b w)"});
                    currentScreen = bullsAndCowsUi;
                    gameLoop(game, currentScreen, scanner, colours);
                    break;
                case "2":
                    colours = MultiColour.values();  // Usando MultiColour para Mastermind
                    if (game != null) {
                        game.startNewRound();
                    } else {
                        game = new MultiColourMastermindGame(seed, size, colours);
                    }
                    multicolorUi.setPrompt(new String []{"Cores Validas (cores: b r o p g y)"});
                    currentScreen = multicolorUi;
                    gameLoop(game, currentScreen, scanner, colours);
                    break;
                case "q":
                    validChoice = true;
                    break;
                default:
                    mainMenuUi.setError("Escolha inválida. Tente novamente:");
            }
        }

        
        
        scanner.close(); // Fechar o scanner
    }

    private static void gameLoop(MastermindGame game, MasterMingGameUI currentScreen, Scanner scanner, Colour[] colours) {
        boolean isGamefinished = false;
        while (!isGamefinished) {
            currentScreen.draw();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                        processGameRound(game, currentScreen, scanner, colours);
                        isGamefinished = game.isRoundFinished();
                    break;
                case "2":
                    currentScreen.setInfo("Dica: " + game.hint().toString());
                    currentScreen.draw();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        currentScreen.setError("Erro ao pausar o jogo.");
                    }
                    break;
                case "e":
                    isGamefinished = true;
                    break;

                default:
                    currentScreen.setError("Escolha inválida. Tente novamente:");
            }
        }

        // Verificar se a rodada foi concluída
        if (game.wasSecretRevealed()) {
            System.out.println("Acertaste o código!");
            System.out.println("Pontuação final: " + game.score());
            System.out.println("Código: " + game.toString());
        } else {
            System.out.println("Nao conseguiste acertar no codigo!");
        }

        System.out.println("Pontuação final: " + game.score());
        System.out.println("<Enter> to continue...");
        scanner.nextLine();
       
    }

    private static void processGameRound(MastermindGame game, MasterMingGameUI currentScreen, Scanner scanner, Colour[] colours) {
        currentScreen.draw(game.toString().split("\n"));
        // Solicitar o código do jogador (entrada do usuário)
        String input = null;
        List<Colour> guessList = new ArrayList<>();
        int retries = 0;
        while (retries < 3) {
            try {
                input = scanner.nextLine();
                guessList = parseInput(input, colours);  // Método para processar a entrada do usuário
                break;
            } catch (IllegalArgumentException e) {
                currentScreen.setError(e.getMessage() + "! Tente novamente:");
                retries++;
                if( retries >=  3) {
                    currentScreen.setError("Max retry attempts exceeded. Choose an option again.");
                    return ;
                }
                currentScreen.draw(game.toString().split("\n"));
            }
        }
        
        Code guess = new Code(guessList);

        // Jogar a tentativa
        game.play(guess);

        currentScreen.draw(game.toString().split("\n"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            currentScreen.setError("Erro ao pausar o jogo.");
        }

        
    }

    // Método para converter a entrada de string para uma lista de cores
    private static List<Colour> parseInput(String input, Colour[] colours) {
        List<Colour> guessList = new ArrayList<>();
        String[] parts = input.split(" "); // Separar as cores por espaços
        if( parts.length != Main.CODE_SIZE) {
            throw new IllegalArgumentException("Número de cores inválido. Deve ser " + Main.CODE_SIZE);
        }

    
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