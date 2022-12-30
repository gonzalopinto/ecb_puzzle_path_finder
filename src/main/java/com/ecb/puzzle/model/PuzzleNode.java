package com.ecb.puzzle.model;

public class PuzzleNode {

	private String word;
	private int depth;
	private PuzzleNode previousNode;

	public PuzzleNode(String word, int depth, PuzzleNode previous) {
		super();
		this.word = word;
		this.depth = depth;
		this.previousNode = previous;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public PuzzleNode getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(PuzzleNode previousNode) {
		this.previousNode = previousNode;
	}

}
