import java.util.Map;
import java.util.Set;

import edu.uwm.cs351.util.ArrayMap;
import junit.framework.TestCase;

public class TestEfficiencyAM extends TestCase {

	private String[] array;
	private ArrayMap<String> am;
	private Set<Map.Entry<Integer,String>> es;
	
	private static final int SIZE = 1_000_000;
	
	protected void setUp() {
		array = new String[SIZE];
		for (int i=0; i < SIZE; ++i) {
			array[i] = ""+i;
		}
		am = new ArrayMap<>(array);
	}
	
	public void testGet() {
		for (int i=0; i < SIZE; ++i) {
			assertEquals(i, Integer.parseInt(am.get((Object)i)));
		}
	}
	
	public void testPut() {
		for (int i=0; i < SIZE; ++i) {
			am.put(i, "gone" + i);
			assertEquals("gone" + i, array[i]);
		}
	}
	
	public void testContainsKey() {
		for (int i=0; i < SIZE; ++i) {
			assertTrue(am.containsKey((Object)i));
			assertFalse(am.containsKey((Object)((i-SIZE))));
			assertFalse(am.containsKey(i+SIZE));
		}
	}
	
	public void testSetContains() {
		for (int i=0; i < SIZE; ++i) {
			
		}
	}
}
