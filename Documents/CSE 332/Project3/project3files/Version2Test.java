// Blake Campbell & Samantha Brender

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class Version2Test {
	Version1 test;
	
	@Before
	public void setUp() throws Exception {
		CensusData cd = PopulationQuery.parse("CenPop2010.txt");
		test = new Version1(cd);
	}

	@Test
	public void testGet4Corners() {
		int total = test.get4Corners();
		assertEquals(312471327, total);
	}

	@Test
	public void testGetSubPopulation() {
		test.get4Corners();
		int entireUS = test.getSubPopulation(1, 1, 100, 500, 100, 500);
		assertEquals(312471327, entireUS);
		int sub1 = test.getSubPopulation(1, 1, 50, 500, 100, 500);
		assertEquals(27820072, sub1);
		int sub2 = test.getSubPopulation(1, 1, 5, 4, 20, 25);
		assertEquals(1360301, sub2);
		int sub3 = test.getSubPopulation(1, 12, 9, 25, 20, 25);
		assertEquals(710231, sub3);
		int sub4 = test.getSubPopulation(9, 1, 20, 13, 20, 25);
		assertEquals(310400795, sub4);
		int sub5 = test.getSubPopulation(5, 5, 7, 5, 9, 14);
		assertEquals(18820388, sub5);
		int sub6 = test.getSubPopulation(6, 3, 8, 4, 9, 14);
		assertEquals(105349619, sub6);
	}
	
	@Test
	public void testExceptions() {
		// test for exception if west boundary is less than 1
		try {
			test.getSubPopulation(0, 1, 50, 500, 100, 500);
			fail("expected an IllegalArgumentException for w < 1");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
		// test for exception if south boundary is less than 1
		try {
			test.getSubPopulation(1, 0, 50, 500, 100, 500);
			fail("expected an IllegalArgumentException for s < 1");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
		// test for exception if west boundary is greater than the
		// total number of columns
		try {
			test.getSubPopulation(101, 1, 50, 500, 100, 500);
			fail("expected an IllegalArgumentException for w > x");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
		// test for exception if south boundary is greater than the
		// total number of rows
		try {
			test.getSubPopulation(1, 501, 50, 500, 100, 500);
			fail("expected an IllegalArgumentException for s > y");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
		// test for exception if north boundary is less than south
		// boundary
		try {
			test.getSubPopulation(20, 20, 50, 19, 100, 500);
			fail("expected an IllegalArgumentException for n < s");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
		// test for exception if north boundary is greater than
		// the total number of rows
		try {
			test.getSubPopulation(20, 20, 50, 501, 100, 500);
			fail("expected an IllegalArgumentException for n > y");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
		// test for exception if east boundary is less than
		// the west boundary
		try {
			test.getSubPopulation(20, 20, 19, 500, 100, 500);
			fail("expected an IllegalArgumentException for e < w");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
		// test for exception if east boundary is greater than
		// the total number of columns
		try {
			test.getSubPopulation(20, 20, 101, 500, 100, 500);
			fail("expected an IllegalArgumentException for e > x");
		} catch (IllegalArgumentException e) {
			assertSame(e.getMessage(), "provide proper arguments");
		}
	}
}
