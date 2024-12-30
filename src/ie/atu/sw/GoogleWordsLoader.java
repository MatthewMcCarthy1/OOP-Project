package ie.atu.sw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.StructuredTaskScope;

//Reference: Moodle -> oop-VirtualThreadLabs -> StructuredFileParser.java

/**
 * The GoogleWordsLoader class is responsible for loading the Google 1000 words from a specified
 * file into a concurrent skip list set. It extends the FileProcessor class to utilize its file
 * reading capabilities.
 */
public class GoogleWordsLoader extends FileProcessor{
    private final ConcurrentSkipListSet<String> google1000Set = new ConcurrentSkipListSet<>();
    /**
     * Loads the Google 1000 words from a specified file using virtual threads.
     *
     * <p>Each line in the file is expected to contain a single word. The words are
     * trimmed of whitespace and converted to lowercase before being added to the set.</p>
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines in the file.</p>
     *
     * @param google1000File the path to the Google 1000 words file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws Throwable if an error occurs during concurrent task execution
     */
    public void loadGoogle1000List(String google1000File) throws Throwable {
        System.out.println("Loading Google 1000 words from: " + google1000File);

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Files.lines(Paths.get(google1000File)).forEach(line -> {
                scope.fork(() -> {
                    addWordToSet(line);
                    return null;
                });
            });
            //wait for all tasks to complete
            scope.join();
            //if a task failed, throw exception
            scope.throwIfFailed(e -> e);

            System.out.println("Successfully loaded Google 1000 words.");
        }catch (IOException e) {
            System.err.println("IO Exception while reading the Google 1000 words file: " + google1000File);
            throw e;
        } catch (Throwable e) {
            System.err.println("Error during concurrent task execution while loading Google 1000 words file: " + google1000File);
            throw e;
        }
    }

    /**
     * Adds a word to the Google 1000 set after trimming and converting it to lowercase.
     *
     * <p>This method is called concurrently by virtual threads during the loading process.</p>
     *
     * <p><b>Time Complexity:</b> O(log n), where n is the current size of the set, due to the
     * nature of ConcurrentSkipListSet.</p>
     *
     * @param line the word to be added to the set
     */
    private void addWordToSet(String line) {
        google1000Set.add(line.trim().toLowerCase());
    }

    /**
     * Retrieves the loaded Google 1000 words.
     *
     * <p><b>Time Complexity:</b> O(1), as it simply returns the reference to the set.</p>
     *
     * @return a ConcurrentSkipListSet containing all the loaded Google 1000 words
     */
    public ConcurrentSkipListSet<String> getGoogle1000Set() {
        return google1000Set;
    }
}