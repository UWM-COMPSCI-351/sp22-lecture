import java.util.Map;
import java.util.Set;

import edu.uwm.cs351.util.ArrayMap;
import edu.uwm.cs351.util.DefaultEntry;
import junit.framework.TestCase;

public class TestArrayMap extends TestCase {

	private String[] array;
	private ArrayMap<String> am;
	private Set<Map.Entry<Integer,String>> es;
	
	protected void setUp() {
		array = new String[] { "dog", "cat", "rat", "snake" };
		am = new ArrayMap<>(array);
		es = am.entrySet();
	}
	
	public void test0() {
		assertEquals(4, am.size());
		assertEquals(4, es.size());
	}
	
	public void test1() {
		assertEquals("dog", am.get(0));
		assertEquals("cat", am.get(1));
		assertEquals("rat", am.get(2));
		assertEquals("snake", am.get(3));
		assertNull(am.get(4));
		assertNull(am.get(-1));
	}
	
	public void test2() {
		assertTrue(am.containsKey(0));
		assertTrue(am.containsKey(1));
		assertTrue(am.containsKey(2));
		assertTrue(am.containsKey(3));
		assertFalse(am.containsKey(4));
		assertFalse(am.containsKey(-1));
	}
	
	public void test3() {
		assertEquals("snake", am.put(3, "ferret"));
		assertEquals("ferret", array[3]);
	}
	
	public void test4() {
		assertTrue(es.contains((Object)new DefaultEntry<>(3, "rat")));
		assertFalse(es.contains((Object)new DefaultEntry<>(3, "ferret")));
	}
}
