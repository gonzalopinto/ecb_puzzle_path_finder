package com.ecb.puzzle.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.ecb.puzzle.PuzzlePathFinder;

public class PFFDictionary {

	protected static String DEFAULT_DICTIONARY_PATH = "4l_words.txt";

	/**
	 * Method which loads the dictionary file that the user can suggest or one by
	 * default otherwise. It throws an exception in case the introduced path is
	 * wrong
	 * 
	 * @param dictionaryPath
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static List<String> getDictionary(String dictionaryPath) throws IOException {
		List<String> dictionary = null;

		if (dictionaryPath != null && !dictionaryPath.isEmpty()) {
			dictionary = Files.readAllLines(Paths.get(dictionaryPath));
		} else {
			InputStream inputStream = PuzzlePathFinder.class.getResourceAsStream(DEFAULT_DICTIONARY_PATH);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			dictionary = reader.lines().collect(Collectors.toList());
		}

		return dictionary;
	}

}
