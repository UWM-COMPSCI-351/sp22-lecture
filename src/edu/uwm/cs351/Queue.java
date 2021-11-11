package edu.uwm.cs351;

import java.util.NoSuchElementException;

public class Queue<E> {

	private E[] data;
	private int front, rear;
	
	@SuppressWarnings("unchecked")
	private E[] makeArray(int cap) {
		return (E[]) new Object[cap];  // lie!
	}
	
	private void ensureCapacity(int cap) {
		// TODO
	}
	
	public Queue() {
		data = makeArray(2);
		front = rear = 0;
	}
	
	public void enqueue(E e) {
		System.out.println("Before enq, front = " + front + ", rear = " + rear);
		ensureCapacity(size()+2);
		data[rear] = e;
		System.out.println("A enq, front = " + front + ", rear = " + rear);
		++rear;
		System.out.println("B enq, front = " + front + ", rear = " + rear);
		if (rear >= data.length) rear = 0;
		System.out.println("after enq, front = " + front + ", rear = " + rear);

	};
	
	/**
	 * Return the first element of the queue without removing it.
	 * @return element that would be dequeued.
	 * @throws NoSuchELementException if the queue is empty
	 */
	public E front() {
		System.out.println("front = " + front + ", rear = " + rear);
		if (isEmpty()) throw new NoSuchElementException("empty!");
		return data[front];
	}
	
	/**
	 * Remove and return the first element in the queue.
	 * @return element after removing from front of queue
	 * @throws NoSuchELementException if the queue is empty
	 */
	public E dequeue() 
	{
		if (isEmpty()) throw new NoSuchElementException("no more");
		E p = data[front];
		++front;
		if (front >= data.length) front = 0;
		return p;
	};
	
	/**
	 * Return whether the queue is empty
	 * @return wheher queue has no elements in it
	 */
	public boolean isEmpty() {
		return rear == front;
	}
	
	/**
	 * Return the number of elements in the queue.
	 * @return number of elements in queue
	 */
	public int size() {
		return rear - front;
	}
}
