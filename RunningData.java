import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RunningData {
    public static void main(String[] args) {
        String filePath = "src/dict.csv"; 

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Split only on the first comma
                String[] parts = line.split(",", 2);

                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String definition = parts[1].trim();
                    System.out.println("Word: " + word);
                    System.out.println("Definition: " + definition);
                    System.out.println("------");
                } else {
                    System.out.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}