package edu.iastate.cs228.hw03;

/**
 * A class of bags whose entries are stored in a chain of doubly linked nodes.
 * 
 * @author Andrei Baechle
 * 
 */

public class DoublyLinkedBag<T> implements BagInterface<T> {
	private DoublyLinkedNode firstN; // Reference to first node
	private DoublyLinkedNode prevN;
	private int numOfEntries;

	public DoublyLinkedBag() {
		firstN = null;
		prevN = null;
		numOfEntries = 0;

	} // end default constructor

	public boolean add(T newEntry) {
		// Add to beginning of chain:
		DoublyLinkedNode newNode = new DoublyLinkedNode(newEntry);
		newNode.next = firstN; // Make new node reference rest of chain
		newNode.prev = prevN;

		firstN = newNode; // New node is at beginning of chain
		numOfEntries++;

		return true;
	} // end add

	public T[] toArray() {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numOfEntries]; // Unchecked cast

		int index = 0;
		DoublyLinkedNode currentNode = firstN;
		while ((index < numOfEntries) && (currentNode != null)) {
			result[index] = currentNode.data;
			index++;
			currentNode = currentNode.next;
		} // end while

		return result;
	} // end toArray

	public boolean isEmpty() {
		if (firstN == null)
			return true;

		return false;
	} // end isEmpty

	public int getCurrentSize() {

		return numOfEntries;
	} // end getCurrentSize

	public int getFrequencyOf(T anEntry) {
		int frequency = 0;

		int counter = 0;
		DoublyLinkedNode currentNode = firstN;
		while ((counter < numOfEntries) && (currentNode != null)) {
			if (anEntry.equals(currentNode.data)) {
				frequency++;
			} // end if

			counter++;
			currentNode = currentNode.next;
		} // end while
		return frequency;
	} // end getFrequencyOf

	public boolean contains(T anEntry) {
		boolean found = false;
		DoublyLinkedNode currentNode = firstN;

		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.data))
				found = true;
			else
				currentNode = currentNode.next;
		} // end while

		return found;
	} // end contains

	public void clear() {
		while (!isEmpty())
			remove();
	} // end clear

	public T remove() {
		T result = null;
		if (firstN != null) {
			result = firstN.data;
			firstN = firstN.next; // Remove first node from chain
			numOfEntries--;
		} // end if

		return result;
	} // end remove

	public boolean remove(T anEntry) {
		boolean result = false;
		int index = 0;
		DoublyLinkedNode nodeN = firstN;

		if (firstN != null) {
			while (index < numOfEntries) {
				if (nodeN == anEntry)
					result = true;
				remove();
				numOfEntries--;
				nodeN = nodeN.next;
			}
			nodeN.data = firstN.data; // Replace located entry with entry in first node

			firstN = firstN.next; // Remove first node
			numOfEntries--;

			result = true;
		} // end if
		return result;
	} // end remove

	private DoublyLinkedNode getReferenceTo(T anEntry) {
		boolean found = false;
		DoublyLinkedNode curNode = firstN;

		while (!found && curNode != null) {
			if (anEntry.equals(curNode.data))
				found = true;
			else
				curNode = curNode.next;
		}
		return curNode;
	} // end getReferenceTo

	public T replace(T replacement) {
		T currNode;
		if (firstN == null) {
			return null;
		} else {
			currNode = firstN.data;
			firstN.data = replacement;
		}

		return currNode;
	}

	public void removeEvery(T anEntry) {
		DoublyLinkedNode anNode = getReferenceTo(anEntry);
		while (anNode != null) {
			anNode.data = firstN.data;
			firstN = firstN.next;
			numOfEntries--;
			anNode = getReferenceTo(anEntry);
		}
	}

	/**
	 * Override the equals method of Object class so that it returns true when the
	 * contents of two DoublyLinkedBags are same. Note that two equal
	 * DoublyLinkedBags contain the same number of entries, and each entry occurs in
	 * each DoublyLinkedBag the same number of times. I.e., the elements in two do
	 * not need to be in exact same location.
	 * 
	 * Before checking the contents inside this method make sure that the passed in
	 * object is not null, is of the same runtime class, and the lengths are same.
	 * If any of these fail you can return false. Otherwise, you base your return
	 * results on contents. (At the start you can also do the quick check if both
	 * refer to the same object in memory.)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {

		boolean eq = false;
		DoublyLinkedNode curNode = firstN;

		int index = 0;

		if (obj != null && firstN != null) {
			while (index < numOfEntries) {
				index++;
				if (((BagInterface<T>) obj).contains(curNode.data)) {
					curNode = curNode.next;
					return true;

				}
			}
		}

		return eq;
	}

	/**
	 * Returns String representation of the items in this bag. For example, it would
	 * return [A, B, C] if bag had three Strings "A", "B", and "C".
	 * 
	 * @return String representation of items in this bag enclosed in square
	 *         brackets, separated by comma and a single space (see example above).
	 *         You can rely on the fact that items' proper toString method was
	 *         implemented. In this method ONLY if you need to you can use String
	 *         class's methods. Also, ONLY in this method you can use fully
	 *         qualified name for StringBuffer class, and use all of its methods.
	 */
	@Override
	public String toString() {
		// The cast is safe because the new array contains null entries.
		int index = 0;
		String strf = "[";
		DoublyLinkedNode curNode = firstN;
		if (firstN == null)
			return "[]";

		else
			while (index < numOfEntries) {
				index++;
				if (index == numOfEntries) {
					strf = strf + curNode.data + "]";
					curNode = curNode.next;
				} else {
					strf = strf + curNode.data + ", ";
					curNode = curNode.next;
				}
			}

		// end for

		return strf; // this is returned in case bag is empty.
	}

	// A class of nodes for a chain of doubly linked nodes.
	private class DoublyLinkedNode {
		private T data; // Entry in bag
		private DoublyLinkedNode next; // Link to next node
		private DoublyLinkedNode prev; // Link to previous node

		private DoublyLinkedNode(T dataPortion) {
			this(dataPortion, null, null);
		} // end constructor

		private DoublyLinkedNode(T dataPortion, DoublyLinkedNode nextNode, DoublyLinkedNode previousNode) {
			data = dataPortion;
			next = nextNode;
			prev = previousNode;
		} // end constructor

	} // end DoublyLinkedNode

} // end DoublyLinkedBag
