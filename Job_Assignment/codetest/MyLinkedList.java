/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

import java.util.Iterator;

public class MyLinkedList<E> implements Iterable<E>, IMyCollection<E> {
	
	private Node headSentinel;
	private Node tailSentinel;
	private int size;
	
	public MyLinkedList(){
		headSentinel = new Node(null);
		tailSentinel = new Node(null);
		headSentinel.next = tailSentinel;
	}
	
	@Override
	public E get(int index) {
		Node temp = headSentinel;
		int i = 0;
		
		if(index >= 0 && index <= size) {
			while(i != index) {
				temp = temp.next;
				i++;
			}
		} else {
			throw new IndexOutOfBoundsException();
		}
		
		return temp.next.element;
	}
	
	/**
	 * Adds element to first position in List to ensure constant time
	 */
	@Override
	public boolean add(E element) {
		Node toAdd = new Node(element);
		toAdd.next = headSentinel.next;
		headSentinel.next = toAdd;
		size++;
		return true;
	}

	@Override
	public boolean set(E element, int index) throws IndexOutOfBoundsException {
		Node temp = headSentinel;
		int i = 0;
		
		if(index >= 0 && index <= size) {
			while(i != index) {
				temp = temp.next;
				i++;
			}
			Node newNode = new Node(element);
			newNode.next = temp.next;
			temp.next = newNode;
			size++;
		} else {
			throw new IndexOutOfBoundsException();
		}
		
		return true;
	}

	@Override
	public E remove(int index) {
		Node temp = headSentinel;
		Node removed = null;
		int i = 0;
		
		if(index >= 0 && index <= size) {
			while(i != index) {
				temp = temp.next;
				i++;
			}
			removed = temp.next;
			temp.next = temp.next.next;
			size--;
		} else {
			throw new IndexOutOfBoundsException();
		}
		
		return removed.element;
	}

	@Override
	public boolean remove(E element) {
		Node temp = headSentinel;
		boolean removed = false;
		
		while(temp.next.element != null && !removed) {
			if(temp.next.element.equals(element)) {
				temp.next = temp.next.next;
				removed = true;
				size--;
			}
		}
		
		return removed;
	}

	@Override
	public boolean contains(E element) {
		boolean found = false;
		Node temp = headSentinel;
		
		while(temp.next.element != null && !found) {
			if(temp.next.element.equals(element))
				found = true;
		}
		
		return found;
	}
	
	public int size() {
		return size;
	}
	
	private class Node{
		private E element;
		private Node next;
		
		public Node(E element) {
			this.element = element;
		}
	}
	
	@Override
	public Iterator<E> iterator() {
		return new CustomIterator();
	}
	
	private class CustomIterator implements Iterator<E>{
		private Node current;
		
		public CustomIterator() {
			current = headSentinel;
		}
		
		@Override
		public boolean hasNext() {
			return current.next.element != null;
		}

		@Override
		public E next() {
			current = current.next;
			return current.element;
		}
	}

}
