package com.ecb.puzzle.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.ecb.puzzle.model.PuzzleNode;

public class PuzzleServiceImpl implements PuzzleService {

	@Override
	public List<String> findPathBFS(String beginWord, String endWord, Set<String> dictionary) {
		List<String> path = new ArrayList<>();
		List<String> visited = new ArrayList<>();
		Queue<PuzzleNode> queueTraversed = new LinkedList<>();

		boolean endWordFound = false;
		PuzzleNode lastNode = null;

		if (beginWord == null || beginWord.isEmpty() || endWord == null || endWord.isEmpty())
			return null;
		else {
			beginWord = beginWord.toLowerCase();
			endWord = endWord.toLowerCase();
		}

		// When they are the same word
		if (beginWord.equals(endWord)) {
			path.add(endWord);
			return path;
		}

		PuzzleNode node = new PuzzleNode(beginWord, 0, null);
		queueTraversed.offer(node);
		visited.add(beginWord);

		while (!queueTraversed.isEmpty() && !endWordFound) {
			PuzzleNode currentNode = queueTraversed.poll();
			List<String> adjacentList = getAdjacents(currentNode, dictionary);

			for (String currentWord : adjacentList) {
				if (!visited.contains(currentWord)) {
					lastNode = new PuzzleNode(currentWord, currentNode.getDepth() + 1, currentNode);
					visited.add(currentWord);
					endWordFound = currentWord.equals(endWord);

					// If has reached the endWord -> it stops
					// otherwise, we enqueue the node to visit it later
					if (endWordFound)
						break;
					else
						queueTraversed.offer(lastNode);
				}
			}
		}

		// Getting the solution path from the endWord backwards
		// If endWord has been found, we have the solution
		// otherwise, there's no path between those words using the dictionary
		if (endWordFound)
			path = getPathFromEndWord(lastNode);
		else
			path.clear();

		return path;
	}

	/**
	 * Method which searches for the adjacent words of the current node that are those
	 * words that has one letter different from the current one and exists in the
	 * dictionary
	 * 
	 * @param currentNode
	 * @param dictionary
	 * @return
	 */
	private List<String> getAdjacents(PuzzleNode currentNode, Set<String> dictionary) {
		List<String> adjacentList = new ArrayList<>();
		for (int i = 0; i < currentNode.getWord().length(); i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				char[] chars = currentNode.getWord().toCharArray();
				if (chars[i] == c) {
					continue;
				}
				chars[i] = c;
				String next = new String(chars);
				if (dictionary != null && dictionary.contains(next)) {
					adjacentList.add(next);
				}
			}
		}
		return adjacentList;
	}

	/**
	 * Method which forms the shortest path from the ending word node to the
	 * beginning by traversing the nodes backwards
	 * 
	 * @param endWordNode
	 * @return
	 */
	private List<String> getPathFromEndWord(PuzzleNode endWordNode) {
		List<String> path = new ArrayList<>();

		PuzzleNode currentNode = endWordNode;
		while (currentNode != null) {
			path.add(currentNode.getWord());
			currentNode = currentNode.getPreviousNode();
		}
		Collections.reverse(path);
		return path;
	}

}
