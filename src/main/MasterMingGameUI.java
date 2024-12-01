package main;

import java.util.*;

public class MasterMingGameUI {
    private List<String> screenText;
    private ArrayList<String> prompt;
    private String screenError = null;
    private String info = null;

    public MasterMingGameUI(String title, SortedMap<String, String> screenOptions) {
        screenText = new ArrayList<>();
        screenText.add("-------------------------------------------------");
        screenText.add(" \t *** " + title + " ***");
        screenText.add("-------------------------------------------------");
        screenText.add("");
        for (Map.Entry<String, String> entry : screenOptions.entrySet()) {
            screenText.add("\t" + entry.getKey() + " - " + entry.getValue());
        }
    }

    public void setError(String error) {
        screenError = error;
    }

    public void setPrompt(String [] prompt) {
        this.prompt = new ArrayList<String>(Arrays.asList(prompt));
    }

    public void addToPrompt(String lineString) {
        this.prompt.add(lineString);
    }

    public void draw() {
        clearConsole();
        for (String line : screenText) {
            System.out.println(line);
        }
        printPrompt(prompt);
        if( info != null) {
            printInfo(info);
        }

        if( screenError != null) {
            printError(screenError);
        }
    }

    public void draw(String [] aditionalPrompt) {
        clearConsole();
        for (String line : screenText) {
            System.out.println(line);
        }
        printPrompt(prompt);

        if( aditionalPrompt != null) {
            printAdditionalPrompt(aditionalPrompt);
        }
        if( info != null) {
            printInfo(info);
        }

        if (screenError != null) {
            printError(screenError);
        }   
    }

    public void setInfo(String info) {
        this.info = info;
    }
    private void printInfo(String info) {
        System.out.println("\nInfo: " + info);
        this.info = null;
    }

    private void printError(String error) {
        System.out.println("\nError: " + error);
        screenError = null;
    }

    private void printPrompt(ArrayList<String> prompt) {
        if( prompt != null) {
            for (String line : prompt) {
                System.out.println("-> " + line);
            }
        }
    }

    private void printAdditionalPrompt(String [] aditionalPrompt) {
        System.out.println("|-------------------------------------------------");
        for (String line : aditionalPrompt) {
            System.out.println("|\t" + line);
        }
        System.out.println("|-------------------------------------------------");

    }
    
    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Erro ao limpar o console: " + e.getMessage());
        }
    }
    
}
