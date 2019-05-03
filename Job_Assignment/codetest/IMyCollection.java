/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

public interface IMyCollection<E> {
	public E get(int index);
	public boolean add(E element);
	public boolean set(E element, int index);
	public E remove(int index);
	public boolean remove(E element);
	public boolean contains(E element);
	public int size();
}
