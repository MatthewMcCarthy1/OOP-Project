package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

/**
 * The TextProcessor class is responsible for processing text files by simplifying words
 * using the Google 1000 most common words and their embeddings.
 */
public class TextProcessor extends FileProcessor {
    private final MapGoogle1000 mapGoogle1000;

    /**
     * Constructs a TextProcessor with the given MapGoogle1000.
     *
     * @param mapGoogle1000 The MapGoogle1000 to process words
     */
    public TextProcessor(MapGoogle1000 mapGoogle1000) {
        this.mapGoogle1000 = mapGoogle1000;
    }

    /**
     * Processes a text file, simplifying words and writing the result to an output file while preserving order.
     *
     * @param textFile   The path to the input text file
     * @param outputFile The path to the output file
     * @throws Throwable If an error occurs during file processing
     */
    @Override
    void processFile(String textFile, String outputFile) throws Throwable {
        System.out.println("Processing the text file: " + textFile);

        // Read all lines to maintain order
        List<String> lines = Files.readAllLines(Paths.get(textFile));
        String[] results = new String[lines.size()];

        try (var scope = StructuredTaskScope.open()) {
            for (int i = 0; i < lines.size(); i++) {
                final int index = i;
                final String line = lines.get(i);
                scope.fork(() -> {
                    results[index] = processLine(line);
                    return null;
                });
            }
            scope.join();
        }

        writeFileLines(outputFile, Arrays.asList(results));
        System.out.println("Successfully processed text file and wrote output to: " + outputFile);
    }

    /**
     * Processes a single line of text, simplifying each word.
     *
     * @param line The line of text to process
     * @return The processed line
     */
    private String processLine(String line) {
        StringBuilder processedLine = new StringBuilder();
        String[] words = line.split("\\s+");
        for (String word : words) {
            if (word.isEmpty()) continue;
            // Basic cleanup of punctuation to find the core word
            String cleanedWord = word.toLowerCase().replaceAll("[^a-z']", "");
            String processedWord = mapGoogle1000.processWord(cleanedWord);
            
            // Re-add the processed word (simplified or original)
            processedLine.append(processedWord).append(" ");
        }
        return processedLine.toString().trim();
    }

    @Override
    void process(String line) {
        // Not used by TextProcessor as we override processFile for ordering logic
    }
}
