package ie.atu.sw;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The Runner class is the main entry point for the Text Simplifier application.
 * It provides a menu-driven interface for users to specify input files, process text, and generate output.
 */
public class Runner {
	private static final Scanner scanner = new Scanner(System.in);
	private static String embeddingsFile;
	private static String google1000File;
	private static String textFile;
	private static String outputFile;

	/**
	 * The main method that starts the application.
	 *
	 * @param args Command line arguments (not used)
	 * @throws Throwable If an error occurs during execution
	 */
	public static void main(String[] args) throws Throwable {
		menu();
	}

	/**
	 * Displays the main menu and handles user input.
	 *
	 *<p><b>Time Complexity:</b> O(1), each iteration of the menu performs constant time operations, but the loop
	 * continues indefinitely until the user chooses to exit so it may be 0(infinity)</p>
	 *
	 * @throws Throwable If an error occurs during menu operations
	 */
	public static void menu() throws Throwable {
		while (true) {
			//You should put the following code into a menu or Menu class
			System.out.println(ConsoleColour.WHITE);
			System.out.println("************************************************************");
			System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
			System.out.println("*                                                          *");
			System.out.println("*             Virtual Threaded Text Simplifier             *");
			System.out.println("*                                                          *");
			System.out.println("************************************************************");
			System.out.println("(1) Specify Embeddings File");
			System.out.println("(2) Specify Google 1000 File");
			System.out.println("(3) Specify Text File");
			System.out.println("(4) Specify an Output File");
			System.out.println("(5) Execute, Analyse and Report");
			System.out.println("(0) Quit");

			//Output a menu of options and solicit text from the user
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option [1-5]>");
			String option = scanner.nextLine();

			switch (option) {
				case "1":
					specifyEmbeddingsFile();
					break;
				case "2":
					specifyGoogle1000File();
					break;
				case "3":
					specifyTextFile();
					break;
				case "4":
					specifyOutputFile();
					break;
				case "5":
					executeAnalyseReport();
					break;
				case "0":
					System.out.println("Exiting...");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid option. Please try again.");
			}
		}
	}

	/**
	 * Prompts the user to specify the embeddings file path.
	 *
	 * <p><b>Time Complexity:</b> O(1), this method performs a constant time operation of reading user input</p>
	 */
	private static void specifyEmbeddingsFile() {
		System.out.print("Enter the path to the embeddings file: ");
		embeddingsFile = scanner.nextLine();
	}

	/**
	 * Prompts the user to specify the Google 1000 file path.
	 *
	 * <p><b>Time Complexity:</b> O(1), this method performs a constant time operation of reading user input</p>
	 */
	private static void specifyGoogle1000File() {
		System.out.print("Enter the path to the Google 1000 file: ");
		google1000File = scanner.nextLine();
	}

	/**
	 * Prompts the user to specify the input text file path.
	 *
	 * <p><b>Time Complexity:</b> O(1), this method performs a constant time operation of reading user input</p>
	 */
	private static void specifyTextFile() {
		System.out.print("Enter the path to the text file: ");
		textFile = scanner.nextLine();
	}

	/**
	 * Prompts the user to specify the output file path.
	 *
	 * <p><b>Time Complexity:</b> O(1), this method performs a constant time operation of reading user input</p>
	 */
	private static void specifyOutputFile() {
		System.out.print("Enter the path to the output file: ");
		outputFile = scanner.nextLine();
	}

	/**
	 * Executes the text analysis and simplification process.
	 *
	 * <p><b>Time Complexity:</b> O(n + m + k), where n is the number of words in the Google 1000 list,
	 * m is the number of words in the embeddings file, and k is the number of words in the input text file </p>
	 *
	 *  @throws Throwable If an error occurs during execution
	 */
	private static void executeAnalyseReport() throws Throwable{
		if (embeddingsFile == null || google1000File == null || textFile == null || outputFile == null) {
			System.out.println("Please specify all file paths before executing.");
			return;
		}

		try {
			// Load Google 1000 words
			GoogleWordsLoader googleWordsLoader = new GoogleWordsLoader();
			googleWordsLoader.loadGoogle1000List(google1000File);
			ConcurrentSkipListSet<String> google1000Set = googleWordsLoader.getGoogle1000Set();

			// Load GloVe embeddings
			GloVEEmbeddingsLoader gloveEmbeddingsLoader = new GloVEEmbeddingsLoader();
			gloveEmbeddingsLoader.loadWordEmbeddings(embeddingsFile);
			ConcurrentHashMap<String, double[]> embeddingsMap = gloveEmbeddingsLoader.getEmbeddings();

			//Initialise google 1000 embeddings
			MapGoogle1000 mapGoogle1000 = new MapGoogle1000(google1000Set, embeddingsMap);
			mapGoogle1000.initializeGoogle1000Embeddings();

			//Process the text file
			TextProcessor textProcessor = new TextProcessor(new FileProcessor() {}, mapGoogle1000);
			textProcessor.processTextFile(textFile, outputFile);

			System.out.println("Text processing completed successfully.");
		} catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}