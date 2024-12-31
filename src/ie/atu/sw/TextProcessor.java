package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.StructuredTaskScope;

//Reference:  Moodle -> oop-VirtualThreadLabs -> StructuredFileParser.java

/**
 * The TextProcessor class is responsible for processing text files by simplifying words
 * using the Google 1000 most common words and their embeddings.
 */
public class TextProcessor extends FileProcessor {
    private final MapGoogle1000 mapGoogle1000;

    /**
     * Constructs a TextProcessor with the given MapGoogle1000.
     *
     * <p><b>Time Complexity:</b> O(1), constant time operation</p>
     *
     * @param mapGoogle1000 The MapGoogle1000 to process words
     */
    public TextProcessor(MapGoogle1000 mapGoogle1000) {
        this.mapGoogle1000 = mapGoogle1000;
    }

    /**
     * Processes a text file, simplifying words and writing the result to an output file.
     *
     * <p><b>Time Complexity:</b> O(n * m), where n is the number of lines in the file and m is the average
     * number of words per line</p>
     *
     * @param textFile   The path to the input text file
     * @param outputFile The path to the output file
     * @throws Throwable If an error occurs during file processing
     */
    @Override
    public void processFile(String textFile, String outputFile) throws Throwable {
        List<String> outputLines = new CopyOnWriteArrayList<>();
        System.out.println("Processing the text file: " + textFile);

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Files.lines(Paths.get(textFile)).forEach(line -> {
                scope.fork(() -> {
                    processLine(line, outputLines);
                    return null;
                });
            });
            scope.join();
            scope.throwIfFailed(e -> e);

            writeFileLines(outputFile, outputLines);
            System.out.println("Successfully processed text file and wrote output to: " + outputFile);
        } catch (Exception e) {
            System.err.println("Error processing text file: " + textFile);
            throw e;
        }
    }

    /**
     * Processes a single line of text, simplifying each word.
     *
     * <p><b>Time Complexity:</b> O(m), where m is the number of words in the line</p>
     *
     * @param line        The line of text to process
     * @param outputLines The list to add the processed line to
     */
    private void processLine(String line, List<String> outputLines) {
        StringBuilder processedLine = new StringBuilder();
        Arrays.stream(line.split("\\s+")).forEach(word -> {
            String lowerWord = word.toLowerCase();
            String processedWord = mapGoogle1000.processWord(lowerWord);
            processedLine.append(processedWord).append(" ");
        });
        outputLines.add(processedLine.toString().trim());
    }
}