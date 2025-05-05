import java.util.*; 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
public class EnglishDictionary {
    private int wordsFound;
    private final String[] englishWords;
    private final String[] sortedEnglishWords;
    private final List<String>[] wordsGroupedByFirstLetter;
    private final List<String>[] wordsGroupedByLastLetter;
    Map<String, String> wordDefinitions = new HashMap<>();
    public EnglishDictionary() {
        wordsFound = 0;
        // Current data structure holding all of the words
        String line;
        String csvFile = "dict.csv";
        List<String> words = new ArrayList<>();
        //reads from the csv file and stores the words and definitions in a hashmap
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",", 2);
                Collections.addAll(words, row);
                if (row.length == 2) {
                    String word = row[0].trim();
                    String definition = row[1].trim();
                    wordDefinitions.put(word, definition);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convert List to array
         englishWords = words.toArray(new String[0]);
        sortedEnglishWords = mergeSortWords(englishWords);
        //Data structure for holding words grouped by the first letter of the words
        wordsGroupedByFirstLetter = new List[26];
        for (int i = 0; i < wordsGroupedByFirstLetter.length; i++) {
            wordsGroupedByFirstLetter[i] = new ArrayList<>();
        }
        for (String w : sortedEnglishWords) {
            if (w == null || w.isEmpty()) {continue;}
            char c = Character.toLowerCase(w.charAt(0));
            if (c >= 'a' && c <= 'z') {
                wordsGroupedByFirstLetter[c - 'a'].add(w);
            }
        }
        //Data structure for holding words grouped by the last letter of the words
        wordsGroupedByLastLetter = new List[26];
        for (int i = 0; i < wordsGroupedByLastLetter.length; i++){
            wordsGroupedByLastLetter[i] = new ArrayList<>();
        }
        for (String w : sortedEnglishWords) {
            if (w == null || w.isEmpty()) {continue;}
            char c = Character.toLowerCase(w.charAt(w.length()-1));
            if (c >= 'a' && c <= 'z') {
                wordsGroupedByLastLetter[c - 'a'].add(w);
            }
        }
    }

    public void getResponse(String input) {
        input = input.trim();
        int hyphenCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-') {
                hyphenCount++;
            }
        }
        
        // Error Codes
        if (input.length() == 0) {
            System.out.println("\nPlease specify what you would like to search\n");
        }
        else if (input.endsWith("-") && input.charAt(0) == '-') {
            System.out.println("\nMake sure you only use 1 \"-\" in your search.");
        }
        else if (hyphenCount > 1) {
            System.out.println("\nMake sure you only use 1 \"-\" in your search.");
        }
        // Program Function Calls
        else if (input.equals("# of words")) {
            wordCount();
        }
        else if (!(input.endsWith("-")) && input.charAt(0) != '-'){
            regularSearch(input, wordDefinitions);
        }
        else if (input.charAt(0) == '-') {
            endSearch(input , wordDefinitions);
        }
        else if (input.endsWith("-")) {
            forwardSearch(input, wordDefinitions);
        }
    }

    //Program Functions
    private void regularSearch(String searchTerm,Map<String,String> wordDefinitions){
        wordsFound = 0;
        searchTerm = searchTerm.toLowerCase();
        //Search Algorithm: Linear Search
        for (String term : sortedEnglishWords){
            term = term.toLowerCase();
			if (searchTerm.equals(term)) {
                System.out.println(term+": "+wordDefinitions.get(term)+"\n");
                wordsFound++;
            }
        }
    }
    
    private void forwardSearch(String searchTerm,Map<String,String> wordDefinitions) {
        wordsFound = 0;
        searchTerm = searchTerm.toLowerCase();

        //Search Algorithm: Linear Search
        for (String term : wordsGroupedByFirstLetter[searchTerm.charAt(0) - 'a']) {
            term = term.toLowerCase();
            if (searchTerm.length()-1 < term.length() && term.substring(0,
                    searchTerm.length()-1).equals(searchTerm.substring(0, searchTerm.length()-1))) {
                System.out.println(term+": "+wordDefinitions.get(term)+"\n");
                wordsFound++;
            }
        }

        if (wordsFound == 0) {
            System.out.println("\nEither there are no words in this program that match your " +
                    "search case or the program made a mistake. Please try again.");
        }
    }

    private void endSearch(String searchTerm,Map<String,String> wordDefinitions) {
        wordsFound = 0;
        searchTerm = searchTerm.toLowerCase();

        //Search Algorithm: Linear Search
        for (String term : wordsGroupedByLastLetter[searchTerm.charAt(searchTerm.length()-1) - 'a']) {
            term = term.toLowerCase();
            if (searchTerm.length()-1 < term.length() && term.substring(term.length() - (searchTerm.length()-1)).equals(searchTerm.substring(1))) {
                System.out.println(term+": "+wordDefinitions.get(term)+"\n");
                wordsFound++;
            }
        }
        if (wordsFound == 0) {
            System.out.println("\nEither there are no words in this program that match your " +
                    "search case or the program made a mistake. Please try again.");
        }
    }

    //Sorting Algorithm: Merge Sort
    private String[] mergeSortWords(String[] array) {
        int n = array.length;
        if (n <= 1) {return array;}
        String[] L = mergeSortWords(Arrays.copyOfRange(array, 0, n/2));
        String[] R = mergeSortWords(Arrays.copyOfRange(array, n/2, n));
        return merge(L, R);
    }

    //Merge Sort Helper Function
    private String[] merge(String[] L, String[] R){
        String[] merged = new String[L.length + R.length];
        int i = 0, j = 0, k = 0;
        while (i < L.length && j < R.length) {
            if (L[i].compareToIgnoreCase(R[j]) < 0) {
                merged[k++] = L[i++];
            } else {
                merged[k++] = R[j++];
            }
        }
        while (i < L.length) {
            merged[k++] = L[i++];
        }
        while (j < R.length) {
            merged[k++] = R[j++];
        }
        return merged;
    }

    private void wordCount() {
        System.out.println("\nThis program currently has " + (englishWords.length + " English words."));
    }
}
