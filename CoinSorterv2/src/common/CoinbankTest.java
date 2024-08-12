package common;

/*
 * JUnit test class.  Use these tests as models for your own.
 */

import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import proj1.Coinbank;

public class CoinbankTest {
	
	@Rule // a test will fail if it takes longer than 1/10 of a second to run
 	public Timeout timeout = Timeout.millis(100); 

	/**
	 * Sets up a bank with the given coins
	 * @param pennies number of pennies you want
	 * @param nickels number of nickels you want
	 * @param dimes number of dimes you want
	 * @param quarters number of quarters you want
	 * @return the Coinbank filled with the requested coins of each type
	 */
	private Coinbank makeBank(int pennies, int nickels, int dimes, int quarters) {
		Coinbank c = new Coinbank();
		int[] money = new int[]{pennies, nickels, dimes, quarters};
		int[] denom = new int[]{1,5,10,25};
		for (int index=0; index<money.length; index++) {
			int numCoins = money[index];
			for (int coin=0; coin<numCoins; coin++) {
				c.insert(denom[index]);
			}
		}
		return c;
	}

	@Test // bank should be empty upon construction
	public void testConstruct() {
		Coinbank emptyDefault = new Coinbank();
		assertEquals(0, emptyDefault.get(1));
		assertEquals(0, emptyDefault.get(5));
		assertEquals(0, emptyDefault.get(10));
		assertEquals(0, emptyDefault.get(25));
	}

	@Test // inserting penny should return true & one penny should be in the bank
	public void testInsertPenny_return()
	{
		Coinbank c = new Coinbank();
		assertTrue(c.insert(1));
		assertEquals(1,c.get(1));
	}

	@Test // inserting nickel should return true & one nickel should be in bank
	public void testInsertNickel_return()
	{
		Coinbank c = new Coinbank();
		assertTrue(c.insert(5));
		assertEquals(1,c.get(5));
	}

	@Test // inserting dime should return true & one dime should be in the bank
	public void testInsertDime_return()
	{
		Coinbank c = new Coinbank();
		assertTrue(c.insert(10));
		assertEquals(1,c.get(10));
	}

	@Test // inserting quarter should return true & one quarter should be in the bank
	public void testInsertQuarter_return()
	{
		Coinbank c = new Coinbank();
		assertTrue(c.insert(25));
		assertEquals(1,c.get(25));
	}

	@Test // inserts 3 nickels and 5 quarters, and should return 3 and 5
	public void testInsert_multiple()
	{
		Coinbank c = new Coinbank();
		for (int i = 0;i < 3;i++){
			assertTrue(c.insert(5));
		}
		for (int i = 0;i < 5;i++){
			assertTrue(c.insert(25));
		}
		assertEquals(3,c.get(5));
		assertEquals(5,c.get(25));
	}

	@Test // atempts to insert an invalid coin (3 cents), should return false and an empty bank
	public void testInsert_invalid()
	{
		Coinbank c = new Coinbank();
		assertEquals(false, c.insert(3));
		assertEquals(0, c.get(1));
		assertEquals(0, c.get(5));
		assertEquals(0, c.get(10));
		assertEquals(0, c.get(25));
	}
	
	@Test // getter should return correct values
	public void testGet()
	{
		Coinbank c = makeBank(0,2,15,1);
		assertEquals(0,c.get(1));
		assertEquals(2,c.get(5));
		assertEquals(15,c.get(10));
		assertEquals(1,c.get(25));
	}
	
	@Test // getter should not alter the bank
	public void testGet_contents()
	{
		Coinbank c = makeBank(0,2,15,1);
		c.get(1);
		c.get(5);
		c.get(10);
		c.get(25);
		String expected = "The bank currently holds $1.85 consisting of \n0 pennies\n2 nickels\n15 dimes\n1 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove, removes just enough quarters to match whats currently in the bank
	public void testRemove_justEnough()
	{
		Coinbank c = makeBank(4,1,3,5);
		assertEquals(5,c.remove(25,5));
		String expected = "The bank currently holds $0.39 consisting of \n4 pennies\n1 nickels\n3 dimes\n0 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove, tries to remove more coins than there are in the bank
	public void testRemove_overKill()
	{
		Coinbank c = makeBank(7,3,1,13);
		assertEquals(7,c.remove(1,20));
		assertEquals(3,c.remove(5,20));
		assertEquals(1,c.remove(10,20));
		assertEquals(13,c.remove(25,20));
		String expected = "The bank currently holds $0.0 consisting of \n0 pennies\n0 nickels\n0 dimes\n0 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove, tries to remove zero of a coin
	public void testRemove_zeroRemoved()
	{
		Coinbank c = makeBank(13,5,3,7);
		assertEquals(0,c.remove(5,0));
		String expected = "The bank currently holds $2.43 consisting of \n13 pennies\n5 nickels\n3 dimes\n7 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove, tries to remove less than zero of a coin
	public void testRemove_negativeRemove()
	{
		Coinbank c = makeBank(5,4,7,0);
		assertEquals(0,c.remove(10,-5));
		String expected = "The bank currently holds $0.95 consisting of \n5 pennies\n4 nickels\n7 dimes\n0 quarters\n";
		assertEquals(expected,c.toString());
	}
	
	@Test // remove should not do anything if a 3-cent coin is requested
	public void testRemove_invalidCoin()
	{
		Coinbank c = makeBank(4,1,3,5);
		assertEquals(0,c.remove(3,1));
	}
}
