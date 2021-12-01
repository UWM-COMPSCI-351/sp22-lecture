package edu.uwm.cs351.util;

import java.util.Comparator;

public class QuickSort<T> {

	private Comparator<T> comparator;
	
	public QuickSort(Comparator<T> c) {
		comparator = c;
	}

	/**
	 * Sort the elements in the range of the array.
	 * @param array array to sort, must not be null
	 * @param lo inclusive lower bound
	 * @param hi exclusive supper bound
	 */
	public void sort(T[] array, int lo, int hi) {
		// TODO
	}
}
