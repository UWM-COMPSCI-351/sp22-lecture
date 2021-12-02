import java.util.Random;

import edu.uwm.cs351.util.SortUtils;
import junit.framework.TestCase;

public class TestEfficiencyQuickSort extends TestCase {

	private Integer[] array;
	private SortUtils<Integer> util = new SortUtils<>(Integer::compareTo);
	
	private static final int MAX = 1_000_000_000;

	@Override
	protected void setUp() {
		try {
			assert array.length == 42 : "cannot run test with assertions enabled";
		} catch (NullPointerException ex) {
			throw new IllegalStateException("Cannot run test with assertions enabled");
		}
	}

	private void setup(int n) {
		int max = 1 << n;
		array = new Integer[max];
		Random r = new Random();
		for (int i=0; i < max; ++i) {
			array[i] = r.nextInt(MAX);
		}
	}
	
	@Override
	protected void tearDown() {
		array = null;
	}
	
	private void check() {
		Integer last = null;
		for (Integer i : array) {
			if (last == null) last = i;
			else {
				if (last <= i) continue;
				assertFalse(last + " coming before " + i, true);
			}
		}
	}
	
	public void testA() {
		setup(0);
		util.quickSort(array);
		check();
	}
	
	public void testB() {
		setup(1);
		util.quickSort(array);
		check();
	}
	
	public void testC() {
		setup(2);
		util.quickSort(array);
		check();
	}
	
	public void testD() {
		setup(3);
		util.quickSort(array);
		check();
	}
	
	public void testE() {
		setup(4);
		util.quickSort(array);
		check();
	}
	
	public void testF() {
		setup(5);
		util.quickSort(array);
		check();
	}
	
	public void testG() {
		setup(6);
		util.quickSort(array);
		check();
	}
	
	public void testH() {
		setup(7);
		util.quickSort(array);
		check();
	}
	
	public void testI() {
		setup(8);
		util.quickSort(array);
		check();
	}
	
	public void testJ() {
		setup(9);
		util.quickSort(array);
		check();
	}
	
	public void testK() {
		setup(10);
		util.quickSort(array);
		check();
	}
	
	public void testL() {
		setup(11);
		util.quickSort(array);
		check();
	}
	
	public void testM() {
		setup(12);
		util.quickSort(array);
		check();
	}
	
	public void testN() {
		setup(13);
		util.quickSort(array);
		check();
	}
	
	public void testO() {
		setup(14);
		util.quickSort(array);
		check();
	}
	
	public void testP() {
		setup(15);
		util.quickSort(array);
		check();
	}
	
	public void testQ() {
		setup(16);
		util.quickSort(array);
		check();
	}
	
	public void testR() {
		setup(17);
		util.quickSort(array);
		check();
	}
	
	public void testS() {
		setup(18);
		util.quickSort(array);
		check();
	}
	
	public void testT() {
		setup(19);
		util.quickSort(array);
		check();
	}
	
	public void testU() {
		setup(20);
		util.quickSort(array);
		check();
	}
	
	public void testV() {
		setup(21);
		util.quickSort(array);
		check();
	}
	
	public void testW() {
		setup(22);
		util.quickSort(array);
		check();
	}
	
	public void testX() {
		setup(23);
		util.quickSort(array);
		check();
	}
	
	/*
	public void testY() {
		setup(24);
		util.quickSort(array);
		check();
	}
	
	public void testZ() {
		setup(25);
		util.quickSort(array);
		check();
	}
	*/
}
