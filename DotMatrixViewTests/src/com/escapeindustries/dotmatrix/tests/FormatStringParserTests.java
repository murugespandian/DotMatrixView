package com.escapeindustries.dotmatrix.tests;

import com.escapeindustries.dotmatrix.Digit;
import com.escapeindustries.dotmatrix.FormatStringParser;
import com.escapeindustries.dotmatrix.Glyph;
import com.escapeindustries.dotmatrix.GlyphFactory;
import com.escapeindustries.dotmatrix.Grid;
import com.escapeindustries.dotmatrix.Seperator;
import com.escapeindustries.dotmatrix.Space;

import junit.framework.TestCase;

public class FormatStringParserTests extends TestCase {

	private FormatStringParser parser;
	private GlyphFactory factory;
	private Grid grid;

	public void setUp() throws Exception {
		grid = new TestGrid();
		factory = new GlyphFactory(grid);
		parser = new FormatStringParser(factory);
	}

	public void testJustFourDigits() {
		// Arrange
		int expectedLength = 4;
		// Act
		Glyph[] result = parser.parse("0000");
		// Assert
		assertEquals("Correct number of Glyphs parsed", expectedLength,
				result.length);
		checkAllDigits(result);
	}

	public void testJustEightDigits() {
		// Arrange
		int expectedLength = 8;
		// Act
		Glyph[] result = parser.parse("00000000");
		// Assert
		assertEquals("Correct number of Glyphs parsed", expectedLength,
				result.length);
		checkAllDigits(result);
	}

	public void testSingleColon() {
		// Arrange
		int expectedLength = 1;
		// Act
		Glyph[] results = parser.parse(":");
		// Assert
		assertEquals("Correct number of Glyphs parsed", expectedLength,
				results.length);
		assertTrue("Results contains 1 colon", results[0] instanceof Seperator);
	}

	public void testDigitsAndColons() {
		// Arrange
		Glyph[] expected = new Glyph[] { new Digit(grid, 0, 0),
				new Digit(grid, 0, 0), new Seperator(grid, 0, 0),
				new Digit(grid, 0, 0), new Digit(grid, 0, 0) };
		// Act
		Glyph[] results = parser.parse("00:00");
		// Assert
		assertEquals("Correct number of Glyphs parsed", expected.length,
				results.length);
		checkGlyphTypesMatch(expected, results);
	}

	public void testSingleSpace() {
		// Arrange
		int expectedLength = 1;
		// Act
		Glyph[] results = parser.parse(" ");
		// Assert
		assertEquals("Correct number of Glyphs parsed", expectedLength,
				results.length);
		assertTrue("Results contains just a space", results[0] instanceof Space);
	}

	public void testDigitsColonsAndSpaces() {
		// Arrange
		String format = " 0 0 : 0 0 ";
		Glyph[] expected = new Glyph[] { new Space(), new Digit(grid, 0, 0),
				new Space(), new Digit(grid, 0, 0), new Space(),
				new Seperator(grid, 0, 0), new Space(), new Digit(grid, 0, 0),
				new Space(), new Digit(grid, 0, 0), new Space() };
		// Act
		Glyph[] results = parser.parse(format);
		// Assert
		assertEquals("Correct number of Glyphs parsed", format.length(),
				results.length);
		checkGlyphTypesMatch(expected, results);
	}

	private void checkGlyphTypesMatch(Glyph[] expected, Glyph[] results) {
		if (expected.length != results.length) {
			fail("Expected " + expected.length + " Glyphs but got "
					+ results.length);
		}
		for (int i = 0; i < results.length; i++) {
			assertEquals("Glyph type is as expected", results[i].getClass(),
					expected[i].getClass());
		}
	}

	private void checkAllDigits(Glyph[] result) {
		for (int i = 0; i < result.length; i++) {
			assertTrue("Glyph array item " + i + " is a Digit",
					result[i] instanceof Digit);
		}
	}
}
