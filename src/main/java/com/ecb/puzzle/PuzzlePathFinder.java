package com.ecb.puzzle;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecb.puzzle.service.PuzzleService;
import com.ecb.puzzle.service.PuzzleServiceImpl;
import com.ecb.puzzle.utils.PFFDictionary;

public class PuzzlePathFinder {

	private static final Logger logger = Logger.getLogger(PuzzlePathFinder.class.getName());

	public static void main(String args[]) throws IOException {

		PuzzleService puzzleService = new PuzzleServiceImpl();
		List<String> dict = null;
		
		//For testing in Eclipse IDE - 2 param
		args = new String[2];
		args[0] = "head";
		args[1] = "tail";
		
		//For testing in Eclipse IDE - 3 param
		//args = new String[3];
		//args[0] = "4l_words.txt"; //absolute path for testing
		//args[1] = "head";
		//args[2] = "tail";

		if (args.length < 2 || args.length > 3) {
			logger.severe("Invalid number of parameters, you should execute the program in the following way: "
					+ "\n-> java -jar puzzle_path_finder.jar begin_word final_word " + "\nor "
					+ "\n-> java -jar puzzle_path_finder.jar dictionary_absolute_path begin_word final_word"
					+ "\nEXAMPLE of USE: "
					+ "\njava -jar puzzle_path_finder.jar C:/Temp/dictionary_4l_words.txt head tail");
			System.exit(1);
		}

		String dictionaryPath = args.length == 2 ? null : args[0];
		String beginWord = args.length == 2 ? args[0] : args[1];
		String endWord = args.length == 2 ? args[1] : args[2];

		// Loading the proper dictionary to do the searches
		try {
			if (dictionaryPath != null && !dictionaryPath.isEmpty())
				logger.info("Loading the dictionary in file: " + dictionaryPath);
			else
				logger.info("Loading default dictionary");
			dict = PFFDictionary.getDictionary(dictionaryPath);
			logger.info("Dictionary loaded successfully");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ERROR loading dictionary on '" + dictionaryPath
					+ "', no file has been located in the path indicated.");
			System.exit(1);
		}

		if (dict != null && !dict.isEmpty()) {
			// Putting into a Set in order to have unique words
			Set<String> dictionary = new HashSet<>(dict);
			if (dictionary.contains(beginWord.toLowerCase()) && dictionary.contains(endWord.toLowerCase())) {
				List<String> solution = puzzleService.findPathBFS(beginWord, endWord, dictionary);
				StringBuilder sb = new StringBuilder();
				for (String word : solution) {
					sb.append(word + "\n");
				}

				logger.info("Puzzle shortest path from '" + beginWord + "'-'" + endWord + "' in lower case letters is: "
						+ "\n" + sb.toString());
				System.exit(0);
			}

			if (!dictionary.contains(beginWord)) {
				logger.warning("The begin word '" + beginWord + "' does not exist in the dictionary");
			}
			if (!dictionary.contains(endWord)) {
				logger.warning("The end word '" + endWord + "' does not exist in the dictionary");
			}
		}
	}
	
}
