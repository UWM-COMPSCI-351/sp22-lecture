package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Iterator;

public class RangeCollection extends AbstractCollection<Integer> {

	public int lo, hi; // inclusive/exclusive;
	
	public RangeCollection(int l, int h) {
		lo = l;
		hi = h;
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
