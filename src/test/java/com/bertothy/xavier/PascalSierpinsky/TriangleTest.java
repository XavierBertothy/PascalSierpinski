package com.bertothy.xavier.PascalSierpinsky;

import java.math.BigInteger;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.bertothy.xavier.PascalSierpinski.model.Triangle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TriangleTest extends TestCase {

	static Logger log = Logger.getLogger(TriangleTest.class.getName());

	public TriangleTest(String testName) {
		super(testName);
		log.info(testName);
	}

	public static Test suite() {
		System.out.println();
		log.info("suite");
		TestSuite suite = new TestSuite();
		suite.addTest(new TriangleTest("triangleWithLevel0"));
		suite.addTest(new TriangleTest("triangleWithLevel51"));
		suite.addTest(new TriangleTest("triangleWithLevel1"));
		suite.addTest(new TriangleTest("triangleWithLevel1GetNodeTotal"));
		suite.addTest(new TriangleTest("triangleWithLevel2GetNodeTotal"));
		suite.addTest(new TriangleTest("triangleWithLevel5GetNodeTotal"));
		suite.addTest(new TriangleTest("triangleWithLevel1GetNumberHoryzontally"));
		suite.addTest(new TriangleTest("triangleWithLevel2GetNumberHoryzontally"));
		suite.addTest(new TriangleTest("triangleWithLevel3GetNumberHoryzontally"));
		suite.addTest(new TriangleTest("triangleWithLevel6GetNumberHoryzontally"));
		suite.addTest(new TriangleTest("triangleWithLevel6GetNumberDiagonally"));
		return suite;
	}

	public void triangleWithLevel0() {
		try {
			new Triangle(0);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Level 0 not valid. "
					+ Triangle.MINLEVEL + " <= level <= " + Triangle.MAXLEVEL
					+ ".");
		}
	}

	public void triangleWithLevel51() {
		try {
			new Triangle(51);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Level 51 not valid. "
					+ Triangle.MINLEVEL + " <= level <= " + Triangle.MAXLEVEL
					+ ".");
		}
	}

	public void triangleWithLevel1() {
		Triangle t = new Triangle(1);
		assertEquals(t.getLevel(), 1);
	}

	public void triangleWithLevel1GetNodeTotal() {
		Triangle t = new Triangle(1);
		assertEquals(t.getNodeTotal(), 1);
	}

	public void triangleWithLevel2GetNodeTotal() {
		Triangle t = new Triangle(2);
		assertEquals(t.getNodeTotal(), 3);
	}

	public void triangleWithLevel5GetNodeTotal() {
		Triangle t = new Triangle(5);
		assertEquals(t.getNodeTotal(), 15);
	}

	public void triangleWithLevel1GetNumberHoryzontally() {
		Triangle t = new Triangle(1);
		assertTrue(Arrays.equals(t.getNumberHoryzontally(),
				new BigInteger[] { new BigInteger("1") }));
	}

	public void triangleWithLevel2GetNumberHoryzontally() {
		Triangle t = new Triangle(2);
		BigInteger[] expectedArray = new BigInteger[] { new BigInteger("1"),
				new BigInteger("1"), new BigInteger("1") };
		testBuildAndGetNumber(t, expectedArray, "H");
	}

	public void triangleWithLevel3GetNumberHoryzontally() {
		Triangle t = new Triangle(3);
		BigInteger[] expectedArray = new BigInteger[] { new BigInteger("1"),
				new BigInteger("1"), new BigInteger("1"), new BigInteger("1"),
				new BigInteger("2"), new BigInteger("1") };
		testBuildAndGetNumber(t, expectedArray, "H");
	}

	public void triangleWithLevel6GetNumberHoryzontally() {
		Triangle t = new Triangle(6);
		BigInteger[] expectedArray = new BigInteger[] { new BigInteger("1"),
				new BigInteger("1"), new BigInteger("1"), new BigInteger("1"),
				new BigInteger("2"), new BigInteger("1"), new BigInteger("1"),
				new BigInteger("3"), new BigInteger("3"), new BigInteger("1"),
				new BigInteger("1"), new BigInteger("4"), new BigInteger("6"),
				new BigInteger("4"), new BigInteger("1"), new BigInteger("1"),
				new BigInteger("5"), new BigInteger("10"),
				new BigInteger("10"), new BigInteger("5"), new BigInteger("1") };
		testBuildAndGetNumber(t, expectedArray, "H");
	}

	public void triangleWithLevel6GetNumberDiagonally() {
		Triangle t = new Triangle(6);
		BigInteger[] expectedArray = new BigInteger[] { new BigInteger("1"),
				new BigInteger("1"), new BigInteger("1"), new BigInteger("1"),
				new BigInteger("1"), new BigInteger("1"), new BigInteger("1"),
				new BigInteger("2"), new BigInteger("3"), new BigInteger("4"),
				new BigInteger("5"), new BigInteger("1"), new BigInteger("3"),
				new BigInteger("6"), new BigInteger("10"), new BigInteger("1"),
				new BigInteger("4"), new BigInteger("10"),
				new BigInteger("1"), new BigInteger("5"), new BigInteger("1") };
		testBuildAndGetNumber(t, expectedArray, "D");
	}

	private void testBuildAndGetNumber(Triangle t, BigInteger[] expectedArray, String method) {
		BigInteger[] array = (method.equals("H"))?t.getNumberHoryzontally():t.getNumberDiagonally();
		if (array == null)
			log.info("array null");
		else
			log.info("array: " + Arrays.toString(array));
		log.debug(Arrays.toString(array));
		log.debug(Arrays.toString(expectedArray));
		assertTrue(Arrays.equals(array, expectedArray));
	}
}
