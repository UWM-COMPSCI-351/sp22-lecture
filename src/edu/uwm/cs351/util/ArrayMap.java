package edu.uwm.cs351.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ArrayMap<T> extends AbstractMap<Integer,T> {

	private final T[] array;
	
	/**
	 * Create a map from integers that is backed by the array. 
	 * @param a array to use, must not be null
	 */
	public ArrayMap(T[] a) {
		if (a == null) throw new NullPointerException();
		array = a;
	}
	
	@Override
	public Set<Map.Entry<Integer, T>> entrySet() {
		return null; // TODO
	}

	private class EntrySet extends AbstractSet<Map.Entry<Integer,T>> {

		@Override
		public Iterator<Entry<Integer, T>> iterator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	private class EntrySetInteger implements Iterator<Map.Entry<Integer, T>> {

		private int index = -1;
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Entry<Integer, T> next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
