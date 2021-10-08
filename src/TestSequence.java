import java.util.function.Supplier;

import edu.uwm.cs351.Sequence;
import junit.framework.TestCase;


public class TestSequence extends TestCase {
	Integer e1 = 100; 
	Integer e2 = 50;
	Integer e3 = 33;
	Integer e4 = 25;
	Integer e5 = 20;

	Sequence<Integer> s, se;
	
	@Override
	protected void setUp() {
		s = new Sequence<Integer>();
		se = new Sequence<Integer>();
		try {
			assert 1/(e1-100) == 42 : "OK";
			System.err.println("Assertions must be enabled to use this test suite.");
			System.err.println("In Eclipse: add -ea in the VM Arguments box under Run>Run Configurations>Arguments");
			assertFalse("Assertions must be -ea enabled in the Run Configuration>Arguments>VM Arguments",true);
		} catch (ArithmeticException ex) {
			return;
		}
	}

	protected <T> void assertException(Class<?> excClass, Supplier<T> producer) {
		try {
			T result = producer.get();
			assertFalse("Should have thrown an exception, not returned " + result,true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	protected <T> void assertException(Runnable f, Class<?> excClass) {
		try {
			f.run();
			assertFalse("Should have thrown an exception, not returned",true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	/**
	 * Return the value as an integer
	 * <dl>
	 * <dt>-1<dd><i>(an exception was thrown)
	 * <dt>0<dd>null
	 * <dt>1<dd>e1
	 * <dt>2<dd>e2
	 * <dt>3<dd>e3
	 * <dt>4<dd>e4
	 * <dt>5<dd>e5
	 * </dl>
	 * @return integer encoding of value supplied
	 */
	protected int asInt(Supplier<Integer> g) {
		try {
			Integer n = g.get();
			if (n == null) return 0;
			return 100/n;
		} catch (RuntimeException ex) {
			return -1;
		}
	}

	public void test00() {
		// Nothing inserted yet:
		assertEquals(0,s.size());
		assertFalse(s.hasCurrent());
		s.start();
		assertFalse(s.hasCurrent());
	}
	
	public void test01() {
		// Initially empty.
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(-1,asInt(() -> s.getCurrent()));
		s.insert(e1);
		assertEquals(1,asInt(() -> s.getCurrent()));
		s.start();
		assertEquals(1,asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(-1,asInt(() -> s.getCurrent()));
	}
	
	public void test02() {
		// Initially empty.
		s.insert(e4);
		s.insert(e5);
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(5,asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(4,asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(-1,asInt(() -> s.getCurrent()));
	}
	
	public void test03() {
		// Initially empty
		s.insert(e3);
		s.advance();
		s.insert(e2);
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(2,asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(-1,asInt(() -> s.getCurrent()));
		s.start();
		assertEquals(3,asInt(() -> s.getCurrent()));
	}
	
	public void test05() {
		// Initially empty
		s.insert(null);
		assertEquals(1,s.size());
		assertEquals(true,s.hasCurrent());
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(0,asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(-1,asInt(() -> s.getCurrent()));
	}
	
	public void test06() {
		s.insert(e1);
		s.insert(e2);
		s.start();
		s.advance();
		assertSame(e1,s.getCurrent());
		Sequence<Integer> s2 = new Sequence<Integer>();
		s2.insert(e4);
		s.insertAll(s2);
		assertEquals(3,s.size());
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(4,asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(1,asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(-1,asInt(() -> s.getCurrent()));
	}
	
	public void test07() {
		s.start();
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
	}
	
	public void test08() {
		s.start();
		try {
			s.removeCurrent();
			assertFalse("empty.removeCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
	}
	
	public void test09() {
		try {
			s.advance();
			assertFalse("s.advance() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
	}
	
	public void test10() {
		s.insert(e1);
		assertEquals(1,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.start();
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertEquals(1,s.size());
		assertFalse(s.hasCurrent());
		s.start();
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(1,s.size());
	}

	public void test11() {
		s.insert(e1);
		s.removeCurrent();
		assertFalse(s.hasCurrent());
		assertEquals(0,s.size());	
		s.insert(e2);
		s.start();
		assertSame(e2,s.getCurrent());
		assertEquals(1,s.size());
	}
	
	public void test12() {
		s.insert(e2);
		s.start();
		s.advance();
		try {
			s.advance();
			assertFalse("s.advance() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		assertFalse(s.hasCurrent());
		assertEquals(1,s.size());
	}


	public void test13() {
		s.insert(e2);
		s.advance();
		try {
			s.removeCurrent();
			assertFalse("s.removeCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		assertFalse(s.hasCurrent());
		assertEquals(1,s.size());
	}

	public void test14() {
		s.insert(e2);
		s.advance();
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		assertFalse(s.hasCurrent());
		assertEquals(1,s.size());
	}

	public void test20() {
		s.insert(e1);
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.insert(e2);
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		assertEquals(2,s.size());
		s.start();
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
		assertEquals(2,s.size());
		s.start();
		assertSame(e2,s.getCurrent());
		s.advance();
		s.start();
		assertSame(e2,s.getCurrent());
	}
	
	public void test21() {
		s.insert(e1);
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		s.insert(e2);
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		assertEquals(2,s.size());
		s.start();
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
		assertTrue(s.hasCurrent());
	}
	
	public void test22() {
		s.insert(e2);
		s.insert(e1);
		s.start();
		s.removeCurrent();
		assertTrue(s.hasCurrent());
		assertEquals(1,s.size());
		assertEquals(e2,s.getCurrent());
		s.advance();
		s.insert(e3);
		assertTrue(s.hasCurrent());
		assertEquals(2,s.size());
		assertSame(e3,s.getCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
	}

	public void test23() {
		s.insert(e2);
		s.insert(e1);
		s.start();
		s.advance();
		s.removeCurrent();
		assertFalse(s.hasCurrent());
		assertEquals(1,s.size());
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		s.insert(e3);
		assertTrue(s.hasCurrent());
		assertEquals(2,s.size());
		assertSame(e3,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
		s.start();
		assertTrue(s.hasCurrent());
		assertEquals(e1,s.getCurrent());
	}
	
	public void test30() {
		s.insert(e1);
		s.insert(e2);
		s.insert(e3);
		assertEquals(3,s.size());
		s.start();
		assertTrue(s.hasCurrent());
		assertSame(e3,s.getCurrent());
		assertTrue(s.hasCurrent());
		assertSame(e3,s.getCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
		assertEquals(3,s.size());
		s.start();
		assertSame(e3,s.getCurrent());
		s.advance();
		s.start();
		assertSame(e3,s.getCurrent());
	}
	
	public void test31() {
		s.insert(e1);
		s.advance();
		s.insert(e2);
		s.insert(e3);
		assertEquals(3,s.size());
		s.start();
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e3,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
		assertEquals(3,s.size());
		s.start();
		assertSame(e1,s.getCurrent());
	}

	public void test32() {
		s.insert(e2);
		s.advance();
		s.insert(e3);
		s.start();
		s.insert(e1);
		assertEquals(3,s.size());
		s.start();
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e3,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
		assertEquals(3,s.size());
	}
	
	public void test33() {
		s.insert(e3);
		s.insert(e2);
		s.insert(e1);
		// s.start();  // redundant
		assertEquals(e1,s.getCurrent());
		s.removeCurrent();
		assertEquals(2,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
	}

	public void test34() {
		s.insert(e3);
		s.insert(e2);
		s.insert(e1);
		s.start(); // REDUNDANT
		s.advance();
		assertSame(e2,s.getCurrent());
		s.removeCurrent();
		assertEquals(2,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e3,s.getCurrent());
	}
	
	public void test35() {
		s.insert(e3);
		s.insert(e2);
		s.insert(e1);
		// s.start(); // REDUNDANT
		s.advance();
		s.advance();
		assertSame(e3,s.getCurrent());
		s.removeCurrent();
		assertEquals(2,s.size());
		assertFalse(s.hasCurrent());
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex, ex instanceof IllegalStateException);
		}
		try {
			s.advance();
			assertFalse("s.advance() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex, ex instanceof IllegalStateException);
		}
		s.start();
		assertSame(e1,s.getCurrent());
	}
 
	public void test39() {
		s.insert(e1);
		s.insert(e2);
		s.insert(e3);
		s.insert(e4);
		s.insert(e5);
		assertSame(e5,s.getCurrent());
		s.insert(e1);
		s.insert(e2);
		s.insert(e3);
		s.insert(e4);
		s.insert(e5);
		s.insert(e1);
		s.insert(e2);
		assertEquals(12,s.size());
		s.removeCurrent();
		assertTrue(s.hasCurrent());
		s.start();
		s.removeCurrent();
		assertSame(e5,s.getCurrent());
		assertEquals(10,s.size());
		s.start();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e1,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());
	}
	
	public void test40() {
		s.insert(null);
		s.advance();
		s.insert(e2);
		s.advance();
		s.insert(null);
		
		assertEquals(3,s.size());
		assertTrue(s.hasCurrent());
		assertSame(null,s.getCurrent());
		s.start();
		assertTrue(s.hasCurrent());
		assertSame(null,s.getCurrent());
		s.removeCurrent();
		assertEquals(2,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(null,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
	}

	public void test50() {
		s.transform((n) -> n + 10);
		assertEquals(0,s.size());
	}
	
	public void test51() {
		s.insert(5);
		s.transform((n) -> n + 10);
		assertEquals(15,s.getCurrent().intValue());
	}
	
	public void test52() {
		s.insert(5);
		s.insert(2);
		s.transform((n) -> (n%2 == 0) ? n/2 : null);
		assertEquals(1,s.getCurrent().intValue());
		s.advance();
		assertNull(s.getCurrent());
	}
	
	public void test53() {
		s.insert(5);
		s.insert(3);
		s.advance();
		s.insert(4);
		s.transform((n) -> n*2);
		assertEquals(8,s.getCurrent().intValue());
		s.start();
		assertEquals(6,s.getCurrent().intValue());
		assertEquals(3,s.size());
	}
	
	public void test54() {
		s.insert(5);
		s.insert(4);
		s.advance();
		s.advance();
		s.transform((n) -> null);
		assertEquals(2,s.size());
		assertFalse(s.hasCurrent());
		s.start();
		assertNull(s.getCurrent());
	}
	
	public void test59() {
		s.insert(3);
		s.insert(1);
		s.advance();
		s.advance();
		s.insert(0);
		assertException(() -> s.transform((n) -> 1/n), ArithmeticException.class);
		s.start();
		assertEquals(1,s.getCurrent().intValue());
		s.advance();
		assertEquals(0,s.getCurrent().intValue());
	}
	
	public void test60() {
		s.insertAll(se);
		assertFalse(s.hasCurrent());
		assertEquals(0,s.size());
		s.insert(e1);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertEquals(1,s.size());
		assertEquals(0,se.size());
		assertSame(e1,s.getCurrent());
		s.advance();
		s.insertAll(se);
		assertFalse(s.hasCurrent());
		assertEquals(1,s.size());
		assertEquals(0,se.size());
		s.insert(e2);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertSame(e2,s.getCurrent());
		assertEquals(2,s.size());
		assertEquals(0,se.size());
		s.start();
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(2,s.size());
		assertEquals(0,se.size());
	}
	
	public void test61() {
		se.insert(e1);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertTrue(se.hasCurrent());
		assertEquals(1,s.size());
		assertEquals(1,se.size());
		s.start();
		assertSame(e1,s.getCurrent());
		assertSame(e1,se.getCurrent());
	}
	
	public void test62() {
		se.insert(e1);
		s.insert(e2);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertEquals(2,s.size());
		assertEquals(1,se.size());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
	}
	
	public void test63() {
		se.insert(e1);
		s.insert(e2);
		s.advance();
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(2,s.size());
		assertEquals(1,se.size());
		assertTrue(se.hasCurrent());
		assertSame(e1,se.getCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e1,s.getCurrent());
	}
	
	public void test64() {
		se.insert(e1);
		se.advance();
		s.insert(e3);
		s.insert(e2);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(3,s.size());
		assertEquals(1,se.size());
		assertFalse(se.hasCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());	
	}
	
	public void test65() {
		se.insert(e1);
		s.insert(e2);
		s.advance();
		s.insert(e3);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertEquals(3,s.size());
		assertEquals(1,se.size());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
	}
	
	public void test66() {
		se.insert(e1);
		s.insert(e2);
		s.advance();
		s.insert(e3);
		s.advance();
		assertFalse(s.hasCurrent());
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(3,s.size());
		assertEquals(1,se.size());
		assertSame(e1,se.getCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
		s.advance();
		assertSame(e1,s.getCurrent());
	}

	public void test67() {
		se.insert(e2);
		se.insert(e1);	
		s.insert(e4);
		s.insert(e3);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertEquals(4,s.size());
		assertEquals(2,se.size());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());	
	}

	public void test68() {
		se.insert(e2);
		se.insert(e1);
		se.advance();
		s.insert(e3);
		s.advance();
		s.insert(e4);
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertEquals(4,s.size());
		assertEquals(2,se.size());
		assertSame(e2,se.getCurrent()); se.advance();
		assertFalse(se.hasCurrent());
		// check s
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());	
		s.start();
		assertSame(e3,s.getCurrent());
	}

	public void test69() {
		se.insert(e2);
		se.insert(e1);
		se.advance();
		se.advance();
		s.insert(e3);
		s.advance();
		s.insert(e4);
		s.advance();
		assertFalse(s.hasCurrent());
		assertFalse(se.hasCurrent());
		s.insertAll(se);
		assertTrue(s.hasCurrent());
		assertFalse(se.hasCurrent());
		assertEquals(4,s.size());
		assertEquals(2,se.size());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		s.start();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());	
	}

	public void test70() {
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		// se has 24 elements
		s.insert(e1);
		s.advance();
		s.insert(e2);
		s.insertAll(se);
		assertEquals(26,s.size());
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		s.insertAll(se);
		assertEquals(50,s.size());
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();		
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		// interruption
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		// done with all 24 copies most recently inserted
		// now back to the original
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); // etc.
	}
	
	public void test71() {
		s.insertAll(s);
		assertFalse(s.hasCurrent());
		assertEquals(0,s.size());
	}
	
	
	public void test72() {
		s.insert(e1);
		s.insertAll(s);
		assertEquals(2,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
	}
	
	public void test73() {
		s.insert(e1);
		s.advance();
		s.insertAll(s);
		assertEquals(2,s.size());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertFalse(s.hasCurrent());
	}
	
	public void test74() {
		s.insert(e1);
		s.removeCurrent();
		assertEquals(0,s.size());
		assertFalse(s.hasCurrent());
	}
	
	public void test75() {
		s.insert(e2);
		s.insert(e1);
		s.insertAll(s);
		assertEquals(4,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());		
	}
	
	public void test76() {
		s.insert(e1);
		s.advance();
		s.insert(e2);
		s.insertAll(s);
		assertEquals(4,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());	
		s.start();
		assertSame(e1,s.getCurrent());
	}

	public void test77() {
		s.insert(e1);
		s.advance();
		s.insert(e2);
		s.advance();
		assertFalse(s.hasCurrent());
		s.insertAll(s);
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());
		assertEquals(4,s.size());
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());		
	}

	public void test78() {
		s.insert(e1);
		s.advance();
		s.insert(e2);
		s.insertAll(s);
		s.removeCurrent();
		s.insert(e3);
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		s.advance();
		s.insertAll(s);
		assertEquals(8,s.size());
		assertTrue(s.hasCurrent());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.hasCurrent());		
	}
	
	public void test79() {
		se.insert(e1);
		se.advance();
		se.insert(e2);	
		s.insert(e3);
		s.advance();
		s.insert(e4);
		s.insertAll(se); // s = e3 * e1 e2 e4
		s.advance();
		s.advance();
		s.insert(e5); // s = e3 e1 e2 * e5 e4
		s.advance();
		assertTrue(s.hasCurrent());
		assertSame(e4,s.getCurrent());
		assertEquals(5,s.size());
		assertEquals(2,se.size());
		assertSame(e2,se.getCurrent());
		se.advance();
		assertFalse(se.hasCurrent());
		se.start();
		assertSame(e1,se.getCurrent());
	}
	
	public void test80() {
		Sequence<Integer> c = s.clone();
		assertFalse(c.hasCurrent());
		assertEquals(0, c.size());
	}
	
	public void test81() {
		s.insert(e1);
		Sequence<Integer> c = s.clone();
		
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e1,c.getCurrent()); c.advance();
		assertFalse(s.hasCurrent());
		assertFalse(c.hasCurrent());
	}
	
	public void test82() {
		s.insert(e1);
		s.advance();
		Sequence<Integer> c = s.clone();
		
		assertFalse(s.hasCurrent());
		assertFalse(c.hasCurrent());
	}

	public void test83() {
		Sequence<Integer> c = s.clone();
		assertFalse(c.hasCurrent());
		
		s.insert(e1);
		c = s.clone();
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,c.getCurrent());
		
		s.advance();
		s.insert(e2);
		c = s.clone();
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e2,s.getCurrent());
		assertSame(e2,c.getCurrent());
		s.advance();
		c.advance();
		assertFalse(s.hasCurrent());
		assertFalse(c.hasCurrent());
		
		s.insert(e3);
		assertTrue(s.hasCurrent());
		assertFalse(c.hasCurrent());
		
		c = s.clone();
		assertSame(e3,s.getCurrent());
		assertSame(e3,c.getCurrent());
		s.advance();
		c.advance();
		assertFalse(s.hasCurrent());
		assertFalse(c.hasCurrent());
		s.start();
		c.start();
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,c.getCurrent());
		s.advance();
		c.advance();
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e2,s.getCurrent());
		assertSame(e2,c.getCurrent());
		
		s.start();
		c = s.clone();
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,c.getCurrent());
		s.advance();
		c.advance();
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e2,s.getCurrent());
		assertSame(e2,c.getCurrent());
		s.advance();
		c.advance();
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		assertSame(e3,s.getCurrent());
		assertSame(e3,c.getCurrent());		
	}
	
	public void test84() {
		s.insert(e1);
		s.advance();
		s.insert(e3);
		s.insert(e2);
		s.removeCurrent();
		
		Sequence<Integer> c = s.clone();
		
		assertEquals(2,c.size());
		
		assertTrue(s.hasCurrent());
		assertTrue(c.hasCurrent());
		
		assertSame(e3,s.getCurrent());
		assertSame(e3,c.getCurrent());
	}

	public void test85() {
		s.insert(e1);
		s.advance();
		s.insert(e2);
		
		Sequence<Integer> c = s.clone();
		s.insert(e3);
		c.insert(e4);
		
		assertSame(e3,s.getCurrent());
		assertSame(e4,c.getCurrent());
		s.advance();
		c.advance();
		assertSame(e2,s.getCurrent());
		assertSame(e2,c.getCurrent());
		s.advance();
		c.advance();
		assertFalse(s.hasCurrent());
		assertFalse(c.hasCurrent());
		
		s.start();
		c.start();
		assertSame(e1,s.getCurrent());
		assertSame(e1,c.getCurrent());
		s.advance();
		c.advance();
		assertSame(e3,s.getCurrent());
		assertSame(e4,c.getCurrent());
	}
	
	public void test86() {
		Sequence<Foo> s1 = new Sequence<>();
		s1.insert(new Foo("hi"));
		assertEquals("hi",s1.getCurrent().getLabel());
	}
	
	private class Foo {
		private String label;
		public Foo(String s) {
			label = s;
		}
		@Override
		public String toString() {
			return super.toString() + label;
		}
		
		public String getLabel() {
			return label;
		}
	}
	
}
