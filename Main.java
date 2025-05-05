import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        EnglishDictionary englishdict = new EnglishDictionary();

        System.out.println("\nWelcome to English Dictionary Search.");
        System.out.println("Directions:\nTo find a word that starts with a certain letter use - after the letter (Ex. A- would give Apple). To find a word that ends with a certain letter or phrase use - before the letter (Ex. -ing would give Running)․ \nEnter \"# of words\" to see how many words this program contains. \nEnter \"Stop\" to stop the program.\n");

        System.out.print("\nEnter Search: ");
        Scanner in = new Scanner (System.in);
        String input = in.nextLine();

        while (!input.equalsIgnoreCase("stop"))
        {
            englishdict.getResponse(input);
            System.out.print("\nEnter Search: ");
            input = in.nextLine();
        }
    }
}
