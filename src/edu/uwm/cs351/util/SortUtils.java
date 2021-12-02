package edu.uwm.cs351.util;

import java.util.Comparator;

public class SortUtils<T> {

	private final Comparator<T> comparator;
	
	public SortUtils(Comparator<T> comp) {
		comparator = comp;
	}
	
	public void insertionSort(T[] array) {
		for (int s = 1; s < array.length; ++s) {
			T current = array[s];
			int hole = s;
			while (hole > 0 && comparator.compare(current, array[hole-1]) < 0) {
			 
				array[hole] = array[hole-1];
				--hole;
				
			}
			array[hole] = current;
		}
	}
	
	private int partition(T[] array, int lo, int hi) {
		T pivot = array[lo];
		int hole = lo;
		while (hi - lo > 1) {
			if (hole == lo) {
				if (comparator.compare(pivot, array[hi-1]) > 0) {
					array[hole] = array[hi-1];
					hole = hi-1;
				} else {
					--hi;
				}
			} else {
				if (comparator.compare(pivot, array[lo]) >= 0) {
					++lo;
				} else {
					array[hole] = array[lo];
					hole = lo;
				}
			}
		}
		array[hole] = pivot;
		return hole;
	}
	
	/**
	 * Sort a range in the array
	 * @param array
	 * @param lo inclusive
	 * @param hi exclusive
	 */
	private void qshelp(T[] array, int lo, int hi) {
		if (hi - lo < 2) return;
		int pivot = partition(array, lo, hi);
		qshelp(array, lo, pivot);
		qshelp(array, pivot+1, hi);
		// everything is sorted!
	}
	public void quickSort(T[] array) {
		qshelp(array, 0, array.length);
	}
}
