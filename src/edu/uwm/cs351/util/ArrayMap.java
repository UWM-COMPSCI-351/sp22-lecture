package edu.uwm.cs351.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
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
	
	@Override // required
	public Set<Map.Entry<Integer, T>> entrySet() {
		return new EntrySet();
	}

	@Override // implementation
	public T put(Integer key, T value) {
		int index = key;
		T result = array[index];
		array[index] = value;
		return result;
	}

	@Override // efficiency
	public T get(Object arg0) {
		if (!(arg0 instanceof Integer)) return null;
		int index = (Integer)arg0;
		if (index < 0 || index >= array.length) return null;
		return array[index];
	}

	@Override // efficiency
	public boolean containsKey(Object arg0) {
		if (!(arg0 instanceof Integer)) return false;
		int index = (Integer)arg0;
		return 0 <= index && index < array.length;
	}

	private class EntrySet extends AbstractSet<Map.Entry<Integer,T>> {

		@Override
		public Iterator<Entry<Integer, T>> iterator() {
			return new EntrySetIterator();
		}

		@Override
		public int size() {
			return array.length;
		}

		@Override //efficiency
		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry<?, ?>)) return false;
			Map.Entry<?,?> entry = (Map.Entry<?, ?>)o;
			Object key = entry.getKey();
			// TODO Auto-generated method stub
			return super.contains(o);
		}
		
	}
	
	private class EntrySetIterator implements Iterator<Map.Entry<Integer, T>> {

		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return (index+1) < array.length;
		}

		@Override
		public Entry<Integer, T> next() {
			if (!hasNext()) throw new NoSuchElementException("no more");
			++index;
			return new DefaultEntry<>(index,array[index]);
		}
		
	}
}
