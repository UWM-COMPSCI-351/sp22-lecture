// This is an assignment for students to complete after reading Chapter 4 of
// "Data Structures and Other Objects Using Java" by Michael Main.

package edu.uwm.cs351;



/******************************************************************************
 * This class is a homework assignment;
 * A Sequence is an aggregate class with a cursor (not an iterator)
 * The sequence can have a special "current element," which is specified and 
 * accessed through four methods
 * (start, getCurrent, advance and hasCurrent).
 *
 ******************************************************************************/
public class Sequence<E> implements Cloneable
{

	
	private static boolean doReport = true; // used only by invariant checker
	
	/**
	 * Used to report an error found when checking the invariant.
	 * By providing a string, this will help debugging the class if the invariant should fail.
	 * @param error string to print to report the exact error found
	 * @return false always
	 */
	private boolean report(String error) {
		if (doReport) System.out.println("Invariant error found: " + error);
		return false;
	}

	/**
	 * Check the invariant.  For information on what a class invariant is,
	 * please read page 123 in the textbook.
	 * Return false if any problem is found.  Returning the result
	 * of {@link #report(String)} will make it easier to debug invariant problems.
	 * @return whether invariant is currently true
	 */
	private boolean wellFormed() {
		// TODO
		
		// If no problems found, then return true:
		return true;
	}

	/**
	 * Create an empty sequence.
	 * @param - none
	 * @postcondition
	 *   This sequence is empty 
	 **/   
	public Sequence( )
	{
		// TODO: initialize data structure
		assert wellFormed() : "invariant failed in constructor";
	}

	/**
	 * Determine the number of elements in this sequence.
	 * @param - none
	 * @return
	 *   the number of elements in this sequence
	 **/ 
	public int size( )
	{
		assert wellFormed() : "invariant wrong at start of size()";
		return -1; // TODO
		// This method shouldn't modify any fields, hence no assertion at end
	}

	/**
	 * Set the current element at the front of this sequence.
	 * @param - none
	 * @postcondition
	 *   The front element of this sequence is now the current element (but 
	 *   if this sequence has no elements at all, then there is no current 
	 *   element).
	 **/ 
	public void start( )
	{
		assert wellFormed() : "invariant wrong at start of start()";
		// TODO
		assert wellFormed() : "invariant wrong at end of start()";
	}

	/**
	 * Accessor method to determine whether this sequence has a specified 
	 * current element that can be retrieved with the 
	 * getCurrent method. 
	 * @param - none
	 * @return
	 *   true (there is a current element) or false (there is no current element at the moment)
	 **/
	public boolean hasCurrent( )
	{
		assert wellFormed() : "invariant wrong at start of getCurrent()";
		return false; // TODO
		// This method shouldn't modify any fields, hence no assertion at end
	}

	/**
	 * Accessor method to get the current element of this sequence. 
	 * @param - none
	 * @precondition
	 *   hasCurrent() returns true.
	 * @return
	 *   the current element of this sequence
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   getCurrent may not be called.
	 **/
	public E getCurrent( )
	{
		assert wellFormed() : "invariant wrong at start of getCurrent()";
		return null; // TODO
		// This method shouldn't modify any fields, hence no assertion at end
	}

	/**
	 * Move forward, so that the current element will be the next element in
	 * this sequence.
	 * @param - none
	 * @precondition
	 *   hasCurrent() returns true. 
	 * @postcondition
	 *   If the current element was already the end element of this sequence 
	 *   (with nothing after it), then there is no longer any current element. 
	 *   Otherwise, the new element is the element immediately after the 
	 *   original current element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   advance may not be called.
	 **/
	public void advance( )
	{
		assert wellFormed() : "invariant wrong at start of advance()";
		// TODO
		assert wellFormed() : "invariant wrong at end of advance()";
	}

	/**
	 * Add a new element to this sequence, before the current element,
	 * if any, or otherwise at the end.
	 * @param element
	 *   the new element that is being added
	 * @postcondition
	 *   A new copy of the element has been added to this sequence. If there was
	 *   a current element, then the new element is placed before the current
	 *   element. If there was no current element, then the new element is placed
	 *   at the end of the sequence. In all cases, the new element becomes the
	 *   new current element of this sequence. 
	 **/
	public void insert(E element)
	{
		assert wellFormed() : "invariant wrong at start of insert";
		// TODO
		assert wellFormed() : "invariant wrong at end of insert";
	}

	/**
	 * Remove the current element from this sequence.
	 * @param - none
	 * @precondition
	 *   hasCurrent() returns true.
	 * @postcondition
	 *   The current element has been removed from this sequence, and the 
	 *   following element (if there is one) is now the new current element. 
	 *   If there was no following element, then there is now no current 
	 *   element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   removeCurrent may not be called. 
	 **/
	public void removeCurrent( )
	{
		assert wellFormed() : "invariant wrong at start of removeCurrent()";
		// TODO
		assert wellFormed() : "invariant wrong at end of removeCurrent()";
	}

	/**
	 * Place the contents of another sequence (which may be the
	 * same sequence as this!) into this sequence before the current element (if any).
	 * If there is no current element, place the new elements at the end of this sequence.
	 * @param addend
	 *   a sequence whose contents will be placed into this sequence
	 * @precondition
	 *   The parameter, addend, is not null. 
	 * @postcondition
	 *   The elements from addend have been placed into
	 *   this sequence. The current element of this sequence is now
	 *   the first element inserted (if any).  If the added sequence
	 *   is empty, this sequence and the current element (if any) are
	 *   unchanged.
	 * @exception NullPointerException
	 *   Indicates that addend is null. 
	 **/
	public void insertAll(Sequence<E> addend)
	{
		assert wellFormed() : "invariant wrong at start of addAll";
		assert addend.wellFormed() : "invariant of parameter wrong at start of addAll";
		// TODO
		assert wellFormed() : "invariant wrong at end of insertAll";
		assert addend.wellFormed() : "invariant of parameter wrong at end of insertAll";
	}   


	/**
	 * Generate a copy of this sequence.
	 * @param - none
	 * @return
	 *   The return value is a copy of this sequence. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa.
	 *   Whatever was current in the original object is now current in the clone.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	@SuppressWarnings("unchecked")
	public Sequence<E> clone( )
	{  	 
		assert wellFormed() : "invariant wrong at start of clone()";

		Sequence<E> result;

		try
		{
			result = (Sequence<E>) super.clone( );
		}
		catch (CloneNotSupportedException e)
		{  
			// This exception should not occur. But if it does, it would probably
			// indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable"
			// clause at the start of this class.
			throw new RuntimeException
			("This class does not implement Cloneable");
		}

		// TODO
		assert wellFormed() : "invariant wrong at end of clone()";
		assert result.wellFormed() : "invariant wrong for result of clone()";
		return result;
	}

	// TODO: doc comment
	public void transform(Object pickABetterTypeAndName) {
		// What else do we need here ?
		report("transform not implemented yet"); // TODO
		// WHat else do we need here ?
	}
}

