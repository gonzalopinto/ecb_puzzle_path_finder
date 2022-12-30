package com.ecb.puzzle;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.ecb.puzzle.service.PuzzleService;
import com.ecb.puzzle.service.PuzzleServiceImpl;
import com.ecb.puzzle.utils.PFFDictionary;

public class PuzzlePathFinderTest {

	private static final Logger logger = Logger.getLogger(PuzzlePathFinderTest.class.getName());

	private PuzzleService puzzleService;

	@Before
	public void setUp() throws Exception {
		this.puzzleService = new PuzzleServiceImpl();
	}

	@Test
	public void testDefaultDictionary() {
		List<String> dict;
		try {
			dict = PFFDictionary.getDictionary(null);
			assertNotNull(dict);
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}
	}

	@Test
	public void testWrongDictionary() {
		List<String> dict = null;
		try {
			dict = PFFDictionary.getDictionary("wrongDict.txt");
			//This test should not ever go this way
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(e instanceof NoSuchFileException);
			assertNull(dict);
			logger.info("If test reaches here, it has passed - OK");
		}
	}

	@Test
	public void testEmptyPathDictionary() {
		List<String> dict;
		try {
			dict = PFFDictionary.getDictionary("");
			assertNotNull(dict);
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}
	}

	@Test
	public void testNullValuesParamBFS() {
		List<String> path = puzzleService.findPathBFS(null, null, null);
		assertNull(path);
		logger.info("If test reaches here, it has passed - OK");
	}

	@Test
	public void testEmptyValuesParamBFS() {
		List<String> path = puzzleService.findPathBFS("", "", null);
		assertNull(path);
		logger.info("If test reaches here, it has passed - OK");
	}

	@Test
	public void testParam1EmptyValuePath() {
		List<String> path = puzzleService.findPathBFS("", null, null);
		assertNull(path);
		logger.info("If test reaches here, it has passed - OK");
	}

	@Test
	public void testParam2EmptyValuePath() {
		List<String> path = puzzleService.findPathBFS(null, "", null);
		assertNull(path);
		logger.info("If test reaches here, it has passed - OK");
	}

	@Test
	public void testWrongParam1ValuePath() {
		List<String> path = puzzleService.findPathBFS("word1", null, null);
		assertNull(path);
		logger.info("If test reaches here, it has passed - OK");
	}

	@Test
	public void testWrongParam2ValuePath() {
		List<String> path = puzzleService.findPathBFS(null, "word2", null);
		assertNull(path);
		logger.info("If test reaches here, it has passed - OK");
	}

	@Test
	public void testWrongParamsWrongDictFindPath() {
		try {
			puzzleService.findPathBFS("word1", "word2", new HashSet<>(PFFDictionary.getDictionary("wrong.txt")));
			//This test should not ever go this way
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(e instanceof NoSuchFileException);
			logger.info("If test reaches here, it has passed - OK");
		}
	}

	@Test
	public void testWrongParamWithDictFindPath() {
		List<String> path = puzzleService.findPathBFS("word1", "word2", null);
		assertTrue(path.isEmpty());
		logger.info("If test reaches here, it has passed - OK");
	}

	@Test
	public void testWrongParamFindPath() {
		List<String> path;
		try {
			path = puzzleService.findPathBFS("word1", "word2", new HashSet<>(PFFDictionary.getDictionary(null)));
			assertTrue(path.isEmpty());
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}

	}

	@Test
	public void testWrongParam2FindPath() {
		List<String> path;
		try {
			path = puzzleService.findPathBFS("head", "word2", new HashSet<>(PFFDictionary.getDictionary(null)));
			assertTrue(path.isEmpty());
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}

	}

	@Test
	public void testOkParamFindPath() {
		List<String> path;
		try {
			path = puzzleService.findPathBFS("head", "head", new HashSet<>(PFFDictionary.getDictionary(null)));
			assertNotNull(path);
			assertEquals(path.get(0), "head");
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}
	}

	@Test
	public void testOneAwayPath() {
		List<String> path;
		try {
			path = puzzleService.findPathBFS("head", "heal", new HashSet<>(PFFDictionary.getDictionary(null)));
			assertNotNull(path);
			assertEquals(path.get(0), "head");
			assertEquals(path.get(1), "heal");
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}
	}

	@Test
	public void testFindPathHeadToTailWords() {
		List<String> path;
		try {
			path = puzzleService.findPathBFS("head", "tail", new HashSet<>(PFFDictionary.getDictionary(null)));
			assertNotNull(path);
			assertEquals(path.get(0), "head");
			assertEquals(path.get(1), "heal");
			assertEquals(path.get(2), "heil");
			assertEquals(path.get(3), "hail");
			assertEquals(path.get(4), "tail");
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}
	}

	@Test
	public void testNoPathBetweenExistingWords() {
		Set<String> dict;
		try {
			dict = new HashSet<>(PFFDictionary.getDictionary(null));
			List<String> path = puzzleService.findPathBFS("zeta", "zuni", dict);
			assertTrue(dict.contains("zeta"));
			assertTrue(dict.contains("zuni"));
			assertTrue(path.isEmpty());
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);			
		}
	}

	@Test
	public void testAnotherPathOtherWords() {
		Set<String> dict;
		try {
			dict = new HashSet<>(PFFDictionary.getDictionary(null));
			List<String> path = puzzleService.findPathBFS("yarn", "alai", dict);
			assertTrue(dict.contains("yarn"));
			assertTrue(dict.contains("alai"));
			assertNotNull(path);
			assertEquals(path.get(0), "yarn");
			assertEquals(path.get(1), "darn");
			assertEquals(path.get(2), "damn");
			assertEquals(path.get(3), "dams");
			assertEquals(path.get(4), "dims");
			assertEquals(path.get(5), "aims");
			assertEquals(path.get(6), "alms");
			assertEquals(path.get(7), "alas");
			assertEquals(path.get(8), "alai");
			logger.info("If test reaches here, it has passed - OK");
		} catch (Exception e) {
			//This test should not ever go this way
			assertTrue(false);
		}

	}

}
