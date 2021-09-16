package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * An immutable collection of a range of integers.
 * The range is represented efficiently with just the low and high bounds.
 */
public class RangeCollection extends AbstractCollection<Integer> {

	private final int lo, hi; // inclusive/exclusive;
	
	/**
	 * Construct a range of integers with inclusive/exclusive bounds [l,h)
	 * @param l inclusive lower bound
	 * @param h exclusive upper bound, must be >= l
	 */
	public RangeCollection(int l, int h) {
		lo = l;
		hi = h;
		if (h < l) throw new IllegalArgumentException("high must not be less than low");
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return hi - lo;
	}

	private class MyIterator implements Iterator<Integer> {
		int next = lo;

		@Override
		public boolean hasNext() {
			return next < hi;
		}

		@Override
		public Integer next() {
			int result = next;
			++next;
			return result;
		}
	}
}
