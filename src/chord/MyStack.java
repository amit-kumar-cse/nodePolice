package chord;

import java.util.Deque;
import java.util.LinkedList;

public class MyStack<E> {
	private Deque<E> list;
	
	MyStack()	{
		this.list = new LinkedList<E>();
	}
	
	public boolean isEmpty()	{
		return this.list.isEmpty();
	}
	
	public void push(E e)	{
		this.list.addLast(e);
	}
	
	public E pop()	{
		return this.list.removeLast();
	}
	
	public void clear()	{
		this.list.clear();
	}
	
	public int size()	{
		return list.size();
	}
}
