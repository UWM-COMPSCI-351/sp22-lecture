package edu.uwm.cs351;

public class Queue<E> {

	public void enqueue(E e) {};
	
	/**
	 * Return the first element of the queue without removing it.
	 * @return element that would be dequeued.
	 * @throws NoSuchELementException if the queue is empty
	 */
	public E front() {
		return null;
	}
	
	/**
	 * Remove and return the first element in the queue.
	 * @return element after removing from front of queue
	 * @throws NoSuchELementException if the queue is empty
	 */
	public E dequeue() {
		return null;
	};
	
	/**
	 * Return whether the queue is empty
	 * @return wheher queue has no elements in it
	 */
	public boolean isEmpty() {
		return false;
	}
	
	/**
	 * Return the number of elements in the queue.
	 * @return number of elements in queue
	 */
	public int size() {
		return -1;
	}
}
