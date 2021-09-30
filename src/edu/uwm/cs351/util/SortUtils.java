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
}
