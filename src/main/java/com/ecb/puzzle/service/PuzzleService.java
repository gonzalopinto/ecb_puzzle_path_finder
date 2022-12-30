package com.ecb.puzzle.service;

import java.util.List;
import java.util.Set;

public interface PuzzleService {

	/**
	 * Method which searches the shortest path from the beginWord to the endWord
	 * using the words in dictionary, traversing the nodes by Breadth First Search
	 * 
	 * @param beginWord
	 * @param endWord
	 * @param dictionary
	 * @return
	 */
	List<String> findPathBFS(String beginWord, String endWord, Set<String> dictionary);

}
